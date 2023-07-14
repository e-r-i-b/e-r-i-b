/**
 * CCAcctExtStmtInqRs_TypeCardAcctRec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class CCAcctExtStmtInqRs_TypeCardAcctRec  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal;

    private com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_Type[] CCAcctStmtRec;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public CCAcctExtStmtInqRs_TypeCardAcctRec() {
    }

    public CCAcctExtStmtInqRs_TypeCardAcctRec(
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal,
           com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_Type[] CCAcctStmtRec,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.cardAcctId = cardAcctId;
           this.acctBal = acctBal;
           this.CCAcctStmtRec = CCAcctStmtRec;
           this.status = status;
    }


    /**
     * Gets the cardAcctId value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the acctBal value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @return acctBal
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @param acctBal
     */
    public void setAcctBal(com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal) {
        this.acctBal = acctBal;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type getAcctBal(int i) {
        return this.acctBal[i];
    }

    public void setAcctBal(int i, com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type _value) {
        this.acctBal[i] = _value;
    }


    /**
     * Gets the CCAcctStmtRec value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @return CCAcctStmtRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_Type[] getCCAcctStmtRec() {
        return CCAcctStmtRec;
    }


    /**
     * Sets the CCAcctStmtRec value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @param CCAcctStmtRec
     */
    public void setCCAcctStmtRec(com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_Type[] CCAcctStmtRec) {
        this.CCAcctStmtRec = CCAcctStmtRec;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_Type getCCAcctStmtRec(int i) {
        return this.CCAcctStmtRec[i];
    }

    public void setCCAcctStmtRec(int i, com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_Type _value) {
        this.CCAcctStmtRec[i] = _value;
    }


    /**
     * Gets the status value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CCAcctExtStmtInqRs_TypeCardAcctRec.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CCAcctExtStmtInqRs_TypeCardAcctRec)) return false;
        CCAcctExtStmtInqRs_TypeCardAcctRec other = (CCAcctExtStmtInqRs_TypeCardAcctRec) obj;
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
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              java.util.Arrays.equals(this.acctBal, other.getAcctBal()))) &&
            ((this.CCAcctStmtRec==null && other.getCCAcctStmtRec()==null) || 
             (this.CCAcctStmtRec!=null &&
              java.util.Arrays.equals(this.CCAcctStmtRec, other.getCCAcctStmtRec()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getCCAcctStmtRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCCAcctStmtRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCCAcctStmtRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CCAcctExtStmtInqRs_TypeCardAcctRec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctExtStmtInqRs_Type>CardAcctRec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CCAcctStmtRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctStmtRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctStmtRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
