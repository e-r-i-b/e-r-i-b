/**
 * IMAOperConvInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Информация о конверсии при операциях с ОМС
 */
public class IMAOperConvInfo_Type  implements java.io.Serializable {
    /* Признак: операция с металлом или денежными средствами */
    private com.rssl.phizicgate.esberibgate.ws.generated.IMAOperCurType_Type curType;

    /* Сумма операции в рублях */
    private java.math.BigDecimal curAmt;

    /* Валюта */
    private java.lang.String acctCur;

    public IMAOperConvInfo_Type() {
    }

    public IMAOperConvInfo_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.IMAOperCurType_Type curType,
           java.math.BigDecimal curAmt,
           java.lang.String acctCur) {
           this.curType = curType;
           this.curAmt = curAmt;
           this.acctCur = acctCur;
    }


    /**
     * Gets the curType value for this IMAOperConvInfo_Type.
     * 
     * @return curType   * Признак: операция с металлом или денежными средствами
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.IMAOperCurType_Type getCurType() {
        return curType;
    }


    /**
     * Sets the curType value for this IMAOperConvInfo_Type.
     * 
     * @param curType   * Признак: операция с металлом или денежными средствами
     */
    public void setCurType(com.rssl.phizicgate.esberibgate.ws.generated.IMAOperCurType_Type curType) {
        this.curType = curType;
    }


    /**
     * Gets the curAmt value for this IMAOperConvInfo_Type.
     * 
     * @return curAmt   * Сумма операции в рублях
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this IMAOperConvInfo_Type.
     * 
     * @param curAmt   * Сумма операции в рублях
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the acctCur value for this IMAOperConvInfo_Type.
     * 
     * @return acctCur   * Валюта
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this IMAOperConvInfo_Type.
     * 
     * @param acctCur   * Валюта
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IMAOperConvInfo_Type)) return false;
        IMAOperConvInfo_Type other = (IMAOperConvInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.curType==null && other.getCurType()==null) || 
             (this.curType!=null &&
              this.curType.equals(other.getCurType()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur())));
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
        if (getCurType() != null) {
            _hashCode += getCurType().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IMAOperConvInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAOperConvInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAOperCurType_Type"));
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
