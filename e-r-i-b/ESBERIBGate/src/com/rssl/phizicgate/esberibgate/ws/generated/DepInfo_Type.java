/**
 * DepInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип информация о депозитном договоре
 */
public class DepInfo_Type  implements java.io.Serializable {
    /* Дата окончания срока вклада */
    private java.lang.String matDt;

    /* Срок вклада в днях */
    private java.lang.Long term;

    private java.math.BigDecimal irreducibleAmt;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type custRec;

    /* Номер вкладного счета */
    private java.lang.String acctId;

    private java.lang.String acctCur;

    /* Вид вклада */
    private java.lang.Long acctCode;

    /* Подвид вклада */
    private java.lang.Long acctSubCode;

    /* Признак льготной ставки */
    private java.lang.String bonusRate;

    /* Кол-во дней до окончания хранения закрываемого вклада. Значение
     * обязательно заполняется для операций с закрытием вкладов */
    private java.lang.Long daysToEndOfSaving;

    /* Дата окончания хранения закрываемого вклада.  Значение обязательно
     * заполняется для операций с закрытием вкладов */
    private java.lang.String dateToEndOfSaving;

    /* Процентная ставка при досрочном погашении (IntRateType = Fixed).
     * Значение обязательно заполняется для операций с закрытием вкладов */
    private com.rssl.phizicgate.esberibgate.ws.generated.IntRate_Type earlyTermRate;

    /* Признак наличия длительного поручения.  Значение обязательно
     * заполняется для операций с закрытием вкладов */
    private java.lang.Boolean haveForm190;

    public DepInfo_Type() {
    }

    public DepInfo_Type(
           java.lang.String matDt,
           java.lang.Long term,
           java.math.BigDecimal irreducibleAmt,
           com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type custRec,
           java.lang.String acctId,
           java.lang.String acctCur,
           java.lang.Long acctCode,
           java.lang.Long acctSubCode,
           java.lang.String bonusRate,
           java.lang.Long daysToEndOfSaving,
           java.lang.String dateToEndOfSaving,
           com.rssl.phizicgate.esberibgate.ws.generated.IntRate_Type earlyTermRate,
           java.lang.Boolean haveForm190) {
           this.matDt = matDt;
           this.term = term;
           this.irreducibleAmt = irreducibleAmt;
           this.custRec = custRec;
           this.acctId = acctId;
           this.acctCur = acctCur;
           this.acctCode = acctCode;
           this.acctSubCode = acctSubCode;
           this.bonusRate = bonusRate;
           this.daysToEndOfSaving = daysToEndOfSaving;
           this.dateToEndOfSaving = dateToEndOfSaving;
           this.earlyTermRate = earlyTermRate;
           this.haveForm190 = haveForm190;
    }


    /**
     * Gets the matDt value for this DepInfo_Type.
     * 
     * @return matDt   * Дата окончания срока вклада
     */
    public java.lang.String getMatDt() {
        return matDt;
    }


    /**
     * Sets the matDt value for this DepInfo_Type.
     * 
     * @param matDt   * Дата окончания срока вклада
     */
    public void setMatDt(java.lang.String matDt) {
        this.matDt = matDt;
    }


    /**
     * Gets the term value for this DepInfo_Type.
     * 
     * @return term   * Срок вклада в днях
     */
    public java.lang.Long getTerm() {
        return term;
    }


    /**
     * Sets the term value for this DepInfo_Type.
     * 
     * @param term   * Срок вклада в днях
     */
    public void setTerm(java.lang.Long term) {
        this.term = term;
    }


    /**
     * Gets the irreducibleAmt value for this DepInfo_Type.
     * 
     * @return irreducibleAmt
     */
    public java.math.BigDecimal getIrreducibleAmt() {
        return irreducibleAmt;
    }


    /**
     * Sets the irreducibleAmt value for this DepInfo_Type.
     * 
     * @param irreducibleAmt
     */
    public void setIrreducibleAmt(java.math.BigDecimal irreducibleAmt) {
        this.irreducibleAmt = irreducibleAmt;
    }


