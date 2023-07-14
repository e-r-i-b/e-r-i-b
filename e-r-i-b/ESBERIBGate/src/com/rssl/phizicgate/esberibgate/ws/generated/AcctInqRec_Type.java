/**
 * AcctInqRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись об остатке по счету
 */
public class AcctInqRec_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRes_Type[] bankAcctRes;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRes_Type[] depAcctRes;

    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRes_Type[] cardAcctRes;

    public AcctInqRec_Type() {
    }

    public AcctInqRec_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRes_Type[] bankAcctRes,
           com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRes_Type[] depAcctRes,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRes_Type[] cardAcctRes) {
           this.bankAcctRes = bankAcctRes;
           this.depAcctRes = depAcctRes;
           this.cardAcctRes = cardAcctRes;
    }


    /**
     * Gets the bankAcctRes value for this AcctInqRec_Type.
     * 
     * @return bankAcctRes
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRes_Type[] getBankAcctRes() {
        return bankAcctRes;
    }


    /**
     * Sets the bankAcctRes value for this AcctInqRec_Type.
     * 
     * @param bankAcctRes
     */
    public void setBankAcctRes(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRes_Type[] bankAcctRes) {
        this.bankAcctRes = bankAcctRes;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRes_Type getBankAcctRes(int i) {
        return this.bankAcctRes[i];
    }

    public void setBankAcctRes(int i, com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRes_Type _value) {
        this.bankAcctRes[i] = _value;
    }


    /**
     * Gets the depAcctRes value for this AcctInqRec_Type.
     * 
     * @return depAcctRes
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRes_Type[] getDepAcctRes() {
        return depAcctRes;
    }


    /**
     * Sets the depAcctRes value for this AcctInqRec_Type.
     * 
     * @param depAcctRes
     */
    public void setDepAcctRes(com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRes_Type[] depAcctRes) {
        this.depAcctRes = depAcctRes;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRes_Type getDepAcctRes(int i) {
        return this.depAcctRes[i];
    }

    public void setDepAcctRes(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRes_Type _value) {
        this.depAcctRes[i] = _value;
    }


    /**
     * Gets the cardAcctRes value for this AcctInqRec_Type.
     * 
     * @return cardAcctRes
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRes_Type[] getCardAcctRes() {
        return cardAcctRes;
    }


    /**
     * Sets the cardAcctRes value for this AcctInqRec_Type.
     * 
     * @param cardAcctRes
     */
    public void setCardAcctRes(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRes_Type[] cardAcctRes) {
        this.cardAcctRes = cardAcctRes;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRes_Type getCardAcctRes(int i) {
        return this.cardAcctRes[i];
    }

    public void setCardAcctRes(int i, com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRes_Type _value) {
        this.cardAcctRes[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AcctInqRec_Type)) return false;
        AcctInqRec_Type other = (AcctInqRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankAcctRes==null && other.getBankAcctRes()==null) || 
             (this.bankAcctRes!=null &&
              java.util.Arrays.equals(this.bankAcctRes, other.getBankAcctRes()))) &&
            ((this.depAcctRes==null && other.getDepAcctRes()==null) || 
             (this.depAcctRes!=null &&
              java.util.Arrays.equals(this.depAcctRes, other.getDepAcctRes()))) &&
            ((this.cardAcctRes==null && other.getCardAcctRes()==null) || 
             (this.cardAcctRes!=null &&
              java.util.Arrays.equals(this.cardAcctRes, other.getCardAcctRes())));
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
        if (getBankAcctRes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAcctRes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAcctRes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDepAcctRes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepAcctRes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepAcctRes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCardAcctRes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCardAcctRes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCardAcctRes(), i);
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
        new org.apache.axis.description.TypeDesc(AcctInqRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctInqRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctRes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctRes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctRes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRes"));
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
