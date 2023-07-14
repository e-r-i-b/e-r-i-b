/**
 * CardAcctInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация  по  карточному  контракту
 */
public class CardAcctInfoType  implements java.io.Serializable {
    /* Тип карточного продукта 01 – VISA Classic,
     * 06 – MC Gold,
     * 14 – ПРО100 «Социальная» */
    private java.lang.String cardTypeProd;

    /* Код бонусной программы «AE» - «Аэрофлот»; «GM» - «Золотая маска»;
     * «PG» - «Подари жизнь», «МТ» - «МТС-бонус» */
    private java.lang.String bonusCode;

    /* Номер зарплатного договора для дебетовых зарплатных карт */
    private java.lang.String salaryAgreementId;

    /* Номер основной карты контракта организации для бизнес-карт.
     * Номер основной карты при выпуске дополнительной карты.Номер выданной
     * экспресс-карты. */
    private java.lang.String mainCardNum;

    /* Печать ПИН-конверта. Признак печати ПИН-конверта для выпуска
     * карт по двум разным технологиям: с ПИН-конвертом / без ПИН-конверта. */
    private java.lang.Long pinPack;

    /* Информация об автоплатеже */
    private java.lang.String autoPayInfo;

    /* Информация о подключении услуги «Мобильный банк» */
    private com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type MBCInfo;

    /* Тариф за обслуживание */
    private com.rssl.phizicgate.esberibgate.ws.generated.TarifUnionType[] tariff;

    /* 10-ный код (2 цифры – код рег.банка+4 цифры – код ОСБ+4 цифры
     * – код ВСП). Используется только для СПООБК для передачи по кредитным
     * картам */
    private java.lang.Long DLCode;

    /* Возможные значения - Y или N. Используется только для СПООБК
     * для передачи по кредитным картам */
    private java.lang.String TAGCode;

    /* Срок действия карты в формате процессинга YYMM (передается
     * для ЕРИБ) */
    private java.lang.String endDtForWay;

    /* Номер заявления-анкеты на получение карты */
    private java.lang.String cardOrderNum;

    /* Дата заявления-анкеты на получение карты */
    private java.lang.String cardOrderDate;

    /* Субтип контракта для карт с индивидуальным дизайном */
    private java.lang.String cardSubType;

    /* Ссылочный номер карты индивидуального дизайна */
    private java.lang.String personalDesignRefNum;

    /* Риск-фактор контракта. Рисковый параметр, который используется
     * чиповой схемой. */
    private java.lang.String riskFactor;

    /* Участие  в  программе */
    private com.rssl.phizicgate.esberibgate.ws.generated.BonusInfo_Type bonusInfo;

    public CardAcctInfoType() {
    }

    public CardAcctInfoType(
           java.lang.String cardTypeProd,
           java.lang.String bonusCode,
           java.lang.String salaryAgreementId,
           java.lang.String mainCardNum,
           java.lang.Long pinPack,
           java.lang.String autoPayInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type MBCInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.TarifUnionType[] tariff,
           java.lang.Long DLCode,
           java.lang.String TAGCode,
           java.lang.String endDtForWay,
           java.lang.String cardOrderNum,
           java.lang.String cardOrderDate,
           java.lang.String cardSubType,
           java.lang.String personalDesignRefNum,
           java.lang.String riskFactor,
           com.rssl.phizicgate.esberibgate.ws.generated.BonusInfo_Type bonusInfo) {
           this.cardTypeProd = cardTypeProd;
           this.bonusCode = bonusCode;
           this.salaryAgreementId = salaryAgreementId;
           this.mainCardNum = mainCardNum;
           this.pinPack = pinPack;
           this.autoPayInfo = autoPayInfo;
           this.MBCInfo = MBCInfo;
           this.tariff = tariff;
           this.DLCode = DLCode;
           this.TAGCode = TAGCode;
           this.endDtForWay = endDtForWay;
           this.cardOrderNum = cardOrderNum;
           this.cardOrderDate = cardOrderDate;
           this.cardSubType = cardSubType;
           this.personalDesignRefNum = personalDesignRefNum;
           this.riskFactor = riskFactor;
           this.bonusInfo = bonusInfo;
    }


