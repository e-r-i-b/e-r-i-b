/**
 * Page_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Параметры страницы
 */
public class Page_Type  implements java.io.Serializable {
    /* Максимальное кол-во записей в ответе */
    private java.math.BigInteger pageSize;

    /* Максимальное кол-во записей в ответе */
    private java.math.BigInteger startRowNum;

    /* Включить в ответ общее кол-во записей */
    private java.lang.Boolean recordCountNeeded;

    public Page_Type() {
    }

    public Page_Type(
           java.math.BigInteger pageSize,
           java.math.BigInteger startRowNum,
           java.lang.Boolean recordCountNeeded) {
           this.pageSize = pageSize;
           this.startRowNum = startRowNum;
           this.recordCountNeeded = recordCountNeeded;
    }


    /**
     * Gets the pageSize value for this Page_Type.
     * 
     * @return pageSize   * Максимальное кол-во записей в ответе
     */
    public java.math.BigInteger getPageSize() {
        return pageSize;
    }


    /**
     * Sets the pageSize value for this Page_Type.
     * 
     * @param pageSize   * Максимальное кол-во записей в ответе
     */
    public void setPageSize(java.math.BigInteger pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * Gets the startRowNum value for this Page_Type.
     * 
     * @return startRowNum   * Максимальное кол-во записей в ответе
     */
    public java.math.BigInteger getStartRowNum() {
        return startRowNum;
    }


    /**
     * Sets the startRowNum value for this Page_Type.
     * 
     * @param startRowNum   * Максимальное кол-во записей в ответе
     */
    public void setStartRowNum(java.math.BigInteger startRowNum) {
        this.startRowNum = startRowNum;
    }


    /**
     * Gets the recordCountNeeded value for this Page_Type.
     * 
     * @return recordCountNeeded   * Включить в ответ общее кол-во записей
     */
    public java.lang.Boolean getRecordCountNeeded() {
        return recordCountNeeded;
    }


    /**
     * Sets the recordCountNeeded value for this Page_Type.
     * 
     * @param recordCountNeeded   * Включить в ответ общее кол-во записей
     */
    public void setRecordCountNeeded(java.lang.Boolean recordCountNeeded) {
        this.recordCountNeeded = recordCountNeeded;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Page_Type)) return false;
        Page_Type other = (Page_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pageSize==null && other.getPageSize()==null) || 
             (this.pageSize!=null &&
              this.pageSize.equals(other.getPageSize()))) &&
            ((this.startRowNum==null && other.getStartRowNum()==null) || 
             (this.startRowNum!=null &&
              this.startRowNum.equals(other.getStartRowNum()))) &&
            ((this.recordCountNeeded==null && other.getRecordCountNeeded()==null) || 
             (this.recordCountNeeded!=null &&
              this.recordCountNeeded.equals(other.getRecordCountNeeded())));
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
        if (getPageSize() != null) {
            _hashCode += getPageSize().hashCode();
        }
        if (getStartRowNum() != null) {
            _hashCode += getStartRowNum().hashCode();
        }
        if (getRecordCountNeeded() != null) {
            _hashCode += getRecordCountNeeded().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Page_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Page_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PageSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startRowNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartRowNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordCountNeeded");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecordCountNeeded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
