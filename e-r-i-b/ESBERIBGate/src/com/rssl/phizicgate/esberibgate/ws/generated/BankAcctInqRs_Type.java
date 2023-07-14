/**
 * BankAcctInqRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Получение списка продуктов
 */
public class BankAcctInqRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] depAcctRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] bankAcctRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] cardAcctRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepoAccounts_Type depoAccounts;

    private com.rssl.phizicgate.esberibgate.ws.generated.SvcsAcct_Type[] svcsAcct;

    private com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctRec_Type[] loanAcctRec;

    /* Список ценных бумаг */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesAcctInfo_Type securitiesAcctInfo;

    public BankAcctInqRs_Type() {
    }

    public BankAcctInqRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] depAcctRec,
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] bankAcctRec,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] cardAcctRec,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAccounts_Type depoAccounts,
           com.rssl.phizicgate.esberibgate.ws.generated.SvcsAcct_Type[] svcsAcct,
           com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctRec_Type[] loanAcctRec,
           com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesAcctInfo_Type securitiesAcctInfo) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.depAcctRec = depAcctRec;
           this.bankAcctRec = bankAcctRec;
           this.cardAcctRec = cardAcctRec;
           this.depoAccounts = depoAccounts;
           this.svcsAcct = svcsAcct;
           this.loanAcctRec = loanAcctRec;
           this.securitiesAcctInfo = securitiesAcctInfo;
    }


    /**
     * Gets the rqUID value for this BankAcctInqRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this BankAcctInqRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this BankAcctInqRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this BankAcctInqRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this BankAcctInqRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this BankAcctInqRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this BankAcctInqRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this BankAcctInqRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the depAcctRec value for this BankAcctInqRs_Type.
     * 
     * @return depAcctRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] getDepAcctRec() {
        return depAcctRec;
    }


    /**
     * Sets the depAcctRec value for this BankAcctInqRs_Type.
     * 
     * @param depAcctRec
     */
    public void setDepAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type[] depAcctRec) {
        this.depAcctRec = depAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type getDepAcctRec(int i) {
        return this.depAcctRec[i];
    }

    public void setDepAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepAcctRec_Type _value) {
        this.depAcctRec[i] = _value;
    }


    /**
     * Gets the bankAcctRec value for this BankAcctInqRs_Type.
     * 
     * @return bankAcctRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] getBankAcctRec() {
        return bankAcctRec;
    }


    /**
     * Sets the bankAcctRec value for this BankAcctInqRs_Type.
     * 
     * @param bankAcctRec
     */
    public void setBankAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type[] bankAcctRec) {
        this.bankAcctRec = bankAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type getBankAcctRec(int i) {
        return this.bankAcctRec[i];
    }

    public void setBankAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type _value) {
        this.bankAcctRec[i] = _value;
    }


    /**
     * Gets the cardAcctRec value for this BankAcctInqRs_Type.
     * 
     * @return cardAcctRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] getCardAcctRec() {
        return cardAcctRec;
    }


    /**
     * Sets the cardAcctRec value for this BankAcctInqRs_Type.
     * 
     * @param cardAcctRec
     */
    public void setCardAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type[] cardAcctRec) {
        this.cardAcctRec = cardAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type getCardAcctRec(int i) {
        return this.cardAcctRec[i];
    }

    public void setCardAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type _value) {
        this.cardAcctRec[i] = _value;
    }


    /**
     * Gets the depoAccounts value for this BankAcctInqRs_Type.
     * 
     * @return depoAccounts
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoAccounts_Type getDepoAccounts() {
        return depoAccounts;
    }


    /**
     * Sets the depoAccounts value for this BankAcctInqRs_Type.
     * 
     * @param depoAccounts
     */
    public void setDepoAccounts(com.rssl.phizicgate.esberibgate.ws.generated.DepoAccounts_Type depoAccounts) {
        this.depoAccounts = depoAccounts;
    }


    /**
     * Gets the svcsAcct value for this BankAcctInqRs_Type.
     * 
     * @return svcsAcct
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SvcsAcct_Type[] getSvcsAcct() {
        return svcsAcct;
    }


    /**
     * Sets the svcsAcct value for this BankAcctInqRs_Type.
     * 
     * @param svcsAcct
     */
    public void setSvcsAcct(com.rssl.phizicgate.esberibgate.ws.generated.SvcsAcct_Type[] svcsAcct) {
        this.svcsAcct = svcsAcct;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.SvcsAcct_Type getSvcsAcct(int i) {
        return this.svcsAcct[i];
    }

    public void setSvcsAcct(int i, com.rssl.phizicgate.esberibgate.ws.generated.SvcsAcct_Type _value) {
        this.svcsAcct[i] = _value;
    }


    /**
     * Gets the loanAcctRec value for this BankAcctInqRs_Type.
     * 
     * @return loanAcctRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctRec_Type[] getLoanAcctRec() {
        return loanAcctRec;
    }


    /**
     * Sets the loanAcctRec value for this BankAcctInqRs_Type.
     * 
     * @param loanAcctRec
     */
    public void setLoanAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctRec_Type[] loanAcctRec) {
        this.loanAcctRec = loanAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctRec_Type getLoanAcctRec(int i) {
        return this.loanAcctRec[i];
    }

    public void setLoanAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctRec_Type _value) {
        this.loanAcctRec[i] = _value;
    }


    /**
     * Gets the securitiesAcctInfo value for this BankAcctInqRs_Type.
     * 
     * @return securitiesAcctInfo   * Список ценных бумаг
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesAcctInfo_Type getSecuritiesAcctInfo() {
        return securitiesAcctInfo;
    }


    /**
     * Sets the securitiesAcctInfo value for this BankAcctInqRs_Type.
     * 
     * @param securitiesAcctInfo   * Список ценных бумаг
     */
    public void setSecuritiesAcctInfo(com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesAcctInfo_Type securitiesAcctInfo) {
        this.securitiesAcctInfo = securitiesAcctInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctInqRs_Type)) return false;
        BankAcctInqRs_Type other = (BankAcctInqRs_Type) obj;
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
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.depAcctRec==null && other.getDepAcctRec()==null) || 
             (this.depAcctRec!=null &&
              java.util.Arrays.equals(this.depAcctRec, other.getDepAcctRec()))) &&
            ((this.bankAcctRec==null && other.getBankAcctRec()==null) || 
             (this.bankAcctRec!=null &&
              java.util.Arrays.equals(this.bankAcctRec, other.getBankAcctRec()))) &&
            ((this.cardAcctRec==null && other.getCardAcctRec()==null) || 
             (this.cardAcctRec!=null &&
              java.util.Arrays.equals(this.cardAcctRec, other.getCardAcctRec()))) &&
            ((this.depoAccounts==null && other.getDepoAccounts()==null) || 
             (this.depoAccounts!=null &&
              this.depoAccounts.equals(other.getDepoAccounts()))) &&
            ((this.svcsAcct==null && other.getSvcsAcct()==null) || 
             (this.svcsAcct!=null &&
              java.util.Arrays.equals(this.svcsAcct, other.getSvcsAcct()))) &&
            ((this.loanAcctRec==null && other.getLoanAcctRec()==null) || 
             (this.loanAcctRec!=null &&
              java.util.Arrays.equals(this.loanAcctRec, other.getLoanAcctRec()))) &&
            ((this.securitiesAcctInfo==null && other.getSecuritiesAcctInfo()==null) || 
             (this.securitiesAcctInfo!=null &&
              this.securitiesAcctInfo.equals(other.getSecuritiesAcctInfo())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getDepAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepAcctRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBankAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAcctRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCardAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCardAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCardAcctRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDepoAccounts() != null) {
            _hashCode += getDepoAccounts().hashCode();
        }
        if (getSvcsAcct() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSvcsAcct());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSvcsAcct(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLoanAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLoanAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLoanAcctRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSecuritiesAcctInfo() != null) {
            _hashCode += getSecuritiesAcctInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctInqRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInqRs_Type"));
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
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAccounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccounts_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("svcsAcct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcsAcct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcsAcct"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securitiesAcctInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesAcctInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesAcctInfo_Type"));
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
