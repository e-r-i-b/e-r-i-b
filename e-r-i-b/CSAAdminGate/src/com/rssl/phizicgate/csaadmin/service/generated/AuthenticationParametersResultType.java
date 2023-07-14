/**
 * AuthenticationParametersResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;


/**
 * Параметры аутентификации
 */
public class AuthenticationParametersResultType  implements java.io.Serializable {
    private java.lang.String sessionId;

    private java.lang.String loginId;

    private java.lang.String lastEmployeeUpdateDate;

    private java.lang.String action;

    private com.rssl.phizicgate.csaadmin.service.generated.MapEntryType[] parameters;

    public AuthenticationParametersResultType() {
    }

    public AuthenticationParametersResultType(
           java.lang.String sessionId,
           java.lang.String loginId,
           java.lang.String lastEmployeeUpdateDate,
           java.lang.String action,
           com.rssl.phizicgate.csaadmin.service.generated.MapEntryType[] parameters) {
           this.sessionId = sessionId;
           this.loginId = loginId;
           this.lastEmployeeUpdateDate = lastEmployeeUpdateDate;
           this.action = action;
           this.parameters = parameters;
    }


    /**
     * Gets the sessionId value for this AuthenticationParametersResultType.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this AuthenticationParametersResultType.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the loginId value for this AuthenticationParametersResultType.
     * 
     * @return loginId
     */
    public java.lang.String getLoginId() {
        return loginId;
    }


    /**
     * Sets the loginId value for this AuthenticationParametersResultType.
     * 
     * @param loginId
     */
    public void setLoginId(java.lang.String loginId) {
        this.loginId = loginId;
    }


    /**
     * Gets the lastEmployeeUpdateDate value for this AuthenticationParametersResultType.
     * 
     * @return lastEmployeeUpdateDate
     */
    public java.lang.String getLastEmployeeUpdateDate() {
        return lastEmployeeUpdateDate;
    }


    /**
     * Sets the lastEmployeeUpdateDate value for this AuthenticationParametersResultType.
     * 
     * @param lastEmployeeUpdateDate
     */
    public void setLastEmployeeUpdateDate(java.lang.String lastEmployeeUpdateDate) {
        this.lastEmployeeUpdateDate = lastEmployeeUpdateDate;
    }


    /**
     * Gets the action value for this AuthenticationParametersResultType.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this AuthenticationParametersResultType.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the parameters value for this AuthenticationParametersResultType.
     * 
     * @return parameters
     */
    public com.rssl.phizicgate.csaadmin.service.generated.MapEntryType[] getParameters() {
        return parameters;
    }


    /**
     * Sets the parameters value for this AuthenticationParametersResultType.
     * 
     * @param parameters
     */
    public void setParameters(com.rssl.phizicgate.csaadmin.service.generated.MapEntryType[] parameters) {
        this.parameters = parameters;
    }

    public com.rssl.phizicgate.csaadmin.service.generated.MapEntryType getParameters(int i) {
        return this.parameters[i];
    }

    public void setParameters(int i, com.rssl.phizicgate.csaadmin.service.generated.MapEntryType _value) {
        this.parameters[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuthenticationParametersResultType)) return false;
        AuthenticationParametersResultType other = (AuthenticationParametersResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
            ((this.loginId==null && other.getLoginId()==null) || 
             (this.loginId!=null &&
              this.loginId.equals(other.getLoginId()))) &&
            ((this.lastEmployeeUpdateDate==null && other.getLastEmployeeUpdateDate()==null) || 
             (this.lastEmployeeUpdateDate!=null &&
              this.lastEmployeeUpdateDate.equals(other.getLastEmployeeUpdateDate()))) &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.parameters==null && other.getParameters()==null) || 
             (this.parameters!=null &&
              java.util.Arrays.equals(this.parameters, other.getParameters())));
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
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        if (getLoginId() != null) {
            _hashCode += getLoginId().hashCode();
        }
        if (getLastEmployeeUpdateDate() != null) {
            _hashCode += getLastEmployeeUpdateDate().hashCode();
        }
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParameters(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthenticationParametersResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AuthenticationParametersResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "sessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "loginId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastEmployeeUpdateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "lastEmployeeUpdateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "parameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MapEntryType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
