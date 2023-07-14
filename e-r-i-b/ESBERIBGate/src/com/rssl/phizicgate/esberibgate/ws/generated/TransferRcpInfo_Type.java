/**
 * TransferRcpInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Основание операции - описание договора (ДЕПО).
 */
public class TransferRcpInfo_Type  implements java.io.Serializable {
    /* Наименование депозитария получателя (ДЕПО) */
    private java.lang.String corrDepositary;

    /* Номер счета депо на который на осуществить перевод или с которого
     * необходимо принять ценные бумаги */
    private java.lang.String corrDepoAcctId;

    /* Владелец счета депо */
    private java.lang.String corrOwner;

    /* Детальная информации о владельце счета */
    private com.rssl.phizicgate.esberibgate.ws.generated.CorrOwnerDetail_Type corrOwnerDetail;

    /* Дополнительные реквизиты */
    private java.lang.String additionalInfo;

    /* Способ доставки сертификатов */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoDeliveryType_Type deliveryType;

    public TransferRcpInfo_Type() {
    }

    public TransferRcpInfo_Type(
           java.lang.String corrDepositary,
           java.lang.String corrDepoAcctId,
           java.lang.String corrOwner,
           com.rssl.phizicgate.esberibgate.ws.generated.CorrOwnerDetail_Type corrOwnerDetail,
           java.lang.String additionalInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoDeliveryType_Type deliveryType) {
           this.corrDepositary = corrDepositary;
           this.corrDepoAcctId = corrDepoAcctId;
           this.corrOwner = corrOwner;
           this.corrOwnerDetail = corrOwnerDetail;
           this.additionalInfo = additionalInfo;
           this.deliveryType = deliveryType;
    }


    /**
     * Gets the corrDepositary value for this TransferRcpInfo_Type.
     * 
     * @return corrDepositary   * Наименование депозитария получателя (ДЕПО)
     */
    public java.lang.String getCorrDepositary() {
        return corrDepositary;
    }


    /**
     * Sets the corrDepositary value for this TransferRcpInfo_Type.
     * 
     * @param corrDepositary   * Наименование депозитария получателя (ДЕПО)
     */
    public void setCorrDepositary(java.lang.String corrDepositary) {
        this.corrDepositary = corrDepositary;
    }


    /**
     * Gets the corrDepoAcctId value for this TransferRcpInfo_Type.
     * 
     * @return corrDepoAcctId   * Номер счета депо на который на осуществить перевод или с которого
     * необходимо принять ценные бумаги
     */
    public java.lang.String getCorrDepoAcctId() {
        return corrDepoAcctId;
    }


    /**
     * Sets the corrDepoAcctId value for this TransferRcpInfo_Type.
     * 
     * @param corrDepoAcctId   * Номер счета депо на который на осуществить перевод или с которого
     * необходимо принять ценные бумаги
     */
    public void setCorrDepoAcctId(java.lang.String corrDepoAcctId) {
        this.corrDepoAcctId = corrDepoAcctId;
    }


    /**
     * Gets the corrOwner value for this TransferRcpInfo_Type.
     * 
     * @return corrOwner   * Владелец счета депо
     */
    public java.lang.String getCorrOwner() {
        return corrOwner;
    }


    /**
     * Sets the corrOwner value for this TransferRcpInfo_Type.
     * 
     * @param corrOwner   * Владелец счета депо
     */
    public void setCorrOwner(java.lang.String corrOwner) {
        this.corrOwner = corrOwner;
    }


    /**
     * Gets the corrOwnerDetail value for this TransferRcpInfo_Type.
     * 
     * @return corrOwnerDetail   * Детальная информации о владельце счета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CorrOwnerDetail_Type getCorrOwnerDetail() {
        return corrOwnerDetail;
    }


    /**
     * Sets the corrOwnerDetail value for this TransferRcpInfo_Type.
     * 
     * @param corrOwnerDetail   * Детальная информации о владельце счета
     */
    public void setCorrOwnerDetail(com.rssl.phizicgate.esberibgate.ws.generated.CorrOwnerDetail_Type corrOwnerDetail) {
        this.corrOwnerDetail = corrOwnerDetail;
    }


    /**
     * Gets the additionalInfo value for this TransferRcpInfo_Type.
     * 
     * @return additionalInfo   * Дополнительные реквизиты
     */
    public java.lang.String getAdditionalInfo() {
        return additionalInfo;
    }


    /**
     * Sets the additionalInfo value for this TransferRcpInfo_Type.
     * 
     * @param additionalInfo   * Дополнительные реквизиты
     */
    public void setAdditionalInfo(java.lang.String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    /**
     * Gets the deliveryType value for this TransferRcpInfo_Type.
     * 
     * @return deliveryType   * Способ доставки сертификатов
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoDeliveryType_Type getDeliveryType() {
        return deliveryType;
    }


    /**
     * Sets the deliveryType value for this TransferRcpInfo_Type.
     * 
     * @param deliveryType   * Способ доставки сертификатов
     */
    public void setDeliveryType(com.rssl.phizicgate.esberibgate.ws.generated.DepoDeliveryType_Type deliveryType) {
        this.deliveryType = deliveryType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransferRcpInfo_Type)) return false;
        TransferRcpInfo_Type other = (TransferRcpInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.corrDepositary==null && other.getCorrDepositary()==null) || 
             (this.corrDepositary!=null &&
              this.corrDepositary.equals(other.getCorrDepositary()))) &&
            ((this.corrDepoAcctId==null && other.getCorrDepoAcctId()==null) || 
             (this.corrDepoAcctId!=null &&
              this.corrDepoAcctId.equals(other.getCorrDepoAcctId()))) &&
            ((this.corrOwner==null && other.getCorrOwner()==null) || 
             (this.corrOwner!=null &&
              this.corrOwner.equals(other.getCorrOwner()))) &&
            ((this.corrOwnerDetail==null && other.getCorrOwnerDetail()==null) || 
             (this.corrOwnerDetail!=null &&
              this.corrOwnerDetail.equals(other.getCorrOwnerDetail()))) &&
            ((this.additionalInfo==null && other.getAdditionalInfo()==null) || 
             (this.additionalInfo!=null &&
              this.additionalInfo.equals(other.getAdditionalInfo()))) &&
            ((this.deliveryType==null && other.getDeliveryType()==null) || 
             (this.deliveryType!=null &&
              this.deliveryType.equals(other.getDeliveryType())));
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
        if (getCorrDepositary() != null) {
            _hashCode += getCorrDepositary().hashCode();
        }
        if (getCorrDepoAcctId() != null) {
            _hashCode += getCorrDepoAcctId().hashCode();
        }
        if (getCorrOwner() != null) {
            _hashCode += getCorrOwner().hashCode();
        }
        if (getCorrOwnerDetail() != null) {
            _hashCode += getCorrOwnerDetail().hashCode();
        }
        if (getAdditionalInfo() != null) {
            _hashCode += getAdditionalInfo().hashCode();
        }
        if (getDeliveryType() != null) {
            _hashCode += getDeliveryType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransferRcpInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransferRcpInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrDepositary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrDepositary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrDepoAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrDepoAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrOwner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrOwner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrOwnerDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrOwnerDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrOwnerDetail_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AdditionalInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeliveryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeliveryType_Type"));
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
