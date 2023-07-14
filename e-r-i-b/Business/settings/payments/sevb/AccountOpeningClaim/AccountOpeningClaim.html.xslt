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
    <xsl:variable name="accountTypes" select="document('accountTypes')/entity-list/entity"/>
    <xsl:template match="/">
	<xsl:choose>
		<xsl:when test="$mode = 'edit'">
			<div style="overflow:hidden">
				<xsl:variable name="product" select="document(concat($data-path,'products.xml'))"/>
				<xsl:call-template name="initializationScript"/>
				<xsl:variable name="accounts" select="document(concat($data-path,'active-debit-accounts.xml'))"/>

				<xsl:apply-templates select="$product" mode="product"/>
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
		<xsl:call-template name="commonScript"/>
		<xsl:call-template name="editHtml"/>
		<xsl:call-template name="editInitControls"/>
		<xsl:call-template name="editInitValues"/>
	</xsl:template>

	<xsl:template name="editHtml" mode="edit" match="form-data">
		<xsl:variable name="departments" select="document('departments.xml')/entity-list"/>
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Вид вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="product" name="product" onchange="refreshProductInfo();refreshTexts();"></select>
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
				<xsl:with-param name="rowName">Сумма вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="amount" value="{amount}" name="amount"/>&nbsp;<span id="currency"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Минимальная сумма вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="minAmount" name="minAmount" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Списать со счета</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="fromAccount" name="fromAccount" onchange="setFromAccType();showHideOperationCode();"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Действие по окончании срока вклада</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="hidden" id="actionType" name="actionType" value="{actionType}"/>
					&nbsp;<input type="radio" name="radiob" id="radio1" checked="true" onClick="setRadio(1)"/>Автоматически пролонгировать<br />
					&nbsp;<input type="radio" name="radiob" id="radio2" onClick="setRadio(2)"/>Перевести на счет <select id="toAccount" name="toAccount" disabled="true" onchange="setToAccType()"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">operationCodeRow</xsl:with-param>
				<xsl:with-param name="rowName">Код валютной операции</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="ground" size="24" value="{ground}" name="ground" />&nbsp;
					<input type="button" class="buttWhite" style="height:18px;width:18;"
					       onclick="javascript:openSelectOperationTypeWindow();" value="..."/>
				</xsl:with-param>
			</xsl:call-template>

		<input type="hidden" id="toAccountType" name="toAccountType"/>
		<input type="hidden" id="fromAccountType" name="fromAccountType"/>
		<input type="hidden" id="accountType" name="accountType"/>
		<input type="hidden" id="radio" name="radio"/>

			<script type="text/javascript">
			document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';

			<![CDATA[

				function show(elem)
				{
					document.getElementById(elem).style.display = "";
				}

				function hide(elem)
				{
					document.getElementById(elem).style.display = "none";
				}

				function clearMasks(event)
				{
					clearInputTemplate("visitDate", DATE_TEMPLATE);
				}

				function openSelectDocument()
				{
					window.open(document.webRootPrivate + '/templates/documents/list.do?action=start',
					'TemplatesDocuments', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
				}

				function openSelectOperationTypeWindow()
				{
					var val = getElementValue("ground");
					if (val.length > 0)
						 window.open('../operationCodes.do?' + "fltrCode=" + val.substring(3,8), 'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
					else
					window.open('../operationCodes.do', 'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
				}

				function setOperationCodeInfo(operationCodeInfo)
				{
					setElement('ground', "{VO" + operationCodeInfo["operationCode"] + "}");
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
						setValue("minAmount", tuple.minAmount);
					}
					else return;

					setValue("accountType", tuple != null ? tuple.accountType : "");

					refreshAccounts(currency, "toAccount") ;
					refreshAccounts(currency, "fromAccount");
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
					updateSelect(accountSelect, createOptions(accounts, accountsOptionCreator, "Нет доступных счетов"));
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
											   "нет"));
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
							"нет"
						)
					);
				}

				function setRadio(ch)
				{
					var tAcc = document.getElementById('toAccount');
					if (ch == 1) {
						tAcc.disabled = true;
						setValue("radio", "true");
						setValue("actionType", "autorenewal");
					} else {
						tAcc.disabled = false;
						setToAccType();
						setValue("radio", "false");
						setValue("actionType", "notautorenewal");
					}
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

				function showHideOperationCode()
				{
					var isRes = '<xsl:value-of select="document('currentPerson.xml')/entity-list/entity/field[@name='isResident']"/>';
					if(isRes == 'false')
					{
						show('operationCodeRow');
					}
					else
					{
						hide('operationCodeRow');
					}
				}

				function isResident(account){
					return (account.substring(0,5)=="40807"||account.substring(0,5)=="40820"||account.substring(0,3)=="426");
				}
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
				if (elem != null)
				{
					if (elem.value != null)
						elem.value = value;
					else if(elem.innerHTML != null)
						elem.innerHTML = value;
			
					if (elem.type == "checkbox")
					{
						var boolVal = false;
						if (value == "true")
							boolVal = true;
						elem.checked = boolVal;
					}
				}
			}

			setInitValue("actionType", "autorenewal");
			setInitValue("currency", '<xsl:value-of select="$formData/*[name()='currency']/text()"/>');
			refreshProductInfo();
			refreshTexts();
			refreshPeriods();
			setFromAccType();
			setToAccType();
			setValue("radio", "true");
			showHideOperationCode();
			updateAction();

		</script>
	</xsl:template>

	<xsl:template name="editInitControls">
		<script type="text/javascript">
		<![CDATA[
		function isEmptyString(value)
		{
			return value == null || value == "";
		}

		function createOptions(array, func, nullOptionText)
		{
			var options = new Array();
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

		function updateAction()
		{
			var elem = document.getElementById("actionType");
			var radio = document.getElementById("radio1");
			if (elem.value == "notautorenewal")
			{
			    radio = document.getElementById("radio2");
			    document.getElementById('toAccount').disabled = false;
			}
			radio.checked = true;
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
		

		updateSelect("product", createOptions(depositProducts, function(value)
															   {
																	var option = new Option(value.name, value.id);
																	option.productObject = value;
																	return option;
															   },
															   "Нет доступных видов вкладов"));
		updateSelect("fromAccount", createOptions(fromAccounts,
												function(account)
												{
													var option = new Option(account.number + ' [' + account.type + '] ' + account.amount.toFixed(2) + ' ' + account.currency, account.number);
													option.account = account;
													return option;
												},
												"Нет доступных счетов"));

		updateSelect("toAccount", createOptions(toAccounts,
												function(account)
												{
													var option = new Option(account.number + ' [' + account.type + '] ' + account.amount.toFixed(2) + ' ' + account.currency , account.number);
													option.account = account;
													return option;
												},
												"Нет доступных счетов"));
																											   
		]]>
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
				tuple.minAmount = minAmount;
				tuple.currency  = currency;
				tuple.period    = period;
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

	<xsl:template name="initProductsScript" match="product" mode="product">
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

	<xsl:template name="viewHtml" mode="view" match="form-data">
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
				<span id="period"><xsl:value-of select="period"/></span>
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
					<xsl:if test="$radio = 'true'">
					Автоматически пролонгировать
					</xsl:if>
                    <xsl:if test="$radio = 'false'">
					Перевести на счет
					&nbsp;<xsl:value-of select="toAccount"/>&nbsp;[<xsl:value-of select="toAccountType"/>]&nbsp;<xsl:value-of select="currency"/>
					</xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:if test="ground != ''">
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Код валютной операции</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="ground"/>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">commissionBlock</xsl:with-param>
			<xsl:with-param name="rowName">Сумма комиссии</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="commission"/>
				<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус заявки</xsl:with-param>
			<xsl:with-param name="rowValue">
				<div id="state">
					<a onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" style="text-decoration:underline">
						<xsl:call-template name="state2text">
							<xsl:with-param name="code">
								<xsl:value-of select="state"/>
							</xsl:with-param>
						</xsl:call-template>
					</a>
				</div>
			</xsl:with-param>
		</xsl:call-template>

		<script type="text/javascript">

			function setValue(elementId, value)
			{
				var elem = document.getElementById(elementId);

				if(elem.value != null)
					elem.value = value;
				else if(elem.innerHTML != null)
					elem.innerHTML = value;
			}

			setValue('period', formatPeriod('<xsl:value-of select="period"/>'));
		</script>

		<xsl:if test="state='A' and $webRoot != '/PhizIA'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Ваша заявка одобрена банком. Вам необходимо прийти в отделение банка
			("<xsl:value-of select="office"/>")
			для завершения процедуры открытия счета
		</div>
		</xsl:if>
			<xsl:if test="state='E'">
			<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
				Причина отказа: <xsl:value-of select="refusingReason"/>
			</div>
		</xsl:if>
		 <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="admissionDate"/>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="standartRow">
		<xsl:param name="td1Id"/>
		<xsl:param name="td2Id"/>
		<xsl:param name="rowId"/>
		<xsl:param name="required" select="'false'"/>
		<xsl:param name="rowName"/>
		<xsl:param name="rowValue"/>
		<tr  id="{$rowId}">
			<td class="{$styleClass}" style="{$styleSpecial}" id="{$td1Id}">
					<xsl:copy-of select="$rowName"/>
				<xsl:if test="$required = 'true' and $mode = 'edit'">
					<span id="asterisk_{$td1Id}" class="asterisk">*</span>
				</xsl:if>
			</td>
			<td id="{$td2Id}">
				<xsl:copy-of select="$rowValue"/>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="titleRow">
		 <xsl:param name="rowName"/>
		 <tr>
			  <td colspan="2" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
		 </tr>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введена</xsl:when>
			<xsl:when test="$code='W'">Принята</xsl:when>
			<xsl:when test="$code='S'">Исполнена</xsl:when>
			<xsl:when test="$code='D'">Отказана</xsl:when>
			<xsl:when test="$code='E'">Отказана</xsl:when>
			<xsl:when test="$code='F'">Приостановлена</xsl:when>
			<xsl:when test="$code='V'">Отозвана</xsl:when>			
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
