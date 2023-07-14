/**
 * EmployeeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;


/**
 * Сотрудник
 */
public class EmployeeType  implements java.io.Serializable {
    private java.lang.Long externalId;

    private com.rssl.phizicgate.csaadmin.service.generated.LoginType login;

    private java.lang.String surname;

    private java.lang.String firstName;

    private java.lang.String patronymic;

    private java.lang.String info;

    private java.lang.String email;

    private java.lang.String mobilePhone;

    private com.rssl.phizicgate.csaadmin.service.generated.DepartmentType department;

    private boolean caAdmin;

    private boolean vspEmployee;

    private java.lang.String managerId;

    private java.lang.String managerPhone;

    private java.lang.String managerEMail;

    private java.lang.String managerLeadEMail;

    private java.lang.String managerChannel;

    private java.lang.String sudirLogin;

    public EmployeeType() {
    }

    public EmployeeType(
           java.lang.Long externalId,
           com.rssl.phizicgate.csaadmin.service.generated.LoginType login,
           java.lang.String surname,
           java.lang.String firstName,
           java.lang.String patronymic,
           java.lang.String info,
           java.lang.String email,
           java.lang.String mobilePhone,
           com.rssl.phizicgate.csaadmin.service.generated.DepartmentType department,
           boolean caAdmin,
           boolean vspEmployee,
           java.lang.String managerId,
           java.lang.String managerPhone,
           java.lang.String managerEMail,
           java.lang.String managerLeadEMail,
           java.lang.String managerChannel,
           java.lang.String sudirLogin) {
           this.externalId = externalId;
           this.login = login;
           this.surname = surname;
           this.firstName = firstName;
           this.patronymic = patronymic;
           this.info = info;
           this.email = email;
           this.mobilePhone = mobilePhone;
           this.department = department;
           this.caAdmin = caAdmin;
           this.vspEmployee = vspEmployee;
           this.managerId = managerId;
           this.managerPhone = managerPhone;
           this.managerEMail = managerEMail;
           this.managerLeadEMail = managerLeadEMail;
           this.managerChannel = managerChannel;
           this.sudirLogin = sudirLogin;
    }


    /**
     * Gets the externalId value for this EmployeeType.
     * 
     * @return externalId
     */
    public java.lang.Long getExternalId() {
        return externalId;
    }


    /**
     * Sets the externalId value for this EmployeeType.
     * 
     * @param externalId
     */
    public void setExternalId(java.lang.Long externalId) {
        this.externalId = externalId;
    }


    /**
     * Gets the login value for this EmployeeType.
     * 
     * @return login
     */
    public com.rssl.phizicgate.csaadmin.service.generated.LoginType getLogin() {
        return login;
    }


    /**
     * Sets the login value for this EmployeeType.
     * 
     * @param login
     */
    public void setLogin(com.rssl.phizicgate.csaadmin.service.generated.LoginType login) {
        this.login = login;
    }


    /**
     * Gets the surname value for this EmployeeType.
     * 
     * @return surname
     */
    public java.lang.String getSurname() {
        return surname;
    }


    /**
     * Sets the surname value for this EmployeeType.
     * 
     * @param surname
     */
    public void setSurname(java.lang.String surname) {
        this.surname = surname;
    }


    /**
     * Gets the firstName value for this EmployeeType.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this EmployeeType.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the patronymic value for this EmployeeType.
     * 
     * @return patronymic
     */
    public java.lang.String getPatronymic() {
        return patronymic;
    }


    /**
     * Sets the patronymic value for this EmployeeType.
     * 
     * @param patronymic
     */
    public void setPatronymic(java.lang.String patronymic) {
        this.patronymic = patronymic;
    }


    /**
     * Gets the info value for this EmployeeType.
     * 
     * @return info
     */
    public java.lang.String getInfo() {
        return info;
    }


