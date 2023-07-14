/**
 * SecuritiesRecShort_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информациооная запись по ценной бумаге
 */
public class SecuritiesRecShort_Type  implements java.io.Serializable {
    /* Бланк ценной бумаги */
    private com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo;

    /* Базовая информация ценной бумаги */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type securitiesInfo;

    /* Признак нахождения на хранении */
    private java.lang.Boolean onStorageInBank;

    public SecuritiesRecShort_Type() {
    }

    public SecuritiesRecShort_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type securitiesInfo,
           java.lang.Boolean onStorageInBank) {
           this.blankInfo = blankInfo;
           this.securitiesInfo = securitiesInfo;
           this.onStorageInBank = onStorageInBank;
    }


    /**
     * Gets the blankInfo value for this SecuritiesRecShort_Type.
     * 
     * @return blankInfo   * Бланк ценной бумаги
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type getBlankInfo() {
        return blankInfo;
    }


    /**
     * Sets the blankInfo value for this SecuritiesRecShort_Type.
     * 
     * @param blankInfo   * Бланк ценной бумаги
     */
    public void setBlankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo) {
        this.blankInfo = blankInfo;
    }


    /**
     * Gets the securitiesInfo value for this SecuritiesRecShort_Type.
     * 
     * @return securitiesInfo   * Базовая информация ценной бумаги
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type getSecuritiesInfo() {
        return securitiesInfo;
    }


    /**
     * Sets the securitiesInfo value for this SecuritiesRecShort_Type.
     * 
     * @param securitiesInfo   * Базовая информация ценной бумаги
     */
    public void setSecuritiesInfo(com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type securitiesInfo) {
        this.securitiesInfo = securitiesInfo;
    }


    /**
     * Gets the onStorageInBank value for this SecuritiesRecShort_Type.
     * 
     * @return onStorageInBank   * Признак нахождения на хранении
     */
    public java.lang.Boolean getOnStorageInBank() {
        return onStorageInBank;
    }


    /**
     * Sets the onStorageInBank value for this SecuritiesRecShort_Type.
     * 
     * @param onStorageInBank   * Признак нахождения на хранении
     */
    public void setOnStorageInBank(java.lang.Boolean onStorageInBank) {
        this.onStorageInBank = onStorageInBank;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesRecShort_Type)) return false;
        SecuritiesRecShort_Type other = (SecuritiesRecShort_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.blankInfo==null && other.getBlankInfo()==null) || 
             (this.blankInfo!=null &&
              this.blankInfo.equals(other.getBlankInfo()))) &&
            ((this.securitiesInfo==null && other.getSecuritiesInfo()==null) || 
             (this.securitiesInfo!=null &&
              this.securitiesInfo.equals(other.getSecuritiesInfo()))) &&
            ((this.onStorageInBank==null && other.getOnStorageInBank()==null) || 
             (this.onStorageInBank!=null &&
              this.onStorageInBank.equals(other.getOnStorageInBank())));
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
        if (getBlankInfo() != null) {
            _hashCode += getBlankInfo().hashCode();
        }
        if (getSecuritiesInfo() != null) {
            _hashCode += getSecuritiesInfo().hashCode();
        }
        if (getOnStorageInBank() != null) {
            _hashCode += getOnStorageInBank().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecuritiesRecShort_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRecShort_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securitiesInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onStorageInBank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OnStorageInBank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
