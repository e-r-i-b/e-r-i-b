/**
 * IdentificationData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * Identifies user, transaction, and session
 */
public class IdentificationData  implements java.io.Serializable {
    private java.lang.String clientSessionId;

    private java.lang.String clientTransactionId;

    private java.lang.Boolean delegated;

    private java.lang.String groupName;

    private java.lang.String newUserName;

    private java.lang.String orgName;

    private java.lang.String sessionId;

    private java.lang.String transactionId;

    private java.lang.String userCountry;

    private java.lang.String userLanguage;

    private java.lang.String userLoginName;

    private java.lang.String userName;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserStatus userStatus;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WSUserType userType;

    public IdentificationData() {
    }

    public IdentificationData(
           java.lang.String clientSessionId,
           java.lang.String clientTransactionId,
           java.lang.Boolean delegated,
           java.lang.String groupName,
           java.lang.String newUserName,
           java.lang.String orgName,
           java.lang.String sessionId,
           java.lang.String transactionId,
           java.lang.String userCountry,
           java.lang.String userLanguage,
           java.lang.String userLoginName,
           java.lang.String userName,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserStatus userStatus,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WSUserType userType) {
           this.clientSessionId = clientSessionId;
           this.clientTransactionId = clientTransactionId;
           this.delegated = delegated;
           this.groupName = groupName;
           this.newUserName = newUserName;
           this.orgName = orgName;
           this.sessionId = sessionId;
           this.transactionId = transactionId;
           this.userCountry = userCountry;
           this.userLanguage = userLanguage;
           this.userLoginName = userLoginName;
           this.userName = userName;
           this.userStatus = userStatus;
           this.userType = userType;
    }


    /**
     * Gets the clientSessionId value for this IdentificationData.
     * 
     * @return clientSessionId
     */
    public java.lang.String getClientSessionId() {
        return clientSessionId;
    }


    /**
     * Sets the clientSessionId value for this IdentificationData.
     * 
     * @param clientSessionId
     */
    public void setClientSessionId(java.lang.String clientSessionId) {
        this.clientSessionId = clientSessionId;
    }


    /**
     * Gets the clientTransactionId value for this IdentificationData.
     * 
     * @return clientTransactionId
     */
    public java.lang.String getClientTransactionId() {
        return clientTransactionId;
    }


    /**
     * Sets the clientTransactionId value for this IdentificationData.
     * 
     * @param clientTransactionId
     */
    public void setClientTransactionId(java.lang.String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }


    /**
     * Gets the delegated value for this IdentificationData.
     * 
     * @return delegated
     */
    public java.lang.Boolean getDelegated() {
        return delegated;
    }


    /**
     * Sets the delegated value for this IdentificationData.
     * 
     * @param delegated
     */
    public void setDelegated(java.lang.Boolean delegated) {
        this.delegated = delegated;
    }


    /**
     * Gets the groupName value for this IdentificationData.
     * 
     * @return groupName
     */
    public java.lang.String getGroupName() {
        return groupName;
    }


    /**
     * Sets the groupName value for this IdentificationData.
     * 
     * @param groupName
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }


    /**
     * Gets the newUserName value for this IdentificationData.
     * 
     * @return newUserName
     */
    public java.lang.String getNewUserName() {
        return newUserName;
    }


    /**
     * Sets the newUserName value for this IdentificationData.
     * 
     * @param newUserName
     */
    public void setNewUserName(java.lang.String newUserName) {
        this.newUserName = newUserName;
    }


    /**
     * Gets the orgName value for this IdentificationData.
     * 
     * @return orgName
     */
    public java.lang.String getOrgName() {
        return orgName;
    }


    /**
     * Sets the orgName value for this IdentificationData.
     * 
     * @param orgName
     */
    public void setOrgName(java.lang.String orgName) {
        this.orgName = orgName;
    }


    /**
     * Gets the sessionId value for this IdentificationData.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this IdentificationData.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the transactionId value for this IdentificationData.
     * 
     * @return transactionId
     */
    public java.lang.String getTransactionId() {
        return transactionId;
    }


