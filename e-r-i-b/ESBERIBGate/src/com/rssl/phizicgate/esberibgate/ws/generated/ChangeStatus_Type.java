/**
 * ChangeStatus_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Информация об изменении состояния подписки
 */
public class ChangeStatus_Type  implements java.io.Serializable {
    /* Код причины нахождения подписки в данном состоянии */
    private java.lang.String reasonCode;

    /* Текстовое описание причины нахождения подписки в данном состоянии */
    private java.lang.String reasonDesc;

    /* Дата и время изменения статуса */
    private java.lang.String changeDate;

    public ChangeStatus_Type() {
    }

    public ChangeStatus_Type(
           java.lang.String reasonCode,
           java.lang.String reasonDesc,
           java.lang.String changeDate) {
           this.reasonCode = reasonCode;
           this.reasonDesc = reasonDesc;
           this.changeDate = changeDate;
    }


    /**
     * Gets the reasonCode value for this ChangeStatus_Type.
     * 
     * @return reasonCode   * Код причины нахождения подписки в данном состоянии
     */
    public java.lang.String getReasonCode() {
        return reasonCode;
    }


    /**
     * Sets the reasonCode value for this ChangeStatus_Type.
     * 
     * @param reasonCode   * Код причины нахождения подписки в данном состоянии
     */
    public void setReasonCode(java.lang.String reasonCode) {
        this.reasonCode = reasonCode;
    }


    /**
     * Gets the reasonDesc value for this ChangeStatus_Type.
     * 
     * @return reasonDesc   * Текстовое описание причины нахождения подписки в данном состоянии
     */
    public java.lang.String getReasonDesc() {
        return reasonDesc;
    }


    /**
     * Sets the reasonDesc value for this ChangeStatus_Type.
     * 
     * @param reasonDesc   * Текстовое описание причины нахождения подписки в данном состоянии
     */
    public void setReasonDesc(java.lang.String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }


    /**
     * Gets the changeDate value for this ChangeStatus_Type.
     * 
     * @return changeDate   * Дата и время изменения статуса
     */
    public java.lang.String getChangeDate() {
        return changeDate;
    }


    /**
     * Sets the changeDate value for this ChangeStatus_Type.
     * 
     * @param changeDate   * Дата и время изменения статуса
     */
    public void setChangeDate(java.lang.String changeDate) {
        this.changeDate = changeDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangeStatus_Type)) return false;
        ChangeStatus_Type other = (ChangeStatus_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reasonCode==null && other.getReasonCode()==null) || 
             (this.reasonCode!=null &&
              this.reasonCode.equals(other.getReasonCode()))) &&
            ((this.reasonDesc==null && other.getReasonDesc()==null) || 
             (this.reasonDesc!=null &&
              this.reasonDesc.equals(other.getReasonDesc()))) &&
            ((this.changeDate==null && other.getChangeDate()==null) || 
             (this.changeDate!=null &&
              this.changeDate.equals(other.getChangeDate())));
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
        if (getReasonCode() != null) {
            _hashCode += getReasonCode().hashCode();
        }
        if (getReasonDesc() != null) {
            _hashCode += getReasonDesc().hashCode();
        }
        if (getChangeDate() != null) {
            _hashCode += getChangeDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeStatus_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeStatus_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReasonCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReasonDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeDate"));
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
