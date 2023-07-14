/**
 * Field_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Поле дополнительного реквизита
 */
public class Field_Type  implements java.io.Serializable {
    /* Идентификатор поля состоящий из цифр и букв английского алфавита */
    private java.lang.String sysFieldName;

    /* Шаблон для заполнения поля */
    private java.lang.String templateType;

    private com.rssl.phizicgate.esberibgate.ws.generated.RegExp_Type[] regExp;

    /* Точная длина значения поля */
    private java.lang.Long length;

    /* Символы на экране до поля */
    private java.lang.String prefix;

    /* Символы на экране после поля */
    private java.lang.String postfix;

    private java.lang.String[] menu;

    /* Введенные клиентом данные */
    private java.lang.String enteredData;

    /* Значение по умолчанию для поля */
    private java.lang.String defaultData;

    /* Текст ошибки на русском языке для пользователя, в случае нахождения
     * ошибки на сервере */
    private java.lang.String error;

    public Field_Type() {
    }

    public Field_Type(
           java.lang.String sysFieldName,
           java.lang.String templateType,
           com.rssl.phizicgate.esberibgate.ws.generated.RegExp_Type[] regExp,
           java.lang.Long length,
           java.lang.String prefix,
           java.lang.String postfix,
           java.lang.String[] menu,
           java.lang.String enteredData,
           java.lang.String defaultData,
           java.lang.String error) {
           this.sysFieldName = sysFieldName;
           this.templateType = templateType;
           this.regExp = regExp;
           this.length = length;
           this.prefix = prefix;
           this.postfix = postfix;
           this.menu = menu;
           this.enteredData = enteredData;
           this.defaultData = defaultData;
           this.error = error;
    }


    /**
     * Gets the sysFieldName value for this Field_Type.
     * 
     * @return sysFieldName   * Идентификатор поля состоящий из цифр и букв английского алфавита
     */
    public java.lang.String getSysFieldName() {
        return sysFieldName;
    }


    /**
     * Sets the sysFieldName value for this Field_Type.
     * 
     * @param sysFieldName   * Идентификатор поля состоящий из цифр и букв английского алфавита
     */
    public void setSysFieldName(java.lang.String sysFieldName) {
        this.sysFieldName = sysFieldName;
    }


    /**
     * Gets the templateType value for this Field_Type.
     * 
     * @return templateType   * Шаблон для заполнения поля
     */
    public java.lang.String getTemplateType() {
        return templateType;
    }


    /**
     * Sets the templateType value for this Field_Type.
     * 
     * @param templateType   * Шаблон для заполнения поля
     */
    public void setTemplateType(java.lang.String templateType) {
        this.templateType = templateType;
    }


