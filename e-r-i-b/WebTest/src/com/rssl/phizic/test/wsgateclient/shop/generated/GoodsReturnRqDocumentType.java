/**
 * GoodsReturnRqDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class GoodsReturnRqDocumentType  implements java.io.Serializable {
    private java.lang.String ERIBUID;

    private java.lang.String goodsReturnId;

    private java.math.BigDecimal amount;

    private com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur;

    private com.rssl.phizic.test.wsgateclient.shop.generated.GoodsReturnRqDocumentFieldType fields;

    public GoodsReturnRqDocumentType() {
    }

    public GoodsReturnRqDocumentType(
           java.lang.String ERIBUID,
           java.lang.String goodsReturnId,
           java.math.BigDecimal amount,
           com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur,
           com.rssl.phizic.test.wsgateclient.shop.generated.GoodsReturnRqDocumentFieldType fields) {
           this.ERIBUID = ERIBUID;
           this.goodsReturnId = goodsReturnId;
           this.amount = amount;
           this.amountCur = amountCur;
           this.fields = fields;
    }


    /**
     * Gets the ERIBUID value for this GoodsReturnRqDocumentType.
     * 
     * @return ERIBUID
     */
    public java.lang.String getERIBUID() {
        return ERIBUID;
    }


    /**
     * Sets the ERIBUID value for this GoodsReturnRqDocumentType.
     * 
     * @param ERIBUID
     */
    public void setERIBUID(java.lang.String ERIBUID) {
        this.ERIBUID = ERIBUID;
    }


    /**
     * Gets the goodsReturnId value for this GoodsReturnRqDocumentType.
     * 
     * @return goodsReturnId
     */
    public java.lang.String getGoodsReturnId() {
        return goodsReturnId;
    }


    /**
     * Sets the goodsReturnId value for this GoodsReturnRqDocumentType.
     * 
     * @param goodsReturnId
     */
    public void setGoodsReturnId(java.lang.String goodsReturnId) {
        this.goodsReturnId = goodsReturnId;
    }


    /**
     * Gets the amount value for this GoodsReturnRqDocumentType.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this GoodsReturnRqDocumentType.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the amountCur value for this GoodsReturnRqDocumentType.
     * 
     * @return amountCur
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType getAmountCur() {
        return amountCur;
    }


    /**
     * Sets the amountCur value for this GoodsReturnRqDocumentType.
     * 
     * @param amountCur
     */
    public void setAmountCur(com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType amountCur) {
        this.amountCur = amountCur;
    }


    /**
     * Gets the fields value for this GoodsReturnRqDocumentType.
     * 
     * @return fields
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.GoodsReturnRqDocumentFieldType getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this GoodsReturnRqDocumentType.
     * 
     * @param fields
     */
    public void setFields(com.rssl.phizic.test.wsgateclient.shop.generated.GoodsReturnRqDocumentFieldType fields) {
        this.fields = fields;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GoodsReturnRqDocumentType)) return false;
        GoodsReturnRqDocumentType other = (GoodsReturnRqDocumentType) obj;
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
            ((this.goodsReturnId==null && other.getGoodsReturnId()==null) || 
             (this.goodsReturnId!=null &&
              this.goodsReturnId.equals(other.getGoodsReturnId()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.amountCur==null && other.getAmountCur()==null) || 
             (this.amountCur!=null &&
              this.amountCur.equals(other.getAmountCur()))) &&
            ((this.fields==null && other.getFields()==null) || 
             (this.fields!=null &&
              this.fields.equals(other.getFields())));
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
        if (getGoodsReturnId() != null) {
            _hashCode += getGoodsReturnId().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getAmountCur() != null) {
            _hashCode += getAmountCur().hashCode();
        }
        if (getFields() != null) {
            _hashCode += getFields().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GoodsReturnRqDocumentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "GoodsReturnRqDocumentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERIBUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "ERIBUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goodsReturnId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "GoodsReturnId"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "GoodsReturnRqDocumentFieldType"));
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
