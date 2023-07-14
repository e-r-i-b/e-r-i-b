/**
 * QueryProfileRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;

public class QueryProfileRqType  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    /* Идентификатор операции REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String operUID;

    /* Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2" */
    private java.lang.String SName;

    private com.rssl.phizic.asfilial.listener.generated.BankInfoType bankInfo;

    private com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity;

    private com.rssl.phizic.asfilial.listener.generated.IdentityType[] clientOldIdentity;

    private boolean createIfNone;

    public QueryProfileRqType() {
    }

    public QueryProfileRqType(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           java.lang.String operUID,
           java.lang.String SName,
           com.rssl.phizic.asfilial.listener.generated.BankInfoType bankInfo,
           com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity,
           com.rssl.phizic.asfilial.listener.generated.IdentityType[] clientOldIdentity,
           boolean createIfNone) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SName = SName;
           this.bankInfo = bankInfo;
           this.clientIdentity = clientIdentity;
           this.clientOldIdentity = clientOldIdentity;
           this.createIfNone = createIfNone;
    }


    /**
     * Gets the rqUID value for this QueryProfileRqType.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this QueryProfileRqType.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this QueryProfileRqType.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this QueryProfileRqType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this QueryProfileRqType.
     * 
     * @return operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this QueryProfileRqType.
     * 
     * @param operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SName value for this QueryProfileRqType.
     * 
     * @return SName   * Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2"
     */
    public java.lang.String getSName() {
        return SName;
    }


    /**
     * Sets the SName value for this QueryProfileRqType.
     * 
     * @param SName   * Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2"
     */
    public void setSName(java.lang.String SName) {
        this.SName = SName;
    }


    /**
     * Gets the bankInfo value for this QueryProfileRqType.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.asfilial.listener.generated.BankInfoType getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this QueryProfileRqType.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.asfilial.listener.generated.BankInfoType bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the clientIdentity value for this QueryProfileRqType.
     * 
     * @return clientIdentity
     */
    public com.rssl.phizic.asfilial.listener.generated.IdentityType getClientIdentity() {
        return clientIdentity;
    }


    /**
     * Sets the clientIdentity value for this QueryProfileRqType.
     * 
     * @param clientIdentity
     */
    public void setClientIdentity(com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity) {
        this.clientIdentity = clientIdentity;
    }


    /**
     * Gets the clientOldIdentity value for this QueryProfileRqType.
     * 
     * @return clientOldIdentity
     */
    public com.rssl.phizic.asfilial.listener.generated.IdentityType[] getClientOldIdentity() {
        return clientOldIdentity;
    }


    /**
     * Sets the clientOldIdentity value for this QueryProfileRqType.
     * 
     * @param clientOldIdentity
     */
    public void setClientOldIdentity(com.rssl.phizic.asfilial.listener.generated.IdentityType[] clientOldIdentity) {
        this.clientOldIdentity = clientOldIdentity;
    }

    public com.rssl.phizic.asfilial.listener.generated.IdentityType getClientOldIdentity(int i) {
        return this.clientOldIdentity[i];
    }

    public void setClientOldIdentity(int i, com.rssl.phizic.asfilial.listener.generated.IdentityType _value) {
        this.clientOldIdentity[i] = _value;
    }


    /**
     * Gets the createIfNone value for this QueryProfileRqType.
     * 
     * @return createIfNone
     */
    public boolean isCreateIfNone() {
        return createIfNone;
    }


    /**
     * Sets the createIfNone value for this QueryProfileRqType.
     * 
     * @param createIfNone
     */
    public void setCreateIfNone(boolean createIfNone) {
        this.createIfNone = createIfNone;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryProfileRqType)) return false;
        QueryProfileRqType other = (QueryProfileRqType) obj;
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
            ((this.SName==null && other.getSName()==null) || 
             (this.SName!=null &&
              this.SName.equals(other.getSName()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.clientIdentity==null && other.getClientIdentity()==null) || 
             (this.clientIdentity!=null &&
              this.clientIdentity.equals(other.getClientIdentity()))) &&
            ((this.clientOldIdentity==null && other.getClientOldIdentity()==null) || 
             (this.clientOldIdentity!=null &&
              java.util.Arrays.equals(this.clientOldIdentity, other.getClientOldIdentity()))) &&
            this.createIfNone == other.isCreateIfNone();
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
        if (getSName() != null) {
            _hashCode += getSName().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getClientIdentity() != null) {
            _hashCode += getClientIdentity().hashCode();
        }
        if (getClientOldIdentity() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClientOldIdentity());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClientOldIdentity(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isCreateIfNone() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryProfileRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRqType"));
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
        elemField.setFieldName("SName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "SName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "BankInfoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientOldIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientOldIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createIfNone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "CreateIfNone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
