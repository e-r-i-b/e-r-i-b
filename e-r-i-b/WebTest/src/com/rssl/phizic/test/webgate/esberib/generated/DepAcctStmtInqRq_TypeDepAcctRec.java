/**
 * DepAcctStmtInqRq_TypeDepAcctRec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class DepAcctStmtInqRq_TypeDepAcctRec  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId;

    public DepAcctStmtInqRq_TypeDepAcctRec() {
    }

    public DepAcctStmtInqRq_TypeDepAcctRec(
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId) {
           this.depAcctId = depAcctId;
    }


    /**
     * Gets the depAcctId value for this DepAcctStmtInqRq_TypeDepAcctRec.
     * 
     * @return depAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctId() {
        return depAcctId;
    }


    /**
     * Sets the depAcctId value for this DepAcctStmtInqRq_TypeDepAcctRec.
     * 
     * @param depAcctId
     */
    public void setDepAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId) {
        this.depAcctId = depAcctId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAcctStmtInqRq_TypeDepAcctRec)) return false;
        DepAcctStmtInqRq_TypeDepAcctRec other = (DepAcctStmtInqRq_TypeDepAcctRec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depAcctId==null && other.getDepAcctId()==null) || 
             (this.depAcctId!=null &&
              this.depAcctId.equals(other.getDepAcctId())));
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
        if (getDepAcctId() != null) {
            _hashCode += getDepAcctId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepAcctStmtInqRq_TypeDepAcctRec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepAcctStmtInqRq_Type>DepAcctRec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
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
