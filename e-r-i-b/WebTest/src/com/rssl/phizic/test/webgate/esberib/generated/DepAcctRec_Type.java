/**
 * DepAcctRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class DepAcctRec_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAccInfo_Type depAccInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermiss_Type[] bankAcctPermiss;

    private com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public DepAcctRec_Type() {
    }

    public DepAcctRec_Type(
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.DepAccInfo_Type depAccInfo,
           com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermiss_Type[] bankAcctPermiss,
           com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] acctBal,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.bankInfo = bankInfo;
           this.depAcctId = depAcctId;
           this.depAccInfo = depAccInfo;
           this.bankAcctPermiss = bankAcctPermiss;
           this.acctBal = acctBal;
           this.status = status;
    }


    /**
     * Gets the bankInfo value for this DepAcctRec_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this DepAcctRec_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the depAcctId value for this DepAcctRec_Type.
     * 
     * @return depAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctId() {
        return depAcctId;
    }


    /**
     * Sets the depAcctId value for this DepAcctRec_Type.
     * 
     * @param depAcctId
     */
    public void setDepAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId) {
        this.depAcctId = depAcctId;
    }


    /**
     * Gets the depAccInfo value for this DepAcctRec_Type.
     * 
     * @return depAccInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAccInfo_Type getDepAccInfo() {
        return depAccInfo;
    }


    /**
     * Sets the depAccInfo value for this DepAcctRec_Type.
     * 
     * @param depAccInfo
     */
    public void setDepAccInfo(com.rssl.phizic.test.webgate.esberib.generated.DepAccInfo_Type depAccInfo) {
        this.depAccInfo = depAccInfo;
    }


    /**
     * Gets the bankAcctPermiss value for this DepAcctRec_Type.
     * 
     * @return bankAcctPermiss
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermiss_Type[] getBankAcctPermiss() {
        return bankAcctPermiss;
    }


    /**
     * Sets the bankAcctPermiss value for this DepAcctRec_Type.
     * 
     * @param bankAcctPermiss
     */
    public void setBankAcctPermiss(com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermiss_Type[] bankAcctPermiss) {
        this.bankAcctPermiss = bankAcctPermiss;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermiss_Type getBankAcctPermiss(int i) {
        return this.bankAcctPermiss[i];
    }

    public void setBankAcctPermiss(int i, com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermiss_Type _value) {
        this.bankAcctPermiss[i] = _value;
    }


    /**
     * Gets the acctBal value for this DepAcctRec_Type.
     * 
     * @return acctBal
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type[] getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this DepAcctRec_Type.
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
     * Gets the status value for this DepAcctRec_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepAcctRec_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAcctRec_Type)) return false;
        DepAcctRec_Type other = (DepAcctRec_Type) obj;
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
            ((this.depAcctId==null && other.getDepAcctId()==null) || 
             (this.depAcctId!=null &&
              this.depAcctId.equals(other.getDepAcctId()))) &&
            ((this.depAccInfo==null && other.getDepAccInfo()==null) || 
             (this.depAccInfo!=null &&
              this.depAccInfo.equals(other.getDepAccInfo()))) &&
            ((this.bankAcctPermiss==null && other.getBankAcctPermiss()==null) || 
             (this.bankAcctPermiss!=null &&
              java.util.Arrays.equals(this.bankAcctPermiss, other.getBankAcctPermiss()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              java.util.Arrays.equals(this.acctBal, other.getAcctBal()))) &&
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
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getDepAcctId() != null) {
            _hashCode += getDepAcctId().hashCode();
        }
        if (getDepAccInfo() != null) {
            _hashCode += getDepAccInfo().hashCode();
        }
        if (getBankAcctPermiss() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAcctPermiss());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAcctPermiss(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepAcctRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAccInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAccInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAccInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctPermiss");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctPermiss"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctPermiss"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
