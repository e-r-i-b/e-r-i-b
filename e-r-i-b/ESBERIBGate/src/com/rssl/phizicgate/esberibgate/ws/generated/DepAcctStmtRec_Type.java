/**
 * DepAcctStmtRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись о выписке
 */
public class DepAcctStmtRec_Type  implements java.io.Serializable {
    private java.lang.String effDate;

    /* Номер документа */
    private java.lang.String documentNumber;

    /* Наименование операции. На русском языке, готовое для показал
     * пользователю. */
    private java.lang.String name;

    /* Вид операции */
    private java.lang.String aspect;

    /* Расходная операция. True – операция зачисления, False – операция
     * списания. */
    private java.lang.Boolean isDebit;

    /* Сумма операции */
    private java.math.BigDecimal amount;

    /* Валюта суммы операции */
    private java.lang.String amountCur;

    /* Корреспондирующий счет */
    private java.lang.String correspondent;

    /* Остаток после операции */
    private java.math.BigDecimal balance;

    /* Валюта остатка после операции */
    private java.lang.String balanceCur;

    /* Текстовое наименование получателя или плательщика. Текст на
     * русском языке, готовый для отобображения пользователю. */
    private java.lang.String recipient;

    /* Номер счета получателя или плательщика */
    private java.lang.String recipientAccount;

    /* БИК банка получателя или плательщика */
    private java.lang.String recipientBIC;

    /* Назначение платежа */
    private java.lang.String destination;

    public DepAcctStmtRec_Type() {
    }

    public DepAcctStmtRec_Type(
           java.lang.String effDate,
           java.lang.String documentNumber,
           java.lang.String name,
           java.lang.String aspect,
           java.lang.Boolean isDebit,
           java.math.BigDecimal amount,
           java.lang.String amountCur,
           java.lang.String correspondent,
           java.math.BigDecimal balance,
           java.lang.String balanceCur,
           java.lang.String recipient,
           java.lang.String recipientAccount,
           java.lang.String recipientBIC,
           java.lang.String destination) {
           this.effDate = effDate;
           this.documentNumber = documentNumber;
           this.name = name;
           this.aspect = aspect;
           this.isDebit = isDebit;
           this.amount = amount;
           this.amountCur = amountCur;
           this.correspondent = correspondent;
           this.balance = balance;
           this.balanceCur = balanceCur;
           this.recipient = recipient;
           this.recipientAccount = recipientAccount;
           this.recipientBIC = recipientBIC;
           this.destination = destination;
    }


    /**
     * Gets the effDate value for this DepAcctStmtRec_Type.
     * 
     * @return effDate
     */
    public java.lang.String getEffDate() {
        return effDate;
    }


    /**
     * Sets the effDate value for this DepAcctStmtRec_Type.
     * 
     * @param effDate
     */
    public void setEffDate(java.lang.String effDate) {
        this.effDate = effDate;
    }


    /**
     * Gets the documentNumber value for this DepAcctStmtRec_Type.
     * 
     * @return documentNumber   * Номер документа
     */
    public java.lang.String getDocumentNumber() {
        return documentNumber;
    }


    /**
     * Sets the documentNumber value for this DepAcctStmtRec_Type.
     * 
     * @param documentNumber   * Номер документа
     */
    public void setDocumentNumber(java.lang.String documentNumber) {
        this.documentNumber = documentNumber;
    }


    /**
     * Gets the name value for this DepAcctStmtRec_Type.
     * 
     * @return name   * Наименование операции. На русском языке, готовое для показал
     * пользователю.
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this DepAcctStmtRec_Type.
     * 
     * @param name   * Наименование операции. На русском языке, готовое для показал
     * пользователю.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the aspect value for this DepAcctStmtRec_Type.
     * 
     * @return aspect   * Вид операции
     */
    public java.lang.String getAspect() {
        return aspect;
    }


