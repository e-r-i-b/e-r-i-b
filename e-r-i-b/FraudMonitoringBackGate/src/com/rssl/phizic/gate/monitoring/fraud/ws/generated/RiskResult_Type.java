/**
 * RiskResult_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.gate.monitoring.fraud.ws.generated;


/**
 * This defines the risk result information
 */
public class RiskResult_Type  implements java.io.Serializable {
    private java.lang.Integer riskScore;

    private java.lang.String riskScoreBand;

    private com.rssl.phizic.gate.monitoring.fraud.ws.generated.TriggeredRule_Type triggeredRule;

    public RiskResult_Type() {
    }

    public RiskResult_Type(
           java.lang.Integer riskScore,
           java.lang.String riskScoreBand,
           com.rssl.phizic.gate.monitoring.fraud.ws.generated.TriggeredRule_Type triggeredRule) {
           this.riskScore = riskScore;
           this.riskScoreBand = riskScoreBand;
           this.triggeredRule = triggeredRule;
    }


    /**
     * Gets the riskScore value for this RiskResult_Type.
     * 
     * @return riskScore
     */
    public java.lang.Integer getRiskScore() {
        return riskScore;
    }


    /**
     * Sets the riskScore value for this RiskResult_Type.
     * 
     * @param riskScore
     */
    public void setRiskScore(java.lang.Integer riskScore) {
        this.riskScore = riskScore;
    }


    /**
     * Gets the riskScoreBand value for this RiskResult_Type.
     * 
     * @return riskScoreBand
     */
    public java.lang.String getRiskScoreBand() {
        return riskScoreBand;
    }


    /**
     * Sets the riskScoreBand value for this RiskResult_Type.
     * 
     * @param riskScoreBand
     */
    public void setRiskScoreBand(java.lang.String riskScoreBand) {
        this.riskScoreBand = riskScoreBand;
    }


    /**
     * Gets the triggeredRule value for this RiskResult_Type.
     * 
     * @return triggeredRule
     */
    public com.rssl.phizic.gate.monitoring.fraud.ws.generated.TriggeredRule_Type getTriggeredRule() {
        return triggeredRule;
    }


    /**
     * Sets the triggeredRule value for this RiskResult_Type.
     * 
     * @param triggeredRule
     */
    public void setTriggeredRule(com.rssl.phizic.gate.monitoring.fraud.ws.generated.TriggeredRule_Type triggeredRule) {
        this.triggeredRule = triggeredRule;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RiskResult_Type)) return false;
        RiskResult_Type other = (RiskResult_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.riskScore==null && other.getRiskScore()==null) || 
             (this.riskScore!=null &&
              this.riskScore.equals(other.getRiskScore()))) &&
            ((this.riskScoreBand==null && other.getRiskScoreBand()==null) || 
             (this.riskScoreBand!=null &&
              this.riskScoreBand.equals(other.getRiskScoreBand()))) &&
            ((this.triggeredRule==null && other.getTriggeredRule()==null) || 
             (this.triggeredRule!=null &&
              this.triggeredRule.equals(other.getTriggeredRule())));
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
        if (getRiskScore() != null) {
            _hashCode += getRiskScore().hashCode();
        }
        if (getRiskScoreBand() != null) {
            _hashCode += getRiskScoreBand().hashCode();
        }
        if (getTriggeredRule() != null) {
            _hashCode += getTriggeredRule().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RiskResult_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "RiskResult_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskScore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "riskScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskScoreBand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "riskScoreBand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("triggeredRule");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "triggeredRule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "TriggeredRule_Type"));
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
