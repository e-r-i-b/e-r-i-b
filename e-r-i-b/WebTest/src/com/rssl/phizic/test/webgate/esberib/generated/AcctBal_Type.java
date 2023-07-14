/**
 * AcctBal_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Остаток.Предопределенные значения для BalType: Avail -остаток по
 * счету(доступный расходный лимит по карте), AvailCash - доступный лимит
 * для получения наличных, AvailPmt - доступный лимит для оплаты товаров/услуг,
 * CashAvail - макс.сумма списания без нарушения условий договора, Outstanding
 * - сумма задолженности, Pmt - величина ближайшего платежа,MinAvail
 * - величина неснижаемого остатка, ClearBalance - сумма остатка по счету
 * без капитализации.Если BalType пустой- остаток после операции
 */
public class AcctBal_Type  implements java.io.Serializable {
    private java.lang.String balType;

    private java.lang.String balName;

    private java.math.BigDecimal curAmt;

    private java.math.BigDecimal nextAmt;

    private java.lang.String acctCur;

    private java.lang.Long priority;

    private java.lang.String acctCount;

    private java.lang.String effDt;

    public AcctBal_Type() {
    }

    public AcctBal_Type(
           java.lang.String balType,
           java.lang.String balName,
           java.math.BigDecimal curAmt,
           java.math.BigDecimal nextAmt,
           java.lang.String acctCur,
           java.lang.Long priority,
           java.lang.String acctCount,
           java.lang.String effDt) {
           this.balType = balType;
           this.balName = balName;
           this.curAmt = curAmt;
           this.nextAmt = nextAmt;
           this.acctCur = acctCur;
           this.priority = priority;
           this.acctCount = acctCount;
           this.effDt = effDt;
    }


    /**
     * Gets the balType value for this AcctBal_Type.
     * 
     * @return balType
     */
    public java.lang.String getBalType() {
        return balType;
    }


    /**
     * Sets the balType value for this AcctBal_Type.
     * 
     * @param balType
     */
    public void setBalType(java.lang.String balType) {
        this.balType = balType;
    }


    /**
     * Gets the balName value for this AcctBal_Type.
     * 
     * @return balName
     */
    public java.lang.String getBalName() {
        return balName;
    }


    /**
     * Sets the balName value for this AcctBal_Type.
     * 
     * @param balName
     */
    public void setBalName(java.lang.String balName) {
        this.balName = balName;
    }


    /**
     * Gets the curAmt value for this AcctBal_Type.
     * 
     * @return curAmt
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this AcctBal_Type.
     * 
     * @param curAmt
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the nextAmt value for this AcctBal_Type.
     * 
     * @return nextAmt
     */
    public java.math.BigDecimal getNextAmt() {
        return nextAmt;
    }


    /**
     * Sets the nextAmt value for this AcctBal_Type.
     * 
     * @param nextAmt
     */
    public void setNextAmt(java.math.BigDecimal nextAmt) {
        this.nextAmt = nextAmt;
    }


    /**
     * Gets the acctCur value for this AcctBal_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this AcctBal_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the priority value for this AcctBal_Type.
     * 
     * @return priority
     */
    public java.lang.Long getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this AcctBal_Type.
     * 
     * @param priority
     */
    public void setPriority(java.lang.Long priority) {
        this.priority = priority;
    }


    /**
     * Gets the acctCount value for this AcctBal_Type.
     * 
     * @return acctCount
     */
    public java.lang.String getAcctCount() {
        return acctCount;
    }


    /**
     * Sets the acctCount value for this AcctBal_Type.
     * 
     * @param acctCount
     */
    public void setAcctCount(java.lang.String acctCount) {
        this.acctCount = acctCount;
    }


    /**
     * Gets the effDt value for this AcctBal_Type.
     * 
     * @return effDt
     */
    public java.lang.String getEffDt() {
        return effDt;
    }


    /**
     * Sets the effDt value for this AcctBal_Type.
     * 
     * @param effDt
     */
    public void setEffDt(java.lang.String effDt) {
        this.effDt = effDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AcctBal_Type)) return false;
        AcctBal_Type other = (AcctBal_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.balType==null && other.getBalType()==null) || 
             (this.balType!=null &&
              this.balType.equals(other.getBalType()))) &&
            ((this.balName==null && other.getBalName()==null) || 
             (this.balName!=null &&
              this.balName.equals(other.getBalName()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.nextAmt==null && other.getNextAmt()==null) || 
             (this.nextAmt!=null &&
              this.nextAmt.equals(other.getNextAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
            ((this.acctCount==null && other.getAcctCount()==null) || 
             (this.acctCount!=null &&
              this.acctCount.equals(other.getAcctCount()))) &&
            ((this.effDt==null && other.getEffDt()==null) || 
             (this.effDt!=null &&
              this.effDt.equals(other.getEffDt())));
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
        if (getBalType() != null) {
            _hashCode += getBalType().hashCode();
        }
        if (getBalName() != null) {
            _hashCode += getBalName().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getNextAmt() != null) {
            _hashCode += getNextAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getAcctCount() != null) {
            _hashCode += getAcctCount().hashCode();
        }
        if (getEffDt() != null) {
            _hashCode += getEffDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AcctBal_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nextAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