    /**
     * Sets the aspect value for this DepAcctStmtRec_Type.
     * 
     * @param aspect   * Вид операции
     */
    public void setAspect(java.lang.String aspect) {
        this.aspect = aspect;
    }


    /**
     * Gets the isDebit value for this DepAcctStmtRec_Type.
     * 
     * @return isDebit   * Расходная операция. True – операция зачисления, False – операция
     * списания.
     */
    public java.lang.Boolean getIsDebit() {
        return isDebit;
    }


    /**
     * Sets the isDebit value for this DepAcctStmtRec_Type.
     * 
     * @param isDebit   * Расходная операция. True – операция зачисления, False – операция
     * списания.
     */
    public void setIsDebit(java.lang.Boolean isDebit) {
        this.isDebit = isDebit;
    }


    /**
     * Gets the amount value for this DepAcctStmtRec_Type.
     * 
     * @return amount   * Сумма операции
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this DepAcctStmtRec_Type.
     * 
     * @param amount   * Сумма операции
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the amountCur value for this DepAcctStmtRec_Type.
     * 
     * @return amountCur   * Валюта суммы операции
     */
    public java.lang.String getAmountCur() {
        return amountCur;
    }


    /**
     * Sets the amountCur value for this DepAcctStmtRec_Type.
     * 
     * @param amountCur   * Валюта суммы операции
     */
    public void setAmountCur(java.lang.String amountCur) {
        this.amountCur = amountCur;
    }


    /**
     * Gets the correspondent value for this DepAcctStmtRec_Type.
     * 
     * @return correspondent   * Корреспондирующий счет
     */
    public java.lang.String getCorrespondent() {
        return correspondent;
    }


    /**
     * Sets the correspondent value for this DepAcctStmtRec_Type.
     * 
     * @param correspondent   * Корреспондирующий счет
     */
    public void setCorrespondent(java.lang.String correspondent) {
        this.correspondent = correspondent;
    }


    /**
     * Gets the balance value for this DepAcctStmtRec_Type.
     * 
     * @return balance   * Остаток после операции
     */
    public java.math.BigDecimal getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this DepAcctStmtRec_Type.
     * 
     * @param balance   * Остаток после операции
     */
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * Gets the balanceCur value for this DepAcctStmtRec_Type.
     * 
     * @return balanceCur   * Валюта остатка после операции
     */
    public java.lang.String getBalanceCur() {
        return balanceCur;
    }


    /**
     * Sets the balanceCur value for this DepAcctStmtRec_Type.
     * 
     * @param balanceCur   * Валюта остатка после операции
     */
    public void setBalanceCur(java.lang.String balanceCur) {
        this.balanceCur = balanceCur;
    }


    /**
     * Gets the recipient value for this DepAcctStmtRec_Type.
     * 
     * @return recipient   * Текстовое наименование получателя или плательщика. Текст на
     * русском языке, готовый для отобображения пользователю.
     */
    public java.lang.String getRecipient() {
        return recipient;
    }


    /**
     * Sets the recipient value for this DepAcctStmtRec_Type.
     * 
     * @param recipient   * Текстовое наименование получателя или плательщика. Текст на
     * русском языке, готовый для отобображения пользователю.
     */
    public void setRecipient(java.lang.String recipient) {
        this.recipient = recipient;
    }


    /**
     * Gets the recipientAccount value for this DepAcctStmtRec_Type.
     * 
     * @return recipientAccount   * Номер счета получателя или плательщика
     */
    public java.lang.String getRecipientAccount() {
        return recipientAccount;
    }


    /**
     * Sets the recipientAccount value for this DepAcctStmtRec_Type.
     * 
     * @param recipientAccount   * Номер счета получателя или плательщика
     */
    public void setRecipientAccount(java.lang.String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }


    /**
     * Gets the recipientBIC value for this DepAcctStmtRec_Type.
     * 
     * @return recipientBIC   * БИК банка получателя или плательщика
     */
    public java.lang.String getRecipientBIC() {
        return recipientBIC;
    }


