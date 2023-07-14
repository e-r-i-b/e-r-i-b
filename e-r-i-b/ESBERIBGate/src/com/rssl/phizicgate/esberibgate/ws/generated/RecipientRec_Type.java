/**
 * RecipientRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о поставщике
 */
public class RecipientRec_Type  implements java.io.Serializable {
    /* Уникальный идентификатор организации получателя платежей. */
    private java.lang.String codeRecipientBS;

    /* Наименование получателя, готовое для отображения пользователю */
    private java.lang.String name;

    /* Код группы услуг. Параметр должен быть определен в ЕРИБ по
     * ПУ и услуге */
    private java.lang.String groupService;

    /* Идентификатор услуги, к которой относится получатель */
    private java.lang.String codeService;

    /* Наименование услуги, к которой относится получатель, готовое
     * для отображения пользователю */
    private java.lang.String nameService;

    /* ИНН получателя */
    private java.lang.String taxId;

    /* Корр. счет получателя */
    private java.lang.String corrAccount;

    /* КПП получателя */
    private java.lang.String KPP;

    /* БИК банка получателя */
    private java.lang.String BIC;

    /* Расчетный счет получателя */
    private java.lang.String acctId;

    /* Наименование поставщика, выводимое в чеке */
    private java.lang.String nameOnBill;

    /* Признак «не отображать банковские реквизиты для поставщика»
     * (в том числе и на странице подтверждения платежа). Если указано «false»
     * или тэг отсутствует, то банковские реквизиты отображаются. Если указано
     * «true» - не отображаются. */
    private java.lang.Boolean notVisibleBankDetails;

    /* Номер телефона поставщика услуг для обращений клиентов банка,
     * которые совершили платежи в адрес поставщика для печати в чеке */
    private java.lang.String phoneToClient;

    /* Признак, определяющий возможность регистрации подписки на автоплатёж
     * в пользу данного ПУ */
    private java.lang.Boolean isAutoPayAccessible;

