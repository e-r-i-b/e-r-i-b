/**
 * TransferInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Параметры операции (ДЕПО).
 */
public class TransferInfo_Type  implements java.io.Serializable {
    /* Дата поручения */
    private java.lang.String documentDate;

    /* Номер поручения */
    private long documentNumber;

    /* Тип операции */
    private com.rssl.phizic.test.webgate.esberib.generated.DepoOperType_Type operType;

    /* Содержание операции */
    private com.rssl.phizic.test.webgate.esberib.generated.DepoOperationSubType_Type operationSubType;

    /* Комментарий к операции */
    private java.lang.String operationDesc;

    /* Тип и номер раздела */
    private com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type divisionNumber;

    /* Депозитарный  код выпуска ценной бумаги (партии неэмисионной
     * ЦБ) */
    private java.lang.String insideCode;

    /* Кол-во ценных бумаг */
    private long securityCount;

    /* Основание операции */
    private java.lang.String operationReason;

    /* Описание договора */
    private com.rssl.phizic.test.webgate.esberib.generated.DepoDetailOperationReason_Type detailOperationReason;

    /* Информация о противоположной стороне операции */
    private com.rssl.phizic.test.webgate.esberib.generated.TransferRcpInfo_Type transferRcpInfo;

    public TransferInfo_Type() {
    }

    public TransferInfo_Type(
           java.lang.String documentDate,
           long documentNumber,
           com.rssl.phizic.test.webgate.esberib.generated.DepoOperType_Type operType,
           com.rssl.phizic.test.webgate.esberib.generated.DepoOperationSubType_Type operationSubType,
           java.lang.String operationDesc,
           com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type divisionNumber,
           java.lang.String insideCode,
           long securityCount,
           java.lang.String operationReason,
           com.rssl.phizic.test.webgate.esberib.generated.DepoDetailOperationReason_Type detailOperationReason,
           com.rssl.phizic.test.webgate.esberib.generated.TransferRcpInfo_Type transferRcpInfo) {
           this.documentDate = documentDate;
           this.documentNumber = documentNumber;
           this.operType = operType;
           this.operationSubType = operationSubType;
           this.operationDesc = operationDesc;
           this.divisionNumber = divisionNumber;
           this.insideCode = insideCode;
           this.securityCount = securityCount;
           this.operationReason = operationReason;
           this.detailOperationReason = detailOperationReason;
           this.transferRcpInfo = transferRcpInfo;
    }


    /**
     * Gets the documentDate value for this TransferInfo_Type.
     * 
     * @return documentDate   * Дата поручения
     */
    public java.lang.String getDocumentDate() {
        return documentDate;
    }


    /**
     * Sets the documentDate value for this TransferInfo_Type.
     * 
     * @param documentDate   * Дата поручения
     */
    public void setDocumentDate(java.lang.String documentDate) {
        this.documentDate = documentDate;
    }


    /**
     * Gets the documentNumber value for this TransferInfo_Type.
     * 
     * @return documentNumber   * Номер поручения
     */
    public long getDocumentNumber() {
        return documentNumber;
    }


    /**
     * Sets the documentNumber value for this TransferInfo_Type.
     * 
     * @param documentNumber   * Номер поручения
     */
    public void setDocumentNumber(long documentNumber) {
        this.documentNumber = documentNumber;
    }


    /**
     * Gets the operType value for this TransferInfo_Type.
     * 
     * @return operType   * Тип операции
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoOperType_Type getOperType() {
        return operType;
    }


    /**
     * Sets the operType value for this TransferInfo_Type.
     * 
     * @param operType   * Тип операции
     */
    public void setOperType(com.rssl.phizic.test.webgate.esberib.generated.DepoOperType_Type operType) {
        this.operType = operType;
    }


    /**
     * Gets the operationSubType value for this TransferInfo_Type.
     * 
     * @return operationSubType   * Содержание операции
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoOperationSubType_Type getOperationSubType() {
        return operationSubType;
    }


    /**
     * Sets the operationSubType value for this TransferInfo_Type.
     * 
     * @param operationSubType   * Содержание операции
     */
    public void setOperationSubType(com.rssl.phizic.test.webgate.esberib.generated.DepoOperationSubType_Type operationSubType) {
        this.operationSubType = operationSubType;
    }


    /**
     * Gets the operationDesc value for this TransferInfo_Type.
     * 
     * @return operationDesc   * Комментарий к операции
     */
    public java.lang.String getOperationDesc() {
        return operationDesc;
    }


    /**
     * Sets the operationDesc value for this TransferInfo_Type.
     * 
     * @param operationDesc   * Комментарий к операции
     */
    public void setOperationDesc(java.lang.String operationDesc) {
        this.operationDesc = operationDesc;
    }


    /**
     * Gets the divisionNumber value for this TransferInfo_Type.
     * 
     * @return divisionNumber   * Тип и номер раздела
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type getDivisionNumber() {
        return divisionNumber;
    }


    /**
     * Sets the divisionNumber value for this TransferInfo_Type.
     * 
     * @param divisionNumber   * Тип и номер раздела
     */
    public void setDivisionNumber(com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type divisionNumber) {
        this.divisionNumber = divisionNumber;
    }


    /**
     * Gets the insideCode value for this TransferInfo_Type.
     * 
     * @return insideCode   * Депозитарный  код выпуска ценной бумаги (партии неэмисионной
     * ЦБ)
     */
    public java.lang.String getInsideCode() {
        return insideCode;
    }


