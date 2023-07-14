/**
 * InternalProduct_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Инфомрация по продуктам компании
 */
public class InternalProduct_Type  implements java.io.Serializable {
    /* Значение типа продукта */
    private java.lang.String productId;

    /* Название продаваемого продукта, с которым связан лимит */
    private java.lang.String productName;

    /* Вид продукта */
    private java.lang.String productType;

    /* Код типа продукта в соответствии с справочником TSM */
    private java.lang.String targetProductType;

    /* Код  продукта в соответствии с справочником TSM */
    private java.lang.String targetProduct;

    /* Код субпродукта в соответствии с справочником TSM */
    private java.lang.String targetProductSub;

    /* Флаг контура TSM */
    private java.lang.String TSMConture;

    /* Приоритет продукта в предложении */
    private java.lang.String priority;

    /* Признак основного продукта предложения */
    private java.lang.String primaryFlag;

    /* Код продукта АП */
    private java.lang.String mainProductId;

    /* Дата окончания действия лимита */
    private java.lang.String expDate;

    /* Символьный код валюты (RUR, EUR, USD) */
    private java.lang.String curCode;

    private com.rssl.phizic.test.webgate.esberib.generated.ProposalParameters_Type proposalParameters;

    public InternalProduct_Type() {
    }

    public InternalProduct_Type(
           java.lang.String productId,
           java.lang.String productName,
           java.lang.String productType,
           java.lang.String targetProductType,
           java.lang.String targetProduct,
           java.lang.String targetProductSub,
           java.lang.String TSMConture,
           java.lang.String priority,
           java.lang.String primaryFlag,
           java.lang.String mainProductId,
           java.lang.String expDate,
           java.lang.String curCode,
           com.rssl.phizic.test.webgate.esberib.generated.ProposalParameters_Type proposalParameters) {
           this.productId = productId;
           this.productName = productName;
           this.productType = productType;
           this.targetProductType = targetProductType;
           this.targetProduct = targetProduct;
           this.targetProductSub = targetProductSub;
           this.TSMConture = TSMConture;
           this.priority = priority;
           this.primaryFlag = primaryFlag;
           this.mainProductId = mainProductId;
           this.expDate = expDate;
           this.curCode = curCode;
           this.proposalParameters = proposalParameters;
    }


