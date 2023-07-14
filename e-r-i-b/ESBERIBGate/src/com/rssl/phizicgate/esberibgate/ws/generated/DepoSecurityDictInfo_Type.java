/**
 * DepoSecurityDictInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись о ценной бумаге в справочнике (ДЕПО).
 */
public class DepoSecurityDictInfo_Type  implements java.io.Serializable {
    /* Эмитент ценной бумаги */
    private java.lang.String issuer;

    /* Наименование ценной бумаги */
    private java.lang.String securityName;

    /* Регистрационный номер ценной бумаги */
    private java.lang.String securityNumber;

    /* Тип ценной бумаги */
    private java.lang.String securityType;

    /* Минимальный номинал выпуска ценной бумаги */
    private java.math.BigDecimal securityNominal;

    /* Валюта минимального номинала ценной бумаги */
    private java.lang.String securityNominalCur;

    /* Депозитарный  код выпуска ценной бумаги */
    private java.lang.String insideCode;

    /* Признак была ли запись удалена */
    private boolean isDelete;

    public DepoSecurityDictInfo_Type() {
    }

    public DepoSecurityDictInfo_Type(
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
     * Gets the issuer value for this DepoSecurityDictInfo_Type.
     * 
     * @return issuer   * Эмитент ценной бумаги
     */
    public java.lang.String getIssuer() {
        return issuer;
    }


    /**
     * Sets the issuer value for this DepoSecurityDictInfo_Type.
     * 
     * @param issuer   * Эмитент ценной бумаги
     */
    public void setIssuer(java.lang.String issuer) {
        this.issuer = issuer;
    }


    /**
     * Gets the securityName value for this DepoSecurityDictInfo_Type.
     * 
     * @return securityName   * Наименование ценной бумаги
     */
    public java.lang.String getSecurityName() {
        return securityName;
    }


    /**
     * Sets the securityName value for this DepoSecurityDictInfo_Type.
     * 
     * @param securityName   * Наименование ценной бумаги
     */
    public void setSecurityName(java.lang.String securityName) {
        this.securityName = securityName;
    }


    /**
     * Gets the securityNumber value for this DepoSecurityDictInfo_Type.
     * 
     * @return securityNumber   * Регистрационный номер ценной бумаги
     */
    public java.lang.String getSecurityNumber() {
        return securityNumber;
    }


    /**
     * Sets the securityNumber value for this DepoSecurityDictInfo_Type.
     * 
     * @param securityNumber   * Регистрационный номер ценной бумаги
     */
    public void setSecurityNumber(java.lang.String securityNumber) {
        this.securityNumber = securityNumber;
    }


    /**
     * Gets the securityType value for this DepoSecurityDictInfo_Type.
     * 
     * @return securityType   * Тип ценной бумаги
     */
    public java.lang.String getSecurityType() {
        return securityType;
    }


    /**
     * Sets the securityType value for this DepoSecurityDictInfo_Type.
     * 
     * @param securityType   * Тип ценной бумаги
     */
    public void setSecurityType(java.lang.String securityType) {
        this.securityType = securityType;
    }


    /**
     * Gets the securityNominal value for this DepoSecurityDictInfo_Type.
     * 
     * @return securityNominal   * Минимальный номинал выпуска ценной бумаги
     */
    public java.math.BigDecimal getSecurityNominal() {
        return securityNominal;
    }


    /**
     * Sets the securityNominal value for this DepoSecurityDictInfo_Type.
     * 
     * @param securityNominal   * Минимальный номинал выпуска ценной бумаги
     */
    public void setSecurityNominal(java.math.BigDecimal securityNominal) {
        this.securityNominal = securityNominal;
    }


    /**
     * Gets the securityNominalCur value for this DepoSecurityDictInfo_Type.
     * 
     * @return securityNominalCur   * Валюта минимального номинала ценной бумаги
     */
    public java.lang.String getSecurityNominalCur() {
        return securityNominalCur;
    }


    /**
     * Sets the securityNominalCur value for this DepoSecurityDictInfo_Type.
     * 
     * @param securityNominalCur   * Валюта минимального номинала ценной бумаги
     */
    public void setSecurityNominalCur(java.lang.String securityNominalCur) {
        this.securityNominalCur = securityNominalCur;
    }


    /**
     * Gets the insideCode value for this DepoSecurityDictInfo_Type.
     * 
     * @return insideCode   * Депозитарный  код выпуска ценной бумаги
     */
    public java.lang.String getInsideCode() {
        return insideCode;
    }


    /**
     * Sets the insideCode value for this DepoSecurityDictInfo_Type.
     * 
     * @param insideCode   * Депозитарный  код выпуска ценной бумаги
     */
    public void setInsideCode(java.lang.String insideCode) {
        this.insideCode = insideCode;
    }


    /**
     * Gets the isDelete value for this DepoSecurityDictInfo_Type.
     * 
     * @return isDelete   * Признак была ли запись удалена
     */
    public boolean isIsDelete() {
        return isDelete;
    }


    /**
     * Sets the isDelete value for this DepoSecurityDictInfo_Type.
     * 
     * @param isDelete   * Признак была ли запись удалена
     */
    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoSecurityDictInfo_Type)) return false;
        DepoSecurityDictInfo_Type other = (DepoSecurityDictInfo_Type) obj;
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
        new org.apache.axis.description.TypeDesc(DepoSecurityDictInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityDictInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Issuer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
