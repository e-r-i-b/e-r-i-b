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
			var card = '<xsl:value-of select="card"/>';
			var cardType = '<xsl:value-of select="cardType"/>';
			var reason = '<xsl:value-of select="reason"/>';
			var anotherReason = '<xsl:value-of select="conditionsOfLost"/>';
			var anotherCardType = '<xsl:value-of select="anotherCardType"/>';
			var currency = '<xsl:value-of select="currency"/>';
			var date = '<xsl:value-of select="dateCreated"/>';
		</script>
		<style>
			.smallText{font-family:Times New Roman;font-size:9pt;font-style:italic;font-weight:bold}
			.text{font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textNotBold{font-family:Times New Roman;font-style:italic}
			.textPadding{font-stretch:ultra-expanded; border:0px;padding-top:10;padding-bottom:10;font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textPaddingBottom{padding-top:5;padding-bottom:35;font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.docTdBorderBottom{border-bottom:1px solid #000000;}
			.bd {border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:5mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdLast {border:1px solid black; width:5mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdNumber {text-align:center; BORDER-TOP: black 1px solid; FONT-SIZE: 10pt; BORDER-LEFT: black 1px solid; WIDTH: 6mm; BORDER-BOTTOM: black 1px solid; FONT-FAMILY: Times New Roman;}
			.bdNumberLast {text-align:center; BORDER: black 1px solid; FONT-SIZE: 10pt; WIDTH: 6mm; FONT-FAMILY: Times New Roman;}
			.justBorder {border-bottom:1px solid black; border-right:1px solid black;}
			.justPadding {padding-top:5; padding-bottom:5}
			.allInput {border-top:2px solid black; border-right:2px solid black; border-left:1px solid black; border-bottom:1px solid black; height:13px;width:13px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
			.curInput {border:1px solid black; height:10px; width:10px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
			.justBorder {border-bottom:1px solid black; border-right:1px solid black; padding-left:5mm}
			.justBorderBottom {border-bottom:1px solid black; vertical-align:top}

		</style>
		<table cellpadding="0" cellspacing="0" width="220mm">
			<col/>
			<tr>
				<td style="padding-top:10mm;" colspan="2">
					<img src="{$resourceRoot}/images/imagesMZB/logoForDoc.gif" border="0"/>
				</td>
			</tr>
			<tr align="right">
				<td class="textPaddingBottom" colspan="2"><br/>Начальнику Отдела оформления <br/>операций по банковским картам <br/>г-же Искаковой Ж.М.<br/></td>
			</tr>
			<tr>
				<td class="textPadding" colspan="2" style="font-size:18pt; text-align:center; padding-top:23">
					Заявление</td>
			</tr>
			<tr>
				<td class="text" nowrap="true" colspan="2">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td class="text" nowrap="true">Я, (Ф.И.О. полностью)</td>
							<td class="docTdBorderBottom" style="width:75%">&nbsp;&nbsp;&nbsp;
										<xsl:value-of select="fullName"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="textPadding" colspan="2" style="padding-top:5mm">
				<table cellspacing="0">
				<tr>
					<td class="text" style="border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black;" nowrap="true">Прошу Вас заблокировать пластиковую карту № </td>
					<script type="text/javascript">
					<![CDATA[
					for ( var i = 0; i < card.length-1; i++ )
					{
					 	if((i == 0) || (card.length-1-i < 4))
						document.write("<td class='bd'>"+card.charAt(i)+"</td>");
						else document.write("<td class='bd'>&nbsp;</td>"); 
					}
					document.write("<td class='bdLast'>"+card.charAt(card.length-1)+"</td>");
					]]>
					</script>
				</tr>
				</table><br/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table style="border-top:1px solid black; border-left:1px solid black; width=100%" cellspacing="0" cellpadding="0">
						<tr>
							<td class="justBorderBottom" style="text-align:right; width:15mm">
								<input class='allInput' readonly='true' type='text' id="card0"/>
							</td>
							<td class="justBorder" style="width:95mm">
								VISA Gold
							</td>
							<td class="justBorderBottom" style="text-align:right; width:15mm">
								<input class='allInput' readonly='true' type='text' id="card1"/>
							</td>
							<td class="justBorder" style="width:95mm">
								MasterCard Gold
							</td>
						</tr>
						<tr>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="card2"/>
							</td>
							<td class="justBorder">
								VISA Classic
							</td>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="card3"/>
							</td>
							<td class="justBorder">
								MasterCard Standard
							</td>
						</tr>
						<tr>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="card4"/>
							</td>
							<td class="justBorder">
								VISA Electron
							</td>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="card5"/>
							</td>
							<td class="justBorder">
								Maestro
							</td>
						</tr>
							<script type="text/javascript">
							<![CDATA[
							var types=['VISA Gold','MasterCard Gold','VISA Classic','MasterCard Standard','VISA Electron','Maestro'];
							for ( var i = 0; i < 6; i++ )
								{
								check = document.getElementById('card'+i);
									if (types[i] == cardType)
										check.value='V';
								 }
							]]>
							</script>
					</table>
				</td>
			</tr>
			<tr style="padding-top:20">
				<td colspan='2' >
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td class="text" style="width:75mm" nowrap="true">В связи: с&nbsp;</td>
							<td class="text" id="td0" style="width:60mm; text-align:right"> кражей,</td>
							<td class="text" id="td1" style="width:60mm; text-align:right"> утерей,</td>
							<td class="text" id="td2" style="width:60mm; text-align:right"> порчей,</td>
							<td class="text" id="td3" style="width:60mm; text-align:right"> прочее,</td>
							<td class="docTdBorderBottom" style="width:40%">&nbsp;
								<xsl:variable name="lost" select="conditionsOfLost"/>
								<xsl:choose>
									<xsl:when test="$lost!='не заполняется'">
										&nbsp;<xsl:value-of select="conditionsOfLost"/>&nbsp;
									</xsl:when>
								</xsl:choose>
							</td>
						</tr>
						<tr><td colspan="6" class="text" style="font-size:8pt; padding-left:25mm">(нужное подчеркнуть)</td></tr>
					</table>
				</td>
			</tr>
			<script type="text/javascript">
						<![CDATA[
						var reasons=['Кража','Утеря','Порча','Другое'];
						for ( var i = 0; i < 4; i++ )
							{
							var underline = document.getElementById('td'+i);
							if (reasons[i] == reason)
								underline.style.borderBottom="black solid 1px";
							}
						]]>
			</script>
			<tr style="PADDING-TOP: 20px">
				<td class="text" colspan="2">
					А также перевыпустить пластиковую карту:
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table style="border-top:1px solid black; border-left:1px solid black; width=100%" cellspacing="0" cellpadding="0">
						<tr>
							<td class="justBorderBottom" style="text-align:right; width:15mm">
								<input class='allInput' readonly='true' type='text' id="cardA0"/>
							</td>
							<td class="justBorder" style="width:95mm">
								VISA Gold
							</td>
							<td class="justBorderBottom" style="text-align:right; width:15mm">
								<input class='allInput' readonly='true' type='text' id="cardA1"/>
							</td>
							<td class="justBorder" style="width:95mm">
								MasterCard Gold
							</td>
						</tr>
						<tr>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="cardA2"/>
							</td>
							<td class="justBorder">
								VISA Classic
							</td>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="cardA3"/>
							</td>
							<td class="justBorder">
								MasterCard Standard
							</td>
						</tr>
						<tr>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="cardA4"/>
							</td>
							<td class="justBorder">
								VISA Electron
							</td>
							<td class="justBorderBottom" style="text-align:right">
								<input class='allInput' readonly='true' type='text' id="cardA5"/>
							</td>
							<td class="justBorder">
								Maestro
							</td>
						</tr>
							<script type="text/javascript">
							<![CDATA[
							var types=['VISA Gold','MasterCard Gold','VISA Classic','MasterCard Standard','VISA Electron','Maestro'];
							for ( var i = 0; i < 6; i++ )
								{
								check = document.getElementById('cardA'+i);
									if (types[i] == anotherCardType)
										check.value='V';
								 }
							]]>
							</script>
					</table>
				</td>
			</tr>
			<tr style="PADDING-TOP: 20px">
				<td colspan="2">
					<table cellpadding="0" cellspacing="0">
						<td class="text" nowrap="true">В долларах США</td>
						<td width='55mm' align="center">
								<input id='c0' class='curInput' readonly='true' type='text'/>
						</td>
						<td class="text" nowrap="true">В евро</td>
						<td width='55mm' align="center">
								<input id='c1' class='curInput' readonly='true' type='text'/>
						</td>
						<td class="text" nowrap="true">В рублях РФ</td>
						<td width='55mm' align="center">
								<input id='c2' class='curInput' readonly='true' type='text'/>
						</td>
						<td class="text" nowrap="true">в течении:</td>
						<td class="docTdBorderBottom" style="width:10%" nowrap="true"><xsl:value-of select="period"/>&nbsp;дней</td>
					</table>
				</td>
			</tr>
			<script type="text/javascript">
						<![CDATA[
						var currencies=['Доллары США','Евро','Рубли РФ'];
						for ( var i = 0; i < 3; i++ )
							{
							var check = document.getElementById('c'+i);
							if (currencies[i] == currency)
							    check.value='V';
							}
						]]>
			</script>
			<tr>
				<td colspan="2" class="text" style="PADDING-TOP: 25px">
					Прошу все расходы, возникшие с этими операциями списать с моего карточного счета<br/>
						<table><tr style="height:3mm"><td colspan="2"></td></tr><tr><td class="text">№</td><td class="docTdBorderBottom" style="width:70mm; text-align:center"><xsl:value-of select="account"/></td><td>.</td></tr></table>
				</td>
			</tr>
			<tr><td colspan="2"  style="padding-top:10mm; padding-right:10mm">
				<table width="100%">
									<tr>
										<td nowrap="true">
										<table>
										<tr>
										<script type="text/javascript">
										<![CDATA[
										var dateArray = date.split(" ");
										var months= new Array(
											new Array ('Jan','января'),
											new Array ('Feb','февраля'),
											new Array ('Mar','марта'),
											new Array ('Apr','апреля'),
											new Array ('May','мая'),
											new Array ('Jun','июня'),
											new Array ('Jul','июля'),
											new Array ('Aug','августа'),
											new Array ('Sep','сентября'),
											new Array ('Oct','октября'),
											new Array ('Nov','ноября'),
											new Array ('Dec','декабря')
											);
										dateCreate = new Date;
										var yearString = dateCreate.getFullYear().toString();
										 document.write("<td>«</td><td class='docTdBorderBottom' style='text-align:center; width:8mm'>"+dateArray[2]+"</td><td>»</td>");
										 for(var i=0;i<12;i++){
										 if (months[i][0]==dateArray[1]) document.write("<td class='docTdBorderBottom' style='width:25mm; text-align:center'>"+months[i][1]+"</td>");
										 }
										 document.write("<td class='textNotBold'>"+dateArray[5].charAt(0)+dateArray[5].charAt(1)+dateArray[5].charAt(2)+"</td><td class='docTdBorderBottom'>"+dateArray[5].charAt(3)+"</td><td class='textNotBold'>г.</td>");
										]]>
										</script>
										</tr>
										</table>
										</td>
										<td class="docTdBorderBottom" width="80mm">&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td align="center" class="textNotBold" style="font-size:10pt; padding-bottom:30" nowrap="true">(подпись Клиента)</td>
									</tr>
								</table>
			</td>
			</tr>
			<tr>
				<td colspan="2" class="text" style="font-size:8pt; padding-left:35mm; border:1px solid black; background-color:rgb(220,220,220)" nowrap="true">Отметки Банка</td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold" style="padding-top:10mm;"><span class="text" style="font-size:8pt">Заявление принял</span>&nbsp;«___» _____________ 200 _г.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;___________________________</td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold" style="padding-right:20mm; font-size:8pt" align="right">(Ф.И.О и подпись сотрудника Банка)</td>
			</tr>
		</table>
		&nbsp;
		&nbsp;
	</xsl:template>

</xsl:stylesheet>