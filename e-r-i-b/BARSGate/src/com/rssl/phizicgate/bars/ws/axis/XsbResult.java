/**
 * XsbResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class XsbResult  implements java.io.Serializable {
    private int errorId;

    private java.lang.String fullMsg;

    private int status;

    private java.lang.String refType;

    private int procCode;

    private int priority;

    public XsbResult() {
    }

    public XsbResult(
           int errorId,
           java.lang.String fullMsg,
           int status,
           java.lang.String refType,
           int procCode,
           int priority) {
           this.errorId = errorId;
           this.fullMsg = fullMsg;
           this.status = status;
           this.refType = refType;
           this.procCode = procCode;
           this.priority = priority;
    }


    /**
     * Gets the errorId value for this XsbResult.
     * 
     * @return errorId
     */
    public int getErrorId() {
        return errorId;
    }


    /**
     * Sets the errorId value for this XsbResult.
     * 
     * @param errorId
     */
    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }


    /**
     * Gets the fullMsg value for this XsbResult.
     * 
     * @return fullMsg
     */
    public java.lang.String getFullMsg() {
        return fullMsg;
    }


    /**
     * Sets the fullMsg value for this XsbResult.
     * 
     * @param fullMsg
     */
    public void setFullMsg(java.lang.String fullMsg) {
        this.fullMsg = fullMsg;
    }


    /**
     * Gets the status value for this XsbResult.
     * 
     * @return status
     */
    public int getStatus() {
        return status;
    }


    /**
     * Sets the status value for this XsbResult.
     * 
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * Gets the refType value for this XsbResult.
     * 
     * @return refType
     */
    public java.lang.String getRefType() {
        return refType;
    }


    /**
     * Sets the refType value for this XsbResult.
     * 
     * @param refType
     */
    public void setRefType(java.lang.String refType) {
        this.refType = refType;
    }


    /**
     * Gets the procCode value for this XsbResult.
     * 
     * @return procCode
     */
    public int getProcCode() {
        return procCode;
    }


    /**
     * Sets the procCode value for this XsbResult.
     * 
     * @param procCode
     */
    public void setProcCode(int procCode) {
        this.procCode = procCode;
    }


    /**
     * Gets the priority value for this XsbResult.
     * 
     * @return priority
     */
    public int getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this XsbResult.
     * 
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XsbResult)) return false;
        XsbResult other = (XsbResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.errorId == other.getErrorId() &&
            ((this.fullMsg==null && other.getFullMsg()==null) || 
             (this.fullMsg!=null &&
              this.fullMsg.equals(other.getFullMsg()))) &&
            this.status == other.getStatus() &&
            ((this.refType==null && other.getRefType()==null) || 
             (this.refType!=null &&
              this.refType.equals(other.getRefType()))) &&
            this.procCode == other.getProcCode() &&
            this.priority == other.getPriority();
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
        _hashCode += getErrorId();
        if (getFullMsg() != null) {
            _hashCode += getFullMsg().hashCode();
        }
        _hashCode += getStatus();
        if (getRefType() != null) {
            _hashCode += getRefType().hashCode();
        }
        _hashCode += getProcCode();
        _hashCode += getPriority();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XsbResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fullMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "procCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