    /**
     * Gets the productId value for this InternalProduct_Type.
     * 
     * @return productId   * Значение типа продукта
     */
    public java.lang.String getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this InternalProduct_Type.
     * 
     * @param productId   * Значение типа продукта
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }


    /**
     * Gets the productName value for this InternalProduct_Type.
     * 
     * @return productName   * Название продаваемого продукта, с которым связан лимит
     */
    public java.lang.String getProductName() {
        return productName;
    }


    /**
     * Sets the productName value for this InternalProduct_Type.
     * 
     * @param productName   * Название продаваемого продукта, с которым связан лимит
     */
    public void setProductName(java.lang.String productName) {
        this.productName = productName;
    }


    /**
     * Gets the productType value for this InternalProduct_Type.
     * 
     * @return productType   * Вид продукта
     */
    public java.lang.String getProductType() {
        return productType;
    }


    /**
     * Sets the productType value for this InternalProduct_Type.
     * 
     * @param productType   * Вид продукта
     */
    public void setProductType(java.lang.String productType) {
        this.productType = productType;
    }


    /**
     * Gets the targetProductType value for this InternalProduct_Type.
     * 
     * @return targetProductType   * Код типа продукта в соответствии с справочником TSM
     */
    public java.lang.String getTargetProductType() {
        return targetProductType;
    }


    /**
     * Sets the targetProductType value for this InternalProduct_Type.
     * 
     * @param targetProductType   * Код типа продукта в соответствии с справочником TSM
     */
    public void setTargetProductType(java.lang.String targetProductType) {
        this.targetProductType = targetProductType;
    }


    /**
     * Gets the targetProduct value for this InternalProduct_Type.
     * 
     * @return targetProduct   * Код  продукта в соответствии с справочником TSM
     */
    public java.lang.String getTargetProduct() {
        return targetProduct;
    }


    /**
     * Sets the targetProduct value for this InternalProduct_Type.
     * 
     * @param targetProduct   * Код  продукта в соответствии с справочником TSM
     */
    public void setTargetProduct(java.lang.String targetProduct) {
        this.targetProduct = targetProduct;
    }


    /**
     * Gets the targetProductSub value for this InternalProduct_Type.
     * 
     * @return targetProductSub   * Код субпродукта в соответствии с справочником TSM
     */
    public java.lang.String getTargetProductSub() {
        return targetProductSub;
    }


    /**
     * Sets the targetProductSub value for this InternalProduct_Type.
     * 
     * @param targetProductSub   * Код субпродукта в соответствии с справочником TSM
     */
    public void setTargetProductSub(java.lang.String targetProductSub) {
        this.targetProductSub = targetProductSub;
    }


    /**
     * Gets the TSMConture value for this InternalProduct_Type.
     * 
     * @return TSMConture   * Флаг контура TSM
     */
    public java.lang.String getTSMConture() {
        return TSMConture;
    }


    /**
     * Sets the TSMConture value for this InternalProduct_Type.
     * 
     * @param TSMConture   * Флаг контура TSM
     */
    public void setTSMConture(java.lang.String TSMConture) {
        this.TSMConture = TSMConture;
    }


    /**
     * Gets the priority value for this InternalProduct_Type.
     * 
     * @return priority   * Приоритет продукта в предложении
     */
    public java.lang.String getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this InternalProduct_Type.
     * 
     * @param priority   * Приоритет продукта в предложении
     */
    public void setPriority(java.lang.String priority) {
        this.priority = priority;
    }


    /**
     * Gets the primaryFlag value for this InternalProduct_Type.
     * 
     * @return primaryFlag   * Признак основного продукта предложения
     */
    public java.lang.String getPrimaryFlag() {
        return primaryFlag;
    }


    /**
     * Sets the primaryFlag value for this InternalProduct_Type.
     * 
     * @param primaryFlag   * Признак основного продукта предложения
     */
    public void setPrimaryFlag(java.lang.String primaryFlag) {
        this.primaryFlag = primaryFlag;
    }


    /**
     * Gets the mainProductId value for this InternalProduct_Type.
     * 
     * @return mainProductId   * Код продукта АП
     */
    public java.lang.String getMainProductId() {
        return mainProductId;
    }


    /**
     * Sets the mainProductId value for this InternalProduct_Type.
     * 
     * @param mainProductId   * Код продукта АП
     */
    public void setMainProductId(java.lang.String mainProductId) {
        this.mainProductId = mainProductId;
    }


    /**
     * Gets the expDate value for this InternalProduct_Type.
     * 
     * @return expDate   * Дата окончания действия лимита
     */
    public java.lang.String getExpDate() {
        return expDate;
    }


    /**
     * Sets the expDate value for this InternalProduct_Type.
     * 
     * @param expDate   * Дата окончания действия лимита
     */
    public void setExpDate(java.lang.String expDate) {
        this.expDate = expDate;
    }


    /**
     * Gets the curCode value for this InternalProduct_Type.
     * 
     * @return curCode   * Символьный код валюты (RUR, EUR, USD)
     */
    public java.lang.String getCurCode() {
        return curCode;
    }


    /**
     * Sets the curCode value for this InternalProduct_Type.
     * 
     * @param curCode   * Символьный код валюты (RUR, EUR, USD)
     */
    public void setCurCode(java.lang.String curCode) {
        this.curCode = curCode;
    }


    /**
     * Gets the proposalParameters value for this InternalProduct_Type.
     * 
     * @return proposalParameters
     */
    public com.rssl.phizic.test.webgate.esberib.generated.ProposalParameters_Type getProposalParameters() {
        return proposalParameters;
    }


    /**
     * Sets the proposalParameters value for this InternalProduct_Type.
     * 
     * @param proposalParameters
     */
    public void setProposalParameters(com.rssl.phizic.test.webgate.esberib.generated.ProposalParameters_Type proposalParameters) {
        this.proposalParameters = proposalParameters;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InternalProduct_Type)) return false;
        InternalProduct_Type other = (InternalProduct_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.productId==null && other.getProductId()==null) || 
             (this.productId!=null &&
              this.productId.equals(other.getProductId()))) &&
            ((this.productName==null && other.getProductName()==null) || 
             (this.productName!=null &&
              this.productName.equals(other.getProductName()))) &&
            ((this.productType==null && other.getProductType()==null) || 
             (this.productType!=null &&
              this.productType.equals(other.getProductType()))) &&
            ((this.targetProductType==null && other.getTargetProductType()==null) || 
             (this.targetProductType!=null &&
              this.targetProductType.equals(other.getTargetProductType()))) &&
            ((this.targetProduct==null && other.getTargetProduct()==null) || 
             (this.targetProduct!=null &&
              this.targetProduct.equals(other.getTargetProduct()))) &&
            ((this.targetProductSub==null && other.getTargetProductSub()==null) || 
             (this.targetProductSub!=null &&
              this.targetProductSub.equals(other.getTargetProductSub()))) &&
            ((this.TSMConture==null && other.getTSMConture()==null) || 
             (this.TSMConture!=null &&
              this.TSMConture.equals(other.getTSMConture()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
            ((this.primaryFlag==null && other.getPrimaryFlag()==null) || 
             (this.primaryFlag!=null &&
              this.primaryFlag.equals(other.getPrimaryFlag()))) &&
            ((this.mainProductId==null && other.getMainProductId()==null) || 
             (this.mainProductId!=null &&
              this.mainProductId.equals(other.getMainProductId()))) &&
            ((this.expDate==null && other.getExpDate()==null) || 
             (this.expDate!=null &&
              this.expDate.equals(other.getExpDate()))) &&
            ((this.curCode==null && other.getCurCode()==null) || 
             (this.curCode!=null &&
              this.curCode.equals(other.getCurCode()))) &&
            ((this.proposalParameters==null && other.getProposalParameters()==null) || 
             (this.proposalParameters!=null &&
              this.proposalParameters.equals(other.getProposalParameters())));
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
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getProductName() != null) {
            _hashCode += getProductName().hashCode();
        }
        if (getProductType() != null) {
            _hashCode += getProductType().hashCode();
        }
        if (getTargetProductType() != null) {
            _hashCode += getTargetProductType().hashCode();
        }
        if (getTargetProduct() != null) {
            _hashCode += getTargetProduct().hashCode();
        }
        if (getTargetProductSub() != null) {
            _hashCode += getTargetProductSub().hashCode();
        }
        if (getTSMConture() != null) {
            _hashCode += getTSMConture().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getPrimaryFlag() != null) {
            _hashCode += getPrimaryFlag().hashCode();
        }
        if (getMainProductId() != null) {
            _hashCode += getMainProductId().hashCode();
        }
        if (getExpDate() != null) {
            _hashCode += getExpDate().hashCode();
        }
        if (getCurCode() != null) {
            _hashCode += getCurCode().hashCode();
        }
        if (getProposalParameters() != null) {
            _hashCode += getProposalParameters().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InternalProduct_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InternalProduct_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetProductType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TargetProductType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetProduct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TargetProduct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetProductSub");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TargetProductSub"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TSMConture");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TSMConture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrimaryFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainProductId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MainProductId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExpDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proposalParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProposalParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProposalParameters_Type"));
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
