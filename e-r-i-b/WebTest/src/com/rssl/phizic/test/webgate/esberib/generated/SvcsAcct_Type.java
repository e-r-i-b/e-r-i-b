/**
 * SvcsAcct_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Список длительных поручений
 */
public class SvcsAcct_Type  implements java.io.Serializable {
    private java.lang.String systemId;

    /* Идентификатор длительного поручения */
    private com.rssl.phizic.test.webgate.esberib.generated.SvcsAcct_TypeSvcAcctId svcAcctId;

    /* Дата начала действия длительного поручения */
    private java.lang.String dtStart;

    /* Дата окончания действия длительного поручения */
    private java.lang.String dtEnd;

    /* Сумма длительного поручения */
    private java.math.BigDecimal curAmt;

    /* Номер карты, с которой выполнится списание */
    private java.lang.String cardNum;

    /* Номер счета, с которого выполнится списание */
    private java.lang.String acctId;

    /* Код вида платежа */
    private java.lang.String pmtKind;

    /* Код вида получателя */
    private java.lang.String rcptKind;

    public SvcsAcct_Type() {
    }

    public SvcsAcct_Type(
           java.lang.String systemId,
           com.rssl.phizic.test.webgate.esberib.generated.SvcsAcct_TypeSvcAcctId svcAcctId,
           java.lang.String dtStart,
           java.lang.String dtEnd,
           java.math.BigDecimal curAmt,
           java.lang.String cardNum,
           java.lang.String acctId,
           java.lang.String pmtKind,
           java.lang.String rcptKind) {
           this.systemId = systemId;
           this.svcAcctId = svcAcctId;
           this.dtStart = dtStart;
           this.dtEnd = dtEnd;
           this.curAmt = curAmt;
           this.cardNum = cardNum;
           this.acctId = acctId;
           this.pmtKind = pmtKind;
           this.rcptKind = rcptKind;
    }


    /**
     * Gets the systemId value for this SvcsAcct_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this SvcsAcct_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the svcAcctId value for this SvcsAcct_Type.
     * 
     * @return svcAcctId   * Идентификатор длительного поручения
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SvcsAcct_TypeSvcAcctId getSvcAcctId() {
        return svcAcctId;
    }


    /**
     * Sets the svcAcctId value for this SvcsAcct_Type.
     * 
     * @param svcAcctId   * Идентификатор длительного поручения
     */
    public void setSvcAcctId(com.rssl.phizic.test.webgate.esberib.generated.SvcsAcct_TypeSvcAcctId svcAcctId) {
        this.svcAcctId = svcAcctId;
    }


    /**
     * Gets the dtStart value for this SvcsAcct_Type.
     * 
     * @return dtStart   * Дата начала действия длительного поручения
     */
    public java.lang.String getDtStart() {
        return dtStart;
    }


    /**
     * Sets the dtStart value for this SvcsAcct_Type.
     * 
     * @param dtStart   * Дата начала действия длительного поручения
     */
    public void setDtStart(java.lang.String dtStart) {
        this.dtStart = dtStart;
    }


    /**
     * Gets the dtEnd value for this SvcsAcct_Type.
     * 
     * @return dtEnd   * Дата окончания действия длительного поручения
     */
    public java.lang.String getDtEnd() {
        return dtEnd;
    }


    /**
     * Sets the dtEnd value for this SvcsAcct_Type.
     * 
     * @param dtEnd   * Дата окончания действия длительного поручения
     */
    public void setDtEnd(java.lang.String dtEnd) {
        this.dtEnd = dtEnd;
    }


    /**
     * Gets the curAmt value for this SvcsAcct_Type.
     * 
     * @return curAmt   * Сумма длительного поручения
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this SvcsAcct_Type.
     * 
     * @param curAmt   * Сумма длительного поручения
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the cardNum value for this SvcsAcct_Type.
     * 
     * @return cardNum   * Номер карты, с которой выполнится списание
     */
    public java.lang.String getCardNum() {
        return cardNum;
    }


    /**
     * Sets the cardNum value for this SvcsAcct_Type.
     * 
     * @param cardNum   * Номер карты, с которой выполнится списание
     */
    public void setCardNum(java.lang.String cardNum) {
        this.cardNum = cardNum;
    }


    /**
     * Gets the acctId value for this SvcsAcct_Type.
     * 
     * @return acctId   * Номер счета, с которого выполнится списание
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this SvcsAcct_Type.
     * 
     * @param acctId   * Номер счета, с которого выполнится списание
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the pmtKind value for this SvcsAcct_Type.
     * 
     * @return pmtKind   * Код вида платежа
     */
    public java.lang.String getPmtKind() {
        return pmtKind;
    }


    /**
     * Sets the pmtKind value for this SvcsAcct_Type.
     * 
     * @param pmtKind   * Код вида платежа
     */
    public void setPmtKind(java.lang.String pmtKind) {
        this.pmtKind = pmtKind;
    }


    /**
     * Gets the rcptKind value for this SvcsAcct_Type.
     * 
     * @return rcptKind   * Код вида получателя
     */
    public java.lang.String getRcptKind() {
        return rcptKind;
    }


    /**
     * Sets the rcptKind value for this SvcsAcct_Type.
     * 
     * @param rcptKind   * Код вида получателя
     */
    public void setRcptKind(java.lang.String rcptKind) {
        this.rcptKind = rcptKind;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SvcsAcct_Type)) return false;
        SvcsAcct_Type other = (SvcsAcct_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.svcAcctId==null && other.getSvcAcctId()==null) || 
             (this.svcAcctId!=null &&
              this.svcAcctId.equals(other.getSvcAcctId()))) &&
            ((this.dtStart==null && other.getDtStart()==null) || 
             (this.dtStart!=null &&
              this.dtStart.equals(other.getDtStart()))) &&
            ((this.dtEnd==null && other.getDtEnd()==null) || 
             (this.dtEnd!=null &&
              this.dtEnd.equals(other.getDtEnd()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.cardNum==null && other.getCardNum()==null) || 
             (this.cardNum!=null &&
              this.cardNum.equals(other.getCardNum()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.pmtKind==null && other.getPmtKind()==null) || 
             (this.pmtKind!=null &&
              this.pmtKind.equals(other.getPmtKind()))) &&
            ((this.rcptKind==null && other.getRcptKind()==null) || 
             (this.rcptKind!=null &&
              this.rcptKind.equals(other.getRcptKind())));
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
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getSvcAcctId() != null) {
            _hashCode += getSvcAcctId().hashCode();
        }
        if (getDtStart() != null) {
            _hashCode += getDtStart().hashCode();
        }
        if (getDtEnd() != null) {
            _hashCode += getDtEnd().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getCardNum() != null) {
            _hashCode += getCardNum().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getPmtKind() != null) {
            _hashCode += getPmtKind().hashCode();
        }
        if (getRcptKind() != null) {
            _hashCode += getRcptKind().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SvcsAcct_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcsAcct_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("svcAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcsAcct_Type>SvcAcctId"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtStart");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DtStart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtEnd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DtEnd"));
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
        elemField.setFieldName("cardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNumType"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("pmtKind");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PmtKind"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rcptKind");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RcptKind"));
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
