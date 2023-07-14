/**
 * InsuranceApp_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о страховом/НПФ продукте
 */
public class InsuranceApp_Type  implements java.io.Serializable {
    /* ID (референс) страховки, по которой запрашиваются данные */
    private java.lang.String reference;

    /* Наименование бизнес-процесса, в рамках которого оформлена страховка */
    private java.lang.String businessProcess;

    /* Наименование типа продукта, в рамках которого оформлена страховка */
    private java.lang.String productType;

    /* Статус страховки для отображения. Для страховых продуктов (Действует,
     * Срок действия истекает, Срок действия истек, Аннулирован). Для НПФ
     * продуктов (Договор заключен, Действующий клиент НПФ Сбербанка, Договор
     * расторгнут). */
    private java.lang.String status;

    /* Страховая компания для отображения */
    private java.lang.String company;

    /* Страховая программа для отображения */
    private java.lang.String program;

    /* Дата начала действия страховки */
    private java.lang.String startDate;

    /* Дата окончания действия страховки */
    private java.lang.String endDate;

    /* Номер СНИЛС для пенсионного страхования */
    private java.lang.String SNILS;

    /* Страховая сумма */
    private java.math.BigDecimal amount;

    /* Валюта страховай суммы */
    private java.lang.String amountCur;

    /* Описание реквизитов договора страхования: Серия, номер и дата
     * выдачи полиса */
    private com.rssl.phizic.test.webgate.esberib.generated.PolicyDetails_Type policyDetails;

    /* Описание страховых рисков */
    private java.lang.String risks;

    /* Дополнительная информация (№ и дата кредитного договора (при
     * условии оформления страховки в рамках кредитной сделки) */
    private java.lang.String additionalInfo;

    public InsuranceApp_Type() {
    }

    public InsuranceApp_Type(
           java.lang.String reference,
           java.lang.String businessProcess,
           java.lang.String productType,
           java.lang.String status,
           java.lang.String company,
           java.lang.String program,
           java.lang.String startDate,
           java.lang.String endDate,
           java.lang.String SNILS,
           java.math.BigDecimal amount,
           java.lang.String amountCur,
           com.rssl.phizic.test.webgate.esberib.generated.PolicyDetails_Type policyDetails,
           java.lang.String risks,
           java.lang.String additionalInfo) {
           this.reference = reference;
           this.businessProcess = businessProcess;
           this.productType = productType;
           this.status = status;
           this.company = company;
           this.program = program;
           this.startDate = startDate;
           this.endDate = endDate;
           this.SNILS = SNILS;
           this.amount = amount;
           this.amountCur = amountCur;
           this.policyDetails = policyDetails;
           this.risks = risks;
           this.additionalInfo = additionalInfo;
    }


    /**
     * Gets the reference value for this InsuranceApp_Type.
     * 
     * @return reference   * ID (референс) страховки, по которой запрашиваются данные
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this InsuranceApp_Type.
     * 
     * @param reference   * ID (референс) страховки, по которой запрашиваются данные
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the businessProcess value for this InsuranceApp_Type.
     * 
     * @return businessProcess   * Наименование бизнес-процесса, в рамках которого оформлена страховка
     */
    public java.lang.String getBusinessProcess() {
        return businessProcess;
    }


    /**
     * Sets the businessProcess value for this InsuranceApp_Type.
     * 
     * @param businessProcess   * Наименование бизнес-процесса, в рамках которого оформлена страховка
     */
    public void setBusinessProcess(java.lang.String businessProcess) {
        this.businessProcess = businessProcess;
    }


    /**
     * Gets the productType value for this InsuranceApp_Type.
     * 
     * @return productType   * Наименование типа продукта, в рамках которого оформлена страховка
     */
    public java.lang.String getProductType() {
        return productType;
    }


    /**
     * Sets the productType value for this InsuranceApp_Type.
     * 
     * @param productType   * Наименование типа продукта, в рамках которого оформлена страховка
     */
    public void setProductType(java.lang.String productType) {
        this.productType = productType;
    }


    /**
     * Gets the status value for this InsuranceApp_Type.
     * 
     * @return status   * Статус страховки для отображения. Для страховых продуктов (Действует,
     * Срок действия истекает, Срок действия истек, Аннулирован). Для НПФ
     * продуктов (Договор заключен, Действующий клиент НПФ Сбербанка, Договор
     * расторгнут).
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this InsuranceApp_Type.
     * 
     * @param status   * Статус страховки для отображения. Для страховых продуктов (Действует,
     * Срок действия истекает, Срок действия истек, Аннулирован). Для НПФ
     * продуктов (Договор заключен, Действующий клиент НПФ Сбербанка, Договор
     * расторгнут).
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the company value for this InsuranceApp_Type.
     * 
     * @return company   * Страховая компания для отображения
     */
    public java.lang.String getCompany() {
        return company;
    }


    /**
     * Sets the company value for this InsuranceApp_Type.
     * 
     * @param company   * Страховая компания для отображения
     */
    public void setCompany(java.lang.String company) {
        this.company = company;
    }


    /**
     * Gets the program value for this InsuranceApp_Type.
     * 
     * @return program   * Страховая программа для отображения
     */
    public java.lang.String getProgram() {
        return program;
    }


    /**
     * Sets the program value for this InsuranceApp_Type.
     * 
     * @param program   * Страховая программа для отображения
     */
    public void setProgram(java.lang.String program) {
        this.program = program;
    }


