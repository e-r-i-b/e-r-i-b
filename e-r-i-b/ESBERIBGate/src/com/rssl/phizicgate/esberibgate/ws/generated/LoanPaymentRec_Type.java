/**
 * LoanPaymentRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о платеже по кредиту
 */
public class LoanPaymentRec_Type  implements java.io.Serializable {
    /* Порядковый номер платежа в графике */
    private long loanPaymentNumber;

    /* Статус: 1 – оплачен, 2 – текущая оплата, 3 – к оплате */
    private java.lang.String loanPaymentStatus;

    /* Дата погашения */
    private java.lang.String loanPaymentDate;

    /* Остаток основного долга после внесения платежа */
    private java.math.BigDecimal remainDebt;

    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate;

    public LoanPaymentRec_Type() {
    }

    public LoanPaymentRec_Type(
           long loanPaymentNumber,
           java.lang.String loanPaymentStatus,
           java.lang.String loanPaymentDate,
           java.math.BigDecimal remainDebt,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate) {
           this.loanPaymentNumber = loanPaymentNumber;
           this.loanPaymentStatus = loanPaymentStatus;
           this.loanPaymentDate = loanPaymentDate;
           this.remainDebt = remainDebt;
           this.acctBalOnDate = acctBalOnDate;
    }


    /**
     * Gets the loanPaymentNumber value for this LoanPaymentRec_Type.
     * 
     * @return loanPaymentNumber   * Порядковый номер платежа в графике
     */
    public long getLoanPaymentNumber() {
        return loanPaymentNumber;
    }


    /**
     * Sets the loanPaymentNumber value for this LoanPaymentRec_Type.
     * 
     * @param loanPaymentNumber   * Порядковый номер платежа в графике
     */
    public void setLoanPaymentNumber(long loanPaymentNumber) {
        this.loanPaymentNumber = loanPaymentNumber;
    }


    /**
     * Gets the loanPaymentStatus value for this LoanPaymentRec_Type.
     * 
     * @return loanPaymentStatus   * Статус: 1 – оплачен, 2 – текущая оплата, 3 – к оплате
     */
    public java.lang.String getLoanPaymentStatus() {
        return loanPaymentStatus;
    }


    /**
     * Sets the loanPaymentStatus value for this LoanPaymentRec_Type.
     * 
     * @param loanPaymentStatus   * Статус: 1 – оплачен, 2 – текущая оплата, 3 – к оплате
     */
    public void setLoanPaymentStatus(java.lang.String loanPaymentStatus) {
        this.loanPaymentStatus = loanPaymentStatus;
    }


    /**
     * Gets the loanPaymentDate value for this LoanPaymentRec_Type.
     * 
     * @return loanPaymentDate   * Дата погашения
     */
    public java.lang.String getLoanPaymentDate() {
        return loanPaymentDate;
    }


    /**
     * Sets the loanPaymentDate value for this LoanPaymentRec_Type.
     * 
     * @param loanPaymentDate   * Дата погашения
     */
    public void setLoanPaymentDate(java.lang.String loanPaymentDate) {
        this.loanPaymentDate = loanPaymentDate;
    }


    /**
     * Gets the remainDebt value for this LoanPaymentRec_Type.
     * 
     * @return remainDebt   * Остаток основного долга после внесения платежа
     */
    public java.math.BigDecimal getRemainDebt() {
        return remainDebt;
    }


    /**
     * Sets the remainDebt value for this LoanPaymentRec_Type.
     * 
     * @param remainDebt   * Остаток основного долга после внесения платежа
     */
    public void setRemainDebt(java.math.BigDecimal remainDebt) {
        this.remainDebt = remainDebt;
    }


    /**
     * Gets the acctBalOnDate value for this LoanPaymentRec_Type.
     * 
     * @return acctBalOnDate
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] getAcctBalOnDate() {
        return acctBalOnDate;
    }


    /**
     * Sets the acctBalOnDate value for this LoanPaymentRec_Type.
     * 
     * @param acctBalOnDate
     */
    public void setAcctBalOnDate(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate) {
        this.acctBalOnDate = acctBalOnDate;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getAcctBalOnDate(int i) {
        return this.acctBalOnDate[i];
    }

    public void setAcctBalOnDate(int i, com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type _value) {
        this.acctBalOnDate[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoanPaymentRec_Type)) return false;
        LoanPaymentRec_Type other = (LoanPaymentRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.loanPaymentNumber == other.getLoanPaymentNumber() &&
            ((this.loanPaymentStatus==null && other.getLoanPaymentStatus()==null) || 
             (this.loanPaymentStatus!=null &&
              this.loanPaymentStatus.equals(other.getLoanPaymentStatus()))) &&
            ((this.loanPaymentDate==null && other.getLoanPaymentDate()==null) || 
             (this.loanPaymentDate!=null &&
              this.loanPaymentDate.equals(other.getLoanPaymentDate()))) &&
            ((this.remainDebt==null && other.getRemainDebt()==null) || 
             (this.remainDebt!=null &&
              this.remainDebt.equals(other.getRemainDebt()))) &&
            ((this.acctBalOnDate==null && other.getAcctBalOnDate()==null) || 
             (this.acctBalOnDate!=null &&
              java.util.Arrays.equals(this.acctBalOnDate, other.getAcctBalOnDate())));
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
        _hashCode += new Long(getLoanPaymentNumber()).hashCode();
        if (getLoanPaymentStatus() != null) {
            _hashCode += getLoanPaymentStatus().hashCode();
        }
        if (getLoanPaymentDate() != null) {
            _hashCode += getLoanPaymentDate().hashCode();
        }
        if (getRemainDebt() != null) {
            _hashCode += getRemainDebt().hashCode();
        }
        if (getAcctBalOnDate() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcctBalOnDate());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcctBalOnDate(), i);
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
        new org.apache.axis.description.TypeDesc(LoanPaymentRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanPaymentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanPaymentStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanPaymentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remainDebt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RemainDebt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBalOnDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBalOnDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
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
