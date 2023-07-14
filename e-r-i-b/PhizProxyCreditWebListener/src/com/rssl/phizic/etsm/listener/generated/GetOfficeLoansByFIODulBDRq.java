/**
 * GetOfficeLoansByFIODulBDRq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.etsm.listener.generated;

public class GetOfficeLoansByFIODulBDRq  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    private java.lang.String FIO;

    private java.lang.String DUL;

    private java.util.Calendar birthDay;

    public GetOfficeLoansByFIODulBDRq() {
    }

    public GetOfficeLoansByFIODulBDRq(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           java.lang.String FIO,
           java.lang.String DUL,
           java.util.Calendar birthDay) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.FIO = FIO;
           this.DUL = DUL;
           this.birthDay = birthDay;
    }


    /**
     * Gets the rqUID value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the FIO value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @return FIO
     */
    public java.lang.String getFIO() {
        return FIO;
    }


    /**
     * Sets the FIO value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @param FIO
     */
    public void setFIO(java.lang.String FIO) {
        this.FIO = FIO;
    }


    /**
     * Gets the DUL value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @return DUL
     */
    public java.lang.String getDUL() {
        return DUL;
    }


    /**
     * Sets the DUL value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @param DUL
     */
    public void setDUL(java.lang.String DUL) {
        this.DUL = DUL;
    }


    /**
     * Gets the birthDay value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @return birthDay
     */
    public java.util.Calendar getBirthDay() {
        return birthDay;
    }


    /**
     * Sets the birthDay value for this GetOfficeLoansByFIODulBDRq.
     * 
     * @param birthDay
     */
    public void setBirthDay(java.util.Calendar birthDay) {
        this.birthDay = birthDay;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOfficeLoansByFIODulBDRq)) return false;
        GetOfficeLoansByFIODulBDRq other = (GetOfficeLoansByFIODulBDRq) obj;
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
            ((this.FIO==null && other.getFIO()==null) || 
             (this.FIO!=null &&
              this.FIO.equals(other.getFIO()))) &&
            ((this.DUL==null && other.getDUL()==null) || 
             (this.DUL!=null &&
              this.DUL.equals(other.getDUL()))) &&
            ((this.birthDay==null && other.getBirthDay()==null) || 
             (this.birthDay!=null &&
              this.birthDay.equals(other.getBirthDay())));
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
        if (getFIO() != null) {
            _hashCode += getFIO().hashCode();
        }
        if (getDUL() != null) {
            _hashCode += getDUL().hashCode();
        }
        if (getBirthDay() != null) {
            _hashCode += getBirthDay().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetOfficeLoansByFIODulBDRq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "GetOfficeLoansByFIODulBDRq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "FIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DUL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "DUL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "BirthDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
