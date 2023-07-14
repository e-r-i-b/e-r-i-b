/**
 * MailListDataTypeBase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;


/**
 * Общие данные письма
 */
public abstract class MailListDataTypeBase  implements java.io.Serializable {
    private long nodeId;

    private long id;

    private java.lang.Long number;

    private java.lang.String creationDate;

    private java.lang.String responseMethod;

    private java.lang.String state;

    private java.lang.String stateDescription;

    private java.lang.String type;

    private java.lang.String typeDescription;

    private java.lang.String theme;

    private java.lang.String subject;

    private java.lang.String employeeFIO;

    private java.lang.String employeeUserId;

    private java.lang.String tb;

    private java.lang.String area;

    public MailListDataTypeBase() {
    }

    public MailListDataTypeBase(
           long nodeId,
           long id,
           java.lang.Long number,
           java.lang.String creationDate,
           java.lang.String responseMethod,
           java.lang.String state,
           java.lang.String stateDescription,
           java.lang.String type,
           java.lang.String typeDescription,
           java.lang.String theme,
           java.lang.String subject,
           java.lang.String employeeFIO,
           java.lang.String employeeUserId,
           java.lang.String tb,
           java.lang.String area) {
           this.nodeId = nodeId;
           this.id = id;
           this.number = number;
           this.creationDate = creationDate;
           this.responseMethod = responseMethod;
           this.state = state;
           this.stateDescription = stateDescription;
           this.type = type;
           this.typeDescription = typeDescription;
           this.theme = theme;
           this.subject = subject;
           this.employeeFIO = employeeFIO;
           this.employeeUserId = employeeUserId;
           this.tb = tb;
           this.area = area;
    }


    /**
     * Gets the nodeId value for this MailListDataTypeBase.
     * 
     * @return nodeId
     */
    public long getNodeId() {
        return nodeId;
    }


