/**
 * IMAInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип информация об ОМС договоре
 */
public class IMAInfo_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec;

    /* Номер ОМС */
    private java.lang.String acctId;

    /* Код металла */
    private java.lang.String acctCur;

    /* Вид вклада */
    private long acctCode;

    /* Подвид вклада */
    private long acctSubCode;

    /* Информация о номере ОСБ и ВСП, в котором открывается ОМС */
    private com.rssl.phizic.test.webgate.esberib.generated.MinBankInfo_Type bankInfo;

    public IMAInfo_Type() {
    }

    public IMAInfo_Type(
           com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec,
           java.lang.String acctId,
           java.lang.String acctCur,
           long acctCode,
           long acctSubCode,
           com.rssl.phizic.test.webgate.esberib.generated.MinBankInfo_Type bankInfo) {
           this.custRec = custRec;
           this.acctId = acctId;
           this.acctCur = acctCur;
           this.acctCode = acctCode;
           this.acctSubCode = acctSubCode;
           this.bankInfo = bankInfo;
    }


    /**
     * Gets the custRec value for this IMAInfo_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this IMAInfo_Type.
     * 
     * @param custRec
     */
    public void setCustRec(com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec) {
        this.custRec = custRec;
    }


    /**
     * Gets the acctId value for this IMAInfo_Type.
     * 
     * @return acctId   * Номер ОМС
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this IMAInfo_Type.
     * 
     * @param acctId   * Номер ОМС
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the acctCur value for this IMAInfo_Type.
     * 
     * @return acctCur   * Код металла
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this IMAInfo_Type.
     * 
     * @param acctCur   * Код металла
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the acctCode value for this IMAInfo_Type.
     * 
     * @return acctCode   * Вид вклада
     */
    public long getAcctCode() {
        return acctCode;
    }


    /**
     * Sets the acctCode value for this IMAInfo_Type.
     * 
     * @param acctCode   * Вид вклада
     */
    public void setAcctCode(long acctCode) {
        this.acctCode = acctCode;
    }


    /**
     * Gets the acctSubCode value for this IMAInfo_Type.
     * 
     * @return acctSubCode   * Подвид вклада
     */
    public long getAcctSubCode() {
        return acctSubCode;
    }


    /**
     * Sets the acctSubCode value for this IMAInfo_Type.
     * 
     * @param acctSubCode   * Подвид вклада
     */
    public void setAcctSubCode(long acctSubCode) {
        this.acctSubCode = acctSubCode;
    }


    /**
     * Gets the bankInfo value for this IMAInfo_Type.
     * 
     * @return bankInfo   * Информация о номере ОСБ и ВСП, в котором открывается ОМС
     */
    public com.rssl.phizic.test.webgate.esberib.generated.MinBankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this IMAInfo_Type.
     * 
     * @param bankInfo   * Информация о номере ОСБ и ВСП, в котором открывается ОМС
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.MinBankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IMAInfo_Type)) return false;
        IMAInfo_Type other = (IMAInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              this.custRec.equals(other.getCustRec()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            this.acctCode == other.getAcctCode() &&
            this.acctSubCode == other.getAcctSubCode() &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo())));
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
        if (getCustRec() != null) {
            _hashCode += getCustRec().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        _hashCode += new Long(getAcctCode()).hashCode();
        _hashCode += new Long(getAcctSubCode()).hashCode();
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IMAInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctIdType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctSubCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctSubCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MinBankInfo_Type"));
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
