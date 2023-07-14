<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:dph="java://com.rssl.phizic.business.deposits.DepositProductHelper"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:mu="java://com.rssl.phizic.business.util.MoneyUtil"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:clh="java://com.rssl.phizic.business.resources.external.CardLinkHelper"
                xmlns:sh="java://com.rssl.phizic.config.SettingsHelper"
                xmlns:lch="java://com.rssl.phizic.business.loanclaim.LoanClaimHelper"
        >
    <xsl:import href="commonFieldTypes.template.xslt"/>
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="isTemplate" select="'isTemplate'"/>
	<xsl:param name="personAvailable" select="true()"/>
    <xsl:param name="isAjax"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>
    <!--Тарифный план клиента-->
    <xsl:variable name="tarifPlanCodeType">
        <xsl:value-of select="ph:getActivePersonTarifPlanCode()"/>
    </xsl:variable>
    <!--Показывать ли обычный курс для тарифного плана клиента-->
    <xsl:variable name="needShowStandartRate">
        <xsl:value-of select="ph:needShowStandartRate($tarifPlanCodeType)"/>
    </xsl:variable>
    <xsl:variable name="tariffPlanCode" select="ph:getPersonTariffPlan()"/>
    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">

        <script type="text/javascript">

            function isEmpty(value)
            {
                if (value == null || value == "")
                    return true;
                return false;
            }

            <!--Отображение или скрытие информации о льготном курсе-->
            function showOrHidePremierWarning(rateShowMsg)
            {
            <xsl:variable name="tarifPlanConfigMessage">
                <xsl:value-of select="ph:getTarifPlanConfigMeessage($tarifPlanCodeType)"/>
            </xsl:variable>
                var SBR_PRIVELEGED_WARNING_MESSAGE = "<xsl:value-of select="$tarifPlanConfigMessage"/>";
            <xsl:choose>
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    if ( String(rateShowMsg).toLowerCase() == 'true' &amp;&amp;
                         !(SBR_PRIVELEGED_WARNING_MESSAGE == null || SBR_PRIVELEGED_WARNING_MESSAGE == '') )
                    {   <!--сообщение отображается если:-->
                    <!--1. валюта операции "USD" или "EUR"; -->
                    <!--2. курс отличается от курса с обычным тарифным планом "UNKNOWN";-->
                    <!--3. нужно отображать сообщение (настраивается в админке)-->
                    addMessage(SBR_PRIVELEGED_WARNING_MESSAGE);
                    }
                    <!--Скрываем сообщение-->
                    else
                    {
                        removeInformation(SBR_PRIVELEGED_WARNING_MESSAGE,'warnings');
                    }
                </xsl:when>
                <!--В противном случае -->
                <xsl:otherwise>
                    <!--Для тарифного плана "Сбербанк Премьер" и "Сбербанк Первый" отображаем информационные сообщения-->
                    <xsl:if test="$tarifPlanCodeType = '1' or $tarifPlanCodeType = '3'">
                       var courseRowPaymentsLegend = ensureElement("courseRow-payments-legend");
                        if (courseRowPaymentsLegend == null)
                            return;
                        if ( String(rateShowMsg).toLowerCase() == 'true' &amp;&amp;
                             !(SBR_PRIVELEGED_WARNING_MESSAGE == null || SBR_PRIVELEGED_WARNING_MESSAGE == '') )
                        {   <!--сообщение отображается если:-->
                        <!--1. валюта операции "USD" или "EUR"; -->
                        <!--2. курс отличается от курса с обычным тарифным планом "UNKNOWN";-->
                        <!--3. тарифный план клиента "PREMIER" или "FIRST"-->
                        addMessage(SBR_PRIVELEGED_WARNING_MESSAGE);
                            courseRowPaymentsLegend.style.display = "";
                        }
                        <!--Скрываем сообщение-->
                        else
                        {
                            removeInformation(SBR_PRIVELEGED_WARNING_MESSAGE,'warnings');
                            courseRowPaymentsLegend.style.display = "none";
                        }
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
            }
        </script>

        <xsl:choose>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>

        <xsl:variable name="defaultFunctions" select="'showHideOperationTypeRow();fillCurrency();hideOrShowCourse();'"/>

        <select id="{$name}" name="{$name}">
            <xsl:attribute name="onchange">
                <xsl:value-of select="$defaultFunctions"/>
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="$isTemplate = 'true'">
                    <option value="">Не задан</option>
                </xsl:when>
                <xsl:when test="count($activeCards) = 0 and count($activeAccounts) = 0">
                    <option value="">Нет доступных счетов и карт</option>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <xsl:choose>
                        <xsl:when test="count($activeCards) = 0">
                            <option value="">Выберите счет списания</option>
                        </xsl:when>
                        <xsl:when test="count($activeAccounts) = 0">
                            <option value="">Выберите карту списания</option>
                        </xsl:when>
                        <xsl:otherwise>
                            <option value="">Выберите счет/карту списания</option>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
            </xsl:choose>
            <xsl:for-each select="$activeAccounts">
                <option>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber=./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="au:getShortAccountNumber(./@key)"/>&nbsp;
                    [<xsl:value-of select="./field[@name='name']"/>]
                    <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>
            <xsl:call-template name="resourcesCards">
                <xsl:with-param name="activeCards" select="$activeCards"/>
                <xsl:with-param name="name" select="$name"/>
                <xsl:with-param name="accountNumber" select="$accountNumber"/>
                <xsl:with-param name="linkId" select="$linkId"/>
            </xsl:call-template>
        </select>
    </xsl:template>

    <xsl:template name="resourcesCards">
        <xsl:param name="activeCards"/>
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>

        <xsl:for-each select="$activeCards">
            <xsl:if test="./field[@name='isMain'] = 'true' or ./field[@name='additionalCardType'] = 'CLIENTTOCLIENT' or $name = 'toResource'">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <option>
                    <xsl:if test="./field[@name='isMain'] = 'false' and $name = 'toResource'">
                        <xsl:attribute name="class">hideable</xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/>
                    &nbsp;
                    [
                    <xsl:value-of select="./field[@name='name']"/>
                    ]
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of
                                select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                    </xsl:if>
                    &nbsp;
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="resourcesCardsForPercent">
        <xsl:param name="name"/>
        <xsl:param name="cardNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeCards"/>

        <select id="{$name}" name="{$name}">
            <xsl:choose>
                <xsl:when test="count($activeCards) = 0">
                    <xsl:attribute name="disabled"/>
                    <option value="">Нет доступных карт</option>
                </xsl:when>
                <xsl:otherwise>
                    <option value="">Выберите карту</option>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:call-template name="resourcesCards">
                <xsl:with-param name="activeCards" select="$activeCards"/>
                <xsl:with-param name="name" select="$name"/>
                <xsl:with-param name="accountNumber" select="$cardNumber"/>
                <xsl:with-param name="linkId" select="$linkId"/>
            </xsl:call-template>
        </select>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="isUseCasNsiDictionaries" select="sh:isUseCasNsiDictionaries()"/>
        <xsl:choose>
            <xsl:when test="$isUseCasNsiDictionaries">
                <xsl:call-template name="loadForm">
                    <xsl:with-param name="depositDescriptions" select="document(concat( 'deposit-entity-description.xml?depositType=', depositType, '&amp;depositGroup=', depositGroup, '&amp;segment=', segment) )/product/data"/>
                    <xsl:with-param name="isUseCasNsiDictionaries" select="$isUseCasNsiDictionaries"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="loadForm">
                    <xsl:with-param name="depositDescriptions" select="document(concat( 'deposit-products.xml?depositId=', depositType ) )/product/data"/>
                    <xsl:with-param name="isUseCasNsiDictionaries" select="$isUseCasNsiDictionaries"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="loadForm">
        <xsl:param name="depositDescriptions"/>
        <xsl:param name="isUseCasNsiDictionaries"/>

        <xsl:variable name="useDragdealerAccountOpening" select="pu:impliesService('UseDragdealerAccountOpening')"/>
        <xsl:variable name="activeAccounts" select="document('active-debit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>

        <xsl:variable name="code" select="depositGroup"/>
        <xsl:variable name="elements" select="$depositDescriptions/options/element[group/groupCode = $code and availToOpen = 'true']"/>

        <xsl:variable name="segment" select="segment"/>
        <xsl:variable name="promoCodes" select="document('client-promo-segment-list.xml')/entity-list/*"/>
        <xsl:variable name="hasPriorPromoSegment" select="$promoCodes[@key = $segment]/field[@name = 'priority']/text() = 1"/>
        <xsl:variable name="promoCodeName" select="$promoCodes[@key = $segment and field[@name = 'priority' and text() = 0]]/field[@name = 'text']/text()"/>

        <!--Доступна ли капитализация для вклада-->
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

        <xsl:variable name="depositName">
            <xsl:choose>
                <xsl:when test="$code = '-22' or $code = '0'">
                    <xsl:value-of select="$elements/subTypeName"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$elements/group/groupName"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <!--Если открывается промо-вклад, то ищем только ставки с этим кодом сегмента-->
        <xsl:variable name="priorPromoElements" select="$elements[segmentCode = $segment and group/groupParams/promoCodeSupported = 'true']"/>

        <!--Если открывается не промовклад (даже если есть промокод, но с приоритетом = 1, учитываем ТП и признак пенсионера)-->
        <xsl:variable name="notPriorPromoElements" select="$elements[(segmentCode = $segment and group/groupParams/promoCodeSupported = 'true') or segmentCode = 1 or segmentCode = 0]"/>
        <xsl:variable name="simpleElements" select="$notPriorPromoElements[segmentCode = 0 or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true')]"/>
        <xsl:variable name="pensionElements" select="$notPriorPromoElements[segmentCode = 1 or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true')]"/>
        <xsl:variable name="pensionElements_param" select="$pensionElements[group/groupParams/pensionRate = 'true']"/>

        <xsl:variable name="clientTariff">
            <xsl:choose>
                <xsl:when test="$tariffPlanCode=0 or count($elements[tariffPlanCode=$tariffPlanCode]) > 0">
                    <xsl:value-of select="$tariffPlanCode"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="0"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
        <input type="hidden" name="depositType" value="{depositType}" id="depositType"/>
        <input type="hidden" name="depositSubType" value="{depositSubType}" id="depositSubType"/>
        <input type="hidden" name="minAdditionalFee" value="{minAdditionalFee}" id="minAdditionalFee"/>
        <input type="hidden" name="needInitialFee" value="{needInitialFee}" id="needInitialFee"/>
        <input type="hidden" name="withMinimumBalance" value="{withMinimumBalance}" id="withMinimumBalance"/>
        <input type="hidden" name="isPension" value="{isPension}" id="isPension"/>
        <input type="hidden" name="depositId" value="{depositId}" id="depositId"/>
        <input type="hidden" name="pfpId" value="{pfpId}" id="pfpId"/>
        <input type="hidden" name="pfpPortfolioId" value="{pfpPortfolioId}" id="pfpPortfolioId"/>
        <input type="hidden" name="fromPersonalFinance" value="{fromPersonalFinance}" id="fromPersonalFinance"/>
        <input type="hidden" name="depositTariffPlanCode" value="{depositTariffPlanCode}" id="depositTariffPlanCode"/>
        <input type="hidden" name="depositGroup" value="{depositGroup}" id="depositGroup"/>
        <input type="hidden" name="segment" value="{segment}" id="segment"/>
        <input type="hidden" name="usePromoRate" value="{usePromoRate}" id="usePromoRate"/>
        <input type="hidden" name="fromExtendedLoanClaim" value="{fromExtendedLoanClaim}" id="fromExtendedLoanClaim"/>

        <script type="text/javascript">
            function updateScrollVal(){
            <xsl:if test="$useDragdealerAccountOpening">
                setScrollVal('scroll', horizDragscroll);
            </xsl:if>
            }
        </script>
        
        <xsl:if test="$hasPriorPromoSegment">
            <div class="premiumDepositProductItem">
                <div class="promoShortDescription"><xsl:value-of select="$promoCodes[@key = $segment]/field[@name = 'shortDescription']/text()"/></div>
                <div class="clear"></div>
                <div class="promoFullDescription"><xsl:value-of select="$promoCodes[@key = $segment]/field[@name = 'fullDescription']/text()"/></div>
            </div>
        </xsl:if>
        
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Открыть вклад:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" value="{depositName}" name="depositName"/>
                <b>&quot;<span id="depositName"></span>&quot;</b><span id="depositPromoSign" class="promoCodeLabel"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата открытия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="openDate" value="{openDate}"/>
                <b><xsl:value-of select="openDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">toResourceCurrency</xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Валюта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <select id="toResourceCurrency" name="toResourceCurrency">
                    <xsl:attribute name="onchange">
                        <xsl:choose>
                            <xsl:when test="needInitialFee = 'true'">
                                <xsl:variable name="defaultFunctions" select="'updateAmounts();updateMinSummBalance();showHideOperationTypeRow();fillCurrency();hideOrShowCourse();'"/>
                                <xsl:choose>
                                    <xsl:when test="$useDragdealerAccountOpening">
                                        <xsl:value-of select="concat($defaultFunctions, 'initFormByProduct(false);')"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="concat($defaultFunctions, 'calculateRate(', withMinimumBalance, ')')"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="'updateCurrencyWithoutInitialFee();'"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                </select>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:if test="needInitialFee = 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">minDepositBalance</xsl:with-param>
                <xsl:with-param name="lineId">minDepositBalanceRow</xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Неснижаемый остаток:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <select id="minDepositBalance" name="minDepositBalance"
                            onchange="javascript:calculateRate({withMinimumBalance});updateScrollVal();">
                    </select>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">fromResource</xsl:with-param>
                <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
                <xsl:with-param name="rowValue">
                        <xsl:call-template name="resources">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                            <xsl:with-param name="linkId" select="fromResource"/>
                            <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                            <xsl:with-param name="activeCards" select="$activeCards"/>
                        </xsl:call-template>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
			<xsl:with-param name="id">buyAmount</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
			<xsl:with-param name="description">
                <xsl:if test="needInitialFee = 'true'">Введите сумму, которую хотите зачислить на открываемый вклад</xsl:if>
            </xsl:with-param>
			<xsl:with-param name="rowName">Сумма зачисления:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="needInitialFee = 'true'">
                        <input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="24"
                               onchange="javascript:buyCurrency();javascript:calculateRate({withMinimumBalance});updateScrollVal();"
                               onkeyup="javascript:buyCurrency();javascript:calculateRate({withMinimumBalance});updateScrollVal();" class="moneyField"/>&nbsp;
                        <span id="buyAmountCurrency"></span>
                        <div class="payments-legend">
                            <xsl:choose>
                                <xsl:when test="withMinimumBalance = 'true'">
                                    не должна быть меньше выбранной суммы неснижаемого остатка
                                </xsl:when>
                                <xsl:otherwise>
                                    не должна быть меньше минимального взноса
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </xsl:when>
                    <xsl:otherwise>
                        <input name="buyAmount" id="buyAmount" type="hidden" value="0"/>
                        <b>0,00 </b>
                        <span id="currencyWithoutInitialFee"></span>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
		</xsl:call-template>

        <xsl:if test="needInitialFee = 'true'">
            <xsl:choose>
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    <!--Поле льготный курс-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">courseRow</xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="description"></xsl:with-param>
                        <xsl:with-param name="rowName">
                            Льготный курс конверсии:
                        </xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" value="{course}" name="course" id="courseValue"/>
                            <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                            <b><span id="course"></span></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <!--Поле обычный курс-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">standartCourseRow</xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="description"></xsl:with-param>
                        <xsl:with-param name="rowName">
                            Обычный курс конверсии:
                        </xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" value="{standartCourse}" name="standartCourse" id="standartCourseValue"/>
                            <span id="standartCourseSpan" class="crossed"></span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <!--Моя выгода-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">courseGainRow</xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="description"></xsl:with-param>
                        <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <span id="courseGainSpan"></span>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
                <!--В противном случае -->
                <xsl:otherwise>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">courseRow</xsl:with-param>
                        <xsl:with-param name="description"></xsl:with-param>
                        <xsl:with-param name="rowName">Курс конверсии:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" value="{course}" name="course" id="courseValue"/>
                            <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                            <span id="course"></span>
                            <xsl:if test="$tarifPlanCodeType = '1'">
                                <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                    <div class="italic">Используется льготный курс покупки/продажи</div>
                                </div>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                <xsl:with-param name="id">sellAmount</xsl:with-param>
                <xsl:with-param name="description">Введите сумму в валюте счета списания, которую хотите зачислить на открываемый вклад</xsl:with-param>
                <xsl:with-param name="rowName">Сумма списания:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24" class="moneyField"
                           onchange="javascript:sellCurrency();javascript:calculateRate({withMinimumBalance});updateScrollVal();" onkeyup="javascript:sellCurrency();"/>&nbsp;
                    <span id="sellAmountCurrency"></span>
                </xsl:with-param>
                <!--Параметр для передачи в шаблон name поля, необходимо для определения, была ли в поле ошибка валидации-->
                <!--Возможно name можно как нить вытащить и из rowValue-->
                <!--<xsl:with-param name="fieldName">amount</xsl:with-param>-->
            </xsl:call-template>
        </xsl:if>

        <script type="text/javascript" src="{$resourceRoot}/scripts/conversionPayment.js"/>
        <script type="text/javascript" src="{$resourceRoot}/scripts/AccountOpening.js"/>
        <script type="text/javascript">
            function checkRates()
            {
                if (<xsl:value-of select="string-length(document(concat('account-card-deposit-currency.xml?depositId=', depositType))/entity-list/entity) = 0"/>)
                {
                    addError('Не удалось получить курсы валют. Операция конверсии временно недоступна.', null, true);
                    return false;
                }
                return true;
            }
        </script>

        <xsl:variable name="activeWithTBLoginedCards" select="document('active-with-tblogined-cards.xml')/entity-list/*"/>
        <!--Есть ли карты для перечисления процентов-->
        <xsl:variable name="cardsListForPercentPayOrderIsEmpty" select="count($activeWithTBLoginedCards) = 0"/>

        <!--Есть ли право на "Изменение порядка уплаты процентов"-->
        <xsl:variable name="isAvailablePercentPaySchemaChange" select="pu:impliesService('PercentPaySchemaChange')"/>
        <!--Доступна капитализвция по вкладу и есть право-->
        <xsl:if test="$isAvailableCapitalization = 'true' and $isAvailablePercentPaySchemaChange and needInitialFee = 'true'">

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">percentPaymentOrderRow</xsl:with-param>
                <xsl:with-param name="id">percentPaymentOrderRow</xsl:with-param>
                <xsl:with-param name="required" select="'true'"/>
                <xsl:with-param name="rowName">
                    <span>Порядок&nbsp;уплаты&nbsp;процентов:</span>
                </xsl:with-param>

                <xsl:with-param name="rowValue">
                    <div>
                        <xsl:if test="$cardsListForPercentPayOrderIsEmpty">
                            <div class="fullWidthRadioButton">
                                <b>
                                   <input type="radio" value="account" name="percentTransferSourceRadio"
                                          onclick="setPercentPaymentOrderToCard(false);">
                                       <xsl:attribute name="checked"/>
                                   </input>
                                   &nbsp;капитализация процентов на счете по вкладу
                                </b>
                            </div>
                        </xsl:if>

                        <div class="fullWidthRadioButton">
                            <b>
                               <input type="radio" value="card" name="percentTransferSourceRadio" id="transferOfInterest"
                                      onclick="setPercentPaymentOrderToCard(true);">
                                    <xsl:choose>
                                        <xsl:when test="$cardsListForPercentPayOrderIsEmpty">
                                            <xsl:attribute name="disabled"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:attribute name="checked"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                               </input>
                               &nbsp;перечисление процентов на счет банковской карты
                            </b>
                        </div>

                        <div>
                            <xsl:call-template name="resourcesCardsForPercent">
                                <xsl:with-param name="activeCards" select="$activeWithTBLoginedCards"/>
                                <xsl:with-param name="name">percentTransferCardSource</xsl:with-param>
                                <xsl:with-param name="cardNumber" select="percentTransferCardSource"/>
                                <xsl:with-param name="linkId" select="percentTransferCardSource"/>
                            </xsl:call-template>
                        </div>

                        <xsl:choose>
                            <xsl:when test="not ($cardsListForPercentPayOrderIsEmpty)">
                                <div name="helpMessageIfCardUsing" class="payments-legend">
                                    Укажите банковскую карту для перечисления процентов по вкладу
                                </div>
                                <div class="fullWidthRadioButton" style="margin-top:1em;">
                                    <b>
                                       <input type="radio" value="account" name="percentTransferSourceRadio"
                                              onclick="setPercentPaymentOrderToCard(false);"/>
                                       &nbsp;капитализация процентов на счете по вкладу
                                    </b>
                                </div>
                            </xsl:when>
                        </xsl:choose>
                    </div>

                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="needInitialFee = 'true' and $useDragdealerAccountOpening">
            <xsl:variable name="interestRateRowLineId">interestRateRow</xsl:variable>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId" select="$interestRateRowLineId"/>
                <xsl:with-param name="id">interestRate</xsl:with-param>
                <xsl:with-param name="required" select="'true'"/>
                <xsl:with-param name="rowName">Ставка:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <script type="text/javascript">
                        var subKindInterestRates =
                        {
                            source   : [],
                            push     : function(item)
                            {
                                var contains = false;

                                this.source.forEach( function(e)
                                {
                                    if (e == item)
                                    {
                                        contains = true;
                                    }
                                })

                                if (!contains)
                                {
                                    this.source.push(item);
                                }
                            },

                            length : function()
                            {
                                return this.source.length;
                            }
                        };

                        <xsl:variable name="depositKinds" select="$elements[availToOpen/text() = 'true']"/>
                        <xsl:if test="count($depositKinds) > 1">
                            <xsl:for-each select="$depositKinds">
                                subKindInterestRates.push('<xsl:value-of select="./interestRate/text()"/>');
                            </xsl:for-each>
                        </xsl:if>

                        doOnLoad( function()
                        {
                            if (subKindInterestRates.length() > 1)
                            {
                                return;
                            }

                            var interestRateRow = document.getElementById('<xsl:value-of select="$interestRateRowLineId"/>');
                            interestRateRow.style.display = 'none';
                        })
                    </script>

                    <xsl:call-template name="scrollRow">
                        <xsl:with-param name="id">scroll</xsl:with-param>
                        <xsl:with-param name="unit">%</xsl:with-param>
                        <xsl:with-param name="fieldName">interestRate</xsl:with-param>
                        <xsl:with-param name="promoRate"><xsl:value-of select="count($priorPromoElements) &gt; 0"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>



        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">depositPeriodRow</xsl:with-param>
			<xsl:with-param name="id">depositPeriodRow</xsl:with-param>
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Срок вклада:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="needInitialFee = 'true'">
                        <div class="radioButtonLines">
                            <div class="payments-radioButton">
                                <input class="float" type="radio" value="period" onchange="setPeriodRadioButton(true);" name="closingDateRadio" width="20%"/>&nbsp;
                                <label class="radioLabel">на</label>&nbsp;
                            </div>
                            <div>
                                <input onchange="setDepositClosingDate();calculateRate({withMinimumBalance});updateScrollVal();"
                                       id="periodDays" size="5" maxlength="5" value="{periodDays}" name="periodDays"/>&nbsp;дней&nbsp;
                                <input onchange="setDepositClosingDate();calculateRate({withMinimumBalance});updateScrollVal();"
                                       id="periodMonths" size="5" maxlength="3" value="{periodMonths}" name="periodMonths"/>&nbsp;месяцев&nbsp;
                                <input onchange="setDepositClosingDate();calculateRate({withMinimumBalance});updateScrollVal();"
                                       id="periodYears" size="5" maxlength="2" value="{periodYears}" name="periodYears"/>&nbsp;лет&nbsp;
                            </div>
                        </div>
                        <div class="radioButtonLines">
                            <div class="payments-radioButton">
                                <input class="float" type="radio" value="date" onclick="setPeriodRadioButton(false);" name="closingDateRadio"/>&nbsp;
                                <label class="radioLabel">до</label>&nbsp;
                            </div>
                            <div>
                                <input readonly="true" onchange="setDepositPeriod();calculateRate({withMinimumBalance});updateScrollVal();"
                                       size="10" name="closingDate" value="{closingDate}" id="closingDate" class="dot-date-pick"/>
                            </div>
                        </div>
                        <div class="payments-legend">
                            Вклад автоматически пролонгируется после окончания указанного срока
                        </div>
                    </xsl:when>
                    <xsl:otherwise>
                        <b>Бессрочный</b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:if test="string-length($segment) > 0 and not($hasPriorPromoSegment)">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">promoCodeName</xsl:with-param>
                <xsl:with-param name="description"></xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Промокод:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" value="{$promoCodeName}" name="promoCodeName"/>
                    <b><span id="promoCodeName"><xsl:value-of select="$promoCodeName"/></span></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

       <xsl:if test="needInitialFee = 'true' and not($useDragdealerAccountOpening)">
           <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">interestRateRow</xsl:with-param>
                <xsl:with-param name="description"></xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" value="{interestRate}" name="interestRate"/>
                    <span id="interestRateResult"></span>
                    <div class="payments-legend">
                        рассчитывается автоматически
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <script type="text/javascript">

            function init()
            {
                gSegment = '<xsl:value-of select="segment"/>';
                gPriorSegment = <xsl:value-of select="string($hasPriorPromoSegment)"/>;
                gPromoCodeName = '<xsl:value-of select="$promoCodeName"/>';
                if (gPriorSegment)
                {
                    setElement("usePromoRate", 'true');
                }
            <xsl:if test="needInitialFee = 'true'">
                <xsl:for-each select="$activeAccounts">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <xsl:variable name="balance" select="field[@name='amountDecimal']/text()"/>
                    account = new Object();
                    account.balance = '<xsl:value-of select="$balance"/>';
                    account.currencyCode = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    account.accountNumber = '<xsl:value-of select="@key"/>';
                    accounts['<xsl:value-of select="$id"/>'] = account;

                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                </xsl:for-each>

                <xsl:for-each select="$activeCards">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    cardAccounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                        addMessage('Информация по Вашим картам может быть неактуальной.');
                    </xsl:if>
                </xsl:for-each>
                <xsl:for-each select="document(concat('account-card-deposit-currency.xml?depositId=', depositType))/entity-list/entity">
                    rate = new Array();
                    <xsl:for-each select="*">
                        rate['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="./text()"/>';
                    </xsl:for-each>
                    rates['<xsl:value-of select="@key"/>'] = rate;
                </xsl:for-each>
            </xsl:if>

                fillDepositCurrency();
                <xsl:choose>
                    <xsl:when test="needInitialFee = 'true'">
                        showHideOperationTypeRow();
                        hideOrShowCourse();
                        fillCurrency();
                        checkRadioButton('<xsl:value-of select="closingDateRadio"/>');
                        <xsl:if test="$useDragdealerAccountOpening">
                            initFormByProduct(true);
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        updateCurrencyWithoutInitialFee();
                    </xsl:otherwise>
                </xsl:choose>

                <xsl:choose>
                    <xsl:when test="$cardsListForPercentPayOrderIsEmpty">
                        checkPercentTransferSourceRadio('account');
                        setPercentPaymentOrderToCard(false);
                        $('input[type=radio][value=card]').eq(0).attr('disabled','disabled');
                    </xsl:when>
                    <xsl:otherwise>
                        checkPercentTransferSourceRadio('<xsl:value-of select="percentTransferSourceRadio"/>');
                    </xsl:otherwise>
                </xsl:choose>
            }

            var depProduct = new Object();

            function fillDepositCurrency()
            {
                var indexes = new Array();
                var mapCurrencies = new Object();
                setElement("depositType", '<xsl:value-of select="depositType"/>');
                var toResourceCurrency  = getElement('toResourceCurrency');
                var prevCurrencyValue = '<xsl:value-of select="toResourceCurrency"/>';

                $("#depositName").html('<xsl:value-of select="$depositName"/>');
                setElement("depositName", '<xsl:value-of select="$depositName"/>');
                setElement('depositTariffPlanCode', '<xsl:value-of select="$clientTariff"/>');
                <xsl:choose>
                    <!--Если открывается промовклад (т.е. с использованием промо-кода с приоритетом 1)-->
                    <xsl:when test="$hasPriorPromoSegment">
                        setElement("isPension", "false");
                        $("#depositPromoSign").html('промо-вклад');
                        <xsl:call-template name="fillDepositInfo">
                            <xsl:with-param name="elements" select="$priorPromoElements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                        </xsl:call-template>
                    </xsl:when>
                    <!--Если открывается не промовклад, то учитываем ставки с ТП, а также ставки с промокодом (при наличии) без учета ТП-->
                    <xsl:when test="$code = '-22' or $code = '0'">
                        <xsl:choose>
                            <xsl:when test="count($pensionElements) &gt; 0 and isPension = 'true'">
                                setElement("isPension", "true");

                                <xsl:variable name="e_RUB_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'RUB']"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_EUR_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'EUR']"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_USD_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'USD']"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:call-template name="fillDeposit">
                                    <xsl:with-param name="elements"
                                                    select="$notPriorPromoElements[currencyCode = 'RUB' and (segmentCode = $e_RUB_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'EUR' and (segmentCode = $e_EUR_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'USD' and (segmentCode = $e_USD_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))]"/>
                                    <xsl:with-param name="callFunction" select="'fillDepositInfo'"/>
                                    <xsl:with-param name="clientTariff" select="$clientTariff"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                setElement("isPension", "false");
                                <xsl:call-template name="fillDepositInfo">
                                    <xsl:with-param name="elements"
                                                    select="$simpleElements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                            <xsl:when test="count($pensionElements_param) &gt; 0 and isPension = 'true'">
                                setElement("isPension", "true");

                                <xsl:variable name="e_RUB_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'RUB']"/>
                                        <xsl:with-param name="groupParams" select="'true'"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_EUR_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'EUR']"/>
                                        <xsl:with-param name="groupParams" select="'true'"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_USD_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'USD']"/>
                                        <xsl:with-param name="groupParams" select="'true'"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:call-template name="fillDeposit">
                                    <xsl:with-param name="elements"
                                                    select="$notPriorPromoElements[currencyCode = 'RUB' and (segmentCode = $e_RUB_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'EUR' and (segmentCode = $e_EUR_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'USD' and (segmentCode = $e_USD_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))]"/>
                                    <xsl:with-param name="callFunction" select="'fillDepositInfo'"/>
                                    <xsl:with-param name="clientTariff" select="$clientTariff"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                setElement("isPension", "false");
                                <xsl:call-template name="fillDepositInfo">
                                    <xsl:with-param name="elements"
                                                    select="$simpleElements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>

                addCurrenciesOptions(toResourceCurrency, prevCurrencyValue, mapCurrencies);
                <xsl:if test="string-length(toResourceCurrency) = 0">
                    getElement('toResourceCurrency').selectedIndex = 0;
                </xsl:if>
                <xsl:if test="needInitialFee = 'true'">
                updateMinSummBalance();
                calculateRate(<xsl:value-of select="withMinimumBalance"/>);
                updateScrollVal();
                $('.dot-date-pick').addClass('dp-disabled');
                </xsl:if>
            }

            <xsl:if test="needInitialFee = 'true'">
            function updateMinSummBalance()
            {
                var minDepositBalance = getElement('minDepositBalance');
                var prevValue = '<xsl:value-of select="minDepositBalance"/>';
                clearOptions(minDepositBalance);
                var currentCurrencyCode = getElement('toResourceCurrency').value;
                var prev = null;
                if ( depProduct[currentCurrencyCode] != undefined )
                {
                    // Считаем что элементы в структуре отсортированы по сумме
                    for (var i in depProduct[currentCurrencyCode].balances)
                    {
                        if (prevValue == '')
                            prevValue = depProduct[currentCurrencyCode].balances[i].balance;
                        if (prev!=null)
                        {
                            var prevElem = parseInt(depProduct[currentCurrencyCode].balances[prev].balance,10);
                            var currElem = parseInt(depProduct[currentCurrencyCode].balances[i].balance,10);
                            if (currElem &lt;= prevElem)
                            {
                                prev = i;
                                continue;
                            }
                        }
                        var balance = depProduct[currentCurrencyCode].balances[i];
                        addOption (minDepositBalance, balance.balance, balance.balance + ' ' + currencySignMap.get(currentCurrencyCode), balance.balance==prevValue);
                        prev = i;
                    }
                }
                <xsl:if test="withMinimumBalance = 'false'">
                    hideOrShow(ensureElement("minDepositBalanceRow"), true);
                </xsl:if>
            }
            </xsl:if>

            <xsl:if test="not(needInitialFee = 'true')">
            function updateCurrencyWithoutInitialFee()
            {
                var currency = ensureElement('toResourceCurrency').value;
                $("#currencyWithoutInitialFee").html(currencySignMap.get(currency));
                setElement("depositSubType", depProduct[currency].balances['bal0'].subType);
                setElement("minAdditionalFee", depProduct[currency].balances['bal0'].minAdditionalFee);
                setElement("exactAmount", "destination-field-exact");
                setElement("isPension", depProduct[currency].balances['bal0'].segmentCode == 1);
            }
            </xsl:if>

            <xsl:if test="$useDragdealerAccountOpening">
            var depProductByRate = new Object();
            var withMaxPeriod = new Object();
            var withMinBalance = new Object();
            var depositRates = new Array();
            var actualDepositRates = [];

            function fillDepProductByRate(){
                var indexes = new Array();
                depProductByRate = new Object();
                withMaxPeriod = new Object();
                withMinBalance = new Object();
                depositRates = new Array();
                actualDepositRates = [];
                var toResourceCurrency  = getElement('toResourceCurrency');

                <xsl:choose>
                    <xsl:when test="$hasPriorPromoSegment">
                        <xsl:call-template name="fillDepositByRate">
                            <xsl:with-param name="elements" select="$priorPromoElements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="$code = '-22' or $code = '0'">
                        <xsl:choose>
                            <xsl:when test="count($pensionElements) &gt; 0 and isPension = 'true'">
                                <xsl:variable name="e_RUB_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'RUB']"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_EUR_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'EUR']"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_USD_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'USD']"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:call-template name="fillDeposit">
                                    <xsl:with-param name="elements"
                                                    select="$notPriorPromoElements[currencyCode = 'RUB' and (segmentCode = $e_RUB_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'EUR' and (segmentCode = $e_EUR_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'USD' and (segmentCode = $e_USD_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))]"/>
                                    <xsl:with-param name="callFunction" select="'fillDepositByRate'"/>
                                    <xsl:with-param name="clientTariff" select="$clientTariff"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="fillDepositByRate">
                                    <xsl:with-param name="elements"
                                                    select="$simpleElements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                            <xsl:when test="count($pensionElements_param) &gt; 0 and isPension = 'true'">
                                <xsl:variable name="e_RUB_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'RUB']"/>
                                        <xsl:with-param name="groupParams" select="'true'"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_EUR_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'EUR']"/>
                                        <xsl:with-param name="groupParams" select="'true'"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:variable name="e_USD_segment">
                                    <xsl:call-template name="elementSegmentForPension">
                                        <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'USD']"/>
                                        <xsl:with-param name="groupParams" select="'true'"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:call-template name="fillDeposit">
                                    <xsl:with-param name="elements"
                                                    select="$notPriorPromoElements[currencyCode = 'RUB' and (segmentCode = $e_RUB_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'EUR' and (segmentCode = $e_EUR_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                            $notPriorPromoElements[currencyCode = 'USD' and (segmentCode = $e_USD_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))]"/>
                                    <xsl:with-param name="callFunction" select="'fillDepositByRate'"/>
                                    <xsl:with-param name="clientTariff" select="$clientTariff"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="fillDepositByRate">
                                    <xsl:with-param name="elements"
                                                    select="$simpleElements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>


                for (var i = 0; i &lt; actualDepositRates.length; i++)
                {
                    var actualDepositRate = actualDepositRates[i];
                    if (actualDepositRate)
                    {
                        for (var j = 0; j &lt; actualDepositRate.rates.length; j++)
                        {
                            var rate = actualDepositRate.rates[j];
                            if (depositRates.indexOf(rate) &lt; 0)
                                depositRates.push(rate);
                        }
                    }
                }
                depositRates.sort(function(a, b){return a-b});
            }

            function updateAmounts() {
                $('#sellAmount').val('');
                $('#buyAmount').val('');
            }

            function updateFormByDragDealer(interestRate, updateBuyAmount){
                if (updateBuyAmount)
                {
                    $(ensureElement("buyAmount")).setMoneyValue( parseInt(withMinBalance[interestRate].balance));
                    buyCurrency();
                }
                var maxYears = parseInt(withMinBalance[interestRate].fromPeriodYears);
                var maxMonth = parseInt(withMinBalance[interestRate].fromPeriodMonths);
                var maxDays = parseInt(withMinBalance[interestRate].fromPeriodDays);
                setElement('periodDays',maxDays);
                setElement('periodMonths',maxMonth);
                setElement('periodYears',maxYears);
                setDepositClosingDate();
                $('#minDepositBalance').val(withMinBalance[interestRate].balance);
                calculateRate(<xsl:value-of select="withMinimumBalance"/>);

            }

            function initFormByProduct(onLoad)
            {
                fillDepProductByRate();
                var currRate = '<xsl:value-of select="interestRate"/>';
                if (!onLoad || isEmpty(currRate))
                {
                    currRate = depositRates[0];
                }
                var buyAmountVal = '<xsl:value-of select="buyAmount"/>';
                if (onLoad &amp;&amp; !isEmpty(buyAmountVal))
                    updateFormByDragDealer(currRate, false);
                else
                    updateFormByDragDealer(currRate, true);
                updateScroll('scroll', horizDragscroll,parseFloat(currRate),parseFloat(depositRates[0]),parseFloat(depositRates[depositRates.length-1]));
            }
            </xsl:if>

            doOnLoad(init);

        </script>

    </xsl:template>
    <xsl:template match="/form-data" mode="view">

        <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
        <xsl:variable name="hasPriorPromoSegment" select="string-length(segment) > 0 and usePromoRate = 'true' and string-length(promoCodeName) = 0"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="documentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="state!='EXECUTED'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Открыть вклад:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>&quot;<xsl:value-of select="depositName"/>&quot;</b>
                    <xsl:if test="$hasPriorPromoSegment">
                        &nbsp;<span class="promoCodeLabel">промо-вклад</span>
                    </xsl:if>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="state='EXECUTED'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Открыт вклад:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="au:getFormattedAccountNumber(receiverAccountSelect)"/>&nbsp;
                        &quot;<xsl:value-of select="depositName"/>&quot;&nbsp;
                        <xsl:if test="$hasPriorPromoSegment">
                            <span class="promoCodeLabel">промо-вклад</span>&nbsp;
                        </xsl:if>
                        <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/></b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата открытия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" value="{openDate}" name="openDate"/>
                <b><xsl:value-of select="openDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">toResourceCurrency</xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Валюта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                   <b><xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="needInitialFee = 'true' or needInitialFee = ''">
            <xsl:if test="withMinimumBalance = 'true'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">minDepositBalance</xsl:with-param>
                    <xsl:with-param name="lineId">minDepositBalanceRow</xsl:with-param>
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">Неснижаемый остаток:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                         <b><xsl:value-of select="minDepositBalance"/>&nbsp;
                             <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Перевести с:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                            <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                        </xsl:when>
                        <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/></xsl:otherwise>
                    </xsl:choose>
                    &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                    <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:variable name="isConversion" select="string-length(sellAmount)>0"/>

        <xsl:choose>
            <xsl:when test="$postConfirmCommission">
                <xsl:call-template name="transferSumRows">
                    <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
                    <xsl:with-param name="toResourceCurrency" select="buyAmountCurrency"/>
                    <xsl:with-param name="chargeOffAmount" select="sellAmount"/>
                    <xsl:with-param name="destinationAmount" select="buyAmount"/>
                    <xsl:with-param name="documentState" select="state"/>
                    <xsl:with-param name="exactAmount" select="exactAmount"/>
                    <xsl:with-param name="needUseTotalRow" select="'false'"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
                    <xsl:with-param name="id">buyAmount</xsl:with-param>
                    <xsl:with-param name="description">Введите сумму, которую хотите зачислить на открываемый вклад</xsl:with-param>
                    <xsl:with-param name="rowName">
                        <xsl:choose>
                            <xsl:when test="$isConversion">
                                Сумма зачисления:
                            </xsl:when>
                            <xsl:otherwise>
                                Сумма:
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" name="buyAmount"/>
                        <span class="summ">
                            <xsl:value-of select="buyAmount"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                        </span>
                    </xsl:with-param>
                    <xsl:with-param name="fieldName">amount</xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:if test="courseChanged = 'true' and state = 'SAVED'">
            <script type="text/javascript">
                doOnLoad(function()
                {
                    removeError("Курс валюты банка изменился! Пожалуйста, отредактируйте платеж. ");
                <!--Курс конверсии-->
                payInput.fieldError('confirmCourse');
                <!--При редактировании суммы зачисления, пересчитывается сумма списания-->
                <xsl:if test="exactAmount = 'destination-field-exact'">
                        payInput.fieldError('sellAmount');
                        addError("Изменился курс конверсии банка, поэтому пересчитана сумма списания средств со вклада.");
                    </xsl:if>
                <!--При редактировании суммы списания, пересчитывается сумма зачисления-->
                <xsl:if test="exactAmount = 'charge-off-field-exact'">
                        payInput.fieldError('buyAmount');
                        addError("Изменился курс конверсии банка, поэтому пересчитана сумма зачисления средств на вклад.");
                    </xsl:if>
                });
            </script>
        </xsl:if>

        <xsl:if test="$isConversion">
            <xsl:choose>
                <!--Если операция проводится по льготному курсу и отображение стандартного курса разрешено-->
                <xsl:when test="$tarifPlanCodeType != '0' and ($needShowStandartRate = 'true')">
                    <!--Поле льготный курс-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Льготный курс конверсии:</xsl:with-param>
                        <xsl:with-param name="id">confirmCourse</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="hidden" name="confirmCourse"/>
                            <b><xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/></b>
                            &nbsp;
                            <span id="course">
                                <b><xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/></b>
                                &#8594;
                                <b><xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/></b>
                            </span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <!--Поле обычный курс-->
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Обычный курс коверсии:</xsl:with-param>
                        <xsl:with-param name="id">confirmStandartCourse</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <span class="crossed">
                                <xsl:value-of select="format-number(standartCourse, '### ##0,00##', 'sbrf')"/>
                                &nbsp;
                                <span id="standartCourseSpan">
                                    <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                                    &#8594;
                                    <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                                </span>
                            </span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <!--Моя выгода-->
                    <xsl:if test="standartCourse > course">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="id">courseGain</xsl:with-param>
                            <xsl:with-param name="rowName">Моя выгода:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:choose>
                                    <xsl:when test="fromResourceCurrency = 'RUB'">
                                        <xsl:call-template name="abs">
                                            <xsl:with-param name="input" select="mu:roundDestinationAmount((1 div course - 1 div standartCourse) * sellAmount)" />
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="abs">
                                            <xsl:with-param name="input" select="mu:roundDestinationAmount((course - standartCourse) * sellAmount)" />
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                                &nbsp;
                                <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:when>
                <!--В противном случае -->
                <xsl:otherwise>
                    <xsl:if test="$isConversion and string-length(course)>0">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Курс конверсии:</xsl:with-param>
                            <xsl:with-param name="id">confirmCourse</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                 <input type="hidden" name="confirmCourse"/>
                                 <input type="hidden" value="{premierShowMsg}" name="premierShowMsg" id="premierShowMsg"/>
                                 <xsl:value-of select="format-number(course, '### ##0,00##', 'sbrf')"/>&nbsp;
                                 <span id="course">
                                    <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                                    &#8594;
                                    <xsl:value-of select="mf:getCurrencySign(buyAmountCurrency)"/>
                                </span>
                                <xsl:if test="$tarifPlanCodeType = '1'">
                                    <div class="payments-legend" id="courseRow-payments-legend" style="display: none">
                                        <div class="italic">Используется льготный курс покупки/продажи</div>
                                    </div>
                                </xsl:if>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>

        <xsl:if test="$isConversion and not($postConfirmCommission)">

           <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                <xsl:with-param name="id">sellAmount</xsl:with-param>
                <xsl:with-param name="description">Введите сумму в валюте счета списания, которую хотите зачислить на открываемый вклад</xsl:with-param>
                <xsl:with-param name="rowName">Сумма списания:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="sellAmount"/>
                    <span class="summ">
                        <xsl:value-of select="sellAmount"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                    </span>
                </xsl:with-param>
                <xsl:with-param name="fieldName">amount</xsl:with-param>
            </xsl:call-template>

        </xsl:if>

        <!--если вообще выбран порядок уплаты процентов, то отображаем его-->
        <xsl:variable name="percentTransferSourceRadio" select="percentTransferSourceRadio"/>
        <xsl:if test="string-length($percentTransferSourceRadio)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">
                    <span>Порядок&nbsp;уплаты&nbsp;процентов:</span>
                </xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:choose>
                            <xsl:when test="percentTransferSourceRadio='card'">
                                на счет банковской карты&nbsp;
                                <xsl:variable name="percentTransferCardNumber" select="percentTransferCardNumber"/>
                                <xsl:choose>
                                    <xsl:when test="string-length($percentTransferCardNumber)>0">
                                        <xsl:value-of select="clh:getCardInfoByCardNumber($percentTransferCardNumber)"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:variable name="activeWithTBLoginedCards" select="document('active-with-tblogined-cards.xml')/entity-list"/>
                                        <xsl:variable name="percentTransferCardSource" select="percentTransferCardSource"/>
                                        <xsl:variable name="cardNode" select="$activeWithTBLoginedCards/entity[$percentTransferCardSource=./field[@name='code']]"/>
                                        <xsl:if test="$cardNode">
                                            <div>
                                                <xsl:value-of select="mask:getCutCardNumber($cardNode/@key)"/>&nbsp;
                                                [<xsl:value-of select="$cardNode/field[@name='name']"/>]
                                                <xsl:value-of select="mf:getCurrencySign($cardNode/field[@name='currencyCode'])"/>
                                            </div>
                                        </xsl:if>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:when>
                            <xsl:otherwise>
                                капитализация процентов на счете по вкладу
                            </xsl:otherwise>
                        </xsl:choose>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">depositPeriodRow</xsl:with-param>
            <xsl:with-param name="id">depositPeriod</xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Срок вклада:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" value="{closingDate}" name="closingDate"/>
                <xsl:choose>
                    <xsl:when test="needInitialFee = 'true' or needInitialFee = ''">
                        <b><span id="depositPeriod"></span></b><br/>
                        <div class="payments-legend">
                            Вклад автоматически пролонгируется после окончания указанного срока
                        </div>
                    </xsl:when>
                    <xsl:otherwise>
                        <b><span>Бессрочный</span></b><br/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:if test="needInitialFee = 'true' or needInitialFee = ''">
            <xsl:variable name="interestRateResultClass">
                <xsl:if test="string-length(segment) > 0 and usePromoRate = 'true'">
                    <xsl:value-of select="'promoInterestRate'"/>
                </xsl:if>
            </xsl:variable>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">interestRateRow</xsl:with-param>
                <xsl:with-param name="id">interestRate</xsl:with-param>
                <xsl:with-param name="required" select="'true'"/>
                <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="interestRate" value="{interestRate}"/>
                    <span class="{$interestRateResultClass}"><b><xsl:value-of select="interestRate"/>&nbsp;%</b></span>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(promoCodeName) > 0">
           <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">promoCodeName</xsl:with-param>
                <xsl:with-param name="description"></xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Промокод:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="promoCodeName"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Статус документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
               <div id="state">
                    <span onmouseover="showLayer('state','stateDescription');"
                          onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:choose>
                            <xsl:when test="$app = 'PhizIA'">
                                <xsl:call-template name="employeeState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </span>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="hasDepositTariff">
            <xsl:choose>
                <xsl:when test="(string-length(depositTariffPlanCode)>0) and (depositTariffPlanCode != 0)">
                    <xsl:value-of select="'true'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'false'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="acceptDepositCondition" select="'Я согласен с условиями Договора о вкладе'"/>
        <xsl:variable name="reviewDepositTariff" select="'Просмотр дополнительного соглашения'"/>

        <xsl:if test="not($isAjax)">
            <xsl:variable name="isNewAccountLoanIssueMetodAvailable" select="lch:isNewAccountLoanIssueMetodAvailable()"/>
            <xsl:variable name="isCurrentAccountLoanIssueMetodAvailable" select="lch:isCurrentAccountLoanIssueMetodAvailable()"/>
            <xsl:choose>
                <xsl:when test="$isNewAccountLoanIssueMetodAvailable or $isCurrentAccountLoanIssueMetodAvailable">
                <div class="okb-dogovor loanOpenAccount">
                        <div class="okb-dogovor-yellow">
                            <xsl:variable name="onclick">
                                <xsl:choose>
                                    <xsl:when test="not(templateId = '')">
                                        printDepositInfo();
                                    </xsl:when>
                                    <xsl:otherwise>
                                        templateNotFound();
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>

                            <div class="okb-dogovor-top">Открыть условия Договора о вкладе в <a class="okb-dogovor-new-win" onclick="{$onclick}">новом окне</a></div>

                            <xsl:attribute name="onclick">
                                <xsl:value-of select="$onclick"/>
                            </xsl:attribute>

                            <div class="okb-dogovor-text accountOpenText" id="accountOpenText">
                                <!--Текст условий открытия вклада-->
                            </div>

                            <xsl:if test="state='SAVED'">
                                <div id="agreeForAllRow">
                                    <input type="checkbox" id="agreeForAll" name="agreeForAll" value="" class="agreeChbx" disabled="true"/>
                                    <label for="agreeForAll"> <xsl:value-of select="$acceptDepositCondition"/></label>
                                </div>
                            </xsl:if>

                            <xsl:if test="$hasDepositTariff = 'true'">

                                <div class="clientButton leftBtn">
                                    <xsl:variable name="onclick">
                                        printDepositInfo('tariffTerms');
                                    </xsl:variable>
                                    <div class="blueGrayLink">
                                        <xsl:attribute name="onclick">
                                            <xsl:value-of select="$onclick"/>
                                        </xsl:attribute>
                                        <div class="left-corner"></div>

                                        <div class="text">
                                            <span><xsl:value-of select="$reviewDepositTariff"/></span>
                                        </div>

                                        <div class="right-corner"></div>
                                    </div>
                                    <div class="clear"></div>
                                </div>

                                <div class="payments-legend">
                                    Прежде чем устанавливать флажок в блоке «<xsl:value-of select="$acceptDepositCondition"/>», Вам необходимо ознакомиться с приведенными выше условиями вклада, открыв соответствующие ссылки.
                                </div>
                            </xsl:if>
                        </div>
                    </div>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">printLinkRow</xsl:with-param>
                        <xsl:with-param name="id">printLink</xsl:with-param>
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName"/>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                        <xsl:with-param name="rowValue">
                            <div class="okb-dogovor conditionality">
                                <div class="okb-dogovor-yellow">
                                    <xsl:variable name="onclick">
                                        <xsl:choose>
                                            <xsl:when test="not(templateId = '')">
                                                printDepositInfo();
                                            </xsl:when>
                                            <xsl:otherwise>
                                                templateNotFound();
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>

                                    <div class="okb-dogovor-top">Открыть условия Договора о вкладе в <a class="okb-dogovor-new-win" onclick="{$onclick}">новом окне</a></div>

                                    <xsl:attribute name="onclick">
                                        <xsl:value-of select="$onclick"/>
                                    </xsl:attribute>

                                    <div class="okb-dogovor-text accountOpenText" id="accountOpenText">
                                        <!--Текст условий открытия вклада-->
                                    </div>

                                    <xsl:if test="state='SAVED'">
                                        <div id="agreeForAllRow">
                                            <input type="checkbox" id="agreeForAll" name="agreeForAll" value="" class="agreeChbx" disabled="true"/>
                                            <label for="agreeForAll"> <xsl:value-of select="$acceptDepositCondition"/></label>
                                        </div>
                                    </xsl:if>

                                    <xsl:if test="$hasDepositTariff = 'true'">

                                        <div class="clientButton leftBtn">
                                            <xsl:variable name="onclick">
                                                printDepositInfo('tariffTerms');
                                            </xsl:variable>
                                            <div class="blueGrayLink">
                                                <xsl:attribute name="onclick">
                                                    <xsl:value-of select="$onclick"/>
                                                </xsl:attribute>
                                                <div class="left-corner"></div>

                                                <div class="text">
                                                    <span><xsl:value-of select="$reviewDepositTariff"/></span>
                                                </div>

                                                <div class="right-corner"></div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>

                                        <div class="payments-legend">
                                            Прежде чем устанавливать флажок в блоке «<xsl:value-of select="$acceptDepositCondition"/>», Вам необходимо ознакомиться с приведенными выше условиями вклада, открыв соответствующие ссылки.
                                        </div>
                                    </xsl:if>
                                </div>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>


        </xsl:if>

        <script>

            <xsl:choose>
                <xsl:when test="$hasDepositTariff = 'true'">
                    var AGREEMENT_CONDITION = "Для того чтобы подтвердить заявку, ознакомьтесь с условиями вклада. Если Вы с условиями согласны, то установите флажок в поле «Я согласен с условиями Договора о вкладе» и нажмите на кнопку ";
                </xsl:when>
                <xsl:otherwise>
                    var AGREEMENT_CONDITION = "Для того чтобы подтвердить заявку, ознакомьтесь с условиями вклада. Если Вы с условиями согласны, то установите флажок в поле «<xsl:value-of select="$acceptDepositCondition"/>» и нажмите на кнопку ";
                </xsl:otherwise>
            </xsl:choose>

            var TEMPLATE_NOT_FOUND = 'Для данного вида вклада не найден шаблон договора. Обратитесь за помощью в службу поддержки банка';

            function init()
            {
            <xsl:if test="needInitialFee = 'true' or needInitialFee = ''">
                $("#depositPeriod").html('<xsl:value-of select="periodDays"/>' + ' ' + 'дней' + ' '+ '<xsl:value-of select="periodMonths"/>' + ' ' + 'месяцев' + ' ' + '<xsl:value-of
                    select="periodYears"/>' + ' ' + 'лет' + ' (действует до <xsl:value-of select="closingDate"/>)');
            </xsl:if>
            }

            function checkClientAgreesCondition(buttonName)
            {
                var checked = getElement('agreeForAll').checked;
                var AGREEMENT_MESSAGE = getAgreementMessage(buttonName);
                if (!checked)
                {
                    addMessage(AGREEMENT_MESSAGE);
                    window.scrollTo(0,0);
                }
                else
                    removeMessage(AGREEMENT_MESSAGE);
                return checked;
            }

            <!--Удаляет сообщения по названию кнопки-->
            function removeMessageByButtonName(buttonName)
            {
                var AGREEMENT_MESSAGE = getAgreementMessage(buttonName);
                removeMessage(AGREEMENT_MESSAGE);
            }

            function getAgreementMessage(buttonName)
            {
                if (buttonName!=undefined)
                {
                    return AGREEMENT_CONDITION + '«' + buttonName + '».';
                }
                else
                    return AGREEMENT_CONDITION + '«Подтвердить».';
            }

            function printDepositInfo(termsType)
            {
                var isFromExtendedLoanClaim = '<xsl:value-of select="fromExtendedLoanClaim"/>';

                var params;
                if (isFromExtendedLoanClaim == 'true')
                {
                    var id = $("#accountOpeningClaimId").val();
                    if (id == undefined || id == "")
                        id = getQueryStringParameter('id');
                    params =  'id=' + id;
                }
                else
                    params = 'id=' + getQueryStringParameter('id');
                var termsURL = '<xsl:value-of select="$webRoot"/>/private/deposits/terms.do?' + params;
                if (!isEmpty(termsType))
                {
			        var urlParts = termsURL.split('?');

			        if (urlParts &amp;&amp; urlParts.length > 0)
			        {
			        	var hasEmpty = -1;

			        	for (var i=0; i&lt;urlParts.length; i++)
			        	{
			        		var urlPart = urlParts[i];

			        		if (!urlPart)
			        		{
			        			hasEmpty = i;
			        			break;
			        		}

			        		urlParts[i] = urlPart.replace('#', '');
			        	}

			        	if (hasEmpty > -1)
			        	{
			        		urlParts.splice(hasEmpty, 1);
			        	}

			        	termsURL = urlParts.join('?');
			        }

                    termsURL = termsURL + '&amp;termsType=' + termsType;
                }

                window.open(termsURL, 'depositInfo', 'menubar=1,toolbar=0,scrollbars=1,resizable=1');
                checkDisableAgreeForAll(termsType);
            }

            function templateNotFound()
            {
                removeMessage(TEMPLATE_NOT_FOUND);
                addMessage(TEMPLATE_NOT_FOUND);
            }

            function getDepositInfo()
            {
                var termsURL = '<xsl:value-of select="$webRoot"/>/private/deposits/terms.do';
                var isFromExtendedLoanClaim = '<xsl:value-of select="fromExtendedLoanClaim"/>';

                var params;
                if (isFromExtendedLoanClaim == 'true')
                {
                    var id = $("#accountOpeningClaimId").val();
                    if (id == undefined || id == "")
                        id = getQueryStringParameter('id');
                    params =  'id=' + id + '&amp;loadInDiv=true';
                }
                else
                    params = 'id=' + getQueryStringParameter('id') + '&amp;loadInDiv=true';
                ajaxQuery(params, termsURL, function (data)
                        {
                            ensureElement('accountOpenText').innerHTML = data;
                            window.userReadFirstConfirmMessage = true;
                            window.userReadSecondConfirmMessage = false;
                            checkDisableAgreeForAll(null);
                        },
                    null, false);
            }

            <!--Настройка доступности чекбокса подтверждения просмотра всех условий вклада-->
            function checkDisableAgreeForAll(termsType)
            {
                var agreeForAll = ensureElement('agreeForAll');
                <xsl:choose>
                    <xsl:when test="$hasDepositTariff = 'true'">
                        <!--две ссылки-->
                        if(!isEmpty(termsType))
                        {
                            <!--посмотрел условие дополнительного соглашения-->
                            window.userReadSecondConfirmMessage = true;
                        }
                        else
                        {
                            <!--посмотрел условие вклада-->
                            window.userReadFirstConfirmMessage = true;
                        }
                        if(window.userReadSecondConfirmMessage &amp;&amp; window.userReadFirstConfirmMessage)
                        {
                            window.userReadSecondConfirmMessage = false;
                            window.userReadFirstConfirmMessage = false;
                            if (agreeForAll != null)
                            {
                                agreeForAll.disabled = false;
                            }
                        }
                    </xsl:when>
                    <xsl:otherwise>
                        <!--одна ссылка-->
                        if (agreeForAll != null)
                        {
                            agreeForAll.disabled = false;
                        }
                    </xsl:otherwise>
                </xsl:choose>
            }

            init();
            doOnLoad(function()
            {
                showOrHidePremierWarning(<xsl:value-of select="premierShowMsg"/>);
                getDepositInfo();
            });
        </script>
    </xsl:template>
    <xsl:template name="fillDepositInfo">
        <xsl:param name="elements"/>
        <xsl:variable name="openDateDay" select="substring-before(openDate, '.')"/>
        <xsl:variable name="openDateMonth" select="substring-before(substring-after(openDate, '.'), '.')"/>
        <xsl:variable name="openDateYear" select="substring-after(substring-after(openDate, '.'), '.')"/>
        <xsl:variable name="docDate" select="concat($openDateYear, $openDateMonth, $openDateDay)"/>

        <xsl:variable name="elementsByDate"
                      select="$elements[$docDate &gt;= translate(interestDateBegin, '.', '') and $docDate &gt;= translate(dateBegin, '.', '') and $docDate &lt;= translate(dateEnd, '.', '')]"/>
        <xsl:variable name="elementsByTariffDependence"
                      select="$elementsByDate[(count(tariffDependence) = 0) or (tariffDependence/tariff/tariffCode = $tariffPlanCode and translate(tariffDependence/tariff/tariffDateBegin, '.', '')&lt;=translate($docDate, '.', '') and translate(tariffDependence/tariff/tariffDateEnd, '.', '')&gt;translate($docDate, '.', ''))]"/>

        <xsl:for-each select="$elementsByTariffDependence">
            <xsl:sort data-type="number" select="./minBalance" order="ascending"/>
            fillDPByCurrency('<xsl:value-of select="currencyCode"/>', '<xsl:value-of select="interestDateBegin"/>', '<xsl:value-of select="minBalance"/>',
            '<xsl:value-of select="period"/>', '<xsl:value-of select="periodInDays"/>', '<xsl:value-of select="interestRate"/>', '<xsl:value-of select="id"/>',
            '<xsl:value-of select="minAdditionalFee"/>', indexes, mapCurrencies, prevCurrencyValue, false, '<xsl:value-of select="./segmentCode"/>');
        </xsl:for-each>
    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
		</xsl:choose>
	</xsl:template>

    <!--добавление option для select'a-->
    <xsl:template name="addOption">
	    <xsl:param name="value"/>
	    <xsl:param name="selected"/>
	    <xsl:param name="source"/>

        <option>
            <xsl:attribute name="value">
                <xsl:value-of select="$value"/>
            </xsl:attribute>
            <xsl:if test="contains($value, $selected)">
                <xsl:attribute name="selected"/>
            </xsl:if>
            <xsl:call-template name="monthsToString">
                <xsl:with-param name="value" select="$value"/>
                <xsl:with-param name="source" select="$source"/>
            </xsl:call-template>
        </option>
	</xsl:template>

    <!--получение списка месяцев в строку-->
    <xsl:template name="monthsToString">
        <xsl:param name="value"/>
        <xsl:param name="source"/>

        <xsl:variable name="delimiter" select="'|'"/>

        <xsl:choose>
            <xsl:when test="contains($value, $delimiter)">
                <xsl:for-each select="xalan:tokenize($value, $delimiter)">
                    <xsl:value-of select="concat($source[@key = current()]/text(), ' ')"/>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat($source[@key = $value]/text(), ' ')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- шаблон формирующий div описания -->
    <xsl:template name="buildDescription">
   <xsl:param name="text"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Как заполнить это поле?</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="end">
   <![CDATA[ </div>
   ]]>
   </xsl:variable>

   <div class="description" style="display: none">

    <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

   <xsl:for-each select="$nodeText/node()">

   <xsl:choose>
		<xsl:when test=" name() = 'cut' and position() = 1 ">
		    <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
		</xsl:when>
        <xsl:when test="name() = 'cut' and position() != 1">
            <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
        </xsl:when>
   		<xsl:otherwise>
		<xsl:copy/>
		</xsl:otherwise>
   </xsl:choose>
   </xsl:for-each>

   <xsl:if test="count($nodeText/cut) > 0">
   <xsl:value-of select="$end" disable-output-escaping="yes"/>
   </xsl:if>
	</div>

</xsl:template>

<xsl:template name="standartRow">

	<xsl:param name="id"/>
    <xsl:param name="lineId"/>
    <!--идентификатор строки-->
    <xsl:param name="required" select="'true'"/>
    <!--параметр обязатьльности заполнения-->
    <xsl:param name="rowName"/>
    <!--описание поля-->
    <xsl:param name="rowValue"/>
    <!--данные-->
    <xsl:param name="description"/>
    <!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
    <xsl:param name="rowStyle"/>
    <!-- Стиль поля -->
    <xsl:param name="isAllocate" select="'true'"/>
    <!-- Выделить при нажатии -->
    <!-- Необязательный параметр -->
    <xsl:param name="fieldName"/>
    <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

    <xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
    <!-- Определение имени инпута или селекта передаваемого в шаблон -->
    <!-- inputName - fieldName или имя поле вытащеное из rowValue -->
    <xsl:variable name="inputName">
	<xsl:choose>
		<xsl:when test="string-length($fieldName) = 0">
				<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
					<xsl:value-of select="$nodeRowValue/input/@name"/>
					<xsl:if test="count($nodeRowValue/select[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/select/@name"/>
					</xsl:if>
                    <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/textarea/@name"/>
					</xsl:if>
				</xsl:if>
		</xsl:when>
		<xsl:otherwise>
				<xsl:copy-of select="$fieldName"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>

    <xsl:variable name="readonly">
		<xsl:choose>
			<xsl:when test="string-length($inputName)>0">
				<xsl:value-of
                        select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
			</xsl:when>
			<xsl:otherwise>
				0
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

 <xsl:variable name="styleClass">
		<xsl:choose>
			<xsl:when test="$isAllocate = 'true'">
				<xsl:value-of select="'form-row'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'form-row-addition'"/>
			</xsl:otherwise>
		</xsl:choose>
 </xsl:variable>

<div class="{$styleClass}">
    <xsl:if test="string-length($lineId) > 0">
        <xsl:attribute name="id">
            <xsl:copy-of select="$lineId"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="string-length($rowStyle) > 0">
        <xsl:attribute name="style">
            <xsl:copy-of select="$rowStyle"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
        <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
    </xsl:if>

	<div class="paymentLabel">
	    <span class="paymentTextLabel">
	        <xsl:if test="string-length($id) > 0">
	            <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
            </xsl:if>
	        <xsl:copy-of select="$rowName"/>
	    </span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>

				<xsl:if test="$readonly = 0 and $mode = 'edit'">
					<xsl:call-template name="buildDescription">
						<xsl:with-param name="text" select="$description"/>
	    			</xsl:call-template>
				</xsl:if>
                <div class="errorDiv" style="display: none;">
				</div>
	</div>
    <div class="clear"></div>
</div>

    <!-- Устанавливаем события onfocus поля -->
    <xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
		<script type="text/javascript">
		if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
		</script>
	</xsl:if>

</xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

    <!--Компонент для отображения "ползунка"-->
    <xsl:template name="scrollRow">
        <xsl:param name="id"/>
        <!--идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько-->
        <xsl:param name="minValue" select="1"/>
        <!--минимальное значение-->
        <xsl:param name="maxValue" select="100"/>
        <!--максимальное значение-->
        <xsl:param name="unit" select="'%'"/>
        <!--единица измерения-->
        <xsl:param name="minUnit"/>
        <!--форма названия единицы измерения для минимального значения-->
        <xsl:param name="maxUnit"/>
        <!--форма названия единицы измерения для максимального значения-->
        <xsl:param name="currValue" select="1"/>
        <!--текущее значение-->
        <xsl:param name="fieldName"/>
        <!--название поля, в которое необходимо положить значение-->
        <xsl:param name="inputData"/>
        <!--данные, которые необходимо отображать после <input>-->
        <xsl:param name="step" select="1"/>
        <!--шаг сетки-->
        <xsl:param name="round" select="2"/>
        <!--знак, до которого округляем значения-->
        <xsl:param name="callback"/>
        <!--функция выполняемая при смене значения ползунка-->
        <xsl:param name="valuesPosition" select="'bottom'"/>
        <!--расположение подписей значений, по умолчанию - на одной линии {inline|bottom}-->
        <xsl:param name="maxInputLength"/>
        <!--ограничение длинны поля для ввода.-->
        <xsl:param name="dataPosition" select="'top'"/>
        <!--Есть ли промо-ставки-->
        <xsl:param name="promoRate"/>

        <xsl:variable name="_minUnit">
            <xsl:choose>
                <xsl:when test="(string-length($minUnit) = 0) and (string-length($unit)>0)">
                    <xsl:value-of select="$unit"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$minUnit"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="_maxUnit">
            <xsl:choose>
                <xsl:when test="(string-length($maxUnit) = 0) and (string-length($unit)>0)">
                    <xsl:value-of select="$unit"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$maxUnit"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="_currValue">
            <xsl:choose>
                <xsl:when test="string-length($currValue) = 0">
                    <xsl:value-of select="$minValue"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$currValue"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <script type="text/javascript">
            <xsl:variable name="_xStep" select="($maxValue - $minValue) div $step + 1"/>
            var horizDrag<xsl:value-of select="$id"/>;
            doOnLoad(function(){

                horizDrag<xsl:value-of select="$id"/> = new Dragdealer('horizScroll<xsl:value-of select="$id"/>',
                {
                    snap: true,
                    xStep:      <xsl:value-of select="$_xStep"/>,
                    fieldName: '<xsl:value-of select="$fieldName"/>',
                    minValue:   <xsl:value-of select="$minValue"/>,
                    maxValue:   <xsl:value-of select="$maxValue"/>,
                    step:       <xsl:value-of select="$step"/>,
                    round:      <xsl:value-of select="$round"/>
                });

                horizDrag<xsl:value-of select="$id"/>.animationCallback = function(x, y){
                    var closingDateEl = getElement('closingDate');
                    var onChng = closingDateEl.onchange;
                    closingDateEl.onchange = function(){};
                    var id = '<xsl:value-of select="$id"/>';
                    var drag = horizDrag<xsl:value-of select="$id"/>;
                    drawActiv (id);

                    var inputVal = Math.round((drag.step - 1) * x);
                    $('input[name=' + drag.fieldName + ']').val(depositRates[inputVal]);
                    updateFormByDragDealer(depositRates[inputVal], true);
                    closingDateEl.onchange = onChng;
                };

                horizDrag<xsl:value-of select="$id"/>.initScroll(<xsl:value-of select="$currValue"/>);
                setScrollVal('<xsl:value-of select="$id"/>', horizDrag<xsl:value-of select="$id"/>);

            });

            function updateScroll(id, dragDealer,currValue,minValue,maxValue,modifyUnit)
            {
                $('input[name='+dragDealer.fieldName +']').val(currValue);
                dragDealer.currValue = currValue;
                dragDealer.minValue =minValue;
                dragDealer.maxValue = maxValue;
                dragDealer.step = depositRates.length;
                dragDealer.xStep = depositRates.length;
                dragDealer.round = 2;
                dragDealer.initScroll(currValue);
                setScrollVal(id, dragDealer);
                if (isEmpty(modifyUnit))
                {
                    $('#'+id+'scrollDescLeft').html(parseFloat(minValue).toFixed(dragDealer.round) + ' ' + $('#minUnit'+id).val());
                    $('#'+id+'scrollDescRight').html(parseFloat(maxValue).toFixed(dragDealer.round) + ' ' + $('#maxUnit'+id).val());
                }
                else
                {
                    $('#'+id+'scrollDescLeft').html(parseFloat(minValue).toFixed(dragDealer.round) + ' '+ modifyUnit(minValue));
                    $('#'+id+'scrollDescRight').html(parseFloat(maxValue).toFixed(dragDealer.round) + ' '+ modifyUnit(maxValue));
                }
            }

            function onScrollEnterPress<xsl:value-of select="$id"/>(e)
            {
                var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;
                if(kk == 13)
                {
                    setScrollVal('<xsl:value-of select="$id"/>', horizDrag<xsl:value-of select="$id"/>);
                    <xsl:if test="string-length($callback)>0">
                        <xsl:value-of select="$callback"/>
                    </xsl:if>
                    cancelBubbling(e);
                }
            }
        </script>

        <xsl:variable name="_class">
            <xsl:choose>
                <xsl:when test="$dataPosition = 'right'">
                    scrollInput scrollInputPosRight
                </xsl:when>
                <xsl:otherwise>
                    scrollInput
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="_maxlength">
            <xsl:choose>
                <xsl:when test="string-length($maxInputLength)>0">
                    <xsl:value-of select="$maxInputLength"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="string-length($maxValue)"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="interestRateResultClass">
            <xsl:if test="$promoRate = 'true'">
                <xsl:value-of select="'promoInterestRate'"/>
            </xsl:if>
        </xsl:variable>

        <div class="{$_class}">
            <span class="{$interestRateResultClass}"><span id="interestRateResult" class="bold"></span></span>
            <input type="hidden" name="{$fieldName}" value="{$currValue}"/>
            <xsl:value-of select="$inputData"/>
        </div>

        <div class="scroller{$valuesPosition}">
            <input id="minUnit{$id}" type="hidden" name="minUnit{$id}" value="{$_minUnit}"/>
            <input id="maxUnit{$id}" type="hidden" name="maxUnit{$id}" value="{$_maxUnit}"/>

            <xsl:choose>
                <xsl:when test="$valuesPosition = 'bottom'">
                    <div class="dragdealer">
                        <div id="horizScroll{$id}" class="greenScroll">
                            <div class="scrollRight"></div>
                            <div class="scrollLeft"></div>
                            <div class="scrollCenter">
                                <div id="Main{$id}" class="scrollMain">
                                    <div class="innerScroll" id="InnerScroll{$id}"></div>
                                </div>
                            </div>
                            <div class="scrollShadow"></div>
                            <div class="clear"></div>
                            <div id="horizScroll{$id}Inner" class="scrollInner handle"></div>
                        </div>
                    </div>
                    <div class="values" style="width:380px">
                        <div id="{$id}scrollDescLeft" class="scrollDescLeft"><xsl:value-of select="$minValue"/><xsl:value-of select="$_minUnit"/></div>
                        <div id="{$id}scrollDescRight" class="scrollDescRight"><xsl:value-of select="$maxValue"/><xsl:value-of select="$_maxUnit"/></div>
                    </div>
                </xsl:when>
                <xsl:otherwise>
                    <div id="{$id}scrollDescLeft" class="scrollDescLeft"><xsl:value-of select="$minValue"/><xsl:value-of select="$_minUnit"/></div>

                    <div class="dragdealer">

                        <div id="horizScroll{$id}" class="greenScroll">

                            <div class="scrollRight"></div>
                            <div class="scrollLeft"></div>
                            <div class="scrollCenter">
                                <div id="Main{$id}" class="scrollMain">
                                    <div class="innerScroll" id="InnerScroll{$id}"></div>
                                </div>
                            </div>
                            <div class="scrollShadow"></div>

                            <div class="clear"></div>

                            <div id="horizScroll{$id}Inner" class="scrollInner handle"></div>
                        </div>

                    </div>

                    <div id="{$id}scrollDescRight" class="scrollDescRight"><xsl:value-of select="$maxValue"/><xsl:value-of select="$_maxUnit"/></div>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>

    <xsl:template name="fillDepositByRate">
        <xsl:param name="elements"/>
        <xsl:variable name="openDateDay" select="substring-before(openDate, '.')"/>
        <xsl:variable name="openDateMonth" select="substring-before(substring-after(openDate, '.'), '.')"/>
        <xsl:variable name="openDateYear" select="substring-after(substring-after(openDate, '.'), '.')"/>
        <xsl:variable name="docDate" select="concat($openDateYear, $openDateMonth, $openDateDay)"/>

        <xsl:variable name="elementsByDate"
                      select="$elements[$docDate &gt;= translate(interestDateBegin, '.', '') and $docDate &gt;= translate(dateBegin, '.', '') and $docDate &lt;= translate(dateEnd, '.', '')]"/>
        <xsl:variable name="elementsByTariffDependence"
                      select="$elementsByDate[(count(tariffDependence) = 0) or (tariffDependence/tariff/tariffCode = $tariffPlanCode and translate(tariffDependence/tariff/tariffDateBegin, '.', '')&lt;=translate($docDate, '.', '') and translate(tariffDependence/tariff/tariffDateEnd, '.', '')&gt;translate($docDate, '.', ''))]"/>

        <xsl:for-each select="$elementsByTariffDependence">
            <xsl:sort data-type="number" select="./interestRate" order="ascending"/>
            fillProductWithRate('<xsl:value-of select="./currencyCode"/>', '<xsl:value-of select="./interestRate"/>', '<xsl:value-of select="./minBalance"/>', '<xsl:value-of select="./period"/>', indexes, '<xsl:value-of select="./id"/>', '<xsl:value-of select="./interestDateBegin"/>', '<xsl:value-of select="./segmentCode"/>');
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="elementSegmentForPension">
        <xsl:param name="elements"/>
        <xsl:param name="groupParams" select="''"/>

        <xsl:variable name="pension_elements" select="$elements[segmentCode != 0]"/>

        <xsl:choose>
            <xsl:when test="$groupParams != 'true'">
                <xsl:choose>
                    <xsl:when test="count($pension_elements)&gt;=1">
                        <xsl:value-of select="'1'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'0'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="pension_elements_param" select="$pension_elements[group/groupParams/pensionRate = 'true']"/>
                <xsl:choose>
                    <xsl:when test="count($pension_elements_param)&gt;=1">
                        <xsl:value-of select="'1'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'0'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="fillDeposit">
        <xsl:param name="elements"/>
        <xsl:param name="clientTariff"/>
        <xsl:param name="callFunction"/>

        <xsl:choose>
            <xsl:when test="$callFunction = 'fillDepositInfo'">
                <xsl:call-template name="fillDepositInfo">
                    <xsl:with-param name="elements"
                            select="$elements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$callFunction = 'fillDepositByRate'">
                <xsl:call-template name="fillDepositByRate">
                    <xsl:with-param name="elements"
                                    select="$elements[(0!=$clientTariff and tariffPlanCode=$tariffPlanCode) or (0=$clientTariff and tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3)]"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
        <!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="validatorsError.xml" srcSchemaRoot="" AssociatedInstance="file:///c:/Flash/v1.18/Business/settings/payments/SBRF/InternalPayment/validatorsError.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
