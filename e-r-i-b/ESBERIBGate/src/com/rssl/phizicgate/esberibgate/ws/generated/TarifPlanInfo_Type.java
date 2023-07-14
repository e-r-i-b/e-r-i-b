/**
 * TarifPlanInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Информация о тарифном плане и сегменте клиента
 */
public class TarifPlanInfo_Type  implements java.io.Serializable {
    /* Код тарифного плана */
    private java.lang.String code;

    /* Дата подключения к тарифному плану */
    private java.lang.String connectionDate;

    /* Код сегмента, к которому относят клиента */
    private java.lang.String segmentCode;

    /* Номер персонального менеджера */
    private java.lang.String managerId;

    /* ОСБ КМ */
    private java.lang.String managerOsb;

    /* ТБ КМ */
    private java.lang.String managerTb;

    /* ВСП КМ */
    private java.lang.String managerFilial;

    public TarifPlanInfo_Type() {
    }

    public TarifPlanInfo_Type(
           java.lang.String code,
           java.lang.String connectionDate,
           java.lang.String segmentCode,
           java.lang.String managerId,
           java.lang.String managerOsb,
           java.lang.String managerTb,
           java.lang.String managerFilial) {
           this.code = code;
           this.connectionDate = connectionDate;
           this.segmentCode = segmentCode;
           this.managerId = managerId;
           this.managerOsb = managerOsb;
           this.managerTb = managerTb;
           this.managerFilial = managerFilial;
    }


    /**
     * Gets the code value for this TarifPlanInfo_Type.
     * 
     * @return code   * Код тарифного плана
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this TarifPlanInfo_Type.
     * 
     * @param code   * Код тарифного плана
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the connectionDate value for this TarifPlanInfo_Type.
     * 
     * @return connectionDate   * Дата подключения к тарифному плану
     */
    public java.lang.String getConnectionDate() {
        return connectionDate;
    }


    /**
     * Sets the connectionDate value for this TarifPlanInfo_Type.
     * 
     * @param connectionDate   * Дата подключения к тарифному плану
     */
    public void setConnectionDate(java.lang.String connectionDate) {
        this.connectionDate = connectionDate;
    }


    /**
     * Gets the segmentCode value for this TarifPlanInfo_Type.
     * 
     * @return segmentCode   * Код сегмента, к которому относят клиента
     */
    public java.lang.String getSegmentCode() {
        return segmentCode;
    }


    /**
     * Sets the segmentCode value for this TarifPlanInfo_Type.
     * 
     * @param segmentCode   * Код сегмента, к которому относят клиента
     */
    public void setSegmentCode(java.lang.String segmentCode) {
        this.segmentCode = segmentCode;
    }


    /**
     * Gets the managerId value for this TarifPlanInfo_Type.
     * 
     * @return managerId   * Номер персонального менеджера
     */
    public java.lang.String getManagerId() {
        return managerId;
    }


    /**
     * Sets the managerId value for this TarifPlanInfo_Type.
     * 
     * @param managerId   * Номер персонального менеджера
     */
    public void setManagerId(java.lang.String managerId) {
        this.managerId = managerId;
    }


    /**
     * Gets the managerOsb value for this TarifPlanInfo_Type.
     * 
     * @return managerOsb   * ОСБ КМ
     */
    public java.lang.String getManagerOsb() {
        return managerOsb;
    }


    /**
     * Sets the managerOsb value for this TarifPlanInfo_Type.
     * 
     * @param managerOsb   * ОСБ КМ
     */
    public void setManagerOsb(java.lang.String managerOsb) {
        this.managerOsb = managerOsb;
    }


    /**
     * Gets the managerTb value for this TarifPlanInfo_Type.
     * 
     * @return managerTb   * ТБ КМ
     */
    public java.lang.String getManagerTb() {
        return managerTb;
    }


    /**
     * Sets the managerTb value for this TarifPlanInfo_Type.
     * 
     * @param managerTb   * ТБ КМ
     */
    public void setManagerTb(java.lang.String managerTb) {
        this.managerTb = managerTb;
    }


    /**
     * Gets the managerFilial value for this TarifPlanInfo_Type.
     * 
     * @return managerFilial   * ВСП КМ
     */
    public java.lang.String getManagerFilial() {
        return managerFilial;
    }


    /**
     * Sets the managerFilial value for this TarifPlanInfo_Type.
     * 
     * @param managerFilial   * ВСП КМ
     */
    public void setManagerFilial(java.lang.String managerFilial) {
        this.managerFilial = managerFilial;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TarifPlanInfo_Type)) return false;
        TarifPlanInfo_Type other = (TarifPlanInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.connectionDate==null && other.getConnectionDate()==null) || 
             (this.connectionDate!=null &&
              this.connectionDate.equals(other.getConnectionDate()))) &&
            ((this.segmentCode==null && other.getSegmentCode()==null) || 
             (this.segmentCode!=null &&
              this.segmentCode.equals(other.getSegmentCode()))) &&
            ((this.managerId==null && other.getManagerId()==null) || 
             (this.managerId!=null &&
              this.managerId.equals(other.getManagerId()))) &&
            ((this.managerOsb==null && other.getManagerOsb()==null) || 
             (this.managerOsb!=null &&
              this.managerOsb.equals(other.getManagerOsb()))) &&
            ((this.managerTb==null && other.getManagerTb()==null) || 
             (this.managerTb!=null &&
              this.managerTb.equals(other.getManagerTb()))) &&
            ((this.managerFilial==null && other.getManagerFilial()==null) || 
             (this.managerFilial!=null &&
              this.managerFilial.equals(other.getManagerFilial())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getConnectionDate() != null) {
            _hashCode += getConnectionDate().hashCode();
        }
        if (getSegmentCode() != null) {
            _hashCode += getSegmentCode().hashCode();
        }
        if (getManagerId() != null) {
            _hashCode += getManagerId().hashCode();
        }
        if (getManagerOsb() != null) {
            _hashCode += getManagerOsb().hashCode();
        }
        if (getManagerTb() != null) {
            _hashCode += getManagerTb().hashCode();
        }
        if (getManagerFilial() != null) {
            _hashCode += getManagerFilial().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TarifPlanInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifPlanInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("connectionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ConnectionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segmentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SegmentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ManagerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerOsb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ManagerOsb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerTb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ManagerTb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("managerFilial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ManagerFilial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
