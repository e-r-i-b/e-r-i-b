/**
 * AgreemtInfoResponse_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Информация о договоре (ответ)
 */
public class AgreemtInfoResponse_Type  implements java.io.Serializable {
    /* Информация о вкладе (ответ) */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepInfoResponse_Type depInfo;

    /* Информация об ОМС (ответ) */
    private com.rssl.phizicgate.esberibgate.ws.generated.IMAInfoResponse_Type IMAInfo;

    public AgreemtInfoResponse_Type() {
    }

    public AgreemtInfoResponse_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.DepInfoResponse_Type depInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.IMAInfoResponse_Type IMAInfo) {
           this.depInfo = depInfo;
           this.IMAInfo = IMAInfo;
    }


    /**
     * Gets the depInfo value for this AgreemtInfoResponse_Type.
     * 
     * @return depInfo   * Информация о вкладе (ответ)
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepInfoResponse_Type getDepInfo() {
        return depInfo;
    }


    /**
     * Sets the depInfo value for this AgreemtInfoResponse_Type.
     * 
     * @param depInfo   * Информация о вкладе (ответ)
     */
    public void setDepInfo(com.rssl.phizicgate.esberibgate.ws.generated.DepInfoResponse_Type depInfo) {
        this.depInfo = depInfo;
    }


    /**
     * Gets the IMAInfo value for this AgreemtInfoResponse_Type.
     * 
     * @return IMAInfo   * Информация об ОМС (ответ)
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.IMAInfoResponse_Type getIMAInfo() {
        return IMAInfo;
    }


    /**
     * Sets the IMAInfo value for this AgreemtInfoResponse_Type.
     * 
     * @param IMAInfo   * Информация об ОМС (ответ)
     */
    public void setIMAInfo(com.rssl.phizicgate.esberibgate.ws.generated.IMAInfoResponse_Type IMAInfo) {
        this.IMAInfo = IMAInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AgreemtInfoResponse_Type)) return false;
        AgreemtInfoResponse_Type other = (AgreemtInfoResponse_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depInfo==null && other.getDepInfo()==null) || 
             (this.depInfo!=null &&
              this.depInfo.equals(other.getDepInfo()))) &&
            ((this.IMAInfo==null && other.getIMAInfo()==null) || 
             (this.IMAInfo!=null &&
              this.IMAInfo.equals(other.getIMAInfo())));
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
        if (getDepInfo() != null) {
            _hashCode += getDepInfo().hashCode();
        }
        if (getIMAInfo() != null) {
            _hashCode += getIMAInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AgreemtInfoResponse_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfoResponse_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepInfoResponse_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IMAInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAInfoResponse_Type"));
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
