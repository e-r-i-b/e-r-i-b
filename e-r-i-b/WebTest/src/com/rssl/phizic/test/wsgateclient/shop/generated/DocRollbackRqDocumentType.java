/**
 * DocRollbackRqDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class DocRollbackRqDocumentType  implements java.io.Serializable {
    private java.lang.String ERIBUID;

    private java.lang.String docRollbackId;

    private java.math.BigDecimal amount;

    private com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur;

    public DocRollbackRqDocumentType() {
    }

    public DocRollbackRqDocumentType(
           java.lang.String ERIBUID,
           java.lang.String docRollbackId,
           java.math.BigDecimal amount,
           com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur) {
           this.ERIBUID = ERIBUID;
           this.docRollbackId = docRollbackId;
           this.amount = amount;
           this.amountCur = amountCur;
    }


    /**
     * Gets the ERIBUID value for this DocRollbackRqDocumentType.
     * 
     * @return ERIBUID
     */
    public java.lang.String getERIBUID() {
        return ERIBUID;
    }


    /**
     * Sets the ERIBUID value for this DocRollbackRqDocumentType.
     * 
     * @param ERIBUID
     */
    public void setERIBUID(java.lang.String ERIBUID) {
        this.ERIBUID = ERIBUID;
    }


    /**
     * Gets the docRollbackId value for this DocRollbackRqDocumentType.
     * 
     * @return docRollbackId
     */
    public java.lang.String getDocRollbackId() {
        return docRollbackId;
    }


    /**
     * Sets the docRollbackId value for this DocRollbackRqDocumentType.
     * 
     * @param docRollbackId
     */
    public void setDocRollbackId(java.lang.String docRollbackId) {
        this.docRollbackId = docRollbackId;
    }


    /**
     * Gets the amount value for this DocRollbackRqDocumentType.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this DocRollbackRqDocumentType.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the amountCur value for this DocRollbackRqDocumentType.
     * 
     * @return amountCur
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType getAmountCur() {
        return amountCur;
    }


    /**
     * Sets the amountCur value for this DocRollbackRqDocumentType.
     * 
     * @param amountCur
     */
    public void setAmountCur(com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur) {
        this.amountCur = amountCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocRollbackRqDocumentType)) return false;
        DocRollbackRqDocumentType other = (DocRollbackRqDocumentType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ERIBUID==null && other.getERIBUID()==null) || 
             (this.ERIBUID!=null &&
              this.ERIBUID.equals(other.getERIBUID()))) &&
            ((this.docRollbackId==null && other.getDocRollbackId()==null) || 
             (this.docRollbackId!=null &&
              this.docRollbackId.equals(other.getDocRollbackId()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.amountCur==null && other.getAmountCur()==null) || 
             (this.amountCur!=null &&
              this.amountCur.equals(other.getAmountCur())));
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
        if (getERIBUID() != null) {
            _hashCode += getERIBUID().hashCode();
        }
        if (getDocRollbackId() != null) {
            _hashCode += getDocRollbackId().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getAmountCur() != null) {
            _hashCode += getAmountCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocRollbackRqDocumentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocRollbackRqDocumentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERIBUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "ERIBUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docRollbackId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocRollbackId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "AmountCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "CurrencyType"));
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
