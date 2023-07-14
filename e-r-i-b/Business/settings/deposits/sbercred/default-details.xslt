<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" encoding="windows-1251" indent="yes"/>

<xsl:template match="/">
	<table>
    <tr>
      <td><span class="infoTitle">Депозит&#160;"<xsl:value-of select="product/@name"/>"</span></td>
    </tr>
	<tr>
      <td>&#160;</td>
    </tr>
	<tr>
      <td><xsl:value-of select="product/@description"/></td>
    </tr>
	<tr>
      <td>&#160;</td>
    </tr>
  </table>

  <xsl:variable name="curs" select="product/dictionaries/entity-list/entity[../@name='currencies']"/>
	<xsl:variable name="elements" select="product/data/element"/>

  <xsl:for-each select="product/dictionaries/entity-list/entity[../@name='currencies' and @key=$elements/value[@field='currency']]">
	<table>
		 <tr>
			 <td><b>Валюта&#160;</b><xsl:value-of select="text()"/></td>
		 </tr>
	 </table>
	<span class="workspaceTitle"><xsl:value-of select="text()"/></span>


		<table class="userTab" cellspacing="0" cellpadding="2">
			<tr class="titleTable">
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
					<xsl:variable name="period" select="$current/value[@field='period']"/>
					<xsl:variable name="maxPeriod" select="$current/value[@field='maxPeriod']"/>
					<xsl:variable name="itemsCount" select="count($currentElement[./value[@field='period' and text()=$period] and ./value[@field='maxPeriod' and text()=$maxPeriod ]])"/>
					<xsl:variable name="precedingElements" select="preceding-sibling::element[value[@field='currency']/text()=$cur]"/>
					<xsl:variable name="prev" select="$precedingElements[count($precedingElements)]"/>
					<xsl:if test="position()=1 or (./value[@field='period']/text() != $prev/value[@field='period']/text() and ./value[@field='maxPeriod']/text() != $prev/value[@field='maxPeriod']/text())">
						<td class="ListItem" rowspan="{$itemsCount}">
							<!--Срок вклада-->
							<script type="text/javascript">
								document.write(formatTwoPeriods('<xsl:value-of select="$period"/>', '<xsl:value-of select="$maxPeriod"/>'))
							</script>
						</td>
					</xsl:if>
					<td class="ListItem">
						<!--Cумма вклада-->
						<xsl:if test="$current/value[@field='rest'] != ''">
							от&#160;<xsl:value-of select="$current/value[@field='rest']"/>
						</xsl:if>&#160;
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
		 </table>
	  <table>
		  <xsl:variable name="cur" select="@key"/>
			<xsl:variable name="current" select="$elements[value[@field='currency']/text()=$cur]"/>
		  <tr>
			<xsl:variable name="addit" select="$current/value[@field='additionalFee']"/>
			<td><b>Возможность пополнения &#160;</b></td>
			  <td>
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
		  <tr>
			<xsl:variable name="addit" select="$current/value[@field='additionalFee']"/>
			<xsl:if test="$addit = 'true'">
			<td><b>Минимальная сумма дополнительного взноса &#160;</b></td>
			  <td>
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
		    </xsl:if>
		  </tr>
		  <tr>
			  <td><b>Возможность снятия части вклада &#160;</b></td>
			  <td>
				<xsl:variable name="Removal" select="$current/value[@field='anticipatoryRemoval']"/>
				<xsl:choose>
					<xsl:when test="$Removal != ''">
						<xsl:if test="$Removal = 'true'"><xsl:value-of
								select="$current/value[@field='minimumAmount']"/></xsl:if>
						<xsl:if test="$Removal = 'false'">Нет</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			  </td>
		  </tr>
		  <tr>
			  <td><b>Автоматическая пролонгация &#160;</b></td>
			  <td>
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
		  <tr>
			  <td><b>Условия при досрочном расторжении вклада &#160;</b></td>
			  <td>
				<xsl:choose>
					<xsl:when test="$current/value[@field='conditionsPaymentPercent'] != ''">
						При досрочном расторжении вклада Банк начисляет проценты по ставке вклада 	
						<xsl:value-of select="$current/value[@field='conditionsPaymentPercent']"/>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			  </td>
		  </tr>
	  </table>

	 <table>
	    <tr><td>&#160;</td></tr>
	  </table>
  </xsl:for-each>
</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="description" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\WebAdmin\src\com\rssl\phizic\web\deposits\test.xml" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><advancedProp name="sInitialMode" value=""/><advancedProp name="bXsltOneIsOkay" value="true"/><advancedProp name="bSchemaAware" value="true"/><advancedProp name="bXml11" value="false"/><advancedProp name="iValidation" value="0"/><advancedProp name="bExtensions" value="true"/><advancedProp name="iWhitespace" value="0"/><advancedProp name="sInitialTemplate" value=""/><advancedProp name="bTinyTree" value="true"/><advancedProp name="bWarnings" value="true"/><advancedProp name="bUseDTD" value="false"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->