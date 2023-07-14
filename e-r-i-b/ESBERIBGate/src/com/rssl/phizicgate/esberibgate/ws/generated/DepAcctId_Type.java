/**
 * DepAcctId_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Идентификатор депозитного счета
 */
public class DepAcctId_Type  implements java.io.Serializable {
    private java.lang.String systemId;

    private java.lang.String acctId;

    /* БИК */
    private java.lang.String BIC;

    /* Корсчет */
    private java.lang.String corrAcctId;

    private java.lang.String acctCur;

    private java.lang.String acctName;

    /* Тип счета */
    private java.lang.Long acctCode;

    /* Подтип счета */
    private java.lang.Long acctSubCode;

    /* Максимальная сумма списания */
    private java.math.BigDecimal maxSumWrite;

    /* Дата открытия счета */
    private java.lang.String openDate;

    /* Статус счета */
    private com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type status;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo;

    /* Элементы для передачи информации о порядке уплаты процентов */
    private com.rssl.phizicgate.esberibgate.ws.generated.VariantInterestPayment_Type variantInterestPayment;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    public DepAcctId_Type() {
    }

    public DepAcctId_Type(
           java.lang.String systemId,
           java.lang.String acctId,
           java.lang.String BIC,
           java.lang.String corrAcctId,
           java.lang.String acctCur,
           java.lang.String acctName,
           java.lang.Long acctCode,
           java.lang.Long acctSubCode,
           java.math.BigDecimal maxSumWrite,
           java.lang.String openDate,
           com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.VariantInterestPayment_Type variantInterestPayment,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
           this.systemId = systemId;
           this.acctId = acctId;
           this.BIC = BIC;
           this.corrAcctId = corrAcctId;
           this.acctCur = acctCur;
           this.acctName = acctName;
           this.acctCode = acctCode;
           this.acctSubCode = acctSubCode;
           this.maxSumWrite = maxSumWrite;
           this.openDate = openDate;
           this.status = status;
           this.custInfo = custInfo;
           this.variantInterestPayment = variantInterestPayment;
           this.bankInfo = bankInfo;
    }


    /**
     * Gets the systemId value for this DepAcctId_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this DepAcctId_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the acctId value for this DepAcctId_Type.
     * 
     * @return acctId
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this DepAcctId_Type.
     * 
     * @param acctId
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the BIC value for this DepAcctId_Type.
     * 
     * @return BIC   * БИК
     */
    public java.lang.String getBIC() {
        return BIC;
    }


