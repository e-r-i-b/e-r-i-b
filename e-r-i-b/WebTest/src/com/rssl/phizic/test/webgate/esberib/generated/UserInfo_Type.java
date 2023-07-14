/**
 * UserInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип Информация о пользователе осуществившим изменения
 */
public class UserInfo_Type  implements java.io.Serializable {
    /* Тип пользователя, осуществившего изменения: Employee - пользователь
     * системы, TechnicalUser - технологический, Client - клиент */
    private com.rssl.phizic.test.webgate.esberib.generated.UserCategoryType userCategory;

    /* Код/логин пользователя внесшего изменения. Передается только
     * для сотрудника банка. */
    private java.lang.String login;

    /* ФИО пользователя внесшего изменения */
    private java.lang.String fio;

    /* Учреждение, к которому привязан пользователь, осуществивший
     * изменения (в кодировке subbranch) */
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    public UserInfo_Type() {
    }

    public UserInfo_Type(
           com.rssl.phizic.test.webgate.esberib.generated.UserCategoryType userCategory,
           java.lang.String login,
           java.lang.String fio,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
           this.userCategory = userCategory;
           this.login = login;
           this.fio = fio;
           this.bankInfo = bankInfo;
    }


    /**
     * Gets the userCategory value for this UserInfo_Type.
     * 
     * @return userCategory   * Тип пользователя, осуществившего изменения: Employee - пользователь
     * системы, TechnicalUser - технологический, Client - клиент
     */
    public com.rssl.phizic.test.webgate.esberib.generated.UserCategoryType getUserCategory() {
        return userCategory;
    }


    /**
     * Sets the userCategory value for this UserInfo_Type.
     * 
     * @param userCategory   * Тип пользователя, осуществившего изменения: Employee - пользователь
     * системы, TechnicalUser - технологический, Client - клиент
     */
    public void setUserCategory(com.rssl.phizic.test.webgate.esberib.generated.UserCategoryType userCategory) {
        this.userCategory = userCategory;
    }


    /**
     * Gets the login value for this UserInfo_Type.
     * 
     * @return login   * Код/логин пользователя внесшего изменения. Передается только
     * для сотрудника банка.
     */
    public java.lang.String getLogin() {
        return login;
    }


    /**
     * Sets the login value for this UserInfo_Type.
     * 
     * @param login   * Код/логин пользователя внесшего изменения. Передается только
     * для сотрудника банка.
     */
    public void setLogin(java.lang.String login) {
        this.login = login;
    }


    /**
     * Gets the fio value for this UserInfo_Type.
     * 
     * @return fio   * ФИО пользователя внесшего изменения
     */
    public java.lang.String getFio() {
        return fio;
    }


    /**
     * Sets the fio value for this UserInfo_Type.
     * 
     * @param fio   * ФИО пользователя внесшего изменения
     */
    public void setFio(java.lang.String fio) {
        this.fio = fio;
    }


    /**
     * Gets the bankInfo value for this UserInfo_Type.
     * 
     * @return bankInfo   * Учреждение, к которому привязан пользователь, осуществивший
     * изменения (в кодировке subbranch)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this UserInfo_Type.
     * 
     * @param bankInfo   * Учреждение, к которому привязан пользователь, осуществивший
     * изменения (в кодировке subbranch)
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserInfo_Type)) return false;
        UserInfo_Type other = (UserInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.userCategory==null && other.getUserCategory()==null) || 
             (this.userCategory!=null &&
              this.userCategory.equals(other.getUserCategory()))) &&
            ((this.login==null && other.getLogin()==null) || 
             (this.login!=null &&
              this.login.equals(other.getLogin()))) &&
            ((this.fio==null && other.getFio()==null) || 
             (this.fio!=null &&
              this.fio.equals(other.getFio()))) &&
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
        if (getUserCategory() != null) {
            _hashCode += getUserCategory().hashCode();
        }
        if (getLogin() != null) {
            _hashCode += getLogin().hashCode();
        }
        if (getFio() != null) {
            _hashCode += getFio().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UserInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UserCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UserCategoryType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("login");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Login"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Fio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
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
