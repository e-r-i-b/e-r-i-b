<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

<xsl:import href="static://include/functions/set/set.distinct.function.xsl"/>

<xsl:output method="html" encoding="windows-1251" indent="yes"/>
<xsl:param name="selectedCurrency"/>
<xsl:param name="productName"/>
<xsl:param name="productDescription"/>
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


	    <tr class="tblInfHeader" align="center">
			    <td width="3%">№</td>
			    <td width="17%">Типы комиссий и дополнительные условия</td>
			    <td width="25%">Значение</td>
			    <td width="25%">Значение для зарплатных клиентов</td>
			    <td width="30%">Примечание</td>
		    </tr>
	        <tr>
				<td colspan="5"><span class="tblGroupTitle">Валюта кредита:</span>&#160;
					<xsl:choose>
					    <xsl:when test="$selectedCurrency = 'RUR'">Рубли</xsl:when>
					    <xsl:when test="$selectedCurrency = 'USD'">Доллары США</xsl:when>
					    <xsl:when test="$selectedCurrency = 'EUR'">Евро</xsl:when>
					    <xsl:otherwise><xsl:value-of select="$selectedCurrency"/></xsl:otherwise>
				  </xsl:choose>
				</td>
		    </tr>

		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Размер кредита</xsl:with-param>
			    <xsl:with-param name="value1">
					<xsl:call-template name="getMinMaxAmounts"/>
			    </xsl:with-param>
			    <xsl:with-param name="value2">
					<xsl:call-template name="getMinMaxAmounts"/>
				</xsl:with-param>
		    </xsl:call-template>

		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Сроки кредитования, месяцев</xsl:with-param>
			    <xsl:with-param name="value1">
					<xsl:call-template name="getDurations"/>
			    </xsl:with-param>
			    <xsl:with-param name="value2">
					<xsl:call-template name="getDurations"/>
				</xsl:with-param>
		    </xsl:call-template>

		    <xsl:variable name="currentRate"
		                  select="$currentDynamicConditions[value[@name='rate']/text() != '']/value[@name='rate']"/>
		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Ставка по кредиту</xsl:with-param>
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
			</xsl:call-template>

		    <xsl:variable name="initInstalmentsString">
			    <xsl:call-template name="getValues">
				    <xsl:with-param name="valueName">minInitialInstalment</xsl:with-param>
					<xsl:with-param name="type">percents</xsl:with-param>
				</xsl:call-template>
			</xsl:variable>

		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Первоначальный взнос</xsl:with-param>
			    <xsl:with-param name="value1">
				    <xsl:if test="$initInstalmentsString != ''">от </xsl:if>
				    <xsl:value-of select="$initInstalmentsString"/>
			    </xsl:with-param>
			    <xsl:with-param name="value2">
				    <xsl:if test="$initInstalmentsString != ''">от </xsl:if>
				    <xsl:value-of select="$initInstalmentsString"/>
			    </xsl:with-param>
				<xsl:with-param name="hint">
					Первоначальный взнос устанавливается Банком в зависимости от категории товаров,
					приобретаемых Заемщиками с использованием Кредита, для каждой Торговой организации,
					но не менее указанной величины
				</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="out-row">
				<xsl:with-param name="description">Комиссия за ведение ссудного счета</xsl:with-param>
				<xsl:with-param name="value1">
					<xsl:call-template name="getCommissionRates"/>
				</xsl:with-param>
				<xsl:with-param name="value2">
					<xsl:call-template name="getCommissionRates"/>
				</xsl:with-param>
				<xsl:with-param name="hint">Рассчитывается от суммы кредита и взимается ежемесячно в составе очередного платежа</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Комиссия за рассмотрение заявки</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">claimReviewCommission</xsl:with-param>
					   <xsl:with-param name="type">percents</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">claimReviewCommission</xsl:with-param>
					   <xsl:with-param name="type">percents</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
		    </xsl:call-template>

			<xsl:call-template name="out-row">
				<xsl:with-param name="description">Единовременная комиссия за выдачу кредита</xsl:with-param>
				<xsl:with-param name="value1">
					<xsl:call-template name="getValues">
						<xsl:with-param name="valueName">openAccountCommission</xsl:with-param>
						<xsl:with-param name="type">currencies</xsl:with-param>
					</xsl:call-template>
				</xsl:with-param>
				<xsl:with-param name="value2">
					<xsl:call-template name="getValues">
						<xsl:with-param name="valueName">openAccountCommission</xsl:with-param>
						<xsl:with-param name="type">currencies</xsl:with-param>
					</xsl:call-template>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="out-row">
			   <xsl:with-param name="description">Штраф за 1-ый пропуск очередного платежа</xsl:with-param>
			   <xsl:with-param name="value1">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">firstDelayPenalty</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
				   </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="value2">
				   <xsl:call-template name="getValues">
					   <xsl:with-param name="valueName">firstDelayPenalty</xsl:with-param>
					   <xsl:with-param name="type">currencies</xsl:with-param>
			       </xsl:call-template>
			   </xsl:with-param>
			   <xsl:with-param name="hint">
				   Первым пропущенным платежом считается неоплата (неполная оплата) очередного платежа
				   в установленные Графиком платежей сроки. Первый пропущенный платеж является оплаченным
				   полностью после списания Банком со счета до следующей даты платежа по Графику платежей
				   суммы денежных средств, включая штраф и просроченный платеж
			   </xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Штраф за 2-ой пропуск очередного платежа</xsl:with-param>
			    <xsl:with-param name="value1">
				    <xsl:call-template name="getValues">
						<xsl:with-param name="valueName">secondDelayPenalty</xsl:with-param>
						<xsl:with-param name="type">currencies</xsl:with-param>
				    </xsl:call-template>
			    </xsl:with-param>
			    <xsl:with-param name="value2">
				    <xsl:call-template name="getValues">
					    <xsl:with-param name="valueName">secondDelayPenalty</xsl:with-param>
					    <xsl:with-param name="type">currencies</xsl:with-param>
			        </xsl:call-template>
			    </xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Штраф за любой последующий пропуск очередного платежа</xsl:with-param>
			    <xsl:with-param name="value1">
				    <xsl:call-template name="getValues">
						<xsl:with-param name="valueName">thirdDelayPenalty</xsl:with-param>
						<xsl:with-param name="type">currencies</xsl:with-param>
				    </xsl:call-template>
			    </xsl:with-param>
			    <xsl:with-param name="value2">
				    <xsl:call-template name="getValues">
					    <xsl:with-param name="valueName">thirdDelayPenalty</xsl:with-param>
					    <xsl:with-param name="type">currencies</xsl:with-param>
			        </xsl:call-template>
			    </xsl:with-param>
			    <xsl:with-param name="hint">
				    Любым последующим пропуском подряд считается неоплата (неполная оплата) очередного платежа
				    в установленные Графиком платежей сроки при уже имеющемся одном неоплаченном (не полностью
				    оплаченном) очередном платеже. Любой последующий пропуск подряд является основанием для
				    требования Банком полного досрочного погашения Кредита
				</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="out-row">
				<xsl:with-param
						name="description">Неустойка от суммы задолженности по основному долгу в день</xsl:with-param>
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
				<xsl:with-param name="hint">Неустойка взимается за просрочку исполнения требования Банка о досрочном погашении Кредита</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="out-row">
			    <xsl:with-param name="description">Страхование</xsl:with-param>
			    <xsl:with-param name="value1">Срок страхования, страховая сумма и страховая премия устанавливается в соответствии с Тарифами Страховой компании</xsl:with-param>
				<xsl:with-param name="value2">Срок страхования, страховая сумма и страховая премия устанавливается в соответствии с Тарифами Страховой компании</xsl:with-param>
			    <xsl:with-param name="hint">Страхование жизни и трудоспособности Заемщика</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="out-row">
				<xsl:with-param name="description">Процентная ставка по кредиту до востребования "Потребительский", % годовых</xsl:with-param>
				<xsl:with-param name="value1">0,00001%</xsl:with-param>
				<xsl:with-param name="value2">0,00001%</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="out-row">
				<xsl:with-param name="description">Дополнительные условия</xsl:with-param>
				<xsl:with-param name="value1">По требованию Банка</xsl:with-param>
				<xsl:with-param name="value2">По требованию Банка</xsl:with-param>
				<xsl:with-param name="hint">Банк вправе вызвать заемщика на собеседование с предоставлением дополнительных документов подтверждающих платежеспособность заемщика</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="out-row">
				<xsl:with-param name="description">Обеспечение по кредиту</xsl:with-param>
				<xsl:with-param name="value1">Поручительство физического лица</xsl:with-param>
				<xsl:with-param name="value2">Поручительство физического лица</xsl:with-param>
				<xsl:with-param name="hint"></xsl:with-param>
			</xsl:call-template>
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
