/**
 * DepoSecuritySectionInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о ценной бумаге (ДЕПО).
 */
public class DepoSecuritySectionInfo_Type  implements java.io.Serializable {
    /* Маркер ценной бумаги */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecurityMarker_Type[] securityMarker;

    /* Депозитарный код выпуска ценной бумаги (партии неэмисионной
     * ЦБ) */
    private java.lang.String insideCode;

    /* Остаток, кол-во ценных бумаг на счете(шт.) */
    private long remainder;

    /* Метод хранения */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoStorageMethod_Type storageMethod;

    public DepoSecuritySectionInfo_Type() {
    }

    public DepoSecuritySectionInfo_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.SecurityMarker_Type[] securityMarker,
           java.lang.String insideCode,
           long remainder,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoStorageMethod_Type storageMethod) {
           this.securityMarker = securityMarker;
           this.insideCode = insideCode;
           this.remainder = remainder;
           this.storageMethod = storageMethod;
    }


    /**
     * Gets the securityMarker value for this DepoSecuritySectionInfo_Type.
     * 
     * @return securityMarker   * Маркер ценной бумаги
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecurityMarker_Type[] getSecurityMarker() {
        return securityMarker;
    }


    /**
     * Sets the securityMarker value for this DepoSecuritySectionInfo_Type.
     * 
     * @param securityMarker   * Маркер ценной бумаги
     */
    public void setSecurityMarker(com.rssl.phizicgate.esberibgate.ws.generated.SecurityMarker_Type[] securityMarker) {
        this.securityMarker = securityMarker;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.SecurityMarker_Type getSecurityMarker(int i) {
        return this.securityMarker[i];
    }

    public void setSecurityMarker(int i, com.rssl.phizicgate.esberibgate.ws.generated.SecurityMarker_Type _value) {
        this.securityMarker[i] = _value;
    }


    /**
     * Gets the insideCode value for this DepoSecuritySectionInfo_Type.
     * 
     * @return insideCode   * Депозитарный код выпуска ценной бумаги (партии неэмисионной
     * ЦБ)
     */
    public java.lang.String getInsideCode() {
        return insideCode;
    }


    /**
     * Sets the insideCode value for this DepoSecuritySectionInfo_Type.
     * 
     * @param insideCode   * Депозитарный код выпуска ценной бумаги (партии неэмисионной
     * ЦБ)
     */
    public void setInsideCode(java.lang.String insideCode) {
        this.insideCode = insideCode;
    }


    /**
     * Gets the remainder value for this DepoSecuritySectionInfo_Type.
     * 
     * @return remainder   * Остаток, кол-во ценных бумаг на счете(шт.)
     */
    public long getRemainder() {
        return remainder;
    }


    /**
     * Sets the remainder value for this DepoSecuritySectionInfo_Type.
     * 
     * @param remainder   * Остаток, кол-во ценных бумаг на счете(шт.)
     */
    public void setRemainder(long remainder) {
        this.remainder = remainder;
    }


    /**
     * Gets the storageMethod value for this DepoSecuritySectionInfo_Type.
     * 
     * @return storageMethod   * Метод хранения
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoStorageMethod_Type getStorageMethod() {
        return storageMethod;
    }


    /**
     * Sets the storageMethod value for this DepoSecuritySectionInfo_Type.
     * 
     * @param storageMethod   * Метод хранения
     */
    public void setStorageMethod(com.rssl.phizicgate.esberibgate.ws.generated.DepoStorageMethod_Type storageMethod) {
        this.storageMethod = storageMethod;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoSecuritySectionInfo_Type)) return false;
        DepoSecuritySectionInfo_Type other = (DepoSecuritySectionInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.securityMarker==null && other.getSecurityMarker()==null) || 
             (this.securityMarker!=null &&
              java.util.Arrays.equals(this.securityMarker, other.getSecurityMarker()))) &&
            ((this.insideCode==null && other.getInsideCode()==null) || 
             (this.insideCode!=null &&
              this.insideCode.equals(other.getInsideCode()))) &&
            this.remainder == other.getRemainder() &&
            ((this.storageMethod==null && other.getStorageMethod()==null) || 
             (this.storageMethod!=null &&
              this.storageMethod.equals(other.getStorageMethod())));
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
        if (getSecurityMarker() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSecurityMarker());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSecurityMarker(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInsideCode() != null) {
            _hashCode += getInsideCode().hashCode();
        }
        _hashCode += new Long(getRemainder()).hashCode();
        if (getStorageMethod() != null) {
            _hashCode += getStorageMethod().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoSecuritySectionInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecuritySectionInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityMarker");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityMarker"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityMarker_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insideCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsideCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remainder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Remainder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("storageMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StorageMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoStorageMethod_Type"));
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
