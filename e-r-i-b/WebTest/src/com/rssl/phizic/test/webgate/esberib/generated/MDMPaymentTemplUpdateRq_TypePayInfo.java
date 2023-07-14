/**
 * MDMPaymentTemplUpdateRq_TypePayInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class MDMPaymentTemplUpdateRq_TypePayInfo  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type acctId;

    /* Назначение платежа */
    private java.lang.String purpose;

    /* Код операции по счетам нерезидентов */
    private java.lang.String noneResidentCode;

    private com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoCurAmt curAmt;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type acctIdTo;

    private com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo toBankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo recipientInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type taxColl;

    private com.rssl.phizic.test.webgate.esberib.generated.Requisite_Type[] biling;

    public MDMPaymentTemplUpdateRq_TypePayInfo() {
    }

    public MDMPaymentTemplUpdateRq_TypePayInfo(
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type acctId,
           java.lang.String purpose,
           java.lang.String noneResidentCode,
           com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoCurAmt curAmt,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type acctIdTo,
           com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo toBankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo recipientInfo,
           com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type taxColl,
           com.rssl.phizic.test.webgate.esberib.generated.Requisite_Type[] biling) {
           this.cardAcctId = cardAcctId;
           this.acctId = acctId;
           this.purpose = purpose;
           this.noneResidentCode = noneResidentCode;
           this.curAmt = curAmt;
           this.cardAcctIdTo = cardAcctIdTo;
           this.acctIdTo = acctIdTo;
           this.toBankInfo = toBankInfo;
           this.recipientInfo = recipientInfo;
           this.taxColl = taxColl;
           this.biling = biling;
    }


    /**
     * Gets the cardAcctId value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the acctId value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return acctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param acctId
     */
    public void setAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the purpose value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return purpose   * Назначение платежа
     */
    public java.lang.String getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param purpose   * Назначение платежа
     */
    public void setPurpose(java.lang.String purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the noneResidentCode value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return noneResidentCode   * Код операции по счетам нерезидентов
     */
    public java.lang.String getNoneResidentCode() {
        return noneResidentCode;
    }


    /**
     * Sets the noneResidentCode value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param noneResidentCode   * Код операции по счетам нерезидентов
     */
    public void setNoneResidentCode(java.lang.String noneResidentCode) {
        this.noneResidentCode = noneResidentCode;
    }


    /**
     * Gets the curAmt value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return curAmt
     */
    public com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoCurAmt getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param curAmt
     */
    public void setCurAmt(com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoCurAmt curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the cardAcctIdTo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return cardAcctIdTo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdTo() {
        return cardAcctIdTo;
    }


    /**
     * Sets the cardAcctIdTo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param cardAcctIdTo
     */
    public void setCardAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo) {
        this.cardAcctIdTo = cardAcctIdTo;
    }


    /**
     * Gets the acctIdTo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return acctIdTo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getAcctIdTo() {
        return acctIdTo;
    }


    /**
     * Sets the acctIdTo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param acctIdTo
     */
    public void setAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type acctIdTo) {
        this.acctIdTo = acctIdTo;
    }


    /**
     * Gets the toBankInfo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return toBankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo getToBankInfo() {
        return toBankInfo;
    }


    /**
     * Sets the toBankInfo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param toBankInfo
     */
    public void setToBankInfo(com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo toBankInfo) {
        this.toBankInfo = toBankInfo;
    }


    /**
     * Gets the recipientInfo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return recipientInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo getRecipientInfo() {
        return recipientInfo;
    }


    /**
     * Sets the recipientInfo value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param recipientInfo
     */
    public void setRecipientInfo(com.rssl.phizic.test.webgate.esberib.generated.MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo recipientInfo) {
        this.recipientInfo = recipientInfo;
    }


    /**
     * Gets the taxColl value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return taxColl
     */
    public com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type getTaxColl() {
        return taxColl;
    }


    /**
     * Sets the taxColl value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param taxColl
     */
    public void setTaxColl(com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type taxColl) {
        this.taxColl = taxColl;
    }


    /**
     * Gets the biling value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @return biling
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Requisite_Type[] getBiling() {
        return biling;
    }


    /**
     * Sets the biling value for this MDMPaymentTemplUpdateRq_TypePayInfo.
     * 
     * @param biling
     */
    public void setBiling(com.rssl.phizic.test.webgate.esberib.generated.Requisite_Type[] biling) {
        this.biling = biling;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MDMPaymentTemplUpdateRq_TypePayInfo)) return false;
        MDMPaymentTemplUpdateRq_TypePayInfo other = (MDMPaymentTemplUpdateRq_TypePayInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.noneResidentCode==null && other.getNoneResidentCode()==null) || 
             (this.noneResidentCode!=null &&
              this.noneResidentCode.equals(other.getNoneResidentCode()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.cardAcctIdTo==null && other.getCardAcctIdTo()==null) || 
             (this.cardAcctIdTo!=null &&
              this.cardAcctIdTo.equals(other.getCardAcctIdTo()))) &&
            ((this.acctIdTo==null && other.getAcctIdTo()==null) || 
             (this.acctIdTo!=null &&
              this.acctIdTo.equals(other.getAcctIdTo()))) &&
            ((this.toBankInfo==null && other.getToBankInfo()==null) || 
             (this.toBankInfo!=null &&
              this.toBankInfo.equals(other.getToBankInfo()))) &&
            ((this.recipientInfo==null && other.getRecipientInfo()==null) || 
             (this.recipientInfo!=null &&
              this.recipientInfo.equals(other.getRecipientInfo()))) &&
            ((this.taxColl==null && other.getTaxColl()==null) || 
             (this.taxColl!=null &&
              this.taxColl.equals(other.getTaxColl()))) &&
            ((this.biling==null && other.getBiling()==null) || 
             (this.biling!=null &&
              java.util.Arrays.equals(this.biling, other.getBiling())));
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
        if (getCardAcctId() != null) {
            _hashCode += getCardAcctId().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getNoneResidentCode() != null) {
            _hashCode += getNoneResidentCode().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getCardAcctIdTo() != null) {
            _hashCode += getCardAcctIdTo().hashCode();
        }
        if (getAcctIdTo() != null) {
            _hashCode += getAcctIdTo().hashCode();
        }
        if (getToBankInfo() != null) {
            _hashCode += getToBankInfo().hashCode();
        }
        if (getRecipientInfo() != null) {
            _hashCode += getRecipientInfo().hashCode();
        }
        if (getTaxColl() != null) {
            _hashCode += getTaxColl().hashCode();
        }
        if (getBiling() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBiling());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBiling(), i);
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
        new org.apache.axis.description.TypeDesc(MDMPaymentTemplUpdateRq_TypePayInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">MDMPaymentTemplUpdateRq_Type>PayInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purpose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Purpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noneResidentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NoneResidentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>MDMPaymentTemplUpdateRq_Type>PayInfo>CurAmt"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toBankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ToBankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>MDMPaymentTemplUpdateRq_Type>PayInfo>ToBankInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>MDMPaymentTemplUpdateRq_Type>PayInfo>RecipientInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxColl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxColl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxColl_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("biling");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Biling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisites"));
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
