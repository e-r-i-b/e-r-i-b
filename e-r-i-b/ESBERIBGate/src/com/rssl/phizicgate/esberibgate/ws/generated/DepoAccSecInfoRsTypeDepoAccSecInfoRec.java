/**
 * DepoAccSecInfoRsTypeDepoAccSecInfoRec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Счета депо
 */
public class DepoAccSecInfoRsTypeDepoAccSecInfoRec  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type depoAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityRec_Type[] depoRec;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    public DepoAccSecInfoRsTypeDepoAccSecInfoRec() {
    }

    public DepoAccSecInfoRsTypeDepoAccSecInfoRec(
           com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type depoAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityRec_Type[] depoRec,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
           this.depoAcctId = depoAcctId;
           this.depoRec = depoRec;
           this.status = status;
    }


    /**
     * Gets the depoAcctId value for this DepoAccSecInfoRsTypeDepoAccSecInfoRec.
     * 
     * @return depoAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type getDepoAcctId() {
        return depoAcctId;
    }


    /**
     * Sets the depoAcctId value for this DepoAccSecInfoRsTypeDepoAccSecInfoRec.
     * 
     * @param depoAcctId
     */
    public void setDepoAcctId(com.rssl.phizicgate.esberibgate.ws.generated.DepoAcctId_Type depoAcctId) {
        this.depoAcctId = depoAcctId;
    }


    /**
     * Gets the depoRec value for this DepoAccSecInfoRsTypeDepoAccSecInfoRec.
     * 
     * @return depoRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityRec_Type[] getDepoRec() {
        return depoRec;
    }


    /**
     * Sets the depoRec value for this DepoAccSecInfoRsTypeDepoAccSecInfoRec.
     * 
     * @param depoRec
     */
    public void setDepoRec(com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityRec_Type[] depoRec) {
        this.depoRec = depoRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityRec_Type getDepoRec(int i) {
        return this.depoRec[i];
    }

    public void setDepoRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityRec_Type _value) {
        this.depoRec[i] = _value;
    }


    /**
     * Gets the status value for this DepoAccSecInfoRsTypeDepoAccSecInfoRec.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepoAccSecInfoRsTypeDepoAccSecInfoRec.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAccSecInfoRsTypeDepoAccSecInfoRec)) return false;
        DepoAccSecInfoRsTypeDepoAccSecInfoRec other = (DepoAccSecInfoRsTypeDepoAccSecInfoRec) obj;
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
            ((this.depoRec==null && other.getDepoRec()==null) || 
             (this.depoRec!=null &&
              java.util.Arrays.equals(this.depoRec, other.getDepoRec()))) &&
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
        if (getDepoRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepoRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepoRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAccSecInfoRsTypeDepoAccSecInfoRec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoAccSecInfoRsType>DepoAccSecInfoRec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityRec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
