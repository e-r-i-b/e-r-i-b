/**
 * BankAcctInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Дополнительные параметры для счета (договора)
 */
public class BankAcctInfo_Type  implements java.io.Serializable {
    /* Признак аннуитентности договора */
    private java.lang.Boolean ann;

    /* Признак аннуитентности договора */
    private java.lang.Boolean isAnn;

    private java.lang.String startDt;

    private java.lang.String expDt;

    public BankAcctInfo_Type() {
    }

    public BankAcctInfo_Type(
           java.lang.Boolean ann,
           java.lang.Boolean isAnn,
           java.lang.String startDt,
           java.lang.String expDt) {
           this.ann = ann;
           this.isAnn = isAnn;
           this.startDt = startDt;
           this.expDt = expDt;
    }


    /**
     * Gets the ann value for this BankAcctInfo_Type.
     * 
     * @return ann   * Признак аннуитентности договора
     */
    public java.lang.Boolean getAnn() {
        return ann;
    }


    /**
     * Sets the ann value for this BankAcctInfo_Type.
     * 
     * @param ann   * Признак аннуитентности договора
     */
    public void setAnn(java.lang.Boolean ann) {
        this.ann = ann;
    }


    /**
     * Gets the isAnn value for this BankAcctInfo_Type.
     * 
     * @return isAnn   * Признак аннуитентности договора
     */
    public java.lang.Boolean getIsAnn() {
        return isAnn;
    }


    /**
     * Sets the isAnn value for this BankAcctInfo_Type.
     * 
     * @param isAnn   * Признак аннуитентности договора
     */
    public void setIsAnn(java.lang.Boolean isAnn) {
        this.isAnn = isAnn;
    }


    /**
     * Gets the startDt value for this BankAcctInfo_Type.
     * 
     * @return startDt
     */
    public java.lang.String getStartDt() {
        return startDt;
    }


    /**
     * Sets the startDt value for this BankAcctInfo_Type.
     * 
     * @param startDt
     */
    public void setStartDt(java.lang.String startDt) {
        this.startDt = startDt;
    }


    /**
     * Gets the expDt value for this BankAcctInfo_Type.
     * 
     * @return expDt
     */
    public java.lang.String getExpDt() {
        return expDt;
    }


    /**
     * Sets the expDt value for this BankAcctInfo_Type.
     * 
     * @param expDt
     */
    public void setExpDt(java.lang.String expDt) {
        this.expDt = expDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctInfo_Type)) return false;
        BankAcctInfo_Type other = (BankAcctInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ann==null && other.getAnn()==null) || 
             (this.ann!=null &&
              this.ann.equals(other.getAnn()))) &&
            ((this.isAnn==null && other.getIsAnn()==null) || 
             (this.isAnn!=null &&
              this.isAnn.equals(other.getIsAnn()))) &&
            ((this.startDt==null && other.getStartDt()==null) || 
             (this.startDt!=null &&
              this.startDt.equals(other.getStartDt()))) &&
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
        if (getAnn() != null) {
            _hashCode += getAnn().hashCode();
        }
        if (getIsAnn() != null) {
            _hashCode += getIsAnn().hashCode();
        }
        if (getStartDt() != null) {
            _hashCode += getStartDt().hashCode();
        }
        if (getExpDt() != null) {
            _hashCode += getExpDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ann");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Ann"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isAnn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsAnn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExpDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Date"));
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
