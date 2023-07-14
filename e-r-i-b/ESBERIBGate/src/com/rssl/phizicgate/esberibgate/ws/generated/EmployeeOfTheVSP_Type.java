/**
 * EmployeeOfTheVSP_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип данных сотрудника ВСП, выполнившего действие
 */
public class EmployeeOfTheVSP_Type  implements java.io.Serializable {
    /* Логин */
    private java.lang.String login;

    /* ФИО Сотрудника ВСП */
    private com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    public EmployeeOfTheVSP_Type() {
    }

    public EmployeeOfTheVSP_Type(
           java.lang.String login,
           com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
           this.login = login;
           this.personName = personName;
           this.bankInfo = bankInfo;
    }


    /**
     * Gets the login value for this EmployeeOfTheVSP_Type.
     * 
     * @return login   * Логин
     */
    public java.lang.String getLogin() {
        return login;
    }


    /**
     * Sets the login value for this EmployeeOfTheVSP_Type.
     * 
     * @param login   * Логин
     */
    public void setLogin(java.lang.String login) {
        this.login = login;
    }


    /**
     * Gets the personName value for this EmployeeOfTheVSP_Type.
     * 
     * @return personName   * ФИО Сотрудника ВСП
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type getPersonName() {
        return personName;
    }


    /**
     * Sets the personName value for this EmployeeOfTheVSP_Type.
     * 
     * @param personName   * ФИО Сотрудника ВСП
     */
    public void setPersonName(com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName) {
        this.personName = personName;
    }


    /**
     * Gets the bankInfo value for this EmployeeOfTheVSP_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this EmployeeOfTheVSP_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmployeeOfTheVSP_Type)) return false;
        EmployeeOfTheVSP_Type other = (EmployeeOfTheVSP_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.login==null && other.getLogin()==null) || 
             (this.login!=null &&
              this.login.equals(other.getLogin()))) &&
            ((this.personName==null && other.getPersonName()==null) || 
             (this.personName!=null &&
              this.personName.equals(other.getPersonName()))) &&
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
        if (getLogin() != null) {
            _hashCode += getLogin().hashCode();
        }
        if (getPersonName() != null) {
            _hashCode += getPersonName().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmployeeOfTheVSP_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmployeeOfTheVSP_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("login");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Login"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonName_Type"));
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
