/**
 * Element_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Группирующий тег для передачи данных о значении ячеек
 */
public class Element_Type  implements java.io.Serializable {
    /* Значение ячейки */
    private java.lang.String value;

    /* Номер столбца в таблице */
    private java.math.BigInteger columnIndex;

    /* Номер строки в таблице */
    private java.math.BigInteger rowIndex;

    public Element_Type() {
    }

    public Element_Type(
           java.lang.String value,
           java.math.BigInteger columnIndex,
           java.math.BigInteger rowIndex) {
           this.value = value;
           this.columnIndex = columnIndex;
           this.rowIndex = rowIndex;
    }


    /**
     * Gets the value value for this Element_Type.
     * 
     * @return value   * Значение ячейки
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this Element_Type.
     * 
     * @param value   * Значение ячейки
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }


    /**
     * Gets the columnIndex value for this Element_Type.
     * 
     * @return columnIndex   * Номер столбца в таблице
     */
    public java.math.BigInteger getColumnIndex() {
        return columnIndex;
    }


    /**
     * Sets the columnIndex value for this Element_Type.
     * 
     * @param columnIndex   * Номер столбца в таблице
     */
    public void setColumnIndex(java.math.BigInteger columnIndex) {
        this.columnIndex = columnIndex;
    }


    /**
     * Gets the rowIndex value for this Element_Type.
     * 
     * @return rowIndex   * Номер строки в таблице
     */
    public java.math.BigInteger getRowIndex() {
        return rowIndex;
    }


    /**
     * Sets the rowIndex value for this Element_Type.
     * 
     * @param rowIndex   * Номер строки в таблице
     */
    public void setRowIndex(java.math.BigInteger rowIndex) {
        this.rowIndex = rowIndex;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Element_Type)) return false;
        Element_Type other = (Element_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue()))) &&
            ((this.columnIndex==null && other.getColumnIndex()==null) || 
             (this.columnIndex!=null &&
              this.columnIndex.equals(other.getColumnIndex()))) &&
            ((this.rowIndex==null && other.getRowIndex()==null) || 
             (this.rowIndex!=null &&
              this.rowIndex.equals(other.getRowIndex())));
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
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        if (getColumnIndex() != null) {
            _hashCode += getColumnIndex().hashCode();
        }
        if (getRowIndex() != null) {
            _hashCode += getRowIndex().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Element_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Element_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("columnIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ColumnIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rowIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RowIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
