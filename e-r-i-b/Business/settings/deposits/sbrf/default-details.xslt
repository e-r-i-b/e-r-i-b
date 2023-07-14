<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:su="java://org.apache.commons.lang.StringUtils"
                exclude-result-prefixes="xalan">
	<xsl:output method="html" indent="yes" encoding="windows-1251" version="1.0"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="curDate"/>
    <xsl:param name="isPension"/>
    <xsl:param name="tariff"/>
    <xsl:param name="group"/>
    <xsl:param name="admin"/>
    <xsl:param name="segment"/>

    <xsl:template match="/product">
        <xsl:variable name="dateFilteredElements" select="./data/options/element[group/groupCode = $group and translate(dateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(dateEnd, '.', '')&gt;translate($curDate, '.', '') and translate(interestDateBegin, '.', '')&lt;=translate($curDate, '.', '') and ($admin or availToOpen='true')]"/>

        <xsl:variable name="promoCodes" select="document('client-promo-segment-list.xml')/entity-list/*"/>
        <xsl:variable name="hasPriorPromoSegment" select="$promoCodes[@key = $segment]/field[@name = 'priority']/text() = 1"/>

        <!--Если открывается промо-вклад, то ищем только ставки с этим кодом сегмента-->
        <xsl:variable name="priorPromoElements" select="$dateFilteredElements[segmentCode = $segment and group/groupParams/promoCodeSupported = 'true']"/>

        <!--Если открывается не промовклад (даже если есть промокод, но с приоритетом = 1, учитываем ТП и признак пенсионера)-->
        <xsl:variable name="notPriorPromoElements" select="$dateFilteredElements[segmentCode = $segment or segmentCode = 1 or segmentCode = 0]"/>
        <!--Не пенсионные ставки без учета параметров группы-->
        <xsl:variable name="standard_elements" select="$notPriorPromoElements[segmentCode = 0 or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true')]"/>
        <!--Пенсионные ставки без учета параметров группы-->
        <xsl:variable name="pension_elements" select="$notPriorPromoElements[segmentCode = 1 or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true')]"/>
        <!--Пенсионные ставки с учетом параметров группы-->
        <xsl:variable name="pension_elements_param" select="$pension_elements[group/groupParams/pensionRate = 'true']"/>

        <xsl:variable name="clientTariff">
            <xsl:choose>
                <xsl:when test="$admin or count($dateFilteredElements[tariffPlanCode=$tariff]) > 0">
                    <xsl:value-of select="$tariff"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'0'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:choose>
            <!--Когда обрабатываем промо-вклад, не проверяем признак пенсионера. Отображаем только промо-ставки-->
            <xsl:when test="$hasPriorPromoSegment">
                <xsl:call-template name="dataWithTariff">
                    <xsl:with-param name="dateFilteredElements" select="$priorPromoElements"/>
                    <xsl:with-param name="tariff" select="$clientTariff"/>
                    <xsl:with-param name="segment" select="$promoCodes[@key = $segment]"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$group = '-22'">
                <xsl:choose>
                    <xsl:when test="$isPension and count($pension_elements) &gt; 0 ">

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

                        <xsl:call-template name="fillData">
                             <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'RUB' and (segmentCode = $e_RUB_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                                     $notPriorPromoElements[currencyCode = 'EUR' and (segmentCode = $e_EUR_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                                     $notPriorPromoElements[currencyCode = 'USD' and (segmentCode = $e_USD_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))]"/>
                             <xsl:with-param name="clientTariff" select="$clientTariff"/>
                             <xsl:with-param name="promoCodes" select="$promoCodes"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="dateFilteredElements" select="$standard_elements"/>
                            <xsl:with-param name="tariff" select="$clientTariff"/>
                            <xsl:with-param name="segment" select="$promoCodes[@key = $segment]"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$isPension and count($pension_elements_param) &gt; 0 ">

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

                        <xsl:call-template name="fillData">
                             <xsl:with-param name="elements" select="$notPriorPromoElements[currencyCode = 'RUB' and (segmentCode = $e_RUB_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                                     $notPriorPromoElements[currencyCode = 'EUR' and (segmentCode = $e_EUR_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))] |
                                                                     $notPriorPromoElements[currencyCode = 'USD' and (segmentCode = $e_USD_segment or (segmentCode = $segment and group/groupParams/promoCodeSupported = 'true'))]"/>
                             <xsl:with-param name="clientTariff" select="$clientTariff"/>
                             <xsl:with-param name="promoCodes" select="$promoCodes"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="dateFilteredElements" select="$standard_elements"/>
                            <xsl:with-param name="tariff" select="$clientTariff"/>
                            <xsl:with-param name="segment" select="$promoCodes[@key = $segment]"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>

    </xsl:template>

    <xsl:template name="elementSegmentForPension">
        <xsl:param name="elements"/>
        <xsl:param name="groupParams" select="''"/>

        <xsl:variable name="pension_elements" select="$elements[segmentCode != 0]"/>

        <xsl:choose>
            <xsl:when test="$groupParams != 'true'">
                <xsl:choose>
                    <xsl:when test="count($pension_elements)&gt;1">
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
                    <xsl:when test="count($pension_elements_param)&gt;1">
                        <xsl:value-of select="'1'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'0'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="fillData">
        <xsl:param name="elements"/>
        <xsl:param name="clientTariff"/>
        <xsl:param name="promoCodes"/>

        <xsl:call-template name="dataWithTariff">
             <xsl:with-param name="dateFilteredElements" select="$elements"/>
             <xsl:with-param name="tariff" select="$clientTariff"/>
             <xsl:with-param name="segment" select="$promoCodes[@key = $segment]"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="dataWithTariff" >
        <xsl:param name="dateFilteredElements"/>
        <xsl:param name="tariff"/>
        <xsl:param name="segment"/>
        <xsl:choose>
            <xsl:when test="$tariff=''">
                <xsl:call-template name="data">
                    <xsl:with-param name="dateFilteredElements" select="$dateFilteredElements"/>
                    <xsl:with-param name="segment" select="$segment"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$tariff=0">
                <xsl:call-template name="data">
                    <xsl:with-param name="dateFilteredElements" select="$dateFilteredElements[tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3]"/>
                    <xsl:with-param name="segment" select="$segment"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="data">
                    <xsl:with-param name="dateFilteredElements" select="$dateFilteredElements[tariffPlanCode=$tariff]"/>
                    <xsl:with-param name="segment" select="$segment"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="dataWithTariffDependence">
        <xsl:param name="dateFilteredElements"/>
        <xsl:param name="tariff"/>
        <xsl:param name="segment"/>
        <xsl:choose>
            <xsl:when test="not($admin)">
                <xsl:call-template name="data">
                    <xsl:with-param name="dateFilteredElements" select="dateFilteredElements[(count(tariffDependence) = 0) or (tariffDependence/tariff/tariffCode = $tariff and translate(tariffDependence/tariff/tariffDateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(tariffDependence/tariff/tariffDateEnd, '.', '')&gt;translate($curDate, '.', ''))]"/>
                    <xsl:with-param name="segment" select="$segment"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="data">
                    <xsl:with-param name="dateFilteredElements" select="$dateFilteredElements"/>
                    <xsl:with-param name="segment" select="$segment"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Основные таблицы -->
    <xsl:template name="data">
        <xsl:param name="dateFilteredElements"/>
        <xsl:param name="segment"/>

        <xsl:variable name="promoProduct" select="count($segment[field[@name = 'priority' and text()='1']]) > 0"/>

        <xsl:variable name="periods" select="xalan:distinct($dateFilteredElements/period/text())"/>
        <xsl:variable name="currencis" select="xalan:distinct($dateFilteredElements/currencyCode/text())"/>

        <xsl:if test="not($admin)">
            <h1>
                <xsl:call-template name="depositName">
                    <xsl:with-param name="filteredElements" select="$dateFilteredElements"/>
                </xsl:call-template>
            </h1>
            <xsl:if test="$promoProduct">
                <span class="promoCodeLabel">промо-вклад</span>
            </xsl:if>
            <br/>

            <xsl:if test="$promoProduct">
                <div class="premiumDepositProductItem">
                    <div class="promoShortDescription"><xsl:value-of select="$segment/field[@name = 'shortDescription']/text()"/></div>
                    <div class="clear"></div>
                    <div class="promoFullDescription"><xsl:value-of select="$segment/field[@name = 'fullDescription']/text()"/></div>
                </div>
            </xsl:if>

        </xsl:if>
        <div class="depositInfoTable">
        <!-- Таблица с обзорной информацией по вкладу -->
		<table class='windowShadowTable simpleTable'>
            <tr class="tblInfHeader">
                <th class="titleTable first">Валюта</th>
                <th class="titleTable">Сумма</th>
                <xsl:choose>
                    <xsl:when test="boolean($periods)">
                        <xsl:for-each select="$periods">
                             <xsl:sort data-type="text" order="ascending"/>
                             <xsl:variable name="period" select="."/>
                             <th class="titleTable">
                                 <xsl:if test="position()= last()">
                                    <xsl:attribute name="class">last titleTable</xsl:attribute>
                                 </xsl:if>
                                 <xsl:choose>
                                    <xsl:when test="contains($period, 'U')">
                                        <xsl:call-template name="parseInterval">
                                            <xsl:with-param name="interval" select="."/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="parsePeriod">
                                            <xsl:with-param name="period" select="."/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                             </th>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:otherwise>
                        <th class="titleTable">Процентная ставка</th>
                    </xsl:otherwise>
                </xsl:choose>
            </tr>


          <xsl:for-each select="$currencis">
              <xsl:variable name="curCurrency" select="."/>
              <xsl:for-each select="xalan:distinct($dateFilteredElements[$curCurrency = currencyCode]/minBalance)">
                 <xsl:sort data-type="number" order="ascending"/>
                 <xsl:variable name="curMinBalance" select="."/>
                 <xsl:variable name="rowStyle">
                     <xsl:choose>
                         <xsl:when test="count($dateFilteredElements[segmentCode = $segment/@key and $curCurrency = currencyCode and $curMinBalance = minBalance]) > 0 and not($promoProduct)">
                             <xsl:value-of select="'premiumDepositProductItem'"/>
                         </xsl:when>
                         <xsl:otherwise>
                             <xsl:value-of select="''"/>
                         </xsl:otherwise>
                     </xsl:choose>
                 </xsl:variable>
                 <tr class="{$rowStyle}">
                     <xsl:if test="position() = 1">
                         <td class='first'>
                            <xsl:attribute name="rowspan"><xsl:value-of select="last()"/></xsl:attribute>
                            <xsl:call-template name="printCurrency">
                                <xsl:with-param name="currency" select="$curCurrency"/>
                            </xsl:call-template>
                         </td>
                     </xsl:if>
                     <!-- Сумма вклада -->
                     <td class="textNobr">от <xsl:value-of select="format-number($curMinBalance, '# ##0,00', 'sbrf')"/></td>

                    <xsl:choose>
                        <xsl:when test="boolean($periods)">
                            <xsl:for-each select="$periods">
                                <xsl:sort data-type="text" order="ascending"/>
                                <xsl:variable name="curPeriod" select="."/>
                                <td nowrap="true">
                                    <xsl:if test="position()= last()">
                                        <xsl:attribute name="class">last</xsl:attribute>
                                    </xsl:if>

                                    <xsl:variable name="filteredElements" select="$dateFilteredElements[$curCurrency = currencyCode and $curMinBalance = minBalance and $curPeriod = period ]"/>
                                    <xsl:variable name="segmentFilteredElements" select="$filteredElements[$segment/@key = segmentCode]"/>

                                    <xsl:choose>
                                        <xsl:when test="count($segmentFilteredElements) > 0">
                                            <xsl:call-template name="rates">
                                                <xsl:with-param name="elements" select="$segmentFilteredElements"/>
                                                <xsl:with-param name="segment" select="$segment"/>
                                            </xsl:call-template>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:call-template name="rates">
                                                <xsl:with-param name="elements" select="$filteredElements"/>
                                                <xsl:with-param name="segment" select="$segment"/>
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise>
                            <td class="last" nowrap="true">
                                <xsl:variable name="filteredElements" select="$dateFilteredElements[$curCurrency = currencyCode and $curMinBalance = minBalance]"/>
                                <xsl:variable name="segmentFilteredElements" select="$filteredElements[$segment/@key = segmentCode]"/>

                                <xsl:choose>
                                    <xsl:when test="count($segmentFilteredElements) > 0">
                                        <xsl:call-template name="rates">
                                            <xsl:with-param name="elements" select="$segmentFilteredElements"/>
                                            <xsl:with-param name="segment" select="$segment"/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="rates">
                                            <xsl:with-param name="elements" select="$filteredElements"/>
                                            <xsl:with-param name="segment" select="$segment"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                        </xsl:otherwise>
                     </xsl:choose>
                 </tr>
              </xsl:for-each>
          </xsl:for-each>

        </table>
        </div>
        <!-- Дополнительная информация по вкладу -->
        <table class="infoTable">
            <xsl:variable name="minDepositSubType">
                <xsl:call-template name="min">
                    <xsl:with-param name="list" select="$dateFilteredElements/id/text()"/>
                </xsl:call-template>
            </xsl:variable>

            <xsl:variable name="incomingTransactions" select="$dateFilteredElements[id=$minDepositSubType]/incomingTransactions"/>
            <xsl:variable name="frequencyAdd" select="$dateFilteredElements[id=$minDepositSubType]/frequencyAdd"/>
            <xsl:variable name="debitTransactions" select="$dateFilteredElements[id=$minDepositSubType]/debitTransactions"/>
            <xsl:variable name="frequencyPercent" select="$dateFilteredElements[id=$minDepositSubType]/frequencyPercent"/>
            <xsl:variable name="percentOrder" select="$dateFilteredElements[id=$minDepositSubType]/percentOrder"/>
            <xsl:variable name="incomeOrder" select="$dateFilteredElements[id=$minDepositSubType]/incomeOrder"/>
            <xsl:variable name="renewals" select="$dateFilteredElements[id=$minDepositSubType]/renewals"/>
            <xsl:variable name="initialFee" select="./data/main/initialFee"/>
            <xsl:variable name="zeroMinAdditionalFee" select="count($dateFilteredElements[minAdditionalFee!=0]) &gt; 0"/>

            <xsl:if test="($initialFee='true') or ($zeroMinAdditionalFee='true')">
                <tr>
                    <td>Процентная ставка:</td>
                    <td>
                        <xsl:for-each select="$dateFilteredElements">
                            <xsl:if test="position() = 1">
                                <xsl:variable name="percent" select="./percent"/>
                                <xsl:variable name="replaceTo">
                                    <xsl:text>Определяется в соответствии с таблицей</xsl:text>
                                </xsl:variable>
                                <xsl:value-of select="su:replace(string($percent), '{PROC}', string($replaceTo))" disable-output-escaping="yes"/>
                            </xsl:if>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:if>

            <xsl:if test="not($incomingTransactions = '')">
                <tr>
                    <td>Приходные операции по вкладу:</td>
                    <td><xsl:value-of select="$incomingTransactions" disable-output-escaping="yes"/></td>
                </tr>
            </xsl:if>

            <tr>
                <td>Минимальный размер дополнительного взноса, вносимого наличными деньгами:</td>
                <td>
                    <xsl:choose>
                        <xsl:when test="count($dateFilteredElements[minAdditionalFee != 0]) &gt; 0">
                            <xsl:for-each select="$currencis">
                                <xsl:variable name="curCurrency" select="."/>

                                <xsl:call-template name="minNumber">
                                    <xsl:with-param name="list" select="$dateFilteredElements[$curCurrency = currencyCode]/minAdditionalFee"/>
                                </xsl:call-template>

                                <xsl:text> </xsl:text>

                                <xsl:call-template name="printCurrency">
                                    <xsl:with-param name="currency" select="$curCurrency"/>
                                </xsl:call-template>

                                <xsl:if test="position() != last()">
                                    <xsl:text>, </xsl:text>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise>
                            не предусмотрен
                        </xsl:otherwise>
                    </xsl:choose>
                </td>
            </tr>

            <xsl:if test="not($frequencyAdd = '')">
                <tr>
                    <td>Периодичность внесения дополнительных взносов:</td>
                    <td><xsl:value-of select="$frequencyAdd" disable-output-escaping="yes"/></td>
                </tr>
            </xsl:if>

            <xsl:if test="not($debitTransactions = '')">
                <tr>
                    <td>Расходные операции по вкладу:</td>
                    <td><xsl:value-of select="$debitTransactions" disable-output-escaping="yes"/></td>
                </tr>
            </xsl:if>

            <xsl:if test="not($frequencyPercent = '')">
                <tr>
                    <td>Периодичность выплаты процентов:</td>
                    <td><xsl:value-of select="$frequencyPercent" disable-output-escaping="yes"/></td>
                </tr>
            </xsl:if>

            <xsl:if test="not($percentOrder = '')">
                <tr>
                    <td>Порядок уплаты процентов:</td>
                    <td><xsl:value-of select="$percentOrder" disable-output-escaping="yes"/></td>
                </tr>
            </xsl:if>

            <xsl:if test="not($incomeOrder = '')">
                <tr>
                    <td>Порядок начисления дохода при досрочном востребовании вклада:</td>
                    <td>
                        <div id="percentInfo">
                            <xsl:variable name="minDepositSubTypeCurr">
                                <xsl:call-template name="minSubTypeCurr">
                                    <xsl:with-param name="list" select="xalan:distinct($dateFilteredElements[id=$minDepositSubType]/currencyCode/text())"/>
                                </xsl:call-template>
                            </xsl:variable>

                            <xsl:value-of select="translate($incomeOrder, '{KOD_VAL}', $minDepositSubTypeCurr)" disable-output-escaping="yes"/>
                        </div>
                    </td>
                </tr>
            </xsl:if>

            <xsl:if test="not($renewals = '')">
                <tr>
                    <td>Количество пролонгаций Договора:</td>
                    <td><xsl:value-of select="$renewals" disable-output-escaping="yes"/></td>
                </tr>
            </xsl:if>
        </table>
    </xsl:template>

    <!-- Определение минимума -->
    <xsl:template name="minNumber">
		<xsl:param name="list"/>
		<xsl:for-each select="$list">
			<xsl:sort data-type="number" order="ascending"/>
			<xsl:if test="position() = 1">
				<xsl:value-of select="."/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

    <!-- Вывод валюты -->
     <xsl:template name="printCurrency">
		<xsl:param name="currency"/>
		<xsl:choose>
            <xsl:when test="$currency='RUB'">
                <xsl:text>руб.</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="mu:getCurrencySign($currency)"/>
            </xsl:otherwise>
        </xsl:choose>
	</xsl:template>

    <!-- Парсер периода -->
    <xsl:template name="parsePeriod">
		<xsl:param name="period"/>
		<xsl:variable name="years" select="number(substring($period, 1, 2))"/>
		<xsl:variable name="months" select="number(substring($period, 4, 2))"/>
		<xsl:variable name="days" select="number(substring($period, 7, 4))"/>
		<xsl:choose>
			<xsl:when test="$years!=0">
				<xsl:value-of select="$years"/>
				<xsl:choose>
					<xsl:when test="$years = 1">
						<xsl:text> год</xsl:text>
					</xsl:when>
                    <xsl:when test="$years &gt;= 2 and $years &lt; 5">
						<xsl:text> года</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> лет</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$months!=0">
				<xsl:value-of select="$months"/>
				<xsl:choose>
                    <xsl:when test="$years = 1">
						<xsl:text> месяц</xsl:text>
					</xsl:when>
                    <xsl:when test="$years &gt;= 2 and $years &lt; 5">
						<xsl:text> месяца</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> месяцев</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$days!=0">
				<xsl:value-of select="$days"/>
				<xsl:choose>
                    <xsl:when test="$years = 1">
						<xsl:text> день</xsl:text>
					</xsl:when>
                    <xsl:when test="$years &gt;= 2 and $years &lt; 5">
						<xsl:text> дня</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> дней</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
		</xsl:choose>
	</xsl:template>


    <xsl:template name="parseInterval">
		<xsl:param name="interval"/>
        <xsl:variable name="from" select="substring($interval, 1, 10)"/>
        <xsl:variable name="to" select="substring($interval, 12, 22)"/>

        <xsl:variable name="fromText">
            <xsl:call-template name="parseIntervalPeriod">
                <xsl:with-param name="period" select="$from"/>
            </xsl:call-template>
        </xsl:variable>

        <xsl:variable name="toText">
            <xsl:call-template name="parseIntervalPeriod">
                <xsl:with-param name="period" select="$to"/>
            </xsl:call-template>
        </xsl:variable>

        <xsl:text>От </xsl:text>

        <xsl:call-template name="parsePeriodNumber">
            <xsl:with-param name="period" select="$from"/>
        </xsl:call-template>

        <xsl:if test="$fromText!=$toText">
              <xsl:value-of select="$fromText"/>
        </xsl:if>

        <xsl:text> до </xsl:text>

        <xsl:call-template name="parsePeriodNumber">
            <xsl:with-param name="period" select="$to"/>
        </xsl:call-template>
        <xsl:value-of select="$toText"/>

    </xsl:template>

    <!-- Парсер интервального периода (вывод текстовой даты) -->
    <xsl:template name="parseIntervalPeriod">
		<xsl:param name="period"/>
		<xsl:variable name="years" select="number(substring($period, 1, 2))"/>
		<xsl:variable name="months" select="number(substring($period, 4, 2))"/>
		<xsl:variable name="days" select="number(substring($period, 7, 4))"/>
		<xsl:choose>
			<xsl:when test="$years!=0">
				<xsl:choose>
					<xsl:when test="(($years mod 10) = 1) and ($years != 11)">
						<xsl:text> года</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> лет</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$months!=0">
				<xsl:choose>
					<xsl:when test="$months = 1">
						<xsl:text> месяца</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> месяцев</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$days!=0">
				<xsl:choose>
					<xsl:when test="(($days mod 10) = 1) and ($days != 11)">
						<xsl:text> дня</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> дней</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
    <!-- Парсер интервального периода (числовая информация) -->
    <xsl:template name="parsePeriodNumber">
		<xsl:param name="period"/>
		<xsl:variable name="years" select="number(substring($period, 1, 2))"/>
		<xsl:variable name="months" select="number(substring($period, 4, 2))"/>
		<xsl:variable name="days" select="number(substring($period, 7, 4))"/>
		<xsl:choose>
			<xsl:when test="$years!=0">
				<xsl:value-of select="$years"/>
			</xsl:when>
			<xsl:when test="$months!=0">
				<xsl:value-of select="$months"/>
			</xsl:when>
			<xsl:when test="$days!=0">
				<xsl:value-of select="$days"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="min">
		<xsl:param name="list"/>
		<xsl:for-each select="$list">
			<xsl:sort data-type="text" order="ascending"/>
			<xsl:if test="position() = 1">
				<xsl:value-of select="."/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

    <xsl:template name="minSubTypeCurr">
		<xsl:param name="list"/>

        <xsl:variable name="newValue" select="''"/>

		<xsl:for-each select="$list">
            <xsl:sort data-type="text" order="ascending"/>
            <xsl:variable name="additionalCurr">
                <xsl:call-template name="printCurrency">
                    <xsl:with-param name="currency" select="."/>
                </xsl:call-template>
            </xsl:variable>

            <xsl:choose>
                <xsl:when test="position() = 1">
                    <xsl:value-of select="concat($newValue, $additionalCurr)"/>
                </xsl:when>
                <xsl:when test="position() != 1">
                    <xsl:value-of select="concat($newValue, ', ', $additionalCurr)"/>
                </xsl:when>
            </xsl:choose>
		</xsl:for-each>
	</xsl:template>

    <!--
    Название депозитного продукта.
    Если есть группа (т.е. не равна -22), используется ее название.
    Если это непараметризированный вклад (группа равна -22), используется название первого подвида из списка
    -->
    <xsl:template name="depositName">
        <xsl:param name="filteredElements"/>
        <xsl:variable name="code" select="$filteredElements/group/groupCode"/>
        <xsl:choose>
            <xsl:when test="$code = '-22'">
                <xsl:value-of select="$filteredElements/subTypeName"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$filteredElements/group/groupName"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="rates">
        <xsl:param name="elements"/>
        <xsl:param name="segment"/>

        <xsl:for-each select="$elements">
            <xsl:sort data-type="number" order="descending" select="translate(./interestDateBegin, '.', '')"/>
            <xsl:if test="position() = 1">
                <xsl:variable name="rate" select="."/>
                <xsl:choose>
                    <xsl:when test="string-length($rate)!=0 != ''">
                        <xsl:choose>
                            <xsl:when test="$rate[segmentCode = $segment/@key]">
                                <b>
                                    <xsl:value-of select="$rate/interestRate"/>
                                    <xsl:text> %</xsl:text>
                                </b>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$rate/interestRate"/>
                                <xsl:text> %</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>&nbsp;</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>