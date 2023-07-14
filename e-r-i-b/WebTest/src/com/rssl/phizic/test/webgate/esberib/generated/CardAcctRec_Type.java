/**
 * CardAcctRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация по счету
 */
public class CardAcctRec_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.BankAcctStatus_Type bankAcctStatus;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctInfo_Type cardAcctInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal;

    /* Статус обработки запроса для данной карты (возвращается при
     * групповых операциях по картам, которые не поддерживаются бек-офисными
     * системами) */
    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    private com.rssl.phizic.test.webgate.esberib.generated.OTPRestriction_Type OTPRestriction;

    /* Информация о транзакционных ограничителях */
    private com.rssl.phizic.test.webgate.esberib.generated.UsageInfo_Type[] usageInfo;

    public CardAcctRec_Type() {
    }

    public CardAcctRec_Type(
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.BankAcctStatus_Type bankAcctStatus,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctInfo_Type cardAcctInfo,
           com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status,
           com.rssl.phizic.test.webgate.esberib.generated.OTPRestriction_Type OTPRestriction,
           com.rssl.phizic.test.webgate.esberib.generated.UsageInfo_Type[] usageInfo) {
           this.bankInfo = bankInfo;
           this.bankAcctStatus = bankAcctStatus;
           this.cardAcctId = cardAcctId;
           this.cardAcctInfo = cardAcctInfo;
           this.acctBal = acctBal;
           this.status = status;
           this.OTPRestriction = OTPRestriction;
           this.usageInfo = usageInfo;
    }


    /**
     * Gets the bankInfo value for this CardAcctRec_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this CardAcctRec_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the bankAcctStatus value for this CardAcctRec_Type.
     * 
     * @return bankAcctStatus
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctStatus_Type getBankAcctStatus() {
        return bankAcctStatus;
    }


    /**
     * Sets the bankAcctStatus value for this CardAcctRec_Type.
     * 
     * @param bankAcctStatus
     */
    public void setBankAcctStatus(com.rssl.phizic.test.webgate.esberib.generated.BankAcctStatus_Type bankAcctStatus) {
        this.bankAcctStatus = bankAcctStatus;
    }


    /**
     * Gets the cardAcctId value for this CardAcctRec_Type.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this CardAcctRec_Type.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the cardAcctInfo value for this CardAcctRec_Type.
     * 
     * @return cardAcctInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctInfo_Type getCardAcctInfo() {
        return cardAcctInfo;
    }


    /**
     * Sets the cardAcctInfo value for this CardAcctRec_Type.
     * 
     * @param cardAcctInfo
     */
    public void setCardAcctInfo(com.rssl.phizic.test.webgate.esberib.generated.CardAcctInfo_Type cardAcctInfo) {
        this.cardAcctInfo = cardAcctInfo;
    }


    /**
     * Gets the acctBal value for this CardAcctRec_Type.
     * 
     * @return acctBal
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this CardAcctRec_Type.
     * 
     * @param acctBal
     */
    public void setAcctBal(com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal) {
        this.acctBal = acctBal;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type getAcctBal(int i) {
        return this.acctBal[i];
    }

    public void setAcctBal(int i, com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type _value) {
        this.acctBal[i] = _value;
    }


    /**
     * Gets the status value for this CardAcctRec_Type.
     * 
     * @return status   * Статус обработки запроса для данной карты (возвращается при
     * групповых операциях по картам, которые не поддерживаются бек-офисными
     * системами)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CardAcctRec_Type.
     * 
     * @param status   * Статус обработки запроса для данной карты (возвращается при
     * групповых операциях по картам, которые не поддерживаются бек-офисными
     * системами)
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the OTPRestriction value for this CardAcctRec_Type.
     * 
     * @return OTPRestriction
     */
    public com.rssl.phizic.test.webgate.esberib.generated.OTPRestriction_Type getOTPRestriction() {
        return OTPRestriction;
    }


    /**
     * Sets the OTPRestriction value for this CardAcctRec_Type.
     * 
     * @param OTPRestriction
     */
    public void setOTPRestriction(com.rssl.phizic.test.webgate.esberib.generated.OTPRestriction_Type OTPRestriction) {
        this.OTPRestriction = OTPRestriction;
    }


    /**
     * Gets the usageInfo value for this CardAcctRec_Type.
     * 
     * @return usageInfo   * Информация о транзакционных ограничителях
     */
    public com.rssl.phizic.test.webgate.esberib.generated.UsageInfo_Type[] getUsageInfo() {
        return usageInfo;
    }


    /**
     * Sets the usageInfo value for this CardAcctRec_Type.
     * 
     * @param usageInfo   * Информация о транзакционных ограничителях
     */
    public void setUsageInfo(com.rssl.phizic.test.webgate.esberib.generated.UsageInfo_Type[] usageInfo) {
        this.usageInfo = usageInfo;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.UsageInfo_Type getUsageInfo(int i) {
        return this.usageInfo[i];
    }

    public void setUsageInfo(int i, com.rssl.phizic.test.webgate.esberib.generated.UsageInfo_Type _value) {
        this.usageInfo[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardAcctRec_Type)) return false;
        CardAcctRec_Type other = (CardAcctRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.bankAcctStatus==null && other.getBankAcctStatus()==null) || 
             (this.bankAcctStatus!=null &&
              this.bankAcctStatus.equals(other.getBankAcctStatus()))) &&
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.cardAcctInfo==null && other.getCardAcctInfo()==null) || 
             (this.cardAcctInfo!=null &&
              this.cardAcctInfo.equals(other.getCardAcctInfo()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              java.util.Arrays.equals(this.acctBal, other.getAcctBal()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.OTPRestriction==null && other.getOTPRestriction()==null) || 
             (this.OTPRestriction!=null &&
              this.OTPRestriction.equals(other.getOTPRestriction()))) &&
            ((this.usageInfo==null && other.getUsageInfo()==null) || 
             (this.usageInfo!=null &&
              java.util.Arrays.equals(this.usageInfo, other.getUsageInfo())));
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
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getBankAcctStatus() != null) {
            _hashCode += getBankAcctStatus().hashCode();
        }
        if (getCardAcctId() != null) {
            _hashCode += getCardAcctId().hashCode();
        }
        if (getCardAcctInfo() != null) {
            _hashCode += getCardAcctInfo().hashCode();
        }
        if (getAcctBal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcctBal());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcctBal(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getOTPRestriction() != null) {
            _hashCode += getOTPRestriction().hashCode();
        }
        if (getUsageInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsageInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsageInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardAcctRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStatus_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OTPRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPRestriction_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usageInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UsageInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UsageInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
