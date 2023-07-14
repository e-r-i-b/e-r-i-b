/**
 * GoodsReturnRqDocumentFieldType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class GoodsReturnRqDocumentFieldType  implements java.io.Serializable {
    private com.rssl.phizic.test.wsgateclient.shop.generated.ItemType[] shop;

    private com.rssl.phizic.test.wsgateclient.shop.generated.TicketItemType[] ticketsList;

    public GoodsReturnRqDocumentFieldType() {
    }

    public GoodsReturnRqDocumentFieldType(
           com.rssl.phizic.test.wsgateclient.shop.generated.ItemType[] shop,
           com.rssl.phizic.test.wsgateclient.shop.generated.TicketItemType[] ticketsList) {
           this.shop = shop;
           this.ticketsList = ticketsList;
    }


    /**
     * Gets the shop value for this GoodsReturnRqDocumentFieldType.
     * 
     * @return shop
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.ItemType[] getShop() {
        return shop;
    }


    /**
     * Sets the shop value for this GoodsReturnRqDocumentFieldType.
     * 
     * @param shop
     */
    public void setShop(com.rssl.phizic.test.wsgateclient.shop.generated.ItemType[] shop) {
        this.shop = shop;
    }


    /**
     * Gets the ticketsList value for this GoodsReturnRqDocumentFieldType.
     * 
     * @return ticketsList
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.TicketItemType[] getTicketsList() {
        return ticketsList;
    }


    /**
     * Sets the ticketsList value for this GoodsReturnRqDocumentFieldType.
     * 
     * @param ticketsList
     */
    public void setTicketsList(com.rssl.phizic.test.wsgateclient.shop.generated.TicketItemType[] ticketsList) {
        this.ticketsList = ticketsList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GoodsReturnRqDocumentFieldType)) return false;
        GoodsReturnRqDocumentFieldType other = (GoodsReturnRqDocumentFieldType) obj;
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
            ((this.ticketsList==null && other.getTicketsList()==null) || 
             (this.ticketsList!=null &&
              java.util.Arrays.equals(this.ticketsList, other.getTicketsList())));
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
        if (getTicketsList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTicketsList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTicketsList(), i);
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
        new org.apache.axis.description.TypeDesc(GoodsReturnRqDocumentFieldType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "GoodsReturnRqDocumentFieldType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shop");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Shop"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "ItemType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketsList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketsList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketItemType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Item"));
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
