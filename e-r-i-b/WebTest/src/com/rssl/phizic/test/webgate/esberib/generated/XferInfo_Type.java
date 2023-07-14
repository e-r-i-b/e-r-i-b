/**
 * XferInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class XferInfo_Type  implements java.io.Serializable {
    /* Перечисление, куда отправляется перевод */
    private java.lang.String xferMethod;

    /* Номер клиентского поручения (Используется для оплаты товаров
     * и услуг и создании ДП оплаты в Московском банке) */
    private java.lang.Long clientDocumentNumber;

    /* Дата клиентского поручения. (Используется для оплаты товаров
     * и услуг и создании ДП оплаты в Москоском банке) */
    private java.lang.String clientDocumentDate;

    /* Идентифицирующая информация о плательщике. (Используется для
     * оплаты товаров и услуг и создании ДП оплаты товаров и услуг  в Москоском
     * банке) */
    private com.rssl.phizic.test.webgate.esberib.generated.XferInfo_TypePayerInfo payerInfo;

    /* ИНН получателя */
    private java.lang.String taxIdTo;

    /* КПП получателя */
    private java.lang.String KPPTo;

    /* ИНН плательщика */
    private java.lang.String taxIdFrom;

    /* Наименование получателя юр. Лица, либо ФИО физ.лица, склеенные
     * через пробел. Пробелы внутри фамилии, имени и отчества не допускаются */
    private java.lang.String recipientName;

    private com.rssl.phizic.test.webgate.esberib.generated.CustInfo_Type custInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type taxColl;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdTo;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo;

    private com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type loanAcctIdTo;

    private com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo;

    /* Идентификатор разбивки */
    private java.lang.String idSpacing;

    /* Назначение платежа */
    private java.lang.String purpose;

    /* Код операции по счетам нерезидентов */
    private java.lang.String nonResCode;

    private java.math.BigDecimal curAmt;

    private java.lang.String acctCur;

    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId;

    /* Сумма операции */
    private java.math.BigDecimal curAmt1;

    /* Валюта операции */
    private java.lang.String acctCur1;

    /* Сумма комиссии */
    private java.math.BigDecimal curAmt2;

    /* Валюта комиссии */
    private java.lang.String acctCur2;

    /* Дата, на которую получена разбивка */
    private java.lang.String dateCalc;

    private com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type curAmtConv;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    /* Код авторизации прямой транзакции */
    private java.lang.Long parentAuthorizationCode;

    /* Время прямой транзакции */
    private java.lang.String parentTransDtTm;

    /* Признак операции открытия с закрытием. На первом этапе внедрения
     * - фиксированное значение «false» */
    private java.lang.Boolean withClose;

    /* Признак проведения операции. Для операции DepToNewDepAdd и
     * CardToNewDepAdd передается фиксированное значение «true». */
    private java.lang.Boolean execute;

    /* Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.comission, полученного из АС ЦПФЛ. Передается для
     * московского банка при оплате товаров и услуг */
    private java.math.BigDecimal comission;

    /* Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.nplatRequisites.requisites, полученного из АС ЦПФЛ.
     * Передается для московского банка при оплате товаров и услуг */
    private java.lang.String requisites;

    /* Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.nplatRequisites.requisites, полученного из АС ЦПФЛ.
     * Передается для московского банка при создании длительных поручений
     * на оплату товаров и услуг */
    private java.lang.String dataSpec;

    /* Уникальный идентификатор организации получателя платежей (Передается
     * в случае оплаты договорного получателя для Московского банка ) */
    private java.lang.String uniqueNumber;

    /* Код спецклиента (Передается в случае оплаты товаров и услуг
     * и создания ДП для Московского банка) */
    private java.lang.String specClientCode;

    /* Признак платежа за текущий месяц. Передается в случае создания
     * ДП для оплаты товаров и услуг для Московского банка */
    private java.lang.Boolean curMonth;

    /* Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.CommissionLabel, полученного из АС ЦПФЛ.Передается
     * в случае создания ДП для оплаты товаров и услуг для Московского банка */
    private java.lang.String commissionLabel;

    /* Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.ServiceCode, полученного из АС ЦПФЛ. Передается
     * в случае создания ДП для оплаты товаров и услуг для Московского банка */
    private java.lang.String serviceCode;

    /* Код тарифа 
     * Значение данного элемента должно совпадать со значением элемента preparedPayment_a.tarif.tarifCode,
     * выбранного клиентом из коллекции  тарифов  полученной из АС ЦПФЛ в
     * узле preparedPayment_a.tarif. Передается в случае создания ДП для
     * оплаты товаров и услуг для Московского банка */
    private java.lang.String serviceKind;

    /* Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.tarif.tarifValue, соотвествующего выбранному клиентом
     * preparedPayment_a.tarif.tarifCode из коллекции тарифов preparedPayment_a.tarif
     * полученной из АС ЦПФЛ. Передается в случае создания ДП для 
     * оплаты товаров и услуг для Московского банка */
    private java.lang.String tarif;

    /* Признак разрешения изменения суммы платежа по тарифам. 
     * Значение данного элемента устанавливается клиентом, если значение
     * узла preparedPayment_a.ackClientBankCanChangeSumm  в сообщении из
     * АС ЦПФЛ равно true. Иначе значение элемента должно совпадать со значением
     * элемента preparedPayment_a.ackClientBankCanChangeSumm. Передается
     * в случае создания ДП для 
     * оплаты товаров и услуг для Московского банка */
    private java.lang.String canChangeTarif;

    /* Разбивка микроопераций списания */
    private com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo;

    public XferInfo_Type() {
    }

    public XferInfo_Type(
           java.lang.String xferMethod,
           java.lang.Long clientDocumentNumber,
           java.lang.String clientDocumentDate,
           com.rssl.phizic.test.webgate.esberib.generated.XferInfo_TypePayerInfo payerInfo,
           java.lang.String taxIdTo,
           java.lang.String KPPTo,
           java.lang.String taxIdFrom,
           java.lang.String recipientName,
           com.rssl.phizic.test.webgate.esberib.generated.CustInfo_Type custInfo,
           com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type taxColl,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdTo,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo,
           com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type loanAcctIdTo,
           com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo,
           java.lang.String idSpacing,
           java.lang.String purpose,
           java.lang.String nonResCode,
           java.math.BigDecimal curAmt,
           java.lang.String acctCur,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId,
           java.math.BigDecimal curAmt1,
           java.lang.String acctCur1,
           java.math.BigDecimal curAmt2,
           java.lang.String acctCur2,
           java.lang.String dateCalc,
           com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type curAmtConv,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           java.lang.Long parentAuthorizationCode,
           java.lang.String parentTransDtTm,
           java.lang.Boolean withClose,
           java.lang.Boolean execute,
           java.math.BigDecimal comission,
           java.lang.String requisites,
           java.lang.String dataSpec,
           java.lang.String uniqueNumber,
           java.lang.String specClientCode,
           java.lang.Boolean curMonth,
           java.lang.String commissionLabel,
           java.lang.String serviceCode,
           java.lang.String serviceKind,
           java.lang.String tarif,
           java.lang.String canChangeTarif,
           com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo) {
           this.xferMethod = xferMethod;
           this.clientDocumentNumber = clientDocumentNumber;
           this.clientDocumentDate = clientDocumentDate;
           this.payerInfo = payerInfo;
           this.taxIdTo = taxIdTo;
           this.KPPTo = KPPTo;
           this.taxIdFrom = taxIdFrom;
           this.recipientName = recipientName;
           this.custInfo = custInfo;
           this.taxColl = taxColl;
           this.cardAcctIdFrom = cardAcctIdFrom;
           this.depAcctIdFrom = depAcctIdFrom;
           this.depAcctIdTo = depAcctIdTo;
           this.cardAcctIdTo = cardAcctIdTo;
           this.loanAcctIdTo = loanAcctIdTo;
           this.agreemtInfo = agreemtInfo;
           this.idSpacing = idSpacing;
           this.purpose = purpose;
           this.nonResCode = nonResCode;
           this.curAmt = curAmt;
           this.acctCur = acctCur;
           this.cardAcctId = cardAcctId;
           this.depAcctId = depAcctId;
           this.curAmt1 = curAmt1;
           this.acctCur1 = acctCur1;
           this.curAmt2 = curAmt2;
           this.acctCur2 = acctCur2;
           this.dateCalc = dateCalc;
           this.curAmtConv = curAmtConv;
           this.bankInfo = bankInfo;
           this.parentAuthorizationCode = parentAuthorizationCode;
           this.parentTransDtTm = parentTransDtTm;
           this.withClose = withClose;
           this.execute = execute;
           this.comission = comission;
           this.requisites = requisites;
           this.dataSpec = dataSpec;
           this.uniqueNumber = uniqueNumber;
           this.specClientCode = specClientCode;
           this.curMonth = curMonth;
           this.commissionLabel = commissionLabel;
           this.serviceCode = serviceCode;
           this.serviceKind = serviceKind;
           this.tarif = tarif;
           this.canChangeTarif = canChangeTarif;
           this.srcLayoutInfo = srcLayoutInfo;
    }


    /**
     * Gets the xferMethod value for this XferInfo_Type.
     * 
     * @return xferMethod   * Перечисление, куда отправляется перевод
     */
    public java.lang.String getXferMethod() {
        return xferMethod;
    }


    /**
     * Sets the xferMethod value for this XferInfo_Type.
     * 
     * @param xferMethod   * Перечисление, куда отправляется перевод
     */
    public void setXferMethod(java.lang.String xferMethod) {
        this.xferMethod = xferMethod;
    }


    /**
     * Gets the clientDocumentNumber value for this XferInfo_Type.
     * 
     * @return clientDocumentNumber   * Номер клиентского поручения (Используется для оплаты товаров
     * и услуг и создании ДП оплаты в Московском банке)
     */
    public java.lang.Long getClientDocumentNumber() {
        return clientDocumentNumber;
    }


    /**
     * Sets the clientDocumentNumber value for this XferInfo_Type.
     * 
     * @param clientDocumentNumber   * Номер клиентского поручения (Используется для оплаты товаров
     * и услуг и создании ДП оплаты в Московском банке)
     */
    public void setClientDocumentNumber(java.lang.Long clientDocumentNumber) {
        this.clientDocumentNumber = clientDocumentNumber;
    }


    /**
     * Gets the clientDocumentDate value for this XferInfo_Type.
     * 
     * @return clientDocumentDate   * Дата клиентского поручения. (Используется для оплаты товаров
     * и услуг и создании ДП оплаты в Москоском банке)
     */
    public java.lang.String getClientDocumentDate() {
        return clientDocumentDate;
    }


    /**
     * Sets the clientDocumentDate value for this XferInfo_Type.
     * 
     * @param clientDocumentDate   * Дата клиентского поручения. (Используется для оплаты товаров
     * и услуг и создании ДП оплаты в Москоском банке)
     */
    public void setClientDocumentDate(java.lang.String clientDocumentDate) {
        this.clientDocumentDate = clientDocumentDate;
    }


    /**
     * Gets the payerInfo value for this XferInfo_Type.
     * 
     * @return payerInfo   * Идентифицирующая информация о плательщике. (Используется для
     * оплаты товаров и услуг и создании ДП оплаты товаров и услуг  в Москоском
     * банке)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.XferInfo_TypePayerInfo getPayerInfo() {
        return payerInfo;
    }


    /**
     * Sets the payerInfo value for this XferInfo_Type.
     * 
     * @param payerInfo   * Идентифицирующая информация о плательщике. (Используется для
     * оплаты товаров и услуг и создании ДП оплаты товаров и услуг  в Москоском
     * банке)
     */
    public void setPayerInfo(com.rssl.phizic.test.webgate.esberib.generated.XferInfo_TypePayerInfo payerInfo) {
        this.payerInfo = payerInfo;
    }


    /**
     * Gets the taxIdTo value for this XferInfo_Type.
     * 
     * @return taxIdTo   * ИНН получателя
     */
    public java.lang.String getTaxIdTo() {
        return taxIdTo;
    }


    /**
     * Sets the taxIdTo value for this XferInfo_Type.
     * 
     * @param taxIdTo   * ИНН получателя
     */
    public void setTaxIdTo(java.lang.String taxIdTo) {
        this.taxIdTo = taxIdTo;
    }


    /**
     * Gets the KPPTo value for this XferInfo_Type.
     * 
     * @return KPPTo   * КПП получателя
     */
    public java.lang.String getKPPTo() {
        return KPPTo;
    }


    /**
     * Sets the KPPTo value for this XferInfo_Type.
     * 
     * @param KPPTo   * КПП получателя
     */
    public void setKPPTo(java.lang.String KPPTo) {
        this.KPPTo = KPPTo;
    }


    /**
     * Gets the taxIdFrom value for this XferInfo_Type.
     * 
     * @return taxIdFrom   * ИНН плательщика
     */
    public java.lang.String getTaxIdFrom() {
        return taxIdFrom;
    }


    /**
     * Sets the taxIdFrom value for this XferInfo_Type.
     * 
     * @param taxIdFrom   * ИНН плательщика
     */
    public void setTaxIdFrom(java.lang.String taxIdFrom) {
        this.taxIdFrom = taxIdFrom;
    }


    /**
     * Gets the recipientName value for this XferInfo_Type.
     * 
     * @return recipientName   * Наименование получателя юр. Лица, либо ФИО физ.лица, склеенные
     * через пробел. Пробелы внутри фамилии, имени и отчества не допускаются
     */
    public java.lang.String getRecipientName() {
        return recipientName;
    }


    /**
     * Sets the recipientName value for this XferInfo_Type.
     * 
     * @param recipientName   * Наименование получателя юр. Лица, либо ФИО физ.лица, склеенные
     * через пробел. Пробелы внутри фамилии, имени и отчества не допускаются
     */
    public void setRecipientName(java.lang.String recipientName) {
        this.recipientName = recipientName;
    }


    /**
     * Gets the custInfo value for this XferInfo_Type.
     * 
     * @return custInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CustInfo_Type getCustInfo() {
        return custInfo;
    }


    /**
     * Sets the custInfo value for this XferInfo_Type.
     * 
     * @param custInfo
     */
    public void setCustInfo(com.rssl.phizic.test.webgate.esberib.generated.CustInfo_Type custInfo) {
        this.custInfo = custInfo;
    }


    /**
     * Gets the taxColl value for this XferInfo_Type.
     * 
     * @return taxColl
     */
    public com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type getTaxColl() {
        return taxColl;
    }


    /**
     * Sets the taxColl value for this XferInfo_Type.
     * 
     * @param taxColl
     */
    public void setTaxColl(com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type taxColl) {
        this.taxColl = taxColl;
    }


    /**
     * Gets the cardAcctIdFrom value for this XferInfo_Type.
     * 
     * @return cardAcctIdFrom
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdFrom() {
        return cardAcctIdFrom;
    }


    /**
     * Sets the cardAcctIdFrom value for this XferInfo_Type.
     * 
     * @param cardAcctIdFrom
     */
    public void setCardAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom) {
        this.cardAcctIdFrom = cardAcctIdFrom;
    }


    /**
     * Gets the depAcctIdFrom value for this XferInfo_Type.
     * 
     * @return depAcctIdFrom
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctIdFrom() {
        return depAcctIdFrom;
    }


    /**
     * Sets the depAcctIdFrom value for this XferInfo_Type.
     * 
     * @param depAcctIdFrom
     */
    public void setDepAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdFrom) {
        this.depAcctIdFrom = depAcctIdFrom;
    }


    /**
     * Gets the depAcctIdTo value for this XferInfo_Type.
     * 
     * @return depAcctIdTo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctIdTo() {
        return depAcctIdTo;
    }


    /**
     * Sets the depAcctIdTo value for this XferInfo_Type.
     * 
     * @param depAcctIdTo
     */
    public void setDepAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctIdTo) {
        this.depAcctIdTo = depAcctIdTo;
    }


    /**
     * Gets the cardAcctIdTo value for this XferInfo_Type.
     * 
     * @return cardAcctIdTo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdTo() {
        return cardAcctIdTo;
    }


    /**
     * Sets the cardAcctIdTo value for this XferInfo_Type.
     * 
     * @param cardAcctIdTo
     */
    public void setCardAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo) {
        this.cardAcctIdTo = cardAcctIdTo;
    }


    /**
     * Gets the loanAcctIdTo value for this XferInfo_Type.
     * 
     * @return loanAcctIdTo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type getLoanAcctIdTo() {
        return loanAcctIdTo;
    }


    /**
     * Sets the loanAcctIdTo value for this XferInfo_Type.
     * 
     * @param loanAcctIdTo
     */
    public void setLoanAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type loanAcctIdTo) {
        this.loanAcctIdTo = loanAcctIdTo;
    }


    /**
     * Gets the agreemtInfo value for this XferInfo_Type.
     * 
     * @return agreemtInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type getAgreemtInfo() {
        return agreemtInfo;
    }


    /**
     * Sets the agreemtInfo value for this XferInfo_Type.
     * 
     * @param agreemtInfo
     */
    public void setAgreemtInfo(com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type agreemtInfo) {
        this.agreemtInfo = agreemtInfo;
    }


    /**
     * Gets the idSpacing value for this XferInfo_Type.
     * 
     * @return idSpacing   * Идентификатор разбивки
     */
    public java.lang.String getIdSpacing() {
        return idSpacing;
    }


    /**
     * Sets the idSpacing value for this XferInfo_Type.
     * 
     * @param idSpacing   * Идентификатор разбивки
     */
    public void setIdSpacing(java.lang.String idSpacing) {
        this.idSpacing = idSpacing;
    }


    /**
     * Gets the purpose value for this XferInfo_Type.
     * 
     * @return purpose   * Назначение платежа
     */
    public java.lang.String getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this XferInfo_Type.
     * 
     * @param purpose   * Назначение платежа
     */
    public void setPurpose(java.lang.String purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the nonResCode value for this XferInfo_Type.
     * 
     * @return nonResCode   * Код операции по счетам нерезидентов
     */
    public java.lang.String getNonResCode() {
        return nonResCode;
    }


    /**
     * Sets the nonResCode value for this XferInfo_Type.
     * 
     * @param nonResCode   * Код операции по счетам нерезидентов
     */
    public void setNonResCode(java.lang.String nonResCode) {
        this.nonResCode = nonResCode;
    }


    /**
     * Gets the curAmt value for this XferInfo_Type.
     * 
     * @return curAmt
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this XferInfo_Type.
     * 
     * @param curAmt
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the acctCur value for this XferInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this XferInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the cardAcctId value for this XferInfo_Type.
     * 
     * @return cardAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this XferInfo_Type.
     * 
     * @param cardAcctId
     */
    public void setCardAcctId(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the depAcctId value for this XferInfo_Type.
     * 
     * @return depAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type getDepAcctId() {
        return depAcctId;
    }


    /**
     * Sets the depAcctId value for this XferInfo_Type.
     * 
     * @param depAcctId
     */
    public void setDepAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type depAcctId) {
        this.depAcctId = depAcctId;
    }


    /**
     * Gets the curAmt1 value for this XferInfo_Type.
     * 
     * @return curAmt1   * Сумма операции
     */
    public java.math.BigDecimal getCurAmt1() {
        return curAmt1;
    }


    /**
     * Sets the curAmt1 value for this XferInfo_Type.
     * 
     * @param curAmt1   * Сумма операции
     */
    public void setCurAmt1(java.math.BigDecimal curAmt1) {
        this.curAmt1 = curAmt1;
    }


    /**
     * Gets the acctCur1 value for this XferInfo_Type.
     * 
     * @return acctCur1   * Валюта операции
     */
    public java.lang.String getAcctCur1() {
        return acctCur1;
    }


    /**
     * Sets the acctCur1 value for this XferInfo_Type.
     * 
     * @param acctCur1   * Валюта операции
     */
    public void setAcctCur1(java.lang.String acctCur1) {
        this.acctCur1 = acctCur1;
    }


    /**
     * Gets the curAmt2 value for this XferInfo_Type.
     * 
     * @return curAmt2   * Сумма комиссии
     */
    public java.math.BigDecimal getCurAmt2() {
        return curAmt2;
    }


    /**
     * Sets the curAmt2 value for this XferInfo_Type.
     * 
     * @param curAmt2   * Сумма комиссии
     */
    public void setCurAmt2(java.math.BigDecimal curAmt2) {
        this.curAmt2 = curAmt2;
    }


    /**
     * Gets the acctCur2 value for this XferInfo_Type.
     * 
     * @return acctCur2   * Валюта комиссии
     */
    public java.lang.String getAcctCur2() {
        return acctCur2;
    }


    /**
     * Sets the acctCur2 value for this XferInfo_Type.
     * 
     * @param acctCur2   * Валюта комиссии
     */
    public void setAcctCur2(java.lang.String acctCur2) {
        this.acctCur2 = acctCur2;
    }


    /**
     * Gets the dateCalc value for this XferInfo_Type.
     * 
     * @return dateCalc   * Дата, на которую получена разбивка
     */
    public java.lang.String getDateCalc() {
        return dateCalc;
    }


    /**
     * Sets the dateCalc value for this XferInfo_Type.
     * 
     * @param dateCalc   * Дата, на которую получена разбивка
     */
    public void setDateCalc(java.lang.String dateCalc) {
        this.dateCalc = dateCalc;
    }


    /**
     * Gets the curAmtConv value for this XferInfo_Type.
     * 
     * @return curAmtConv
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type getCurAmtConv() {
        return curAmtConv;
    }


    /**
     * Sets the curAmtConv value for this XferInfo_Type.
     * 
     * @param curAmtConv
     */
    public void setCurAmtConv(com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type curAmtConv) {
        this.curAmtConv = curAmtConv;
    }


    /**
     * Gets the bankInfo value for this XferInfo_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this XferInfo_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the parentAuthorizationCode value for this XferInfo_Type.
     * 
     * @return parentAuthorizationCode   * Код авторизации прямой транзакции
     */
    public java.lang.Long getParentAuthorizationCode() {
        return parentAuthorizationCode;
    }


    /**
     * Sets the parentAuthorizationCode value for this XferInfo_Type.
     * 
     * @param parentAuthorizationCode   * Код авторизации прямой транзакции
     */
    public void setParentAuthorizationCode(java.lang.Long parentAuthorizationCode) {
        this.parentAuthorizationCode = parentAuthorizationCode;
    }


    /**
     * Gets the parentTransDtTm value for this XferInfo_Type.
     * 
     * @return parentTransDtTm   * Время прямой транзакции
     */
    public java.lang.String getParentTransDtTm() {
        return parentTransDtTm;
    }


    /**
     * Sets the parentTransDtTm value for this XferInfo_Type.
     * 
     * @param parentTransDtTm   * Время прямой транзакции
     */
    public void setParentTransDtTm(java.lang.String parentTransDtTm) {
        this.parentTransDtTm = parentTransDtTm;
    }


    /**
     * Gets the withClose value for this XferInfo_Type.
     * 
     * @return withClose   * Признак операции открытия с закрытием. На первом этапе внедрения
     * - фиксированное значение «false»
     */
    public java.lang.Boolean getWithClose() {
        return withClose;
    }


    /**
     * Sets the withClose value for this XferInfo_Type.
     * 
     * @param withClose   * Признак операции открытия с закрытием. На первом этапе внедрения
     * - фиксированное значение «false»
     */
    public void setWithClose(java.lang.Boolean withClose) {
        this.withClose = withClose;
    }


    /**
     * Gets the execute value for this XferInfo_Type.
     * 
     * @return execute   * Признак проведения операции. Для операции DepToNewDepAdd и
     * CardToNewDepAdd передается фиксированное значение «true».
     */
    public java.lang.Boolean getExecute() {
        return execute;
    }


    /**
     * Sets the execute value for this XferInfo_Type.
     * 
     * @param execute   * Признак проведения операции. Для операции DepToNewDepAdd и
     * CardToNewDepAdd передается фиксированное значение «true».
     */
    public void setExecute(java.lang.Boolean execute) {
        this.execute = execute;
    }


    /**
     * Gets the comission value for this XferInfo_Type.
     * 
     * @return comission   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.comission, полученного из АС ЦПФЛ. Передается для
     * московского банка при оплате товаров и услуг
     */
    public java.math.BigDecimal getComission() {
        return comission;
    }


    /**
     * Sets the comission value for this XferInfo_Type.
     * 
     * @param comission   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.comission, полученного из АС ЦПФЛ. Передается для
     * московского банка при оплате товаров и услуг
     */
    public void setComission(java.math.BigDecimal comission) {
        this.comission = comission;
    }


    /**
     * Gets the requisites value for this XferInfo_Type.
     * 
     * @return requisites   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.nplatRequisites.requisites, полученного из АС ЦПФЛ.
     * Передается для московского банка при оплате товаров и услуг
     */
    public java.lang.String getRequisites() {
        return requisites;
    }


    /**
     * Sets the requisites value for this XferInfo_Type.
     * 
     * @param requisites   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.nplatRequisites.requisites, полученного из АС ЦПФЛ.
     * Передается для московского банка при оплате товаров и услуг
     */
    public void setRequisites(java.lang.String requisites) {
        this.requisites = requisites;
    }


    /**
     * Gets the dataSpec value for this XferInfo_Type.
     * 
     * @return dataSpec   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.nplatRequisites.requisites, полученного из АС ЦПФЛ.
     * Передается для московского банка при создании длительных поручений
     * на оплату товаров и услуг
     */
    public java.lang.String getDataSpec() {
        return dataSpec;
    }


    /**
     * Sets the dataSpec value for this XferInfo_Type.
     * 
     * @param dataSpec   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.nplatRequisites.requisites, полученного из АС ЦПФЛ.
     * Передается для московского банка при создании длительных поручений
     * на оплату товаров и услуг
     */
    public void setDataSpec(java.lang.String dataSpec) {
        this.dataSpec = dataSpec;
    }


    /**
     * Gets the uniqueNumber value for this XferInfo_Type.
     * 
     * @return uniqueNumber   * Уникальный идентификатор организации получателя платежей (Передается
     * в случае оплаты договорного получателя для Московского банка )
     */
    public java.lang.String getUniqueNumber() {
        return uniqueNumber;
    }


    /**
     * Sets the uniqueNumber value for this XferInfo_Type.
     * 
     * @param uniqueNumber   * Уникальный идентификатор организации получателя платежей (Передается
     * в случае оплаты договорного получателя для Московского банка )
     */
    public void setUniqueNumber(java.lang.String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }


    /**
     * Gets the specClientCode value for this XferInfo_Type.
     * 
     * @return specClientCode   * Код спецклиента (Передается в случае оплаты товаров и услуг
     * и создания ДП для Московского банка)
     */
    public java.lang.String getSpecClientCode() {
        return specClientCode;
    }


    /**
     * Sets the specClientCode value for this XferInfo_Type.
     * 
     * @param specClientCode   * Код спецклиента (Передается в случае оплаты товаров и услуг
     * и создания ДП для Московского банка)
     */
    public void setSpecClientCode(java.lang.String specClientCode) {
        this.specClientCode = specClientCode;
    }


    /**
     * Gets the curMonth value for this XferInfo_Type.
     * 
     * @return curMonth   * Признак платежа за текущий месяц. Передается в случае создания
     * ДП для оплаты товаров и услуг для Московского банка
     */
    public java.lang.Boolean getCurMonth() {
        return curMonth;
    }


    /**
     * Sets the curMonth value for this XferInfo_Type.
     * 
     * @param curMonth   * Признак платежа за текущий месяц. Передается в случае создания
     * ДП для оплаты товаров и услуг для Московского банка
     */
    public void setCurMonth(java.lang.Boolean curMonth) {
        this.curMonth = curMonth;
    }


    /**
     * Gets the commissionLabel value for this XferInfo_Type.
     * 
     * @return commissionLabel   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.CommissionLabel, полученного из АС ЦПФЛ.Передается
     * в случае создания ДП для оплаты товаров и услуг для Московского банка
     */
    public java.lang.String getCommissionLabel() {
        return commissionLabel;
    }


    /**
     * Sets the commissionLabel value for this XferInfo_Type.
     * 
     * @param commissionLabel   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.CommissionLabel, полученного из АС ЦПФЛ.Передается
     * в случае создания ДП для оплаты товаров и услуг для Московского банка
     */
    public void setCommissionLabel(java.lang.String commissionLabel) {
        this.commissionLabel = commissionLabel;
    }


    /**
     * Gets the serviceCode value for this XferInfo_Type.
     * 
     * @return serviceCode   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.ServiceCode, полученного из АС ЦПФЛ. Передается
     * в случае создания ДП для оплаты товаров и услуг для Московского банка
     */
    public java.lang.String getServiceCode() {
        return serviceCode;
    }


    /**
     * Sets the serviceCode value for this XferInfo_Type.
     * 
     * @param serviceCode   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.ServiceCode, полученного из АС ЦПФЛ. Передается
     * в случае создания ДП для оплаты товаров и услуг для Московского банка
     */
    public void setServiceCode(java.lang.String serviceCode) {
        this.serviceCode = serviceCode;
    }


    /**
     * Gets the serviceKind value for this XferInfo_Type.
     * 
     * @return serviceKind   * Код тарифа 
     * Значение данного элемента должно совпадать со значением элемента preparedPayment_a.tarif.tarifCode,
     * выбранного клиентом из коллекции  тарифов  полученной из АС ЦПФЛ в
     * узле preparedPayment_a.tarif. Передается в случае создания ДП для
     * оплаты товаров и услуг для Московского банка
     */
    public java.lang.String getServiceKind() {
        return serviceKind;
    }


    /**
     * Sets the serviceKind value for this XferInfo_Type.
     * 
     * @param serviceKind   * Код тарифа 
     * Значение данного элемента должно совпадать со значением элемента preparedPayment_a.tarif.tarifCode,
     * выбранного клиентом из коллекции  тарифов  полученной из АС ЦПФЛ в
     * узле preparedPayment_a.tarif. Передается в случае создания ДП для
     * оплаты товаров и услуг для Московского банка
     */
    public void setServiceKind(java.lang.String serviceKind) {
        this.serviceKind = serviceKind;
    }


    /**
     * Gets the tarif value for this XferInfo_Type.
     * 
     * @return tarif   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.tarif.tarifValue, соотвествующего выбранному клиентом
     * preparedPayment_a.tarif.tarifCode из коллекции тарифов preparedPayment_a.tarif
     * полученной из АС ЦПФЛ. Передается в случае создания ДП для 
     * оплаты товаров и услуг для Московского банка
     */
    public java.lang.String getTarif() {
        return tarif;
    }


    /**
     * Sets the tarif value for this XferInfo_Type.
     * 
     * @param tarif   * Значение данного элемента должно совпадать со значением элемента
     * preparedPayment_a.tarif.tarifValue, соотвествующего выбранному клиентом
     * preparedPayment_a.tarif.tarifCode из коллекции тарифов preparedPayment_a.tarif
     * полученной из АС ЦПФЛ. Передается в случае создания ДП для 
     * оплаты товаров и услуг для Московского банка
     */
    public void setTarif(java.lang.String tarif) {
        this.tarif = tarif;
    }


    /**
     * Gets the canChangeTarif value for this XferInfo_Type.
     * 
     * @return canChangeTarif   * Признак разрешения изменения суммы платежа по тарифам. 
     * Значение данного элемента устанавливается клиентом, если значение
     * узла preparedPayment_a.ackClientBankCanChangeSumm  в сообщении из
     * АС ЦПФЛ равно true. Иначе значение элемента должно совпадать со значением
     * элемента preparedPayment_a.ackClientBankCanChangeSumm. Передается
     * в случае создания ДП для 
     * оплаты товаров и услуг для Московского банка
     */
    public java.lang.String getCanChangeTarif() {
        return canChangeTarif;
    }


    /**
     * Sets the canChangeTarif value for this XferInfo_Type.
     * 
     * @param canChangeTarif   * Признак разрешения изменения суммы платежа по тарифам. 
     * Значение данного элемента устанавливается клиентом, если значение
     * узла preparedPayment_a.ackClientBankCanChangeSumm  в сообщении из
     * АС ЦПФЛ равно true. Иначе значение элемента должно совпадать со значением
     * элемента preparedPayment_a.ackClientBankCanChangeSumm. Передается
     * в случае создания ДП для 
     * оплаты товаров и услуг для Московского банка
     */
    public void setCanChangeTarif(java.lang.String canChangeTarif) {
        this.canChangeTarif = canChangeTarif;
    }


    /**
     * Gets the srcLayoutInfo value for this XferInfo_Type.
     * 
     * @return srcLayoutInfo   * Разбивка микроопераций списания
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type getSrcLayoutInfo() {
        return srcLayoutInfo;
    }


    /**
     * Sets the srcLayoutInfo value for this XferInfo_Type.
     * 
     * @param srcLayoutInfo   * Разбивка микроопераций списания
     */
    public void setSrcLayoutInfo(com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type srcLayoutInfo) {
        this.srcLayoutInfo = srcLayoutInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XferInfo_Type)) return false;
        XferInfo_Type other = (XferInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.xferMethod==null && other.getXferMethod()==null) || 
             (this.xferMethod!=null &&
              this.xferMethod.equals(other.getXferMethod()))) &&
            ((this.clientDocumentNumber==null && other.getClientDocumentNumber()==null) || 
             (this.clientDocumentNumber!=null &&
              this.clientDocumentNumber.equals(other.getClientDocumentNumber()))) &&
            ((this.clientDocumentDate==null && other.getClientDocumentDate()==null) || 
             (this.clientDocumentDate!=null &&
              this.clientDocumentDate.equals(other.getClientDocumentDate()))) &&
            ((this.payerInfo==null && other.getPayerInfo()==null) || 
             (this.payerInfo!=null &&
              this.payerInfo.equals(other.getPayerInfo()))) &&
            ((this.taxIdTo==null && other.getTaxIdTo()==null) || 
             (this.taxIdTo!=null &&
              this.taxIdTo.equals(other.getTaxIdTo()))) &&
            ((this.KPPTo==null && other.getKPPTo()==null) || 
             (this.KPPTo!=null &&
              this.KPPTo.equals(other.getKPPTo()))) &&
            ((this.taxIdFrom==null && other.getTaxIdFrom()==null) || 
             (this.taxIdFrom!=null &&
              this.taxIdFrom.equals(other.getTaxIdFrom()))) &&
            ((this.recipientName==null && other.getRecipientName()==null) || 
             (this.recipientName!=null &&
              this.recipientName.equals(other.getRecipientName()))) &&
            ((this.custInfo==null && other.getCustInfo()==null) || 
             (this.custInfo!=null &&
              this.custInfo.equals(other.getCustInfo()))) &&
            ((this.taxColl==null && other.getTaxColl()==null) || 
             (this.taxColl!=null &&
              this.taxColl.equals(other.getTaxColl()))) &&
            ((this.cardAcctIdFrom==null && other.getCardAcctIdFrom()==null) || 
             (this.cardAcctIdFrom!=null &&
              this.cardAcctIdFrom.equals(other.getCardAcctIdFrom()))) &&
            ((this.depAcctIdFrom==null && other.getDepAcctIdFrom()==null) || 
             (this.depAcctIdFrom!=null &&
              this.depAcctIdFrom.equals(other.getDepAcctIdFrom()))) &&
            ((this.depAcctIdTo==null && other.getDepAcctIdTo()==null) || 
             (this.depAcctIdTo!=null &&
              this.depAcctIdTo.equals(other.getDepAcctIdTo()))) &&
            ((this.cardAcctIdTo==null && other.getCardAcctIdTo()==null) || 
             (this.cardAcctIdTo!=null &&
              this.cardAcctIdTo.equals(other.getCardAcctIdTo()))) &&
            ((this.loanAcctIdTo==null && other.getLoanAcctIdTo()==null) || 
             (this.loanAcctIdTo!=null &&
              this.loanAcctIdTo.equals(other.getLoanAcctIdTo()))) &&
            ((this.agreemtInfo==null && other.getAgreemtInfo()==null) || 
             (this.agreemtInfo!=null &&
              this.agreemtInfo.equals(other.getAgreemtInfo()))) &&
            ((this.idSpacing==null && other.getIdSpacing()==null) || 
             (this.idSpacing!=null &&
              this.idSpacing.equals(other.getIdSpacing()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.nonResCode==null && other.getNonResCode()==null) || 
             (this.nonResCode!=null &&
              this.nonResCode.equals(other.getNonResCode()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.depAcctId==null && other.getDepAcctId()==null) || 
             (this.depAcctId!=null &&
              this.depAcctId.equals(other.getDepAcctId()))) &&
            ((this.curAmt1==null && other.getCurAmt1()==null) || 
             (this.curAmt1!=null &&
              this.curAmt1.equals(other.getCurAmt1()))) &&
            ((this.acctCur1==null && other.getAcctCur1()==null) || 
             (this.acctCur1!=null &&
              this.acctCur1.equals(other.getAcctCur1()))) &&
            ((this.curAmt2==null && other.getCurAmt2()==null) || 
             (this.curAmt2!=null &&
              this.curAmt2.equals(other.getCurAmt2()))) &&
            ((this.acctCur2==null && other.getAcctCur2()==null) || 
             (this.acctCur2!=null &&
              this.acctCur2.equals(other.getAcctCur2()))) &&
            ((this.dateCalc==null && other.getDateCalc()==null) || 
             (this.dateCalc!=null &&
              this.dateCalc.equals(other.getDateCalc()))) &&
            ((this.curAmtConv==null && other.getCurAmtConv()==null) || 
             (this.curAmtConv!=null &&
              this.curAmtConv.equals(other.getCurAmtConv()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.parentAuthorizationCode==null && other.getParentAuthorizationCode()==null) || 
             (this.parentAuthorizationCode!=null &&
              this.parentAuthorizationCode.equals(other.getParentAuthorizationCode()))) &&
            ((this.parentTransDtTm==null && other.getParentTransDtTm()==null) || 
             (this.parentTransDtTm!=null &&
              this.parentTransDtTm.equals(other.getParentTransDtTm()))) &&
            ((this.withClose==null && other.getWithClose()==null) || 
             (this.withClose!=null &&
              this.withClose.equals(other.getWithClose()))) &&
            ((this.execute==null && other.getExecute()==null) || 
             (this.execute!=null &&
              this.execute.equals(other.getExecute()))) &&
            ((this.comission==null && other.getComission()==null) || 
             (this.comission!=null &&
              this.comission.equals(other.getComission()))) &&
            ((this.requisites==null && other.getRequisites()==null) || 
             (this.requisites!=null &&
              this.requisites.equals(other.getRequisites()))) &&
            ((this.dataSpec==null && other.getDataSpec()==null) || 
             (this.dataSpec!=null &&
              this.dataSpec.equals(other.getDataSpec()))) &&
            ((this.uniqueNumber==null && other.getUniqueNumber()==null) || 
             (this.uniqueNumber!=null &&
              this.uniqueNumber.equals(other.getUniqueNumber()))) &&
            ((this.specClientCode==null && other.getSpecClientCode()==null) || 
             (this.specClientCode!=null &&
              this.specClientCode.equals(other.getSpecClientCode()))) &&
            ((this.curMonth==null && other.getCurMonth()==null) || 
             (this.curMonth!=null &&
              this.curMonth.equals(other.getCurMonth()))) &&
            ((this.commissionLabel==null && other.getCommissionLabel()==null) || 
             (this.commissionLabel!=null &&
              this.commissionLabel.equals(other.getCommissionLabel()))) &&
            ((this.serviceCode==null && other.getServiceCode()==null) || 
             (this.serviceCode!=null &&
              this.serviceCode.equals(other.getServiceCode()))) &&
            ((this.serviceKind==null && other.getServiceKind()==null) || 
             (this.serviceKind!=null &&
              this.serviceKind.equals(other.getServiceKind()))) &&
            ((this.tarif==null && other.getTarif()==null) || 
             (this.tarif!=null &&
              this.tarif.equals(other.getTarif()))) &&
            ((this.canChangeTarif==null && other.getCanChangeTarif()==null) || 
             (this.canChangeTarif!=null &&
              this.canChangeTarif.equals(other.getCanChangeTarif()))) &&
            ((this.srcLayoutInfo==null && other.getSrcLayoutInfo()==null) || 
             (this.srcLayoutInfo!=null &&
              this.srcLayoutInfo.equals(other.getSrcLayoutInfo())));
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
        if (getXferMethod() != null) {
            _hashCode += getXferMethod().hashCode();
        }
        if (getClientDocumentNumber() != null) {
            _hashCode += getClientDocumentNumber().hashCode();
        }
        if (getClientDocumentDate() != null) {
            _hashCode += getClientDocumentDate().hashCode();
        }
        if (getPayerInfo() != null) {
            _hashCode += getPayerInfo().hashCode();
        }
        if (getTaxIdTo() != null) {
            _hashCode += getTaxIdTo().hashCode();
        }
        if (getKPPTo() != null) {
            _hashCode += getKPPTo().hashCode();
        }
        if (getTaxIdFrom() != null) {
            _hashCode += getTaxIdFrom().hashCode();
        }
        if (getRecipientName() != null) {
            _hashCode += getRecipientName().hashCode();
        }
        if (getCustInfo() != null) {
            _hashCode += getCustInfo().hashCode();
        }
        if (getTaxColl() != null) {
            _hashCode += getTaxColl().hashCode();
        }
        if (getCardAcctIdFrom() != null) {
            _hashCode += getCardAcctIdFrom().hashCode();
        }
        if (getDepAcctIdFrom() != null) {
            _hashCode += getDepAcctIdFrom().hashCode();
        }
        if (getDepAcctIdTo() != null) {
            _hashCode += getDepAcctIdTo().hashCode();
        }
        if (getCardAcctIdTo() != null) {
            _hashCode += getCardAcctIdTo().hashCode();
        }
        if (getLoanAcctIdTo() != null) {
            _hashCode += getLoanAcctIdTo().hashCode();
        }
        if (getAgreemtInfo() != null) {
            _hashCode += getAgreemtInfo().hashCode();
        }
        if (getIdSpacing() != null) {
            _hashCode += getIdSpacing().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getNonResCode() != null) {
            _hashCode += getNonResCode().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getCardAcctId() != null) {
            _hashCode += getCardAcctId().hashCode();
        }
        if (getDepAcctId() != null) {
            _hashCode += getDepAcctId().hashCode();
        }
        if (getCurAmt1() != null) {
            _hashCode += getCurAmt1().hashCode();
        }
        if (getAcctCur1() != null) {
            _hashCode += getAcctCur1().hashCode();
        }
        if (getCurAmt2() != null) {
            _hashCode += getCurAmt2().hashCode();
        }
        if (getAcctCur2() != null) {
            _hashCode += getAcctCur2().hashCode();
        }
        if (getDateCalc() != null) {
            _hashCode += getDateCalc().hashCode();
        }
        if (getCurAmtConv() != null) {
            _hashCode += getCurAmtConv().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getParentAuthorizationCode() != null) {
            _hashCode += getParentAuthorizationCode().hashCode();
        }
        if (getParentTransDtTm() != null) {
            _hashCode += getParentTransDtTm().hashCode();
        }
        if (getWithClose() != null) {
            _hashCode += getWithClose().hashCode();
        }
        if (getExecute() != null) {
            _hashCode += getExecute().hashCode();
        }
        if (getComission() != null) {
            _hashCode += getComission().hashCode();
        }
        if (getRequisites() != null) {
            _hashCode += getRequisites().hashCode();
        }
        if (getDataSpec() != null) {
            _hashCode += getDataSpec().hashCode();
        }
        if (getUniqueNumber() != null) {
            _hashCode += getUniqueNumber().hashCode();
        }
        if (getSpecClientCode() != null) {
            _hashCode += getSpecClientCode().hashCode();
        }
        if (getCurMonth() != null) {
            _hashCode += getCurMonth().hashCode();
        }
        if (getCommissionLabel() != null) {
            _hashCode += getCommissionLabel().hashCode();
        }
        if (getServiceCode() != null) {
            _hashCode += getServiceCode().hashCode();
        }
        if (getServiceKind() != null) {
            _hashCode += getServiceKind().hashCode();
        }
        if (getTarif() != null) {
            _hashCode += getTarif().hashCode();
        }
        if (getCanChangeTarif() != null) {
            _hashCode += getCanChangeTarif().hashCode();
        }
        if (getSrcLayoutInfo() != null) {
            _hashCode += getSrcLayoutInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XferInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xferMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDocumentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientDocumentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDocumentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientDocumentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payerInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayerInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferInfo_Type>PayerInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KPPTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "KPPTo"));
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
        elemField.setFieldName("recipientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxColl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxColl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxColl_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctIdFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctIdFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctIdFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctIdFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanAcctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSpacing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdSpacing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purpose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Purpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nonResCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NonResCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateCalc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateCalc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmtConv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtConv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtConv_Type"));
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
        elemField.setFieldName("parentAuthorizationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ParentAuthorizationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentTransDtTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ParentTransDtTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withClose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WithClose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("execute");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Execute"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comission");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Comission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requisites");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisites"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DataSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uniqueNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UniqueNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specClientCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SpecClientCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commissionLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CommissionLabel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceKind");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceKind"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tarif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Tarif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canChangeTarif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CanChangeTarif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcLayoutInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo_Type"));
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
