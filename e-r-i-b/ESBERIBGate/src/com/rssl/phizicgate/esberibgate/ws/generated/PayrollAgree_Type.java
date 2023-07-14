/**
 * PayrollAgree_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Зарплатный договор
 */
public class PayrollAgree_Type  implements java.io.Serializable {
    /* Идентификатор зарплатного договора */
    private java.lang.String agreeId;

    private com.rssl.phizicgate.esberibgate.ws.generated.DirectSaler_Type[] directSaler;

    public PayrollAgree_Type() {
    }

    public PayrollAgree_Type(
           java.lang.String agreeId,
           com.rssl.phizicgate.esberibgate.ws.generated.DirectSaler_Type[] directSaler) {
           this.agreeId = agreeId;
           this.directSaler = directSaler;
    }


    /**
     * Gets the agreeId value for this PayrollAgree_Type.
     * 
     * @return agreeId   * Идентификатор зарплатного договора
     */
    public java.lang.String getAgreeId() {
        return agreeId;
    }


    /**
     * Sets the agreeId value for this PayrollAgree_Type.
     * 
     * @param agreeId   * Идентификатор зарплатного договора
     */
    public void setAgreeId(java.lang.String agreeId) {
        this.agreeId = agreeId;
    }


    /**
     * Gets the directSaler value for this PayrollAgree_Type.
     * 
     * @return directSaler
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DirectSaler_Type[] getDirectSaler() {
        return directSaler;
    }


    /**
     * Sets the directSaler value for this PayrollAgree_Type.
     * 
     * @param directSaler
     */
    public void setDirectSaler(com.rssl.phizicgate.esberibgate.ws.generated.DirectSaler_Type[] directSaler) {
        this.directSaler = directSaler;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DirectSaler_Type getDirectSaler(int i) {
        return this.directSaler[i];
    }

    public void setDirectSaler(int i, com.rssl.phizicgate.esberibgate.ws.generated.DirectSaler_Type _value) {
        this.directSaler[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PayrollAgree_Type)) return false;
        PayrollAgree_Type other = (PayrollAgree_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agreeId==null && other.getAgreeId()==null) || 
             (this.agreeId!=null &&
              this.agreeId.equals(other.getAgreeId()))) &&
            ((this.directSaler==null && other.getDirectSaler()==null) || 
             (this.directSaler!=null &&
              java.util.Arrays.equals(this.directSaler, other.getDirectSaler())));
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
        if (getAgreeId() != null) {
            _hashCode += getAgreeId().hashCode();
        }
        if (getDirectSaler() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDirectSaler());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDirectSaler(), i);
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
        new org.apache.axis.description.TypeDesc(PayrollAgree_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayrollAgree_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("directSaler");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DirectSaler"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DirectSaler"));
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
