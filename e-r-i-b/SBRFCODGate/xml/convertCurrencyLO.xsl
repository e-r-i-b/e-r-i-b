<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>

<xsl:param name="subDivisionName"/>
<xsl:param name="subDivisionNum"/>
<xsl:param name="sumOutput"/>
<xsl:param name="currencyIn"/>
<xsl:param name="currencyOut"/>

<xsl:template match="message">		
		<xsl:apply-templates select="form190Result_a"/>	
</xsl:template>

<xsl:template match="form190Result_a">
	<script LANGUAGE="JavaScript">
		function createDate(str1)
		{
			var day,month,year,res;
			day = str1.substring(8, 10);
			month = str1.substring(5, 7);
			year = str1.substring(0, 4);
			res = day + "." + month + "." + year;
			return res;
		}
		function createTime(str1)
		{
			return str1.substring(12, 16);
		}
	</script>
	<table>
	<tr>
		<td align="center">Отчет об исполнении длительных поручений</td>
	</tr>
	<tr>
		<td align="center">
			по вкладу (счету) №<xsl:value-of select="account"/>
		</td>
	</tr>
	<tr>
		<td align="center">
			на покупку и продажу (конверсию) в безналичном порядке иностранной валюты с зачислением полученной 
		</td>
	</tr>
	<tr>
		<td align="center">
			суммы на действующий счет по вкладу клиента в том же филиале Банка
		</td>
	</tr>
	<tr>
		<td align="center">			
			<br/>			
		</td>
	</tr>
	<tr>
		<td align="center">
			за период с 
			<script LANGUAGE="JavaScript">												
				var str = createDate("<xsl:value-of select="startDate"/>");
				document.write(str);						
			</script>
			 по 
			 <script LANGUAGE="JavaScript">												
				var str = createDate("<xsl:value-of select="endDate"/>");
				document.write(str);						
			</script>
		</td>
	</tr>
	<tr>
		<td align="center">			
			<br/>			
		</td>
	</tr>
	<tr>
		<td align="center">
			сформировано 
			<script LANGUAGE="JavaScript">												
				var str = createDate("<xsl:value-of select="form190/formDate"/>");
				document.write(str);						
			</script>
			 в
			<script LANGUAGE="JavaScript">
				var str = createTime("<xsl:value-of select="form190/formDate"/>");
				document.write(str);
			</script>
			<br/>
		</td>
	</tr>
	<tr>
		<td align="center">			
			<br/>			
		</td>
	</tr>
	<tr>
		<td align="center">
			<xsl:copy-of select="$subDivisionName"/> № <xsl:copy-of select="$subDivisionNum"/>
		</td>
	</tr>
	<tr>
		<td align="center">			
			<br/>			
		</td>
	</tr>
	<xsl:apply-templates select="form190/row"/>	
	</table>
</xsl:template>

<xsl:template match="row">

	<xsl:variable name="recAcc" select="recipientAccount"/>

	<table style="border-collapse:collapse;">
	<tr>
		<td width="33%" align="left">						
			Дата списания 
			<script LANGUAGE="JavaScript">												
				var str = createDate("<xsl:value-of select="operationDate"/>");
				document.write(str);						
			</script>			
		</td>
		<td width="33%">
			 Сумма списания <xsl:value-of select="sum"/>
		</td>
		<td>
			 <xsl:copy-of select="$currencyIn"/>
		</td>
	</tr>		
	<tr>
		<td align="left">
			Счет №<xsl:value-of select="recipientAccount"/>
		</td>
		<td>
			 Сумма зачисления <xsl:copy-of select="$sumOutput"/>
		</td>
		<td>
			<xsl:value-of select="document( concat('accountInfo.xml?AccountNumber=',$recAcc) )/entity-list/entity[@key=$recAcc]/field[@name='currency']"/>
		</td>
	</tr>
		<tr>
		<td align="left" style="border-top:1 solid black">						
			<br/>
		</td>
		<td style="border-top:1 solid black">
			 <br/>
		</td>
		<td style="border-top:1 solid black">
			<br/>
		</td>
	</tr>
	</table>
</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="longOffer.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->