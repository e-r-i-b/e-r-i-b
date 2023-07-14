<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:template match="/form-data">
		<script type="text/javascript">
			var card = '<xsl:value-of select="card"/>';
			var cardType = '<xsl:value-of select="cardType"/>';
			var code = '<xsl:value-of select="phoneCode"/>';
			var number = '<xsl:value-of select="phoneNumber"/>';
			var operator = '<xsl:value-of select="operatorSelect"/>';
			var date = '<xsl:value-of select="dateCreated"/>';
		</script>
		<style>
			.smallText{font-family:Times New Roman;font-size:9pt;font-style:italic;font-weight:bold}
			.text{font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textNotBold{font-family:Times New Roman;font-style:italic}
			.textPadding{font-stretch:ultra-expanded; border:0px;padding-top:15;padding-bottom:15;font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textPaddingBottom{padding-top:5;padding-bottom:35;font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.docTdBorderBottom{border-bottom:1px solid #000000;}
			.bd {border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:5mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdLast {border:1px solid black; width:5mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdNumber {text-align:center; BORDER-TOP: black 1px solid; FONT-SIZE: 10pt; BORDER-LEFT: black 1px solid; WIDTH: 6mm; BORDER-BOTTOM: black 1px solid; FONT-FAMILY: Times New Roman;}
			.bdNumberLast {text-align:center; BORDER: black 1px solid; FONT-SIZE: 10pt; WIDTH: 6mm; FONT-FAMILY: Times New Roman;}
			.allInput {border:1px solid black; height:13px;width:13px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
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
				<td class="textPadding" colspan="2" align="center">
					<span style="font-size:18pt;">Заявление<br/></span>на подключение услуги SMS-информирования по мобильному телефону</td>
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
				<td class="docTdBorderBottom" colspan="2">&nbsp;<br/></td>
			</tr>
			<tr>
				<td class="textPadding" colspan="2" style="padding-top:10mm">Прошу Вас подключить услугу SMS-информирования по операциям по пластиковой карте:<br/></td>
			</tr>
			<tr>
				<td colspan="2">
					<br/>
					<table>
						<tr>
							<td width="70%">
								<table  cellspacing="0">
									<tr  style="height:6mm">
										<td width="10mm"></td>
										<td class="bd" >№</td>
										<script type="text/javascript">
										<![CDATA[
										for ( var i = 0; i < card.length-1; i++ )
										{
											if( (i==0) || (card.length-1-i<4))
												document.write("<td class='bd'>"+card.charAt(i)+"</td>");
											else document.write("<td class='bd'>&nbsp;</td>"); 
										}
										document.write("<td class='bdLast'>"+card.charAt(card.length-1)+"</td>");
										]]>
										</script>
										</tr>
								</table>
							</td>
							<td>
								<table>
									<tr>
										<td class="docTdBorderBottom">
											<script type="text/javascript">
												<![CDATA[
												document.write(cardType);
												]]>
											</script>
										</td>
									</tr>
									<tr>
										<td align="center" class="text" style="font-size:7pt" nowrap="true">тип карты:Visa, EuroCardMasterCard</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<br/>
				</td>
			</tr>
			<tr>
				<td class="textPadding" style="padding-top:7mm" colspan="2">на мобильный телефон,<br/>
					<table>
						<tr>
							<td class="text">сотовый оператор:</td>
							<td class='text' style="border-bottom:1px solid #000000;">
								МТС&nbsp;&nbsp;&nbsp;<input class='allInput' readonly='true' type='text' id="op0"/>&nbsp;,&nbsp;
								БиЛайн&nbsp;&nbsp;&nbsp;<input class='allInput' readonly='true' type='text' id="op1"/>&nbsp;,&nbsp;
								Мегафон&nbsp;&nbsp;&nbsp;<input class='allInput' readonly='true' type='text' id="op2"/>&nbsp;,&nbsp;
								Скайлинк&nbsp;&nbsp;&nbsp;<input class='allInput' readonly='true' type='text' id="op3"/>&nbsp;&nbsp;
								<script type="text/javascript">
									<![CDATA[
									var operators=new Array('МТС','БиЛайн','Мегафон','Скайлинк');
									for ( var i = 0; i < 6; i++ )
										{
										check = document.getElementById('op'+i);
											if (operators[i] == operator)
												check.value='V';
										 }
									]]>
								</script>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table cellspacing="0">
						<tr style="height:6mm">
							<td width="10mm"></td>
										<script type="text/javascript">
										<![CDATA[
										var count = code.length;
										for ( var i = 0; i < count; i++ )
										{ document.write("<td class='bdNumber' style='background-color: rgb(200,200,200)'>"+code.charAt(i)+"</td>");}
										var count = number.length;
										for ( var i = 0; i < count-1; i++ )
										{ document.write("<td class='bdNumber'>"+number.charAt(i)+"</td>");}
										document.write("<td class='bdNumberLast'>"+number.charAt(count-1)+"</td>");
										]]>
										</script>
						</tr>
						<tr>
							<td width="10mm"></td>
							<td colspan="3" align="center" class="smallText" nowrap="true"><br/>(код оператора)</td>
							<td colspan="7" align="center" class="smallText"><br/>номер телефона</td>
						</tr>
					</table>
					<br/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span class="textPadding">С тарифами и условиями ознакомлен, расходы за предоставление услуги<br/>
						SMS-информирования прошу Вас списать с моего карточного счета</span><br/>
						<table><tr><td class="text">№</td><td class="docTdBorderBottom" style="width:80mm; text-align:center"><xsl:value-of select="account"/></td><td>.</td></tr></table>
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
										 document.write("<td>&quot;</td><td class='docTdBorderBottom' style='text-align:center; width:8mm'>"+dateArray[2]+"</td><td>&quot;</td>");
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
										<td align="center" class="textNotBold" style="font-size:10pt" nowrap="true">(подпись Клиента)</td>
									</tr>
								</table>
			</td>
			</tr>
			<tr>
				<td colspan="2" class="text" style="font-size:8pt; padding-left:35mm; border:1px solid black; background-color:rgb(220,220,220)" nowrap="true">Отметки Банка</td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold" style="padding-top:10mm;"><span class="text" style="font-size:8pt">Заявление принял</span> &quot;___&quot; _____________ 200 _г. ___________________________________</td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold" style="padding-right:20mm; font-size:8pt" align="right">(Ф.И.О и подпись сотрудника Банка)</td>
			</tr>
		</table>
		&nbsp;
		&nbsp;
	</xsl:template>

</xsl:stylesheet>