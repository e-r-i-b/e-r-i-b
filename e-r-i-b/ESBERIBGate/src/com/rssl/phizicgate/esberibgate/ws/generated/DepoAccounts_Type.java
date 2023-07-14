/**
 * DepoAccounts_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Список счетов депо.
 */
public class DepoAccounts_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepoAccount_Type[] depoAccount;

    public DepoAccounts_Type() {
    }

    public DepoAccounts_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAccount_Type[] depoAccount) {
           this.status = status;
           this.depoAccount = depoAccount;
    }


    /**
     * Gets the status value for this DepoAccounts_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepoAccounts_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the depoAccount value for this DepoAccounts_Type.
     * 
     * @return depoAccount
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoAccount_Type[] getDepoAccount() {
        return depoAccount;
    }


    /**
     * Sets the depoAccount value for this DepoAccounts_Type.
     * 
     * @param depoAccount
     */
    public void setDepoAccount(com.rssl.phizicgate.esberibgate.ws.generated.DepoAccount_Type[] depoAccount) {
        this.depoAccount = depoAccount;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepoAccount_Type getDepoAccount(int i) {
        return this.depoAccount[i];
    }

    public void setDepoAccount(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepoAccount_Type _value) {
        this.depoAccount[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAccounts_Type)) return false;
        DepoAccounts_Type other = (DepoAccounts_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.depoAccount==null && other.getDepoAccount()==null) || 
             (this.depoAccount!=null &&
              java.util.Arrays.equals(this.depoAccount, other.getDepoAccount())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getDepoAccount() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepoAccount());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepoAccount(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAccounts_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccounts_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
