<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:variable name="actionType" select="document('actionTypes')/entity-list/entity"/>
	<xsl:variable name="pictureUrl" select="resourceRoot"/>

	<xsl:template match="/form-data">
		<script type="text/javascript">
			var card = '<xsl:value-of select="card"/>';
			var phone = '<xsl:value-of select="phone"/>';
			var date = '<xsl:value-of select="documentDate"/>';
			var cardType = '<xsl:value-of select="cardType"/>';
			var actionCode = '<xsl:value-of select="actionType"/>';
			var expCardDate = '<xsl:value-of select="expireCardDate"/>';
			var expBlockDate = '<xsl:value-of select="expireBlockDate"/>';

			function setValueAndChecked(elementId, value)
			{
				var elem = document.getElementById(elementId);

				if (elem != null)
				{
					elem.value = value;
					elem.checked = value;
				}
			}

			var reg1 = <xsl:value-of select="region1"/>;
			var reg2 = <xsl:value-of select="region2"/>;
			var reg3 = <xsl:value-of select="region3"/>;
			var reg4 = <xsl:value-of select="region4"/>;
			var reg5 = <xsl:value-of select="region5"/>;
			var reg6 = <xsl:value-of select="region6"/>;
			var reg7 = <xsl:value-of select="region7"/>;
			var reg8 = <xsl:value-of select="region8"/>;
			var other = "";
			<xsl:if test="region8 = 'true'">
				other = '<xsl:value-of select="other"/>';
			</xsl:if>
			var rea1 = <xsl:value-of select="reason1"/>;
			var rea2 = <xsl:value-of select="reason2"/>;
			var rea3 = <xsl:value-of select="reason3"/>;
			var rea4 = <xsl:value-of select="reason4"/>;
			var rea5 = <xsl:value-of select="reason5"/>;
		</script>
		<script type="text/javascript" language="javascript">
			var actions = new Array();
			var act;
			<xsl:for-each select="$actionType">
				<xsl:variable name="actionType" select="@key"/>
			act          = new Object()
			act.code     = '<xsl:value-of select="@key"/>';
			act.type     = '<xsl:value-of select="."/>';
			actions[actions.length] = act;
			</xsl:for-each>
		</script>
		<style>
			.textBoldItalic{font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textBold12{font-family:Times New Roman;font-size:12pt;font-weight:bold;margin:0px;}
			.textTitle{font-family:Times New Roman;font-size:16pt;font-style:italic;font-weight:bold}
			.textBold10{font-family:Times New Roman;font-size:10pt;font-weight:bold}
			.textPlain{font-family:Times New Roman;font-size:12pt;}
			.textPlain10{font-family:Times New Roman;font-size:10pt;}
			.textItalicSmall{font-family:Times New Roman;font-size:9pt;font-style:italic}
			.padmar{padding:0; margin:0}
			.docTdBorderBottom{border-bottom:1px solid #000000;}
			.bd {border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:6mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdLast {border:1px solid black; text-align:center}
			.justBorder {border-bottom:1px solid black; border-right:1px solid black;}
			.allInput {border-top:2px solid black; border-right:2px solid black; border-left:1px solid black; border-bottom:1px solid black; height:13px;width:13px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
		</style>
		<div style="width:170mm">
		<img src="{$resourceRoot}/images/imagesMZB/logoForDoc.gif" width="100%" border="0" />
		
		<p class="textBoldItalic" align="right">
		<br/>Начальнику Отдела оформления
		<br/>операций по банковским картам
		<br/>Искаковой Ж.М.</p>

		<p align="center" class="textTitle">Заявление на постановку в СТОП-ЛИСТ</p>
		<br/>

		<table width="100%" class="padmar">
		<tr>
			<td width="180px" class="textBoldItalic padmar">Я, (Ф.И.О. полностью)</td>
			<td class="docTdBorderBottom textPlain">&nbsp;&nbsp;&nbsp;<xsl:value-of select="fullName"/></td>
		</tr>
		</table>
		
		<br/>
		
		<table width="100%" class="padmar">
		<tr>
			<td width="180px" class="textBoldItalic padmar">Контактный телефон</td>
			<td class="docTdBorderBottom textPlain">&nbsp;&nbsp;&nbsp;<xsl:value-of select="phone"/></td>
		</tr>
		</table>
		
		<br/>

		<table cellspacing="0">
		<tr>
			<td class="textBoldItalic padmar">&nbsp;Прошу Вас:&nbsp;</td>
		</tr>
		<tr>
		<td>
			<script type="text/javascript">
				<![CDATA[
				for( var i = 0; i < actions.length; ++i )
				{
					if (actions[i].code == actionCode)
						document.write("<input class='allInput' readonly='true' style='margin-left:40px;' type='text' value='V'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					else
						document.write("<input class='allInput' readonly='true' style='margin-left:40px;' type='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					document.write(actions[i].type+"<br/>");
				}
				document.write("<input class='allInput' readonly='true' style='margin-left:40px;' type='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				document.write("Запросить историю включения карты в Stoplist");
				]]>
			</script>
		</td>
		</tr>
		</table>
			
		<br/>

		<table style="border-top: 1px solid black; border-left: 1px solid black;" cellspacing="0">
		<script type="text/javascript">
			<![CDATA[
			var types=['VISA Gold','MasterCard Gold','VISA Classic','MasterCard Standard','VISA Electron','Maestro'];
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

		<br/>

		<table cellspacing="0"><tr>
		<td class="textPlain padmar">&nbsp;Карта&nbsp;</td>
		<td>
		<script type="text/javascript">
		<![CDATA[
		for ( var i = 0; i < card.length; i++ )
		{
			if( (i==0) || (card.length - i < 5))
				document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>"+card.charAt(i)+"</span>");
			else document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>&nbsp;</span>"); 
		}
		]]>
		</script>
		</td>
		</tr></table>

		<br/>

		<table cellspacing="0"><tr>
			<td class="textPlain padmar">&nbsp;Срок действия&nbsp;</td>
			<td>
			<script type="text/javascript">
			<![CDATA[
			var expCD = expCardDate.split(".");
			if (expCD[1].length == 1) {
				expCD[1] = "0" + expCD[1];
			}
			document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>"+expCD[1].charAt(0)+"</span>");
			document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>"+expCD[1].charAt(1)+"</span>");
			for ( var i = 0; i < 4; i++ )
			{
				document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>"+expCD[2].charAt(i)+"</span>");
			}
			]]>
			</script>
			</td>
		</tr></table>

		<br/>
		
		<table cellspacing="0"><tr>
			<td class="textPlain padmar">&nbsp;Срок блокировки карты&nbsp;</td>
			<td>
			<script type="text/javascript">
			<![CDATA[
			var expBD = expBlockDate.split(".");
			if (expBD[0].length == 1) {
				expBD[0] = "0" + expBD[0];
			}
			document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>"+expBD[0].charAt(0)+"</span>");
			document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>"+expBD[0].charAt(1)+"</span>");
			for ( var i = 0; i < 4; i++ )
			{
				document.write("<span class='bdLast' style='margin:3px;padding:5px;height:5px;'>"+expBD[1].charAt(i)+"</span>");
			}
			]]>
			</script>
			</td>
		</tr></table>

		<br/>
		<span class="textBoldItalic padmar">&nbsp;Регионы блокировки карты:&nbsp;</span>

		<table>
			<tr>

		<script type="text/javascript">
			<![CDATA[
		if (reg1 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
		document.write("<td>&nbsp;РФ&nbsp;</td></tr><tr>");
		if (reg2 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
		document.write("<td>&nbsp;США&nbsp;</td></tr><tr>");
		if (reg3 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
		document.write("<td>&nbsp;Канада&nbsp;</td></tr><tr>");
		if (reg4 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
		document.write("<td>&nbsp;Латинская и Центральная Америка&nbsp;</td></tr><tr>");
		if (reg5 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
		document.write("<td>&nbsp;Азия и Тихоокеанский регион&nbsp;</td></tr><tr>");
		if (reg6 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
		document.write("<td>&nbsp;Европа&nbsp;</td></tr><tr>");
		if (reg7 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
		document.write("<td>&nbsp;Восточная Европа, Ближний Восток и Африка&nbsp;</td></tr><tr>");
		if (reg8 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
			document.write("<td>&nbsp;Другое&nbsp;<span class='docTdBorderBottom textPlain'>"+other+"</span></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
			document.write("<td>&nbsp;Другое&nbsp;</td>");
		}
			]]>
		</script>
			</tr>
		</table>

		<br/>
		<span class="textBoldItalic padmar">&nbsp;Индикатор причины постановки карты в Стоп-лист:&nbsp;</span>
		<table style="padding-left:50px">
			<tr>
		<script type="text/javascript">
			<![CDATA[
		if (rea1 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td>&nbsp;мошенничество&nbsp;</td></tr><tr>");
        if (rea2 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td>&nbsp;подделанная карта&nbsp;</td></tr><tr>");
        if (rea3 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td>&nbsp;потерянная карта&nbsp;</td></tr><tr>");
        if (rea4 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td>&nbsp;украденная карта&nbsp;</td></tr><tr>");
        if (rea5 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td>&nbsp;захватить карту&nbsp;</td>");
			]]>
		</script>
			</tr>
		</table>

		<br/>

		<table width="100%">
		<tr style="font-style:italic;">
		    <td>
			&nbsp;&nbsp;&quot;___&quot;&nbsp;&nbsp;_____________________&nbsp;200_г.
			</td>

			<td align="right" class="textPlain padmar">__________________________(Подпись Клиента)</td>
		</tr>
		</table>
		<br/>
		<table width="100%" class="padmar">
			<tr>
				<td colspan="2" class="text" style="font-size:8pt; font-style:italic; font-wieght:bold; padding-left:35mm; border:1px solid black; background-color:rgb(220,220,220)" nowrap="true">Отметки Банка</td>
			</tr>
		</table>
		<table width="100%" class="padmar" style="font-style:italic;">
		<tr>
		<td class="textBold10 padmar" width="200px" align="right" style="padding-right: 40px">Заявление принял </td>
		<td>
			<script type="text/javascript">
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
			document.write("&nbsp;&quot;"+dateArray[0]+"&quot;&nbsp;");
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
		<td class="docTdBorderBottom" width="320px">&nbsp;

		</td>
		</tr>
		</table>
		<p class="textItalicSmall padmar" align="right" style="padding-right:30px;">(Ф.И.О и подпись сотрудника Банка)</p>
		&nbsp;
		&nbsp;
		</div>
	</xsl:template>

</xsl:stylesheet>