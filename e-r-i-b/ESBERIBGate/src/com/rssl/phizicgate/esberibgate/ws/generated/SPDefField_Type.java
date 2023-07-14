/**
 * SPDefField_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Поля, определяемые ПУ <SPDefField>
 */
public class SPDefField_Type  implements java.io.Serializable {
    /* Название признака */
    private java.lang.String fieldName;

    /* Значение признака */
    private java.lang.Boolean fieldValue;

    /* Дата открытия УДБО */
    private java.lang.String fieldDt;

    /* Номер */
    private java.lang.String fieldNum;

    /* Дата закрытия УДБО */
    private java.lang.String fieldDt1;

    /* Код ТБ+индекс ОСБ (информация используется ЭСК) */
    private java.lang.String fieldData1;

    /* Статус УДБО */
    private com.rssl.phizicgate.esberibgate.ws.generated.State_Type state;

    public SPDefField_Type() {
    }

    public SPDefField_Type(
           java.lang.String fieldName,
           java.lang.Boolean fieldValue,
           java.lang.String fieldDt,
           java.lang.String fieldNum,
           java.lang.String fieldDt1,
           java.lang.String fieldData1,
           com.rssl.phizicgate.esberibgate.ws.generated.State_Type state) {
           this.fieldName = fieldName;
           this.fieldValue = fieldValue;
           this.fieldDt = fieldDt;
           this.fieldNum = fieldNum;
           this.fieldDt1 = fieldDt1;
           this.fieldData1 = fieldData1;
           this.state = state;
    }


    /**
     * Gets the fieldName value for this SPDefField_Type.
     * 
     * @return fieldName   * Название признака
     */
    public java.lang.String getFieldName() {
        return fieldName;
    }


    /**
     * Sets the fieldName value for this SPDefField_Type.
     * 
     * @param fieldName   * Название признака
     */
    public void setFieldName(java.lang.String fieldName) {
        this.fieldName = fieldName;
    }


    /**
     * Gets the fieldValue value for this SPDefField_Type.
     * 
     * @return fieldValue   * Значение признака
     */
    public java.lang.Boolean getFieldValue() {
        return fieldValue;
    }


    /**
     * Sets the fieldValue value for this SPDefField_Type.
     * 
     * @param fieldValue   * Значение признака
     */
    public void setFieldValue(java.lang.Boolean fieldValue) {
        this.fieldValue = fieldValue;
    }


    /**
     * Gets the fieldDt value for this SPDefField_Type.
     * 
     * @return fieldDt   * Дата открытия УДБО
     */
    public java.lang.String getFieldDt() {
        return fieldDt;
    }


    /**
     * Sets the fieldDt value for this SPDefField_Type.
     * 
     * @param fieldDt   * Дата открытия УДБО
     */
    public void setFieldDt(java.lang.String fieldDt) {
        this.fieldDt = fieldDt;
    }


    /**
     * Gets the fieldNum value for this SPDefField_Type.
     * 
     * @return fieldNum   * Номер
     */
    public java.lang.String getFieldNum() {
        return fieldNum;
    }


    /**
     * Sets the fieldNum value for this SPDefField_Type.
     * 
     * @param fieldNum   * Номер
     */
    public void setFieldNum(java.lang.String fieldNum) {
        this.fieldNum = fieldNum;
    }


    /**
     * Gets the fieldDt1 value for this SPDefField_Type.
     * 
     * @return fieldDt1   * Дата закрытия УДБО
     */
    public java.lang.String getFieldDt1() {
        return fieldDt1;
    }


    /**
     * Sets the fieldDt1 value for this SPDefField_Type.
     * 
     * @param fieldDt1   * Дата закрытия УДБО
     */
    public void setFieldDt1(java.lang.String fieldDt1) {
        this.fieldDt1 = fieldDt1;
    }


    /**
     * Gets the fieldData1 value for this SPDefField_Type.
     * 
     * @return fieldData1   * Код ТБ+индекс ОСБ (информация используется ЭСК)
     */
    public java.lang.String getFieldData1() {
        return fieldData1;
    }


    /**
     * Sets the fieldData1 value for this SPDefField_Type.
     * 
     * @param fieldData1   * Код ТБ+индекс ОСБ (информация используется ЭСК)
     */
    public void setFieldData1(java.lang.String fieldData1) {
        this.fieldData1 = fieldData1;
    }


    /**
     * Gets the state value for this SPDefField_Type.
     * 
     * @return state   * Статус УДБО
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.State_Type getState() {
        return state;
    }


    /**
     * Sets the state value for this SPDefField_Type.
     * 
     * @param state   * Статус УДБО
     */
    public void setState(com.rssl.phizicgate.esberibgate.ws.generated.State_Type state) {
        this.state = state;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SPDefField_Type)) return false;
        SPDefField_Type other = (SPDefField_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fieldName==null && other.getFieldName()==null) || 
             (this.fieldName!=null &&
              this.fieldName.equals(other.getFieldName()))) &&
            ((this.fieldValue==null && other.getFieldValue()==null) || 
             (this.fieldValue!=null &&
              this.fieldValue.equals(other.getFieldValue()))) &&
            ((this.fieldDt==null && other.getFieldDt()==null) || 
             (this.fieldDt!=null &&
              this.fieldDt.equals(other.getFieldDt()))) &&
            ((this.fieldNum==null && other.getFieldNum()==null) || 
             (this.fieldNum!=null &&
              this.fieldNum.equals(other.getFieldNum()))) &&
            ((this.fieldDt1==null && other.getFieldDt1()==null) || 
             (this.fieldDt1!=null &&
              this.fieldDt1.equals(other.getFieldDt1()))) &&
            ((this.fieldData1==null && other.getFieldData1()==null) || 
             (this.fieldData1!=null &&
              this.fieldData1.equals(other.getFieldData1()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState())));
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
        if (getFieldName() != null) {
            _hashCode += getFieldName().hashCode();
        }
        if (getFieldValue() != null) {
            _hashCode += getFieldValue().hashCode();
        }
        if (getFieldDt() != null) {
            _hashCode += getFieldDt().hashCode();
        }
        if (getFieldNum() != null) {
            _hashCode += getFieldNum().hashCode();
        }
        if (getFieldDt1() != null) {
            _hashCode += getFieldDt1().hashCode();
        }
        if (getFieldData1() != null) {
            _hashCode += getFieldData1().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SPDefField_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPDefField_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldDt1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldDt1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldData1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldData1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "State_Type"));
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
