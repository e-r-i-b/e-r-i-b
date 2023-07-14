/**
 * DepoAccSecRegRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Подача заявки на регистрацию новой ценной бумаги ДЕПО
 */
public class DepoAccSecRegRqType  extends com.rssl.phizicgate.esberibgate.ws.generated.DepoAccInfoRqType  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationInfo_Type operationInfo;

    public DepoAccSecRegRqType() {
    }

    public DepoAccSecRegRqType(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type[] depoAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.OperInfo_Type operInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationInfo_Type operationInfo) {
        super(
            rqUID,
            rqTm,
            operUID,
            SPName,
            bankInfo,
            depoAcctId,
            operInfo);
        this.operationInfo = operationInfo;
    }


    /**
     * Gets the operationInfo value for this DepoAccSecRegRqType.
     * 
     * @return operationInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationInfo_Type getOperationInfo() {
        return operationInfo;
    }


    /**
     * Sets the operationInfo value for this DepoAccSecRegRqType.
     * 
     * @param operationInfo
     */
    public void setOperationInfo(com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationInfo_Type operationInfo) {
        this.operationInfo = operationInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAccSecRegRqType)) return false;
        DepoAccSecRegRqType other = (DepoAccSecRegRqType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.operationInfo==null && other.getOperationInfo()==null) || 
             (this.operationInfo!=null &&
              this.operationInfo.equals(other.getOperationInfo())));
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
        if (getOperationInfo() != null) {
            _hashCode += getOperationInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAccSecRegRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccSecRegRqType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperationInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationInfo_Type"));
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
