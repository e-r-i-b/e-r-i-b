/**
 * XferOperStatusInfoRs_TypeTCDO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class XferOperStatusInfoRs_TypeTCDO  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization;

    private com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type agreemtInfo;

    public XferOperStatusInfoRs_TypeTCDO() {
    }

    public XferOperStatusInfoRs_TypeTCDO(
           com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization,
           com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type agreemtInfo) {
           this.cardAuthorization = cardAuthorization;
           this.agreemtInfo = agreemtInfo;
    }


    /**
     * Gets the cardAuthorization value for this XferOperStatusInfoRs_TypeTCDO.
     * 
     * @return cardAuthorization
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type getCardAuthorization() {
        return cardAuthorization;
    }


    /**
     * Sets the cardAuthorization value for this XferOperStatusInfoRs_TypeTCDO.
     * 
     * @param cardAuthorization
     */
    public void setCardAuthorization(com.rssl.phizicgate.esberibgate.ws.generated.CardAuthorization_Type cardAuthorization) {
        this.cardAuthorization = cardAuthorization;
    }


    /**
     * Gets the agreemtInfo value for this XferOperStatusInfoRs_TypeTCDO.
     * 
     * @return agreemtInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type getAgreemtInfo() {
        return agreemtInfo;
    }


    /**
     * Sets the agreemtInfo value for this XferOperStatusInfoRs_TypeTCDO.
     * 
     * @param agreemtInfo
     */
    public void setAgreemtInfo(com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type agreemtInfo) {
        this.agreemtInfo = agreemtInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XferOperStatusInfoRs_TypeTCDO)) return false;
        XferOperStatusInfoRs_TypeTCDO other = (XferOperStatusInfoRs_TypeTCDO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cardAuthorization==null && other.getCardAuthorization()==null) || 
             (this.cardAuthorization!=null &&
              this.cardAuthorization.equals(other.getCardAuthorization()))) &&
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
        if (getCardAuthorization() != null) {
            _hashCode += getCardAuthorization().hashCode();
        }
        if (getAgreemtInfo() != null) {
            _hashCode += getAgreemtInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XferOperStatusInfoRs_TypeTCDO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TCDO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAuthorization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
