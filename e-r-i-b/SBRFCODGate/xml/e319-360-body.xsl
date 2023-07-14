<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- Тело для Форм 360 и 319 -->
<xsl:template name="fBody">
<style>
	.insertInput{border:0 solid transparent;border-bottom:1 solid black;margin:0pt;font-family:Times New Roman;font-size:12pt;}
	.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
	.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
	.docTdBorderFirst{border-top:1px solid #000000;border-bottom:1px solid #000000;border-right:1px solid #000000;}
	.docTdBorderFirstAngle{border-top:1px solid #000000;border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
	.docTdBorderSecond{border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
	.textPadding{padding-left:4;padding-right:4;}
	.textPaddingTop{padding-left:4;padding-right:4;padding-top:4;}
	.font10{font-family:Times New Roman;font-size:10pt;}
</style>

<tr>
<td>
<table class="font10">
<tr>
	<td>
		выдана за 
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(monthToStringWithoutYear("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		20
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(getYoungDigitsYear("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		 год
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table class="font10" >
<tr>
	<td>
		дата предыдущей операции по счету 
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(monthToStringWithoutYear("<xsl:value-of select="prevOperDate"/>"));</script>
	</td>
	<td>
		20
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(getYoungDigitsYear("<xsl:value-of select="prevOperDate"/>"));</script>
	</td>
	<td>
		 год
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table class="font10">
<tr>
	<td>
		входящий остаток на начало дня
	</td>
	<td align="left">		
		<input type="Text" readonly="true" class="insertInput font10" style="width:98%">
		<xsl:attribute name="value">
			<xsl:value-of select="beginningBalance"/>  
		</xsl:attribute>
		</input>
	</td>
</tr>
</table>
</td>
</tr>

<tr>
	<td>
		<table class="font10" cellpadding="0" cellspacing="0"  width="100%">
		<tr>
			<td valign="top" class="docTdBorderFirstAngle textPaddingTop" align="center" width="50%">Дата совершения операции</td>
			<td valign="top" class="docTdBorderFirst textPaddingTop" align="center" width="15%">Вид операции</td>
			<td valign="top" class="docTdBorderFirst textPaddingTop" align="center" width="15%">Номер документа</td>
			<td valign="top" class="docTdBorderFirst textPaddingTop" align="center" width="15%">Операция</td>
			<td valign="top" colspan="2" class="docTdBorderFirst textPaddingTop" align="center" width="50%">Сумма операции</td>
			<td valign="top" class="docTdBorderFirst textPaddingTop" align="center" width="15%">Корреспондирующие счета</td>
			<td valign="top" class="docTdBorderFirst textPaddingTop" align="center" width="15%">Остаток по счету после проведения операции</td>
		</tr>
		<tr>
			<td class="docTdBorderSecond textPaddingTop" align="center">&nbsp;</td>
			<td class="docTdBorder textPaddingTop" align="center">&nbsp;</td>
			<td class="docTdBorder textPaddingTop" align="center">&nbsp;</td>
			<td class="docTdBorder textPaddingTop" align="center">&nbsp;</td>
			<td class="docTdBorder textPaddingTop" align="center">По Дт счета</td>
			<td class="docTdBorder textPaddingTop" align="center">По Кт счета</td>
			<td class="docTdBorder textPaddingTop" align="center">&nbsp;</td>
			<td class="docTdBorder textPaddingTop" align="center">&nbsp;</td>
		</tr>
		<xsl:apply-templates select="row" mode="f360"/>
		</table>
		
	</td>
</tr>
<tr>
<td>
<table class="font10">
<tr>
	<td>
		исходящий остаток по счету на конец дня
	</td>
	<td align="left">		
		<input type="Text" readonly="true" class="insertInput font10" style="width:98%">
		<xsl:attribute name="value">
			<xsl:value-of select="outBalance"/>
		</xsl:attribute>
		</input>
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table class="font10">
<tr>
	<td>
		Выписка выдана за "
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(getDateOnly("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		"
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(monthToStringOnly("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		20
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(getYoungDigitsYear("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		г. в порядке, установленном договором 
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table class="font10" width="100%">
<tr>
	<td>
		"О счете 
	</td>
	<td style="border-bottom:1 solid #000000;">
		<xsl:value-of select="account"/>
	</td>
	<td>
		". Подпись работника структурного подразделения 
	</td>
	<td width="15%">
		<input type="Text" readonly="true" class="insertInput font10" style="width:98%"/>
	</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table class="font10">
<tr>
	<td>
		Дата "
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(getDateOnly("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		"
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(monthToStringOnly("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		20
	</td>
	<td style="border-bottom:1 solid #000000;">
		<script>document.write(getYoungDigitsYear("<xsl:value-of select="$createDate"/>"));</script>
	</td>
	<td>
		г.
	</td>
</tr>
</table>
</td>
</tr>
</xsl:template>


<xsl:template match="row" mode="f360">
<tr>
	<td class="docTdBorderSecond textPadding" align="center">
		<script LANGUAGE="JavaScript">												
			var str = createDate("<xsl:value-of select="date"/>");
			document.write(str);	
		</script>								
	</td>
	<td class="docTdBorder textPadding" align="center">
		&nbsp;<xsl:value-of select="aspect"/>
	</td>
	<td class="docTdBorder textPadding" align="center">
		&nbsp;<xsl:value-of select="Document"/>
	</td>
	<td class="docTdBorder textPadding" align="center">
		<xsl:value-of select="name"/>
	</td>
	<xsl:choose>
    <xsl:when test="debit='true'">
		<td class="docTdBorder textPadding" align="right" >
			&nbsp;<xsl:value-of select="sum"/>
		</td>
		<td class="docTdBorder textPadding" align="center">
			&nbsp;
		</td>
	</xsl:when>
	<xsl:otherwise>
		<td class="docTdBorder textPadding" align="center">
			&nbsp;
		</td>
		<td class="docTdBorder textPadding" align="right">
			&nbsp;<xsl:value-of select="sum"/>
		</td>
	</xsl:otherwise>
	</xsl:choose>
	<td class="docTdBorder textPadding" align="center">
		&nbsp;<xsl:value-of select="correspondent"/>
	</td>
	<td class="docTdBorder textPadding" align="center">
		&nbsp;<xsl:value-of select="balance"/>
	</td>
</tr>
</xsl:template>


</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="fcommon" userelativepaths="yes" externalpreview="no" url="extract.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->