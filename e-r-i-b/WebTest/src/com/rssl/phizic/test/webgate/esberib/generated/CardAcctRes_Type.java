/**
 * CardAcctRes_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class CardAcctRes_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal;

    public CardAcctRes_Type() {
    }

    public CardAcctRes_Type(
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal) {
           this.cardAcctId = cardAcctId;
           this.acctBal = acctBal;
    }


    /**
     * Gets the cardAcctId value for this CardAcctRes_Type.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this CardAcctRes_Type.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the acctBal value for this CardAcctRes_Type.
     * 
     * @return acctBal
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this CardAcctRes_Type.
     * 
     * @param acctBal
     */
    public void setAcctBal(com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal) {
        this.acctBal = acctBal;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type getAcctBal(int i) {
        return this.acctBal[i];
    }

    public void setAcctBal(int i, com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type _value) {
        this.acctBal[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardAcctRes_Type)) return false;
        CardAcctRes_Type other = (CardAcctRes_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              java.util.Arrays.equals(this.acctBal, other.getAcctBal())));
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
        if (getCardAcctId() != null) {
            _hashCode += getCardAcctId().hashCode();
        }
        if (getAcctBal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcctBal());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcctBal(), i);
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
        new org.apache.axis.description.TypeDesc(CardAcctRes_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRes_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
