/**
 * EventType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class EventType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EventType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NULL = "NULL";
    public static final java.lang.String _SESSION_SIGNIN = "SESSION_SIGNIN";
    public static final java.lang.String _FAILED_LOGIN_ATTEMPT = "FAILED_LOGIN_ATTEMPT";
    public static final java.lang.String _ENROLL = "ENROLL";
    public static final java.lang.String _UPDATE_USER = "UPDATE_USER";
    public static final java.lang.String _CREATE_USER = "CREATE_USER";
    public static final java.lang.String _OLB_ENROLL = "OLB_ENROLL";
    public static final java.lang.String _FAILED_OLB_ENROLL_ATTEMPT = "FAILED_OLB_ENROLL_ATTEMPT";
    public static final java.lang.String _ACTIVATE_CARD = "ACTIVATE_CARD";
    public static final java.lang.String _OPEN_NEW_ACCOUNT = "OPEN_NEW_ACCOUNT";
    public static final java.lang.String _REQUEST_CREDIT = "REQUEST_CREDIT";
    public static final java.lang.String _ADD_PAYEE = "ADD_PAYEE";
    public static final java.lang.String _EDIT_PAYEE = "EDIT_PAYEE";
    public static final java.lang.String _PAYMENT = "PAYMENT";
    public static final java.lang.String _DEPOSIT = "DEPOSIT";
    public static final java.lang.String _STOCK_TRADE = "STOCK_TRADE";
    public static final java.lang.String _OPTIONS_TRADE = "OPTIONS_TRADE";
    public static final java.lang.String _CHANGE_LOGIN_ID = "CHANGE_LOGIN_ID";
    public static final java.lang.String _CHANGE_EMAIL = "CHANGE_EMAIL";
    public static final java.lang.String _CHANGE_PHONE = "CHANGE_PHONE";
    public static final java.lang.String _CHANGE_ADDRESS = "CHANGE_ADDRESS";
    public static final java.lang.String _CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public static final java.lang.String _CHANGE_LIFE_QUESTIONS = "CHANGE_LIFE_QUESTIONS";
    public static final java.lang.String _FAILED_CHANGE_PASSWORD_ATTEMPT = "FAILED_CHANGE_PASSWORD_ATTEMPT";
    public static final java.lang.String _CHANGE_ALERT_SETTINGS = "CHANGE_ALERT_SETTINGS";
    public static final java.lang.String _CHANGE_STATEMENT_SETTINGS = "CHANGE_STATEMENT_SETTINGS";
    public static final java.lang.String _CHANGE_AUTH_DATA = "CHANGE_AUTH_DATA";
    public static final java.lang.String _SEND_SECURE_MESSAGE = "SEND_SECURE_MESSAGE";
    public static final java.lang.String _READ_SECURE_MESSAGE = "READ_SECURE_MESSAGE";
    public static final java.lang.String _VIEW_CHECK = "VIEW_CHECK";
    public static final java.lang.String _VIEW_STATEMENT = "VIEW_STATEMENT";
    public static final java.lang.String _REQUEST_CHECK_COPY = "REQUEST_CHECK_COPY";
    public static final java.lang.String _REQUEST_STATEMENT_COPY = "REQUEST_STATEMENT_COPY";
    public static final java.lang.String _REQUEST_CHECKS = "REQUEST_CHECKS";
    public static final java.lang.String _REQUEST_NEW_CARD = "REQUEST_NEW_CARD";
    public static final java.lang.String _REQUEST_NEW_PIN = "REQUEST_NEW_PIN";
    public static final java.lang.String _EXTRA_AUTH = "EXTRA_AUTH";
    public static final java.lang.String _USER_DETAILS = "USER_DETAILS";
    public static final java.lang.String _CLIENT_DEFINED = "CLIENT_DEFINED";
    public static final java.lang.String _WITHDRAW = "WITHDRAW";
    public static final java.lang.String _CARD_PIN_CHANGE = "CARD_PIN_CHANGE";
    public static final EventType NULL = new EventType(_NULL);
    public static final EventType SESSION_SIGNIN = new EventType(_SESSION_SIGNIN);
    public static final EventType FAILED_LOGIN_ATTEMPT = new EventType(_FAILED_LOGIN_ATTEMPT);
    public static final EventType ENROLL = new EventType(_ENROLL);
    public static final EventType UPDATE_USER = new EventType(_UPDATE_USER);
    public static final EventType CREATE_USER = new EventType(_CREATE_USER);
    public static final EventType OLB_ENROLL = new EventType(_OLB_ENROLL);
    public static final EventType FAILED_OLB_ENROLL_ATTEMPT = new EventType(_FAILED_OLB_ENROLL_ATTEMPT);
    public static final EventType ACTIVATE_CARD = new EventType(_ACTIVATE_CARD);
    public static final EventType OPEN_NEW_ACCOUNT = new EventType(_OPEN_NEW_ACCOUNT);
    public static final EventType REQUEST_CREDIT = new EventType(_REQUEST_CREDIT);
    public static final EventType ADD_PAYEE = new EventType(_ADD_PAYEE);
    public static final EventType EDIT_PAYEE = new EventType(_EDIT_PAYEE);
    public static final EventType PAYMENT = new EventType(_PAYMENT);
    public static final EventType DEPOSIT = new EventType(_DEPOSIT);
    public static final EventType STOCK_TRADE = new EventType(_STOCK_TRADE);
    public static final EventType OPTIONS_TRADE = new EventType(_OPTIONS_TRADE);
    public static final EventType CHANGE_LOGIN_ID = new EventType(_CHANGE_LOGIN_ID);
    public static final EventType CHANGE_EMAIL = new EventType(_CHANGE_EMAIL);
    public static final EventType CHANGE_PHONE = new EventType(_CHANGE_PHONE);
    public static final EventType CHANGE_ADDRESS = new EventType(_CHANGE_ADDRESS);
    public static final EventType CHANGE_PASSWORD = new EventType(_CHANGE_PASSWORD);
    public static final EventType CHANGE_LIFE_QUESTIONS = new EventType(_CHANGE_LIFE_QUESTIONS);
    public static final EventType FAILED_CHANGE_PASSWORD_ATTEMPT = new EventType(_FAILED_CHANGE_PASSWORD_ATTEMPT);
    public static final EventType CHANGE_ALERT_SETTINGS = new EventType(_CHANGE_ALERT_SETTINGS);
    public static final EventType CHANGE_STATEMENT_SETTINGS = new EventType(_CHANGE_STATEMENT_SETTINGS);
    public static final EventType CHANGE_AUTH_DATA = new EventType(_CHANGE_AUTH_DATA);
    public static final EventType SEND_SECURE_MESSAGE = new EventType(_SEND_SECURE_MESSAGE);
    public static final EventType READ_SECURE_MESSAGE = new EventType(_READ_SECURE_MESSAGE);
    public static final EventType VIEW_CHECK = new EventType(_VIEW_CHECK);
    public static final EventType VIEW_STATEMENT = new EventType(_VIEW_STATEMENT);
    public static final EventType REQUEST_CHECK_COPY = new EventType(_REQUEST_CHECK_COPY);
    public static final EventType REQUEST_STATEMENT_COPY = new EventType(_REQUEST_STATEMENT_COPY);
    public static final EventType REQUEST_CHECKS = new EventType(_REQUEST_CHECKS);
    public static final EventType REQUEST_NEW_CARD = new EventType(_REQUEST_NEW_CARD);
    public static final EventType REQUEST_NEW_PIN = new EventType(_REQUEST_NEW_PIN);
    public static final EventType EXTRA_AUTH = new EventType(_EXTRA_AUTH);
    public static final EventType USER_DETAILS = new EventType(_USER_DETAILS);
    public static final EventType CLIENT_DEFINED = new EventType(_CLIENT_DEFINED);
    public static final EventType WITHDRAW = new EventType(_WITHDRAW);
    public static final EventType CARD_PIN_CHANGE = new EventType(_CARD_PIN_CHANGE);
    public java.lang.String getValue() { return _value_;}
    public static EventType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EventType enumeration = (EventType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EventType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EventType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
