/**
 * SecuritiesAcctInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о сертификатах
 */
public class SecuritiesAcctInfo_Type  implements java.io.Serializable {
    /* Держатель ЦБ */
    private com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type holder;

    /* Информационная запись по ценной бумаге */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRecShort_Type[] securitiesRec;

    public SecuritiesAcctInfo_Type() {
    }

    public SecuritiesAcctInfo_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type holder,
           com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRecShort_Type[] securitiesRec) {
           this.holder = holder;
           this.securitiesRec = securitiesRec;
    }


    /**
     * Gets the holder value for this SecuritiesAcctInfo_Type.
     * 
     * @return holder   * Держатель ЦБ
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type getHolder() {
        return holder;
    }


    /**
     * Sets the holder value for this SecuritiesAcctInfo_Type.
     * 
     * @param holder   * Держатель ЦБ
     */
    public void setHolder(com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type holder) {
        this.holder = holder;
    }


    /**
     * Gets the securitiesRec value for this SecuritiesAcctInfo_Type.
     * 
     * @return securitiesRec   * Информационная запись по ценной бумаге
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRecShort_Type[] getSecuritiesRec() {
        return securitiesRec;
    }


    /**
     * Sets the securitiesRec value for this SecuritiesAcctInfo_Type.
     * 
     * @param securitiesRec   * Информационная запись по ценной бумаге
     */
    public void setSecuritiesRec(com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRecShort_Type[] securitiesRec) {
        this.securitiesRec = securitiesRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRecShort_Type getSecuritiesRec(int i) {
        return this.securitiesRec[i];
    }

    public void setSecuritiesRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRecShort_Type _value) {
        this.securitiesRec[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesAcctInfo_Type)) return false;
        SecuritiesAcctInfo_Type other = (SecuritiesAcctInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.holder==null && other.getHolder()==null) || 
             (this.holder!=null &&
              this.holder.equals(other.getHolder()))) &&
            ((this.securitiesRec==null && other.getSecuritiesRec()==null) || 
             (this.securitiesRec!=null &&
              java.util.Arrays.equals(this.securitiesRec, other.getSecuritiesRec())));
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
        if (getHolder() != null) {
            _hashCode += getHolder().hashCode();
        }
        if (getSecuritiesRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSecuritiesRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSecuritiesRec(), i);
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
        new org.apache.axis.description.TypeDesc(SecuritiesAcctInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesAcctInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("holder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Holder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securitiesRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRecShort_Type"));
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
