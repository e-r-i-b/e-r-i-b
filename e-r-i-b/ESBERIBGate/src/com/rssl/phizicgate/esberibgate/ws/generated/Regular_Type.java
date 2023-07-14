/**
 * Regular_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Параметры регулярного платежа
 */
public class Regular_Type  implements java.io.Serializable {
    /* Дата начала действия поручения */
    private java.lang.String dateBegin;

    /* Дата окончания действия поручения */
    private java.lang.String dateEnd;

    private com.rssl.phizicgate.esberibgate.ws.generated.Regular_TypePayDay payDay;

    /* Приоритет исполнения поручения */
    private java.lang.Long priority;

    /* Код события исполнения формы */
    private java.lang.String exeEventCode;

    /* Сумма */
    private java.math.BigDecimal summa;

    /* Алгоритм расчета суммы платежа */
    private java.lang.String summaKindCode;

    /* Процент от суммы */
    private java.math.BigDecimal percent;

    public Regular_Type() {
    }

    public Regular_Type(
           java.lang.String dateBegin,
           java.lang.String dateEnd,
           com.rssl.phizicgate.esberibgate.ws.generated.Regular_TypePayDay payDay,
           java.lang.Long priority,
           java.lang.String exeEventCode,
           java.math.BigDecimal summa,
           java.lang.String summaKindCode,
           java.math.BigDecimal percent) {
           this.dateBegin = dateBegin;
           this.dateEnd = dateEnd;
           this.payDay = payDay;
           this.priority = priority;
           this.exeEventCode = exeEventCode;
           this.summa = summa;
           this.summaKindCode = summaKindCode;
           this.percent = percent;
    }


    /**
     * Gets the dateBegin value for this Regular_Type.
     * 
     * @return dateBegin   * Дата начала действия поручения
     */
    public java.lang.String getDateBegin() {
        return dateBegin;
    }


    /**
     * Sets the dateBegin value for this Regular_Type.
     * 
     * @param dateBegin   * Дата начала действия поручения
     */
    public void setDateBegin(java.lang.String dateBegin) {
        this.dateBegin = dateBegin;
    }


    /**
     * Gets the dateEnd value for this Regular_Type.
     * 
     * @return dateEnd   * Дата окончания действия поручения
     */
    public java.lang.String getDateEnd() {
        return dateEnd;
    }


    /**
     * Sets the dateEnd value for this Regular_Type.
     * 
     * @param dateEnd   * Дата окончания действия поручения
     */
    public void setDateEnd(java.lang.String dateEnd) {
        this.dateEnd = dateEnd;
    }


    /**
     * Gets the payDay value for this Regular_Type.
     * 
     * @return payDay
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Regular_TypePayDay getPayDay() {
        return payDay;
    }


    /**
     * Sets the payDay value for this Regular_Type.
     * 
     * @param payDay
     */
    public void setPayDay(com.rssl.phizicgate.esberibgate.ws.generated.Regular_TypePayDay payDay) {
        this.payDay = payDay;
    }


    /**
     * Gets the priority value for this Regular_Type.
     * 
     * @return priority   * Приоритет исполнения поручения
     */
    public java.lang.Long getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this Regular_Type.
     * 
     * @param priority   * Приоритет исполнения поручения
     */
    public void setPriority(java.lang.Long priority) {
        this.priority = priority;
    }


    /**
     * Gets the exeEventCode value for this Regular_Type.
     * 
     * @return exeEventCode   * Код события исполнения формы
     */
    public java.lang.String getExeEventCode() {
        return exeEventCode;
    }


    /**
     * Sets the exeEventCode value for this Regular_Type.
     * 
     * @param exeEventCode   * Код события исполнения формы
     */
    public void setExeEventCode(java.lang.String exeEventCode) {
        this.exeEventCode = exeEventCode;
    }


    /**
     * Gets the summa value for this Regular_Type.
     * 
     * @return summa   * Сумма
     */
    public java.math.BigDecimal getSumma() {
        return summa;
    }


    /**
     * Sets the summa value for this Regular_Type.
     * 
     * @param summa   * Сумма
     */
    public void setSumma(java.math.BigDecimal summa) {
        this.summa = summa;
    }


    /**
     * Gets the summaKindCode value for this Regular_Type.
     * 
     * @return summaKindCode   * Алгоритм расчета суммы платежа
     */
    public java.lang.String getSummaKindCode() {
        return summaKindCode;
    }


    /**
     * Sets the summaKindCode value for this Regular_Type.
     * 
     * @param summaKindCode   * Алгоритм расчета суммы платежа
     */
    public void setSummaKindCode(java.lang.String summaKindCode) {
        this.summaKindCode = summaKindCode;
    }


    /**
     * Gets the percent value for this Regular_Type.
     * 
     * @return percent   * Процент от суммы
     */
    public java.math.BigDecimal getPercent() {
        return percent;
    }


    /**
     * Sets the percent value for this Regular_Type.
     * 
     * @param percent   * Процент от суммы
     */
    public void setPercent(java.math.BigDecimal percent) {
        this.percent = percent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Regular_Type)) return false;
        Regular_Type other = (Regular_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateBegin==null && other.getDateBegin()==null) || 
             (this.dateBegin!=null &&
              this.dateBegin.equals(other.getDateBegin()))) &&
            ((this.dateEnd==null && other.getDateEnd()==null) || 
             (this.dateEnd!=null &&
              this.dateEnd.equals(other.getDateEnd()))) &&
            ((this.payDay==null && other.getPayDay()==null) || 
             (this.payDay!=null &&
              this.payDay.equals(other.getPayDay()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
            ((this.exeEventCode==null && other.getExeEventCode()==null) || 
             (this.exeEventCode!=null &&
              this.exeEventCode.equals(other.getExeEventCode()))) &&
            ((this.summa==null && other.getSumma()==null) || 
             (this.summa!=null &&
              this.summa.equals(other.getSumma()))) &&
            ((this.summaKindCode==null && other.getSummaKindCode()==null) || 
             (this.summaKindCode!=null &&
              this.summaKindCode.equals(other.getSummaKindCode()))) &&
            ((this.percent==null && other.getPercent()==null) || 
             (this.percent!=null &&
              this.percent.equals(other.getPercent())));
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
        if (getDateBegin() != null) {
            _hashCode += getDateBegin().hashCode();
        }
        if (getDateEnd() != null) {
            _hashCode += getDateEnd().hashCode();
        }
        if (getPayDay() != null) {
            _hashCode += getPayDay().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getExeEventCode() != null) {
            _hashCode += getExeEventCode().hashCode();
        }
        if (getSumma() != null) {
            _hashCode += getSumma().hashCode();
        }
        if (getSummaKindCode() != null) {
            _hashCode += getSummaKindCode().hashCode();
        }
        if (getPercent() != null) {
            _hashCode += getPercent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Regular_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Regular_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateBegin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateBegin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateEnd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateEnd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">Regular_Type>PayDay"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exeEventCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExeEventCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Summa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summaKindCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaKindCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Percent"));
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
