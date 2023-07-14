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
		<input id="purchase" name="purchase" type="hidden" value="{purchase}"/>
			<table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
				<col style="width:172mm"/>
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
							  <td colspan="3" align="center"  valign="middle" class="bd">0401060</td>
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
							<xsl:value-of select="documentDate"/>
						  </td>
						  <td></td>
						  <td colspan="4" align="center" class="ul" valign="bottom">
							  телеграфом
						  </td>
						  <td colspan="4"></td>
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
							  <xsl:variable name="amountV" select="amount"/>
							   <xsl:variable name="currencyV" select="currency"/>
							  <xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/>	&nbsp;руб.						  
						   </td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
                            <xsl:value-of select="INN"/>
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
							   <xsl:value-of select="fullName"/>
						   </td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10" class="ul">&nbsp;</td>
						</tr>
						<tr style="height:10mm">
						   <td colspan="2" style="border-right:1px solid black" valign="top">&nbsp;Сч. N</td>
						   <td colspan="10" valign="top">&nbsp;
							  <xsl:value-of select="account"/>
						   </td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Плательщик</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10"></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">							   
							  &nbsp;АКБ &quot;РУССЛАВБАНК&quot; (ЗАО) Г.МОСКВА
						   </td>
						   <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
						   <td colspan="10" >&nbsp;044552685</td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
						   <td colspan="10" >&nbsp;30101810800000000685</td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Банк плательщика</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10" class="ul"></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
							  &nbsp;АКБ &quot;РУССЛАВБАНК&quot; (ЗАО) Г.МОСКВА
						   </td>
						   <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
						   <td colspan="10" >&nbsp;044552685</td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
						   <td colspan="10" >&nbsp;30101810800000000685</td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Банк получателя</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
                            &nbsp;<xsl:value-of select="INN"/>
                           </td>
                           <td colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;
                            &nbsp;
                           </td>
						   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
						   <td colspan="10">&nbsp;<xsl:value-of select="depositAccount"/></td>
						</tr>
						<tr style="height:10mm">
						  <td colspan="9" rowspan="3" style="border-right:1px solid black" valign="top">
							&nbsp;<xsl:value-of select="fullName"/>
						  </td>
						  <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						  <td colspan="10" class="ul">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Вид оп.</td>
							<td colspan="3" class="ul">&nbsp;
								01
							</td>
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Срок плат.</td>
							<td colspan="5">&nbsp;

							</td>
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
						<tr style="height:5mm">
							<td colspan="21">Назначение платежа&nbsp;<br/>Пополнение срочного вклада</td>
						</tr>
						<tr style="height:25mm">
							<td colspan="21" valign="top">
							   <br/>
							   <br/>
							   <br/>
							   <br/>
							   <br/>&nbsp;							   
							</td>
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
						<div style="position:absolute;left:152mm;top:161mm;">
						   <img src="{$resourceRoot}/images/stamp.gif" border="0"/>
						</div>
					</td>
				</tr>
			</table>

	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="print" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->