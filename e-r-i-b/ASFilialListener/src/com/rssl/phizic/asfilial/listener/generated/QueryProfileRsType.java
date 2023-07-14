/**
 * QueryProfileRsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;

public class QueryProfileRsType  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    /* Идентификатор операции REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String operUID;

    private com.rssl.phizic.asfilial.listener.generated.StatusType status;

    private com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] engagedPhones;

    private com.rssl.phizic.asfilial.listener.generated.ResponseType response;

    public QueryProfileRsType() {
    }

    public QueryProfileRsType(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           java.lang.String operUID,
           com.rssl.phizic.asfilial.listener.generated.StatusType status,
           com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] engagedPhones,
           com.rssl.phizic.asfilial.listener.generated.ResponseType response) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.engagedPhones = engagedPhones;
           this.response = response;
    }


    /**
     * Gets the rqUID value for this QueryProfileRsType.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this QueryProfileRsType.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this QueryProfileRsType.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this QueryProfileRsType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this QueryProfileRsType.
     * 
     * @return operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this QueryProfileRsType.
     * 
     * @param operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this QueryProfileRsType.
     * 
     * @return status
     */
    public com.rssl.phizic.asfilial.listener.generated.StatusType getStatus() {
        return status;
    }


    /**
     * Sets the status value for this QueryProfileRsType.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.asfilial.listener.generated.StatusType status) {
        this.status = status;
    }


    /**
     * Gets the engagedPhones value for this QueryProfileRsType.
     * 
     * @return engagedPhones
     */
    public com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] getEngagedPhones() {
        return engagedPhones;
    }


    /**
     * Sets the engagedPhones value for this QueryProfileRsType.
     * 
     * @param engagedPhones
     */
    public void setEngagedPhones(com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] engagedPhones) {
        this.engagedPhones = engagedPhones;
    }

    public com.rssl.phizic.asfilial.listener.generated.PhoneNumberType getEngagedPhones(int i) {
        return this.engagedPhones[i];
    }

    public void setEngagedPhones(int i, com.rssl.phizic.asfilial.listener.generated.PhoneNumberType _value) {
        this.engagedPhones[i] = _value;
    }


    /**
     * Gets the response value for this QueryProfileRsType.
     * 
     * @return response
     */
    public com.rssl.phizic.asfilial.listener.generated.ResponseType getResponse() {
        return response;
    }


    /**
     * Sets the response value for this QueryProfileRsType.
     * 
     * @param response
     */
    public void setResponse(com.rssl.phizic.asfilial.listener.generated.ResponseType response) {
        this.response = response;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryProfileRsType)) return false;
        QueryProfileRsType other = (QueryProfileRsType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.engagedPhones==null && other.getEngagedPhones()==null) || 
             (this.engagedPhones!=null &&
              java.util.Arrays.equals(this.engagedPhones, other.getEngagedPhones()))) &&
            ((this.response==null && other.getResponse()==null) || 
             (this.response!=null &&
              this.response.equals(other.getResponse())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getEngagedPhones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEngagedPhones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEngagedPhones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResponse() != null) {
            _hashCode += getResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryProfileRsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRsType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "StatusType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("engagedPhones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "EngagedPhones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("response");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "Response"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResponseType"));
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
