/**
 * CardToNewDepAddRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип ответа для нтерфейса TCDO. Открытие вклада с переводом на него
 * денежных средств с МБК
 */
public class CardToNewDepAddRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type cardAuthorization;

    private com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo;

    public CardToNewDepAddRs_Type() {
    }

    public CardToNewDepAddRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status,
           com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type cardAuthorization,
           com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.cardAuthorization = cardAuthorization;
           this.agreemtInfo = agreemtInfo;
    }


    /**
     * Gets the rqUID value for this CardToNewDepAddRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this CardToNewDepAddRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this CardToNewDepAddRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this CardToNewDepAddRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this CardToNewDepAddRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this CardToNewDepAddRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this CardToNewDepAddRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CardToNewDepAddRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the cardAuthorization value for this CardToNewDepAddRs_Type.
     * 
     * @return cardAuthorization
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type getCardAuthorization() {
        return cardAuthorization;
    }


    /**
     * Sets the cardAuthorization value for this CardToNewDepAddRs_Type.
     * 
     * @param cardAuthorization
     */
    public void setCardAuthorization(com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type cardAuthorization) {
        this.cardAuthorization = cardAuthorization;
    }


    /**
     * Gets the agreemtInfo value for this CardToNewDepAddRs_Type.
     * 
     * @return agreemtInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type getAgreemtInfo() {
        return agreemtInfo;
    }


    /**
     * Sets the agreemtInfo value for this CardToNewDepAddRs_Type.
     * 
     * @param agreemtInfo
     */
    public void setAgreemtInfo(com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo) {
        this.agreemtInfo = agreemtInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardToNewDepAddRs_Type)) return false;
        CardToNewDepAddRs_Type other = (CardToNewDepAddRs_Type) obj;
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
        new org.apache.axis.description.TypeDesc(CardToNewDepAddRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardToNewDepAddRs_Type"));
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
        elemField.setFieldName("cardAuthorization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo_Type"));
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
