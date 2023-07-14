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
				<xsl:call-template name="initCurrencyScript"/>
				<xsl:call-template name="initAccountsScript"/>
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
		<!--TODO зачем это скрытое поле?!! если ниже есть с таким же именем и ID(!!!!!) нескрытое?!!!-->
		<input id="documentNumber" name="documentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="documentNumber"/>
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
						<input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/>
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
								<xsl:attribute name="value">2</xsl:attribute>
								<xsl:if test="$type = '2'">
									<xsl:attribute name="selected">true</xsl:attribute>
								</xsl:if>
								Купить валюту
							</option>
							<option>
								<xsl:attribute name="value">1</xsl:attribute>
								<xsl:if test="$type = '1'">
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
										<input id="purchaseForeignCurrencyAmount" size="24" value="{foreignCurrencyAmount}" name="foreignCurrencyAmount" onchange="javascript:setBuyAmount()"/>&nbsp;<span id="currencyCodePurchase"/></td>
								</tr>
								<tr>
									<td class="Width120 LabelAll" nowrap="true">Курс банка продажи валюты</td>
									<td><input type ="text" id="rateForeignCurrencySale" name="rateForeignCurrencySale" size="10" disabled="true">

											<xsl:attribute name="value">
												<xsl:value-of select="rateForeignCurrencySale"/>
											</xsl:attribute>

										</input>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll" nowrap="true">Сумма для списания средств на покупку валюты</td>
									<td><input id="buyCurrencyAmount" name="buyCurrencyAmount" size="10" disabled="true">
									<xsl:attribute name="value">
											<xsl:value-of select="buyCurrencyAmount"/>
										</xsl:attribute>
										</input>
									</td>
								</tr>
	                            <tr>
									<td class="Width120 LabelAll">Счет списания средств на покупку валюты</td>
									<td>
										<xsl:variable name="payerAccount" select="payerAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id = "payerAccountPurchase" name="payerAccountSelect">
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
									<td class="Width120 LabelAll">Счет для зачисления купленной валюты</td>
									<td>
										<select id="receiverAccountPurchase" name="receiverAccountSelect"/>

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
										<input id="saleForeignCurrencyAmount" size="24" value="{foreignCurrencyAmount}" name="foreignCurrencyAmount" onchange="javascript:setSaleAmount()"/>
										&nbsp;
										<span id="currencyCodeSale"/>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll" nowrap="true">Курс банка покупки валюты</td>
									<td><input id="rateForeignCurrencyPurchase" name="rateForeignCurrencyPurchase" size="10" disabled="true">
											<xsl:attribute name="value">
												<xsl:value-of select="rateForeignCurrencyPurchase"/>
											</xsl:attribute>
										</input>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll" nowrap="true">Сумма средств за проданную валюту</td>
									<td><input id="saleCurrencyAmount" name="saleCurrencyAmount" size="10" disabled="true">
								    	    <xsl:attribute name="value">
										        <xsl:value-of select="saleCurrencyAmount"/>
											</xsl:attribute>
										</input>
					                </td>
								</tr>
								<tr>
									<td class="Width120 LabelAll">Счет для списания средств на продажу валюты</td>
									<td>
										<select id="payerAccountSale" name="payerAccountSelect"/>
									</td>
								</tr>
								<tr>
									<td class="Width120 LabelAll">Счет для зачисления средств полученных от продажи валюты</td>
									<td>
										<xsl:variable name="receiverAccount" select="receiverAccountSelect"/>
										<xsl:if test="$personAvailable">
											<select id="receiverAccountSale" name="receiverAccountSelect">
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
		  //Добавлено округление сумм!
function round2Str(a,nfract)
{
 if (nfract==null) nfract=2;
 var ba=Math.pow(10,nfract);
 var i=Math.floor(a);
 var b=Math.round((a - i)*ba);
 b=b.toString();
 if (b.length > nfract) {i = i + 1; b = b.substr(1)}//перенос разряда в целую часть при округлении
 while ( b.length < nfract ) b = "0"+b;
 return ( "" + i + "." + b);
}

