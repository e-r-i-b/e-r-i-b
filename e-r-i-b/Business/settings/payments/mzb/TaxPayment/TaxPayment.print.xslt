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
			   <xsl:variable name="rurAccounts" select="document('rur-accounts.xml')/entity-list"/>
			   <xsl:variable name="amountV" select="amount"/>
			   <xsl:variable name="currencyV" select="$rurAccounts/entity/field[@name='currencyCode']"/>
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
			  &nbsp; АКБ  "Московский залоговый банк" (ЗАО)
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
			  <xsl:value-of select="receiverBank"/>
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
			<td colspan="5">&nbsp;6</td>
		</tr>
		<tr style="height:5mm">
			<td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Получатель</td>
			<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Код</td>
			<td colspan="3" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;</td>
			<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Рез. поле.</td>
			<td colspan="5" style="border-bottom:1px solid black">&nbsp;</td>
		</tr>
		<tr style="height:5mm" >
			<td style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">
			   <xsl:value-of select="taxKBK"/>
			</td>
			<td style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">
			   <xsl:value-of select="taxOKATO"/>
			</td>
			<td style="border-bottom:1px solid black;border-right:1px solid black" align="center">
			   <xsl:if test="//taxGround=''">0</xsl:if>
			   <xsl:if test="//taxGround!=''"><xsl:value-of select="taxGround"/></xsl:if>
			</td>
			<td style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">
			   <xsl:if test="//taxPeriod1=''">0</xsl:if>
			   <xsl:if test="//taxPeriod1!=''"><xsl:value-of select="taxPeriod1"/>.<xsl:value-of select="taxPeriod2"/>.<xsl:value-of select="taxPeriod3"/></xsl:if>
			</td>
			<td style="border-bottom:1px solid black;border-right:1px solid black" colspan="5" align="center">
			   <xsl:if test="//taxDocumentNumber=''">0</xsl:if>
				<xsl:if test="//taxDocumentNumber!=''"><xsl:value-of select="taxDocumentNumber"/></xsl:if>
			</td>
			<td style="border-bottom:1px solid black;border-right:1px solid black" colspan="4" align="center">
			   <xsl:if test="//taxDocumentDate=''">0</xsl:if>
				<xsl:if test="//taxDocumentDate!=''"></xsl:if><xsl:value-of select="taxDocumentDate"/>
			</td>
			<td style="border-bottom:1px solid black;" colspan="2" align="center">
			   <xsl:if test="//taxType=''">0</xsl:if>
			   <xsl:if test="//taxType!=''"><xsl:value-of select="taxType"/></xsl:if>
			</td>
		</tr>
		<tr style="height:25mm">
			<td colspan="21" valign="top">

				<br/>
				<br/>
				<br/>

			</td>
	   </tr>
	   <tr style="height:5mm">
			 <td colspan="21" style="border-bottom:1px solid black;">Назначение платежа
			     <xsl:value-of select="ground"/>
			 </td>

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
		</xsl:template>

</xsl:stylesheet>