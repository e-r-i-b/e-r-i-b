/**
 * DetailAcctInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Детальная информация по вкладу
 */
public class DetailAcctInfo_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type depAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepAccInfo_Type depAccInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    public DetailAcctInfo_Type() {
    }

    public DetailAcctInfo_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type depAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.DepAccInfo_Type depAccInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
           this.depAcctId = depAcctId;
           this.depAccInfo = depAccInfo;
           this.status = status;
    }


    /**
     * Gets the depAcctId value for this DetailAcctInfo_Type.
     * 
     * @return depAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type getDepAcctId() {
        return depAcctId;
    }


    /**
     * Sets the depAcctId value for this DetailAcctInfo_Type.
     * 
     * @param depAcctId
     */
    public void setDepAcctId(com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type depAcctId) {
        this.depAcctId = depAcctId;
    }


    /**
     * Gets the depAccInfo value for this DetailAcctInfo_Type.
     * 
     * @return depAccInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepAccInfo_Type getDepAccInfo() {
        return depAccInfo;
    }


    /**
     * Sets the depAccInfo value for this DetailAcctInfo_Type.
     * 
     * @param depAccInfo
     */
    public void setDepAccInfo(com.rssl.phizicgate.esberibgate.ws.generated.DepAccInfo_Type depAccInfo) {
        this.depAccInfo = depAccInfo;
    }


    /**
     * Gets the status value for this DetailAcctInfo_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DetailAcctInfo_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetailAcctInfo_Type)) return false;
        DetailAcctInfo_Type other = (DetailAcctInfo_Type) obj;
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
            ((this.depAccInfo==null && other.getDepAccInfo()==null) || 
             (this.depAccInfo!=null &&
              this.depAccInfo.equals(other.getDepAccInfo()))) &&
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
        if (getDepAcctId() != null) {
            _hashCode += getDepAcctId().hashCode();
        }
        if (getDepAccInfo() != null) {
            _hashCode += getDepAccInfo().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetailAcctInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DetailAcctInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAccInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAccInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAccInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