    /**
     * Sets the BIC value for this DepAcctId_Type.
     * 
     * @param BIC   * БИК
     */
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }


    /**
     * Gets the corrAcctId value for this DepAcctId_Type.
     * 
     * @return corrAcctId   * Корсчет
     */
    public java.lang.String getCorrAcctId() {
        return corrAcctId;
    }


    /**
     * Sets the corrAcctId value for this DepAcctId_Type.
     * 
     * @param corrAcctId   * Корсчет
     */
    public void setCorrAcctId(java.lang.String corrAcctId) {
        this.corrAcctId = corrAcctId;
    }


    /**
     * Gets the acctCur value for this DepAcctId_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this DepAcctId_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the acctName value for this DepAcctId_Type.
     * 
     * @return acctName
     */
    public java.lang.String getAcctName() {
        return acctName;
    }


    /**
     * Sets the acctName value for this DepAcctId_Type.
     * 
     * @param acctName
     */
    public void setAcctName(java.lang.String acctName) {
        this.acctName = acctName;
    }


    /**
     * Gets the acctCode value for this DepAcctId_Type.
     * 
     * @return acctCode   * Тип счета
     */
    public java.lang.Long getAcctCode() {
        return acctCode;
    }


    /**
     * Sets the acctCode value for this DepAcctId_Type.
     * 
     * @param acctCode   * Тип счета
     */
    public void setAcctCode(java.lang.Long acctCode) {
        this.acctCode = acctCode;
    }


    /**
     * Gets the acctSubCode value for this DepAcctId_Type.
     * 
     * @return acctSubCode   * Подтип счета
     */
    public java.lang.Long getAcctSubCode() {
        return acctSubCode;
    }


    /**
     * Sets the acctSubCode value for this DepAcctId_Type.
     * 
     * @param acctSubCode   * Подтип счета
     */
    public void setAcctSubCode(java.lang.Long acctSubCode) {
        this.acctSubCode = acctSubCode;
    }


    /**
     * Gets the maxSumWrite value for this DepAcctId_Type.
     * 
     * @return maxSumWrite   * Максимальная сумма списания
     */
    public java.math.BigDecimal getMaxSumWrite() {
        return maxSumWrite;
    }


    /**
     * Sets the maxSumWrite value for this DepAcctId_Type.
     * 
     * @param maxSumWrite   * Максимальная сумма списания
     */
    public void setMaxSumWrite(java.math.BigDecimal maxSumWrite) {
        this.maxSumWrite = maxSumWrite;
    }


    /**
     * Gets the openDate value for this DepAcctId_Type.
     * 
     * @return openDate   * Дата открытия счета
     */
    public java.lang.String getOpenDate() {
        return openDate;
    }


    /**
     * Sets the openDate value for this DepAcctId_Type.
     * 
     * @param openDate   * Дата открытия счета
     */
    public void setOpenDate(java.lang.String openDate) {
        this.openDate = openDate;
    }


    /**
     * Gets the status value for this DepAcctId_Type.
     * 
     * @return status   * Статус счета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepAcctId_Type.
     * 
     * @param status   * Статус счета
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type status) {
        this.status = status;
    }


    /**
     * Gets the custInfo value for this DepAcctId_Type.
     * 
     * @return custInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type getCustInfo() {
        return custInfo;
    }


    /**
     * Sets the custInfo value for this DepAcctId_Type.
     * 
     * @param custInfo
     */
    public void setCustInfo(com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo) {
        this.custInfo = custInfo;
    }


    /**
     * Gets the variantInterestPayment value for this DepAcctId_Type.
     * 
     * @return variantInterestPayment   * Элементы для передачи информации о порядке уплаты процентов
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.VariantInterestPayment_Type getVariantInterestPayment() {
        return variantInterestPayment;
    }


    /**
     * Sets the variantInterestPayment value for this DepAcctId_Type.
     * 
     * @param variantInterestPayment   * Элементы для передачи информации о порядке уплаты процентов
     */
    public void setVariantInterestPayment(com.rssl.phizicgate.esberibgate.ws.generated.VariantInterestPayment_Type variantInterestPayment) {
        this.variantInterestPayment = variantInterestPayment;
    }


    /**
     * Gets the bankInfo value for this DepAcctId_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this DepAcctId_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAcctId_Type)) return false;
        DepAcctId_Type other = (DepAcctId_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.BIC==null && other.getBIC()==null) || 
             (this.BIC!=null &&
              this.BIC.equals(other.getBIC()))) &&
            ((this.corrAcctId==null && other.getCorrAcctId()==null) || 
             (this.corrAcctId!=null &&
              this.corrAcctId.equals(other.getCorrAcctId()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.acctName==null && other.getAcctName()==null) || 
             (this.acctName!=null &&
              this.acctName.equals(other.getAcctName()))) &&
            ((this.acctCode==null && other.getAcctCode()==null) || 
             (this.acctCode!=null &&
              this.acctCode.equals(other.getAcctCode()))) &&
            ((this.acctSubCode==null && other.getAcctSubCode()==null) || 
             (this.acctSubCode!=null &&
              this.acctSubCode.equals(other.getAcctSubCode()))) &&
            ((this.maxSumWrite==null && other.getMaxSumWrite()==null) || 
             (this.maxSumWrite!=null &&
              this.maxSumWrite.equals(other.getMaxSumWrite()))) &&
            ((this.openDate==null && other.getOpenDate()==null) || 
             (this.openDate!=null &&
              this.openDate.equals(other.getOpenDate()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.custInfo==null && other.getCustInfo()==null) || 
             (this.custInfo!=null &&
              this.custInfo.equals(other.getCustInfo()))) &&
            ((this.variantInterestPayment==null && other.getVariantInterestPayment()==null) || 
             (this.variantInterestPayment!=null &&
              this.variantInterestPayment.equals(other.getVariantInterestPayment()))) &&
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
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getBIC() != null) {
            _hashCode += getBIC().hashCode();
        }
        if (getCorrAcctId() != null) {
            _hashCode += getCorrAcctId().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getAcctName() != null) {
            _hashCode += getAcctName().hashCode();
        }
        if (getAcctCode() != null) {
            _hashCode += getAcctCode().hashCode();
        }
        if (getAcctSubCode() != null) {
            _hashCode += getAcctSubCode().hashCode();
        }
        if (getMaxSumWrite() != null) {
            _hashCode += getMaxSumWrite().hashCode();
        }
        if (getOpenDate() != null) {
            _hashCode += getOpenDate().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getCustInfo() != null) {
            _hashCode += getCustInfo().hashCode();
        }
        if (getVariantInterestPayment() != null) {
            _hashCode += getVariantInterestPayment().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepAcctId_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("BIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("acctCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctSubCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctSubCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("openDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OpenDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccountStatusEnum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variantInterestPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VariantInterestPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VariantInterestPayment_Type"));
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
