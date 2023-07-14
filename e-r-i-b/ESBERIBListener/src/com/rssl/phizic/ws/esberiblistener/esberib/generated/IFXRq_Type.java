/**
 * IFXRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.esberib.generated;


/**
 * Список типов данных сообщений для выполнения запросов
 */
public class IFXRq_Type  implements java.io.Serializable {
    private com.rssl.phizic.ws.esberiblistener.esberib.generated.DocStateUpdateRq_Type docStateUpdateRq;

    private com.rssl.phizic.ws.esberiblistener.esberib.generated.SecDicInfoRq_Type secDicInfoRq;

    public IFXRq_Type() {
    }

    public IFXRq_Type(
           com.rssl.phizic.ws.esberiblistener.esberib.generated.DocStateUpdateRq_Type docStateUpdateRq,
           com.rssl.phizic.ws.esberiblistener.esberib.generated.SecDicInfoRq_Type secDicInfoRq) {
           this.docStateUpdateRq = docStateUpdateRq;
           this.secDicInfoRq = secDicInfoRq;
    }


    /**
     * Gets the docStateUpdateRq value for this IFXRq_Type.
     * 
     * @return docStateUpdateRq
     */
    public com.rssl.phizic.ws.esberiblistener.esberib.generated.DocStateUpdateRq_Type getDocStateUpdateRq() {
        return docStateUpdateRq;
    }


    /**
     * Sets the docStateUpdateRq value for this IFXRq_Type.
     * 
     * @param docStateUpdateRq
     */
    public void setDocStateUpdateRq(com.rssl.phizic.ws.esberiblistener.esberib.generated.DocStateUpdateRq_Type docStateUpdateRq) {
        this.docStateUpdateRq = docStateUpdateRq;
    }


    /**
     * Gets the secDicInfoRq value for this IFXRq_Type.
     * 
     * @return secDicInfoRq
     */
    public com.rssl.phizic.ws.esberiblistener.esberib.generated.SecDicInfoRq_Type getSecDicInfoRq() {
        return secDicInfoRq;
    }


    /**
     * Sets the secDicInfoRq value for this IFXRq_Type.
     * 
     * @param secDicInfoRq
     */
    public void setSecDicInfoRq(com.rssl.phizic.ws.esberiblistener.esberib.generated.SecDicInfoRq_Type secDicInfoRq) {
        this.secDicInfoRq = secDicInfoRq;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IFXRq_Type)) return false;
        IFXRq_Type other = (IFXRq_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docStateUpdateRq==null && other.getDocStateUpdateRq()==null) || 
             (this.docStateUpdateRq!=null &&
              this.docStateUpdateRq.equals(other.getDocStateUpdateRq()))) &&
            ((this.secDicInfoRq==null && other.getSecDicInfoRq()==null) || 
             (this.secDicInfoRq!=null &&
              this.secDicInfoRq.equals(other.getSecDicInfoRq())));
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
        if (getDocStateUpdateRq() != null) {
            _hashCode += getDocStateUpdateRq().hashCode();
        }
        if (getSecDicInfoRq() != null) {
            _hashCode += getSecDicInfoRq().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IFXRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IFXRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docStateUpdateRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStateUpdateRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStateUpdateRq_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secDicInfoRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecDicInfoRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecDicInfoRq_Type"));
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
