/**
 * AutoPaymentRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись с информацией об автоплатеже
 */
public class AutoPaymentRec_Type  implements java.io.Serializable {
    /* Идентификационная информация об автоплатеже */
    private com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type autoPaymentId;

    /* Информация по подписке */
    private com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionInfo_Type autoSubscriptionInfo;

    /* Информация об автоплатеже */
    private com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentInfo_Type autoPaymentInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec;

    /* Информация о платёжном средстве, с которого производилась оплата */
    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] bankAcctRec;

    /* Информация о карте зачисления */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctTo;

    /* Информация о счёте зачисления */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type depAcctIdTo;

    /* Информация об авторизации карты в Way4, если операция прошла
     * успешно */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization;

    /* Идентификационная информация о подписке */
    private com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionId_Type autoSubscriptionId;

    public AutoPaymentRec_Type() {
    }

    public AutoPaymentRec_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type autoPaymentId,
           com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionInfo_Type autoSubscriptionInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentInfo_Type autoPaymentInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec,
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] bankAcctRec,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctTo,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type depAcctIdTo,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization,
           com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionId_Type autoSubscriptionId) {
           this.autoPaymentId = autoPaymentId;
           this.autoSubscriptionInfo = autoSubscriptionInfo;
           this.autoPaymentInfo = autoPaymentInfo;
           this.recipientRec = recipientRec;
           this.bankAcctRec = bankAcctRec;
           this.cardAcctTo = cardAcctTo;
           this.depAcctIdTo = depAcctIdTo;
           this.cardAuthorization = cardAuthorization;
           this.autoSubscriptionId = autoSubscriptionId;
    }


    /**
     * Gets the autoPaymentId value for this AutoPaymentRec_Type.
     * 
     * @return autoPaymentId   * Идентификационная информация об автоплатеже
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type getAutoPaymentId() {
        return autoPaymentId;
    }


    /**
     * Sets the autoPaymentId value for this AutoPaymentRec_Type.
     * 
     * @param autoPaymentId   * Идентификационная информация об автоплатеже
     */
    public void setAutoPaymentId(com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentId_Type autoPaymentId) {
        this.autoPaymentId = autoPaymentId;
    }


    /**
     * Gets the autoSubscriptionInfo value for this AutoPaymentRec_Type.
     * 
     * @return autoSubscriptionInfo   * Информация по подписке
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionInfo_Type getAutoSubscriptionInfo() {
        return autoSubscriptionInfo;
    }


    /**
     * Sets the autoSubscriptionInfo value for this AutoPaymentRec_Type.
     * 
     * @param autoSubscriptionInfo   * Информация по подписке
     */
    public void setAutoSubscriptionInfo(com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionInfo_Type autoSubscriptionInfo) {
        this.autoSubscriptionInfo = autoSubscriptionInfo;
    }


    /**
     * Gets the autoPaymentInfo value for this AutoPaymentRec_Type.
     * 
     * @return autoPaymentInfo   * Информация об автоплатеже
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentInfo_Type getAutoPaymentInfo() {
        return autoPaymentInfo;
    }


    /**
     * Sets the autoPaymentInfo value for this AutoPaymentRec_Type.
     * 
     * @param autoPaymentInfo   * Информация об автоплатеже
     */
    public void setAutoPaymentInfo(com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentInfo_Type autoPaymentInfo) {
        this.autoPaymentInfo = autoPaymentInfo;
    }


    /**
     * Gets the recipientRec value for this AutoPaymentRec_Type.
     * 
     * @return recipientRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type getRecipientRec() {
        return recipientRec;
    }


    /**
     * Sets the recipientRec value for this AutoPaymentRec_Type.
     * 
     * @param recipientRec
     */
    public void setRecipientRec(com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type recipientRec) {
        this.recipientRec = recipientRec;
    }


    /**
     * Gets the bankAcctRec value for this AutoPaymentRec_Type.
     * 
     * @return bankAcctRec   * Информация о платёжном средстве, с которого производилась оплата
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] getBankAcctRec() {
        return bankAcctRec;
    }


    /**
     * Sets the bankAcctRec value for this AutoPaymentRec_Type.
     * 
     * @param bankAcctRec   * Информация о платёжном средстве, с которого производилась оплата
     */
    public void setBankAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] bankAcctRec) {
        this.bankAcctRec = bankAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type getBankAcctRec(int i) {
        return this.bankAcctRec[i];
    }

    public void setBankAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type _value) {
        this.bankAcctRec[i] = _value;
    }


    /**
     * Gets the cardAcctTo value for this AutoPaymentRec_Type.
     * 
     * @return cardAcctTo   * Информация о карте зачисления
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type getCardAcctTo() {
        return cardAcctTo;
    }


    /**
     * Sets the cardAcctTo value for this AutoPaymentRec_Type.
     * 
     * @param cardAcctTo   * Информация о карте зачисления
     */
    public void setCardAcctTo(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctTo) {
        this.cardAcctTo = cardAcctTo;
    }


    /**
     * Gets the depAcctIdTo value for this AutoPaymentRec_Type.
     * 
     * @return depAcctIdTo   * Информация о счёте зачисления
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type getDepAcctIdTo() {
        return depAcctIdTo;
    }


    /**
     * Sets the depAcctIdTo value for this AutoPaymentRec_Type.
     * 
     * @param depAcctIdTo   * Информация о счёте зачисления
     */
    public void setDepAcctIdTo(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type depAcctIdTo) {
        this.depAcctIdTo = depAcctIdTo;
    }


    /**
     * Gets the cardAuthorization value for this AutoPaymentRec_Type.
     * 
     * @return cardAuthorization   * Информация об авторизации карты в Way4, если операция прошла
     * успешно
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type getCardAuthorization() {
        return cardAuthorization;
    }


    /**
     * Sets the cardAuthorization value for this AutoPaymentRec_Type.
     * 
     * @param cardAuthorization   * Информация об авторизации карты в Way4, если операция прошла
     * успешно
     */
    public void setCardAuthorization(com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization) {
        this.cardAuthorization = cardAuthorization;
    }


    /**
     * Gets the autoSubscriptionId value for this AutoPaymentRec_Type.
     * 
     * @return autoSubscriptionId   * Идентификационная информация о подписке
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionId_Type getAutoSubscriptionId() {
        return autoSubscriptionId;
    }


    /**
     * Sets the autoSubscriptionId value for this AutoPaymentRec_Type.
     * 
     * @param autoSubscriptionId   * Идентификационная информация о подписке
     */
    public void setAutoSubscriptionId(com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionId_Type autoSubscriptionId) {
        this.autoSubscriptionId = autoSubscriptionId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoPaymentRec_Type)) return false;
        AutoPaymentRec_Type other = (AutoPaymentRec_Type) obj;
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
            ((this.autoSubscriptionInfo==null && other.getAutoSubscriptionInfo()==null) || 
             (this.autoSubscriptionInfo!=null &&
              this.autoSubscriptionInfo.equals(other.getAutoSubscriptionInfo()))) &&
            ((this.autoPaymentInfo==null && other.getAutoPaymentInfo()==null) || 
             (this.autoPaymentInfo!=null &&
              this.autoPaymentInfo.equals(other.getAutoPaymentInfo()))) &&
            ((this.recipientRec==null && other.getRecipientRec()==null) || 
             (this.recipientRec!=null &&
              this.recipientRec.equals(other.getRecipientRec()))) &&
            ((this.bankAcctRec==null && other.getBankAcctRec()==null) || 
             (this.bankAcctRec!=null &&
              java.util.Arrays.equals(this.bankAcctRec, other.getBankAcctRec()))) &&
            ((this.cardAcctTo==null && other.getCardAcctTo()==null) || 
             (this.cardAcctTo!=null &&
              this.cardAcctTo.equals(other.getCardAcctTo()))) &&
            ((this.depAcctIdTo==null && other.getDepAcctIdTo()==null) || 
             (this.depAcctIdTo!=null &&
              this.depAcctIdTo.equals(other.getDepAcctIdTo()))) &&
            ((this.cardAuthorization==null && other.getCardAuthorization()==null) || 
             (this.cardAuthorization!=null &&
              this.cardAuthorization.equals(other.getCardAuthorization()))) &&
            ((this.autoSubscriptionId==null && other.getAutoSubscriptionId()==null) || 
             (this.autoSubscriptionId!=null &&
              this.autoSubscriptionId.equals(other.getAutoSubscriptionId())));
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
        if (getAutoSubscriptionInfo() != null) {
            _hashCode += getAutoSubscriptionInfo().hashCode();
        }
        if (getAutoPaymentInfo() != null) {
            _hashCode += getAutoPaymentInfo().hashCode();
        }
        if (getRecipientRec() != null) {
            _hashCode += getRecipientRec().hashCode();
        }
        if (getBankAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAcctRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCardAcctTo() != null) {
            _hashCode += getCardAcctTo().hashCode();
        }
        if (getDepAcctIdTo() != null) {
            _hashCode += getDepAcctIdTo().hashCode();
        }
        if (getCardAuthorization() != null) {
            _hashCode += getCardAuthorization().hashCode();
        }
        if (getAutoSubscriptionId() != null) {
            _hashCode += getAutoSubscriptionId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoPaymentRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPaymentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoSubscriptionInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPaymentInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentInfo_Type"));
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
        elemField.setFieldName("bankAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAuthorization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoSubscriptionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId_Type"));
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
