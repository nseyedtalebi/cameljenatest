package com;

import com.google.gson.Gson;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

public class Testbed {
    public Testbed(){}

    public void doStuff(String messageBody){
        NetFlow nfn = new Gson().fromJson(messageBody, NetFlow.class);
        Model netflowRDF = NF2RDF.getRDF(nfn);
        String queryString = "PREFIX nf: <"+NetFlowOnt.getURI()+"/> "
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
                + "CONSTRUCT {?s ?p ?o} "
                //+ "SELECT ?s ?p ?o "
                + "WHERE {?s nf:asSrc <"+NetFlowOnt.asn.getURI()+"/23162"+">. "
                + "?s nf:asDst <"+NetFlowOnt.asn.getURI()+"/30700"+">. "
                + "?s ?p ?o. "
                + "?s nf:bytes ?bytes "
                + "FILTER(?bytes > \"1024\"^^xsd:int)}";
        //System.out.println(queryString);
        //RDFDataMgr.write(System.out,netflowRDF,Lang.NTRIPLES);
        Query query = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(query, netflowRDF) ;
        Model resultModel = qexec.execConstruct() ;
        //ResultSet res = qexec.execSelect();
        //while(res.hasNext()){
        //    System.out.println(res.next().toString());
        //}
        /*TODO: Get CONSTRUCT query working*/
        /*TODO: Investiage how to do windowing and timestamping
            -Perhaps a Window class that keeps track of start, end, and advances the window?
            -Figure out how to add timestamps/generate quads, preferrably without having to rewrite my N2RDF class
         */
        //resultModel.write(System.out);
        RDFDataMgr.write(System.out,resultModel, Lang.NTRIPLES);
        qexec.close();
    }
}
