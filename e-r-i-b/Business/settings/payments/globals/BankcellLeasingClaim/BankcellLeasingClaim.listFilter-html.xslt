<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/list-data/filter-data"/>
		<script language="javascript">
			function initTemplates() {
			}
			function clearMasks(event){
			 clearInputTemplate("fromDate",DATE_TEMPLATE);
			 clearInputTemplate("toDate",DATE_TEMPLATE);
			}
			function checkData() {
			 if (!checkPeriod ("fromDate", "toDate", "Период с", "Период по")) return false;
			 return true;
			}
			initTemplates();
			setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");
		</script>
    </xsl:template>
	
	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
		<span class="filterLabel">Статус</span>
		<select name="state" onkeydown="onTabClick(event,'fromDate')" style="font-weight:normal;">
			<option value="">Все</option>
			<option value="SAVED">Введен</option>
			<option value="DISPATCHED">Принят</option>
			<option value="ACCEPTED">Одобрен</option>
			<option value="EXECUTED">Исполнен</option>
			<option value="REFUSED">Отказан</option>
         </select>
         <br/>
         <span class="filterLabel">Период&nbsp;c</span>
		 <input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
         &nbsp;по&nbsp;
		 <input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
	</td>	
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="filter&#x2D;>html " userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\BankcellLeasingClaimDebugData\list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->