/**
 * XferIMAInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация об операции со счетом ОМС
 */
public class XferIMAInfo_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom;

    /* Идентификатор счета ОМС (для операции TIC) */
    private com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type IMAAcctIdFrom;

    private com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo;

    /* Идентификатор счета ОМС (для операции TCI) */
    private com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type IMAAcctIdTo;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo;

    /* Назначение платежа */
    private java.lang.String purpose;

    /* Сумма операции (в граммах) */
    private java.math.BigDecimal curAmt;

    private java.lang.String acctCur;

    private com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type curAmtConv;

    /* Признак проведения операции. Для операций DepToNewIMAAdd, CardToNewIMAAdd,
     * CardToIMAAdd, IMAToCardAdd передается фиксированное значение «true». */
    private boolean execute;

    /* Разбивка микроопераций списания */
    private com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo;

    public XferIMAInfo_Type() {
    }

    public XferIMAInfo_Type(
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom,
           com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type IMAAcctIdFrom,
           com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo,
           com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type IMAAcctIdTo,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo,
           java.lang.String purpose,
           java.math.BigDecimal curAmt,
           java.lang.String acctCur,
           com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type curAmtConv,
           boolean execute,
           com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo) {
           this.depAcctIdFrom = depAcctIdFrom;
           this.cardAcctIdFrom = cardAcctIdFrom;
           this.IMAAcctIdFrom = IMAAcctIdFrom;
           this.agreemtInfo = agreemtInfo;
           this.IMAAcctIdTo = IMAAcctIdTo;
           this.cardAcctIdTo = cardAcctIdTo;
           this.purpose = purpose;
           this.curAmt = curAmt;
           this.acctCur = acctCur;
           this.curAmtConv = curAmtConv;
           this.execute = execute;
           this.srcLayoutInfo = srcLayoutInfo;
    }


    /**
     * Gets the depAcctIdFrom value for this XferIMAInfo_Type.
     * 
     * @return depAcctIdFrom
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctIdFrom() {
        return depAcctIdFrom;
    }


    /**
     * Sets the depAcctIdFrom value for this XferIMAInfo_Type.
     * 
     * @param depAcctIdFrom
     */
    public void setDepAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom) {
        this.depAcctIdFrom = depAcctIdFrom;
    }


    /**
     * Gets the cardAcctIdFrom value for this XferIMAInfo_Type.
     * 
     * @return cardAcctIdFrom
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdFrom() {
        return cardAcctIdFrom;
    }


    /**
     * Sets the cardAcctIdFrom value for this XferIMAInfo_Type.
     * 
     * @param cardAcctIdFrom
     */
    public void setCardAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom) {
        this.cardAcctIdFrom = cardAcctIdFrom;
    }


    /**
     * Gets the IMAAcctIdFrom value for this XferIMAInfo_Type.
     * 
     * @return IMAAcctIdFrom   * Идентификатор счета ОМС (для операции TIC)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type getIMAAcctIdFrom() {
        return IMAAcctIdFrom;
    }


    /**
     * Sets the IMAAcctIdFrom value for this XferIMAInfo_Type.
     * 
     * @param IMAAcctIdFrom   * Идентификатор счета ОМС (для операции TIC)
     */
    public void setIMAAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type IMAAcctIdFrom) {
        this.IMAAcctIdFrom = IMAAcctIdFrom;
    }


    /**
     * Gets the agreemtInfo value for this XferIMAInfo_Type.
     * 
     * @return agreemtInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type getAgreemtInfo() {
        return agreemtInfo;
    }


    /**
     * Sets the agreemtInfo value for this XferIMAInfo_Type.
     * 
     * @param agreemtInfo
     */
    public void setAgreemtInfo(com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo) {
        this.agreemtInfo = agreemtInfo;
    }


    /**
     * Gets the IMAAcctIdTo value for this XferIMAInfo_Type.
     * 
     * @return IMAAcctIdTo   * Идентификатор счета ОМС (для операции TCI)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type getIMAAcctIdTo() {
        return IMAAcctIdTo;
    }


    /**
     * Sets the IMAAcctIdTo value for this XferIMAInfo_Type.
     * 
     * @param IMAAcctIdTo   * Идентификатор счета ОМС (для операции TCI)
     */
    public void setIMAAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type IMAAcctIdTo) {
        this.IMAAcctIdTo = IMAAcctIdTo;
    }


    /**
     * Gets the cardAcctIdTo value for this XferIMAInfo_Type.
     * 
     * @return cardAcctIdTo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdTo() {
        return cardAcctIdTo;
    }


    /**
     * Sets the cardAcctIdTo value for this XferIMAInfo_Type.
     * 
     * @param cardAcctIdTo
     */
    public void setCardAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo) {
        this.cardAcctIdTo = cardAcctIdTo;
    }


    /**
     * Gets the purpose value for this XferIMAInfo_Type.
     * 
     * @return purpose   * Назначение платежа
     */
    public java.lang.String getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this XferIMAInfo_Type.
     * 
     * @param purpose   * Назначение платежа
     */
    public void setPurpose(java.lang.String purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the curAmt value for this XferIMAInfo_Type.
     * 
     * @return curAmt   * Сумма операции (в граммах)
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this XferIMAInfo_Type.
     * 
     * @param curAmt   * Сумма операции (в граммах)
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the acctCur value for this XferIMAInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this XferIMAInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the curAmtConv value for this XferIMAInfo_Type.
     * 
     * @return curAmtConv
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type getCurAmtConv() {
        return curAmtConv;
    }


    /**
     * Sets the curAmtConv value for this XferIMAInfo_Type.
     * 
     * @param curAmtConv
     */
    public void setCurAmtConv(com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type curAmtConv) {
        this.curAmtConv = curAmtConv;
    }


    /**
     * Gets the execute value for this XferIMAInfo_Type.
     * 
     * @return execute   * Признак проведения операции. Для операций DepToNewIMAAdd, CardToNewIMAAdd,
     * CardToIMAAdd, IMAToCardAdd передается фиксированное значение «true».
     */
    public boolean isExecute() {
        return execute;
    }


    /**
     * Sets the execute value for this XferIMAInfo_Type.
     * 
     * @param execute   * Признак проведения операции. Для операций DepToNewIMAAdd, CardToNewIMAAdd,
     * CardToIMAAdd, IMAToCardAdd передается фиксированное значение «true».
     */
    public void setExecute(boolean execute) {
        this.execute = execute;
    }


    /**
     * Gets the srcLayoutInfo value for this XferIMAInfo_Type.
     * 
     * @return srcLayoutInfo   * Разбивка микроопераций списания
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type getSrcLayoutInfo() {
        return srcLayoutInfo;
    }


    /**
     * Sets the srcLayoutInfo value for this XferIMAInfo_Type.
     * 
     * @param srcLayoutInfo   * Разбивка микроопераций списания
     */
    public void setSrcLayoutInfo(com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo) {
        this.srcLayoutInfo = srcLayoutInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XferIMAInfo_Type)) return false;
        XferIMAInfo_Type other = (XferIMAInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depAcctIdFrom==null && other.getDepAcctIdFrom()==null) || 
             (this.depAcctIdFrom!=null &&
              this.depAcctIdFrom.equals(other.getDepAcctIdFrom()))) &&
            ((this.cardAcctIdFrom==null && other.getCardAcctIdFrom()==null) || 
             (this.cardAcctIdFrom!=null &&
              this.cardAcctIdFrom.equals(other.getCardAcctIdFrom()))) &&
            ((this.IMAAcctIdFrom==null && other.getIMAAcctIdFrom()==null) || 
             (this.IMAAcctIdFrom!=null &&
              this.IMAAcctIdFrom.equals(other.getIMAAcctIdFrom()))) &&
            ((this.agreemtInfo==null && other.getAgreemtInfo()==null) || 
             (this.agreemtInfo!=null &&
              this.agreemtInfo.equals(other.getAgreemtInfo()))) &&
            ((this.IMAAcctIdTo==null && other.getIMAAcctIdTo()==null) || 
             (this.IMAAcctIdTo!=null &&
              this.IMAAcctIdTo.equals(other.getIMAAcctIdTo()))) &&
            ((this.cardAcctIdTo==null && other.getCardAcctIdTo()==null) || 
             (this.cardAcctIdTo!=null &&
              this.cardAcctIdTo.equals(other.getCardAcctIdTo()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.curAmtConv==null && other.getCurAmtConv()==null) || 
             (this.curAmtConv!=null &&
              this.curAmtConv.equals(other.getCurAmtConv()))) &&
            this.execute == other.isExecute() &&
            ((this.srcLayoutInfo==null && other.getSrcLayoutInfo()==null) || 
             (this.srcLayoutInfo!=null &&
              this.srcLayoutInfo.equals(other.getSrcLayoutInfo())));
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
        if (getDepAcctIdFrom() != null) {
            _hashCode += getDepAcctIdFrom().hashCode();
        }
        if (getCardAcctIdFrom() != null) {
            _hashCode += getCardAcctIdFrom().hashCode();
        }
        if (getIMAAcctIdFrom() != null) {
            _hashCode += getIMAAcctIdFrom().hashCode();
        }
        if (getAgreemtInfo() != null) {
            _hashCode += getAgreemtInfo().hashCode();
        }
        if (getIMAAcctIdTo() != null) {
            _hashCode += getIMAAcctIdTo().hashCode();
        }
        if (getCardAcctIdTo() != null) {
            _hashCode += getCardAcctIdTo().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getCurAmtConv() != null) {
            _hashCode += getCurAmtConv().hashCode();
        }
        _hashCode += (isExecute() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSrcLayoutInfo() != null) {
            _hashCode += getSrcLayoutInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XferIMAInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferIMAInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("IMAAcctIdFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAAcctIdFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IMAAcctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAAcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAAcctId_Type"));
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
        elemField.setFieldName("purpose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Purpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmtConv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtConv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtConv_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("execute");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Execute"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcLayoutInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo_Type"));
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
