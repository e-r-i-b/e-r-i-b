<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
 	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'label120 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="'width:140px;'"/>

	<xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
	<xsl:call-template name="commonScript"/>
<!--				<script type="text/javascript" src="file://C:\Projects\RS\PhizIC-MAIN\WebResources\web\scripts\Utils.js"/>-->
	<xsl:choose>
		<xsl:when test="$mode = 'edit'">
			  <div style="overflow:hidden">
				<xsl:variable name="products" select="document(concat($data-path,'products.xml'))"/>
				<xsl:call-template name="initializationScript"/>
				<xsl:call-template name="commonScript"/>
				<xsl:variable name="accounts" select="document(concat($data-path,'active-accounts.xml'))"/>

				<xsl:apply-templates select="$products" mode="products"/>
				<xsl:apply-templates select="$accounts" mode="accounts"/>
				<xsl:call-template name="editForm"/>
			  </div>
			</xsl:when>
		<xsl:when test="$mode = 'view'">
			<xsl:apply-templates mode="view"/>
		</xsl:when>
	</xsl:choose>
    </xsl:template>

 	<xsl:template name="editForm">
		<xsl:call-template name="editHtml"/>
		<xsl:call-template name="editInitControls"/>
		<xsl:call-template name="editInitValues"/>
	</xsl:template>

	<xsl:template name="editHtml" mode="accounts">
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
		<xsl:variable name="departments" select="document('departments.xml')/entity-list"/>
		<input type="hidden" id="radio" name="radio"/>
            <xsl:call-template name="standartRow">
			    <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			    <xsl:with-param name="required">false</xsl:with-param>
			    <xsl:with-param name="rowValue">
			        <xsl:value-of select="$formData/documentNumber"/>
			        <input id="documentNumber" name="documentNumber" type="hidden"/>
			    </xsl:with-param>
		    </xsl:call-template>		
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Открыть</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select onchange="this.value=0;window.location='{$webRoot}/private/claims/claim.do?form=AccountOpeningClaim';">
							<option value="0" selected="true">Вклад</option>
							<option>Счет</option>
						  </select>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Вид вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="product" name="product" onchange="refreshProductInfo();refreshTexts();"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Валюта вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="currency" name="currency" onchange="refreshPeriods();refreshTexts();"></select>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Срок вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="period" name="period" onchange="refreshTexts();"></select>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Минимальная сумма вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<span id="minAmountSpan"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="amount" name="amount"/>&nbsp;<span id="amountCurrencySpan"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Списать со счета</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="fromAccount" name="fromAccount" onchange="setFromAccType()"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Действие по окончании срока вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="hidden" id="actionType" name="actionType" value="{actionType}"/>
					&nbsp;
					<xsl:variable name="radio" select="radio"/>
					<input type="radio" name="radiob" id="radio1"  onClick="setRadio(1)">
					    <xsl:if test="$radio = 'false'">
							<xsl:attribute name="checked">true</xsl:attribute>
						</xsl:if>
					</input>
					Автоматически пролонгировать<br/>&nbsp;
					<input type="radio" name="radiob" id="radio2" onClick="setRadio(2)">
						<xsl:if test="$radio = 'true'">
							<xsl:attribute name="checked">true</xsl:attribute>
						</xsl:if>
					</input>
					Перевести на счет
					<select id="toAccount" name="toAccount" disabled="true" onchange="setToAccType()"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Дата визита в отделение банка</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="visitDate" name="visitDate" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>&nbsp;ДД.ММ.ГГГГ
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Офис банка</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="department" name="department">
							<xsl:choose>
								<xsl:when test="count($departments) = 0">
									<option value="">Нет офисов</option>
								</xsl:when>
								<xsl:otherwise>
									<xsl:for-each select="$departments/entity">
										<option>
											<xsl:if test="@key = $currentPerson/entity/field[@name='currentDepartmentId']/text()">
												<xsl:attribute name="selected">true</xsl:attribute>
											</xsl:if>
											<xsl:attribute name="value">
												<xsl:value-of select="@key"/>
											</xsl:attribute>
											<xsl:value-of select="field[@name='fullName']/text()"/>
										</option>
									</xsl:for-each>
								</xsl:otherwise>
							</xsl:choose>
					 </select>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="fullName" size="50" name="fullName" maxlength="100"/>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Гражданство</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="citizenship" name="citizenship"  maxlength="100"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Контактный телефон</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="phone" name="phone" maxlength="13"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">E-mail</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="email" name="email" maxlength="30"/>
				</xsl:with-param>
			</xsl:call-template>
			<tr>				
				<td colspan="2" align="right" class="Width120 LabelAll"><a href ="javascript:void(0)" onclick="javascript:openSelectDocument();">Посмотреть договор</a></td>
			</tr>
			<!--<tr>
				<td colspan="2" align="right" class="Width120 LabelAll"><a href ="javascript:void(0);" onclick="javascript:changeLocation();">Счета</a></td>
			</tr>-->
			<tr><td >&nbsp;</td></tr>
		<!--</table>-->
		
		<!--<br/><br/>-->
		<b><div id="message"/></b>


		<input type="hidden" id="minAmount" name="minAmount"/>
		<input type="hidden" id="currentDate" name="currentDate"/>
		<input type="hidden" id="accountType" name="accountType"/>
		<input type="hidden" id="toAccountType" name="toAccountType"/>
		<input type="hidden" id="fromAccountType" name="fromAccountType"/>

		<script type="text/javascript">

		document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';

		<![CDATA[

		function clearMasks(event)
		{
			clearInputTemplate("visitDate", DATE_TEMPLATE);
		}

		function refreshTexts()
		{
			var currency      = document.getElementById('currency').value;
			var period        = document.getElementById('period').value;
			var productSelect = document.getElementById('product');

			var productObject = null;
			var tuple = null;
			<!-- если выбрано значение -->
		    if (productSelect.selectedIndex >= 0 &&  productSelect.options[productSelect.selectedIndex].text!="Нет доступных видов вкладов" )
		    {
			    productObject = productSelect.options[productSelect.selectedIndex].productObject;
                tuple = productObject.getTupleWithMinAmount(currency, period);
            }
            else return;
			var text          = tuple != null ? tuple.minAmount : "";
			
			setValue("minAmount", tuple != null ? tuple.minAmount : "");
			setValue("minAmountSpan", tuple != null ? tuple.minAmount + '&nbsp;' + currency : "" );
			setValue("amountCurrencySpan", currency);
            setValue("accountType", tuple != null ? tuple.accountType : "");

            refreshAccounts(currency, "toAccount") ;
            refreshAccounts(currency, "fromAccount");

			var message = tuple != null ? "" : "Невозможно открыть вклад.\nВыберите другие значения валюты и/или срока.";
			setValue("message", message);
		}
        function refreshAccounts(currency, accountSelect)
        {
			var accountsOptionCreator= function(account)
									{
										if (currency != account.currency)
											return null;
										var value = account.number+"["+account.type+"] "+account.amount+ " "+account.currency;
										var option = new Option(value, account.number);
										option.account = account;
										return option;
								   	}
			updateSelect(accountSelect, createOptions(accounts, accountsOptionCreator, "Нет доступных счетов", "Выберите счет"));
		}

        function refreshProductInfo()
        {
            var productSelect = document.getElementById('product');
            if(productSelect.selectedIndex == 0 && productSelect.options[productSelect.selectedIndex].text=="Нет доступных видов вкладов"){
                var currency = document.getElementById('currency');
                var period = document.getElementById('period');
                currency.options[0]=new Option('Нет доступных валют вклада', 0);
                period.options[0]=new Option('Нет доступных периодов вклада',0);
                return;
            }
            if (productSelect.selectedIndex == -1){
                // если ничего не выбрано
                return;
            }
            var productObject = productSelect.options[productSelect.selectedIndex].productObject;
            var currencies = productObject.getCurrencies();
            updateSelect("currency", createOptions(currencies.sort(),
                                       function(value){return new Option("(" + value.code + ") " + value.name, value.code)},
                                       "нет", "Выберите валюту"));
            refreshPeriods();
        }

        function refreshPeriods()
        {
            var productSelect = document.getElementById('product');
            var productObject = productSelect.options[productSelect.selectedIndex].productObject;
            var periods = productObject.getPeriods(
                document.getElementById('currency').value
            );
            updateSelect(
                "period",
                createOptions(
                    periods.sort(),
                    function(value){return new Option(formatPeriod(value), value)},
                    "нет", "Выберите период"
                )
            );
        }

        function setRadio(ch)
		{
			var tAcc = document.getElementById('toAccount');
			if (ch == 1) {
				tAcc.disabled = true;
				setValue("radio", "false");
				setValue("actionType", "autorenewal");
			} else {
				tAcc.disabled = false;
				setToAccType();
				setValue("radio", "true");
				setValue("actionType", "notautorenewal");
			}
		}

		function setFromAccType()
		{
			var fromAcc = document.getElementById('fromAccount');
			if (fromAcc.selectedIndex < 0)
				fromAcc.selectedIndex = 0;
			var account = fromAcc.options[fromAcc.selectedIndex].account;

			if ( account == null ) {
				return;
			}
			setValue("fromAccountType", account.type);
		}

		function openSelectDocument()
		{
			window.open(document.webRootPrivate + '/templates/documents/list.do?action=start',
		       	'TemplatesDocuments', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
		}		

		function setToAccType()
		{
			var toAcc = document.getElementById('toAccount');
			if (toAcc.selectedIndex < 0)
				toAcc.selectedIndex = 0;
			var account = toAcc.options[toAcc.selectedIndex].account;

			if ( account == null ) {
				return;
			}
			setValue("toAccountType", account.type);
		}
		]]>
		setElement("fullName", "<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>");
		</script>
	</xsl:template>

	<xsl:template name="editInitControls">
		<script type="text/javascript">
		<![CDATA[
		function isEmptyString(value)
		{
			return value == null || value == "";
		}
		 
		function createOptions(array, func, nullOptionText, selectOptionText)
		{
			var options = new Array();
			if (array.length > 0 && !isEmptyString(selectOptionText)
			    options[options.length] = new Option(selectOptionText, "");
			for ( var i = 0; i < array.length; i++ )
			{
                try
                {
				    if(func(array[i]) != null)
				        options[options.length] = func(array[i]);
				}
				catch(e){}
			}

			if ( options.length == 0 && !isEmptyString(nullOptionText) )
				options[options.length] = new Option(nullOptionText, "");

			return options;
		}

		function updateSelect(id, options)
		{
			var select  = document.getElementById(id);
			select.options.length = 0;

			for ( var i = 0 ; i < options.length; i++ )
			{
				select.options[i] = options[i];
			}
		}

		function updateAction()
		{
			var radio = document.getElementById("radio");
			if (radio.value == "true")
			{
			    document.getElementById("radio2").checked = true;
			    document.getElementById("toAccount").disabled = false;
			}
			else
			{
			    document.getElementById("radio1").checked = true;
				document.getElementById("toAccount").disabled = true;
			}
		}

		updateSelect("product", createOptions(depositProducts, function(value)
															   {
																	var option = new Option(value.name, value.id);
																	option.productObject = value; 
																	return option;
															   },
															   "Нет доступных видов вкладов", "Выберите вид вклада"));


		updateSelect("fromAccount", createOptions(fromAccounts,
												function(account)
												{
													var option = new Option(account.number + ' [' + account.type + '] ' + account.amount.toFixed(2) + ' ' + account.currency, account.number);
													option.account = account;
													return option;
												},
												"Нет доступных счетов", "Выберите счет списания"));

		updateSelect("toAccount", createOptions(toAccounts,
												function(account)
												{
													var option = new Option(account.number + ' [' + account.type + '] ' + account.amount.toFixed(2) + ' ' + account.currency , account.number);
													option.account = account;
													return option;
												},
												"Нет доступных счетов", "Выберите счет зачисления"));
		]]>
		</script>
	</xsl:template>

	<xsl:template name="editInitValues">

		<script type="text/javascript">
        <xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>

		function setInitValue(elementId, value)
		{
			var elem = document.getElementById(elementId);
			if (elem == null)
				return;
			elem.value = value;
			if (elem.type == "checkbox")
			{
				var boolVal = false;
				if (value == "true")
					boolVal = true;
				elem.checked = boolVal;
			}
		}
		setInitValue("actionType", "autorenewal");
		setInitValue("currency", '<xsl:value-of select="$formData/*[name()='currency']/text()"/>');
		refreshProductInfo();
		refreshTexts();
		setFromAccType();
		setToAccType();

		updateAction();
		</script>
	</xsl:template>

	<xsl:template name="initAccountsScript" match="entity-list" mode="accounts">
		<script type="text/javascript">
			<xsl:for-each select="entity">
			var account = new Object()
			account.number   = '<xsl:value-of select="@key"/>';
			account.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			account.amount   = <xsl:value-of select="field[@name='amountDecimal']/text()"/>;
			account.type     = '<xsl:value-of select="field[@name='type']/text()"/>';

			if ( account.number.match("^42301") || account.number.match("^42601") || account.number.match("^40817") || account.number.match("^40820"))
			{
				toAccounts[toAccounts.length] = account;
			}
			accounts[accounts.length] = account;
			fromAccounts[fromAccounts.length] = account;
			</xsl:for-each>
		</script>
	</xsl:template>


	<xsl:template name="initializationScript">
		<script type="text/javascript">
		<![CDATA[
		var depositProducts = new Array();
		var toAccounts      = new Array();
		var fromAccounts    = new Array();
        var accounts        = new Array();
		/* Дескриптор валюты <Код ISO, Название> */
		function CurrencyDescriptor(code, name)
		{
			this.code = code;
			this.name = name;
		}

		function findAccountByCurrency(currencyCode)
		{
			for (var i = 0; i < toAccounts.length; i++ )
			{
				if ( toAccounts[i].currency == currencyCode || (toAccounts[i].currency == 'RUR' && currencyCode == 'RUB'))
				{
					return toAccounts[i];
				}
			}

			return null;
		}

		/* Продукт */
		function DepositProduct(id, name)
		{
		    this.id     = id;
			this.name   = name;
			this.tuples = new Array();

			this.addTuple = function(currency, period, minAmount, rate, accountType)
			{
				var tuple       = new Object();
				tuple.accountType = accountType;
				tuple.currency  = currency;
				tuple.period    = period;
                tuple.minAmount = minAmount;
				tuple.rate      = rate;

				this.tuples[ this.tuples.length ] = tuple;
			}

			this.getTupleWithMinAmount = function(currencyCode, period)
			{
				var result;

				for ( var i = 0; i < this.tuples.length; i++ )
				{
					var tuple = this.tuples[i];

					if ( currencyCode == tuple.currency.code && period == tuple.period )
					{
						result = tuple;
					}
				}

				return result;
			}

			this.getCurrencies = function()
			{
				var currencyComparator = function(val1, val2) 
										 { 
										 	return val1.code == val2.code
										 }
				var result = new Array();

				for ( var i = 0; i < this.tuples.length; i++ )
				{
					var currency = this.tuples[i].currency;
					if ( !result.contains(currency, currencyComparator) )
						 result[result.length] = currency;
				}

				return result.sort();
			}

			this.getPeriods = function( currency)
			{
                var result = new Array();
                for ( var i = 0; i < this.tuples.length; i++ )
                {
                    var period = this.tuples[i].period;
                    if (period == null || period == ''){
                        continue;
                    }
                    var minAmount =
                        this.getTupleWithMinAmount(
                            currency,
                            period
                    );
                    if ( !result.contains(period) && (minAmount != null) )
                        result[result.length] = period;
                    }

                    return result.sort();
			}
		}
		]]>
		</script>
	</xsl:template>

	<xsl:template name="commonScript">
		<script type="text/javascript">
		<![CDATA[
        function setValue(elementId, value)
		{
			var elem = document.getElementById(elementId);

			if(elem.value != null)
				elem.value = value;
			else if(elem.innerHTML != null)
				elem.innerHTML = value;
		}
		]]>
		</script>
	</xsl:template>

    <xsl:template name="initProductsScript" match="product" mode="products">
        <script type="text/javascript">
            var index = depositProducts.length;
            depositProducts[index] = new DepositProduct(<xsl:value-of select="@id"/>, '<xsl:value-of select="@name"/>');
            <xsl:variable name="currencies" select="dictionaries/entity-list[@name='currencies']"/>
            <xsl:for-each select="data/element">
                <xsl:variable name="currencyCode" select="value[@field='currency']/text()"/>
                <xsl:variable name="currencyName" select="$currencies/entity[@key=$currencyCode]/text()"/>
                depositProducts[index].addTuple
                (
                    new CurrencyDescriptor('<xsl:value-of select="value[@field='currency']"/>',
                                           '<xsl:value-of select="$currencyName"/>'),
                                           '<xsl:value-of select="value[@field='period']"/>',
                                           '<xsl:value-of select="value[@field='minimumAmount']"/>',
                                           '<xsl:value-of select="value[@field='interestRate']"/>',
	                                       '<xsl:value-of select="value[@field='accountTypeId']"/>'
                );
            </xsl:for-each>
        </script>
    </xsl:template>

	<xsl:template name="viewHtml" mode="view" match="form-data">
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Вид вклада</xsl:with-param>
			<xsl:with-param name="rowValue">
				<span id="product"><xsl:value-of select="contribution"/></span>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта вклада</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Срок вклада</xsl:with-param>
			<xsl:with-param name="rowValue">
				<span id="period"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма вклада</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="amount"/>&nbsp;
				<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Списать со счета</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fromAccount"/>&nbsp;[<xsl:value-of select="fromAccountType"/>]&nbsp;<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Действие по окончании срока вклада</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="radio" select="radio"/>
					<xsl:if test="$radio = 'false'">
					Автоматически пролонгировать
					</xsl:if>
                    <xsl:if test="$radio = 'true'">
					Перевести на счет
					&nbsp;<xsl:value-of select="toAccount"/>&nbsp;[<xsl:value-of select="toAccountType"/>]&nbsp;<xsl:value-of select="currency"/>
					</xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата визита в отделение банка</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="vDate" select="visitDate"/>
					<xsl:if test="string-length($vDate) > 0">
						&nbsp;<xsl:value-of select="substring(visitDate,9,2)"/>.<xsl:value-of select="substring(visitDate,6,2)"/>.<xsl:value-of select="substring(visitDate,1,4)"/>&nbsp;
				    </xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Офис банка</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="office"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fullName"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Гражданство</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="citizenship"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Контактный телефон</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="phone"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">E-mail</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="email"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус документа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<div id="state">
					<span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
						<xsl:call-template name="state2text">
							<xsl:with-param name="code">
								<xsl:value-of select="state"/>
							</xsl:with-param>
						</xsl:call-template>
					</span>
				</div>
			</xsl:with-param>
		</xsl:call-template>
		<!--</tbody>-->
		<script type="text/javascript">
			setValue('period', formatPeriod('<xsl:value-of select="period"/>'));
		</script>
	<!--</table>-->
	<xsl:if test="state='ACCEPTED' and $webRoot != '/PhizIA'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Ваша заявка одобрена банком. Вам необходимо прийти в отделение банка
			("<xsl:value-of select="office"/>")
			для завершения процедуры открытия вклада
		</div>
	</xsl:if>
	<xsl:if test="state='REFUSED'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа: <xsl:value-of select="refusingReason"/>
		</div>
	</xsl:if>
</xsl:template>

	<xsl:template name="standartRow">

	<xsl:param name="id"/>
	<xsl:param name="required" select="'true'"/>
	<xsl:param name="rowName"/>
	<xsl:param name="rowValue"/>

	<tr>
		<td class="{$styleClass}" style="{$styleSpecial}" id="{$id}">
				<xsl:copy-of select="$rowName"/>
			<xsl:if test="$required = 'true' and $mode = 'edit'">
				<span id="asterisk_{$id}" class="asterisk">*</span>
			</xsl:if>
		</td>
		<td>
			<xsl:copy-of select="$rowValue"/>
		</td>
	</tr>

	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='SENDED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='ACCEPTED'">Одобрен</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="init-values">
	<xsl:param name="form-data"/>
	<xsl:choose>
		<xsl:when test="$form-data">
			<xsl:for-each select="$form-data/*">
				<xsl:if test="string-length(text()) > 0">
			setInitValue('<xsl:value-of select="name()"/>', '<xsl:value-of select="text()"/>');
				</xsl:if>
			</xsl:for-each>
		</xsl:when>
	</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\'"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="xsl:choose" x="64" y="147"/><block path="xsl:choose/=[0]" x="68" y="230"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template" x="233" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[1]" x="193" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates" x="153" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[1]" x="113" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[2]" x="73" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[2]" x="33" y="0"/><block path="xsl:choose/=[1]" x="177" y="205"/><block path="xsl:choose/xsl:when[1]/xsl:apply&#x2D;templates" x="249" y="30"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
--><!-- Stylesheet edited using Stylus Studio - (c) 2004-2005. Progress Software Corporation. All rights reserved. -->