/**
 * FieldsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.shop.generated;

public class FieldsType  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.shop.generated.ItemType[] shop;

    private com.rssl.phizic.test.webgate.shop.generated.AirlineReservationType airlineReservation;

    public FieldsType() {
    }

    public FieldsType(
           com.rssl.phizic.test.webgate.shop.generated.ItemType[] shop,
           com.rssl.phizic.test.webgate.shop.generated.AirlineReservationType airlineReservation) {
           this.shop = shop;
           this.airlineReservation = airlineReservation;
    }


    /**
     * Gets the shop value for this FieldsType.
     * 
     * @return shop
     */
    public com.rssl.phizic.test.webgate.shop.generated.ItemType[] getShop() {
        return shop;
    }


    /**
     * Sets the shop value for this FieldsType.
     * 
     * @param shop
     */
    public void setShop(com.rssl.phizic.test.webgate.shop.generated.ItemType[] shop) {
        this.shop = shop;
    }


    /**
     * Gets the airlineReservation value for this FieldsType.
     * 
     * @return airlineReservation
     */
    public com.rssl.phizic.test.webgate.shop.generated.AirlineReservationType getAirlineReservation() {
        return airlineReservation;
    }


    /**
     * Sets the airlineReservation value for this FieldsType.
     * 
     * @param airlineReservation
     */
    public void setAirlineReservation(com.rssl.phizic.test.webgate.shop.generated.AirlineReservationType airlineReservation) {
        this.airlineReservation = airlineReservation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FieldsType)) return false;
        FieldsType other = (FieldsType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.shop==null && other.getShop()==null) || 
             (this.shop!=null &&
              java.util.Arrays.equals(this.shop, other.getShop()))) &&
            ((this.airlineReservation==null && other.getAirlineReservation()==null) || 
             (this.airlineReservation!=null &&
              this.airlineReservation.equals(other.getAirlineReservation())));
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
        if (getShop() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShop());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShop(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAirlineReservation() != null) {
            _hashCode += getAirlineReservation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FieldsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "FieldsType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shop");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Shop"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ItemType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airlineReservation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "AirlineReservation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "AirlineReservationType"));
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
