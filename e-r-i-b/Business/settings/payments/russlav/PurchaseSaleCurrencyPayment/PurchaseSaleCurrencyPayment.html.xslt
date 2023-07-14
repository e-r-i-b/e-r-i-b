<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="foreignCurrency" name="foreignCurrency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="foreignCurrency"/>
			    </xsl:attribute>
		</input>
		<input id="type" name="type" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="type"/>
	            </xsl:attribute>
		</input>
		<table class="paymentFon" cellSpacing="2" cellPadding="0" border="0">
			<tbody>
				<tr>
					<td vAlign="middle" align="right">
						<img src="{$resourceRoot}/images/PCPayment.gif" border="0"/>
					</td>
					<td>
						<table class="MaxSize">
							<tbody>
								<tr>
									<td class="silverBott paperTitle" align="center">
										<table height="100%" cellSpacing="0" cellPadding="0" width="420">
											<tbody>
												<tr>
													<td class="titleHelp">
														<span class="formTitle">Покупка/продажа иностранной валюты</span>
														<br/>Покупка и продажа иностранной валюты за рубли.
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td width="100%">&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Номер документа</td>
					<td>
						<xsl:value-of select="documentNumber"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Дата документа</td>
					<td>
						<input type="text" id="documentDate" name="documentDate" size="10">
							<xsl:attribute name="value">
								<xsl:value-of select="documentDate"/>
							</xsl:attribute>
						</input>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Операция</td>
					<td>
						<xsl:variable name="type" select="type"/>
						<select id="purchaseSelect" name="purchaseSelect" onchange="javascript:setDiv()" onkeypress="javascript:setDiv()" onkeydown="javascript:setDiv()" onkeyup="javascript:setDiv()">
							<option>
								<xsl:attribute name="value">BUY</xsl:attribute>
								<xsl:if test="type = 'BUY'">
									<xsl:attribute name="selected">true</xsl:attribute>
								</xsl:if>
								Купить валюту
							</option>
							<option>
								<xsl:attribute name="value">SALE</xsl:attribute>
								<xsl:if test="$type = 'SALE'">
									<xsl:attribute name="selected">true</xsl:attribute>
								</xsl:if>
								Продать валюту
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<table class="MaxSize"  id="purchaseDiv" style="display:block">
								<tr>
									<td class="Width120 LabelAll">Вид валюты для покупки</td>
									<td>
										<xsl:variable name="foreignCurrency" select="foreignCurrency"/>
										<select id="purchaseCurrencyCodeSelect" name="purchaseCurrencyCodeSelect"
										  onchange="javascript:setPurchaseCurrencyCode()"
										  onkeypress="javascript:setPurchaseCurrencyCode()"
										  onkeydown="javascript:setPurchaseCurrencyCode()"
										  onkeyup="javascript:setPurchaseCurrencyCode()">
											<option>
												<xsl:attribute name="value">USD</xsl:attribute>
												<xsl:if test="$foreignCurrency = 'USD'">
													<xsl:attribute name="selected">true</xsl:attribute>
												</xsl:if>
												Доллар США
											</option>
											<option>
												<xsl:attribute name="value">EUR</xsl:attribute>
												<xsl:if test="$foreignCurrency = 'EUR'">
													<xsl:attribute name="selected">true</xsl:attribute>
												</xsl:if>
												ЕВРО
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll" nowrap="true">Сумма покупаемой валюты</td>
									<td>
										<input id="purchaseForeignCurrencyAmount" size="24" value="{foreignCurrencyAmount}" name="foreignCurrencyAmount"/>&nbsp;<span id="currencyCodePurchase"/></td>
								</tr>
								<tr>
									<td class="Width120 LabelAll">Счет списания средств на покупку валюты</td>
									<td>
										<xsl:variable name="payerAccount" select="payerAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id = "payerAccountPurchase" name="payerAccountSelect">
												<xsl:for-each select="document('rur-accounts.xml')/entity-list/*">
													<option>
														<xsl:attribute name="value">
															<xsl:value-of select="./@key"/>
														</xsl:attribute>
														<xsl:if test="$payerAccount = ./@key">
															<xsl:attribute name="selected">true</xsl:attribute>
														</xsl:if>
														<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
														<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
														&nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>
													</option>
												</xsl:for-each>
											</select>
										</xsl:if>
										<xsl:if test="not($personAvailable)">
											<select disabled="disabled">
												<option selected="selected">Счет клиента</option>
											</select>
										</xsl:if>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll">Счет для зачисления купленной валюты</td>
									<td>
										<xsl:variable name="receiverAccount" select="receiverAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id="receiverAccountPurchase" name="receiverAccountSelect">
												<xsl:variable name="foreignCurrencyAccounts" select="document('foreign-currency-accounts.xml')/entity-list/*"/>

												<xsl:choose>
													<xsl:when test="count($foreignCurrencyAccounts) = 0">
														<option value="">нет валютных счетов</option>
													</xsl:when>
													<xsl:otherwise>
														<xsl:for-each select="$foreignCurrencyAccounts">
															<option>
																<xsl:attribute name="value">
																	<xsl:value-of select="./@key"/>
																</xsl:attribute>
																<xsl:if test="$receiverAccount = ./@key">
																	<xsl:attribute name="selected">true</xsl:attribute>
																</xsl:if>
																<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
																<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
																&nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>
															</option>
														</xsl:for-each>
													</xsl:otherwise>
												</xsl:choose>
											</select>
										</xsl:if>
										<xsl:if test="not($personAvailable)">
											<select disabled="disabled">
												<option selected="selected">Счет клиента</option>
											</select>
										</xsl:if>
									</td>
								</tr>
							</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
							<table class="MaxSize" id="saleDiv" style="display:block">
								<tr>
									<td class="Width120 LabelAll">Вид валюты для продажи</td>
									<td>
										<xsl:variable name="foreignCurrency" select="foreignCurrency"/>
										<select id="saleCurrencyCodeSelect" name="saleCurrencyCodeSelect" 
										  onchange="javascript:setSaleCurrencyCode()" 
										  onkeypress="javascript:setSaleCurrencyCode()" 
										  onkeydown="javascript:setSaleCurrencyCode()" 
										  onkeyup="javascript:setSaleCurrencyCode()">
											<option>
												<xsl:attribute name="value">USD</xsl:attribute>
												<xsl:if test="$foreignCurrency = 'USD'">
													<xsl:attribute name="selected">true</xsl:attribute>
												</xsl:if>
												Доллар США
											</option>
											<option>
												<xsl:attribute name="value">EUR</xsl:attribute>
												<xsl:if test="$foreignCurrency = 'EUR'">
													<xsl:attribute name="selected">true</xsl:attribute>
												</xsl:if>
												ЕВРО
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll" nowrap="true">Сумма продаваемой валюты</td>
									<td>
										<input id="saleForeignCurrencyAmount" size="24" value="{foreignCurrencyAmount}" name="foreignCurrencyAmount"/>
										&nbsp;
										<span id="currencyCodeSale"/>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll">Счет для списания средств на продажу валюты</td>
									<td>
										<xsl:variable name="payerAccount" select="payerAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id="payerAccountSale" name="payerAccountSelect">
												<xsl:variable name="foreignCurrencyAccounts" select="document('foreign-currency-accounts.xml')/entity-list/*"/>

												<xsl:choose>
													<xsl:when test="count($foreignCurrencyAccounts) = 0">
														<option value="">нет валютных счетов</option>
													</xsl:when>
													<xsl:otherwise>
														<xsl:for-each select="$foreignCurrencyAccounts">
															<option>
																<xsl:attribute name="value">
																	<xsl:value-of select="./@key"/>
																</xsl:attribute>
																<xsl:if test="$payerAccount = ./@key">
																	<xsl:attribute name="selected">true</xsl:attribute>
																</xsl:if>
																<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
																<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
																&nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>
															</option>
														</xsl:for-each>
													</xsl:otherwise>
												</xsl:choose>
											</select>
										</xsl:if>
										<xsl:if test="not($personAvailable)">
											<select disabled="disabled">
												<option selected="selected">Счет клиента</option>
											</select>
										</xsl:if>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll">Счет для зачисления средств полученных от продажи валюты</td>
									<td>
										<xsl:variable name="receiverAccount" select="receiverAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id="receiverAccountSale" name="receiverAccountSelect">
												<xsl:for-each select="document('rur-accounts.xml')/entity-list/*">
													<option>
														<xsl:attribute name="value">
															<xsl:value-of select="./@key"/>
														</xsl:attribute>
														<xsl:if test="$receiverAccount = ./@key">
															<xsl:attribute name="selected">true</xsl:attribute>
														</xsl:if>
														<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
														<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
														&nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>
													</option>
												</xsl:for-each>
											</select>
										</xsl:if>
										<xsl:if test="not($personAvailable)">
											<select disabled="disabled">
												<option selected="selected">Счет клиента</option>
											</select>
										</xsl:if>
									</td>
								</tr>
							</table>
					</td>
				</tr>
				<tr>
					<td colSpan="2">&nbsp;</td>
				</tr>
			</tbody>
		</table>
		<script type="text/javascript">

		<!--
		Функция setDiv очищает поле сумма
		-->
		function setDiv()
			{
				setElems();
				var purchaseForeignCurrencyAmountElem = document.getElementById("purchaseForeignCurrencyAmount");
				var saleForeignCurrencyAmountElem = document.getElementById("saleForeignCurrencyAmount");
				var purchaseElem    = document.getElementById("type");

				if (purchaseElem.value == 'BUY')
					purchaseForeignCurrencyAmountElem.value = "";
				else
					saleForeignCurrencyAmountElem.value = "";
			}
			<!--Функция переключает блоки покупки продажи
		ставит disabled на элементы неактивного блока, чтобы они не пошли в submit-->
	    function setElems()
			{
				var purchaseDiv = document.getElementById("purchaseDiv");
			    var saleDiv = document.getElementById("saleDiv");

				var purchaseSelect = document.getElementById("purchaseSelect");
				var purchaseElem    = document.getElementById("type");

				var payerAccountSaleElem = document.getElementById("payerAccountSale");
				var payerAccountPurchaseElem = document.getElementById("payerAccountPurchase");

				var receiverAccountSaleElem = document.getElementById("receiverAccountSale");
				var receiverAccountPurchaseElem = document.getElementById("receiverAccountPurchase");

				var purchaseForeignCurrencyAmountElem = document.getElementById("purchaseForeignCurrencyAmount");
				var saleForeignCurrencyAmountElem = document.getElementById("saleForeignCurrencyAmount");

				var saleCurrencyCodeSelect = document.getElementById("saleCurrencyCodeSelect");
				var purchaseCurrencyCodeSelect = document.getElementById("purchaseCurrencyCodeSelect");

				var foreignCurrencyElem  = document.getElementById("foreignCurrency");

				purchaseElem.value = purchaseSelect.value;
				if (purchaseElem.value == 'BUY')
				{
					purchaseDiv.style.display = "block";
					saleDiv.style.display = "none";

					payerAccountSaleElem.disabled = true;
					payerAccountPurchaseElem.disabled = false;

					receiverAccountSaleElem.disabled = true;
					receiverAccountPurchaseElem.disabled = false;

					saleForeignCurrencyAmountElem.disabled = true;
					purchaseForeignCurrencyAmountElem.disabled = false;

					foreignCurrencyElem.value = purchaseCurrencyCodeSelect.value;
				}
				else
				{
					purchaseDiv.style.display = "none";
					saleDiv.style.display = "block";

					payerAccountSaleElem.disabled = false;
					payerAccountPurchaseElem.disabled = true;

					receiverAccountSaleElem.disabled = false;
					receiverAccountPurchaseElem.disabled = true;

					saleForeignCurrencyAmountElem.disabled = false;
					purchaseForeignCurrencyAmountElem.disabled = true;

					foreignCurrencyElem.value = saleCurrencyCodeSelect.value;
				}
			}
		function setPurchaseCurrencyCode()
			{
				var code = document.getElementById("purchaseCurrencyCodeSelect");
				var currencyCodeElem       = document.getElementById("currencyCodePurchase");
 			    var foreignCurrencyElem    = document.getElementById("foreignCurrency");
				currencyCodeElem.innerHTML = code.value;
				foreignCurrencyElem.value  = code.value;
			}

		function setSaleCurrencyCode()
			{
				var code = document.getElementById("saleCurrencyCodeSelect");
				var currencyCodeElem       = document.getElementById("currencyCodeSale");
				var foreignCurrencyElem    = document.getElementById("foreignCurrency");
				currencyCodeElem.innerHTML = code.value;
				foreignCurrencyElem.value  = code.value;
			}

		   function init()
			{
				var foreignCurrencyElem    = document.getElementById("foreignCurrency");
				if (foreignCurrencyElem.value=="")
					foreignCurrencyElem.value = "USD"
				var currencyCodeElem1       = document.getElementById("currencyCodeSale");
				currencyCodeElem1.innerHTML = foreignCurrencyElem.value;
				var currencyCodeElem2       = document.getElementById("currencyCodePurchase");
				if (currencyCodeElem2.innerHTML=="")
					currencyCodeElem2.innerHTML = foreignCurrencyElem.value;
				var purchaseElem    = document.getElementById("type");
				var saleDiv = document.getElementById("saleDiv");
				var purchaseDiv = document.getElementById("purchaseDiv");

				setElems();
				}
 			
			init();</script>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<input id="type" name="type" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="type"/>
	            </xsl:attribute>
		</input>
		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon" id="purchaseDiv" style="display:block">
			<tr>
				<td align="right" valign="middle">
					<img src="{$resourceRoot}/images/PCPayment.gif" border="0"/>
				</td>
				<td>
					<table class="MaxSize">
						<tr>
							<td align="center" style="border-bottom:1 solid silver" class="paperTitle">
								<table height="100%" width="260px" cellspacing="0" cellpadding="0">
									<tr>
										<td class="titleHelp">
											<span class="formTitle">Покупка иностранной валюты</span>
											<br/>
											<span id="titleHelp"></span>
										</td>
									</tr>
								</table>
							</td>
							<td width="15px">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Номер документа</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата документа</td>
				<td>
					<xsl:value-of select="documentDate"/>
				</td>
			</tr>
			<xsl:variable name="adDate" select="admissionDate"/>
			<xsl:if test="not($adDate = '')">
				<tr>
					<td class="Width120 LabelAll">Дата приема платежа</td>
					<td>
						<xsl:value-of select="admissionDate"/>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td class="Width120 LabelAll" nowrap="true">Сумма покупаемой валюты</td>
				<td>
					<xsl:value-of select="foreignCurrencyAmount"/>&nbsp;
					<xsl:value-of select="foreignCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Курс банка продажи валюты</td>
				<td>
					<xsl:value-of select="course"/>&nbsp;
					<xsl:value-of select="rurCurrency"/>/<xsl:value-of select="foreignCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма для списания средств на покупку валюту</td>
				<td>
					<xsl:value-of select="rurAmount"/>&nbsp;
					<xsl:value-of select="rurCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет списания средств на покупку валюты</td>
				<td>
					<xsl:variable name="payerAcc" select="payerAccountSelect"/>
					<xsl:variable name="payerAccount" select="document('rur-accounts.xml')/entity-list/entity[@key=$payerAcc]"/>
					<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="$payerAccount/field[@name='type']"/>]
					<xsl:value-of select="$payerAccount/field[@name='currencyCode']"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет для зачисления купленной валюты</td>
				<td>
					<xsl:variable name="receiverAcc" select="receiverAccountSelect"/>
					<xsl:variable name="receiverAccount" select="document('foreign-currency-accounts.xml')/entity-list/entity[@key=$receiverAcc]"/>
					<xsl:value-of select="receiverAccountSelect"/>[<xsl:value-of select="$receiverAccount/field[@name='type']"/>]
					<xsl:value-of select="$receiverAccount/field[@name='currencyCode']"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Статус платежа</td>
				<td>
					<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state"/>
					</xsl:with-param>
				</xsl:call-template>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
			<table cellspacing="2" cellpadding="0" border="0" class="paymentFon" id="saleDiv" style="display:none">
				<tr>
					<td align="right" valign="middle">
						<img src="{$resourceRoot}/images/SCPayment.gif" border="0"/>
					</td>
					<td>
						<table class="MaxSize">
							<tr>
								<td align="center" style="border-bottom:1 solid silver" class="paperTitle">
									<table height="100%" width="260px" cellspacing="0" cellpadding="0">
										<tr>
											<td class="titleHelp">
												<span class="formTitle">Продажа иностранной валюты</span>
												<br/>
												<span id="titleHelp"></span>
											</td>
										</tr>
									</table>
								</td>
								<td width="15px">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Номер документа</td>
					<td>
						<xsl:value-of select="documentNumber"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Дата документа</td>
					<td>
						<xsl:value-of select="documentDate"/>
					</td>
				</tr>
				<xsl:variable name="adDate" select="admissionDate"/>
				<xsl:if test="not($adDate = '')">
				<tr>
					<td class="Width120 LabelAll">Дата приема платежа</td>
					<td>
						<xsl:value-of select="admissionDate"/>
					</td>
				</tr>
				</xsl:if>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">Сумма продаваемой валюты</td>
					<td>
						<xsl:value-of select="foreignCurrencyAmount"/>&nbsp;
						<xsl:value-of select="foreignCurrency"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Курс банка покупки валюты</td>
					<td>
						<xsl:value-of select="course"/>&nbsp;
						<xsl:value-of select="rurCurrency"/>/<xsl:value-of select="foreignCurrency"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Сумма средств за проданную валюту</td>
					<td>
						<xsl:value-of select="rurAmount"/>&nbsp;
						<xsl:value-of select="rurCurrency"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Счет для списания средств на продажу валюты</td>
					<td>
						<xsl:variable name="payerAcc" select="payerAccountSelect"/>
						<xsl:variable name="payerAccount" select="document('foreign-currency-accounts.xml')/entity-list/entity[@key=$payerAcc]"/>
						<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="$payerAccount/field[@name='type']"/>]
						<xsl:value-of select="$payerAccount/field[@name='currencyCode']"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Счет для зачисления средств полученных от продажи валюты</td>
					<td>
						<xsl:variable name="receiverAcc" select="receiverAccountSelect"/>
						<xsl:variable name="receiverAccount" select="document('rur-accounts.xml')/entity-list/entity[@key=$receiverAcc]"/>
						<xsl:value-of select="receiverAccountSelect"/>[<xsl:value-of select="$receiverAccount/field[@name='type']"/>]
						<xsl:value-of select="$receiverAccount/field[@name='currencyCode']"/>
					</td>
				</tr>
				<tr>
				<td class="Width120 LabelAll">Статус платежа</td>
				<td>
					<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state"/>
					</xsl:with-param>
				</xsl:call-template>
				</td>
			</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
		<script type="text/javascript">
		function init()
			{
				var purchaseDiv = document.getElementById("purchaseDiv");
				var saleDiv = document.getElementById("saleDiv");
				var purchaseElem    = document.getElementById("type");
				if (purchaseElem.value == 'BUY')
				{
					purchaseDiv.style.display = "block";
					saleDiv.style.display = "none";
				}
				else
				{
					purchaseDiv.style.display = "none";
					saleDiv.style.display = "block";
				}
			}
			init();</script>

	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='W'">Принят</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;>html" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="rur&#x2D;accounts.xml" srcSchemaRoot="entity&#x2D;list" AssociatedInstance="file:///c:/Projects/RS/PhizIC&#x2D;SBRF/Business/settings/forms/PurchaseCurrencyPayment/rur&#x2D;accounts.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template><template match="/form&#x2D;data"><block path="script/xsl:value&#x2D;of" x="172" y="18"/><block path="table/tr[1]/td[1]/xsl:if" x="172" y="97"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="12" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="126" y="175"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="172" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="172" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="57"/><block path="table/tr[1]/td[1]/xsl:if[1]" x="132" y="97"/><block path="table/tr[3]/td[1]/xsl:if" x="92" y="97"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="212" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="86" y="175"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="132" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="12" y="57"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="17"/><block path="table/tr[3]/td[1]/xsl:if[1]" x="52" y="97"/><block path="table/tr[4]/td[1]/span/xsl:value&#x2D;of" x="12" y="97"/><block path="script[1]/xsl:for&#x2D;each" x="222" y="107"/><block path="" x="12" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of" x="132" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of[1]" x="52" y="137"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->