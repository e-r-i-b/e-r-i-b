/**
 * BankAcctResult_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Результат изменения прав доступа для каждого счета в отдельности
 */
public class BankAcctResult_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] depAcctRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] cardAcctRec;

    public BankAcctResult_Type() {
    }

    public BankAcctResult_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] depAcctRec,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] cardAcctRec) {
           this.depAcctRec = depAcctRec;
           this.cardAcctRec = cardAcctRec;
    }


    /**
     * Gets the depAcctRec value for this BankAcctResult_Type.
     * 
     * @return depAcctRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] getDepAcctRec() {
        return depAcctRec;
    }


    /**
     * Sets the depAcctRec value for this BankAcctResult_Type.
     * 
     * @param depAcctRec
     */
    public void setDepAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] depAcctRec) {
        this.depAcctRec = depAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type getDepAcctRec(int i) {
        return this.depAcctRec[i];
    }

    public void setDepAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type _value) {
        this.depAcctRec[i] = _value;
    }


    /**
     * Gets the cardAcctRec value for this BankAcctResult_Type.
     * 
     * @return cardAcctRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] getCardAcctRec() {
        return cardAcctRec;
    }


    /**
     * Sets the cardAcctRec value for this BankAcctResult_Type.
     * 
     * @param cardAcctRec
     */
    public void setCardAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] cardAcctRec) {
        this.cardAcctRec = cardAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type getCardAcctRec(int i) {
        return this.cardAcctRec[i];
    }

    public void setCardAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type _value) {
        this.cardAcctRec[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctResult_Type)) return false;
        BankAcctResult_Type other = (BankAcctResult_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depAcctRec==null && other.getDepAcctRec()==null) || 
             (this.depAcctRec!=null &&
              java.util.Arrays.equals(this.depAcctRec, other.getDepAcctRec()))) &&
            ((this.cardAcctRec==null && other.getCardAcctRec()==null) || 
             (this.cardAcctRec!=null &&
              java.util.Arrays.equals(this.cardAcctRec, other.getCardAcctRec())));
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
        if (getDepAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepAcctRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCardAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCardAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCardAcctRec(), i);
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
        new org.apache.axis.description.TypeDesc(BankAcctResult_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctResult_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRec_Type"));
        elemField.setMinOccurs(0);
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
