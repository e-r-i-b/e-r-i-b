/**
 * IdentityCard_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.mdm.generated;


/**
 * Удостоверение личности <IdentityCard>
 */
public class IdentityCard_Type  implements java.io.Serializable {
    /* Тип документа удостоверяющего личность */
    private java.lang.String idType;

    /* Серия документа удостоверяющего личность */
    private java.lang.String idSeries;

    /* Номер документа удостоверяющего личность */
    private java.lang.String idNum;

    /* Кем выдан. */
    private java.lang.String issuedBy;

    /* Кем выдан. */
    private java.lang.String issuedCode;

    /* Дата выдачи. */
    private java.lang.String issueDt;

    /* Срок действия/окончания */
    private java.lang.String expDt;

    public IdentityCard_Type() {
    }

    public IdentityCard_Type(
           java.lang.String idType,
           java.lang.String idSeries,
           java.lang.String idNum,
           java.lang.String issuedBy,
           java.lang.String issuedCode,
           java.lang.String issueDt,
           java.lang.String expDt) {
           this.idType = idType;
           this.idSeries = idSeries;
           this.idNum = idNum;
           this.issuedBy = issuedBy;
           this.issuedCode = issuedCode;
           this.issueDt = issueDt;
           this.expDt = expDt;
    }


    /**
     * Gets the idType value for this IdentityCard_Type.
     * 
     * @return idType   * Тип документа удостоверяющего личность
     */
    public java.lang.String getIdType() {
        return idType;
    }


    /**
     * Sets the idType value for this IdentityCard_Type.
     * 
     * @param idType   * Тип документа удостоверяющего личность
     */
    public void setIdType(java.lang.String idType) {
        this.idType = idType;
    }


    /**
     * Gets the idSeries value for this IdentityCard_Type.
     * 
     * @return idSeries   * Серия документа удостоверяющего личность
     */
    public java.lang.String getIdSeries() {
        return idSeries;
    }


    /**
     * Sets the idSeries value for this IdentityCard_Type.
     * 
     * @param idSeries   * Серия документа удостоверяющего личность
     */
    public void setIdSeries(java.lang.String idSeries) {
        this.idSeries = idSeries;
    }


    /**
     * Gets the idNum value for this IdentityCard_Type.
     * 
     * @return idNum   * Номер документа удостоверяющего личность
     */
    public java.lang.String getIdNum() {
        return idNum;
    }


    /**
     * Sets the idNum value for this IdentityCard_Type.
     * 
     * @param idNum   * Номер документа удостоверяющего личность
     */
    public void setIdNum(java.lang.String idNum) {
        this.idNum = idNum;
    }


    /**
     * Gets the issuedBy value for this IdentityCard_Type.
     * 
     * @return issuedBy   * Кем выдан.
     */
    public java.lang.String getIssuedBy() {
        return issuedBy;
    }


    /**
     * Sets the issuedBy value for this IdentityCard_Type.
     * 
     * @param issuedBy   * Кем выдан.
     */
    public void setIssuedBy(java.lang.String issuedBy) {
        this.issuedBy = issuedBy;
    }


    /**
     * Gets the issuedCode value for this IdentityCard_Type.
     * 
     * @return issuedCode   * Кем выдан.
     */
    public java.lang.String getIssuedCode() {
        return issuedCode;
    }


    /**
     * Sets the issuedCode value for this IdentityCard_Type.
     * 
     * @param issuedCode   * Кем выдан.
     */
    public void setIssuedCode(java.lang.String issuedCode) {
        this.issuedCode = issuedCode;
    }


    /**
     * Gets the issueDt value for this IdentityCard_Type.
     * 
     * @return issueDt   * Дата выдачи.
     */
    public java.lang.String getIssueDt() {
        return issueDt;
    }


    /**
     * Sets the issueDt value for this IdentityCard_Type.
     * 
     * @param issueDt   * Дата выдачи.
     */
    public void setIssueDt(java.lang.String issueDt) {
        this.issueDt = issueDt;
    }


    /**
     * Gets the expDt value for this IdentityCard_Type.
     * 
     * @return expDt   * Срок действия/окончания
     */
    public java.lang.String getExpDt() {
        return expDt;
    }


    /**
     * Sets the expDt value for this IdentityCard_Type.
     * 
     * @param expDt   * Срок действия/окончания
     */
    public void setExpDt(java.lang.String expDt) {
        this.expDt = expDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdentityCard_Type)) return false;
        IdentityCard_Type other = (IdentityCard_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idType==null && other.getIdType()==null) || 
             (this.idType!=null &&
              this.idType.equals(other.getIdType()))) &&
            ((this.idSeries==null && other.getIdSeries()==null) || 
             (this.idSeries!=null &&
              this.idSeries.equals(other.getIdSeries()))) &&
            ((this.idNum==null && other.getIdNum()==null) || 
             (this.idNum!=null &&
              this.idNum.equals(other.getIdNum()))) &&
            ((this.issuedBy==null && other.getIssuedBy()==null) || 
             (this.issuedBy!=null &&
              this.issuedBy.equals(other.getIssuedBy()))) &&
            ((this.issuedCode==null && other.getIssuedCode()==null) || 
             (this.issuedCode!=null &&
              this.issuedCode.equals(other.getIssuedCode()))) &&
            ((this.issueDt==null && other.getIssueDt()==null) || 
             (this.issueDt!=null &&
              this.issueDt.equals(other.getIssueDt()))) &&
            ((this.expDt==null && other.getExpDt()==null) || 
             (this.expDt!=null &&
              this.expDt.equals(other.getExpDt())));
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
        if (getIdType() != null) {
            _hashCode += getIdType().hashCode();
        }
        if (getIdSeries() != null) {
            _hashCode += getIdSeries().hashCode();
        }
        if (getIdNum() != null) {
            _hashCode += getIdNum().hashCode();
        }
        if (getIssuedBy() != null) {
            _hashCode += getIssuedBy().hashCode();
        }
        if (getIssuedCode() != null) {
            _hashCode += getIssuedCode().hashCode();
        }
        if (getIssueDt() != null) {
            _hashCode += getIssueDt().hashCode();
        }
        if (getExpDt() != null) {
            _hashCode += getExpDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdentityCard_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdentityCard_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSeries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdSeries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdSeries_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdNum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IssuedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IssuedBy_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuedCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IssuedCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issueDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IssueDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ExpDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Date"));
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