    /**
     * Gets the cardTypeProd value for this CardAcctInfoType.
     * 
     * @return cardTypeProd   * Тип карточного продукта 01 – VISA Classic,
     * 06 – MC Gold,
     * 14 – ПРО100 «Социальная»
     */
    public java.lang.String getCardTypeProd() {
        return cardTypeProd;
    }


    /**
     * Sets the cardTypeProd value for this CardAcctInfoType.
     * 
     * @param cardTypeProd   * Тип карточного продукта 01 – VISA Classic,
     * 06 – MC Gold,
     * 14 – ПРО100 «Социальная»
     */
    public void setCardTypeProd(java.lang.String cardTypeProd) {
        this.cardTypeProd = cardTypeProd;
    }


    /**
     * Gets the bonusCode value for this CardAcctInfoType.
     * 
     * @return bonusCode   * Код бонусной программы «AE» - «Аэрофлот»; «GM» - «Золотая маска»;
     * «PG» - «Подари жизнь», «МТ» - «МТС-бонус»
     */
    public java.lang.String getBonusCode() {
        return bonusCode;
    }


    /**
     * Sets the bonusCode value for this CardAcctInfoType.
     * 
     * @param bonusCode   * Код бонусной программы «AE» - «Аэрофлот»; «GM» - «Золотая маска»;
     * «PG» - «Подари жизнь», «МТ» - «МТС-бонус»
     */
    public void setBonusCode(java.lang.String bonusCode) {
        this.bonusCode = bonusCode;
    }


    /**
     * Gets the salaryAgreementId value for this CardAcctInfoType.
     * 
     * @return salaryAgreementId   * Номер зарплатного договора для дебетовых зарплатных карт
     */
    public java.lang.String getSalaryAgreementId() {
        return salaryAgreementId;
    }


    /**
     * Sets the salaryAgreementId value for this CardAcctInfoType.
     * 
     * @param salaryAgreementId   * Номер зарплатного договора для дебетовых зарплатных карт
     */
    public void setSalaryAgreementId(java.lang.String salaryAgreementId) {
        this.salaryAgreementId = salaryAgreementId;
    }


    /**
     * Gets the mainCardNum value for this CardAcctInfoType.
     * 
     * @return mainCardNum   * Номер основной карты контракта организации для бизнес-карт.
     * Номер основной карты при выпуске дополнительной карты.Номер выданной
     * экспресс-карты.
     */
    public java.lang.String getMainCardNum() {
        return mainCardNum;
    }


    /**
     * Sets the mainCardNum value for this CardAcctInfoType.
     * 
     * @param mainCardNum   * Номер основной карты контракта организации для бизнес-карт.
     * Номер основной карты при выпуске дополнительной карты.Номер выданной
     * экспресс-карты.
     */
    public void setMainCardNum(java.lang.String mainCardNum) {
        this.mainCardNum = mainCardNum;
    }


    /**
     * Gets the pinPack value for this CardAcctInfoType.
     * 
     * @return pinPack   * Печать ПИН-конверта. Признак печати ПИН-конверта для выпуска
     * карт по двум разным технологиям: с ПИН-конвертом / без ПИН-конверта.
     */
    public java.lang.Long getPinPack() {
        return pinPack;
    }


    /**
     * Sets the pinPack value for this CardAcctInfoType.
     * 
     * @param pinPack   * Печать ПИН-конверта. Признак печати ПИН-конверта для выпуска
     * карт по двум разным технологиям: с ПИН-конвертом / без ПИН-конверта.
     */
    public void setPinPack(java.lang.Long pinPack) {
        this.pinPack = pinPack;
    }


    /**
     * Gets the autoPayInfo value for this CardAcctInfoType.
     * 
     * @return autoPayInfo   * Информация об автоплатеже
     */
    public java.lang.String getAutoPayInfo() {
        return autoPayInfo;
    }


    /**
     * Sets the autoPayInfo value for this CardAcctInfoType.
     * 
     * @param autoPayInfo   * Информация об автоплатеже
     */
    public void setAutoPayInfo(java.lang.String autoPayInfo) {
        this.autoPayInfo = autoPayInfo;
    }


