/**
 * CCAcctFullStmtInqRs_TypeCardAcctRec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class CCAcctFullStmtInqRs_TypeCardAcctRec  implements java.io.Serializable {
    /* Дата начала периода выписки. */
    private java.lang.String fromDate;

    /* Дата окончания периода выписки. */
    private java.lang.String toDate;

    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBal;

    /* Входящий остаток по счету карты клиента на дату начала периода
     * выписки */
    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type startBalance;

    /* Исходящий остаток по счету карты на дату окончания периода
     * выписки */
    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type endBalance;

    private com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_Type[] CCAcctStmtRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    public CCAcctFullStmtInqRs_TypeCardAcctRec() {
    }

    public CCAcctFullStmtInqRs_TypeCardAcctRec(
           java.lang.String fromDate,
           java.lang.String toDate,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBal,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type startBalance,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type endBalance,
           com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_Type[] CCAcctStmtRec,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
           this.fromDate = fromDate;
           this.toDate = toDate;
           this.cardAcctId = cardAcctId;
           this.acctBal = acctBal;
           this.startBalance = startBalance;
           this.endBalance = endBalance;
           this.CCAcctStmtRec = CCAcctStmtRec;
           this.status = status;
    }


    /**
     * Gets the fromDate value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return fromDate   * Дата начала периода выписки.
     */
    public java.lang.String getFromDate() {
        return fromDate;
    }


    /**
     * Sets the fromDate value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param fromDate   * Дата начала периода выписки.
     */
    public void setFromDate(java.lang.String fromDate) {
        this.fromDate = fromDate;
    }


    /**
     * Gets the toDate value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return toDate   * Дата окончания периода выписки.
     */
    public java.lang.String getToDate() {
        return toDate;
    }


    /**
     * Sets the toDate value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param toDate   * Дата окончания периода выписки.
     */
    public void setToDate(java.lang.String toDate) {
        this.toDate = toDate;
    }


    /**
     * Gets the cardAcctId value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the acctBal value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return acctBal
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param acctBal
     */
    public void setAcctBal(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBal) {
        this.acctBal = acctBal;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getAcctBal(int i) {
        return this.acctBal[i];
    }

    public void setAcctBal(int i, com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type _value) {
        this.acctBal[i] = _value;
    }


    /**
     * Gets the startBalance value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return startBalance   * Входящий остаток по счету карты клиента на дату начала периода
     * выписки
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getStartBalance() {
        return startBalance;
    }


    /**
     * Sets the startBalance value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param startBalance   * Входящий остаток по счету карты клиента на дату начала периода
     * выписки
     */
    public void setStartBalance(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type startBalance) {
        this.startBalance = startBalance;
    }


    /**
     * Gets the endBalance value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return endBalance   * Исходящий остаток по счету карты на дату окончания периода
     * выписки
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getEndBalance() {
        return endBalance;
    }


    /**
     * Sets the endBalance value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param endBalance   * Исходящий остаток по счету карты на дату окончания периода
     * выписки
     */
    public void setEndBalance(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type endBalance) {
        this.endBalance = endBalance;
    }


    /**
     * Gets the CCAcctStmtRec value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return CCAcctStmtRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_Type[] getCCAcctStmtRec() {
        return CCAcctStmtRec;
    }


    /**
     * Sets the CCAcctStmtRec value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param CCAcctStmtRec
     */
    public void setCCAcctStmtRec(com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_Type[] CCAcctStmtRec) {
        this.CCAcctStmtRec = CCAcctStmtRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_Type getCCAcctStmtRec(int i) {
        return this.CCAcctStmtRec[i];
    }

    public void setCCAcctStmtRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.CCAcctStmtRec_Type _value) {
        this.CCAcctStmtRec[i] = _value;
    }


    /**
     * Gets the status value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CCAcctFullStmtInqRs_TypeCardAcctRec.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CCAcctFullStmtInqRs_TypeCardAcctRec)) return false;
        CCAcctFullStmtInqRs_TypeCardAcctRec other = (CCAcctFullStmtInqRs_TypeCardAcctRec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fromDate==null && other.getFromDate()==null) || 
             (this.fromDate!=null &&
              this.fromDate.equals(other.getFromDate()))) &&
            ((this.toDate==null && other.getToDate()==null) || 
             (this.toDate!=null &&
              this.toDate.equals(other.getToDate()))) &&
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              java.util.Arrays.equals(this.acctBal, other.getAcctBal()))) &&
            ((this.startBalance==null && other.getStartBalance()==null) || 
             (this.startBalance!=null &&
              this.startBalance.equals(other.getStartBalance()))) &&
            ((this.endBalance==null && other.getEndBalance()==null) || 
             (this.endBalance!=null &&
              this.endBalance.equals(other.getEndBalance()))) &&
            ((this.CCAcctStmtRec==null && other.getCCAcctStmtRec()==null) || 
             (this.CCAcctStmtRec!=null &&
              java.util.Arrays.equals(this.CCAcctStmtRec, other.getCCAcctStmtRec()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getFromDate() != null) {
            _hashCode += getFromDate().hashCode();
        }
        if (getToDate() != null) {
            _hashCode += getToDate().hashCode();
        }
        if (getCardAcctId() != null) {
            _hashCode += getCardAcctId().hashCode();
        }
        if (getAcctBal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcctBal());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcctBal(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStartBalance() != null) {
            _hashCode += getStartBalance().hashCode();
        }
        if (getEndBalance() != null) {
            _hashCode += getEndBalance().hashCode();
        }
        if (getCCAcctStmtRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCCAcctStmtRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCCAcctStmtRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CCAcctFullStmtInqRs_TypeCardAcctRec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctFullStmtInqRs_Type>CardAcctRec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FromDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ToDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CCAcctStmtRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctStmtRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctStmtRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