    /**
     * Sets the insideCode value for this TransferInfo_Type.
     * 
     * @param insideCode   * Депозитарный  код выпуска ценной бумаги (партии неэмисионной
     * ЦБ)
     */
    public void setInsideCode(java.lang.String insideCode) {
        this.insideCode = insideCode;
    }


    /**
     * Gets the securityCount value for this TransferInfo_Type.
     * 
     * @return securityCount   * Кол-во ценных бумаг
     */
    public long getSecurityCount() {
        return securityCount;
    }


    /**
     * Sets the securityCount value for this TransferInfo_Type.
     * 
     * @param securityCount   * Кол-во ценных бумаг
     */
    public void setSecurityCount(long securityCount) {
        this.securityCount = securityCount;
    }


    /**
     * Gets the operationReason value for this TransferInfo_Type.
     * 
     * @return operationReason   * Основание операции
     */
    public java.lang.String getOperationReason() {
        return operationReason;
    }


    /**
     * Sets the operationReason value for this TransferInfo_Type.
     * 
     * @param operationReason   * Основание операции
     */
    public void setOperationReason(java.lang.String operationReason) {
        this.operationReason = operationReason;
    }


    /**
     * Gets the detailOperationReason value for this TransferInfo_Type.
     * 
     * @return detailOperationReason   * Описание договора
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoDetailOperationReason_Type getDetailOperationReason() {
        return detailOperationReason;
    }


    /**
     * Sets the detailOperationReason value for this TransferInfo_Type.
     * 
     * @param detailOperationReason   * Описание договора
     */
    public void setDetailOperationReason(com.rssl.phizic.test.webgate.esberib.generated.DepoDetailOperationReason_Type detailOperationReason) {
        this.detailOperationReason = detailOperationReason;
    }


    /**
     * Gets the transferRcpInfo value for this TransferInfo_Type.
     * 
     * @return transferRcpInfo   * Информация о противоположной стороне операции
     */
    public com.rssl.phizic.test.webgate.esberib.generated.TransferRcpInfo_Type getTransferRcpInfo() {
        return transferRcpInfo;
    }


    /**
     * Sets the transferRcpInfo value for this TransferInfo_Type.
     * 
     * @param transferRcpInfo   * Информация о противоположной стороне операции
     */
    public void setTransferRcpInfo(com.rssl.phizic.test.webgate.esberib.generated.TransferRcpInfo_Type transferRcpInfo) {
        this.transferRcpInfo = transferRcpInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransferInfo_Type)) return false;
        TransferInfo_Type other = (TransferInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.documentDate==null && other.getDocumentDate()==null) || 
             (this.documentDate!=null &&
              this.documentDate.equals(other.getDocumentDate()))) &&
            this.documentNumber == other.getDocumentNumber() &&
            ((this.operType==null && other.getOperType()==null) || 
             (this.operType!=null &&
              this.operType.equals(other.getOperType()))) &&
            ((this.operationSubType==null && other.getOperationSubType()==null) || 
             (this.operationSubType!=null &&
              this.operationSubType.equals(other.getOperationSubType()))) &&
            ((this.operationDesc==null && other.getOperationDesc()==null) || 
             (this.operationDesc!=null &&
              this.operationDesc.equals(other.getOperationDesc()))) &&
            ((this.divisionNumber==null && other.getDivisionNumber()==null) || 
             (this.divisionNumber!=null &&
              this.divisionNumber.equals(other.getDivisionNumber()))) &&
            ((this.insideCode==null && other.getInsideCode()==null) || 
             (this.insideCode!=null &&
              this.insideCode.equals(other.getInsideCode()))) &&
            this.securityCount == other.getSecurityCount() &&
            ((this.operationReason==null && other.getOperationReason()==null) || 
             (this.operationReason!=null &&
              this.operationReason.equals(other.getOperationReason()))) &&
            ((this.detailOperationReason==null && other.getDetailOperationReason()==null) || 
             (this.detailOperationReason!=null &&
              this.detailOperationReason.equals(other.getDetailOperationReason()))) &&
            ((this.transferRcpInfo==null && other.getTransferRcpInfo()==null) || 
             (this.transferRcpInfo!=null &&
              this.transferRcpInfo.equals(other.getTransferRcpInfo())));
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
        if (getDocumentDate() != null) {
            _hashCode += getDocumentDate().hashCode();
        }
        _hashCode += new Long(getDocumentNumber()).hashCode();
        if (getOperType() != null) {
            _hashCode += getOperType().hashCode();
        }
        if (getOperationSubType() != null) {
            _hashCode += getOperationSubType().hashCode();
        }
        if (getOperationDesc() != null) {
            _hashCode += getOperationDesc().hashCode();
        }
        if (getDivisionNumber() != null) {
            _hashCode += getDivisionNumber().hashCode();
        }
        if (getInsideCode() != null) {
            _hashCode += getInsideCode().hashCode();
        }
        _hashCode += new Long(getSecurityCount()).hashCode();
        if (getOperationReason() != null) {
            _hashCode += getOperationReason().hashCode();
        }
        if (getDetailOperationReason() != null) {
            _hashCode += getDetailOperationReason().hashCode();
        }
        if (getTransferRcpInfo() != null) {
            _hashCode += getTransferRcpInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransferInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransferInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoOperType_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationSubType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperationSubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoOperationSubType_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperationDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("divisionNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DivisionNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DivisionNumber_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insideCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsideCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperationReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailOperationReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DetailOperationReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDetailOperationReason_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transferRcpInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransferRcpInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransferRcpInfo_Type"));
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
