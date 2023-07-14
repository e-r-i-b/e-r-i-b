/**
 * ATM.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class ATM  extends com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier  implements java.io.Serializable {
    private java.lang.String timezone;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ATMOwnerType atmOwner;

    private java.lang.String atmID;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocationTypes locationType;

    private java.lang.String cardIssueDate;

    private java.lang.String atmLanguage;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocation location;

    private java.lang.String atmIP;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Gender userGender;

    private java.math.BigInteger atmExternalScore;

    private com.rssl.phizic.rsa.integration.ws.control.generated.LoginFailureType loginFailureReason;

    private java.math.BigInteger numberOfFailedLogins;

    private java.math.BigInteger userYearOfBirth;

    private java.lang.String cardPINChangeDate;

    private java.lang.String atmModel;

    private java.lang.String atmOS;

    private java.lang.String atmOwnerOther;

    private java.lang.String cardIssuerID;

    private java.lang.String cardType;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Amount atmDailyLimit;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Amount cardDailyLimit;

    public ATM() {
    }

    public ATM(
           java.lang.String timezone,
           com.rssl.phizic.rsa.integration.ws.control.generated.ATMOwnerType atmOwner,
           java.lang.String atmID,
           com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocationTypes locationType,
           java.lang.String cardIssueDate,
           java.lang.String atmLanguage,
           com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocation location,
           java.lang.String atmIP,
           com.rssl.phizic.rsa.integration.ws.control.generated.Gender userGender,
           java.math.BigInteger atmExternalScore,
           com.rssl.phizic.rsa.integration.ws.control.generated.LoginFailureType loginFailureReason,
           java.math.BigInteger numberOfFailedLogins,
           java.math.BigInteger userYearOfBirth,
           java.lang.String cardPINChangeDate,
           java.lang.String atmModel,
           java.lang.String atmOS,
           java.lang.String atmOwnerOther,
           java.lang.String cardIssuerID,
           java.lang.String cardType,
           com.rssl.phizic.rsa.integration.ws.control.generated.Amount atmDailyLimit,
           com.rssl.phizic.rsa.integration.ws.control.generated.Amount cardDailyLimit) {
        this.timezone = timezone;
        this.atmOwner = atmOwner;
        this.atmID = atmID;
        this.locationType = locationType;
        this.cardIssueDate = cardIssueDate;
        this.atmLanguage = atmLanguage;
        this.location = location;
        this.atmIP = atmIP;
        this.userGender = userGender;
        this.atmExternalScore = atmExternalScore;
        this.loginFailureReason = loginFailureReason;
        this.numberOfFailedLogins = numberOfFailedLogins;
        this.userYearOfBirth = userYearOfBirth;
        this.cardPINChangeDate = cardPINChangeDate;
        this.atmModel = atmModel;
        this.atmOS = atmOS;
        this.atmOwnerOther = atmOwnerOther;
        this.cardIssuerID = cardIssuerID;
        this.cardType = cardType;
        this.atmDailyLimit = atmDailyLimit;
        this.cardDailyLimit = cardDailyLimit;
    }


    /**
     * Gets the timezone value for this ATM.
     * 
     * @return timezone
     */
    public java.lang.String getTimezone() {
        return timezone;
    }


    /**
     * Sets the timezone value for this ATM.
     * 
     * @param timezone
     */
    public void setTimezone(java.lang.String timezone) {
        this.timezone = timezone;
    }


    /**
     * Gets the atmOwner value for this ATM.
     * 
     * @return atmOwner
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ATMOwnerType getAtmOwner() {
        return atmOwner;
    }


    /**
     * Sets the atmOwner value for this ATM.
     * 
     * @param atmOwner
     */
    public void setAtmOwner(com.rssl.phizic.rsa.integration.ws.control.generated.ATMOwnerType atmOwner) {
        this.atmOwner = atmOwner;
    }


    /**
     * Gets the atmID value for this ATM.
     * 
     * @return atmID
     */
    public java.lang.String getAtmID() {
        return atmID;
    }


    /**
     * Sets the atmID value for this ATM.
     * 
     * @param atmID
     */
    public void setAtmID(java.lang.String atmID) {
        this.atmID = atmID;
    }


    /**
     * Gets the locationType value for this ATM.
     * 
     * @return locationType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocationTypes getLocationType() {
        return locationType;
    }


    /**
     * Sets the locationType value for this ATM.
     * 
     * @param locationType
     */
    public void setLocationType(com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocationTypes locationType) {
        this.locationType = locationType;
    }


    /**
     * Gets the cardIssueDate value for this ATM.
     * 
     * @return cardIssueDate
     */
    public java.lang.String getCardIssueDate() {
        return cardIssueDate;
    }


    /**
     * Sets the cardIssueDate value for this ATM.
     * 
     * @param cardIssueDate
     */
    public void setCardIssueDate(java.lang.String cardIssueDate) {
        this.cardIssueDate = cardIssueDate;
    }


    /**
     * Gets the atmLanguage value for this ATM.
     * 
     * @return atmLanguage
     */
    public java.lang.String getAtmLanguage() {
        return atmLanguage;
    }


    /**
     * Sets the atmLanguage value for this ATM.
     * 
     * @param atmLanguage
     */
    public void setAtmLanguage(java.lang.String atmLanguage) {
        this.atmLanguage = atmLanguage;
    }


    /**
     * Gets the location value for this ATM.
     * 
     * @return location
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocation getLocation() {
        return location;
    }


    /**
     * Sets the location value for this ATM.
     * 
     * @param location
     */
    public void setLocation(com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocation location) {
        this.location = location;
    }


    /**
     * Gets the atmIP value for this ATM.
     * 
     * @return atmIP
     */
    public java.lang.String getAtmIP() {
        return atmIP;
    }


    /**
     * Sets the atmIP value for this ATM.
     * 
     * @param atmIP
     */
    public void setAtmIP(java.lang.String atmIP) {
        this.atmIP = atmIP;
    }


    /**
     * Gets the userGender value for this ATM.
     * 
     * @return userGender
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Gender getUserGender() {
        return userGender;
    }


    /**
     * Sets the userGender value for this ATM.
     * 
     * @param userGender
     */
    public void setUserGender(com.rssl.phizic.rsa.integration.ws.control.generated.Gender userGender) {
        this.userGender = userGender;
    }


    /**
     * Gets the atmExternalScore value for this ATM.
     * 
     * @return atmExternalScore
     */
    public java.math.BigInteger getAtmExternalScore() {
        return atmExternalScore;
    }


    /**
     * Sets the atmExternalScore value for this ATM.
     * 
     * @param atmExternalScore
     */
    public void setAtmExternalScore(java.math.BigInteger atmExternalScore) {
        this.atmExternalScore = atmExternalScore;
    }


    /**
     * Gets the loginFailureReason value for this ATM.
     * 
     * @return loginFailureReason
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.LoginFailureType getLoginFailureReason() {
        return loginFailureReason;
    }


    /**
     * Sets the loginFailureReason value for this ATM.
     * 
     * @param loginFailureReason
     */
    public void setLoginFailureReason(com.rssl.phizic.rsa.integration.ws.control.generated.LoginFailureType loginFailureReason) {
        this.loginFailureReason = loginFailureReason;
    }


    /**
     * Gets the numberOfFailedLogins value for this ATM.
     * 
     * @return numberOfFailedLogins
     */
    public java.math.BigInteger getNumberOfFailedLogins() {
        return numberOfFailedLogins;
    }


    /**
     * Sets the numberOfFailedLogins value for this ATM.
     * 
     * @param numberOfFailedLogins
     */
    public void setNumberOfFailedLogins(java.math.BigInteger numberOfFailedLogins) {
        this.numberOfFailedLogins = numberOfFailedLogins;
    }


    /**
     * Gets the userYearOfBirth value for this ATM.
     * 
     * @return userYearOfBirth
     */
    public java.math.BigInteger getUserYearOfBirth() {
        return userYearOfBirth;
    }


    /**
     * Sets the userYearOfBirth value for this ATM.
     * 
     * @param userYearOfBirth
     */
    public void setUserYearOfBirth(java.math.BigInteger userYearOfBirth) {
        this.userYearOfBirth = userYearOfBirth;
    }


    /**
     * Gets the cardPINChangeDate value for this ATM.
     * 
     * @return cardPINChangeDate
     */
    public java.lang.String getCardPINChangeDate() {
        return cardPINChangeDate;
    }


    /**
     * Sets the cardPINChangeDate value for this ATM.
     * 
     * @param cardPINChangeDate
     */
    public void setCardPINChangeDate(java.lang.String cardPINChangeDate) {
        this.cardPINChangeDate = cardPINChangeDate;
    }


    /**
     * Gets the atmModel value for this ATM.
     * 
     * @return atmModel
     */
    public java.lang.String getAtmModel() {
        return atmModel;
    }


    /**
     * Sets the atmModel value for this ATM.
     * 
     * @param atmModel
     */
    public void setAtmModel(java.lang.String atmModel) {
        this.atmModel = atmModel;
    }


    /**
     * Gets the atmOS value for this ATM.
     * 
     * @return atmOS
     */
    public java.lang.String getAtmOS() {
        return atmOS;
    }


    /**
     * Sets the atmOS value for this ATM.
     * 
     * @param atmOS
     */
    public void setAtmOS(java.lang.String atmOS) {
        this.atmOS = atmOS;
    }


    /**
     * Gets the atmOwnerOther value for this ATM.
     * 
     * @return atmOwnerOther
     */
    public java.lang.String getAtmOwnerOther() {
        return atmOwnerOther;
    }


    /**
     * Sets the atmOwnerOther value for this ATM.
     * 
     * @param atmOwnerOther
     */
    public void setAtmOwnerOther(java.lang.String atmOwnerOther) {
        this.atmOwnerOther = atmOwnerOther;
    }


    /**
     * Gets the cardIssuerID value for this ATM.
     * 
     * @return cardIssuerID
     */
    public java.lang.String getCardIssuerID() {
        return cardIssuerID;
    }


    /**
     * Sets the cardIssuerID value for this ATM.
     * 
     * @param cardIssuerID
     */
    public void setCardIssuerID(java.lang.String cardIssuerID) {
        this.cardIssuerID = cardIssuerID;
    }


    /**
     * Gets the cardType value for this ATM.
     * 
     * @return cardType
     */
    public java.lang.String getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this ATM.
     * 
     * @param cardType
     */
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the atmDailyLimit value for this ATM.
     * 
     * @return atmDailyLimit
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Amount getAtmDailyLimit() {
        return atmDailyLimit;
    }


    /**
     * Sets the atmDailyLimit value for this ATM.
     * 
     * @param atmDailyLimit
     */
    public void setAtmDailyLimit(com.rssl.phizic.rsa.integration.ws.control.generated.Amount atmDailyLimit) {
        this.atmDailyLimit = atmDailyLimit;
    }


    /**
     * Gets the cardDailyLimit value for this ATM.
     * 
     * @return cardDailyLimit
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Amount getCardDailyLimit() {
        return cardDailyLimit;
    }


    /**
     * Sets the cardDailyLimit value for this ATM.
     * 
     * @param cardDailyLimit
     */
    public void setCardDailyLimit(com.rssl.phizic.rsa.integration.ws.control.generated.Amount cardDailyLimit) {
        this.cardDailyLimit = cardDailyLimit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ATM)) return false;
        ATM other = (ATM) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.timezone==null && other.getTimezone()==null) || 
             (this.timezone!=null &&
              this.timezone.equals(other.getTimezone()))) &&
            ((this.atmOwner==null && other.getAtmOwner()==null) || 
             (this.atmOwner!=null &&
              this.atmOwner.equals(other.getAtmOwner()))) &&
            ((this.atmID==null && other.getAtmID()==null) || 
             (this.atmID!=null &&
              this.atmID.equals(other.getAtmID()))) &&
            ((this.locationType==null && other.getLocationType()==null) || 
             (this.locationType!=null &&
              this.locationType.equals(other.getLocationType()))) &&
            ((this.cardIssueDate==null && other.getCardIssueDate()==null) || 
             (this.cardIssueDate!=null &&
              this.cardIssueDate.equals(other.getCardIssueDate()))) &&
            ((this.atmLanguage==null && other.getAtmLanguage()==null) || 
             (this.atmLanguage!=null &&
              this.atmLanguage.equals(other.getAtmLanguage()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.atmIP==null && other.getAtmIP()==null) || 
             (this.atmIP!=null &&
              this.atmIP.equals(other.getAtmIP()))) &&
            ((this.userGender==null && other.getUserGender()==null) || 
             (this.userGender!=null &&
              this.userGender.equals(other.getUserGender()))) &&
            ((this.atmExternalScore==null && other.getAtmExternalScore()==null) || 
             (this.atmExternalScore!=null &&
              this.atmExternalScore.equals(other.getAtmExternalScore()))) &&
            ((this.loginFailureReason==null && other.getLoginFailureReason()==null) || 
             (this.loginFailureReason!=null &&
              this.loginFailureReason.equals(other.getLoginFailureReason()))) &&
            ((this.numberOfFailedLogins==null && other.getNumberOfFailedLogins()==null) || 
             (this.numberOfFailedLogins!=null &&
              this.numberOfFailedLogins.equals(other.getNumberOfFailedLogins()))) &&
            ((this.userYearOfBirth==null && other.getUserYearOfBirth()==null) || 
             (this.userYearOfBirth!=null &&
              this.userYearOfBirth.equals(other.getUserYearOfBirth()))) &&
            ((this.cardPINChangeDate==null && other.getCardPINChangeDate()==null) || 
             (this.cardPINChangeDate!=null &&
              this.cardPINChangeDate.equals(other.getCardPINChangeDate()))) &&
            ((this.atmModel==null && other.getAtmModel()==null) || 
             (this.atmModel!=null &&
              this.atmModel.equals(other.getAtmModel()))) &&
            ((this.atmOS==null && other.getAtmOS()==null) || 
             (this.atmOS!=null &&
              this.atmOS.equals(other.getAtmOS()))) &&
            ((this.atmOwnerOther==null && other.getAtmOwnerOther()==null) || 
             (this.atmOwnerOther!=null &&
              this.atmOwnerOther.equals(other.getAtmOwnerOther()))) &&
            ((this.cardIssuerID==null && other.getCardIssuerID()==null) || 
             (this.cardIssuerID!=null &&
              this.cardIssuerID.equals(other.getCardIssuerID()))) &&
            ((this.cardType==null && other.getCardType()==null) || 
             (this.cardType!=null &&
              this.cardType.equals(other.getCardType()))) &&
            ((this.atmDailyLimit==null && other.getAtmDailyLimit()==null) || 
             (this.atmDailyLimit!=null &&
              this.atmDailyLimit.equals(other.getAtmDailyLimit()))) &&
            ((this.cardDailyLimit==null && other.getCardDailyLimit()==null) || 
             (this.cardDailyLimit!=null &&
              this.cardDailyLimit.equals(other.getCardDailyLimit())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getTimezone() != null) {
            _hashCode += getTimezone().hashCode();
        }
        if (getAtmOwner() != null) {
            _hashCode += getAtmOwner().hashCode();
        }
        if (getAtmID() != null) {
            _hashCode += getAtmID().hashCode();
        }
        if (getLocationType() != null) {
            _hashCode += getLocationType().hashCode();
        }
        if (getCardIssueDate() != null) {
            _hashCode += getCardIssueDate().hashCode();
        }
        if (getAtmLanguage() != null) {
            _hashCode += getAtmLanguage().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getAtmIP() != null) {
            _hashCode += getAtmIP().hashCode();
        }
        if (getUserGender() != null) {
            _hashCode += getUserGender().hashCode();
        }
        if (getAtmExternalScore() != null) {
            _hashCode += getAtmExternalScore().hashCode();
        }
        if (getLoginFailureReason() != null) {
            _hashCode += getLoginFailureReason().hashCode();
        }
        if (getNumberOfFailedLogins() != null) {
            _hashCode += getNumberOfFailedLogins().hashCode();
        }
        if (getUserYearOfBirth() != null) {
            _hashCode += getUserYearOfBirth().hashCode();
        }
        if (getCardPINChangeDate() != null) {
            _hashCode += getCardPINChangeDate().hashCode();
        }
        if (getAtmModel() != null) {
            _hashCode += getAtmModel().hashCode();
        }
        if (getAtmOS() != null) {
            _hashCode += getAtmOS().hashCode();
        }
        if (getAtmOwnerOther() != null) {
            _hashCode += getAtmOwnerOther().hashCode();
        }
        if (getCardIssuerID() != null) {
            _hashCode += getCardIssuerID().hashCode();
        }
        if (getCardType() != null) {
            _hashCode += getCardType().hashCode();
        }
        if (getAtmDailyLimit() != null) {
            _hashCode += getAtmDailyLimit().hashCode();
        }
        if (getCardDailyLimit() != null) {
            _hashCode += getCardDailyLimit().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ATM.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATM"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timezone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "timezone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmOwner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmOwner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATMOwnerType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "locationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATMLocationTypes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardIssueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "cardIssueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmLanguage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmLanguage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATMLocation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userGender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userGender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Gender"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmExternalScore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmExternalScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginFailureReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "loginFailureReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "LoginFailureType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfFailedLogins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "numberOfFailedLogins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userYearOfBirth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userYearOfBirth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardPINChangeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "cardPINChangeDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmModel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmModel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmOS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmOS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmOwnerOther");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmOwnerOther"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardIssuerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "cardIssuerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "cardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atmDailyLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "atmDailyLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardDailyLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "cardDailyLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
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
