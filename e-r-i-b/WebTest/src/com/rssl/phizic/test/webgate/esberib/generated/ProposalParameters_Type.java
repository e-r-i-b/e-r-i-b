/**
 * ProposalParameters_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Параметры предложений
 */
public class ProposalParameters_Type  implements java.io.Serializable {
    /* Название таблицы процентных ставок */
    private java.lang.String tableName;

    private com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] columns;

    private com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] rows;

    private com.rssl.phizic.test.webgate.esberib.generated.Element_Type[] elements;

    /* Минимальная процентная ставка по предложению */
    private java.lang.String rateMin;

    /* Максимальная процентная ставка по предложению */
    private java.lang.String rateMax;

    /* Минимальный лимит по предложению */
    private java.lang.String limitMin;

    /* Максимальный лимит по предложению */
    private java.lang.String limitMax;

    /* Минимальный период по предложению */
    private java.lang.String periodMin;

    /* Максимальный период по предложению */
    private java.lang.String periodMax;

    public ProposalParameters_Type() {
    }

    public ProposalParameters_Type(
           java.lang.String tableName,
           com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] columns,
           com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] rows,
           com.rssl.phizic.test.webgate.esberib.generated.Element_Type[] elements,
           java.lang.String rateMin,
           java.lang.String rateMax,
           java.lang.String limitMin,
           java.lang.String limitMax,
           java.lang.String periodMin,
           java.lang.String periodMax) {
           this.tableName = tableName;
           this.columns = columns;
           this.rows = rows;
           this.elements = elements;
           this.rateMin = rateMin;
           this.rateMax = rateMax;
           this.limitMin = limitMin;
           this.limitMax = limitMax;
           this.periodMin = periodMin;
           this.periodMax = periodMax;
    }


    /**
     * Gets the tableName value for this ProposalParameters_Type.
     * 
     * @return tableName   * Название таблицы процентных ставок
     */
    public java.lang.String getTableName() {
        return tableName;
    }


    /**
     * Sets the tableName value for this ProposalParameters_Type.
     * 
     * @param tableName   * Название таблицы процентных ставок
     */
    public void setTableName(java.lang.String tableName) {
        this.tableName = tableName;
    }


    /**
     * Gets the columns value for this ProposalParameters_Type.
     * 
     * @return columns
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] getColumns() {
        return columns;
    }


    /**
     * Sets the columns value for this ProposalParameters_Type.
     * 
     * @param columns
     */
    public void setColumns(com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] columns) {
        this.columns = columns;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.Table_Type getColumns(int i) {
        return this.columns[i];
    }

    public void setColumns(int i, com.rssl.phizic.test.webgate.esberib.generated.Table_Type _value) {
        this.columns[i] = _value;
    }


    /**
     * Gets the rows value for this ProposalParameters_Type.
     * 
     * @return rows
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] getRows() {
        return rows;
    }


    /**
     * Sets the rows value for this ProposalParameters_Type.
     * 
     * @param rows
     */
    public void setRows(com.rssl.phizic.test.webgate.esberib.generated.Table_Type[] rows) {
        this.rows = rows;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.Table_Type getRows(int i) {
        return this.rows[i];
    }

    public void setRows(int i, com.rssl.phizic.test.webgate.esberib.generated.Table_Type _value) {
        this.rows[i] = _value;
    }


    /**
     * Gets the elements value for this ProposalParameters_Type.
     * 
     * @return elements
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Element_Type[] getElements() {
        return elements;
    }


    /**
     * Sets the elements value for this ProposalParameters_Type.
     * 
     * @param elements
     */
    public void setElements(com.rssl.phizic.test.webgate.esberib.generated.Element_Type[] elements) {
        this.elements = elements;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.Element_Type getElements(int i) {
        return this.elements[i];
    }

    public void setElements(int i, com.rssl.phizic.test.webgate.esberib.generated.Element_Type _value) {
        this.elements[i] = _value;
    }


    /**
     * Gets the rateMin value for this ProposalParameters_Type.
     * 
     * @return rateMin   * Минимальная процентная ставка по предложению
     */
    public java.lang.String getRateMin() {
        return rateMin;
    }


    /**
     * Sets the rateMin value for this ProposalParameters_Type.
     * 
     * @param rateMin   * Минимальная процентная ставка по предложению
     */
    public void setRateMin(java.lang.String rateMin) {
        this.rateMin = rateMin;
    }


    /**
     * Gets the rateMax value for this ProposalParameters_Type.
     * 
     * @return rateMax   * Максимальная процентная ставка по предложению
     */
    public java.lang.String getRateMax() {
        return rateMax;
    }


    /**
     * Sets the rateMax value for this ProposalParameters_Type.
     * 
     * @param rateMax   * Максимальная процентная ставка по предложению
     */
    public void setRateMax(java.lang.String rateMax) {
        this.rateMax = rateMax;
    }


    /**
     * Gets the limitMin value for this ProposalParameters_Type.
     * 
     * @return limitMin   * Минимальный лимит по предложению
     */
    public java.lang.String getLimitMin() {
        return limitMin;
    }


    /**
     * Sets the limitMin value for this ProposalParameters_Type.
     * 
     * @param limitMin   * Минимальный лимит по предложению
     */
    public void setLimitMin(java.lang.String limitMin) {
        this.limitMin = limitMin;
    }


    /**
     * Gets the limitMax value for this ProposalParameters_Type.
     * 
     * @return limitMax   * Максимальный лимит по предложению
     */
    public java.lang.String getLimitMax() {
        return limitMax;
    }


    /**
     * Sets the limitMax value for this ProposalParameters_Type.
     * 
     * @param limitMax   * Максимальный лимит по предложению
     */
    public void setLimitMax(java.lang.String limitMax) {
        this.limitMax = limitMax;
    }


    /**
     * Gets the periodMin value for this ProposalParameters_Type.
     * 
     * @return periodMin   * Минимальный период по предложению
     */
    public java.lang.String getPeriodMin() {
        return periodMin;
    }


    /**
     * Sets the periodMin value for this ProposalParameters_Type.
     * 
     * @param periodMin   * Минимальный период по предложению
     */
    public void setPeriodMin(java.lang.String periodMin) {
        this.periodMin = periodMin;
    }


    /**
     * Gets the periodMax value for this ProposalParameters_Type.
     * 
     * @return periodMax   * Максимальный период по предложению
     */
    public java.lang.String getPeriodMax() {
        return periodMax;
    }


    /**
     * Sets the periodMax value for this ProposalParameters_Type.
     * 
     * @param periodMax   * Максимальный период по предложению
     */
    public void setPeriodMax(java.lang.String periodMax) {
        this.periodMax = periodMax;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProposalParameters_Type)) return false;
        ProposalParameters_Type other = (ProposalParameters_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tableName==null && other.getTableName()==null) || 
             (this.tableName!=null &&
              this.tableName.equals(other.getTableName()))) &&
            ((this.columns==null && other.getColumns()==null) || 
             (this.columns!=null &&
              java.util.Arrays.equals(this.columns, other.getColumns()))) &&
            ((this.rows==null && other.getRows()==null) || 
             (this.rows!=null &&
              java.util.Arrays.equals(this.rows, other.getRows()))) &&
            ((this.elements==null && other.getElements()==null) || 
             (this.elements!=null &&
              java.util.Arrays.equals(this.elements, other.getElements()))) &&
            ((this.rateMin==null && other.getRateMin()==null) || 
             (this.rateMin!=null &&
              this.rateMin.equals(other.getRateMin()))) &&
            ((this.rateMax==null && other.getRateMax()==null) || 
             (this.rateMax!=null &&
              this.rateMax.equals(other.getRateMax()))) &&
            ((this.limitMin==null && other.getLimitMin()==null) || 
             (this.limitMin!=null &&
              this.limitMin.equals(other.getLimitMin()))) &&
            ((this.limitMax==null && other.getLimitMax()==null) || 
             (this.limitMax!=null &&
              this.limitMax.equals(other.getLimitMax()))) &&
            ((this.periodMin==null && other.getPeriodMin()==null) || 
             (this.periodMin!=null &&
              this.periodMin.equals(other.getPeriodMin()))) &&
            ((this.periodMax==null && other.getPeriodMax()==null) || 
             (this.periodMax!=null &&
              this.periodMax.equals(other.getPeriodMax())));
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
        if (getTableName() != null) {
            _hashCode += getTableName().hashCode();
        }
        if (getColumns() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getColumns());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getColumns(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRows() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRows());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRows(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getElements() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElements());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElements(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRateMin() != null) {
            _hashCode += getRateMin().hashCode();
        }
        if (getRateMax() != null) {
            _hashCode += getRateMax().hashCode();
        }
        if (getLimitMin() != null) {
            _hashCode += getLimitMin().hashCode();
        }
        if (getLimitMax() != null) {
            _hashCode += getLimitMax().hashCode();
        }
        if (getPeriodMin() != null) {
            _hashCode += getPeriodMin().hashCode();
        }
        if (getPeriodMax() != null) {
            _hashCode += getPeriodMax().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProposalParameters_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProposalParameters_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tableName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TableName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("columns");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Columns"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Columns"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rows");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Rows"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Rows"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elements");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Elements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Elements"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateMin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RateMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateMax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RateMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitMin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LimitMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitMax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LimitMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodMin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PeriodMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodMax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PeriodMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