    /**
     * Gets the regExp value for this Field_Type.
     * 
     * @return regExp
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.RegExp_Type[] getRegExp() {
        return regExp;
    }


    /**
     * Sets the regExp value for this Field_Type.
     * 
     * @param regExp
     */
    public void setRegExp(com.rssl.phizicgate.esberibgate.ws.generated.RegExp_Type[] regExp) {
        this.regExp = regExp;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.RegExp_Type getRegExp(int i) {
        return this.regExp[i];
    }

    public void setRegExp(int i, com.rssl.phizicgate.esberibgate.ws.generated.RegExp_Type _value) {
        this.regExp[i] = _value;
    }


    /**
     * Gets the length value for this Field_Type.
     * 
     * @return length   * Точная длина значения поля
     */
    public java.lang.Long getLength() {
        return length;
    }


    /**
     * Sets the length value for this Field_Type.
     * 
     * @param length   * Точная длина значения поля
     */
    public void setLength(java.lang.Long length) {
        this.length = length;
    }


    /**
     * Gets the prefix value for this Field_Type.
     * 
     * @return prefix   * Символы на экране до поля
     */
    public java.lang.String getPrefix() {
        return prefix;
    }


    /**
     * Sets the prefix value for this Field_Type.
     * 
     * @param prefix   * Символы на экране до поля
     */
    public void setPrefix(java.lang.String prefix) {
        this.prefix = prefix;
    }


    /**
     * Gets the postfix value for this Field_Type.
     * 
     * @return postfix   * Символы на экране после поля
     */
    public java.lang.String getPostfix() {
        return postfix;
    }


    /**
     * Sets the postfix value for this Field_Type.
     * 
     * @param postfix   * Символы на экране после поля
     */
    public void setPostfix(java.lang.String postfix) {
        this.postfix = postfix;
    }


    /**
     * Gets the menu value for this Field_Type.
     * 
     * @return menu
     */
    public java.lang.String[] getMenu() {
        return menu;
    }


    /**
     * Sets the menu value for this Field_Type.
     * 
     * @param menu
     */
    public void setMenu(java.lang.String[] menu) {
        this.menu = menu;
    }


    /**
     * Gets the enteredData value for this Field_Type.
     * 
     * @return enteredData   * Введенные клиентом данные
     */
    public java.lang.String getEnteredData() {
        return enteredData;
    }


    /**
     * Sets the enteredData value for this Field_Type.
     * 
     * @param enteredData   * Введенные клиентом данные
     */
    public void setEnteredData(java.lang.String enteredData) {
        this.enteredData = enteredData;
    }


    /**
     * Gets the defaultData value for this Field_Type.
     * 
     * @return defaultData   * Значение по умолчанию для поля
     */
    public java.lang.String getDefaultData() {
        return defaultData;
    }


    /**
     * Sets the defaultData value for this Field_Type.
     * 
     * @param defaultData   * Значение по умолчанию для поля
     */
    public void setDefaultData(java.lang.String defaultData) {
        this.defaultData = defaultData;
    }


    /**
     * Gets the error value for this Field_Type.
     * 
     * @return error   * Текст ошибки на русском языке для пользователя, в случае нахождения
     * ошибки на сервере
     */
    public java.lang.String getError() {
        return error;
    }


    /**
     * Sets the error value for this Field_Type.
     * 
     * @param error   * Текст ошибки на русском языке для пользователя, в случае нахождения
     * ошибки на сервере
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Field_Type)) return false;
        Field_Type other = (Field_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sysFieldName==null && other.getSysFieldName()==null) || 
             (this.sysFieldName!=null &&
              this.sysFieldName.equals(other.getSysFieldName()))) &&
            ((this.templateType==null && other.getTemplateType()==null) || 
             (this.templateType!=null &&
              this.templateType.equals(other.getTemplateType()))) &&
            ((this.regExp==null && other.getRegExp()==null) || 
             (this.regExp!=null &&
              java.util.Arrays.equals(this.regExp, other.getRegExp()))) &&
            ((this.length==null && other.getLength()==null) || 
             (this.length!=null &&
              this.length.equals(other.getLength()))) &&
            ((this.prefix==null && other.getPrefix()==null) || 
             (this.prefix!=null &&
              this.prefix.equals(other.getPrefix()))) &&
            ((this.postfix==null && other.getPostfix()==null) || 
             (this.postfix!=null &&
              this.postfix.equals(other.getPostfix()))) &&
            ((this.menu==null && other.getMenu()==null) || 
             (this.menu!=null &&
              java.util.Arrays.equals(this.menu, other.getMenu()))) &&
            ((this.enteredData==null && other.getEnteredData()==null) || 
             (this.enteredData!=null &&
              this.enteredData.equals(other.getEnteredData()))) &&
            ((this.defaultData==null && other.getDefaultData()==null) || 
             (this.defaultData!=null &&
              this.defaultData.equals(other.getDefaultData()))) &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError())));
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
        if (getSysFieldName() != null) {
            _hashCode += getSysFieldName().hashCode();
        }
        if (getTemplateType() != null) {
            _hashCode += getTemplateType().hashCode();
        }
        if (getRegExp() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRegExp());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRegExp(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLength() != null) {
            _hashCode += getLength().hashCode();
        }
        if (getPrefix() != null) {
            _hashCode += getPrefix().hashCode();
        }
        if (getPostfix() != null) {
            _hashCode += getPostfix().hashCode();
        }
        if (getMenu() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMenu());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMenu(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEnteredData() != null) {
            _hashCode += getEnteredData().hashCode();
        }
        if (getDefaultData() != null) {
            _hashCode += getDefaultData().hashCode();
        }
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Field_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Field_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sysFieldName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SysFieldName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TemplateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regExp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegExp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegExp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("length");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prefix");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Prefix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postfix");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Postfix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("menu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Menu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Menu_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enteredData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EnteredData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DefaultData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Error"));
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
