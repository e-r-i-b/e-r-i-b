/**
 * DepoDetailOperationReason_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Основание операции - описание договора (ДЕПО).
 */
public class DepoDetailOperationReason_Type  implements java.io.Serializable {
    /* описание договора (ДЕПО) */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoAgreement_Type agreementDetail;

    public DepoDetailOperationReason_Type() {
    }

    public DepoDetailOperationReason_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAgreement_Type agreementDetail) {
           this.agreementDetail = agreementDetail;
    }


    /**
     * Gets the agreementDetail value for this DepoDetailOperationReason_Type.
     * 
     * @return agreementDetail   * описание договора (ДЕПО)
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoAgreement_Type getAgreementDetail() {
        return agreementDetail;
    }


    /**
     * Sets the agreementDetail value for this DepoDetailOperationReason_Type.
     * 
     * @param agreementDetail   * описание договора (ДЕПО)
     */
    public void setAgreementDetail(com.rssl.phizicgate.esberibgate.ws.generated.DepoAgreement_Type agreementDetail) {
        this.agreementDetail = agreementDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoDetailOperationReason_Type)) return false;
        DepoDetailOperationReason_Type other = (DepoDetailOperationReason_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agreementDetail==null && other.getAgreementDetail()==null) || 
             (this.agreementDetail!=null &&
              this.agreementDetail.equals(other.getAgreementDetail())));
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
        if (getAgreementDetail() != null) {
            _hashCode += getAgreementDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoDetailOperationReason_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDetailOperationReason_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreementDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreementDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAgreement_Type"));
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
