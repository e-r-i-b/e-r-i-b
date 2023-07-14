<?xml version="1.0" encoding="windows-1251"?>

<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" encoding="windows-1251"/>

	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="detailsUrl" select="'details?id='"/>

	<xsl:template match="/">
		<span class="workspaceTitle" height="40px">Вклады банка</span>
		<table class="userTab" cellspacing="0" cellpadding="2" width="100%">
			<tr class="titleTable">
				<td>Cумма вклада</td>
				<td>Валюта вклада</td>
				<td>Срок вклада</td>
				<td>Процентная ставка (% годовых)</td>
			</tr>
			<xsl:apply-templates select="//product"/>
		</table>
	</xsl:template>

	<xsl:template match="product">
		<xsl:variable name="product" select="."/>
		<xsl:variable name="currencies" select="dictionaries/entity-list[@name='currencies']/entity/@key"/>
		<tr>
			<td class="ListItem" style="background-color:#F8FBFD;" colspan="4" align="center">
				<table cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td width="20%">&#160;</td>
						<td align="center">
							<a href="{concat($detailsUrl,@id)}">
								<xsl:value-of select="@name"/>
							</a>
						</td>
						<td align="right" width="20%">
							<a href="{concat($detailsUrl,@id)}">
								подробнее...
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="ListItem">
				<xsl:for-each select="$currencies">

					<xsl:variable name="curr" select="."/>
					<xsl:variable
							name="minimumAmounts"
							select="$product/data/element[value[@field='currency']/text()=$curr]/value[@field='minimumAmount']"/>
					<xsl:variable
							name="maximumAmounts"
							select="$product/data/element[value[@field='currency']/text()=$curr]/value[@field='maximumAmount']"/>
					<xsl:for-each select="$minimumAmounts">
						<xsl:sort data-type="number"/>
						<xsl:if test="position()=1">
							от
							<xsl:value-of select="number(.)"/>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:for-each>
			</td>
			<td class="ListItem">
				<xsl:for-each select="$currencies">
					<xsl:value-of select="."/>
					<br/>
				</xsl:for-each>
			</td>
			<td class="ListItem">
				<xsl:for-each select="$currencies">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="periods" select="$elements_currency/value[@field='period']"/>
					<xsl:for-each select="$periods">
						<xsl:sort data-type="text"/>
						<xsl:if test="position()=1">
							<script type="text/javascript">document.write(formatPeriod('<xsl:value-of select="."/>'))
							</script>
							&#160;
						</xsl:if>
						<xsl:if test="not(position()=1) and position()=count($periods)">
							-
							<script type="text/javascript">document.write(formatPeriod('<xsl:value-of select="."/>'))
							</script>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:for-each>
			</td>
			<td class="ListItem">
				<xsl:for-each select="$currencies">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="interestRates"
					              select="$elements_currency/value[@field='interestRate']"/>
					<xsl:for-each select="$interestRates">
						<xsl:sort data-type="number"/>
						<xsl:if test="position()=1">
							от
							<xsl:value-of select="."/>
							&#160;
						</xsl:if>
						<xsl:if test="not(position()=1) and position()=count($interestRates)">
							до
							<xsl:value-of select="."/>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:for-each>
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="products.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\products\Доходный\description.xml" srcSchemaRoot="product" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="table/xsl:apply&#x2D;templates" x="156" y="18"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->