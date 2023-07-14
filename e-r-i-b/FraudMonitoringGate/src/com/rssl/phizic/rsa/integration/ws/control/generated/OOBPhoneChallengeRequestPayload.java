/**
 * OOBPhoneChallengeRequestPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This type defines the Phone Challenge Request Payload
 */
public class OOBPhoneChallengeRequestPayload  implements java.io.Serializable {
    private java.lang.Boolean noOp;

    private com.rssl.phizic.rsa.integration.ws.control.generated.PhoneInfo phoneInfo;

    private java.lang.Boolean tokenCollectionFlow;

    public OOBPhoneChallengeRequestPayload() {
    }

    public OOBPhoneChallengeRequestPayload(
           java.lang.Boolean noOp,
           com.rssl.phizic.rsa.integration.ws.control.generated.PhoneInfo phoneInfo,
           java.lang.Boolean tokenCollectionFlow) {
           this.noOp = noOp;
           this.phoneInfo = phoneInfo;
           this.tokenCollectionFlow = tokenCollectionFlow;
    }


    /**
     * Gets the noOp value for this OOBPhoneChallengeRequestPayload.
     * 
     * @return noOp
     */
    public java.lang.Boolean getNoOp() {
        return noOp;
    }


    /**
     * Sets the noOp value for this OOBPhoneChallengeRequestPayload.
     * 
     * @param noOp
     */
    public void setNoOp(java.lang.Boolean noOp) {
        this.noOp = noOp;
    }


    /**
     * Gets the phoneInfo value for this OOBPhoneChallengeRequestPayload.
     * 
     * @return phoneInfo
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.PhoneInfo getPhoneInfo() {
        return phoneInfo;
    }


    /**
     * Sets the phoneInfo value for this OOBPhoneChallengeRequestPayload.
     * 
     * @param phoneInfo
     */
    public void setPhoneInfo(com.rssl.phizic.rsa.integration.ws.control.generated.PhoneInfo phoneInfo) {
        this.phoneInfo = phoneInfo;
    }


    /**
     * Gets the tokenCollectionFlow value for this OOBPhoneChallengeRequestPayload.
     * 
     * @return tokenCollectionFlow
     */
    public java.lang.Boolean getTokenCollectionFlow() {
        return tokenCollectionFlow;
    }


    /**
     * Sets the tokenCollectionFlow value for this OOBPhoneChallengeRequestPayload.
     * 
     * @param tokenCollectionFlow
     */
    public void setTokenCollectionFlow(java.lang.Boolean tokenCollectionFlow) {
        this.tokenCollectionFlow = tokenCollectionFlow;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OOBPhoneChallengeRequestPayload)) return false;
        OOBPhoneChallengeRequestPayload other = (OOBPhoneChallengeRequestPayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.noOp==null && other.getNoOp()==null) || 
             (this.noOp!=null &&
              this.noOp.equals(other.getNoOp()))) &&
            ((this.phoneInfo==null && other.getPhoneInfo()==null) || 
             (this.phoneInfo!=null &&
              this.phoneInfo.equals(other.getPhoneInfo()))) &&
            ((this.tokenCollectionFlow==null && other.getTokenCollectionFlow()==null) || 
             (this.tokenCollectionFlow!=null &&
              this.tokenCollectionFlow.equals(other.getTokenCollectionFlow())));
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
        if (getNoOp() != null) {
            _hashCode += getNoOp().hashCode();
        }
        if (getPhoneInfo() != null) {
            _hashCode += getPhoneInfo().hashCode();
        }
        if (getTokenCollectionFlow() != null) {
            _hashCode += getTokenCollectionFlow().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OOBPhoneChallengeRequestPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBPhoneChallengeRequestPayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noOp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "noOp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "phoneInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tokenCollectionFlow");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "tokenCollectionFlow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
