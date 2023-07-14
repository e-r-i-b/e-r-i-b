/**
 * PrivateLoanInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class PrivateLoanInfo_Type  implements java.io.Serializable {
    private java.lang.String prodId;

    private java.math.BigDecimal recPayment;

    private java.lang.String paymentDate;

    public PrivateLoanInfo_Type() {
    }

    public PrivateLoanInfo_Type(
           java.lang.String prodId,
           java.math.BigDecimal recPayment,
           java.lang.String paymentDate) {
           this.prodId = prodId;
           this.recPayment = recPayment;
           this.paymentDate = paymentDate;
    }


    /**
     * Gets the prodId value for this PrivateLoanInfo_Type.
     * 
     * @return prodId
     */
    public java.lang.String getProdId() {
        return prodId;
    }


    /**
     * Sets the prodId value for this PrivateLoanInfo_Type.
     * 
     * @param prodId
     */
    public void setProdId(java.lang.String prodId) {
        this.prodId = prodId;
    }


    /**
     * Gets the recPayment value for this PrivateLoanInfo_Type.
     * 
     * @return recPayment
     */
    public java.math.BigDecimal getRecPayment() {
        return recPayment;
    }


    /**
     * Sets the recPayment value for this PrivateLoanInfo_Type.
     * 
     * @param recPayment
     */
    public void setRecPayment(java.math.BigDecimal recPayment) {
        this.recPayment = recPayment;
    }


    /**
     * Gets the paymentDate value for this PrivateLoanInfo_Type.
     * 
     * @return paymentDate
     */
    public java.lang.String getPaymentDate() {
        return paymentDate;
    }


    /**
     * Sets the paymentDate value for this PrivateLoanInfo_Type.
     * 
     * @param paymentDate
     */
    public void setPaymentDate(java.lang.String paymentDate) {
        this.paymentDate = paymentDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PrivateLoanInfo_Type)) return false;
        PrivateLoanInfo_Type other = (PrivateLoanInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.prodId==null && other.getProdId()==null) || 
             (this.prodId!=null &&
              this.prodId.equals(other.getProdId()))) &&
            ((this.recPayment==null && other.getRecPayment()==null) || 
             (this.recPayment!=null &&
              this.recPayment.equals(other.getRecPayment()))) &&
            ((this.paymentDate==null && other.getPaymentDate()==null) || 
             (this.paymentDate!=null &&
              this.paymentDate.equals(other.getPaymentDate())));
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
        if (getProdId() != null) {
            _hashCode += getProdId().hashCode();
        }
        if (getRecPayment() != null) {
            _hashCode += getRecPayment().hashCode();
        }
        if (getPaymentDate() != null) {
            _hashCode += getPaymentDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PrivateLoanInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateLoanInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentDate"));
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
