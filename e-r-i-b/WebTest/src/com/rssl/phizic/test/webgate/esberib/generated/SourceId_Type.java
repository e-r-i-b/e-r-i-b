/**
 * SourceId_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class SourceId_Type  implements java.io.Serializable {
    /* Тип идентификатора канала ('BP_VSP', 'BP_ERIB') */
    private java.lang.String sourceIdType;

    /* ВСП из которго идет запрос. Заполняется в случае SourceIdType
     * = 'BP_VSP' */
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type VSPInfo;

    public SourceId_Type() {
    }

    public SourceId_Type(
           java.lang.String sourceIdType,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type VSPInfo) {
           this.sourceIdType = sourceIdType;
           this.VSPInfo = VSPInfo;
    }


    /**
     * Gets the sourceIdType value for this SourceId_Type.
     * 
     * @return sourceIdType   * Тип идентификатора канала ('BP_VSP', 'BP_ERIB')
     */
    public java.lang.String getSourceIdType() {
        return sourceIdType;
    }


    /**
     * Sets the sourceIdType value for this SourceId_Type.
     * 
     * @param sourceIdType   * Тип идентификатора канала ('BP_VSP', 'BP_ERIB')
     */
    public void setSourceIdType(java.lang.String sourceIdType) {
        this.sourceIdType = sourceIdType;
    }


    /**
     * Gets the VSPInfo value for this SourceId_Type.
     * 
     * @return VSPInfo   * ВСП из которго идет запрос. Заполняется в случае SourceIdType
     * = 'BP_VSP'
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type getVSPInfo() {
        return VSPInfo;
    }


    /**
     * Sets the VSPInfo value for this SourceId_Type.
     * 
     * @param VSPInfo   * ВСП из которго идет запрос. Заполняется в случае SourceIdType
     * = 'BP_VSP'
     */
    public void setVSPInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type VSPInfo) {
        this.VSPInfo = VSPInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SourceId_Type)) return false;
        SourceId_Type other = (SourceId_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sourceIdType==null && other.getSourceIdType()==null) || 
             (this.sourceIdType!=null &&
              this.sourceIdType.equals(other.getSourceIdType()))) &&
            ((this.VSPInfo==null && other.getVSPInfo()==null) || 
             (this.VSPInfo!=null &&
              this.VSPInfo.equals(other.getVSPInfo())));
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
        if (getSourceIdType() != null) {
            _hashCode += getSourceIdType().hashCode();
        }
        if (getVSPInfo() != null) {
            _hashCode += getVSPInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SourceId_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceId_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceIdType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceIdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VSPInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VSPInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoLeadZero_Type"));
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
