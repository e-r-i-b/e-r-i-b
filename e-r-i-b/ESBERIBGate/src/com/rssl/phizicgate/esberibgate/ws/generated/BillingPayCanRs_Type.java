/**
 * BillingPayCanRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Ответ интерфейса TBP_CAN отмены билингового платежа
 */
public class BillingPayCanRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status2;

    /* Дополнительные реквизиты */
    private com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] requisites;

    /* Код авторизации транзакции в Way4, если операция прошла успешно */
    private java.lang.Long authorizationCode;

    /* Время авторизации транзакции в Way4, если операция прошла успешно */
    private java.lang.String authorizationDtTm;

    public BillingPayCanRs_Type() {
    }

    public BillingPayCanRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status2,
           com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] requisites,
           java.lang.Long authorizationCode,
           java.lang.String authorizationDtTm) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.status2 = status2;
           this.requisites = requisites;
           this.authorizationCode = authorizationCode;
           this.authorizationDtTm = authorizationDtTm;
    }


    /**
     * Gets the rqUID value for this BillingPayCanRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this BillingPayCanRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this BillingPayCanRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this BillingPayCanRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this BillingPayCanRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this BillingPayCanRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this BillingPayCanRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this BillingPayCanRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the status2 value for this BillingPayCanRs_Type.
     * 
     * @return status2
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus2() {
        return status2;
    }


    /**
     * Sets the status2 value for this BillingPayCanRs_Type.
     * 
     * @param status2
     */
    public void setStatus2(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status2) {
        this.status2 = status2;
    }


    /**
     * Gets the requisites value for this BillingPayCanRs_Type.
     * 
     * @return requisites   * Дополнительные реквизиты
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] getRequisites() {
        return requisites;
    }


    /**
     * Sets the requisites value for this BillingPayCanRs_Type.
     * 
     * @param requisites   * Дополнительные реквизиты
     */
    public void setRequisites(com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] requisites) {
        this.requisites = requisites;
    }


    /**
     * Gets the authorizationCode value for this BillingPayCanRs_Type.
     * 
     * @return authorizationCode   * Код авторизации транзакции в Way4, если операция прошла успешно
     */
    public java.lang.Long getAuthorizationCode() {
        return authorizationCode;
    }


    /**
     * Sets the authorizationCode value for this BillingPayCanRs_Type.
     * 
     * @param authorizationCode   * Код авторизации транзакции в Way4, если операция прошла успешно
     */
    public void setAuthorizationCode(java.lang.Long authorizationCode) {
        this.authorizationCode = authorizationCode;
    }


    /**
     * Gets the authorizationDtTm value for this BillingPayCanRs_Type.
     * 
     * @return authorizationDtTm   * Время авторизации транзакции в Way4, если операция прошла успешно
     */
    public java.lang.String getAuthorizationDtTm() {
        return authorizationDtTm;
    }


    /**
     * Sets the authorizationDtTm value for this BillingPayCanRs_Type.
     * 
     * @param authorizationDtTm   * Время авторизации транзакции в Way4, если операция прошла успешно
     */
    public void setAuthorizationDtTm(java.lang.String authorizationDtTm) {
        this.authorizationDtTm = authorizationDtTm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BillingPayCanRs_Type)) return false;
        BillingPayCanRs_Type other = (BillingPayCanRs_Type) obj;
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
            ((this.status2==null && other.getStatus2()==null) || 
             (this.status2!=null &&
              this.status2.equals(other.getStatus2()))) &&
            ((this.requisites==null && other.getRequisites()==null) || 
             (this.requisites!=null &&
              java.util.Arrays.equals(this.requisites, other.getRequisites()))) &&
            ((this.authorizationCode==null && other.getAuthorizationCode()==null) || 
             (this.authorizationCode!=null &&
              this.authorizationCode.equals(other.getAuthorizationCode()))) &&
            ((this.authorizationDtTm==null && other.getAuthorizationDtTm()==null) || 
             (this.authorizationDtTm!=null &&
              this.authorizationDtTm.equals(other.getAuthorizationDtTm())));
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
        if (getStatus2() != null) {
            _hashCode += getStatus2().hashCode();
        }
        if (getRequisites() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequisites());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequisites(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAuthorizationCode() != null) {
            _hashCode += getAuthorizationCode().hashCode();
        }
        if (getAuthorizationDtTm() != null) {
            _hashCode += getAuthorizationDtTm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BillingPayCanRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayCanRs_Type"));
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
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requisites");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisites"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite_Type"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AuthorizationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationDtTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AuthorizationDtTm"));
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
