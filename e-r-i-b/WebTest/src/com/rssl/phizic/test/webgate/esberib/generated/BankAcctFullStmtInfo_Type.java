/**
 * BankAcctFullStmtInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о запросе полной выписки по ОМС
 */
public class BankAcctFullStmtInfo_Type  implements java.io.Serializable {
    /* Дата начала периода выписки */
    private java.lang.String fromDate;

    /* Дата окончания периода выписки */
    private java.lang.String toDate;

    /* Дата предыдущей операции по счету */
    private java.lang.String lastDate;

    private java.lang.String curAmtCur;

    /* Входящий остаток */
    private com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type startBalance;

    /* Исходящий остаток */
    private com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type endBalance;

    /* Учетная цена Банка России (руб/гр) на дату получения выписки */
    private java.math.BigDecimal rate;

    public BankAcctFullStmtInfo_Type() {
    }

    public BankAcctFullStmtInfo_Type(
           java.lang.String fromDate,
           java.lang.String toDate,
           java.lang.String lastDate,
           java.lang.String curAmtCur,
           com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type startBalance,
           com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type endBalance,
           java.math.BigDecimal rate) {
           this.fromDate = fromDate;
           this.toDate = toDate;
           this.lastDate = lastDate;
           this.curAmtCur = curAmtCur;
           this.startBalance = startBalance;
           this.endBalance = endBalance;
           this.rate = rate;
    }


    /**
     * Gets the fromDate value for this BankAcctFullStmtInfo_Type.
     * 
     * @return fromDate   * Дата начала периода выписки
     */
    public java.lang.String getFromDate() {
        return fromDate;
    }


    /**
     * Sets the fromDate value for this BankAcctFullStmtInfo_Type.
     * 
     * @param fromDate   * Дата начала периода выписки
     */
    public void setFromDate(java.lang.String fromDate) {
        this.fromDate = fromDate;
    }


    /**
     * Gets the toDate value for this BankAcctFullStmtInfo_Type.
     * 
     * @return toDate   * Дата окончания периода выписки
     */
    public java.lang.String getToDate() {
        return toDate;
    }


    /**
     * Sets the toDate value for this BankAcctFullStmtInfo_Type.
     * 
     * @param toDate   * Дата окончания периода выписки
     */
    public void setToDate(java.lang.String toDate) {
        this.toDate = toDate;
    }


    /**
     * Gets the lastDate value for this BankAcctFullStmtInfo_Type.
     * 
     * @return lastDate   * Дата предыдущей операции по счету
     */
    public java.lang.String getLastDate() {
        return lastDate;
    }


    /**
     * Sets the lastDate value for this BankAcctFullStmtInfo_Type.
     * 
     * @param lastDate   * Дата предыдущей операции по счету
     */
    public void setLastDate(java.lang.String lastDate) {
        this.lastDate = lastDate;
    }


    /**
     * Gets the curAmtCur value for this BankAcctFullStmtInfo_Type.
     * 
     * @return curAmtCur
     */
    public java.lang.String getCurAmtCur() {
        return curAmtCur;
    }


    /**
     * Sets the curAmtCur value for this BankAcctFullStmtInfo_Type.
     * 
     * @param curAmtCur
     */
    public void setCurAmtCur(java.lang.String curAmtCur) {
        this.curAmtCur = curAmtCur;
    }


    /**
     * Gets the startBalance value for this BankAcctFullStmtInfo_Type.
     * 
     * @return startBalance   * Входящий остаток
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type getStartBalance() {
        return startBalance;
    }


    /**
     * Sets the startBalance value for this BankAcctFullStmtInfo_Type.
     * 
     * @param startBalance   * Входящий остаток
     */
    public void setStartBalance(com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type startBalance) {
        this.startBalance = startBalance;
    }


    /**
     * Gets the endBalance value for this BankAcctFullStmtInfo_Type.
     * 
     * @return endBalance   * Исходящий остаток
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type getEndBalance() {
        return endBalance;
    }


    /**
     * Sets the endBalance value for this BankAcctFullStmtInfo_Type.
     * 
     * @param endBalance   * Исходящий остаток
     */
    public void setEndBalance(com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type endBalance) {
        this.endBalance = endBalance;
    }


    /**
     * Gets the rate value for this BankAcctFullStmtInfo_Type.
     * 
     * @return rate   * Учетная цена Банка России (руб/гр) на дату получения выписки
     */
    public java.math.BigDecimal getRate() {
        return rate;
    }


    /**
     * Sets the rate value for this BankAcctFullStmtInfo_Type.
     * 
     * @param rate   * Учетная цена Банка России (руб/гр) на дату получения выписки
     */
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctFullStmtInfo_Type)) return false;
        BankAcctFullStmtInfo_Type other = (BankAcctFullStmtInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fromDate==null && other.getFromDate()==null) || 
             (this.fromDate!=null &&
              this.fromDate.equals(other.getFromDate()))) &&
            ((this.toDate==null && other.getToDate()==null) || 
             (this.toDate!=null &&
              this.toDate.equals(other.getToDate()))) &&
            ((this.lastDate==null && other.getLastDate()==null) || 
             (this.lastDate!=null &&
              this.lastDate.equals(other.getLastDate()))) &&
            ((this.curAmtCur==null && other.getCurAmtCur()==null) || 
             (this.curAmtCur!=null &&
              this.curAmtCur.equals(other.getCurAmtCur()))) &&
            ((this.startBalance==null && other.getStartBalance()==null) || 
             (this.startBalance!=null &&
              this.startBalance.equals(other.getStartBalance()))) &&
            ((this.endBalance==null && other.getEndBalance()==null) || 
             (this.endBalance!=null &&
              this.endBalance.equals(other.getEndBalance()))) &&
            ((this.rate==null && other.getRate()==null) || 
             (this.rate!=null &&
              this.rate.equals(other.getRate())));
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
        if (getFromDate() != null) {
            _hashCode += getFromDate().hashCode();
        }
        if (getToDate() != null) {
            _hashCode += getToDate().hashCode();
        }
        if (getLastDate() != null) {
            _hashCode += getLastDate().hashCode();
        }
        if (getCurAmtCur() != null) {
            _hashCode += getCurAmtCur().hashCode();
        }
        if (getStartBalance() != null) {
            _hashCode += getStartBalance().hashCode();
        }
        if (getEndBalance() != null) {
            _hashCode += getEndBalance().hashCode();
        }
        if (getRate() != null) {
            _hashCode += getRate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctFullStmtInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FromDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ToDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LastDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmtCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSBalance_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSBalance_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Rate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