    /**
     * Gets the startDate value for this InsuranceApp_Type.
     * 
     * @return startDate   * Дата начала действия страховки
     */
    public java.lang.String getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this InsuranceApp_Type.
     * 
     * @param startDate   * Дата начала действия страховки
     */
    public void setStartDate(java.lang.String startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this InsuranceApp_Type.
     * 
     * @return endDate   * Дата окончания действия страховки
     */
    public java.lang.String getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this InsuranceApp_Type.
     * 
     * @param endDate   * Дата окончания действия страховки
     */
    public void setEndDate(java.lang.String endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the SNILS value for this InsuranceApp_Type.
     * 
     * @return SNILS   * Номер СНИЛС для пенсионного страхования
     */
    public java.lang.String getSNILS() {
        return SNILS;
    }


    /**
     * Sets the SNILS value for this InsuranceApp_Type.
     * 
     * @param SNILS   * Номер СНИЛС для пенсионного страхования
     */
    public void setSNILS(java.lang.String SNILS) {
        this.SNILS = SNILS;
    }


    /**
     * Gets the amount value for this InsuranceApp_Type.
     * 
     * @return amount   * Страховая сумма
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this InsuranceApp_Type.
     * 
     * @param amount   * Страховая сумма
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the amountCur value for this InsuranceApp_Type.
     * 
     * @return amountCur   * Валюта страховай суммы
     */
    public java.lang.String getAmountCur() {
        return amountCur;
    }


    /**
     * Sets the amountCur value for this InsuranceApp_Type.
     * 
     * @param amountCur   * Валюта страховай суммы
     */
    public void setAmountCur(java.lang.String amountCur) {
        this.amountCur = amountCur;
    }


    /**
     * Gets the policyDetails value for this InsuranceApp_Type.
     * 
     * @return policyDetails   * Описание реквизитов договора страхования: Серия, номер и дата
     * выдачи полиса
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PolicyDetails_Type getPolicyDetails() {
        return policyDetails;
    }


    /**
     * Sets the policyDetails value for this InsuranceApp_Type.
     * 
     * @param policyDetails   * Описание реквизитов договора страхования: Серия, номер и дата
     * выдачи полиса
     */
    public void setPolicyDetails(com.rssl.phizic.test.webgate.esberib.generated.PolicyDetails_Type policyDetails) {
        this.policyDetails = policyDetails;
    }


    /**
     * Gets the risks value for this InsuranceApp_Type.
     * 
     * @return risks   * Описание страховых рисков
     */
    public java.lang.String getRisks() {
        return risks;
    }


    /**
     * Sets the risks value for this InsuranceApp_Type.
     * 
     * @param risks   * Описание страховых рисков
     */
    public void setRisks(java.lang.String risks) {
        this.risks = risks;
    }


    /**
     * Gets the additionalInfo value for this InsuranceApp_Type.
     * 
     * @return additionalInfo   * Дополнительная информация (№ и дата кредитного договора (при
     * условии оформления страховки в рамках кредитной сделки)
     */
    public java.lang.String getAdditionalInfo() {
        return additionalInfo;
    }


    /**
     * Sets the additionalInfo value for this InsuranceApp_Type.
     * 
     * @param additionalInfo   * Дополнительная информация (№ и дата кредитного договора (при
     * условии оформления страховки в рамках кредитной сделки)
     */
    public void setAdditionalInfo(java.lang.String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsuranceApp_Type)) return false;
        InsuranceApp_Type other = (InsuranceApp_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.businessProcess==null && other.getBusinessProcess()==null) || 
             (this.businessProcess!=null &&
              this.businessProcess.equals(other.getBusinessProcess()))) &&
            ((this.productType==null && other.getProductType()==null) || 
             (this.productType!=null &&
              this.productType.equals(other.getProductType()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.company==null && other.getCompany()==null) || 
             (this.company!=null &&
              this.company.equals(other.getCompany()))) &&
            ((this.program==null && other.getProgram()==null) || 
             (this.program!=null &&
              this.program.equals(other.getProgram()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.SNILS==null && other.getSNILS()==null) || 
             (this.SNILS!=null &&
              this.SNILS.equals(other.getSNILS()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.amountCur==null && other.getAmountCur()==null) || 
             (this.amountCur!=null &&
              this.amountCur.equals(other.getAmountCur()))) &&
            ((this.policyDetails==null && other.getPolicyDetails()==null) || 
             (this.policyDetails!=null &&
              this.policyDetails.equals(other.getPolicyDetails()))) &&
            ((this.risks==null && other.getRisks()==null) || 
             (this.risks!=null &&
              this.risks.equals(other.getRisks()))) &&
            ((this.additionalInfo==null && other.getAdditionalInfo()==null) || 
             (this.additionalInfo!=null &&
              this.additionalInfo.equals(other.getAdditionalInfo())));
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
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getBusinessProcess() != null) {
            _hashCode += getBusinessProcess().hashCode();
        }
        if (getProductType() != null) {
            _hashCode += getProductType().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getCompany() != null) {
            _hashCode += getCompany().hashCode();
        }
        if (getProgram() != null) {
            _hashCode += getProgram().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getSNILS() != null) {
            _hashCode += getSNILS().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getAmountCur() != null) {
            _hashCode += getAmountCur().hashCode();
        }
        if (getPolicyDetails() != null) {
            _hashCode += getPolicyDetails().hashCode();
        }
        if (getRisks() != null) {
            _hashCode += getRisks().hashCode();
        }
        if (getAdditionalInfo() != null) {
            _hashCode += getAdditionalInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsuranceApp_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsuranceApp_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessProcess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BusinessProcess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("company");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Company"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("program");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Program"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SNILS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SNILS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AmountCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PolicyDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PolicyDetails_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("risks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Risks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AdditionalInfo"));
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
