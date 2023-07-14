/**
 * CampaignMember_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Данные по участнику
 */
public class CampaignMember_Type  implements java.io.Serializable {
    /* Информация о физическом лице */
    private com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type personInfo;

    /* Код участника кампании */
    private java.lang.String campaignMemberId;

    /* Дата формирования предложения */
    private java.lang.String offerDate;

    /* Название кампании */
    private java.lang.String campaignName;

    /* Идентификатор кампании */
    private java.lang.String campaignId;

    /* Код предложения */
    private java.lang.String sourceCode;

    /* Вид продуктов предложения */
    private java.lang.String prodType;

    /* Наименование предложения */
    private java.lang.String sourceName;

    /* Наименование продукта АП (маркетинговый продукт) */
    private java.lang.String productASName;

    /* Приоритет продукта АП/предложения сквозной */
    private java.math.BigInteger productASPriority;

    /* Текст персонализированного сообщения для клиента */
    private java.lang.String personalText;

    /* Код канала коммуникаций предложения */
    private java.lang.String mediaType;

    /* Идентификатор клиента */
    private java.lang.String clientId;

    /* Наименование сегмента клиента */
    private java.lang.String clientSegment;

    private com.rssl.phizicgate.esberibgate.ws.generated.CardInfo_Type cardInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.PayrollAgree_Type[] payrollAgree;

    /* Признак возможности печати информации о продукте */
    private java.lang.Boolean printingAllowed;

    /* Номер фона для печати предложения */
    private java.lang.String backgroundId;

    /* Поисковое выражение */
    private java.lang.String searchSpec;

    private com.rssl.phizicgate.esberibgate.ws.generated.InternalProduct_Type[] internalProduct;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    /* Канал предложения для поиска. Константа "SBOL" */
    private java.lang.String treatmentType;

    /* Блок данных по top-up кредиту */
    private com.rssl.phizicgate.esberibgate.ws.generated.TopUp_Type topUp;

    public CampaignMember_Type() {
    }

