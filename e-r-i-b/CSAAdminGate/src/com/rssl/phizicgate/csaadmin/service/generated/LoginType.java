/**
 * LoginType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;


/**
 * Логин
 */
public class LoginType  implements java.io.Serializable {
    private java.lang.Long id;

    private java.lang.String name;

    private java.lang.String password;

    private com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType accessScheme;

    private com.rssl.phizicgate.csaadmin.service.generated.LoginBlockType[] blocks;

    private java.lang.String lastUpdateDate;

    public LoginType() {
    }

    public LoginType(
           java.lang.Long id,
           java.lang.String name,
           java.lang.String password,
           com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType accessScheme,
           com.rssl.phizicgate.csaadmin.service.generated.LoginBlockType[] blocks,
           java.lang.String lastUpdateDate) {
           this.id = id;
           this.name = name;
           this.password = password;
           this.accessScheme = accessScheme;
           this.blocks = blocks;
           this.lastUpdateDate = lastUpdateDate;
    }


    /**
     * Gets the id value for this LoginType.
     * 
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this LoginType.
     * 
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }


    /**
     * Gets the name value for this LoginType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this LoginType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the password value for this LoginType.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this LoginType.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the accessScheme value for this LoginType.
     * 
     * @return accessScheme
     */
    public com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType getAccessScheme() {
        return accessScheme;
    }


    /**
     * Sets the accessScheme value for this LoginType.
     * 
     * @param accessScheme
     */
    public void setAccessScheme(com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType accessScheme) {
        this.accessScheme = accessScheme;
    }


    /**
     * Gets the blocks value for this LoginType.
     * 
     * @return blocks
     */
    public com.rssl.phizicgate.csaadmin.service.generated.LoginBlockType[] getBlocks() {
        return blocks;
    }


    /**
     * Sets the blocks value for this LoginType.
     * 
     * @param blocks
     */
    public void setBlocks(com.rssl.phizicgate.csaadmin.service.generated.LoginBlockType[] blocks) {
        this.blocks = blocks;
    }


    /**
     * Gets the lastUpdateDate value for this LoginType.
     * 
     * @return lastUpdateDate
     */
    public java.lang.String getLastUpdateDate() {
        return lastUpdateDate;
    }


    /**
     * Sets the lastUpdateDate value for this LoginType.
     * 
     * @param lastUpdateDate
     */
    public void setLastUpdateDate(java.lang.String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginType)) return false;
        LoginType other = (LoginType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.accessScheme==null && other.getAccessScheme()==null) || 
             (this.accessScheme!=null &&
              this.accessScheme.equals(other.getAccessScheme()))) &&
            ((this.blocks==null && other.getBlocks()==null) || 
             (this.blocks!=null &&
              java.util.Arrays.equals(this.blocks, other.getBlocks()))) &&
            ((this.lastUpdateDate==null && other.getLastUpdateDate()==null) || 
             (this.lastUpdateDate!=null &&
              this.lastUpdateDate.equals(other.getLastUpdateDate())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getAccessScheme() != null) {
            _hashCode += getAccessScheme().hashCode();
        }
        if (getBlocks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBlocks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBlocks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLastUpdateDate() != null) {
            _hashCode += getLastUpdateDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "id"));
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
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessScheme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "accessScheme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AccessSchemeType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blocks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "blocks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginBlockType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastUpdateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "lastUpdateDate"));
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
