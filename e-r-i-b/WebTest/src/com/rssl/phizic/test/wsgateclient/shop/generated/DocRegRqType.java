/**
 * DocRegRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class DocRegRqType  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String SPName;

    private java.lang.String systemId;

    private java.lang.String riskGroup;

    private java.lang.String supplierType;

    private java.lang.String initiator;

    private java.lang.String eShopIdBySP;

    private java.lang.String eShopURL;

    private java.lang.String mode;

    private java.lang.String phone;

    private java.lang.Boolean mobileCheckout;

    private com.rssl.phizic.test.wsgateclient.shop.generated.RegRqDocumentType document;

    public DocRegRqType() {
    }

    public DocRegRqType(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String SPName,
           java.lang.String systemId,
           java.lang.String riskGroup,
           java.lang.String supplierType,
           java.lang.String initiator,
           java.lang.String eShopIdBySP,
           java.lang.String eShopURL,
           java.lang.String mode,
           java.lang.String phone,
           java.lang.Boolean mobileCheckout,
           com.rssl.phizic.test.wsgateclient.shop.generated.RegRqDocumentType document) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.systemId = systemId;
           this.riskGroup = riskGroup;
           this.supplierType = supplierType;
           this.initiator = initiator;
           this.eShopIdBySP = eShopIdBySP;
           this.eShopURL = eShopURL;
           this.mode = mode;
           this.phone = phone;
           this.mobileCheckout = mobileCheckout;
           this.document = document;
    }


    /**
     * Gets the rqUID value for this DocRegRqType.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this DocRegRqType.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this DocRegRqType.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this DocRegRqType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this DocRegRqType.
     * 
     * @return SPName
     */
    public java.lang.String getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this DocRegRqType.
     * 
     * @param SPName
     */
    public void setSPName(java.lang.String SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the systemId value for this DocRegRqType.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this DocRegRqType.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the riskGroup value for this DocRegRqType.
     * 
     * @return riskGroup
     */
    public java.lang.String getRiskGroup() {
        return riskGroup;
    }


    /**
     * Sets the riskGroup value for this DocRegRqType.
     * 
     * @param riskGroup
     */
    public void setRiskGroup(java.lang.String riskGroup) {
        this.riskGroup = riskGroup;
    }


    /**
     * Gets the supplierType value for this DocRegRqType.
     * 
     * @return supplierType
     */
    public java.lang.String getSupplierType() {
        return supplierType;
    }


    /**
     * Sets the supplierType value for this DocRegRqType.
     * 
     * @param supplierType
     */
    public void setSupplierType(java.lang.String supplierType) {
        this.supplierType = supplierType;
    }


    /**
     * Gets the initiator value for this DocRegRqType.
     * 
     * @return initiator
     */
    public java.lang.String getInitiator() {
        return initiator;
    }


    /**
     * Sets the initiator value for this DocRegRqType.
     * 
     * @param initiator
     */
    public void setInitiator(java.lang.String initiator) {
        this.initiator = initiator;
    }


    /**
     * Gets the eShopIdBySP value for this DocRegRqType.
     * 
     * @return eShopIdBySP
     */
    public java.lang.String getEShopIdBySP() {
        return eShopIdBySP;
    }


    /**
     * Sets the eShopIdBySP value for this DocRegRqType.
     * 
     * @param eShopIdBySP
     */
    public void setEShopIdBySP(java.lang.String eShopIdBySP) {
        this.eShopIdBySP = eShopIdBySP;
    }


    /**
     * Gets the eShopURL value for this DocRegRqType.
     * 
     * @return eShopURL
     */
    public java.lang.String getEShopURL() {
        return eShopURL;
    }


    /**
     * Sets the eShopURL value for this DocRegRqType.
     * 
     * @param eShopURL
     */
    public void setEShopURL(java.lang.String eShopURL) {
        this.eShopURL = eShopURL;
    }


    /**
     * Gets the mode value for this DocRegRqType.
     * 
     * @return mode
     */
    public java.lang.String getMode() {
        return mode;
    }


    /**
     * Sets the mode value for this DocRegRqType.
     * 
     * @param mode
     */
    public void setMode(java.lang.String mode) {
        this.mode = mode;
    }


    /**
     * Gets the phone value for this DocRegRqType.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this DocRegRqType.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the mobileCheckout value for this DocRegRqType.
     * 
     * @return mobileCheckout
     */
    public java.lang.Boolean getMobileCheckout() {
        return mobileCheckout;
    }


    /**
     * Sets the mobileCheckout value for this DocRegRqType.
     * 
     * @param mobileCheckout
     */
    public void setMobileCheckout(java.lang.Boolean mobileCheckout) {
        this.mobileCheckout = mobileCheckout;
    }


    /**
     * Gets the document value for this DocRegRqType.
     * 
     * @return document
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.RegRqDocumentType getDocument() {
        return document;
    }


    /**
     * Sets the document value for this DocRegRqType.
     * 
     * @param document
     */
    public void setDocument(com.rssl.phizic.test.wsgateclient.shop.generated.RegRqDocumentType document) {
        this.document = document;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocRegRqType)) return false;
        DocRegRqType other = (DocRegRqType) obj;
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
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.riskGroup==null && other.getRiskGroup()==null) || 
             (this.riskGroup!=null &&
              this.riskGroup.equals(other.getRiskGroup()))) &&
            ((this.supplierType==null && other.getSupplierType()==null) || 
             (this.supplierType!=null &&
              this.supplierType.equals(other.getSupplierType()))) &&
            ((this.initiator==null && other.getInitiator()==null) || 
             (this.initiator!=null &&
              this.initiator.equals(other.getInitiator()))) &&
            ((this.eShopIdBySP==null && other.getEShopIdBySP()==null) || 
             (this.eShopIdBySP!=null &&
              this.eShopIdBySP.equals(other.getEShopIdBySP()))) &&
            ((this.eShopURL==null && other.getEShopURL()==null) || 
             (this.eShopURL!=null &&
              this.eShopURL.equals(other.getEShopURL()))) &&
            ((this.mode==null && other.getMode()==null) || 
             (this.mode!=null &&
              this.mode.equals(other.getMode()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.mobileCheckout==null && other.getMobileCheckout()==null) || 
             (this.mobileCheckout!=null &&
              this.mobileCheckout.equals(other.getMobileCheckout()))) &&
            ((this.document==null && other.getDocument()==null) || 
             (this.document!=null &&
              this.document.equals(other.getDocument())));
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
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getRiskGroup() != null) {
            _hashCode += getRiskGroup().hashCode();
        }
        if (getSupplierType() != null) {
            _hashCode += getSupplierType().hashCode();
        }
        if (getInitiator() != null) {
            _hashCode += getInitiator().hashCode();
        }
        if (getEShopIdBySP() != null) {
            _hashCode += getEShopIdBySP().hashCode();
        }
        if (getEShopURL() != null) {
            _hashCode += getEShopURL().hashCode();
        }
        if (getMode() != null) {
            _hashCode += getMode().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getMobileCheckout() != null) {
            _hashCode += getMobileCheckout().hashCode();
        }
        if (getDocument() != null) {
            _hashCode += getDocument().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocRegRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocRegRqType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "RiskGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supplierType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "SupplierType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initiator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Initiator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EShopIdBySP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "eShopIdBySP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EShopURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "eShopURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Mode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileCheckout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "MobileCheckout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("document");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Document"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "RegRqDocumentType"));
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
