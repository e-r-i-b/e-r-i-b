/**
 * Action_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Действие со счетом
 */
public class Action_Type  implements java.io.Serializable {
    /* Действие метода */
    private com.rssl.phizic.test.webgate.esberib.generated.ActionName_Type actionName;

    /* Для изменения варианта уплаты процентов */
    private com.rssl.phizic.test.webgate.esberib.generated.VariantInterestPayment_Type variantInterestPayment;

    /* Для изменения неснижаемого остатка */
    private com.rssl.phizic.test.webgate.esberib.generated.AlterMinRemainder_Type alterMinRemainder;

    public Action_Type() {
    }

    public Action_Type(
           com.rssl.phizic.test.webgate.esberib.generated.ActionName_Type actionName,
           com.rssl.phizic.test.webgate.esberib.generated.VariantInterestPayment_Type variantInterestPayment,
           com.rssl.phizic.test.webgate.esberib.generated.AlterMinRemainder_Type alterMinRemainder) {
           this.actionName = actionName;
           this.variantInterestPayment = variantInterestPayment;
           this.alterMinRemainder = alterMinRemainder;
    }


    /**
     * Gets the actionName value for this Action_Type.
     * 
     * @return actionName   * Действие метода
     */
    public com.rssl.phizic.test.webgate.esberib.generated.ActionName_Type getActionName() {
        return actionName;
    }


    /**
     * Sets the actionName value for this Action_Type.
     * 
     * @param actionName   * Действие метода
     */
    public void setActionName(com.rssl.phizic.test.webgate.esberib.generated.ActionName_Type actionName) {
        this.actionName = actionName;
    }


    /**
     * Gets the variantInterestPayment value for this Action_Type.
     * 
     * @return variantInterestPayment   * Для изменения варианта уплаты процентов
     */
    public com.rssl.phizic.test.webgate.esberib.generated.VariantInterestPayment_Type getVariantInterestPayment() {
        return variantInterestPayment;
    }


    /**
     * Sets the variantInterestPayment value for this Action_Type.
     * 
     * @param variantInterestPayment   * Для изменения варианта уплаты процентов
     */
    public void setVariantInterestPayment(com.rssl.phizic.test.webgate.esberib.generated.VariantInterestPayment_Type variantInterestPayment) {
        this.variantInterestPayment = variantInterestPayment;
    }


    /**
     * Gets the alterMinRemainder value for this Action_Type.
     * 
     * @return alterMinRemainder   * Для изменения неснижаемого остатка
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AlterMinRemainder_Type getAlterMinRemainder() {
        return alterMinRemainder;
    }


    /**
     * Sets the alterMinRemainder value for this Action_Type.
     * 
     * @param alterMinRemainder   * Для изменения неснижаемого остатка
     */
    public void setAlterMinRemainder(com.rssl.phizic.test.webgate.esberib.generated.AlterMinRemainder_Type alterMinRemainder) {
        this.alterMinRemainder = alterMinRemainder;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Action_Type)) return false;
        Action_Type other = (Action_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actionName==null && other.getActionName()==null) || 
             (this.actionName!=null &&
              this.actionName.equals(other.getActionName()))) &&
            ((this.variantInterestPayment==null && other.getVariantInterestPayment()==null) || 
             (this.variantInterestPayment!=null &&
              this.variantInterestPayment.equals(other.getVariantInterestPayment()))) &&
            ((this.alterMinRemainder==null && other.getAlterMinRemainder()==null) || 
             (this.alterMinRemainder!=null &&
              this.alterMinRemainder.equals(other.getAlterMinRemainder())));
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
        if (getActionName() != null) {
            _hashCode += getActionName().hashCode();
        }
        if (getVariantInterestPayment() != null) {
            _hashCode += getVariantInterestPayment().hashCode();
        }
        if (getAlterMinRemainder() != null) {
            _hashCode += getAlterMinRemainder().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Action_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Action_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ActionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ActionName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variantInterestPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VariantInterestPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VariantInterestPayment_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alterMinRemainder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AlterMinRemainder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AlterMinRemainder_Type"));
        elemField.setMinOccurs(0);
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
