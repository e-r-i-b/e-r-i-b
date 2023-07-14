/**
 * AgreemtInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Инфромация о договоре
 */
public class AgreemtInfo_Type  implements java.io.Serializable {
    /* Тип договора. При создании вклада используется фиксированное
     * значение «Dep». При создании ОМС используется фиксированное значение
     * "IMA" */
    private java.lang.String agreemtType;

    /* Информация о депозитном договоре */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepInfo_Type depInfo;

    /* Информация об ОМС */
    private com.rssl.phizicgate.esberibgate.ws.generated.IMAInfo_Type IMAInfo;

    public AgreemtInfo_Type() {
    }

    public AgreemtInfo_Type(
           java.lang.String agreemtType,
           com.rssl.phizicgate.esberibgate.ws.generated.DepInfo_Type depInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.IMAInfo_Type IMAInfo) {
           this.agreemtType = agreemtType;
           this.depInfo = depInfo;
           this.IMAInfo = IMAInfo;
    }


    /**
     * Gets the agreemtType value for this AgreemtInfo_Type.
     * 
     * @return agreemtType   * Тип договора. При создании вклада используется фиксированное
     * значение «Dep». При создании ОМС используется фиксированное значение
     * "IMA"
     */
    public java.lang.String getAgreemtType() {
        return agreemtType;
    }


    /**
     * Sets the agreemtType value for this AgreemtInfo_Type.
     * 
     * @param agreemtType   * Тип договора. При создании вклада используется фиксированное
     * значение «Dep». При создании ОМС используется фиксированное значение
     * "IMA"
     */
    public void setAgreemtType(java.lang.String agreemtType) {
        this.agreemtType = agreemtType;
    }


    /**
     * Gets the depInfo value for this AgreemtInfo_Type.
     * 
     * @return depInfo   * Информация о депозитном договоре
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepInfo_Type getDepInfo() {
        return depInfo;
    }


    /**
     * Sets the depInfo value for this AgreemtInfo_Type.
     * 
     * @param depInfo   * Информация о депозитном договоре
     */
    public void setDepInfo(com.rssl.phizicgate.esberibgate.ws.generated.DepInfo_Type depInfo) {
        this.depInfo = depInfo;
    }


    /**
     * Gets the IMAInfo value for this AgreemtInfo_Type.
     * 
     * @return IMAInfo   * Информация об ОМС
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.IMAInfo_Type getIMAInfo() {
        return IMAInfo;
    }


    /**
     * Sets the IMAInfo value for this AgreemtInfo_Type.
     * 
     * @param IMAInfo   * Информация об ОМС
     */
    public void setIMAInfo(com.rssl.phizicgate.esberibgate.ws.generated.IMAInfo_Type IMAInfo) {
        this.IMAInfo = IMAInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AgreemtInfo_Type)) return false;
        AgreemtInfo_Type other = (AgreemtInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agreemtType==null && other.getAgreemtType()==null) || 
             (this.agreemtType!=null &&
              this.agreemtType.equals(other.getAgreemtType()))) &&
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
        if (getAgreemtType() != null) {
            _hashCode += getAgreemtType().hashCode();
        }
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
        new org.apache.axis.description.TypeDesc(AgreemtInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IMAInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAInfo_Type"));
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
