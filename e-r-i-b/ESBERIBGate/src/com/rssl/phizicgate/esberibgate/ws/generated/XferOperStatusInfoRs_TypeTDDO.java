/**
 * XferOperStatusInfoRs_TypeTDDO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class XferOperStatusInfoRs_TypeTDDO  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type agreemtInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfo_Type agreemtInfoClose;

    private java.math.BigDecimal dstCurAmt;

    private java.math.BigDecimal srcCurAmt;

    public XferOperStatusInfoRs_TypeTDDO() {
    }

    public XferOperStatusInfoRs_TypeTDDO(
           com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type agreemtInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfo_Type agreemtInfoClose,
           java.math.BigDecimal dstCurAmt,
           java.math.BigDecimal srcCurAmt) {
           this.agreemtInfo = agreemtInfo;
           this.agreemtInfoClose = agreemtInfoClose;
           this.dstCurAmt = dstCurAmt;
           this.srcCurAmt = srcCurAmt;
    }


    /**
     * Gets the agreemtInfo value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @return agreemtInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type getAgreemtInfo() {
        return agreemtInfo;
    }


    /**
     * Sets the agreemtInfo value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @param agreemtInfo
     */
    public void setAgreemtInfo(com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfoResponse_Type agreemtInfo) {
        this.agreemtInfo = agreemtInfo;
    }


    /**
     * Gets the agreemtInfoClose value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @return agreemtInfoClose
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfo_Type getAgreemtInfoClose() {
        return agreemtInfoClose;
    }


    /**
     * Sets the agreemtInfoClose value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @param agreemtInfoClose
     */
    public void setAgreemtInfoClose(com.rssl.phizicgate.esberibgate.ws.generated.AgreemtInfo_Type agreemtInfoClose) {
        this.agreemtInfoClose = agreemtInfoClose;
    }


    /**
     * Gets the dstCurAmt value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @return dstCurAmt
     */
    public java.math.BigDecimal getDstCurAmt() {
        return dstCurAmt;
    }


    /**
     * Sets the dstCurAmt value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @param dstCurAmt
     */
    public void setDstCurAmt(java.math.BigDecimal dstCurAmt) {
        this.dstCurAmt = dstCurAmt;
    }


    /**
     * Gets the srcCurAmt value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @return srcCurAmt
     */
    public java.math.BigDecimal getSrcCurAmt() {
        return srcCurAmt;
    }


    /**
     * Sets the srcCurAmt value for this XferOperStatusInfoRs_TypeTDDO.
     * 
     * @param srcCurAmt
     */
    public void setSrcCurAmt(java.math.BigDecimal srcCurAmt) {
        this.srcCurAmt = srcCurAmt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XferOperStatusInfoRs_TypeTDDO)) return false;
        XferOperStatusInfoRs_TypeTDDO other = (XferOperStatusInfoRs_TypeTDDO) obj;
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
              this.agreemtInfo.equals(other.getAgreemtInfo()))) &&
            ((this.agreemtInfoClose==null && other.getAgreemtInfoClose()==null) || 
             (this.agreemtInfoClose!=null &&
              this.agreemtInfoClose.equals(other.getAgreemtInfoClose()))) &&
            ((this.dstCurAmt==null && other.getDstCurAmt()==null) || 
             (this.dstCurAmt!=null &&
              this.dstCurAmt.equals(other.getDstCurAmt()))) &&
            ((this.srcCurAmt==null && other.getSrcCurAmt()==null) || 
             (this.srcCurAmt!=null &&
              this.srcCurAmt.equals(other.getSrcCurAmt())));
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
        if (getAgreemtInfoClose() != null) {
            _hashCode += getAgreemtInfoClose().hashCode();
        }
        if (getDstCurAmt() != null) {
            _hashCode += getDstCurAmt().hashCode();
        }
        if (getSrcCurAmt() != null) {
            _hashCode += getSrcCurAmt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XferOperStatusInfoRs_TypeTDDO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TDDO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfoResponse_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtInfoClose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfoClose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dstCurAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DstCurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcCurAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcCurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
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
