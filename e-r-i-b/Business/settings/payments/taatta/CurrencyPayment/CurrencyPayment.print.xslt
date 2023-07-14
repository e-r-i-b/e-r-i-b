<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:template match="/form-data">
		<style>
			.insertInput{border:0 solid transparent;border-bottom:1 solid black;margin:0pt;font-family:Times New Roman;font-size:12pt;}
			.insertInputnoBorder{border:0 solid transparent;border-bottom:1 solid white;margin:0pt;font-family:Times New Roman;font-size:12pt;}
			.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
			.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
			.docBorderFirst{border-bottom:1px solid #000000;border-right:1px solid #000000;border-top:1px solid #000000;border-left:1px solid #000000;}
			.docBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;border-top:1px solid #000000;}
			.textPadding{padding-left:4;padding-right:4;}
			.textPaddingTop{padding-left:4;padding-right:4;padding-top:4;}
			.font10{font-family:Times New Roman;font-size:10pt;}
		</style>
		<input id="purchase" name="purchase" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="purchase"/>
	            </xsl:attribute>
		</input>
			<table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
				<col style="width:172mm"/>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
							<tr>
								<td align="right" width="55%">
									<b>ЗАЯВЛЕНИЕ НА ПЕРЕВОД НОМЕР<br/>PAYMENT ORDER NUMBER</b>
								</td>
								<td align="left">
									&nbsp;&nbsp;<input type="Text" readonly="true" style="Border:1px solid black;width:25%" align="center">
										<xsl:attribute name="value">&nbsp;<xsl:value-of select="documentNumber"/>&nbsp;</xsl:attribute>
									</input>
								</td>
							</tr>
							<tr>
								<td align="right" width="50%">
									<b>Дата / Date</b>
								</td>
								<td align="left">
									&nbsp;&nbsp;<input type="Text" readonly="true" style="Border:1px solid black;width:35%" align="center">
										<xsl:attribute name="value">&nbsp;<xsl:value-of select="documentDate"/>&nbsp;</xsl:attribute>
									</input>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<br/>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<table class="docTableBorder" style="width:172mm;height:100%;font-family:Times New Roman;font-size:9pt;padding-top:4;padding-left:14;" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" align="left" class='docTdBorder' width="50%">
									:50: КЛИЕНТ / CLIENT<br/>
									Имя клиента/ Client’s name<br/><br/>
									Адрес / Address
									
								</td>
								<td class='docTdBorder'>
									<br/>
									<span style="font-weight:bold;font-size:10pt;"><i>ЗАПОЛНЯЕТСЯ БАНКОМ</i></span><br/>
									Банк - корреспондент<br/>
									<font style="font-weight:normal;">:30: Дата валютирования</font><br/><br/>
                                    Подписи ответственных лиц Банка<br/><br/><br/><br/>
								</td>
							</tr>
							<tr>
								<td valign="top" align="left" class='docTdBorder' colspan="2">
									<table width="100%" style="font-family:Times New Roman;font-size:9pt;">
										<tr>
											<td  valign="top" width="50%">
											   :32: ПЛАТИТЬ / PAY
												<table style="font-family:Times New Roman;font-size:9pt;" cellpadding="0" cellspacing="0" width="80%">
													<tr>
														<td align="center" class='docBorderFirst' width="40%">
															<xsl:value-of select="currency"/>
														</td>
														<td align="center" class='docBorder'>
															<xsl:value-of select="amount"/>
														</td>
													</tr>
													<tr>
														<td>
														   Валюта платежа
														</td>
														<td align="center">
															Сумма цифрами
														</td>
													</tr>
												</table>
												Payment currency Numerical amount
											</td>
											<td  valign="top" width="50%">
												Сумма прописью / Amount in words<br/>
												<xsl:variable name="amountV" select="amount"/>
												<xsl:variable name="currencyV" select="currency"/>
												<xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/>
											</td>
										</tr>
										<tr>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;height:100%;font-family:Times New Roman;font-size:9pt;padding-top:4;padding-left:14;padding-bottom:4;" cellpadding="0" cellspacing="0">
							<tr>
								<td valign="top" align="left" class='docTdBorder' width="60%">
									<table style="font-family:Times New Roman;font-size:9pt;" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td>
												<nobr>Платеж осуществить с нашего счета</nobr><br/>
												Please debit our account No
											</td>
											<td align="left">
											&nbsp;<input type="Text" readonly="true" style="Border:1px solid black;width:90%" align="center">
														<xsl:attribute name="value">&nbsp;<xsl:value-of select="payerAccountSelect"/>&nbsp;</xsl:attribute>
												  </input>
											</td>
										</tr>
									</table>
								</td>
								<td valign="top" class='docTdBorder' >
									<xsl:variable name="paymentConditionsV" select="paymentConditions"/>
									<nobr>Перевод на условиях: / Payment terms:</nobr><br/>
									обычный/ <span style="font-weight:bold;font-size:10pt;">SPOT</span>
									<input type="Text" readonly="true" style="Border:1px solid black;width:9%">
										<xsl:if test="$paymentConditionsV = '2'">
											<xsl:attribute name="value">&nbsp;X</xsl:attribute>
										</xsl:if>
										<xsl:if test="$paymentConditionsV != '2'">
											<xsl:attribute name="value">&nbsp;</xsl:attribute>
										</xsl:if>
									</input>
									; срочный/ <span style="font-weight:bold;font-size:10pt;">TOD</span> 
									<input type="Text" readonly="true" style="Border:1px solid black;width:9%">
										<xsl:if test="$paymentConditionsV = '0'">
											<xsl:attribute name="value">&nbsp;X</xsl:attribute>
										</xsl:if>
										<xsl:if test="$paymentConditionsV != '0'">
											<xsl:attribute name="value">&nbsp;</xsl:attribute>
										</xsl:if>
									</input>
								</td>
							</tr>
							<tr>
								<td style="border-bottom:1px solid #000000;" valign="top" align="left">
									<table style="font-family:Times New Roman;font-size:9pt;" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td width="70%">
												<nobr>:  :	Расходы по переводу Тестового банка за наш счет</nobr><br/>
												&nbsp;&nbsp;&nbsp;<nobr>Charges of Russky Slaviansky Bank for our account</nobr>
											</td>
											<td align="center">
												<xsl:variable name="commissionV" select="commission"/>
												<input type="Text" readonly="true" style="Border:1px solid black;width:19%">
													<xsl:if test="$commissionV = '0'">
														<xsl:attribute name="value">&nbsp;X</xsl:attribute>
													</xsl:if>
													<xsl:if test="$commissionV != '0'">
														<xsl:attribute name="value">&nbsp;</xsl:attribute>
													</xsl:if>
												</input>
											</td>
										</tr>
									</table>
								</td>
								<td valign="top" class='docTdBorder' >
									<table style="font-family:Times New Roman;font-size:9pt;" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td width="70%">
												<nobr>за счет получателя</nobr><br/>
												for beneficiary’s account
											</td>
											<td align="center">
												<xsl:variable name="commissionV" select="commission"/>
												<input type="Text" readonly="true" style="Border:1px solid black;width:29%">
													<xsl:if test="$commissionV = '1'">
														<xsl:attribute name="value">&nbsp;X</xsl:attribute>
													</xsl:if>
													<xsl:if test="$commissionV != '1'">
														<xsl:attribute name="value">&nbsp;</xsl:attribute>
													</xsl:if>
												</input>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;height:100%;font-family:Times New Roman;font-size:9pt;padding-top:4;padding-left:14;padding-bottom:4;" cellpadding="0" cellspacing="0">
							<tr>
								<td class='docTdBorder'>
									<br/><br/>
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;height:100%;font-family:Times New Roman;font-size:9pt;padding-top:4;padding-left:14;padding-bottom:4;" cellpadding="0" cellspacing="0">
							<tr>
								<td style="border-right:1px solid #000000;">
									:56:	БАНК-КОРРЕСПОНДЕНТ БАНКА ПОЛУЧАТЕЛЯ<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;CORRESPONDENT BANK OF BENEFICIARY’S BANK<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;Наименование банка / Bank name<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="intermediaryBankName"/><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;Адрес / Address<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="intermediaryBankAddress"/><br/>
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;font-family:Times New Roman;font-size:9pt;padding-left:14;" cellpadding="0" cellspacing="0">
							<tr>
								<td class='docTdBorder' width="33%">
									S.W.I.F.T. код / code<br/>
									или / or &lt; ... &gt;
								</td>
								<td class='docBorder' width="33%">
									<xsl:value-of select="intermediaryBankSWIFT"/>
								</td>
								<td class='docTdBorder' width="33%">
									&lt;CHIPS UID No, ABA No,<br/>
									Bank Code, Sort Code etc.&gt;
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;height:100%;font-family:Times New Roman;font-size:9pt;padding-top:4;padding-left:14;padding-bottom:4;" cellpadding="0" cellspacing="0">
							<tr>
								<td style="border-right:1px solid #000000;">
									:57:	БАНК ПОЛУЧАТЕЛЯ ПЛАТЕЖА / BENEFICIARY’S BANK<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;Наименование банка / Bank name<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="receiverBankName"/><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;Адрес / Address<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="receiverBankAddress"/><br/>
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;font-family:Times New Roman;font-size:9pt;padding-left:14;" cellpadding="0" cellspacing="0">
							<tr>
								<td style="border-right:1px solid #000000;" width="33%">
									Счет в банке-корресп.<br/>
									Acc with corresp. Bank
								</td>
								<td class='docBorder' width="33%">
									<xsl:value-of select="receiverBankСorrAccount"/>
								</td>
								<td style="border-right:1px solid #000000;" width="33%">
									&nbsp;
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;border-right:1px solid #000000;width:172mm;font-family:Times New Roman;font-size:4pt;padding-left:14;" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<br/>
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;font-family:Times New Roman;font-size:9pt;padding-left:14;" cellpadding="0" cellspacing="0">
							<tr>
								<td class='docTdBorder' width="33%">
									S.W.I.F.T. код / code<br/>
									или / or &lt; ... &gt;
								</td>
								<td class='docBorder' width="33%">
									<xsl:value-of select="receiverBankSWIFT"/>
								</td>
								<td class='docTdBorder' width="33%">
									&lt;CHIPS UID No, ABA No,<br/>
									Bank Code, Sort Code etc.&gt;
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;height:100%;font-family:Times New Roman;font-size:9pt;padding-top:4;padding-left:14;padding-bottom:4;" cellpadding="0" cellspacing="0">
							<tr>
								<td style="border-right:1px solid #000000;">
									:59:	ПОЛУЧАТЕЛЬ ПЛАТЕЖА / BENEFICIARY<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;Имя получателя / Beneficiary’s name<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="receiverName"/><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;Адрес / Address<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="receiverAddress"/><br/>
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;font-family:Times New Roman;font-size:9pt;padding-left:14;" cellpadding="0" cellspacing="0">
							<tr>
								<td class='docTdBorder' width="33%">
									Счет получателя номер<br/>
									Beneficiary’s Acc No
								</td>
								<td class='docBorder' width="33%">
									<xsl:value-of select="receiverAccountSelect"/>
								</td>
								<td class='docTdBorder' width="33%">
									&nbsp;
								</td>
							</tr>
						</table>
						<table style="border-left:1px solid #000000;width:172mm;height:100%;font-family:Times New Roman;font-size:9pt;padding-top:4;padding-left:14;padding-bottom:4;" cellpadding="0" cellspacing="0">
							<tr>
								<td style="border-right:1px solid #000000;">
									:70: ДЕТАЛИ ПЛАТЕЖА / PAYMENT DETAILS<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="ground"/><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;<xsl:value-of select="groundAdd"/><br/>
								</td>
							</tr>
						</table>
						<table class = "docTableBorder" style="width:172mm;height:100%;font-family:Times New Roman;font-size:7pt;padding-top:4;padding-left:14;padding-bottom:4;" cellpadding="0" cellspacing="0">
							<tr>
								<td class='docTdBorder' align="center">
									В случае недостаточной или неточной информации для платежа Банк не несет ответственности за сроки прохождения платежа.<br/>
									In case of incomplete or incorrect information in payment instructions the Bank incurs no responsibility for processing of payment.

								</td>
							</tr>
						</table>
						<table style="width:172mm;height:100%;font-family:Times New Roman;font-size:10pt;padding-top:4;padding-left:14;padding-bottom:4;">
							<tr>
								<td>
									<br/>
								</td>
							</tr>

							<tr>
								<td width="35%" align="center">
									М.П.
								</td>
								<td>
									<b>Подписи / Signatures</b>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;>html" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="rur&#x2D;accounts.xml" srcSchemaRoot="entity&#x2D;list" AssociatedInstance="file:///c:/Projects/RS/PhizIC&#x2D;SBRF/Business/settings/forms/PurchaseCurrencyPayment/rur&#x2D;accounts.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template><template match="/form&#x2D;data"><block path="script/xsl:value&#x2D;of" x="172" y="18"/><block path="table/tr[1]/td[1]/xsl:if" x="172" y="97"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="12" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="126" y="175"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="172" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="172" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="57"/><block path="table/tr[1]/td[1]/xsl:if[1]" x="132" y="97"/><block path="table/tr[3]/td[1]/xsl:if" x="92" y="97"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="212" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="86" y="175"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="132" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="12" y="57"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="17"/><block path="table/tr[3]/td[1]/xsl:if[1]" x="52" y="97"/><block path="table/tr[4]/td[1]/span/xsl:value&#x2D;of" x="12" y="97"/><block path="script[1]/xsl:for&#x2D;each" x="222" y="107"/><block path="" x="12" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of" x="132" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of[1]" x="52" y="137"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->