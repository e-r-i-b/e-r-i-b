/**
 * DepAcctStmtGen_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class DepAcctStmtGen_Type  implements java.io.Serializable {
    /* Дата с которой необходимо получать выписку, включая эту дату */
    private java.lang.String dateFrom;

    /* Дата по которую необходимо получать выписку, включая эту дату */
    private java.lang.String dateTo;

    /* Входящий остаток (остаток на дату «С») */
    private java.math.BigDecimal balanceFrom;

    /* Валюта входящего остатка */
    private java.lang.String balanceFromCur;

    /* Исходящий остаток (остаток на дату «ПО») */
    private java.math.BigDecimal balanceTo;

    /* Валюта исходящего остатка */
    private java.lang.String balanceToCur;

    public DepAcctStmtGen_Type() {
    }

    public DepAcctStmtGen_Type(
           java.lang.String dateFrom,
           java.lang.String dateTo,
           java.math.BigDecimal balanceFrom,
           java.lang.String balanceFromCur,
           java.math.BigDecimal balanceTo,
           java.lang.String balanceToCur) {
           this.dateFrom = dateFrom;
           this.dateTo = dateTo;
           this.balanceFrom = balanceFrom;
           this.balanceFromCur = balanceFromCur;
           this.balanceTo = balanceTo;
           this.balanceToCur = balanceToCur;
    }


    /**
     * Gets the dateFrom value for this DepAcctStmtGen_Type.
     * 
     * @return dateFrom   * Дата с которой необходимо получать выписку, включая эту дату
     */
    public java.lang.String getDateFrom() {
        return dateFrom;
    }


    /**
     * Sets the dateFrom value for this DepAcctStmtGen_Type.
     * 
     * @param dateFrom   * Дата с которой необходимо получать выписку, включая эту дату
     */
    public void setDateFrom(java.lang.String dateFrom) {
        this.dateFrom = dateFrom;
    }


    /**
     * Gets the dateTo value for this DepAcctStmtGen_Type.
     * 
     * @return dateTo   * Дата по которую необходимо получать выписку, включая эту дату
     */
    public java.lang.String getDateTo() {
        return dateTo;
    }


    /**
     * Sets the dateTo value for this DepAcctStmtGen_Type.
     * 
     * @param dateTo   * Дата по которую необходимо получать выписку, включая эту дату
     */
    public void setDateTo(java.lang.String dateTo) {
        this.dateTo = dateTo;
    }


    /**
     * Gets the balanceFrom value for this DepAcctStmtGen_Type.
     * 
     * @return balanceFrom   * Входящий остаток (остаток на дату «С»)
     */
    public java.math.BigDecimal getBalanceFrom() {
        return balanceFrom;
    }


    /**
     * Sets the balanceFrom value for this DepAcctStmtGen_Type.
     * 
     * @param balanceFrom   * Входящий остаток (остаток на дату «С»)
     */
    public void setBalanceFrom(java.math.BigDecimal balanceFrom) {
        this.balanceFrom = balanceFrom;
    }


    /**
     * Gets the balanceFromCur value for this DepAcctStmtGen_Type.
     * 
     * @return balanceFromCur   * Валюта входящего остатка
     */
    public java.lang.String getBalanceFromCur() {
        return balanceFromCur;
    }


    /**
     * Sets the balanceFromCur value for this DepAcctStmtGen_Type.
     * 
     * @param balanceFromCur   * Валюта входящего остатка
     */
    public void setBalanceFromCur(java.lang.String balanceFromCur) {
        this.balanceFromCur = balanceFromCur;
    }


    /**
     * Gets the balanceTo value for this DepAcctStmtGen_Type.
     * 
     * @return balanceTo   * Исходящий остаток (остаток на дату «ПО»)
     */
    public java.math.BigDecimal getBalanceTo() {
        return balanceTo;
    }


    /**
     * Sets the balanceTo value for this DepAcctStmtGen_Type.
     * 
     * @param balanceTo   * Исходящий остаток (остаток на дату «ПО»)
     */
    public void setBalanceTo(java.math.BigDecimal balanceTo) {
        this.balanceTo = balanceTo;
    }


    /**
     * Gets the balanceToCur value for this DepAcctStmtGen_Type.
     * 
     * @return balanceToCur   * Валюта исходящего остатка
     */
    public java.lang.String getBalanceToCur() {
        return balanceToCur;
    }


    /**
     * Sets the balanceToCur value for this DepAcctStmtGen_Type.
     * 
     * @param balanceToCur   * Валюта исходящего остатка
     */
    public void setBalanceToCur(java.lang.String balanceToCur) {
        this.balanceToCur = balanceToCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAcctStmtGen_Type)) return false;
        DepAcctStmtGen_Type other = (DepAcctStmtGen_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateFrom==null && other.getDateFrom()==null) || 
             (this.dateFrom!=null &&
              this.dateFrom.equals(other.getDateFrom()))) &&
            ((this.dateTo==null && other.getDateTo()==null) || 
             (this.dateTo!=null &&
              this.dateTo.equals(other.getDateTo()))) &&
            ((this.balanceFrom==null && other.getBalanceFrom()==null) || 
             (this.balanceFrom!=null &&
              this.balanceFrom.equals(other.getBalanceFrom()))) &&
            ((this.balanceFromCur==null && other.getBalanceFromCur()==null) || 
             (this.balanceFromCur!=null &&
              this.balanceFromCur.equals(other.getBalanceFromCur()))) &&
            ((this.balanceTo==null && other.getBalanceTo()==null) || 
             (this.balanceTo!=null &&
              this.balanceTo.equals(other.getBalanceTo()))) &&
            ((this.balanceToCur==null && other.getBalanceToCur()==null) || 
             (this.balanceToCur!=null &&
              this.balanceToCur.equals(other.getBalanceToCur())));
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
        if (getDateFrom() != null) {
            _hashCode += getDateFrom().hashCode();
        }
        if (getDateTo() != null) {
            _hashCode += getDateTo().hashCode();
        }
        if (getBalanceFrom() != null) {
            _hashCode += getBalanceFrom().hashCode();
        }
        if (getBalanceFromCur() != null) {
            _hashCode += getBalanceFromCur().hashCode();
        }
        if (getBalanceTo() != null) {
            _hashCode += getBalanceTo().hashCode();
        }
        if (getBalanceToCur() != null) {
            _hashCode += getBalanceToCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepAcctStmtGen_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtGen_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalanceFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceFromCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalanceFromCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalanceTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceToCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalanceToCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
