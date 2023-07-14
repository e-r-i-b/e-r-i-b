/**
 * DepoAcctRes_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запись о счете депо
 */
public class DepoAcctRes_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type depoAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type acctBal;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctInfo_Type acctInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type custRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    public DepoAcctRes_Type() {
    }

    public DepoAcctRes_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type depoAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type acctBal,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctInfo_Type acctInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type custRec,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
           this.depoAcctId = depoAcctId;
           this.acctBal = acctBal;
           this.acctInfo = acctInfo;
           this.custRec = custRec;
           this.status = status;
    }


    /**
     * Gets the depoAcctId value for this DepoAcctRes_Type.
     * 
     * @return depoAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type getDepoAcctId() {
        return depoAcctId;
    }


    /**
     * Sets the depoAcctId value for this DepoAcctRes_Type.
     * 
     * @param depoAcctId
     */
    public void setDepoAcctId(com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type depoAcctId) {
        this.depoAcctId = depoAcctId;
    }


    /**
     * Gets the acctBal value for this DepoAcctRes_Type.
     * 
     * @return acctBal
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this DepoAcctRes_Type.
     * 
     * @param acctBal
     */
    public void setAcctBal(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type acctBal) {
        this.acctBal = acctBal;
    }


    /**
     * Gets the acctInfo value for this DepoAcctRes_Type.
     * 
     * @return acctInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctInfo_Type getAcctInfo() {
        return acctInfo;
    }


    /**
     * Sets the acctInfo value for this DepoAcctRes_Type.
     * 
     * @param acctInfo
     */
    public void setAcctInfo(com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctInfo_Type acctInfo) {
        this.acctInfo = acctInfo;
    }


    /**
     * Gets the custRec value for this DepoAcctRes_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this DepoAcctRes_Type.
     * 
     * @param custRec
     */
    public void setCustRec(com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type custRec) {
        this.custRec = custRec;
    }


    /**
     * Gets the status value for this DepoAcctRes_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepoAcctRes_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAcctRes_Type)) return false;
        DepoAcctRes_Type other = (DepoAcctRes_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.depoAcctId==null && other.getDepoAcctId()==null) || 
             (this.depoAcctId!=null &&
              this.depoAcctId.equals(other.getDepoAcctId()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              this.acctBal.equals(other.getAcctBal()))) &&
            ((this.acctInfo==null && other.getAcctInfo()==null) || 
             (this.acctInfo!=null &&
              this.acctInfo.equals(other.getAcctInfo()))) &&
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              this.custRec.equals(other.getCustRec()))) &&
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
        if (getDepoAcctId() != null) {
            _hashCode += getDepoAcctId().hashCode();
        }
        if (getAcctBal() != null) {
            _hashCode += getAcctBal().hashCode();
        }
        if (getAcctInfo() != null) {
            _hashCode += getAcctInfo().hashCode();
        }
        if (getCustRec() != null) {
            _hashCode += getCustRec().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAcctRes_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctRes_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type"));
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
