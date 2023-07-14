/**
 * GenericActionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class GenericActionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected GenericActionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _GET_USER_STATUS = "GET_USER_STATUS";
    public static final java.lang.String _GET_USER_PREFERENCE = "GET_USER_PREFERENCE";
    public static final java.lang.String _GET_USER_GROUP = "GET_USER_GROUP";
    public static final java.lang.String _GET_SYSTEM_CREDENTIAL = "GET_SYSTEM_CREDENTIAL";
    public static final java.lang.String _GET_USER_CREDENTIAL = "GET_USER_CREDENTIAL";
    public static final java.lang.String _GET_SYSTEM_CREDENTIAL_EXTENDED = "GET_SYSTEM_CREDENTIAL_EXTENDED";
    public static final java.lang.String _GET_USER_CREDENTIAL_EXTENDED = "GET_USER_CREDENTIAL_EXTENDED";
    public static final java.lang.String _BROWSE_USER_GROUP = "BROWSE_USER_GROUP";
    public static final java.lang.String _SET_USER_GROUP = "SET_USER_GROUP";
    public static final java.lang.String _UPDATE_USER_NAME = "UPDATE_USER_NAME";
    public static final java.lang.String _SET_USER_STATUS = "SET_USER_STATUS";
    public static final java.lang.String _SET_USER_PREFERENCE = "SET_USER_PREFERENCE";
    public static final java.lang.String _ADD_FAVORITE = "ADD_FAVORITE";
    public static final java.lang.String _DEL_FAVORITE = "DEL_FAVORITE";
    public static final java.lang.String _CLEAR_FAVORITES = "CLEAR_FAVORITES";
    public static final java.lang.String _GET_FAVORITES = "GET_FAVORITES";
    public static final java.lang.String _OPEN_SESSION = "OPEN_SESSION";
    public static final java.lang.String _CLOSE_SESSION = "CLOSE_SESSION";
    public static final java.lang.String _COMMIT = "COMMIT";
    public static final java.lang.String _CANCEL = "CANCEL";
    public static final GenericActionType GET_USER_STATUS = new GenericActionType(_GET_USER_STATUS);
    public static final GenericActionType GET_USER_PREFERENCE = new GenericActionType(_GET_USER_PREFERENCE);
    public static final GenericActionType GET_USER_GROUP = new GenericActionType(_GET_USER_GROUP);
    public static final GenericActionType GET_SYSTEM_CREDENTIAL = new GenericActionType(_GET_SYSTEM_CREDENTIAL);
    public static final GenericActionType GET_USER_CREDENTIAL = new GenericActionType(_GET_USER_CREDENTIAL);
    public static final GenericActionType GET_SYSTEM_CREDENTIAL_EXTENDED = new GenericActionType(_GET_SYSTEM_CREDENTIAL_EXTENDED);
    public static final GenericActionType GET_USER_CREDENTIAL_EXTENDED = new GenericActionType(_GET_USER_CREDENTIAL_EXTENDED);
    public static final GenericActionType BROWSE_USER_GROUP = new GenericActionType(_BROWSE_USER_GROUP);
    public static final GenericActionType SET_USER_GROUP = new GenericActionType(_SET_USER_GROUP);
    public static final GenericActionType UPDATE_USER_NAME = new GenericActionType(_UPDATE_USER_NAME);
    public static final GenericActionType SET_USER_STATUS = new GenericActionType(_SET_USER_STATUS);
    public static final GenericActionType SET_USER_PREFERENCE = new GenericActionType(_SET_USER_PREFERENCE);
    public static final GenericActionType ADD_FAVORITE = new GenericActionType(_ADD_FAVORITE);
    public static final GenericActionType DEL_FAVORITE = new GenericActionType(_DEL_FAVORITE);
    public static final GenericActionType CLEAR_FAVORITES = new GenericActionType(_CLEAR_FAVORITES);
    public static final GenericActionType GET_FAVORITES = new GenericActionType(_GET_FAVORITES);
    public static final GenericActionType OPEN_SESSION = new GenericActionType(_OPEN_SESSION);
    public static final GenericActionType CLOSE_SESSION = new GenericActionType(_CLOSE_SESSION);
    public static final GenericActionType COMMIT = new GenericActionType(_COMMIT);
    public static final GenericActionType CANCEL = new GenericActionType(_CANCEL);
    public java.lang.String getValue() { return _value_;}
    public static GenericActionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        GenericActionType enumeration = (GenericActionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static GenericActionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(GenericActionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericActionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
