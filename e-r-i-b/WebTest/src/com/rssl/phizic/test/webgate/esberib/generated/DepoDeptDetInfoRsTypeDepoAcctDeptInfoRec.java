/**
 * DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Запись о счете депо
 */
public class DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.DepoDeptResZad_Type[] depoDeptRes;

    public DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec() {
    }

    public DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec(
           com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.DepoDeptResZad_Type[] depoDeptRes) {
           this.depoAcctId = depoAcctId;
           this.depoDeptRes = depoDeptRes;
    }


    /**
     * Gets the depoAcctId value for this DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec.
     * 
     * @return depoAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type getDepoAcctId() {
        return depoAcctId;
    }


    /**
     * Sets the depoAcctId value for this DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec.
     * 
     * @param depoAcctId
     */
    public void setDepoAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId) {
        this.depoAcctId = depoAcctId;
    }


    /**
     * Gets the depoDeptRes value for this DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec.
     * 
     * @return depoDeptRes
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoDeptResZad_Type[] getDepoDeptRes() {
        return depoDeptRes;
    }


    /**
     * Sets the depoDeptRes value for this DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec.
     * 
     * @param depoDeptRes
     */
    public void setDepoDeptRes(com.rssl.phizic.test.webgate.esberib.generated.DepoDeptResZad_Type[] depoDeptRes) {
        this.depoDeptRes = depoDeptRes;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.DepoDeptResZad_Type getDepoDeptRes(int i) {
        return this.depoDeptRes[i];
    }

    public void setDepoDeptRes(int i, com.rssl.phizic.test.webgate.esberib.generated.DepoDeptResZad_Type _value) {
        this.depoDeptRes[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec)) return false;
        DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec other = (DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depoAcctId==null && other.getDepoAcctId()==null) || 
             (this.depoAcctId!=null &&
              this.depoAcctId.equals(other.getDepoAcctId()))) &&
            ((this.depoDeptRes==null && other.getDepoDeptRes()==null) || 
             (this.depoDeptRes!=null &&
              java.util.Arrays.equals(this.depoDeptRes, other.getDepoDeptRes())));
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
        if (getDepoAcctId() != null) {
            _hashCode += getDepoAcctId().hashCode();
        }
        if (getDepoDeptRes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepoDeptRes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepoDeptRes(), i);
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
        new org.apache.axis.description.TypeDesc(DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoDeptDetInfoRsType>DepoAcctDeptInfoRec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoDeptRes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptRes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptResZad_Type"));
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
