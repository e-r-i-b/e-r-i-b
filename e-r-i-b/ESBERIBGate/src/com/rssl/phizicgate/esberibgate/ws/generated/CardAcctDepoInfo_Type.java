/**
 * CardAcctDepoInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Дополнительная информация по карточному договору (ДЕПО)
 */
public class CardAcctDepoInfo_Type  implements java.io.Serializable {
    /* Наименование карты */
    private java.lang.String cardName;

    /* Валюта */
    private java.lang.String acctCur;

    /* Срок действия карты */
    private java.lang.String endDt;

    /* Статус карты */
    private com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type status;

    public CardAcctDepoInfo_Type() {
    }

    public CardAcctDepoInfo_Type(
           java.lang.String cardName,
           java.lang.String acctCur,
           java.lang.String endDt,
           com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type status) {
           this.cardName = cardName;
           this.acctCur = acctCur;
           this.endDt = endDt;
           this.status = status;
    }


    /**
     * Gets the cardName value for this CardAcctDepoInfo_Type.
     * 
     * @return cardName   * Наименование карты
     */
    public java.lang.String getCardName() {
        return cardName;
    }


    /**
     * Sets the cardName value for this CardAcctDepoInfo_Type.
     * 
     * @param cardName   * Наименование карты
     */
    public void setCardName(java.lang.String cardName) {
        this.cardName = cardName;
    }


    /**
     * Gets the acctCur value for this CardAcctDepoInfo_Type.
     * 
     * @return acctCur   * Валюта
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this CardAcctDepoInfo_Type.
     * 
     * @param acctCur   * Валюта
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the endDt value for this CardAcctDepoInfo_Type.
     * 
     * @return endDt   * Срок действия карты
     */
    public java.lang.String getEndDt() {
        return endDt;
    }


    /**
     * Sets the endDt value for this CardAcctDepoInfo_Type.
     * 
     * @param endDt   * Срок действия карты
     */
    public void setEndDt(java.lang.String endDt) {
        this.endDt = endDt;
    }


    /**
     * Gets the status value for this CardAcctDepoInfo_Type.
     * 
     * @return status   * Статус карты
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CardAcctDepoInfo_Type.
     * 
     * @param status   * Статус карты
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardAcctDepoInfo_Type)) return false;
        CardAcctDepoInfo_Type other = (CardAcctDepoInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cardName==null && other.getCardName()==null) || 
             (this.cardName!=null &&
              this.cardName.equals(other.getCardName()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.endDt==null && other.getEndDt()==null) || 
             (this.endDt!=null &&
              this.endDt.equals(other.getEndDt()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getCardName() != null) {
            _hashCode += getCardName().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getEndDt() != null) {
            _hashCode += getEndDt().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardAcctDepoInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctDepoInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccountStatusEnum_Type"));
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
