/**
 * MarketingResponse_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class MarketingResponse_Type  implements java.io.Serializable {
    private java.lang.String sourceCode;

    private java.lang.String description;

    private java.lang.String type;

    private java.lang.String method;

    private java.lang.String result;

    private java.lang.String detailResult;

    private java.math.BigInteger phoneNumber;

    private java.lang.String campaingMemberId;

    private java.lang.String responseDateTime;

    public MarketingResponse_Type() {
    }

    public MarketingResponse_Type(
           java.lang.String sourceCode,
           java.lang.String description,
           java.lang.String type,
           java.lang.String method,
           java.lang.String result,
           java.lang.String detailResult,
           java.math.BigInteger phoneNumber,
           java.lang.String campaingMemberId,
           java.lang.String responseDateTime) {
           this.sourceCode = sourceCode;
           this.description = description;
           this.type = type;
           this.method = method;
           this.result = result;
           this.detailResult = detailResult;
           this.phoneNumber = phoneNumber;
           this.campaingMemberId = campaingMemberId;
           this.responseDateTime = responseDateTime;
    }


    /**
     * Gets the sourceCode value for this MarketingResponse_Type.
     * 
     * @return sourceCode
     */
    public java.lang.String getSourceCode() {
        return sourceCode;
    }


    /**
     * Sets the sourceCode value for this MarketingResponse_Type.
     * 
     * @param sourceCode
     */
    public void setSourceCode(java.lang.String sourceCode) {
        this.sourceCode = sourceCode;
    }


    /**
     * Gets the description value for this MarketingResponse_Type.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this MarketingResponse_Type.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the type value for this MarketingResponse_Type.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this MarketingResponse_Type.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the method value for this MarketingResponse_Type.
     * 
     * @return method
     */
    public java.lang.String getMethod() {
        return method;
    }


    /**
     * Sets the method value for this MarketingResponse_Type.
     * 
     * @param method
     */
    public void setMethod(java.lang.String method) {
        this.method = method;
    }


    /**
     * Gets the result value for this MarketingResponse_Type.
     * 
     * @return result
     */
    public java.lang.String getResult() {
        return result;
    }


    /**
     * Sets the result value for this MarketingResponse_Type.
     * 
     * @param result
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }


    /**
     * Gets the detailResult value for this MarketingResponse_Type.
     * 
     * @return detailResult
     */
    public java.lang.String getDetailResult() {
        return detailResult;
    }


    /**
     * Sets the detailResult value for this MarketingResponse_Type.
     * 
     * @param detailResult
     */
    public void setDetailResult(java.lang.String detailResult) {
        this.detailResult = detailResult;
    }


    /**
     * Gets the phoneNumber value for this MarketingResponse_Type.
     * 
     * @return phoneNumber
     */
    public java.math.BigInteger getPhoneNumber() {
        return phoneNumber;
    }


    /**
     * Sets the phoneNumber value for this MarketingResponse_Type.
     * 
     * @param phoneNumber
     */
    public void setPhoneNumber(java.math.BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * Gets the campaingMemberId value for this MarketingResponse_Type.
     * 
     * @return campaingMemberId
     */
    public java.lang.String getCampaingMemberId() {
        return campaingMemberId;
    }


    /**
     * Sets the campaingMemberId value for this MarketingResponse_Type.
     * 
     * @param campaingMemberId
     */
    public void setCampaingMemberId(java.lang.String campaingMemberId) {
        this.campaingMemberId = campaingMemberId;
    }


    /**
     * Gets the responseDateTime value for this MarketingResponse_Type.
     * 
     * @return responseDateTime
     */
    public java.lang.String getResponseDateTime() {
        return responseDateTime;
    }


    /**
     * Sets the responseDateTime value for this MarketingResponse_Type.
     * 
     * @param responseDateTime
     */
    public void setResponseDateTime(java.lang.String responseDateTime) {
        this.responseDateTime = responseDateTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MarketingResponse_Type)) return false;
        MarketingResponse_Type other = (MarketingResponse_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sourceCode==null && other.getSourceCode()==null) || 
             (this.sourceCode!=null &&
              this.sourceCode.equals(other.getSourceCode()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.method==null && other.getMethod()==null) || 
             (this.method!=null &&
              this.method.equals(other.getMethod()))) &&
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              this.result.equals(other.getResult()))) &&
            ((this.detailResult==null && other.getDetailResult()==null) || 
             (this.detailResult!=null &&
              this.detailResult.equals(other.getDetailResult()))) &&
            ((this.phoneNumber==null && other.getPhoneNumber()==null) || 
             (this.phoneNumber!=null &&
              this.phoneNumber.equals(other.getPhoneNumber()))) &&
            ((this.campaingMemberId==null && other.getCampaingMemberId()==null) || 
             (this.campaingMemberId!=null &&
              this.campaingMemberId.equals(other.getCampaingMemberId()))) &&
            ((this.responseDateTime==null && other.getResponseDateTime()==null) || 
             (this.responseDateTime!=null &&
              this.responseDateTime.equals(other.getResponseDateTime())));
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
        if (getSourceCode() != null) {
            _hashCode += getSourceCode().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getMethod() != null) {
            _hashCode += getMethod().hashCode();
        }
        if (getResult() != null) {
            _hashCode += getResult().hashCode();
        }
        if (getDetailResult() != null) {
            _hashCode += getDetailResult().hashCode();
        }
        if (getPhoneNumber() != null) {
            _hashCode += getPhoneNumber().hashCode();
        }
        if (getCampaingMemberId() != null) {
            _hashCode += getCampaingMemberId().hashCode();
        }
        if (getResponseDateTime() != null) {
            _hashCode += getResponseDateTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MarketingResponse_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarketingResponse_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("method");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Method"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DetailResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaingMemberId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaingMemberId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ResponseDateTime"));
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
