/**
 * MailEmployeeStatisticsDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;


/**
 * Статистика для писем в разрезе сотрудников
 */
public class MailEmployeeStatisticsDataType  implements java.io.Serializable {
    private long id;

    private long nodeId;

    private java.lang.String arriveTime;

    private java.lang.String processingTime;

    private java.lang.String state;

    private java.lang.String employeeFIO;

    private java.lang.String areaName;

    public MailEmployeeStatisticsDataType() {
    }

    public MailEmployeeStatisticsDataType(
           long id,
           long nodeId,
           java.lang.String arriveTime,
           java.lang.String processingTime,
           java.lang.String state,
           java.lang.String employeeFIO,
           java.lang.String areaName) {
           this.id = id;
           this.nodeId = nodeId;
           this.arriveTime = arriveTime;
           this.processingTime = processingTime;
           this.state = state;
           this.employeeFIO = employeeFIO;
           this.areaName = areaName;
    }


    /**
     * Gets the id value for this MailEmployeeStatisticsDataType.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this MailEmployeeStatisticsDataType.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the nodeId value for this MailEmployeeStatisticsDataType.
     * 
     * @return nodeId
     */
    public long getNodeId() {
        return nodeId;
    }


    /**
     * Sets the nodeId value for this MailEmployeeStatisticsDataType.
     * 
     * @param nodeId
     */
    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }


    /**
     * Gets the arriveTime value for this MailEmployeeStatisticsDataType.
     * 
     * @return arriveTime
     */
    public java.lang.String getArriveTime() {
        return arriveTime;
    }


    /**
     * Sets the arriveTime value for this MailEmployeeStatisticsDataType.
     * 
     * @param arriveTime
     */
    public void setArriveTime(java.lang.String arriveTime) {
        this.arriveTime = arriveTime;
    }


    /**
     * Gets the processingTime value for this MailEmployeeStatisticsDataType.
     * 
     * @return processingTime
     */
    public java.lang.String getProcessingTime() {
        return processingTime;
    }


    /**
     * Sets the processingTime value for this MailEmployeeStatisticsDataType.
     * 
     * @param processingTime
     */
    public void setProcessingTime(java.lang.String processingTime) {
        this.processingTime = processingTime;
    }


    /**
     * Gets the state value for this MailEmployeeStatisticsDataType.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this MailEmployeeStatisticsDataType.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the employeeFIO value for this MailEmployeeStatisticsDataType.
     * 
     * @return employeeFIO
     */
    public java.lang.String getEmployeeFIO() {
        return employeeFIO;
    }


    /**
     * Sets the employeeFIO value for this MailEmployeeStatisticsDataType.
     * 
     * @param employeeFIO
     */
    public void setEmployeeFIO(java.lang.String employeeFIO) {
        this.employeeFIO = employeeFIO;
    }


    /**
     * Gets the areaName value for this MailEmployeeStatisticsDataType.
     * 
     * @return areaName
     */
    public java.lang.String getAreaName() {
        return areaName;
    }


    /**
     * Sets the areaName value for this MailEmployeeStatisticsDataType.
     * 
     * @param areaName
     */
    public void setAreaName(java.lang.String areaName) {
        this.areaName = areaName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MailEmployeeStatisticsDataType)) return false;
        MailEmployeeStatisticsDataType other = (MailEmployeeStatisticsDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.id == other.getId() &&
            this.nodeId == other.getNodeId() &&
            ((this.arriveTime==null && other.getArriveTime()==null) || 
             (this.arriveTime!=null &&
              this.arriveTime.equals(other.getArriveTime()))) &&
            ((this.processingTime==null && other.getProcessingTime()==null) || 
             (this.processingTime!=null &&
              this.processingTime.equals(other.getProcessingTime()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.employeeFIO==null && other.getEmployeeFIO()==null) || 
             (this.employeeFIO!=null &&
              this.employeeFIO.equals(other.getEmployeeFIO()))) &&
            ((this.areaName==null && other.getAreaName()==null) || 
             (this.areaName!=null &&
              this.areaName.equals(other.getAreaName())));
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
        _hashCode += new Long(getId()).hashCode();
        _hashCode += new Long(getNodeId()).hashCode();
        if (getArriveTime() != null) {
            _hashCode += getArriveTime().hashCode();
        }
        if (getProcessingTime() != null) {
            _hashCode += getProcessingTime().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getEmployeeFIO() != null) {
            _hashCode += getEmployeeFIO().hashCode();
        }
        if (getAreaName() != null) {
            _hashCode += getAreaName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MailEmployeeStatisticsDataType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailEmployeeStatisticsDataType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "nodeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arriveTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "arriveTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processingTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "processingTime"));
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
        elemField.setFieldName("employeeFIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "employeeFIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "areaName"));
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
