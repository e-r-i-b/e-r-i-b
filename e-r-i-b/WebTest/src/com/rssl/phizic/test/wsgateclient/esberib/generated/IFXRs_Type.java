/**
 * IFXRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.esberib.generated;


/**
 * Список типов данных ответных сообщений на выполненные запросы
 */
public class IFXRs_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.wsgateclient.esberib.generated.DocStateUpdateRs_Type docStateUpdateRs;

    private com.rssl.phizic.test.wsgateclient.esberib.generated.SecDicInfoRs_Type secDicInfoRs;

    public IFXRs_Type() {
    }

    public IFXRs_Type(
           com.rssl.phizic.test.wsgateclient.esberib.generated.DocStateUpdateRs_Type docStateUpdateRs,
           com.rssl.phizic.test.wsgateclient.esberib.generated.SecDicInfoRs_Type secDicInfoRs) {
           this.docStateUpdateRs = docStateUpdateRs;
           this.secDicInfoRs = secDicInfoRs;
    }


    /**
     * Gets the docStateUpdateRs value for this IFXRs_Type.
     * 
     * @return docStateUpdateRs
     */
    public com.rssl.phizic.test.wsgateclient.esberib.generated.DocStateUpdateRs_Type getDocStateUpdateRs() {
        return docStateUpdateRs;
    }


    /**
     * Sets the docStateUpdateRs value for this IFXRs_Type.
     * 
     * @param docStateUpdateRs
     */
    public void setDocStateUpdateRs(com.rssl.phizic.test.wsgateclient.esberib.generated.DocStateUpdateRs_Type docStateUpdateRs) {
        this.docStateUpdateRs = docStateUpdateRs;
    }


    /**
     * Gets the secDicInfoRs value for this IFXRs_Type.
     * 
     * @return secDicInfoRs
     */
    public com.rssl.phizic.test.wsgateclient.esberib.generated.SecDicInfoRs_Type getSecDicInfoRs() {
        return secDicInfoRs;
    }


    /**
     * Sets the secDicInfoRs value for this IFXRs_Type.
     * 
     * @param secDicInfoRs
     */
    public void setSecDicInfoRs(com.rssl.phizic.test.wsgateclient.esberib.generated.SecDicInfoRs_Type secDicInfoRs) {
        this.secDicInfoRs = secDicInfoRs;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IFXRs_Type)) return false;
        IFXRs_Type other = (IFXRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docStateUpdateRs==null && other.getDocStateUpdateRs()==null) || 
             (this.docStateUpdateRs!=null &&
              this.docStateUpdateRs.equals(other.getDocStateUpdateRs()))) &&
            ((this.secDicInfoRs==null && other.getSecDicInfoRs()==null) || 
             (this.secDicInfoRs!=null &&
              this.secDicInfoRs.equals(other.getSecDicInfoRs())));
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
        if (getDocStateUpdateRs() != null) {
            _hashCode += getDocStateUpdateRs().hashCode();
        }
        if (getSecDicInfoRs() != null) {
            _hashCode += getSecDicInfoRs().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IFXRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IFXRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docStateUpdateRs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStateUpdateRs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStateUpdateRs_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secDicInfoRs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecDicInfoRs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecDicInfoRs_Type"));
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
