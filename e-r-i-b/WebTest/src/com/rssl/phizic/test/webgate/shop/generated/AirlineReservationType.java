/**
 * AirlineReservationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.shop.generated;

public class AirlineReservationType  implements java.io.Serializable {
    private java.lang.String reservId;

    private java.lang.String reservExpiration;

    private com.rssl.phizic.test.webgate.shop.generated.PassengerType[] passengersList;

    private com.rssl.phizic.test.webgate.shop.generated.RouteType[] routesList;

    public AirlineReservationType() {
    }

    public AirlineReservationType(
           java.lang.String reservId,
           java.lang.String reservExpiration,
           com.rssl.phizic.test.webgate.shop.generated.PassengerType[] passengersList,
           com.rssl.phizic.test.webgate.shop.generated.RouteType[] routesList) {
           this.reservId = reservId;
           this.reservExpiration = reservExpiration;
           this.passengersList = passengersList;
           this.routesList = routesList;
    }


    /**
     * Gets the reservId value for this AirlineReservationType.
     * 
     * @return reservId
     */
    public java.lang.String getReservId() {
        return reservId;
    }


    /**
     * Sets the reservId value for this AirlineReservationType.
     * 
     * @param reservId
     */
    public void setReservId(java.lang.String reservId) {
        this.reservId = reservId;
    }


    /**
     * Gets the reservExpiration value for this AirlineReservationType.
     * 
     * @return reservExpiration
     */
    public java.lang.String getReservExpiration() {
        return reservExpiration;
    }


    /**
     * Sets the reservExpiration value for this AirlineReservationType.
     * 
     * @param reservExpiration
     */
    public void setReservExpiration(java.lang.String reservExpiration) {
        this.reservExpiration = reservExpiration;
    }


    /**
     * Gets the passengersList value for this AirlineReservationType.
     * 
     * @return passengersList
     */
    public com.rssl.phizic.test.webgate.shop.generated.PassengerType[] getPassengersList() {
        return passengersList;
    }


    /**
     * Sets the passengersList value for this AirlineReservationType.
     * 
     * @param passengersList
     */
    public void setPassengersList(com.rssl.phizic.test.webgate.shop.generated.PassengerType[] passengersList) {
        this.passengersList = passengersList;
    }


    /**
     * Gets the routesList value for this AirlineReservationType.
     * 
     * @return routesList
     */
    public com.rssl.phizic.test.webgate.shop.generated.RouteType[] getRoutesList() {
        return routesList;
    }


    /**
     * Sets the routesList value for this AirlineReservationType.
     * 
     * @param routesList
     */
    public void setRoutesList(com.rssl.phizic.test.webgate.shop.generated.RouteType[] routesList) {
        this.routesList = routesList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AirlineReservationType)) return false;
        AirlineReservationType other = (AirlineReservationType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reservId==null && other.getReservId()==null) || 
             (this.reservId!=null &&
              this.reservId.equals(other.getReservId()))) &&
            ((this.reservExpiration==null && other.getReservExpiration()==null) || 
             (this.reservExpiration!=null &&
              this.reservExpiration.equals(other.getReservExpiration()))) &&
            ((this.passengersList==null && other.getPassengersList()==null) || 
             (this.passengersList!=null &&
              java.util.Arrays.equals(this.passengersList, other.getPassengersList()))) &&
            ((this.routesList==null && other.getRoutesList()==null) || 
             (this.routesList!=null &&
              java.util.Arrays.equals(this.routesList, other.getRoutesList())));
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
        if (getReservId() != null) {
            _hashCode += getReservId().hashCode();
        }
        if (getReservExpiration() != null) {
            _hashCode += getReservExpiration().hashCode();
        }
        if (getPassengersList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPassengersList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPassengersList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRoutesList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRoutesList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRoutesList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AirlineReservationType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "AirlineReservationType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ReservId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservExpiration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ReservExpiration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passengersList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "PassengersList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "PassengerType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Passenger"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("routesList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "RoutesList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "RouteType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Route"));
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
