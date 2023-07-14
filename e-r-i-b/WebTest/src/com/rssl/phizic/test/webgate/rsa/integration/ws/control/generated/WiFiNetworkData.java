/**
 * WiFiNetworkData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class WiFiNetworkData  implements java.io.Serializable {
    private java.lang.String stationName;

    private java.lang.String bbsId;

    private java.lang.Integer signalStrength;

    private java.lang.String channel;

    private java.lang.String ssId;

    public WiFiNetworkData() {
    }

    public WiFiNetworkData(
           java.lang.String stationName,
           java.lang.String bbsId,
           java.lang.Integer signalStrength,
           java.lang.String channel,
           java.lang.String ssId) {
           this.stationName = stationName;
           this.bbsId = bbsId;
           this.signalStrength = signalStrength;
           this.channel = channel;
           this.ssId = ssId;
    }


    /**
     * Gets the stationName value for this WiFiNetworkData.
     * 
     * @return stationName
     */
    public java.lang.String getStationName() {
        return stationName;
    }


    /**
     * Sets the stationName value for this WiFiNetworkData.
     * 
     * @param stationName
     */
    public void setStationName(java.lang.String stationName) {
        this.stationName = stationName;
    }


    /**
     * Gets the bbsId value for this WiFiNetworkData.
     * 
     * @return bbsId
     */
    public java.lang.String getBbsId() {
        return bbsId;
    }


    /**
     * Sets the bbsId value for this WiFiNetworkData.
     * 
     * @param bbsId
     */
    public void setBbsId(java.lang.String bbsId) {
        this.bbsId = bbsId;
    }


    /**
     * Gets the signalStrength value for this WiFiNetworkData.
     * 
     * @return signalStrength
     */
    public java.lang.Integer getSignalStrength() {
        return signalStrength;
    }


    /**
     * Sets the signalStrength value for this WiFiNetworkData.
     * 
     * @param signalStrength
     */
    public void setSignalStrength(java.lang.Integer signalStrength) {
        this.signalStrength = signalStrength;
    }


    /**
     * Gets the channel value for this WiFiNetworkData.
     * 
     * @return channel
     */
    public java.lang.String getChannel() {
        return channel;
    }


    /**
     * Sets the channel value for this WiFiNetworkData.
     * 
     * @param channel
     */
    public void setChannel(java.lang.String channel) {
        this.channel = channel;
    }


    /**
     * Gets the ssId value for this WiFiNetworkData.
     * 
     * @return ssId
     */
    public java.lang.String getSsId() {
        return ssId;
    }


    /**
     * Sets the ssId value for this WiFiNetworkData.
     * 
     * @param ssId
     */
    public void setSsId(java.lang.String ssId) {
        this.ssId = ssId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WiFiNetworkData)) return false;
        WiFiNetworkData other = (WiFiNetworkData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.stationName==null && other.getStationName()==null) || 
             (this.stationName!=null &&
              this.stationName.equals(other.getStationName()))) &&
            ((this.bbsId==null && other.getBbsId()==null) || 
             (this.bbsId!=null &&
              this.bbsId.equals(other.getBbsId()))) &&
            ((this.signalStrength==null && other.getSignalStrength()==null) || 
             (this.signalStrength!=null &&
              this.signalStrength.equals(other.getSignalStrength()))) &&
            ((this.channel==null && other.getChannel()==null) || 
             (this.channel!=null &&
              this.channel.equals(other.getChannel()))) &&
            ((this.ssId==null && other.getSsId()==null) || 
             (this.ssId!=null &&
              this.ssId.equals(other.getSsId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getStationName() != null) {
            _hashCode += getStationName().hashCode();
        }
        if (getBbsId() != null) {
            _hashCode += getBbsId().hashCode();
        }
        if (getSignalStrength() != null) {
            _hashCode += getSignalStrength().hashCode();
        }
        if (getChannel() != null) {
            _hashCode += getChannel().hashCode();
        }
        if (getSsId() != null) {
            _hashCode += getSsId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WiFiNetworkData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "WiFiNetworkData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "stationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bbsId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "bbsId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signalStrength");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "signalStrength"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ssId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ssId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
