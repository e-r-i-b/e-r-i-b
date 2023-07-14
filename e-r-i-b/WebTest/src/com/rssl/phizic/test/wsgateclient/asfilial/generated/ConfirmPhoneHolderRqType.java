/**
 * ConfirmPhoneHolderRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.asfilial.generated;

public class ConfirmPhoneHolderRqType  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    /* Идентификатор операции REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String operUID;

    /* Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2" */
    private java.lang.String SName;

    private com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType[] phones;

    public ConfirmPhoneHolderRqType() {
    }

    public ConfirmPhoneHolderRqType(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           java.lang.String operUID,
           java.lang.String SName,
           com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType[] phones) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SName = SName;
           this.phones = phones;
    }


    /**
     * Gets the rqUID value for this ConfirmPhoneHolderRqType.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this ConfirmPhoneHolderRqType.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this ConfirmPhoneHolderRqType.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this ConfirmPhoneHolderRqType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this ConfirmPhoneHolderRqType.
     * 
     * @return operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this ConfirmPhoneHolderRqType.
     * 
     * @param operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SName value for this ConfirmPhoneHolderRqType.
     * 
     * @return SName   * Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2"
     */
    public java.lang.String getSName() {
        return SName;
    }


    /**
     * Sets the SName value for this ConfirmPhoneHolderRqType.
     * 
     * @param SName   * Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2"
     */
    public void setSName(java.lang.String SName) {
        this.SName = SName;
    }


    /**
     * Gets the phones value for this ConfirmPhoneHolderRqType.
     * 
     * @return phones
     */
    public com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType[] getPhones() {
        return phones;
    }


    /**
     * Sets the phones value for this ConfirmPhoneHolderRqType.
     * 
     * @param phones
     */
    public void setPhones(com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType[] phones) {
        this.phones = phones;
    }

    public com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType getPhones(int i) {
        return this.phones[i];
    }

    public void setPhones(int i, com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType _value) {
        this.phones[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConfirmPhoneHolderRqType)) return false;
        ConfirmPhoneHolderRqType other = (ConfirmPhoneHolderRqType) obj;
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
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.SName==null && other.getSName()==null) || 
             (this.SName!=null &&
              this.SName.equals(other.getSName()))) &&
            ((this.phones==null && other.getPhones()==null) || 
             (this.phones!=null &&
              java.util.Arrays.equals(this.phones, other.getPhones())));
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
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getSName() != null) {
            _hashCode += getSName().hashCode();
        }
        if (getPhones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPhones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPhones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConfirmPhoneHolderRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmPhoneHolderRqType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "SName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "Phones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberType"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
