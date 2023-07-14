/**
 * SvcsAcct_TypeSvcAcctId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class SvcsAcct_TypeSvcAcctId  implements java.io.Serializable {
    /* Номер длительного поручения */
    private long svcAcctNum;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    /* Признак «Длительное поручение на перевод». Если значение false,
     * то значит не перевод, а коммунальные платежи */
    private boolean svcType;

    public SvcsAcct_TypeSvcAcctId() {
    }

    public SvcsAcct_TypeSvcAcctId(
           long svcAcctNum,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           boolean svcType) {
           this.svcAcctNum = svcAcctNum;
           this.bankInfo = bankInfo;
           this.svcType = svcType;
    }


    /**
     * Gets the svcAcctNum value for this SvcsAcct_TypeSvcAcctId.
     * 
     * @return svcAcctNum   * Номер длительного поручения
     */
    public long getSvcAcctNum() {
        return svcAcctNum;
    }


    /**
     * Sets the svcAcctNum value for this SvcsAcct_TypeSvcAcctId.
     * 
     * @param svcAcctNum   * Номер длительного поручения
     */
    public void setSvcAcctNum(long svcAcctNum) {
        this.svcAcctNum = svcAcctNum;
    }


    /**
     * Gets the bankInfo value for this SvcsAcct_TypeSvcAcctId.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this SvcsAcct_TypeSvcAcctId.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the svcType value for this SvcsAcct_TypeSvcAcctId.
     * 
     * @return svcType   * Признак «Длительное поручение на перевод». Если значение false,
     * то значит не перевод, а коммунальные платежи
     */
    public boolean isSvcType() {
        return svcType;
    }


    /**
     * Sets the svcType value for this SvcsAcct_TypeSvcAcctId.
     * 
     * @param svcType   * Признак «Длительное поручение на перевод». Если значение false,
     * то значит не перевод, а коммунальные платежи
     */
    public void setSvcType(boolean svcType) {
        this.svcType = svcType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SvcsAcct_TypeSvcAcctId)) return false;
        SvcsAcct_TypeSvcAcctId other = (SvcsAcct_TypeSvcAcctId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.svcAcctNum == other.getSvcAcctNum() &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            this.svcType == other.isSvcType();
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
        _hashCode += new Long(getSvcAcctNum()).hashCode();
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        _hashCode += (isSvcType() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SvcsAcct_TypeSvcAcctId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcsAcct_Type>SvcAcctId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("svcAcctNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("svcType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
