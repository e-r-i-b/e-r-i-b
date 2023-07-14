/**
 * DepoAgreement_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Описание договора (ДЕПО).
 */
public class DepoAgreement_Type  implements java.io.Serializable {
    /* Номер договора */
    private java.lang.String numberAgreement;

    /* Дата договора */
    private java.lang.String dateAgreement;

    /* Сумма по договору */
    private java.math.BigDecimal summaAgreement;

    public DepoAgreement_Type() {
    }

    public DepoAgreement_Type(
           java.lang.String numberAgreement,
           java.lang.String dateAgreement,
           java.math.BigDecimal summaAgreement) {
           this.numberAgreement = numberAgreement;
           this.dateAgreement = dateAgreement;
           this.summaAgreement = summaAgreement;
    }


    /**
     * Gets the numberAgreement value for this DepoAgreement_Type.
     * 
     * @return numberAgreement   * Номер договора
     */
    public java.lang.String getNumberAgreement() {
        return numberAgreement;
    }


    /**
     * Sets the numberAgreement value for this DepoAgreement_Type.
     * 
     * @param numberAgreement   * Номер договора
     */
    public void setNumberAgreement(java.lang.String numberAgreement) {
        this.numberAgreement = numberAgreement;
    }


    /**
     * Gets the dateAgreement value for this DepoAgreement_Type.
     * 
     * @return dateAgreement   * Дата договора
     */
    public java.lang.String getDateAgreement() {
        return dateAgreement;
    }


    /**
     * Sets the dateAgreement value for this DepoAgreement_Type.
     * 
     * @param dateAgreement   * Дата договора
     */
    public void setDateAgreement(java.lang.String dateAgreement) {
        this.dateAgreement = dateAgreement;
    }


    /**
     * Gets the summaAgreement value for this DepoAgreement_Type.
     * 
     * @return summaAgreement   * Сумма по договору
     */
    public java.math.BigDecimal getSummaAgreement() {
        return summaAgreement;
    }


    /**
     * Sets the summaAgreement value for this DepoAgreement_Type.
     * 
     * @param summaAgreement   * Сумма по договору
     */
    public void setSummaAgreement(java.math.BigDecimal summaAgreement) {
        this.summaAgreement = summaAgreement;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAgreement_Type)) return false;
        DepoAgreement_Type other = (DepoAgreement_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.numberAgreement==null && other.getNumberAgreement()==null) || 
             (this.numberAgreement!=null &&
              this.numberAgreement.equals(other.getNumberAgreement()))) &&
            ((this.dateAgreement==null && other.getDateAgreement()==null) || 
             (this.dateAgreement!=null &&
              this.dateAgreement.equals(other.getDateAgreement()))) &&
            ((this.summaAgreement==null && other.getSummaAgreement()==null) || 
             (this.summaAgreement!=null &&
              this.summaAgreement.equals(other.getSummaAgreement())));
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
        if (getNumberAgreement() != null) {
            _hashCode += getNumberAgreement().hashCode();
        }
        if (getDateAgreement() != null) {
            _hashCode += getDateAgreement().hashCode();
        }
        if (getSummaAgreement() != null) {
            _hashCode += getSummaAgreement().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAgreement_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAgreement_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberAgreement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NumberAgreement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateAgreement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateAgreement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summaAgreement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaAgreement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
