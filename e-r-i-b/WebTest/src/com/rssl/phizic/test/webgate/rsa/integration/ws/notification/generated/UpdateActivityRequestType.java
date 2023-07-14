/**
 * UpdateActivityRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated;


/**
 * Обновить резолюцию события
 */
public class UpdateActivityRequestType  implements java.io.Serializable {
    /* Идентификатор события */
    private java.lang.String eventId;

    /* Решение по обновляемому событию */
    private com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ResolutionTypeList resolution;

    /* Имя пользователя оператора, связаного с кейсом */
    private java.lang.String operatorUserName;

    /* Описание причины установки статуса */
    private java.lang.String description;

    /* Идентификатор пользователя системы с правами работы в casemanagment. */
    private java.lang.String login;

    /* Пароль пользователя системы фрод-мониторинга, от имени которого
     * отправляется запрос */
    private java.lang.String password;

    public UpdateActivityRequestType() {
    }

    public UpdateActivityRequestType(
           java.lang.String eventId,
           com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ResolutionTypeList resolution,
           java.lang.String operatorUserName,
           java.lang.String description,
           java.lang.String login,
           java.lang.String password) {
           this.eventId = eventId;
           this.resolution = resolution;
           this.operatorUserName = operatorUserName;
           this.description = description;
           this.login = login;
           this.password = password;
    }


    /**
     * Gets the eventId value for this UpdateActivityRequestType.
     * 
     * @return eventId   * Идентификатор события
     */
    public java.lang.String getEventId() {
        return eventId;
    }


    /**
     * Sets the eventId value for this UpdateActivityRequestType.
     * 
     * @param eventId   * Идентификатор события
     */
    public void setEventId(java.lang.String eventId) {
        this.eventId = eventId;
    }


    /**
     * Gets the resolution value for this UpdateActivityRequestType.
     * 
     * @return resolution   * Решение по обновляемому событию
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ResolutionTypeList getResolution() {
        return resolution;
    }


    /**
     * Sets the resolution value for this UpdateActivityRequestType.
     * 
     * @param resolution   * Решение по обновляемому событию
     */
    public void setResolution(com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ResolutionTypeList resolution) {
        this.resolution = resolution;
    }


    /**
     * Gets the operatorUserName value for this UpdateActivityRequestType.
     * 
     * @return operatorUserName   * Имя пользователя оператора, связаного с кейсом
     */
    public java.lang.String getOperatorUserName() {
        return operatorUserName;
    }


    /**
     * Sets the operatorUserName value for this UpdateActivityRequestType.
     * 
     * @param operatorUserName   * Имя пользователя оператора, связаного с кейсом
     */
    public void setOperatorUserName(java.lang.String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }


    /**
     * Gets the description value for this UpdateActivityRequestType.
     * 
     * @return description   * Описание причины установки статуса
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this UpdateActivityRequestType.
     * 
     * @param description   * Описание причины установки статуса
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the login value for this UpdateActivityRequestType.
     * 
     * @return login   * Идентификатор пользователя системы с правами работы в casemanagment.
     */
    public java.lang.String getLogin() {
        return login;
    }


    /**
     * Sets the login value for this UpdateActivityRequestType.
     * 
     * @param login   * Идентификатор пользователя системы с правами работы в casemanagment.
     */
    public void setLogin(java.lang.String login) {
        this.login = login;
    }


    /**
     * Gets the password value for this UpdateActivityRequestType.
     * 
     * @return password   * Пароль пользователя системы фрод-мониторинга, от имени которого
     * отправляется запрос
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this UpdateActivityRequestType.
     * 
     * @param password   * Пароль пользователя системы фрод-мониторинга, от имени которого
     * отправляется запрос
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateActivityRequestType)) return false;
        UpdateActivityRequestType other = (UpdateActivityRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.eventId==null && other.getEventId()==null) || 
             (this.eventId!=null &&
              this.eventId.equals(other.getEventId()))) &&
            ((this.resolution==null && other.getResolution()==null) || 
             (this.resolution!=null &&
              this.resolution.equals(other.getResolution()))) &&
            ((this.operatorUserName==null && other.getOperatorUserName()==null) || 
             (this.operatorUserName!=null &&
              this.operatorUserName.equals(other.getOperatorUserName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.login==null && other.getLogin()==null) || 
             (this.login!=null &&
              this.login.equals(other.getLogin()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword())));
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
        if (getEventId() != null) {
            _hashCode += getEventId().hashCode();
        }
        if (getResolution() != null) {
            _hashCode += getResolution().hashCode();
        }
        if (getOperatorUserName() != null) {
            _hashCode += getOperatorUserName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getLogin() != null) {
            _hashCode += getLogin().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateActivityRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "updateActivityRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "eventId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resolution");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "resolution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "ResolutionTypeList"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operatorUserName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "operatorUserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("login");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "login"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
