/**
 * EmployeeMailManagerFilterParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class EmployeeMailManagerFilterParametersType  implements java.io.Serializable {
    private int firstResult;

    private int maxResults;

    private java.lang.String soughtBlockedUntil;

    private java.lang.Long soughtId;

    private java.lang.String soughtFIO;

    private java.lang.String soughtArea;

    public EmployeeMailManagerFilterParametersType() {
    }

    public EmployeeMailManagerFilterParametersType(
           int firstResult,
           int maxResults,
           java.lang.String soughtBlockedUntil,
           java.lang.Long soughtId,
           java.lang.String soughtFIO,
           java.lang.String soughtArea) {
           this.firstResult = firstResult;
           this.maxResults = maxResults;
           this.soughtBlockedUntil = soughtBlockedUntil;
           this.soughtId = soughtId;
           this.soughtFIO = soughtFIO;
           this.soughtArea = soughtArea;
    }


    /**
     * Gets the firstResult value for this EmployeeMailManagerFilterParametersType.
     * 
     * @return firstResult
     */
    public int getFirstResult() {
        return firstResult;
    }


    /**
     * Sets the firstResult value for this EmployeeMailManagerFilterParametersType.
     * 
     * @param firstResult
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }


    /**
     * Gets the maxResults value for this EmployeeMailManagerFilterParametersType.
     * 
     * @return maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }


    /**
     * Sets the maxResults value for this EmployeeMailManagerFilterParametersType.
     * 
     * @param maxResults
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }


    /**
     * Gets the soughtBlockedUntil value for this EmployeeMailManagerFilterParametersType.
     * 
     * @return soughtBlockedUntil
     */
    public java.lang.String getSoughtBlockedUntil() {
        return soughtBlockedUntil;
    }


    /**
     * Sets the soughtBlockedUntil value for this EmployeeMailManagerFilterParametersType.
     * 
     * @param soughtBlockedUntil
     */
    public void setSoughtBlockedUntil(java.lang.String soughtBlockedUntil) {
        this.soughtBlockedUntil = soughtBlockedUntil;
    }


    /**
     * Gets the soughtId value for this EmployeeMailManagerFilterParametersType.
     * 
     * @return soughtId
     */
    public java.lang.Long getSoughtId() {
        return soughtId;
    }


    /**
     * Sets the soughtId value for this EmployeeMailManagerFilterParametersType.
     * 
     * @param soughtId
     */
    public void setSoughtId(java.lang.Long soughtId) {
        this.soughtId = soughtId;
    }


    /**
     * Gets the soughtFIO value for this EmployeeMailManagerFilterParametersType.
     * 
     * @return soughtFIO
     */
    public java.lang.String getSoughtFIO() {
        return soughtFIO;
    }


    /**
     * Sets the soughtFIO value for this EmployeeMailManagerFilterParametersType.
     * 
     * @param soughtFIO
     */
    public void setSoughtFIO(java.lang.String soughtFIO) {
        this.soughtFIO = soughtFIO;
    }


    /**
     * Gets the soughtArea value for this EmployeeMailManagerFilterParametersType.
     * 
     * @return soughtArea
     */
    public java.lang.String getSoughtArea() {
        return soughtArea;
    }


    /**
     * Sets the soughtArea value for this EmployeeMailManagerFilterParametersType.
     * 
     * @param soughtArea
     */
    public void setSoughtArea(java.lang.String soughtArea) {
        this.soughtArea = soughtArea;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmployeeMailManagerFilterParametersType)) return false;
        EmployeeMailManagerFilterParametersType other = (EmployeeMailManagerFilterParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.firstResult == other.getFirstResult() &&
            this.maxResults == other.getMaxResults() &&
            ((this.soughtBlockedUntil==null && other.getSoughtBlockedUntil()==null) || 
             (this.soughtBlockedUntil!=null &&
              this.soughtBlockedUntil.equals(other.getSoughtBlockedUntil()))) &&
            ((this.soughtId==null && other.getSoughtId()==null) || 
             (this.soughtId!=null &&
              this.soughtId.equals(other.getSoughtId()))) &&
            ((this.soughtFIO==null && other.getSoughtFIO()==null) || 
             (this.soughtFIO!=null &&
              this.soughtFIO.equals(other.getSoughtFIO()))) &&
            ((this.soughtArea==null && other.getSoughtArea()==null) || 
             (this.soughtArea!=null &&
              this.soughtArea.equals(other.getSoughtArea())));
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
        _hashCode += getFirstResult();
        _hashCode += getMaxResults();
        if (getSoughtBlockedUntil() != null) {
            _hashCode += getSoughtBlockedUntil().hashCode();
        }
        if (getSoughtId() != null) {
            _hashCode += getSoughtId().hashCode();
        }
        if (getSoughtFIO() != null) {
            _hashCode += getSoughtFIO().hashCode();
        }
        if (getSoughtArea() != null) {
            _hashCode += getSoughtArea().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmployeeMailManagerFilterParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeMailManagerFilterParametersType"));
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
        elemField.setFieldName("soughtBlockedUntil");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtBlockedUntil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtFIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtFIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtArea"));
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
