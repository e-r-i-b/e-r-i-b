<?xml version="1.0" encoding="windows-1251"?>

<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" encoding="windows-1251"/>

	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="detailsUrl" select="'details.do?id='"/>

	<xsl:template match="/">
		<span class="workspaceTitle" height="40px">Вклады банка</span>
		<table class="userTab" cellspacing="0" cellpadding="2" width="100%">
			<tr class="titleTable">
				<td width="15%">Срок вклада</td>
				<td width="30%">Cумма вклада</td>
				<td width="10%">Процентная ставка (% годовых)</td>
				<td width="10%">Периодичность выплаты процентов</td>
				<td width="10%">Возможность пополнения вклада</td>
				<td width="10%">Возможность снятия части вклада</td>
				<td width="15%">Описание</td>
			</tr>
			<xsl:apply-templates select="//product"/>
		</table>
	</xsl:template>

	<xsl:template match="product">
		<xsl:variable name="currentProduct" select="."/>
		<xsl:variable name="entityListPeriods" select="dictionaries/entity-list[@name='paymentPeriods']/entity"/>
		<xsl:variable name="elements" select="./data/element"/>
		<tr>
			<td class="ListItem" style="background-color:#F8FBFD;" colspan="7" align="center">
				<table cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td align="left" width="5%">&#160;
						</td>
						<td align="left" width="95%">
							Депозит&#160;"<xsl:value-of select="@name"/>"&#160;
							<a href="{concat($detailsUrl,@id)}">
								подробнее...
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<xsl:for-each select="$elements">
		<xsl:sort select="./value[@field='period']" data-type="text"/>
		<xsl:variable name="countRow" select="count($elements)"/>
		<xsl:variable name="current" select="."/>
        <tr>
			<xsl:variable name="period" select="$current/value[@field='period']"/>
			<xsl:variable name="maxPeriod" select="$current/value[@field='maxPeriod']"/>
			<xsl:variable name="itemsCount" select="count($elements[./value[@field='period' and text()=$period] and ./value[@field='maxPeriod' and text()=$maxPeriod]])"/>
			<xsl:variable name="precedingElements" select="preceding-sibling::element"/>
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
					&#160;<xsl:value-of select="$current/value[@field='currency']"/>
				</xsl:if>
				&#160;
			</td>
			<td class="ListItem">
				<!--Процентная ставка (% годовых)-->
				&#160;<xsl:value-of select="$current/value[@field='interestRate']"/>
			</td>
			<td class="ListItem">
				<!--Периодичность выплаты процентов-->
				<xsl:variable name="procents" select="$entityListPeriods[@key = $current/value[@field='paymentPeriod']]"/>
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
				<!--Возможность пополнения-->
				<xsl:variable name="addit" select="$current/value[@field='additionalFee']"/>
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
			<td class="ListItem">
				<!--Возможность снятия части вклада-->
				<xsl:variable name="Removal" select="$current/value[@field='anticipatoryRemoval']"/>
				<xsl:choose>
					<xsl:when test="$Removal != ''">
						<xsl:if test="$Removal = 'true'">Да</xsl:if>
						<xsl:if test="$Removal = 'false'">Нет</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						&#160;-
					</xsl:otherwise>
				</xsl:choose>
			</td>
	        <xsl:if test="position()=1">
			<td class="ListItem" rowspan="{$countRow}">
				<!--Описание-->
					<xsl:choose>
					<xsl:when test="$currentProduct/@productDescription != ''">
						<xsl:value-of select="$currentProduct/@productDescription"/>
					</xsl:when>
					<xsl:otherwise>
						&#160;
					</xsl:otherwise>
				</xsl:choose>
			</td>
	        </xsl:if>
		</tr>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\..\..\..\WebAdmin\src\com\rssl\phizic\web\deposits\test.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\products\Доходный\description.xml" srcSchemaRoot="product" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="table/xsl:apply&#x2D;templates" x="156" y="18"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->