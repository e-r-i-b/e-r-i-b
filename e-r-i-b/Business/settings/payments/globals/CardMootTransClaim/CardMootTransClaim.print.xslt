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

			var item1 = <xsl:value-of select="item1"/>;
			var item2 = <xsl:value-of select="item2"/>;
			var item3 = <xsl:value-of select="item3"/>;
			var item4 = <xsl:value-of select="item4"/>;
			var item5 = <xsl:value-of select="item5"/>;
			var item6 = <xsl:value-of select="item6"/>;
			var item7 = <xsl:value-of select="item7"/>;
			var other = "";
			var returnDate = "_________";
			var startValue = "_______";
			var endValue = "_______";
			<xsl:if test="item7 = 'true'">
			other = '<xsl:value-of select="other"/>';
			</xsl:if>
			<xsl:if test="item4 = 'true'">
			returnDate = '<xsl:value-of select="returnDate"/>';
			</xsl:if>
			<xsl:if test="item3 = 'true'">
			startValue = '<xsl:value-of select="amountPayment"/>';
			endValue = '<xsl:value-of select="endValue"/>';
			</xsl:if>
		</script>
		<style>
			.textBoldItalic{font-family:Times New Roman;font-size:12pt;font-style:italic;font-weight:bold}
			.textBold12{font-family:Times New Roman;font-size:12pt;font-weight:bold;margin:0px;}
			.textBold10{font-family:Times New Roman;font-size:10pt;font-weight:bold}
			.textPlain{font-family:Times New Roman;font-size:12pt;}
			.textPlain10{font-family:Times New Roman;font-size:10pt;}
			.textSmall{font-family:Times New Roman;font-size:9pt}
			.padmar{padding:0; margin:0}
			.docTdBorderBottom{border-bottom:1px solid #000000;}
			.bd {border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:6mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdLast {border:1px solid black; width:6mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.justBorder {border-bottom:1px solid black; border-right:1px solid black;}
			.allInput {border-top:2px solid black; border-right:2px solid black; border-left:1px solid black; border-bottom:1px solid black; height:13px;width:13px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
		</style>
		<div style="width:170mm">
		<img src="{$resourceRoot}/images/imagesMZB/logoForDoc.gif" width="100%" border="0" />
		
		<p class="textBoldItalic" align="right">
		<br/>���������� ������ ����������
		<br/>�������� �� ���������� ������
		<br/>��������� �.�.</p>

		<p align="center" class="textBold12">��������� � ������� ��������</p>
		<br/>
		<table width="100%" class="padmar">
		<tr>
			<td width="230px" class="textPlain padmar">��� � ������� ��������� �����</td>
			<td class="docTdBorderBottom textPlain">&nbsp;&nbsp;&nbsp;<xsl:value-of select="fullName"/></td>
		</tr>
		<tr class='textSmall padmar'>
			<td width="130px">&nbsp;&nbsp;&nbsp;Cardholder�s name</td>
			<td>&nbsp;</td>
		</tr>
		</table>

		<table width="100%" class="padmar">
		<tr>
			<td width="300px" class="textPlain padmar">���������� ������� (����������� �����):</td>
			<td class="docTdBorderBottom textPlain">&nbsp;&nbsp;&nbsp;<xsl:value-of select="phone"/></td>
		</tr>
		<tr class='textSmall padmar'>
			<td width="300px">&nbsp;&nbsp;&nbsp;Cardholder�s phone number (e-mail) </td>
			<td>&nbsp;</td>
		</tr>
		</table>
		 <script type="text/javascript">
			 var card = '<xsl:value-of select="card"/>';
		 </script>
		<table width="100%" class="padmar">
		<tr>
			<td width="80px" class="textPlain padmar">&nbsp;� �����&nbsp;</td>
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
		<tr class="textSmall padmar">
			<td width="130px">&nbsp;&nbsp;&nbsp;Card account number �</td>
			<td>&nbsp;</td>
		</tr>
		</table>

		<table width="100%" class="padmar">
		<tr>
			<td width="310px" class="textPlain padmar">&nbsp;���� ���������� �������� �� �������&nbsp;</td>
			<td class="docTdBorderBottom">&nbsp;<xsl:value-of select="payDate"/></td>
		</tr>
		<tr class="textSmall padmar">
			<td width="130px">&nbsp;&nbsp;&nbsp;Transaction Date</td>
			<td>&nbsp;</td>
		</tr>
		</table>

		<table width="100%" class="padmar">
		<tr>
			<td width="310px" class="textPlain padmar">&nbsp;����� ���������� �������� �� �������&nbsp;</td>
			<td class="docTdBorderBottom">&nbsp;<xsl:value-of select="payPlace"/></td>
		</tr>
		<tr class="textSmall padmar">
			<td width="130px">&nbsp;&nbsp;&nbsp;Merchant Name &amp; Location</td>
			<td>&nbsp;</td>
		</tr>
		</table>
		
		<table width="100%" class="padmar">
		<tr>
			<td class="textPlain padmar" nowrap="true" width="220px">&nbsp;����� � ������ �������:&nbsp;</td>
			<td class="docTdBorderBottom" align="center">&nbsp;&nbsp;&nbsp;
				<xsl:value-of select="amountPayment"/>&nbsp;<xsl:value-of select="paymentCurrency"/>
			</td>
		</tr>
		<tr class="textSmall padmar">
			<td width="130px">&nbsp;&nbsp;&nbsp;Transaction Amount</td>
			<td>&nbsp;</td>
		</tr>
		</table>

		<table width="100%" class="padmar">
		<tr>
			<td class="textPlain padmar" nowrap="true" width="200px">&nbsp;����� � ������ �����:&nbsp;</td>
			<td class="docTdBorderBottom" align="center">&nbsp;&nbsp;&nbsp;
				<xsl:value-of select="amountCard"/>&nbsp;<xsl:value-of select="cardCurrency"/>
			</td>
		</tr>
		<tr class="textSmall padmar">
			<td width="130px">&nbsp;&nbsp;&nbsp;Billing Amount</td>
			<td>&nbsp;</td>
		</tr>
		</table>

		<p class="textBold12">����������, �������� ���������������(���) ����� �������� �����(�):</p>
		<br />
		<table style="padding-left:50px">
			<tr>
		<script type="text/javascript">
			<![CDATA[
		if (item1 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td> � ������� �� ������� ������ ��� ����� �� ������������� ��������&nbsp;</td></tr><tr class='textSmall padmar'>");
        document.write("<td>&nbsp;</td>");
        document.write("<td>(I neither received goods nor services by the above transaction)</td></tr><tr>");

		if (item2 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td> ������������� �������� ����������� ���� ���&nbsp;</td></tr><tr class='textSmall padmar'>");
        document.write("<td>&nbsp;</td>");
        document.write("<td>(I�ve been charged twice for the same transaction)</td></tr><tr>");

        if (item3 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td> ����� �� ������������� �������� ��������� � "+startValue+" �� "+endValue+"&nbsp;</td></tr><tr>");
        document.write("<td>&nbsp;</td>");
		document.write("<td> (�������� ����� ����, ���������� ���������� �����)</td></tr><tr class='textSmall padmar'>");
		document.write("<td>&nbsp;</td>");
        document.write("<td>(The transaction amount has been altered from "+startValue+" to "+endValue+". I enclosed copy of my receipt as a proof.)</td></tr><tr>");

        if (item4 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
			document.write("<td> � ������ ����� �� ������������� �������� "+returnDate+"</td></tr><tr class='textSmall padmar'>");
			document.write("<td>&nbsp;</td>");
	        document.write("<td>(I returned the goods on "+returnDate+". But the enclosed credit voucher has not been credited to my account)</td></tr><tr>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
			document.write("<td> � ������ ����� �� ������������� �������� &quot;__&quot; ________ 200_ �.</td></tr><tr class='textSmall padmar'>");
			document.write("<td>&nbsp;</td>");
            document.write("<td>(I returned the goods on �__� _____ 200_. But the enclosed credit voucher has not been credited to my account)</td></tr><tr>");
		}

        if (item5 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td> ������������� �������� ���� ���������� </td></tr><tr>");
        document.write("<td>&nbsp;</td>");
		document.write("<td> �������� ����� ���������� �����, ���������� �����, ���������� � ����� �����.</td></tr><tr class='textSmall padmar'>");
		document.write("<td>&nbsp;</td>");
        document.write("<td>(The above transaction was credit operation. I enclosed copy of my credit voucher)</td></tr><tr>");

        if (item6 == true) {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
		} else {
			document.write("<td>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
		}
        document.write("<td> ������ �� ������� </td></tr><tr class='textSmall padmar'>");
        document.write("<td>&nbsp;</td>");
        document.write("<td>(I have not received purchased goods/services)</td></tr><tr>");

		if (item7 == true) {
			document.write("<td valign='top'>&nbsp;<input class='allInput' readonly='true' type='text' value='V'></td>");
			document.write("<td> ������ (��������� ���������� ��������� � ���������� ������� ���������� ��������): "+other+"</td></tr><tr class='textSmall padmar'>");
		} else {
			document.write("<td valign='top'>&nbsp;<input class='allInput' readonly='true' type='text'></td>");
			document.write("<td> ������ (��������� ���������� ��������� � ���������� ������� ���������� ��������)</td></tr><tr class='textSmall padmar'>");
		}
        document.write("<td>&nbsp;</td>");
        document.write("<td>Other (Please enclosed your explanation letter)</td></tr><tr>");
			]]>
		</script>
			</tr>
		</table>


		<br />
		<p class="textBold12">����� ��� ������������ �������� (����� �����, ������������ ����), �� ��������� �������� ������������� ����� ���� ������� � ����� �����, ���� ������������ �������� �� �����.</p>
		<p class="textSmall padmar">I authorize the bank to charge the above mentioned transactions back.</p>
		<p class="textSmall padmar">� ������������ � ���, ��� ���� ����� �������� ��������� ��������� � ������� ���������� � ������������� ��������� ������� Visa Intemational/ MasterCard Intemational, ���������� ������������� ��������� �������, � ����� � ������������������ ������, ���� ��� ����� ������ � ������������� ������� ����������.</p>
		<p class="textSmall padmar">� ������������ � ���, ��� �������������� ���� ������������� ���������� �� ��������� � ������� ���������� ����� ������� ������������� �� ������.</p>
		<p class="textSmall padmar">� ������������ � ���, ��� ���� ����� �������� � ������������ ��������� � ������ ���� ��� ������ � ���������� ������, ����������� ��� �� ��������� ��������� (� ��� ����� ����������� ����������� ��������� �� ��������).</p>
		<p class="textSmall padmar">� ������������ � ���, ��� � ���� ����� ���� �������� �����, � ������������ � �������� ����� � ������ ���������������� ����� ��������������.</p>


		<table width="100%">
		<tr>
		<td width="50px" class="textPlain padmar">�������</td>
		<td class="docTdBorderBottom textPlain">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td class="textPlain padmar" align="right">
			����
		</td>
		<td class="docTdBorderBottom">
			<script type="text/javascript">
			<![CDATA[
			var dateArray = date.split(".");
			var months= new Array(
				new Array ('01','������'),
				new Array ('02','�������'),
				new Array ('03','�����'),
				new Array ('04','������'),
				new Array ('05','���'),
				new Array ('06','����'),
				new Array ('07','����'),
				new Array ('08','�������'),
				new Array ('09','��������'),
				new Array ('10','�������'),
				new Array ('11','������'),
				new Array ('12','�������')
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
			document.write("&nbsp;"+dateArray[2]+"&nbsp;�.");
			]]>
		</script>
		</td>
		</tr>
		<tr class="textSmall padmar">
		<td width="50px">Signature</td>
		<td>&nbsp;</td>
		<td align="right">
			Date
		</td>
		<td>&nbsp;</td>
		</tr>
		</table>
		<br/>
		<table width="100%" class="padmar">
			<tr>
				<td colspan="2" class="text" style="font-size:8pt; font-style:italic; padding-left:35mm; border:1px solid black; background-color:rgb(220,220,220)" nowrap="true">������� �����</td>
			</tr>
		</table>
		<table width="100%" class="padmar" style="font-style:italic;">
		<tr>
		<td class="textBold10 padmar" width="250px" align="right">��������� ������</td>
		<td>
			&nbsp;&quot;___&quot;&nbsp;___________________&nbsp;200__�.
		</td>
		<td class="docTdBorderBottom" width="320px">&nbsp;

		</td>
		</tr>
		</table>
		<p class="textSmall padmar" align="right" style="padding-right:50px; font-style:italic;">(�.�.� � ������� ���������� �����)</p>
		&nbsp;
		&nbsp;
		</div>
	</xsl:template>

</xsl:stylesheet>