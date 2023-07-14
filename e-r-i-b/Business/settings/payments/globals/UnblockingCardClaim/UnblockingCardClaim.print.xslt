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
			var anotherReason = '<xsl:value-of select="anotherReason"/>';
			var date = '<xsl:value-of select="dateCreated"/>';
			var picture = '{$resourceRoot}' + '/images/imagesMZB';
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
			.justBorder {border-bottom:1px solid black; border-right:1px solid black;}
			.justPadding {padding-top:5; padding-bottom:5}
			.allInput {border-top:2px solid black; border-right:2px solid black; border-left:1px solid black; border-bottom:1px solid black; height:13px;width:13px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
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
				<td class="textPadding" colspan="2" style="padding-top:10mm">
				<table cellspacing="0">
				<tr>
					<td class="text" style="border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black;" nowrap="true">Прошу Вас разблокировать пластиковую карту № </td>
					<script type="text/javascript">
					<![CDATA[
					for ( var i = 0; i < card.length-1; i++ )
					{
						if( (i==0) || (card.length-1-i<4) )
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
			<tr class="justPadding" style="padding-top:30">
				<td colspan='2' >
					<table>
						<tr>
							<td width='55mm'>
								<input id='td0' class='allInput' readonly='true' type='text'/>
							</td>
							<td>В связи с превышением попыток неправильного ввода PIN-кода</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr class="justPadding">
				<td colspan='2'>
					<table>
						<tr>
							<td width='55mm'>
								<input id='td1' class='allInput' readonly='true' type='text'/>
							</td>
							<td>В связи с находкой карты (PIN-кода)</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr class="justPadding" style="padding-bottom:30">
			<td colspan='2'>
				<table  cellspacing="0">
					<tr width="100%">
						<td width='55mm' style="padding-left:0.7mm">
								<input id='td2' class='allInput' readonly='true' type='text'/>
							</td>
						<td nowrap="true">Другая причина&nbsp;</td>
						<td style="border-bottom:1px solid black;width=70%">
						<xsl:variable name="aReason" select="anotherReason"/>
							<xsl:choose>
								<xsl:when test="$aReason!='не заполняется'">
									&nbsp;<xsl:value-of select="anotherReason"/>
								</xsl:when>
							</xsl:choose>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
			</tr>
			<script type="text/javascript">
						<![CDATA[
						var reasons=['Превышение попыток неправильного ввода PIN-кода','Находка карты (PIN-кода)','Другое'];
						for ( var i = 0; i < 3; i++ )
							{
							var check = document.getElementById('td'+i);
							if (reasons[i] == reason) check.value='V';
							}
						]]>
			</script>
  			<tr>
				<td colspan="2">
					<span class="textPadding">Прошу все расходы, возникшие с этими операциями списать с моего карточного счета</span><br/>
						<table><tr style="height:5mm"><td colspan="2"></td></tr><tr><td class="text">№</td><td class="docTdBorderBottom" style="width:70mm; text-align:center"><xsl:value-of select="account"/></td></tr></table>
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
										<td align="center" class="textNotBold" style="font-size:10pt; padding-bottom:30" nowrap="true">(подпись Клиента)</td>
									</tr>
								</table>
			</td>
			</tr>
			<tr>
				<td colspan="2" class="text" style="font-size:8pt; padding-left:35mm; border:1px solid black; background-color:rgb(220,220,220)" nowrap="true">Отметки Банка</td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold" style="padding-top:10mm;"><span class="text" style="font-size:8pt">Заявление принял</span>&nbsp;&quot;___&quot; _____________ 200 _г.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;___________________________</td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold" style="padding-right:20mm; font-size:8pt" align="right">(Ф.И.О и подпись сотрудника Банка)</td>
			</tr>
		</table>
		&nbsp;
		&nbsp;
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="test.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->