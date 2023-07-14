/**
 * TaxColl_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Налоговые реквизиты
 */
public class TaxColl_Type  implements java.io.Serializable {
    /* Статус составителя документа */
    private java.lang.String SRCstatus;

    /* КПП получателя */
    private java.lang.String taxRegCodeTo;

    /* Код бюджетной классификации */
    private java.lang.String budgetCode;

    /* Код ОКАТО */
    private java.lang.String OKATO;

    /* Основание налогового платежа */
    private java.lang.String taxBase;

    /* Налоговый период */
    private java.lang.String taxPeriod;

    /* Номер налогового документа */
    private java.lang.String taxNum;

    /* Дата налогового документа */
    private java.lang.String taxDt;

    /* Тип налогового платежа */
    private java.lang.String taxType;

    public TaxColl_Type() {
    }

    public TaxColl_Type(
           java.lang.String SRCstatus,
           java.lang.String taxRegCodeTo,
           java.lang.String budgetCode,
           java.lang.String OKATO,
           java.lang.String taxBase,
           java.lang.String taxPeriod,
           java.lang.String taxNum,
           java.lang.String taxDt,
           java.lang.String taxType) {
           this.SRCstatus = SRCstatus;
           this.taxRegCodeTo = taxRegCodeTo;
           this.budgetCode = budgetCode;
           this.OKATO = OKATO;
           this.taxBase = taxBase;
           this.taxPeriod = taxPeriod;
           this.taxNum = taxNum;
           this.taxDt = taxDt;
           this.taxType = taxType;
    }


    /**
     * Gets the SRCstatus value for this TaxColl_Type.
     * 
     * @return SRCstatus   * Статус составителя документа
     */
    public java.lang.String getSRCstatus() {
        return SRCstatus;
    }


    /**
     * Sets the SRCstatus value for this TaxColl_Type.
     * 
     * @param SRCstatus   * Статус составителя документа
     */
    public void setSRCstatus(java.lang.String SRCstatus) {
        this.SRCstatus = SRCstatus;
    }


    /**
     * Gets the taxRegCodeTo value for this TaxColl_Type.
     * 
     * @return taxRegCodeTo   * КПП получателя
     */
    public java.lang.String getTaxRegCodeTo() {
        return taxRegCodeTo;
    }


    /**
     * Sets the taxRegCodeTo value for this TaxColl_Type.
     * 
     * @param taxRegCodeTo   * КПП получателя
     */
    public void setTaxRegCodeTo(java.lang.String taxRegCodeTo) {
        this.taxRegCodeTo = taxRegCodeTo;
    }


    /**
     * Gets the budgetCode value for this TaxColl_Type.
     * 
     * @return budgetCode   * Код бюджетной классификации
     */
    public java.lang.String getBudgetCode() {
        return budgetCode;
    }


    /**
     * Sets the budgetCode value for this TaxColl_Type.
     * 
     * @param budgetCode   * Код бюджетной классификации
     */
    public void setBudgetCode(java.lang.String budgetCode) {
        this.budgetCode = budgetCode;
    }


    /**
     * Gets the OKATO value for this TaxColl_Type.
     * 
     * @return OKATO   * Код ОКАТО
     */
    public java.lang.String getOKATO() {
        return OKATO;
    }


    /**
     * Sets the OKATO value for this TaxColl_Type.
     * 
     * @param OKATO   * Код ОКАТО
     */
    public void setOKATO(java.lang.String OKATO) {
        this.OKATO = OKATO;
    }


    /**
     * Gets the taxBase value for this TaxColl_Type.
     * 
     * @return taxBase   * Основание налогового платежа
     */
    public java.lang.String getTaxBase() {
        return taxBase;
    }


    /**
     * Sets the taxBase value for this TaxColl_Type.
     * 
     * @param taxBase   * Основание налогового платежа
     */
    public void setTaxBase(java.lang.String taxBase) {
        this.taxBase = taxBase;
    }


    /**
     * Gets the taxPeriod value for this TaxColl_Type.
     * 
     * @return taxPeriod   * Налоговый период
     */
    public java.lang.String getTaxPeriod() {
        return taxPeriod;
    }


