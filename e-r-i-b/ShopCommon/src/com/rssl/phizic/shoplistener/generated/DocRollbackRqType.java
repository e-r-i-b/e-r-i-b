/**
 * DocRollbackRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.shoplistener.generated;

public class DocRollbackRqType  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String SPName;

    private java.lang.String eShopIdBySP;

    private com.rssl.phizic.shoplistener.generated.DocRollbackRqDocumentType document;

    public DocRollbackRqType() {
    }

    public DocRollbackRqType(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String SPName,
           java.lang.String eShopIdBySP,
           com.rssl.phizic.shoplistener.generated.DocRollbackRqDocumentType document) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.eShopIdBySP = eShopIdBySP;
           this.document = document;
    }


    /**
     * Gets the rqUID value for this DocRollbackRqType.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this DocRollbackRqType.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this DocRollbackRqType.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this DocRollbackRqType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this DocRollbackRqType.
     * 
     * @return SPName
     */
    public java.lang.String getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this DocRollbackRqType.
     * 
     * @param SPName
     */
    public void setSPName(java.lang.String SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the eShopIdBySP value for this DocRollbackRqType.
     * 
     * @return eShopIdBySP
     */
    public java.lang.String getEShopIdBySP() {
        return eShopIdBySP;
    }


    /**
     * Sets the eShopIdBySP value for this DocRollbackRqType.
     * 
     * @param eShopIdBySP
     */
    public void setEShopIdBySP(java.lang.String eShopIdBySP) {
        this.eShopIdBySP = eShopIdBySP;
    }


    /**
     * Gets the document value for this DocRollbackRqType.
     * 
     * @return document
     */
    public com.rssl.phizic.shoplistener.generated.DocRollbackRqDocumentType getDocument() {
        return document;
    }


    /**
     * Sets the document value for this DocRollbackRqType.
     * 
     * @param document
     */
    public void setDocument(com.rssl.phizic.shoplistener.generated.DocRollbackRqDocumentType document) {
        this.document = document;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocRollbackRqType)) return false;
        DocRollbackRqType other = (DocRollbackRqType) obj;
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
            ((this.eShopIdBySP==null && other.getEShopIdBySP()==null) || 
             (this.eShopIdBySP!=null &&
              this.eShopIdBySP.equals(other.getEShopIdBySP()))) &&
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
        if (getEShopIdBySP() != null) {
            _hashCode += getEShopIdBySP().hashCode();
        }
        if (getDocument() != null) {
            _hashCode += getDocument().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocRollbackRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocRollbackRqType"));
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
        elemField.setFieldName("EShopIdBySP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "eShopIdBySP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("document");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Document"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocRollbackRqDocumentType"));
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
