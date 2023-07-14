/**
 * CardAcctInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class CardAcctInfo_Type  implements java.io.Serializable {
    /* Тип дополнительной карты */
    private java.lang.String additionalCard;

    /* Номер основной карты */
    private java.lang.String mainCard;

    /* Дата очередного отчета по счету карты */
    private java.lang.String nextReportDate;

    /* Срок действия карты в формате процессинга YYMM */
    private java.lang.String endDtForWay;

    public CardAcctInfo_Type() {
    }

    public CardAcctInfo_Type(
           java.lang.String additionalCard,
           java.lang.String mainCard,
           java.lang.String nextReportDate,
           java.lang.String endDtForWay) {
           this.additionalCard = additionalCard;
           this.mainCard = mainCard;
           this.nextReportDate = nextReportDate;
           this.endDtForWay = endDtForWay;
    }


    /**
     * Gets the additionalCard value for this CardAcctInfo_Type.
     * 
     * @return additionalCard   * Тип дополнительной карты
     */
    public java.lang.String getAdditionalCard() {
        return additionalCard;
    }


    /**
     * Sets the additionalCard value for this CardAcctInfo_Type.
     * 
     * @param additionalCard   * Тип дополнительной карты
     */
    public void setAdditionalCard(java.lang.String additionalCard) {
        this.additionalCard = additionalCard;
    }


    /**
     * Gets the mainCard value for this CardAcctInfo_Type.
     * 
     * @return mainCard   * Номер основной карты
     */
    public java.lang.String getMainCard() {
        return mainCard;
    }


    /**
     * Sets the mainCard value for this CardAcctInfo_Type.
     * 
     * @param mainCard   * Номер основной карты
     */
    public void setMainCard(java.lang.String mainCard) {
        this.mainCard = mainCard;
    }


    /**
     * Gets the nextReportDate value for this CardAcctInfo_Type.
     * 
     * @return nextReportDate   * Дата очередного отчета по счету карты
     */
    public java.lang.String getNextReportDate() {
        return nextReportDate;
    }


    /**
     * Sets the nextReportDate value for this CardAcctInfo_Type.
     * 
     * @param nextReportDate   * Дата очередного отчета по счету карты
     */
    public void setNextReportDate(java.lang.String nextReportDate) {
        this.nextReportDate = nextReportDate;
    }


    /**
     * Gets the endDtForWay value for this CardAcctInfo_Type.
     * 
     * @return endDtForWay   * Срок действия карты в формате процессинга YYMM
     */
    public java.lang.String getEndDtForWay() {
        return endDtForWay;
    }


    /**
     * Sets the endDtForWay value for this CardAcctInfo_Type.
     * 
     * @param endDtForWay   * Срок действия карты в формате процессинга YYMM
     */
    public void setEndDtForWay(java.lang.String endDtForWay) {
        this.endDtForWay = endDtForWay;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardAcctInfo_Type)) return false;
        CardAcctInfo_Type other = (CardAcctInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.additionalCard==null && other.getAdditionalCard()==null) || 
             (this.additionalCard!=null &&
              this.additionalCard.equals(other.getAdditionalCard()))) &&
            ((this.mainCard==null && other.getMainCard()==null) || 
             (this.mainCard!=null &&
              this.mainCard.equals(other.getMainCard()))) &&
            ((this.nextReportDate==null && other.getNextReportDate()==null) || 
             (this.nextReportDate!=null &&
              this.nextReportDate.equals(other.getNextReportDate()))) &&
            ((this.endDtForWay==null && other.getEndDtForWay()==null) || 
             (this.endDtForWay!=null &&
              this.endDtForWay.equals(other.getEndDtForWay())));
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
        if (getAdditionalCard() != null) {
            _hashCode += getAdditionalCard().hashCode();
        }
        if (getMainCard() != null) {
            _hashCode += getMainCard().hashCode();
        }
        if (getNextReportDate() != null) {
            _hashCode += getNextReportDate().hashCode();
        }
        if (getEndDtForWay() != null) {
            _hashCode += getEndDtForWay().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardAcctInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AdditionalCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MainCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nextReportDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextReportDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDtForWay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDtForWay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
