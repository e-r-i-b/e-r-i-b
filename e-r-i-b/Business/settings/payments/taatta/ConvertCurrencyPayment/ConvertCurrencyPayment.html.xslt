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

	<xsl:variable name="currency" select="document('account-card-currency.xml')/entity-list/entity"/>
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
	    <script language="JavaScript" id="scrCheck"> </script>

		<input id="sellAmountCurrency" name="sellAmountCurrency" type="hidden"/>
		<input id="buyAmountCurrency"  name="buyAmountCurrency"  type="hidden"/>

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
														<span class="formTitle">Конверсия иностранной валюты</span>
														<br/>Используйте данную форму для конверсии иностранной валюты.
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
				  <td class="Width120 LabelAll">Счет для списания средств</td>
				  <td>
					   <xsl:variable name="payerAccount" select="payerAccountSelect"/>
					   <xsl:if test="$personAvailable">
							<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:updateCurrencyCodes()">
								<xsl:variable name="foreignCurrencyAccounts" select="document('active-accounts.xml')/entity-list/*"/>

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
					<td class="Width120 LabelAll" nowrap="true">Сумма продаваемой валюты</td>
					<td>
						<input id="sellAmount" size="24" name="sellAmount" value="0.00"/>&nbsp;<span id="sellCurrencyCode"/>
					    &nbsp;<input id="getRateSale" name="getRateSale" onclick="javascript:setRate(0)" value="Курс" type="button" class="buttWhite"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">Курс конверсии</td>
					<td><input type ="text" id="rateForeignCurrencySale" name="rateForeignCurrencySale" size="10" value="0.00" disabled="true">
							<xsl:attribute name="value">
								<xsl:value-of select="rateForeignCurrencySale"/>
							</xsl:attribute>
						</input>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Счет для зачисления средств</td>
					<td>
						<xsl:variable name="receiverAccount" select="receiverAccountSelect"/>
						<xsl:if test="$personAvailable">
							<select id="receiverAccountSelect" name="receiverAccountSelect" onchange="javascript:updateCurrencyCodes()">
								<xsl:variable name="foreignCurrencyAccounts" select="document('active-accounts.xml')/entity-list/*"/>

								<xsl:choose>
									<xsl:when test="count($foreignCurrencyAccounts) = 0">
										<option value="">нет счетов</option>
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
			    <tr>
					<td class="Width120 LabelAll" nowrap="true">Сумма покупаемой валюты</td>
					<td><input id="buyAmount" name="buyAmount" size="24" value="0.00"/>&nbsp;<span id="buyCurrencyCode"></span>
						&nbsp;<input id="getRateBuySale" name="getRateBuy" onclick="javascript:setRate(1)" value="Курс" type="button" class="buttWhite"/>
					</td>
				</tr>
				<tr>
					<td colSpan="2"> &nbsp;</td>
				</tr>
			</tbody>
		</table>

		<script type="text/javascript">
            var currencyCodes = new Array();

			function updateCurrencyCodes()
			{
				var payerAccountElem         = document.getElementById("payerAccountSelect");
				var code                     = currencyCodes[payerAccountElem.value];

				if (code == null)
					 code = "";

				var currencyCodeElem         = document.getElementById("sellCurrencyCode");
				currencyCodeElem.innerHTML   = code;
				var sellAmountCurrencyElem   = document.getElementById("sellAmountCurrency");
				sellAmountCurrencyElem.value = code;

				var receiverAccountElem      = document.getElementById("receiverAccountSelect");
				code                         = currencyCodes[receiverAccountElem.value];

				if (code == null)
					 code = "";

			    currencyCodeElem             = document.getElementById("buyCurrencyCode");
				currencyCodeElem.innerHTML   = code;

				var buyAmountCurrencyElem    = document.getElementById("buyAmountCurrency");
				buyAmountCurrencyElem.value  = code;

                document.getElementById("buyAmount").value = "";
				document.getElementById("sellAmount").value = "";
			    document.getElementById("rateForeignCurrencySale").value = "";
			}

			function init()
			{
			<xsl:variable name="foregnCurrencyAccounts" select="document('active-accounts.xml')/entity-list/*"/>
			<xsl:for-each select="$foregnCurrencyAccounts">
				currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
			</xsl:for-each>

				updateCurrencyCodes();
			}

			init();

			<![CDATA[
			function setRate(purchase)
			{
			   var amount, type;
			   if (purchase) /* покупка */
			      amount = document.getElementById("buyAmount").value; // покупаемая валюта
			   else
			      amount = document.getElementById("sellAmount").value; // продаваемая валюта

               if (amount > 600000)
			   {
			      alert("Сумма превышает сумму 600 000!")
				  return false;
			   }

			   var currencySell = document.getElementById("sellAmountCurrency").value;
			   var currencyBuy = document.getElementById("buyAmountCurrency").value;

	           document.all("scrCheck").src = document.webRootPrivate +
	                    "/rate/getRate.do?" + "cur1=" + currencySell + "&cur2="+currencyBuy +
	                    "&sum="+amount + "&type="+purchase;
			}
			    ]]>
			</script>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">

		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
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
											<span class="formTitle">Конверсия иностранной валюты</span>
											<br/>
											<span id="titleHelp">Конверсия иностранной валюты.</span>
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
				<td class="Width120 LabelAll">Счет для списания средств</td>
				<td>
					<xsl:variable name="payerAcc" select="payerAccountSelect"/>
					<xsl:variable name="payerAccount" select="document('active-accounts.xml')/entity-list/entity[@key=$payerAcc]"/>
					<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="$payerAccount/field[@name='type']"/>]
					<xsl:value-of select="$payerAccount/field[@name='currencyCode']"/>
				</td>
			</tr>

			<tr>
				<td class="Width120 LabelAll" nowrap="true">Сумма продаваемой валюты</td>
				<td>
					<xsl:value-of select="sellAmount"/>&nbsp;
					<xsl:value-of select="sellAmountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Курс конверсии</td>
				<td>
					<xsl:value-of select="course"/>&nbsp;
					<xsl:value-of select="sellAmountCurrency"/>/<xsl:value-of select="buyAmountCurrency"/>
				</td>
			</tr>

			<tr>
				<td class="Width120 LabelAll">Счет для зачисления средств</td>
				<td>
					<xsl:variable name="receiverAcc" select="receiverAccountSelect"/>
					<xsl:variable name="receiverAccount" select="document('active-accounts.xml')/entity-list/entity[@key=$receiverAcc]"/>
					<xsl:value-of select="receiverAccountSelect"/>[<xsl:value-of select="$receiverAccount/field[@name='type']"/>]
					<xsl:value-of select="$receiverAccount/field[@name='currencyCode']"/>
				</td>
			</tr>

			<tr>
				<td class="Width120 LabelAll">Сумма покупаемой валюты</td>
				<td>
					<xsl:value-of select="buyAmount"/>&nbsp;
					<xsl:value-of select="buyAmountCurrency"/>
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
				<td colspan="2"> &nbsp;</td>
			</tr>
		</table>

	<xsl:if test="(state='E') or (state='D') and (stateDescription != '')">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа:<xsl:value-of select="stateDescription"/>
		</div>
	</xsl:if>

	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='W'">Обрабатывается</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:when test="$code='D'">Отказан</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='V'">Отозван</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>