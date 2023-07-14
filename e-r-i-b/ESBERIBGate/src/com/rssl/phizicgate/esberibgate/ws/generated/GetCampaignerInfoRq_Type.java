/**
 * GetCampaignerInfoRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип запроса для интерфейса SrvGetCampaignerInfo. Предоставление
 * данных по участнику кампании
 */
public class GetCampaignerInfoRq_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    private java.lang.String systemId;

    private com.rssl.phizicgate.esberibgate.ws.generated.CampaignMember_Type campaignMember;

    private com.rssl.phizicgate.esberibgate.ws.generated.Page_Type page;

    public GetCampaignerInfoRq_Type() {
    }

    public GetCampaignerInfoRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           java.lang.String systemId,
           com.rssl.phizicgate.esberibgate.ws.generated.CampaignMember_Type campaignMember,
           com.rssl.phizicgate.esberibgate.ws.generated.Page_Type page) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.systemId = systemId;
           this.campaignMember = campaignMember;
           this.page = page;
    }


    /**
     * Gets the rqUID value for this GetCampaignerInfoRq_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetCampaignerInfoRq_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetCampaignerInfoRq_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetCampaignerInfoRq_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this GetCampaignerInfoRq_Type.
     * 
     * @return SPName
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this GetCampaignerInfoRq_Type.
     * 
     * @param SPName
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the systemId value for this GetCampaignerInfoRq_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this GetCampaignerInfoRq_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the campaignMember value for this GetCampaignerInfoRq_Type.
     * 
     * @return campaignMember
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CampaignMember_Type getCampaignMember() {
        return campaignMember;
    }


    /**
     * Sets the campaignMember value for this GetCampaignerInfoRq_Type.
     * 
     * @param campaignMember
     */
    public void setCampaignMember(com.rssl.phizicgate.esberibgate.ws.generated.CampaignMember_Type campaignMember) {
        this.campaignMember = campaignMember;
    }


    /**
     * Gets the page value for this GetCampaignerInfoRq_Type.
     * 
     * @return page
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Page_Type getPage() {
        return page;
    }


    /**
     * Sets the page value for this GetCampaignerInfoRq_Type.
     * 
     * @param page
     */
    public void setPage(com.rssl.phizicgate.esberibgate.ws.generated.Page_Type page) {
        this.page = page;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCampaignerInfoRq_Type)) return false;
        GetCampaignerInfoRq_Type other = (GetCampaignerInfoRq_Type) obj;
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
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.campaignMember==null && other.getCampaignMember()==null) || 
             (this.campaignMember!=null &&
              this.campaignMember.equals(other.getCampaignMember()))) &&
            ((this.page==null && other.getPage()==null) || 
             (this.page!=null &&
              this.page.equals(other.getPage())));
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
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getCampaignMember() != null) {
            _hashCode += getCampaignMember().hashCode();
        }
        if (getPage() != null) {
            _hashCode += getPage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCampaignerInfoRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetCampaignerInfoRq_Type"));
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
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignMember");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignMember"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignMember_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("page");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Page"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Page_Type"));
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
