package com;
import org.apache.jena.datatypes.xsd.impl.XSDDateTimeType;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class NF2RDF {
    /*The following is based on the procedure described in the specs associated with the OpenCOG project.
    Their site is at http://www.opencog.net and the spec is available from
    https://sourceforge.net/projects/opencyberontology/files/Specification/
    I made a few changes. First, the original spec does not specify a timezone (although the examples use Central time).
    IMHO, it doesn't make sense to *not* specify a time zone. I went with UTC because it's...universal.
    Also, I used four-byte ints for the port to avoid spending extra time and effort making something like a two-byte
    unsigned int in Java. Just using a regular four-byte int gets the whole range without pulling any teeth. Since I'm
    not using the spec exactly as written anyway, I figured, "why not?"
    */
    public static String getNetflowUID(String ipSrc,int srcPort,String ipDst,int dstPort,String protocolName,
                                       String stampInserted) {
        InetAddress rawipSrc = null;
        InetAddress rawipDst = null;
        try {
            rawipSrc = InetAddress.getByName(ipSrc);
            rawipDst = InetAddress.getByName(ipDst);
        } catch (UnknownHostException ex) {
            System.out.println("Couldn't convert ip address to raw form");
            System.out.println(ex);
        }
        ZonedDateTime stamp_inserted_utc = parseNetFlowDateString(stampInserted).withZoneSameInstant(ZoneId.of("UTC"));
        Long stamp_inserted_sec = stamp_inserted_utc.toInstant().toEpochMilli();
        int ipAddrTotalLength = rawipDst.getAddress().length + rawipDst.getAddress().length;

        /*field name: srcAddr srcPort dstAddr dstPort proto start*/
        /*bytes     :  4/16     4       4/16     4       1    8  */
        /*Note: 4 is for IPv4, 16 for IPv6                       */

        ByteBuffer uidInput = ByteBuffer.allocate(ipAddrTotalLength+17);
        uidInput.putLong(stamp_inserted_sec);
        uidInput.put(IPProtocols.getProtocolNumberByte(protocolName));
        uidInput.putInt(dstPort);
        uidInput.put(rawipDst.getAddress());
        uidInput.putInt(srcPort);
        uidInput.put(rawipSrc.getAddress());
        MessageDigest md5sum;
        try {
            md5sum = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException ex){
            //There damn well better be MD5.
            System.out.println("What? No MD5?");
            return "NO_MD5";
        }

        return Base64.getUrlEncoder().withoutPadding().encodeToString(md5sum.digest(uidInput.array()));
    }

    public static String getUTCTimestamp(){
        return ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static ZonedDateTime parseNetFlowDateString(String datestring){
        return ZonedDateTime.of(LocalDateTime.parse(datestring,
                DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")), ZoneId.systemDefault());
    }

    public static Model getRDF(NetFlow inputObject) {
        Model netflowRDF = ModelFactory.createDefaultModel();
        String netflowontns = "http://www.semanticweb.org/nimo3/ontologies/2018/4/netflow_test/";
        netflowRDF.setNsPrefix("netflowns",netflowontns);
        String flowID = NF2RDF.getNetflowUID(inputObject.getIpSrc(),
                inputObject.getPortSrc(),inputObject.getIpDst(),inputObject.getPortDst(),inputObject.getIpProto(),
                inputObject.getStampInserted());
        //Using string concatenation to build URLs feels dirty. Maybe there's a nice library to do it?
        Resource nfEvent = netflowRDF.createResource(NetFlowOnt.netflow.getURI()+"/"+flowID);
        Resource ipSrc = netflowRDF.createResource(NetFlowOnt.ipv4.getURI()+"/"+inputObject.getIpSrc());
        Resource ipDst = netflowRDF.createResource(NetFlowOnt.ipv4.getURI()+"/"+inputObject.getIpDst());
        Resource portSrc = netflowRDF.createResource(NetFlowOnt.port.getURI()+"/"+inputObject.getPortSrc());
        Resource portDst = netflowRDF.createResource(NetFlowOnt.port.getURI()+"/"+inputObject.getPortDst());
        Resource asSrc = netflowRDF.createResource(NetFlowOnt.asn.getURI()+"/"+inputObject.getAsSrc());
        Resource asDst = netflowRDF.createResource(NetFlowOnt.asn.getURI()+"/"+inputObject.getAsDst());
        Resource peerAsDst = netflowRDF.createResource(NetFlowOnt.asn.getURI()+"/"+inputObject.getPeerAsDst());

        nfEvent.addProperty(NetFlowOnt.ipSrc,ipSrc);
        nfEvent.addProperty(NetFlowOnt.ipDst,ipDst);
        nfEvent.addProperty(NetFlowOnt.asSrc,asSrc);
        nfEvent.addProperty(NetFlowOnt.asDst,asDst);
        nfEvent.addProperty(NetFlowOnt.portSrc,portSrc);
        nfEvent.addProperty(NetFlowOnt.portDst,portDst);
        nfEvent.addProperty(NetFlowOnt.peerAsDst,peerAsDst);
            /*I couldn't decide if I thought using typed literals was the best idea. I like that it makes
            the type explicit but could cause comparisons to fail if we're combining this with a dataset that
            doesn't make use of typed literal. This is something to keep in mind.
             */
        /*TODO: Change so asPath uses RDFS list or the like*/
        nfEvent.addProperty(NetFlowOnt.asPath,netflowRDF.createTypedLiteral(inputObject.getAsPath()));

        nfEvent.addProperty(NetFlowOnt.bytes,netflowRDF.createTypedLiteral(inputObject.getBytes()));
        nfEvent.addProperty(NetFlowOnt.eventType,netflowRDF.createTypedLiteral(inputObject.getEventType()));
        nfEvent.addProperty(NetFlowOnt.ipProto,netflowRDF.createTypedLiteral(inputObject.getIpProto()));
        nfEvent.addProperty(NetFlowOnt.localPref,netflowRDF.createTypedLiteral(inputObject.getLocalPref()));
        nfEvent.addProperty(NetFlowOnt.med,netflowRDF.createTypedLiteral(inputObject.getMed()));
        nfEvent.addProperty(NetFlowOnt.packets,netflowRDF.createTypedLiteral(inputObject.getPackets()));

        ZonedDateTime stampInserted = NF2RDF.parseNetFlowDateString(inputObject.getStampInserted());
        ZonedDateTime stampUpdated =  NF2RDF.parseNetFlowDateString(inputObject.getStampUpdated());
        nfEvent.addProperty(NetFlowOnt.stampInserted,
                netflowRDF.createTypedLiteral(stampInserted.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        XSDDateTimeType.XSDdateTime)
        );
        nfEvent.addProperty(NetFlowOnt.stampUpdated,
                netflowRDF.createTypedLiteral(stampUpdated.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        XSDDateTimeType.XSDdateTime)
        );
        nfEvent.addProperty(NetFlowOnt.tos,netflowRDF.createTypedLiteral(inputObject.getTos()));
        nfEvent.addProperty(NetFlowOnt.writerId,netflowRDF.createTypedLiteral(inputObject.getWriterId()));
        /*Note that we can easily get the union of two models. */
        return netflowRDF;
    }
}
