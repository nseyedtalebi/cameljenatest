package com;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NetFlow {

    @SerializedName("event_type")
    @Expose
    private String eventType;
    @SerializedName("as_src")
    @Expose
    private Integer asSrc;
    @SerializedName("as_dst")
    @Expose
    private Integer asDst;
    @SerializedName("as_path")
    @Expose
    private String asPath;
    @SerializedName("local_pref")
    @Expose
    private Integer localPref;
    @SerializedName("med")
    @Expose
    private Integer med;
    @SerializedName("peer_as_dst")
    @Expose
    private Integer peerAsDst;
    @SerializedName("ip_src")
    @Expose
    private String ipSrc;
    @SerializedName("ip_dst")
    @Expose
    private String ipDst;
    @SerializedName("port_src")
    @Expose
    private Integer portSrc;
    @SerializedName("port_dst")
    @Expose
    private Integer portDst;
    @SerializedName("ip_proto")
    @Expose
    private String ipProto;
    @SerializedName("tos")
    @Expose
    private Integer tos;
    @SerializedName("stamp_inserted")
    @Expose
    private String stampInserted;
    @SerializedName("stamp_updated")
    @Expose
    private String stampUpdated;
    @SerializedName("packets")
    @Expose
    private Integer packets;
    @SerializedName("bytes")
    @Expose
    private Integer bytes;
    @SerializedName("writer_id")
    @Expose
    private String writerId;

    /**
     * No args constructor for use in serialization
     *
     */
    public NetFlow() {
    }

    /**
     *
     * @param med
     * @param bytes
     * @param ipProto
     * @param portDst
     * @param asPath
     * @param packets
     * @param eventType
     * @param asDst
     * @param peerAsDst
     * @param ipSrc
     * @param tos
     * @param portSrc
     * @param stampUpdated
     * @param asSrc
     * @param localPref
     * @param stampInserted
     * @param ipDst
     * @param writerId
     */
    public NetFlow(String eventType, Integer asSrc, Integer asDst, String asPath, Integer localPref, Integer med, Integer peerAsDst, String ipSrc, String ipDst, Integer portSrc, Integer portDst, String ipProto, Integer tos, String stampInserted, String stampUpdated, Integer packets, Integer bytes, String writerId) {
        super();
        this.eventType = eventType;
        this.asSrc = asSrc;
        this.asDst = asDst;
        this.asPath = asPath;
        this.localPref = localPref;
        this.med = med;
        this.peerAsDst = peerAsDst;
        this.ipSrc = ipSrc;
        this.ipDst = ipDst;
        this.portSrc = portSrc;
        this.portDst = portDst;
        this.ipProto = ipProto;
        this.tos = tos;
        this.stampInserted = stampInserted;
        this.stampUpdated = stampUpdated;
        this.packets = packets;
        this.bytes = bytes;
        this.writerId = writerId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getAsSrc() {
        return asSrc;
    }

    public void setAsSrc(Integer asSrc) {
        this.asSrc = asSrc;
    }

    public Integer getAsDst() {
        return asDst;
    }

    public void setAsDst(Integer asDst) {
        this.asDst = asDst;
    }

    public String getAsPath() {
        return asPath;
    }

    public void setAsPath(String asPath) {
        this.asPath = asPath;
    }

    public Integer getLocalPref() {
        return localPref;
    }

    public void setLocalPref(Integer localPref) {
        this.localPref = localPref;
    }

    public Integer getMed() {
        return med;
    }

    public void setMed(Integer med) {
        this.med = med;
    }

    public Integer getPeerAsDst() {
        return peerAsDst;
    }

    public void setPeerAsDst(Integer peerAsDst) {
        this.peerAsDst = peerAsDst;
    }

    public String getIpSrc() {
        return ipSrc;
    }

    public void setIpSrc(String ipSrc) {
        this.ipSrc = ipSrc;
    }

    public String getIpDst() {
        return ipDst;
    }

    public void setIpDst(String ipDst) {
        this.ipDst = ipDst;
    }

    public Integer getPortSrc() {
        return portSrc;
    }

    public void setPortSrc(Integer portSrc) {
        this.portSrc = portSrc;
    }

    public Integer getPortDst() {
        return portDst;
    }

    public void setPortDst(Integer portDst) {
        this.portDst = portDst;
    }

    public String getIpProto() {
        return ipProto;
    }

    public void setIpProto(String ipProto) {
        this.ipProto = ipProto;
    }

    public Integer getTos() {
        return tos;
    }

    public void setTos(Integer tos) {
        this.tos = tos;
    }

    public String getStampInserted() {
        return stampInserted;
    }

    public void setStampInserted(String stampInserted) {
        this.stampInserted = stampInserted;
    }

    public String getStampUpdated() {
        return stampUpdated;
    }

    public void setStampUpdated(String stampUpdated) {
        this.stampUpdated = stampUpdated;
    }

    public Integer getPackets() {
        return packets;
    }

    public void setPackets(Integer packets) {
        this.packets = packets;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("eventType", eventType).append("asSrc", asSrc).append("asDst", asDst).append("asPath", asPath).append("localPref", localPref).append("med", med).append("peerAsDst", peerAsDst).append("ipSrc", ipSrc).append("ipDst", ipDst).append("portSrc", portSrc).append("portDst", portDst).append("ipProto", ipProto).append("tos", tos).append("stampInserted", stampInserted).append("stampUpdated", stampUpdated).append("packets", packets).append("bytes", bytes).append("writerId", writerId).toString();
    }

}