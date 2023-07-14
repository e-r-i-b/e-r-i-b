/**
 * CustRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class CustRec_Type  implements java.io.Serializable {
    /* Идентификатор потребителя */
    private java.lang.String custId;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.ServiceInfo_Type serviceInfo;

    /* Информации о рублевом счете */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccount_Type[] bnkAccRub;

    /* Информации о валютном счете */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountCur_Type[] bnkAccCur;

    /* Доп. информация */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountAdditionalInfo_Type additionalInfo;

    /* Дата заполнения. */
    private java.lang.String startDate;

    /* ИНН плательщика */
    private java.lang.String taxIdFrom;

    /* Информация о тарифном плане и сегменте */
    private com.rssl.phizicgate.esberibgate.ws.generated.TarifPlanInfo_Type tarifPlanInfo;

    public CustRec_Type() {
    }

    public CustRec_Type(
           java.lang.String custId,
           com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.ServiceInfo_Type serviceInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccount_Type[] bnkAccRub,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountCur_Type[] bnkAccCur,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountAdditionalInfo_Type additionalInfo,
           java.lang.String startDate,
           java.lang.String taxIdFrom,
           com.rssl.phizicgate.esberibgate.ws.generated.TarifPlanInfo_Type tarifPlanInfo) {
           this.custId = custId;
           this.custInfo = custInfo;
           this.serviceInfo = serviceInfo;
           this.bnkAccRub = bnkAccRub;
           this.bnkAccCur = bnkAccCur;
           this.additionalInfo = additionalInfo;
           this.startDate = startDate;
           this.taxIdFrom = taxIdFrom;
           this.tarifPlanInfo = tarifPlanInfo;
    }


    /**
     * Gets the custId value for this CustRec_Type.
     * 
     * @return custId   * Идентификатор потребителя
     */
    public java.lang.String getCustId() {
        return custId;
    }


    /**
     * Sets the custId value for this CustRec_Type.
     * 
     * @param custId   * Идентификатор потребителя
     */
    public void setCustId(java.lang.String custId) {
        this.custId = custId;
    }


    /**
     * Gets the custInfo value for this CustRec_Type.
     * 
     * @return custInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type getCustInfo() {
        return custInfo;
    }


    /**
     * Sets the custInfo value for this CustRec_Type.
     * 
     * @param custInfo
     */
    public void setCustInfo(com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo) {
        this.custInfo = custInfo;
    }


    /**
     * Gets the serviceInfo value for this CustRec_Type.
     * 
     * @return serviceInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ServiceInfo_Type getServiceInfo() {
        return serviceInfo;
    }


    /**
     * Sets the serviceInfo value for this CustRec_Type.
     * 
     * @param serviceInfo
     */
    public void setServiceInfo(com.rssl.phizicgate.esberibgate.ws.generated.ServiceInfo_Type serviceInfo) {
        this.serviceInfo = serviceInfo;
    }


    /**
     * Gets the bnkAccRub value for this CustRec_Type.
     * 
     * @return bnkAccRub   * Информации о рублевом счете
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccount_Type[] getBnkAccRub() {
        return bnkAccRub;
    }


    /**
     * Sets the bnkAccRub value for this CustRec_Type.
     * 
     * @param bnkAccRub   * Информации о рублевом счете
     */
    public void setBnkAccRub(com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccount_Type[] bnkAccRub) {
        this.bnkAccRub = bnkAccRub;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccount_Type getBnkAccRub(int i) {
        return this.bnkAccRub[i];
    }

    public void setBnkAccRub(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccount_Type _value) {
        this.bnkAccRub[i] = _value;
    }


    /**
     * Gets the bnkAccCur value for this CustRec_Type.
     * 
     * @return bnkAccCur   * Информации о валютном счете
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountCur_Type[] getBnkAccCur() {
        return bnkAccCur;
    }


    /**
     * Sets the bnkAccCur value for this CustRec_Type.
     * 
     * @param bnkAccCur   * Информации о валютном счете
     */
    public void setBnkAccCur(com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountCur_Type[] bnkAccCur) {
        this.bnkAccCur = bnkAccCur;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountCur_Type getBnkAccCur(int i) {
        return this.bnkAccCur[i];
    }

    public void setBnkAccCur(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountCur_Type _value) {
        this.bnkAccCur[i] = _value;
    }


    /**
     * Gets the additionalInfo value for this CustRec_Type.
     * 
     * @return additionalInfo   * Доп. информация
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountAdditionalInfo_Type getAdditionalInfo() {
        return additionalInfo;
    }


    /**
     * Sets the additionalInfo value for this CustRec_Type.
     * 
     * @param additionalInfo   * Доп. информация
     */
    public void setAdditionalInfo(com.rssl.phizicgate.esberibgate.ws.generated.DepoBankAccountAdditionalInfo_Type additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    /**
     * Gets the startDate value for this CustRec_Type.
     * 
     * @return startDate   * Дата заполнения.
     */
    public java.lang.String getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this CustRec_Type.
     * 
     * @param startDate   * Дата заполнения.
     */
    public void setStartDate(java.lang.String startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the taxIdFrom value for this CustRec_Type.
     * 
     * @return taxIdFrom   * ИНН плательщика
     */
    public java.lang.String getTaxIdFrom() {
        return taxIdFrom;
    }


    /**
     * Sets the taxIdFrom value for this CustRec_Type.
     * 
     * @param taxIdFrom   * ИНН плательщика
     */
    public void setTaxIdFrom(java.lang.String taxIdFrom) {
        this.taxIdFrom = taxIdFrom;
    }


    /**
     * Gets the tarifPlanInfo value for this CustRec_Type.
     * 
     * @return tarifPlanInfo   * Информация о тарифном плане и сегменте
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.TarifPlanInfo_Type getTarifPlanInfo() {
        return tarifPlanInfo;
    }


    /**
     * Sets the tarifPlanInfo value for this CustRec_Type.
     * 
     * @param tarifPlanInfo   * Информация о тарифном плане и сегменте
     */
    public void setTarifPlanInfo(com.rssl.phizicgate.esberibgate.ws.generated.TarifPlanInfo_Type tarifPlanInfo) {
        this.tarifPlanInfo = tarifPlanInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustRec_Type)) return false;
        CustRec_Type other = (CustRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custId==null && other.getCustId()==null) || 
             (this.custId!=null &&
              this.custId.equals(other.getCustId()))) &&
            ((this.custInfo==null && other.getCustInfo()==null) || 
             (this.custInfo!=null &&
              this.custInfo.equals(other.getCustInfo()))) &&
            ((this.serviceInfo==null && other.getServiceInfo()==null) || 
             (this.serviceInfo!=null &&
              this.serviceInfo.equals(other.getServiceInfo()))) &&
            ((this.bnkAccRub==null && other.getBnkAccRub()==null) || 
             (this.bnkAccRub!=null &&
              java.util.Arrays.equals(this.bnkAccRub, other.getBnkAccRub()))) &&
            ((this.bnkAccCur==null && other.getBnkAccCur()==null) || 
             (this.bnkAccCur!=null &&
              java.util.Arrays.equals(this.bnkAccCur, other.getBnkAccCur()))) &&
            ((this.additionalInfo==null && other.getAdditionalInfo()==null) || 
             (this.additionalInfo!=null &&
              this.additionalInfo.equals(other.getAdditionalInfo()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.taxIdFrom==null && other.getTaxIdFrom()==null) || 
             (this.taxIdFrom!=null &&
              this.taxIdFrom.equals(other.getTaxIdFrom()))) &&
            ((this.tarifPlanInfo==null && other.getTarifPlanInfo()==null) || 
             (this.tarifPlanInfo!=null &&
              this.tarifPlanInfo.equals(other.getTarifPlanInfo())));
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
        if (getCustId() != null) {
            _hashCode += getCustId().hashCode();
        }
        if (getCustInfo() != null) {
            _hashCode += getCustInfo().hashCode();
        }
        if (getServiceInfo() != null) {
            _hashCode += getServiceInfo().hashCode();
        }
        if (getBnkAccRub() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBnkAccRub());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBnkAccRub(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBnkAccCur() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBnkAccCur());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBnkAccCur(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAdditionalInfo() != null) {
            _hashCode += getAdditionalInfo().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getTaxIdFrom() != null) {
            _hashCode += getTaxIdFrom().hashCode();
        }
        if (getTarifPlanInfo() != null) {
            _hashCode += getTarifPlanInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bnkAccRub");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BnkAccRub"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccount_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bnkAccCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BnkAccCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccountCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AdditionalInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccountAdditionalInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxIdFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxIdFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tarifPlanInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifPlanInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifPlanInfo_Type"));
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
