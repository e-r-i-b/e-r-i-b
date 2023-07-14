/**
 * RiskResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the risk result information
 */
public class RiskResult  implements java.io.Serializable {
    private java.lang.Integer riskScore;

    private java.lang.String riskScoreBand;

    private com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule triggeredRule;

    private com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule triggeredTestRule;

    public RiskResult() {
    }

    public RiskResult(
           java.lang.Integer riskScore,
           java.lang.String riskScoreBand,
           com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule triggeredRule,
           com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule triggeredTestRule) {
           this.riskScore = riskScore;
           this.riskScoreBand = riskScoreBand;
           this.triggeredRule = triggeredRule;
           this.triggeredTestRule = triggeredTestRule;
    }


    /**
     * Gets the riskScore value for this RiskResult.
     * 
     * @return riskScore
     */
    public java.lang.Integer getRiskScore() {
        return riskScore;
    }


    /**
     * Sets the riskScore value for this RiskResult.
     * 
     * @param riskScore
     */
    public void setRiskScore(java.lang.Integer riskScore) {
        this.riskScore = riskScore;
    }


    /**
     * Gets the riskScoreBand value for this RiskResult.
     * 
     * @return riskScoreBand
     */
    public java.lang.String getRiskScoreBand() {
        return riskScoreBand;
    }


    /**
     * Sets the riskScoreBand value for this RiskResult.
     * 
     * @param riskScoreBand
     */
    public void setRiskScoreBand(java.lang.String riskScoreBand) {
        this.riskScoreBand = riskScoreBand;
    }


    /**
     * Gets the triggeredRule value for this RiskResult.
     * 
     * @return triggeredRule
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule getTriggeredRule() {
        return triggeredRule;
    }


    /**
     * Sets the triggeredRule value for this RiskResult.
     * 
     * @param triggeredRule
     */
    public void setTriggeredRule(com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule triggeredRule) {
        this.triggeredRule = triggeredRule;
    }


    /**
     * Gets the triggeredTestRule value for this RiskResult.
     * 
     * @return triggeredTestRule
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule getTriggeredTestRule() {
        return triggeredTestRule;
    }


    /**
     * Sets the triggeredTestRule value for this RiskResult.
     * 
     * @param triggeredTestRule
     */
    public void setTriggeredTestRule(com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule triggeredTestRule) {
        this.triggeredTestRule = triggeredTestRule;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RiskResult)) return false;
        RiskResult other = (RiskResult) obj;
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
              this.triggeredRule.equals(other.getTriggeredRule()))) &&
            ((this.triggeredTestRule==null && other.getTriggeredTestRule()==null) || 
             (this.triggeredTestRule!=null &&
              this.triggeredTestRule.equals(other.getTriggeredTestRule())));
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
        if (getTriggeredTestRule() != null) {
            _hashCode += getTriggeredTestRule().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RiskResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RiskResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskScore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "riskScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskScoreBand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "riskScoreBand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("triggeredRule");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "triggeredRule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TriggeredRule"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("triggeredTestRule");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "triggeredTestRule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TriggeredRule"));
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
