/**
 * DeptRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Реквизиты для оплаты задолженности ДЕПО
 */
public class DeptRec_Type  implements java.io.Serializable {
    /* БИК банка получателя */
    private java.lang.String BIC;

    /* Наименование банка получателя */
    private java.lang.String bankName;

    /* Корсчет банка получателя */
    private java.lang.String corBankAccount;

    /* Наименование получателя */
    private java.lang.String recipientName;

    /* ИНН получателя */
    private java.lang.String taxId;

    /* КПП получателя */
    private java.lang.String KPP;

    /* Расчетный счет получателя */
    private java.lang.String recipientAccount;

    public DeptRec_Type() {
    }

    public DeptRec_Type(
           java.lang.String BIC,
           java.lang.String bankName,
           java.lang.String corBankAccount,
           java.lang.String recipientName,
           java.lang.String taxId,
           java.lang.String KPP,
           java.lang.String recipientAccount) {
           this.BIC = BIC;
           this.bankName = bankName;
           this.corBankAccount = corBankAccount;
           this.recipientName = recipientName;
           this.taxId = taxId;
           this.KPP = KPP;
           this.recipientAccount = recipientAccount;
    }


    /**
     * Gets the BIC value for this DeptRec_Type.
     * 
     * @return BIC   * БИК банка получателя
     */
    public java.lang.String getBIC() {
        return BIC;
    }


    /**
     * Sets the BIC value for this DeptRec_Type.
     * 
     * @param BIC   * БИК банка получателя
     */
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }


    /**
     * Gets the bankName value for this DeptRec_Type.
     * 
     * @return bankName   * Наименование банка получателя
     */
    public java.lang.String getBankName() {
        return bankName;
    }


    /**
     * Sets the bankName value for this DeptRec_Type.
     * 
     * @param bankName   * Наименование банка получателя
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }


    /**
     * Gets the corBankAccount value for this DeptRec_Type.
     * 
     * @return corBankAccount   * Корсчет банка получателя
     */
    public java.lang.String getCorBankAccount() {
        return corBankAccount;
    }


    /**
     * Sets the corBankAccount value for this DeptRec_Type.
     * 
     * @param corBankAccount   * Корсчет банка получателя
     */
    public void setCorBankAccount(java.lang.String corBankAccount) {
        this.corBankAccount = corBankAccount;
    }


    /**
     * Gets the recipientName value for this DeptRec_Type.
     * 
     * @return recipientName   * Наименование получателя
     */
    public java.lang.String getRecipientName() {
        return recipientName;
    }


    /**
     * Sets the recipientName value for this DeptRec_Type.
     * 
     * @param recipientName   * Наименование получателя
     */
    public void setRecipientName(java.lang.String recipientName) {
        this.recipientName = recipientName;
    }


    /**
     * Gets the taxId value for this DeptRec_Type.
     * 
     * @return taxId   * ИНН получателя
     */
    public java.lang.String getTaxId() {
        return taxId;
    }


    /**
     * Sets the taxId value for this DeptRec_Type.
     * 
     * @param taxId   * ИНН получателя
     */
    public void setTaxId(java.lang.String taxId) {
        this.taxId = taxId;
    }


    /**
     * Gets the KPP value for this DeptRec_Type.
     * 
     * @return KPP   * КПП получателя
     */
    public java.lang.String getKPP() {
        return KPP;
    }


    /**
     * Sets the KPP value for this DeptRec_Type.
     * 
     * @param KPP   * КПП получателя
     */
    public void setKPP(java.lang.String KPP) {
        this.KPP = KPP;
    }


    /**
     * Gets the recipientAccount value for this DeptRec_Type.
     * 
     * @return recipientAccount   * Расчетный счет получателя
     */
    public java.lang.String getRecipientAccount() {
        return recipientAccount;
    }


    /**
     * Sets the recipientAccount value for this DeptRec_Type.
     * 
     * @param recipientAccount   * Расчетный счет получателя
     */
    public void setRecipientAccount(java.lang.String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeptRec_Type)) return false;
        DeptRec_Type other = (DeptRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.BIC==null && other.getBIC()==null) || 
             (this.BIC!=null &&
              this.BIC.equals(other.getBIC()))) &&
            ((this.bankName==null && other.getBankName()==null) || 
             (this.bankName!=null &&
              this.bankName.equals(other.getBankName()))) &&
            ((this.corBankAccount==null && other.getCorBankAccount()==null) || 
             (this.corBankAccount!=null &&
              this.corBankAccount.equals(other.getCorBankAccount()))) &&
            ((this.recipientName==null && other.getRecipientName()==null) || 
             (this.recipientName!=null &&
              this.recipientName.equals(other.getRecipientName()))) &&
            ((this.taxId==null && other.getTaxId()==null) || 
             (this.taxId!=null &&
              this.taxId.equals(other.getTaxId()))) &&
            ((this.KPP==null && other.getKPP()==null) || 
             (this.KPP!=null &&
              this.KPP.equals(other.getKPP()))) &&
            ((this.recipientAccount==null && other.getRecipientAccount()==null) || 
             (this.recipientAccount!=null &&
              this.recipientAccount.equals(other.getRecipientAccount())));
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
        if (getBIC() != null) {
            _hashCode += getBIC().hashCode();
        }
        if (getBankName() != null) {
            _hashCode += getBankName().hashCode();
        }
        if (getCorBankAccount() != null) {
            _hashCode += getCorBankAccount().hashCode();
        }
        if (getRecipientName() != null) {
            _hashCode += getRecipientName().hashCode();
        }
        if (getTaxId() != null) {
            _hashCode += getTaxId().hashCode();
        }
        if (getKPP() != null) {
            _hashCode += getKPP().hashCode();
        }
        if (getRecipientAccount() != null) {
            _hashCode += getRecipientAccount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeptRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corBankAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorBankAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KPP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "KPP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientAccount"));
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
