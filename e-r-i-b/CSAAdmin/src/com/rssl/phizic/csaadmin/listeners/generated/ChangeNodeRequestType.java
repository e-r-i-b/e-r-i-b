/**
 * ChangeNodeRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class ChangeNodeRequestType  implements java.io.Serializable {
    private long nodeId;

    private java.lang.String action;

    private com.rssl.phizic.csaadmin.listeners.generated.MapEntryType[] parameters;

    public ChangeNodeRequestType() {
    }

    public ChangeNodeRequestType(
           long nodeId,
           java.lang.String action,
           com.rssl.phizic.csaadmin.listeners.generated.MapEntryType[] parameters) {
           this.nodeId = nodeId;
           this.action = action;
           this.parameters = parameters;
    }


    /**
     * Gets the nodeId value for this ChangeNodeRequestType.
     * 
     * @return nodeId
     */
    public long getNodeId() {
        return nodeId;
    }


    /**
     * Sets the nodeId value for this ChangeNodeRequestType.
     * 
     * @param nodeId
     */
    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }


    /**
     * Gets the action value for this ChangeNodeRequestType.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this ChangeNodeRequestType.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the parameters value for this ChangeNodeRequestType.
     * 
     * @return parameters
     */
    public com.rssl.phizic.csaadmin.listeners.generated.MapEntryType[] getParameters() {
        return parameters;
    }


    /**
     * Sets the parameters value for this ChangeNodeRequestType.
     * 
     * @param parameters
     */
    public void setParameters(com.rssl.phizic.csaadmin.listeners.generated.MapEntryType[] parameters) {
        this.parameters = parameters;
    }

    public com.rssl.phizic.csaadmin.listeners.generated.MapEntryType getParameters(int i) {
        return this.parameters[i];
    }

    public void setParameters(int i, com.rssl.phizic.csaadmin.listeners.generated.MapEntryType _value) {
        this.parameters[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangeNodeRequestType)) return false;
        ChangeNodeRequestType other = (ChangeNodeRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nodeId == other.getNodeId() &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.parameters==null && other.getParameters()==null) || 
             (this.parameters!=null &&
              java.util.Arrays.equals(this.parameters, other.getParameters())));
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
        _hashCode += new Long(getNodeId()).hashCode();
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParameters(), i);
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
        new org.apache.axis.description.TypeDesc(ChangeNodeRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeNodeRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "nodeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "parameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MapEntryType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
