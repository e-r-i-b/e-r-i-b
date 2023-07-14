/**
 * StatNotRqDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.uec.generated;

public class StatNotRqDocumentType  implements java.io.Serializable {
    /* Уникальный идентификатор документа во внешней системе.
     *                         Заполняется значением из тега DocUID документа,
     * переданного на оплату. */
    private java.lang.String docUID;

    /* Текущий статус документа */
    private com.rssl.phizicgate.uec.generated.StateType state;

    public StatNotRqDocumentType() {
    }

    public StatNotRqDocumentType(
           java.lang.String docUID,
           com.rssl.phizicgate.uec.generated.StateType state) {
           this.docUID = docUID;
           this.state = state;
    }


    /**
     * Gets the docUID value for this StatNotRqDocumentType.
     * 
     * @return docUID   * Уникальный идентификатор документа во внешней системе.
     *                         Заполняется значением из тега DocUID документа,
     * переданного на оплату.
     */
    public java.lang.String getDocUID() {
        return docUID;
    }


    /**
     * Sets the docUID value for this StatNotRqDocumentType.
     * 
     * @param docUID   * Уникальный идентификатор документа во внешней системе.
     *                         Заполняется значением из тега DocUID документа,
     * переданного на оплату.
     */
    public void setDocUID(java.lang.String docUID) {
        this.docUID = docUID;
    }


    /**
     * Gets the state value for this StatNotRqDocumentType.
     * 
     * @return state   * Текущий статус документа
     */
    public com.rssl.phizicgate.uec.generated.StateType getState() {
        return state;
    }


    /**
     * Sets the state value for this StatNotRqDocumentType.
     * 
     * @param state   * Текущий статус документа
     */
    public void setState(com.rssl.phizicgate.uec.generated.StateType state) {
        this.state = state;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatNotRqDocumentType)) return false;
        StatNotRqDocumentType other = (StatNotRqDocumentType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docUID==null && other.getDocUID()==null) || 
             (this.docUID!=null &&
              this.docUID.equals(other.getDocUID()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState())));
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
        if (getDocUID() != null) {
            _hashCode += getDocUID().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatNotRqDocumentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StatNotRqDocumentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "DocUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StateType"));
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
