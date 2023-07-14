/**
 * DepoSecurityRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о разделе счета (ДЕПО).
 */
public class DepoSecurityRec_Type  implements java.io.Serializable {
    /* Тип и номер раздела */
    private com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type divisionNumber;

    /* Информация о ценной бумаге */
    private com.rssl.phizic.test.webgate.esberib.generated.DepoSecuritySectionInfo_Type[] sectionInfo;

    public DepoSecurityRec_Type() {
    }

    public DepoSecurityRec_Type(
           com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type divisionNumber,
           com.rssl.phizic.test.webgate.esberib.generated.DepoSecuritySectionInfo_Type[] sectionInfo) {
           this.divisionNumber = divisionNumber;
           this.sectionInfo = sectionInfo;
    }


    /**
     * Gets the divisionNumber value for this DepoSecurityRec_Type.
     * 
     * @return divisionNumber   * Тип и номер раздела
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type getDivisionNumber() {
        return divisionNumber;
    }


    /**
     * Sets the divisionNumber value for this DepoSecurityRec_Type.
     * 
     * @param divisionNumber   * Тип и номер раздела
     */
    public void setDivisionNumber(com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type divisionNumber) {
        this.divisionNumber = divisionNumber;
    }


    /**
     * Gets the sectionInfo value for this DepoSecurityRec_Type.
     * 
     * @return sectionInfo   * Информация о ценной бумаге
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoSecuritySectionInfo_Type[] getSectionInfo() {
        return sectionInfo;
    }


    /**
     * Sets the sectionInfo value for this DepoSecurityRec_Type.
     * 
     * @param sectionInfo   * Информация о ценной бумаге
     */
    public void setSectionInfo(com.rssl.phizic.test.webgate.esberib.generated.DepoSecuritySectionInfo_Type[] sectionInfo) {
        this.sectionInfo = sectionInfo;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.DepoSecuritySectionInfo_Type getSectionInfo(int i) {
        return this.sectionInfo[i];
    }

    public void setSectionInfo(int i, com.rssl.phizic.test.webgate.esberib.generated.DepoSecuritySectionInfo_Type _value) {
        this.sectionInfo[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoSecurityRec_Type)) return false;
        DepoSecurityRec_Type other = (DepoSecurityRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.divisionNumber==null && other.getDivisionNumber()==null) || 
             (this.divisionNumber!=null &&
              this.divisionNumber.equals(other.getDivisionNumber()))) &&
            ((this.sectionInfo==null && other.getSectionInfo()==null) || 
             (this.sectionInfo!=null &&
              java.util.Arrays.equals(this.sectionInfo, other.getSectionInfo())));
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
        if (getDivisionNumber() != null) {
            _hashCode += getDivisionNumber().hashCode();
        }
        if (getSectionInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSectionInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSectionInfo(), i);
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
        new org.apache.axis.description.TypeDesc(DepoSecurityRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("divisionNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DivisionNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DivisionNumber_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sectionInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SectionInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecuritySectionInfo_Type"));
        elemField.setMinOccurs(0);
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
