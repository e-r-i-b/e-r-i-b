/**
 * DictRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.esberib.generated;

public class DictRec_Type  implements java.io.Serializable {
    private java.lang.String issuer;

    private java.lang.String securityName;

    private java.lang.String securityNumber;

    private java.lang.String securityType;

    private java.math.BigDecimal securityNominal;

    private java.lang.String securityNominalCur;

    private java.lang.String insideCode;

    private boolean isDelete;

    public DictRec_Type() {
    }

    public DictRec_Type(
           java.lang.String issuer,
           java.lang.String securityName,
           java.lang.String securityNumber,
           java.lang.String securityType,
           java.math.BigDecimal securityNominal,
           java.lang.String securityNominalCur,
           java.lang.String insideCode,
           boolean isDelete) {
           this.issuer = issuer;
           this.securityName = securityName;
           this.securityNumber = securityNumber;
           this.securityType = securityType;
           this.securityNominal = securityNominal;
           this.securityNominalCur = securityNominalCur;
           this.insideCode = insideCode;
           this.isDelete = isDelete;
    }


    /**
     * Gets the issuer value for this DictRec_Type.
     * 
     * @return issuer
     */
    public java.lang.String getIssuer() {
        return issuer;
    }


    /**
     * Sets the issuer value for this DictRec_Type.
     * 
     * @param issuer
     */
    public void setIssuer(java.lang.String issuer) {
        this.issuer = issuer;
    }


    /**
     * Gets the securityName value for this DictRec_Type.
     * 
     * @return securityName
     */
    public java.lang.String getSecurityName() {
        return securityName;
    }


    /**
     * Sets the securityName value for this DictRec_Type.
     * 
     * @param securityName
     */
    public void setSecurityName(java.lang.String securityName) {
        this.securityName = securityName;
    }


    /**
     * Gets the securityNumber value for this DictRec_Type.
     * 
     * @return securityNumber
     */
    public java.lang.String getSecurityNumber() {
        return securityNumber;
    }


    /**
     * Sets the securityNumber value for this DictRec_Type.
     * 
     * @param securityNumber
     */
    public void setSecurityNumber(java.lang.String securityNumber) {
        this.securityNumber = securityNumber;
    }


    /**
     * Gets the securityType value for this DictRec_Type.
     * 
     * @return securityType
     */
    public java.lang.String getSecurityType() {
        return securityType;
    }


    /**
     * Sets the securityType value for this DictRec_Type.
     * 
     * @param securityType
     */
    public void setSecurityType(java.lang.String securityType) {
        this.securityType = securityType;
    }


    /**
     * Gets the securityNominal value for this DictRec_Type.
     * 
     * @return securityNominal
     */
    public java.math.BigDecimal getSecurityNominal() {
        return securityNominal;
    }


    /**
     * Sets the securityNominal value for this DictRec_Type.
     * 
     * @param securityNominal
     */
    public void setSecurityNominal(java.math.BigDecimal securityNominal) {
        this.securityNominal = securityNominal;
    }


    /**
     * Gets the securityNominalCur value for this DictRec_Type.
     * 
     * @return securityNominalCur
     */
    public java.lang.String getSecurityNominalCur() {
        return securityNominalCur;
    }


    /**
     * Sets the securityNominalCur value for this DictRec_Type.
     * 
     * @param securityNominalCur
     */
    public void setSecurityNominalCur(java.lang.String securityNominalCur) {
        this.securityNominalCur = securityNominalCur;
    }


    /**
     * Gets the insideCode value for this DictRec_Type.
     * 
     * @return insideCode
     */
    public java.lang.String getInsideCode() {
        return insideCode;
    }


    /**
     * Sets the insideCode value for this DictRec_Type.
     * 
     * @param insideCode
     */
    public void setInsideCode(java.lang.String insideCode) {
        this.insideCode = insideCode;
    }


    /**
     * Gets the isDelete value for this DictRec_Type.
     * 
     * @return isDelete
     */
    public boolean isIsDelete() {
        return isDelete;
    }


    /**
     * Sets the isDelete value for this DictRec_Type.
     * 
     * @param isDelete
     */
    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DictRec_Type)) return false;
        DictRec_Type other = (DictRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.issuer==null && other.getIssuer()==null) || 
             (this.issuer!=null &&
              this.issuer.equals(other.getIssuer()))) &&
            ((this.securityName==null && other.getSecurityName()==null) || 
             (this.securityName!=null &&
              this.securityName.equals(other.getSecurityName()))) &&
            ((this.securityNumber==null && other.getSecurityNumber()==null) || 
             (this.securityNumber!=null &&
              this.securityNumber.equals(other.getSecurityNumber()))) &&
            ((this.securityType==null && other.getSecurityType()==null) || 
             (this.securityType!=null &&
              this.securityType.equals(other.getSecurityType()))) &&
            ((this.securityNominal==null && other.getSecurityNominal()==null) || 
             (this.securityNominal!=null &&
              this.securityNominal.equals(other.getSecurityNominal()))) &&
            ((this.securityNominalCur==null && other.getSecurityNominalCur()==null) || 
             (this.securityNominalCur!=null &&
              this.securityNominalCur.equals(other.getSecurityNominalCur()))) &&
            ((this.insideCode==null && other.getInsideCode()==null) || 
             (this.insideCode!=null &&
              this.insideCode.equals(other.getInsideCode()))) &&
            this.isDelete == other.isIsDelete();
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
        if (getIssuer() != null) {
            _hashCode += getIssuer().hashCode();
        }
        if (getSecurityName() != null) {
            _hashCode += getSecurityName().hashCode();
        }
        if (getSecurityNumber() != null) {
            _hashCode += getSecurityNumber().hashCode();
        }
        if (getSecurityType() != null) {
            _hashCode += getSecurityType().hashCode();
        }
        if (getSecurityNominal() != null) {
            _hashCode += getSecurityNominal().hashCode();
        }
        if (getSecurityNominalCur() != null) {
            _hashCode += getSecurityNominalCur().hashCode();
        }
        if (getInsideCode() != null) {
            _hashCode += getInsideCode().hashCode();
        }
        _hashCode += (isIsDelete() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DictRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DictRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Issuer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityNominal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityNominal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityNominalCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityNominalCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insideCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsideCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDelete");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsDelete"));
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
