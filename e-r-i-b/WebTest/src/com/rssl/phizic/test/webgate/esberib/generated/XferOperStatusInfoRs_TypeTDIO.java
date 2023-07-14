/**
 * XferOperStatusInfoRs_TypeTDIO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class XferOperStatusInfoRs_TypeTDIO  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type agreemtInfo;

    public XferOperStatusInfoRs_TypeTDIO() {
    }

    public XferOperStatusInfoRs_TypeTDIO(
           com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type agreemtInfo) {
           this.agreemtInfo = agreemtInfo;
    }


    /**
     * Gets the agreemtInfo value for this XferOperStatusInfoRs_TypeTDIO.
     * 
     * @return agreemtInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type getAgreemtInfo() {
        return agreemtInfo;
    }


    /**
     * Sets the agreemtInfo value for this XferOperStatusInfoRs_TypeTDIO.
     * 
     * @param agreemtInfo
     */
    public void setAgreemtInfo(com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type agreemtInfo) {
        this.agreemtInfo = agreemtInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XferOperStatusInfoRs_TypeTDIO)) return false;
        XferOperStatusInfoRs_TypeTDIO other = (XferOperStatusInfoRs_TypeTDIO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agreemtInfo==null && other.getAgreemtInfo()==null) || 
             (this.agreemtInfo!=null &&
              this.agreemtInfo.equals(other.getAgreemtInfo())));
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
        if (getAgreemtInfo() != null) {
            _hashCode += getAgreemtInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XferOperStatusInfoRs_TypeTDIO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TDIO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfoResponse_Type"));
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
