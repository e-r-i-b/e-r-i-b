/**
 * BankAcctRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Список депозитов,ОМС, кредитов, банковских карт, длительных поручений,
 * услуг мобильный банк
 */
public class BankAcctRec_Type  implements java.io.Serializable {
    /* Доп.информация о банке используется для DepAcctId и BankAcctId
     * и CardAcctId */
    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type imsAcctInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBal;

    public BankAcctRec_Type() {
    }

    public BankAcctRec_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type imsAcctInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBal) {
           this.bankInfo = bankInfo;
           this.bankAcctId = bankAcctId;
           this.imsAcctInfo = imsAcctInfo;
           this.cardAcctId = cardAcctId;
           this.acctBal = acctBal;
    }


    /**
     * Gets the bankInfo value for this BankAcctRec_Type.
     * 
     * @return bankInfo   * Доп.информация о банке используется для DepAcctId и BankAcctId
     * и CardAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this BankAcctRec_Type.
     * 
     * @param bankInfo   * Доп.информация о банке используется для DepAcctId и BankAcctId
     * и CardAcctId
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the bankAcctId value for this BankAcctRec_Type.
     * 
     * @return bankAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type getBankAcctId() {
        return bankAcctId;
    }


    /**
     * Sets the bankAcctId value for this BankAcctRec_Type.
     * 
     * @param bankAcctId
     */
    public void setBankAcctId(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId) {
        this.bankAcctId = bankAcctId;
    }


    /**
     * Gets the imsAcctInfo value for this BankAcctRec_Type.
     * 
     * @return imsAcctInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type getImsAcctInfo() {
        return imsAcctInfo;
    }


    /**
     * Sets the imsAcctInfo value for this BankAcctRec_Type.
     * 
     * @param imsAcctInfo
     */
    public void setImsAcctInfo(com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type imsAcctInfo) {
        this.imsAcctInfo = imsAcctInfo;
    }


    /**
     * Gets the cardAcctId value for this BankAcctRec_Type.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this BankAcctRec_Type.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the acctBal value for this BankAcctRec_Type.
     * 
     * @return acctBal
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this BankAcctRec_Type.
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

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctRec_Type)) return false;
        BankAcctRec_Type other = (BankAcctRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.bankAcctId==null && other.getBankAcctId()==null) || 
             (this.bankAcctId!=null &&
              this.bankAcctId.equals(other.getBankAcctId()))) &&
            ((this.imsAcctInfo==null && other.getImsAcctInfo()==null) || 
             (this.imsAcctInfo!=null &&
              this.imsAcctInfo.equals(other.getImsAcctInfo()))) &&
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              java.util.Arrays.equals(this.acctBal, other.getAcctBal())));
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
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getBankAcctId() != null) {
            _hashCode += getBankAcctId().hashCode();
        }
        if (getImsAcctInfo() != null) {
            _hashCode += getImsAcctInfo().hashCode();
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imsAcctInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
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
