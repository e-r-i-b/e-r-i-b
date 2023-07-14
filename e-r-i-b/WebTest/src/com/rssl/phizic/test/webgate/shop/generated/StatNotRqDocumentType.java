/**
 * StatNotRqDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.shop.generated;

public class StatNotRqDocumentType  implements java.io.Serializable {
    private java.lang.String ERIBUID;

    private java.lang.String id;

    private java.lang.String UTRRNO;

    private java.lang.String state;

    public StatNotRqDocumentType() {
    }

    public StatNotRqDocumentType(
           java.lang.String ERIBUID,
           java.lang.String id,
           java.lang.String UTRRNO,
           java.lang.String state) {
           this.ERIBUID = ERIBUID;
           this.id = id;
           this.UTRRNO = UTRRNO;
           this.state = state;
    }


    /**
     * Gets the ERIBUID value for this StatNotRqDocumentType.
     * 
     * @return ERIBUID
     */
    public java.lang.String getERIBUID() {
        return ERIBUID;
    }


    /**
     * Sets the ERIBUID value for this StatNotRqDocumentType.
     * 
     * @param ERIBUID
     */
    public void setERIBUID(java.lang.String ERIBUID) {
        this.ERIBUID = ERIBUID;
    }


    /**
     * Gets the id value for this StatNotRqDocumentType.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this StatNotRqDocumentType.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the UTRRNO value for this StatNotRqDocumentType.
     * 
     * @return UTRRNO
     */
    public java.lang.String getUTRRNO() {
        return UTRRNO;
    }


    /**
     * Sets the UTRRNO value for this StatNotRqDocumentType.
     * 
     * @param UTRRNO
     */
    public void setUTRRNO(java.lang.String UTRRNO) {
        this.UTRRNO = UTRRNO;
    }


    /**
     * Gets the state value for this StatNotRqDocumentType.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this StatNotRqDocumentType.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatNotRqDocumentType)) return false;
        StatNotRqDocumentType other = (StatNotRqDocumentType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ERIBUID==null && other.getERIBUID()==null) || 
             (this.ERIBUID!=null &&
              this.ERIBUID.equals(other.getERIBUID()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.UTRRNO==null && other.getUTRRNO()==null) || 
             (this.UTRRNO!=null &&
              this.UTRRNO.equals(other.getUTRRNO()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState())));
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
        if (getERIBUID() != null) {
            _hashCode += getERIBUID().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getUTRRNO() != null) {
            _hashCode += getUTRRNO().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatNotRqDocumentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "StatNotRqDocumentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERIBUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ERIBUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "UUIDType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UTRRNO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "UTRRNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "StringType"));
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
