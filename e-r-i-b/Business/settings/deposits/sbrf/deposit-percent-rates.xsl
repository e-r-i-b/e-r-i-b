<?xml version="1.0" encoding="windows-1251"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan">

	<xsl:output method="html" indent="yes" encoding="windows-1251" version="1.0"/>

	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

	<xsl:param name="currentDate"/>
	<xsl:param name="type"               select="''"/> <!-- Вид счета -->
	<xsl:param name="currency"           select="''"/> <!-- Код валюты (RUB, EUR...) -->
	<xsl:param name="clientCategoryCode" select="''"/> <!-- Код категории клиента -->
	<xsl:param name="termsTemplateId"    select="''"/>

	<xsl:variable name="translatedDate"  select="translate($currentDate, '.', '')"/>

	<xsl:template match="/product">
		<xsl:choose>
			<xsl:when test="$type = ''"/>
			<xsl:when test="$type = 61">
				<xsl:call-template name="type_61"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="type_61">
		<xsl:variable name="elements" select="./data/options/element[$translatedDate &gt;= translate(dateBegin, '.', '') and $translatedDate &lt; translate(dateEnd, '.', '') and currencyCode = $currency and tariffPlanCode = $clientCategoryCode and tariffPlanAgreementId = $termsTemplateId]"/>

        <!--Определить минимальные суммы-->
        <xsl:variable name="minBalances" select="xalan:distinct($elements/minBalance/text())"/>
        <xsl:for-each select="$minBalances">
            <xsl:variable name="minBalance" select="."/>
            <xsl:variable name="minBalanceElements" select="$elements[minBalance = $minBalance]"/>

            <!--Для каждой из минимальных сумм определить максимальные. В итоге получим набор элементов, относящихся к одному диапазону сумм вклада-->
            <xsl:for-each select="xalan:distinct($minBalanceElements/maxBalance/text())">
                <xsl:variable name="minMaxBalance" select="."/>
                <xsl:variable name="minMaxBalanceElements" select="$minBalanceElements[maxBalance = $minMaxBalance]"/>

                <!--Из полученного набора элементов выбрать ставку/ставки с максимально поздним началом действия процентной ставки (interestDateBegin)-->
                <xsl:variable name="maxInterestDate">
                    <xsl:call-template name="max">
                        <xsl:with-param name="list" select="$minMaxBalanceElements/interestDateBegin/text()"/>
                    </xsl:call-template>
                </xsl:variable>

                <!--Теоретически могут быть еще какие-то признаки, по которым будут различаться ставки. Поэтому используем перебор-->
                <xsl:for-each select="$minMaxBalanceElements[translate($maxInterestDate, '.', '') = translate(interestDateBegin, '.', '')]">
                    <xsl:variable name="element"      select="."/>
                    <xsl:variable name="interestRate" select="$element/interestRate/text()"/>

                    <tr>
                        <td class="textNobr" align="center">
                            от
                            <xsl:value-of select="format-number(number($minBalance), '# ##0', 'sbrf')"/>
                            до
                            <xsl:choose>
                                <xsl:when test="$minMaxBalance &lt; 999999999999">
                                    <xsl:variable select="number($minMaxBalance) + 0.01" name="prepared"/>
                                    <xsl:value-of select="format-number($prepared, '# ##0', 'sbrf')"/>
                                </xsl:when>
                            </xsl:choose>
                        </td>
                        <td class="textNobr" align="center">
                            <xsl:value-of select="$interestRate"/>%
                        </td>
                    </tr>
                </xsl:for-each>

            </xsl:for-each>
        </xsl:for-each>
	</xsl:template>

    <xsl:template name="max">
		<xsl:param name="list"/>
		<xsl:for-each select="$list">
			<xsl:sort order="descending"/>
			<xsl:if test="position() = 1">
				<xsl:value-of select="."/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>