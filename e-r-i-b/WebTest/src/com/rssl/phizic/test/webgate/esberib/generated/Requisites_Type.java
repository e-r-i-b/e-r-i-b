/**
 * Requisites_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Дополнительный реквизит
 */
public class Requisites_Type  implements java.io.Serializable {
    /* Наименование дополнительного реквизита для отображения пользователю */
    private java.lang.String name;

    /* Идентификатор поля состоящий из цифр и букв английского алфавита */
    private java.lang.String sysName;

    /* Признак «Дополнительный реквизит обязательно должен быть указан
     * при формировании шаблона» */
    private java.lang.Boolean isMandatory;

    /* Признак «Данный реквизит является общей суммой платежа за вычетом
     * комиссии» */
    private java.lang.Boolean isMainSum;

    /* Признак «Данный реквизит является одним из ключевых, т.е. индетифицирующий
     * плательщика» */
    private java.lang.Boolean isKey;

    /* Признак «Информационного поля» */
    private java.lang.Boolean isReadOnly;

    /* Признак «Поля для ввода даты» */
    private java.lang.Boolean isCalendar;

    /* Признак «Скрытого поля» */
    private java.lang.Boolean isHidden;

    /* Признак «Расширяемого поля» */
    private java.lang.Boolean isExtended;

    /* Правила заполнения поля */
    private java.lang.String explanation;

    /* Комментарий к полю */
    private java.lang.String comment;

    private com.rssl.phizic.test.webgate.esberib.generated.Field_Type[] field;

    public Requisites_Type() {
    }

    public Requisites_Type(
           java.lang.String name,
           java.lang.String sysName,
           java.lang.Boolean isMandatory,
           java.lang.Boolean isMainSum,
           java.lang.Boolean isKey,
           java.lang.Boolean isReadOnly,
           java.lang.Boolean isCalendar,
           java.lang.Boolean isHidden,
           java.lang.Boolean isExtended,
           java.lang.String explanation,
           java.lang.String comment,
           com.rssl.phizic.test.webgate.esberib.generated.Field_Type[] field) {
           this.name = name;
           this.sysName = sysName;
           this.isMandatory = isMandatory;
           this.isMainSum = isMainSum;
           this.isKey = isKey;
           this.isReadOnly = isReadOnly;
           this.isCalendar = isCalendar;
           this.isHidden = isHidden;
           this.isExtended = isExtended;
           this.explanation = explanation;
           this.comment = comment;
           this.field = field;
    }


    /**
     * Gets the name value for this Requisites_Type.
     * 
     * @return name   * Наименование дополнительного реквизита для отображения пользователю
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Requisites_Type.
     * 
     * @param name   * Наименование дополнительного реквизита для отображения пользователю
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the sysName value for this Requisites_Type.
     * 
     * @return sysName   * Идентификатор поля состоящий из цифр и букв английского алфавита
     */
    public java.lang.String getSysName() {
        return sysName;
    }


    /**
     * Sets the sysName value for this Requisites_Type.
     * 
     * @param sysName   * Идентификатор поля состоящий из цифр и букв английского алфавита
     */
    public void setSysName(java.lang.String sysName) {
        this.sysName = sysName;
    }


    /**
     * Gets the isMandatory value for this Requisites_Type.
     * 
     * @return isMandatory   * Признак «Дополнительный реквизит обязательно должен быть указан
     * при формировании шаблона»
     */
    public java.lang.Boolean getIsMandatory() {
        return isMandatory;
    }


    /**
     * Sets the isMandatory value for this Requisites_Type.
     * 
     * @param isMandatory   * Признак «Дополнительный реквизит обязательно должен быть указан
     * при формировании шаблона»
     */
    public void setIsMandatory(java.lang.Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }


    /**
     * Gets the isMainSum value for this Requisites_Type.
     * 
     * @return isMainSum   * Признак «Данный реквизит является общей суммой платежа за вычетом
     * комиссии»
     */
    public java.lang.Boolean getIsMainSum() {
        return isMainSum;
    }


    /**
     * Sets the isMainSum value for this Requisites_Type.
     * 
     * @param isMainSum   * Признак «Данный реквизит является общей суммой платежа за вычетом
     * комиссии»
     */
    public void setIsMainSum(java.lang.Boolean isMainSum) {
        this.isMainSum = isMainSum;
    }


    /**
     * Gets the isKey value for this Requisites_Type.
     * 
     * @return isKey   * Признак «Данный реквизит является одним из ключевых, т.е. индетифицирующий
     * плательщика»
     */
    public java.lang.Boolean getIsKey() {
        return isKey;
    }


    /**
     * Sets the isKey value for this Requisites_Type.
     * 
     * @param isKey   * Признак «Данный реквизит является одним из ключевых, т.е. индетифицирующий
     * плательщика»
     */
    public void setIsKey(java.lang.Boolean isKey) {
        this.isKey = isKey;
    }


    /**
     * Gets the isReadOnly value for this Requisites_Type.
     * 
     * @return isReadOnly   * Признак «Информационного поля»
     */
    public java.lang.Boolean getIsReadOnly() {
        return isReadOnly;
    }


    /**
     * Sets the isReadOnly value for this Requisites_Type.
     * 
     * @param isReadOnly   * Признак «Информационного поля»
     */
    public void setIsReadOnly(java.lang.Boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }


