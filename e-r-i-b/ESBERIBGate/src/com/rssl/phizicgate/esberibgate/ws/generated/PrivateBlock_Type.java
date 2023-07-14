/**
 * PrivateBlock_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Дополнительная информация
 */
public class PrivateBlock_Type  implements java.io.Serializable {
    /* Дополнительная информация */
    private java.lang.String addInfo1;

    /* Дополнительная информация */
    private java.lang.String addInfo2;

    /* Дополнительная информация */
    private java.lang.String addInfo3;

    public PrivateBlock_Type() {
    }

    public PrivateBlock_Type(
           java.lang.String addInfo1,
           java.lang.String addInfo2,
           java.lang.String addInfo3) {
           this.addInfo1 = addInfo1;
           this.addInfo2 = addInfo2;
           this.addInfo3 = addInfo3;
    }


    /**
     * Gets the addInfo1 value for this PrivateBlock_Type.
     * 
     * @return addInfo1   * Дополнительная информация
     */
    public java.lang.String getAddInfo1() {
        return addInfo1;
    }


    /**
     * Sets the addInfo1 value for this PrivateBlock_Type.
     * 
     * @param addInfo1   * Дополнительная информация
     */
    public void setAddInfo1(java.lang.String addInfo1) {
        this.addInfo1 = addInfo1;
    }


    /**
     * Gets the addInfo2 value for this PrivateBlock_Type.
     * 
     * @return addInfo2   * Дополнительная информация
     */
    public java.lang.String getAddInfo2() {
        return addInfo2;
    }


    /**
     * Sets the addInfo2 value for this PrivateBlock_Type.
     * 
     * @param addInfo2   * Дополнительная информация
     */
    public void setAddInfo2(java.lang.String addInfo2) {
        this.addInfo2 = addInfo2;
    }


    /**
     * Gets the addInfo3 value for this PrivateBlock_Type.
     * 
     * @return addInfo3   * Дополнительная информация
     */
    public java.lang.String getAddInfo3() {
        return addInfo3;
    }


    /**
     * Sets the addInfo3 value for this PrivateBlock_Type.
     * 
     * @param addInfo3   * Дополнительная информация
     */
    public void setAddInfo3(java.lang.String addInfo3) {
        this.addInfo3 = addInfo3;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PrivateBlock_Type)) return false;
        PrivateBlock_Type other = (PrivateBlock_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addInfo1==null && other.getAddInfo1()==null) || 
             (this.addInfo1!=null &&
              this.addInfo1.equals(other.getAddInfo1()))) &&
            ((this.addInfo2==null && other.getAddInfo2()==null) || 
             (this.addInfo2!=null &&
              this.addInfo2.equals(other.getAddInfo2()))) &&
            ((this.addInfo3==null && other.getAddInfo3()==null) || 
             (this.addInfo3!=null &&
              this.addInfo3.equals(other.getAddInfo3())));
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
        if (getAddInfo1() != null) {
            _hashCode += getAddInfo1().hashCode();
        }
        if (getAddInfo2() != null) {
            _hashCode += getAddInfo2().hashCode();
        }
        if (getAddInfo3() != null) {
            _hashCode += getAddInfo3().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PrivateBlock_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateBlock_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addInfo1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddInfo1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addInfo2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddInfo2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addInfo3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddInfo3"));
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
