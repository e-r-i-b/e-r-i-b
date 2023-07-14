/**
 * DepoSecurityOperationList_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Планируемая операция над ценной бумагой
 */
public class DepoSecurityOperationList_Type  implements java.io.Serializable {
    /* Наименование операции */
    private com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationType_Type[] operName;

    /* Наименование операции (в виде текста) */
    private java.lang.String customOperName;

    public DepoSecurityOperationList_Type() {
    }

    public DepoSecurityOperationList_Type(
           com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationType_Type[] operName,
           java.lang.String customOperName) {
           this.operName = operName;
           this.customOperName = customOperName;
    }


    /**
     * Gets the operName value for this DepoSecurityOperationList_Type.
     * 
     * @return operName   * Наименование операции
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationType_Type[] getOperName() {
        return operName;
    }


    /**
     * Sets the operName value for this DepoSecurityOperationList_Type.
     * 
     * @param operName   * Наименование операции
     */
    public void setOperName(com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationType_Type[] operName) {
        this.operName = operName;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationType_Type getOperName(int i) {
        return this.operName[i];
    }

    public void setOperName(int i, com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationType_Type _value) {
        this.operName[i] = _value;
    }


    /**
     * Gets the customOperName value for this DepoSecurityOperationList_Type.
     * 
     * @return customOperName   * Наименование операции (в виде текста)
     */
    public java.lang.String getCustomOperName() {
        return customOperName;
    }


    /**
     * Sets the customOperName value for this DepoSecurityOperationList_Type.
     * 
     * @param customOperName   * Наименование операции (в виде текста)
     */
    public void setCustomOperName(java.lang.String customOperName) {
        this.customOperName = customOperName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoSecurityOperationList_Type)) return false;
        DepoSecurityOperationList_Type other = (DepoSecurityOperationList_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operName==null && other.getOperName()==null) || 
             (this.operName!=null &&
              java.util.Arrays.equals(this.operName, other.getOperName()))) &&
            ((this.customOperName==null && other.getCustomOperName()==null) || 
             (this.customOperName!=null &&
              this.customOperName.equals(other.getCustomOperName())));
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
        if (getOperName() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOperName());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOperName(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCustomOperName() != null) {
            _hashCode += getCustomOperName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoSecurityOperationList_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationList_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customOperName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustomOperName"));
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
