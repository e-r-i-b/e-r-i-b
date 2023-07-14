/**
 * SecuritiesInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Базовая информация ценной бумаги
 */
public class SecuritiesInfo_Type  implements java.io.Serializable {
    /* Сумма номинала ценной бумаги */
    private java.math.BigDecimal nominalAmt;

    /* Код валюты номинала ценной бумаги */
    private java.lang.String nominalCurCode;

    /* Процентная ставка дохода по ценной бумаге */
    private java.math.BigDecimal incomeRate;

    /* Сумма дохода по ценной бумаге */
    private java.math.BigDecimal incomeAmt;

    /* Дата составления (валютирования) ценной бумаги */
    private java.lang.String composeDt;

    /* Тип срока платежа по ценной бумаге */
    private java.lang.String termType;

    /* Срок оплаты ценной бумаги в днях */
    private java.lang.Long termDays;

    /* Дата начала срока оплаты ценной бумаги */
    private java.lang.String termStartDt;

    /* Дата окончания срока оплаты ценной бумаги */
    private java.lang.String termFinishDt;

    /* Дата наступления срока давности об оплате */
    private java.lang.String termLimitDt;

    public SecuritiesInfo_Type() {
    }

    public SecuritiesInfo_Type(
           java.math.BigDecimal nominalAmt,
           java.lang.String nominalCurCode,
           java.math.BigDecimal incomeRate,
           java.math.BigDecimal incomeAmt,
           java.lang.String composeDt,
           java.lang.String termType,
           java.lang.Long termDays,
           java.lang.String termStartDt,
           java.lang.String termFinishDt,
           java.lang.String termLimitDt) {
           this.nominalAmt = nominalAmt;
           this.nominalCurCode = nominalCurCode;
           this.incomeRate = incomeRate;
           this.incomeAmt = incomeAmt;
           this.composeDt = composeDt;
           this.termType = termType;
           this.termDays = termDays;
           this.termStartDt = termStartDt;
           this.termFinishDt = termFinishDt;
           this.termLimitDt = termLimitDt;
    }


    /**
     * Gets the nominalAmt value for this SecuritiesInfo_Type.
     * 
     * @return nominalAmt   * Сумма номинала ценной бумаги
     */
    public java.math.BigDecimal getNominalAmt() {
        return nominalAmt;
    }


    /**
     * Sets the nominalAmt value for this SecuritiesInfo_Type.
     * 
     * @param nominalAmt   * Сумма номинала ценной бумаги
     */
    public void setNominalAmt(java.math.BigDecimal nominalAmt) {
        this.nominalAmt = nominalAmt;
    }


    /**
     * Gets the nominalCurCode value for this SecuritiesInfo_Type.
     * 
     * @return nominalCurCode   * Код валюты номинала ценной бумаги
     */
    public java.lang.String getNominalCurCode() {
        return nominalCurCode;
    }


    /**
     * Sets the nominalCurCode value for this SecuritiesInfo_Type.
     * 
     * @param nominalCurCode   * Код валюты номинала ценной бумаги
     */
    public void setNominalCurCode(java.lang.String nominalCurCode) {
        this.nominalCurCode = nominalCurCode;
    }


    /**
     * Gets the incomeRate value for this SecuritiesInfo_Type.
     * 
     * @return incomeRate   * Процентная ставка дохода по ценной бумаге
     */
    public java.math.BigDecimal getIncomeRate() {
        return incomeRate;
    }


    /**
     * Sets the incomeRate value for this SecuritiesInfo_Type.
     * 
     * @param incomeRate   * Процентная ставка дохода по ценной бумаге
     */
    public void setIncomeRate(java.math.BigDecimal incomeRate) {
        this.incomeRate = incomeRate;
    }


    /**
     * Gets the incomeAmt value for this SecuritiesInfo_Type.
     * 
     * @return incomeAmt   * Сумма дохода по ценной бумаге
     */
    public java.math.BigDecimal getIncomeAmt() {
        return incomeAmt;
    }


    /**
     * Sets the incomeAmt value for this SecuritiesInfo_Type.
     * 
     * @param incomeAmt   * Сумма дохода по ценной бумаге
     */
    public void setIncomeAmt(java.math.BigDecimal incomeAmt) {
        this.incomeAmt = incomeAmt;
    }


    /**
     * Gets the composeDt value for this SecuritiesInfo_Type.
     * 
     * @return composeDt   * Дата составления (валютирования) ценной бумаги
     */
    public java.lang.String getComposeDt() {
        return composeDt;
    }


    /**
     * Sets the composeDt value for this SecuritiesInfo_Type.
     * 
     * @param composeDt   * Дата составления (валютирования) ценной бумаги
     */
    public void setComposeDt(java.lang.String composeDt) {
        this.composeDt = composeDt;
    }


    /**
     * Gets the termType value for this SecuritiesInfo_Type.
     * 
     * @return termType   * Тип срока платежа по ценной бумаге
     */
    public java.lang.String getTermType() {
        return termType;
    }


    /**
     * Sets the termType value for this SecuritiesInfo_Type.
     * 
     * @param termType   * Тип срока платежа по ценной бумаге
     */
    public void setTermType(java.lang.String termType) {
        this.termType = termType;
    }


