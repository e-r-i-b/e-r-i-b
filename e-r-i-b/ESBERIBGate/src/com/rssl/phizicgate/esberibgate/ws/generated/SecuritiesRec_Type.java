/**
 * SecuritiesRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информационная запись по ценной бумаге
 */
public class SecuritiesRec_Type  implements java.io.Serializable {
    /* ВСП, осуществившее выдачу ЦБ */
    private com.rssl.phizicgate.esberibgate.ws.generated.Issuer_Type issuer;

    /* Держатель ЦБ */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesHolder_Type holder;

    /* Бланк ценной бумаги */
    private com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo;

    /* Дата оформления ценной бумаги */
    private java.lang.String formDt;

    /* Базовая информация ценной бумаги */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type securitiesInfo;

    /* Информация о договоре хранения */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesDocument_Type securitiesDocument;

    public SecuritiesRec_Type() {
    }

    public SecuritiesRec_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.Issuer_Type issuer,
           com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesHolder_Type holder,
           com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo,
           java.lang.String formDt,
           com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type securitiesInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesDocument_Type securitiesDocument) {
           this.issuer = issuer;
           this.holder = holder;
           this.blankInfo = blankInfo;
           this.formDt = formDt;
           this.securitiesInfo = securitiesInfo;
           this.securitiesDocument = securitiesDocument;
    }


    /**
     * Gets the issuer value for this SecuritiesRec_Type.
     * 
     * @return issuer   * ВСП, осуществившее выдачу ЦБ
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Issuer_Type getIssuer() {
        return issuer;
    }


    /**
     * Sets the issuer value for this SecuritiesRec_Type.
     * 
     * @param issuer   * ВСП, осуществившее выдачу ЦБ
     */
    public void setIssuer(com.rssl.phizicgate.esberibgate.ws.generated.Issuer_Type issuer) {
        this.issuer = issuer;
    }


    /**
     * Gets the holder value for this SecuritiesRec_Type.
     * 
     * @return holder   * Держатель ЦБ
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesHolder_Type getHolder() {
        return holder;
    }


    /**
     * Sets the holder value for this SecuritiesRec_Type.
     * 
     * @param holder   * Держатель ЦБ
     */
    public void setHolder(com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesHolder_Type holder) {
        this.holder = holder;
    }


    /**
     * Gets the blankInfo value for this SecuritiesRec_Type.
     * 
     * @return blankInfo   * Бланк ценной бумаги
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type getBlankInfo() {
        return blankInfo;
    }


    /**
     * Sets the blankInfo value for this SecuritiesRec_Type.
     * 
     * @param blankInfo   * Бланк ценной бумаги
     */
    public void setBlankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo) {
        this.blankInfo = blankInfo;
    }


    /**
     * Gets the formDt value for this SecuritiesRec_Type.
     * 
     * @return formDt   * Дата оформления ценной бумаги
     */
    public java.lang.String getFormDt() {
        return formDt;
    }


    /**
     * Sets the formDt value for this SecuritiesRec_Type.
     * 
     * @param formDt   * Дата оформления ценной бумаги
     */
    public void setFormDt(java.lang.String formDt) {
        this.formDt = formDt;
    }


    /**
     * Gets the securitiesInfo value for this SecuritiesRec_Type.
     * 
     * @return securitiesInfo   * Базовая информация ценной бумаги
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type getSecuritiesInfo() {
        return securitiesInfo;
    }


    /**
     * Sets the securitiesInfo value for this SecuritiesRec_Type.
     * 
     * @param securitiesInfo   * Базовая информация ценной бумаги
     */
    public void setSecuritiesInfo(com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesInfo_Type securitiesInfo) {
        this.securitiesInfo = securitiesInfo;
    }


    /**
     * Gets the securitiesDocument value for this SecuritiesRec_Type.
     * 
     * @return securitiesDocument   * Информация о договоре хранения
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesDocument_Type getSecuritiesDocument() {
        return securitiesDocument;
    }


    /**
     * Sets the securitiesDocument value for this SecuritiesRec_Type.
     * 
     * @param securitiesDocument   * Информация о договоре хранения
     */
    public void setSecuritiesDocument(com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesDocument_Type securitiesDocument) {
        this.securitiesDocument = securitiesDocument;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesRec_Type)) return false;
        SecuritiesRec_Type other = (SecuritiesRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.issuer==null && other.getIssuer()==null) || 
             (this.issuer!=null &&
              this.issuer.equals(other.getIssuer()))) &&
            ((this.holder==null && other.getHolder()==null) || 
             (this.holder!=null &&
              this.holder.equals(other.getHolder()))) &&
            ((this.blankInfo==null && other.getBlankInfo()==null) || 
             (this.blankInfo!=null &&
              this.blankInfo.equals(other.getBlankInfo()))) &&
            ((this.formDt==null && other.getFormDt()==null) || 
             (this.formDt!=null &&
              this.formDt.equals(other.getFormDt()))) &&
            ((this.securitiesInfo==null && other.getSecuritiesInfo()==null) || 
             (this.securitiesInfo!=null &&
              this.securitiesInfo.equals(other.getSecuritiesInfo()))) &&
            ((this.securitiesDocument==null && other.getSecuritiesDocument()==null) || 
             (this.securitiesDocument!=null &&
              this.securitiesDocument.equals(other.getSecuritiesDocument())));
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
        if (getIssuer() != null) {
            _hashCode += getIssuer().hashCode();
        }
        if (getHolder() != null) {
            _hashCode += getHolder().hashCode();
        }
        if (getBlankInfo() != null) {
            _hashCode += getBlankInfo().hashCode();
        }
        if (getFormDt() != null) {
            _hashCode += getFormDt().hashCode();
        }
        if (getSecuritiesInfo() != null) {
            _hashCode += getSecuritiesInfo().hashCode();
        }
        if (getSecuritiesDocument() != null) {
            _hashCode += getSecuritiesDocument().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecuritiesRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Issuer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Issuer_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("holder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Holder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesHolder_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FormDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securitiesInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securitiesDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesDocument_Type"));
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
