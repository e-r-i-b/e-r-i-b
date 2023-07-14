/**
 * IFXRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.mdm.generated;


/**
 * Список типов данных сообщений для выполнения запросов
 */
public class IFXRq_Type  implements java.io.Serializable {
    private com.rssl.phizic.ws.esberiblistener.mdm.generated.MDMClientInfoUpdateRq_Type MDMClientInfoUpdateRq;

    public IFXRq_Type() {
    }

    public IFXRq_Type(
           com.rssl.phizic.ws.esberiblistener.mdm.generated.MDMClientInfoUpdateRq_Type MDMClientInfoUpdateRq) {
           this.MDMClientInfoUpdateRq = MDMClientInfoUpdateRq;
    }


    /**
     * Gets the MDMClientInfoUpdateRq value for this IFXRq_Type.
     * 
     * @return MDMClientInfoUpdateRq
     */
    public com.rssl.phizic.ws.esberiblistener.mdm.generated.MDMClientInfoUpdateRq_Type getMDMClientInfoUpdateRq() {
        return MDMClientInfoUpdateRq;
    }


    /**
     * Sets the MDMClientInfoUpdateRq value for this IFXRq_Type.
     * 
     * @param MDMClientInfoUpdateRq
     */
    public void setMDMClientInfoUpdateRq(com.rssl.phizic.ws.esberiblistener.mdm.generated.MDMClientInfoUpdateRq_Type MDMClientInfoUpdateRq) {
        this.MDMClientInfoUpdateRq = MDMClientInfoUpdateRq;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IFXRq_Type)) return false;
        IFXRq_Type other = (IFXRq_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.MDMClientInfoUpdateRq==null && other.getMDMClientInfoUpdateRq()==null) || 
             (this.MDMClientInfoUpdateRq!=null &&
              this.MDMClientInfoUpdateRq.equals(other.getMDMClientInfoUpdateRq())));
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
        if (getMDMClientInfoUpdateRq() != null) {
            _hashCode += getMDMClientInfoUpdateRq().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IFXRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IFXRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MDMClientInfoUpdateRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "MDMClientInfoUpdateRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "MDMClientInfoUpdateRq_Type"));
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
