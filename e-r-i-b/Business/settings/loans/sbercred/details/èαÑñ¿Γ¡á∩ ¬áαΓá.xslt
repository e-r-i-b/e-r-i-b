<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

<xsl:import href="static://include/functions/set/set.distinct.function.xsl"/>

<xsl:output method="html" encoding="windows-1251" indent="yes"/>
<xsl:param name="selectedCurrency"/>
<xsl:variable name="dynamicConditions"
              select="loan-product/conditions/dynamic/condition"/>
<xsl:variable name="currentDynamicConditions"
              select="$dynamicConditions[value[@name='currency']/text()=$selectedCurrency]"/>

	<xsl:variable name="currencyName">
		<xsl:choose>
			<xsl:when test="$selectedCurrency = 'RUR'">руб.</xsl:when>
			<xsl:when test="$selectedCurrency = 'USD'">$</xsl:when>
			<xsl:when test="$selectedCurrency = 'EUR'">евро</xsl:when>
			<xsl:otherwise><xsl:value-of select="$selectedCurrency"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:template name="getDurations">
		<xsl:variable name="currentDurations"
		              select="$currentDynamicConditions[value[@name='duration']/text() != '']/value[@name='duration']"/>
		<xsl:for-each select="set:distinct($currentDurations)">
		    <xsl:sort select="substring-after(.,'M:')" data-type="number"/>
			<xsl:if test="position()!=1">, </xsl:if>
		    <xsl:value-of select="substring-after(.,'M:')"/>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="getMinMaxAmounts">
		<xsl:variable name="minAmounts"
		              select="$currentDynamicConditions[value[@name='minAmount']/text() != '']/value[@name='minAmount']/text()"/>
		<xsl:variable name="maxAmounts"
		              select="$currentDynamicConditions[value[@name='maxAmount']/text() != '']/value[@name='maxAmount']/text()"/>

		<xsl:for-each select="$minAmounts">
		  <xsl:sort data-type="number"/>
		  <xsl:if test="position()=1">От&#160;<xsl:value-of select="number(.)"/>&#160;</xsl:if>
		</xsl:for-each>
		<xsl:for-each select="$maxAmounts">
		  <xsl:sort data-type="number" order="descending"/>
		  <xsl:if test="position()=1">до&#160;<xsl:value-of select="number(.)"/>&#160;</xsl:if>
		</xsl:for-each>
		<xsl:if test="string-length($minAmounts) > 0 or string-length($maxAmounts) > 0">
			<xsl:value-of select="$currencyName"/>
		</xsl:if>
	</xsl:template>

	<xsl:template name="getCommissionRates">
		<xsl:variable name="currentConds"
		              select="$currentDynamicConditions[value[@name='commissionRate']/text() != '']"/>
		<xsl:for-each select="$currentConds">
			<xsl:sort select="./value[@name='commissionRate']/text()" data-type="number"/>
			<xsl:if test="position()!=1">, </xsl:if>
			<xsl:value-of select="number(./value[@name='commissionRate']/text())"/>%
			<xsl:variable name="commissionBase" select="./value[@name='commissionBase']/text()"/>
			<xsl:if test="string-length($commissionBase) > 0">
				<xsl:choose>
					<xsl:when test="substring($commissionBase, 1, 1) = '1'">от суммы кредита</xsl:when>
					<xsl:when test="substring($commissionBase, 1, 1) = '2'">от задолженности</xsl:when>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="getValues">
		<xsl:param name="valueName"/>
		<xsl:param name="type"/>
		<xsl:variable name="elements"
		              select="$currentDynamicConditions[value[@name=$valueName]/text() != '']/value[@name=$valueName]"/>
		<xsl:for-each select="set:distinct($elements)">
		   <xsl:sort select="." data-type="number"/>
		   <xsl:if test="position()!=1">, </xsl:if>
		   <xsl:value-of select="number(.)"/>
			<xsl:choose>
				<xsl:when test="$type = 'percents'">%</xsl:when>
				<xsl:when test="$type = 'currencies'">&#160;<xsl:value-of select="$currencyName"/></xsl:when>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/">
		<script>
			var row_number=0;
		</script>
		<table>
			<tr>
				<td><span class="infoTitle">Кредит&#160;"<xsl:value-of select="loan-product/@name"/>"</span></td>
		    </tr>
		    <tr><td>&#160;</td></tr>
		    <tr>
				<td><span class="infoTitle">Валюта кредита:</span>&#160;
					<xsl:choose>
					    <xsl:when test="$selectedCurrency = 'RUR'">Рубли</xsl:when>
					    <xsl:when test="$selectedCurrency = 'USD'">Доллары США</xsl:when>
					    <xsl:when test="$selectedCurrency = 'EUR'">Евро</xsl:when>
					    <xsl:otherwise><xsl:value-of select="$selectedCurrency"/></xsl:otherwise>
				  </xsl:choose>
				</td>
		    </tr>
		    <tr><td>&#160;</td></tr>
		</table>

	    <table cellspacing="0" cellpadding="2" class="userTab MaxSize">
		    <tr class="titleTable" align="center">
			    <td width="3%">№</td>
			    <td width="17%">Типы комиссий и дополнительные условия</td>
			    <td width="25%">Значение</td>
			    <td width="25%">Значение для постоянных клиентов</td>
			    <td width="30%">Примечание</td>
		    </tr>

			<xsl:call-template name="out-row">
			   <xsl:with-param name="description">Размер кредита</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:call-template name="getMinMaxAmounts"/>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				   <xsl:call-template name="getMinMaxAmounts"/>
			   </xsl:with-param>
			   <xsl:with-param name="hint">Лимит кредита устанавливается в течение 1 (одного) рабочего дня после получения Карты</xsl:with-param>
		    </xsl:call-template>

		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Срок предоставления карты, месяцев</xsl:with-param>
			    <xsl:with-param name="value1">
					<xsl:call-template name="getDurations"/>&#160;(с последующим выпуском новой карты)
			    </xsl:with-param>
			    <xsl:with-param name="value2">
					<xsl:call-template name="getDurations"/>&#160;(с последующим выпуском новой карты)
				</xsl:with-param>
			    <xsl:with-param name="hint">
				    Выдача новой карты возможна при соблюдении следующих требований:
				    - погашение в полном объеме задолженности по действующей карте;
				    - выполнение держателем всех обязательств (отсутствие просроченных платежей в течение всего периода действия Карты).
				    В случае наличия просроченных платежей новая Карта может быть предоставлена Держателю по усмотрению Банка.
			    </xsl:with-param>
		    </xsl:call-template>

			<xsl:variable name="currentRate"
			              select="$currentDynamicConditions[value[@name='rate']/text() != '']/value[@name='rate']"/>
		    <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Ставка по кредиту(годовых)</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:for-each select="set:distinct($currentRate)">
					   <xsl:sort select="." data-type="number"/><xsl:if test="position()!=1">, </xsl:if>
					   <xsl:value-of select="number(.)"/>%
				   </xsl:for-each>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				    <xsl:for-each select="set:distinct($currentRate)">
					    <xsl:sort select="." data-type="number"/><xsl:if test="position()!=1">, </xsl:if>
						<xsl:value-of select="number(.)-number(../value[@name='reducingPatronRate']/text())"/>%
					</xsl:for-each>
			    </xsl:with-param>
				<xsl:with-param name="hint">Проценты начисляются на сумму задолженности</xsl:with-param>
		    </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param
					   name="description">Комиссия за обслуживание счета карты в течение 1-го года</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">serviceCommissionFirstYear</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">serviceCommissionFirstYear</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param
					   name="hint">Списывается в течение 10 дней от даты установления лимита</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param
					   name="description">Комиссия за обслуживание счета карты, начиная со 2-го года</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">serviceCommissionSecondYear</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">serviceCommissionSecondYear</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="hint">Списывается со СК</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param
					   name="description">Дополнительная комиссия за срочный выпуск карты</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">expressMakingCardCommission</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">expressMakingCardCommission</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="hint">Списывается со СК</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Процентная ставка за несвоевременное исполнение обязательств по погашению кредита (годовых)</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">penaltyPerDay</xsl:with-param>
					   <xsl:with-param name="type">percents</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">penaltyPerDay</xsl:with-param>
					   <xsl:with-param name="type">percents</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="hint">Начисляется с момента нарушения срока погашения задолженности по договору и действует до момента исполнение обязательств по погашению кредита</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Штраф за возникновение сверхлимитной (технической) задолженности</xsl:with-param>
			   <xsl:with-param name="value1">20% от суммы технической задолженности</xsl:with-param>
			   <xsl:with-param name="value2">20% от суммы технической задолженности</xsl:with-param>
			   <xsl:with-param name="hint">Техническая задолженность и штрафы подлежат уплате не позднее 15 календарных дней</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Штраф за несвоевременное погашение сверхлимитной (технической) задолженности</xsl:with-param>
			   <xsl:with-param name="value1">50% от суммы технической задолженности</xsl:with-param>
			   <xsl:with-param name="value2">50% от суммы технической задолженности</xsl:with-param>
			   <xsl:with-param name="hint">Банк вправе начислить и списать штрафы в момент поступления денежных средств на СК</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Комиссия за операцию получения наличных денежных средств с использованием Карты в ПВН и банкоматах АКБ "Банк Сбережений и Кредита" ЗАО</xsl:with-param>
			   <xsl:with-param name="value1">3% (но не менее $5, 5 евро, 150 руб.)</xsl:with-param>
			   <xsl:with-param name="value2">3% (но не менее $5, 5 евро, 150 руб.)</xsl:with-param>
			   <xsl:with-param
					   name="hint">Взимается при списании суммы операции со счета карты</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param
					   name="description">Пополнение счета кредитной карты в валюте счета</xsl:with-param>
			   <xsl:with-param name="value1">бесплатно</xsl:with-param>
			   <xsl:with-param name="value2">бесплатно</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Комиссия за операцию безналичной оплаты товаров и услуг с использованием кредитной карты</xsl:with-param>
			   <xsl:with-param name="value1">0%</xsl:with-param>
			   <xsl:with-param name="value2">0%</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Конверсия при осуществлении операций по кредитной карте в валюте, отличной от валюты счета кредитной карты</xsl:with-param>
			   <xsl:with-param
					   name="value1">При ведении счета карты в рублях РФ: Курс ЦБ РФ + 0,3%</xsl:with-param>
			   <xsl:with-param
					   name="value2">При ведении счета карты в рублях РФ: Курс ЦБ РФ + 0,3%</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Льготный период кредитования</xsl:with-param>
			   <xsl:with-param name="value1">до 50 дней</xsl:with-param>
			   <xsl:with-param name="value2">до 50 дней</xsl:with-param>
		   </xsl:call-template>

		   <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Дополнительные условия</xsl:with-param>
			   <xsl:with-param name="value1">По требованию Банка</xsl:with-param>
			   <xsl:with-param name="value2">По требованию Банка</xsl:with-param>
			   <xsl:with-param name="hint">Банк вправе вызвать заемщика на собеседование с предоставлением дополнительных документов подтверждающих платежеспособность заемщика</xsl:with-param>
		   </xsl:call-template>
		</table>
	</xsl:template>

	<xsl:template name="out-row">
		<xsl:param name="description"/>
		<xsl:param name="value1"/>
		<xsl:param name="value2"/>
		<xsl:param name="hint"/>
		<xsl:if test="string-length($value1)>0 or string-length(value2)>0">
			<tr>
		        <td class="ListItem"><script>document.write(++row_number);</script></td>
				<td class="ListItem"><xsl:value-of select="$description"/>&#160;</td>
				<td class="ListItem"><xsl:value-of select="$value1"/>&#160;</td>
				<td class="ListItem"><xsl:value-of select="$value2"/>&#160;</td>
				<td class="ListItem"><xsl:value-of select="$hint"/>&#160;</td>
			</tr>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>