    /* Параметры подписки */
    private com.rssl.phizicgate.esberibgate.ws.generated.AutopayDetails_Type autoPayDetails;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] requisites;

    public RecipientRec_Type() {
    }

    public RecipientRec_Type(
           java.lang.String codeRecipientBS,
           java.lang.String name,
           java.lang.String groupService,
           java.lang.String codeService,
           java.lang.String nameService,
           java.lang.String taxId,
           java.lang.String corrAccount,
           java.lang.String KPP,
           java.lang.String BIC,
           java.lang.String acctId,
           java.lang.String nameOnBill,
           java.lang.Boolean notVisibleBankDetails,
           java.lang.String phoneToClient,
           java.lang.Boolean isAutoPayAccessible,
           com.rssl.phizicgate.esberibgate.ws.generated.AutopayDetails_Type autoPayDetails,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] requisites) {
           this.codeRecipientBS = codeRecipientBS;
           this.name = name;
           this.groupService = groupService;
           this.codeService = codeService;
           this.nameService = nameService;
           this.taxId = taxId;
           this.corrAccount = corrAccount;
           this.KPP = KPP;
           this.BIC = BIC;
           this.acctId = acctId;
           this.nameOnBill = nameOnBill;
           this.notVisibleBankDetails = notVisibleBankDetails;
           this.phoneToClient = phoneToClient;
           this.isAutoPayAccessible = isAutoPayAccessible;
           this.autoPayDetails = autoPayDetails;
           this.bankInfo = bankInfo;
           this.requisites = requisites;
    }


    /**
     * Gets the codeRecipientBS value for this RecipientRec_Type.
     * 
     * @return codeRecipientBS   * Уникальный идентификатор организации получателя платежей.
     */
    public java.lang.String getCodeRecipientBS() {
        return codeRecipientBS;
    }


    /**
     * Sets the codeRecipientBS value for this RecipientRec_Type.
     * 
     * @param codeRecipientBS   * Уникальный идентификатор организации получателя платежей.
     */
    public void setCodeRecipientBS(java.lang.String codeRecipientBS) {
        this.codeRecipientBS = codeRecipientBS;
    }


    /**
     * Gets the name value for this RecipientRec_Type.
     * 
     * @return name   * Наименование получателя, готовое для отображения пользователю
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this RecipientRec_Type.
     * 
     * @param name   * Наименование получателя, готовое для отображения пользователю
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the groupService value for this RecipientRec_Type.
     * 
     * @return groupService   * Код группы услуг. Параметр должен быть определен в ЕРИБ по
     * ПУ и услуге
     */
    public java.lang.String getGroupService() {
        return groupService;
    }


    /**
     * Sets the groupService value for this RecipientRec_Type.
     * 
     * @param groupService   * Код группы услуг. Параметр должен быть определен в ЕРИБ по
     * ПУ и услуге
     */
    public void setGroupService(java.lang.String groupService) {
        this.groupService = groupService;
    }


    /**
     * Gets the codeService value for this RecipientRec_Type.
     * 
     * @return codeService   * Идентификатор услуги, к которой относится получатель
     */
    public java.lang.String getCodeService() {
        return codeService;
    }


    /**
     * Sets the codeService value for this RecipientRec_Type.
     * 
     * @param codeService   * Идентификатор услуги, к которой относится получатель
     */
    public void setCodeService(java.lang.String codeService) {
        this.codeService = codeService;
    }


    /**
     * Gets the nameService value for this RecipientRec_Type.
     * 
     * @return nameService   * Наименование услуги, к которой относится получатель, готовое
     * для отображения пользователю
     */
    public java.lang.String getNameService() {
        return nameService;
    }


    /**
     * Sets the nameService value for this RecipientRec_Type.
     * 
     * @param nameService   * Наименование услуги, к которой относится получатель, готовое
     * для отображения пользователю
     */
    public void setNameService(java.lang.String nameService) {
        this.nameService = nameService;
    }


    /**
     * Gets the taxId value for this RecipientRec_Type.
     * 
     * @return taxId   * ИНН получателя
     */
    public java.lang.String getTaxId() {
        return taxId;
    }


    /**
     * Sets the taxId value for this RecipientRec_Type.
     * 
     * @param taxId   * ИНН получателя
     */
    public void setTaxId(java.lang.String taxId) {
        this.taxId = taxId;
    }


    /**
     * Gets the corrAccount value for this RecipientRec_Type.
     * 
     * @return corrAccount   * Корр. счет получателя
     */
    public java.lang.String getCorrAccount() {
        return corrAccount;
    }


    /**
     * Sets the corrAccount value for this RecipientRec_Type.
     * 
     * @param corrAccount   * Корр. счет получателя
     */
    public void setCorrAccount(java.lang.String corrAccount) {
        this.corrAccount = corrAccount;
    }


    /**
     * Gets the KPP value for this RecipientRec_Type.
     * 
     * @return KPP   * КПП получателя
     */
    public java.lang.String getKPP() {
        return KPP;
    }


    /**
     * Sets the KPP value for this RecipientRec_Type.
     * 
     * @param KPP   * КПП получателя
     */
    public void setKPP(java.lang.String KPP) {
        this.KPP = KPP;
    }


    /**
     * Gets the BIC value for this RecipientRec_Type.
     * 
     * @return BIC   * БИК банка получателя
     */
    public java.lang.String getBIC() {
        return BIC;
    }


    /**
     * Sets the BIC value for this RecipientRec_Type.
     * 
     * @param BIC   * БИК банка получателя
     */
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }


    /**
     * Gets the acctId value for this RecipientRec_Type.
     * 
     * @return acctId   * Расчетный счет получателя
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this RecipientRec_Type.
     * 
     * @param acctId   * Расчетный счет получателя
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the nameOnBill value for this RecipientRec_Type.
     * 
     * @return nameOnBill   * Наименование поставщика, выводимое в чеке
     */
    public java.lang.String getNameOnBill() {
        return nameOnBill;
    }


    /**
     * Sets the nameOnBill value for this RecipientRec_Type.
     * 
     * @param nameOnBill   * Наименование поставщика, выводимое в чеке
     */
    public void setNameOnBill(java.lang.String nameOnBill) {
        this.nameOnBill = nameOnBill;
    }


    /**
     * Gets the notVisibleBankDetails value for this RecipientRec_Type.
     * 
     * @return notVisibleBankDetails   * Признак «не отображать банковские реквизиты для поставщика»
     * (в том числе и на странице подтверждения платежа). Если указано «false»
     * или тэг отсутствует, то банковские реквизиты отображаются. Если указано
     * «true» - не отображаются.
     */
    public java.lang.Boolean getNotVisibleBankDetails() {
        return notVisibleBankDetails;
    }


    /**
     * Sets the notVisibleBankDetails value for this RecipientRec_Type.
     * 
     * @param notVisibleBankDetails   * Признак «не отображать банковские реквизиты для поставщика»
     * (в том числе и на странице подтверждения платежа). Если указано «false»
     * или тэг отсутствует, то банковские реквизиты отображаются. Если указано
     * «true» - не отображаются.
     */
    public void setNotVisibleBankDetails(java.lang.Boolean notVisibleBankDetails) {
        this.notVisibleBankDetails = notVisibleBankDetails;
    }


    /**
     * Gets the phoneToClient value for this RecipientRec_Type.
     * 
     * @return phoneToClient   * Номер телефона поставщика услуг для обращений клиентов банка,
     * которые совершили платежи в адрес поставщика для печати в чеке
     */
    public java.lang.String getPhoneToClient() {
        return phoneToClient;
    }


    /**
     * Sets the phoneToClient value for this RecipientRec_Type.
     * 
     * @param phoneToClient   * Номер телефона поставщика услуг для обращений клиентов банка,
     * которые совершили платежи в адрес поставщика для печати в чеке
     */
    public void setPhoneToClient(java.lang.String phoneToClient) {
        this.phoneToClient = phoneToClient;
    }


    /**
     * Gets the isAutoPayAccessible value for this RecipientRec_Type.
     * 
     * @return isAutoPayAccessible   * Признак, определяющий возможность регистрации подписки на автоплатёж
     * в пользу данного ПУ
     */
    public java.lang.Boolean getIsAutoPayAccessible() {
        return isAutoPayAccessible;
    }


    /**
     * Sets the isAutoPayAccessible value for this RecipientRec_Type.
     * 
     * @param isAutoPayAccessible   * Признак, определяющий возможность регистрации подписки на автоплатёж
     * в пользу данного ПУ
     */
    public void setIsAutoPayAccessible(java.lang.Boolean isAutoPayAccessible) {
        this.isAutoPayAccessible = isAutoPayAccessible;
    }


    /**
     * Gets the autoPayDetails value for this RecipientRec_Type.
     * 
     * @return autoPayDetails   * Параметры подписки
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AutopayDetails_Type getAutoPayDetails() {
        return autoPayDetails;
    }


    /**
     * Sets the autoPayDetails value for this RecipientRec_Type.
     * 
     * @param autoPayDetails   * Параметры подписки
     */
    public void setAutoPayDetails(com.rssl.phizicgate.esberibgate.ws.generated.AutopayDetails_Type autoPayDetails) {
        this.autoPayDetails = autoPayDetails;
    }


    /**
     * Gets the bankInfo value for this RecipientRec_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this RecipientRec_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the requisites value for this RecipientRec_Type.
     * 
     * @return requisites
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] getRequisites() {
        return requisites;
    }


    /**
     * Sets the requisites value for this RecipientRec_Type.
     * 
     * @param requisites
     */
    public void setRequisites(com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type[] requisites) {
        this.requisites = requisites;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RecipientRec_Type)) return false;
        RecipientRec_Type other = (RecipientRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codeRecipientBS==null && other.getCodeRecipientBS()==null) || 
             (this.codeRecipientBS!=null &&
              this.codeRecipientBS.equals(other.getCodeRecipientBS()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.groupService==null && other.getGroupService()==null) || 
             (this.groupService!=null &&
              this.groupService.equals(other.getGroupService()))) &&
            ((this.codeService==null && other.getCodeService()==null) || 
             (this.codeService!=null &&
              this.codeService.equals(other.getCodeService()))) &&
            ((this.nameService==null && other.getNameService()==null) || 
             (this.nameService!=null &&
              this.nameService.equals(other.getNameService()))) &&
            ((this.taxId==null && other.getTaxId()==null) || 
             (this.taxId!=null &&
              this.taxId.equals(other.getTaxId()))) &&
            ((this.corrAccount==null && other.getCorrAccount()==null) || 
             (this.corrAccount!=null &&
              this.corrAccount.equals(other.getCorrAccount()))) &&
            ((this.KPP==null && other.getKPP()==null) || 
             (this.KPP!=null &&
              this.KPP.equals(other.getKPP()))) &&
            ((this.BIC==null && other.getBIC()==null) || 
             (this.BIC!=null &&
              this.BIC.equals(other.getBIC()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.nameOnBill==null && other.getNameOnBill()==null) || 
             (this.nameOnBill!=null &&
              this.nameOnBill.equals(other.getNameOnBill()))) &&
            ((this.notVisibleBankDetails==null && other.getNotVisibleBankDetails()==null) || 
             (this.notVisibleBankDetails!=null &&
              this.notVisibleBankDetails.equals(other.getNotVisibleBankDetails()))) &&
            ((this.phoneToClient==null && other.getPhoneToClient()==null) || 
             (this.phoneToClient!=null &&
              this.phoneToClient.equals(other.getPhoneToClient()))) &&
            ((this.isAutoPayAccessible==null && other.getIsAutoPayAccessible()==null) || 
             (this.isAutoPayAccessible!=null &&
              this.isAutoPayAccessible.equals(other.getIsAutoPayAccessible()))) &&
            ((this.autoPayDetails==null && other.getAutoPayDetails()==null) || 
             (this.autoPayDetails!=null &&
              this.autoPayDetails.equals(other.getAutoPayDetails()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.requisites==null && other.getRequisites()==null) || 
             (this.requisites!=null &&
              java.util.Arrays.equals(this.requisites, other.getRequisites())));
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
        if (getCodeRecipientBS() != null) {
            _hashCode += getCodeRecipientBS().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getGroupService() != null) {
            _hashCode += getGroupService().hashCode();
        }
        if (getCodeService() != null) {
            _hashCode += getCodeService().hashCode();
        }
        if (getNameService() != null) {
            _hashCode += getNameService().hashCode();
        }
        if (getTaxId() != null) {
            _hashCode += getTaxId().hashCode();
        }
        if (getCorrAccount() != null) {
            _hashCode += getCorrAccount().hashCode();
        }
        if (getKPP() != null) {
            _hashCode += getKPP().hashCode();
        }
        if (getBIC() != null) {
            _hashCode += getBIC().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getNameOnBill() != null) {
            _hashCode += getNameOnBill().hashCode();
        }
        if (getNotVisibleBankDetails() != null) {
            _hashCode += getNotVisibleBankDetails().hashCode();
        }
        if (getPhoneToClient() != null) {
            _hashCode += getPhoneToClient().hashCode();
        }
        if (getIsAutoPayAccessible() != null) {
            _hashCode += getIsAutoPayAccessible().hashCode();
        }
        if (getAutoPayDetails() != null) {
            _hashCode += getAutoPayDetails().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getRequisites() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequisites());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequisites(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RecipientRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeRecipientBS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CodeRecipientBS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GroupService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CodeService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NameService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KPP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "KPP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameOnBill");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NameOnBill"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notVisibleBankDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NotVisibleBankDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneToClient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneToClient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isAutoPayAccessible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsAutoPayAccessible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPayDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPayDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayDetails_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requisites");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisites"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite"));
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
