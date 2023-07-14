/**
 * ExctractLine_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class ExctractLine_Type  implements java.io.Serializable {
    /* Дата операции */
    private java.lang.String payDate;

    /* Сумма платежа */
    private java.math.BigDecimal summa;

    /* Валюта суммы платежа */
    private java.lang.String summaCur;

    /* Месяц платежа */
    private java.lang.String monthOfPay;

    /* БИК получателя */
    private java.lang.String recBIC;

    /* Корсчет получателя */
    private java.lang.String recCorrAccount;

    /* Расчетный счет получателя */
    private java.lang.String recCalcAccount;

    /* Статус платежа: 1 – исполнен, 2 – не выполнен */
    private java.lang.String status;

    public ExctractLine_Type() {
    }

    public ExctractLine_Type(
           java.lang.String payDate,
           java.math.BigDecimal summa,
           java.lang.String summaCur,
           java.lang.String monthOfPay,
           java.lang.String recBIC,
           java.lang.String recCorrAccount,
           java.lang.String recCalcAccount,
           java.lang.String status) {
           this.payDate = payDate;
           this.summa = summa;
           this.summaCur = summaCur;
           this.monthOfPay = monthOfPay;
           this.recBIC = recBIC;
           this.recCorrAccount = recCorrAccount;
           this.recCalcAccount = recCalcAccount;
           this.status = status;
    }


    /**
     * Gets the payDate value for this ExctractLine_Type.
     * 
     * @return payDate   * Дата операции
     */
    public java.lang.String getPayDate() {
        return payDate;
    }


    /**
     * Sets the payDate value for this ExctractLine_Type.
     * 
     * @param payDate   * Дата операции
     */
    public void setPayDate(java.lang.String payDate) {
        this.payDate = payDate;
    }


    /**
     * Gets the summa value for this ExctractLine_Type.
     * 
     * @return summa   * Сумма платежа
     */
    public java.math.BigDecimal getSumma() {
        return summa;
    }


    /**
     * Sets the summa value for this ExctractLine_Type.
     * 
     * @param summa   * Сумма платежа
     */
    public void setSumma(java.math.BigDecimal summa) {
        this.summa = summa;
    }


    /**
     * Gets the summaCur value for this ExctractLine_Type.
     * 
     * @return summaCur   * Валюта суммы платежа
     */
    public java.lang.String getSummaCur() {
        return summaCur;
    }


    /**
     * Sets the summaCur value for this ExctractLine_Type.
     * 
     * @param summaCur   * Валюта суммы платежа
     */
    public void setSummaCur(java.lang.String summaCur) {
        this.summaCur = summaCur;
    }


    /**
     * Gets the monthOfPay value for this ExctractLine_Type.
     * 
     * @return monthOfPay   * Месяц платежа
     */
    public java.lang.String getMonthOfPay() {
        return monthOfPay;
    }


    /**
     * Sets the monthOfPay value for this ExctractLine_Type.
     * 
     * @param monthOfPay   * Месяц платежа
     */
    public void setMonthOfPay(java.lang.String monthOfPay) {
        this.monthOfPay = monthOfPay;
    }


    /**
     * Gets the recBIC value for this ExctractLine_Type.
     * 
     * @return recBIC   * БИК получателя
     */
    public java.lang.String getRecBIC() {
        return recBIC;
    }


    /**
     * Sets the recBIC value for this ExctractLine_Type.
     * 
     * @param recBIC   * БИК получателя
     */
    public void setRecBIC(java.lang.String recBIC) {
        this.recBIC = recBIC;
    }


    /**
     * Gets the recCorrAccount value for this ExctractLine_Type.
     * 
     * @return recCorrAccount   * Корсчет получателя
     */
    public java.lang.String getRecCorrAccount() {
        return recCorrAccount;
    }


    /**
     * Sets the recCorrAccount value for this ExctractLine_Type.
     * 
     * @param recCorrAccount   * Корсчет получателя
     */
    public void setRecCorrAccount(java.lang.String recCorrAccount) {
        this.recCorrAccount = recCorrAccount;
    }


    /**
     * Gets the recCalcAccount value for this ExctractLine_Type.
     * 
     * @return recCalcAccount   * Расчетный счет получателя
     */
    public java.lang.String getRecCalcAccount() {
        return recCalcAccount;
    }


    /**
     * Sets the recCalcAccount value for this ExctractLine_Type.
     * 
     * @param recCalcAccount   * Расчетный счет получателя
     */
    public void setRecCalcAccount(java.lang.String recCalcAccount) {
        this.recCalcAccount = recCalcAccount;
    }


    /**
     * Gets the status value for this ExctractLine_Type.
     * 
     * @return status   * Статус платежа: 1 – исполнен, 2 – не выполнен
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ExctractLine_Type.
     * 
     * @param status   * Статус платежа: 1 – исполнен, 2 – не выполнен
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExctractLine_Type)) return false;
        ExctractLine_Type other = (ExctractLine_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.payDate==null && other.getPayDate()==null) || 
             (this.payDate!=null &&
              this.payDate.equals(other.getPayDate()))) &&
            ((this.summa==null && other.getSumma()==null) || 
             (this.summa!=null &&
              this.summa.equals(other.getSumma()))) &&
            ((this.summaCur==null && other.getSummaCur()==null) || 
             (this.summaCur!=null &&
              this.summaCur.equals(other.getSummaCur()))) &&
            ((this.monthOfPay==null && other.getMonthOfPay()==null) || 
             (this.monthOfPay!=null &&
              this.monthOfPay.equals(other.getMonthOfPay()))) &&
            ((this.recBIC==null && other.getRecBIC()==null) || 
             (this.recBIC!=null &&
              this.recBIC.equals(other.getRecBIC()))) &&
            ((this.recCorrAccount==null && other.getRecCorrAccount()==null) || 
             (this.recCorrAccount!=null &&
              this.recCorrAccount.equals(other.getRecCorrAccount()))) &&
            ((this.recCalcAccount==null && other.getRecCalcAccount()==null) || 
             (this.recCalcAccount!=null &&
              this.recCalcAccount.equals(other.getRecCalcAccount()))) &&
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
        if (getPayDate() != null) {
            _hashCode += getPayDate().hashCode();
        }
        if (getSumma() != null) {
            _hashCode += getSumma().hashCode();
        }
        if (getSummaCur() != null) {
            _hashCode += getSummaCur().hashCode();
        }
        if (getMonthOfPay() != null) {
            _hashCode += getMonthOfPay().hashCode();
        }
        if (getRecBIC() != null) {
            _hashCode += getRecBIC().hashCode();
        }
        if (getRecCorrAccount() != null) {
            _hashCode += getRecCorrAccount().hashCode();
        }
        if (getRecCalcAccount() != null) {
            _hashCode += getRecCalcAccount().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExctractLine_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExctractLine_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Summa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summaCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthOfPay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MonthOfPay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recBIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecBIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recCorrAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecCorrAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recCalcAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecCalcAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
