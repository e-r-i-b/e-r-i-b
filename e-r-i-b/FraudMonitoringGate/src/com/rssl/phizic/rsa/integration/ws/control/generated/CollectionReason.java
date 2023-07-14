/**
 * CollectionReason.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class CollectionReason implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CollectionReason(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CSR_REQUESTED = "CSR_REQUESTED";
    public static final java.lang.String _USER_SETTINGS = "USER_SETTINGS";
    public static final java.lang.String _FIRST_COLLECTION = "FIRST_COLLECTION";
    public static final java.lang.String _REFRESH_AFTER_FAILURE = "REFRESH_AFTER_FAILURE";
    public static final java.lang.String _ADDITIONAL_COLLECTION = "ADDITIONAL_COLLECTION";
    public static final java.lang.String _REFRESH_COLLECTION = "REFRESH_COLLECTION";
    public static final CollectionReason CSR_REQUESTED = new CollectionReason(_CSR_REQUESTED);
    public static final CollectionReason USER_SETTINGS = new CollectionReason(_USER_SETTINGS);
    public static final CollectionReason FIRST_COLLECTION = new CollectionReason(_FIRST_COLLECTION);
    public static final CollectionReason REFRESH_AFTER_FAILURE = new CollectionReason(_REFRESH_AFTER_FAILURE);
    public static final CollectionReason ADDITIONAL_COLLECTION = new CollectionReason(_ADDITIONAL_COLLECTION);
    public static final CollectionReason REFRESH_COLLECTION = new CollectionReason(_REFRESH_COLLECTION);
    public java.lang.String getValue() { return _value_;}
    public static CollectionReason fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CollectionReason enumeration = (CollectionReason)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CollectionReason fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CollectionReason.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionReason"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
