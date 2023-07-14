/**
 * Validator_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Валидаторы (контроли), используемые для дополнительного атрибута.
 * Неприменимо для типов set и list.
 */
public class Validator_Type  implements java.io.Serializable {
    /* Тип валидатора (контроля). На текущий момент используется значение:
     * regexp */
    private java.lang.String type;

    /* Сообщение, выводимое на экран клиенту при срабатывании валидатора
     * (контроля). */
    private java.lang.String message;

    /* Выражение для валидатора (контроля). Список значений для выбора
     * клиентом при для выбора клиентом при Type=list и set */
    private java.lang.String parameter;

    public Validator_Type() {
    }

    public Validator_Type(
           java.lang.String type,
           java.lang.String message,
           java.lang.String parameter) {
           this.type = type;
           this.message = message;
           this.parameter = parameter;
    }


    /**
     * Gets the type value for this Validator_Type.
     * 
     * @return type   * Тип валидатора (контроля). На текущий момент используется значение:
     * regexp
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Validator_Type.
     * 
     * @param type   * Тип валидатора (контроля). На текущий момент используется значение:
     * regexp
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the message value for this Validator_Type.
     * 
     * @return message   * Сообщение, выводимое на экран клиенту при срабатывании валидатора
     * (контроля).
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Validator_Type.
     * 
     * @param message   * Сообщение, выводимое на экран клиенту при срабатывании валидатора
     * (контроля).
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the parameter value for this Validator_Type.
     * 
     * @return parameter   * Выражение для валидатора (контроля). Список значений для выбора
     * клиентом при для выбора клиентом при Type=list и set
     */
    public java.lang.String getParameter() {
        return parameter;
    }


    /**
     * Sets the parameter value for this Validator_Type.
     * 
     * @param parameter   * Выражение для валидатора (контроля). Список значений для выбора
     * клиентом при для выбора клиентом при Type=list и set
     */
    public void setParameter(java.lang.String parameter) {
        this.parameter = parameter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Validator_Type)) return false;
        Validator_Type other = (Validator_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.parameter==null && other.getParameter()==null) || 
             (this.parameter!=null &&
              this.parameter.equals(other.getParameter())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getParameter() != null) {
            _hashCode += getParameter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Validator_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validator_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parameter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Parameter"));
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
