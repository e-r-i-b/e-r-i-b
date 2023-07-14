/**
 * SecuritiesClient_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о клиенте по договору хранения
 */
public class SecuritiesClient_Type  implements java.io.Serializable {
    /* Идентификационные данные клиента физ. лица, которому выдана
     * ценная бумага */
    private com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type personInfo;

    public SecuritiesClient_Type() {
    }

    public SecuritiesClient_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type personInfo) {
           this.personInfo = personInfo;
    }


    /**
     * Gets the personInfo value for this SecuritiesClient_Type.
     * 
     * @return personInfo   * Идентификационные данные клиента физ. лица, которому выдана
     * ценная бумага
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type getPersonInfo() {
        return personInfo;
    }


    /**
     * Sets the personInfo value for this SecuritiesClient_Type.
     * 
     * @param personInfo   * Идентификационные данные клиента физ. лица, которому выдана
     * ценная бумага
     */
    public void setPersonInfo(com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type personInfo) {
        this.personInfo = personInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesClient_Type)) return false;
        SecuritiesClient_Type other = (SecuritiesClient_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.personInfo==null && other.getPersonInfo()==null) || 
             (this.personInfo!=null &&
              this.personInfo.equals(other.getPersonInfo())));
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
        if (getPersonInfo() != null) {
            _hashCode += getPersonInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecuritiesClient_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesClient_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonInfoSec_Type"));
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
