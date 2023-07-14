/**
 * IdentityCardType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;


/**
 * ДУЛ
 */
public class IdentityCardType  implements java.io.Serializable {
    private java.lang.String idType;

    private java.lang.String idSeries;

    private java.lang.String idNum;

    private java.lang.String issuedBy;

    private java.util.Date issueDt;

    public IdentityCardType() {
    }

    public IdentityCardType(
           java.lang.String idType,
           java.lang.String idSeries,
           java.lang.String idNum,
           java.lang.String issuedBy,
           java.util.Date issueDt) {
           this.idType = idType;
           this.idSeries = idSeries;
           this.idNum = idNum;
           this.issuedBy = issuedBy;
           this.issueDt = issueDt;
    }


    /**
     * Gets the idType value for this IdentityCardType.
     * 
     * @return idType
     */
    public java.lang.String getIdType() {
        return idType;
    }


    /**
     * Sets the idType value for this IdentityCardType.
     * 
     * @param idType
     */
    public void setIdType(java.lang.String idType) {
        this.idType = idType;
    }


    /**
     * Gets the idSeries value for this IdentityCardType.
     * 
     * @return idSeries
     */
    public java.lang.String getIdSeries() {
        return idSeries;
    }


    /**
     * Sets the idSeries value for this IdentityCardType.
     * 
     * @param idSeries
     */
    public void setIdSeries(java.lang.String idSeries) {
        this.idSeries = idSeries;
    }


    /**
     * Gets the idNum value for this IdentityCardType.
     * 
     * @return idNum
     */
    public java.lang.String getIdNum() {
        return idNum;
    }


    /**
     * Sets the idNum value for this IdentityCardType.
     * 
     * @param idNum
     */
    public void setIdNum(java.lang.String idNum) {
        this.idNum = idNum;
    }


    /**
     * Gets the issuedBy value for this IdentityCardType.
     * 
     * @return issuedBy
     */
    public java.lang.String getIssuedBy() {
        return issuedBy;
    }


    /**
     * Sets the issuedBy value for this IdentityCardType.
     * 
     * @param issuedBy
     */
    public void setIssuedBy(java.lang.String issuedBy) {
        this.issuedBy = issuedBy;
    }


    /**
     * Gets the issueDt value for this IdentityCardType.
     * 
     * @return issueDt
     */
    public java.util.Date getIssueDt() {
        return issueDt;
    }


    /**
     * Sets the issueDt value for this IdentityCardType.
     * 
     * @param issueDt
     */
    public void setIssueDt(java.util.Date issueDt) {
        this.issueDt = issueDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdentityCardType)) return false;
        IdentityCardType other = (IdentityCardType) obj;
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
            ((this.issueDt==null && other.getIssueDt()==null) || 
             (this.issueDt!=null &&
              this.issueDt.equals(other.getIssueDt())));
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
        if (getIssueDt() != null) {
            _hashCode += getIssueDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdentityCardType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityCardType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSeries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdSeries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IssuedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issueDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IssueDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
