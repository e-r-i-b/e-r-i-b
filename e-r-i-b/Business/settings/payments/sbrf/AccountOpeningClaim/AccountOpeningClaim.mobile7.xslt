<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:dph="java://com.rssl.phizic.business.deposits.DepositProductHelper"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:c="http://www.w3.org/1999/XSL/Transform"
                xmlns:sh="java://com.rssl.phizic.config.SettingsHelper"
                exclude-result-prefixes="xalan mu mask au dph pu ph c sh">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId"/>
    <xsl:param name="documentStatus"/>
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
    <xsl:variable name="tariffPlanCode" select="ph:getPersonTariffPlan()"/>
    <xsl:variable name="isUseCasNsiDictionaries" select="sh:isUseCasNsiDictionaries()"/>

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
        <initialData>
            <form>AccountOpeningClaim</form>
            <AccountOpeningClaim>
                <xsl:choose>
                    <xsl:when test="$isUseCasNsiDictionaries">
                        <xsl:call-template name="initialData">
                            <xsl:with-param name="fullDepositDescriptions" select="document(concat( 'deposit-entity-description.xml?depositType=', depositType, '&amp;depositGroup=', depositGroup, '&amp;segment=', segment) )/product/data"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="initialData">
                            <xsl:with-param name="fullDepositDescriptions" select="document(concat( 'deposit-products.xml?depositId=', depositType ) )/product/data"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </AccountOpeningClaim>
        </initialData>
    </xsl:template>

    <xsl:template name="initialData">
        <xsl:param name="fullDepositDescriptions"/>

        <xsl:variable name="groupToShow" select="depositGroup"/>

        <xsl:call-template name="allElements">
            <xsl:with-param name="groupToShow" select="$groupToShow"/>
            <xsl:with-param name="fullDepositDescriptions" select="$fullDepositDescriptions"/>
        </xsl:call-template>

    </xsl:template>

    <!--Отсортировать подвиды с учетом кода группы, если это возможно-->
    <xsl:template name="allElements">
        <xsl:param name="groupToShow" select="''"/>
        <xsl:param name="fullDepositDescriptions"/>

        <xsl:choose>
            <xsl:when test="string-length($groupToShow) > 0">
                <xsl:variable name="groupToShowNew">
                    <xsl:choose>
                        <xsl:when test="$isUseCasNsiDictionaries and $groupToShow = -22">
                            <xsl:value-of select="0"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$groupToShow"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:call-template name="groupFilteredElements">
                    <xsl:with-param name="depositDescriptions" select="$fullDepositDescriptions/options/element[group/groupCode = $groupToShowNew]"/>
                    <xsl:with-param name="depositCode" select="$groupToShowNew"/>
                    <xsl:with-param name="depositName" select="$fullDepositDescriptions/name"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="groupFilteredElements">
                    <xsl:with-param name="depositDescriptions" select="$fullDepositDescriptions/options/element"/>
                    <xsl:with-param name="depositCode" select="$groupToShow"/>
                    <xsl:with-param name="depositName" select="$fullDepositDescriptions/name"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="groupFilteredElements">
        <xsl:param name="depositDescriptions"/>
        <xsl:param name="depositCode" select="''"/>
        <xsl:param name="depositName"/>

        <xsl:variable name="activeAccounts" select="document('active-debit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeWithTBLoginedCards" select="document('active-with-tblogined-cards.xml')/entity-list/*"/>

        <xsl:variable name="notPromoElements" select="$depositDescriptions[segmentCode = 0 or segmentCode = 1]"/>

        <xsl:variable name="clientTariff">
            <xsl:choose>
                <xsl:when test="$tariffPlanCode=0 or count($notPromoElements[tariffPlanCode=$tariffPlanCode]) > 0">
                    <xsl:value-of select="$tariffPlanCode"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="0"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="openDateDay" select="substring-before(openDate, '.')"/>
        <xsl:variable name="openDateMonth" select="substring-before(substring-after(openDate, '.'), '.')"/>
        <xsl:variable name="openDateYear" select="substring-after(substring-after(openDate, '.'), '.')"/>
        <xsl:variable name="docDate" select="concat($openDateYear, $openDateMonth, $openDateDay)"/>

        <xsl:variable name="elements" select="$notPromoElements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>

        <!--Подвиды вкладов, в которых учитывается ТП клиента и зависимость от ТП-->
        <xsl:variable name="elementsByTariffDependence"
                      select="$elements[(count(tariffDependence) = 0) or (tariffDependence/tariff/tariffCode = $tariffPlanCode and translate(tariffDependence/tariff/tariffDateBegin, '.', '')&lt;=translate($docDate, '.', '') and translate(tariffDependence/tariff/tariffDateEnd, '.', '')&gt;translate($docDate, '.', ''))]"/>

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

        <xsl:variable name="depositActualName">
            <xsl:choose>
                <xsl:when test="$depositCode = ''">
                    <xsl:value-of select="$depositName"/>
                </xsl:when>
                <xsl:when test="$depositCode = '-22'">
                    <xsl:value-of select="$elements/subTypeName"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$elements/group/groupName"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <depositName>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">depositName</xsl:with-param>
                <xsl:with-param name="title">Открыть вклад</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="$depositActualName"/>
            </xsl:call-template>
        </depositName>

        <openDate>
            <xsl:call-template name="dateField">
                <xsl:with-param name="name">openDate</xsl:with-param>
                <xsl:with-param name="title">Дата открытия</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="openDate"/>
            </xsl:call-template>
        </openDate>

        <xsl:variable name="currencies">
            <xsl:for-each select="xalan:distinct($elementsByTariffDependence/currencyCode)">
                <xsl:element name="entity">
                    <xsl:attribute name="key"><xsl:value-of select="./text()"/></xsl:attribute>
                    <xsl:value-of select="mu:getCurrencySign(./text())"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:variable>

        <toResourceCurrency>
            <xsl:call-template name="listField">
                <xsl:with-param name="name">toResourceCurrency</xsl:with-param>
                <xsl:with-param name="title">Валюта</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="listValues" select="xalan:nodeset($currencies)/node()"/>
                <xsl:with-param name="value" select="toResourceCurrency"/>
            </xsl:call-template>
        </toResourceCurrency>

        <needInitialFee>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">needInitialFee</xsl:with-param>
                <xsl:with-param name="title">Требуется начальный взнос</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="needInitialFee"/>
            </xsl:call-template>
        </needInitialFee>

        <withMinimumBalance>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">withMinimumBalance</xsl:with-param>
                <xsl:with-param name="title">Есть ли у вклада неснижаемый остаток</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="withMinimumBalance"/>
            </xsl:call-template>
        </withMinimumBalance>

        <isPension>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">isPension</xsl:with-param>
                <xsl:with-param name="title">Является ли вклад пенсионным</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value">
                    <xsl:variable name="pensionElements" select="$elementsByTariffDependence[segmentCode = 1]"/>
                    <xsl:variable name="pensionElements_param" select="$pensionElements[group/groupParams/pensionRate = 'true']"/>

                    <xsl:choose>
                        <xsl:when test="isPension = 'true'">
                            <xsl:choose>
                                <xsl:when test="($depositCode = '' or $depositCode = '-22' or $depositCode = '0') and count($pensionElements) &gt; 0 ">
                                    <xsl:value-of select="'true'"/>
                                </xsl:when>
                                <xsl:when test="($depositCode != '' and $depositCode != '-22' and $depositCode != '0') and count($pensionElements_param) &gt; 0 ">
                                    <xsl:value-of select="'true'"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="'false'"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'false'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </isPension>

        <buyAmount>
            <xsl:call-template name="moneyField">
                <xsl:with-param name="name">buyAmount</xsl:with-param>
                <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="needInitialFee = 'true'"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value">
                    <xsl:choose>
                        <xsl:when test="needInitialFee = 'true'">
                            <xsl:value-of select="buyAmount"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'0'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </buyAmount>

        <exactAmount>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">exactAmount</xsl:with-param>
                <xsl:with-param name="title">Признак, обозначающий какое из полей суммы заполнил пользователь</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="needInitialFee = 'true'"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value">
                    <xsl:choose>
                        <xsl:when test="needInitialFee = 'true'">
                            <xsl:value-of select="exactAmount"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'destination-field-exact'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </exactAmount>

        <xsl:if test="needInitialFee = 'true'">
            <minDepositBalance>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">minDepositBalance</xsl:with-param>
                    <xsl:with-param name="title">Неснижаемый остаток</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="withMinimumBalance = 'true'"/>
                    <xsl:with-param name="value" select="minDepositBalance"/>
                </xsl:call-template>
            </minDepositBalance>

            <fromResource>
                <xsl:call-template name="resourceField">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="title">Счет списания</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                </xsl:call-template>
            </fromResource>

            <xsl:if test="string-length(course) > 0">
                <xsl:choose>
                    <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                    <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                        <!--Поле льготный курс-->
                        <course>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">course</xsl:with-param>
                                <xsl:with-param name="title">Льготный курс конверсии</xsl:with-param>
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
                                <xsl:with-param name="title">Обычный курс конверсии</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="standartCourse"/>
                            </xsl:call-template>
                        </standartCourse>

                        <!--Моя выгода-->
                        <gain>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">gain</xsl:with-param>
                                <xsl:with-param name="title">Моя выгода</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="(standartCourse - course)*sellAmount"/>
                            </xsl:call-template>
                        </gain>
                    </xsl:when>
                    <!--В противном случае -->
                    <xsl:otherwise>
                        <course>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">course</xsl:with-param>
                                <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="course"/>
                            </xsl:call-template>
                        </course>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>

            <sellAmount>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">sellAmount</xsl:with-param>
                    <xsl:with-param name="title">Сумма списания</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="sellAmount"/>
                </xsl:call-template>
            </sellAmount>
            <period>
                <termType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">termType</xsl:with-param>
                        <xsl:with-param name="title">Тип срока</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="'period'"/>
                    </xsl:call-template>
                </termType>
                <periodDays>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">periodDays</xsl:with-param>
                        <xsl:with-param name="title">Количество дней</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="periodDays"/>
                    </xsl:call-template>
                </periodDays>
                <periodMonths>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">periodMonths</xsl:with-param>
                        <xsl:with-param name="title">Количество месяцев</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="periodMonths"/>
                    </xsl:call-template>
                </periodMonths>
                <periodYears>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">periodYears</xsl:with-param>
                        <xsl:with-param name="title">Количество лет</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="periodYears"/>
                    </xsl:call-template>
                </periodYears>
            </period>
            <closingDate>
                <xsl:call-template name="dateField">
                    <xsl:with-param name="name">closingDate</xsl:with-param>
                    <xsl:with-param name="title">Дата закрытия</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="false()"/>
                    <xsl:with-param name="value" select="closingDate"/>
                </xsl:call-template>
            </closingDate>
            <interestRate>
                <xsl:call-template name="numberField">
                    <xsl:with-param name="name">interestRate</xsl:with-param>
                    <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="interestRate"/>
                </xsl:call-template>
            </interestRate>

        </xsl:if>

        <depositId>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">depositId</xsl:with-param>
                <xsl:with-param name="title">Идентификатор депозитного продукта</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="depositId"/>
            </xsl:call-template>
        </depositId>

        <depositType>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">depositType</xsl:with-param>
                <xsl:with-param name="title">Вид вклада</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="depositType"/>
            </xsl:call-template>
        </depositType>

        <depositSubType>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">depositSubType</xsl:with-param>
                <xsl:with-param name="title">Подвид вклада</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="depositSubType"/>
            </xsl:call-template>
        </depositSubType>

        <minAdditionalFee>
            <xsl:call-template name="moneyField">
                <xsl:with-param name="name">minAdditionalFee</xsl:with-param>
                <xsl:with-param name="title">Минимальный размер дополнительного взноса</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="minAdditionalFee"/>
            </xsl:call-template>
        </minAdditionalFee>

        <xsl:variable name="isAvailableCapitalization">
            <xsl:choose>
                <xsl:when test="$isUseCasNsiDictionaries">
                    <xsl:value-of select="dph:isAvailableCapitalizationByType(depositType)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="dph:isAvailableCapitalization(depositId)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <!--Есть ли право на "Изменение порядка уплаты процентов"-->
        <xsl:variable name="isAvailablePercentPaySchemaChange" select="pu:impliesService('PercentPaySchemaChange')"/>
        <!--Доступна капитализвция по вкладу и есть право-->
        <xsl:if test="$isAvailableCapitalization and $isAvailablePercentPaySchemaChange and needInitialFee = 'true'">
            <percentTransferSource>
                <xsl:call-template name="percentTransferSourceTypeField">
                    <xsl:with-param name="name">percentTransferSourceRadio</xsl:with-param>
                    <xsl:with-param name="title">Вариант перечисления процентов</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="cardsForPercent" select="$activeWithTBLoginedCards"/>
                </xsl:call-template>
            </percentTransferSource>

            <c:if test="count($activeWithTBLoginedCards)>0">
                <percentTransferCardSource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">percentTransferCardSource</xsl:with-param>
                        <xsl:with-param name="title">Номер карты для перечисления процентов</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="activeAccounts" select="''"/>
                        <xsl:with-param name="activeCards" select="''"/>
                        <xsl:with-param name="cardsForPercent" select="$activeWithTBLoginedCards"/>
                    </xsl:call-template>
                </percentTransferCardSource>
            </c:if>
        </xsl:if>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <form>AccountOpeningClaim</form>

            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <AccountOpeningClaimDocument>

                <depositSubType>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">depositSubType</xsl:with-param>
                        <xsl:with-param name="title">Подвид вклада</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="depositSubType"/>
                    </xsl:call-template>
                </depositSubType>

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

                <depositName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">depositName</xsl:with-param>
                        <xsl:with-param name="title">Открыть вклад</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="depositName"/>
                    </xsl:call-template>
                </depositName>

                <openDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">openDate</xsl:with-param>
                        <xsl:with-param name="title">Дата открытия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="openDate"/>
                    </xsl:call-template>
                </openDate>

                <closingDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">closingDate</xsl:with-param>
                        <xsl:with-param name="title">Дата закрытия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="needInitialFee = 'true'"/>
                        <xsl:with-param name="value" select="closingDate"/>
                    </xsl:call-template>
                </closingDate>

                <toResourceCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">toResourceCurrency</xsl:with-param>
                        <xsl:with-param name="title">Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="toResourceCurrency"/>
                    </xsl:call-template>
                </toResourceCurrency>

                <needInitialFee>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">needInitialFee</xsl:with-param>
                        <xsl:with-param name="title">Требуется начальный взнос</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="needInitialFee"/>
                    </xsl:call-template>
                </needInitialFee>

                <withMinimumBalance>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">withMinimumBalance</xsl:with-param>
                        <xsl:with-param name="title">Есть ли у вклада неснижаемый остаток</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="withMinimumBalance"/>
                    </xsl:call-template>
                </withMinimumBalance>

                <xsl:if test="string-length(minDepositBalance) > 0">
                    <minDepositBalance>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">minDepositBalance</xsl:with-param>
                            <xsl:with-param name="title">Неснижаемый остаток</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="withMinimumBalance = 'true'"/>
                            <xsl:with-param name="value" select="minDepositBalance"/>
                        </xsl:call-template>
                    </minDepositBalance>
                </xsl:if>

                <xsl:if test="string-length(fromResource) > 0">
                    <xsl:variable name="fromDisplayedValue">
                        <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                    </xsl:variable>
                    <fromResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Счет списания</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="selectedResource" select="fromCodeAccountSelect"/>
                            <xsl:with-param name="displayedValue" select="$fromDisplayedValue"/>
                        </xsl:call-template>
                    </fromResource>
                </xsl:if>

                <xsl:if test="string-length(buyAmount) > 0">
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
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
                                    <xsl:with-param name="title">Льготный курс конверсии</xsl:with-param>
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
                                    <xsl:with-param name="title">Обычный курс конверсии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="standartCourse"/>
                                </xsl:call-template>
                            </standartCourse>

                            <!--Моя выгода-->
                            <gain>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">gain</xsl:with-param>
                                    <xsl:with-param name="title">Моя выгода</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value">
                                        <xsl:choose>
                                            <xsl:when test="fromResourceCurrency = 'RUB'">
                                                <xsl:call-template name="abs">
                                                    <xsl:with-param name="input" select="(1 div course - 1 div standartCourse)*sellAmount" />
                                                </xsl:call-template>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:call-template name="abs">
                                                    <xsl:with-param name="input" select="(course - standartCourse)*sellAmount" />
                                                </xsl:call-template>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </gain>
                        </xsl:when>
                        <!--В противном случае -->
                        <xsl:otherwise>
                            <course>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">course</xsl:with-param>
                                    <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="course"/>
                                </xsl:call-template>
                            </course>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>

                <xsl:if test="string-length(sellAmount) > 0">
                    <sellAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
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

                <xsl:if test="string-length(exactAmount) > 0">
                    <exactAmount>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">exactAmount</xsl:with-param>
                            <xsl:with-param name="title">Признак, обозначающий какое из полей суммы заполнил пользователь</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="exactAmount"/>
                        </xsl:call-template>
                    </exactAmount>
                </xsl:if>

                <xsl:if test="string-length(interestRate) > 0">
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
                </xsl:if>

                <xsl:if test="string-length(minAdditionalFee) > 0">
                    <minAdditionalFee>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">minAdditionalFee</xsl:with-param>
                            <xsl:with-param name="title">Минимальный размер дополнительного взноса</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="minAdditionalFee"/>
                        </xsl:call-template>
                    </minAdditionalFee>
                </xsl:if>

                <xsl:if test="string-length(percentTransferSourceRadio) > 0">
                    <percentTransferSource>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">percentTransferSourceRadio</xsl:with-param>
                            <xsl:with-param name="title">Вариант перечисления процентов</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="percentTransferSourceRadio"/>
                        </xsl:call-template>
                    </percentTransferSource>

                    <xsl:if test="percentTransferSourceRadio='card' and string-length(percentTransferCardNumber) > 0 and string-length(percentTransferCardSource) > 0">
                        <xsl:variable name="fromDisplayedValue">
                            <xsl:value-of select="mask:getCutCardNumber(percentTransferCardNumber)"/>
                        </xsl:variable>
                        <percentTransferCardSource>
                            <xsl:call-template name="resourceField">
                                <xsl:with-param name="name">percentTransferCardSource</xsl:with-param>
                                <xsl:with-param name="title">Номер карты для перечисления процентов</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="selectedResource" select="percentTransferCardSource"/>
                                <xsl:with-param name="displayedValue" select="$fromDisplayedValue"/>
                            </xsl:call-template>
                        </percentTransferCardSource>
                    </xsl:if>
                </xsl:if>

                <xsl:variable name="depositTariffPlanCode" select="depositTariffPlanCode"/>
                <xsl:if test="(string-length($depositTariffPlanCode)>0)">
                    <needTariffAgreement>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">needTariffAgreement</xsl:with-param>
                            <xsl:with-param name="title">Техническое поле, сигнализирующее о применении льготных процентных ставок и необходимости показать дополнительное соглашение</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="($depositTariffPlanCode != 0)"/>
                        </xsl:call-template>
                    </needTariffAgreement>
                </xsl:if>

            </AccountOpeningClaimDocument>
        </document>

    </xsl:template>

    <xsl:template name="percentTransferSourceTypeField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="cardsForPercent"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <xsl:variable name="hasCards" select="count($cardsForPercent)>0"/>

                    <availableValues>
                        <valueItem>
                            <value>account</value>
                            <title>account</title>
                            <selected><xsl:value-of select="string(not($hasCards))"/></selected>
                        </valueItem>
                        <!--Есть ли карты для перечисления процентов-->
                        <xsl:if test="$hasCards">
                             <valueItem>
                                 <value>card</value>
                                 <title>card</title>
                                 <selected>true</selected>
                             </valueItem>
                         </xsl:if>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
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
        <xsl:param name="cardsForPercent"/>
        <xsl:param name="linkId"/>
        <xsl:param name="selectedResource"/>
        <xsl:param name="displayedValue"/>
        <xsl:variable name="value">
            <resourceType>
                <availableValues>
                    <xsl:if test="$cardsForPercent != ''">
                        <xsl:call-template name="availableCardForPercentValue">
                            <xsl:with-param name="resource" select="$cardsForPercent"/>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="$activeAccounts != ''">
                        <xsl:call-template name="availableValue">
                            <xsl:with-param name="resource" select="$activeAccounts"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="type">account</xsl:with-param>
                            <xsl:with-param name="name" select="$name"/>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="$activeCards != ''">
                        <xsl:call-template name="availableValue">
                            <xsl:with-param name="resource" select="$activeCards"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="type">card</xsl:with-param>
                            <xsl:with-param name="name" select="$name"/>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="string-length($selectedResource)>0">
                        <valueItem>
                            <value><xsl:value-of select="$selectedResource"/></value>
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
                    </xsl:if>
                </availableValues>
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

    <xsl:template name="availableValue">
        <xsl:param name="resource"/>
        <xsl:param name="linkId"/>
        <xsl:param name="type"/>
        <xsl:param name="name"/>
        <xsl:if test="count($resource)>0">
            <xsl:for-each select="$resource">
                <valueItem>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <value><xsl:value-of select="$id"/></value>
                    <selected><xsl:value-of select="string($linkId=$id)"/></selected>
                    <displayedValue>
                        <xsl:choose>
                            <xsl:when test="$type = 'card'">
                                <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:otherwise>
                        </xsl:choose>
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
    </xsl:template>

    <xsl:template name="availableCardForPercentValue">
        <xsl:param name="resource"/>
        <xsl:if test="count($resource)>0">
            <xsl:for-each select="$resource">
                <valueItem>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <value><xsl:value-of select="$id"/></value>
                    <xsl:choose>
                        <xsl:when test="position()=1">
                            <selected>true</selected>
                        </xsl:when>
                        <xsl:otherwise>
                            <selected>false</selected>
                        </xsl:otherwise>
                    </xsl:choose>
                    <displayedValue>
                        <xsl:value-of select="mask:getCutCardNumber(./@key)"/>
                    </displayedValue>
                </valueItem>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>