    /**
     * Gets the termDays value for this SecuritiesInfo_Type.
     * 
     * @return termDays   * Срок оплаты ценной бумаги в днях
     */
    public java.lang.Long getTermDays() {
        return termDays;
    }


    /**
     * Sets the termDays value for this SecuritiesInfo_Type.
     * 
     * @param termDays   * Срок оплаты ценной бумаги в днях
     */
    public void setTermDays(java.lang.Long termDays) {
        this.termDays = termDays;
    }


    /**
     * Gets the termStartDt value for this SecuritiesInfo_Type.
     * 
     * @return termStartDt   * Дата начала срока оплаты ценной бумаги
     */
    public java.lang.String getTermStartDt() {
        return termStartDt;
    }


    /**
     * Sets the termStartDt value for this SecuritiesInfo_Type.
     * 
     * @param termStartDt   * Дата начала срока оплаты ценной бумаги
     */
    public void setTermStartDt(java.lang.String termStartDt) {
        this.termStartDt = termStartDt;
    }


    /**
     * Gets the termFinishDt value for this SecuritiesInfo_Type.
     * 
     * @return termFinishDt   * Дата окончания срока оплаты ценной бумаги
     */
    public java.lang.String getTermFinishDt() {
        return termFinishDt;
    }


    /**
     * Sets the termFinishDt value for this SecuritiesInfo_Type.
     * 
     * @param termFinishDt   * Дата окончания срока оплаты ценной бумаги
     */
    public void setTermFinishDt(java.lang.String termFinishDt) {
        this.termFinishDt = termFinishDt;
    }


    /**
     * Gets the termLimitDt value for this SecuritiesInfo_Type.
     * 
     * @return termLimitDt   * Дата наступления срока давности об оплате
     */
    public java.lang.String getTermLimitDt() {
        return termLimitDt;
    }


    /**
     * Sets the termLimitDt value for this SecuritiesInfo_Type.
     * 
     * @param termLimitDt   * Дата наступления срока давности об оплате
     */
    public void setTermLimitDt(java.lang.String termLimitDt) {
        this.termLimitDt = termLimitDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesInfo_Type)) return false;
        SecuritiesInfo_Type other = (SecuritiesInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nominalAmt==null && other.getNominalAmt()==null) || 
             (this.nominalAmt!=null &&
              this.nominalAmt.equals(other.getNominalAmt()))) &&
            ((this.nominalCurCode==null && other.getNominalCurCode()==null) || 
             (this.nominalCurCode!=null &&
              this.nominalCurCode.equals(other.getNominalCurCode()))) &&
            ((this.incomeRate==null && other.getIncomeRate()==null) || 
             (this.incomeRate!=null &&
              this.incomeRate.equals(other.getIncomeRate()))) &&
            ((this.incomeAmt==null && other.getIncomeAmt()==null) || 
             (this.incomeAmt!=null &&
              this.incomeAmt.equals(other.getIncomeAmt()))) &&
            ((this.composeDt==null && other.getComposeDt()==null) || 
             (this.composeDt!=null &&
              this.composeDt.equals(other.getComposeDt()))) &&
            ((this.termType==null && other.getTermType()==null) || 
             (this.termType!=null &&
              this.termType.equals(other.getTermType()))) &&
            ((this.termDays==null && other.getTermDays()==null) || 
             (this.termDays!=null &&
              this.termDays.equals(other.getTermDays()))) &&
            ((this.termStartDt==null && other.getTermStartDt()==null) || 
             (this.termStartDt!=null &&
              this.termStartDt.equals(other.getTermStartDt()))) &&
            ((this.termFinishDt==null && other.getTermFinishDt()==null) || 
             (this.termFinishDt!=null &&
              this.termFinishDt.equals(other.getTermFinishDt()))) &&
            ((this.termLimitDt==null && other.getTermLimitDt()==null) || 
             (this.termLimitDt!=null &&
              this.termLimitDt.equals(other.getTermLimitDt())));
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
        if (getNominalAmt() != null) {
            _hashCode += getNominalAmt().hashCode();
        }
        if (getNominalCurCode() != null) {
            _hashCode += getNominalCurCode().hashCode();
        }
        if (getIncomeRate() != null) {
            _hashCode += getIncomeRate().hashCode();
        }
        if (getIncomeAmt() != null) {
            _hashCode += getIncomeAmt().hashCode();
        }
        if (getComposeDt() != null) {
            _hashCode += getComposeDt().hashCode();
        }
        if (getTermType() != null) {
            _hashCode += getTermType().hashCode();
        }
        if (getTermDays() != null) {
            _hashCode += getTermDays().hashCode();
        }
        if (getTermStartDt() != null) {
            _hashCode += getTermStartDt().hashCode();
        }
        if (getTermFinishDt() != null) {
            _hashCode += getTermFinishDt().hashCode();
        }
        if (getTermLimitDt() != null) {
            _hashCode += getTermLimitDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecuritiesInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nominalAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NominalAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nominalCurCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NominalCurCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IncomeRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IncomeAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("composeDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ComposeDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TermType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termDays");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TermDays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termStartDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TermStartDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termFinishDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TermFinishDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termLimitDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TermLimitDt"));
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
