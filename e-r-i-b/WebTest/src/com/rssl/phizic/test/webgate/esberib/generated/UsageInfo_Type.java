/**
 * UsageInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип Информация о транзакционных ограничителях
 */
public class UsageInfo_Type  implements java.io.Serializable {
    /* Код ограничителя:
     * P2P_CUM_LIM – индивидуальный суточный лимит по счету карты для операций
     * перевода с карточного счета клиента на карточный счет другого клиента
     * через УС */
    private java.lang.String usageCode;

    /* Максимальная сумма */
    private java.math.BigDecimal maxCurAmt;

    private java.lang.String acctCur;

    public UsageInfo_Type() {
    }

    public UsageInfo_Type(
           java.lang.String usageCode,
           java.math.BigDecimal maxCurAmt,
           java.lang.String acctCur) {
           this.usageCode = usageCode;
           this.maxCurAmt = maxCurAmt;
           this.acctCur = acctCur;
    }


    /**
     * Gets the usageCode value for this UsageInfo_Type.
     * 
     * @return usageCode   * Код ограничителя:
     * P2P_CUM_LIM – индивидуальный суточный лимит по счету карты для операций
     * перевода с карточного счета клиента на карточный счет другого клиента
     * через УС
     */
    public java.lang.String getUsageCode() {
        return usageCode;
    }


    /**
     * Sets the usageCode value for this UsageInfo_Type.
     * 
     * @param usageCode   * Код ограничителя:
     * P2P_CUM_LIM – индивидуальный суточный лимит по счету карты для операций
     * перевода с карточного счета клиента на карточный счет другого клиента
     * через УС
     */
    public void setUsageCode(java.lang.String usageCode) {
        this.usageCode = usageCode;
    }


    /**
     * Gets the maxCurAmt value for this UsageInfo_Type.
     * 
     * @return maxCurAmt   * Максимальная сумма
     */
    public java.math.BigDecimal getMaxCurAmt() {
        return maxCurAmt;
    }


    /**
     * Sets the maxCurAmt value for this UsageInfo_Type.
     * 
     * @param maxCurAmt   * Максимальная сумма
     */
    public void setMaxCurAmt(java.math.BigDecimal maxCurAmt) {
        this.maxCurAmt = maxCurAmt;
    }


    /**
     * Gets the acctCur value for this UsageInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this UsageInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UsageInfo_Type)) return false;
        UsageInfo_Type other = (UsageInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.usageCode==null && other.getUsageCode()==null) || 
             (this.usageCode!=null &&
              this.usageCode.equals(other.getUsageCode()))) &&
            ((this.maxCurAmt==null && other.getMaxCurAmt()==null) || 
             (this.maxCurAmt!=null &&
              this.maxCurAmt.equals(other.getMaxCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur())));
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
        if (getUsageCode() != null) {
            _hashCode += getUsageCode().hashCode();
        }
        if (getMaxCurAmt() != null) {
            _hashCode += getMaxCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UsageInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UsageInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UsageCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxCurAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaxCurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
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