    public CampaignMember_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type personInfo,
           java.lang.String campaignMemberId,
           java.lang.String offerDate,
           java.lang.String campaignName,
           java.lang.String campaignId,
           java.lang.String sourceCode,
           java.lang.String prodType,
           java.lang.String sourceName,
           java.lang.String productASName,
           java.math.BigInteger productASPriority,
           java.lang.String personalText,
           java.lang.String mediaType,
           java.lang.String clientId,
           java.lang.String clientSegment,
           com.rssl.phizicgate.esberibgate.ws.generated.CardInfo_Type cardInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.PayrollAgree_Type[] payrollAgree,
           java.lang.Boolean printingAllowed,
           java.lang.String backgroundId,
           java.lang.String searchSpec,
           com.rssl.phizicgate.esberibgate.ws.generated.InternalProduct_Type[] internalProduct,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           java.lang.String treatmentType,
           com.rssl.phizicgate.esberibgate.ws.generated.TopUp_Type topUp) {
           this.personInfo = personInfo;
           this.campaignMemberId = campaignMemberId;
           this.offerDate = offerDate;
           this.campaignName = campaignName;
           this.campaignId = campaignId;
           this.sourceCode = sourceCode;
           this.prodType = prodType;
           this.sourceName = sourceName;
           this.productASName = productASName;
           this.productASPriority = productASPriority;
           this.personalText = personalText;
           this.mediaType = mediaType;
           this.clientId = clientId;
           this.clientSegment = clientSegment;
           this.cardInfo = cardInfo;
           this.payrollAgree = payrollAgree;
           this.printingAllowed = printingAllowed;
           this.backgroundId = backgroundId;
           this.searchSpec = searchSpec;
           this.internalProduct = internalProduct;
           this.bankInfo = bankInfo;
           this.treatmentType = treatmentType;
           this.topUp = topUp;
    }


    /**
     * Gets the personInfo value for this CampaignMember_Type.
     * 
     * @return personInfo   * Информация о физическом лице
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type getPersonInfo() {
        return personInfo;
    }


    /**
     * Sets the personInfo value for this CampaignMember_Type.
     * 
     * @param personInfo   * Информация о физическом лице
     */
    public void setPersonInfo(com.rssl.phizicgate.esberibgate.ws.generated.PersonInfoSec_Type personInfo) {
        this.personInfo = personInfo;
    }


    /**
     * Gets the campaignMemberId value for this CampaignMember_Type.
     * 
     * @return campaignMemberId   * Код участника кампании
     */
    public java.lang.String getCampaignMemberId() {
        return campaignMemberId;
    }


    /**
     * Sets the campaignMemberId value for this CampaignMember_Type.
     * 
     * @param campaignMemberId   * Код участника кампании
     */
    public void setCampaignMemberId(java.lang.String campaignMemberId) {
        this.campaignMemberId = campaignMemberId;
    }


    /**
     * Gets the offerDate value for this CampaignMember_Type.
     * 
     * @return offerDate   * Дата формирования предложения
     */
    public java.lang.String getOfferDate() {
        return offerDate;
    }


    /**
     * Sets the offerDate value for this CampaignMember_Type.
     * 
     * @param offerDate   * Дата формирования предложения
     */
    public void setOfferDate(java.lang.String offerDate) {
        this.offerDate = offerDate;
    }


    /**
     * Gets the campaignName value for this CampaignMember_Type.
     * 
     * @return campaignName   * Название кампании
     */
    public java.lang.String getCampaignName() {
        return campaignName;
    }


    /**
     * Sets the campaignName value for this CampaignMember_Type.
     * 
     * @param campaignName   * Название кампании
     */
    public void setCampaignName(java.lang.String campaignName) {
        this.campaignName = campaignName;
    }


    /**
     * Gets the campaignId value for this CampaignMember_Type.
     * 
     * @return campaignId   * Идентификатор кампании
     */
    public java.lang.String getCampaignId() {
        return campaignId;
    }


    /**
     * Sets the campaignId value for this CampaignMember_Type.
     * 
     * @param campaignId   * Идентификатор кампании
     */
    public void setCampaignId(java.lang.String campaignId) {
        this.campaignId = campaignId;
    }


    /**
     * Gets the sourceCode value for this CampaignMember_Type.
     * 
     * @return sourceCode   * Код предложения
     */
    public java.lang.String getSourceCode() {
        return sourceCode;
    }


    /**
     * Sets the sourceCode value for this CampaignMember_Type.
     * 
     * @param sourceCode   * Код предложения
     */
    public void setSourceCode(java.lang.String sourceCode) {
        this.sourceCode = sourceCode;
    }


    /**
     * Gets the prodType value for this CampaignMember_Type.
     * 
     * @return prodType   * Вид продуктов предложения
     */
    public java.lang.String getProdType() {
        return prodType;
    }


    /**
     * Sets the prodType value for this CampaignMember_Type.
     * 
     * @param prodType   * Вид продуктов предложения
     */
    public void setProdType(java.lang.String prodType) {
        this.prodType = prodType;
    }


    /**
     * Gets the sourceName value for this CampaignMember_Type.
     * 
     * @return sourceName   * Наименование предложения
     */
    public java.lang.String getSourceName() {
        return sourceName;
    }


    /**
     * Sets the sourceName value for this CampaignMember_Type.
     * 
     * @param sourceName   * Наименование предложения
     */
    public void setSourceName(java.lang.String sourceName) {
        this.sourceName = sourceName;
    }


    /**
     * Gets the productASName value for this CampaignMember_Type.
     * 
     * @return productASName   * Наименование продукта АП (маркетинговый продукт)
     */
    public java.lang.String getProductASName() {
        return productASName;
    }


    /**
     * Sets the productASName value for this CampaignMember_Type.
     * 
     * @param productASName   * Наименование продукта АП (маркетинговый продукт)
     */
    public void setProductASName(java.lang.String productASName) {
        this.productASName = productASName;
    }


    /**
     * Gets the productASPriority value for this CampaignMember_Type.
     * 
     * @return productASPriority   * Приоритет продукта АП/предложения сквозной
     */
    public java.math.BigInteger getProductASPriority() {
        return productASPriority;
    }


    /**
     * Sets the productASPriority value for this CampaignMember_Type.
     * 
     * @param productASPriority   * Приоритет продукта АП/предложения сквозной
     */
    public void setProductASPriority(java.math.BigInteger productASPriority) {
        this.productASPriority = productASPriority;
    }


    /**
     * Gets the personalText value for this CampaignMember_Type.
     * 
     * @return personalText   * Текст персонализированного сообщения для клиента
     */
    public java.lang.String getPersonalText() {
        return personalText;
    }


    /**
     * Sets the personalText value for this CampaignMember_Type.
     * 
     * @param personalText   * Текст персонализированного сообщения для клиента
     */
    public void setPersonalText(java.lang.String personalText) {
        this.personalText = personalText;
    }


    /**
     * Gets the mediaType value for this CampaignMember_Type.
     * 
     * @return mediaType   * Код канала коммуникаций предложения
     */
    public java.lang.String getMediaType() {
        return mediaType;
    }


    /**
     * Sets the mediaType value for this CampaignMember_Type.
     * 
     * @param mediaType   * Код канала коммуникаций предложения
     */
    public void setMediaType(java.lang.String mediaType) {
        this.mediaType = mediaType;
    }


    /**
     * Gets the clientId value for this CampaignMember_Type.
     * 
     * @return clientId   * Идентификатор клиента
     */
    public java.lang.String getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this CampaignMember_Type.
     * 
     * @param clientId   * Идентификатор клиента
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }


    /**
     * Gets the clientSegment value for this CampaignMember_Type.
     * 
     * @return clientSegment   * Наименование сегмента клиента
     */
    public java.lang.String getClientSegment() {
        return clientSegment;
    }


    /**
     * Sets the clientSegment value for this CampaignMember_Type.
     * 
     * @param clientSegment   * Наименование сегмента клиента
     */
    public void setClientSegment(java.lang.String clientSegment) {
        this.clientSegment = clientSegment;
    }


    /**
     * Gets the cardInfo value for this CampaignMember_Type.
     * 
     * @return cardInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardInfo_Type getCardInfo() {
        return cardInfo;
    }


    /**
     * Sets the cardInfo value for this CampaignMember_Type.
     * 
     * @param cardInfo
     */
    public void setCardInfo(com.rssl.phizicgate.esberibgate.ws.generated.CardInfo_Type cardInfo) {
        this.cardInfo = cardInfo;
    }


    /**
     * Gets the payrollAgree value for this CampaignMember_Type.
     * 
     * @return payrollAgree
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PayrollAgree_Type[] getPayrollAgree() {
        return payrollAgree;
    }


    /**
     * Sets the payrollAgree value for this CampaignMember_Type.
     * 
     * @param payrollAgree
     */
    public void setPayrollAgree(com.rssl.phizicgate.esberibgate.ws.generated.PayrollAgree_Type[] payrollAgree) {
        this.payrollAgree = payrollAgree;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.PayrollAgree_Type getPayrollAgree(int i) {
        return this.payrollAgree[i];
    }

    public void setPayrollAgree(int i, com.rssl.phizicgate.esberibgate.ws.generated.PayrollAgree_Type _value) {
        this.payrollAgree[i] = _value;
    }


    /**
     * Gets the printingAllowed value for this CampaignMember_Type.
     * 
     * @return printingAllowed   * Признак возможности печати информации о продукте
     */
    public java.lang.Boolean getPrintingAllowed() {
        return printingAllowed;
    }


    /**
     * Sets the printingAllowed value for this CampaignMember_Type.
     * 
     * @param printingAllowed   * Признак возможности печати информации о продукте
     */
    public void setPrintingAllowed(java.lang.Boolean printingAllowed) {
        this.printingAllowed = printingAllowed;
    }


    /**
     * Gets the backgroundId value for this CampaignMember_Type.
     * 
     * @return backgroundId   * Номер фона для печати предложения
     */
    public java.lang.String getBackgroundId() {
        return backgroundId;
    }


    /**
     * Sets the backgroundId value for this CampaignMember_Type.
     * 
     * @param backgroundId   * Номер фона для печати предложения
     */
    public void setBackgroundId(java.lang.String backgroundId) {
        this.backgroundId = backgroundId;
    }


    /**
     * Gets the searchSpec value for this CampaignMember_Type.
     * 
     * @return searchSpec   * Поисковое выражение
     */
    public java.lang.String getSearchSpec() {
        return searchSpec;
    }


    /**
     * Sets the searchSpec value for this CampaignMember_Type.
     * 
     * @param searchSpec   * Поисковое выражение
     */
    public void setSearchSpec(java.lang.String searchSpec) {
        this.searchSpec = searchSpec;
    }


    /**
     * Gets the internalProduct value for this CampaignMember_Type.
     * 
     * @return internalProduct
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.InternalProduct_Type[] getInternalProduct() {
        return internalProduct;
    }


    /**
     * Sets the internalProduct value for this CampaignMember_Type.
     * 
     * @param internalProduct
     */
    public void setInternalProduct(com.rssl.phizicgate.esberibgate.ws.generated.InternalProduct_Type[] internalProduct) {
        this.internalProduct = internalProduct;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.InternalProduct_Type getInternalProduct(int i) {
        return this.internalProduct[i];
    }

    public void setInternalProduct(int i, com.rssl.phizicgate.esberibgate.ws.generated.InternalProduct_Type _value) {
        this.internalProduct[i] = _value;
    }


    /**
     * Gets the bankInfo value for this CampaignMember_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this CampaignMember_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the treatmentType value for this CampaignMember_Type.
     * 
     * @return treatmentType   * Канал предложения для поиска. Константа "SBOL"
     */
    public java.lang.String getTreatmentType() {
        return treatmentType;
    }


    /**
     * Sets the treatmentType value for this CampaignMember_Type.
     * 
     * @param treatmentType   * Канал предложения для поиска. Константа "SBOL"
     */
    public void setTreatmentType(java.lang.String treatmentType) {
        this.treatmentType = treatmentType;
    }


    /**
     * Gets the topUp value for this CampaignMember_Type.
     * 
     * @return topUp   * Блок данных по top-up кредиту
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.TopUp_Type getTopUp() {
        return topUp;
    }


    /**
     * Sets the topUp value for this CampaignMember_Type.
     * 
     * @param topUp   * Блок данных по top-up кредиту
     */
    public void setTopUp(com.rssl.phizicgate.esberibgate.ws.generated.TopUp_Type topUp) {
        this.topUp = topUp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CampaignMember_Type)) return false;
        CampaignMember_Type other = (CampaignMember_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.personInfo==null && other.getPersonInfo()==null) || 
             (this.personInfo!=null &&
              this.personInfo.equals(other.getPersonInfo()))) &&
            ((this.campaignMemberId==null && other.getCampaignMemberId()==null) || 
             (this.campaignMemberId!=null &&
              this.campaignMemberId.equals(other.getCampaignMemberId()))) &&
            ((this.offerDate==null && other.getOfferDate()==null) || 
             (this.offerDate!=null &&
              this.offerDate.equals(other.getOfferDate()))) &&
            ((this.campaignName==null && other.getCampaignName()==null) || 
             (this.campaignName!=null &&
              this.campaignName.equals(other.getCampaignName()))) &&
            ((this.campaignId==null && other.getCampaignId()==null) || 
             (this.campaignId!=null &&
              this.campaignId.equals(other.getCampaignId()))) &&
            ((this.sourceCode==null && other.getSourceCode()==null) || 
             (this.sourceCode!=null &&
              this.sourceCode.equals(other.getSourceCode()))) &&
            ((this.prodType==null && other.getProdType()==null) || 
             (this.prodType!=null &&
              this.prodType.equals(other.getProdType()))) &&
            ((this.sourceName==null && other.getSourceName()==null) || 
             (this.sourceName!=null &&
              this.sourceName.equals(other.getSourceName()))) &&
            ((this.productASName==null && other.getProductASName()==null) || 
             (this.productASName!=null &&
              this.productASName.equals(other.getProductASName()))) &&
            ((this.productASPriority==null && other.getProductASPriority()==null) || 
             (this.productASPriority!=null &&
              this.productASPriority.equals(other.getProductASPriority()))) &&
            ((this.personalText==null && other.getPersonalText()==null) || 
             (this.personalText!=null &&
              this.personalText.equals(other.getPersonalText()))) &&
            ((this.mediaType==null && other.getMediaType()==null) || 
             (this.mediaType!=null &&
              this.mediaType.equals(other.getMediaType()))) &&
            ((this.clientId==null && other.getClientId()==null) || 
             (this.clientId!=null &&
              this.clientId.equals(other.getClientId()))) &&
            ((this.clientSegment==null && other.getClientSegment()==null) || 
             (this.clientSegment!=null &&
              this.clientSegment.equals(other.getClientSegment()))) &&
            ((this.cardInfo==null && other.getCardInfo()==null) || 
             (this.cardInfo!=null &&
              this.cardInfo.equals(other.getCardInfo()))) &&
            ((this.payrollAgree==null && other.getPayrollAgree()==null) || 
             (this.payrollAgree!=null &&
              java.util.Arrays.equals(this.payrollAgree, other.getPayrollAgree()))) &&
            ((this.printingAllowed==null && other.getPrintingAllowed()==null) || 
             (this.printingAllowed!=null &&
              this.printingAllowed.equals(other.getPrintingAllowed()))) &&
            ((this.backgroundId==null && other.getBackgroundId()==null) || 
             (this.backgroundId!=null &&
              this.backgroundId.equals(other.getBackgroundId()))) &&
            ((this.searchSpec==null && other.getSearchSpec()==null) || 
             (this.searchSpec!=null &&
              this.searchSpec.equals(other.getSearchSpec()))) &&
            ((this.internalProduct==null && other.getInternalProduct()==null) || 
             (this.internalProduct!=null &&
              java.util.Arrays.equals(this.internalProduct, other.getInternalProduct()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.treatmentType==null && other.getTreatmentType()==null) || 
             (this.treatmentType!=null &&
              this.treatmentType.equals(other.getTreatmentType()))) &&
            ((this.topUp==null && other.getTopUp()==null) || 
             (this.topUp!=null &&
              this.topUp.equals(other.getTopUp())));
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
        if (getPersonInfo() != null) {
            _hashCode += getPersonInfo().hashCode();
        }
        if (getCampaignMemberId() != null) {
            _hashCode += getCampaignMemberId().hashCode();
        }
        if (getOfferDate() != null) {
            _hashCode += getOfferDate().hashCode();
        }
        if (getCampaignName() != null) {
            _hashCode += getCampaignName().hashCode();
        }
        if (getCampaignId() != null) {
            _hashCode += getCampaignId().hashCode();
        }
        if (getSourceCode() != null) {
            _hashCode += getSourceCode().hashCode();
        }
        if (getProdType() != null) {
            _hashCode += getProdType().hashCode();
        }
        if (getSourceName() != null) {
            _hashCode += getSourceName().hashCode();
        }
        if (getProductASName() != null) {
            _hashCode += getProductASName().hashCode();
        }
        if (getProductASPriority() != null) {
            _hashCode += getProductASPriority().hashCode();
        }
        if (getPersonalText() != null) {
            _hashCode += getPersonalText().hashCode();
        }
        if (getMediaType() != null) {
            _hashCode += getMediaType().hashCode();
        }
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getClientSegment() != null) {
            _hashCode += getClientSegment().hashCode();
        }
        if (getCardInfo() != null) {
            _hashCode += getCardInfo().hashCode();
        }
        if (getPayrollAgree() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPayrollAgree());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPayrollAgree(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrintingAllowed() != null) {
            _hashCode += getPrintingAllowed().hashCode();
        }
        if (getBackgroundId() != null) {
            _hashCode += getBackgroundId().hashCode();
        }
        if (getSearchSpec() != null) {
            _hashCode += getSearchSpec().hashCode();
        }
        if (getInternalProduct() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInternalProduct());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInternalProduct(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getTreatmentType() != null) {
            _hashCode += getTreatmentType().hashCode();
        }
        if (getTopUp() != null) {
            _hashCode += getTopUp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CampaignMember_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignMember_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonInfoSec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignMemberId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignMemberId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offerDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OfferDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productASName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductASName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productASPriority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductASPriority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personalText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonalText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediaType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MediaType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientSegment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientSegment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payrollAgree");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayrollAgree"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayrollAgree"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printingAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrintingAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("backgroundId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BackgroundId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SearchSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internalProduct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InternalProduct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InternalProduct"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("treatmentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TreatmentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topUp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TopUp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TopUp_Type"));
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