    /**
     * Sets the recipientBIC value for this DepAcctStmtRec_Type.
     * 
     * @param recipientBIC   * БИК банка получателя или плательщика
     */
    public void setRecipientBIC(java.lang.String recipientBIC) {
        this.recipientBIC = recipientBIC;
    }


    /**
     * Gets the destination value for this DepAcctStmtRec_Type.
     * 
     * @return destination   * Назначение платежа
     */
    public java.lang.String getDestination() {
        return destination;
    }


    /**
     * Sets the destination value for this DepAcctStmtRec_Type.
     * 
     * @param destination   * Назначение платежа
     */
    public void setDestination(java.lang.String destination) {
        this.destination = destination;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAcctStmtRec_Type)) return false;
        DepAcctStmtRec_Type other = (DepAcctStmtRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.effDate==null && other.getEffDate()==null) || 
             (this.effDate!=null &&
              this.effDate.equals(other.getEffDate()))) &&
            ((this.documentNumber==null && other.getDocumentNumber()==null) || 
             (this.documentNumber!=null &&
              this.documentNumber.equals(other.getDocumentNumber()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.aspect==null && other.getAspect()==null) || 
             (this.aspect!=null &&
              this.aspect.equals(other.getAspect()))) &&
            ((this.isDebit==null && other.getIsDebit()==null) || 
             (this.isDebit!=null &&
              this.isDebit.equals(other.getIsDebit()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.amountCur==null && other.getAmountCur()==null) || 
             (this.amountCur!=null &&
              this.amountCur.equals(other.getAmountCur()))) &&
            ((this.correspondent==null && other.getCorrespondent()==null) || 
             (this.correspondent!=null &&
              this.correspondent.equals(other.getCorrespondent()))) &&
            ((this.balance==null && other.getBalance()==null) || 
             (this.balance!=null &&
              this.balance.equals(other.getBalance()))) &&
            ((this.balanceCur==null && other.getBalanceCur()==null) || 
             (this.balanceCur!=null &&
              this.balanceCur.equals(other.getBalanceCur()))) &&
            ((this.recipient==null && other.getRecipient()==null) || 
             (this.recipient!=null &&
              this.recipient.equals(other.getRecipient()))) &&
            ((this.recipientAccount==null && other.getRecipientAccount()==null) || 
             (this.recipientAccount!=null &&
              this.recipientAccount.equals(other.getRecipientAccount()))) &&
            ((this.recipientBIC==null && other.getRecipientBIC()==null) || 
             (this.recipientBIC!=null &&
              this.recipientBIC.equals(other.getRecipientBIC()))) &&
            ((this.destination==null && other.getDestination()==null) || 
             (this.destination!=null &&
              this.destination.equals(other.getDestination())));
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
        if (getEffDate() != null) {
            _hashCode += getEffDate().hashCode();
        }
        if (getDocumentNumber() != null) {
            _hashCode += getDocumentNumber().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getAspect() != null) {
            _hashCode += getAspect().hashCode();
        }
        if (getIsDebit() != null) {
            _hashCode += getIsDebit().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getAmountCur() != null) {
            _hashCode += getAmountCur().hashCode();
        }
        if (getCorrespondent() != null) {
            _hashCode += getCorrespondent().hashCode();
        }
        if (getBalance() != null) {
            _hashCode += getBalance().hashCode();
        }
        if (getBalanceCur() != null) {
            _hashCode += getBalanceCur().hashCode();
        }
        if (getRecipient() != null) {
            _hashCode += getRecipient().hashCode();
        }
        if (getRecipientAccount() != null) {
            _hashCode += getRecipientAccount().hashCode();
        }
        if (getRecipientBIC() != null) {
            _hashCode += getRecipientBIC().hashCode();
        }
        if (getDestination() != null) {
            _hashCode += getDestination().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepAcctStmtRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aspect");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Aspect"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDebit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsDebit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AmountCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correspondent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Correspondent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalanceCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Recipient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientBIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientBIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Destination"));
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
