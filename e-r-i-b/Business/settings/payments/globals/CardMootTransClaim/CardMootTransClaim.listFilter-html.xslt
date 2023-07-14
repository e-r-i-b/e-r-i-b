<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/list-data/filter-data"/>
		<script language="javascript">
			setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state"/>");
			setSelectBoxValue ("card","<xsl:value-of select="/list-data/filter-data/card"/>");
		</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
		<span class="filter">Статус</span>
		<select name="state" style="font-weight:normal;">
			<option value="">Все</option>
			<option value="SAVED">Введен</option>
			<option value="DISPATCHED">Принят</option>
			<option value="ACCEPTED">Одобрен</option>
			<option value="EXECUTED">Исполнен</option>
			<option value="REFUSED">Отказан</option>
		</select>
		<br/>
		<span class="filter">Период&nbsp;c</span>
		<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
		&nbsp;по&nbsp;
		<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		<br/>
		<span class="filter">Карта&nbsp;</span>
		<select name="card" style="font-weight:normal;">
			<option value=''>Все</option>

			<xsl:for-each select="document('cards.xml')/entity-list/entity">
			<xsl:variable name="number" select="@key"/>
			<option value="{field[@name='id']/text()}">
				<xsl:value-of select="concat(substring($number, 1, 1), '..', substring($number, string-length($number)-3, 4))"/> [<xsl:value-of select="field[@name='type']/text()"/>]
			</option>
			</xsl:for-each>
		</select>
	</td>	
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="filter&#x2D;>html " userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->