    /**
     * Gets the isCalendar value for this Requisites_Type.
     * 
     * @return isCalendar   * Признак «Поля для ввода даты»
     */
    public java.lang.Boolean getIsCalendar() {
        return isCalendar;
    }


    /**
     * Sets the isCalendar value for this Requisites_Type.
     * 
     * @param isCalendar   * Признак «Поля для ввода даты»
     */
    public void setIsCalendar(java.lang.Boolean isCalendar) {
        this.isCalendar = isCalendar;
    }


    /**
     * Gets the isHidden value for this Requisites_Type.
     * 
     * @return isHidden   * Признак «Скрытого поля»
     */
    public java.lang.Boolean getIsHidden() {
        return isHidden;
    }


    /**
     * Sets the isHidden value for this Requisites_Type.
     * 
     * @param isHidden   * Признак «Скрытого поля»
     */
    public void setIsHidden(java.lang.Boolean isHidden) {
        this.isHidden = isHidden;
    }


    /**
     * Gets the isExtended value for this Requisites_Type.
     * 
     * @return isExtended   * Признак «Расширяемого поля»
     */
    public java.lang.Boolean getIsExtended() {
        return isExtended;
    }


    /**
     * Sets the isExtended value for this Requisites_Type.
     * 
     * @param isExtended   * Признак «Расширяемого поля»
     */
    public void setIsExtended(java.lang.Boolean isExtended) {
        this.isExtended = isExtended;
    }


    /**
     * Gets the explanation value for this Requisites_Type.
     * 
     * @return explanation   * Правила заполнения поля
     */
    public java.lang.String getExplanation() {
        return explanation;
    }


    /**
     * Sets the explanation value for this Requisites_Type.
     * 
     * @param explanation   * Правила заполнения поля
     */
    public void setExplanation(java.lang.String explanation) {
        this.explanation = explanation;
    }


    /**
     * Gets the comment value for this Requisites_Type.
     * 
     * @return comment   * Комментарий к полю
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this Requisites_Type.
     * 
     * @param comment   * Комментарий к полю
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the field value for this Requisites_Type.
     * 
     * @return field
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Field_Type[] getField() {
        return field;
    }


    /**
     * Sets the field value for this Requisites_Type.
     * 
     * @param field
     */
    public void setField(com.rssl.phizic.test.webgate.esberib.generated.Field_Type[] field) {
        this.field = field;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.Field_Type getField(int i) {
        return this.field[i];
    }

    public void setField(int i, com.rssl.phizic.test.webgate.esberib.generated.Field_Type _value) {
        this.field[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Requisites_Type)) return false;
        Requisites_Type other = (Requisites_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.sysName==null && other.getSysName()==null) || 
             (this.sysName!=null &&
              this.sysName.equals(other.getSysName()))) &&
            ((this.isMandatory==null && other.getIsMandatory()==null) || 
             (this.isMandatory!=null &&
              this.isMandatory.equals(other.getIsMandatory()))) &&
            ((this.isMainSum==null && other.getIsMainSum()==null) || 
             (this.isMainSum!=null &&
              this.isMainSum.equals(other.getIsMainSum()))) &&
            ((this.isKey==null && other.getIsKey()==null) || 
             (this.isKey!=null &&
              this.isKey.equals(other.getIsKey()))) &&
            ((this.isReadOnly==null && other.getIsReadOnly()==null) || 
             (this.isReadOnly!=null &&
              this.isReadOnly.equals(other.getIsReadOnly()))) &&
            ((this.isCalendar==null && other.getIsCalendar()==null) || 
             (this.isCalendar!=null &&
              this.isCalendar.equals(other.getIsCalendar()))) &&
            ((this.isHidden==null && other.getIsHidden()==null) || 
             (this.isHidden!=null &&
              this.isHidden.equals(other.getIsHidden()))) &&
            ((this.isExtended==null && other.getIsExtended()==null) || 
             (this.isExtended!=null &&
              this.isExtended.equals(other.getIsExtended()))) &&
            ((this.explanation==null && other.getExplanation()==null) || 
             (this.explanation!=null &&
              this.explanation.equals(other.getExplanation()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.field==null && other.getField()==null) || 
             (this.field!=null &&
              java.util.Arrays.equals(this.field, other.getField())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSysName() != null) {
            _hashCode += getSysName().hashCode();
        }
        if (getIsMandatory() != null) {
            _hashCode += getIsMandatory().hashCode();
        }
        if (getIsMainSum() != null) {
            _hashCode += getIsMainSum().hashCode();
        }
        if (getIsKey() != null) {
            _hashCode += getIsKey().hashCode();
        }
        if (getIsReadOnly() != null) {
            _hashCode += getIsReadOnly().hashCode();
        }
        if (getIsCalendar() != null) {
            _hashCode += getIsCalendar().hashCode();
        }
        if (getIsHidden() != null) {
            _hashCode += getIsHidden().hashCode();
        }
        if (getIsExtended() != null) {
            _hashCode += getIsExtended().hashCode();
        }
        if (getExplanation() != null) {
            _hashCode += getExplanation().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getField() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getField());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getField(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Requisites_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisites_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sysName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SysName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isMandatory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsMandatory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isMainSum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsMainSum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isReadOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsReadOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isCalendar");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsCalendar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isHidden");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsHidden"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isExtended");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsExtended"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("explanation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Explanation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Field"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Field"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
