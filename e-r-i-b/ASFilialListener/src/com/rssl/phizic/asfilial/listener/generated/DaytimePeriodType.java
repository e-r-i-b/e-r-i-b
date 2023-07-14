/**
 * DaytimePeriodType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;


/**
 * Временной интервал в течение дня с указанием дня недели и часового
 * пояса
 */
public class DaytimePeriodType  implements java.io.Serializable {
    private org.apache.axis.types.Time begin;

    private org.apache.axis.types.Time end;

    /* День недели
     *                         Допустимые значения:
     *                         "Monday" – понедельник
     *                         "Tuesday" – вторник
     *                         "Wednesday" – среда
     *                         "Thursday" – четверг
     *                         "Friday" – пятница
     *                         "Saturday" – суббота
     *                         "Sunday" – воскресенье */
    private java.lang.String[] day;

    private long timeZone;

    public DaytimePeriodType() {
    }

    public DaytimePeriodType(
           org.apache.axis.types.Time begin,
           org.apache.axis.types.Time end,
           java.lang.String[] day,
           long timeZone) {
           this.begin = begin;
           this.end = end;
           this.day = day;
           this.timeZone = timeZone;
    }


    /**
     * Gets the begin value for this DaytimePeriodType.
     * 
     * @return begin
     */
    public org.apache.axis.types.Time getBegin() {
        return begin;
    }


    /**
     * Sets the begin value for this DaytimePeriodType.
     * 
     * @param begin
     */
    public void setBegin(org.apache.axis.types.Time begin) {
        this.begin = begin;
    }


    /**
     * Gets the end value for this DaytimePeriodType.
     * 
     * @return end
     */
    public org.apache.axis.types.Time getEnd() {
        return end;
    }


    /**
     * Sets the end value for this DaytimePeriodType.
     * 
     * @param end
     */
    public void setEnd(org.apache.axis.types.Time end) {
        this.end = end;
    }


    /**
     * Gets the day value for this DaytimePeriodType.
     * 
     * @return day   * День недели
     *                         Допустимые значения:
     *                         "Monday" – понедельник
     *                         "Tuesday" – вторник
     *                         "Wednesday" – среда
     *                         "Thursday" – четверг
     *                         "Friday" – пятница
     *                         "Saturday" – суббота
     *                         "Sunday" – воскресенье
     */
    public java.lang.String[] getDay() {
        return day;
    }


    /**
     * Sets the day value for this DaytimePeriodType.
     * 
     * @param day   * День недели
     *                         Допустимые значения:
     *                         "Monday" – понедельник
     *                         "Tuesday" – вторник
     *                         "Wednesday" – среда
     *                         "Thursday" – четверг
     *                         "Friday" – пятница
     *                         "Saturday" – суббота
     *                         "Sunday" – воскресенье
     */
    public void setDay(java.lang.String[] day) {
        this.day = day;
    }

    public java.lang.String getDay(int i) {
        return this.day[i];
    }

    public void setDay(int i, java.lang.String _value) {
        this.day[i] = _value;
    }


    /**
     * Gets the timeZone value for this DaytimePeriodType.
     * 
     * @return timeZone
     */
    public long getTimeZone() {
        return timeZone;
    }


    /**
     * Sets the timeZone value for this DaytimePeriodType.
     * 
     * @param timeZone
     */
    public void setTimeZone(long timeZone) {
        this.timeZone = timeZone;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DaytimePeriodType)) return false;
        DaytimePeriodType other = (DaytimePeriodType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.begin==null && other.getBegin()==null) || 
             (this.begin!=null &&
              this.begin.equals(other.getBegin()))) &&
            ((this.end==null && other.getEnd()==null) || 
             (this.end!=null &&
              this.end.equals(other.getEnd()))) &&
            ((this.day==null && other.getDay()==null) || 
             (this.day!=null &&
              java.util.Arrays.equals(this.day, other.getDay()))) &&
            this.timeZone == other.getTimeZone();
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
        if (getBegin() != null) {
            _hashCode += getBegin().hashCode();
        }
        if (getEnd() != null) {
            _hashCode += getEnd().hashCode();
        }
        if (getDay() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDay());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDay(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getTimeZone()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DaytimePeriodType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "DaytimePeriodType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("begin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "Begin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "time"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "End"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "time"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("day");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "Day"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeZone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "TimeZone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
