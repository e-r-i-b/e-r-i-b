<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:template match="/form-data">
		<script type="text/javascript">;
		</script>
		<style>
			.bd{border:1px solid #000000;}
			.bdBottom{{border-bottom:1px solid #000000;}
			.bdBottomRight{border-bottom:1px solid #000000;border-right:1px solid #000000;}
			.bdRight{border-right:1px solid #000000;}
			.bdTop{border-top:1px solid #000000;}
			.bdBottomLeftRight{border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
			.bdLeftRight{border-right:1px solid #000000;border-left:1px solid #000000;}
			.allInput {border:1px solid black; height:13px;width:13px; text-align:center; font-size:6pt; font-weight:900; vertical-align:middle}
		</style>
		<xsl:variable name="currentBankConfig" select="document('currenntBankConfig.xml')"/>
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
		<xsl:variable name="currentPersonInfo" select="document('currentPersonInfo.xml')/entity-list"/>

		<table cellpadding="0" cellspacing="0" width="180mm" class="font11" style="font-family:Times New Roman">
			<tr>
				<td style="padding-left:2mm">
					<table class="font11" cellpadding="0" cellspacing="0" style="width:180mm; font-family:Times New Roman">
						<tr>
							<td rowspan="3" valign="top" style="width:12mm"><img src="{$resourceRoot}/images/imagesSevB/miniLogoSbrf.jpg"/></td>
							<td colspan="2">Сбербанк России</td>
						</tr>
						<tr>
							<td class="font9" style="text-align:center; padding-top:2mm">
								 <xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='name']"/>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td class="bdTop font9" style="width:40mm; text-align:center;">Наименование филиала<br/>Сбербанка России</td>
							<td style="text-align:right; padding-right:5mm">Ф.№ 285</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="padding-left:80mm">
					<table class="font11" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman">
						<tr>
							<td style="width:90mm">&nbsp;</td>
							<td style="width:90mm">
								<table class="font11" cellpadding="0" cellspacing="0" style="width:90mm; font-family:Times New Roman">
									<tr>
										<td>В</td>
										<td class="bdBottom"><nobr>&nbsp;
											<script type="text/javascript">
												var str = '<xsl:value-of select="$currentPerson/entity/field[@name='office-name']"/>';												
												var officeName = breakString(str, 37);
												document.write(officeName);
												str = str.substring(officeName.length, str.length);
											</script></nobr>
										</td>
									</tr>
									<script type="text/javascript">
										<![CDATA[
										 while (str.length > 0)
										 {
											officeName = breakString(str, 42);
											document.write("<tr><td colspan='2' class='bdBottom'><nobr>&nbsp;"+officeName+"</nobr></td></tr>");
											str = str.substring(officeName.length, str.length);
										 }
										 ]]>
									</script>
									<tr>
										<td colspan="2" class="font8" style="padding-left:5mm"><nobr>(номер/наименование структурного подразделения Сбербанка России)</nobr></td>
									</tr>
									<tr>
										<td>от</td>
										<td class="bdBottom"><nobr>&nbsp;
											<script type="text/javascript">
												var str = '<xsl:value-of select="$currentPersonInfo/entity/field[@name='fullName']"/>';
												var fullName = breakString(str, 37);
												document.write(fullName);
												str = str.substring(fullName.length, str.length);
											</script></nobr>
										</td>
									</tr>
									<script type="text/javascript">
										<![CDATA[
										 while (str.length > 0)
										 {
											fullName = breakString(str, 42);
											document.write("<tr><td colspan='2' class='bdBottom'><nobr>&nbsp;"+fullName+"</nobr></td></tr>");
											str = str.substring(fullName.length, str.length);
										 }
										 ]]>
									</script>
									<tr>
										<td colspan="2" class="font8" style="text-align:center">(Фамилия, имя и отчество)</td>
									</tr>
									<tr>
										<td colspan="2">
											<table  class="font11" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman">
												<tr>
													<td style="width:45mm"><nobr>проживающего по адресу</nobr></td>
													<td class="bdBottom"><nobr>&nbsp;
														<script type="text/javascript">
															var str = '<xsl:value-of select="$currentPersonInfo/entity/field[@name='residenceAddress']"/>';															
															var residenceAddress = breakString(str, 20);
															document.write(residenceAddress);
															str = str.substring(residenceAddress.length, str.length);
														</script>
													</nobr>
													</td>
												</tr>
												<script type="text/javascript">
												<![CDATA[
												 while (str.length > 0)
												 {
													residenceAddress = breakString(str, 42);
													document.write("<tr><td colspan='2' class='bdBottom'><nobr>&nbsp;"+residenceAddress+"</nobr></td></tr>");
													str = str.substring(residenceAddress.length, str.length);
												 }
												 ]]>
											</script>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table  class="font11" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman">
												<tr>
													<td style="width:35mm"><nobr>паспортные данные</nobr></td>
													<td class="bdBottom"><nobr>&nbsp;
														<script type="text/javascript">
														var str = '<xsl:value-of select="$currentPersonInfo/entity/field[@name='REGULAR_PASSPORT_RF-number']"/>';
															str+='<xsl:value-of select="$currentPersonInfo/entity/field[@name='REGULAR_PASSPORT_RF-series']"/>';
															str+=', выдан: <xsl:value-of select="$currentPersonInfo/entity/field[@name='REGULAR_PASSPORT_RF-issue-by']"/>';
															<xsl:variable name="issue-date" select="$currentPersonInfo/entity/field[@name='REGULAR_PASSPORT_RF-issue-date']"/>
															str+=' <xsl:value-of select="substring($issue-date,9,2)"/>.<xsl:value-of select="substring($issue-date,6,2)"/>.<xsl:value-of select="substring($issue-date,1,4)"/>';

															var passport = breakString(str, 25);
															document.write(passport);
															str = str.substring(passport.length, str.length);
														</script>
													</nobr>
													</td>
												</tr>
												<script type="text/javascript">
												<![CDATA[
												 while (str.length > 0)
												 {
													passport = breakString(str, 42);
													document.write("<tr><td colspan='2' class='bdBottom'><nobr>&nbsp;"+passport+"</nobr></td></tr>");
													str = str.substring(passport.length, str.length);
												 }
												 ]]>
											</script>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="font11" style="padding-top:6mm; padding-bottom:6mm; text-align:center;">
					ЗАЯВЛЕНИЕ ОБ УТРАТЕ СБЕРЕГАТЕЛЬНОЙ КНИЖКИ
				</td>
			</tr>
			<tr>
				<td>
					<table class="font11" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman; margin-right:5mm">
						<tr>
							<td style="padding-left:13mm; width:95mm">В связи с утратой сберегательной книжки по вкладу №</td>
							<td class="bdBottom"><xsl:value-of select="accountSelect"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table class="font11" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman; padding:0mm 6mm 0mm 3mm">
						<tr>
							<td style="width:12mm"><nobr>на имя</nobr></td>
							<td class="bdBottom"><nobr>
								<xsl:value-of select="document( concat('accountInfo.xml?AccountNumber=',accountSelect) )/entity-list/entity/field[@name='fullname']"/>
								</nobr>
							</td>
							<td class="bdBottom" style="width:1mm; text-align:right">,</td>
							<td style="width:12mm"><nobr>прошу:</nobr></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="font9" style="text-align:center; padding-bottom:6mm">(указывается Ф.И.О. вкладчика)</td>
			</tr>
			<tr>
				<td style="padding-left:2mm">
					<table class="font11 bd" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman">
						<tr>
							<td class="bdRight" style="width:22mm; text-align:center;" valign="middle">
								<input class='allInput' readonly='true' type='text' id="reject"/>
							</td>
							<td style="padding:2mm; ">
								<table class="font11" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman">
									<tr>
										<td colspan="4">расторгнуть договор с закрытием счета по вкладу.</td>
									</tr>
									<tr>
										<td>Причитающуюся сумму вклада:</td>
										<td colspan="3">
											<input class='allInput' readonly='true' type='text' id="cash"/>&nbsp;
											выдать наличными деньгами;
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><input class='allInput' readonly='true' type='text' id="transfer"/>&nbsp;
											перечислить на счет №&nbsp;	</td>
										<td class="bdBottom" style="width:40mm">&nbsp;<xsl:value-of select="toAccountSelect"/></td>
										<td style="width:1mm">.</td>
									</tr>
									<tr>
										<td colspan="4">
											С размером платы, взимаемой за приостановление операций по счету при утрате<br/>
											сберегательной книжки (в случае, если она предусмотрена), согласен.
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="padding-left:2mm; padding-top:6mm">
					<table class="font11 bd" cellpadding="0" cellspacing="0" style="width:100%; font-family:Times New Roman">
						<tr>
							<td class="bdRight" style="width:22mm; text-align:center;" valign="middle"><input class='allInput' readonly='true' type='text' id="repeat"/></td>
							<td  style="padding-left:2mm">
								выдать ее дубликат.<br/>
								С размером платы, взимаемой за выдачу дубликата сберегательной книжки при<br/>
								ее утрате, согласен.<br/>
								Обязуюсь при обнаружении сберегательной книжки, ранее заявленной<br/>
								утраченной, вернуть ее в структурное подразделение Сбербанка России.
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="padding-top:15mm; text-align:right;">____________________________</td>
			</tr>
			<tr>
				<td class="font9" style="padding-right:25mm; text-align:right;">(Подпись)</td>
			</tr>
			<tr>
				<td style="text-align:right;">
					<table class="font11" cellpadding="0" cellspacing="0" style="width:60mm; font-family:Times New Roman">
						<tr>
							<td style="width:1mm">"</td>
							<td class="bdBottom" style="width:10mm; text-align:center">
								<script>
									newdate = new Date();
									document.write(newdate.getDate());
								</script>
							</td>
							<td style="width:1mm">"</td>
							<td class="bdBottom" style="width:15mm; text-align:center">
								<script>
									<!--newdate = new Date();-->
									document.write(monthToStringByNumber(newdate.getMonth()));
								</script>
							</td>
							<td class="bdBottom" style="width:15mm; text-align:right">
								<script>
									<!--newdate = new Date();-->
									document.write(newdate.getYear()+"&nbsp;г.");
								</script>
							</td>
							<td style="width:8mm">&nbsp;</td>
							<td class="bdBottom" style="width:10mm">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<script>
			<xsl:choose>
				<xsl:when test="closingAmountOrPassbookDuplicateRadio = 0">
					check = document.getElementById('reject');
					check.value='V';
					<xsl:choose>
						<xsl:when test="moneyOrTransferToAccountRadio = 0">
							check = document.getElementById('cash');
							check.value='V';
						</xsl:when>
						<xsl:when test="moneyOrTransferToAccountRadio = 1">
							check = document.getElementById('transfer');
							check.value='V';
						</xsl:when>
					</xsl:choose>

				</xsl:when>
				<xsl:when test="closingAmountOrPassbookDuplicateRadio = 1">
					check = document.getElementById('repeat');
					check.value='V';
				</xsl:when>
			</xsl:choose>
		</script>

	</xsl:template>

</xsl:stylesheet>		