    /**
     * Sets the nodeId value for this MailListDataTypeBase.
     * 
     * @param nodeId
     */
    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }


    /**
     * Gets the id value for this MailListDataTypeBase.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this MailListDataTypeBase.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the number value for this MailListDataTypeBase.
     * 
     * @return number
     */
    public java.lang.Long getNumber() {
        return number;
    }


    /**
     * Sets the number value for this MailListDataTypeBase.
     * 
     * @param number
     */
    public void setNumber(java.lang.Long number) {
        this.number = number;
    }


    /**
     * Gets the creationDate value for this MailListDataTypeBase.
     * 
     * @return creationDate
     */
    public java.lang.String getCreationDate() {
        return creationDate;
    }


    /**
     * Sets the creationDate value for this MailListDataTypeBase.
     * 
     * @param creationDate
     */
    public void setCreationDate(java.lang.String creationDate) {
        this.creationDate = creationDate;
    }


    /**
     * Gets the responseMethod value for this MailListDataTypeBase.
     * 
     * @return responseMethod
     */
    public java.lang.String getResponseMethod() {
        return responseMethod;
    }


    /**
     * Sets the responseMethod value for this MailListDataTypeBase.
     * 
     * @param responseMethod
     */
    public void setResponseMethod(java.lang.String responseMethod) {
        this.responseMethod = responseMethod;
    }


    /**
     * Gets the state value for this MailListDataTypeBase.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this MailListDataTypeBase.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the stateDescription value for this MailListDataTypeBase.
     * 
     * @return stateDescription
     */
    public java.lang.String getStateDescription() {
        return stateDescription;
    }


    /**
     * Sets the stateDescription value for this MailListDataTypeBase.
     * 
     * @param stateDescription
     */
    public void setStateDescription(java.lang.String stateDescription) {
        this.stateDescription = stateDescription;
    }


    /**
     * Gets the type value for this MailListDataTypeBase.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this MailListDataTypeBase.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the typeDescription value for this MailListDataTypeBase.
     * 
     * @return typeDescription
     */
    public java.lang.String getTypeDescription() {
        return typeDescription;
    }


    /**
     * Sets the typeDescription value for this MailListDataTypeBase.
     * 
     * @param typeDescription
     */
    public void setTypeDescription(java.lang.String typeDescription) {
        this.typeDescription = typeDescription;
    }


    /**
     * Gets the theme value for this MailListDataTypeBase.
     * 
     * @return theme
     */
    public java.lang.String getTheme() {
        return theme;
    }


    /**
     * Sets the theme value for this MailListDataTypeBase.
     * 
     * @param theme
     */
    public void setTheme(java.lang.String theme) {
        this.theme = theme;
    }


    /**
     * Gets the subject value for this MailListDataTypeBase.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this MailListDataTypeBase.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the employeeFIO value for this MailListDataTypeBase.
     * 
     * @return employeeFIO
     */
    public java.lang.String getEmployeeFIO() {
        return employeeFIO;
    }


    /**
     * Sets the employeeFIO value for this MailListDataTypeBase.
     * 
     * @param employeeFIO
     */
    public void setEmployeeFIO(java.lang.String employeeFIO) {
        this.employeeFIO = employeeFIO;
    }


    /**
     * Gets the employeeUserId value for this MailListDataTypeBase.
     * 
     * @return employeeUserId
     */
    public java.lang.String getEmployeeUserId() {
        return employeeUserId;
    }


    /**
     * Sets the employeeUserId value for this MailListDataTypeBase.
     * 
     * @param employeeUserId
     */
    public void setEmployeeUserId(java.lang.String employeeUserId) {
        this.employeeUserId = employeeUserId;
    }


    /**
     * Gets the tb value for this MailListDataTypeBase.
     * 
     * @return tb
     */
    public java.lang.String getTb() {
        return tb;
    }


    /**
     * Sets the tb value for this MailListDataTypeBase.
     * 
     * @param tb
     */
    public void setTb(java.lang.String tb) {
        this.tb = tb;
    }


    /**
     * Gets the area value for this MailListDataTypeBase.
     * 
     * @return area
     */
    public java.lang.String getArea() {
        return area;
    }


    /**
     * Sets the area value for this MailListDataTypeBase.
     * 
     * @param area
     */
    public void setArea(java.lang.String area) {
        this.area = area;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MailListDataTypeBase)) return false;
        MailListDataTypeBase other = (MailListDataTypeBase) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nodeId == other.getNodeId() &&
            this.id == other.getId() &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.creationDate==null && other.getCreationDate()==null) || 
             (this.creationDate!=null &&
              this.creationDate.equals(other.getCreationDate()))) &&
            ((this.responseMethod==null && other.getResponseMethod()==null) || 
             (this.responseMethod!=null &&
              this.responseMethod.equals(other.getResponseMethod()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.stateDescription==null && other.getStateDescription()==null) || 
             (this.stateDescription!=null &&
              this.stateDescription.equals(other.getStateDescription()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.typeDescription==null && other.getTypeDescription()==null) || 
             (this.typeDescription!=null &&
              this.typeDescription.equals(other.getTypeDescription()))) &&
            ((this.theme==null && other.getTheme()==null) || 
             (this.theme!=null &&
              this.theme.equals(other.getTheme()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.employeeFIO==null && other.getEmployeeFIO()==null) || 
             (this.employeeFIO!=null &&
              this.employeeFIO.equals(other.getEmployeeFIO()))) &&
            ((this.employeeUserId==null && other.getEmployeeUserId()==null) || 
             (this.employeeUserId!=null &&
              this.employeeUserId.equals(other.getEmployeeUserId()))) &&
            ((this.tb==null && other.getTb()==null) || 
             (this.tb!=null &&
              this.tb.equals(other.getTb()))) &&
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea())));
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
        _hashCode += new Long(getNodeId()).hashCode();
        _hashCode += new Long(getId()).hashCode();
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getCreationDate() != null) {
            _hashCode += getCreationDate().hashCode();
        }
        if (getResponseMethod() != null) {
            _hashCode += getResponseMethod().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getStateDescription() != null) {
            _hashCode += getStateDescription().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getTypeDescription() != null) {
            _hashCode += getTypeDescription().hashCode();
        }
        if (getTheme() != null) {
            _hashCode += getTheme().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getEmployeeFIO() != null) {
            _hashCode += getEmployeeFIO().hashCode();
        }
        if (getEmployeeUserId() != null) {
            _hashCode += getEmployeeUserId().hashCode();
        }
        if (getTb() != null) {
            _hashCode += getTb().hashCode();
        }
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MailListDataTypeBase.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailListDataTypeBase"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "nodeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "creationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "responseMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "stateDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "typeDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("theme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "theme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employeeFIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "employeeFIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employeeUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "employeeUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "tb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
