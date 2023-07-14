/**
 * DepToNewDepAddRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип ответа для интерфейса TDDO и TDO. Открытие вклада с переводом
 * на него денежных средств с другого вклада (возможно с закрытием этого
 * вклада) и открытие бессрочного вклада без первоначального взноса
 */
public class DepToNewDepAddRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    private com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfoClose;

    private java.math.BigDecimal dstCurAmt;

    private java.math.BigDecimal srcCurAmt;

    /* Разбивка микроопераций списания */
    private com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo;

    public DepToNewDepAddRs_Type() {
    }

    public DepToNewDepAddRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status,
           com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo,
           com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfoClose,
           java.math.BigDecimal dstCurAmt,
           java.math.BigDecimal srcCurAmt,
           com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.agreemtInfo = agreemtInfo;
           this.agreemtInfoClose = agreemtInfoClose;
           this.dstCurAmt = dstCurAmt;
           this.srcCurAmt = srcCurAmt;
           this.srcLayoutInfo = srcLayoutInfo;
    }


    /**
     * Gets the rqUID value for this DepToNewDepAddRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this DepToNewDepAddRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this DepToNewDepAddRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this DepToNewDepAddRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this DepToNewDepAddRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this DepToNewDepAddRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this DepToNewDepAddRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepToNewDepAddRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the agreemtInfo value for this DepToNewDepAddRs_Type.
     * 
     * @return agreemtInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type getAgreemtInfo() {
        return agreemtInfo;
    }


    /**
     * Sets the agreemtInfo value for this DepToNewDepAddRs_Type.
     * 
     * @param agreemtInfo
     */
    public void setAgreemtInfo(com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo) {
        this.agreemtInfo = agreemtInfo;
    }


    /**
     * Gets the agreemtInfoClose value for this DepToNewDepAddRs_Type.
     * 
     * @return agreemtInfoClose
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type getAgreemtInfoClose() {
        return agreemtInfoClose;
    }


    /**
     * Sets the agreemtInfoClose value for this DepToNewDepAddRs_Type.
     * 
     * @param agreemtInfoClose
     */
    public void setAgreemtInfoClose(com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfoClose) {
        this.agreemtInfoClose = agreemtInfoClose;
    }


    /**
     * Gets the dstCurAmt value for this DepToNewDepAddRs_Type.
     * 
     * @return dstCurAmt
     */
    public java.math.BigDecimal getDstCurAmt() {
        return dstCurAmt;
    }


    /**
     * Sets the dstCurAmt value for this DepToNewDepAddRs_Type.
     * 
     * @param dstCurAmt
     */
    public void setDstCurAmt(java.math.BigDecimal dstCurAmt) {
        this.dstCurAmt = dstCurAmt;
    }


    /**
     * Gets the srcCurAmt value for this DepToNewDepAddRs_Type.
     * 
     * @return srcCurAmt
     */
    public java.math.BigDecimal getSrcCurAmt() {
        return srcCurAmt;
    }


    /**
     * Sets the srcCurAmt value for this DepToNewDepAddRs_Type.
     * 
     * @param srcCurAmt
     */
    public void setSrcCurAmt(java.math.BigDecimal srcCurAmt) {
        this.srcCurAmt = srcCurAmt;
    }


    /**
     * Gets the srcLayoutInfo value for this DepToNewDepAddRs_Type.
     * 
     * @return srcLayoutInfo   * Разбивка микроопераций списания
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type getSrcLayoutInfo() {
        return srcLayoutInfo;
    }


    /**
     * Sets the srcLayoutInfo value for this DepToNewDepAddRs_Type.
     * 
     * @param srcLayoutInfo   * Разбивка микроопераций списания
     */
    public void setSrcLayoutInfo(com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo) {
        this.srcLayoutInfo = srcLayoutInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepToNewDepAddRs_Type)) return false;
        DepToNewDepAddRs_Type other = (DepToNewDepAddRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
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
              this.srcCurAmt.equals(other.getSrcCurAmt()))) &&
            ((this.srcLayoutInfo==null && other.getSrcLayoutInfo()==null) || 
             (this.srcLayoutInfo!=null &&
              this.srcLayoutInfo.equals(other.getSrcLayoutInfo())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
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
        if (getSrcLayoutInfo() != null) {
            _hashCode += getSrcLayoutInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepToNewDepAddRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepToNewDepAddRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo_Type"));
        elemField.setMinOccurs(0);
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcLayoutInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo_Type"));
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
