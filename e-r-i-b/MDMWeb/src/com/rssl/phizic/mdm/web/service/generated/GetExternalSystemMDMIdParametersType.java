/**
 * GetExternalSystemMDMIdParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.mdm.web.service.generated;


/**
 * Тип данных для данных запроса поиска mdm_id во вншней системе
 */
public class GetExternalSystemMDMIdParametersType  implements java.io.Serializable {
    private long innerId;

    private java.lang.String lastName;

    private java.lang.String firstName;

    private java.lang.String middleName;

    private java.lang.String birthday;

    private java.lang.String cardNum;

    private java.lang.String documentSeries;

    private java.lang.String documentNumber;

    private java.lang.String documentType;

    public GetExternalSystemMDMIdParametersType() {
    }

    public GetExternalSystemMDMIdParametersType(
           long innerId,
           java.lang.String lastName,
           java.lang.String firstName,
           java.lang.String middleName,
           java.lang.String birthday,
           java.lang.String cardNum,
           java.lang.String documentSeries,
           java.lang.String documentNumber,
           java.lang.String documentType) {
           this.innerId = innerId;
           this.lastName = lastName;
           this.firstName = firstName;
           this.middleName = middleName;
           this.birthday = birthday;
           this.cardNum = cardNum;
           this.documentSeries = documentSeries;
           this.documentNumber = documentNumber;
           this.documentType = documentType;
    }


    /**
     * Gets the innerId value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return innerId
     */
    public long getInnerId() {
        return innerId;
    }


    /**
     * Sets the innerId value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param innerId
     */
    public void setInnerId(long innerId) {
        this.innerId = innerId;
    }


    /**
     * Gets the lastName value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the firstName value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the middleName value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return middleName
     */
    public java.lang.String getMiddleName() {
        return middleName;
    }


    /**
     * Sets the middleName value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param middleName
     */
    public void setMiddleName(java.lang.String middleName) {
        this.middleName = middleName;
    }


    /**
     * Gets the birthday value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return birthday
     */
    public java.lang.String getBirthday() {
        return birthday;
    }


    /**
     * Sets the birthday value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param birthday
     */
    public void setBirthday(java.lang.String birthday) {
        this.birthday = birthday;
    }


    /**
     * Gets the cardNum value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return cardNum
     */
    public java.lang.String getCardNum() {
        return cardNum;
    }


    /**
     * Sets the cardNum value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param cardNum
     */
    public void setCardNum(java.lang.String cardNum) {
        this.cardNum = cardNum;
    }


    /**
     * Gets the documentSeries value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return documentSeries
     */
    public java.lang.String getDocumentSeries() {
        return documentSeries;
    }


    /**
     * Sets the documentSeries value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param documentSeries
     */
    public void setDocumentSeries(java.lang.String documentSeries) {
        this.documentSeries = documentSeries;
    }


    /**
     * Gets the documentNumber value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return documentNumber
     */
    public java.lang.String getDocumentNumber() {
        return documentNumber;
    }


    /**
     * Sets the documentNumber value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param documentNumber
     */
    public void setDocumentNumber(java.lang.String documentNumber) {
        this.documentNumber = documentNumber;
    }


    /**
     * Gets the documentType value for this GetExternalSystemMDMIdParametersType.
     * 
     * @return documentType
     */
    public java.lang.String getDocumentType() {
        return documentType;
    }


    /**
     * Sets the documentType value for this GetExternalSystemMDMIdParametersType.
     * 
     * @param documentType
     */
    public void setDocumentType(java.lang.String documentType) {
        this.documentType = documentType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetExternalSystemMDMIdParametersType)) return false;
        GetExternalSystemMDMIdParametersType other = (GetExternalSystemMDMIdParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.innerId == other.getInnerId() &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.middleName==null && other.getMiddleName()==null) || 
             (this.middleName!=null &&
              this.middleName.equals(other.getMiddleName()))) &&
            ((this.birthday==null && other.getBirthday()==null) || 
             (this.birthday!=null &&
              this.birthday.equals(other.getBirthday()))) &&
            ((this.cardNum==null && other.getCardNum()==null) || 
             (this.cardNum!=null &&
              this.cardNum.equals(other.getCardNum()))) &&
            ((this.documentSeries==null && other.getDocumentSeries()==null) || 
             (this.documentSeries!=null &&
              this.documentSeries.equals(other.getDocumentSeries()))) &&
            ((this.documentNumber==null && other.getDocumentNumber()==null) || 
             (this.documentNumber!=null &&
              this.documentNumber.equals(other.getDocumentNumber()))) &&
            ((this.documentType==null && other.getDocumentType()==null) || 
             (this.documentType!=null &&
              this.documentType.equals(other.getDocumentType())));
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
        _hashCode += new Long(getInnerId()).hashCode();
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getMiddleName() != null) {
            _hashCode += getMiddleName().hashCode();
        }
        if (getBirthday() != null) {
            _hashCode += getBirthday().hashCode();
        }
        if (getCardNum() != null) {
            _hashCode += getCardNum().hashCode();
        }
        if (getDocumentSeries() != null) {
            _hashCode += getDocumentSeries().hashCode();
        }
        if (getDocumentNumber() != null) {
            _hashCode += getDocumentNumber().hashCode();
        }
        if (getDocumentType() != null) {
            _hashCode += getDocumentType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetExternalSystemMDMIdParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetExternalSystemMDMIdParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("innerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "innerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("middleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "middleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthday");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "birthday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "cardNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentSeries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "documentSeries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "documentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "documentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