    /**
     * Gets the custRec value for this DepInfo_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this DepInfo_Type.
     * 
     * @param custRec
     */
    public void setCustRec(com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type custRec) {
        this.custRec = custRec;
    }


    /**
     * Gets the acctId value for this DepInfo_Type.
     * 
     * @return acctId   * Номер вкладного счета
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this DepInfo_Type.
     * 
     * @param acctId   * Номер вкладного счета
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the acctCur value for this DepInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this DepInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the acctCode value for this DepInfo_Type.
     * 
     * @return acctCode   * Вид вклада
     */
    public java.lang.Long getAcctCode() {
        return acctCode;
    }


    /**
     * Sets the acctCode value for this DepInfo_Type.
     * 
     * @param acctCode   * Вид вклада
     */
    public void setAcctCode(java.lang.Long acctCode) {
        this.acctCode = acctCode;
    }


    /**
     * Gets the acctSubCode value for this DepInfo_Type.
     * 
     * @return acctSubCode   * Подвид вклада
     */
    public java.lang.Long getAcctSubCode() {
        return acctSubCode;
    }


    /**
     * Sets the acctSubCode value for this DepInfo_Type.
     * 
     * @param acctSubCode   * Подвид вклада
     */
    public void setAcctSubCode(java.lang.Long acctSubCode) {
        this.acctSubCode = acctSubCode;
    }


    /**
     * Gets the bonusRate value for this DepInfo_Type.
     * 
     * @return bonusRate   * Признак льготной ставки
     */
    public java.lang.String getBonusRate() {
        return bonusRate;
    }


    /**
     * Sets the bonusRate value for this DepInfo_Type.
     * 
     * @param bonusRate   * Признак льготной ставки
     */
    public void setBonusRate(java.lang.String bonusRate) {
        this.bonusRate = bonusRate;
    }


    /**
     * Gets the daysToEndOfSaving value for this DepInfo_Type.
     * 
     * @return daysToEndOfSaving   * Кол-во дней до окончания хранения закрываемого вклада. Значение
     * обязательно заполняется для операций с закрытием вкладов
     */
    public java.lang.Long getDaysToEndOfSaving() {
        return daysToEndOfSaving;
    }


    /**
     * Sets the daysToEndOfSaving value for this DepInfo_Type.
     * 
     * @param daysToEndOfSaving   * Кол-во дней до окончания хранения закрываемого вклада. Значение
     * обязательно заполняется для операций с закрытием вкладов
     */
    public void setDaysToEndOfSaving(java.lang.Long daysToEndOfSaving) {
        this.daysToEndOfSaving = daysToEndOfSaving;
    }


    /**
     * Gets the dateToEndOfSaving value for this DepInfo_Type.
     * 
     * @return dateToEndOfSaving   * Дата окончания хранения закрываемого вклада.  Значение обязательно
     * заполняется для операций с закрытием вкладов
     */
    public java.lang.String getDateToEndOfSaving() {
        return dateToEndOfSaving;
    }


    /**
     * Sets the dateToEndOfSaving value for this DepInfo_Type.
     * 
     * @param dateToEndOfSaving   * Дата окончания хранения закрываемого вклада.  Значение обязательно
     * заполняется для операций с закрытием вкладов
     */
    public void setDateToEndOfSaving(java.lang.String dateToEndOfSaving) {
        this.dateToEndOfSaving = dateToEndOfSaving;
    }


    /**
     * Gets the earlyTermRate value for this DepInfo_Type.
     * 
     * @return earlyTermRate   * Процентная ставка при досрочном погашении (IntRateType = Fixed).
     * Значение обязательно заполняется для операций с закрытием вкладов
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.IntRate_Type getEarlyTermRate() {
        return earlyTermRate;
    }


    /**
     * Sets the earlyTermRate value for this DepInfo_Type.
     * 
     * @param earlyTermRate   * Процентная ставка при досрочном погашении (IntRateType = Fixed).
     * Значение обязательно заполняется для операций с закрытием вкладов
     */
    public void setEarlyTermRate(com.rssl.phizicgate.esberibgate.ws.generated.IntRate_Type earlyTermRate) {
        this.earlyTermRate = earlyTermRate;
    }


