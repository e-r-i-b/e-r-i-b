/**
 * AutoPaymentInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип Информация об автоплатеже
 */
public class AutoPaymentInfo_Type  implements java.io.Serializable {
    /* Код состояния платежа */
    private com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type paymentStatus;

    /* Текстовое описание состояния */
    private java.lang.String paymentStatusDesc;

    /* Сумма платежа в рублях */
    private java.math.BigDecimal curAmt;

    private java.lang.String acctCur;

    /* Комиссия платежа в рублях. Не заполняется, если платеж не был
     * проведен */
    private java.math.BigDecimal commission;

    /* Код операции в биллинге */
    private java.lang.String madeOperationId;

    /* Информация об исполнении автоплатежа */
    private com.rssl.phizic.test.webgate.esberib.generated.ExecStatus_Type execStatus;

    public AutoPaymentInfo_Type() {
    }

    public AutoPaymentInfo_Type(
           com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type paymentStatus,
           java.lang.String paymentStatusDesc,
           java.math.BigDecimal curAmt,
           java.lang.String acctCur,
           java.math.BigDecimal commission,
           java.lang.String madeOperationId,
           com.rssl.phizic.test.webgate.esberib.generated.ExecStatus_Type execStatus) {
           this.paymentStatus = paymentStatus;
           this.paymentStatusDesc = paymentStatusDesc;
           this.curAmt = curAmt;
           this.acctCur = acctCur;
           this.commission = commission;
           this.madeOperationId = madeOperationId;
           this.execStatus = execStatus;
    }


    /**
     * Gets the paymentStatus value for this AutoPaymentInfo_Type.
     * 
     * @return paymentStatus   * Код состояния платежа
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type getPaymentStatus() {
        return paymentStatus;
    }


    /**
     * Sets the paymentStatus value for this AutoPaymentInfo_Type.
     * 
     * @param paymentStatus   * Код состояния платежа
     */
    public void setPaymentStatus(com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    /**
     * Gets the paymentStatusDesc value for this AutoPaymentInfo_Type.
     * 
     * @return paymentStatusDesc   * Текстовое описание состояния
     */
    public java.lang.String getPaymentStatusDesc() {
        return paymentStatusDesc;
    }


    /**
     * Sets the paymentStatusDesc value for this AutoPaymentInfo_Type.
     * 
     * @param paymentStatusDesc   * Текстовое описание состояния
     */
    public void setPaymentStatusDesc(java.lang.String paymentStatusDesc) {
        this.paymentStatusDesc = paymentStatusDesc;
    }


    /**
     * Gets the curAmt value for this AutoPaymentInfo_Type.
     * 
     * @return curAmt   * Сумма платежа в рублях
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this AutoPaymentInfo_Type.
     * 
     * @param curAmt   * Сумма платежа в рублях
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the acctCur value for this AutoPaymentInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this AutoPaymentInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the commission value for this AutoPaymentInfo_Type.
     * 
     * @return commission   * Комиссия платежа в рублях. Не заполняется, если платеж не был
     * проведен
     */
    public java.math.BigDecimal getCommission() {
        return commission;
    }


    /**
     * Sets the commission value for this AutoPaymentInfo_Type.
     * 
     * @param commission   * Комиссия платежа в рублях. Не заполняется, если платеж не был
     * проведен
     */
    public void setCommission(java.math.BigDecimal commission) {
        this.commission = commission;
    }


    /**
     * Gets the madeOperationId value for this AutoPaymentInfo_Type.
     * 
     * @return madeOperationId   * Код операции в биллинге
     */
    public java.lang.String getMadeOperationId() {
        return madeOperationId;
    }


    /**
     * Sets the madeOperationId value for this AutoPaymentInfo_Type.
     * 
     * @param madeOperationId   * Код операции в биллинге
     */
    public void setMadeOperationId(java.lang.String madeOperationId) {
        this.madeOperationId = madeOperationId;
    }


    /**
     * Gets the execStatus value for this AutoPaymentInfo_Type.
     * 
     * @return execStatus   * Информация об исполнении автоплатежа
     */
    public com.rssl.phizic.test.webgate.esberib.generated.ExecStatus_Type getExecStatus() {
        return execStatus;
    }


    /**
     * Sets the execStatus value for this AutoPaymentInfo_Type.
     * 
     * @param execStatus   * Информация об исполнении автоплатежа
     */
    public void setExecStatus(com.rssl.phizic.test.webgate.esberib.generated.ExecStatus_Type execStatus) {
        this.execStatus = execStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoPaymentInfo_Type)) return false;
        AutoPaymentInfo_Type other = (AutoPaymentInfo_Type) obj;
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
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
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
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
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
        new org.apache.axis.description.TypeDesc(AutoPaymentInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentInfo_Type"));
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
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
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
        elemField.setMinOccurs(0);
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
