<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                exclude-result-prefixes="xalan mask au mu pu">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="loanInfo" select="document(concat(concat('loanCardOffer.xml?offer=',loan), concat('&amp;changeLimit=',changeLimit)))/entity-list/entity"/>
        <xsl:variable name="isCreditCardOfficeAllowed" select="pu:impliesOperation('CreditCardOfficeOperation', 'CreditCardOfficeService')"/>

        <initialData>
            <form>LoanCardOffer</form>
            <LoanCardOffer>
                <loan>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">loan</xsl:with-param>
                        <xsl:with-param name="title">Идентификатор заявки</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="loan"/>
                    </xsl:call-template>
                </loan>

                <offerId>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">offerId</xsl:with-param>
                        <xsl:with-param name="title">Идентификатор предложения</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="offerId"/>
                    </xsl:call-template>
                </offerId>

                <xsl:variable name="offerType" select="$loanInfo/field[@name = 'offerType']"/>
                <xsl:if test="$offerType = 2">
                    <changeLimit>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">changeLimit</xsl:with-param>
                            <xsl:with-param name="title">Предложение на изменение лимита</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="changeLimit"/>
                        </xsl:call-template>
                    </changeLimit>
                </xsl:if>

                <xsl:variable name="idWay" select="$loanInfo/field[@name = 'idWay']"/>
                <idWay>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">idWay</xsl:with-param>
                        <xsl:with-param name="title">Техническая информация</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$idWay"/>
                    </xsl:call-template>
                </idWay>

                <tb>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">tb</xsl:with-param>
                        <xsl:with-param name="title">ТБ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="$isCreditCardOfficeAllowed"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value">
                            <xsl:choose>
                                <xsl:when test="$isCreditCardOfficeAllowed and $offerType != 2">
                                    <xsl:value-of select="tb"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="$loanInfo/field[@name = 'tb']"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </tb>

                <osb>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">osb</xsl:with-param>
                        <xsl:with-param name="title">ОСБ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="$isCreditCardOfficeAllowed"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value">
                            <xsl:choose>
                                <xsl:when test="$isCreditCardOfficeAllowed and $offerType != 2">
                                    <xsl:value-of select="osb"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="$loanInfo/field[@name = 'osb']"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </osb>

                <vsp>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">vsp</xsl:with-param>
                        <xsl:with-param name="title">ВСП</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="$isCreditCardOfficeAllowed"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value">
                            <xsl:choose>
                                <xsl:when test="$isCreditCardOfficeAllowed and $offerType != 2">
                                    <xsl:value-of select="vsp"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="$loanInfo/field[@name = 'vsp']"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </vsp>

                <xsl:variable name="passportNumber" select="$loanInfo/field[@name = 'seriaAndNumber']"/>
                <passportNumber>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">passportNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер паспорта клиента</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$passportNumber"/>
                    </xsl:call-template>
                </passportNumber>


                <offerType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">offerType</xsl:with-param>
                        <xsl:with-param name="title">Типы предложений на предодобреную кредитную карту</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$offerType"/>
                    </xsl:call-template>
                </offerType>

                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>

                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>


                <xsl:variable name="creditCard">
                    <xsl:choose>
                        <xsl:when test="$offerType = 2">
                            <xsl:value-of select="concat($loanInfo/field[@name = 'cardName'] ,' ', $loanInfo/field[@name = 'cardNumber'])"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$loanInfo/field[@name = 'cardName']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <creditCard>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditCard</xsl:with-param>
                        <xsl:with-param name="title">Кредитная карта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$creditCard"/>
                    </xsl:call-template>
                </creditCard>

                <xsl:if test="$offerType != 2">
                    <xsl:variable name="interestRate" select="$loanInfo/field[@name = 'interestRate']"/>
                    <interestRate>
                        <xsl:call-template name="numberField">
                            <xsl:with-param name="name">interestRate</xsl:with-param>
                            <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$interestRate"/>
                        </xsl:call-template>
                    </interestRate>

                    <xsl:variable name="first-year-service" select="$loanInfo/field[@name = 'firstYearPaymentDecimal']"/>
                    <firstYearPayment>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">first-year-service</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание первый год</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$first-year-service"/>
                        </xsl:call-template>
                    </firstYearPayment>

                    <xsl:variable name="first-year-service-currency" select="$loanInfo/field[@name = 'firstYearPaymentCurrency']"/>
                    <firstYearPaymentCurrency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">first-year-service-currency</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание первый год. Валюта</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$first-year-service-currency"/>
                        </xsl:call-template>
                    </firstYearPaymentCurrency>

                    <xsl:variable name="next-year-service" select="$loanInfo/field[@name = 'nextYearPaymentDecimal']"/>
                    <nextYearPayment>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">next-year-service</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание последующие годы.</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$next-year-service"/>
                        </xsl:call-template>
                    </nextYearPayment>

                    <xsl:variable name="next-year-service-currency" select="$loanInfo/field[@name = 'nextYearPaymentCurrency']"/>
                    <nextYearPaymentCurrency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">next-year-service-currency</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание последующие годы.Валюта</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$next-year-service-currency"/>
                        </xsl:call-template>
                    </nextYearPaymentCurrency>
                </xsl:if>

                <xsl:choose>
                    <xsl:when test="$offerType != 2">
                        <xsl:variable name="limit"
                                      select="document(concat(concat('cardAmountStep.xml?loan=', $loanInfo/field[@name = 'conditionId']), concat('&amp;amount=', $loanInfo/field[@name = 'amount'])))"/>

                        <xsl:variable name="amountLimit">
                            <xsl:for-each select="xalan:distinct($limit/entity-list/entity)">
                                <xsl:element name="entity">
                                    <xsl:attribute name="key"><xsl:value-of select="field[@name='decimal']/text()"/></xsl:attribute>
                                    <xsl:value-of select="field[@name='decimal']/text()"/>
                                </xsl:element>
                            </xsl:for-each>
                        </xsl:variable>

                        <amount>
                            <xsl:call-template name="listField">
                                <xsl:with-param name="name">amount</xsl:with-param>
                                <xsl:with-param name="title">Кредитный лимит</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="listValues" select="xalan:nodeset($amountLimit)/node()"/>
                                <xsl:with-param name="value" select="amount"/>
                            </xsl:call-template>
                        </amount>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="amount" select="$loanInfo/field[@name = 'amount']"/>
                        <amount>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">amount</xsl:with-param>
                                <xsl:with-param name="title">Кредитный лимит</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="mu:getFormatAmountWithNoCents($amount,'.')"/>
                            </xsl:call-template>
                        </amount>
                    </xsl:otherwise>
                </xsl:choose>

                <xsl:variable name="currency" select="$loanInfo/field[@name = 'currency']"/>
                <currency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currency</xsl:with-param>
                        <xsl:with-param name="title">Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$currency"/>
                    </xsl:call-template>
                </currency>

                <xsl:variable name="person" select="document('currentPersonData.xml')/entity-list/entity"/>
                <xsl:variable name="surName" select="substring($person/field[@name = 'surName'], 1, 1)"/>
                <surName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">surName</xsl:with-param>
                        <xsl:with-param name="title">Фамилия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$surName"/>
                    </xsl:call-template>
                </surName>

                <xsl:variable name="firstName" select="$person/field[@name = 'firstName']"/>
                <firstName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">firstName</xsl:with-param>
                        <xsl:with-param name="title">Имя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$firstName"/>
                    </xsl:call-template>
                </firstName>

                <xsl:variable name="patrName" select="$person/field[@name = 'patrName']"/>
                <patrName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">patrName</xsl:with-param>
                        <xsl:with-param name="title">Отчество</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$patrName"/>
                    </xsl:call-template>
                </patrName>

                <xsl:if test="$offerType != 2">
                    <place>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">credit-card-office</xsl:with-param>
                            <xsl:with-param name="title">Место получения карты</xsl:with-param>
                            <xsl:with-param name="required" select="$isCreditCardOfficeAllowed"/>
                            <xsl:with-param name="editable" select="$isCreditCardOfficeAllowed"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value">
                                <xsl:choose>
                                    <xsl:when test="$isCreditCardOfficeAllowed">
                                        <xsl:value-of select="credit-card-office"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        В подразделении Сбербанка России, по месту  ведения счета  Вашей карты Сбербанка России
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </place>

                    <duration>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">duration</xsl:with-param>
                            <xsl:with-param name="title">Срок получения карты</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value">В течение  5-10 рабочих дней</xsl:with-param>
                        </xsl:call-template>
                    </duration>
                </xsl:if>

            </LoanCardOffer>

        </initialData>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <form>LoanCardOffer</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <LoanCardOfferDocument>

                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>

                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>

                <offerType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">offerType</xsl:with-param>
                        <xsl:with-param name="title">Типы предложений на предодобреную кредитную карту</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="offerType"/>
                    </xsl:call-template>
                </offerType>
                <creditCard>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditCard</xsl:with-param>
                        <xsl:with-param name="title">Кредитная карта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="creditCard"/>
                    </xsl:call-template>
                </creditCard>

                <xsl:if test="offerType != 2">
                    <interestRate>
                        <xsl:call-template name="numberField">
                            <xsl:with-param name="name">interestRate</xsl:with-param>
                            <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="interestRate"/>
                        </xsl:call-template>
                    </interestRate>

                    <firstYearPayment>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">first-year-service</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание первый год</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="first-year-service"/>
                        </xsl:call-template>
                    </firstYearPayment>

                    <firstYearPaymentCurrency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">first-year-service-currency</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание первый год. Валюта</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="first-year-service-currency"/>
                        </xsl:call-template>
                    </firstYearPaymentCurrency>

                    <nextYearPayment>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">next-year-service</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание последующие годы.</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="next-year-service"/>
                        </xsl:call-template>
                    </nextYearPayment>

                    <nextYearPaymentCurrency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">next-year-service-currency</xsl:with-param>
                            <xsl:with-param name="title">Годовое обслуживание последующие годы.Валюта</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="next-year-service-currency"/>
                        </xsl:call-template>
                    </nextYearPaymentCurrency>
                </xsl:if>

                <amount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">Кредитный лимит</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="mu:getFormatAmountWithNoCents(amount,'.')"/>
                    </xsl:call-template>
                </amount>

                <currency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currency</xsl:with-param>
                        <xsl:with-param name="title">Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="currency"/>
                    </xsl:call-template>
                </currency>

                <xsl:variable name="person" select="document('currentPersonData.xml')/entity-list/entity"/>
                <xsl:variable name="surName" select="substring($person/field[@name = 'surName'], 1, 1)"/>
                <surName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">surName</xsl:with-param>
                        <xsl:with-param name="title">Фамилия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$surName"/>
                    </xsl:call-template>
                </surName>

                <xsl:variable name="firstName" select="$person/field[@name = 'firstName']"/>
                <firstName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">firstName</xsl:with-param>
                        <xsl:with-param name="title">Имя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$firstName"/>
                    </xsl:call-template>
                </firstName>

                <xsl:variable name="patrName" select="$person/field[@name = 'patrName']"/>
                <patrName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">patrName</xsl:with-param>
                        <xsl:with-param name="title">Отчество</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$patrName"/>
                    </xsl:call-template>
                </patrName>

                <xsl:if test="offerType != 2">
                    <place>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">place</xsl:with-param>
                            <xsl:with-param name="title">Место получения карты</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value">
                                <xsl:choose>
                                    <xsl:when test="string-length(credit-card-office)>0">
                                        <xsl:value-of select="credit-card-office"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        В подразделении Сбербанка России, по месту  ведения счета  Вашей карты Сбербанка России
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </place>

                    <duration>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">duration</xsl:with-param>
                            <xsl:with-param name="title">Срок получения карты</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value">В течение  5-10 рабочих дней</xsl:with-param>
                        </xsl:call-template>
                    </duration>
                </xsl:if>
            </LoanCardOfferDocument>
        </document>

    </xsl:template>

</xsl:stylesheet>