function formatFloat(v, nfract)
{
  var i,res, val = v.toString();
  if ( val.length==0 ) return val;
  res = round2Str(parseFloat(val), nfract);
  i   = res.indexOf(".") + 1;
  if (i == 0)
   {
      res = res.concat(".");
      i=res.length;
   }
  while ( res.length-i < nfract ) res = res.concat("0");
  return res;
}

		function setDiv()
			{
				setElems();
				var purchaseForeignCurrencyAmountElem = document.getElementById("purchaseForeignCurrencyAmount");
				var saleForeignCurrencyAmountElem = document.getElementById("saleForeignCurrencyAmount");
				var purchaseElem    = document.getElementById("type");

				if (purchaseElem.value == "2")
					purchaseForeignCurrencyAmountElem.value = "";
				else
					saleForeignCurrencyAmountElem.value = "";

			setCurrency();
			}
		//Функция переключает блоки покупки продажи
		//ставит disabled на элементы неактивного блока, чтобы они не пошли в submit-->
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
				if (purchaseElem.value == "2")
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
				var purchaseElem    = document.getElementById("type");
				var saleDiv = document.getElementById("saleDiv");
				var purchaseDiv = document.getElementById("purchaseDiv");

				setElems();
				}

			function setCurrency(){
				document.getElementById("buyCurrencyAmount").value = "";
				document.getElementById("saleCurrencyAmount").value = "";
			    document.getElementById("purchaseForeignCurrencyAmount").value = "";
				document.getElementById("saleForeignCurrencyAmount").value = "";
				var purSelect = document.getElementById("purchaseSelect").value;
    			var operation = purSelect == '1' ? 'BUY' : 'SALE';

				var selectCurr = document.getElementById("purchaseCurrencyCodeSelect");
				var name = selectCurr[selectCurr.selectedIndex].value;
				var buyField = document.getElementById("rateForeignCurrencySale");
					buyField.value = currencies[name][purSelect];

				var selectCurr = document.getElementById("saleCurrencyCodeSelect");
				var name = selectCurr[selectCurr.selectedIndex].value;
				var saleField = document.getElementById("rateForeignCurrencyPurchase");
					saleField.value = currencies[name][operation];

			}
			function setBuyAmount(){
				var rate   = document.getElementById("rateForeignCurrencySale").value;
				var amount = document.getElementById("purchaseForeignCurrencyAmount").value;
				if (rate*amount>600000) {
					alert("Сумма покупки валюты превышает сумму 600 000 рублей")
					return false;
				}
					else document.getElementById("buyCurrencyAmount").value = formatFloat((rate*amount), 2);
                    return true;
				}
			function setSaleAmount(){
			    var rate   = document.getElementById("rateForeignCurrencyPurchase").value;
				var amount = document.getElementById("saleForeignCurrencyAmount").value;
				if (rate*amount>600000) {
					alert("Сумма продажи валюты превышает сумму 600 000 рублей")
					return false;
				}
					else document.getElementById("saleCurrencyAmount").value = formatFloat((rate*amount), 2);
			        return true;
			}

			function refreshCurrencyAccount()
			{
			    if (document.getElementById("purchaseSelect").value == '2'){
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

			var btn = findCommandButton("button.save");
			btn.setValidationFunction (setSaleAmount);

			var btn = findCommandButton("button.save");
			btn.setValidationFunction (setBuyAmount);

			init();
			]]>
			</script>
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
				<td class="Width120 LabelAll">Сумма для списания средств на покупку валюты</td>
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
				<td><div id="state">
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
		<script type="text/javascript">
		function init()
			{
				var purchaseDiv = document.getElementById("purchaseDiv");
				var saleDiv = document.getElementById("saleDiv");
				var purchaseElem    = document.getElementById("type");
				if (purchaseElem.value == "2")
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
			<xsl:when test="$code='W'">Обрабатывается</xsl:when>
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

</xsl:stylesheet>