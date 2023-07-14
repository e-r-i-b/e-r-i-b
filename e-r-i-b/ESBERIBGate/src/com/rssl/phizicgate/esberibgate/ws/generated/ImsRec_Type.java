/**
 * ImsRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация по ОМС
 */
public class ImsRec_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctId_Type imsAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type imsAcctInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    public ImsRec_Type() {
    }

    public ImsRec_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctId_Type imsAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type imsAcctInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
           this.imsAcctId = imsAcctId;
           this.imsAcctInfo = imsAcctInfo;
           this.status = status;
    }


    /**
     * Gets the imsAcctId value for this ImsRec_Type.
     * 
     * @return imsAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctId_Type getImsAcctId() {
        return imsAcctId;
    }


    /**
     * Sets the imsAcctId value for this ImsRec_Type.
     * 
     * @param imsAcctId
     */
    public void setImsAcctId(com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctId_Type imsAcctId) {
        this.imsAcctId = imsAcctId;
    }


    /**
     * Gets the imsAcctInfo value for this ImsRec_Type.
     * 
     * @return imsAcctInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type getImsAcctInfo() {
        return imsAcctInfo;
    }


    /**
     * Sets the imsAcctInfo value for this ImsRec_Type.
     * 
     * @param imsAcctInfo
     */
    public void setImsAcctInfo(com.rssl.phizicgate.esberibgate.ws.generated.ImsAcctInfo_Type imsAcctInfo) {
        this.imsAcctInfo = imsAcctInfo;
    }


    /**
     * Gets the status value for this ImsRec_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ImsRec_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImsRec_Type)) return false;
        ImsRec_Type other = (ImsRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.imsAcctId==null && other.getImsAcctId()==null) || 
             (this.imsAcctId!=null &&
              this.imsAcctId.equals(other.getImsAcctId()))) &&
            ((this.imsAcctInfo==null && other.getImsAcctInfo()==null) || 
             (this.imsAcctInfo!=null &&
              this.imsAcctInfo.equals(other.getImsAcctInfo()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getImsAcctId() != null) {
            _hashCode += getImsAcctId().hashCode();
        }
        if (getImsAcctInfo() != null) {
            _hashCode += getImsAcctInfo().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImsRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imsAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imsAcctInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
