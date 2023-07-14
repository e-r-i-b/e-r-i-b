/**
 * TicketsListType2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class TicketsListType2  implements java.io.Serializable {
    private java.lang.String[] ticketNumber;

    private java.math.BigDecimal price;

    private com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType priceCur;

    public TicketsListType2() {
    }

    public TicketsListType2(
           java.lang.String[] ticketNumber,
           java.math.BigDecimal price,
           com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType priceCur) {
           this.ticketNumber = ticketNumber;
           this.price = price;
           this.priceCur = priceCur;
    }


    /**
     * Gets the ticketNumber value for this TicketsListType2.
     * 
     * @return ticketNumber
     */
    public java.lang.String[] getTicketNumber() {
        return ticketNumber;
    }


    /**
     * Sets the ticketNumber value for this TicketsListType2.
     * 
     * @param ticketNumber
     */
    public void setTicketNumber(java.lang.String[] ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public java.lang.String getTicketNumber(int i) {
        return this.ticketNumber[i];
    }

    public void setTicketNumber(int i, java.lang.String _value) {
        this.ticketNumber[i] = _value;
    }


    /**
     * Gets the price value for this TicketsListType2.
     * 
     * @return price
     */
    public java.math.BigDecimal getPrice() {
        return price;
    }


    /**
     * Sets the price value for this TicketsListType2.
     * 
     * @param price
     */
    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }


    /**
     * Gets the priceCur value for this TicketsListType2.
     * 
     * @return priceCur
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType getPriceCur() {
        return priceCur;
    }


    /**
     * Sets the priceCur value for this TicketsListType2.
     * 
     * @param priceCur
     */
    public void setPriceCur(com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType priceCur) {
        this.priceCur = priceCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TicketsListType2)) return false;
        TicketsListType2 other = (TicketsListType2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ticketNumber==null && other.getTicketNumber()==null) || 
             (this.ticketNumber!=null &&
              java.util.Arrays.equals(this.ticketNumber, other.getTicketNumber()))) &&
            ((this.price==null && other.getPrice()==null) || 
             (this.price!=null &&
              this.price.equals(other.getPrice()))) &&
            ((this.priceCur==null && other.getPriceCur()==null) || 
             (this.priceCur!=null &&
              this.priceCur.equals(other.getPriceCur())));
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
        if (getTicketNumber() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTicketNumber());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTicketNumber(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrice() != null) {
            _hashCode += getPrice().hashCode();
        }
        if (getPriceCur() != null) {
            _hashCode += getPriceCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TicketsListType2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketsListType2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "String15Type"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DecimalType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priceCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "PriceCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "CurrencyType"));
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
