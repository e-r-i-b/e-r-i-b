/**
 * OOBEmailChallengeRequestPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This type defines the Email Challenge Request Payload
 */
public class OOBEmailChallengeRequestPayload  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.EmailInfo emailInfo;

    private java.lang.String fromAddress;

    private java.lang.String fromName;

    private java.lang.Boolean noOp;

    private java.lang.String subject;

    public OOBEmailChallengeRequestPayload() {
    }

    public OOBEmailChallengeRequestPayload(
           com.rssl.phizic.rsa.integration.ws.control.generated.EmailInfo emailInfo,
           java.lang.String fromAddress,
           java.lang.String fromName,
           java.lang.Boolean noOp,
           java.lang.String subject) {
           this.emailInfo = emailInfo;
           this.fromAddress = fromAddress;
           this.fromName = fromName;
           this.noOp = noOp;
           this.subject = subject;
    }


    /**
     * Gets the emailInfo value for this OOBEmailChallengeRequestPayload.
     * 
     * @return emailInfo
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.EmailInfo getEmailInfo() {
        return emailInfo;
    }


    /**
     * Sets the emailInfo value for this OOBEmailChallengeRequestPayload.
     * 
     * @param emailInfo
     */
    public void setEmailInfo(com.rssl.phizic.rsa.integration.ws.control.generated.EmailInfo emailInfo) {
        this.emailInfo = emailInfo;
    }


    /**
     * Gets the fromAddress value for this OOBEmailChallengeRequestPayload.
     * 
     * @return fromAddress
     */
    public java.lang.String getFromAddress() {
        return fromAddress;
    }


    /**
     * Sets the fromAddress value for this OOBEmailChallengeRequestPayload.
     * 
     * @param fromAddress
     */
    public void setFromAddress(java.lang.String fromAddress) {
        this.fromAddress = fromAddress;
    }


    /**
     * Gets the fromName value for this OOBEmailChallengeRequestPayload.
     * 
     * @return fromName
     */
    public java.lang.String getFromName() {
        return fromName;
    }


    /**
     * Sets the fromName value for this OOBEmailChallengeRequestPayload.
     * 
     * @param fromName
     */
    public void setFromName(java.lang.String fromName) {
        this.fromName = fromName;
    }


    /**
     * Gets the noOp value for this OOBEmailChallengeRequestPayload.
     * 
     * @return noOp
     */
    public java.lang.Boolean getNoOp() {
        return noOp;
    }


    /**
     * Sets the noOp value for this OOBEmailChallengeRequestPayload.
     * 
     * @param noOp
     */
    public void setNoOp(java.lang.Boolean noOp) {
        this.noOp = noOp;
    }


    /**
     * Gets the subject value for this OOBEmailChallengeRequestPayload.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this OOBEmailChallengeRequestPayload.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OOBEmailChallengeRequestPayload)) return false;
        OOBEmailChallengeRequestPayload other = (OOBEmailChallengeRequestPayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.emailInfo==null && other.getEmailInfo()==null) || 
             (this.emailInfo!=null &&
              this.emailInfo.equals(other.getEmailInfo()))) &&
            ((this.fromAddress==null && other.getFromAddress()==null) || 
             (this.fromAddress!=null &&
              this.fromAddress.equals(other.getFromAddress()))) &&
            ((this.fromName==null && other.getFromName()==null) || 
             (this.fromName!=null &&
              this.fromName.equals(other.getFromName()))) &&
            ((this.noOp==null && other.getNoOp()==null) || 
             (this.noOp!=null &&
              this.noOp.equals(other.getNoOp()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject())));
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
        if (getEmailInfo() != null) {
            _hashCode += getEmailInfo().hashCode();
        }
        if (getFromAddress() != null) {
            _hashCode += getFromAddress().hashCode();
        }
        if (getFromName() != null) {
            _hashCode += getFromName().hashCode();
        }
        if (getNoOp() != null) {
            _hashCode += getNoOp().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OOBEmailChallengeRequestPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBEmailChallengeRequestPayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "emailInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EmailInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "fromAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "fromName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noOp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "noOp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "subject"));
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
