/**
 * BillingPayPrepRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Ответ интерфейса TBP_PR подготовки билингового платежа к отправке
 */
public class BillingPayPrepRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    private java.lang.String systemId;

    private com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type recipientRec;

    /* Признак наличия комиссии при осуществлении платежей по подписке
     * на автоплатеж в соответствии с тарифами банка */
    private java.lang.Boolean withCommision;

    private java.math.BigDecimal commission;

    private java.lang.String commissionCur;

    /* Номер операции в БС ТБ. Наличие данного поля и поля Commission
     * в документе является признаком того, что переданных ранее реквизитов
     * достаточно для исполнения операции */
    private java.lang.String madeOperationId;

    public BillingPayPrepRs_Type() {
    }

    public BillingPayPrepRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status,
           java.lang.String systemId,
           com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type recipientRec,
           java.lang.Boolean withCommision,
           java.math.BigDecimal commission,
           java.lang.String commissionCur,
           java.lang.String madeOperationId) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.systemId = systemId;
           this.recipientRec = recipientRec;
           this.withCommision = withCommision;
           this.commission = commission;
           this.commissionCur = commissionCur;
           this.madeOperationId = madeOperationId;
    }


    /**
     * Gets the rqUID value for this BillingPayPrepRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this BillingPayPrepRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this BillingPayPrepRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this BillingPayPrepRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this BillingPayPrepRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this BillingPayPrepRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this BillingPayPrepRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this BillingPayPrepRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the systemId value for this BillingPayPrepRs_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this BillingPayPrepRs_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the recipientRec value for this BillingPayPrepRs_Type.
     * 
     * @return recipientRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type getRecipientRec() {
        return recipientRec;
    }


    /**
     * Sets the recipientRec value for this BillingPayPrepRs_Type.
     * 
     * @param recipientRec
     */
    public void setRecipientRec(com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type recipientRec) {
        this.recipientRec = recipientRec;
    }


    /**
     * Gets the withCommision value for this BillingPayPrepRs_Type.
     * 
     * @return withCommision   * Признак наличия комиссии при осуществлении платежей по подписке
     * на автоплатеж в соответствии с тарифами банка
     */
    public java.lang.Boolean getWithCommision() {
        return withCommision;
    }


    /**
     * Sets the withCommision value for this BillingPayPrepRs_Type.
     * 
     * @param withCommision   * Признак наличия комиссии при осуществлении платежей по подписке
     * на автоплатеж в соответствии с тарифами банка
     */
    public void setWithCommision(java.lang.Boolean withCommision) {
        this.withCommision = withCommision;
    }


    /**
     * Gets the commission value for this BillingPayPrepRs_Type.
     * 
     * @return commission
     */
    public java.math.BigDecimal getCommission() {
        return commission;
    }


    /**
     * Sets the commission value for this BillingPayPrepRs_Type.
     * 
     * @param commission
     */
    public void setCommission(java.math.BigDecimal commission) {
        this.commission = commission;
    }


    /**
     * Gets the commissionCur value for this BillingPayPrepRs_Type.
     * 
     * @return commissionCur
     */
    public java.lang.String getCommissionCur() {
        return commissionCur;
    }


    /**
     * Sets the commissionCur value for this BillingPayPrepRs_Type.
     * 
     * @param commissionCur
     */
    public void setCommissionCur(java.lang.String commissionCur) {
        this.commissionCur = commissionCur;
    }


    /**
     * Gets the madeOperationId value for this BillingPayPrepRs_Type.
     * 
     * @return madeOperationId   * Номер операции в БС ТБ. Наличие данного поля и поля Commission
     * в документе является признаком того, что переданных ранее реквизитов
     * достаточно для исполнения операции
     */
    public java.lang.String getMadeOperationId() {
        return madeOperationId;
    }


    /**
     * Sets the madeOperationId value for this BillingPayPrepRs_Type.
     * 
     * @param madeOperationId   * Номер операции в БС ТБ. Наличие данного поля и поля Commission
     * в документе является признаком того, что переданных ранее реквизитов
     * достаточно для исполнения операции
     */
    public void setMadeOperationId(java.lang.String madeOperationId) {
        this.madeOperationId = madeOperationId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BillingPayPrepRs_Type)) return false;
        BillingPayPrepRs_Type other = (BillingPayPrepRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.recipientRec==null && other.getRecipientRec()==null) || 
             (this.recipientRec!=null &&
              this.recipientRec.equals(other.getRecipientRec()))) &&
            ((this.withCommision==null && other.getWithCommision()==null) || 
             (this.withCommision!=null &&
              this.withCommision.equals(other.getWithCommision()))) &&
            ((this.commission==null && other.getCommission()==null) || 
             (this.commission!=null &&
              this.commission.equals(other.getCommission()))) &&
            ((this.commissionCur==null && other.getCommissionCur()==null) || 
             (this.commissionCur!=null &&
              this.commissionCur.equals(other.getCommissionCur()))) &&
            ((this.madeOperationId==null && other.getMadeOperationId()==null) || 
             (this.madeOperationId!=null &&
              this.madeOperationId.equals(other.getMadeOperationId())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getRecipientRec() != null) {
            _hashCode += getRecipientRec().hashCode();
        }
        if (getWithCommision() != null) {
            _hashCode += getWithCommision().hashCode();
        }
        if (getCommission() != null) {
            _hashCode += getCommission().hashCode();
        }
        if (getCommissionCur() != null) {
            _hashCode += getCommissionCur().hashCode();
        }
        if (getMadeOperationId() != null) {
            _hashCode += getMadeOperationId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BillingPayPrepRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayPrepRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withCommision");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WithCommision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commission");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Commission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commissionCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CommissionCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("madeOperationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MadeOperationId"));
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
