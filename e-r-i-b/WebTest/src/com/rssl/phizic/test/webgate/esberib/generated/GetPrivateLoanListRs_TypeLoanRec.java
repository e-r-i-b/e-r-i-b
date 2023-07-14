/**
 * GetPrivateLoanListRs_TypeLoanRec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class GetPrivateLoanListRs_TypeLoanRec  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanInfo_Type loanInfo;

    public GetPrivateLoanListRs_TypeLoanRec() {
    }

    public GetPrivateLoanListRs_TypeLoanRec(
           com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanInfo_Type loanInfo) {
           this.loanInfo = loanInfo;
    }


    /**
     * Gets the loanInfo value for this GetPrivateLoanListRs_TypeLoanRec.
     * 
     * @return loanInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanInfo_Type getLoanInfo() {
        return loanInfo;
    }


    /**
     * Sets the loanInfo value for this GetPrivateLoanListRs_TypeLoanRec.
     * 
     * @param loanInfo
     */
    public void setLoanInfo(com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanInfo_Type loanInfo) {
        this.loanInfo = loanInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPrivateLoanListRs_TypeLoanRec)) return false;
        GetPrivateLoanListRs_TypeLoanRec other = (GetPrivateLoanListRs_TypeLoanRec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.loanInfo==null && other.getLoanInfo()==null) || 
             (this.loanInfo!=null &&
              this.loanInfo.equals(other.getLoanInfo())));
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
        if (getLoanInfo() != null) {
            _hashCode += getLoanInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPrivateLoanListRs_TypeLoanRec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateLoanListRs_Type>LoanRec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateLoanInfo_Type"));
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
