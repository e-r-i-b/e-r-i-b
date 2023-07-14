/**
 * AutoSubscriptionModRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип сообщения-запроса для интерфейса ASM - cоздание/изменение подписки
 */
public class AutoSubscriptionModRq_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    /* Данные сотрудника ВСП, выполнившего действие */
    private com.rssl.phizicgate.esberibgate.ws.generated.EmployeeOfTheVSP_Type employeeOfTheVSP;

    private com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionRec_Type autoSubscriptionRec;

    /* true - необходимо создать подписку и немедленно исполнить платёж,
     * false - необходимо только создать подписку */
    private boolean execute;

    /* Информация об услуге мобильный банк */
    private com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type MBCInfo;

    /* Необходимость подтверждения операции клиентом.
     * true - нужно подтверждение
     * false - не нужно подтверждение (операция уже была подтверждена клиентом) */
    private boolean needConfirmation;

    public AutoSubscriptionModRq_Type() {
    }

    public AutoSubscriptionModRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.EmployeeOfTheVSP_Type employeeOfTheVSP,
           com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionRec_Type autoSubscriptionRec,
           boolean execute,
           com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type MBCInfo,
           boolean needConfirmation) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.bankInfo = bankInfo;
           this.employeeOfTheVSP = employeeOfTheVSP;
           this.autoSubscriptionRec = autoSubscriptionRec;
           this.execute = execute;
           this.MBCInfo = MBCInfo;
           this.needConfirmation = needConfirmation;
    }


    /**
     * Gets the rqUID value for this AutoSubscriptionModRq_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this AutoSubscriptionModRq_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this AutoSubscriptionModRq_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this AutoSubscriptionModRq_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this AutoSubscriptionModRq_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this AutoSubscriptionModRq_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this AutoSubscriptionModRq_Type.
     * 
     * @return SPName
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this AutoSubscriptionModRq_Type.
     * 
     * @param SPName
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the bankInfo value for this AutoSubscriptionModRq_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this AutoSubscriptionModRq_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the employeeOfTheVSP value for this AutoSubscriptionModRq_Type.
     * 
     * @return employeeOfTheVSP   * Данные сотрудника ВСП, выполнившего действие
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.EmployeeOfTheVSP_Type getEmployeeOfTheVSP() {
        return employeeOfTheVSP;
    }


    /**
     * Sets the employeeOfTheVSP value for this AutoSubscriptionModRq_Type.
     * 
     * @param employeeOfTheVSP   * Данные сотрудника ВСП, выполнившего действие
     */
    public void setEmployeeOfTheVSP(com.rssl.phizicgate.esberibgate.ws.generated.EmployeeOfTheVSP_Type employeeOfTheVSP) {
        this.employeeOfTheVSP = employeeOfTheVSP;
    }


    /**
     * Gets the autoSubscriptionRec value for this AutoSubscriptionModRq_Type.
     * 
     * @return autoSubscriptionRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionRec_Type getAutoSubscriptionRec() {
        return autoSubscriptionRec;
    }


    /**
     * Sets the autoSubscriptionRec value for this AutoSubscriptionModRq_Type.
     * 
     * @param autoSubscriptionRec
     */
    public void setAutoSubscriptionRec(com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionRec_Type autoSubscriptionRec) {
        this.autoSubscriptionRec = autoSubscriptionRec;
    }


    /**
     * Gets the execute value for this AutoSubscriptionModRq_Type.
     * 
     * @return execute   * true - необходимо создать подписку и немедленно исполнить платёж,
     * false - необходимо только создать подписку
     */
    public boolean isExecute() {
        return execute;
    }


    /**
     * Sets the execute value for this AutoSubscriptionModRq_Type.
     * 
     * @param execute   * true - необходимо создать подписку и немедленно исполнить платёж,
     * false - необходимо только создать подписку
     */
    public void setExecute(boolean execute) {
        this.execute = execute;
    }


    /**
     * Gets the MBCInfo value for this AutoSubscriptionModRq_Type.
     * 
     * @return MBCInfo   * Информация об услуге мобильный банк
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type getMBCInfo() {
        return MBCInfo;
    }


    /**
     * Sets the MBCInfo value for this AutoSubscriptionModRq_Type.
     * 
     * @param MBCInfo   * Информация об услуге мобильный банк
     */
    public void setMBCInfo(com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type MBCInfo) {
        this.MBCInfo = MBCInfo;
    }


    /**
     * Gets the needConfirmation value for this AutoSubscriptionModRq_Type.
     * 
     * @return needConfirmation   * Необходимость подтверждения операции клиентом.
     * true - нужно подтверждение
     * false - не нужно подтверждение (операция уже была подтверждена клиентом)
     */
    public boolean isNeedConfirmation() {
        return needConfirmation;
    }


    /**
     * Sets the needConfirmation value for this AutoSubscriptionModRq_Type.
     * 
     * @param needConfirmation   * Необходимость подтверждения операции клиентом.
     * true - нужно подтверждение
     * false - не нужно подтверждение (операция уже была подтверждена клиентом)
     */
    public void setNeedConfirmation(boolean needConfirmation) {
        this.needConfirmation = needConfirmation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoSubscriptionModRq_Type)) return false;
        AutoSubscriptionModRq_Type other = (AutoSubscriptionModRq_Type) obj;
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
            ((this.employeeOfTheVSP==null && other.getEmployeeOfTheVSP()==null) || 
             (this.employeeOfTheVSP!=null &&
              this.employeeOfTheVSP.equals(other.getEmployeeOfTheVSP()))) &&
            ((this.autoSubscriptionRec==null && other.getAutoSubscriptionRec()==null) || 
             (this.autoSubscriptionRec!=null &&
              this.autoSubscriptionRec.equals(other.getAutoSubscriptionRec()))) &&
            this.execute == other.isExecute() &&
            ((this.MBCInfo==null && other.getMBCInfo()==null) || 
             (this.MBCInfo!=null &&
              this.MBCInfo.equals(other.getMBCInfo()))) &&
            this.needConfirmation == other.isNeedConfirmation();
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
        if (getEmployeeOfTheVSP() != null) {
            _hashCode += getEmployeeOfTheVSP().hashCode();
        }
        if (getAutoSubscriptionRec() != null) {
            _hashCode += getAutoSubscriptionRec().hashCode();
        }
        _hashCode += (isExecute() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMBCInfo() != null) {
            _hashCode += getMBCInfo().hashCode();
        }
        _hashCode += (isNeedConfirmation() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoSubscriptionModRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionModRq_Type"));
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
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("employeeOfTheVSP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmployeeOfTheVSP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmployeeOfTheVSP_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoSubscriptionRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionRec_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("execute");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Execute"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MBCInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MBCInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MBCInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("needConfirmation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NeedConfirmation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
