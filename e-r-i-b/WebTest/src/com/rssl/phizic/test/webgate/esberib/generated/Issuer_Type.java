/**
 * Issuer_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * ВСП, осуществляющий выдачу ЦБ
 */
public class Issuer_Type  implements java.io.Serializable {
    /* Информация о банке */
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfoExt_Type bankInfo;

    /* Информация о банке - текущем правоприемнике ВСП, осуществившего
     * выдачу ЦБ */
    private com.rssl.phizic.test.webgate.esberib.generated.Owner_Type bankOwnerInfo;

    public Issuer_Type() {
    }

    public Issuer_Type(
           com.rssl.phizic.test.webgate.esberib.generated.BankInfoExt_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.Owner_Type bankOwnerInfo) {
           this.bankInfo = bankInfo;
           this.bankOwnerInfo = bankOwnerInfo;
    }


    /**
     * Gets the bankInfo value for this Issuer_Type.
     * 
     * @return bankInfo   * Информация о банке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfoExt_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this Issuer_Type.
     * 
     * @param bankInfo   * Информация о банке
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfoExt_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the bankOwnerInfo value for this Issuer_Type.
     * 
     * @return bankOwnerInfo   * Информация о банке - текущем правоприемнике ВСП, осуществившего
     * выдачу ЦБ
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Owner_Type getBankOwnerInfo() {
        return bankOwnerInfo;
    }


    /**
     * Sets the bankOwnerInfo value for this Issuer_Type.
     * 
     * @param bankOwnerInfo   * Информация о банке - текущем правоприемнике ВСП, осуществившего
     * выдачу ЦБ
     */
    public void setBankOwnerInfo(com.rssl.phizic.test.webgate.esberib.generated.Owner_Type bankOwnerInfo) {
        this.bankOwnerInfo = bankOwnerInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Issuer_Type)) return false;
        Issuer_Type other = (Issuer_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.bankOwnerInfo==null && other.getBankOwnerInfo()==null) || 
             (this.bankOwnerInfo!=null &&
              this.bankOwnerInfo.equals(other.getBankOwnerInfo())));
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
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getBankOwnerInfo() != null) {
            _hashCode += getBankOwnerInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Issuer_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Issuer_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoExt_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankOwnerInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankOwnerInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Owner_Type"));
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
