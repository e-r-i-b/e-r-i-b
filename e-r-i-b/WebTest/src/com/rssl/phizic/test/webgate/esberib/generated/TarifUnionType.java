/**
 * TarifUnionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация   по  Тарифному    модулю
 */
public class TarifUnionType  implements java.io.Serializable {
    /* Тип тарифа платы за обслуживание: 1- индивидуальный тариф,
     * 2 – тариф банка */
    private java.lang.Long tariffType;

    /* Сумма тарифа за первый год обслуживания в валюте счета (в целых
     * рублях/долларах/евро) */
    private java.lang.Long feeByFirstYear;

    /* Сумма тарифа за каждый год со второго года обслуживания в валюте
     * счета (в целых рублях/долларах/евро) */
    private java.lang.Long feeByOtherYears;

    /* Код  тарифа   за  обслуживание */
    private java.lang.String codePlanTariff;

    /* Классификатор тарифного плана */
    private com.rssl.phizic.test.webgate.esberib.generated.TariffClassifier_Type[] tariffClassifier;

    public TarifUnionType() {
    }

    public TarifUnionType(
           java.lang.Long tariffType,
           java.lang.Long feeByFirstYear,
           java.lang.Long feeByOtherYears,
           java.lang.String codePlanTariff,
           com.rssl.phizic.test.webgate.esberib.generated.TariffClassifier_Type[] tariffClassifier) {
           this.tariffType = tariffType;
           this.feeByFirstYear = feeByFirstYear;
           this.feeByOtherYears = feeByOtherYears;
           this.codePlanTariff = codePlanTariff;
           this.tariffClassifier = tariffClassifier;
    }


    /**
     * Gets the tariffType value for this TarifUnionType.
     * 
     * @return tariffType   * Тип тарифа платы за обслуживание: 1- индивидуальный тариф,
     * 2 – тариф банка
     */
    public java.lang.Long getTariffType() {
        return tariffType;
    }


    /**
     * Sets the tariffType value for this TarifUnionType.
     * 
     * @param tariffType   * Тип тарифа платы за обслуживание: 1- индивидуальный тариф,
     * 2 – тариф банка
     */
    public void setTariffType(java.lang.Long tariffType) {
        this.tariffType = tariffType;
    }


    /**
     * Gets the feeByFirstYear value for this TarifUnionType.
     * 
     * @return feeByFirstYear   * Сумма тарифа за первый год обслуживания в валюте счета (в целых
     * рублях/долларах/евро)
     */
    public java.lang.Long getFeeByFirstYear() {
        return feeByFirstYear;
    }


    /**
     * Sets the feeByFirstYear value for this TarifUnionType.
     * 
     * @param feeByFirstYear   * Сумма тарифа за первый год обслуживания в валюте счета (в целых
     * рублях/долларах/евро)
     */
    public void setFeeByFirstYear(java.lang.Long feeByFirstYear) {
        this.feeByFirstYear = feeByFirstYear;
    }


    /**
     * Gets the feeByOtherYears value for this TarifUnionType.
     * 
     * @return feeByOtherYears   * Сумма тарифа за каждый год со второго года обслуживания в валюте
     * счета (в целых рублях/долларах/евро)
     */
    public java.lang.Long getFeeByOtherYears() {
        return feeByOtherYears;
    }


    /**
     * Sets the feeByOtherYears value for this TarifUnionType.
     * 
     * @param feeByOtherYears   * Сумма тарифа за каждый год со второго года обслуживания в валюте
     * счета (в целых рублях/долларах/евро)
     */
    public void setFeeByOtherYears(java.lang.Long feeByOtherYears) {
        this.feeByOtherYears = feeByOtherYears;
    }


    /**
     * Gets the codePlanTariff value for this TarifUnionType.
     * 
     * @return codePlanTariff   * Код  тарифа   за  обслуживание
     */
    public java.lang.String getCodePlanTariff() {
        return codePlanTariff;
    }


    /**
     * Sets the codePlanTariff value for this TarifUnionType.
     * 
     * @param codePlanTariff   * Код  тарифа   за  обслуживание
     */
    public void setCodePlanTariff(java.lang.String codePlanTariff) {
        this.codePlanTariff = codePlanTariff;
    }


    /**
     * Gets the tariffClassifier value for this TarifUnionType.
     * 
     * @return tariffClassifier   * Классификатор тарифного плана
     */
    public com.rssl.phizic.test.webgate.esberib.generated.TariffClassifier_Type[] getTariffClassifier() {
        return tariffClassifier;
    }


    /**
     * Sets the tariffClassifier value for this TarifUnionType.
     * 
     * @param tariffClassifier   * Классификатор тарифного плана
     */
    public void setTariffClassifier(com.rssl.phizic.test.webgate.esberib.generated.TariffClassifier_Type[] tariffClassifier) {
        this.tariffClassifier = tariffClassifier;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.TariffClassifier_Type getTariffClassifier(int i) {
        return this.tariffClassifier[i];
    }

    public void setTariffClassifier(int i, com.rssl.phizic.test.webgate.esberib.generated.TariffClassifier_Type _value) {
        this.tariffClassifier[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TarifUnionType)) return false;
        TarifUnionType other = (TarifUnionType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tariffType==null && other.getTariffType()==null) || 
             (this.tariffType!=null &&
              this.tariffType.equals(other.getTariffType()))) &&
            ((this.feeByFirstYear==null && other.getFeeByFirstYear()==null) || 
             (this.feeByFirstYear!=null &&
              this.feeByFirstYear.equals(other.getFeeByFirstYear()))) &&
            ((this.feeByOtherYears==null && other.getFeeByOtherYears()==null) || 
             (this.feeByOtherYears!=null &&
              this.feeByOtherYears.equals(other.getFeeByOtherYears()))) &&
            ((this.codePlanTariff==null && other.getCodePlanTariff()==null) || 
             (this.codePlanTariff!=null &&
              this.codePlanTariff.equals(other.getCodePlanTariff()))) &&
            ((this.tariffClassifier==null && other.getTariffClassifier()==null) || 
             (this.tariffClassifier!=null &&
              java.util.Arrays.equals(this.tariffClassifier, other.getTariffClassifier())));
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
        if (getTariffType() != null) {
            _hashCode += getTariffType().hashCode();
        }
        if (getFeeByFirstYear() != null) {
            _hashCode += getFeeByFirstYear().hashCode();
        }
        if (getFeeByOtherYears() != null) {
            _hashCode += getFeeByOtherYears().hashCode();
        }
        if (getCodePlanTariff() != null) {
            _hashCode += getCodePlanTariff().hashCode();
        }
        if (getTariffClassifier() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTariffClassifier());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTariffClassifier(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TarifUnionType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifUnionType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariffType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TariffType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feeByFirstYear");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FeeByFirstYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feeByOtherYears");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FeeByOtherYears"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codePlanTariff");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CodePlanTariff"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariffClassifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TariffClassifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TariffClassifier_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
