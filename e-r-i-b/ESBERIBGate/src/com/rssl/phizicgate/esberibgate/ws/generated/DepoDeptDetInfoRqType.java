/**
 * DepoDeptDetInfoRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Получение детальной информации по задолженности по счету ДЕПО
 */
public class DepoDeptDetInfoRqType  extends com.rssl.phizicgate.esberibgate.ws.generated.DepoAccInfoRqType  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.DeptId_Type[] deptId;

    public DepoDeptDetInfoRqType() {
    }

    public DepoDeptDetInfoRqType(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type[] depoAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.OperInfo_Type operInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.DeptId_Type[] deptId) {
        super(
            rqUID,
            rqTm,
            operUID,
            SPName,
            bankInfo,
            depoAcctId,
            operInfo);
        this.deptId = deptId;
    }


    /**
     * Gets the deptId value for this DepoDeptDetInfoRqType.
     * 
     * @return deptId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DeptId_Type[] getDeptId() {
        return deptId;
    }


    /**
     * Sets the deptId value for this DepoDeptDetInfoRqType.
     * 
     * @param deptId
     */
    public void setDeptId(com.rssl.phizicgate.esberibgate.ws.generated.DeptId_Type[] deptId) {
        this.deptId = deptId;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DeptId_Type getDeptId(int i) {
        return this.deptId[i];
    }

    public void setDeptId(int i, com.rssl.phizicgate.esberibgate.ws.generated.DeptId_Type _value) {
        this.deptId[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoDeptDetInfoRqType)) return false;
        DepoDeptDetInfoRqType other = (DepoDeptDetInfoRqType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.deptId==null && other.getDeptId()==null) || 
             (this.deptId!=null &&
              java.util.Arrays.equals(this.deptId, other.getDeptId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDeptId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeptId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeptId(), i);
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
        new org.apache.axis.description.TypeDesc(DepoDeptDetInfoRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptDetInfoRqType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deptId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptId_Type"));
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
