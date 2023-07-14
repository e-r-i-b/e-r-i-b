/**
 * DepAcct_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class DepAcct_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtGen_Type depAcctStmtGen;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtRec_Type[] depAcctStmtRec;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public DepAcct_Type() {
    }

    public DepAcct_Type(
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtGen_Type depAcctStmtGen,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtRec_Type[] depAcctStmtRec,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.depAcctId = depAcctId;
           this.depAcctStmtGen = depAcctStmtGen;
           this.depAcctStmtRec = depAcctStmtRec;
           this.status = status;
    }


    /**
     * Gets the depAcctId value for this DepAcct_Type.
     * 
     * @return depAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctId() {
        return depAcctId;
    }


    /**
     * Sets the depAcctId value for this DepAcct_Type.
     * 
     * @param depAcctId
     */
    public void setDepAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId) {
        this.depAcctId = depAcctId;
    }


    /**
     * Gets the depAcctStmtGen value for this DepAcct_Type.
     * 
     * @return depAcctStmtGen
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtGen_Type getDepAcctStmtGen() {
        return depAcctStmtGen;
    }


    /**
     * Sets the depAcctStmtGen value for this DepAcct_Type.
     * 
     * @param depAcctStmtGen
     */
    public void setDepAcctStmtGen(com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtGen_Type depAcctStmtGen) {
        this.depAcctStmtGen = depAcctStmtGen;
    }


    /**
     * Gets the depAcctStmtRec value for this DepAcct_Type.
     * 
     * @return depAcctStmtRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtRec_Type[] getDepAcctStmtRec() {
        return depAcctStmtRec;
    }


    /**
     * Sets the depAcctStmtRec value for this DepAcct_Type.
     * 
     * @param depAcctStmtRec
     */
    public void setDepAcctStmtRec(com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtRec_Type[] depAcctStmtRec) {
        this.depAcctStmtRec = depAcctStmtRec;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtRec_Type getDepAcctStmtRec(int i) {
        return this.depAcctStmtRec[i];
    }

    public void setDepAcctStmtRec(int i, com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtRec_Type _value) {
        this.depAcctStmtRec[i] = _value;
    }


    /**
     * Gets the status value for this DepAcct_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepAcct_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAcct_Type)) return false;
        DepAcct_Type other = (DepAcct_Type) obj;
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
            ((this.depAcctStmtGen==null && other.getDepAcctStmtGen()==null) || 
             (this.depAcctStmtGen!=null &&
              this.depAcctStmtGen.equals(other.getDepAcctStmtGen()))) &&
            ((this.depAcctStmtRec==null && other.getDepAcctStmtRec()==null) || 
             (this.depAcctStmtRec!=null &&
              java.util.Arrays.equals(this.depAcctStmtRec, other.getDepAcctStmtRec()))) &&
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
        if (getDepAcctStmtGen() != null) {
            _hashCode += getDepAcctStmtGen().hashCode();
        }
        if (getDepAcctStmtRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepAcctStmtRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepAcctStmtRec(), i);
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
        new org.apache.axis.description.TypeDesc(DepAcct_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcct_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctStmtGen");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtGen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtGen_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctStmtRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtRec"));
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
