/**
 * DepoBankAccountAdditionalInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Доп. информация по клиентским счетам ДЕПО
 */
public class DepoBankAccountAdditionalInfo_Type  implements java.io.Serializable {
    /* Способ получения */
    private java.lang.String recIncomeMethod;

    /* Способ приема */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] recInstructionMethods;

    /* Способ передачи информации */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] recInfoMethods;

    public DepoBankAccountAdditionalInfo_Type() {
    }

    public DepoBankAccountAdditionalInfo_Type(
           java.lang.String recIncomeMethod,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] recInstructionMethods,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] recInfoMethods) {
           this.recIncomeMethod = recIncomeMethod;
           this.recInstructionMethods = recInstructionMethods;
           this.recInfoMethods = recInfoMethods;
    }


    /**
     * Gets the recIncomeMethod value for this DepoBankAccountAdditionalInfo_Type.
     * 
     * @return recIncomeMethod   * Способ получения
     */
    public java.lang.String getRecIncomeMethod() {
        return recIncomeMethod;
    }


    /**
     * Sets the recIncomeMethod value for this DepoBankAccountAdditionalInfo_Type.
     * 
     * @param recIncomeMethod   * Способ получения
     */
    public void setRecIncomeMethod(java.lang.String recIncomeMethod) {
        this.recIncomeMethod = recIncomeMethod;
    }


    /**
     * Gets the recInstructionMethods value for this DepoBankAccountAdditionalInfo_Type.
     * 
     * @return recInstructionMethods   * Способ приема
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] getRecInstructionMethods() {
        return recInstructionMethods;
    }


    /**
     * Sets the recInstructionMethods value for this DepoBankAccountAdditionalInfo_Type.
     * 
     * @param recInstructionMethods   * Способ приема
     */
    public void setRecInstructionMethods(com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] recInstructionMethods) {
        this.recInstructionMethods = recInstructionMethods;
    }


    /**
     * Gets the recInfoMethods value for this DepoBankAccountAdditionalInfo_Type.
     * 
     * @return recInfoMethods   * Способ передачи информации
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] getRecInfoMethods() {
        return recInfoMethods;
    }


    /**
     * Sets the recInfoMethods value for this DepoBankAccountAdditionalInfo_Type.
     * 
     * @param recInfoMethods   * Способ передачи информации
     */
    public void setRecInfoMethods(com.rssl.phizicgate.esberibgate.ws.generated.DepoRecMethodr_Type[] recInfoMethods) {
        this.recInfoMethods = recInfoMethods;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoBankAccountAdditionalInfo_Type)) return false;
        DepoBankAccountAdditionalInfo_Type other = (DepoBankAccountAdditionalInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recIncomeMethod==null && other.getRecIncomeMethod()==null) || 
             (this.recIncomeMethod!=null &&
              this.recIncomeMethod.equals(other.getRecIncomeMethod()))) &&
            ((this.recInstructionMethods==null && other.getRecInstructionMethods()==null) || 
             (this.recInstructionMethods!=null &&
              java.util.Arrays.equals(this.recInstructionMethods, other.getRecInstructionMethods()))) &&
            ((this.recInfoMethods==null && other.getRecInfoMethods()==null) || 
             (this.recInfoMethods!=null &&
              java.util.Arrays.equals(this.recInfoMethods, other.getRecInfoMethods())));
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
        if (getRecIncomeMethod() != null) {
            _hashCode += getRecIncomeMethod().hashCode();
        }
        if (getRecInstructionMethods() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRecInstructionMethods());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRecInstructionMethods(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRecInfoMethods() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRecInfoMethods());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRecInfoMethods(), i);
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
        new org.apache.axis.description.TypeDesc(DepoBankAccountAdditionalInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccountAdditionalInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recIncomeMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecIncomeMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recInstructionMethods");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecInstructionMethods"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRecMethodr_Type"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecInstructionMethod"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recInfoMethods");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecInfoMethods"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRecMethodr_Type"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecInfoMethod"));
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
