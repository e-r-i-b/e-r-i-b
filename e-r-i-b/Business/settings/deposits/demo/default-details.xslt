<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

<xsl:import href="static://include/functions/set/set.distinct.function.xsl"/>


<xsl:output method="html" encoding="windows-1251" indent="yes"/>

<xsl:template match="/">


  <xsl:variable name="curs" select="product/dictionaries/entity-list/entity[../@name='currencies']"/>
  <xsl:variable name="elements" select="product/data/element"/>
  <xsl:for-each select="$curs">
	<tr>
		<td colspan="5"><span class="tblGroupTitle"><b>Валюта:&#160;</b><xsl:value-of select="text()"/></span></td>
	</tr>
	<tr class="tblInfHeader">
		<td width="20%">Срок вклада</td>
		<td width="30%">Cумма вклада</td>
		<td width="15%">% ставка (годовых)</td>
		<td width="20%">Периодичность выплаты процентов</td>
		<td width="15%">Капитализация процентов</td>
	</tr>
		<xsl:variable name="cur" select="@key"/>
		<xsl:variable name="currentElement" select="$elements[value[@field='currency']/text()=$cur]"/>
		<xsl:for-each select="$currentElement">
			<xsl:sort select="./value[@field='period']" data-type="text"/>
			<xsl:variable name="current" select="."/>
			<tr>
				<td class="ListItem">
					<!--Срок вклада-->
					<xsl:variable name="period" select="$current/value[@field='period']"/>
					<xsl:variable name="maxPeriod" select="$current/value[@field='maxPeriod']"/>
					<script type="text/javascript">
						document.write(formatTwoPeriods('<xsl:value-of select="$period"/>', '<xsl:value-of
							select="$maxPeriod"/>'))
					</script>
				</td>
				<td class="ListItem">
					<!--Сумма вклада-->
					<xsl:if test="$current/value[@field='minimumAmount'] != ''">
						от&#160;<xsl:value-of select="$current/value[@field='minimumAmount']"/>
					</xsl:if>
					<xsl:if test="$current/value[@field='maximumAmount'] != ''">
						&#160;до&#160;<xsl:value-of select="$current/value[@field='maximumAmount']"/>
					</xsl:if>
				</td>
				<td class="ListItem">
					<!--Процентная ставка (% годовых)-->
					&#160;<xsl:value-of select="$current/value[@field='interestRate']"/>
				</td>
				<td class="ListItem">
					<!--Периодичность выплаты процентов-->
					<xsl:variable name="procents" select="$current/value[@field='paymentPeriod']"/>
					<xsl:choose>
						<xsl:when test="$procents != ''">
							<xsl:value-of select="$procents"/>
						</xsl:when>
						<xsl:otherwise>
							&#160;-
						</xsl:otherwise>
					</xsl:choose>
				</td>
				<td class="ListItem">
					<!--Капитализация процентов-->
					<xsl:variable name="capipaliz" select="$current/value[@field='capitalization']"/>
					<xsl:choose>
						<xsl:when test="$capipaliz != ''">
							<xsl:if test="$capipaliz = 'true'">Да</xsl:if>
							<xsl:if test="$capipaliz = 'false'">Нет</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							&#160;-
						</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
		</xsl:for-each>

	    <xsl:variable name="cur" select="@key"/>
		<xsl:variable name="currentElement" select="$elements[value[@field='currency']/text()=$cur]"/>
		<xsl:for-each select="$currentElement">
			<xsl:sort select="./value[@field='period']" data-type="text"/>
			<xsl:variable name="current" select="."/>
	    <tr class="ListLine0">
			<xsl:variable name="addit" select="$current/value[@field='additionalFee']"/>
			<td colspan="2"><b>Возможность пополнения &#160;</b></td>
			<td colspan="3">
			     <xsl:choose>
					<xsl:when test="$addit != ''">
						<xsl:if test="$addit = 'true'">Да</xsl:if>
						<xsl:if test="$addit = 'false'">Нет</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			</td>
		  </tr>
		  <tr class="ListLine0">
			<xsl:variable name="addit" select="$current/value[@field='additionalFee']"/>
			<td colspan="2"><b>Минимальная сумма дополнительного взноса &#160;</b></td>
			<td colspan="3">
			    <xsl:variable name="minAddit" select="$current/value[@field='minAdditionalFee']"/>
				<xsl:choose>
					<xsl:when test="$addit != ''">
						<xsl:choose>
							<xsl:when test="$minAddit != ''">
									<xsl:value-of select="$minAddit"/>
							</xsl:when>
							<xsl:otherwise>
								&#160;Без ограничения
							</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="$minAddit = 'true'">Да</xsl:if>
						<xsl:if test="$minAddit = 'false'">Нет</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			</td>
		  </tr>
		  <tr class="ListLine0">
			  <td colspan="2"><b>Возможность снятия части вклада &#160;</b></td>
			  <td colspan="3">
				<xsl:variable name="removal" select="$current/value[@field='anticipatoryRemoval']"/>
				<xsl:choose>
					<xsl:when test="$removal != ''">
						<xsl:if test="$removal = 'true'">Да</xsl:if>
						<xsl:if test="$removal = 'false'">Нет</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			  </td>
		  </tr>
		  <tr class="ListLine0">
			  <td colspan="2"><b>Автоматическая пролонгация &#160;</b></td>
			  <td colspan="3">
				<xsl:variable name="prolong" select="$current/value[@field='renewal']"/>
				<xsl:choose>
					<xsl:when test="$prolong != ''">
						<xsl:if test="$prolong = 'true'">Да</xsl:if>
						<xsl:if test="$prolong = 'false'">Нет</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			  </td>
		  </tr>
		  <tr class="ListLine0">
			  <td colspan="2"><b>Условия при досрочном расторжении вклада &#160;</b></td>
			  <td colspan="3">
				<xsl:choose>
					<xsl:when test="$current/value[@field='conditionsPaymentPercent'] != ''">
						<xsl:value-of select="$current/value[@field='conditionsPaymentPercent']"/>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			  </td>
		  </tr>
		  </xsl:for-each>
  </xsl:for-each>
</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="description" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\WebAdmin\src\com\rssl\phizic\web\deposits\test.xml" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><advancedProp name="sInitialMode" value=""/><advancedProp name="bXsltOneIsOkay" value="true"/><advancedProp name="bSchemaAware" value="true"/><advancedProp name="bXml11" value="false"/><advancedProp name="iValidation" value="0"/><advancedProp name="bExtensions" value="true"/><advancedProp name="iWhitespace" value="0"/><advancedProp name="sInitialTemplate" value=""/><advancedProp name="bTinyTree" value="true"/><advancedProp name="bWarnings" value="true"/><advancedProp name="bUseDTD" value="false"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->