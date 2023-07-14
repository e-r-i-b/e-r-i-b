/**
 * GeoLocation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class GeoLocation  implements java.io.Serializable {
    private java.math.BigDecimal longitude;

    private java.math.BigDecimal latitude;

    private java.lang.Integer horizontalAccuracy;

    private java.lang.Integer altitude;

    private java.lang.Integer altitudeAccuracy;

    private java.math.BigDecimal heading;

    private java.lang.Integer speed;

    private java.lang.Long timestamp;

    private java.lang.Integer statusCode;

    public GeoLocation() {
    }

    public GeoLocation(
           java.math.BigDecimal longitude,
           java.math.BigDecimal latitude,
           java.lang.Integer horizontalAccuracy,
           java.lang.Integer altitude,
           java.lang.Integer altitudeAccuracy,
           java.math.BigDecimal heading,
           java.lang.Integer speed,
           java.lang.Long timestamp,
           java.lang.Integer statusCode) {
           this.longitude = longitude;
           this.latitude = latitude;
           this.horizontalAccuracy = horizontalAccuracy;
           this.altitude = altitude;
           this.altitudeAccuracy = altitudeAccuracy;
           this.heading = heading;
           this.speed = speed;
           this.timestamp = timestamp;
           this.statusCode = statusCode;
    }


    /**
     * Gets the longitude value for this GeoLocation.
     * 
     * @return longitude
     */
    public java.math.BigDecimal getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this GeoLocation.
     * 
     * @param longitude
     */
    public void setLongitude(java.math.BigDecimal longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets the latitude value for this GeoLocation.
     * 
     * @return latitude
     */
    public java.math.BigDecimal getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude value for this GeoLocation.
     * 
     * @param latitude
     */
    public void setLatitude(java.math.BigDecimal latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the horizontalAccuracy value for this GeoLocation.
     * 
     * @return horizontalAccuracy
     */
    public java.lang.Integer getHorizontalAccuracy() {
        return horizontalAccuracy;
    }


    /**
     * Sets the horizontalAccuracy value for this GeoLocation.
     * 
     * @param horizontalAccuracy
     */
    public void setHorizontalAccuracy(java.lang.Integer horizontalAccuracy) {
        this.horizontalAccuracy = horizontalAccuracy;
    }


    /**
     * Gets the altitude value for this GeoLocation.
     * 
     * @return altitude
     */
    public java.lang.Integer getAltitude() {
        return altitude;
    }


    /**
     * Sets the altitude value for this GeoLocation.
     * 
     * @param altitude
     */
    public void setAltitude(java.lang.Integer altitude) {
        this.altitude = altitude;
    }


    /**
     * Gets the altitudeAccuracy value for this GeoLocation.
     * 
     * @return altitudeAccuracy
     */
    public java.lang.Integer getAltitudeAccuracy() {
        return altitudeAccuracy;
    }


    /**
     * Sets the altitudeAccuracy value for this GeoLocation.
     * 
     * @param altitudeAccuracy
     */
    public void setAltitudeAccuracy(java.lang.Integer altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }


    /**
     * Gets the heading value for this GeoLocation.
     * 
     * @return heading
     */
    public java.math.BigDecimal getHeading() {
        return heading;
    }


    /**
     * Sets the heading value for this GeoLocation.
     * 
     * @param heading
     */
    public void setHeading(java.math.BigDecimal heading) {
        this.heading = heading;
    }


    /**
     * Gets the speed value for this GeoLocation.
     * 
     * @return speed
     */
    public java.lang.Integer getSpeed() {
        return speed;
    }


    /**
     * Sets the speed value for this GeoLocation.
     * 
     * @param speed
     */
    public void setSpeed(java.lang.Integer speed) {
        this.speed = speed;
    }


    /**
     * Gets the timestamp value for this GeoLocation.
     * 
     * @return timestamp
     */
    public java.lang.Long getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp value for this GeoLocation.
     * 
     * @param timestamp
     */
    public void setTimestamp(java.lang.Long timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Gets the statusCode value for this GeoLocation.
     * 
     * @return statusCode
     */
    public java.lang.Integer getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this GeoLocation.
     * 
     * @param statusCode
     */
    public void setStatusCode(java.lang.Integer statusCode) {
        this.statusCode = statusCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GeoLocation)) return false;
        GeoLocation other = (GeoLocation) obj;
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
            ((this.horizontalAccuracy==null && other.getHorizontalAccuracy()==null) || 
             (this.horizontalAccuracy!=null &&
              this.horizontalAccuracy.equals(other.getHorizontalAccuracy()))) &&
            ((this.altitude==null && other.getAltitude()==null) || 
             (this.altitude!=null &&
              this.altitude.equals(other.getAltitude()))) &&
            ((this.altitudeAccuracy==null && other.getAltitudeAccuracy()==null) || 
             (this.altitudeAccuracy!=null &&
              this.altitudeAccuracy.equals(other.getAltitudeAccuracy()))) &&
            ((this.heading==null && other.getHeading()==null) || 
             (this.heading!=null &&
              this.heading.equals(other.getHeading()))) &&
            ((this.speed==null && other.getSpeed()==null) || 
             (this.speed!=null &&
              this.speed.equals(other.getSpeed()))) &&
            ((this.timestamp==null && other.getTimestamp()==null) || 
             (this.timestamp!=null &&
              this.timestamp.equals(other.getTimestamp()))) &&
            ((this.statusCode==null && other.getStatusCode()==null) || 
             (this.statusCode!=null &&
              this.statusCode.equals(other.getStatusCode())));
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
        if (getHorizontalAccuracy() != null) {
            _hashCode += getHorizontalAccuracy().hashCode();
        }
        if (getAltitude() != null) {
            _hashCode += getAltitude().hashCode();
        }
        if (getAltitudeAccuracy() != null) {
            _hashCode += getAltitudeAccuracy().hashCode();
        }
        if (getHeading() != null) {
            _hashCode += getHeading().hashCode();
        }
        if (getSpeed() != null) {
            _hashCode += getSpeed().hashCode();
        }
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        if (getStatusCode() != null) {
            _hashCode += getStatusCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GeoLocation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GeoLocation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "longitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "latitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horizontalAccuracy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "horizontalAccuracy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "altitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altitudeAccuracy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "altitudeAccuracy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("heading");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "heading"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("speed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "speed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "statusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
