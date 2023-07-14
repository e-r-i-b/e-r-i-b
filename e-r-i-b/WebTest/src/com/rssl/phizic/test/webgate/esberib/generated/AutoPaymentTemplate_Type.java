/**
 * AutoPaymentTemplate_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип Информация о шаблоне автоплатежа
 */
public class AutoPaymentTemplate_Type  implements java.io.Serializable {
    private java.lang.String systemId;

    private com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type recipientRec;

    private com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] bankAcctRec;

    /* Информация о карте зачисления */
    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctTo;

    /* Информация о счёте зачисления */
    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type depAcctIdTo;

    public AutoPaymentTemplate_Type() {
    }

    public AutoPaymentTemplate_Type(
           java.lang.String systemId,
           com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type recipientRec,
           com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] bankAcctRec,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctTo,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type depAcctIdTo) {
           this.systemId = systemId;
           this.recipientRec = recipientRec;
           this.bankAcctRec = bankAcctRec;
           this.cardAcctTo = cardAcctTo;
           this.depAcctIdTo = depAcctIdTo;
    }


    /**
     * Gets the systemId value for this AutoPaymentTemplate_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this AutoPaymentTemplate_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the recipientRec value for this AutoPaymentTemplate_Type.
     * 
     * @return recipientRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type getRecipientRec() {
        return recipientRec;
    }


    /**
     * Sets the recipientRec value for this AutoPaymentTemplate_Type.
     * 
     * @param recipientRec
     */
    public void setRecipientRec(com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type recipientRec) {
        this.recipientRec = recipientRec;
    }


    /**
     * Gets the bankAcctRec value for this AutoPaymentTemplate_Type.
     * 
     * @return bankAcctRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] getBankAcctRec() {
        return bankAcctRec;
    }


    /**
     * Sets the bankAcctRec value for this AutoPaymentTemplate_Type.
     * 
     * @param bankAcctRec
     */
    public void setBankAcctRec(com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] bankAcctRec) {
        this.bankAcctRec = bankAcctRec;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type getBankAcctRec(int i) {
        return this.bankAcctRec[i];
    }

    public void setBankAcctRec(int i, com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type _value) {
        this.bankAcctRec[i] = _value;
    }


    /**
     * Gets the cardAcctTo value for this AutoPaymentTemplate_Type.
     * 
     * @return cardAcctTo   * Информация о карте зачисления
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctTo() {
        return cardAcctTo;
    }


    /**
     * Sets the cardAcctTo value for this AutoPaymentTemplate_Type.
     * 
     * @param cardAcctTo   * Информация о карте зачисления
     */
    public void setCardAcctTo(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctTo) {
        this.cardAcctTo = cardAcctTo;
    }


    /**
     * Gets the depAcctIdTo value for this AutoPaymentTemplate_Type.
     * 
     * @return depAcctIdTo   * Информация о счёте зачисления
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getDepAcctIdTo() {
        return depAcctIdTo;
    }


    /**
     * Sets the depAcctIdTo value for this AutoPaymentTemplate_Type.
     * 
     * @param depAcctIdTo   * Информация о счёте зачисления
     */
    public void setDepAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type depAcctIdTo) {
        this.depAcctIdTo = depAcctIdTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoPaymentTemplate_Type)) return false;
        AutoPaymentTemplate_Type other = (AutoPaymentTemplate_Type) obj;
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
              this.depAcctIdTo.equals(other.getDepAcctIdTo())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoPaymentTemplate_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentTemplate_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
