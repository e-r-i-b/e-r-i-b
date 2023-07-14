<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>

<!-- Форма 319 -->
<!-- Шапка -->
<xsl:template name="f319">
<style>
	.insertInput{border:0 solid transparent;border-bottom:1 solid black;margin:0pt;font-family:Times New Roman;font-size:12pt;}
	.font10{font-family:Times New Roman;font-size:10pt;}
</style>

<table class="font10" style="width:160mm;margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;">
<tr>
<td>
<tr>
<td>
<table class="font10" width="100%">
	<tr>
		<td rowspan="2"><img src="{$resourceRoot}/images/miniLogoSbrf.jpg"/>
		</td>
		<td align="left" width="30%">
			<nobr>Сбербанк&nbsp;России</nobr>
		</td>
		<td align="right" width="70%">
			<nobr>Ф. N№ 319</nobr>
		</td>
	</tr>
	<tr>
		<td>
			<xsl:value-of select="bankName"/>
		</td>
	</tr>
	<tr style="font-size:8pt;">
		<td>
			&nbsp;
		</td>
		<td style="border-top:1 solid #000000;" valign="top" align="center">
			Наименование филиала<br/>Сбербанка России
		</td>
	</tr>
</table>
</td>
</tr>
<tr>
	<td>
		<br/>
		<br/>
	</td>
</tr>
<tr>
<td>
<table width="100%" class="font10">
<tr>
	<td>
		<nobr>Наименование&nbsp;банка
			<input type="Text" readonly="true" class="insertInput font10" style="width:98%">
			<xsl:attribute name="value">
				<xsl:value-of select="bankName"/>
			</xsl:attribute>
			</input>
		</nobr>
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table width="100%" class="font10">
<tr>
	<td> 
		<nobr>Номер&nbsp;отделения
			<input type="Text" readonly="true" class="insertInput font10" style="width:98%">
			<xsl:attribute name="value">
				<xsl:value-of select="branch"/>
			</xsl:attribute>
			</input>
		</nobr>
		<nobr>Номер&nbsp;структурного&nbsp;подразделения
		<input type="Text" readonly="true" class="insertInput font10" style="width:98%">
			<xsl:attribute name="value">
				<xsl:value-of select="office"/>
			</xsl:attribute>
		</input>
		</nobr>
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table width="100%" class="font10">
<tr>
	<td width="30%">
		<nobr>ВЫПИСКА&nbsp;ПО&nbsp;ТЕКУЩЕМУ&nbsp;СЧЕТУ&nbsp;№</nobr>
	</td>
	<td width="30%">
			<input type="Text" readonly="true" class="insertInput font10" style="width:98%">
			<xsl:attribute name="value">
				<xsl:value-of select="account"/>
			</xsl:attribute>
			</input>
	</td>
	<td width="20%">
		&nbsp;
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table width="100%" class="font10">
<tr>
	<td align="center">
			<input type="Text" readonly="true" class="insertInput font10" style="width:100%;text-align:center;">
			<xsl:attribute name="value">
				<xsl:value-of select="depositorName"/>
			</xsl:attribute>
			</input>
	</td>
</tr>
<tr>
	<td align="center">
		(ФИО владельца текущего счета)
	</td>
</tr>
</table>
</td>
</tr>
<!-- Конец шапки -->
<xsl:call-template name="fBody"/>
</td>
</tr>
</table>			
</xsl:template>

<!-- Конец Формы 319 -->

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="f319" userelativepaths="yes" externalpreview="no" url="extract.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->