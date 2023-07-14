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
				<xsl:call-template name="initCurrencyScript"/>
				<xsl:call-template name="initAccountsScript"/>
				<xsl:call-template name="initAllAccountsScript"/>
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="fromAccountCurrency" name="fromAccountCurrency" type="hidden"/>
		<input id="fromAccountAmount"   name="fromAccountAmount"  type="hidden"/>
		<input id="fromAccountType"     name="fromAccountType"    type="hidden"/>
		<input id="toAccountCurrency"   name="toAccountCurrency"   type="hidden"/>
		<input id="toAccountAmount"     name="toAccountAmount"    type="hidden"/>
        <input id="toAccountType"       name="toAccountType"      type="hidden"/>
		<input id="foreignCurrency" name="foreignCurrency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="foreignCurrency"/>
			    </xsl:attribute>
		</input>
		<input id="purchase" name="purchase" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="purchase"/>
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
				<td colspan="2">
					<table class="MaxSize">
						<tr>
							<td class="Width200 LabelAll aPayment">Номер документа</td>
							<td>
								<xsl:value-of select="documentNumber"/>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll aPayment">Дата документа</td>
							<td class="aPayment">
								<input type="text" id="documentDate" name="documentDate" size="10">
									<xsl:attribute name="value">
										<xsl:value-of select="documentDate"/>
									</xsl:attribute>
								</input>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll aPayment">Операция</td>
							<td class="aPayment">
								<xsl:variable name="purchase" select="purchase"/>
								<select id="purchaseSelect" name="purchaseSelect" onchange="javascript:setDiv()" onkeypress="javascript:setDiv()" onkeydown="javascript:setDiv()" onkeyup="javascript:setDiv()">
									<option>
										<xsl:attribute name="value">BUY</xsl:attribute>
										<xsl:if test="$purchase = 'BUY'">
											<xsl:attribute name="selected">true</xsl:attribute>
										</xsl:if>
										Купить валюту
									</option>
									<option>
										<xsl:attribute name="value">SALE</xsl:attribute>
										<xsl:if test="$purchase = 'SALE'">
											<xsl:attribute name="selected">true</xsl:attribute>
										</xsl:if>
										Продать валюту
									</option>
								</select>
							</td>
						</tr>
					</table>
				</td>
				</tr>
				<tr>
					<td colspan="2">
							<table class="MaxSize"  id="purchaseDiv" style="display:block">
								<tr>
									<td class="Width200 LabelAll aPayment">Вид валюты для покупки</td>
									<td class="aPayment">
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
									<td class="Width200 LabelAll aPayment">Сумма покупаемой валюты</td>
									<td class="aPayment">
										<input id="purchaseForeignCurrencyAmount" size="24" value="{foreignCurrencyAmount}" name="foreignCurrencyAmount" onchange="javascript:setBuyAmount()"/>&nbsp;<span id="currencyCodePurchase"/></td>
								</tr>
								<tr>
									<td class="Width200 LabelAll aPayment">Курс банка продажи валюты</td>
									<td class="aPayment"><input type ="text" id="rateForeignCurrencySale" name="rateForeignCurrencySale" size="10" disabled="true">
										
											<xsl:attribute name="value">
												<xsl:value-of select="rateForeignCurrencySale"/>
											</xsl:attribute>

										</input>
									</td>
								</tr>
								<tr>
									<td class="Width200 LabelAll aPayment">Сумма для списания средств на покупку валюты</td>
									<td class="aPayment"><input id="buyCurrencyAmount" name="buyCurrencyAmount" size="10" disabled="true">
									<xsl:attribute name="value">
											<xsl:value-of select="buyCurrencyAmount"/>
										</xsl:attribute>
										</input>
									</td>
								</tr>
	                            <tr>
									<td class="Width200 LabelAll aPayment">Счет списания средств на покупку валюты</td>
									<td class="aPayment">
										<xsl:variable name="payerAccount" select="payerAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id = "payerAccountPurchase" name="payerAccountSelect" onchange="javascript:fromAccount()">
											<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
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
									<td class="Width200 LabelAll aPayment">Счет для зачисления купленной валюты</td>
									<td class="aPayment">
										<select id="receiverAccountPurchase" name="receiverAccountSelect" onChange="javascript:toAccount()"/>

									</td>
								</tr>
							</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
							<table class="MaxSize" id="saleDiv" style="display:block">
								<tr>
									<td class="Width200 LabelAll aPayment">Вид валюты для продажи</td>
									<td class="aPayment">
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
									<td class="Width200 LabelAll aPayment">Сумма продаваемой валюты</td>
									<td class="aPayment">
										<input id="saleForeignCurrencyAmount" size="24" value="{foreignCurrencyAmount}" name="foreignCurrencyAmount" onchange="javascript:setSaleAmount()"/>
										&nbsp;
										<span id="currencyCodeSale"/>
									</td>
								</tr>
								<tr>
									<td class="Width200 LabelAll aPayment">Курс банка покупки валюты</td>
									<td class="aPayment"><input id="rateForeignCurrencyPurchase" name="rateForeignCurrencyPurchase" size="10" disabled="true">
											<xsl:attribute name="value">
												<xsl:value-of select="rateForeignCurrencyPurchase"/>
											</xsl:attribute>
										</input>
									</td>
								</tr>
								<tr>
									<td class="Width200 LabelAll aPayment">Сумма средств за проданную валюту</td>
									<td class="aPayment"><input id="saleCurrencyAmount" name="saleCurrencyAmount" size="10" disabled="true">
								    	    <xsl:attribute name="value">
										        <xsl:value-of select="saleCurrencyAmount"/>
											</xsl:attribute>
										</input>
					                </td>
								</tr>
								<tr>
									<td class="Width200 LabelAll aPayment">Счет для списания средств на продажу валюты</td>
									<td class="aPayment">
										<select id="payerAccountSale" name="payerAccountSelect" onChange="javascript:fromAccount()"/>
									</td>
								</tr>
								<tr>
									<td class="Width200 LabelAll aPayment">Счет для зачисления средств полученных от продажи валюты</td>
									<td class="aPayment">
										<xsl:variable name="receiverAccount" select="receiverAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id="receiverAccountSale" name="receiverAccountSelect" onChange="javascript:toAccount()">
												<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
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
		  <![CDATA[

		//Функция setDiv очищает поле сумма

		function setDiv()
			{
				setElems();
				var purchaseForeignCurrencyAmountElem = document.getElementById("purchaseForeignCurrencyAmount");
				var saleForeignCurrencyAmountElem = document.getElementById("saleForeignCurrencyAmount");
				var purchaseElem    = document.getElementById("purchase");

				if (purchaseElem.value == "BUY")
				{
					purchaseForeignCurrencyAmountElem.value = "";
					setPurchaseCurrencyCode();
				}
				else
				{
					saleForeignCurrencyAmountElem.value = "";
					setSaleCurrencyCode();
				}

			}
		//Функция переключает блоки покупки продажи
		//ставит disabled на элементы неактивного блока, чтобы они не пошли в submit-->
	    function setElems()
			{
				var purchaseDiv = document.getElementById("purchaseDiv");
			    var saleDiv = document.getElementById("saleDiv");

				var purchaseSelect = document.getElementById("purchaseSelect");
				var purchaseElem    = document.getElementById("purchase");

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
				if (purchaseElem.value == "BUY")
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

			    setCurrency();
			    refreshCurrencyAccount();
			}

		function setSaleCurrencyCode()
			{
				var code = document.getElementById("saleCurrencyCodeSelect");
				var currencyCodeElem       = document.getElementById("currencyCodeSale");
				var foreignCurrencyElem    = document.getElementById("foreignCurrency");
				currencyCodeElem.innerHTML = code.value;
				foreignCurrencyElem.value  = code.value;

			    setCurrency();
			    refreshCurrencyAccount();
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
				var purchaseElem    = document.getElementById("purchase");
				var saleDiv = document.getElementById("saleDiv");
				var purchaseDiv = document.getElementById("purchaseDiv");

				setElems();
				setDiv();
				}

			function setCurrency(){
				document.getElementById("buyCurrencyAmount").value = "";
				document.getElementById("saleCurrencyAmount").value = "";
			    document.getElementById("purchaseForeignCurrencyAmount").value = "";
				document.getElementById("saleForeignCurrencyAmount").value = "";
				var purSelect = document.getElementById("purchaseSelect").value;
			    var operation = purSelect == 'SALE' ? 'BUY' : 'SALE';

				var selectCurr = document.getElementById("purchaseCurrencyCodeSelect");
				var name = selectCurr[selectCurr.selectedIndex].value;
				var buyField = document.getElementById("rateForeignCurrencySale");
					buyField.value = currencies[name][operation];

				var selectCurr = document.getElementById("saleCurrencyCodeSelect");
				var name = selectCurr[selectCurr.selectedIndex].value;
				var saleField = document.getElementById("rateForeignCurrencyPurchase");
					saleField.value = currencies[name][operation];
	
			}
			function setBuyAmount(){
				var rate   = document.getElementById("rateForeignCurrencySale").value+'e1';
				var amount = document.getElementById("purchaseForeignCurrencyAmount").value+'e-1';
				if (rate*amount>600000) {
					alert("Сумма покупки валюты превышает сумму 600 000 рублей")
					return false;
				}	
				var sum = rate*amount;
                sum = sum*100;
                sum = Math.round(sum);
                sum = sum/100;
				document.getElementById("buyCurrencyAmount").value = sum;
                return true;
				}
			function setSaleAmount(){
			    var rate   = document.getElementById("rateForeignCurrencyPurchase").value+'e1';
				var amount = document.getElementById("saleForeignCurrencyAmount").value+'e-1';
				if (rate*amount>600000) {
					alert("Сумма продажи валюты превышает сумму 600 000 рублей")
					return false;
				}
					var sum = rate*amount;
                    sum = sum*100;
                    sum = Math.round(sum);
                    sum = sum/100;
					document.getElementById("saleCurrencyAmount").value = sum;
			        return true;
			}

			function refreshCurrencyAccount()
			{
			    if (document.getElementById("purchaseSelect").value == 'BUY'){
					var selectAccount = document.getElementById("receiverAccountPurchase");
					var selectCurr = document.getElementById("purchaseCurrencyCodeSelect");
					var name = selectCurr[selectCurr.selectedIndex].value;
                } else {
					var selectAccount = document.getElementById("payerAccountSale");
					var selectCurr = document.getElementById("saleCurrencyCodeSelect");
					var name = selectCurr[selectCurr.selectedIndex].value;

                }

	            selectAccount.options.length = 0;
             	for ( var i = 0; i < accounts.length; i++ )
				{   var currentAccount = accounts[i].number+"["+accounts[i].type+"] "+accounts[i].amount+ " "+accounts[i].currency;
					if ( accounts[i].currency == name){
						selectAccount.options[selectAccount.options.length] = new Option(currentAccount, accounts[i].number);
			        }
				}
                if ( selectAccount.options.length == 0 )
					selectAccount.options[selectAccount.options.length] = new Option("Нет дос тупных счетов в данной валюте", "");
			}

			function fromAccount(){
				if (!document.getElementById("payerAccountSale").disabled)
                   var fromAccountSelect = document.getElementById("payerAccountSale");
                if (!document.getElementById("payerAccountPurchase").disabled)
                   var fromAccountSelect = document.getElementById("payerAccountPurchase");
                       
                var fromCurrency = accountCurrencySave[fromAccountSelect.value];
                var fromAccountCurrency = document.getElementById("fromAccountCurrency");
                fromAccountCurrency.value = fromCurrency;

				var fromAmount = accountAmountsSave[fromAccountSelect.value];
	            var fromAccountAmount = document.getElementById("fromAccountAmount");
	            fromAccountAmount.value = fromAmount;

				var fromType =  accountTypesSave[fromAccountSelect.value];
		        var fromAccountType = document.getElementById("fromAccountType");
		        fromAccountType.value = fromType;
		    }

			function toAccount(){
				if (!document.getElementById("receiverAccountPurchase").disabled)
                   var toAccountSelect = document.getElementById("receiverAccountPurchase");
                if (!document.getElementById("receiverAccountSale").disabled)
                   var toAccountSelect = document.getElementById("receiverAccountSale");

                var toCurrency = accountCurrencySave[toAccountSelect.value];
                var toAccountCurrency = document.getElementById("toAccountCurrency");
                toAccountCurrency.value = toCurrency;

				var toAmount = accountAmountsSave[toAccountSelect.value];
	            var toAccountAmount = document.getElementById("toAccountAmount");
	            toAccountAmount.value = toAmount;

				var toType =  accountTypesSave[toAccountSelect.value];
		        var toAccountType = document.getElementById("toAccountType");
		        toAccountType.value = toType;
		   }

			var btn = findCommandButton("button.save");
			btn.setValidationFunction (setSaleAmount);

			var btn = findCommandButton("button.save");
			btn.setValidationFunction (setBuyAmount);
			
			init();
			toAccount();
			fromAccount();
			]]>
			</script>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<input id="purchase" name="purchase" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="purchase"/>
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
											<span id="titleHelp">Покупка и продажа иностранной валюты за рубли.</span>
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
				<td class="Width200 LabelAll aPayment">Номер документа</td>
				<td class="aPayment">
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll aPayment">Дата документа</td>
				<td class="aPayment">
					<xsl:value-of select="documentDate"/>
				</td>
			</tr>
			<xsl:variable name="adDate" select="admissionDate"/>
			<xsl:if test="not($adDate = '')">
				<tr>
					<td class="Width200 LabelAll aPayment">Дата приема платежа</td>
					<td class="aPayment">
						<xsl:value-of select="admissionDate"/>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td class="Width200 LabelAll aPayment">Сумма покупаемой валюты</td>
				<td class="aPayment">
					<xsl:value-of select="foreignCurrencyAmount"/>&nbsp;
					<xsl:value-of select="foreignCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll aPayment">Курс банка продажи валюты</td>
				<td class="aPayment">
					<xsl:value-of select="course"/>&nbsp;
					<xsl:value-of select="rurCurrency"/>/<xsl:value-of select="foreignCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll aPayment">Сумма для списания средств на покупку валюты</td>
				<td class="aPayment">
					<xsl:value-of select="rurAmount"/>&nbsp;
					<xsl:value-of select="rurCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll aPayment">Счет списания средств на покупку валюты</td>
				<td class="aPayment">
					<xsl:value-of select="payerAccountSelect"/>
				   [<xsl:value-of select="fromAccountType"/>]
					<xsl:value-of select="format-number(fromAccountAmount, '0.00')"/>&nbsp;
					<xsl:value-of select="fromAccountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll aPayment">Счет для зачисления купленной валюты</td>
				<td class="aPayment">
					<xsl:value-of select="receiverAccountSelect"/>
				   [<xsl:value-of select="toAccountType"/>]
					<xsl:value-of select="format-number(toAccountAmount, '0.00')"/>&nbsp;
					<xsl:value-of select="toAccountCurrency"/>
				</td>
			</tr>
			<tr class="aPayment">
				<td class="Width200 LabelAll">Статус платежа</td>
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
					<td class="Width200 LabelAll aPayment">Номер документа</td>
					<td class="aPayment">
						<xsl:value-of select="documentNumber"/>
					</td>
				</tr>
				<tr>
					<td class="Width200 LabelAll aPayment">Дата документа</td>
					<td class="aPayment">
						<xsl:value-of select="documentDate"/>
					</td>
				</tr>
				<xsl:variable name="adDate" select="admissionDate"/>
				<xsl:if test="not($adDate = '')">
				<tr>
					<td class="Width200 LabelAll aPayment">Дата приема платежа</td>
					<td class="aPayment">
						<xsl:value-of select="admissionDate"/>
					</td>
				</tr>
				</xsl:if>
				<tr>
					<td class="Width200 LabelAll aPayment">Сумма продаваемой валюты</td>
					<td class="aPayment">
						<xsl:value-of select="foreignCurrencyAmount"/>&nbsp;
						<xsl:value-of select="foreignCurrency"/>
					</td>
				</tr>
				<tr>
					<td class="Width200 LabelAll aPayment">Курс банка покупки валюты</td>
					<td class="aPayment">
						<xsl:value-of select="course"/>&nbsp;
						<xsl:value-of select="rurCurrency"/>/<xsl:value-of select="foreignCurrency"/>
					</td>
				</tr>
				<tr>
					<td class="Width200 LabelAll aPayment">Сумма средств за проданную валюту</td>
					<td class="aPayment">
						<xsl:value-of select="rurAmount"/>&nbsp;
						<xsl:value-of select="rurCurrency"/>
					</td>
				</tr>
				<tr>
					<td class="Width200 LabelAll aPayment">Счет для списания средств на продажу валюты</td>
					<td class="aPayment">
						<xsl:value-of select="payerAccountSelect"/>
					   [<xsl:value-of select="fromAccountType"/>]
						<xsl:value-of select="format-number(fromAccountAmount, '0.00')"/>&nbsp;
						<xsl:value-of select="fromAccountCurrency"/>
					</td>
				</tr>
				<tr>
					<td class="Width200 LabelAll aPayment">Счет для зачисления средств полученных от продажи валюты</td>
					<td class="aPayment">
						<xsl:value-of select="receiverAccountSelect"/>
					   [<xsl:value-of select="toAccountType"/>]
						<xsl:value-of select="format-number(toAccountAmount, '0.00')"/>&nbsp;
						<xsl:value-of select="toAccountCurrency"/>
					</td>
				</tr>
				<tr>
				<td class="Width200 LabelAll aPayment">Статус платежа</td>
				<td class="aPayment"><div id="state">
						<a onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" style="text-decoration:underline">
							<xsl:call-template name="state2text">
								<xsl:with-param name="code">
									<xsl:value-of select="state"/>
								</xsl:with-param>
							</xsl:call-template>
						</a>
					</div>
				</td>
			</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
	<xsl:if test="(state='E') or (state='D') and (stateDescription != '')">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа:<xsl:value-of select="stateDescription"/>
		</div>
	</xsl:if>
		<script type="text/javascript">
		function init()
			{
				var purchaseDiv = document.getElementById("purchaseDiv");
				var saleDiv = document.getElementById("saleDiv");
				var purchaseElem    = document.getElementById("purchase");
				if (purchaseElem.value == "BUY")
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
			<xsl:when test="$code='W'">На исполнении</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:when test="$code='D'">Отказан</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='V'">Отозван</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="initCurrencyScript">
		<script type="text/javascript">
			var currencies = new Array();

            <xsl:for-each select="$currency">
                <xsl:variable name="optCurrency" select="@key"/>
            rate = new Array()
	        <xsl:for-each select="*">

	        rate['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="./text()"/>';
            </xsl:for-each>
            currencies['<xsl:value-of select="@key"/>'] = rate;

			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initAccountsScript">
		<script type="text/javascript">

			var accounts = new Array();
			var account;
			<xsl:for-each select="document('active-foreign-currency-accounts.xml')/entity-list/*">
			account          = new Object()
			account.number   = '<xsl:value-of select="@key"/>';
			account.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			account.amount   = '<xsl:value-of select="field[@name='amountDecimal']/text()"/>';
			account.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			accounts[accounts.length] = account;
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initAllAccountsScript">
		<script type="text/javascript">

		var accountCurrencySave = new Array();
	    var accountAmountsSave  = new Array();
		var accountTypesSave    = new Array();

		function init(){
			<xsl:variable name="allAccounts" select="document('all-accounts.xml')/entity-list/*"/>
			<xsl:for-each select="$allAccounts">
	            accountCurrencySave['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
				accountAmountsSave['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='amountDecimal']"/>';
				accountTypesSave['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='type']"/>';
			</xsl:for-each>
		}

		init();
		</script>
	</xsl:template>
</xsl:stylesheet>