/**
 * IncomeMailListDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;


/**
 * Данные входящего письма
 */
public class IncomeMailListDataType  extends com.rssl.phizicgate.csaadmin.service.generated.MailListDataTypeBase  implements java.io.Serializable {
    private java.lang.String senderFIO;

    private java.lang.Long senderId;

    public IncomeMailListDataType() {
    }

    public IncomeMailListDataType(
           long nodeId,
           long id,
           java.lang.Long number,
           java.lang.String creationDate,
           java.lang.String responseMethod,
           java.lang.String state,
           java.lang.String stateDescription,
           java.lang.String type,
           java.lang.String typeDescription,
           java.lang.String theme,
           java.lang.String subject,
           java.lang.String employeeFIO,
           java.lang.String employeeUserId,
           java.lang.String tb,
           java.lang.String area,
           java.lang.String senderFIO,
           java.lang.Long senderId) {
        super(
            nodeId,
            id,
            number,
            creationDate,
            responseMethod,
            state,
            stateDescription,
            type,
            typeDescription,
            theme,
            subject,
            employeeFIO,
            employeeUserId,
            tb,
            area);
        this.senderFIO = senderFIO;
        this.senderId = senderId;
    }


    /**
     * Gets the senderFIO value for this IncomeMailListDataType.
     * 
     * @return senderFIO
     */
    public java.lang.String getSenderFIO() {
        return senderFIO;
    }


    /**
     * Sets the senderFIO value for this IncomeMailListDataType.
     * 
     * @param senderFIO
     */
    public void setSenderFIO(java.lang.String senderFIO) {
        this.senderFIO = senderFIO;
    }


    /**
     * Gets the senderId value for this IncomeMailListDataType.
     * 
     * @return senderId
     */
    public java.lang.Long getSenderId() {
        return senderId;
    }


    /**
     * Sets the senderId value for this IncomeMailListDataType.
     * 
     * @param senderId
     */
    public void setSenderId(java.lang.Long senderId) {
        this.senderId = senderId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IncomeMailListDataType)) return false;
        IncomeMailListDataType other = (IncomeMailListDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.senderFIO==null && other.getSenderFIO()==null) || 
             (this.senderFIO!=null &&
              this.senderFIO.equals(other.getSenderFIO()))) &&
            ((this.senderId==null && other.getSenderId()==null) || 
             (this.senderId!=null &&
              this.senderId.equals(other.getSenderId())));
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
        if (getSenderFIO() != null) {
            _hashCode += getSenderFIO().hashCode();
        }
        if (getSenderId() != null) {
            _hashCode += getSenderId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IncomeMailListDataType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "IncomeMailListDataType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderFIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "senderFIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "senderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