    /**
     * Sets the taxPeriod value for this TaxColl_Type.
     * 
     * @param taxPeriod   * Налоговый период
     */
    public void setTaxPeriod(java.lang.String taxPeriod) {
        this.taxPeriod = taxPeriod;
    }


    /**
     * Gets the taxNum value for this TaxColl_Type.
     * 
     * @return taxNum   * Номер налогового документа
     */
    public java.lang.String getTaxNum() {
        return taxNum;
    }


    /**
     * Sets the taxNum value for this TaxColl_Type.
     * 
     * @param taxNum   * Номер налогового документа
     */
    public void setTaxNum(java.lang.String taxNum) {
        this.taxNum = taxNum;
    }


    /**
     * Gets the taxDt value for this TaxColl_Type.
     * 
     * @return taxDt   * Дата налогового документа
     */
    public java.lang.String getTaxDt() {
        return taxDt;
    }


    /**
     * Sets the taxDt value for this TaxColl_Type.
     * 
     * @param taxDt   * Дата налогового документа
     */
    public void setTaxDt(java.lang.String taxDt) {
        this.taxDt = taxDt;
    }


    /**
     * Gets the taxType value for this TaxColl_Type.
     * 
     * @return taxType   * Тип налогового платежа
     */
    public java.lang.String getTaxType() {
        return taxType;
    }


    /**
     * Sets the taxType value for this TaxColl_Type.
     * 
     * @param taxType   * Тип налогового платежа
     */
    public void setTaxType(java.lang.String taxType) {
        this.taxType = taxType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TaxColl_Type)) return false;
        TaxColl_Type other = (TaxColl_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SRCstatus==null && other.getSRCstatus()==null) || 
             (this.SRCstatus!=null &&
              this.SRCstatus.equals(other.getSRCstatus()))) &&
            ((this.taxRegCodeTo==null && other.getTaxRegCodeTo()==null) || 
             (this.taxRegCodeTo!=null &&
              this.taxRegCodeTo.equals(other.getTaxRegCodeTo()))) &&
            ((this.budgetCode==null && other.getBudgetCode()==null) || 
             (this.budgetCode!=null &&
              this.budgetCode.equals(other.getBudgetCode()))) &&
            ((this.OKATO==null && other.getOKATO()==null) || 
             (this.OKATO!=null &&
              this.OKATO.equals(other.getOKATO()))) &&
            ((this.taxBase==null && other.getTaxBase()==null) || 
             (this.taxBase!=null &&
              this.taxBase.equals(other.getTaxBase()))) &&
            ((this.taxPeriod==null && other.getTaxPeriod()==null) || 
             (this.taxPeriod!=null &&
              this.taxPeriod.equals(other.getTaxPeriod()))) &&
            ((this.taxNum==null && other.getTaxNum()==null) || 
             (this.taxNum!=null &&
              this.taxNum.equals(other.getTaxNum()))) &&
            ((this.taxDt==null && other.getTaxDt()==null) || 
             (this.taxDt!=null &&
              this.taxDt.equals(other.getTaxDt()))) &&
            ((this.taxType==null && other.getTaxType()==null) || 
             (this.taxType!=null &&
              this.taxType.equals(other.getTaxType())));
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
        if (getSRCstatus() != null) {
            _hashCode += getSRCstatus().hashCode();
        }
        if (getTaxRegCodeTo() != null) {
            _hashCode += getTaxRegCodeTo().hashCode();
        }
        if (getBudgetCode() != null) {
            _hashCode += getBudgetCode().hashCode();
        }
        if (getOKATO() != null) {
            _hashCode += getOKATO().hashCode();
        }
        if (getTaxBase() != null) {
            _hashCode += getTaxBase().hashCode();
        }
        if (getTaxPeriod() != null) {
            _hashCode += getTaxPeriod().hashCode();
        }
        if (getTaxNum() != null) {
            _hashCode += getTaxNum().hashCode();
        }
        if (getTaxDt() != null) {
            _hashCode += getTaxDt().hashCode();
        }
        if (getTaxType() != null) {
            _hashCode += getTaxType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TaxColl_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxColl_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SRCstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SRCstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxRegCodeTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxRegCodeTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("budgetCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BudgetCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OKATO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OKATO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxBase");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxBase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxType"));
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
