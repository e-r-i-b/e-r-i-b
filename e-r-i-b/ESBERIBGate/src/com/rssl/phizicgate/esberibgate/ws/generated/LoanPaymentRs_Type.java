/**
 * LoanPaymentRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Ответ интерфейса LN_PSC получения графика платежей
 */
public class LoanPaymentRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private java.lang.String systemId;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    private java.lang.String acctId;

    /* Сумма всех выплат, которые были произведены, с учетом штрафов
     * и проч. */
    private java.math.BigDecimal doneAmount;

    /* Сумма платежа для закрытия кредита */
    private java.math.BigDecimal remainAmount;

    /* Сумма всех штрафов и пеней */
    private java.math.BigDecimal fineAmount;

    /* Общее количество платежей */
    private java.lang.Long maxSize;

    private com.rssl.phizicgate.esberibgate.ws.generated.LoanPaymentRec_Type[] loanPaymentRec;

    public LoanPaymentRs_Type() {
    }

    public LoanPaymentRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           java.lang.String systemId,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status,
           java.lang.String acctId,
           java.math.BigDecimal doneAmount,
           java.math.BigDecimal remainAmount,
           java.math.BigDecimal fineAmount,
           java.lang.Long maxSize,
           com.rssl.phizicgate.esberibgate.ws.generated.LoanPaymentRec_Type[] loanPaymentRec) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.systemId = systemId;
           this.status = status;
           this.acctId = acctId;
           this.doneAmount = doneAmount;
           this.remainAmount = remainAmount;
           this.fineAmount = fineAmount;
           this.maxSize = maxSize;
           this.loanPaymentRec = loanPaymentRec;
    }


    /**
     * Gets the rqUID value for this LoanPaymentRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this LoanPaymentRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this LoanPaymentRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this LoanPaymentRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this LoanPaymentRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this LoanPaymentRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the systemId value for this LoanPaymentRs_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this LoanPaymentRs_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the status value for this LoanPaymentRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this LoanPaymentRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the acctId value for this LoanPaymentRs_Type.
     * 
     * @return acctId
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this LoanPaymentRs_Type.
     * 
     * @param acctId
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the doneAmount value for this LoanPaymentRs_Type.
     * 
     * @return doneAmount   * Сумма всех выплат, которые были произведены, с учетом штрафов
     * и проч.
     */
    public java.math.BigDecimal getDoneAmount() {
        return doneAmount;
    }


    /**
     * Sets the doneAmount value for this LoanPaymentRs_Type.
     * 
     * @param doneAmount   * Сумма всех выплат, которые были произведены, с учетом штрафов
     * и проч.
     */
    public void setDoneAmount(java.math.BigDecimal doneAmount) {
        this.doneAmount = doneAmount;
    }


    /**
     * Gets the remainAmount value for this LoanPaymentRs_Type.
     * 
     * @return remainAmount   * Сумма платежа для закрытия кредита
     */
    public java.math.BigDecimal getRemainAmount() {
        return remainAmount;
    }


    /**
     * Sets the remainAmount value for this LoanPaymentRs_Type.
     * 
     * @param remainAmount   * Сумма платежа для закрытия кредита
     */
    public void setRemainAmount(java.math.BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }


    /**
     * Gets the fineAmount value for this LoanPaymentRs_Type.
     * 
     * @return fineAmount   * Сумма всех штрафов и пеней
     */
    public java.math.BigDecimal getFineAmount() {
        return fineAmount;
    }


    /**
     * Sets the fineAmount value for this LoanPaymentRs_Type.
     * 
     * @param fineAmount   * Сумма всех штрафов и пеней
     */
    public void setFineAmount(java.math.BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }


    /**
     * Gets the maxSize value for this LoanPaymentRs_Type.
     * 
     * @return maxSize   * Общее количество платежей
     */
    public java.lang.Long getMaxSize() {
        return maxSize;
    }


    /**
     * Sets the maxSize value for this LoanPaymentRs_Type.
     * 
     * @param maxSize   * Общее количество платежей
     */
    public void setMaxSize(java.lang.Long maxSize) {
        this.maxSize = maxSize;
    }


    /**
     * Gets the loanPaymentRec value for this LoanPaymentRs_Type.
     * 
     * @return loanPaymentRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.LoanPaymentRec_Type[] getLoanPaymentRec() {
        return loanPaymentRec;
    }


    /**
     * Sets the loanPaymentRec value for this LoanPaymentRs_Type.
     * 
     * @param loanPaymentRec
     */
    public void setLoanPaymentRec(com.rssl.phizicgate.esberibgate.ws.generated.LoanPaymentRec_Type[] loanPaymentRec) {
        this.loanPaymentRec = loanPaymentRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.LoanPaymentRec_Type getLoanPaymentRec(int i) {
        return this.loanPaymentRec[i];
    }

    public void setLoanPaymentRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.LoanPaymentRec_Type _value) {
        this.loanPaymentRec[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoanPaymentRs_Type)) return false;
        LoanPaymentRs_Type other = (LoanPaymentRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.doneAmount==null && other.getDoneAmount()==null) || 
             (this.doneAmount!=null &&
              this.doneAmount.equals(other.getDoneAmount()))) &&
            ((this.remainAmount==null && other.getRemainAmount()==null) || 
             (this.remainAmount!=null &&
              this.remainAmount.equals(other.getRemainAmount()))) &&
            ((this.fineAmount==null && other.getFineAmount()==null) || 
             (this.fineAmount!=null &&
              this.fineAmount.equals(other.getFineAmount()))) &&
            ((this.maxSize==null && other.getMaxSize()==null) || 
             (this.maxSize!=null &&
              this.maxSize.equals(other.getMaxSize()))) &&
            ((this.loanPaymentRec==null && other.getLoanPaymentRec()==null) || 
             (this.loanPaymentRec!=null &&
              java.util.Arrays.equals(this.loanPaymentRec, other.getLoanPaymentRec())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getDoneAmount() != null) {
            _hashCode += getDoneAmount().hashCode();
        }
        if (getRemainAmount() != null) {
            _hashCode += getRemainAmount().hashCode();
        }
        if (getFineAmount() != null) {
            _hashCode += getFineAmount().hashCode();
        }
        if (getMaxSize() != null) {
            _hashCode += getMaxSize().hashCode();
        }
        if (getLoanPaymentRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLoanPaymentRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLoanPaymentRec(), i);
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
        new org.apache.axis.description.TypeDesc(LoanPaymentRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
        elemField.setFieldName("doneAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DoneAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remainAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RemainAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fineAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FineAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaxSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanPaymentRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentRec"));
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
