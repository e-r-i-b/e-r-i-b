/**
 * PaymentList_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Список задолженностей по АП
 */
public class PaymentList_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type autoPaymentId;

    /* Информация о платеже */
    private com.rssl.phizicgate.esberibgate.ws.generated.PaymentInfo_Type paymentInfo;

    /* Информация о поставщике */
    private com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec;

    /* Информация о платёжном средстве, с которого производилась оплата */
    private com.rssl.phizicgate.esberibgate.ws.generated.PaymentList_TypeBankAcctRec bankAcctRec;

    /* Информация об авторизации карты в Way4, если операция прошла
     * успешно */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization;

    public PaymentList_Type() {
    }

    public PaymentList_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type autoPaymentId,
           com.rssl.phizicgate.esberibgate.ws.generated.PaymentInfo_Type paymentInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec,
           com.rssl.phizicgate.esberibgate.ws.generated.PaymentList_TypeBankAcctRec bankAcctRec,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization) {
           this.autoPaymentId = autoPaymentId;
           this.paymentInfo = paymentInfo;
           this.recipientRec = recipientRec;
           this.bankAcctRec = bankAcctRec;
           this.cardAuthorization = cardAuthorization;
    }


    /**
     * Gets the autoPaymentId value for this PaymentList_Type.
     * 
     * @return autoPaymentId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type getAutoPaymentId() {
        return autoPaymentId;
    }


    /**
     * Sets the autoPaymentId value for this PaymentList_Type.
     * 
     * @param autoPaymentId
     */
    public void setAutoPaymentId(com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type autoPaymentId) {
        this.autoPaymentId = autoPaymentId;
    }


    /**
     * Gets the paymentInfo value for this PaymentList_Type.
     * 
     * @return paymentInfo   * Информация о платеже
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PaymentInfo_Type getPaymentInfo() {
        return paymentInfo;
    }


    /**
     * Sets the paymentInfo value for this PaymentList_Type.
     * 
     * @param paymentInfo   * Информация о платеже
     */
    public void setPaymentInfo(com.rssl.phizicgate.esberibgate.ws.generated.PaymentInfo_Type paymentInfo) {
        this.paymentInfo = paymentInfo;
    }


    /**
     * Gets the recipientRec value for this PaymentList_Type.
     * 
     * @return recipientRec   * Информация о поставщике
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type getRecipientRec() {
        return recipientRec;
    }


    /**
     * Sets the recipientRec value for this PaymentList_Type.
     * 
     * @param recipientRec   * Информация о поставщике
     */
    public void setRecipientRec(com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec) {
        this.recipientRec = recipientRec;
    }


    /**
     * Gets the bankAcctRec value for this PaymentList_Type.
     * 
     * @return bankAcctRec   * Информация о платёжном средстве, с которого производилась оплата
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PaymentList_TypeBankAcctRec getBankAcctRec() {
        return bankAcctRec;
    }


    /**
     * Sets the bankAcctRec value for this PaymentList_Type.
     * 
     * @param bankAcctRec   * Информация о платёжном средстве, с которого производилась оплата
     */
    public void setBankAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.PaymentList_TypeBankAcctRec bankAcctRec) {
        this.bankAcctRec = bankAcctRec;
    }


    /**
     * Gets the cardAuthorization value for this PaymentList_Type.
     * 
     * @return cardAuthorization   * Информация об авторизации карты в Way4, если операция прошла
     * успешно
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type getCardAuthorization() {
        return cardAuthorization;
    }


    /**
     * Sets the cardAuthorization value for this PaymentList_Type.
     * 
     * @param cardAuthorization   * Информация об авторизации карты в Way4, если операция прошла
     * успешно
     */
    public void setCardAuthorization(com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization) {
        this.cardAuthorization = cardAuthorization;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentList_Type)) return false;
        PaymentList_Type other = (PaymentList_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.autoPaymentId==null && other.getAutoPaymentId()==null) || 
             (this.autoPaymentId!=null &&
              this.autoPaymentId.equals(other.getAutoPaymentId()))) &&
            ((this.paymentInfo==null && other.getPaymentInfo()==null) || 
             (this.paymentInfo!=null &&
              this.paymentInfo.equals(other.getPaymentInfo()))) &&
            ((this.recipientRec==null && other.getRecipientRec()==null) || 
             (this.recipientRec!=null &&
              this.recipientRec.equals(other.getRecipientRec()))) &&
            ((this.bankAcctRec==null && other.getBankAcctRec()==null) || 
             (this.bankAcctRec!=null &&
              this.bankAcctRec.equals(other.getBankAcctRec()))) &&
            ((this.cardAuthorization==null && other.getCardAuthorization()==null) || 
             (this.cardAuthorization!=null &&
              this.cardAuthorization.equals(other.getCardAuthorization())));
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
        if (getAutoPaymentId() != null) {
            _hashCode += getAutoPaymentId().hashCode();
        }
        if (getPaymentInfo() != null) {
            _hashCode += getPaymentInfo().hashCode();
        }
        if (getRecipientRec() != null) {
            _hashCode += getRecipientRec().hashCode();
        }
        if (getBankAcctRec() != null) {
            _hashCode += getBankAcctRec().hashCode();
        }
        if (getCardAuthorization() != null) {
            _hashCode += getCardAuthorization().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentList_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentList_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPaymentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PaymentList_Type>BankAcctRec"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAuthorization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization_Type"));
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
