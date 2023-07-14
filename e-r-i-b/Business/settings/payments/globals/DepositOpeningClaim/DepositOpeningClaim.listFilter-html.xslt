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
			setSelectBoxValue ("claimType","<xsl:value-of select="/list-data/filter-data/claimType"/>");
		</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
	<table>
	<tr>
		<td class="filter">������</td>
		<td class="filter" style="font-weight:normal;">
		<select name="state">
			<option value="">���</option>
			<option value="SAVED">������</option>
			<option value="DISPATCHED,SENDED">������</option>
			<option value="ACCEPTED">�������</option>
			<option value="EXECUTED">��������</option>
			<option value="REFUSED">�������</option>
		</select>
		</td>
	</tr>
	<tr>
		<td class="filter">������&nbsp;c</td>
		<td class="filter">
			<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
			&nbsp;��&nbsp;
			<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		</td>
	</tr>
	<tr>
		<td class="filter">���&nbsp;������</td>
		<td class="filter" style="font-weight:normal;">
		<select name="claimType">
			<option value=''>���</option>
			<option value='AccountOpeningClaim'>�������� �����</option>
			<option value='DepositOpeningClaim'>�������� ������</option>
		</select>
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