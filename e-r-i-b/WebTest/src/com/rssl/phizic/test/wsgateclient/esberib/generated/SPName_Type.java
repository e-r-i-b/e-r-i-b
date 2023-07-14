/**
 * SPName_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.esberib.generated;

public class SPName_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    public SPName_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BP_ES = "BP_ES";
    public static final java.lang.String _BP_VSP = "BP_VSP";
    public static final java.lang.String _BP_ERIB = "BP_ERIB";
    public static final java.lang.String _BP_COD = "BP_COD";
    public static final java.lang.String _BP_ESK = "BP_ESK";
    public static final java.lang.String _BP_IASK = "BP_IASK";
    public static final java.lang.String _BP_WAY = "BP_WAY";
    public static final java.lang.String _BP_DEPO = "BP_DEPO";
    public static final java.lang.String _AUTOPAY = "urn:sbrfsystems:99-autopay";
    public static final SPName_Type BP_ES = new SPName_Type(_BP_ES);
    public static final SPName_Type BP_VSP = new SPName_Type(_BP_VSP);
    public static final SPName_Type BP_ERIB = new SPName_Type(_BP_ERIB);
    public static final SPName_Type BP_COD = new SPName_Type(_BP_COD);
    public static final SPName_Type BP_ESK = new SPName_Type(_BP_ESK);
    public static final SPName_Type BP_IASK = new SPName_Type(_BP_IASK);
    public static final SPName_Type BP_WAY = new SPName_Type(_BP_WAY);
    public static final SPName_Type BP_DEPO = new SPName_Type(_BP_DEPO  );
    public static final SPName_Type AUTOPAY = new SPName_Type(_AUTOPAY);
    public java.lang.String getValue() { return _value_;}
    public static SPName_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SPName_Type enumeration = (SPName_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SPName_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SPName_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