    /**
     * Sets the info value for this EmployeeType.
     * 
     * @param info
     */
    public void setInfo(java.lang.String info) {
        this.info = info;
    }


    /**
     * Gets the email value for this EmployeeType.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this EmployeeType.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the mobilePhone value for this EmployeeType.
     * 
     * @return mobilePhone
     */
    public java.lang.String getMobilePhone() {
        return mobilePhone;
    }


    /**
     * Sets the mobilePhone value for this EmployeeType.
     * 
     * @param mobilePhone
     */
    public void setMobilePhone(java.lang.String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }


    /**
     * Gets the department value for this EmployeeType.
     * 
     * @return department
     */
    public com.rssl.phizicgate.csaadmin.service.generated.DepartmentType getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this EmployeeType.
     * 
     * @param department
     */
    public void setDepartment(com.rssl.phizicgate.csaadmin.service.generated.DepartmentType department) {
        this.department = department;
    }


    /**
     * Gets the caAdmin value for this EmployeeType.
     * 
     * @return caAdmin
     */
    public boolean isCaAdmin() {
        return caAdmin;
    }


    /**
     * Sets the caAdmin value for this EmployeeType.
     * 
     * @param caAdmin
     */
    public void setCaAdmin(boolean caAdmin) {
        this.caAdmin = caAdmin;
    }


    /**
     * Gets the vspEmployee value for this EmployeeType.
     * 
     * @return vspEmployee
     */
    public boolean isVspEmployee() {
        return vspEmployee;
    }


    /**
     * Sets the vspEmployee value for this EmployeeType.
     * 
     * @param vspEmployee
     */
    public void setVspEmployee(boolean vspEmployee) {
        this.vspEmployee = vspEmployee;
    }


    /**
     * Gets the managerId value for this EmployeeType.
     * 
     * @return managerId
     */
    public java.lang.String getManagerId() {
        return managerId;
    }


    /**
     * Sets the managerId value for this EmployeeType.
     * 
     * @param managerId
     */
    public void setManagerId(java.lang.String managerId) {
        this.managerId = managerId;
    }


    /**
     * Gets the managerPhone value for this EmployeeType.
     * 
     * @return managerPhone
     */
    public java.lang.String getManagerPhone() {
        return managerPhone;
    }


    /**
     * Sets the managerPhone value for this EmployeeType.
     * 
     * @param managerPhone
     */
    public void setManagerPhone(java.lang.String managerPhone) {
        this.managerPhone = managerPhone;
    }


    /**
     * Gets the managerEMail value for this EmployeeType.
     * 
     * @return managerEMail
     */
    public java.lang.String getManagerEMail() {
        return managerEMail;
    }


    /**
     * Sets the managerEMail value for this EmployeeType.
     * 
     * @param managerEMail
     */
    public void setManagerEMail(java.lang.String managerEMail) {
        this.managerEMail = managerEMail;
    }


    /**
     * Gets the managerLeadEMail value for this EmployeeType.
     * 
     * @return managerLeadEMail
     */
    public java.lang.String getManagerLeadEMail() {
        return managerLeadEMail;
    }


    /**
     * Sets the managerLeadEMail value for this EmployeeType.
     * 
     * @param managerLeadEMail
     */
    public void setManagerLeadEMail(java.lang.String managerLeadEMail) {
        this.managerLeadEMail = managerLeadEMail;
    }


    /**
     * Gets the managerChannel value for this EmployeeType.
     * 
     * @return managerChannel
     */
    public java.lang.String getManagerChannel() {
        return managerChannel;
    }


    /**
     * Sets the managerChannel value for this EmployeeType.
     * 
     * @param managerChannel
     */
    public void setManagerChannel(java.lang.String managerChannel) {
        this.managerChannel = managerChannel;
    }


    /**
     * Gets the sudirLogin value for this EmployeeType.
     * 
     * @return sudirLogin
     */
    public java.lang.String getSudirLogin() {
        return sudirLogin;
    }


