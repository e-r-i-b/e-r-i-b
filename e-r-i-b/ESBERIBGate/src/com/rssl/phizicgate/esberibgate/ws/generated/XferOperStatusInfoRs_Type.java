/**
 * XferOperStatusInfoRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип ответа для интерфейса SrvXferOperStatusInfo. Запрос на уточнение
 * статуса операции из ЕРИБ
 */
public class XferOperStatusInfoRs_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    /* Результат выполнения оригинального запроса */
    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type statusOriginalRequest;

    private com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDDO TDDO;

    private com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCDO TCDO;

    private com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDIO TDIO;

    private com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCIO TCIO;

    public XferOperStatusInfoRs_Type() {
    }

    public XferOperStatusInfoRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type statusOriginalRequest,
           com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDDO TDDO,
           com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCDO TCDO,
           com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDIO TDIO,
           com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCIO TCIO) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.status = status;
           this.statusOriginalRequest = statusOriginalRequest;
           this.TDDO = TDDO;
           this.TCDO = TCDO;
           this.TDIO = TDIO;
           this.TCIO = TCIO;
    }


    /**
     * Gets the rqUID value for this XferOperStatusInfoRs_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this XferOperStatusInfoRs_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this XferOperStatusInfoRs_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this XferOperStatusInfoRs_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this XferOperStatusInfoRs_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this XferOperStatusInfoRs_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the status value for this XferOperStatusInfoRs_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this XferOperStatusInfoRs_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the statusOriginalRequest value for this XferOperStatusInfoRs_Type.
     * 
     * @return statusOriginalRequest   * Результат выполнения оригинального запроса
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatusOriginalRequest() {
        return statusOriginalRequest;
    }


    /**
     * Sets the statusOriginalRequest value for this XferOperStatusInfoRs_Type.
     * 
     * @param statusOriginalRequest   * Результат выполнения оригинального запроса
     */
    public void setStatusOriginalRequest(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type statusOriginalRequest) {
        this.statusOriginalRequest = statusOriginalRequest;
    }


    /**
     * Gets the TDDO value for this XferOperStatusInfoRs_Type.
     * 
     * @return TDDO
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDDO getTDDO() {
        return TDDO;
    }


    /**
     * Sets the TDDO value for this XferOperStatusInfoRs_Type.
     * 
     * @param TDDO
     */
    public void setTDDO(com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDDO TDDO) {
        this.TDDO = TDDO;
    }


    /**
     * Gets the TCDO value for this XferOperStatusInfoRs_Type.
     * 
     * @return TCDO
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCDO getTCDO() {
        return TCDO;
    }


    /**
     * Sets the TCDO value for this XferOperStatusInfoRs_Type.
     * 
     * @param TCDO
     */
    public void setTCDO(com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCDO TCDO) {
        this.TCDO = TCDO;
    }


    /**
     * Gets the TDIO value for this XferOperStatusInfoRs_Type.
     * 
     * @return TDIO
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDIO getTDIO() {
        return TDIO;
    }


    /**
     * Sets the TDIO value for this XferOperStatusInfoRs_Type.
     * 
     * @param TDIO
     */
    public void setTDIO(com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTDIO TDIO) {
        this.TDIO = TDIO;
    }


    /**
     * Gets the TCIO value for this XferOperStatusInfoRs_Type.
     * 
     * @return TCIO
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCIO getTCIO() {
        return TCIO;
    }


    /**
     * Sets the TCIO value for this XferOperStatusInfoRs_Type.
     * 
     * @param TCIO
     */
    public void setTCIO(com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_TypeTCIO TCIO) {
        this.TCIO = TCIO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XferOperStatusInfoRs_Type)) return false;
        XferOperStatusInfoRs_Type other = (XferOperStatusInfoRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.statusOriginalRequest==null && other.getStatusOriginalRequest()==null) || 
             (this.statusOriginalRequest!=null &&
              this.statusOriginalRequest.equals(other.getStatusOriginalRequest()))) &&
            ((this.TDDO==null && other.getTDDO()==null) || 
             (this.TDDO!=null &&
              this.TDDO.equals(other.getTDDO()))) &&
            ((this.TCDO==null && other.getTCDO()==null) || 
             (this.TCDO!=null &&
              this.TCDO.equals(other.getTCDO()))) &&
            ((this.TDIO==null && other.getTDIO()==null) || 
             (this.TDIO!=null &&
              this.TDIO.equals(other.getTDIO()))) &&
            ((this.TCIO==null && other.getTCIO()==null) || 
             (this.TCIO!=null &&
              this.TCIO.equals(other.getTCIO())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getStatusOriginalRequest() != null) {
            _hashCode += getStatusOriginalRequest().hashCode();
        }
        if (getTDDO() != null) {
            _hashCode += getTDDO().hashCode();
        }
        if (getTCDO() != null) {
            _hashCode += getTCDO().hashCode();
        }
        if (getTDIO() != null) {
            _hashCode += getTDIO().hashCode();
        }
        if (getTCIO() != null) {
            _hashCode += getTCIO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XferOperStatusInfoRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferOperStatusInfoRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusOriginalRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusOriginalRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TDDO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TDDO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TDDO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TCDO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TCDO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TCDO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TDIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TDIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TDIO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TCIO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TCIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TCIO"));
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
