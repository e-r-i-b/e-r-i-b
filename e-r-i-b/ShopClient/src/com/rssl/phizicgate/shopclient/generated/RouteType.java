/**
 * RouteType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.shopclient.generated;

public class RouteType  implements java.io.Serializable {
    private java.lang.String airlineName;

    private com.rssl.phizicgate.shopclient.generated.DepartureType departure;

    private com.rssl.phizicgate.shopclient.generated.ArrivalType arrival;

    public RouteType() {
    }

    public RouteType(
           java.lang.String airlineName,
           com.rssl.phizicgate.shopclient.generated.DepartureType departure,
           com.rssl.phizicgate.shopclient.generated.ArrivalType arrival) {
           this.airlineName = airlineName;
           this.departure = departure;
           this.arrival = arrival;
    }


    /**
     * Gets the airlineName value for this RouteType.
     * 
     * @return airlineName
     */
    public java.lang.String getAirlineName() {
        return airlineName;
    }


    /**
     * Sets the airlineName value for this RouteType.
     * 
     * @param airlineName
     */
    public void setAirlineName(java.lang.String airlineName) {
        this.airlineName = airlineName;
    }


    /**
     * Gets the departure value for this RouteType.
     * 
     * @return departure
     */
    public com.rssl.phizicgate.shopclient.generated.DepartureType getDeparture() {
        return departure;
    }


    /**
     * Sets the departure value for this RouteType.
     * 
     * @param departure
     */
    public void setDeparture(com.rssl.phizicgate.shopclient.generated.DepartureType departure) {
        this.departure = departure;
    }


    /**
     * Gets the arrival value for this RouteType.
     * 
     * @return arrival
     */
    public com.rssl.phizicgate.shopclient.generated.ArrivalType getArrival() {
        return arrival;
    }


    /**
     * Sets the arrival value for this RouteType.
     * 
     * @param arrival
     */
    public void setArrival(com.rssl.phizicgate.shopclient.generated.ArrivalType arrival) {
        this.arrival = arrival;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RouteType)) return false;
        RouteType other = (RouteType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.airlineName==null && other.getAirlineName()==null) || 
             (this.airlineName!=null &&
              this.airlineName.equals(other.getAirlineName()))) &&
            ((this.departure==null && other.getDeparture()==null) || 
             (this.departure!=null &&
              this.departure.equals(other.getDeparture()))) &&
            ((this.arrival==null && other.getArrival()==null) || 
             (this.arrival!=null &&
              this.arrival.equals(other.getArrival())));
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
        if (getAirlineName() != null) {
            _hashCode += getAirlineName().hashCode();
        }
        if (getDeparture() != null) {
            _hashCode += getDeparture().hashCode();
        }
        if (getArrival() != null) {
            _hashCode += getArrival().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RouteType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "RouteType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airlineName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "AirlineName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departure");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Departure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "DepartureType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arrival");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Arrival"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ArrivalType"));
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
