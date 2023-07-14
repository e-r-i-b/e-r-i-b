/**
 * RemovedMailListDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;


/**
 * Данные удаленного письма
 */
public class RemovedMailListDataType  extends com.rssl.phizicgate.csaadmin.service.generated.MailListDataTypeBase  implements java.io.Serializable {
    private java.lang.String recipientName;

    private java.lang.String directionDescription;

    public RemovedMailListDataType() {
    }

    public RemovedMailListDataType(
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
           java.lang.String recipientName,
           java.lang.String directionDescription) {
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
        this.recipientName = recipientName;
        this.directionDescription = directionDescription;
    }


    /**
     * Gets the recipientName value for this RemovedMailListDataType.
     * 
     * @return recipientName
     */
    public java.lang.String getRecipientName() {
        return recipientName;
    }


    /**
     * Sets the recipientName value for this RemovedMailListDataType.
     * 
     * @param recipientName
     */
    public void setRecipientName(java.lang.String recipientName) {
        this.recipientName = recipientName;
    }


    /**
     * Gets the directionDescription value for this RemovedMailListDataType.
     * 
     * @return directionDescription
     */
    public java.lang.String getDirectionDescription() {
        return directionDescription;
    }


    /**
     * Sets the directionDescription value for this RemovedMailListDataType.
     * 
     * @param directionDescription
     */
    public void setDirectionDescription(java.lang.String directionDescription) {
        this.directionDescription = directionDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemovedMailListDataType)) return false;
        RemovedMailListDataType other = (RemovedMailListDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.recipientName==null && other.getRecipientName()==null) || 
             (this.recipientName!=null &&
              this.recipientName.equals(other.getRecipientName()))) &&
            ((this.directionDescription==null && other.getDirectionDescription()==null) || 
             (this.directionDescription!=null &&
              this.directionDescription.equals(other.getDirectionDescription())));
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
        if (getRecipientName() != null) {
            _hashCode += getRecipientName().hashCode();
        }
        if (getDirectionDescription() != null) {
            _hashCode += getDirectionDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemovedMailListDataType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RemovedMailListDataType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "recipientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("directionDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "directionDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
