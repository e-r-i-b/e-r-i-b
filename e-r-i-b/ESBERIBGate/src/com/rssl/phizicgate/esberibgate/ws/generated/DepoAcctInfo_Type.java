/**
 * DepoAcctInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация по счету депо
 */
public class DepoAcctInfo_Type  implements java.io.Serializable {
    /* Статус счета депо: 1 – открыт, 2 – закрыт */
    private java.lang.String status;

    /* Разрешены ли операции со счетом */
    private java.lang.Boolean isOperationAllowed;

    private java.lang.String agreemtNum;

    private java.lang.String startDt;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    public DepoAcctInfo_Type() {
    }

    public DepoAcctInfo_Type(
           java.lang.String status,
           java.lang.Boolean isOperationAllowed,
           java.lang.String agreemtNum,
           java.lang.String startDt,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
           this.status = status;
           this.isOperationAllowed = isOperationAllowed;
           this.agreemtNum = agreemtNum;
           this.startDt = startDt;
           this.bankInfo = bankInfo;
    }


    /**
     * Gets the status value for this DepoAcctInfo_Type.
     * 
     * @return status   * Статус счета депо: 1 – открыт, 2 – закрыт
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepoAcctInfo_Type.
     * 
     * @param status   * Статус счета депо: 1 – открыт, 2 – закрыт
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the isOperationAllowed value for this DepoAcctInfo_Type.
     * 
     * @return isOperationAllowed   * Разрешены ли операции со счетом
     */
    public java.lang.Boolean getIsOperationAllowed() {
        return isOperationAllowed;
    }


    /**
     * Sets the isOperationAllowed value for this DepoAcctInfo_Type.
     * 
     * @param isOperationAllowed   * Разрешены ли операции со счетом
     */
    public void setIsOperationAllowed(java.lang.Boolean isOperationAllowed) {
        this.isOperationAllowed = isOperationAllowed;
    }


    /**
     * Gets the agreemtNum value for this DepoAcctInfo_Type.
     * 
     * @return agreemtNum
     */
    public java.lang.String getAgreemtNum() {
        return agreemtNum;
    }


    /**
     * Sets the agreemtNum value for this DepoAcctInfo_Type.
     * 
     * @param agreemtNum
     */
    public void setAgreemtNum(java.lang.String agreemtNum) {
        this.agreemtNum = agreemtNum;
    }


    /**
     * Gets the startDt value for this DepoAcctInfo_Type.
     * 
     * @return startDt
     */
    public java.lang.String getStartDt() {
        return startDt;
    }


    /**
     * Sets the startDt value for this DepoAcctInfo_Type.
     * 
     * @param startDt
     */
    public void setStartDt(java.lang.String startDt) {
        this.startDt = startDt;
    }


    /**
     * Gets the bankInfo value for this DepoAcctInfo_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this DepoAcctInfo_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAcctInfo_Type)) return false;
        DepoAcctInfo_Type other = (DepoAcctInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.isOperationAllowed==null && other.getIsOperationAllowed()==null) || 
             (this.isOperationAllowed!=null &&
              this.isOperationAllowed.equals(other.getIsOperationAllowed()))) &&
            ((this.agreemtNum==null && other.getAgreemtNum()==null) || 
             (this.agreemtNum!=null &&
              this.agreemtNum.equals(other.getAgreemtNum()))) &&
            ((this.startDt==null && other.getStartDt()==null) || 
             (this.startDt!=null &&
              this.startDt.equals(other.getStartDt()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getIsOperationAllowed() != null) {
            _hashCode += getIsOperationAllowed().hashCode();
        }
        if (getAgreemtNum() != null) {
            _hashCode += getAgreemtNum().hashCode();
        }
        if (getStartDt() != null) {
            _hashCode += getStartDt().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAcctInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isOperationAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "isOperationAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtNum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
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
