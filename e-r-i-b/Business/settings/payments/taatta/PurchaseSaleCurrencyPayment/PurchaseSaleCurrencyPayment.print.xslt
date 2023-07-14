<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:template match="/form-data">
		<style>
			.insertInput{border:0 solid transparent;border-bottom:1 solid black;margin:0pt;font-family:Times New Roman;font-size:12pt;}
			.insertInputnoBorder{border:0 solid transparent;border-bottom:1 solid white;margin:0pt;font-family:Times New Roman;font-size:12pt;}
			.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
			.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
			.textPadding{padding-left:4;padding-right:4;}
			.textPaddingTop{padding-left:4;padding-right:4;padding-top:4;}
			.font10{font-family:Times New Roman;font-size:10pt;}
		</style>
		<input id="type" name="type" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="type"/>
	            </xsl:attribute>
		</input>
		<div id="purchaseDiv" name="purchaseDiv" style="display:none">
			<table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
				<col style="width:172mm"/>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td>
								&nbsp;
								</td>
							</tr>
							<tr>
								<td align="center">
							<b><nobr>Коммерческий банк "Русский Славянский Банк"</nobr><br/>
							(закрытое акционерное общество)</b>
								</td>
							</tr>
							<tr>
								<td align="right">
								&nbsp;<br/><b>П О К У П К А</b><br/>
								</td>
							</tr>
							<tr>
								<td align="center">
								З А Я В Л Е Н И Е  №
								         <input type="Text" readonly="true" class="insertInput" style="width:12%" align="center">
											<xsl:attribute name="value">
												<xsl:value-of select="documentNumber"/>
											</xsl:attribute>
										</input><br/><br/>
							на покупку иностранной валюты<br/><br/>
							от "<input type="Text" readonly="true" class="insertInput" style="width:5%" align="center">
									<xsl:attribute name="value">
										<xsl:value-of select="substring(documentDate,1,2)"/>
									</xsl:attribute>
								</input>"
								<input type="Text" id="purchaseMonth" name="purchaseMonth" readonly="true" class="insertInput" style="width:15%" align="center">
									<xsl:attribute name="value">
										<xsl:value-of select="documentDate"/>
									</xsl:attribute>
								</input>
								200
								<input type="Text" readonly="true" class="insertInput" style="width:3%" align="center">
									<xsl:attribute name="value">
										<xsl:value-of select="substring(documentDate,10,1)"/>
									</xsl:attribute>
								</input>г.<br/><br/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td height="30">
									Фамилия, имя, отчество: _____________________________________________
								</td>
							</tr>
							<tr>
								<td height="30">
									Почтовый адрес: ____________________________________________________
								</td>
							</tr>
							<tr>
								<td height="30">
									Телефон: ________________________ Телефакс: _______________________
								</td>
							</tr>
							<tr>
								<td height="30">
									Номер валютного счета
									<input type="Text" readonly="true" class="insertInput" style="width:50%" align="center">
										<xsl:attribute name="value">&nbsp;<xsl:value-of select="receiverAccountSelect"/></xsl:attribute>
									</input>
								</td>
							</tr>
							<tr>
								<td height="30">
									в АКБ "РУССЛАВБАНК" (ЗАО)
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td align="left" width="620"  height="30">
									Прошу Русский Славянский Банк продать мне иностранную валюту на нижеследующих условиях:
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<table class="docTableBorder" style="width:172mm;height:100%;" cellpadding="0" cellspacing="0">
							<tr>
								<td class="docTdBorder textPadding" valign="top" align="center" width="10%">
									Код<br/> валюты
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center" width="20%">
									Сумма покупки инвалюты<br/>
									(заполняется банком)
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center" width="40%">
									Курс сделки в рублях<br/>
									(договорная цена)
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
								Сумма покупки в рублях<br/>
									по курсу сделки<br/>
								(заполняется клиентом)

								</td>
							</tr>
							<tr>
								<td class="docTdBorder textPadding" valign="top" align="center">
									1
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									2
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									3
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									4
								</td>
							</tr>
							<tr>
								<td class="docTdBorder textPadding" valign="top" align="center">
									<xsl:value-of select="foreignCurrency"/>&nbsp;
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									<xsl:value-of select="foreignCurrencyAmount"/>&nbsp;
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									на условиях: <input type="Text" readonly="true" class="insertInput" style="width:13%" align="right" value="TOD"/><input type="Text" readonly="true" class="insertInputnoBorder" style="width:35%" align="left" value="/TOM /SPOT"/>
						                                                    (нужное подчеркнуть)<br/>

						по курсу банка:<input type="Text" readonly="true" class="insertInput" style="width:32%" align="center">
										<xsl:attribute name="value">
											<xsl:value-of select="course"/>&nbsp;
										</xsl:attribute>
									</input>

								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									<xsl:value-of select="rurAmount"/>&nbsp;
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td>
									Сумму рублевого покрытия прошу списать с моего текущего  рублевого счета
								</td>
							</tr>
							<tr>
								<td>
									№
									<input type="Text" readonly="true" class="insertInput" style="width:32%" align="center">
										<xsl:attribute name="value">&nbsp;<xsl:value-of select="payerAccountSelect"/>&nbsp;</xsl:attribute>
									</input>
									в АКБ "РУССЛАВБАНК" (ЗАО).
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<br/>
						<br/>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						Подпись физического лица ________________________________________
					</td>
				</tr>
			</table>
		</div>
		<div id="saleDiv" name="saleDiv" style="display:block">
			<table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
				<col style="width:172mm"/>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td>
								&nbsp;
								</td>
							</tr>
							<tr>
								<td align="center">
							<b><nobr>Коммерческий банк "Русский Славянский Банк"</nobr><br/>
							(закрытое акционерное общество)</b>
								</td>
							</tr>
							<tr>
								<td align="right">
								&nbsp;<br/><b>П Р О Д А Ж А</b><br/>
								</td>
							</tr>
							<tr>
								<td align="center">
								З А Я В Л Е Н И Е  №
								         <input type="Text" readonly="true" class="insertInput" style="width:12%" align="center">
											<xsl:attribute name="value">
												<xsl:value-of select="documentNumber"/>
											</xsl:attribute>
										</input><br/><br/>
							на продажу иностранной валюты<br/><br/>
							от "<input type="Text" readonly="true" class="insertInput" style="width:5%" align="center">
									<xsl:attribute name="value">
										<xsl:value-of select="substring(documentDate,1,2)"/>
									</xsl:attribute>
								</input>"
								<input type="Text" id="saleMonth" name="saleMonth" readonly="true" class="insertInput" style="width:15%" align="center">
									<xsl:attribute name="value">
										<xsl:value-of select="documentDate"/>
									</xsl:attribute>
								</input>
								200
								<input type="Text" readonly="true" class="insertInput" style="width:3%" align="center">
									<xsl:attribute name="value">
										<xsl:value-of select="substring(documentDate,10,1)"/>
									</xsl:attribute>
								</input>г.<br/><br/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td height="30">
									Фамилия, имя, отчество: _____________________________________________
								</td>
							</tr>
							<tr>
								<td height="30">
									Почтовый адрес: ____________________________________________________
								</td>
							</tr>
							<tr>
								<td height="30">
									Телефон: ________________________ Телефакс: _______________________
								</td>
							</tr>
							<tr>
								<td height="30">
									Номер текущего рублевого счета
									<input type="Text" readonly="true" class="insertInput" style="width:50%" align="center">
										<xsl:attribute name="value">&nbsp;<xsl:value-of select="receiverAccountSelect"/></xsl:attribute>
									</input>
								</td>
							</tr>
							<tr>
								<td height="30">
									в АКБ "РУССЛАВБАНК" (ЗАО)
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td align="left" width="620"  height="30">
									Прошу Русский Славянский Банк приобрести у меня иностранную валюту на нижеследующих условиях:
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<table class="docTableBorder" style="width:172mm;height:100%;" cellpadding="0" cellspacing="0">
							<tr>
								<td class="docTdBorder textPadding" valign="top" align="center" width="10%">
									Код<br/> валюты
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center" width="20%">
									Сумма продажи инвалюты<br/>
									(заполняется клиентом)
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center" width="40%">
									Курс сделки в рублях<br/>
									(договорная цена)
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									Выручка от продажи по курсу сделки<br/>
									(заполняется банком)
								</td>
							</tr>
							<tr>
								<td class="docTdBorder textPadding" valign="top" align="center">
									1
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									2
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									3
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									4
								</td>
							</tr>
							<tr>
								<td class="docTdBorder textPadding" valign="top" align="center">
									<xsl:value-of select="foreignCurrency"/>&nbsp;
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									<xsl:value-of select="foreignCurrencyAmount"/>&nbsp;
								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									на условиях: <input type="Text" readonly="true" class="insertInput" style="width:13%" align="right" value="TOD"/><input type="Text" readonly="true" class="insertInputnoBorder" style="width:35%" align="left" value="/TOM /SPOT"/>
						                                                    (нужное подчеркнуть)<br/>

						по курсу банка:<input type="Text" readonly="true" class="insertInput" style="width:32%" align="center">
										<xsl:attribute name="value">
											<xsl:value-of select="course"/>&nbsp;
										</xsl:attribute>
									</input>

								</td>
								<td class="docTdBorder textPadding" valign="top" align="center">
									<xsl:value-of select="rurAmount"/>&nbsp;
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td>
									Валютные средства в объеме сделки просим списать с моего валютного счета
								</td>
							</tr>
							<tr>
								<td>
									№
									<input type="Text" readonly="true" class="insertInput" style="width:32%" align="center">
										<xsl:attribute name="value">&nbsp;<xsl:value-of select="payerAccountSelect"/>&nbsp;</xsl:attribute>
									</input>
									в АКБ "РУССЛАВБАНК" (ЗАО).
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td>
						<br/>
						<br/>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						Подпись физического лица ________________________________________
					</td>
				</tr>
			</table>
		</div>

		<script type="text/javascript">
			function monthToStringOnly(str1)
			{
				var month;
				var date;
				date = "";
				month = str1.substring(3, 5)-1;
				switch(month)
				{
					    case 0:date = date + "января";break;
			            case 1:date = date + "февраля";break;
			            case 2:date = date + "марта";break;
			            case 3:date = date + "апреля";break;
			            case 4:date = date + "мая";break;
			            case 5:date = date + "июня";break;
			            case 6:date = date + "июля";break;
			            case 7:date = date + "августа";break;
			            case 8:date = date + "сентября";break;
			            case 9:date = date + "октября";break;
			            case 10:date = date + "ноября";break;
			            case 11:date = date + "декабря";break;
						default:date = date + "unknown";break;
				}
			return date;
			}

		function init()
			{
			    var purchaseMonth = document.getElementById("purchaseMonth");
			    purchaseMonth.value = monthToStringOnly(purchaseMonth.value);

    			var saleMonth = document.getElementById("saleMonth");
				saleMonth.value = monthToStringOnly(saleMonth.value);

				var purchaseDiv = document.getElementById("purchaseDiv");
				var saleDiv = document.getElementById("saleDiv");
				var purchaseElem    = document.getElementById("type");
				if (purchaseElem.value == 2)
				{
					purchaseDiv.style.display = "block";
					saleDiv.style.display = "none";
				}
				else
				{
					purchaseDiv.style.display = "none";
					saleDiv.style.display = "block";
				}
			}
			init();</script>

	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;>html" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="rur&#x2D;accounts.xml" srcSchemaRoot="entity&#x2D;list" AssociatedInstance="file:///c:/Projects/RS/PhizIC&#x2D;SBRF/Business/settings/forms/PurchaseCurrencyPayment/rur&#x2D;accounts.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template><template match="/form&#x2D;data"><block path="script/xsl:value&#x2D;of" x="172" y="18"/><block path="table/tr[1]/td[1]/xsl:if" x="172" y="97"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="12" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="126" y="175"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="172" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="172" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="57"/><block path="table/tr[1]/td[1]/xsl:if[1]" x="132" y="97"/><block path="table/tr[3]/td[1]/xsl:if" x="92" y="97"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="212" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="86" y="175"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="132" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="12" y="57"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="17"/><block path="table/tr[3]/td[1]/xsl:if[1]" x="52" y="97"/><block path="table/tr[4]/td[1]/span/xsl:value&#x2D;of" x="12" y="97"/><block path="script[1]/xsl:for&#x2D;each" x="222" y="107"/><block path="" x="12" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of" x="132" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of[1]" x="52" y="137"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->