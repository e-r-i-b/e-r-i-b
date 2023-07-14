/**
 * OutcomeMailListDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;


/**
 * Данные исходящего письма
 */
public class OutcomeMailListDataType  extends com.rssl.phizic.csaadmin.listeners.generated.MailListDataTypeBase  implements java.io.Serializable {
    private java.lang.String recipientFIO;

    private java.lang.Long recipientId;

    public OutcomeMailListDataType() {
    }

    public OutcomeMailListDataType(
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
           java.lang.String recipientFIO,
           java.lang.Long recipientId) {
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
        this.recipientFIO = recipientFIO;
        this.recipientId = recipientId;
    }


    /**
     * Gets the recipientFIO value for this OutcomeMailListDataType.
     * 
     * @return recipientFIO
     */
    public java.lang.String getRecipientFIO() {
        return recipientFIO;
    }


    /**
     * Sets the recipientFIO value for this OutcomeMailListDataType.
     * 
     * @param recipientFIO
     */
    public void setRecipientFIO(java.lang.String recipientFIO) {
        this.recipientFIO = recipientFIO;
    }


    /**
     * Gets the recipientId value for this OutcomeMailListDataType.
     * 
     * @return recipientId
     */
    public java.lang.Long getRecipientId() {
        return recipientId;
    }


    /**
     * Sets the recipientId value for this OutcomeMailListDataType.
     * 
     * @param recipientId
     */
    public void setRecipientId(java.lang.Long recipientId) {
        this.recipientId = recipientId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OutcomeMailListDataType)) return false;
        OutcomeMailListDataType other = (OutcomeMailListDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.recipientFIO==null && other.getRecipientFIO()==null) || 
             (this.recipientFIO!=null &&
              this.recipientFIO.equals(other.getRecipientFIO()))) &&
            ((this.recipientId==null && other.getRecipientId()==null) || 
             (this.recipientId!=null &&
              this.recipientId.equals(other.getRecipientId())));
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
        if (getRecipientFIO() != null) {
            _hashCode += getRecipientFIO().hashCode();
        }
        if (getRecipientId() != null) {
            _hashCode += getRecipientId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OutcomeMailListDataType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "OutcomeMailListDataType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientFIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "recipientFIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "recipientId"));
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
