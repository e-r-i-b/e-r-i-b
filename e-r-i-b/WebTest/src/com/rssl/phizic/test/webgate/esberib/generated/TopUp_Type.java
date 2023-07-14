/**
 * TopUp_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Блок данных по TopUp кредитам
 */
public class TopUp_Type  implements java.io.Serializable {
    /* Количество блоков погашаемых договоров */
    private int topUpLoanListCount;

    /* Сумма полного досрочного погашения */
    private java.math.BigDecimal totalRepaymentSum;

    /* Погашаемый договор */
    private com.rssl.phizic.test.webgate.esberib.generated.RepayLoan_Type[] repayLoan;

    public TopUp_Type() {
    }

    public TopUp_Type(
           int topUpLoanListCount,
           java.math.BigDecimal totalRepaymentSum,
           com.rssl.phizic.test.webgate.esberib.generated.RepayLoan_Type[] repayLoan) {
           this.topUpLoanListCount = topUpLoanListCount;
           this.totalRepaymentSum = totalRepaymentSum;
           this.repayLoan = repayLoan;
    }


    /**
     * Gets the topUpLoanListCount value for this TopUp_Type.
     * 
     * @return topUpLoanListCount   * Количество блоков погашаемых договоров
     */
    public int getTopUpLoanListCount() {
        return topUpLoanListCount;
    }


    /**
     * Sets the topUpLoanListCount value for this TopUp_Type.
     * 
     * @param topUpLoanListCount   * Количество блоков погашаемых договоров
     */
    public void setTopUpLoanListCount(int topUpLoanListCount) {
        this.topUpLoanListCount = topUpLoanListCount;
    }


    /**
     * Gets the totalRepaymentSum value for this TopUp_Type.
     * 
     * @return totalRepaymentSum   * Сумма полного досрочного погашения
     */
    public java.math.BigDecimal getTotalRepaymentSum() {
        return totalRepaymentSum;
    }


    /**
     * Sets the totalRepaymentSum value for this TopUp_Type.
     * 
     * @param totalRepaymentSum   * Сумма полного досрочного погашения
     */
    public void setTotalRepaymentSum(java.math.BigDecimal totalRepaymentSum) {
        this.totalRepaymentSum = totalRepaymentSum;
    }


    /**
     * Gets the repayLoan value for this TopUp_Type.
     * 
     * @return repayLoan   * Погашаемый договор
     */
    public com.rssl.phizic.test.webgate.esberib.generated.RepayLoan_Type[] getRepayLoan() {
        return repayLoan;
    }


    /**
     * Sets the repayLoan value for this TopUp_Type.
     * 
     * @param repayLoan   * Погашаемый договор
     */
    public void setRepayLoan(com.rssl.phizic.test.webgate.esberib.generated.RepayLoan_Type[] repayLoan) {
        this.repayLoan = repayLoan;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.RepayLoan_Type getRepayLoan(int i) {
        return this.repayLoan[i];
    }

    public void setRepayLoan(int i, com.rssl.phizic.test.webgate.esberib.generated.RepayLoan_Type _value) {
        this.repayLoan[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopUp_Type)) return false;
        TopUp_Type other = (TopUp_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.topUpLoanListCount == other.getTopUpLoanListCount() &&
            ((this.totalRepaymentSum==null && other.getTotalRepaymentSum()==null) || 
             (this.totalRepaymentSum!=null &&
              this.totalRepaymentSum.equals(other.getTotalRepaymentSum()))) &&
            ((this.repayLoan==null && other.getRepayLoan()==null) || 
             (this.repayLoan!=null &&
              java.util.Arrays.equals(this.repayLoan, other.getRepayLoan())));
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
        _hashCode += getTopUpLoanListCount();
        if (getTotalRepaymentSum() != null) {
            _hashCode += getTotalRepaymentSum().hashCode();
        }
        if (getRepayLoan() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRepayLoan());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRepayLoan(), i);
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
        new org.apache.axis.description.TypeDesc(TopUp_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TopUp_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topUpLoanListCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TopUpLoanListCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRepaymentSum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TotalRepaymentSum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repayLoan");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepayLoan"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepayLoan_Type"));
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
