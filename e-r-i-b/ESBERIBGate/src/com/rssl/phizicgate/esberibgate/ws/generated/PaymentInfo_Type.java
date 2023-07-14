/**
 * PaymentInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о платеже
 */
public class PaymentInfo_Type  implements java.io.Serializable {
    /* Код состояния платежа */
    private com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatusASAP_Type paymentStatus;

    /* Текстовое описание состояния платежа */
    private java.lang.String paymentStatusDesc;

    /* Комиссия платежа в рублях */
    private java.math.BigDecimal commission;

    /* Код операции в биллинге */
    private java.lang.String madeOperationId;

    /* Информация об исполнении автоплатежа */
    private com.rssl.phizicgate.esberibgate.ws.generated.ExecStatus_Type execStatus;

    public PaymentInfo_Type() {
    }

    public PaymentInfo_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatusASAP_Type paymentStatus,
           java.lang.String paymentStatusDesc,
           java.math.BigDecimal commission,
           java.lang.String madeOperationId,
           com.rssl.phizicgate.esberibgate.ws.generated.ExecStatus_Type execStatus) {
           this.paymentStatus = paymentStatus;
           this.paymentStatusDesc = paymentStatusDesc;
           this.commission = commission;
           this.madeOperationId = madeOperationId;
           this.execStatus = execStatus;
    }


    /**
     * Gets the paymentStatus value for this PaymentInfo_Type.
     * 
     * @return paymentStatus   * Код состояния платежа
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatusASAP_Type getPaymentStatus() {
        return paymentStatus;
    }


    /**
     * Sets the paymentStatus value for this PaymentInfo_Type.
     * 
     * @param paymentStatus   * Код состояния платежа
     */
    public void setPaymentStatus(com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatusASAP_Type paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    /**
     * Gets the paymentStatusDesc value for this PaymentInfo_Type.
     * 
     * @return paymentStatusDesc   * Текстовое описание состояния платежа
     */
    public java.lang.String getPaymentStatusDesc() {
        return paymentStatusDesc;
    }


    /**
     * Sets the paymentStatusDesc value for this PaymentInfo_Type.
     * 
     * @param paymentStatusDesc   * Текстовое описание состояния платежа
     */
    public void setPaymentStatusDesc(java.lang.String paymentStatusDesc) {
        this.paymentStatusDesc = paymentStatusDesc;
    }


    /**
     * Gets the commission value for this PaymentInfo_Type.
     * 
     * @return commission   * Комиссия платежа в рублях
     */
    public java.math.BigDecimal getCommission() {
        return commission;
    }


    /**
     * Sets the commission value for this PaymentInfo_Type.
     * 
     * @param commission   * Комиссия платежа в рублях
     */
    public void setCommission(java.math.BigDecimal commission) {
        this.commission = commission;
    }


    /**
     * Gets the madeOperationId value for this PaymentInfo_Type.
     * 
     * @return madeOperationId   * Код операции в биллинге
     */
    public java.lang.String getMadeOperationId() {
        return madeOperationId;
    }


    /**
     * Sets the madeOperationId value for this PaymentInfo_Type.
     * 
     * @param madeOperationId   * Код операции в биллинге
     */
    public void setMadeOperationId(java.lang.String madeOperationId) {
        this.madeOperationId = madeOperationId;
    }


    /**
     * Gets the execStatus value for this PaymentInfo_Type.
     * 
     * @return execStatus   * Информация об исполнении автоплатежа
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ExecStatus_Type getExecStatus() {
        return execStatus;
    }


    /**
     * Sets the execStatus value for this PaymentInfo_Type.
     * 
     * @param execStatus   * Информация об исполнении автоплатежа
     */
    public void setExecStatus(com.rssl.phizicgate.esberibgate.ws.generated.ExecStatus_Type execStatus) {
        this.execStatus = execStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentInfo_Type)) return false;
        PaymentInfo_Type other = (PaymentInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.paymentStatus==null && other.getPaymentStatus()==null) || 
             (this.paymentStatus!=null &&
              this.paymentStatus.equals(other.getPaymentStatus()))) &&
            ((this.paymentStatusDesc==null && other.getPaymentStatusDesc()==null) || 
             (this.paymentStatusDesc!=null &&
              this.paymentStatusDesc.equals(other.getPaymentStatusDesc()))) &&
            ((this.commission==null && other.getCommission()==null) || 
             (this.commission!=null &&
              this.commission.equals(other.getCommission()))) &&
            ((this.madeOperationId==null && other.getMadeOperationId()==null) || 
             (this.madeOperationId!=null &&
              this.madeOperationId.equals(other.getMadeOperationId()))) &&
            ((this.execStatus==null && other.getExecStatus()==null) || 
             (this.execStatus!=null &&
              this.execStatus.equals(other.getExecStatus())));
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
        if (getPaymentStatus() != null) {
            _hashCode += getPaymentStatus().hashCode();
        }
        if (getPaymentStatusDesc() != null) {
            _hashCode += getPaymentStatusDesc().hashCode();
        }
        if (getCommission() != null) {
            _hashCode += getCommission().hashCode();
        }
        if (getMadeOperationId() != null) {
            _hashCode += getMadeOperationId().hashCode();
        }
        if (getExecStatus() != null) {
            _hashCode += getExecStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusASAP_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentStatusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commission");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Commission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("madeOperationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MadeOperationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("execStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExecStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExecStatus_Type"));
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
