<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:template match="/form-data">
		<style>
			.page_break {page-break-before: always;}
.s0 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s1 {
 font-family: Arial;
 font-size: 15px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 text-align: Center; vertical-align: Middle;
}
.s2 {
 font-family: Arial;
 font-size: 15px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s3 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s4 {
 font-family: Arial;
 font-size: 11px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Center; vertical-align: Middle;
}
.s5 {
 font-family: Arial;
 font-size: 11px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Middle;
}
.s6 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s7 {
 font-family: Arial;
 font-size: 9px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Center; vertical-align: Middle;
}
.s8 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s9 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s10 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Middle;
}
.s11 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Top;
}
.s12 {
 font-family: Arial;
 font-size: 11px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Center; vertical-align: Middle;
}
.s13 {
 font-family: Arial;
 font-size: 12px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s14 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Middle;
}
.s15 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s16 {
 font-family: Arial;
 font-size: 12px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s17 {
 font-family: Arial;
 font-size: 1px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s18 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-weight: bold; font-style: italic;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s19 {
 font-family: Arial;
 font-size: 9px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s20 {
 font-family: Arial;
 font-size: 1px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 2;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s21 {
 font-family: Arial;
 font-size: 1px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 2;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s22 {
 font-family: Arial;
 font-size: 15px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 text-align: Left; vertical-align: Top;
}
.s23 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Top;
}
.s24 {
 font-family: Arial;
 font-size: 12px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Top;
}
.s25 {
 font-family: Arial;
 font-size: 12px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s26 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Top;
}
.s27 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Bottom;
}
.s28 {
 font-family: Arial;
 font-size: 12px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s29 {
 font-family: Arial;
 font-size: 12px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Middle;
}
.s30 {
 font-family: Arial;
 font-size: 15px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Center; vertical-align: Middle;
}
.s31 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Middle;
}
.s32 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 0px;
 border-top-width: 1;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s33 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Center; vertical-align: Middle;
}
.s34 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 text-align: Center; vertical-align: Middle;
}
.s35 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 0px;
 text-align: Center; vertical-align: Middle;
}
.s36 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s37 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Middle;
}
.s38 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 0px;
 border-top-width: 1;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Top;
}
.s39 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 0px;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s40 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Top;
}
.s41 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s42 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Top;
}
.s43 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s44 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s45 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s46 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 0px;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Top;
}
.s47 {
 font-family: Arial;
 font-size: 11px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Center; vertical-align: Middle;
}
.s48 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Left; vertical-align: Middle;
}
.s49 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 1;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 0px;
 text-align: Left; vertical-align: Top;
}
.s50 {
 font-family: Arial;
 font-size: 15px;
 color: #000000; font-weight: bold; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 1;
 border-bottom-width: 0px;
 text-align: Center; vertical-align: Middle;
}
.s51 {
 font-family: Arial;
 font-size: 13px;
 color: #000000; font-style: normal;
 background-color: transparent;
 border-color:#000000; border-style: solid;
 border-left-width: 0px;
 border-right-width: 1;
 border-top-width: 0px;
 border-bottom-width: 1;
 text-align: Center; vertical-align: Top;
}

			.insertInput{border:0 solid transparent;border-bottom:1 solid black;margin:0pt;font-family:Times New Roman;font-size:10pt;}
			.insertInputnoBorder{border:0 solid transparent;border-bottom:1 solid white;margin:0pt;font-family:Times New Roman;font-size:10pt;}
			.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
			.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
			.docBorderFirst{border-bottom:1px solid #000000;border-right:1px solid #000000;border-top:1px solid #000000;border-left:1px solid #000000;}
			.docBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;border-top:1px solid #000000;}
			.textPadding{padding-left:4;padding-right:4;}
			.textPaddingTop{padding-left:4;padding-right:4;padding-top:4;}
			.font10{font-family:Times New Roman;font-size:10pt;}
			.ul {border-bottom: 1px solid black;}
			.bd {border: 1px solid black;}
		</style>
		<body
		 background="REP1.backgrnd.jpg"
		 bgcolor="#FFFFFF" text="#000000">
		<a name="1"></a>
		<table width="718" border="0" cellspacing="0" cellpadding="0">
		<tr style="height: 1px"><td width="38"></td><td width="4"></td><td width="4"></td><td width="8"></td><td width="23"></td><td width="4"></td><td width="8"></td><td width="19"></td><td width="15"></td><td width="19"></td><td width="11"></td><td width="19"></td><td width="23"></td><td width="34"></td><td width="8"></td><td width="30"></td><td width="4"></td><td width="34"></td><td width="11"></td><td width="8"></td><td width="23"></td><td width="4"></td><td width="11"></td><td width="1"></td><td width="7"></td><td width="11"></td><td width="4"></td><td width="23"></td><td width="30"></td><td width="11"></td><td width="30"></td><td width="42"></td><td width="14"></td><td width="1"></td><td width="8"></td><td width="45"></td><td width="38"></td><td width="30"></td><td width="11"></td><td width="4"></td><td width="4"></td><td width="4"></td><td width="4"></td><td width="38"></td></tr>
		<tr style="height:11px">
		<td rowspan="77" class="s0" style="font-size:1px">&nbsp;</td><td colspan="13" rowspan="3" class="s1">Сеть &quot;Contact&quot;<br/>Заявление <br/>на денежный перевод</td><td colspan="30" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:33px">
		<td colspan="2" rowspan="2" class="s0" style="font-size:1px">&nbsp;</td><td colspan="12" class="s2"><xsl:value-of select="documentNumber"/></td><td colspan="2" rowspan="2" class="s0" style="font-size:1px">&nbsp;</td><td colspan="2" class="s1">Дата</td><td colspan="3" rowspan="2" class="s0" style="font-size:1px">&nbsp;</td><td colspan="4" class="s2"><xsl:value-of select="documentDate"/></td><td colspan="5" rowspan="2" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:11px">
		<td colspan="12" class="s0" style="font-size:1px">&nbsp;</td><td colspan="2" class="s0" style="font-size:1px">&nbsp;</td><td colspan="4" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:4px">
		<td colspan="2" rowspan="5" class="s32" style="font-size:1px">&nbsp;</td><td colspan="2" rowspan="3" class="s5">Я,</td><td colspan="38" class="s33" style="font-size:1px">&nbsp;</td><td rowspan="74" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:11px">
		<td colspan="3" rowspan="2" class="s34" style="font-size:1px">&nbsp;</td><td colspan="30" class="s7">Фамилия, имя, (отчество) Отправителя, документ, удостоверяющий личность (№, серия, кем и когда выдан), телефон:</td><td colspan="5" rowspan="2" class="s35" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:4px">
		<td colspan="30" class="s34" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:33px">
			<td colspan="38" class="s6"><b><xsl:value-of select="payerName"/></b> Паспорт серия
				<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
				<xsl:variable name="passportIssueDate" select="$currentPerson/entity/field[@name='passportIssueDate']"/>
				<b><xsl:value-of select="$currentPerson/entity/field[@name='passportSeries']"/></b> №
				<b><xsl:value-of select="$currentPerson/entity/field[@name='passportNumber']"/></b>
				Выдан<b>
				&nbsp;<xsl:value-of select="substring($passportIssueDate,9,2)"/>.<xsl:value-of select="substring($passportIssueDate,6,2)"/>.<xsl:value-of select="substring($passportIssueDate,1,4)"/>&nbsp;
				&nbsp;<xsl:value-of select="$currentPerson/entity/field[@name='passportIssueBy']"/></b>
			</td>
			<td colspan="2" rowspan="2" class="s36" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:4px">
		<td colspan="38" class="s37" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:2px">
		<td colspan="42" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:18px">
		<td colspan="42" class="s6">Прошу АКБ &quot;РУССЛАВБАНК&quot; (ЗАО) перевести сумму в размере</td>
		</tr>
		<tr style="height:2px">
		<td colspan="42" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:15px">
		<td colspan="11" class="s4">Сумма цифрами</td><td colspan="5" class="s47">Код валюты</td><td colspan="26" class="s47">Сумма прописью</td>
		</tr>
		<tr style="height:33px">
		<td colspan="11" class="s8"><xsl:value-of select="amount"/></td>
		<td colspan="5" class="s36">
			<xsl:variable name="rurAccounts" select="document('rur-accounts.xml')/entity-list"/>
		    <xsl:variable name="amountV" select="amount"/>							  
			<b><xsl:value-of select="$rurAccounts/entity/field[@name='currencyCode']"/></b>
		</td>
		<td colspan="26" class="s48">
			<xsl:variable name="amountV" select="amount"/>
			<xsl:variable name="currencyV" select="currency"/>
			<b><xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/></b>
		</td>
		</tr>
		<tr style="height:15px">
		<td colspan="2" rowspan="3" class="s46" style="font-size:1px">&nbsp;</td><td colspan="7" class="s5">Для выдачи</td><td colspan="10" class="s6" style="font-size:1px">&nbsp;</td><td colspan="21" class="s5">Фамилия, имя, (отчество) Получателя</td><td colspan="2" rowspan="3" class="s42" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:33px">
		<td rowspan="2" class="s23" style="font-size:1px">&nbsp;</td><td colspan="37" class="s6"><b><xsl:value-of select="receiverSurName"/>&nbsp;<xsl:value-of select="receiverFirstName"/>&nbsp;<xsl:value-of select="receiverPatrName"/></b>&nbsp;&nbsp;&nbsp;дополнительная инф.:<b> <xsl:value-of select="addInformation"/></b></td>
		</tr>
		<tr style="height:4px">
		<td colspan="37" class="s23" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:15px">
		<td colspan="2" rowspan="3" class="s46" style="font-size:1px">&nbsp;</td><td colspan="3" class="s5">В</td><td colspan="9" class="s6" style="font-size:1px">&nbsp;</td><td colspan="21" class="s12">(Наименование Банка Получателя, город)</td><td colspan="7" class="s45" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:33px">
		<td rowspan="2" class="s23" style="font-size:1px">&nbsp;</td><td colspan="37" class="s13"><xsl:value-of select="receiverBankName"/>, <xsl:value-of select="receiverBankCity"/> (<xsl:value-of select="receiverBankAddress"/>), Тел.: <xsl:value-of select="receiverBankPhone"/></td><td colspan="2" rowspan="2" class="s42" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:4px">
		<td colspan="37" class="s23" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:2px">
		<td colspan="42" class="s49" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:18px">
		<td colspan="2" rowspan="2" class="s44" style="font-size:1px">&nbsp;</td><td colspan="10" class="s14">Назначение платежа:</td><td colspan="28" class="s15">Денежный перевод на текущие расходы</td><td colspan="2" rowspan="2" class="s45" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:2px">
		<td colspan="38" class="s6" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:18px">
		<td rowspan="2" class="s0" style="font-size:1px">&nbsp;</td><td colspan="37" class="s18">Условия осуществления Денежного перевода</td><td colspan="4" rowspan="2" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:2px">
		<td colspan="37" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:1px">
		<td colspan="42" class="s20" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:1px">
		<td colspan="42" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:92px">
		<td rowspan="2" class="s0" style="font-size:1px">&nbsp;</td><td colspan="38" class="s19">1. Отправитель самостоятельно уведомляет Получателя о сумме и номере Денежного перевода и адресе Банка Получателя.<br/>2. Банк Получателя не несет ответственности за отказ в выплате Перевода при несоответствии сведений о Получателе, указанных Отправителем, документам, предъявленным Получателем.<br/>3. Сумма уплаченной комиссии возврату не подлежит.<br/>4. Невостребованная Получателем сумма Денежного перевода подлежит возврату в Банк Отправителя по истечении 30 (Тридцати) календарных дней с даты приема Заявления на Денежный перевод.<br/>    С условиями Денежного перевода согласен. Я гарантирую, что данный Денежный перевод не связан с осуществлением предпринимательской деятельности, инвестиционной деятельности или приобретением прав на недвижимое имущество.</td><td colspan="3" rowspan="2" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:4px">
		<td colspan="38" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:4px">
		<td colspan="42" class="s43" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:18px">
		<td colspan="2" rowspan="3" class="s46" style="font-size:1px">&nbsp;</td><td colspan="18" class="s16">Подпись отправителя</td><td colspan="2" rowspan="3" class="s23" style="font-size:1px">&nbsp;</td><td rowspan="2" class="s17" style="font-size:1px">&nbsp;</td><td rowspan="3" class="s23" style="font-size:1px">&nbsp;</td><td colspan="14" class="s16">Штамп, подпись операциониста</td><td colspan="4" rowspan="3" class="s42" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:12px">
		<td colspan="18" rowspan="2" class="s23" style="font-size:1px">&nbsp;</td><td colspan="14" rowspan="2" class="s23" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:3px">
		<td class="s23" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:6px">
		<td colspan="42" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:1px">
		<td colspan="42" class="s21" style="font-size:1px">&nbsp;</td>
		</tr>
		<tr style="height:5px">
		<td colspan="42" class="s0" style="font-size:1px">&nbsp;</td>
		</tr>
		</table>

		<br style="page-break-after:always;"/>

		<table cellspacing="0" cellpadding="0" border="0"
			style="margin-left:10mm;margin-top:10mm;table-layout:fixed;width:180mm;border-collapse:collapse;">
		  <col  style="width:20mm"/>
		  <col  style="width:15mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:15mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:10mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:5mm"/>
		  <col  style="width:3mm"/>
		  <col  style="width:7mm"/>

			<tr style="height:5mm">
			  <td colspan="2" align="center" class="ul" valign="bottom">

			  </td>
			  <td colspan="2"></td>
			  <td colspan="3" align="center" class="ul" valign="bottom">

			  </td>
			  <td colspan="11"></td>
			  <td colspan="3" align="center"  valign="middle" class="bd"></td>
			</tr>
			<tr style="height:6mm">
			  <td colspan="2"  align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Поступ. в банк плат.</td>
			  <td colspan="2"></td>
			  <td colspan="3" align="center" valign="top" style="font-size:8pt;border-top:1 solid black;">Списано со сч. плат.</td>
			  <td colspan="14"></td>
			</tr>
			<tr style="height:7mm">
			  <td colspan="5" >ПЛАТЕЖНОЕ ПОРУЧЕНИЕ</td>
			  <td colspan="2" ><span  >N </span>
				 <xsl:value-of select="documentNumber"/>
			  </td>
			  <td colspan="5" align="center" class="ul" valign="bottom">
				<xsl:value-of select="documentDate"/>
			  </td>
			  <td></td>
			  <td colspan="4" align="center" class="ul" valign="bottom">
				  Электронно
			  </td>
			  <td colspan="3"></td>
			  <td class="bd" align="center"  valign="middle">
					&nbsp;
			  </td>
			</tr>
			<tr style="height:7mm">
			  <td colspan="7"></td>
			  <td colspan="5" align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Дата</td>
			  <td></td>
			  <td colspan="4" align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Вид платежа</td>
			  <td colspan="4"></td>
			</tr>
			<tr style="height:15mm">
			   <td valign="top" style="border-right:1px solid black">Сумма прописью</td>
			   <td colspan="20" valign="top">&nbsp;
					<xsl:variable name="amountV" select="amount"/>
					<xsl:variable name="currencyV" select="currency"/>
					<xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/>
			   </td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="4" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
				   <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
				   <xsl:value-of select="$currentPerson/entity/field[@name='inn']"/>
			   </td>
			   <td colspan="5" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;
				  &nbsp;
			   </td>
			   <td colspan="2" style="border-top:1px solid black;border-right:1px solid black">&nbsp;Сумма</td>
			   <td colspan="10" style="border-top:1px solid black;">&nbsp;
				  <xsl:value-of select="amount"/>
			   </td>
			</tr>
			<tr style="height:10mm">
			   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
				  <xsl:value-of select="payerName"/>
			   </td>
			   <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
			   <td colspan="10"></td>
			</tr>
			<tr style="height:10mm">
			   <td colspan="2" style="border-right:1px solid black" valign="top">&nbsp;Сч. N</td>
			   <td colspan="10" style="border-top:1px solid black;" valign="top">&nbsp;
				   <xsl:value-of select="payerAccountSelect"/>
			   </td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Плательщик</td>
			   <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
			   <td colspan="10"></td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
				  &nbsp; АКБ «РУССЛАВБАНК» (ЗАО) Г.МОСКВА
			   </td>
			   <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
			   <td colspan="10" >&nbsp;
				  044552685
			   </td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
			   <td colspan="10" >&nbsp;
				  30101810800000000685
			   </td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Банк плательщика</td>
			   <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
			   <td colspan="10" class="ul"></td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
				  <xsl:value-of select="receiverBankName"/>
			   </td>
			   <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
			   <td colspan="10" style="border-top:1px solid black;">&nbsp;
				  <xsl:value-of select="receiverBIC"/>
			   </td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
			   <td colspan="10" >&nbsp;
				  <xsl:value-of select="receiverCorAccount"/>
			   </td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Банк получателя</td>
			   <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
			   <td colspan="10"></td>
			</tr>
			<tr style="height:5mm">
			   <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
				   <xsl:value-of select="receiverINN"/>
			   </td>
			   <td colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;
				  <xsl:value-of select="receiverKPP"/>
			   </td>
			   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
			   <td colspan="10">&nbsp;
				 <xsl:value-of select="receiverAccount"/>
			   </td>
			</tr>
			<tr style="height:10mm">
			  <td colspan="9" rowspan="3" style="border-right:1px solid black" valign="top">
				 <xsl:value-of select="receiverName"/>
			  </td>
			  <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
			  <td colspan="10" class="ul"></td>
			</tr>
			<tr style="height:5mm">
				<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Вид оп.</td>
				<td colspan="3" style="border-top:1px solid black; border-right:1px solid black">&nbsp;01 </td>
				<td colspan="2" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">Срок плат.</td>
				<td colspan="5" style="border-top:1px solid black;">&nbsp;

				</td>
			</tr>
			<tr style="height:5mm">
				<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Наз. пл.</td>
				<td colspan="3" style="border-right:1px solid black">&nbsp;</td>
				<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Очер.плат.</td>
				<td colspan="5">&nbsp;</td>
			</tr>
			<tr style="height:5mm">
				<td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Получатель</td>
				<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Код</td>
				<td colspan="3" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;</td>
				<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Рез. поле.</td>
				<td colspan="5" style="border-bottom:1px solid black">&nbsp;</td>
			</tr>
			<tr style="height:25mm">
				<td colspan="21" valign="top">
					<xsl:value-of select="ground"/>
					<br/>
					<br/>
					<br/>

				</td>
		   </tr>
		   <tr style="height:5mm">
				 <td colspan="21" style="border-bottom:1px solid black;">Назначение платежа</td>
		   </tr>
		   <tr style="height:15mm">
				<td colspan="5">&nbsp;</td>
				<td colspan="7" align="center" valign="top" style="border-bottom:1px solid black">Подписи</td>
				<td colspan="9" align="center" valign="top">Отметки банка</td>
		   </tr>
		   <tr style="height:15mm">
				<td colspan="2">&nbsp;</td>
				<td align="center" valign="top">М.П.</td>
				<td colspan="2">&nbsp;</td>
				<td colspan="7" style="border-bottom:1px solid black">&nbsp;</td>
				<td colspan="9" align="center"></td>
		   </tr>
		   </table>
			<div style="position:absolute;left:152mm;top:172mm;display:none">
			   <img src="/RSPortal/images/stamp.gif" width="125px" height="100px" border="0"/>
			</div>
	</body>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;>html" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="rur&#x2D;accounts.xml" srcSchemaRoot="entity&#x2D;list" AssociatedInstance="file:///c:/Projects/RS/PhizIC&#x2D;SBRF/Business/settings/forms/PurchaseCurrencyPayment/rur&#x2D;accounts.xml" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="currentPerson.xml" srcSchemaRoot="entity&#x2D;list" AssociatedInstance="file:///c:/Interbank/PhizIC/Business/settings/payments/russlav/ContactPayment/currentPerson.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template><template match="/form&#x2D;data"></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->