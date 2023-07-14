/**
 * OfficeLoanClaimType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.etsm.offer.service.generated;

public class OfficeLoanClaimType  implements java.io.Serializable {
    /* Идентификатор заявки в ETSM */
    private java.lang.String applicationNumber;

    /* Код статуса заявки */
    private java.math.BigInteger state;

    /* Причина необходимости посещения ВСП */
    private java.lang.String needVisitOfficeReason;

    /* ФИО КИ от имени которого создана заявка в TSM */
    private java.lang.String fioKI;

    /* Логин КИ, от имени которого создана заявка в TSM */
    private java.lang.String loginKI;

    /* ФИО создателя заявки (сотрудника ТМ) */
    private java.lang.String fioTM;

    /* Логин создателя заявки (сотрудника ТМ) */
    private java.lang.String loginTM;

    /* Подразделение для оформления заявки в формате: тер. банк (2
     * цифры), ОСБ (4 цифры), ВСП (5 цифр) */
    private java.lang.String department;

    /* Канал продаж */
    private java.lang.String channel;

    /* Дата подписания заявки клинтом */
    private java.util.Date agreementDate;

    /* Тип продукта. 1 - Потребительский кредит 3 - Банковские карты */
    private java.lang.String type;

    /* В соответствии со справочником продуктов: 5 - Доверительный
     * кредит, 10 - Потребительский кредит без обеспечения Новый продукт:
     * Дебетовые карты с разрешенным овердрафтом для VIP-клиентов */
    private java.lang.String productCode;

    /* Код субпродукта */
    private java.lang.String subProductCode;

    /* Сумма кредита */
    private java.math.BigDecimal productAmount;

    /* Срок кредита в месяцах */
    private java.math.BigInteger productPeriod;

    /* Сумма одобренного кредита */
    private java.math.BigDecimal loanAmount;

    /* Срок одобренного кредита в месяцах */
    private java.math.BigInteger loanPeriod;

    /* Ставка одобренного кредита */
    private java.math.BigDecimal loanRate;

    /* Валюта кредита */
    private java.lang.String currency;

    /* Тип погашения кредита */
    private java.lang.String paymentType;

    /* Имя */
    private java.lang.String firstName;

    /* Фамилия */
    private java.lang.String surName;

    /* Дата рождения */
    private java.util.Date birthDay;

    /* Очество */
    private java.lang.String patrName;

    /* Гражданство */
    private java.lang.String citizen;

    /* Серия документа */
    private java.lang.String documentSeries;

    /* Номер документа */
    private java.lang.String documentNumber;

    /* Дата выдачи документа */
    private java.util.Date passportIssueDate;

    /* Код подразделения */
    private java.lang.String passportIssueByCode;

    /* Орган, выдавший документ */
    private java.lang.String passportIssueBy;

    /* Наличие в действующем паспорте отметки о ранее выданном (Паспорт
     * менялся) */
    private boolean hasOldPassport;

    /* Серия предыдущего паспорта */
    private java.lang.String oldDocumentSeries;

    /* Номер предыдущего паспорта */
    private java.lang.String oldDocumentNumber;

    /* Дата выдачи предыдущего паспорта */
    private java.util.Date oldPassportIssueDate;

    /* Орган, выдавший предыдущий паспорт */
    private java.lang.String oldPassportIssueBy;

    /* Дата запроса */
    private java.util.Calendar createDate;

    /* Тип выдачи кредита */
    private java.lang.String typeOfIssue;

    /* Номер счета вклада в Сбербанке */
    private java.lang.String acctId;

    /* Номер банковской карты (кроме Сберкарт) */
    private java.lang.String cardNum;

    /* Признак предодобренного предложения. 1 - предодобренное 0 -
     * нет */
    private boolean preapproved;

    public OfficeLoanClaimType() {
    }

    public OfficeLoanClaimType(
           java.lang.String applicationNumber,
           java.math.BigInteger state,
           java.lang.String needVisitOfficeReason,
           java.lang.String fioKI,
           java.lang.String loginKI,
           java.lang.String fioTM,
           java.lang.String loginTM,
           java.lang.String department,
           java.lang.String channel,
           java.util.Date agreementDate,
           java.lang.String type,
           java.lang.String productCode,
           java.lang.String subProductCode,
           java.math.BigDecimal productAmount,
           java.math.BigInteger productPeriod,
           java.math.BigDecimal loanAmount,
           java.math.BigInteger loanPeriod,
           java.math.BigDecimal loanRate,
           java.lang.String currency,
           java.lang.String paymentType,
           java.lang.String firstName,
           java.lang.String surName,
           java.util.Date birthDay,
           java.lang.String patrName,
           java.lang.String citizen,
           java.lang.String documentSeries,
           java.lang.String documentNumber,
           java.util.Date passportIssueDate,
           java.lang.String passportIssueByCode,
           java.lang.String passportIssueBy,
           boolean hasOldPassport,
           java.lang.String oldDocumentSeries,
           java.lang.String oldDocumentNumber,
           java.util.Date oldPassportIssueDate,
           java.lang.String oldPassportIssueBy,
           java.util.Calendar createDate,
           java.lang.String typeOfIssue,
           java.lang.String acctId,
           java.lang.String cardNum,
           boolean preapproved) {
           this.applicationNumber = applicationNumber;
           this.state = state;
           this.needVisitOfficeReason = needVisitOfficeReason;
           this.fioKI = fioKI;
           this.loginKI = loginKI;
           this.fioTM = fioTM;
           this.loginTM = loginTM;
           this.department = department;
           this.channel = channel;
           this.agreementDate = agreementDate;
           this.type = type;
           this.productCode = productCode;
           this.subProductCode = subProductCode;
           this.productAmount = productAmount;
           this.productPeriod = productPeriod;
           this.loanAmount = loanAmount;
           this.loanPeriod = loanPeriod;
           this.loanRate = loanRate;
           this.currency = currency;
           this.paymentType = paymentType;
           this.firstName = firstName;
           this.surName = surName;
           this.birthDay = birthDay;
           this.patrName = patrName;
           this.citizen = citizen;
           this.documentSeries = documentSeries;
           this.documentNumber = documentNumber;
           this.passportIssueDate = passportIssueDate;
           this.passportIssueByCode = passportIssueByCode;
           this.passportIssueBy = passportIssueBy;
           this.hasOldPassport = hasOldPassport;
           this.oldDocumentSeries = oldDocumentSeries;
           this.oldDocumentNumber = oldDocumentNumber;
           this.oldPassportIssueDate = oldPassportIssueDate;
           this.oldPassportIssueBy = oldPassportIssueBy;
           this.createDate = createDate;
           this.typeOfIssue = typeOfIssue;
           this.acctId = acctId;
           this.cardNum = cardNum;
           this.preapproved = preapproved;
    }


    /**
     * Gets the applicationNumber value for this OfficeLoanClaimType.
     * 
     * @return applicationNumber   * Идентификатор заявки в ETSM
     */
    public java.lang.String getApplicationNumber() {
        return applicationNumber;
    }


    /**
     * Sets the applicationNumber value for this OfficeLoanClaimType.
     * 
     * @param applicationNumber   * Идентификатор заявки в ETSM
     */
    public void setApplicationNumber(java.lang.String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }


    /**
     * Gets the state value for this OfficeLoanClaimType.
     * 
     * @return state   * Код статуса заявки
     */
    public java.math.BigInteger getState() {
        return state;
    }


    /**
     * Sets the state value for this OfficeLoanClaimType.
     * 
     * @param state   * Код статуса заявки
     */
    public void setState(java.math.BigInteger state) {
        this.state = state;
    }


    /**
     * Gets the needVisitOfficeReason value for this OfficeLoanClaimType.
     * 
     * @return needVisitOfficeReason   * Причина необходимости посещения ВСП
     */
    public java.lang.String getNeedVisitOfficeReason() {
        return needVisitOfficeReason;
    }


    /**
     * Sets the needVisitOfficeReason value for this OfficeLoanClaimType.
     * 
     * @param needVisitOfficeReason   * Причина необходимости посещения ВСП
     */
    public void setNeedVisitOfficeReason(java.lang.String needVisitOfficeReason) {
        this.needVisitOfficeReason = needVisitOfficeReason;
    }


    /**
     * Gets the fioKI value for this OfficeLoanClaimType.
     * 
     * @return fioKI   * ФИО КИ от имени которого создана заявка в TSM
     */
    public java.lang.String getFioKI() {
        return fioKI;
    }


    /**
     * Sets the fioKI value for this OfficeLoanClaimType.
     * 
     * @param fioKI   * ФИО КИ от имени которого создана заявка в TSM
     */
    public void setFioKI(java.lang.String fioKI) {
        this.fioKI = fioKI;
    }


    /**
     * Gets the loginKI value for this OfficeLoanClaimType.
     * 
     * @return loginKI   * Логин КИ, от имени которого создана заявка в TSM
     */
    public java.lang.String getLoginKI() {
        return loginKI;
    }


    /**
     * Sets the loginKI value for this OfficeLoanClaimType.
     * 
     * @param loginKI   * Логин КИ, от имени которого создана заявка в TSM
     */
    public void setLoginKI(java.lang.String loginKI) {
        this.loginKI = loginKI;
    }


    /**
     * Gets the fioTM value for this OfficeLoanClaimType.
     * 
     * @return fioTM   * ФИО создателя заявки (сотрудника ТМ)
     */
    public java.lang.String getFioTM() {
        return fioTM;
    }


    /**
     * Sets the fioTM value for this OfficeLoanClaimType.
     * 
     * @param fioTM   * ФИО создателя заявки (сотрудника ТМ)
     */
    public void setFioTM(java.lang.String fioTM) {
        this.fioTM = fioTM;
    }


    /**
     * Gets the loginTM value for this OfficeLoanClaimType.
     * 
     * @return loginTM   * Логин создателя заявки (сотрудника ТМ)
     */
    public java.lang.String getLoginTM() {
        return loginTM;
    }


    /**
     * Sets the loginTM value for this OfficeLoanClaimType.
     * 
     * @param loginTM   * Логин создателя заявки (сотрудника ТМ)
     */
    public void setLoginTM(java.lang.String loginTM) {
        this.loginTM = loginTM;
    }


    /**
     * Gets the department value for this OfficeLoanClaimType.
     * 
     * @return department   * Подразделение для оформления заявки в формате: тер. банк (2
     * цифры), ОСБ (4 цифры), ВСП (5 цифр)
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this OfficeLoanClaimType.
     * 
     * @param department   * Подразделение для оформления заявки в формате: тер. банк (2
     * цифры), ОСБ (4 цифры), ВСП (5 цифр)
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the channel value for this OfficeLoanClaimType.
     * 
     * @return channel   * Канал продаж
     */
    public java.lang.String getChannel() {
        return channel;
    }


    /**
     * Sets the channel value for this OfficeLoanClaimType.
     * 
     * @param channel   * Канал продаж
     */
    public void setChannel(java.lang.String channel) {
        this.channel = channel;
    }


    /**
     * Gets the agreementDate value for this OfficeLoanClaimType.
     * 
     * @return agreementDate   * Дата подписания заявки клинтом
     */
    public java.util.Date getAgreementDate() {
        return agreementDate;
    }


    /**
     * Sets the agreementDate value for this OfficeLoanClaimType.
     * 
     * @param agreementDate   * Дата подписания заявки клинтом
     */
    public void setAgreementDate(java.util.Date agreementDate) {
        this.agreementDate = agreementDate;
    }


    /**
     * Gets the type value for this OfficeLoanClaimType.
     * 
     * @return type   * Тип продукта. 1 - Потребительский кредит 3 - Банковские карты
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this OfficeLoanClaimType.
     * 
     * @param type   * Тип продукта. 1 - Потребительский кредит 3 - Банковские карты
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the productCode value for this OfficeLoanClaimType.
     * 
     * @return productCode   * В соответствии со справочником продуктов: 5 - Доверительный
     * кредит, 10 - Потребительский кредит без обеспечения Новый продукт:
     * Дебетовые карты с разрешенным овердрафтом для VIP-клиентов
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this OfficeLoanClaimType.
     * 
     * @param productCode   * В соответствии со справочником продуктов: 5 - Доверительный
     * кредит, 10 - Потребительский кредит без обеспечения Новый продукт:
     * Дебетовые карты с разрешенным овердрафтом для VIP-клиентов
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the subProductCode value for this OfficeLoanClaimType.
     * 
     * @return subProductCode   * Код субпродукта
     */
    public java.lang.String getSubProductCode() {
        return subProductCode;
    }


    /**
     * Sets the subProductCode value for this OfficeLoanClaimType.
     * 
     * @param subProductCode   * Код субпродукта
     */
    public void setSubProductCode(java.lang.String subProductCode) {
        this.subProductCode = subProductCode;
    }


    /**
     * Gets the productAmount value for this OfficeLoanClaimType.
     * 
     * @return productAmount   * Сумма кредита
     */
    public java.math.BigDecimal getProductAmount() {
        return productAmount;
    }


    /**
     * Sets the productAmount value for this OfficeLoanClaimType.
     * 
     * @param productAmount   * Сумма кредита
     */
    public void setProductAmount(java.math.BigDecimal productAmount) {
        this.productAmount = productAmount;
    }


    /**
     * Gets the productPeriod value for this OfficeLoanClaimType.
     * 
     * @return productPeriod   * Срок кредита в месяцах
     */
    public java.math.BigInteger getProductPeriod() {
        return productPeriod;
    }


    /**
     * Sets the productPeriod value for this OfficeLoanClaimType.
     * 
     * @param productPeriod   * Срок кредита в месяцах
     */
    public void setProductPeriod(java.math.BigInteger productPeriod) {
        this.productPeriod = productPeriod;
    }


    /**
     * Gets the loanAmount value for this OfficeLoanClaimType.
     * 
     * @return loanAmount   * Сумма одобренного кредита
     */
    public java.math.BigDecimal getLoanAmount() {
        return loanAmount;
    }


    /**
     * Sets the loanAmount value for this OfficeLoanClaimType.
     * 
     * @param loanAmount   * Сумма одобренного кредита
     */
    public void setLoanAmount(java.math.BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }


    /**
     * Gets the loanPeriod value for this OfficeLoanClaimType.
     * 
     * @return loanPeriod   * Срок одобренного кредита в месяцах
     */
    public java.math.BigInteger getLoanPeriod() {
        return loanPeriod;
    }


    /**
     * Sets the loanPeriod value for this OfficeLoanClaimType.
     * 
     * @param loanPeriod   * Срок одобренного кредита в месяцах
     */
    public void setLoanPeriod(java.math.BigInteger loanPeriod) {
        this.loanPeriod = loanPeriod;
    }


    /**
     * Gets the loanRate value for this OfficeLoanClaimType.
     * 
     * @return loanRate   * Ставка одобренного кредита
     */
    public java.math.BigDecimal getLoanRate() {
        return loanRate;
    }


    /**
     * Sets the loanRate value for this OfficeLoanClaimType.
     * 
     * @param loanRate   * Ставка одобренного кредита
     */
    public void setLoanRate(java.math.BigDecimal loanRate) {
        this.loanRate = loanRate;
    }


    /**
     * Gets the currency value for this OfficeLoanClaimType.
     * 
     * @return currency   * Валюта кредита
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this OfficeLoanClaimType.
     * 
     * @param currency   * Валюта кредита
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the paymentType value for this OfficeLoanClaimType.
     * 
     * @return paymentType   * Тип погашения кредита
     */
    public java.lang.String getPaymentType() {
        return paymentType;
    }


    /**
     * Sets the paymentType value for this OfficeLoanClaimType.
     * 
     * @param paymentType   * Тип погашения кредита
     */
    public void setPaymentType(java.lang.String paymentType) {
        this.paymentType = paymentType;
    }


    /**
     * Gets the firstName value for this OfficeLoanClaimType.
     * 
     * @return firstName   * Имя
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this OfficeLoanClaimType.
     * 
     * @param firstName   * Имя
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the surName value for this OfficeLoanClaimType.
     * 
     * @return surName   * Фамилия
     */
    public java.lang.String getSurName() {
        return surName;
    }


    /**
     * Sets the surName value for this OfficeLoanClaimType.
     * 
     * @param surName   * Фамилия
     */
    public void setSurName(java.lang.String surName) {
        this.surName = surName;
    }


    /**
     * Gets the birthDay value for this OfficeLoanClaimType.
     * 
     * @return birthDay   * Дата рождения
     */
    public java.util.Date getBirthDay() {
        return birthDay;
    }


    /**
     * Sets the birthDay value for this OfficeLoanClaimType.
     * 
     * @param birthDay   * Дата рождения
     */
    public void setBirthDay(java.util.Date birthDay) {
        this.birthDay = birthDay;
    }


    /**
     * Gets the patrName value for this OfficeLoanClaimType.
     * 
     * @return patrName   * Очество
     */
    public java.lang.String getPatrName() {
        return patrName;
    }


    /**
     * Sets the patrName value for this OfficeLoanClaimType.
     * 
     * @param patrName   * Очество
     */
    public void setPatrName(java.lang.String patrName) {
        this.patrName = patrName;
    }


    /**
     * Gets the citizen value for this OfficeLoanClaimType.
     * 
     * @return citizen   * Гражданство
     */
    public java.lang.String getCitizen() {
        return citizen;
    }


    /**
     * Sets the citizen value for this OfficeLoanClaimType.
     * 
     * @param citizen   * Гражданство
     */
    public void setCitizen(java.lang.String citizen) {
        this.citizen = citizen;
    }


    /**
     * Gets the documentSeries value for this OfficeLoanClaimType.
     * 
     * @return documentSeries   * Серия документа
     */
    public java.lang.String getDocumentSeries() {
        return documentSeries;
    }


    /**
     * Sets the documentSeries value for this OfficeLoanClaimType.
     * 
     * @param documentSeries   * Серия документа
     */
    public void setDocumentSeries(java.lang.String documentSeries) {
        this.documentSeries = documentSeries;
    }


    /**
     * Gets the documentNumber value for this OfficeLoanClaimType.
     * 
     * @return documentNumber   * Номер документа
     */
    public java.lang.String getDocumentNumber() {
        return documentNumber;
    }


    /**
     * Sets the documentNumber value for this OfficeLoanClaimType.
     * 
     * @param documentNumber   * Номер документа
     */
    public void setDocumentNumber(java.lang.String documentNumber) {
        this.documentNumber = documentNumber;
    }


    /**
     * Gets the passportIssueDate value for this OfficeLoanClaimType.
     * 
     * @return passportIssueDate   * Дата выдачи документа
     */
    public java.util.Date getPassportIssueDate() {
        return passportIssueDate;
    }


    /**
     * Sets the passportIssueDate value for this OfficeLoanClaimType.
     * 
     * @param passportIssueDate   * Дата выдачи документа
     */
    public void setPassportIssueDate(java.util.Date passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }


    /**
     * Gets the passportIssueByCode value for this OfficeLoanClaimType.
     * 
     * @return passportIssueByCode   * Код подразделения
     */
    public java.lang.String getPassportIssueByCode() {
        return passportIssueByCode;
    }


    /**
     * Sets the passportIssueByCode value for this OfficeLoanClaimType.
     * 
     * @param passportIssueByCode   * Код подразделения
     */
    public void setPassportIssueByCode(java.lang.String passportIssueByCode) {
        this.passportIssueByCode = passportIssueByCode;
    }


    /**
     * Gets the passportIssueBy value for this OfficeLoanClaimType.
     * 
     * @return passportIssueBy   * Орган, выдавший документ
     */
    public java.lang.String getPassportIssueBy() {
        return passportIssueBy;
    }


    /**
     * Sets the passportIssueBy value for this OfficeLoanClaimType.
     * 
     * @param passportIssueBy   * Орган, выдавший документ
     */
    public void setPassportIssueBy(java.lang.String passportIssueBy) {
        this.passportIssueBy = passportIssueBy;
    }


    /**
     * Gets the hasOldPassport value for this OfficeLoanClaimType.
     * 
     * @return hasOldPassport   * Наличие в действующем паспорте отметки о ранее выданном (Паспорт
     * менялся)
     */
    public boolean isHasOldPassport() {
        return hasOldPassport;
    }


    /**
     * Sets the hasOldPassport value for this OfficeLoanClaimType.
     * 
     * @param hasOldPassport   * Наличие в действующем паспорте отметки о ранее выданном (Паспорт
     * менялся)
     */
    public void setHasOldPassport(boolean hasOldPassport) {
        this.hasOldPassport = hasOldPassport;
    }


    /**
     * Gets the oldDocumentSeries value for this OfficeLoanClaimType.
     * 
     * @return oldDocumentSeries   * Серия предыдущего паспорта
     */
    public java.lang.String getOldDocumentSeries() {
        return oldDocumentSeries;
    }


    /**
     * Sets the oldDocumentSeries value for this OfficeLoanClaimType.
     * 
     * @param oldDocumentSeries   * Серия предыдущего паспорта
     */
    public void setOldDocumentSeries(java.lang.String oldDocumentSeries) {
        this.oldDocumentSeries = oldDocumentSeries;
    }


    /**
     * Gets the oldDocumentNumber value for this OfficeLoanClaimType.
     * 
     * @return oldDocumentNumber   * Номер предыдущего паспорта
     */
    public java.lang.String getOldDocumentNumber() {
        return oldDocumentNumber;
    }


    /**
     * Sets the oldDocumentNumber value for this OfficeLoanClaimType.
     * 
     * @param oldDocumentNumber   * Номер предыдущего паспорта
     */
    public void setOldDocumentNumber(java.lang.String oldDocumentNumber) {
        this.oldDocumentNumber = oldDocumentNumber;
    }


    /**
     * Gets the oldPassportIssueDate value for this OfficeLoanClaimType.
     * 
     * @return oldPassportIssueDate   * Дата выдачи предыдущего паспорта
     */
    public java.util.Date getOldPassportIssueDate() {
        return oldPassportIssueDate;
    }


    /**
     * Sets the oldPassportIssueDate value for this OfficeLoanClaimType.
     * 
     * @param oldPassportIssueDate   * Дата выдачи предыдущего паспорта
     */
    public void setOldPassportIssueDate(java.util.Date oldPassportIssueDate) {
        this.oldPassportIssueDate = oldPassportIssueDate;
    }


    /**
     * Gets the oldPassportIssueBy value for this OfficeLoanClaimType.
     * 
     * @return oldPassportIssueBy   * Орган, выдавший предыдущий паспорт
     */
    public java.lang.String getOldPassportIssueBy() {
        return oldPassportIssueBy;
    }


    /**
     * Sets the oldPassportIssueBy value for this OfficeLoanClaimType.
     * 
     * @param oldPassportIssueBy   * Орган, выдавший предыдущий паспорт
     */
    public void setOldPassportIssueBy(java.lang.String oldPassportIssueBy) {
        this.oldPassportIssueBy = oldPassportIssueBy;
    }


    /**
     * Gets the createDate value for this OfficeLoanClaimType.
     * 
     * @return createDate   * Дата запроса
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this OfficeLoanClaimType.
     * 
     * @param createDate   * Дата запроса
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the typeOfIssue value for this OfficeLoanClaimType.
     * 
     * @return typeOfIssue   * Тип выдачи кредита
     */
    public java.lang.String getTypeOfIssue() {
        return typeOfIssue;
    }


    /**
     * Sets the typeOfIssue value for this OfficeLoanClaimType.
     * 
     * @param typeOfIssue   * Тип выдачи кредита
     */
    public void setTypeOfIssue(java.lang.String typeOfIssue) {
        this.typeOfIssue = typeOfIssue;
    }


    /**
     * Gets the acctId value for this OfficeLoanClaimType.
     * 
     * @return acctId   * Номер счета вклада в Сбербанке
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this OfficeLoanClaimType.
     * 
     * @param acctId   * Номер счета вклада в Сбербанке
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the cardNum value for this OfficeLoanClaimType.
     * 
     * @return cardNum   * Номер банковской карты (кроме Сберкарт)
     */
    public java.lang.String getCardNum() {
        return cardNum;
    }


    /**
     * Sets the cardNum value for this OfficeLoanClaimType.
     * 
     * @param cardNum   * Номер банковской карты (кроме Сберкарт)
     */
    public void setCardNum(java.lang.String cardNum) {
        this.cardNum = cardNum;
    }


    /**
     * Gets the preapproved value for this OfficeLoanClaimType.
     * 
     * @return preapproved   * Признак предодобренного предложения. 1 - предодобренное 0 -
     * нет
     */
    public boolean isPreapproved() {
        return preapproved;
    }


    /**
     * Sets the preapproved value for this OfficeLoanClaimType.
     * 
     * @param preapproved   * Признак предодобренного предложения. 1 - предодобренное 0 -
     * нет
     */
    public void setPreapproved(boolean preapproved) {
        this.preapproved = preapproved;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OfficeLoanClaimType)) return false;
        OfficeLoanClaimType other = (OfficeLoanClaimType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.applicationNumber==null && other.getApplicationNumber()==null) || 
             (this.applicationNumber!=null &&
              this.applicationNumber.equals(other.getApplicationNumber()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.needVisitOfficeReason==null && other.getNeedVisitOfficeReason()==null) || 
             (this.needVisitOfficeReason!=null &&
              this.needVisitOfficeReason.equals(other.getNeedVisitOfficeReason()))) &&
            ((this.fioKI==null && other.getFioKI()==null) || 
             (this.fioKI!=null &&
              this.fioKI.equals(other.getFioKI()))) &&
            ((this.loginKI==null && other.getLoginKI()==null) || 
             (this.loginKI!=null &&
              this.loginKI.equals(other.getLoginKI()))) &&
            ((this.fioTM==null && other.getFioTM()==null) || 
             (this.fioTM!=null &&
              this.fioTM.equals(other.getFioTM()))) &&
            ((this.loginTM==null && other.getLoginTM()==null) || 
             (this.loginTM!=null &&
              this.loginTM.equals(other.getLoginTM()))) &&
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.channel==null && other.getChannel()==null) || 
             (this.channel!=null &&
              this.channel.equals(other.getChannel()))) &&
            ((this.agreementDate==null && other.getAgreementDate()==null) || 
             (this.agreementDate!=null &&
              this.agreementDate.equals(other.getAgreementDate()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.subProductCode==null && other.getSubProductCode()==null) || 
             (this.subProductCode!=null &&
              this.subProductCode.equals(other.getSubProductCode()))) &&
            ((this.productAmount==null && other.getProductAmount()==null) || 
             (this.productAmount!=null &&
              this.productAmount.equals(other.getProductAmount()))) &&
            ((this.productPeriod==null && other.getProductPeriod()==null) || 
             (this.productPeriod!=null &&
              this.productPeriod.equals(other.getProductPeriod()))) &&
            ((this.loanAmount==null && other.getLoanAmount()==null) || 
             (this.loanAmount!=null &&
              this.loanAmount.equals(other.getLoanAmount()))) &&
            ((this.loanPeriod==null && other.getLoanPeriod()==null) || 
             (this.loanPeriod!=null &&
              this.loanPeriod.equals(other.getLoanPeriod()))) &&
            ((this.loanRate==null && other.getLoanRate()==null) || 
             (this.loanRate!=null &&
              this.loanRate.equals(other.getLoanRate()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.paymentType==null && other.getPaymentType()==null) || 
             (this.paymentType!=null &&
              this.paymentType.equals(other.getPaymentType()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.surName==null && other.getSurName()==null) || 
             (this.surName!=null &&
              this.surName.equals(other.getSurName()))) &&
            ((this.birthDay==null && other.getBirthDay()==null) || 
             (this.birthDay!=null &&
              this.birthDay.equals(other.getBirthDay()))) &&
            ((this.patrName==null && other.getPatrName()==null) || 
             (this.patrName!=null &&
              this.patrName.equals(other.getPatrName()))) &&
            ((this.citizen==null && other.getCitizen()==null) || 
             (this.citizen!=null &&
              this.citizen.equals(other.getCitizen()))) &&
            ((this.documentSeries==null && other.getDocumentSeries()==null) || 
             (this.documentSeries!=null &&
              this.documentSeries.equals(other.getDocumentSeries()))) &&
            ((this.documentNumber==null && other.getDocumentNumber()==null) || 
             (this.documentNumber!=null &&
              this.documentNumber.equals(other.getDocumentNumber()))) &&
            ((this.passportIssueDate==null && other.getPassportIssueDate()==null) || 
             (this.passportIssueDate!=null &&
              this.passportIssueDate.equals(other.getPassportIssueDate()))) &&
            ((this.passportIssueByCode==null && other.getPassportIssueByCode()==null) || 
             (this.passportIssueByCode!=null &&
              this.passportIssueByCode.equals(other.getPassportIssueByCode()))) &&
            ((this.passportIssueBy==null && other.getPassportIssueBy()==null) || 
             (this.passportIssueBy!=null &&
              this.passportIssueBy.equals(other.getPassportIssueBy()))) &&
            this.hasOldPassport == other.isHasOldPassport() &&
            ((this.oldDocumentSeries==null && other.getOldDocumentSeries()==null) || 
             (this.oldDocumentSeries!=null &&
              this.oldDocumentSeries.equals(other.getOldDocumentSeries()))) &&
            ((this.oldDocumentNumber==null && other.getOldDocumentNumber()==null) || 
             (this.oldDocumentNumber!=null &&
              this.oldDocumentNumber.equals(other.getOldDocumentNumber()))) &&
            ((this.oldPassportIssueDate==null && other.getOldPassportIssueDate()==null) || 
             (this.oldPassportIssueDate!=null &&
              this.oldPassportIssueDate.equals(other.getOldPassportIssueDate()))) &&
            ((this.oldPassportIssueBy==null && other.getOldPassportIssueBy()==null) || 
             (this.oldPassportIssueBy!=null &&
              this.oldPassportIssueBy.equals(other.getOldPassportIssueBy()))) &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.typeOfIssue==null && other.getTypeOfIssue()==null) || 
             (this.typeOfIssue!=null &&
              this.typeOfIssue.equals(other.getTypeOfIssue()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.cardNum==null && other.getCardNum()==null) || 
             (this.cardNum!=null &&
              this.cardNum.equals(other.getCardNum()))) &&
            this.preapproved == other.isPreapproved();
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
        if (getApplicationNumber() != null) {
            _hashCode += getApplicationNumber().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getNeedVisitOfficeReason() != null) {
            _hashCode += getNeedVisitOfficeReason().hashCode();
        }
        if (getFioKI() != null) {
            _hashCode += getFioKI().hashCode();
        }
        if (getLoginKI() != null) {
            _hashCode += getLoginKI().hashCode();
        }
        if (getFioTM() != null) {
            _hashCode += getFioTM().hashCode();
        }
        if (getLoginTM() != null) {
            _hashCode += getLoginTM().hashCode();
        }
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getChannel() != null) {
            _hashCode += getChannel().hashCode();
        }
        if (getAgreementDate() != null) {
            _hashCode += getAgreementDate().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getSubProductCode() != null) {
            _hashCode += getSubProductCode().hashCode();
        }
        if (getProductAmount() != null) {
            _hashCode += getProductAmount().hashCode();
        }
        if (getProductPeriod() != null) {
            _hashCode += getProductPeriod().hashCode();
        }
        if (getLoanAmount() != null) {
            _hashCode += getLoanAmount().hashCode();
        }
        if (getLoanPeriod() != null) {
            _hashCode += getLoanPeriod().hashCode();
        }
        if (getLoanRate() != null) {
            _hashCode += getLoanRate().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getPaymentType() != null) {
            _hashCode += getPaymentType().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getSurName() != null) {
            _hashCode += getSurName().hashCode();
        }
        if (getBirthDay() != null) {
            _hashCode += getBirthDay().hashCode();
        }
        if (getPatrName() != null) {
            _hashCode += getPatrName().hashCode();
        }
        if (getCitizen() != null) {
            _hashCode += getCitizen().hashCode();
        }
        if (getDocumentSeries() != null) {
            _hashCode += getDocumentSeries().hashCode();
        }
        if (getDocumentNumber() != null) {
            _hashCode += getDocumentNumber().hashCode();
        }
        if (getPassportIssueDate() != null) {
            _hashCode += getPassportIssueDate().hashCode();
        }
        if (getPassportIssueByCode() != null) {
            _hashCode += getPassportIssueByCode().hashCode();
        }
        if (getPassportIssueBy() != null) {
            _hashCode += getPassportIssueBy().hashCode();
        }
        _hashCode += (isHasOldPassport() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getOldDocumentSeries() != null) {
            _hashCode += getOldDocumentSeries().hashCode();
        }
        if (getOldDocumentNumber() != null) {
            _hashCode += getOldDocumentNumber().hashCode();
        }
        if (getOldPassportIssueDate() != null) {
            _hashCode += getOldPassportIssueDate().hashCode();
        }
        if (getOldPassportIssueBy() != null) {
            _hashCode += getOldPassportIssueBy().hashCode();
        }
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getTypeOfIssue() != null) {
            _hashCode += getTypeOfIssue().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getCardNum() != null) {
            _hashCode += getCardNum().hashCode();
        }
        _hashCode += (isPreapproved() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OfficeLoanClaimType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfficeLoanClaimType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "ApplicationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("needVisitOfficeReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "needVisitOfficeReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fioKI");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "fioKI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginKI");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "loginKI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fioTM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "fioTM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginTM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "loginTM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreementDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "agreementDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "productCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subProductCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "subProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "productAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "productPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "loanAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "loanPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "loanRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "paymentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "surName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "birthDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patrName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "patrName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("citizen");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "citizen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentSeries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "documentSeries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "documentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passportIssueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "passportIssueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passportIssueByCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "passportIssueByCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passportIssueBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "passportIssueBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasOldPassport");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "hasOldPassport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldDocumentSeries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "oldDocumentSeries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldDocumentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "oldDocumentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldPassportIssueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "oldPassportIssueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldPassportIssueBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "oldPassportIssueBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeOfIssue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "typeOfIssue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "acctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "cardNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preapproved");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "preapproved"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
