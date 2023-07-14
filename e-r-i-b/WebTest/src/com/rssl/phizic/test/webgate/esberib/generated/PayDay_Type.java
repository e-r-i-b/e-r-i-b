/**
 * PayDay_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Расписание  исполнения АП.
 */
public class PayDay_Type  implements java.io.Serializable {
    /* День платежа в месяце. Передается значение от 1 до количества
     * дней в месяце создания АП (январь - 31, в апрель - 30) */
    private java.lang.String day;

    /* Номер месяца в квартале: от 1 до 3 */
    private java.lang.String monthInQuarter;

    /* Номер месяца в году: от 1 до 12 */
    private java.lang.String monthInYear;

    /* День недели платежа: –	Monday – Понедельник;–	Tuesday - Вторник;–
     * 	Wednesday  - Среда;–	Thursday - Четверг–	Friday – Пятница;–	Saturday
     * – Суббота;Sunday – Воскресенье */
    private java.lang.String weekDay;

    public PayDay_Type() {
    }

    public PayDay_Type(
           java.lang.String day,
           java.lang.String monthInQuarter,
           java.lang.String monthInYear,
           java.lang.String weekDay) {
           this.day = day;
           this.monthInQuarter = monthInQuarter;
           this.monthInYear = monthInYear;
           this.weekDay = weekDay;
    }


    /**
     * Gets the day value for this PayDay_Type.
     * 
     * @return day   * День платежа в месяце. Передается значение от 1 до количества
     * дней в месяце создания АП (январь - 31, в апрель - 30)
     */
    public java.lang.String getDay() {
        return day;
    }


    /**
     * Sets the day value for this PayDay_Type.
     * 
     * @param day   * День платежа в месяце. Передается значение от 1 до количества
     * дней в месяце создания АП (январь - 31, в апрель - 30)
     */
    public void setDay(java.lang.String day) {
        this.day = day;
    }


    /**
     * Gets the monthInQuarter value for this PayDay_Type.
     * 
     * @return monthInQuarter   * Номер месяца в квартале: от 1 до 3
     */
    public java.lang.String getMonthInQuarter() {
        return monthInQuarter;
    }


    /**
     * Sets the monthInQuarter value for this PayDay_Type.
     * 
     * @param monthInQuarter   * Номер месяца в квартале: от 1 до 3
     */
    public void setMonthInQuarter(java.lang.String monthInQuarter) {
        this.monthInQuarter = monthInQuarter;
    }


    /**
     * Gets the monthInYear value for this PayDay_Type.
     * 
     * @return monthInYear   * Номер месяца в году: от 1 до 12
     */
    public java.lang.String getMonthInYear() {
        return monthInYear;
    }


    /**
     * Sets the monthInYear value for this PayDay_Type.
     * 
     * @param monthInYear   * Номер месяца в году: от 1 до 12
     */
    public void setMonthInYear(java.lang.String monthInYear) {
        this.monthInYear = monthInYear;
    }


    /**
     * Gets the weekDay value for this PayDay_Type.
     * 
     * @return weekDay   * День недели платежа: –	Monday – Понедельник;–	Tuesday - Вторник;–
     * 	Wednesday  - Среда;–	Thursday - Четверг–	Friday – Пятница;–	Saturday
     * – Суббота;Sunday – Воскресенье
     */
    public java.lang.String getWeekDay() {
        return weekDay;
    }


    /**
     * Sets the weekDay value for this PayDay_Type.
     * 
     * @param weekDay   * День недели платежа: –	Monday – Понедельник;–	Tuesday - Вторник;–
     * 	Wednesday  - Среда;–	Thursday - Четверг–	Friday – Пятница;–	Saturday
     * – Суббота;Sunday – Воскресенье
     */
    public void setWeekDay(java.lang.String weekDay) {
        this.weekDay = weekDay;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PayDay_Type)) return false;
        PayDay_Type other = (PayDay_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.day==null && other.getDay()==null) || 
             (this.day!=null &&
              this.day.equals(other.getDay()))) &&
            ((this.monthInQuarter==null && other.getMonthInQuarter()==null) || 
             (this.monthInQuarter!=null &&
              this.monthInQuarter.equals(other.getMonthInQuarter()))) &&
            ((this.monthInYear==null && other.getMonthInYear()==null) || 
             (this.monthInYear!=null &&
              this.monthInYear.equals(other.getMonthInYear()))) &&
            ((this.weekDay==null && other.getWeekDay()==null) || 
             (this.weekDay!=null &&
              this.weekDay.equals(other.getWeekDay())));
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
        if (getDay() != null) {
            _hashCode += getDay().hashCode();
        }
        if (getMonthInQuarter() != null) {
            _hashCode += getMonthInQuarter().hashCode();
        }
        if (getMonthInYear() != null) {
            _hashCode += getMonthInYear().hashCode();
        }
        if (getWeekDay() != null) {
            _hashCode += getWeekDay().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PayDay_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayDay_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("day");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Day"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthInQuarter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MonthInQuarter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthInYear");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MonthInYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("weekDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WeekDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
