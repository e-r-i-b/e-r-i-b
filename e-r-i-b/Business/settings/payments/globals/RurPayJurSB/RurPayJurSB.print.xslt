<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:template match="/form-data">
		<html>
			<head>
				<style>
					.smallText{font-family:Times New Roman;font-size:10pt;}
					.text td{font-family:Times New Roman;font-size:12pt;}
					.smallPadding{padding-top:2.5mm; padding-bottom:2.5mm}
					.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
					.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
				</style>
				<script type="text/javascript" language="JavaScript">
					var status = '<xsl:value-of select="state"/>';					
					var amount = '<xsl:value-of select="amount"/>';
					<![CDATA[
					function writeCurrentDate()
					{
						temp_date = new Date();
						day = temp_date.getDate();
						month = temp_date.getMonth() + 1;
						year = temp_date.getFullYear();
						if (day < 10) {
						day = "0" + day;
						}
						if (month < 10) {
						month = "0" + month;
						}
					  document.write(day+"."+month+"."+year);

					}

					function writeAmount()
					{
						var amountArray = amount.split(".");
						var amountRub = amountArray[0];
						var amountCop = amountArray[1];
						document.write(amountRub+"-"+amountCop);
					}

					function checkStatus()
					{
					//<!--�.�. ���� ������ ��������������, �� � ��� ��� ������ � ������ ������� � ���� ���������� � �������� ����������-->
					   if(status == 'W')
					   {
					      alert("��� ������ �������������� ������");
					   }
					}
					]]>
				</script>
			</head>
			<body onload="checkStatus()">			
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
		<table class="text" cellpadding="0" cellspacing="0" style="width:170mm">
			<tr>
				<td align="center">
					<img style="width:14.44mm; height:12mm" src="{$resourceRoot}/images/imagesSbrf/miniLogoSbrf.jpg"/>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="center" style="font-weight:bold;">
					<nobr class="smallText">��������&nbsp;������&nbsp;���</nobr>
				</td>
				<td align="right" style="width:90%" class="smallText">
					<nobr class="smallText">�. �395</nobr>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding-top:2mm">
					<xsl:value-of select="$currentPerson/entity/field[@name='office-name']"/>,
					�<xsl:value-of select="$currentPerson/entity/field[@name='office-codeBranch']"/> ��������� ������ ���/�<xsl:value-of select="$currentPerson/entity/field[@name='office-codeOffice']"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="font-weight:bold; padding-top:10mm; padding-bottom:8mm">
					������������� � ������������� ��������
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					����������: <xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					������������� ���������� �� ������ � <xsl:value-of select="operationDate"/> �� <xsl:value-of select="operationDate"/>.					
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					������������ �������: ������ ������� � �����
					<xsl:variable name="description" select="receiverDescription"/>
					<!--�������� �������� �������-->
					<xsl:if test="receiverDescription != ''">
						: <xsl:value-of select="receiverDescription"/>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					������������ �����������-���������� �������: <xsl:value-of select="receiverName"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					������������� �����������: <xsl:value-of select="payerAccountSelect"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					���������� ��������� �����������-���������� �������: <xsl:value-of  select="receiverBankName"/>,&nbsp;
					<xsl:value-of select="receiverCorAccount"/>,&nbsp;<xsl:value-of select="receiverBIC"/>,&nbsp;
					<xsl:value-of select="receiverAccount"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					<table class="docTableBorder" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td style="width:35mm" align="center" class="docTdBorder">���� �������</td>
							<td style="width:67mm" align="center" class="docTdBorder">������ ������</td>
							<td style="width:55mm" align="center" class="docTdBorder">����� �������</td>
						</tr>
						<tr>
							<td class="docTdBorder">&nbsp;<xsl:value-of select="chargeOffDate"/></td>
							<td class="docTdBorder">
								<!-- ������ ������ �������� ����� ���� ��� ����� ������ � �������� �������� � ����������� ��������� ������� ��� -->
								&nbsp;
							</td>
							<td class="docTdBorder">&nbsp;
								<script>
								   writeAmount();
								</script>
								&nbsp;RUB
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding" style="padding-top:8mm">
					���� �����������:
					<script type="text/javascript" language="JavaScript">
						writeCurrentDate();
					</script>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					����� �������: <xsl:value-of select="register-number"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					������ �������: <xsl:value-of select="register-string"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallPadding">
					<!--��. �������.-->
					�����������:
				</td>
			</tr>
		</table>
		</body>
	</html>
	</xsl:template>

</xsl:stylesheet>