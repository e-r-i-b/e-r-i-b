/**
 * DepoDeptResZad_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись о задолженности на счете ДЕПО
 */
public class DepoDeptResZad_Type  implements java.io.Serializable {
    /* Номер выставленного счета */
    private java.lang.String recNumber;

    /* Дата выставления счета */
    private java.lang.String effDt;

    private com.rssl.phizicgate.esberibgate.ws.generated.DeptRec_Type deptRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    public DepoDeptResZad_Type() {
    }

    public DepoDeptResZad_Type(
           java.lang.String recNumber,
           java.lang.String effDt,
           com.rssl.phizicgate.esberibgate.ws.generated.DeptRec_Type deptRec,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
           this.recNumber = recNumber;
           this.effDt = effDt;
           this.deptRec = deptRec;
           this.status = status;
    }


    /**
     * Gets the recNumber value for this DepoDeptResZad_Type.
     * 
     * @return recNumber   * Номер выставленного счета
     */
    public java.lang.String getRecNumber() {
        return recNumber;
    }


    /**
     * Sets the recNumber value for this DepoDeptResZad_Type.
     * 
     * @param recNumber   * Номер выставленного счета
     */
    public void setRecNumber(java.lang.String recNumber) {
        this.recNumber = recNumber;
    }


    /**
     * Gets the effDt value for this DepoDeptResZad_Type.
     * 
     * @return effDt   * Дата выставления счета
     */
    public java.lang.String getEffDt() {
        return effDt;
    }


    /**
     * Sets the effDt value for this DepoDeptResZad_Type.
     * 
     * @param effDt   * Дата выставления счета
     */
    public void setEffDt(java.lang.String effDt) {
        this.effDt = effDt;
    }


    /**
     * Gets the deptRec value for this DepoDeptResZad_Type.
     * 
     * @return deptRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DeptRec_Type getDeptRec() {
        return deptRec;
    }


    /**
     * Sets the deptRec value for this DepoDeptResZad_Type.
     * 
     * @param deptRec
     */
    public void setDeptRec(com.rssl.phizicgate.esberibgate.ws.generated.DeptRec_Type deptRec) {
        this.deptRec = deptRec;
    }


    /**
     * Gets the status value for this DepoDeptResZad_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepoDeptResZad_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoDeptResZad_Type)) return false;
        DepoDeptResZad_Type other = (DepoDeptResZad_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recNumber==null && other.getRecNumber()==null) || 
             (this.recNumber!=null &&
              this.recNumber.equals(other.getRecNumber()))) &&
            ((this.effDt==null && other.getEffDt()==null) || 
             (this.effDt!=null &&
              this.effDt.equals(other.getEffDt()))) &&
            ((this.deptRec==null && other.getDeptRec()==null) || 
             (this.deptRec!=null &&
              this.deptRec.equals(other.getDeptRec()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getRecNumber() != null) {
            _hashCode += getRecNumber().hashCode();
        }
        if (getEffDt() != null) {
            _hashCode += getEffDt().hashCode();
        }
        if (getDeptRec() != null) {
            _hashCode += getDeptRec().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoDeptResZad_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptResZad_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deptRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptRec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
