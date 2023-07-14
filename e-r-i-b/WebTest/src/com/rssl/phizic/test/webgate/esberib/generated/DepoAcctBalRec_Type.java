/**
 * DepoAcctBalRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Разбивка задолженности ДЕПО
 */
public class DepoAcctBalRec_Type  implements java.io.Serializable {
    /* Номер выставленного счета */
    private java.lang.String recNumber;

    /* Дата выставления счета */
    private java.lang.String effDt;

    /* Сумма без ндс */
    private java.math.BigDecimal curAmt;

    /* Сумма ндс */
    private java.math.BigDecimal curAmtNDS;

    private java.lang.String acctCur;

    /* Начало периода, за который выставлен счет */
    private java.lang.String strDt;

    /* Конец периода, за который выставлен счет */
    private java.lang.String endDt;

    public DepoAcctBalRec_Type() {
    }

    public DepoAcctBalRec_Type(
           java.lang.String recNumber,
           java.lang.String effDt,
           java.math.BigDecimal curAmt,
           java.math.BigDecimal curAmtNDS,
           java.lang.String acctCur,
           java.lang.String strDt,
           java.lang.String endDt) {
           this.recNumber = recNumber;
           this.effDt = effDt;
           this.curAmt = curAmt;
           this.curAmtNDS = curAmtNDS;
           this.acctCur = acctCur;
           this.strDt = strDt;
           this.endDt = endDt;
    }


    /**
     * Gets the recNumber value for this DepoAcctBalRec_Type.
     * 
     * @return recNumber   * Номер выставленного счета
     */
    public java.lang.String getRecNumber() {
        return recNumber;
    }


    /**
     * Sets the recNumber value for this DepoAcctBalRec_Type.
     * 
     * @param recNumber   * Номер выставленного счета
     */
    public void setRecNumber(java.lang.String recNumber) {
        this.recNumber = recNumber;
    }


    /**
     * Gets the effDt value for this DepoAcctBalRec_Type.
     * 
     * @return effDt   * Дата выставления счета
     */
    public java.lang.String getEffDt() {
        return effDt;
    }


    /**
     * Sets the effDt value for this DepoAcctBalRec_Type.
     * 
     * @param effDt   * Дата выставления счета
     */
    public void setEffDt(java.lang.String effDt) {
        this.effDt = effDt;
    }


    /**
     * Gets the curAmt value for this DepoAcctBalRec_Type.
     * 
     * @return curAmt   * Сумма без ндс
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this DepoAcctBalRec_Type.
     * 
     * @param curAmt   * Сумма без ндс
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the curAmtNDS value for this DepoAcctBalRec_Type.
     * 
     * @return curAmtNDS   * Сумма ндс
     */
    public java.math.BigDecimal getCurAmtNDS() {
        return curAmtNDS;
    }


    /**
     * Sets the curAmtNDS value for this DepoAcctBalRec_Type.
     * 
     * @param curAmtNDS   * Сумма ндс
     */
    public void setCurAmtNDS(java.math.BigDecimal curAmtNDS) {
        this.curAmtNDS = curAmtNDS;
    }


    /**
     * Gets the acctCur value for this DepoAcctBalRec_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this DepoAcctBalRec_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the strDt value for this DepoAcctBalRec_Type.
     * 
     * @return strDt   * Начало периода, за который выставлен счет
     */
    public java.lang.String getStrDt() {
        return strDt;
    }


    /**
     * Sets the strDt value for this DepoAcctBalRec_Type.
     * 
     * @param strDt   * Начало периода, за который выставлен счет
     */
    public void setStrDt(java.lang.String strDt) {
        this.strDt = strDt;
    }


    /**
     * Gets the endDt value for this DepoAcctBalRec_Type.
     * 
     * @return endDt   * Конец периода, за который выставлен счет
     */
    public java.lang.String getEndDt() {
        return endDt;
    }


    /**
     * Sets the endDt value for this DepoAcctBalRec_Type.
     * 
     * @param endDt   * Конец периода, за который выставлен счет
     */
    public void setEndDt(java.lang.String endDt) {
        this.endDt = endDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAcctBalRec_Type)) return false;
        DepoAcctBalRec_Type other = (DepoAcctBalRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recNumber==null && other.getRecNumber()==null) || 
             (this.recNumber!=null &&
              this.recNumber.equals(other.getRecNumber()))) &&
            ((this.effDt==null && other.getEffDt()==null) || 
             (this.effDt!=null &&
              this.effDt.equals(other.getEffDt()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.curAmtNDS==null && other.getCurAmtNDS()==null) || 
             (this.curAmtNDS!=null &&
              this.curAmtNDS.equals(other.getCurAmtNDS()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.strDt==null && other.getStrDt()==null) || 
             (this.strDt!=null &&
              this.strDt.equals(other.getStrDt()))) &&
            ((this.endDt==null && other.getEndDt()==null) || 
             (this.endDt!=null &&
              this.endDt.equals(other.getEndDt())));
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
        if (getRecNumber() != null) {
            _hashCode += getRecNumber().hashCode();
        }
        if (getEffDt() != null) {
            _hashCode += getEffDt().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getCurAmtNDS() != null) {
            _hashCode += getCurAmtNDS().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getStrDt() != null) {
            _hashCode += getStrDt().hashCode();
        }
        if (getEndDt() != null) {
            _hashCode += getEndDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAcctBalRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctBalRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmtNDS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtNDS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StrDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
