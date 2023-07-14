/**
 * ATMLocationTypes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class ATMLocationTypes implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ATMLocationTypes(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BANK_BRANCH = "BANK_BRANCH";
    public static final java.lang.String _PETROL_STATION = "PETROL_STATION";
    public static final java.lang.String _PUBLIC_TRANSPORT = "PUBLIC_TRANSPORT";
    public static final java.lang.String _STREET = "STREET";
    public static final java.lang.String _CONVENIENCE_STORE = "CONVENIENCE_STORE";
    public static final java.lang.String _SUPERMARKET = "SUPERMARKET";
    public static final java.lang.String _LEISURE_FACILITY = "LEISURE_FACILITY";
    public static final java.lang.String _DRIVE_THRU = "DRIVE_THRU";
    public static final java.lang.String _ENTERTAINMENT_VENUE = "ENTERTAINMENT_VENUE";
    public static final java.lang.String _TRANSPORT_TERMINAL = "TRANSPORT_TERMINAL";
    public static final java.lang.String _POST_OFFICE = "POST_OFFICE";
    public static final java.lang.String _RETAIL_OUTLET = "RETAIL_OUTLET";
    public static final java.lang.String _CASINO = "CASINO";
    public static final java.lang.String _GOVERNMENT_OFFICE = "GOVERNMENT_OFFICE";
    public static final java.lang.String _OTHER = "OTHER";
    public static final ATMLocationTypes BANK_BRANCH = new ATMLocationTypes(_BANK_BRANCH);
    public static final ATMLocationTypes PETROL_STATION = new ATMLocationTypes(_PETROL_STATION);
    public static final ATMLocationTypes PUBLIC_TRANSPORT = new ATMLocationTypes(_PUBLIC_TRANSPORT);
    public static final ATMLocationTypes STREET = new ATMLocationTypes(_STREET);
    public static final ATMLocationTypes CONVENIENCE_STORE = new ATMLocationTypes(_CONVENIENCE_STORE);
    public static final ATMLocationTypes SUPERMARKET = new ATMLocationTypes(_SUPERMARKET);
    public static final ATMLocationTypes LEISURE_FACILITY = new ATMLocationTypes(_LEISURE_FACILITY);
    public static final ATMLocationTypes DRIVE_THRU = new ATMLocationTypes(_DRIVE_THRU);
    public static final ATMLocationTypes ENTERTAINMENT_VENUE = new ATMLocationTypes(_ENTERTAINMENT_VENUE);
    public static final ATMLocationTypes TRANSPORT_TERMINAL = new ATMLocationTypes(_TRANSPORT_TERMINAL);
    public static final ATMLocationTypes POST_OFFICE = new ATMLocationTypes(_POST_OFFICE);
    public static final ATMLocationTypes RETAIL_OUTLET = new ATMLocationTypes(_RETAIL_OUTLET);
    public static final ATMLocationTypes CASINO = new ATMLocationTypes(_CASINO);
    public static final ATMLocationTypes GOVERNMENT_OFFICE = new ATMLocationTypes(_GOVERNMENT_OFFICE);
    public static final ATMLocationTypes OTHER = new ATMLocationTypes(_OTHER);
    public java.lang.String getValue() { return _value_;}
    public static ATMLocationTypes fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ATMLocationTypes enumeration = (ATMLocationTypes)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ATMLocationTypes fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ATMLocationTypes.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATMLocationTypes"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
