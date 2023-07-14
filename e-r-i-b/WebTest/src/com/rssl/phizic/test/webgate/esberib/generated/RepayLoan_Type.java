/**
 * RepayLoan_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Погашаемый по Top-Up договор
 */
public class RepayLoan_Type  implements java.io.Serializable {
    /* ИД источника формирования кредитных историй */
    private java.lang.String idSource;

    /* ИД Договора */
    private java.lang.String idContract;

    /* АС СД: Номер ссудного счёта */
    private java.lang.String loanAccount_Number;

    /* Номер кредитного договора */
    private java.lang.String agreementNumber;

    /* Дата выдачи кредита */
    private java.lang.String startDate;

    /* Плановая дата погашения кредита */
    private java.lang.String maturityDate;

    /* Сумма кредита (начальная) */
    private java.math.BigDecimal totalAmount;

    /* Валюта кредита */
    private java.lang.String currency;

    public RepayLoan_Type() {
    }

    public RepayLoan_Type(
           java.lang.String idSource,
           java.lang.String idContract,
           java.lang.String loanAccount_Number,
           java.lang.String agreementNumber,
           java.lang.String startDate,
           java.lang.String maturityDate,
           java.math.BigDecimal totalAmount,
           java.lang.String currency) {
           this.idSource = idSource;
           this.idContract = idContract;
           this.loanAccount_Number = loanAccount_Number;
           this.agreementNumber = agreementNumber;
           this.startDate = startDate;
           this.maturityDate = maturityDate;
           this.totalAmount = totalAmount;
           this.currency = currency;
    }


    /**
     * Gets the idSource value for this RepayLoan_Type.
     * 
     * @return idSource   * ИД источника формирования кредитных историй
     */
    public java.lang.String getIdSource() {
        return idSource;
    }


    /**
     * Sets the idSource value for this RepayLoan_Type.
     * 
     * @param idSource   * ИД источника формирования кредитных историй
     */
    public void setIdSource(java.lang.String idSource) {
        this.idSource = idSource;
    }


    /**
     * Gets the idContract value for this RepayLoan_Type.
     * 
     * @return idContract   * ИД Договора
     */
    public java.lang.String getIdContract() {
        return idContract;
    }


    /**
     * Sets the idContract value for this RepayLoan_Type.
     * 
     * @param idContract   * ИД Договора
     */
    public void setIdContract(java.lang.String idContract) {
        this.idContract = idContract;
    }


    /**
     * Gets the loanAccount_Number value for this RepayLoan_Type.
     * 
     * @return loanAccount_Number   * АС СД: Номер ссудного счёта
     */
    public java.lang.String getLoanAccount_Number() {
        return loanAccount_Number;
    }


    /**
     * Sets the loanAccount_Number value for this RepayLoan_Type.
     * 
     * @param loanAccount_Number   * АС СД: Номер ссудного счёта
     */
    public void setLoanAccount_Number(java.lang.String loanAccount_Number) {
        this.loanAccount_Number = loanAccount_Number;
    }


    /**
     * Gets the agreementNumber value for this RepayLoan_Type.
     * 
     * @return agreementNumber   * Номер кредитного договора
     */
    public java.lang.String getAgreementNumber() {
        return agreementNumber;
    }


    /**
     * Sets the agreementNumber value for this RepayLoan_Type.
     * 
     * @param agreementNumber   * Номер кредитного договора
     */
    public void setAgreementNumber(java.lang.String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }


    /**
     * Gets the startDate value for this RepayLoan_Type.
     * 
     * @return startDate   * Дата выдачи кредита
     */
    public java.lang.String getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this RepayLoan_Type.
     * 
     * @param startDate   * Дата выдачи кредита
     */
    public void setStartDate(java.lang.String startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the maturityDate value for this RepayLoan_Type.
     * 
     * @return maturityDate   * Плановая дата погашения кредита
     */
    public java.lang.String getMaturityDate() {
        return maturityDate;
    }


    /**
     * Sets the maturityDate value for this RepayLoan_Type.
     * 
     * @param maturityDate   * Плановая дата погашения кредита
     */
    public void setMaturityDate(java.lang.String maturityDate) {
        this.maturityDate = maturityDate;
    }


    /**
     * Gets the totalAmount value for this RepayLoan_Type.
     * 
     * @return totalAmount   * Сумма кредита (начальная)
     */
    public java.math.BigDecimal getTotalAmount() {
        return totalAmount;
    }


    /**
     * Sets the totalAmount value for this RepayLoan_Type.
     * 
     * @param totalAmount   * Сумма кредита (начальная)
     */
    public void setTotalAmount(java.math.BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    /**
     * Gets the currency value for this RepayLoan_Type.
     * 
     * @return currency   * Валюта кредита
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this RepayLoan_Type.
     * 
     * @param currency   * Валюта кредита
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RepayLoan_Type)) return false;
        RepayLoan_Type other = (RepayLoan_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idSource==null && other.getIdSource()==null) || 
             (this.idSource!=null &&
              this.idSource.equals(other.getIdSource()))) &&
            ((this.idContract==null && other.getIdContract()==null) || 
             (this.idContract!=null &&
              this.idContract.equals(other.getIdContract()))) &&
            ((this.loanAccount_Number==null && other.getLoanAccount_Number()==null) || 
             (this.loanAccount_Number!=null &&
              this.loanAccount_Number.equals(other.getLoanAccount_Number()))) &&
            ((this.agreementNumber==null && other.getAgreementNumber()==null) || 
             (this.agreementNumber!=null &&
              this.agreementNumber.equals(other.getAgreementNumber()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.maturityDate==null && other.getMaturityDate()==null) || 
             (this.maturityDate!=null &&
              this.maturityDate.equals(other.getMaturityDate()))) &&
            ((this.totalAmount==null && other.getTotalAmount()==null) || 
             (this.totalAmount!=null &&
              this.totalAmount.equals(other.getTotalAmount()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency())));
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
        if (getIdSource() != null) {
            _hashCode += getIdSource().hashCode();
        }
        if (getIdContract() != null) {
            _hashCode += getIdContract().hashCode();
        }
        if (getLoanAccount_Number() != null) {
            _hashCode += getLoanAccount_Number().hashCode();
        }
        if (getAgreementNumber() != null) {
            _hashCode += getAgreementNumber().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getMaturityDate() != null) {
            _hashCode += getMaturityDate().hashCode();
        }
        if (getTotalAmount() != null) {
            _hashCode += getTotalAmount().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RepayLoan_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepayLoan_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdSource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idContract");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdContract"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanAccount_Number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAccount_Number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreementNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreementNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maturityDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaturityDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TotalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
