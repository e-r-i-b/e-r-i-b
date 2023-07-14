/**
 * SrcLayoutInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип для разбивки микроопераций списания
 */
public class SrcLayoutInfo_Type  implements java.io.Serializable {
    /* Признак расчёта операции в депозитной системе */
    private java.lang.Boolean isCalcOperation;

    /* Микрооперация списания */
    private com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type[] writeDownOperation;

    public SrcLayoutInfo_Type() {
    }

    public SrcLayoutInfo_Type(
           java.lang.Boolean isCalcOperation,
           com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type[] writeDownOperation) {
           this.isCalcOperation = isCalcOperation;
           this.writeDownOperation = writeDownOperation;
    }


    /**
     * Gets the isCalcOperation value for this SrcLayoutInfo_Type.
     * 
     * @return isCalcOperation   * Признак расчёта операции в депозитной системе
     */
    public java.lang.Boolean getIsCalcOperation() {
        return isCalcOperation;
    }


    /**
     * Sets the isCalcOperation value for this SrcLayoutInfo_Type.
     * 
     * @param isCalcOperation   * Признак расчёта операции в депозитной системе
     */
    public void setIsCalcOperation(java.lang.Boolean isCalcOperation) {
        this.isCalcOperation = isCalcOperation;
    }


    /**
     * Gets the writeDownOperation value for this SrcLayoutInfo_Type.
     * 
     * @return writeDownOperation   * Микрооперация списания
     */
    public com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type[] getWriteDownOperation() {
        return writeDownOperation;
    }


    /**
     * Sets the writeDownOperation value for this SrcLayoutInfo_Type.
     * 
     * @param writeDownOperation   * Микрооперация списания
     */
    public void setWriteDownOperation(com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type[] writeDownOperation) {
        this.writeDownOperation = writeDownOperation;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type getWriteDownOperation(int i) {
        return this.writeDownOperation[i];
    }

    public void setWriteDownOperation(int i, com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type _value) {
        this.writeDownOperation[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SrcLayoutInfo_Type)) return false;
        SrcLayoutInfo_Type other = (SrcLayoutInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.isCalcOperation==null && other.getIsCalcOperation()==null) || 
             (this.isCalcOperation!=null &&
              this.isCalcOperation.equals(other.getIsCalcOperation()))) &&
            ((this.writeDownOperation==null && other.getWriteDownOperation()==null) || 
             (this.writeDownOperation!=null &&
              java.util.Arrays.equals(this.writeDownOperation, other.getWriteDownOperation())));
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
        if (getIsCalcOperation() != null) {
            _hashCode += getIsCalcOperation().hashCode();
        }
        if (getWriteDownOperation() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getWriteDownOperation());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getWriteDownOperation(), i);
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
        new org.apache.axis.description.TypeDesc(SrcLayoutInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isCalcOperation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsCalcOperation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("writeDownOperation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WriteDownOperation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WriteDownOperation_Type"));
        elemField.setMinOccurs(0);
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
