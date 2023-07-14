<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

<xsl:import href="static://include/functions/set/set.distinct.function.xsl"/>

<xsl:output method="html" encoding="windows-1251" indent="yes"/>

<xsl:template match="/">

  <xsl:variable name="elements" select="/product/data/element"/>
  <xsl:variable name="curs" select="/product/dictionaries/entity-list/entity[../@name='currencies']"/>
  <xsl:variable name="periodDict" select="/product/dictionaries/entity-list/entity[../@name='paymentPeriods']"/>
  <xsl:variable name="flagDict" select="/product/dictionaries/entity-list/entity[../@name='flag']"/>

		<table>
		<tr>
		  <td><xsl:value-of select="product/@productDescription"/></td>
		</tr>
		<tr>
		  <td>&#160;</td>
		</tr>
		</table>

  <xsl:for-each select="$curs">
	<span class="workspaceTitle"><xsl:value-of select="text()"/></span>

	<xsl:variable name="cur" select="@key"/>
	<xsl:variable name="currentElement" select="$elements[value[@field='currency']/text()=$cur]"/>
	<xsl:variable name="addit" select="$currentElement/value[@field='additionalFee']"/>

		<table class="userTab" cellspacing="0" cellpadding="2" style="width:100%">
			<tr class="titleTable">
				<td><nowrap>Срок вклада</nowrap></td>
				<td><nowrap>Cумма вклада</nowrap></td>
				<td><nowrap>% ставка (годовых)</nowrap></td>
				<td><nowrap>Периодичность выплаты процентов</nowrap></td>
				<td><nowrap>Капитализация процентов</nowrap></td>
			</tr>
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
						<!--Cумма вклада-->
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
						<xsl:variable name="capitaliz" select="$current/value[@field='capitalization']"/>
						<xsl:choose>
							<xsl:when test="$capitaliz != ''">
								<xsl:if test="$capitaliz = 'true'">Да</xsl:if>
								<xsl:if test="$capitaliz = 'false'">Нет</xsl:if>
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
		  <tr>
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
			<td><b>Минимальная сумма дополнительного взноса &#160;</b></td>
			  <td>
			    <xsl:variable name="minAddit" select="$currentElement/value[@field='minAdditionalFee']"/>
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
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			</td>
		  </tr>
		  <tr>
			  <td><b>Возможность снятия части вклада &#160;</b></td>
			  <td>
				<xsl:variable name="removal" select="$currentElement/value[@field='anticipatoryRemoval']"/>
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
		  <tr>
			  <td><b>Автоматическая пролонгация &#160;</b></td>
			  <td>
				<xsl:variable name="prolong" select="$currentElement/value[@field='renewal']"/>
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
					<xsl:when test="$currentElement/value[@field='conditionsPaymentPercent'] != ''">
						<xsl:value-of select="$currentElement/value[@field='conditionsPaymentPercent']"/>
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

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="description" userelativepaths="yes" externalpreview="no" url="description.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->