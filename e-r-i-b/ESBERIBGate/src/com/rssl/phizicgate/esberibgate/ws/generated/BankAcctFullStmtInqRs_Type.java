/**
 * BankAcctFullStmtInqRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Ответ интерфейса IAS_F получения полной выписки по ОМС
 */
public class BankAcctFullStmtInqRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    private com.rssl.phizicgate.esberibgate.ws.generated.IMSAcctRec_Type[] IMSAcctRec;

    public BankAcctFullStmtInqRs_Type() {
    }

    public BankAcctFullStmtInqRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.IMSAcctRec_Type[] IMSAcctRec) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.IMSAcctRec = IMSAcctRec;
    }


    /**
     * Gets the rqUID value for this BankAcctFullStmtInqRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this BankAcctFullStmtInqRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this BankAcctFullStmtInqRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this BankAcctFullStmtInqRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this BankAcctFullStmtInqRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this BankAcctFullStmtInqRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this BankAcctFullStmtInqRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this BankAcctFullStmtInqRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the IMSAcctRec value for this BankAcctFullStmtInqRs_Type.
     * 
     * @return IMSAcctRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.IMSAcctRec_Type[] getIMSAcctRec() {
        return IMSAcctRec;
    }


    /**
     * Sets the IMSAcctRec value for this BankAcctFullStmtInqRs_Type.
     * 
     * @param IMSAcctRec
     */
    public void setIMSAcctRec(com.rssl.phizicgate.esberibgate.ws.generated.IMSAcctRec_Type[] IMSAcctRec) {
        this.IMSAcctRec = IMSAcctRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.IMSAcctRec_Type getIMSAcctRec(int i) {
        return this.IMSAcctRec[i];
    }

    public void setIMSAcctRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.IMSAcctRec_Type _value) {
        this.IMSAcctRec[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctFullStmtInqRs_Type)) return false;
        BankAcctFullStmtInqRs_Type other = (BankAcctFullStmtInqRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.IMSAcctRec==null && other.getIMSAcctRec()==null) || 
             (this.IMSAcctRec!=null &&
              java.util.Arrays.equals(this.IMSAcctRec, other.getIMSAcctRec())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getIMSAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIMSAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIMSAcctRec(), i);
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
        new org.apache.axis.description.TypeDesc(BankAcctFullStmtInqRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtInqRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IMSAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSAcctRec"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
