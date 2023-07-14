/**
 * CCAcctStmtRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class CCAcctStmtRec_Type  implements java.io.Serializable {
    /* Дата и время операции */
    private java.lang.String effDate;

    /* Дата списания или зачисления */
    private java.lang.String discDate;

    /* Номер документа */
    private java.lang.String number;

    /* Шифр операции */
    private java.lang.String code;

    /* Номер корреспондирующего счета */
    private java.lang.String corAccountNumber;

    private com.rssl.phizicgate.esberibgate.ws.generated.StmtSummAmt_Type stmtSummAmt;

    /* True- операция зачисления, False – операция списания */
    private java.lang.Boolean isDebit;

    /* Признак, что операция списания со счета карты. True- операция
     * зачисления, False – операция списания */
    private java.lang.Boolean trnType;

    /* Наименование операции. Готовое для отображения пользователю. */
    private java.lang.String trnDest;

    /* Информация о торговой точке, совершившей операцию */
    private java.lang.String trnSrc;

    /* Описание транзакции */
    private java.lang.String trnDesc;

    /* Сумма в валюте счета карты */
    private com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOrigCurAmt origCurAmt;

    /* Сумма в валюте операции */
    private com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOperationAmt operationAmt;

    /* Остаток на счете после выполнения операции */
    private com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeRemaindAmt remaindAmt;

    public CCAcctStmtRec_Type() {
    }

    public CCAcctStmtRec_Type(
           java.lang.String effDate,
           java.lang.String discDate,
           java.lang.String number,
           java.lang.String code,
           java.lang.String corAccountNumber,
           com.rssl.phizicgate.esberibgate.ws.generated.StmtSummAmt_Type stmtSummAmt,
           java.lang.Boolean isDebit,
           java.lang.Boolean trnType,
           java.lang.String trnDest,
           java.lang.String trnSrc,
           java.lang.String trnDesc,
           com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOrigCurAmt origCurAmt,
           com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOperationAmt operationAmt,
           com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeRemaindAmt remaindAmt) {
           this.effDate = effDate;
           this.discDate = discDate;
           this.number = number;
           this.code = code;
           this.corAccountNumber = corAccountNumber;
           this.stmtSummAmt = stmtSummAmt;
           this.isDebit = isDebit;
           this.trnType = trnType;
           this.trnDest = trnDest;
           this.trnSrc = trnSrc;
           this.trnDesc = trnDesc;
           this.origCurAmt = origCurAmt;
           this.operationAmt = operationAmt;
           this.remaindAmt = remaindAmt;
    }


    /**
     * Gets the effDate value for this CCAcctStmtRec_Type.
     * 
     * @return effDate   * Дата и время операции
     */
    public java.lang.String getEffDate() {
        return effDate;
    }


    /**
     * Sets the effDate value for this CCAcctStmtRec_Type.
     * 
     * @param effDate   * Дата и время операции
     */
    public void setEffDate(java.lang.String effDate) {
        this.effDate = effDate;
    }


    /**
     * Gets the discDate value for this CCAcctStmtRec_Type.
     * 
     * @return discDate   * Дата списания или зачисления
     */
    public java.lang.String getDiscDate() {
        return discDate;
    }


    /**
     * Sets the discDate value for this CCAcctStmtRec_Type.
     * 
     * @param discDate   * Дата списания или зачисления
     */
    public void setDiscDate(java.lang.String discDate) {
        this.discDate = discDate;
    }


    /**
     * Gets the number value for this CCAcctStmtRec_Type.
     * 
     * @return number   * Номер документа
     */
    public java.lang.String getNumber() {
        return number;
    }


    /**
     * Sets the number value for this CCAcctStmtRec_Type.
     * 
     * @param number   * Номер документа
     */
    public void setNumber(java.lang.String number) {
        this.number = number;
    }


    /**
     * Gets the code value for this CCAcctStmtRec_Type.
     * 
     * @return code   * Шифр операции
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this CCAcctStmtRec_Type.
     * 
     * @param code   * Шифр операции
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the corAccountNumber value for this CCAcctStmtRec_Type.
     * 
     * @return corAccountNumber   * Номер корреспондирующего счета
     */
    public java.lang.String getCorAccountNumber() {
        return corAccountNumber;
    }


    /**
     * Sets the corAccountNumber value for this CCAcctStmtRec_Type.
     * 
     * @param corAccountNumber   * Номер корреспондирующего счета
     */
    public void setCorAccountNumber(java.lang.String corAccountNumber) {
        this.corAccountNumber = corAccountNumber;
    }


    /**
     * Gets the stmtSummAmt value for this CCAcctStmtRec_Type.
     * 
     * @return stmtSummAmt
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.StmtSummAmt_Type getStmtSummAmt() {
        return stmtSummAmt;
    }


    /**
     * Sets the stmtSummAmt value for this CCAcctStmtRec_Type.
     * 
     * @param stmtSummAmt
     */
    public void setStmtSummAmt(com.rssl.phizicgate.esberibgate.ws.generated.StmtSummAmt_Type stmtSummAmt) {
        this.stmtSummAmt = stmtSummAmt;
    }


    /**
     * Gets the isDebit value for this CCAcctStmtRec_Type.
     * 
     * @return isDebit   * True- операция зачисления, False – операция списания
     */
    public java.lang.Boolean getIsDebit() {
        return isDebit;
    }


    /**
     * Sets the isDebit value for this CCAcctStmtRec_Type.
     * 
     * @param isDebit   * True- операция зачисления, False – операция списания
     */
    public void setIsDebit(java.lang.Boolean isDebit) {
        this.isDebit = isDebit;
    }


    /**
     * Gets the trnType value for this CCAcctStmtRec_Type.
     * 
     * @return trnType   * Признак, что операция списания со счета карты. True- операция
     * зачисления, False – операция списания
     */
    public java.lang.Boolean getTrnType() {
        return trnType;
    }


    /**
     * Sets the trnType value for this CCAcctStmtRec_Type.
     * 
     * @param trnType   * Признак, что операция списания со счета карты. True- операция
     * зачисления, False – операция списания
     */
    public void setTrnType(java.lang.Boolean trnType) {
        this.trnType = trnType;
    }


    /**
     * Gets the trnDest value for this CCAcctStmtRec_Type.
     * 
     * @return trnDest   * Наименование операции. Готовое для отображения пользователю.
     */
    public java.lang.String getTrnDest() {
        return trnDest;
    }


    /**
     * Sets the trnDest value for this CCAcctStmtRec_Type.
     * 
     * @param trnDest   * Наименование операции. Готовое для отображения пользователю.
     */
    public void setTrnDest(java.lang.String trnDest) {
        this.trnDest = trnDest;
    }


    /**
     * Gets the trnSrc value for this CCAcctStmtRec_Type.
     * 
     * @return trnSrc   * Информация о торговой точке, совершившей операцию
     */
    public java.lang.String getTrnSrc() {
        return trnSrc;
    }


    /**
     * Sets the trnSrc value for this CCAcctStmtRec_Type.
     * 
     * @param trnSrc   * Информация о торговой точке, совершившей операцию
     */
    public void setTrnSrc(java.lang.String trnSrc) {
        this.trnSrc = trnSrc;
    }


    /**
     * Gets the trnDesc value for this CCAcctStmtRec_Type.
     * 
     * @return trnDesc   * Описание транзакции
     */
    public java.lang.String getTrnDesc() {
        return trnDesc;
    }


    /**
     * Sets the trnDesc value for this CCAcctStmtRec_Type.
     * 
     * @param trnDesc   * Описание транзакции
     */
    public void setTrnDesc(java.lang.String trnDesc) {
        this.trnDesc = trnDesc;
    }


    /**
     * Gets the origCurAmt value for this CCAcctStmtRec_Type.
     * 
     * @return origCurAmt   * Сумма в валюте счета карты
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOrigCurAmt getOrigCurAmt() {
        return origCurAmt;
    }


    /**
     * Sets the origCurAmt value for this CCAcctStmtRec_Type.
     * 
     * @param origCurAmt   * Сумма в валюте счета карты
     */
    public void setOrigCurAmt(com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOrigCurAmt origCurAmt) {
        this.origCurAmt = origCurAmt;
    }


    /**
     * Gets the operationAmt value for this CCAcctStmtRec_Type.
     * 
     * @return operationAmt   * Сумма в валюте операции
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOperationAmt getOperationAmt() {
        return operationAmt;
    }


    /**
     * Sets the operationAmt value for this CCAcctStmtRec_Type.
     * 
     * @param operationAmt   * Сумма в валюте операции
     */
    public void setOperationAmt(com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeOperationAmt operationAmt) {
        this.operationAmt = operationAmt;
    }


    /**
     * Gets the remaindAmt value for this CCAcctStmtRec_Type.
     * 
     * @return remaindAmt   * Остаток на счете после выполнения операции
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeRemaindAmt getRemaindAmt() {
        return remaindAmt;
    }


    /**
     * Sets the remaindAmt value for this CCAcctStmtRec_Type.
     * 
     * @param remaindAmt   * Остаток на счете после выполнения операции
     */
    public void setRemaindAmt(com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_TypeRemaindAmt remaindAmt) {
        this.remaindAmt = remaindAmt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CCAcctStmtRec_Type)) return false;
        CCAcctStmtRec_Type other = (CCAcctStmtRec_Type) obj;
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
            ((this.discDate==null && other.getDiscDate()==null) || 
             (this.discDate!=null &&
              this.discDate.equals(other.getDiscDate()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.corAccountNumber==null && other.getCorAccountNumber()==null) || 
             (this.corAccountNumber!=null &&
              this.corAccountNumber.equals(other.getCorAccountNumber()))) &&
            ((this.stmtSummAmt==null && other.getStmtSummAmt()==null) || 
             (this.stmtSummAmt!=null &&
              this.stmtSummAmt.equals(other.getStmtSummAmt()))) &&
            ((this.isDebit==null && other.getIsDebit()==null) || 
             (this.isDebit!=null &&
              this.isDebit.equals(other.getIsDebit()))) &&
            ((this.trnType==null && other.getTrnType()==null) || 
             (this.trnType!=null &&
              this.trnType.equals(other.getTrnType()))) &&
            ((this.trnDest==null && other.getTrnDest()==null) || 
             (this.trnDest!=null &&
              this.trnDest.equals(other.getTrnDest()))) &&
            ((this.trnSrc==null && other.getTrnSrc()==null) || 
             (this.trnSrc!=null &&
              this.trnSrc.equals(other.getTrnSrc()))) &&
            ((this.trnDesc==null && other.getTrnDesc()==null) || 
             (this.trnDesc!=null &&
              this.trnDesc.equals(other.getTrnDesc()))) &&
            ((this.origCurAmt==null && other.getOrigCurAmt()==null) || 
             (this.origCurAmt!=null &&
              this.origCurAmt.equals(other.getOrigCurAmt()))) &&
            ((this.operationAmt==null && other.getOperationAmt()==null) || 
             (this.operationAmt!=null &&
              this.operationAmt.equals(other.getOperationAmt()))) &&
            ((this.remaindAmt==null && other.getRemaindAmt()==null) || 
             (this.remaindAmt!=null &&
              this.remaindAmt.equals(other.getRemaindAmt())));
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
        if (getDiscDate() != null) {
            _hashCode += getDiscDate().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getCorAccountNumber() != null) {
            _hashCode += getCorAccountNumber().hashCode();
        }
        if (getStmtSummAmt() != null) {
            _hashCode += getStmtSummAmt().hashCode();
        }
        if (getIsDebit() != null) {
            _hashCode += getIsDebit().hashCode();
        }
        if (getTrnType() != null) {
            _hashCode += getTrnType().hashCode();
        }
        if (getTrnDest() != null) {
            _hashCode += getTrnDest().hashCode();
        }
        if (getTrnSrc() != null) {
            _hashCode += getTrnSrc().hashCode();
        }
        if (getTrnDesc() != null) {
            _hashCode += getTrnDesc().hashCode();
        }
        if (getOrigCurAmt() != null) {
            _hashCode += getOrigCurAmt().hashCode();
        }
        if (getOperationAmt() != null) {
            _hashCode += getOperationAmt().hashCode();
        }
        if (getRemaindAmt() != null) {
            _hashCode += getRemaindAmt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CCAcctStmtRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctStmtRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DiscDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corAccountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorAccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stmtSummAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummAmt_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDebit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsDebit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trnType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TrnType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trnDest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TrnDest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trnSrc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TrnSrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trnDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TrnDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origCurAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrigCurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctStmtRec_Type>OrigCurAmt"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperationAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctStmtRec_Type>OperationAmt"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remaindAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RemaindAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctStmtRec_Type>RemaindAmt"));
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
