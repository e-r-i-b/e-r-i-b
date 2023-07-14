/**
 * ExecStatus_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Информация об исполнении автоплатежа в АС AutoPay
 */
public class ExecStatus_Type  implements java.io.Serializable {
    /* Дата исполнения платежа */
    private java.lang.String execPaymentDate;

    /* Код причины неисполнения платежа. Присутствует только для платежей
     * со статусом Отменен. (PaymentStatus = Canceled) */
    private java.lang.String nonExecReasonCode;

    /* Причина неисполнения. Присутствует только для платежей со статусом
     * Отменен. (PaymentStatus = Canceled) */
    private java.lang.String nonExecReasonDesc;

    public ExecStatus_Type() {
    }

    public ExecStatus_Type(
           java.lang.String execPaymentDate,
           java.lang.String nonExecReasonCode,
           java.lang.String nonExecReasonDesc) {
           this.execPaymentDate = execPaymentDate;
           this.nonExecReasonCode = nonExecReasonCode;
           this.nonExecReasonDesc = nonExecReasonDesc;
    }


    /**
     * Gets the execPaymentDate value for this ExecStatus_Type.
     * 
     * @return execPaymentDate   * Дата исполнения платежа
     */
    public java.lang.String getExecPaymentDate() {
        return execPaymentDate;
    }


    /**
     * Sets the execPaymentDate value for this ExecStatus_Type.
     * 
     * @param execPaymentDate   * Дата исполнения платежа
     */
    public void setExecPaymentDate(java.lang.String execPaymentDate) {
        this.execPaymentDate = execPaymentDate;
    }


    /**
     * Gets the nonExecReasonCode value for this ExecStatus_Type.
     * 
     * @return nonExecReasonCode   * Код причины неисполнения платежа. Присутствует только для платежей
     * со статусом Отменен. (PaymentStatus = Canceled)
     */
    public java.lang.String getNonExecReasonCode() {
        return nonExecReasonCode;
    }


    /**
     * Sets the nonExecReasonCode value for this ExecStatus_Type.
     * 
     * @param nonExecReasonCode   * Код причины неисполнения платежа. Присутствует только для платежей
     * со статусом Отменен. (PaymentStatus = Canceled)
     */
    public void setNonExecReasonCode(java.lang.String nonExecReasonCode) {
        this.nonExecReasonCode = nonExecReasonCode;
    }


    /**
     * Gets the nonExecReasonDesc value for this ExecStatus_Type.
     * 
     * @return nonExecReasonDesc   * Причина неисполнения. Присутствует только для платежей со статусом
     * Отменен. (PaymentStatus = Canceled)
     */
    public java.lang.String getNonExecReasonDesc() {
        return nonExecReasonDesc;
    }


    /**
     * Sets the nonExecReasonDesc value for this ExecStatus_Type.
     * 
     * @param nonExecReasonDesc   * Причина неисполнения. Присутствует только для платежей со статусом
     * Отменен. (PaymentStatus = Canceled)
     */
    public void setNonExecReasonDesc(java.lang.String nonExecReasonDesc) {
        this.nonExecReasonDesc = nonExecReasonDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExecStatus_Type)) return false;
        ExecStatus_Type other = (ExecStatus_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.execPaymentDate==null && other.getExecPaymentDate()==null) || 
             (this.execPaymentDate!=null &&
              this.execPaymentDate.equals(other.getExecPaymentDate()))) &&
            ((this.nonExecReasonCode==null && other.getNonExecReasonCode()==null) || 
             (this.nonExecReasonCode!=null &&
              this.nonExecReasonCode.equals(other.getNonExecReasonCode()))) &&
            ((this.nonExecReasonDesc==null && other.getNonExecReasonDesc()==null) || 
             (this.nonExecReasonDesc!=null &&
              this.nonExecReasonDesc.equals(other.getNonExecReasonDesc())));
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
        if (getExecPaymentDate() != null) {
            _hashCode += getExecPaymentDate().hashCode();
        }
        if (getNonExecReasonCode() != null) {
            _hashCode += getNonExecReasonCode().hashCode();
        }
        if (getNonExecReasonDesc() != null) {
            _hashCode += getNonExecReasonDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExecStatus_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExecStatus_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("execPaymentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExecPaymentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nonExecReasonCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NonExecReasonCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nonExecReasonDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NonExecReasonDesc"));
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
