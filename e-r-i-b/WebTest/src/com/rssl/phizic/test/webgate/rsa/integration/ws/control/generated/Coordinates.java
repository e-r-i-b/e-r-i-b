/**
 * Coordinates.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class Coordinates  implements java.io.Serializable {
    private java.math.BigDecimal longitude;

    private java.math.BigDecimal latitude;

    private java.math.BigDecimal altitude;

    public Coordinates() {
    }

    public Coordinates(
           java.math.BigDecimal longitude,
           java.math.BigDecimal latitude,
           java.math.BigDecimal altitude) {
           this.longitude = longitude;
           this.latitude = latitude;
           this.altitude = altitude;
    }


    /**
     * Gets the longitude value for this Coordinates.
     * 
     * @return longitude
     */
    public java.math.BigDecimal getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this Coordinates.
     * 
     * @param longitude
     */
    public void setLongitude(java.math.BigDecimal longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets the latitude value for this Coordinates.
     * 
     * @return latitude
     */
    public java.math.BigDecimal getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude value for this Coordinates.
     * 
     * @param latitude
     */
    public void setLatitude(java.math.BigDecimal latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the altitude value for this Coordinates.
     * 
     * @return altitude
     */
    public java.math.BigDecimal getAltitude() {
        return altitude;
    }


    /**
     * Sets the altitude value for this Coordinates.
     * 
     * @param altitude
     */
    public void setAltitude(java.math.BigDecimal altitude) {
        this.altitude = altitude;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Coordinates)) return false;
        Coordinates other = (Coordinates) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.longitude==null && other.getLongitude()==null) || 
             (this.longitude!=null &&
              this.longitude.equals(other.getLongitude()))) &&
            ((this.latitude==null && other.getLatitude()==null) || 
             (this.latitude!=null &&
              this.latitude.equals(other.getLatitude()))) &&
            ((this.altitude==null && other.getAltitude()==null) || 
             (this.altitude!=null &&
              this.altitude.equals(other.getAltitude())));
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
        if (getLongitude() != null) {
            _hashCode += getLongitude().hashCode();
        }
        if (getLatitude() != null) {
            _hashCode += getLatitude().hashCode();
        }
        if (getAltitude() != null) {
            _hashCode += getAltitude().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Coordinates.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Coordinates"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "longitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "latitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "altitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
