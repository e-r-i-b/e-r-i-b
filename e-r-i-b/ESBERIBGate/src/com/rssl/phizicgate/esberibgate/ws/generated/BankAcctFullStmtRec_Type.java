/**
 * BankAcctFullStmtRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись о выписке
 */
public class BankAcctFullStmtRec_Type  implements java.io.Serializable {
    /* Дата операции */
    private java.lang.String effDate;

    /* Номер операции */
    private java.lang.String number;

    /* Шифр операции */
    private java.lang.String code;

    /* Номер документа, на основании которого была выполнена операция,
     * с указанием обоснования. */
    private java.lang.String documentNumber;

    /* Номер корреспондирующего счета */
    private java.lang.String corAcc;

    private java.lang.String stmtSummType;

    /* Сумма операции в граммах */
    private java.math.BigDecimal amt;

    /* Сумма операции в физической форме */
    private java.math.BigDecimal amtPhiz;

    /* Остаток в граммах */
    private java.math.BigDecimal balance;

    /* Остаток в физической форме */
    private java.math.BigDecimal balancePhiz;

    public BankAcctFullStmtRec_Type() {
    }

    public BankAcctFullStmtRec_Type(
           java.lang.String effDate,
           java.lang.String number,
           java.lang.String code,
           java.lang.String documentNumber,
           java.lang.String corAcc,
           java.lang.String stmtSummType,
           java.math.BigDecimal amt,
           java.math.BigDecimal amtPhiz,
           java.math.BigDecimal balance,
           java.math.BigDecimal balancePhiz) {
           this.effDate = effDate;
           this.number = number;
           this.code = code;
           this.documentNumber = documentNumber;
           this.corAcc = corAcc;
           this.stmtSummType = stmtSummType;
           this.amt = amt;
           this.amtPhiz = amtPhiz;
           this.balance = balance;
           this.balancePhiz = balancePhiz;
    }


    /**
     * Gets the effDate value for this BankAcctFullStmtRec_Type.
     * 
     * @return effDate   * Дата операции
     */
    public java.lang.String getEffDate() {
        return effDate;
    }


    /**
     * Sets the effDate value for this BankAcctFullStmtRec_Type.
     * 
     * @param effDate   * Дата операции
     */
    public void setEffDate(java.lang.String effDate) {
        this.effDate = effDate;
    }


    /**
     * Gets the number value for this BankAcctFullStmtRec_Type.
     * 
     * @return number   * Номер операции
     */
    public java.lang.String getNumber() {
        return number;
    }


    /**
     * Sets the number value for this BankAcctFullStmtRec_Type.
     * 
     * @param number   * Номер операции
     */
    public void setNumber(java.lang.String number) {
        this.number = number;
    }


    /**
     * Gets the code value for this BankAcctFullStmtRec_Type.
     * 
     * @return code   * Шифр операции
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this BankAcctFullStmtRec_Type.
     * 
     * @param code   * Шифр операции
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the documentNumber value for this BankAcctFullStmtRec_Type.
     * 
     * @return documentNumber   * Номер документа, на основании которого была выполнена операция,
     * с указанием обоснования.
     */
    public java.lang.String getDocumentNumber() {
        return documentNumber;
    }


    /**
     * Sets the documentNumber value for this BankAcctFullStmtRec_Type.
     * 
     * @param documentNumber   * Номер документа, на основании которого была выполнена операция,
     * с указанием обоснования.
     */
    public void setDocumentNumber(java.lang.String documentNumber) {
        this.documentNumber = documentNumber;
    }


    /**
     * Gets the corAcc value for this BankAcctFullStmtRec_Type.
     * 
     * @return corAcc   * Номер корреспондирующего счета
     */
    public java.lang.String getCorAcc() {
        return corAcc;
    }


    /**
     * Sets the corAcc value for this BankAcctFullStmtRec_Type.
     * 
     * @param corAcc   * Номер корреспондирующего счета
     */
    public void setCorAcc(java.lang.String corAcc) {
        this.corAcc = corAcc;
    }


    /**
     * Gets the stmtSummType value for this BankAcctFullStmtRec_Type.
     * 
     * @return stmtSummType
     */
    public java.lang.String getStmtSummType() {
        return stmtSummType;
    }


