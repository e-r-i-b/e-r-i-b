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
			function initTemplates() {
				setInputTemplate("fromDate",DATE_TEMPLATE);
				setInputTemplate("toDate",DATE_TEMPLATE);
			}
			function clearMasks(event){
				clearInputTemplate("fromDate",DATE_TEMPLATE);
				clearInputTemplate("toDate",DATE_TEMPLATE);
			}
			initTemplates();
			setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state"/>");
		</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
	<table>
	<tr>
		<td class="filter">Статус</td>
		<td class="filter" style="font-weight:normal;">
		<select name="state">
			<option value="">Все</option>
			<option value="I">Введена</option>
			<option value="W">Принята</option>
			<option value="S">Исполнена</option>
			<option value="E">Отказана</option>
		</select>
		</td>
	</tr>
	<tr>
		<td class="filter">Период&nbsp;c</td>
		<td class="filter">
			<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
			&nbsp;по&nbsp;
			<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		</td>
	</tr>
	</table>
	</td>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="filter&#x2D;>html " userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->