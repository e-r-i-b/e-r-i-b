/**
 * PrivateLoanDetails_TypeAccounts.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class PrivateLoanDetails_TypeAccounts  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsCardElement[] card;

    private com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsAccountElement[] account;

    public PrivateLoanDetails_TypeAccounts() {
    }

    public PrivateLoanDetails_TypeAccounts(
           com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsCardElement[] card,
           com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsAccountElement[] account) {
           this.card = card;
           this.account = account;
    }


    /**
     * Gets the card value for this PrivateLoanDetails_TypeAccounts.
     * 
     * @return card
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsCardElement[] getCard() {
        return card;
    }


    /**
     * Sets the card value for this PrivateLoanDetails_TypeAccounts.
     * 
     * @param card
     */
    public void setCard(com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsCardElement[] card) {
        this.card = card;
    }


    /**
     * Gets the account value for this PrivateLoanDetails_TypeAccounts.
     * 
     * @return account
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsAccountElement[] getAccount() {
        return account;
    }


    /**
     * Sets the account value for this PrivateLoanDetails_TypeAccounts.
     * 
     * @param account
     */
    public void setAccount(com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccountsAccountElement[] account) {
        this.account = account;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PrivateLoanDetails_TypeAccounts)) return false;
        PrivateLoanDetails_TypeAccounts other = (PrivateLoanDetails_TypeAccounts) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.card==null && other.getCard()==null) || 
             (this.card!=null &&
              java.util.Arrays.equals(this.card, other.getCard()))) &&
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              java.util.Arrays.equals(this.account, other.getAccount())));
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
        if (getCard() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCard());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCard(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAccount() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccount());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccount(), i);
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
        new org.apache.axis.description.TypeDesc(PrivateLoanDetails_TypeAccounts.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PrivateLoanDetails_Type>Accounts"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("card");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Card"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PrivateLoanDetails_Type>Accounts>Card>Element"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Element"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PrivateLoanDetails_Type>Accounts>Account>Element"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Element"));
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
