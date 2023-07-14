/**
 * GetMultiNodeListParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public abstract class GetMultiNodeListParametersType  extends com.rssl.phizic.csaadmin.listeners.generated.GetMultiNodeParametersType  implements java.io.Serializable {
    private int firstResult;

    private int maxResults;

    private java.lang.String orderParameters;

    public GetMultiNodeListParametersType() {
    }

    public GetMultiNodeListParametersType(
           java.lang.String parameters,
           int firstResult,
           int maxResults,
           java.lang.String orderParameters) {
        super(
            parameters);
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.orderParameters = orderParameters;
    }


    /**
     * Gets the firstResult value for this GetMultiNodeListParametersType.
     * 
     * @return firstResult
     */
    public int getFirstResult() {
        return firstResult;
    }


    /**
     * Sets the firstResult value for this GetMultiNodeListParametersType.
     * 
     * @param firstResult
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }


    /**
     * Gets the maxResults value for this GetMultiNodeListParametersType.
     * 
     * @return maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }


    /**
     * Sets the maxResults value for this GetMultiNodeListParametersType.
     * 
     * @param maxResults
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }


    /**
     * Gets the orderParameters value for this GetMultiNodeListParametersType.
     * 
     * @return orderParameters
     */
    public java.lang.String getOrderParameters() {
        return orderParameters;
    }


    /**
     * Sets the orderParameters value for this GetMultiNodeListParametersType.
     * 
     * @param orderParameters
     */
    public void setOrderParameters(java.lang.String orderParameters) {
        this.orderParameters = orderParameters;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetMultiNodeListParametersType)) return false;
        GetMultiNodeListParametersType other = (GetMultiNodeListParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.firstResult == other.getFirstResult() &&
            this.maxResults == other.getMaxResults() &&
            ((this.orderParameters==null && other.getOrderParameters()==null) || 
             (this.orderParameters!=null &&
              this.orderParameters.equals(other.getOrderParameters())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        _hashCode += getFirstResult();
        _hashCode += getMaxResults();
        if (getOrderParameters() != null) {
            _hashCode += getOrderParameters().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetMultiNodeListParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMultiNodeListParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "firstResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "maxResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "orderParameters"));
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
