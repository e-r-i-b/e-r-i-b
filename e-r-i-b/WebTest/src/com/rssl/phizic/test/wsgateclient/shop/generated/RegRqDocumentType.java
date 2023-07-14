/**
 * RegRqDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class RegRqDocumentType  implements java.io.Serializable {
    private java.lang.String id;

    private java.math.BigDecimal amount;

    private com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur;

    private java.lang.String desc;

    private java.lang.String printDesc;

    private java.lang.String account;

    private java.lang.String correspondent;

    private java.lang.String recipient;

    private java.lang.String taxId;

    private java.lang.String KPP;

    private java.lang.String recipientName;

    private com.rssl.phizic.test.wsgateclient.shop.generated.FieldsType fields;

    private java.lang.String backUrl;

    public RegRqDocumentType() {
    }

    public RegRqDocumentType(
           java.lang.String id,
           java.math.BigDecimal amount,
           com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur,
           java.lang.String desc,
           java.lang.String printDesc,
           java.lang.String account,
           java.lang.String correspondent,
           java.lang.String recipient,
           java.lang.String taxId,
           java.lang.String KPP,
           java.lang.String recipientName,
           com.rssl.phizic.test.wsgateclient.shop.generated.FieldsType fields,
           java.lang.String backUrl) {
           this.id = id;
           this.amount = amount;
           this.amountCur = amountCur;
           this.desc = desc;
           this.printDesc = printDesc;
           this.account = account;
           this.correspondent = correspondent;
           this.recipient = recipient;
           this.taxId = taxId;
           this.KPP = KPP;
           this.recipientName = recipientName;
           this.fields = fields;
           this.backUrl = backUrl;
    }


    /**
     * Gets the id value for this RegRqDocumentType.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this RegRqDocumentType.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the amount value for this RegRqDocumentType.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this RegRqDocumentType.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the amountCur value for this RegRqDocumentType.
     * 
     * @return amountCur
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType getAmountCur() {
        return amountCur;
    }


    /**
     * Sets the amountCur value for this RegRqDocumentType.
     * 
     * @param amountCur
     */
    public void setAmountCur(com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur) {
        this.amountCur = amountCur;
    }


    /**
     * Gets the desc value for this RegRqDocumentType.
     * 
     * @return desc
     */
    public java.lang.String getDesc() {
        return desc;
    }


    /**
     * Sets the desc value for this RegRqDocumentType.
     * 
     * @param desc
     */
    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }


    /**
     * Gets the printDesc value for this RegRqDocumentType.
     * 
     * @return printDesc
     */
    public java.lang.String getPrintDesc() {
        return printDesc;
    }


    /**
     * Sets the printDesc value for this RegRqDocumentType.
     * 
     * @param printDesc
     */
    public void setPrintDesc(java.lang.String printDesc) {
        this.printDesc = printDesc;
    }


    /**
     * Gets the account value for this RegRqDocumentType.
     * 
     * @return account
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this RegRqDocumentType.
     * 
     * @param account
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the correspondent value for this RegRqDocumentType.
     * 
     * @return correspondent
     */
    public java.lang.String getCorrespondent() {
        return correspondent;
    }


    /**
     * Sets the correspondent value for this RegRqDocumentType.
     * 
     * @param correspondent
     */
    public void setCorrespondent(java.lang.String correspondent) {
        this.correspondent = correspondent;
    }


    /**
     * Gets the recipient value for this RegRqDocumentType.
     * 
     * @return recipient
     */
    public java.lang.String getRecipient() {
        return recipient;
    }


    /**
     * Sets the recipient value for this RegRqDocumentType.
     * 
     * @param recipient
     */
    public void setRecipient(java.lang.String recipient) {
        this.recipient = recipient;
    }


    /**
     * Gets the taxId value for this RegRqDocumentType.
     * 
     * @return taxId
     */
    public java.lang.String getTaxId() {
        return taxId;
    }


    /**
     * Sets the taxId value for this RegRqDocumentType.
     * 
     * @param taxId
     */
    public void setTaxId(java.lang.String taxId) {
        this.taxId = taxId;
    }


    /**
     * Gets the KPP value for this RegRqDocumentType.
     * 
     * @return KPP
     */
    public java.lang.String getKPP() {
        return KPP;
    }


    /**
     * Sets the KPP value for this RegRqDocumentType.
     * 
     * @param KPP
     */
    public void setKPP(java.lang.String KPP) {
        this.KPP = KPP;
    }


    /**
     * Gets the recipientName value for this RegRqDocumentType.
     * 
     * @return recipientName
     */
    public java.lang.String getRecipientName() {
        return recipientName;
    }


    /**
     * Sets the recipientName value for this RegRqDocumentType.
     * 
     * @param recipientName
     */
    public void setRecipientName(java.lang.String recipientName) {
        this.recipientName = recipientName;
    }


    /**
     * Gets the fields value for this RegRqDocumentType.
     * 
     * @return fields
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.FieldsType getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this RegRqDocumentType.
     * 
     * @param fields
     */
    public void setFields(com.rssl.phizic.test.wsgateclient.shop.generated.FieldsType fields) {
        this.fields = fields;
    }


    /**
     * Gets the backUrl value for this RegRqDocumentType.
     * 
     * @return backUrl
     */
    public java.lang.String getBackUrl() {
        return backUrl;
    }


    /**
     * Sets the backUrl value for this RegRqDocumentType.
     * 
     * @param backUrl
     */
    public void setBackUrl(java.lang.String backUrl) {
        this.backUrl = backUrl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegRqDocumentType)) return false;
        RegRqDocumentType other = (RegRqDocumentType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.amountCur==null && other.getAmountCur()==null) || 
             (this.amountCur!=null &&
              this.amountCur.equals(other.getAmountCur()))) &&
            ((this.desc==null && other.getDesc()==null) || 
             (this.desc!=null &&
              this.desc.equals(other.getDesc()))) &&
            ((this.printDesc==null && other.getPrintDesc()==null) || 
             (this.printDesc!=null &&
              this.printDesc.equals(other.getPrintDesc()))) &&
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            ((this.correspondent==null && other.getCorrespondent()==null) || 
             (this.correspondent!=null &&
              this.correspondent.equals(other.getCorrespondent()))) &&
            ((this.recipient==null && other.getRecipient()==null) || 
             (this.recipient!=null &&
              this.recipient.equals(other.getRecipient()))) &&
            ((this.taxId==null && other.getTaxId()==null) || 
             (this.taxId!=null &&
              this.taxId.equals(other.getTaxId()))) &&
            ((this.KPP==null && other.getKPP()==null) || 
             (this.KPP!=null &&
              this.KPP.equals(other.getKPP()))) &&
            ((this.recipientName==null && other.getRecipientName()==null) || 
             (this.recipientName!=null &&
              this.recipientName.equals(other.getRecipientName()))) &&
            ((this.fields==null && other.getFields()==null) || 
             (this.fields!=null &&
              this.fields.equals(other.getFields()))) &&
            ((this.backUrl==null && other.getBackUrl()==null) || 
             (this.backUrl!=null &&
              this.backUrl.equals(other.getBackUrl())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getAmountCur() != null) {
            _hashCode += getAmountCur().hashCode();
        }
        if (getDesc() != null) {
            _hashCode += getDesc().hashCode();
        }
        if (getPrintDesc() != null) {
            _hashCode += getPrintDesc().hashCode();
        }
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getCorrespondent() != null) {
            _hashCode += getCorrespondent().hashCode();
        }
        if (getRecipient() != null) {
            _hashCode += getRecipient().hashCode();
        }
        if (getTaxId() != null) {
            _hashCode += getTaxId().hashCode();
        }
        if (getKPP() != null) {
            _hashCode += getKPP().hashCode();
        }
        if (getRecipientName() != null) {
            _hashCode += getRecipientName().hashCode();
        }
        if (getFields() != null) {
            _hashCode += getFields().hashCode();
        }
        if (getBackUrl() != null) {
            _hashCode += getBackUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegRqDocumentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "RegRqDocumentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "String255Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DecimalType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "AmountCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "CurrencyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Desc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "String255Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "PrintDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correspondent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Correspondent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Recipient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TaxId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KPP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "KPP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "RecipientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "FieldsType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("backUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "BackUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
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
