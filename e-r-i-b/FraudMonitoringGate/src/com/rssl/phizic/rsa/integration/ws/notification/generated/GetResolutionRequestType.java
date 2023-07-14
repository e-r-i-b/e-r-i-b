/**
 * GetResolutionRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.notification.generated;


/**
 * Запрос на получение резолюции события
 */
public class GetResolutionRequestType  implements java.io.Serializable {
    /* Идентификатор события */
    private java.lang.String eventId;

    /* Идентификатор пользователя АСВМ, от имени которого отправляется
     * запрос */
    private java.lang.String login;

    /* Пароль пользователя АСВМ, от имени которого отправляется запрос */
    private java.lang.String password;

    public GetResolutionRequestType() {
    }

    public GetResolutionRequestType(
           java.lang.String eventId,
           java.lang.String login,
           java.lang.String password) {
           this.eventId = eventId;
           this.login = login;
           this.password = password;
    }


    /**
     * Gets the eventId value for this GetResolutionRequestType.
     * 
     * @return eventId   * Идентификатор события
     */
    public java.lang.String getEventId() {
        return eventId;
    }


    /**
     * Sets the eventId value for this GetResolutionRequestType.
     * 
     * @param eventId   * Идентификатор события
     */
    public void setEventId(java.lang.String eventId) {
        this.eventId = eventId;
    }


    /**
     * Gets the login value for this GetResolutionRequestType.
     * 
     * @return login   * Идентификатор пользователя АСВМ, от имени которого отправляется
     * запрос
     */
    public java.lang.String getLogin() {
        return login;
    }


    /**
     * Sets the login value for this GetResolutionRequestType.
     * 
     * @param login   * Идентификатор пользователя АСВМ, от имени которого отправляется
     * запрос
     */
    public void setLogin(java.lang.String login) {
        this.login = login;
    }


    /**
     * Gets the password value for this GetResolutionRequestType.
     * 
     * @return password   * Пароль пользователя АСВМ, от имени которого отправляется запрос
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this GetResolutionRequestType.
     * 
     * @param password   * Пароль пользователя АСВМ, от имени которого отправляется запрос
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetResolutionRequestType)) return false;
        GetResolutionRequestType other = (GetResolutionRequestType) obj;
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
        new org.apache.axis.description.TypeDesc(GetResolutionRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "getResolutionRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "eventId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
