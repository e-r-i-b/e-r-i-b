/**
 * DepoDeptRes_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Запись о счете депо
 */
public class DepoDeptRes_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type acctBal;

    private com.rssl.phizic.test.webgate.esberib.generated.DepoAcctBalRec_Type[] depoAcctBalRec;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public DepoDeptRes_Type() {
    }

    public DepoDeptRes_Type(
           com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type acctBal,
           com.rssl.phizic.test.webgate.esberib.generated.DepoAcctBalRec_Type[] depoAcctBalRec,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.depoAcctId = depoAcctId;
           this.acctBal = acctBal;
           this.depoAcctBalRec = depoAcctBalRec;
           this.status = status;
    }


    /**
     * Gets the depoAcctId value for this DepoDeptRes_Type.
     * 
     * @return depoAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type getDepoAcctId() {
        return depoAcctId;
    }


    /**
     * Sets the depoAcctId value for this DepoDeptRes_Type.
     * 
     * @param depoAcctId
     */
    public void setDepoAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId) {
        this.depoAcctId = depoAcctId;
    }


    /**
     * Gets the acctBal value for this DepoDeptRes_Type.
     * 
     * @return acctBal
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this DepoDeptRes_Type.
     * 
     * @param acctBal
     */
    public void setAcctBal(com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type acctBal) {
        this.acctBal = acctBal;
    }


    /**
     * Gets the depoAcctBalRec value for this DepoDeptRes_Type.
     * 
     * @return depoAcctBalRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoAcctBalRec_Type[] getDepoAcctBalRec() {
        return depoAcctBalRec;
    }


    /**
     * Sets the depoAcctBalRec value for this DepoDeptRes_Type.
     * 
     * @param depoAcctBalRec
     */
    public void setDepoAcctBalRec(com.rssl.phizic.test.webgate.esberib.generated.DepoAcctBalRec_Type[] depoAcctBalRec) {
        this.depoAcctBalRec = depoAcctBalRec;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.DepoAcctBalRec_Type getDepoAcctBalRec(int i) {
        return this.depoAcctBalRec[i];
    }

    public void setDepoAcctBalRec(int i, com.rssl.phizic.test.webgate.esberib.generated.DepoAcctBalRec_Type _value) {
        this.depoAcctBalRec[i] = _value;
    }


    /**
     * Gets the status value for this DepoDeptRes_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepoDeptRes_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoDeptRes_Type)) return false;
        DepoDeptRes_Type other = (DepoDeptRes_Type) obj;
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
            ((this.depoAcctBalRec==null && other.getDepoAcctBalRec()==null) || 
             (this.depoAcctBalRec!=null &&
              java.util.Arrays.equals(this.depoAcctBalRec, other.getDepoAcctBalRec()))) &&
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
        if (getDepoAcctBalRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepoAcctBalRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepoAcctBalRec(), i);
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
        new org.apache.axis.description.TypeDesc(DepoDeptRes_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptRes_Type"));
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAcctBalRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctBalRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctBalRec_Type"));
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
