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
		<input id="purchase" name="purchase" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="purchase"/>
	            </xsl:attribute>
		</input>

		<table cellspacing="0" cellpadding="0" border="0" style="margin-left:5mm;margin-top:5mm;table-layout:fixed;width:180mm;border-collapse:collapse;">
				<col style="width:180mm"/>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
						<tr style="height:5mm">
							  <td colspan="2" align="center" class="ul" valign="bottom">
								 &nbsp;
							  </td>
							  <td colspan="2"></td>
							  <td colspan="3" align="center" class="ul" valign="bottom">
								 &nbsp;
							  </td>
							  <td colspan="11"></td>
							   <td colspan="3" align="center"  valign="middle" class="bd" style="border-top:1 solid black;border-bottom:1 solid black;border-left:1 solid black;border-right:1 solid black;">
								0401060
							  </td>
						</tr>
						<tr style="height:6mm">
						  <td colspan="2"  align="center" valign="top" style="font-size:8pt;">Поступ. в банк плат.</td>
						  <td colspan="2"></td>
						  <td colspan="3" align="center" valign="top" style="font-size:8pt;">Списано со сч. плат.</td>
						  <td colspan="14"></td>
						</tr>
						<tr style="7mm">
						  <td colspan="5" class="Title">ПЛАТЕЖНОЕ ПОРУЧЕНИЕ</td>
						  <td colspan="2" class="Title"><span UNSELECTABLE="on">N </span>
							<xsl:value-of select="documentNumber"/>
						  </td>
						  <td colspan="5" align="center" class="ul" valign="bottom">
							&nbsp;<xsl:value-of select="documentDate"/>
						  </td>
						  <td></td>
						  <td colspan="4" align="center" class="ul" valign="bottom" style="font-size:9pt;">
							  электронно
						  </td>
						  <td colspan="3"></td>
						  <td colspan="1" align="center"  valign="middle" class="bd">&nbsp;</td>
						</tr>
						<tr style="height:7mm">
						  <td colspan="7"></td>
						  <td colspan="5" align="center" valign="top" style="font-size:8pt;">Дата</td>
						  <td>&nbsp;</td>
						  <td colspan="4" align="center" valign="top" style="font-size:8pt;">Вид платежа</td>
						  <td colspan="4">&nbsp;</td>
						</tr>
						<tr style="height:15mm">
						   <td valign="top" class="ul" style="border-right:1px solid black;border-bottom:1px solid black;">Сумма прописью</td>
						   <td colspan="20" class="ul" valign="top">&nbsp;
							  <xsl:variable name="rurAccounts" select="document('rur-accounts.xml')/entity-list"/>
							  <xsl:variable name="amountV" select="amount"/>
							  <xsl:variable name="currencyV" select="$rurAccounts/entity/field[@name='currencyCode']"/>
							  <xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/>
						   </td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
                             <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
							   <xsl:variable name="currentPersonInn" select="$currentPerson/entity/field[@name='inn']"/>
				             <xsl:value-of select="$currentPersonInn"/>
                           </td>
                           <td colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;
                            &nbsp;
                           </td>
                           <td colspan="2" style="border-right:1px solid black">&nbsp;Сумма</td>
                           <td colspan="10">&nbsp;
                             <xsl:value-of select="amount"/>
						   </td>
						</tr>
						<tr style="height:10mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
							   <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
							   &nbsp;<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>
						   </td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10" class="ul">&nbsp;</td>
						</tr>
						<tr style="height:10mm">
						   <td colspan="2" style="border-right:1px solid black" valign="top">&nbsp;Сч. N</td>
						   <td colspan="10" valign="top">&nbsp;
							  <xsl:value-of select="fromAccountSelect"/>
						   </td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Плательщик</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10"></td>
						</tr>
						<xsl:variable name="currentBankConfig" select="document('currenntBankConfig.xml')"/>
						<tr style="height:5mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
							  <xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='name']"/>
						   </td>
						   <td colspan="2" style="border-right:1px solid black">БИК</td>
						   <td colspan="10" ><xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='bic']"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="2" style="border-right:1px solid black">Сч. N</td>
						   <td colspan="10" ><xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='corrAcc']"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Банк плательщика</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10" class="ul"></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
							   <xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='name']"/>
						   </td>
						   <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
						   <td colspan="10" > <xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='bic']"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
						   <td colspan="10" > <xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='corrAcc']"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Банк получателя</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
                             <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
							 <xsl:variable name="currentPersonInn" select="$currentPerson/entity/field[@name='inn']"/>
				             <xsl:value-of select="$currentPersonInn"/>
                           </td>
                           <td colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;</td>
						   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
						   <td colspan="10">&nbsp;<xsl:value-of select="toAccountSelect"/></td>
						</tr>
						<tr style="height:10mm">
						  <td colspan="9" rowspan="3" style="border-right:1px solid black" valign="top">
							<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
							   &nbsp;<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>
						  </td>
						  <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						  <td colspan="10" class="ul">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Вид оп.</td>
							<td colspan="3"  style="border-right:1px solid black">&nbsp;01</td>
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Срок плат.</td>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Наз. пл.</td>
							<td colspan="3" style="border-right:1px solid black">&nbsp;

							</td>
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Очер. плат.</td>
							<td colspan="5">&nbsp;

							</td>
						</tr>
						<tr style="height:5mm">
							<td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Получатель</td>
							<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Код</td>
							<td colspan="3" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;

							</td>
							<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Рез. поле.</td>
							<td colspan="5" class="ul">&nbsp;

							</td>
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
						<tr style="height:15mm;">
							<td colspan="5" style="border-top:1px solid black">&nbsp;</td>
							<td colspan="7" align="center" valign="top" style="border-bottom:1px solid black;border-top:1px solid black">Подписи</td>
							<td colspan="9" align="center" valign="top" class="ul">Отметки банка</td>
						   </tr>
						   <tr style="height:15mm">
							<td colspan="2">&nbsp;</td>
							<td align="center" valign="top">М.П.</td>
							<td colspan="2">&nbsp;</td>
							<td colspan="7" class="ul">&nbsp;</td>
							<td colspan="9" align="center" ><xsl:value-of select="documentDate"/>&nbsp;</td>
						</tr>
					    </table>
					</td>
				</tr>
			</table>

	</xsl:template>

</xsl:stylesheet>