    /**
     * Sets the stmtSummType value for this BankAcctFullStmtRec_Type.
     * 
     * @param stmtSummType
     */
    public void setStmtSummType(java.lang.String stmtSummType) {
        this.stmtSummType = stmtSummType;
    }


    /**
     * Gets the amt value for this BankAcctFullStmtRec_Type.
     * 
     * @return amt   * Сумма операции в граммах
     */
    public java.math.BigDecimal getAmt() {
        return amt;
    }


    /**
     * Sets the amt value for this BankAcctFullStmtRec_Type.
     * 
     * @param amt   * Сумма операции в граммах
     */
    public void setAmt(java.math.BigDecimal amt) {
        this.amt = amt;
    }


    /**
     * Gets the amtPhiz value for this BankAcctFullStmtRec_Type.
     * 
     * @return amtPhiz   * Сумма операции в физической форме
     */
    public java.math.BigDecimal getAmtPhiz() {
        return amtPhiz;
    }


    /**
     * Sets the amtPhiz value for this BankAcctFullStmtRec_Type.
     * 
     * @param amtPhiz   * Сумма операции в физической форме
     */
    public void setAmtPhiz(java.math.BigDecimal amtPhiz) {
        this.amtPhiz = amtPhiz;
    }


    /**
     * Gets the balance value for this BankAcctFullStmtRec_Type.
     * 
     * @return balance   * Остаток в граммах
     */
    public java.math.BigDecimal getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this BankAcctFullStmtRec_Type.
     * 
     * @param balance   * Остаток в граммах
     */
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * Gets the balancePhiz value for this BankAcctFullStmtRec_Type.
     * 
     * @return balancePhiz   * Остаток в физической форме
     */
    public java.math.BigDecimal getBalancePhiz() {
        return balancePhiz;
    }


    /**
     * Sets the balancePhiz value for this BankAcctFullStmtRec_Type.
     * 
     * @param balancePhiz   * Остаток в физической форме
     */
    public void setBalancePhiz(java.math.BigDecimal balancePhiz) {
        this.balancePhiz = balancePhiz;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctFullStmtRec_Type)) return false;
        BankAcctFullStmtRec_Type other = (BankAcctFullStmtRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.effDate==null && other.getEffDate()==null) || 
             (this.effDate!=null &&
              this.effDate.equals(other.getEffDate()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.documentNumber==null && other.getDocumentNumber()==null) || 
             (this.documentNumber!=null &&
              this.documentNumber.equals(other.getDocumentNumber()))) &&
            ((this.corAcc==null && other.getCorAcc()==null) || 
             (this.corAcc!=null &&
              this.corAcc.equals(other.getCorAcc()))) &&
            ((this.stmtSummType==null && other.getStmtSummType()==null) || 
             (this.stmtSummType!=null &&
              this.stmtSummType.equals(other.getStmtSummType()))) &&
            ((this.amt==null && other.getAmt()==null) || 
             (this.amt!=null &&
              this.amt.equals(other.getAmt()))) &&
            ((this.amtPhiz==null && other.getAmtPhiz()==null) || 
             (this.amtPhiz!=null &&
              this.amtPhiz.equals(other.getAmtPhiz()))) &&
            ((this.balance==null && other.getBalance()==null) || 
             (this.balance!=null &&
              this.balance.equals(other.getBalance()))) &&
            ((this.balancePhiz==null && other.getBalancePhiz()==null) || 
             (this.balancePhiz!=null &&
              this.balancePhiz.equals(other.getBalancePhiz())));
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
        if (getEffDate() != null) {
            _hashCode += getEffDate().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getDocumentNumber() != null) {
            _hashCode += getDocumentNumber().hashCode();
        }
        if (getCorAcc() != null) {
            _hashCode += getCorAcc().hashCode();
        }
        if (getStmtSummType() != null) {
            _hashCode += getStmtSummType().hashCode();
        }
        if (getAmt() != null) {
            _hashCode += getAmt().hashCode();
        }
        if (getAmtPhiz() != null) {
            _hashCode += getAmtPhiz().hashCode();
        }
        if (getBalance() != null) {
            _hashCode += getBalance().hashCode();
        }
        if (getBalancePhiz() != null) {
            _hashCode += getBalancePhiz().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctFullStmtRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corAcc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorAcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stmtSummType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummType_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amtPhiz");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AmtPhiz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balancePhiz");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalancePhiz"));
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
