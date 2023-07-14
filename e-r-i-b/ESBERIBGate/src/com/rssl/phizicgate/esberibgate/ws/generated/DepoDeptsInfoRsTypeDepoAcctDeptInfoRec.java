/**
 * DepoDeptsInfoRsTypeDepoAcctDeptInfoRec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись о счете депо
 */
public class DepoDeptsInfoRsTypeDepoAcctDeptInfoRec  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoDeptRes_Type depoDeptRes;

    public DepoDeptsInfoRsTypeDepoAcctDeptInfoRec() {
    }

    public DepoDeptsInfoRsTypeDepoAcctDeptInfoRec(
           com.rssl.phizicgate.esberibgate.ws.generated.DepoDeptRes_Type depoDeptRes) {
           this.depoDeptRes = depoDeptRes;
    }


    /**
     * Gets the depoDeptRes value for this DepoDeptsInfoRsTypeDepoAcctDeptInfoRec.
     * 
     * @return depoDeptRes
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoDeptRes_Type getDepoDeptRes() {
        return depoDeptRes;
    }


    /**
     * Sets the depoDeptRes value for this DepoDeptsInfoRsTypeDepoAcctDeptInfoRec.
     * 
     * @param depoDeptRes
     */
    public void setDepoDeptRes(com.rssl.phizicgate.esberibgate.ws.generated.DepoDeptRes_Type depoDeptRes) {
        this.depoDeptRes = depoDeptRes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoDeptsInfoRsTypeDepoAcctDeptInfoRec)) return false;
        DepoDeptsInfoRsTypeDepoAcctDeptInfoRec other = (DepoDeptsInfoRsTypeDepoAcctDeptInfoRec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depoDeptRes==null && other.getDepoDeptRes()==null) || 
             (this.depoDeptRes!=null &&
              this.depoDeptRes.equals(other.getDepoDeptRes())));
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
        if (getDepoDeptRes() != null) {
            _hashCode += getDepoDeptRes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoDeptsInfoRsTypeDepoAcctDeptInfoRec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoDeptsInfoRsType>DepoAcctDeptInfoRec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoDeptRes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptRes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptRes_Type"));
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
