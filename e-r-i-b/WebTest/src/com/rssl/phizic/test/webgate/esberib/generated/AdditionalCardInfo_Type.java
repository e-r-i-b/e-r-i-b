/**
 * AdditionalCardInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class AdditionalCardInfo_Type  implements java.io.Serializable {
    private java.lang.String systemId;

    private java.lang.String cardNum;

    private com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_TypeCards[] cards;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public AdditionalCardInfo_Type() {
    }

    public AdditionalCardInfo_Type(
           java.lang.String systemId,
           java.lang.String cardNum,
           com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_TypeCards[] cards,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.systemId = systemId;
           this.cardNum = cardNum;
           this.cards = cards;
           this.status = status;
    }


    /**
     * Gets the systemId value for this AdditionalCardInfo_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this AdditionalCardInfo_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the cardNum value for this AdditionalCardInfo_Type.
     * 
     * @return cardNum
     */
    public java.lang.String getCardNum() {
        return cardNum;
    }


    /**
     * Sets the cardNum value for this AdditionalCardInfo_Type.
     * 
     * @param cardNum
     */
    public void setCardNum(java.lang.String cardNum) {
        this.cardNum = cardNum;
    }


    /**
     * Gets the cards value for this AdditionalCardInfo_Type.
     * 
     * @return cards
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_TypeCards[] getCards() {
        return cards;
    }


    /**
     * Sets the cards value for this AdditionalCardInfo_Type.
     * 
     * @param cards
     */
    public void setCards(com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_TypeCards[] cards) {
        this.cards = cards;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_TypeCards getCards(int i) {
        return this.cards[i];
    }

    public void setCards(int i, com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_TypeCards _value) {
        this.cards[i] = _value;
    }


    /**
     * Gets the status value for this AdditionalCardInfo_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this AdditionalCardInfo_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdditionalCardInfo_Type)) return false;
        AdditionalCardInfo_Type other = (AdditionalCardInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.cardNum==null && other.getCardNum()==null) || 
             (this.cardNum!=null &&
              this.cardNum.equals(other.getCardNum()))) &&
            ((this.cards==null && other.getCards()==null) || 
             (this.cards!=null &&
              java.util.Arrays.equals(this.cards, other.getCards()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getCardNum() != null) {
            _hashCode += getCardNum().hashCode();
        }
        if (getCards() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCards());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCards(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AdditionalCardInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AdditionalCardInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNumType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cards");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Cards"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">AdditionalCardInfo_Type>Cards"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
