/**
 * TransactionData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the specific detials of a transaction
 */
public class TransactionData  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount amount;

    private java.lang.String dueDate;

    private java.lang.String estimatedDeliveryDate;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ExecutionSpeed executionSpeed;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData myAccountData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountBankType otherAccountBankType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData otherAccountData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountOwnershipType otherAccountOwnershipType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountType otherAccountType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount previousAmount;

    private java.lang.Integer recurringFrequency;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Schedule schedule;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.TransferMediumType transferMediumType;

    public TransactionData() {
    }

    public TransactionData(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount amount,
           java.lang.String dueDate,
           java.lang.String estimatedDeliveryDate,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ExecutionSpeed executionSpeed,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData myAccountData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountBankType otherAccountBankType,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData otherAccountData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountOwnershipType otherAccountOwnershipType,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountType otherAccountType,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount previousAmount,
           java.lang.Integer recurringFrequency,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Schedule schedule,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.TransferMediumType transferMediumType) {
           this.amount = amount;
           this.dueDate = dueDate;
           this.estimatedDeliveryDate = estimatedDeliveryDate;
           this.executionSpeed = executionSpeed;
           this.myAccountData = myAccountData;
           this.otherAccountBankType = otherAccountBankType;
           this.otherAccountData = otherAccountData;
           this.otherAccountOwnershipType = otherAccountOwnershipType;
           this.otherAccountType = otherAccountType;
           this.previousAmount = previousAmount;
           this.recurringFrequency = recurringFrequency;
           this.schedule = schedule;
           this.transferMediumType = transferMediumType;
    }


    /**
     * Gets the amount value for this TransactionData.
     * 
     * @return amount
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this TransactionData.
     * 
     * @param amount
     */
    public void setAmount(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount amount) {
        this.amount = amount;
    }


    /**
     * Gets the dueDate value for this TransactionData.
     * 
     * @return dueDate
     */
    public java.lang.String getDueDate() {
        return dueDate;
    }


    /**
     * Sets the dueDate value for this TransactionData.
     * 
     * @param dueDate
     */
    public void setDueDate(java.lang.String dueDate) {
        this.dueDate = dueDate;
    }


    /**
     * Gets the estimatedDeliveryDate value for this TransactionData.
     * 
     * @return estimatedDeliveryDate
     */
    public java.lang.String getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }


    /**
     * Sets the estimatedDeliveryDate value for this TransactionData.
     * 
     * @param estimatedDeliveryDate
     */
    public void setEstimatedDeliveryDate(java.lang.String estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }


    /**
     * Gets the executionSpeed value for this TransactionData.
     * 
     * @return executionSpeed
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ExecutionSpeed getExecutionSpeed() {
        return executionSpeed;
    }


    /**
     * Sets the executionSpeed value for this TransactionData.
     * 
     * @param executionSpeed
     */
    public void setExecutionSpeed(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ExecutionSpeed executionSpeed) {
        this.executionSpeed = executionSpeed;
    }


    /**
     * Gets the myAccountData value for this TransactionData.
     * 
     * @return myAccountData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData getMyAccountData() {
        return myAccountData;
    }


    /**
     * Sets the myAccountData value for this TransactionData.
     * 
     * @param myAccountData
     */
    public void setMyAccountData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData myAccountData) {
        this.myAccountData = myAccountData;
    }


    /**
     * Gets the otherAccountBankType value for this TransactionData.
     * 
     * @return otherAccountBankType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountBankType getOtherAccountBankType() {
        return otherAccountBankType;
    }


    /**
     * Sets the otherAccountBankType value for this TransactionData.
     * 
     * @param otherAccountBankType
     */
    public void setOtherAccountBankType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountBankType otherAccountBankType) {
        this.otherAccountBankType = otherAccountBankType;
    }


    /**
     * Gets the otherAccountData value for this TransactionData.
     * 
     * @return otherAccountData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData getOtherAccountData() {
        return otherAccountData;
    }


    /**
     * Sets the otherAccountData value for this TransactionData.
     * 
     * @param otherAccountData
     */
    public void setOtherAccountData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountData otherAccountData) {
        this.otherAccountData = otherAccountData;
    }


    /**
     * Gets the otherAccountOwnershipType value for this TransactionData.
     * 
     * @return otherAccountOwnershipType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountOwnershipType getOtherAccountOwnershipType() {
        return otherAccountOwnershipType;
    }


    /**
     * Sets the otherAccountOwnershipType value for this TransactionData.
     * 
     * @param otherAccountOwnershipType
     */
    public void setOtherAccountOwnershipType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountOwnershipType otherAccountOwnershipType) {
        this.otherAccountOwnershipType = otherAccountOwnershipType;
    }


    /**
     * Gets the otherAccountType value for this TransactionData.
     * 
     * @return otherAccountType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountType getOtherAccountType() {
        return otherAccountType;
    }


    /**
     * Sets the otherAccountType value for this TransactionData.
     * 
     * @param otherAccountType
     */
    public void setOtherAccountType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OtherAccountType otherAccountType) {
        this.otherAccountType = otherAccountType;
    }


    /**
     * Gets the previousAmount value for this TransactionData.
     * 
     * @return previousAmount
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getPreviousAmount() {
        return previousAmount;
    }


    /**
     * Sets the previousAmount value for this TransactionData.
     * 
     * @param previousAmount
     */
    public void setPreviousAmount(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount previousAmount) {
        this.previousAmount = previousAmount;
    }


    /**
     * Gets the recurringFrequency value for this TransactionData.
     * 
     * @return recurringFrequency
     */
    public java.lang.Integer getRecurringFrequency() {
        return recurringFrequency;
    }


    /**
     * Sets the recurringFrequency value for this TransactionData.
     * 
     * @param recurringFrequency
     */
    public void setRecurringFrequency(java.lang.Integer recurringFrequency) {
        this.recurringFrequency = recurringFrequency;
    }


    /**
     * Gets the schedule value for this TransactionData.
     * 
     * @return schedule
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Schedule getSchedule() {
        return schedule;
    }


    /**
     * Sets the schedule value for this TransactionData.
     * 
     * @param schedule
     */
    public void setSchedule(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Schedule schedule) {
        this.schedule = schedule;
    }


    /**
     * Gets the transferMediumType value for this TransactionData.
     * 
     * @return transferMediumType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.TransferMediumType getTransferMediumType() {
        return transferMediumType;
    }


    /**
     * Sets the transferMediumType value for this TransactionData.
     * 
     * @param transferMediumType
     */
    public void setTransferMediumType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.TransferMediumType transferMediumType) {
        this.transferMediumType = transferMediumType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionData)) return false;
        TransactionData other = (TransactionData) obj;
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
            ((this.dueDate==null && other.getDueDate()==null) || 
             (this.dueDate!=null &&
              this.dueDate.equals(other.getDueDate()))) &&
            ((this.estimatedDeliveryDate==null && other.getEstimatedDeliveryDate()==null) || 
             (this.estimatedDeliveryDate!=null &&
              this.estimatedDeliveryDate.equals(other.getEstimatedDeliveryDate()))) &&
            ((this.executionSpeed==null && other.getExecutionSpeed()==null) || 
             (this.executionSpeed!=null &&
              this.executionSpeed.equals(other.getExecutionSpeed()))) &&
            ((this.myAccountData==null && other.getMyAccountData()==null) || 
             (this.myAccountData!=null &&
              this.myAccountData.equals(other.getMyAccountData()))) &&
            ((this.otherAccountBankType==null && other.getOtherAccountBankType()==null) || 
             (this.otherAccountBankType!=null &&
              this.otherAccountBankType.equals(other.getOtherAccountBankType()))) &&
            ((this.otherAccountData==null && other.getOtherAccountData()==null) || 
             (this.otherAccountData!=null &&
              this.otherAccountData.equals(other.getOtherAccountData()))) &&
            ((this.otherAccountOwnershipType==null && other.getOtherAccountOwnershipType()==null) || 
             (this.otherAccountOwnershipType!=null &&
              this.otherAccountOwnershipType.equals(other.getOtherAccountOwnershipType()))) &&
            ((this.otherAccountType==null && other.getOtherAccountType()==null) || 
             (this.otherAccountType!=null &&
              this.otherAccountType.equals(other.getOtherAccountType()))) &&
            ((this.previousAmount==null && other.getPreviousAmount()==null) || 
             (this.previousAmount!=null &&
              this.previousAmount.equals(other.getPreviousAmount()))) &&
            ((this.recurringFrequency==null && other.getRecurringFrequency()==null) || 
             (this.recurringFrequency!=null &&
              this.recurringFrequency.equals(other.getRecurringFrequency()))) &&
            ((this.schedule==null && other.getSchedule()==null) || 
             (this.schedule!=null &&
              this.schedule.equals(other.getSchedule()))) &&
            ((this.transferMediumType==null && other.getTransferMediumType()==null) || 
             (this.transferMediumType!=null &&
              this.transferMediumType.equals(other.getTransferMediumType())));
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
        if (getDueDate() != null) {
            _hashCode += getDueDate().hashCode();
        }
        if (getEstimatedDeliveryDate() != null) {
            _hashCode += getEstimatedDeliveryDate().hashCode();
        }
        if (getExecutionSpeed() != null) {
            _hashCode += getExecutionSpeed().hashCode();
        }
        if (getMyAccountData() != null) {
            _hashCode += getMyAccountData().hashCode();
        }
        if (getOtherAccountBankType() != null) {
            _hashCode += getOtherAccountBankType().hashCode();
        }
        if (getOtherAccountData() != null) {
            _hashCode += getOtherAccountData().hashCode();
        }
        if (getOtherAccountOwnershipType() != null) {
            _hashCode += getOtherAccountOwnershipType().hashCode();
        }
        if (getOtherAccountType() != null) {
            _hashCode += getOtherAccountType().hashCode();
        }
        if (getPreviousAmount() != null) {
            _hashCode += getPreviousAmount().hashCode();
        }
        if (getRecurringFrequency() != null) {
            _hashCode += getRecurringFrequency().hashCode();
        }
        if (getSchedule() != null) {
            _hashCode += getSchedule().hashCode();
        }
        if (getTransferMediumType() != null) {
            _hashCode += getTransferMediumType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransactionData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TransactionData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "dueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estimatedDeliveryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "estimatedDeliveryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executionSpeed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "executionSpeed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ExecutionSpeed"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("myAccountData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "myAccountData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherAccountBankType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "otherAccountBankType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OtherAccountBankType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherAccountData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "otherAccountData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherAccountOwnershipType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "otherAccountOwnershipType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OtherAccountOwnershipType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherAccountType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "otherAccountType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OtherAccountType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("previousAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "previousAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recurringFrequency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "recurringFrequency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedule");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "schedule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Schedule"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transferMediumType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "transferMediumType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TransferMediumType"));
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
