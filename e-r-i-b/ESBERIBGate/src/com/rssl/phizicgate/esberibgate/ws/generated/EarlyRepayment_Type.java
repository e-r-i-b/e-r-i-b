/**
 * EarlyRepayment_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class EarlyRepayment_Type  implements java.io.Serializable {
    private java.math.BigDecimal amount;

    private java.lang.String date;

    private java.lang.String status;

    private java.lang.String account;

    /* Канал, по которому зарегистрировано ДП.
     * Возможные значения:
     * 1 – канал УКО
     * 2 – канал не УКО(ВСП) */
    private java.lang.String repaymentChannel;

    /* Идентификатор заявки на досрочное погашение */
    private java.lang.String terminationRequestId;

    /* Идентификатор заявки на ДП в ЕРИБ */
    private java.lang.String ERIBRequestID;

    public EarlyRepayment_Type() {
    }

    public EarlyRepayment_Type(
           java.math.BigDecimal amount,
           java.lang.String date,
           java.lang.String status,
           java.lang.String account,
           java.lang.String repaymentChannel,
           java.lang.String terminationRequestId,
           java.lang.String ERIBRequestID) {
           this.amount = amount;
           this.date = date;
           this.status = status;
           this.account = account;
           this.repaymentChannel = repaymentChannel;
           this.terminationRequestId = terminationRequestId;
           this.ERIBRequestID = ERIBRequestID;
    }


    /**
     * Gets the amount value for this EarlyRepayment_Type.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this EarlyRepayment_Type.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the date value for this EarlyRepayment_Type.
     * 
     * @return date
     */
    public java.lang.String getDate() {
        return date;
    }


    /**
     * Sets the date value for this EarlyRepayment_Type.
     * 
     * @param date
     */
    public void setDate(java.lang.String date) {
        this.date = date;
    }


    /**
     * Gets the status value for this EarlyRepayment_Type.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this EarlyRepayment_Type.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the account value for this EarlyRepayment_Type.
     * 
     * @return account
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this EarlyRepayment_Type.
     * 
     * @param account
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the repaymentChannel value for this EarlyRepayment_Type.
     * 
     * @return repaymentChannel   * Канал, по которому зарегистрировано ДП.
     * Возможные значения:
     * 1 – канал УКО
     * 2 – канал не УКО(ВСП)
     */
    public java.lang.String getRepaymentChannel() {
        return repaymentChannel;
    }


    /**
     * Sets the repaymentChannel value for this EarlyRepayment_Type.
     * 
     * @param repaymentChannel   * Канал, по которому зарегистрировано ДП.
     * Возможные значения:
     * 1 – канал УКО
     * 2 – канал не УКО(ВСП)
     */
    public void setRepaymentChannel(java.lang.String repaymentChannel) {
        this.repaymentChannel = repaymentChannel;
    }


    /**
     * Gets the terminationRequestId value for this EarlyRepayment_Type.
     * 
     * @return terminationRequestId   * Идентификатор заявки на досрочное погашение
     */
    public java.lang.String getTerminationRequestId() {
        return terminationRequestId;
    }


    /**
     * Sets the terminationRequestId value for this EarlyRepayment_Type.
     * 
     * @param terminationRequestId   * Идентификатор заявки на досрочное погашение
     */
    public void setTerminationRequestId(java.lang.String terminationRequestId) {
        this.terminationRequestId = terminationRequestId;
    }


    /**
     * Gets the ERIBRequestID value for this EarlyRepayment_Type.
     * 
     * @return ERIBRequestID   * Идентификатор заявки на ДП в ЕРИБ
     */
    public java.lang.String getERIBRequestID() {
        return ERIBRequestID;
    }


    /**
     * Sets the ERIBRequestID value for this EarlyRepayment_Type.
     * 
     * @param ERIBRequestID   * Идентификатор заявки на ДП в ЕРИБ
     */
    public void setERIBRequestID(java.lang.String ERIBRequestID) {
        this.ERIBRequestID = ERIBRequestID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EarlyRepayment_Type)) return false;
        EarlyRepayment_Type other = (EarlyRepayment_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            ((this.repaymentChannel==null && other.getRepaymentChannel()==null) || 
             (this.repaymentChannel!=null &&
              this.repaymentChannel.equals(other.getRepaymentChannel()))) &&
            ((this.terminationRequestId==null && other.getTerminationRequestId()==null) || 
             (this.terminationRequestId!=null &&
              this.terminationRequestId.equals(other.getTerminationRequestId()))) &&
            ((this.ERIBRequestID==null && other.getERIBRequestID()==null) || 
             (this.ERIBRequestID!=null &&
              this.ERIBRequestID.equals(other.getERIBRequestID())));
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
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getRepaymentChannel() != null) {
            _hashCode += getRepaymentChannel().hashCode();
        }
        if (getTerminationRequestId() != null) {
            _hashCode += getTerminationRequestId().hashCode();
        }
        if (getERIBRequestID() != null) {
            _hashCode += getERIBRequestID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EarlyRepayment_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EarlyRepayment_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repaymentChannel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepaymentChannel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminationRequestId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TerminationRequestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERIBRequestID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ERIBRequestID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
