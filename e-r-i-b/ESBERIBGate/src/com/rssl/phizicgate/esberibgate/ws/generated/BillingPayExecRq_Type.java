/**
 * BillingPayExecRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запрос интерфейса TBP_PAY проводки билингового платежа
 */
public class BillingPayExecRq_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    /* Идентификатор операции в СБОЛ */
    private java.lang.String SBOLUID;

    /* Дата и время регистрации операции в СБОЛ */
    private java.lang.String SBOLTm;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    private java.lang.String systemId;

    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec;

    private java.math.BigDecimal commission;

    private java.lang.String commissionCur;

    /* Код операции в биллинге */
    private java.lang.String madeOperationId;

    /* Код авторизации транзакции WAY4 для повторного запроса */
    private java.lang.Long authorizationCode;

    /* Время авторизации транзакции WAY4 для повторного запроса */
    private java.lang.String authorizationDtTm;

    public BillingPayExecRq_Type() {
    }

    public BillingPayExecRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           java.lang.String SBOLUID,
           java.lang.String SBOLTm,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           java.lang.String systemId,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec,
           java.math.BigDecimal commission,
           java.lang.String commissionCur,
           java.lang.String madeOperationId,
           java.lang.Long authorizationCode,
           java.lang.String authorizationDtTm) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.SBOLUID = SBOLUID;
           this.SBOLTm = SBOLTm;
           this.bankInfo = bankInfo;
           this.systemId = systemId;
           this.cardAcctId = cardAcctId;
           this.recipientRec = recipientRec;
           this.commission = commission;
           this.commissionCur = commissionCur;
           this.madeOperationId = madeOperationId;
           this.authorizationCode = authorizationCode;
           this.authorizationDtTm = authorizationDtTm;
    }


    /**
     * Gets the rqUID value for this BillingPayExecRq_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this BillingPayExecRq_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this BillingPayExecRq_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this BillingPayExecRq_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this BillingPayExecRq_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this BillingPayExecRq_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this BillingPayExecRq_Type.
     * 
     * @return SPName
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this BillingPayExecRq_Type.
     * 
     * @param SPName
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the SBOLUID value for this BillingPayExecRq_Type.
     * 
     * @return SBOLUID   * Идентификатор операции в СБОЛ
     */
    public java.lang.String getSBOLUID() {
        return SBOLUID;
    }


    /**
     * Sets the SBOLUID value for this BillingPayExecRq_Type.
     * 
     * @param SBOLUID   * Идентификатор операции в СБОЛ
     */
    public void setSBOLUID(java.lang.String SBOLUID) {
        this.SBOLUID = SBOLUID;
    }


    /**
     * Gets the SBOLTm value for this BillingPayExecRq_Type.
     * 
     * @return SBOLTm   * Дата и время регистрации операции в СБОЛ
     */
    public java.lang.String getSBOLTm() {
        return SBOLTm;
    }


    /**
     * Sets the SBOLTm value for this BillingPayExecRq_Type.
     * 
     * @param SBOLTm   * Дата и время регистрации операции в СБОЛ
     */
    public void setSBOLTm(java.lang.String SBOLTm) {
        this.SBOLTm = SBOLTm;
    }


    /**
     * Gets the bankInfo value for this BillingPayExecRq_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this BillingPayExecRq_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the systemId value for this BillingPayExecRq_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this BillingPayExecRq_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the cardAcctId value for this BillingPayExecRq_Type.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this BillingPayExecRq_Type.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the recipientRec value for this BillingPayExecRq_Type.
     * 
     * @return recipientRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type getRecipientRec() {
        return recipientRec;
    }


    /**
     * Sets the recipientRec value for this BillingPayExecRq_Type.
     * 
     * @param recipientRec
     */
    public void setRecipientRec(com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec) {
        this.recipientRec = recipientRec;
    }


    /**
     * Gets the commission value for this BillingPayExecRq_Type.
     * 
     * @return commission
     */
    public java.math.BigDecimal getCommission() {
        return commission;
    }


    /**
     * Sets the commission value for this BillingPayExecRq_Type.
     * 
     * @param commission
     */
    public void setCommission(java.math.BigDecimal commission) {
        this.commission = commission;
    }


    /**
     * Gets the commissionCur value for this BillingPayExecRq_Type.
     * 
     * @return commissionCur
     */
    public java.lang.String getCommissionCur() {
        return commissionCur;
    }


    /**
     * Sets the commissionCur value for this BillingPayExecRq_Type.
     * 
     * @param commissionCur
     */
    public void setCommissionCur(java.lang.String commissionCur) {
        this.commissionCur = commissionCur;
    }


    /**
     * Gets the madeOperationId value for this BillingPayExecRq_Type.
     * 
     * @return madeOperationId   * Код операции в биллинге
     */
    public java.lang.String getMadeOperationId() {
        return madeOperationId;
    }


    /**
     * Sets the madeOperationId value for this BillingPayExecRq_Type.
     * 
     * @param madeOperationId   * Код операции в биллинге
     */
    public void setMadeOperationId(java.lang.String madeOperationId) {
        this.madeOperationId = madeOperationId;
    }


    /**
     * Gets the authorizationCode value for this BillingPayExecRq_Type.
     * 
     * @return authorizationCode   * Код авторизации транзакции WAY4 для повторного запроса
     */
    public java.lang.Long getAuthorizationCode() {
        return authorizationCode;
    }


    /**
     * Sets the authorizationCode value for this BillingPayExecRq_Type.
     * 
     * @param authorizationCode   * Код авторизации транзакции WAY4 для повторного запроса
     */
    public void setAuthorizationCode(java.lang.Long authorizationCode) {
        this.authorizationCode = authorizationCode;
    }


    /**
     * Gets the authorizationDtTm value for this BillingPayExecRq_Type.
     * 
     * @return authorizationDtTm   * Время авторизации транзакции WAY4 для повторного запроса
     */
    public java.lang.String getAuthorizationDtTm() {
        return authorizationDtTm;
    }


    /**
     * Sets the authorizationDtTm value for this BillingPayExecRq_Type.
     * 
     * @param authorizationDtTm   * Время авторизации транзакции WAY4 для повторного запроса
     */
    public void setAuthorizationDtTm(java.lang.String authorizationDtTm) {
        this.authorizationDtTm = authorizationDtTm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BillingPayExecRq_Type)) return false;
        BillingPayExecRq_Type other = (BillingPayExecRq_Type) obj;
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
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            ((this.SBOLUID==null && other.getSBOLUID()==null) || 
             (this.SBOLUID!=null &&
              this.SBOLUID.equals(other.getSBOLUID()))) &&
            ((this.SBOLTm==null && other.getSBOLTm()==null) || 
             (this.SBOLTm!=null &&
              this.SBOLTm.equals(other.getSBOLTm()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.recipientRec==null && other.getRecipientRec()==null) || 
             (this.recipientRec!=null &&
              this.recipientRec.equals(other.getRecipientRec()))) &&
            ((this.commission==null && other.getCommission()==null) || 
             (this.commission!=null &&
              this.commission.equals(other.getCommission()))) &&
            ((this.commissionCur==null && other.getCommissionCur()==null) || 
             (this.commissionCur!=null &&
              this.commissionCur.equals(other.getCommissionCur()))) &&
            ((this.madeOperationId==null && other.getMadeOperationId()==null) || 
             (this.madeOperationId!=null &&
              this.madeOperationId.equals(other.getMadeOperationId()))) &&
            ((this.authorizationCode==null && other.getAuthorizationCode()==null) || 
             (this.authorizationCode!=null &&
              this.authorizationCode.equals(other.getAuthorizationCode()))) &&
            ((this.authorizationDtTm==null && other.getAuthorizationDtTm()==null) || 
             (this.authorizationDtTm!=null &&
              this.authorizationDtTm.equals(other.getAuthorizationDtTm())));
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
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        if (getSBOLUID() != null) {
            _hashCode += getSBOLUID().hashCode();
        }
        if (getSBOLTm() != null) {
            _hashCode += getSBOLTm().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getCardAcctId() != null) {
            _hashCode += getCardAcctId().hashCode();
        }
        if (getRecipientRec() != null) {
            _hashCode += getRecipientRec().hashCode();
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
        if (getAuthorizationCode() != null) {
            _hashCode += getAuthorizationCode().hashCode();
        }
        if (getAuthorizationDtTm() != null) {
            _hashCode += getAuthorizationDtTm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BillingPayExecRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayExecRq_Type"));
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
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SBOLUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SBOLUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SBOLTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SBOLTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commission");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Commission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commissionCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CommissionCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("madeOperationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MadeOperationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AuthorizationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationDtTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AuthorizationDtTm"));
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
