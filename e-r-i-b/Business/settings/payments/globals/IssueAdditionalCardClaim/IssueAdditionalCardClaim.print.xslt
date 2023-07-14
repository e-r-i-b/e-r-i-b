<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:variable name="pictureUrl" select="resourceRoot"/>
	<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>

	<xsl:template match="/form-data">
		<script type="text/javascript">
			var card = '<xsl:value-of select="cardNumber"/>';
			var reason = '<xsl:value-of select="reason"/>';
			var anotherReason = '<xsl:value-of select="conditionsOfLost"/>';
			var cardType = '<xsl:value-of select="cardType"/>';
			var urgency = '<xsl:value-of select="urgency"/>';
			var date = '<xsl:value-of select="dateCreated"/>';
			var account = '<xsl:value-of select="account"/>';
			var status='<xsl:value-of select="$currentPerson/entity/field[@name='status']"/>';
			var isResident = '<xsl:value-of select="$currentPerson/entity/field[@name='isResident']"/>';
		</script>
		<style>
			.text{font-family:Times New Roman;font-size:10pt;font-weight:bold}
			.textNotBold{font-family:Times New Roman;font-size:10pt}
			.docTdBorderBottom{border-bottom:1px solid #000000;}
			.bd {border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:6mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.bdLast {border:1px solid black; width:6mm; font-family:Times New Roman;font-size:10pt; text-align:center}
			.justBorder {border-bottom:1px solid black; border-right:1px solid black;}
			.allInput {border-top:2px solid black; border-right:2px solid black; border-left:1px solid black; border-bottom:1px solid black; height:9px;width:9px; text-align:left; font-size:3pt; font-weight:900; vertical-align:top}
			.justPadding {padding-top:2mm; padding-bottom:2mm}
			.justPaddingTop {padding-top:1mm;}
			.justBorder {border-bottom:1px solid black; border-right:1px solid black; padding-left:5mm}
			.justBorderBottom {border-bottom:1px solid black; vertical-align:top}

		</style>
		<table cellpadding="0" cellspacing="0" width="220mm">
			<col/>
			<tr>
				<td colspan="2">
					<img src="{$resourceRoot}/images/imagesMZB/logoForDoc.gif" border="0"/>
				</td>
			</tr>
			<tr>
				<td class="text" colspan="2" style="text-align:center; padding-top:10">
					<span style="font-size:16pt;">���������</span><br/><span style="font-size:12pt;">�� �������������� �������������� ���������� �����<br/>��� &quot;���������� ��������� ����&quot; (���)</span></td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold">����� ��������� � ����� �����:</td>
			</tr>
			<tr>
				<td class="textNotBold" colspan="2">
				<table cellspacing="0">
				<tr>
					<td class="textNotBold" style="border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:50mm" nowrap="true">&nbsp;&nbsp;&nbsp;����� � </td>
					<script type="text/javascript">
					<![CDATA[
					var cardArray = card.split("[");
					for ( var i = 0; i < cardArray[0].length-2; i++ )
					{
						if( (i == 0) || (cardArray[0].length-2 - i <  4) )
							document.write("<td class='bd textNotBold'>"+cardArray[0].charAt(i)+"</td>");
						else document.write("<td class='bd textNotBold'>&nbsp;</td>"); 
					}
					document.write("<td class='bdLast textNotBold'>"+cardArray[0].charAt(cardArray[0].length-2)+"</td>");
					]]>
					</script>
				</tr>
				</table><br/>
				</td>
			</tr>
			<tr>
				<td class="textNotBold" colspan="2">
				<table cellspacing="0">
				<tr>
					<td class="textNotBold" style="border-top:1px solid black; border-left:1px solid black; border-bottom:1px solid black; width:40mm" nowrap="true">&nbsp;&nbsp;&nbsp;� �����</td>
					<script type="text/javascript">
					<![CDATA[
					for ( var i = 0; i < account.length-1; i++ )
					{ document.write("<td class='bd'>"+account.charAt(i)+"</td>");}
					document.write("<td class='bdLast'>"+account.charAt(account.length-1)+"</td>");
					]]>
					</script>
				</tr>
				</table><br/>
				</td>
			</tr>
			<tr><td colspan="2" class="textNotBold">�����</td></tr>
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
			<tr class="justPadding">
				<td colspan="2">
					<table>
						<tr class="textNotBold">
							<td>������� ������:</td>
							<td width='55mm' align="center">
								<input id='c0' class='allInput' readonly='true' type='text'/>
							</td>
							<td>��</td>
							<td width='55mm' align="center">
								<input id='c1' class='allInput' readonly='true' type='text'/>
							</td>
							<td>���</td>
						</tr>
					</table>
				</td>
			</tr>
			<script type="text/javascript">
						<![CDATA[
						var urgencies=['������� ������','������� ������'];
						for ( var i = 0; i < 2; i++ )
							{
							var check = document.getElementById('c'+i);
							if (urgencies[i] == urgency)
							    check.value='X';
							}
						]]>
			</script>
			<tr>
				<td colspan="2" class="text">&nbsp;&nbsp;&nbsp;������ ������ �������:</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr class="textNotBold justPaddingTop">
							<td nowrap="true">&nbsp;1. ������� ��� ��������:</td>
							<td class="docTdBorderBottom" width="76%">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%">
						<tr  class="textNotBold justPaddingTop">
							<td nowrap="true">2. �������, ��� ��� � ��������������:</td>
							<td class="docTdBorderBottom" width="70%">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="surnameInLatin"/>&nbsp;<xsl:value-of select="nameInLatin"/>
								<br/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr class="textNotBold justPaddingTop">
				<td colspan="2">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td nowrap="true" class="textNotBold">&nbsp;3. ���:</td>
							<td class="docTdBorderBottom textNotBold" width="37%">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="gender"/><br/>
							</td>
							<td nowrap="true" class="textNotBold">&nbsp;&nbsp;&nbsp;4. ���� ��������:</td>
							<td class="docTdBorderBottom textNotBold" width="37%">&nbsp;&nbsp;&nbsp;
								<script type="text/javascript">
										var dateValue = '<xsl:value-of select="$currentPerson/entity/field[@name='birthDay']"/>';
										tempDate = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
										document.write(tempDate);
									</script>
								<br/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%">
						<tr class="textNotBold justPaddingTop">
							<td nowrap="true">5. �����������:</td>
							<td class="docTdBorderBottom" width="85%">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="citizen"/>
								<br/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr class="justPaddingTop">
				<td colspan="2">
					<table width="100%">
						<tr  class="textNotBold">
							<td nowrap="true" width="60mm">6. ������:</td>
							<td width='10mm' align="center">
								<input id='s0' class='allInput' readonly='true' type='text'/>
							</td>
							<td class="textNotBold" width="85mm">��������</td>
							<td width='10mm' align="center">
								<input id='s1' class='allInput' readonly='true' type='text'/>
							</td>
							<td class="textNotBold" width="100mm">����������</td>
							<td nowrap="true" class="textNotBold" width="50mm">7. ���:</td>
							<td class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="inn"/>
								<br/>
							</td>
						</tr>
					</table>
			</td>
			</tr>
			<script type="text/javascript">
						<![CDATA[
						if (isResident == 'true')
						  var check = document.getElementById('s0');
						else
						  var check = document.getElementById('s1');
						 check.value='X';
						]]>
			</script>
			<tr class="textNotBold justPaddingTop">
				<td colspan="2">
					<table width="100%">
						<tr  class="textNotBold">
							<td nowrap="true">8. �������: �����: </td>
							<td width='40%' class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="$currentPerson/entity/field[@name='passportSeries']"/>
							</td>
							<td nowrap="true" class="textNotBold">&nbsp;&nbsp;&nbsp;�����:</td>
							<td width='40%' class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="$currentPerson/entity/field[@name='passportNumber']"/>
								<br/>
							</td>
						</tr>
						<tr  class="textNotBold">
							<td nowrap="true" align="right">�����: </td>
							<td class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<script type="text/javascript">
										var dateValue = '<xsl:value-of select="$currentPerson/entity/field[@name='passportIssueDate']"/>';
										tempDate = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
										document.write(tempDate);
									</script>
							</td>
							<td nowrap="true" class="textNotBold">�.,&nbsp;&nbsp;&nbsp;���:</td>
							<td class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="$currentPerson/entity/field[@name='passportIssueBy']"/>
								<br/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr  class="justPaddingTop">
				<td colspan="2">
					<table width="100%">
						<tr class="textNotBold">
							<td nowrap="true">9. ����� ��������: </td>
							<td width='90%' class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="registrationAddress"/>
							</td>
						</tr>
						<tr  class="textNotBold">
							<td nowrap="true" align="right">�������: </td>
							<td class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="registrationPhone"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr  class="justPaddingTop">
				<td colspan="2">
					<table width="100%">
						<tr class="textNotBold">
							<td nowrap="true">10. ����� ����������: </td>
							<td width='90%' class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="residenceAddress"/>
							</td>
						</tr>
						<tr  class="textNotBold">
							<td nowrap="true" align="right">�������: </td>
							<td class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="residencePhone"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr class="justPaddingTop">
				<td colspan="2">
					<table width="100%">
						<tr  class="textNotBold">
							<td nowrap="true">11. ������� �����:</td>
							<td width="40%" class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="codeWord"/>
							</td>
							<td nowrap="true" class="textNotBold">12. E-mail:</td>
							<td width="35%" class="docTdBorderBottom">&nbsp;&nbsp;&nbsp;
								<xsl:value-of select="eMail"/>
								<br/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr class="justPaddingTop">
				<td class="text" colspan="2" style="padding-right:20mm; text-align:justify">
					� �������� � ��������� �� ������������ ���������� ��� � �������������� ����������
					����� ��� &quot;���������� ��������� ����&quot; (���) ���������� � �������� �� ���������. �
					����������� ������������� ���� ������������� ������ � �������� � �� ���������
					���������. ������� �� ����� ����� �������� ��� ������ ��������� � ����.
				</td>
			</tr>
			<tr class="justPaddingTop">
				<td colspan="2" style="padding-bottom: 2mm;  padding-top:4mm">
					<table width="100%">
						<tr class="textNotBold">
							<td style="width:15mm">&nbsp;</td>
							<td style="width:30mm;">����</td>
							<td style="width:45mm; font-size:10pt" class="bdLast">
							  <script type="text/javascript">
											<![CDATA[
											var dateArray = date.split(" ");
											var months= new Array(
												new Array ('Jan','������'),
												new Array ('Feb','�������'),
												new Array ('Mar','�����'),
												new Array ('Apr','������'),
												new Array ('May','���'),
												new Array ('Jun','����'),
												new Array ('Jul','����'),
												new Array ('Aug','�������'),
												new Array ('Sep','��������'),
												new Array ('Oct','�������'),
												new Array ('Nov','������'),
												new Array ('Dec','�������')
												);
											dateCreate = new Date;
											var yearString = dateCreate.getFullYear().toString();
											 document.write("&nbsp;&nbsp;"+dateArray[2]+"&nbsp;");
											 for(var i=0;i<12;i++){
											 if (months[i][0]==dateArray[1]) document.write("&nbsp;"+months[i][1]+"&nbsp;");
											 }
											 document.write("&nbsp;"+dateArray[5].charAt(0)+dateArray[5].charAt(1)+dateArray[5].charAt(2)+dateArray[5].charAt(3)+"&nbsp;�.");
											]]>
											</script>
							</td>
							<td style="width:45mm; padding-left:10mm">&nbsp;������� �������</td>
							<td style="width:45mm" class="bdLast">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="textNotBold" style="padding-top:3mm">
					<table width="100%">
						<tr>
							<td style="padding-right:10mm; padding-left:10mm" nowrap="true">��������� ������ � ��������</td>
							<td class="bdLast" style="width:100%">&nbsp;</td>
						</tr>
						<tr>
							<td colspan='2' style="padding-right:20mm; FONT-SIZE: 8pt; text-align: right">������� �.� � ������� ���������� �����</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding-top:3mm">
					<table>
						<td class="textNotBold" style="padding-left:10mm; padding-right:8mm; padding-top:2mm; font-size:11pt" nowrap="true">����� �����:</td>
						<td>
							<table  cellspacing="0">
								<tr style="height:10mm">
								<script type="text/javascript">
								<![CDATA[
								for(i=0;i<15;i++)
								{
									document.write("<td class='bd' style='width:10mm'>&nbsp</td>");
								}
								document.write("<td class='bdLast' style='width:10mm'>&nbsp</td>");
								]]>
								</script>
							</tr></table>
						</td>
						<td class="textNotBold" nowrap="true" style="text-align:center; font-size:11pt">� ��� �������<br/>�������</td>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding-top:3mm" class="textNotBold">&quot;_______&quot;_____________________200____ �.    ______________________________</td>
			</tr>
			<tr>
				<td colspan="2" style="PADDING-RIGHT: 30mm; text-align: right; font-size:8pt">(������� �������)</td>
			</tr>
		</table>
		&nbsp;
		&nbsp;
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="document.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->