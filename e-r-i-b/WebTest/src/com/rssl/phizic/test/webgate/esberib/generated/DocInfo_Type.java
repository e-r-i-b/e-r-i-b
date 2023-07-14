/**
 * DocInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о переводе
 */
public class DocInfo_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId;

    /* Действия с вкладом: 1 – расторгнуть договор с закрытием счета
     * по вкладу, 2 – выдать дубликат сберкнижки */
    private java.lang.String activity;

    /* Номер счета, для перечисления средств со вклада */
    private java.lang.String recAcc;

    /* Выдать наличными */
    private java.lang.Boolean isByCash;

    public DocInfo_Type() {
    }

    public DocInfo_Type(
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId,
           java.lang.String activity,
           java.lang.String recAcc,
           java.lang.Boolean isByCash) {
           this.depAcctId = depAcctId;
           this.activity = activity;
           this.recAcc = recAcc;
           this.isByCash = isByCash;
    }


    /**
     * Gets the depAcctId value for this DocInfo_Type.
     * 
     * @return depAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctId() {
        return depAcctId;
    }


    /**
     * Sets the depAcctId value for this DocInfo_Type.
     * 
     * @param depAcctId
     */
    public void setDepAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId) {
        this.depAcctId = depAcctId;
    }


    /**
     * Gets the activity value for this DocInfo_Type.
     * 
     * @return activity   * Действия с вкладом: 1 – расторгнуть договор с закрытием счета
     * по вкладу, 2 – выдать дубликат сберкнижки
     */
    public java.lang.String getActivity() {
        return activity;
    }


    /**
     * Sets the activity value for this DocInfo_Type.
     * 
     * @param activity   * Действия с вкладом: 1 – расторгнуть договор с закрытием счета
     * по вкладу, 2 – выдать дубликат сберкнижки
     */
    public void setActivity(java.lang.String activity) {
        this.activity = activity;
    }


    /**
     * Gets the recAcc value for this DocInfo_Type.
     * 
     * @return recAcc   * Номер счета, для перечисления средств со вклада
     */
    public java.lang.String getRecAcc() {
        return recAcc;
    }


    /**
     * Sets the recAcc value for this DocInfo_Type.
     * 
     * @param recAcc   * Номер счета, для перечисления средств со вклада
     */
    public void setRecAcc(java.lang.String recAcc) {
        this.recAcc = recAcc;
    }


    /**
     * Gets the isByCash value for this DocInfo_Type.
     * 
     * @return isByCash   * Выдать наличными
     */
    public java.lang.Boolean getIsByCash() {
        return isByCash;
    }


    /**
     * Sets the isByCash value for this DocInfo_Type.
     * 
     * @param isByCash   * Выдать наличными
     */
    public void setIsByCash(java.lang.Boolean isByCash) {
        this.isByCash = isByCash;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocInfo_Type)) return false;
        DocInfo_Type other = (DocInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depAcctId==null && other.getDepAcctId()==null) || 
             (this.depAcctId!=null &&
              this.depAcctId.equals(other.getDepAcctId()))) &&
            ((this.activity==null && other.getActivity()==null) || 
             (this.activity!=null &&
              this.activity.equals(other.getActivity()))) &&
            ((this.recAcc==null && other.getRecAcc()==null) || 
             (this.recAcc!=null &&
              this.recAcc.equals(other.getRecAcc()))) &&
            ((this.isByCash==null && other.getIsByCash()==null) || 
             (this.isByCash!=null &&
              this.isByCash.equals(other.getIsByCash())));
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
        if (getDepAcctId() != null) {
            _hashCode += getDepAcctId().hashCode();
        }
        if (getActivity() != null) {
            _hashCode += getActivity().hashCode();
        }
        if (getRecAcc() != null) {
            _hashCode += getRecAcc().hashCode();
        }
        if (getIsByCash() != null) {
            _hashCode += getIsByCash().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Activity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recAcc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecAcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isByCash");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsByCash"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
