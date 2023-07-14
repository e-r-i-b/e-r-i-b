/**
 * TriggeredRule_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.gate.monitoring.fraud.ws.generated;


/**
 * This type contains information about the specific rule that was
 * triggered during risk analysis
 */
public class TriggeredRule_Type  implements java.io.Serializable {
    private com.rssl.phizic.gate.monitoring.fraud.ws.generated.ActionCode_Type actionCode;

    private java.lang.Boolean blockClient;

    private java.lang.String comments;

    private java.lang.String actionName;

    private java.lang.String ruleId;

    private java.lang.String ruleName;

    public TriggeredRule_Type() {
    }

    public TriggeredRule_Type(
           com.rssl.phizic.gate.monitoring.fraud.ws.generated.ActionCode_Type actionCode,
           java.lang.Boolean blockClient,
           java.lang.String comments,
           java.lang.String actionName,
           java.lang.String ruleId,
           java.lang.String ruleName) {
           this.actionCode = actionCode;
           this.blockClient = blockClient;
           this.comments = comments;
           this.actionName = actionName;
           this.ruleId = ruleId;
           this.ruleName = ruleName;
    }


    /**
     * Gets the actionCode value for this TriggeredRule_Type.
     * 
     * @return actionCode
     */
    public com.rssl.phizic.gate.monitoring.fraud.ws.generated.ActionCode_Type getActionCode() {
        return actionCode;
    }


    /**
     * Sets the actionCode value for this TriggeredRule_Type.
     * 
     * @param actionCode
     */
    public void setActionCode(com.rssl.phizic.gate.monitoring.fraud.ws.generated.ActionCode_Type actionCode) {
        this.actionCode = actionCode;
    }


    /**
     * Gets the blockClient value for this TriggeredRule_Type.
     * 
     * @return blockClient
     */
    public java.lang.Boolean getBlockClient() {
        return blockClient;
    }


    /**
     * Sets the blockClient value for this TriggeredRule_Type.
     * 
     * @param blockClient
     */
    public void setBlockClient(java.lang.Boolean blockClient) {
        this.blockClient = blockClient;
    }


    /**
     * Gets the comments value for this TriggeredRule_Type.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this TriggeredRule_Type.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the actionName value for this TriggeredRule_Type.
     * 
     * @return actionName
     */
    public java.lang.String getActionName() {
        return actionName;
    }


    /**
     * Sets the actionName value for this TriggeredRule_Type.
     * 
     * @param actionName
     */
    public void setActionName(java.lang.String actionName) {
        this.actionName = actionName;
    }


    /**
     * Gets the ruleId value for this TriggeredRule_Type.
     * 
     * @return ruleId
     */
    public java.lang.String getRuleId() {
        return ruleId;
    }


    /**
     * Sets the ruleId value for this TriggeredRule_Type.
     * 
     * @param ruleId
     */
    public void setRuleId(java.lang.String ruleId) {
        this.ruleId = ruleId;
    }


    /**
     * Gets the ruleName value for this TriggeredRule_Type.
     * 
     * @return ruleName
     */
    public java.lang.String getRuleName() {
        return ruleName;
    }


    /**
     * Sets the ruleName value for this TriggeredRule_Type.
     * 
     * @param ruleName
     */
    public void setRuleName(java.lang.String ruleName) {
        this.ruleName = ruleName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TriggeredRule_Type)) return false;
        TriggeredRule_Type other = (TriggeredRule_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actionCode==null && other.getActionCode()==null) || 
             (this.actionCode!=null &&
              this.actionCode.equals(other.getActionCode()))) &&
            ((this.blockClient==null && other.getBlockClient()==null) || 
             (this.blockClient!=null &&
              this.blockClient.equals(other.getBlockClient()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.actionName==null && other.getActionName()==null) || 
             (this.actionName!=null &&
              this.actionName.equals(other.getActionName()))) &&
            ((this.ruleId==null && other.getRuleId()==null) || 
             (this.ruleId!=null &&
              this.ruleId.equals(other.getRuleId()))) &&
            ((this.ruleName==null && other.getRuleName()==null) || 
             (this.ruleName!=null &&
              this.ruleName.equals(other.getRuleName())));
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
        if (getActionCode() != null) {
            _hashCode += getActionCode().hashCode();
        }
        if (getBlockClient() != null) {
            _hashCode += getBlockClient().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getActionName() != null) {
            _hashCode += getActionName().hashCode();
        }
        if (getRuleId() != null) {
            _hashCode += getRuleId().hashCode();
        }
        if (getRuleName() != null) {
            _hashCode += getRuleName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TriggeredRule_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "TriggeredRule_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "actionCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "ActionCode_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockClient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "blockClient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "actionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "ruleId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "ruleName"));
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
