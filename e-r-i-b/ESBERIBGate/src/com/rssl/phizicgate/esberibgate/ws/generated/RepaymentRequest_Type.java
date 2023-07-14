/**
 * RepaymentRequest_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class RepaymentRequest_Type  implements java.io.Serializable {
    /* Идентификатор заявки на досрочное погашение. Передается в случае
     * отмены заявки */
    private java.lang.Long terminationRequestId;

    /* Идентификатор заявки на ДП в ЕРИБ */
    private java.lang.String ERIBRequestID;

    /* Идентификатор кредитного договора в ЕКП */
    private java.lang.String prodId;

    /* Счет для погашения */
    private java.lang.String payAccount;

    /* Тип
     * Card – номер карты
     * Account – номер счета */
    private java.lang.String type;

    /* Дата досрочного погашения */
    private java.lang.String repaymentDate;

    /* Сумма для досрочного погашения */
    private java.math.BigDecimal amount;

    private java.lang.String currency;

    /* Признак полного досрочного погашения.
     * 1 – ПДП
     * 0 или не передается – ЧДП */
    private java.lang.Long fullRepayment;

    /* Дата подписания клиентом заявления на досрочное погашение */
    private java.lang.String dateOfSign;

    public RepaymentRequest_Type() {
    }

    public RepaymentRequest_Type(
           java.lang.Long terminationRequestId,
           java.lang.String ERIBRequestID,
           java.lang.String prodId,
           java.lang.String payAccount,
           java.lang.String type,
           java.lang.String repaymentDate,
           java.math.BigDecimal amount,
           java.lang.String currency,
           java.lang.Long fullRepayment,
           java.lang.String dateOfSign) {
           this.terminationRequestId = terminationRequestId;
           this.ERIBRequestID = ERIBRequestID;
           this.prodId = prodId;
           this.payAccount = payAccount;
           this.type = type;
           this.repaymentDate = repaymentDate;
           this.amount = amount;
           this.currency = currency;
           this.fullRepayment = fullRepayment;
           this.dateOfSign = dateOfSign;
    }


    /**
     * Gets the terminationRequestId value for this RepaymentRequest_Type.
     * 
     * @return terminationRequestId   * Идентификатор заявки на досрочное погашение. Передается в случае
     * отмены заявки
     */
    public java.lang.Long getTerminationRequestId() {
        return terminationRequestId;
    }


    /**
     * Sets the terminationRequestId value for this RepaymentRequest_Type.
     * 
     * @param terminationRequestId   * Идентификатор заявки на досрочное погашение. Передается в случае
     * отмены заявки
     */
    public void setTerminationRequestId(java.lang.Long terminationRequestId) {
        this.terminationRequestId = terminationRequestId;
    }


    /**
     * Gets the ERIBRequestID value for this RepaymentRequest_Type.
     * 
     * @return ERIBRequestID   * Идентификатор заявки на ДП в ЕРИБ
     */
    public java.lang.String getERIBRequestID() {
        return ERIBRequestID;
    }


    /**
     * Sets the ERIBRequestID value for this RepaymentRequest_Type.
     * 
     * @param ERIBRequestID   * Идентификатор заявки на ДП в ЕРИБ
     */
    public void setERIBRequestID(java.lang.String ERIBRequestID) {
        this.ERIBRequestID = ERIBRequestID;
    }


    /**
     * Gets the prodId value for this RepaymentRequest_Type.
     * 
     * @return prodId   * Идентификатор кредитного договора в ЕКП
     */
    public java.lang.String getProdId() {
        return prodId;
    }


    /**
     * Sets the prodId value for this RepaymentRequest_Type.
     * 
     * @param prodId   * Идентификатор кредитного договора в ЕКП
     */
    public void setProdId(java.lang.String prodId) {
        this.prodId = prodId;
    }


    /**
     * Gets the payAccount value for this RepaymentRequest_Type.
     * 
     * @return payAccount   * Счет для погашения
     */
    public java.lang.String getPayAccount() {
        return payAccount;
    }


    /**
     * Sets the payAccount value for this RepaymentRequest_Type.
     * 
     * @param payAccount   * Счет для погашения
     */
    public void setPayAccount(java.lang.String payAccount) {
        this.payAccount = payAccount;
    }


    /**
     * Gets the type value for this RepaymentRequest_Type.
     * 
     * @return type   * Тип
     * Card – номер карты
     * Account – номер счета
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this RepaymentRequest_Type.
     * 
     * @param type   * Тип
     * Card – номер карты
     * Account – номер счета
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the repaymentDate value for this RepaymentRequest_Type.
     * 
     * @return repaymentDate   * Дата досрочного погашения
     */
    public java.lang.String getRepaymentDate() {
        return repaymentDate;
    }


    /**
     * Sets the repaymentDate value for this RepaymentRequest_Type.
     * 
     * @param repaymentDate   * Дата досрочного погашения
     */
    public void setRepaymentDate(java.lang.String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }


    /**
     * Gets the amount value for this RepaymentRequest_Type.
     * 
     * @return amount   * Сумма для досрочного погашения
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this RepaymentRequest_Type.
     * 
     * @param amount   * Сумма для досрочного погашения
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the currency value for this RepaymentRequest_Type.
     * 
     * @return currency
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this RepaymentRequest_Type.
     * 
     * @param currency
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the fullRepayment value for this RepaymentRequest_Type.
     * 
     * @return fullRepayment   * Признак полного досрочного погашения.
     * 1 – ПДП
     * 0 или не передается – ЧДП
     */
    public java.lang.Long getFullRepayment() {
        return fullRepayment;
    }


    /**
     * Sets the fullRepayment value for this RepaymentRequest_Type.
     * 
     * @param fullRepayment   * Признак полного досрочного погашения.
     * 1 – ПДП
     * 0 или не передается – ЧДП
     */
    public void setFullRepayment(java.lang.Long fullRepayment) {
        this.fullRepayment = fullRepayment;
    }


    /**
     * Gets the dateOfSign value for this RepaymentRequest_Type.
     * 
     * @return dateOfSign   * Дата подписания клиентом заявления на досрочное погашение
     */
    public java.lang.String getDateOfSign() {
        return dateOfSign;
    }


    /**
     * Sets the dateOfSign value for this RepaymentRequest_Type.
     * 
     * @param dateOfSign   * Дата подписания клиентом заявления на досрочное погашение
     */
    public void setDateOfSign(java.lang.String dateOfSign) {
        this.dateOfSign = dateOfSign;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RepaymentRequest_Type)) return false;
        RepaymentRequest_Type other = (RepaymentRequest_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.terminationRequestId==null && other.getTerminationRequestId()==null) || 
             (this.terminationRequestId!=null &&
              this.terminationRequestId.equals(other.getTerminationRequestId()))) &&
            ((this.ERIBRequestID==null && other.getERIBRequestID()==null) || 
             (this.ERIBRequestID!=null &&
              this.ERIBRequestID.equals(other.getERIBRequestID()))) &&
            ((this.prodId==null && other.getProdId()==null) || 
             (this.prodId!=null &&
              this.prodId.equals(other.getProdId()))) &&
            ((this.payAccount==null && other.getPayAccount()==null) || 
             (this.payAccount!=null &&
              this.payAccount.equals(other.getPayAccount()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.repaymentDate==null && other.getRepaymentDate()==null) || 
             (this.repaymentDate!=null &&
              this.repaymentDate.equals(other.getRepaymentDate()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.fullRepayment==null && other.getFullRepayment()==null) || 
             (this.fullRepayment!=null &&
              this.fullRepayment.equals(other.getFullRepayment()))) &&
            ((this.dateOfSign==null && other.getDateOfSign()==null) || 
             (this.dateOfSign!=null &&
              this.dateOfSign.equals(other.getDateOfSign())));
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
        if (getTerminationRequestId() != null) {
            _hashCode += getTerminationRequestId().hashCode();
        }
        if (getERIBRequestID() != null) {
            _hashCode += getERIBRequestID().hashCode();
        }
        if (getProdId() != null) {
            _hashCode += getProdId().hashCode();
        }
        if (getPayAccount() != null) {
            _hashCode += getPayAccount().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getRepaymentDate() != null) {
            _hashCode += getRepaymentDate().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getFullRepayment() != null) {
            _hashCode += getFullRepayment().hashCode();
        }
        if (getDateOfSign() != null) {
            _hashCode += getDateOfSign().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RepaymentRequest_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepaymentRequest_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminationRequestId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TerminationRequestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERIBRequestID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ERIBRequestID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repaymentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepaymentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullRepayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullRepayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateOfSign");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateOfSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