    /**
     * Gets the MBCInfo value for this CardAcctInfoType.
     * 
     * @return MBCInfo   * Информация о подключении услуги «Мобильный банк»
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type getMBCInfo() {
        return MBCInfo;
    }


    /**
     * Sets the MBCInfo value for this CardAcctInfoType.
     * 
     * @param MBCInfo   * Информация о подключении услуги «Мобильный банк»
     */
    public void setMBCInfo(com.rssl.phizicgate.esberibgate.ws.generated.MBCInfo_Type MBCInfo) {
        this.MBCInfo = MBCInfo;
    }


    /**
     * Gets the tariff value for this CardAcctInfoType.
     * 
     * @return tariff   * Тариф за обслуживание
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.TarifUnionType[] getTariff() {
        return tariff;
    }


    /**
     * Sets the tariff value for this CardAcctInfoType.
     * 
     * @param tariff   * Тариф за обслуживание
     */
    public void setTariff(com.rssl.phizicgate.esberibgate.ws.generated.TarifUnionType[] tariff) {
        this.tariff = tariff;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.TarifUnionType getTariff(int i) {
        return this.tariff[i];
    }

    public void setTariff(int i, com.rssl.phizicgate.esberibgate.ws.generated.TarifUnionType _value) {
        this.tariff[i] = _value;
    }


    /**
     * Gets the DLCode value for this CardAcctInfoType.
     * 
     * @return DLCode   * 10-ный код (2 цифры – код рег.банка+4 цифры – код ОСБ+4 цифры
     * – код ВСП). Используется только для СПООБК для передачи по кредитным
     * картам
     */
    public java.lang.Long getDLCode() {
        return DLCode;
    }


    /**
     * Sets the DLCode value for this CardAcctInfoType.
     * 
     * @param DLCode   * 10-ный код (2 цифры – код рег.банка+4 цифры – код ОСБ+4 цифры
     * – код ВСП). Используется только для СПООБК для передачи по кредитным
     * картам
     */
    public void setDLCode(java.lang.Long DLCode) {
        this.DLCode = DLCode;
    }


    /**
     * Gets the TAGCode value for this CardAcctInfoType.
     * 
     * @return TAGCode   * Возможные значения - Y или N. Используется только для СПООБК
     * для передачи по кредитным картам
     */
    public java.lang.String getTAGCode() {
        return TAGCode;
    }


    /**
     * Sets the TAGCode value for this CardAcctInfoType.
     * 
     * @param TAGCode   * Возможные значения - Y или N. Используется только для СПООБК
     * для передачи по кредитным картам
     */
    public void setTAGCode(java.lang.String TAGCode) {
        this.TAGCode = TAGCode;
    }


    /**
     * Gets the endDtForWay value for this CardAcctInfoType.
     * 
     * @return endDtForWay   * Срок действия карты в формате процессинга YYMM (передается
     * для ЕРИБ)
     */
    public java.lang.String getEndDtForWay() {
        return endDtForWay;
    }


    /**
     * Sets the endDtForWay value for this CardAcctInfoType.
     * 
     * @param endDtForWay   * Срок действия карты в формате процессинга YYMM (передается
     * для ЕРИБ)
     */
    public void setEndDtForWay(java.lang.String endDtForWay) {
        this.endDtForWay = endDtForWay;
    }


    /**
     * Gets the cardOrderNum value for this CardAcctInfoType.
     * 
     * @return cardOrderNum   * Номер заявления-анкеты на получение карты
     */
    public java.lang.String getCardOrderNum() {
        return cardOrderNum;
    }


    /**
     * Sets the cardOrderNum value for this CardAcctInfoType.
     * 
     * @param cardOrderNum   * Номер заявления-анкеты на получение карты
     */
    public void setCardOrderNum(java.lang.String cardOrderNum) {
        this.cardOrderNum = cardOrderNum;
    }


    /**
     * Gets the cardOrderDate value for this CardAcctInfoType.
     * 
     * @return cardOrderDate   * Дата заявления-анкеты на получение карты
     */
    public java.lang.String getCardOrderDate() {
        return cardOrderDate;
    }


    /**
     * Sets the cardOrderDate value for this CardAcctInfoType.
     * 
     * @param cardOrderDate   * Дата заявления-анкеты на получение карты
     */
    public void setCardOrderDate(java.lang.String cardOrderDate) {
        this.cardOrderDate = cardOrderDate;
    }


    /**
     * Gets the cardSubType value for this CardAcctInfoType.
     * 
     * @return cardSubType   * Субтип контракта для карт с индивидуальным дизайном
     */
    public java.lang.String getCardSubType() {
        return cardSubType;
    }


    /**
     * Sets the cardSubType value for this CardAcctInfoType.
     * 
     * @param cardSubType   * Субтип контракта для карт с индивидуальным дизайном
     */
    public void setCardSubType(java.lang.String cardSubType) {
        this.cardSubType = cardSubType;
    }


    /**
     * Gets the personalDesignRefNum value for this CardAcctInfoType.
     * 
     * @return personalDesignRefNum   * Ссылочный номер карты индивидуального дизайна
     */
    public java.lang.String getPersonalDesignRefNum() {
        return personalDesignRefNum;
    }


    /**
     * Sets the personalDesignRefNum value for this CardAcctInfoType.
     * 
     * @param personalDesignRefNum   * Ссылочный номер карты индивидуального дизайна
     */
    public void setPersonalDesignRefNum(java.lang.String personalDesignRefNum) {
        this.personalDesignRefNum = personalDesignRefNum;
    }


    /**
     * Gets the riskFactor value for this CardAcctInfoType.
     * 
     * @return riskFactor   * Риск-фактор контракта. Рисковый параметр, который используется
     * чиповой схемой.
     */
    public java.lang.String getRiskFactor() {
        return riskFactor;
    }


    /**
     * Sets the riskFactor value for this CardAcctInfoType.
     * 
     * @param riskFactor   * Риск-фактор контракта. Рисковый параметр, который используется
     * чиповой схемой.
     */
    public void setRiskFactor(java.lang.String riskFactor) {
        this.riskFactor = riskFactor;
    }


    /**
     * Gets the bonusInfo value for this CardAcctInfoType.
     * 
     * @return bonusInfo   * Участие  в  программе
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BonusInfo_Type getBonusInfo() {
        return bonusInfo;
    }


    /**
     * Sets the bonusInfo value for this CardAcctInfoType.
     * 
     * @param bonusInfo   * Участие  в  программе
     */
    public void setBonusInfo(com.rssl.phizicgate.esberibgate.ws.generated.BonusInfo_Type bonusInfo) {
        this.bonusInfo = bonusInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardAcctInfoType)) return false;
        CardAcctInfoType other = (CardAcctInfoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cardTypeProd==null && other.getCardTypeProd()==null) || 
             (this.cardTypeProd!=null &&
              this.cardTypeProd.equals(other.getCardTypeProd()))) &&
            ((this.bonusCode==null && other.getBonusCode()==null) || 
             (this.bonusCode!=null &&
              this.bonusCode.equals(other.getBonusCode()))) &&
            ((this.salaryAgreementId==null && other.getSalaryAgreementId()==null) || 
             (this.salaryAgreementId!=null &&
              this.salaryAgreementId.equals(other.getSalaryAgreementId()))) &&
            ((this.mainCardNum==null && other.getMainCardNum()==null) || 
             (this.mainCardNum!=null &&
              this.mainCardNum.equals(other.getMainCardNum()))) &&
            ((this.pinPack==null && other.getPinPack()==null) || 
             (this.pinPack!=null &&
              this.pinPack.equals(other.getPinPack()))) &&
            ((this.autoPayInfo==null && other.getAutoPayInfo()==null) || 
             (this.autoPayInfo!=null &&
              this.autoPayInfo.equals(other.getAutoPayInfo()))) &&
            ((this.MBCInfo==null && other.getMBCInfo()==null) || 
             (this.MBCInfo!=null &&
              this.MBCInfo.equals(other.getMBCInfo()))) &&
            ((this.tariff==null && other.getTariff()==null) || 
             (this.tariff!=null &&
              java.util.Arrays.equals(this.tariff, other.getTariff()))) &&
            ((this.DLCode==null && other.getDLCode()==null) || 
             (this.DLCode!=null &&
              this.DLCode.equals(other.getDLCode()))) &&
            ((this.TAGCode==null && other.getTAGCode()==null) || 
             (this.TAGCode!=null &&
              this.TAGCode.equals(other.getTAGCode()))) &&
            ((this.endDtForWay==null && other.getEndDtForWay()==null) || 
             (this.endDtForWay!=null &&
              this.endDtForWay.equals(other.getEndDtForWay()))) &&
            ((this.cardOrderNum==null && other.getCardOrderNum()==null) || 
             (this.cardOrderNum!=null &&
              this.cardOrderNum.equals(other.getCardOrderNum()))) &&
            ((this.cardOrderDate==null && other.getCardOrderDate()==null) || 
             (this.cardOrderDate!=null &&
              this.cardOrderDate.equals(other.getCardOrderDate()))) &&
            ((this.cardSubType==null && other.getCardSubType()==null) || 
             (this.cardSubType!=null &&
              this.cardSubType.equals(other.getCardSubType()))) &&
            ((this.personalDesignRefNum==null && other.getPersonalDesignRefNum()==null) || 
             (this.personalDesignRefNum!=null &&
              this.personalDesignRefNum.equals(other.getPersonalDesignRefNum()))) &&
            ((this.riskFactor==null && other.getRiskFactor()==null) || 
             (this.riskFactor!=null &&
              this.riskFactor.equals(other.getRiskFactor()))) &&
            ((this.bonusInfo==null && other.getBonusInfo()==null) || 
             (this.bonusInfo!=null &&
              this.bonusInfo.equals(other.getBonusInfo())));
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
        if (getCardTypeProd() != null) {
            _hashCode += getCardTypeProd().hashCode();
        }
        if (getBonusCode() != null) {
            _hashCode += getBonusCode().hashCode();
        }
        if (getSalaryAgreementId() != null) {
            _hashCode += getSalaryAgreementId().hashCode();
        }
        if (getMainCardNum() != null) {
            _hashCode += getMainCardNum().hashCode();
        }
        if (getPinPack() != null) {
            _hashCode += getPinPack().hashCode();
        }
        if (getAutoPayInfo() != null) {
            _hashCode += getAutoPayInfo().hashCode();
        }
        if (getMBCInfo() != null) {
            _hashCode += getMBCInfo().hashCode();
        }
        if (getTariff() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTariff());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTariff(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDLCode() != null) {
            _hashCode += getDLCode().hashCode();
        }
        if (getTAGCode() != null) {
            _hashCode += getTAGCode().hashCode();
        }
        if (getEndDtForWay() != null) {
            _hashCode += getEndDtForWay().hashCode();
        }
        if (getCardOrderNum() != null) {
            _hashCode += getCardOrderNum().hashCode();
        }
        if (getCardOrderDate() != null) {
            _hashCode += getCardOrderDate().hashCode();
        }
        if (getCardSubType() != null) {
            _hashCode += getCardSubType().hashCode();
        }
        if (getPersonalDesignRefNum() != null) {
            _hashCode += getPersonalDesignRefNum().hashCode();
        }
        if (getRiskFactor() != null) {
            _hashCode += getRiskFactor().hashCode();
        }
        if (getBonusInfo() != null) {
            _hashCode += getBonusInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardAcctInfoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardTypeProd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardTypeProd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salaryAgreementId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SalaryAgreementId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Identifier"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainCardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MainCardNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pinPack");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PinPack"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPayInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPayInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MBCInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MBCInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MBCInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariff");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Tariff"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifUnionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DLCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DLCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TAGCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TAGCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDtForWay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDtForWay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardOrderNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardOrderNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardOrderDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardOrderDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardSubType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardSubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personalDesignRefNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonalDesignRefNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Identifier"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskFactor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RiskFactor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusInfo_Type"));
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
