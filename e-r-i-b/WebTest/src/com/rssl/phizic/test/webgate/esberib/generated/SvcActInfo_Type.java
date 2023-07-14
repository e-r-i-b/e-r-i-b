/**
 * SvcActInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class SvcActInfo_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    private com.rssl.phizic.test.webgate.esberib.generated.SvcActInfo_TypeSvcAct svcAct;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom;

    /* БИК получателя */
    private java.lang.String recBIC;

    /* Корсчет получателя */
    private java.lang.String recCorrAccount;

    /* Расчетный счет получателя */
    private java.lang.String recCalcAccount;

    /* Валюта счета получателя */
    private java.lang.String recAcctCur;

    /* ИНН получателя */
    private java.lang.String recINN;

    /* КПП получателя */
    private java.lang.String KPPTo;

    /* Назначение платежа */
    private java.lang.String purpose;

    /* Наименование получателя юр. Лица, либо ФИО физ.лица */
    private java.lang.String recipientName;

    /* Тип операции */
    private java.lang.String pmtKind;

    private com.rssl.phizic.test.webgate.esberib.generated.Regular_Type regular;

    public SvcActInfo_Type() {
    }

    public SvcActInfo_Type(
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status,
           com.rssl.phizic.test.webgate.esberib.generated.SvcActInfo_TypeSvcAct svcAct,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom,
           java.lang.String recBIC,
           java.lang.String recCorrAccount,
           java.lang.String recCalcAccount,
           java.lang.String recAcctCur,
           java.lang.String recINN,
           java.lang.String KPPTo,
           java.lang.String purpose,
           java.lang.String recipientName,
           java.lang.String pmtKind,
           com.rssl.phizic.test.webgate.esberib.generated.Regular_Type regular) {
           this.status = status;
           this.svcAct = svcAct;
           this.depAcctIdFrom = depAcctIdFrom;
           this.cardAcctIdFrom = cardAcctIdFrom;
           this.recBIC = recBIC;
           this.recCorrAccount = recCorrAccount;
           this.recCalcAccount = recCalcAccount;
           this.recAcctCur = recAcctCur;
           this.recINN = recINN;
           this.KPPTo = KPPTo;
           this.purpose = purpose;
           this.recipientName = recipientName;
           this.pmtKind = pmtKind;
           this.regular = regular;
    }


    /**
     * Gets the status value for this SvcActInfo_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SvcActInfo_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the svcAct value for this SvcActInfo_Type.
     * 
     * @return svcAct
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SvcActInfo_TypeSvcAct getSvcAct() {
        return svcAct;
    }


    /**
     * Sets the svcAct value for this SvcActInfo_Type.
     * 
     * @param svcAct
     */
    public void setSvcAct(com.rssl.phizic.test.webgate.esberib.generated.SvcActInfo_TypeSvcAct svcAct) {
        this.svcAct = svcAct;
    }


    /**
     * Gets the depAcctIdFrom value for this SvcActInfo_Type.
     * 
     * @return depAcctIdFrom
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctIdFrom() {
        return depAcctIdFrom;
    }


    /**
     * Sets the depAcctIdFrom value for this SvcActInfo_Type.
     * 
     * @param depAcctIdFrom
     */
    public void setDepAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom) {
        this.depAcctIdFrom = depAcctIdFrom;
    }


    /**
     * Gets the cardAcctIdFrom value for this SvcActInfo_Type.
     * 
     * @return cardAcctIdFrom
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdFrom() {
        return cardAcctIdFrom;
    }


    /**
     * Sets the cardAcctIdFrom value for this SvcActInfo_Type.
     * 
     * @param cardAcctIdFrom
     */
    public void setCardAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom) {
        this.cardAcctIdFrom = cardAcctIdFrom;
    }


    /**
     * Gets the recBIC value for this SvcActInfo_Type.
     * 
     * @return recBIC   * БИК получателя
     */
    public java.lang.String getRecBIC() {
        return recBIC;
    }


    /**
     * Sets the recBIC value for this SvcActInfo_Type.
     * 
     * @param recBIC   * БИК получателя
     */
    public void setRecBIC(java.lang.String recBIC) {
        this.recBIC = recBIC;
    }


    /**
     * Gets the recCorrAccount value for this SvcActInfo_Type.
     * 
     * @return recCorrAccount   * Корсчет получателя
     */
    public java.lang.String getRecCorrAccount() {
        return recCorrAccount;
    }


    /**
     * Sets the recCorrAccount value for this SvcActInfo_Type.
     * 
     * @param recCorrAccount   * Корсчет получателя
     */
    public void setRecCorrAccount(java.lang.String recCorrAccount) {
        this.recCorrAccount = recCorrAccount;
    }


    /**
     * Gets the recCalcAccount value for this SvcActInfo_Type.
     * 
     * @return recCalcAccount   * Расчетный счет получателя
     */
    public java.lang.String getRecCalcAccount() {
        return recCalcAccount;
    }


    /**
     * Sets the recCalcAccount value for this SvcActInfo_Type.
     * 
     * @param recCalcAccount   * Расчетный счет получателя
     */
    public void setRecCalcAccount(java.lang.String recCalcAccount) {
        this.recCalcAccount = recCalcAccount;
    }


    /**
     * Gets the recAcctCur value for this SvcActInfo_Type.
     * 
     * @return recAcctCur   * Валюта счета получателя
     */
    public java.lang.String getRecAcctCur() {
        return recAcctCur;
    }


    /**
     * Sets the recAcctCur value for this SvcActInfo_Type.
     * 
     * @param recAcctCur   * Валюта счета получателя
     */
    public void setRecAcctCur(java.lang.String recAcctCur) {
        this.recAcctCur = recAcctCur;
    }


    /**
     * Gets the recINN value for this SvcActInfo_Type.
     * 
     * @return recINN   * ИНН получателя
     */
    public java.lang.String getRecINN() {
        return recINN;
    }


    /**
     * Sets the recINN value for this SvcActInfo_Type.
     * 
     * @param recINN   * ИНН получателя
     */
    public void setRecINN(java.lang.String recINN) {
        this.recINN = recINN;
    }


    /**
     * Gets the KPPTo value for this SvcActInfo_Type.
     * 
     * @return KPPTo   * КПП получателя
     */
    public java.lang.String getKPPTo() {
        return KPPTo;
    }


    /**
     * Sets the KPPTo value for this SvcActInfo_Type.
     * 
     * @param KPPTo   * КПП получателя
     */
    public void setKPPTo(java.lang.String KPPTo) {
        this.KPPTo = KPPTo;
    }


    /**
     * Gets the purpose value for this SvcActInfo_Type.
     * 
     * @return purpose   * Назначение платежа
     */
    public java.lang.String getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this SvcActInfo_Type.
     * 
     * @param purpose   * Назначение платежа
     */
    public void setPurpose(java.lang.String purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the recipientName value for this SvcActInfo_Type.
     * 
     * @return recipientName   * Наименование получателя юр. Лица, либо ФИО физ.лица
     */
    public java.lang.String getRecipientName() {
        return recipientName;
    }


    /**
     * Sets the recipientName value for this SvcActInfo_Type.
     * 
     * @param recipientName   * Наименование получателя юр. Лица, либо ФИО физ.лица
     */
    public void setRecipientName(java.lang.String recipientName) {
        this.recipientName = recipientName;
    }


    /**
     * Gets the pmtKind value for this SvcActInfo_Type.
     * 
     * @return pmtKind   * Тип операции
     */
    public java.lang.String getPmtKind() {
        return pmtKind;
    }


    /**
     * Sets the pmtKind value for this SvcActInfo_Type.
     * 
     * @param pmtKind   * Тип операции
     */
    public void setPmtKind(java.lang.String pmtKind) {
        this.pmtKind = pmtKind;
    }


    /**
     * Gets the regular value for this SvcActInfo_Type.
     * 
     * @return regular
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Regular_Type getRegular() {
        return regular;
    }


    /**
     * Sets the regular value for this SvcActInfo_Type.
     * 
     * @param regular
     */
    public void setRegular(com.rssl.phizic.test.webgate.esberib.generated.Regular_Type regular) {
        this.regular = regular;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SvcActInfo_Type)) return false;
        SvcActInfo_Type other = (SvcActInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.svcAct==null && other.getSvcAct()==null) || 
             (this.svcAct!=null &&
              this.svcAct.equals(other.getSvcAct()))) &&
            ((this.depAcctIdFrom==null && other.getDepAcctIdFrom()==null) || 
             (this.depAcctIdFrom!=null &&
              this.depAcctIdFrom.equals(other.getDepAcctIdFrom()))) &&
            ((this.cardAcctIdFrom==null && other.getCardAcctIdFrom()==null) || 
             (this.cardAcctIdFrom!=null &&
              this.cardAcctIdFrom.equals(other.getCardAcctIdFrom()))) &&
            ((this.recBIC==null && other.getRecBIC()==null) || 
             (this.recBIC!=null &&
              this.recBIC.equals(other.getRecBIC()))) &&
            ((this.recCorrAccount==null && other.getRecCorrAccount()==null) || 
             (this.recCorrAccount!=null &&
              this.recCorrAccount.equals(other.getRecCorrAccount()))) &&
            ((this.recCalcAccount==null && other.getRecCalcAccount()==null) || 
             (this.recCalcAccount!=null &&
              this.recCalcAccount.equals(other.getRecCalcAccount()))) &&
            ((this.recAcctCur==null && other.getRecAcctCur()==null) || 
             (this.recAcctCur!=null &&
              this.recAcctCur.equals(other.getRecAcctCur()))) &&
            ((this.recINN==null && other.getRecINN()==null) || 
             (this.recINN!=null &&
              this.recINN.equals(other.getRecINN()))) &&
            ((this.KPPTo==null && other.getKPPTo()==null) || 
             (this.KPPTo!=null &&
              this.KPPTo.equals(other.getKPPTo()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.recipientName==null && other.getRecipientName()==null) || 
             (this.recipientName!=null &&
              this.recipientName.equals(other.getRecipientName()))) &&
            ((this.pmtKind==null && other.getPmtKind()==null) || 
             (this.pmtKind!=null &&
              this.pmtKind.equals(other.getPmtKind()))) &&
            ((this.regular==null && other.getRegular()==null) || 
             (this.regular!=null &&
              this.regular.equals(other.getRegular())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSvcAct() != null) {
            _hashCode += getSvcAct().hashCode();
        }
        if (getDepAcctIdFrom() != null) {
            _hashCode += getDepAcctIdFrom().hashCode();
        }
        if (getCardAcctIdFrom() != null) {
            _hashCode += getCardAcctIdFrom().hashCode();
        }
        if (getRecBIC() != null) {
            _hashCode += getRecBIC().hashCode();
        }
        if (getRecCorrAccount() != null) {
            _hashCode += getRecCorrAccount().hashCode();
        }
        if (getRecCalcAccount() != null) {
            _hashCode += getRecCalcAccount().hashCode();
        }
        if (getRecAcctCur() != null) {
            _hashCode += getRecAcctCur().hashCode();
        }
        if (getRecINN() != null) {
            _hashCode += getRecINN().hashCode();
        }
        if (getKPPTo() != null) {
            _hashCode += getKPPTo().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getRecipientName() != null) {
            _hashCode += getRecipientName().hashCode();
        }
        if (getPmtKind() != null) {
            _hashCode += getPmtKind().hashCode();
        }
        if (getRegular() != null) {
            _hashCode += getRegular().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SvcActInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcActInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("svcAct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcActInfo_Type>SvcAct"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctIdFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctIdFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctIdFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctIdFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recBIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecBIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recCorrAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecCorrAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recCalcAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecCalcAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recAcctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecAcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recINN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecINN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KPPTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "KPPTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("recipientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pmtKind");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PmtKind"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regular");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Regular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Regular_Type"));
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
