/**
 * IMSAcctRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Выписка по ОМС
 */
public class IMSAcctRec_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtInfo_Type bankAcctFullStmtInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtRec_Type[] bankAcctFullStmtRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctStmtRec_Type[] bankAcctStmtRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    public IMSAcctRec_Type() {
    }

    public IMSAcctRec_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtInfo_Type bankAcctFullStmtInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtRec_Type[] bankAcctFullStmtRec,
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctStmtRec_Type[] bankAcctStmtRec,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
           this.bankAcctId = bankAcctId;
           this.bankAcctFullStmtInfo = bankAcctFullStmtInfo;
           this.bankAcctFullStmtRec = bankAcctFullStmtRec;
           this.bankAcctStmtRec = bankAcctStmtRec;
           this.status = status;
    }


    /**
     * Gets the bankAcctId value for this IMSAcctRec_Type.
     * 
     * @return bankAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type getBankAcctId() {
        return bankAcctId;
    }


    /**
     * Sets the bankAcctId value for this IMSAcctRec_Type.
     * 
     * @param bankAcctId
     */
    public void setBankAcctId(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId) {
        this.bankAcctId = bankAcctId;
    }


    /**
     * Gets the bankAcctFullStmtInfo value for this IMSAcctRec_Type.
     * 
     * @return bankAcctFullStmtInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtInfo_Type getBankAcctFullStmtInfo() {
        return bankAcctFullStmtInfo;
    }


    /**
     * Sets the bankAcctFullStmtInfo value for this IMSAcctRec_Type.
     * 
     * @param bankAcctFullStmtInfo
     */
    public void setBankAcctFullStmtInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtInfo_Type bankAcctFullStmtInfo) {
        this.bankAcctFullStmtInfo = bankAcctFullStmtInfo;
    }


    /**
     * Gets the bankAcctFullStmtRec value for this IMSAcctRec_Type.
     * 
     * @return bankAcctFullStmtRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtRec_Type[] getBankAcctFullStmtRec() {
        return bankAcctFullStmtRec;
    }


    /**
     * Sets the bankAcctFullStmtRec value for this IMSAcctRec_Type.
     * 
     * @param bankAcctFullStmtRec
     */
    public void setBankAcctFullStmtRec(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtRec_Type[] bankAcctFullStmtRec) {
        this.bankAcctFullStmtRec = bankAcctFullStmtRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtRec_Type getBankAcctFullStmtRec(int i) {
        return this.bankAcctFullStmtRec[i];
    }

    public void setBankAcctFullStmtRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.BankAcctFullStmtRec_Type _value) {
        this.bankAcctFullStmtRec[i] = _value;
    }


    /**
     * Gets the bankAcctStmtRec value for this IMSAcctRec_Type.
     * 
     * @return bankAcctStmtRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctStmtRec_Type[] getBankAcctStmtRec() {
        return bankAcctStmtRec;
    }


    /**
     * Sets the bankAcctStmtRec value for this IMSAcctRec_Type.
     * 
     * @param bankAcctStmtRec
     */
    public void setBankAcctStmtRec(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctStmtRec_Type[] bankAcctStmtRec) {
        this.bankAcctStmtRec = bankAcctStmtRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctStmtRec_Type getBankAcctStmtRec(int i) {
        return this.bankAcctStmtRec[i];
    }

    public void setBankAcctStmtRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.BankAcctStmtRec_Type _value) {
        this.bankAcctStmtRec[i] = _value;
    }


    /**
     * Gets the status value for this IMSAcctRec_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this IMSAcctRec_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IMSAcctRec_Type)) return false;
        IMSAcctRec_Type other = (IMSAcctRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankAcctId==null && other.getBankAcctId()==null) || 
             (this.bankAcctId!=null &&
              this.bankAcctId.equals(other.getBankAcctId()))) &&
            ((this.bankAcctFullStmtInfo==null && other.getBankAcctFullStmtInfo()==null) || 
             (this.bankAcctFullStmtInfo!=null &&
              this.bankAcctFullStmtInfo.equals(other.getBankAcctFullStmtInfo()))) &&
            ((this.bankAcctFullStmtRec==null && other.getBankAcctFullStmtRec()==null) || 
             (this.bankAcctFullStmtRec!=null &&
              java.util.Arrays.equals(this.bankAcctFullStmtRec, other.getBankAcctFullStmtRec()))) &&
            ((this.bankAcctStmtRec==null && other.getBankAcctStmtRec()==null) || 
             (this.bankAcctStmtRec!=null &&
              java.util.Arrays.equals(this.bankAcctStmtRec, other.getBankAcctStmtRec()))) &&
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
        if (getBankAcctId() != null) {
            _hashCode += getBankAcctId().hashCode();
        }
        if (getBankAcctFullStmtInfo() != null) {
            _hashCode += getBankAcctFullStmtInfo().hashCode();
        }
        if (getBankAcctFullStmtRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAcctFullStmtRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAcctFullStmtRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBankAcctStmtRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAcctStmtRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAcctStmtRec(), i);
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
        new org.apache.axis.description.TypeDesc(IMSAcctRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSAcctRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctFullStmtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctFullStmtRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctStmtRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtRec"));
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
