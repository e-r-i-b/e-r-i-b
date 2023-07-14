/**
 * AutopayTypeList_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Список типов Автоплатежей
 */
public class AutopayTypeList_Type  implements java.io.Serializable {
    /* Код события, который определяет исполнение подписки */
    private com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type exeEventCode;

    /* Алгоритм расчета суммы очередного автоплатежа */
    private com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type summaKindCode;

    public AutopayTypeList_Type() {
    }

    public AutopayTypeList_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type exeEventCode,
           com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type summaKindCode) {
           this.exeEventCode = exeEventCode;
           this.summaKindCode = summaKindCode;
    }


    /**
     * Gets the exeEventCode value for this AutopayTypeList_Type.
     * 
     * @return exeEventCode   * Код события, который определяет исполнение подписки
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type getExeEventCode() {
        return exeEventCode;
    }


    /**
     * Sets the exeEventCode value for this AutopayTypeList_Type.
     * 
     * @param exeEventCode   * Код события, который определяет исполнение подписки
     */
    public void setExeEventCode(com.rssl.phizicgate.esberibgate.ws.generated.ExeEventCodeASAP_Type exeEventCode) {
        this.exeEventCode = exeEventCode;
    }


    /**
     * Gets the summaKindCode value for this AutopayTypeList_Type.
     * 
     * @return summaKindCode   * Алгоритм расчета суммы очередного автоплатежа
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type getSummaKindCode() {
        return summaKindCode;
    }


    /**
     * Sets the summaKindCode value for this AutopayTypeList_Type.
     * 
     * @param summaKindCode   * Алгоритм расчета суммы очередного автоплатежа
     */
    public void setSummaKindCode(com.rssl.phizicgate.esberibgate.ws.generated.SummaKindCodeASAP_Type summaKindCode) {
        this.summaKindCode = summaKindCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutopayTypeList_Type)) return false;
        AutopayTypeList_Type other = (AutopayTypeList_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.exeEventCode==null && other.getExeEventCode()==null) || 
             (this.exeEventCode!=null &&
              this.exeEventCode.equals(other.getExeEventCode()))) &&
            ((this.summaKindCode==null && other.getSummaKindCode()==null) || 
             (this.summaKindCode!=null &&
              this.summaKindCode.equals(other.getSummaKindCode())));
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
        if (getExeEventCode() != null) {
            _hashCode += getExeEventCode().hashCode();
        }
        if (getSummaKindCode() != null) {
            _hashCode += getSummaKindCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutopayTypeList_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayTypeList_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exeEventCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExeEventCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExeEventCodeASAP_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summaKindCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaKindCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaKindCodeASAP_Type"));
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
