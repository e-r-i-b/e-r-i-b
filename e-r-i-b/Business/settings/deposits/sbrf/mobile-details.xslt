<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                exclude-result-prefixes="xalan mu">
	<xsl:output method="html" indent="yes" encoding="windows-1251" version="1.0"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="curDate"/>
    <xsl:param name="isPension"/>
    <xsl:param name="tariff"/>
    <xsl:param name="group"/>
    <xsl:param name="showNewDeposits"/>

    <xsl:template match="/product">
        <!--Доступные для открытия коды групп, соответствующие старым вкладам-->
        <xsl:variable name="productType" select="./data/productId"/>
        <xsl:variable name="allowedGroupCode" select="document('old-deposit-codes.xml')/entity-list/entity[field[@name = 'deposit' and text() = $productType]]/field[@name = 'groupCode']/text()"/>

        <xsl:variable name="groupToShow">
            <xsl:choose>
                <!--Если не отображаем новые вклады (до mApi8)-->
                <xsl:when test="not($showNewDeposits) and count($allowedGroupCode) > 0">
                    <xsl:value-of select="$allowedGroupCode"/>
                </xsl:when>
                <!--Если отображаем новые вклады, то должна быть передана группа-->
                <xsl:otherwise>
                    <xsl:value-of select="$group"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:call-template name="allElements">
            <xsl:with-param name="groupToShow" select="$groupToShow"/>
        </xsl:call-template>
    </xsl:template>

    <!--Отсортировать подвиды с учетом кода группы, если это возможно-->
    <xsl:template name="allElements">
        <xsl:param name="groupToShow" select="''"/>

        <xsl:choose>
            <xsl:when test="string-length($groupToShow) > 0">
                <xsl:call-template name="groupFilteredElements">
                    <xsl:with-param name="elementList" select="./data/options/element[group/groupCode = $groupToShow]"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="groupFilteredElements">
                    <xsl:with-param name="elementList" select="./data/options/element"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="groupFilteredElements">
        <xsl:param name="elementList"/>

        <xsl:variable name="dateFilteredElements" select="$elementList[translate(dateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(dateEnd, '.', '')&gt;translate($curDate, '.', '') and translate(interestDateBegin, '.', '')&lt;=translate($curDate, '.', '') and availToOpen='true' and (segmentCode = 0 or segmentCode = 1)]"/>
        <xsl:variable name="clientTariff">
            <xsl:choose>
                <xsl:when test="count($dateFilteredElements[tariffPlanCode=$tariff]) > 0">
                    <xsl:value-of select="$tariff"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'0'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <!--Не пенсионные ставки без учета параметров группы-->
        <xsl:variable name="standard_elements" select="$dateFilteredElements[segmentCode = 0]"/>
        <!--Пенсионные ставки без учета параметров группы-->
        <xsl:variable name="pension_elements" select="$dateFilteredElements[segmentCode = 1]"/>
        <!--Пенсионные ставки с учетом параметров группы-->
        <xsl:variable name="pension_elements_param" select="$pension_elements[group/groupParams/pensionRate = 'true']"/>

        <xsl:choose>
            <xsl:when test="$group = '-22'">
                <xsl:choose>
                    <xsl:when test="$isPension and count($pension_elements) &gt; 0 ">
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="dateFilteredElements" select="$pension_elements"/>
                            <xsl:with-param name="tariff" select="$clientTariff"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="dateFilteredElements" select="$standard_elements"/>
                            <xsl:with-param name="tariff" select="$clientTariff"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$isPension and count($pension_elements_param) &gt; 0 ">
                         <xsl:call-template name="dataWithTariff">
                             <xsl:with-param name="dateFilteredElements" select="$pension_elements_param"/>
                             <xsl:with-param name="tariff" select="$clientTariff"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="dateFilteredElements" select="$standard_elements"/>
                            <xsl:with-param name="tariff" select="$clientTariff"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="data">
        <xsl:param name="dateFilteredElements"/>

        <xsl:variable name="periods" select="xalan:distinct($dateFilteredElements/period/text())"/>
        <xsl:variable name="currencis" select="xalan:distinct($dateFilteredElements/currencyCode/text())"/>

        <conditionsList>

          <xsl:for-each select="$currencis">
              <xsl:variable name="curCurrency" select="."/>
              <xsl:for-each select="xalan:distinct($dateFilteredElements[$curCurrency = currencyCode]/minBalance)">
                 <xsl:sort data-type="number" order="ascending"/>
                 <xsl:variable name="curMinBalance" select="."/>

                    <xsl:choose>
                        <xsl:when test="boolean($periods)">
                            <xsl:for-each select="$periods">
                                <xsl:sort data-type="text" order="ascending"/>
                                <xsl:variable name="curPeriod" select="."/>

                                <condition>
                                    <currency>
                                        <code>
                                            <xsl:value-of select="$curCurrency"/>
                                        </code>
                                        <name>
                                            <xsl:value-of select="mu:getCurrencySign($curCurrency)" disable-output-escaping="yes"/>
                                        </name>
                                    </currency>

                                    <amountOf>
                                        <xsl:value-of select="$curMinBalance"/>
                                    </amountOf>

                                    <period>
                                        <xsl:choose>
                                            <xsl:when test="contains($curPeriod, 'U')">
                                                <from>
                                                    <xsl:value-of select="substring($curPeriod, 1, 10)"/>
                                                </from>
                                                <to>
                                                    <xsl:value-of select="substring($curPeriod, 12, 22)"/>
                                                </to>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <from>
                                                    <xsl:value-of select="$curPeriod"/>
                                                </from>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </period>

                                    <xsl:variable name="rate">
                                        <xsl:for-each select="$dateFilteredElements[$curCurrency = currencyCode and $curMinBalance = minBalance and $curPeriod = period ]">
                                            <xsl:sort data-type="number" order="descending" select="translate(./interestDateBegin, '.', '')"/>
                                            <xsl:if test="position() = 1">
                                                <xsl:value-of select="./interestRate"/>
                                            </xsl:if>
                                        </xsl:for-each>
                                    </xsl:variable>

                                    <xsl:if test="$rate != '' ">
                                        <interestRate>
                                            <xsl:value-of select="$rate"/>
                                        </interestRate>
                                    </xsl:if>

                                </condition>

                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise>

                            <condition>

                                <currency>
                                    <code>
                                        <xsl:value-of select="$curCurrency"/>
                                    </code>
                                    <name>
                                        <xsl:value-of select="mu:getCurrencySign($curCurrency)" disable-output-escaping="yes"/>
                                    </name>
                                </currency>

                                <amountOf>
                                    <xsl:value-of select="$curMinBalance"/>
                                </amountOf>

                                <xsl:variable name="rate">
                                    <xsl:for-each select="$dateFilteredElements[$curCurrency = currencyCode and $curMinBalance = minBalance]">
                                        <xsl:sort data-type="number" order="descending" select="translate(./interestDateBegin, '.', '')"/>
                                        <xsl:if test="position() = 1">
                                            <xsl:value-of select="./interestRate"/>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:variable>

                                <xsl:if test="$rate != '' ">
                                    <interestRate>
                                        <xsl:value-of select="$rate"/>
                                    </interestRate>
                                </xsl:if>

                            </condition>

                        </xsl:otherwise>
                     </xsl:choose>

              </xsl:for-each>
          </xsl:for-each>

        </conditionsList>

        <xsl:variable name="minDepositSubType">
            <xsl:call-template name="minNumber">
                <xsl:with-param name="list" select="$dateFilteredElements/id/text()"/>
            </xsl:call-template>
        </xsl:variable>

        <!-- Дополнительная информация по вкладу -->
        <xsl:variable name="incomingTransactions" select="$dateFilteredElements[id=$minDepositSubType]/incomingTransactions"/>
        <xsl:variable name="frequencyAdd" select="$dateFilteredElements[id=$minDepositSubType]/frequencyAdd"/>
        <xsl:variable name="debitTransactions" select="$dateFilteredElements[id=$minDepositSubType]/debitTransactions"/>
        <xsl:variable name="frequencyPercent" select="$dateFilteredElements[id=$minDepositSubType]/frequencyPercent"/>
        <xsl:variable name="percentOrder" select="$dateFilteredElements[id=$minDepositSubType]/percentOrder"/>
        <xsl:variable name="incomeOrder" select="$dateFilteredElements[id=$minDepositSubType]/incomeOrder"/>
        <xsl:variable name="renewals" select="$dateFilteredElements[id=$minDepositSubType]/renewals"/>

        <xsl:if test="not($incomingTransactions = '')">
            <incomingTransactions>
                <xsl:value-of select="$incomingTransactions"/>
            </incomingTransactions>
        </xsl:if>

        <minAditionalFee>

                <xsl:for-each select="$currencis">
                       <xsl:variable name="curCurrency" select="."/>
                        <currencyList>
                            <currency>
                                <code>
                                    <xsl:value-of select="$curCurrency"/>
                                </code>
                                <name>
                                    <xsl:value-of select="mu:getCurrencySign($curCurrency)" disable-output-escaping="yes"/>
                                </name>
                            </currency>
                            <amount>
                                <xsl:call-template name="minNumber">
                                    <xsl:with-param name="list" select="$dateFilteredElements[$curCurrency = currencyCode]/minAdditionalFee"/>
                                </xsl:call-template>
                            </amount>
                        </currencyList>
                </xsl:for-each>
        </minAditionalFee>
        <xsl:if test="not($frequencyAdd = '')">
            <frequencyAdd>
                <xsl:value-of select="$frequencyAdd"/>
            </frequencyAdd>
        </xsl:if>

        <xsl:if test="not($debitTransactions = '')">
            <debitTransactions>
                <xsl:value-of select="$debitTransactions"/>
            </debitTransactions>
        </xsl:if>

        <xsl:if test="not($frequencyPercent = '')">
            <frequencyPercent>
                <xsl:value-of select="$frequencyPercent"/>
            </frequencyPercent>
        </xsl:if>

        <xsl:if test="not($percentOrder = '')">
            <percentOrder>
                <xsl:value-of select="$percentOrder"/>
            </percentOrder>
        </xsl:if>

        <xsl:if test="not($incomeOrder = '')">
            <incomeOrder>
                <xsl:value-of select="$incomeOrder"/>
            </incomeOrder>
        </xsl:if>

        <xsl:if test="not($renewals = '')">
            <renewals>
                <xsl:value-of select="$renewals"/>
            </renewals>
        </xsl:if>
    </xsl:template>

    <xsl:template name="dataWithTariff" >
        <xsl:param name="dateFilteredElements"/>
        <xsl:param name="tariff"/>
        <xsl:choose>
            <xsl:when test="$tariff=0">
                <xsl:call-template name="data">
                    <xsl:with-param name="dateFilteredElements" select="$dateFilteredElements[tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3]"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="data">
                    <xsl:with-param name="dateFilteredElements" select="$dateFilteredElements[tariffPlanCode=$tariff]"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
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

</xsl:stylesheet>