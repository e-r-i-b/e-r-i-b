/**
 * TriggeredRule.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This type contains information about the specific rule that was
 * triggered during risk anaylsis
 */
public class TriggeredRule  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.ActionCode actionCode;

    private java.lang.String actionName;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ActionApplyType actionType;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] clientFactList;

    private java.lang.String ruleId;

    private java.lang.String ruleName;

    public TriggeredRule() {
    }

    public TriggeredRule(
           com.rssl.phizic.rsa.integration.ws.control.generated.ActionCode actionCode,
           java.lang.String actionName,
           com.rssl.phizic.rsa.integration.ws.control.generated.ActionApplyType actionType,
           com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] clientFactList,
           java.lang.String ruleId,
           java.lang.String ruleName) {
           this.actionCode = actionCode;
           this.actionName = actionName;
           this.actionType = actionType;
           this.clientFactList = clientFactList;
           this.ruleId = ruleId;
           this.ruleName = ruleName;
    }


    /**
     * Gets the actionCode value for this TriggeredRule.
     * 
     * @return actionCode
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ActionCode getActionCode() {
        return actionCode;
    }


    /**
     * Sets the actionCode value for this TriggeredRule.
     * 
     * @param actionCode
     */
    public void setActionCode(com.rssl.phizic.rsa.integration.ws.control.generated.ActionCode actionCode) {
        this.actionCode = actionCode;
    }


    /**
     * Gets the actionName value for this TriggeredRule.
     * 
     * @return actionName
     */
    public java.lang.String getActionName() {
        return actionName;
    }


    /**
     * Sets the actionName value for this TriggeredRule.
     * 
     * @param actionName
     */
    public void setActionName(java.lang.String actionName) {
        this.actionName = actionName;
    }


    /**
     * Gets the actionType value for this TriggeredRule.
     * 
     * @return actionType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ActionApplyType getActionType() {
        return actionType;
    }


    /**
     * Sets the actionType value for this TriggeredRule.
     * 
     * @param actionType
     */
    public void setActionType(com.rssl.phizic.rsa.integration.ws.control.generated.ActionApplyType actionType) {
        this.actionType = actionType;
    }


    /**
     * Gets the clientFactList value for this TriggeredRule.
     * 
     * @return clientFactList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] getClientFactList() {
        return clientFactList;
    }


    /**
     * Sets the clientFactList value for this TriggeredRule.
     * 
     * @param clientFactList
     */
    public void setClientFactList(com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] clientFactList) {
        this.clientFactList = clientFactList;
    }


    /**
     * Gets the ruleId value for this TriggeredRule.
     * 
     * @return ruleId
     */
    public java.lang.String getRuleId() {
        return ruleId;
    }


    /**
     * Sets the ruleId value for this TriggeredRule.
     * 
     * @param ruleId
     */
    public void setRuleId(java.lang.String ruleId) {
        this.ruleId = ruleId;
    }


    /**
     * Gets the ruleName value for this TriggeredRule.
     * 
     * @return ruleName
     */
    public java.lang.String getRuleName() {
        return ruleName;
    }


    /**
     * Sets the ruleName value for this TriggeredRule.
     * 
     * @param ruleName
     */
    public void setRuleName(java.lang.String ruleName) {
        this.ruleName = ruleName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TriggeredRule)) return false;
        TriggeredRule other = (TriggeredRule) obj;
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
            ((this.actionName==null && other.getActionName()==null) || 
             (this.actionName!=null &&
              this.actionName.equals(other.getActionName()))) &&
            ((this.actionType==null && other.getActionType()==null) || 
             (this.actionType!=null &&
              this.actionType.equals(other.getActionType()))) &&
            ((this.clientFactList==null && other.getClientFactList()==null) || 
             (this.clientFactList!=null &&
              java.util.Arrays.equals(this.clientFactList, other.getClientFactList()))) &&
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
        if (getActionName() != null) {
            _hashCode += getActionName().hashCode();
        }
        if (getActionType() != null) {
            _hashCode += getActionType().hashCode();
        }
        if (getClientFactList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClientFactList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClientFactList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        new org.apache.axis.description.TypeDesc(TriggeredRule.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TriggeredRule"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actionCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ActionCode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ActionApplyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientFactList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientFactList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientDefinedFact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "fact"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ruleId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ruleName"));
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
