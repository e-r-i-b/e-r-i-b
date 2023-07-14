/**
 * ImsAcctInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация по ОМС
 */
public class ImsAcctInfo_Type  implements java.io.Serializable {
    /* Вид металла */
    private java.lang.String acctCur;

    /* Краткое наименование. На русском языке готовое для отображения
     * пользователю */
    private java.lang.String acctName;

    /* Остаток в граммах(Значение дробной части должно быть указано
     * с точностью до второго знака.) */
    private java.math.BigDecimal amount;

    /* Дата открытия счета */
    private java.lang.String startDate;

    /* Дата закрытия счета */
    private java.lang.String endDate;

    /* Статус счета */
    private com.rssl.phizic.test.webgate.esberib.generated.IMSStatusEnum_Type status;

    /* Номер договора счета ОМС */
    private java.lang.String agreementNumber;

    /* Максимальная сумма списания */
    private java.math.BigDecimal maxSumWrite;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec;

    public ImsAcctInfo_Type() {
    }

    public ImsAcctInfo_Type(
           java.lang.String acctCur,
           java.lang.String acctName,
           java.math.BigDecimal amount,
           java.lang.String startDate,
           java.lang.String endDate,
           com.rssl.phizic.test.webgate.esberib.generated.IMSStatusEnum_Type status,
           java.lang.String agreementNumber,
           java.math.BigDecimal maxSumWrite,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec) {
           this.acctCur = acctCur;
           this.acctName = acctName;
           this.amount = amount;
           this.startDate = startDate;
           this.endDate = endDate;
           this.status = status;
           this.agreementNumber = agreementNumber;
           this.maxSumWrite = maxSumWrite;
           this.bankInfo = bankInfo;
           this.custRec = custRec;
    }


    /**
     * Gets the acctCur value for this ImsAcctInfo_Type.
     * 
     * @return acctCur   * Вид металла
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this ImsAcctInfo_Type.
     * 
     * @param acctCur   * Вид металла
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the acctName value for this ImsAcctInfo_Type.
     * 
     * @return acctName   * Краткое наименование. На русском языке готовое для отображения
     * пользователю
     */
    public java.lang.String getAcctName() {
        return acctName;
    }


    /**
     * Sets the acctName value for this ImsAcctInfo_Type.
     * 
     * @param acctName   * Краткое наименование. На русском языке готовое для отображения
     * пользователю
     */
    public void setAcctName(java.lang.String acctName) {
        this.acctName = acctName;
    }


    /**
     * Gets the amount value for this ImsAcctInfo_Type.
     * 
     * @return amount   * Остаток в граммах(Значение дробной части должно быть указано
     * с точностью до второго знака.)
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this ImsAcctInfo_Type.
     * 
     * @param amount   * Остаток в граммах(Значение дробной части должно быть указано
     * с точностью до второго знака.)
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the startDate value for this ImsAcctInfo_Type.
     * 
     * @return startDate   * Дата открытия счета
     */
    public java.lang.String getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this ImsAcctInfo_Type.
     * 
     * @param startDate   * Дата открытия счета
     */
    public void setStartDate(java.lang.String startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this ImsAcctInfo_Type.
     * 
     * @return endDate   * Дата закрытия счета
     */
    public java.lang.String getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this ImsAcctInfo_Type.
     * 
     * @param endDate   * Дата закрытия счета
     */
    public void setEndDate(java.lang.String endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the status value for this ImsAcctInfo_Type.
     * 
     * @return status   * Статус счета
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IMSStatusEnum_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ImsAcctInfo_Type.
     * 
     * @param status   * Статус счета
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.IMSStatusEnum_Type status) {
        this.status = status;
    }


    /**
     * Gets the agreementNumber value for this ImsAcctInfo_Type.
     * 
     * @return agreementNumber   * Номер договора счета ОМС
     */
    public java.lang.String getAgreementNumber() {
        return agreementNumber;
    }


    /**
     * Sets the agreementNumber value for this ImsAcctInfo_Type.
     * 
     * @param agreementNumber   * Номер договора счета ОМС
     */
    public void setAgreementNumber(java.lang.String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }


    /**
     * Gets the maxSumWrite value for this ImsAcctInfo_Type.
     * 
     * @return maxSumWrite   * Максимальная сумма списания
     */
    public java.math.BigDecimal getMaxSumWrite() {
        return maxSumWrite;
    }


    /**
     * Sets the maxSumWrite value for this ImsAcctInfo_Type.
     * 
     * @param maxSumWrite   * Максимальная сумма списания
     */
    public void setMaxSumWrite(java.math.BigDecimal maxSumWrite) {
        this.maxSumWrite = maxSumWrite;
    }


    /**
     * Gets the bankInfo value for this ImsAcctInfo_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this ImsAcctInfo_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the custRec value for this ImsAcctInfo_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this ImsAcctInfo_Type.
     * 
     * @param custRec
     */
    public void setCustRec(com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec) {
        this.custRec = custRec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImsAcctInfo_Type)) return false;
        ImsAcctInfo_Type other = (ImsAcctInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.acctName==null && other.getAcctName()==null) || 
             (this.acctName!=null &&
              this.acctName.equals(other.getAcctName()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.agreementNumber==null && other.getAgreementNumber()==null) || 
             (this.agreementNumber!=null &&
              this.agreementNumber.equals(other.getAgreementNumber()))) &&
            ((this.maxSumWrite==null && other.getMaxSumWrite()==null) || 
             (this.maxSumWrite!=null &&
              this.maxSumWrite.equals(other.getMaxSumWrite()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              this.custRec.equals(other.getCustRec())));
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
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getAcctName() != null) {
            _hashCode += getAcctName().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getAgreementNumber() != null) {
            _hashCode += getAgreementNumber().hashCode();
        }
        if (getMaxSumWrite() != null) {
            _hashCode += getMaxSumWrite().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getCustRec() != null) {
            _hashCode += getCustRec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImsAcctInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSStatusEnum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreementNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreementNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxSumWrite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaxSumWrite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type"));
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
