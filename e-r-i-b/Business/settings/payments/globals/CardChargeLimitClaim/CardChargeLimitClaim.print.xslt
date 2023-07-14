<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:variable name="pictureUrl" select="resourceRoot"/>

	<xsl:template match="/form-data">
		<script type="text/javascript">
			var card = '<xsl:value-of select="additCardNumber"/>';
			var date = '<xsl:value-of select="documentDate"/>';
			var account = '<xsl:value-of select="account"/>';
			var cardType = '<xsl:value-of select="cardType"/>';
			var cur = '<xsl:value-of select="currency"/>';
		</script>
		<style>
			.text{font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textBoldItalic{font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textBold12{font-family:Times New Roman;font-size:12pt;font-weight:bold;margin:0px;}
			.textBold10{font-family:Times New Roman;font-size:10pt;font-weight:bold}
			.textPlain{font-family:Times New Roman;font-size:12pt;}
			.textPlain10{font-family:Times New Roman;font-size:10pt;}
			.textItalicSmall{font-family:Times New Roman;font-size:9pt;font-style:italic}
			.padmar{padding:0; margin:0}
			.docTdBorderBottom{border-bottom:1px solid #000000;}
			.bd {border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:6mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdLast {border:1px solid black; width:6mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.justBorder {border-bottom:1px solid black; border-right:1px solid black;}
			.allInput {border-top:2px solid black; border-right:2px solid black; border-left:1px solid black; border-bottom:1px solid black; height:13px;width:13px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
		</style>
		<table style="width:180mm" cellspacing="0" cellpadding="0">
			<tr>
				<td style="padding-top:10mm;">
					<img src="{$resourceRoot}/images/imagesMZB/logoForDoc.gif" width="100%" border="0" />
				</td>
			</tr>
		    <tr>
			    <td class="textBoldItalic" style="font-size: 18pt;" align="center">
					<p >Заявление</p>
					<p align="center" class="textBold12">На установление лимита по банковской карте<br/>
					АКБ "Московский залоговый банк" (ЗАО)</p>
					<p class="textBoldItalic" align="right">
					<br/>Начальнику Отдела оформления
					<br/>операций по банковским картам
					<br/>Искаковой Ж.М.</p>
			    </td>
		    </tr>
			<tr>
				<td class="text" nowrap="true" >
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td class="textBoldItalic padmar" width="21%">Я, (Ф.И.О. полностью)</td>
							<td class="docTdBorderBottom textPlain" width="79%">&nbsp;&nbsp;&nbsp;<xsl:value-of select="fullName"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td >&nbsp;<br/></td>
			</tr>
			<tr>
				<td class="text" nowrap="true">
					<table width="100%">
						<tr>
							<td class="textBoldItalic padmar" width="21%">Контактный телефон:</td>
							<td class="docTdBorderBottom textPlain" align="left" width="79%">&nbsp;&nbsp;&nbsp;<xsl:value-of select="phone"/></td>
						</tr>
					</table>
		    	</td>
			</tr>
		    <tr>
				<td>&nbsp;<br/></td>
			</tr>
		    <tr>
			    <td>
					<table cellspacing="0">
						<tr>
							<td style="border-top: 1px solid black; border-left: 1px solid black; border-bottom: 1px solid black; font-size: 11pt;" nowrap="true">
								&nbsp;№ счёта по основной карте&nbsp;
							</td>
							<script type="text/javascript">
												<![CDATA[
												for ( var i = 0; i < account.length-1; i++ )
												{
													document.write("<td class='bd padmar'>"+account.charAt(i)+"</td>");
												}
												document.write("<td class='bdLast padmar'>"+account.charAt(account.length-1)+"</td>");
												]]>
							</script>
						</tr>
					</table>
			    </td>
		    </tr>

			<tr>
				<td class="textPadding" style="padding-top:10mm">
					<p class="textBold12 padmar">Прошу установить лимит по дополнительной карте:</p>
					<br/>
					<p class="textBold10 padmar">карту/карты (нужное отметить)</p>
				</td>
			</tr>
		<tr>
			<td>
				<table style="border-top: 1px solid black; border-left: 1px solid black;" cellspacing="0">
				<script type="text/javascript">
									<![CDATA[
									var types=['VISA Gold','MasterCard Gold','VISA Classic','MasterCard Standard','VISA Electron','CirrusMaestro'];
									document.write("<tr>");
									for ( var i = 0; i < 6; i++ )
										{
										if (types[i] == cardType)
											document.write("<td class='justBorder' width='50%'>&nbsp;<input class='allInput' readonly='true' type='text' value='V'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+cardType+"</td>");
										else
											document.write("<td class='justBorder'>&nbsp;<input class='allInput' readonly='true' type='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+types[i]+"</td>");
										if ((i%2) != 0)
											document.write("</tr><tr>");
										}
									document.write("</tr>");
									]]>
				</script>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;<br/></td>
		</tr>
		<tr>
			<td>
				<table cellspacing="0">
					<tr>
						<td style="border-top: 1px solid black; border-left: 1px solid black; border-bottom: 1px solid black; font-size: 11pt;" nowrap="true" width="33%">
							&nbsp;Дополнительная карта №&nbsp;
						</td>
							<script type="text/javascript">
												<![CDATA[
												var cardArray = card.split("[");
												for ( var i = 0; i < cardArray[0].length-1; i++ )
												{
													if((i == 0) || (cardArray[0].length-1-i < 4))
														document.write("<td class='bd'>"+cardArray[0].charAt(i)+"</td>");
													else document.write("<td class='bd'>&nbsp;</td>"); 	
												}
												document.write("<td class='bdLast'>"+cardArray[0].charAt(cardArray[0].length-1)+"</td>");
												]]>
							</script>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td >&nbsp;<br/></td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="padmar">
					<tr>
						<td class="textBoldItalic padmar" width="25%">ФИО (держателя доп. карты)</td>
						<td class="docTdBorderBottom textPlain padmar" width="75%">&nbsp;&nbsp;&nbsp;<xsl:value-of select="ownerName"/></td>
					</tr>
					<tr>
						<td class="textBoldItalic padmar" width="25%"></td>
						<td class="textItalicSmall padmar" align="center" width="75%">ФИО полностью</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="padmar">
					<tr>
						<td class="textBoldItalic" nowrap="true">Лимит ограничений</td>
						<td class="docTdBorderBottom" width="20%">&nbsp;&nbsp;&nbsp;
							<xsl:value-of select="limit"/>&nbsp;<xsl:value-of select="currency"/>
						</td><td>(</td>
						<td class="docTdBorderBottom textPlain10" align="center" width="60%">&nbsp;&nbsp;&nbsp;
							<xsl:variable name="limitV" select="limit"/>
							<xsl:variable name="currencyV" select="currency"/>
							<xsl:value-of select="phizic:sumInWords($limitV, $currencyV)"/>
						</td><td>)</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="padmar">
					<tr>
						<td class="textBoldItalic"></td>
						<td class="textItalicSmall" align="center">&nbsp;&nbsp;&nbsp;Сумма цифрами</td>
						<td class="textItalicSmall" align="center">&nbsp;&nbsp;&nbsp;Сумма прописью</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="padmar">
					<tr>
						<td class="textBoldItalic padmar" nowrap="true" width="21%">Период ограничений</td>
						<td class="docTdBorderBottom textPlain" width="79%" align="left">&nbsp;&nbsp;&nbsp;
							<xsl:value-of select="period"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="padmar">
					<tr>
						<td class="textBoldItalic padmar" nowrap="true">Сумма наличных операций</td>
						<td class="docTdBorderBottom" width="20%">&nbsp;&nbsp;&nbsp;
							<xsl:value-of select="amount"/>&nbsp;<xsl:value-of select="currency"/>
						</td><td>(</td>
						<td class="docTdBorderBottom textPlain10" align="center" width="60%">&nbsp;&nbsp;&nbsp;
							<xsl:variable name="amountV" select="amount"/>
							<xsl:variable name="currencyV" select="currency"/>
							<xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/>
						</td><td>)</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td class="textItalicSmall" ></td>
						<td class="textItalicSmall" align="center">&nbsp;&nbsp;&nbsp;Сумма цифрами</td>
						<td class="textItalicSmall" align="center">&nbsp;&nbsp;&nbsp;Сумма прописью</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td class="textBoldItalic padmar" nowrap="true" width="">Сумма безналичных операций</td>
						<td class="docTdBorderBottom" width="20%">&nbsp;&nbsp;&nbsp;
							<xsl:value-of select="amountv"/>&nbsp;<xsl:value-of select="currency"/>
						</td><td>(</td>
						<td class="docTdBorderBottom textPlain10" align="center" width="60%">&nbsp;&nbsp;&nbsp;
							<xsl:variable name="amountV" select="amountv"/>
							<xsl:variable name="currencyV" select="currency"/>
							<xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/>
						</td><td>)</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td class="textItalicSmall" width="18%"></td>
						<td class="textItalicSmall" align="center" width="18%">&nbsp;&nbsp;&nbsp;Сумма цифрами</td>
						<td class="textItalicSmall" align="center">&nbsp;&nbsp;&nbsp;Сумма прописью</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td width="20%" class="textPlain padmar">Дата</td>
						<td width="30%" class="bdLast textPlain"><script type="text/javascript">
							<![CDATA[
							var dateArray = date.split(".");
							var months= new Array(
								new Array ('01','января'),
								new Array ('02','февраля'),
								new Array ('03','марта'),
								new Array ('04','апреля'),
								new Array ('05','мая'),
								new Array ('06','июня'),
								new Array ('07','июля'),
								new Array ('08','августа'),
								new Array ('09','сентября'),
								new Array ('10','октября'),
								new Array ('11','ноября'),
								new Array ('12','декабря')
							);
							dateCreate = new Date;
							var yearString = dateCreate.getFullYear().toString();
							document.write("&nbsp;&nbsp;"+dateArray[0]+"&nbsp;");
							for(var i=0;i<12;i++){
								if (months[i][0] == dateArray[1]) {
									document.write("&nbsp;"+months[i][1]+"&nbsp;");
									break;
								}
							}
							document.write("&nbsp;"+dateArray[2]+"&nbsp;г.");
							]]>
						</script>
						</td>
						<td width="20%" class="textPlain padmar">
							Подпись клиента
						</td>
						<td class="bdLast" width="20%">
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="padmar">
					<tr>
						<td class="textBold10 padmar" width="25%" align="right" style="padding-right:100px%">Заявление принял и проверил</td>
						<td class="bdLast">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="100%">
				<p class="textItalicSmall padmar" align="right" style="padding-right:9%;">(Ф.И.О и подпись сотрудника Банка)</p>
			</td>
		</tr>
	</table>
	</xsl:template>

</xsl:stylesheet>