    /**
     * Gets the haveForm190 value for this DepInfo_Type.
     * 
     * @return haveForm190   * Признак наличия длительного поручения.  Значение обязательно
     * заполняется для операций с закрытием вкладов
     */
    public java.lang.Boolean getHaveForm190() {
        return haveForm190;
    }


    /**
     * Sets the haveForm190 value for this DepInfo_Type.
     * 
     * @param haveForm190   * Признак наличия длительного поручения.  Значение обязательно
     * заполняется для операций с закрытием вкладов
     */
    public void setHaveForm190(java.lang.Boolean haveForm190) {
        this.haveForm190 = haveForm190;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepInfo_Type)) return false;
        DepInfo_Type other = (DepInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.matDt==null && other.getMatDt()==null) || 
             (this.matDt!=null &&
              this.matDt.equals(other.getMatDt()))) &&
            ((this.term==null && other.getTerm()==null) || 
             (this.term!=null &&
              this.term.equals(other.getTerm()))) &&
            ((this.irreducibleAmt==null && other.getIrreducibleAmt()==null) || 
             (this.irreducibleAmt!=null &&
              this.irreducibleAmt.equals(other.getIrreducibleAmt()))) &&
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              this.custRec.equals(other.getCustRec()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.acctCode==null && other.getAcctCode()==null) || 
             (this.acctCode!=null &&
              this.acctCode.equals(other.getAcctCode()))) &&
            ((this.acctSubCode==null && other.getAcctSubCode()==null) || 
             (this.acctSubCode!=null &&
              this.acctSubCode.equals(other.getAcctSubCode()))) &&
            ((this.bonusRate==null && other.getBonusRate()==null) || 
             (this.bonusRate!=null &&
              this.bonusRate.equals(other.getBonusRate()))) &&
            ((this.daysToEndOfSaving==null && other.getDaysToEndOfSaving()==null) || 
             (this.daysToEndOfSaving!=null &&
              this.daysToEndOfSaving.equals(other.getDaysToEndOfSaving()))) &&
            ((this.dateToEndOfSaving==null && other.getDateToEndOfSaving()==null) || 
             (this.dateToEndOfSaving!=null &&
              this.dateToEndOfSaving.equals(other.getDateToEndOfSaving()))) &&
            ((this.earlyTermRate==null && other.getEarlyTermRate()==null) || 
             (this.earlyTermRate!=null &&
              this.earlyTermRate.equals(other.getEarlyTermRate()))) &&
            ((this.haveForm190==null && other.getHaveForm190()==null) || 
             (this.haveForm190!=null &&
              this.haveForm190.equals(other.getHaveForm190())));
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
        if (getMatDt() != null) {
            _hashCode += getMatDt().hashCode();
        }
        if (getTerm() != null) {
            _hashCode += getTerm().hashCode();
        }
        if (getIrreducibleAmt() != null) {
            _hashCode += getIrreducibleAmt().hashCode();
        }
        if (getCustRec() != null) {
            _hashCode += getCustRec().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getAcctCode() != null) {
            _hashCode += getAcctCode().hashCode();
        }
        if (getAcctSubCode() != null) {
            _hashCode += getAcctSubCode().hashCode();
        }
        if (getBonusRate() != null) {
            _hashCode += getBonusRate().hashCode();
        }
        if (getDaysToEndOfSaving() != null) {
            _hashCode += getDaysToEndOfSaving().hashCode();
        }
        if (getDateToEndOfSaving() != null) {
            _hashCode += getDateToEndOfSaving().hashCode();
        }
        if (getEarlyTermRate() != null) {
            _hashCode += getEarlyTermRate().hashCode();
        }
        if (getHaveForm190() != null) {
            _hashCode += getHaveForm190().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MatDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("term");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Term"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("irreducibleAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IrreducibleAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctIdType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctSubCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctSubCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("daysToEndOfSaving");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DaysToEndOfSaving"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateToEndOfSaving");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateToEndOfSaving"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("earlyTermRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EarlyTermRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IntRate_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("haveForm190");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "HaveForm190"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
