/**
 * CustInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mdm.generated;


/**
 * Информация о потребителе <CustInfo>
 */
public class CustInfo_Type  implements java.io.Serializable {
    private java.lang.String custId;

    /* Дата и время последнего изменения данных */
    private java.lang.String effDt;

    /* Информация о физическом лице. */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.PersonInfo_Type personInfo;

    private com.rssl.phizic.test.wsgateclient.mdm.generated.BankInfo_Type bankInfo;

    /* Поле, опеределяющее  заключение клиентом заявления на подключение */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.SPDefField_Type SPDefField;

    /* Идентификаторы клиента в ИС */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.IntegrationInfo_Type integrationInfo;

    public CustInfo_Type() {
    }

    public CustInfo_Type(
           java.lang.String custId,
           java.lang.String effDt,
           com.rssl.phizic.test.wsgateclient.mdm.generated.PersonInfo_Type personInfo,
           com.rssl.phizic.test.wsgateclient.mdm.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.wsgateclient.mdm.generated.SPDefField_Type SPDefField,
           com.rssl.phizic.test.wsgateclient.mdm.generated.IntegrationInfo_Type integrationInfo) {
           this.custId = custId;
           this.effDt = effDt;
           this.personInfo = personInfo;
           this.bankInfo = bankInfo;
           this.SPDefField = SPDefField;
           this.integrationInfo = integrationInfo;
    }


    /**
     * Gets the custId value for this CustInfo_Type.
     * 
     * @return custId
     */
    public java.lang.String getCustId() {
        return custId;
    }


    /**
     * Sets the custId value for this CustInfo_Type.
     * 
     * @param custId
     */
    public void setCustId(java.lang.String custId) {
        this.custId = custId;
    }


    /**
     * Gets the effDt value for this CustInfo_Type.
     * 
     * @return effDt   * Дата и время последнего изменения данных
     */
    public java.lang.String getEffDt() {
        return effDt;
    }


    /**
     * Sets the effDt value for this CustInfo_Type.
     * 
     * @param effDt   * Дата и время последнего изменения данных
     */
    public void setEffDt(java.lang.String effDt) {
        this.effDt = effDt;
    }


    /**
     * Gets the personInfo value for this CustInfo_Type.
     * 
     * @return personInfo   * Информация о физическом лице.
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.PersonInfo_Type getPersonInfo() {
        return personInfo;
    }


    /**
     * Sets the personInfo value for this CustInfo_Type.
     * 
     * @param personInfo   * Информация о физическом лице.
     */
    public void setPersonInfo(com.rssl.phizic.test.wsgateclient.mdm.generated.PersonInfo_Type personInfo) {
        this.personInfo = personInfo;
    }


    /**
     * Gets the bankInfo value for this CustInfo_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this CustInfo_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.wsgateclient.mdm.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the SPDefField value for this CustInfo_Type.
     * 
     * @return SPDefField   * Поле, опеределяющее  заключение клиентом заявления на подключение
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.SPDefField_Type getSPDefField() {
        return SPDefField;
    }


    /**
     * Sets the SPDefField value for this CustInfo_Type.
     * 
     * @param SPDefField   * Поле, опеределяющее  заключение клиентом заявления на подключение
     */
    public void setSPDefField(com.rssl.phizic.test.wsgateclient.mdm.generated.SPDefField_Type SPDefField) {
        this.SPDefField = SPDefField;
    }


    /**
     * Gets the integrationInfo value for this CustInfo_Type.
     * 
     * @return integrationInfo   * Идентификаторы клиента в ИС
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.IntegrationInfo_Type getIntegrationInfo() {
        return integrationInfo;
    }


    /**
     * Sets the integrationInfo value for this CustInfo_Type.
     * 
     * @param integrationInfo   * Идентификаторы клиента в ИС
     */
    public void setIntegrationInfo(com.rssl.phizic.test.wsgateclient.mdm.generated.IntegrationInfo_Type integrationInfo) {
        this.integrationInfo = integrationInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustInfo_Type)) return false;
        CustInfo_Type other = (CustInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custId==null && other.getCustId()==null) || 
             (this.custId!=null &&
              this.custId.equals(other.getCustId()))) &&
            ((this.effDt==null && other.getEffDt()==null) || 
             (this.effDt!=null &&
              this.effDt.equals(other.getEffDt()))) &&
            ((this.personInfo==null && other.getPersonInfo()==null) || 
             (this.personInfo!=null &&
              this.personInfo.equals(other.getPersonInfo()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.SPDefField==null && other.getSPDefField()==null) || 
             (this.SPDefField!=null &&
              this.SPDefField.equals(other.getSPDefField()))) &&
            ((this.integrationInfo==null && other.getIntegrationInfo()==null) || 
             (this.integrationInfo!=null &&
              this.integrationInfo.equals(other.getIntegrationInfo())));
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
        if (getCustId() != null) {
            _hashCode += getCustId().hashCode();
        }
        if (getEffDt() != null) {
            _hashCode += getEffDt().hashCode();
        }
        if (getPersonInfo() != null) {
            _hashCode += getPersonInfo().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getSPDefField() != null) {
            _hashCode += getSPDefField().hashCode();
        }
        if (getIntegrationInfo() != null) {
            _hashCode += getIntegrationInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "CustInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "CustId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "EffDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PersonInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PersonInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPDefField");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "SPDefField"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "SPDefField_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("integrationInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IntegrationInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IntegrationInfo_Type"));
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
