/**
 * WriteDownOperation_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип для микрооперации списания
 */
public class WriteDownOperation_Type  implements java.io.Serializable {
    /* Краткое наименование микрооперации */
    private java.lang.String operationName;

    /* Сумма микрооперации */
    private java.math.BigDecimal curAmt;

    /* Признак списания/зачисления */
    private com.rssl.phizicgate.esberibgate.ws.generated.Turnover_Type turnover;

    public WriteDownOperation_Type() {
    }

    public WriteDownOperation_Type(
           java.lang.String operationName,
           java.math.BigDecimal curAmt,
           com.rssl.phizicgate.esberibgate.ws.generated.Turnover_Type turnover) {
           this.operationName = operationName;
           this.curAmt = curAmt;
           this.turnover = turnover;
    }


    /**
     * Gets the operationName value for this WriteDownOperation_Type.
     * 
     * @return operationName   * Краткое наименование микрооперации
     */
    public java.lang.String getOperationName() {
        return operationName;
    }


    /**
     * Sets the operationName value for this WriteDownOperation_Type.
     * 
     * @param operationName   * Краткое наименование микрооперации
     */
    public void setOperationName(java.lang.String operationName) {
        this.operationName = operationName;
    }


    /**
     * Gets the curAmt value for this WriteDownOperation_Type.
     * 
     * @return curAmt   * Сумма микрооперации
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this WriteDownOperation_Type.
     * 
     * @param curAmt   * Сумма микрооперации
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the turnover value for this WriteDownOperation_Type.
     * 
     * @return turnover   * Признак списания/зачисления
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Turnover_Type getTurnover() {
        return turnover;
    }


    /**
     * Sets the turnover value for this WriteDownOperation_Type.
     * 
     * @param turnover   * Признак списания/зачисления
     */
    public void setTurnover(com.rssl.phizicgate.esberibgate.ws.generated.Turnover_Type turnover) {
        this.turnover = turnover;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WriteDownOperation_Type)) return false;
        WriteDownOperation_Type other = (WriteDownOperation_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operationName==null && other.getOperationName()==null) || 
             (this.operationName!=null &&
              this.operationName.equals(other.getOperationName()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.turnover==null && other.getTurnover()==null) || 
             (this.turnover!=null &&
              this.turnover.equals(other.getTurnover())));
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
        if (getOperationName() != null) {
            _hashCode += getOperationName().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getTurnover() != null) {
            _hashCode += getTurnover().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WriteDownOperation_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WriteDownOperation_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("turnover");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Turnover"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Turnover_Type"));
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
