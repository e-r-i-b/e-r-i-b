/**
 * ChangeAccountInfoRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип запроса для интерфейса SrvChangeAccountInfo. Изменение информации
 * по счету
 */
public class ChangeAccountInfoRq_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type depAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo;

    /* Действие со счетом */
    private com.rssl.phizicgate.esberibgate.ws.generated.Action_Type action;

    public ChangeAccountInfoRq_Type() {
    }

    public ChangeAccountInfoRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type depAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.Action_Type action) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.bankInfo = bankInfo;
           this.depAcctId = depAcctId;
           this.custInfo = custInfo;
           this.action = action;
    }


    /**
     * Gets the rqUID value for this ChangeAccountInfoRq_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this ChangeAccountInfoRq_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this ChangeAccountInfoRq_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this ChangeAccountInfoRq_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this ChangeAccountInfoRq_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this ChangeAccountInfoRq_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this ChangeAccountInfoRq_Type.
     * 
     * @return SPName
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this ChangeAccountInfoRq_Type.
     * 
     * @param SPName
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the bankInfo value for this ChangeAccountInfoRq_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this ChangeAccountInfoRq_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the depAcctId value for this ChangeAccountInfoRq_Type.
     * 
     * @return depAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type getDepAcctId() {
        return depAcctId;
    }


    /**
     * Sets the depAcctId value for this ChangeAccountInfoRq_Type.
     * 
     * @param depAcctId
     */
    public void setDepAcctId(com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type depAcctId) {
        this.depAcctId = depAcctId;
    }


    /**
     * Gets the custInfo value for this ChangeAccountInfoRq_Type.
     * 
     * @return custInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type getCustInfo() {
        return custInfo;
    }


    /**
     * Sets the custInfo value for this ChangeAccountInfoRq_Type.
     * 
     * @param custInfo
     */
    public void setCustInfo(com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo) {
        this.custInfo = custInfo;
    }


    /**
     * Gets the action value for this ChangeAccountInfoRq_Type.
     * 
     * @return action   * Действие со счетом
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Action_Type getAction() {
        return action;
    }


    /**
     * Sets the action value for this ChangeAccountInfoRq_Type.
     * 
     * @param action   * Действие со счетом
     */
    public void setAction(com.rssl.phizicgate.esberibgate.ws.generated.Action_Type action) {
        this.action = action;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangeAccountInfoRq_Type)) return false;
        ChangeAccountInfoRq_Type other = (ChangeAccountInfoRq_Type) obj;
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
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.depAcctId==null && other.getDepAcctId()==null) || 
             (this.depAcctId!=null &&
              this.depAcctId.equals(other.getDepAcctId()))) &&
            ((this.custInfo==null && other.getCustInfo()==null) || 
             (this.custInfo!=null &&
              this.custInfo.equals(other.getCustInfo()))) &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction())));
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
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getDepAcctId() != null) {
            _hashCode += getDepAcctId().hashCode();
        }
        if (getCustInfo() != null) {
            _hashCode += getCustInfo().hashCode();
        }
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeAccountInfoRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeAccountInfoRq_Type"));
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
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Action_Type"));
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
