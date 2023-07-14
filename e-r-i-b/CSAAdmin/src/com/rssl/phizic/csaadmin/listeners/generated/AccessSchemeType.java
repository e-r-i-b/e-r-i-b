/**
 * AccessSchemeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;


/**
 * Схема прав
 */
public class AccessSchemeType  implements java.io.Serializable {
    private java.lang.Long externalId;

    private java.lang.String name;

    private java.lang.String category;

    private boolean CAAdminScheme;

    private boolean VSPEmployeeScheme;

    private boolean mailManagement;

    public AccessSchemeType() {
    }

    public AccessSchemeType(
           java.lang.Long externalId,
           java.lang.String name,
           java.lang.String category,
           boolean CAAdminScheme,
           boolean VSPEmployeeScheme,
           boolean mailManagement) {
           this.externalId = externalId;
           this.name = name;
           this.category = category;
           this.CAAdminScheme = CAAdminScheme;
           this.VSPEmployeeScheme = VSPEmployeeScheme;
           this.mailManagement = mailManagement;
    }


    /**
     * Gets the externalId value for this AccessSchemeType.
     * 
     * @return externalId
     */
    public java.lang.Long getExternalId() {
        return externalId;
    }


    /**
     * Sets the externalId value for this AccessSchemeType.
     * 
     * @param externalId
     */
    public void setExternalId(java.lang.Long externalId) {
        this.externalId = externalId;
    }


    /**
     * Gets the name value for this AccessSchemeType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this AccessSchemeType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the category value for this AccessSchemeType.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this AccessSchemeType.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the CAAdminScheme value for this AccessSchemeType.
     * 
     * @return CAAdminScheme
     */
    public boolean isCAAdminScheme() {
        return CAAdminScheme;
    }


    /**
     * Sets the CAAdminScheme value for this AccessSchemeType.
     * 
     * @param CAAdminScheme
     */
    public void setCAAdminScheme(boolean CAAdminScheme) {
        this.CAAdminScheme = CAAdminScheme;
    }


    /**
     * Gets the VSPEmployeeScheme value for this AccessSchemeType.
     * 
     * @return VSPEmployeeScheme
     */
    public boolean isVSPEmployeeScheme() {
        return VSPEmployeeScheme;
    }


    /**
     * Sets the VSPEmployeeScheme value for this AccessSchemeType.
     * 
     * @param VSPEmployeeScheme
     */
    public void setVSPEmployeeScheme(boolean VSPEmployeeScheme) {
        this.VSPEmployeeScheme = VSPEmployeeScheme;
    }


    /**
     * Gets the mailManagement value for this AccessSchemeType.
     * 
     * @return mailManagement
     */
    public boolean isMailManagement() {
        return mailManagement;
    }


    /**
     * Sets the mailManagement value for this AccessSchemeType.
     * 
     * @param mailManagement
     */
    public void setMailManagement(boolean mailManagement) {
        this.mailManagement = mailManagement;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccessSchemeType)) return false;
        AccessSchemeType other = (AccessSchemeType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.externalId==null && other.getExternalId()==null) || 
             (this.externalId!=null &&
              this.externalId.equals(other.getExternalId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            this.CAAdminScheme == other.isCAAdminScheme() &&
            this.VSPEmployeeScheme == other.isVSPEmployeeScheme() &&
            this.mailManagement == other.isMailManagement();
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
        if (getExternalId() != null) {
            _hashCode += getExternalId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        _hashCode += (isCAAdminScheme() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isVSPEmployeeScheme() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isMailManagement() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccessSchemeType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AccessSchemeType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "externalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CAAdminScheme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "CAAdminScheme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VSPEmployeeScheme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "VSPEmployeeScheme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailManagement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "mailManagement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
