/**
 * ChallengeQuestionActionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class ChallengeQuestionActionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ChallengeQuestionActionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ADD_USER_QUESTION = "ADD_USER_QUESTION";
    public static final java.lang.String _BROWSE_QUESTION = "BROWSE_QUESTION";
    public static final java.lang.String _GET_USER_QUESTION = "GET_USER_QUESTION";
    public static final java.lang.String _SET_USER_QUESTION = "SET_USER_QUESTION";
    public static final ChallengeQuestionActionType ADD_USER_QUESTION = new ChallengeQuestionActionType(_ADD_USER_QUESTION);
    public static final ChallengeQuestionActionType BROWSE_QUESTION = new ChallengeQuestionActionType(_BROWSE_QUESTION);
    public static final ChallengeQuestionActionType GET_USER_QUESTION = new ChallengeQuestionActionType(_GET_USER_QUESTION);
    public static final ChallengeQuestionActionType SET_USER_QUESTION = new ChallengeQuestionActionType(_SET_USER_QUESTION);
    public java.lang.String getValue() { return _value_;}
    public static ChallengeQuestionActionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ChallengeQuestionActionType enumeration = (ChallengeQuestionActionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ChallengeQuestionActionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ChallengeQuestionActionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionActionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
