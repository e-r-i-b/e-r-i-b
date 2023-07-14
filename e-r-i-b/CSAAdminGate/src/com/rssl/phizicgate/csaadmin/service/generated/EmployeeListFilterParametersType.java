/**
 * EmployeeListFilterParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class EmployeeListFilterParametersType  implements java.io.Serializable {
    private int firstResult;

    private int maxResults;

    private java.lang.String seekerLogin;

    private boolean seekerAllDepartments;

    private java.lang.Long soughtId;

    private java.lang.String soughtLogin;

    private java.lang.String soughtFIO;

    private long soughtBlockedState;

    private java.lang.String soughtBlockedUntil;

    private java.lang.String soughtInfo;

    private java.lang.String soughtTB;

    private java.lang.String soughtBranchCode;

    private java.lang.String soughtDepartmentCode;

    public EmployeeListFilterParametersType() {
    }

    public EmployeeListFilterParametersType(
           int firstResult,
           int maxResults,
           java.lang.String seekerLogin,
           boolean seekerAllDepartments,
           java.lang.Long soughtId,
           java.lang.String soughtLogin,
           java.lang.String soughtFIO,
           long soughtBlockedState,
           java.lang.String soughtBlockedUntil,
           java.lang.String soughtInfo,
           java.lang.String soughtTB,
           java.lang.String soughtBranchCode,
           java.lang.String soughtDepartmentCode) {
           this.firstResult = firstResult;
           this.maxResults = maxResults;
           this.seekerLogin = seekerLogin;
           this.seekerAllDepartments = seekerAllDepartments;
           this.soughtId = soughtId;
           this.soughtLogin = soughtLogin;
           this.soughtFIO = soughtFIO;
           this.soughtBlockedState = soughtBlockedState;
           this.soughtBlockedUntil = soughtBlockedUntil;
           this.soughtInfo = soughtInfo;
           this.soughtTB = soughtTB;
           this.soughtBranchCode = soughtBranchCode;
           this.soughtDepartmentCode = soughtDepartmentCode;
    }


    /**
     * Gets the firstResult value for this EmployeeListFilterParametersType.
     * 
     * @return firstResult
     */
    public int getFirstResult() {
        return firstResult;
    }


    /**
     * Sets the firstResult value for this EmployeeListFilterParametersType.
     * 
     * @param firstResult
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }


    /**
     * Gets the maxResults value for this EmployeeListFilterParametersType.
     * 
     * @return maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }


    /**
     * Sets the maxResults value for this EmployeeListFilterParametersType.
     * 
     * @param maxResults
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }


    /**
     * Gets the seekerLogin value for this EmployeeListFilterParametersType.
     * 
     * @return seekerLogin
     */
    public java.lang.String getSeekerLogin() {
        return seekerLogin;
    }


    /**
     * Sets the seekerLogin value for this EmployeeListFilterParametersType.
     * 
     * @param seekerLogin
     */
    public void setSeekerLogin(java.lang.String seekerLogin) {
        this.seekerLogin = seekerLogin;
    }


    /**
     * Gets the seekerAllDepartments value for this EmployeeListFilterParametersType.
     * 
     * @return seekerAllDepartments
     */
    public boolean isSeekerAllDepartments() {
        return seekerAllDepartments;
    }


    /**
     * Sets the seekerAllDepartments value for this EmployeeListFilterParametersType.
     * 
     * @param seekerAllDepartments
     */
    public void setSeekerAllDepartments(boolean seekerAllDepartments) {
        this.seekerAllDepartments = seekerAllDepartments;
    }


    /**
     * Gets the soughtId value for this EmployeeListFilterParametersType.
     * 
     * @return soughtId
     */
    public java.lang.Long getSoughtId() {
        return soughtId;
    }


    /**
     * Sets the soughtId value for this EmployeeListFilterParametersType.
     * 
     * @param soughtId
     */
    public void setSoughtId(java.lang.Long soughtId) {
        this.soughtId = soughtId;
    }


    /**
     * Gets the soughtLogin value for this EmployeeListFilterParametersType.
     * 
     * @return soughtLogin
     */
    public java.lang.String getSoughtLogin() {
        return soughtLogin;
    }


    /**
     * Sets the soughtLogin value for this EmployeeListFilterParametersType.
     * 
     * @param soughtLogin
     */
    public void setSoughtLogin(java.lang.String soughtLogin) {
        this.soughtLogin = soughtLogin;
    }


    /**
     * Gets the soughtFIO value for this EmployeeListFilterParametersType.
     * 
     * @return soughtFIO
     */
    public java.lang.String getSoughtFIO() {
        return soughtFIO;
    }


    /**
     * Sets the soughtFIO value for this EmployeeListFilterParametersType.
     * 
     * @param soughtFIO
     */
    public void setSoughtFIO(java.lang.String soughtFIO) {
        this.soughtFIO = soughtFIO;
    }


    /**
     * Gets the soughtBlockedState value for this EmployeeListFilterParametersType.
     * 
     * @return soughtBlockedState
     */
    public long getSoughtBlockedState() {
        return soughtBlockedState;
    }


    /**
     * Sets the soughtBlockedState value for this EmployeeListFilterParametersType.
     * 
     * @param soughtBlockedState
     */
    public void setSoughtBlockedState(long soughtBlockedState) {
        this.soughtBlockedState = soughtBlockedState;
    }


    /**
     * Gets the soughtBlockedUntil value for this EmployeeListFilterParametersType.
     * 
     * @return soughtBlockedUntil
     */
    public java.lang.String getSoughtBlockedUntil() {
        return soughtBlockedUntil;
    }


    /**
     * Sets the soughtBlockedUntil value for this EmployeeListFilterParametersType.
     * 
     * @param soughtBlockedUntil
     */
    public void setSoughtBlockedUntil(java.lang.String soughtBlockedUntil) {
        this.soughtBlockedUntil = soughtBlockedUntil;
    }


    /**
     * Gets the soughtInfo value for this EmployeeListFilterParametersType.
     * 
     * @return soughtInfo
     */
    public java.lang.String getSoughtInfo() {
        return soughtInfo;
    }


    /**
     * Sets the soughtInfo value for this EmployeeListFilterParametersType.
     * 
     * @param soughtInfo
     */
    public void setSoughtInfo(java.lang.String soughtInfo) {
        this.soughtInfo = soughtInfo;
    }


    /**
     * Gets the soughtTB value for this EmployeeListFilterParametersType.
     * 
     * @return soughtTB
     */
    public java.lang.String getSoughtTB() {
        return soughtTB;
    }


    /**
     * Sets the soughtTB value for this EmployeeListFilterParametersType.
     * 
     * @param soughtTB
     */
    public void setSoughtTB(java.lang.String soughtTB) {
        this.soughtTB = soughtTB;
    }


    /**
     * Gets the soughtBranchCode value for this EmployeeListFilterParametersType.
     * 
     * @return soughtBranchCode
     */
    public java.lang.String getSoughtBranchCode() {
        return soughtBranchCode;
    }


    /**
     * Sets the soughtBranchCode value for this EmployeeListFilterParametersType.
     * 
     * @param soughtBranchCode
     */
    public void setSoughtBranchCode(java.lang.String soughtBranchCode) {
        this.soughtBranchCode = soughtBranchCode;
    }


    /**
     * Gets the soughtDepartmentCode value for this EmployeeListFilterParametersType.
     * 
     * @return soughtDepartmentCode
     */
    public java.lang.String getSoughtDepartmentCode() {
        return soughtDepartmentCode;
    }


    /**
     * Sets the soughtDepartmentCode value for this EmployeeListFilterParametersType.
     * 
     * @param soughtDepartmentCode
     */
    public void setSoughtDepartmentCode(java.lang.String soughtDepartmentCode) {
        this.soughtDepartmentCode = soughtDepartmentCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmployeeListFilterParametersType)) return false;
        EmployeeListFilterParametersType other = (EmployeeListFilterParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.firstResult == other.getFirstResult() &&
            this.maxResults == other.getMaxResults() &&
            ((this.seekerLogin==null && other.getSeekerLogin()==null) || 
             (this.seekerLogin!=null &&
              this.seekerLogin.equals(other.getSeekerLogin()))) &&
            this.seekerAllDepartments == other.isSeekerAllDepartments() &&
            ((this.soughtId==null && other.getSoughtId()==null) || 
             (this.soughtId!=null &&
              this.soughtId.equals(other.getSoughtId()))) &&
            ((this.soughtLogin==null && other.getSoughtLogin()==null) || 
             (this.soughtLogin!=null &&
              this.soughtLogin.equals(other.getSoughtLogin()))) &&
            ((this.soughtFIO==null && other.getSoughtFIO()==null) || 
             (this.soughtFIO!=null &&
              this.soughtFIO.equals(other.getSoughtFIO()))) &&
            this.soughtBlockedState == other.getSoughtBlockedState() &&
            ((this.soughtBlockedUntil==null && other.getSoughtBlockedUntil()==null) || 
             (this.soughtBlockedUntil!=null &&
              this.soughtBlockedUntil.equals(other.getSoughtBlockedUntil()))) &&
            ((this.soughtInfo==null && other.getSoughtInfo()==null) || 
             (this.soughtInfo!=null &&
              this.soughtInfo.equals(other.getSoughtInfo()))) &&
            ((this.soughtTB==null && other.getSoughtTB()==null) || 
             (this.soughtTB!=null &&
              this.soughtTB.equals(other.getSoughtTB()))) &&
            ((this.soughtBranchCode==null && other.getSoughtBranchCode()==null) || 
             (this.soughtBranchCode!=null &&
              this.soughtBranchCode.equals(other.getSoughtBranchCode()))) &&
            ((this.soughtDepartmentCode==null && other.getSoughtDepartmentCode()==null) || 
             (this.soughtDepartmentCode!=null &&
              this.soughtDepartmentCode.equals(other.getSoughtDepartmentCode())));
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
        _hashCode += getFirstResult();
        _hashCode += getMaxResults();
        if (getSeekerLogin() != null) {
            _hashCode += getSeekerLogin().hashCode();
        }
        _hashCode += (isSeekerAllDepartments() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSoughtId() != null) {
            _hashCode += getSoughtId().hashCode();
        }
        if (getSoughtLogin() != null) {
            _hashCode += getSoughtLogin().hashCode();
        }
        if (getSoughtFIO() != null) {
            _hashCode += getSoughtFIO().hashCode();
        }
        _hashCode += new Long(getSoughtBlockedState()).hashCode();
        if (getSoughtBlockedUntil() != null) {
            _hashCode += getSoughtBlockedUntil().hashCode();
        }
        if (getSoughtInfo() != null) {
            _hashCode += getSoughtInfo().hashCode();
        }
        if (getSoughtTB() != null) {
            _hashCode += getSoughtTB().hashCode();
        }
        if (getSoughtBranchCode() != null) {
            _hashCode += getSoughtBranchCode().hashCode();
        }
        if (getSoughtDepartmentCode() != null) {
            _hashCode += getSoughtDepartmentCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmployeeListFilterParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeListFilterParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "firstResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "maxResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seekerLogin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "seekerLogin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seekerAllDepartments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "seekerAllDepartments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtLogin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtLogin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtFIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtFIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtBlockedState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtBlockedState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtBlockedUntil");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtBlockedUntil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtTB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtTB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtBranchCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtBranchCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soughtDepartmentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "soughtDepartmentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