    /**
     * Sets the sudirLogin value for this EmployeeType.
     * 
     * @param sudirLogin
     */
    public void setSudirLogin(java.lang.String sudirLogin) {
        this.sudirLogin = sudirLogin;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmployeeType)) return false;
        EmployeeType other = (EmployeeType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.externalId==null && other.getExternalId()==null) || 
             (this.externalId!=null &&
              this.externalId.equals(other.getExternalId()))) &&
            ((this.login==null && other.getLogin()==null) || 
             (this.login!=null &&
              this.login.equals(other.getLogin()))) &&
            ((this.surname==null && other.getSurname()==null) || 
             (this.surname!=null &&
              this.surname.equals(other.getSurname()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.patronymic==null && other.getPatronymic()==null) || 
             (this.patronymic!=null &&
              this.patronymic.equals(other.getPatronymic()))) &&
            ((this.info==null && other.getInfo()==null) || 
             (this.info!=null &&
              this.info.equals(other.getInfo()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.mobilePhone==null && other.getMobilePhone()==null) || 
             (this.mobilePhone!=null &&
              this.mobilePhone.equals(other.getMobilePhone()))) &&
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            this.caAdmin == other.isCaAdmin() &&
            this.vspEmployee == other.isVspEmployee() &&
            ((this.managerId==null && other.getManagerId()==null) || 
             (this.managerId!=null &&
              this.managerId.equals(other.getManagerId()))) &&
            ((this.managerPhone==null && other.getManagerPhone()==null) || 
             (this.managerPhone!=null &&
              this.managerPhone.equals(other.getManagerPhone()))) &&
            ((this.managerEMail==null && other.getManagerEMail()==null) || 
             (this.managerEMail!=null &&
              this.managerEMail.equals(other.getManagerEMail()))) &&
            ((this.managerLeadEMail==null && other.getManagerLeadEMail()==null) || 
             (this.managerLeadEMail!=null &&
              this.managerLeadEMail.equals(other.getManagerLeadEMail()))) &&
            ((this.managerChannel==null && other.getManagerChannel()==null) || 
             (this.managerChannel!=null &&
              this.managerChannel.equals(other.getManagerChannel()))) &&
            ((this.sudirLogin==null && other.getSudirLogin()==null) || 
             (this.sudirLogin!=null &&
              this.sudirLogin.equals(other.getSudirLogin())));
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
        if (getExternalId() != null) {
            _hashCode += getExternalId().hashCode();
        }
        if (getLogin() != null) {
            _hashCode += getLogin().hashCode();
        }
        if (getSurname() != null) {
            _hashCode += getSurname().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getPatronymic() != null) {
            _hashCode += getPatronymic().hashCode();
        }
        if (getInfo() != null) {
            _hashCode += getInfo().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getMobilePhone() != null) {
            _hashCode += getMobilePhone().hashCode();
        }
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        _hashCode += (isCaAdmin() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isVspEmployee() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getManagerId() != null) {
            _hashCode += getManagerId().hashCode();
        }
        if (getManagerPhone() != null) {
            _hashCode += getManagerPhone().hashCode();
        }
        if (getManagerEMail() != null) {
            _hashCode += getManagerEMail().hashCode();
        }
        if (getManagerLeadEMail() != null) {
            _hashCode += getManagerLeadEMail().hashCode();
        }
        if (getManagerChannel() != null) {
            _hashCode += getManagerChannel().hashCode();
        }
        if (getSudirLogin() != null) {
            _hashCode += getSudirLogin().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmployeeType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "externalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("login");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "login"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "surname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patronymic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "patronymic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("info");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "info"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobilePhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "mobilePhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DepartmentType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caAdmin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "caAdmin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vspEmployee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "vspEmployee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "managerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "managerPhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerEMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "managerEMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerLeadEMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "managerLeadEMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerChannel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "managerChannel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sudirLogin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "sudirLogin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
