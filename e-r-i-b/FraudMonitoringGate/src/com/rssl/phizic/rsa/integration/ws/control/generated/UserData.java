/**
 * UserData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class UserData  implements java.io.Serializable {
    private java.lang.Boolean VIP;

    private java.lang.Boolean business;

    private java.lang.String lastAccountOpenDate;

    private java.lang.String lastOnlineServicePasswordChangeDate;

    private java.lang.String onlineServiceEnrollDate;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalAvailableBalance;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalCreditLimit;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalCreditsUsed;

    private com.rssl.phizic.rsa.integration.ws.control.generated.UserAddress userAddress;

    private com.rssl.phizic.rsa.integration.ws.control.generated.UserName userNameData;

    public UserData() {
    }

    public UserData(
           java.lang.Boolean VIP,
           java.lang.Boolean business,
           java.lang.String lastAccountOpenDate,
           java.lang.String lastOnlineServicePasswordChangeDate,
           java.lang.String onlineServiceEnrollDate,
           com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalAvailableBalance,
           com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalCreditLimit,
           com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalCreditsUsed,
           com.rssl.phizic.rsa.integration.ws.control.generated.UserAddress userAddress,
           com.rssl.phizic.rsa.integration.ws.control.generated.UserName userNameData) {
           this.VIP = VIP;
           this.business = business;
           this.lastAccountOpenDate = lastAccountOpenDate;
           this.lastOnlineServicePasswordChangeDate = lastOnlineServicePasswordChangeDate;
           this.onlineServiceEnrollDate = onlineServiceEnrollDate;
           this.totalAvailableBalance = totalAvailableBalance;
           this.totalCreditLimit = totalCreditLimit;
           this.totalCreditsUsed = totalCreditsUsed;
           this.userAddress = userAddress;
           this.userNameData = userNameData;
    }


    /**
     * Gets the VIP value for this UserData.
     * 
     * @return VIP
     */
    public java.lang.Boolean getVIP() {
        return VIP;
    }


    /**
     * Sets the VIP value for this UserData.
     * 
     * @param VIP
     */
    public void setVIP(java.lang.Boolean VIP) {
        this.VIP = VIP;
    }


    /**
     * Gets the business value for this UserData.
     * 
     * @return business
     */
    public java.lang.Boolean getBusiness() {
        return business;
    }


    /**
     * Sets the business value for this UserData.
     * 
     * @param business
     */
    public void setBusiness(java.lang.Boolean business) {
        this.business = business;
    }


    /**
     * Gets the lastAccountOpenDate value for this UserData.
     * 
     * @return lastAccountOpenDate
     */
    public java.lang.String getLastAccountOpenDate() {
        return lastAccountOpenDate;
    }


    /**
     * Sets the lastAccountOpenDate value for this UserData.
     * 
     * @param lastAccountOpenDate
     */
    public void setLastAccountOpenDate(java.lang.String lastAccountOpenDate) {
        this.lastAccountOpenDate = lastAccountOpenDate;
    }


    /**
     * Gets the lastOnlineServicePasswordChangeDate value for this UserData.
     * 
     * @return lastOnlineServicePasswordChangeDate
     */
    public java.lang.String getLastOnlineServicePasswordChangeDate() {
        return lastOnlineServicePasswordChangeDate;
    }


    /**
     * Sets the lastOnlineServicePasswordChangeDate value for this UserData.
     * 
     * @param lastOnlineServicePasswordChangeDate
     */
    public void setLastOnlineServicePasswordChangeDate(java.lang.String lastOnlineServicePasswordChangeDate) {
        this.lastOnlineServicePasswordChangeDate = lastOnlineServicePasswordChangeDate;
    }


    /**
     * Gets the onlineServiceEnrollDate value for this UserData.
     * 
     * @return onlineServiceEnrollDate
     */
    public java.lang.String getOnlineServiceEnrollDate() {
        return onlineServiceEnrollDate;
    }


    /**
     * Sets the onlineServiceEnrollDate value for this UserData.
     * 
     * @param onlineServiceEnrollDate
     */
    public void setOnlineServiceEnrollDate(java.lang.String onlineServiceEnrollDate) {
        this.onlineServiceEnrollDate = onlineServiceEnrollDate;
    }


    /**
     * Gets the totalAvailableBalance value for this UserData.
     * 
     * @return totalAvailableBalance
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Amount getTotalAvailableBalance() {
        return totalAvailableBalance;
    }


    /**
     * Sets the totalAvailableBalance value for this UserData.
     * 
     * @param totalAvailableBalance
     */
    public void setTotalAvailableBalance(com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalAvailableBalance) {
        this.totalAvailableBalance = totalAvailableBalance;
    }


    /**
     * Gets the totalCreditLimit value for this UserData.
     * 
     * @return totalCreditLimit
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Amount getTotalCreditLimit() {
        return totalCreditLimit;
    }


    /**
     * Sets the totalCreditLimit value for this UserData.
     * 
     * @param totalCreditLimit
     */
    public void setTotalCreditLimit(com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalCreditLimit) {
        this.totalCreditLimit = totalCreditLimit;
    }


    /**
     * Gets the totalCreditsUsed value for this UserData.
     * 
     * @return totalCreditsUsed
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Amount getTotalCreditsUsed() {
        return totalCreditsUsed;
    }


    /**
     * Sets the totalCreditsUsed value for this UserData.
     * 
     * @param totalCreditsUsed
     */
    public void setTotalCreditsUsed(com.rssl.phizic.rsa.integration.ws.control.generated.Amount totalCreditsUsed) {
        this.totalCreditsUsed = totalCreditsUsed;
    }


    /**
     * Gets the userAddress value for this UserData.
     * 
     * @return userAddress
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.UserAddress getUserAddress() {
        return userAddress;
    }


    /**
     * Sets the userAddress value for this UserData.
     * 
     * @param userAddress
     */
    public void setUserAddress(com.rssl.phizic.rsa.integration.ws.control.generated.UserAddress userAddress) {
        this.userAddress = userAddress;
    }


    /**
     * Gets the userNameData value for this UserData.
     * 
     * @return userNameData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.UserName getUserNameData() {
        return userNameData;
    }


    /**
     * Sets the userNameData value for this UserData.
     * 
     * @param userNameData
     */
    public void setUserNameData(com.rssl.phizic.rsa.integration.ws.control.generated.UserName userNameData) {
        this.userNameData = userNameData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserData)) return false;
        UserData other = (UserData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.VIP==null && other.getVIP()==null) || 
             (this.VIP!=null &&
              this.VIP.equals(other.getVIP()))) &&
            ((this.business==null && other.getBusiness()==null) || 
             (this.business!=null &&
              this.business.equals(other.getBusiness()))) &&
            ((this.lastAccountOpenDate==null && other.getLastAccountOpenDate()==null) || 
             (this.lastAccountOpenDate!=null &&
              this.lastAccountOpenDate.equals(other.getLastAccountOpenDate()))) &&
            ((this.lastOnlineServicePasswordChangeDate==null && other.getLastOnlineServicePasswordChangeDate()==null) || 
             (this.lastOnlineServicePasswordChangeDate!=null &&
              this.lastOnlineServicePasswordChangeDate.equals(other.getLastOnlineServicePasswordChangeDate()))) &&
            ((this.onlineServiceEnrollDate==null && other.getOnlineServiceEnrollDate()==null) || 
             (this.onlineServiceEnrollDate!=null &&
              this.onlineServiceEnrollDate.equals(other.getOnlineServiceEnrollDate()))) &&
            ((this.totalAvailableBalance==null && other.getTotalAvailableBalance()==null) || 
             (this.totalAvailableBalance!=null &&
              this.totalAvailableBalance.equals(other.getTotalAvailableBalance()))) &&
            ((this.totalCreditLimit==null && other.getTotalCreditLimit()==null) || 
             (this.totalCreditLimit!=null &&
              this.totalCreditLimit.equals(other.getTotalCreditLimit()))) &&
            ((this.totalCreditsUsed==null && other.getTotalCreditsUsed()==null) || 
             (this.totalCreditsUsed!=null &&
              this.totalCreditsUsed.equals(other.getTotalCreditsUsed()))) &&
            ((this.userAddress==null && other.getUserAddress()==null) || 
             (this.userAddress!=null &&
              this.userAddress.equals(other.getUserAddress()))) &&
            ((this.userNameData==null && other.getUserNameData()==null) || 
             (this.userNameData!=null &&
              this.userNameData.equals(other.getUserNameData())));
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
        if (getVIP() != null) {
            _hashCode += getVIP().hashCode();
        }
        if (getBusiness() != null) {
            _hashCode += getBusiness().hashCode();
        }
        if (getLastAccountOpenDate() != null) {
            _hashCode += getLastAccountOpenDate().hashCode();
        }
        if (getLastOnlineServicePasswordChangeDate() != null) {
            _hashCode += getLastOnlineServicePasswordChangeDate().hashCode();
        }
        if (getOnlineServiceEnrollDate() != null) {
            _hashCode += getOnlineServiceEnrollDate().hashCode();
        }
        if (getTotalAvailableBalance() != null) {
            _hashCode += getTotalAvailableBalance().hashCode();
        }
        if (getTotalCreditLimit() != null) {
            _hashCode += getTotalCreditLimit().hashCode();
        }
        if (getTotalCreditsUsed() != null) {
            _hashCode += getTotalCreditsUsed().hashCode();
        }
        if (getUserAddress() != null) {
            _hashCode += getUserAddress().hashCode();
        }
        if (getUserNameData() != null) {
            _hashCode += getUserNameData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "VIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("business");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "business"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastAccountOpenDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "lastAccountOpenDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastOnlineServicePasswordChangeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "lastOnlineServicePasswordChangeDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlineServiceEnrollDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "onlineServiceEnrollDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAvailableBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "totalAvailableBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "totalCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCreditsUsed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "totalCreditsUsed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserAddress"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userNameData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userNameData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserName"));
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
