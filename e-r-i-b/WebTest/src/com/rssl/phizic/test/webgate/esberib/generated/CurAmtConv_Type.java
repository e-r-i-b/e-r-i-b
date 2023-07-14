/**
 * CurAmtConv_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class CurAmtConv_Type  implements java.io.Serializable {
    /* Курс продажи банком клиенту для валюты списания */
    private java.math.BigDecimal debet_sale;

    /* Курс покупки банком у клиента для валюты списания */
    private java.math.BigDecimal debet_buy;

    /* Курс ЦБ РФ для валюты списания */
    private java.math.BigDecimal debet_cb;

    /* Курс продажи банком клиенту для валюты зачисления */
    private java.math.BigDecimal credit_sale;

    /* Курс покупки банком у клиента для валюты зачисления */
    private java.math.BigDecimal credit_buy;

    /* Курс ЦБ РФ для валюты зачисления */
    private java.math.BigDecimal credit_cb;

    public CurAmtConv_Type() {
    }

    public CurAmtConv_Type(
           java.math.BigDecimal debet_sale,
           java.math.BigDecimal debet_buy,
           java.math.BigDecimal debet_cb,
           java.math.BigDecimal credit_sale,
           java.math.BigDecimal credit_buy,
           java.math.BigDecimal credit_cb) {
           this.debet_sale = debet_sale;
           this.debet_buy = debet_buy;
           this.debet_cb = debet_cb;
           this.credit_sale = credit_sale;
           this.credit_buy = credit_buy;
           this.credit_cb = credit_cb;
    }


    /**
     * Gets the debet_sale value for this CurAmtConv_Type.
     * 
     * @return debet_sale   * Курс продажи банком клиенту для валюты списания
     */
    public java.math.BigDecimal getDebet_sale() {
        return debet_sale;
    }


    /**
     * Sets the debet_sale value for this CurAmtConv_Type.
     * 
     * @param debet_sale   * Курс продажи банком клиенту для валюты списания
     */
    public void setDebet_sale(java.math.BigDecimal debet_sale) {
        this.debet_sale = debet_sale;
    }


    /**
     * Gets the debet_buy value for this CurAmtConv_Type.
     * 
     * @return debet_buy   * Курс покупки банком у клиента для валюты списания
     */
    public java.math.BigDecimal getDebet_buy() {
        return debet_buy;
    }


    /**
     * Sets the debet_buy value for this CurAmtConv_Type.
     * 
     * @param debet_buy   * Курс покупки банком у клиента для валюты списания
     */
    public void setDebet_buy(java.math.BigDecimal debet_buy) {
        this.debet_buy = debet_buy;
    }


    /**
     * Gets the debet_cb value for this CurAmtConv_Type.
     * 
     * @return debet_cb   * Курс ЦБ РФ для валюты списания
     */
    public java.math.BigDecimal getDebet_cb() {
        return debet_cb;
    }


    /**
     * Sets the debet_cb value for this CurAmtConv_Type.
     * 
     * @param debet_cb   * Курс ЦБ РФ для валюты списания
     */
    public void setDebet_cb(java.math.BigDecimal debet_cb) {
        this.debet_cb = debet_cb;
    }


    /**
     * Gets the credit_sale value for this CurAmtConv_Type.
     * 
     * @return credit_sale   * Курс продажи банком клиенту для валюты зачисления
     */
    public java.math.BigDecimal getCredit_sale() {
        return credit_sale;
    }


    /**
     * Sets the credit_sale value for this CurAmtConv_Type.
     * 
     * @param credit_sale   * Курс продажи банком клиенту для валюты зачисления
     */
    public void setCredit_sale(java.math.BigDecimal credit_sale) {
        this.credit_sale = credit_sale;
    }


    /**
     * Gets the credit_buy value for this CurAmtConv_Type.
     * 
     * @return credit_buy   * Курс покупки банком у клиента для валюты зачисления
     */
    public java.math.BigDecimal getCredit_buy() {
        return credit_buy;
    }


    /**
     * Sets the credit_buy value for this CurAmtConv_Type.
     * 
     * @param credit_buy   * Курс покупки банком у клиента для валюты зачисления
     */
    public void setCredit_buy(java.math.BigDecimal credit_buy) {
        this.credit_buy = credit_buy;
    }


    /**
     * Gets the credit_cb value for this CurAmtConv_Type.
     * 
     * @return credit_cb   * Курс ЦБ РФ для валюты зачисления
     */
    public java.math.BigDecimal getCredit_cb() {
        return credit_cb;
    }


    /**
     * Sets the credit_cb value for this CurAmtConv_Type.
     * 
     * @param credit_cb   * Курс ЦБ РФ для валюты зачисления
     */
    public void setCredit_cb(java.math.BigDecimal credit_cb) {
        this.credit_cb = credit_cb;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CurAmtConv_Type)) return false;
        CurAmtConv_Type other = (CurAmtConv_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.debet_sale==null && other.getDebet_sale()==null) || 
             (this.debet_sale!=null &&
              this.debet_sale.equals(other.getDebet_sale()))) &&
            ((this.debet_buy==null && other.getDebet_buy()==null) || 
             (this.debet_buy!=null &&
              this.debet_buy.equals(other.getDebet_buy()))) &&
            ((this.debet_cb==null && other.getDebet_cb()==null) || 
             (this.debet_cb!=null &&
              this.debet_cb.equals(other.getDebet_cb()))) &&
            ((this.credit_sale==null && other.getCredit_sale()==null) || 
             (this.credit_sale!=null &&
              this.credit_sale.equals(other.getCredit_sale()))) &&
            ((this.credit_buy==null && other.getCredit_buy()==null) || 
             (this.credit_buy!=null &&
              this.credit_buy.equals(other.getCredit_buy()))) &&
            ((this.credit_cb==null && other.getCredit_cb()==null) || 
             (this.credit_cb!=null &&
              this.credit_cb.equals(other.getCredit_cb())));
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
        if (getDebet_sale() != null) {
            _hashCode += getDebet_sale().hashCode();
        }
        if (getDebet_buy() != null) {
            _hashCode += getDebet_buy().hashCode();
        }
        if (getDebet_cb() != null) {
            _hashCode += getDebet_cb().hashCode();
        }
        if (getCredit_sale() != null) {
            _hashCode += getCredit_sale().hashCode();
        }
        if (getCredit_buy() != null) {
            _hashCode += getCredit_buy().hashCode();
        }
        if (getCredit_cb() != null) {
            _hashCode += getCredit_cb().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CurAmtConv_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtConv_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debet_sale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "debet_sale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debet_buy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "debet_buy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debet_cb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "debet_cb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credit_sale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "credit_sale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credit_buy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "credit_buy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credit_cb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "credit_cb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
