/**
 * AlternativeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.etsm.listener.generated;


/**
 * Альтернативные варианты кредита
 */
public class AlternativeType  implements java.io.Serializable {
    /* Уникальный идентификатор */
    private long id;

    /* Альтернативный срок в месяцах */
    private org.apache.axis.types.UnsignedInt altPeriodM;

    /* Альтернативный сумма кредита в рублях */
    private java.math.BigDecimal altAmount;

    /* Альтернативный ставка */
    private java.math.BigDecimal altInterestRate;

    /* Альтернативная полная стоимость кредита */
    private java.math.BigDecimal altFullLoanCost;

    /* Альтернативный аннуитентный платеж */
    private java.math.BigDecimal altAnnuitentyPayment;

    /* Альтернативный лимит по кредитной карте */
    private java.math.BigDecimal altCreditCardlimit;

    public AlternativeType() {
    }

    public AlternativeType(
           long id,
           org.apache.axis.types.UnsignedInt altPeriodM,
           java.math.BigDecimal altAmount,
           java.math.BigDecimal altInterestRate,
           java.math.BigDecimal altFullLoanCost,
           java.math.BigDecimal altAnnuitentyPayment,
           java.math.BigDecimal altCreditCardlimit) {
           this.id = id;
           this.altPeriodM = altPeriodM;
           this.altAmount = altAmount;
           this.altInterestRate = altInterestRate;
           this.altFullLoanCost = altFullLoanCost;
           this.altAnnuitentyPayment = altAnnuitentyPayment;
           this.altCreditCardlimit = altCreditCardlimit;
    }


    /**
     * Gets the id value for this AlternativeType.
     * 
     * @return id   * Уникальный идентификатор
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this AlternativeType.
     * 
     * @param id   * Уникальный идентификатор
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the altPeriodM value for this AlternativeType.
     * 
     * @return altPeriodM   * Альтернативный срок в месяцах
     */
    public org.apache.axis.types.UnsignedInt getAltPeriodM() {
        return altPeriodM;
    }


    /**
     * Sets the altPeriodM value for this AlternativeType.
     * 
     * @param altPeriodM   * Альтернативный срок в месяцах
     */
    public void setAltPeriodM(org.apache.axis.types.UnsignedInt altPeriodM) {
        this.altPeriodM = altPeriodM;
    }


    /**
     * Gets the altAmount value for this AlternativeType.
     * 
     * @return altAmount   * Альтернативный сумма кредита в рублях
     */
    public java.math.BigDecimal getAltAmount() {
        return altAmount;
    }


    /**
     * Sets the altAmount value for this AlternativeType.
     * 
     * @param altAmount   * Альтернативный сумма кредита в рублях
     */
    public void setAltAmount(java.math.BigDecimal altAmount) {
        this.altAmount = altAmount;
    }


    /**
     * Gets the altInterestRate value for this AlternativeType.
     * 
     * @return altInterestRate   * Альтернативный ставка
     */
    public java.math.BigDecimal getAltInterestRate() {
        return altInterestRate;
    }


    /**
     * Sets the altInterestRate value for this AlternativeType.
     * 
     * @param altInterestRate   * Альтернативный ставка
     */
    public void setAltInterestRate(java.math.BigDecimal altInterestRate) {
        this.altInterestRate = altInterestRate;
    }


    /**
     * Gets the altFullLoanCost value for this AlternativeType.
     * 
     * @return altFullLoanCost   * Альтернативная полная стоимость кредита
     */
    public java.math.BigDecimal getAltFullLoanCost() {
        return altFullLoanCost;
    }


    /**
     * Sets the altFullLoanCost value for this AlternativeType.
     * 
     * @param altFullLoanCost   * Альтернативная полная стоимость кредита
     */
    public void setAltFullLoanCost(java.math.BigDecimal altFullLoanCost) {
        this.altFullLoanCost = altFullLoanCost;
    }


    /**
     * Gets the altAnnuitentyPayment value for this AlternativeType.
     * 
     * @return altAnnuitentyPayment   * Альтернативный аннуитентный платеж
     */
    public java.math.BigDecimal getAltAnnuitentyPayment() {
        return altAnnuitentyPayment;
    }


    /**
     * Sets the altAnnuitentyPayment value for this AlternativeType.
     * 
     * @param altAnnuitentyPayment   * Альтернативный аннуитентный платеж
     */
    public void setAltAnnuitentyPayment(java.math.BigDecimal altAnnuitentyPayment) {
        this.altAnnuitentyPayment = altAnnuitentyPayment;
    }


    /**
     * Gets the altCreditCardlimit value for this AlternativeType.
     * 
     * @return altCreditCardlimit   * Альтернативный лимит по кредитной карте
     */
    public java.math.BigDecimal getAltCreditCardlimit() {
        return altCreditCardlimit;
    }


    /**
     * Sets the altCreditCardlimit value for this AlternativeType.
     * 
     * @param altCreditCardlimit   * Альтернативный лимит по кредитной карте
     */
    public void setAltCreditCardlimit(java.math.BigDecimal altCreditCardlimit) {
        this.altCreditCardlimit = altCreditCardlimit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AlternativeType)) return false;
        AlternativeType other = (AlternativeType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.id == other.getId() &&
            ((this.altPeriodM==null && other.getAltPeriodM()==null) || 
             (this.altPeriodM!=null &&
              this.altPeriodM.equals(other.getAltPeriodM()))) &&
            ((this.altAmount==null && other.getAltAmount()==null) || 
             (this.altAmount!=null &&
              this.altAmount.equals(other.getAltAmount()))) &&
            ((this.altInterestRate==null && other.getAltInterestRate()==null) || 
             (this.altInterestRate!=null &&
              this.altInterestRate.equals(other.getAltInterestRate()))) &&
            ((this.altFullLoanCost==null && other.getAltFullLoanCost()==null) || 
             (this.altFullLoanCost!=null &&
              this.altFullLoanCost.equals(other.getAltFullLoanCost()))) &&
            ((this.altAnnuitentyPayment==null && other.getAltAnnuitentyPayment()==null) || 
             (this.altAnnuitentyPayment!=null &&
              this.altAnnuitentyPayment.equals(other.getAltAnnuitentyPayment()))) &&
            ((this.altCreditCardlimit==null && other.getAltCreditCardlimit()==null) || 
             (this.altCreditCardlimit!=null &&
              this.altCreditCardlimit.equals(other.getAltCreditCardlimit())));
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
        _hashCode += new Long(getId()).hashCode();
        if (getAltPeriodM() != null) {
            _hashCode += getAltPeriodM().hashCode();
        }
        if (getAltAmount() != null) {
            _hashCode += getAltAmount().hashCode();
        }
        if (getAltInterestRate() != null) {
            _hashCode += getAltInterestRate().hashCode();
        }
        if (getAltFullLoanCost() != null) {
            _hashCode += getAltFullLoanCost().hashCode();
        }
        if (getAltAnnuitentyPayment() != null) {
            _hashCode += getAltAnnuitentyPayment().hashCode();
        }
        if (getAltCreditCardlimit() != null) {
            _hashCode += getAltCreditCardlimit().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AlternativeType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AlternativeType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altPeriodM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AltPeriodM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AltAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altInterestRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AltInterestRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altFullLoanCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AltFullLoanCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altAnnuitentyPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AltAnnuitentyPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altCreditCardlimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AltCreditCardlimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