    /**
     * Sets the transactionId value for this IdentificationData.
     * 
     * @param transactionId
     */
    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }


    /**
     * Gets the userCountry value for this IdentificationData.
     * 
     * @return userCountry
     */
    public java.lang.String getUserCountry() {
        return userCountry;
    }


    /**
     * Sets the userCountry value for this IdentificationData.
     * 
     * @param userCountry
     */
    public void setUserCountry(java.lang.String userCountry) {
        this.userCountry = userCountry;
    }


    /**
     * Gets the userLanguage value for this IdentificationData.
     * 
     * @return userLanguage
     */
    public java.lang.String getUserLanguage() {
        return userLanguage;
    }


    /**
     * Sets the userLanguage value for this IdentificationData.
     * 
     * @param userLanguage
     */
    public void setUserLanguage(java.lang.String userLanguage) {
        this.userLanguage = userLanguage;
    }


    /**
     * Gets the userLoginName value for this IdentificationData.
     * 
     * @return userLoginName
     */
    public java.lang.String getUserLoginName() {
        return userLoginName;
    }


    /**
     * Sets the userLoginName value for this IdentificationData.
     * 
     * @param userLoginName
     */
    public void setUserLoginName(java.lang.String userLoginName) {
        this.userLoginName = userLoginName;
    }


    /**
     * Gets the userName value for this IdentificationData.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this IdentificationData.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the userStatus value for this IdentificationData.
     * 
     * @return userStatus
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserStatus getUserStatus() {
        return userStatus;
    }


    /**
     * Sets the userStatus value for this IdentificationData.
     * 
     * @param userStatus
     */
    public void setUserStatus(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserStatus userStatus) {
        this.userStatus = userStatus;
    }


    /**
     * Gets the userType value for this IdentificationData.
     * 
     * @return userType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WSUserType getUserType() {
        return userType;
    }


    /**
     * Sets the userType value for this IdentificationData.
     * 
     * @param userType
     */
    public void setUserType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WSUserType userType) {
        this.userType = userType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdentificationData)) return false;
        IdentificationData other = (IdentificationData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clientSessionId==null && other.getClientSessionId()==null) || 
             (this.clientSessionId!=null &&
              this.clientSessionId.equals(other.getClientSessionId()))) &&
            ((this.clientTransactionId==null && other.getClientTransactionId()==null) || 
             (this.clientTransactionId!=null &&
              this.clientTransactionId.equals(other.getClientTransactionId()))) &&
            ((this.delegated==null && other.getDelegated()==null) || 
             (this.delegated!=null &&
              this.delegated.equals(other.getDelegated()))) &&
            ((this.groupName==null && other.getGroupName()==null) || 
             (this.groupName!=null &&
              this.groupName.equals(other.getGroupName()))) &&
            ((this.newUserName==null && other.getNewUserName()==null) || 
             (this.newUserName!=null &&
              this.newUserName.equals(other.getNewUserName()))) &&
            ((this.orgName==null && other.getOrgName()==null) || 
             (this.orgName!=null &&
              this.orgName.equals(other.getOrgName()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
            ((this.transactionId==null && other.getTransactionId()==null) || 
             (this.transactionId!=null &&
              this.transactionId.equals(other.getTransactionId()))) &&
            ((this.userCountry==null && other.getUserCountry()==null) || 
             (this.userCountry!=null &&
              this.userCountry.equals(other.getUserCountry()))) &&
            ((this.userLanguage==null && other.getUserLanguage()==null) || 
             (this.userLanguage!=null &&
              this.userLanguage.equals(other.getUserLanguage()))) &&
            ((this.userLoginName==null && other.getUserLoginName()==null) || 
             (this.userLoginName!=null &&
              this.userLoginName.equals(other.getUserLoginName()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.userStatus==null && other.getUserStatus()==null) || 
             (this.userStatus!=null &&
              this.userStatus.equals(other.getUserStatus()))) &&
            ((this.userType==null && other.getUserType()==null) || 
             (this.userType!=null &&
              this.userType.equals(other.getUserType())));
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
        if (getClientSessionId() != null) {
            _hashCode += getClientSessionId().hashCode();
        }
        if (getClientTransactionId() != null) {
            _hashCode += getClientTransactionId().hashCode();
        }
        if (getDelegated() != null) {
            _hashCode += getDelegated().hashCode();
        }
        if (getGroupName() != null) {
            _hashCode += getGroupName().hashCode();
        }
        if (getNewUserName() != null) {
            _hashCode += getNewUserName().hashCode();
        }
        if (getOrgName() != null) {
            _hashCode += getOrgName().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        if (getTransactionId() != null) {
            _hashCode += getTransactionId().hashCode();
        }
        if (getUserCountry() != null) {
            _hashCode += getUserCountry().hashCode();
        }
        if (getUserLanguage() != null) {
            _hashCode += getUserLanguage().hashCode();
        }
        if (getUserLoginName() != null) {
            _hashCode += getUserLoginName().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getUserStatus() != null) {
            _hashCode += getUserStatus().hashCode();
        }
        if (getUserType() != null) {
            _hashCode += getUserType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdentificationData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "IdentificationData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientSessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientSessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientTransactionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientTransactionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delegated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "delegated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "groupName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newUserName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "newUserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "orgName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "sessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "transactionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userLanguage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userLanguage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userLoginName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userLoginName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "WSUserType"));
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
