<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                exclude-result-prefixes="xalan mask pu au ph">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/> <!-- поддержка отображения комисий из ЦОД -->
    <!--Тарифный план клиента-->
    <xsl:variable name="tarifPlanCodeType">
        <xsl:value-of select="ph:getActivePersonTarifPlanCode()"/>
    </xsl:variable>
    <!--Показывать ли стандартный курс для тарифного плана клиента-->
    <xsl:variable name="needShowStandartRate">
        <xsl:value-of select="ph:needShowStandartRate($tarifPlanCodeType)"/>
    </xsl:variable>
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
        <xsl:variable name="activeDebitAccounts" select="document('active-rur-debit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeDebitCards" select="document('active-rur-not-credit-cards-with-corrected-offices.xml')/entity-list/*"/>

        <initialData>
            <form>IMAOpeningClaim</form>
            <IMAOpeningClaim>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>
                <imaId>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">imaId</xsl:with-param>
                        <xsl:with-param name="title">Идентификатор продукта ОМС</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="imaId"/>
                    </xsl:call-template>
                </imaId>
                <imaName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">buyIMAProduct</xsl:with-param>
                        <xsl:with-param name="title">Металл</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="buyIMAProduct"/>
                    </xsl:call-template>
                </imaName>
                <defaultLocaleImaName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">defaultLocaleImaName</xsl:with-param>
                        <xsl:with-param name="title">Название металла в базовой локали</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="defaultLocaleImaName"/>
                    </xsl:call-template>
                </defaultLocaleImaName>
                <imaType>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">IMAType</xsl:with-param>
                        <xsl:with-param name="title">Вид продукта ОМС</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="IMAType"/>
                    </xsl:call-template>
                </imaType>
                <imaSubType>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">IMASubType</xsl:with-param>
                        <xsl:with-param name="title">Подвид продукта ОМС</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="IMASubType"/>
                    </xsl:call-template>
                </imaSubType>
                <openingDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">openingDate</xsl:with-param>
                        <xsl:with-param name="title">Дата открытия</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="openingDate"/>
                    </xsl:call-template>
                </openingDate>
                <buyCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">toResourceCurrency</xsl:with-param>
                        <xsl:with-param name="title">Код металла</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="buyCurrency"/>
                    </xsl:call-template>
                </buyCurrency>
                <buyAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">buyAmount</xsl:with-param>
                        <xsl:with-param name="title">Масса</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="buyAmount"/>
                    </xsl:call-template>
                </buyAmount>

                <xsl:if test="string-length(course) > 0">
                    <xsl:choose>
                        <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                        <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                            <!--Поле льготный курс-->
                            <course>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">course</xsl:with-param>
                                    <xsl:with-param name="title">Льготный курс покупки</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="course"/>
                                </xsl:call-template>
                            </course>

                            <!--Поле обычный курс-->
                            <standartCourse>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">standartCourse</xsl:with-param>
                                    <xsl:with-param name="title">Обычный курс покупки</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="standartCourse"/>
                                </xsl:call-template>
                            </standartCourse>

                            <!--Моя выгода-->
                            <xsl:if test="standartCourse > course">
                                <gain>
                                    <xsl:call-template name="moneyField">
                                        <xsl:with-param name="name">gain</xsl:with-param>
                                        <xsl:with-param name="title">Моя выгода</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="standartCourse - course"/>
                                    </xsl:call-template>
                                </gain>
                            </xsl:if>
                        </xsl:when>
                        <!--В противном случае -->
                        <xsl:otherwise>
                            <course>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">course</xsl:with-param>
                                    <xsl:with-param name="title">Курс покупки</xsl:with-param>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="course"/>
                                </xsl:call-template>
                            </course>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Счет списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="linkId" select="fromResource"/>
                        <xsl:with-param name="activeAccounts" select="$activeDebitAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeDebitCards"/>
                    </xsl:call-template>
                </fromResource>
                <sellCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">sellCurrency</xsl:with-param>
                        <xsl:with-param name="title">Валюта продажи</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="sellCurrency"/>
                    </xsl:call-template>
                </sellCurrency>
                <sellAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">sellAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="sellAmount"/>
                    </xsl:call-template>
                </sellAmount>
                <exactAmount>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">exactAmount</xsl:with-param>
                        <xsl:with-param name="title">Признак, обозначающий какое из полей суммы заполнил пользователь</xsl:with-param>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="exactAmount"/>
                    </xsl:call-template>
                </exactAmount>

                <xsl:call-template name="offices">
                    <xsl:with-param name="activeAccounts" select="$activeDebitAccounts"/>
                    <xsl:with-param name="activeCards" select="$activeDebitCards"/>
                </xsl:call-template>

                <officeName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">officeName</xsl:with-param>
                        <xsl:with-param name="title">Название офиса</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="officeName"/>
                    </xsl:call-template>
                </officeName>
                <officeAddress>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">officeAddress</xsl:with-param>
                        <xsl:with-param name="title">Адрес офиса</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="officeAddress"/>
                    </xsl:call-template>
                </officeAddress>
                <tb>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">officeRegion</xsl:with-param>
                        <xsl:with-param name="title">ТБ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="officeRegion"/>
                    </xsl:call-template>
                </tb>
                <osb>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">officeBranch</xsl:with-param>
                        <xsl:with-param name="title">ОСБ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="officeBranch"/>
                    </xsl:call-template>
                </osb>
                <vsp>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">office</xsl:with-param>
                        <xsl:with-param name="title">ВСП</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="office"/>
                    </xsl:call-template>
                </vsp>
            </IMAOpeningClaim>
        </initialData>
    </xsl:template>

    <xsl:template name="offices">
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>

        <offices>
            <xsl:if test="pu:impliesService('IMAOpeningFromAccountClaim')">
                <xsl:if test="string-length($activeAccounts)>0">
                    <xsl:for-each select="$activeAccounts">
                        <office>
                            <code><xsl:value-of select="field[@name='code']/text()"/></code>
                            <name><xsl:value-of select="./field[@name='officeName']"/></name>
                            <address><xsl:value-of select="./field[@name='officeAddress']"/></address>
                            <tb><xsl:value-of select="./field[@name='officeRegionCode']"/></tb>
                            <osb><xsl:value-of select="./field[@name='officeBranchCode']"/></osb>
                            <vsp><xsl:value-of select="./field[@name='officeOfficeCode']"/></vsp>
                            <isIma><xsl:value-of select="string(./field[@name='isImaOpening'] = '1')"/></isIma>
                            <parentSynchKey><xsl:value-of select="./field[@name='officeSynchKey']"/></parentSynchKey>
                        </office>
                    </xsl:for-each>
                </xsl:if>
            </xsl:if>
            <xsl:if test="pu:impliesService('IMAOpeningFromCardClaim')">
                <xsl:if test="string-length($activeCards)>0">
                    <xsl:for-each select="$activeCards">
                        <xsl:if test="field[@name='isImaOpening'] != '0' or field[@name='officeSynchKey'] != ''">
                            <office>
                                <code><xsl:value-of select="field[@name='code']/text()"/></code>
                                <name><xsl:value-of select="./field[@name='officeName']"/></name>
                                <address><xsl:value-of select="./field[@name='officeAddress']"/></address>
                                <tb><xsl:value-of select="./field[@name='officeRegionCode']"/></tb>
                                <osb><xsl:value-of select="./field[@name='officeBranchCode']"/></osb>
                                <vsp><xsl:value-of select="./field[@name='officeOfficeCode']"/></vsp>
                                <isIma><xsl:value-of select="string(./field[@name='isImaOpening'] = '1')"/></isIma>
                                <parentSynchKey><xsl:value-of select="./field[@name='officeSynchKey']"/></parentSynchKey>
                            </office>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>
            </xsl:if>
        </offices>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>IMAOpeningClaim</form>

            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <IMAOpeningClaimDocument>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>
                <xsl:if test="toAccountSelect != ''">
                    <toAccount>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">toAccountSelect</xsl:with-param>
                            <xsl:with-param name="title">Открыт ОМС</xsl:with-param>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="toAccountSelect"/>
                        </xsl:call-template>
                    </toAccount>
                </xsl:if>
                <openingDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">openingDate</xsl:with-param>
                        <xsl:with-param name="title">Дата открытия</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="openingDate"/>
                    </xsl:call-template>
                </openingDate>
                <buyCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">buyIMAProduct</xsl:with-param>
                        <xsl:with-param name="title">Металл</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="buyIMAProduct"/>
                    </xsl:call-template>
                </buyCurrency>
                <xsl:if test="string-length(buyAmount) > 0">
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Масса</xsl:with-param>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="buyAmount"/>
                        </xsl:call-template>
                    </buyAmount>
                </xsl:if>
                <xsl:if test="string-length(course) > 0">
                    <xsl:choose>
                        <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                        <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                            <!--Поле льготный курс-->
                            <course>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">course</xsl:with-param>
                                    <xsl:with-param name="title">Льготный курс покупки</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="course"/>
                                </xsl:call-template>
                            </course>

                            <!--Поле обычный курс-->
                            <standartCourse>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">standartCourse</xsl:with-param>
                                    <xsl:with-param name="title">Обычный курс покупки</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="standartCourse"/>
                                </xsl:call-template>
                            </standartCourse>

                            <!--Моя выгода-->
                            <xsl:if test="standartCourse > course">
                                <gain>
                                    <xsl:call-template name="moneyField">
                                        <xsl:with-param name="name">gain</xsl:with-param>
                                        <xsl:with-param name="title">Моя выгода</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="standartCourse - course"/>
                                    </xsl:call-template>
                                </gain>
                            </xsl:if>
                        </xsl:when>
                        <!--В противном случае -->
                        <xsl:otherwise>
                            <course>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">course</xsl:with-param>
                                    <xsl:with-param name="title">Курс покупки</xsl:with-param>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="course"/>
                                </xsl:call-template>
                            </course>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
                <xsl:variable name="displayedValue">
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.AccountLink'">
                            <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Счет списания</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="singleViewValue" select="fromResourceCode"/>
                        <xsl:with-param name="displayedValue" select="$displayedValue"/>
                    </xsl:call-template>
                </fromResource>
                <xsl:if test="string-length(sellAmount) > 0">
                    <sellAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма</xsl:with-param>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmount"/>
                        </xsl:call-template>
                    </sellAmount>
                </xsl:if>
                <xsl:choose>
                    <xsl:when test="$postConfirmCommission and number($mobileApiVersion) >= 7.0">
                        <xsl:variable name="operations" select="document('writeDownOperations.xml')/Operations/Operation[./Name != 'Частичная выдача' and ./Name != 'Закрытие счета']"/>  <!-- отображаемые микрооперации списания -->
                        <xsl:call-template name="writeDownOperations">
                            <xsl:with-param name="operations" select="$operations"/>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>

                <officeName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">officeName</xsl:with-param>
                        <xsl:with-param name="title">Название места открытия</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="officeName"/>
                    </xsl:call-template>
                </officeName>
                <officeAddress>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">officeAddress</xsl:with-param>
                        <xsl:with-param name="title">Адрес места открытия</xsl:with-param>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="officeAddress"/>
                    </xsl:call-template>
                </officeAddress>
            </IMAOpeningClaimDocument>
        </document>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>
        <xsl:param name="linkId"/>
        <xsl:param name="singleViewValue"/>
        <xsl:param name="displayedValue"/>
        <xsl:variable name="value">
            <resourceType>
                <xsl:choose>
                    <xsl:when test="string-length($singleViewValue)=0">
                        <xsl:variable name="showAccounts" select="pu:impliesService('IMAOpeningFromAccountClaim') and string-length($activeAccounts)>0"/>
                        <xsl:variable name="showCards" select="pu:impliesService('IMAOpeningFromCardClaim') and string-length($activeCards)>0"/>
                        <xsl:if test="$showAccounts or $showCards">
                            <availableValues>
                                <xsl:if test="$showAccounts">
                                    <xsl:for-each select="$activeAccounts">
                                        <valueItem>
                                            <xsl:variable name="id" select="field[@name='code']/text()"/>
                                            <value><xsl:value-of select="$id"/></value>
                                            <selected><xsl:value-of select="string($linkId=$id)"/></selected>
                                            <displayedValue>
                                                <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                            </displayedValue>
                                            <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                                <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                            </xsl:if>
                                            <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                                <currency><xsl:value-of select="toResourceCurrency"/></currency>
                                            </xsl:if>
                                        </valueItem>
                                    </xsl:for-each>
                                </xsl:if>
                                <xsl:if test="$showCards">
                                    <xsl:for-each select="$activeCards">
                                        <xsl:if test="field[@name='isImaOpening'] != '0' or field[@name='officeSynchKey'] != ''">
                                            <valueItem>
                                                <xsl:variable name="id" select="field[@name='code']/text()"/>
                                                <value><xsl:value-of select="$id"/></value>
                                                <selected><xsl:value-of select="string($linkId=$id)"/></selected>
                                                <displayedValue>
                                                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                                </displayedValue>
                                                <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                                    <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                                </xsl:if>
                                                <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                                    <currency><xsl:value-of select="toResourceCurrency"/></currency>
                                                </xsl:if>
                                            </valueItem>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:if>
                            </availableValues>
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <availableValues>
                            <valueItem>
                                <value><xsl:value-of select="$singleViewValue"/></value>
                                <selected>true</selected>
                                <xsl:if test="string-length($displayedValue)>0">
                                    <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                                </xsl:if>
                                <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                    <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                </xsl:if>
                                <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                    <currency><xsl:value-of select="toResourceCurrency"/></currency>
                                </xsl:if>
                            </valueItem>
                        </availableValues>
                    </xsl:otherwise>
                </xsl:choose>
            </resourceType>
        </xsl:variable>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag" select="$value"/>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>