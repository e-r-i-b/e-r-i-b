/**
 * SecuritiesDocument_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Связанный документ
 */
public class SecuritiesDocument_Type  implements java.io.Serializable {
    /* Тип документа */
    private java.lang.String docType;

    /* Номер документа */
    private java.lang.String docNum;

    /* Дата документа */
    private java.lang.String docDt;

    /* Примечание */
    private java.lang.String annotation;

    /* Состояние документа */
    private java.lang.String status;

    /* Информация о клиенте по договору хранения */
    private com.rssl.phizic.test.webgate.esberib.generated.SecuritiesClient_Type client;

    /* ВСП, осуществляющее учет договора хранения */
    private com.rssl.phizic.test.webgate.esberib.generated.Owner_Type registrar;

    public SecuritiesDocument_Type() {
    }

    public SecuritiesDocument_Type(
           java.lang.String docType,
           java.lang.String docNum,
           java.lang.String docDt,
           java.lang.String annotation,
           java.lang.String status,
           com.rssl.phizic.test.webgate.esberib.generated.SecuritiesClient_Type client,
           com.rssl.phizic.test.webgate.esberib.generated.Owner_Type registrar) {
           this.docType = docType;
           this.docNum = docNum;
           this.docDt = docDt;
           this.annotation = annotation;
           this.status = status;
           this.client = client;
           this.registrar = registrar;
    }


    /**
     * Gets the docType value for this SecuritiesDocument_Type.
     * 
     * @return docType   * Тип документа
     */
    public java.lang.String getDocType() {
        return docType;
    }


    /**
     * Sets the docType value for this SecuritiesDocument_Type.
     * 
     * @param docType   * Тип документа
     */
    public void setDocType(java.lang.String docType) {
        this.docType = docType;
    }


    /**
     * Gets the docNum value for this SecuritiesDocument_Type.
     * 
     * @return docNum   * Номер документа
     */
    public java.lang.String getDocNum() {
        return docNum;
    }


    /**
     * Sets the docNum value for this SecuritiesDocument_Type.
     * 
     * @param docNum   * Номер документа
     */
    public void setDocNum(java.lang.String docNum) {
        this.docNum = docNum;
    }


    /**
     * Gets the docDt value for this SecuritiesDocument_Type.
     * 
     * @return docDt   * Дата документа
     */
    public java.lang.String getDocDt() {
        return docDt;
    }


    /**
     * Sets the docDt value for this SecuritiesDocument_Type.
     * 
     * @param docDt   * Дата документа
     */
    public void setDocDt(java.lang.String docDt) {
        this.docDt = docDt;
    }


    /**
     * Gets the annotation value for this SecuritiesDocument_Type.
     * 
     * @return annotation   * Примечание
     */
    public java.lang.String getAnnotation() {
        return annotation;
    }


    /**
     * Sets the annotation value for this SecuritiesDocument_Type.
     * 
     * @param annotation   * Примечание
     */
    public void setAnnotation(java.lang.String annotation) {
        this.annotation = annotation;
    }


    /**
     * Gets the status value for this SecuritiesDocument_Type.
     * 
     * @return status   * Состояние документа
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SecuritiesDocument_Type.
     * 
     * @param status   * Состояние документа
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the client value for this SecuritiesDocument_Type.
     * 
     * @return client   * Информация о клиенте по договору хранения
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SecuritiesClient_Type getClient() {
        return client;
    }


    /**
     * Sets the client value for this SecuritiesDocument_Type.
     * 
     * @param client   * Информация о клиенте по договору хранения
     */
    public void setClient(com.rssl.phizic.test.webgate.esberib.generated.SecuritiesClient_Type client) {
        this.client = client;
    }


    /**
     * Gets the registrar value for this SecuritiesDocument_Type.
     * 
     * @return registrar   * ВСП, осуществляющее учет договора хранения
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Owner_Type getRegistrar() {
        return registrar;
    }


    /**
     * Sets the registrar value for this SecuritiesDocument_Type.
     * 
     * @param registrar   * ВСП, осуществляющее учет договора хранения
     */
    public void setRegistrar(com.rssl.phizic.test.webgate.esberib.generated.Owner_Type registrar) {
        this.registrar = registrar;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesDocument_Type)) return false;
        SecuritiesDocument_Type other = (SecuritiesDocument_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docType==null && other.getDocType()==null) || 
             (this.docType!=null &&
              this.docType.equals(other.getDocType()))) &&
            ((this.docNum==null && other.getDocNum()==null) || 
             (this.docNum!=null &&
              this.docNum.equals(other.getDocNum()))) &&
            ((this.docDt==null && other.getDocDt()==null) || 
             (this.docDt!=null &&
              this.docDt.equals(other.getDocDt()))) &&
            ((this.annotation==null && other.getAnnotation()==null) || 
             (this.annotation!=null &&
              this.annotation.equals(other.getAnnotation()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.client==null && other.getClient()==null) || 
             (this.client!=null &&
              this.client.equals(other.getClient()))) &&
            ((this.registrar==null && other.getRegistrar()==null) || 
             (this.registrar!=null &&
              this.registrar.equals(other.getRegistrar())));
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
        if (getDocType() != null) {
            _hashCode += getDocType().hashCode();
        }
        if (getDocNum() != null) {
            _hashCode += getDocNum().hashCode();
        }
        if (getDocDt() != null) {
            _hashCode += getDocDt().hashCode();
        }
        if (getAnnotation() != null) {
            _hashCode += getAnnotation().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getClient() != null) {
            _hashCode += getClient().hashCode();
        }
        if (getRegistrar() != null) {
            _hashCode += getRegistrar().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecuritiesDocument_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesDocument_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annotation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Annotation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("client");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Client"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesClient_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registrar");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Registrar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Owner_Type"));
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
