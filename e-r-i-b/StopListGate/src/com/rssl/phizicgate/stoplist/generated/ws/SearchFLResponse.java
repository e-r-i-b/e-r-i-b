/**
 * SearchFLResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.stoplist.generated.ws;

public class SearchFLResponse  implements java.io.Serializable {
    private java.lang.String searchFLReturn;

    public SearchFLResponse() {
    }

    public SearchFLResponse(
           java.lang.String searchFLReturn) {
           this.searchFLReturn = searchFLReturn;
    }


    /**
     * Gets the searchFLReturn value for this SearchFLResponse.
     * 
     * @return searchFLReturn
     */
    public java.lang.String getSearchFLReturn() {
        return searchFLReturn;
    }


    /**
     * Sets the searchFLReturn value for this SearchFLResponse.
     * 
     * @param searchFLReturn
     */
    public void setSearchFLReturn(java.lang.String searchFLReturn) {
        this.searchFLReturn = searchFLReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchFLResponse)) return false;
        SearchFLResponse other = (SearchFLResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.searchFLReturn==null && other.getSearchFLReturn()==null) || 
             (this.searchFLReturn!=null &&
              this.searchFLReturn.equals(other.getSearchFLReturn())));
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
        if (getSearchFLReturn() != null) {
            _hashCode += getSearchFLReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchFLResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://search.sbrf.ru", ">searchFLResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchFLReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://search.sbrf.ru", "searchFLReturn"));
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
