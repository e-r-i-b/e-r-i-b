<?xml version='1.0' encoding='windows-1251'?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>

	<xsl:variable name="styleClass" select="'label120 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

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
	<input id="currency" type="hidden" name="currency"/>
	<input id="amount" type="hidden" name="amount"/>
	<input id="accountType" type="hidden" name="accountType"/>
	<input id="toAccountType" type="hidden" name="toAccountType"/>
	<input id="receiverType" name="receiverType" type="hidden">
		<xsl:attribute name="value">
			<xsl:value-of select="receiverType"/>
		</xsl:attribute>
	</input>
            <xsl:call-template name="standartRow">
			    <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			    <xsl:with-param name="required" select="'false'"/>
			    <xsl:with-param name="rowValue">
			        <xsl:value-of select="documentNumber"/>
			        <input id="documentNumber" name="documentNumber" type="hidden"/>
			    </xsl:with-param>
		    </xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Закрыть</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select onchange="this.value=0;window.location='{$webRoot}/private/claims/claim.do?form=DepositClosingClaim';">
						<option value="0" selected="true">Счет</option>
						<option>Вклад</option>
					</select>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Счет для закрытия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="closingAccount" select="account"/>
					<xsl:if test="$personAvailable">
						<select id="account" name="account" onchange="javascript:updateCurrency();refreshToAccount();refreshAnotherBank()">
							<xsl:for-each select="document('active-accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$closingAccount = ./@key">
										<xsl:attribute name="selected">true</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="./@key"/>
									&nbsp;
									[
									<xsl:value-of select="./field[@name='type']"/>
									]
									<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
									&nbsp;
									<xsl:value-of select="./field[@name='currencyCode']"/>
								</option>
							</xsl:for-each>
						</select>
					</xsl:if>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Номер договора</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="contractNumber" size="25" name="contractNumber" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Дата открытия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="openingDate" size="10" name="openingDate" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Дата закрытия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="closingDate" size="10" name="closingDate" readonly="true" select = "closingDate"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Остаток средств перевести на счет</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:if test="$personAvailable">
						<select id="toAccount" name="toAccount" onchange="refreshAnotherBank();">
						</select>
					</xsl:if>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="titleRow">
		        <xsl:with-param name="id">rurPaymantLabel</xsl:with-param>
				<xsl:with-param name="rowName"><br/>Перевод рублей РФ</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="titleRow">
		        <xsl:with-param name="id">curPaymantLabel</xsl:with-param>
				<xsl:with-param name="rowName">Перевод иностранной валюты</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="titleRow">
		        <xsl:with-param name="rowId">reciverPropertiesLabel</xsl:with-param>
				<xsl:with-param name="rowName">Реквизиты получателя платежа</xsl:with-param>
			</xsl:call-template>
			<xsl:variable name="receiverType" select="receiverType"/>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowId">typePerson</xsl:with-param>
				<xsl:with-param name="rowName">&nbsp;</xsl:with-param>
				<xsl:with-param name="rowValue">
					<table cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td width="50%">
								<input type="radio" id="typePersonJur" name="typePerson"
								       onclick="selectTypePerson('Jur')">Юридическое лицо</input><br/>
							</td>
							<td>
								<input type="radio" id="typePersonPh" name="typePerson" onclick="selectTypePerson('Ph')">
									<xsl:if test="$receiverType = 'Ph'">
										<xsl:attribute name="checked">true</xsl:attribute>
									</xsl:if>
									Физическое лицо</input>
							</td>
						</tr>
					</table>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverNameBlock</xsl:with-param>
				<xsl:with-param name="td1Id">receiverTypeName</xsl:with-param>
				<xsl:with-param name="rowName">Название организации</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverName" size="50" name="receiverName"  value="{receiverName}"/>
					<input id="selectReceiverButton" class="buttWhite smButt" onclick="javascript:openSelectReceiver();" value="..." type="button"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverCC</xsl:with-param>
				<xsl:with-param name="rowName">Код страны получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverCountryCode" size="50" name="receiverCountryCode" value="{receiverCountryCode}"/>
					<input class="buttWhite smButt"
					       onclick="javascript:openCountryDictionary(setReceiverCountryInfo,getFieldValue('receiverCountryCode'));" value="..."/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">INN</xsl:with-param>
				<xsl:with-param name="rowName">ИНН получателя платежа</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverINN" maxLength="12" size="24" value="{receiverINN}" name="receiverINN"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">KPP</xsl:with-param>
				<xsl:with-param name="td1Id">receiverKPPIdent</xsl:with-param>
				<xsl:with-param name="rowName">КПП получателя платежа</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverKPP" maxLength="9" size="24" value="{receiverKPP}" name="receiverKPP"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">Acc</xsl:with-param>
				<xsl:with-param name="rowName">Счет получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverAccount" maxLength="20" size="24" value="{receiverAccount}" name="receiverAccount"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverCityBlock</xsl:with-param>
				<xsl:with-param name="rowName">Город</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverCity" size="50" name="receiverCity" value="{receiverCity}"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverAddressBlock</xsl:with-param>
				<xsl:with-param name="rowName">Адрес получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverAddress" size="50" name="receiverAddress" value="{receiverAddress}"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverAddressBlock</xsl:with-param>
				<xsl:with-param name="rowName">Адрес получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverAddress" size="50" name="receiverAddress" value="{receiverAddress}"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="titleRow">
		        <xsl:with-param name="rowId">bankPropertiesLabel</xsl:with-param>
				<xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">bankName</xsl:with-param>
		        <xsl:with-param name="td1Id">receiverBankTypeName</xsl:with-param>
				<xsl:with-param name="rowName">Наименование</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverBankName" size="50" value="{receiverBankName}" name="receiverBankName"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">BIC</xsl:with-param>
				<xsl:with-param name="rowName">БИК</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverBIC" maxLength="9" size="14" value="{receiverBIC}" name="receiverBIC"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('receiverBankName'),getFieldValue('receiverBIC'));" value="..."/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverBankCountryCodeBlock</xsl:with-param>
				<xsl:with-param name="rowName">Код страны</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverBankCountryCode" size="50" name="receiverBankCountryCode" value="{receiverBankCountryCode}"/>
					<input class="buttWhite smButt"
					       onclick="javascript:openCountryDictionary(setReceiverBankCountryInfo,getFieldValue('receiverBankCountryCode'));" value="..."/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverBankCityBlock</xsl:with-param>
				<xsl:with-param name="rowName">Город банка получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverBankCity" size="50" name="receiverBankCity" value="{receiverBankCity}"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">receiverBankAddressBlock</xsl:with-param>
				<xsl:with-param name="rowName">Адрес банка получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverBankAddress" size="50" name="receiverBankAddress" value="{receiverBankAddress}"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">CorAccount</xsl:with-param>
				<xsl:with-param name="rowName">Кор. счет банка получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverCorAccount" maxLength="20" size="24" value="{receiverCorAccount}" name="receiverCorAccount"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">ndsBlock</xsl:with-param>
				<xsl:with-param name="rowName">Размер НДС</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="nds" name="nds" type="text"  size="4" onchange="calculateNDS();"/>%
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">groundBlock</xsl:with-param>
				<xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
				<xsl:with-param name="rowValue">
					<textarea id="ground" cols="50" rows="2" name="ground" type="text" value="{ground}"  readonly="true" style="overflow: hidden;"></textarea>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowId">paymentConditionsBlock</xsl:with-param>
				<xsl:with-param name="rowName">>Условия перевода</xsl:with-param>
				<xsl:with-param name="rowValue">
					 <xsl:variable name="paymentConditionsV" select="paymentConditions"/>
			        <select name="paymentConditions">
				        <option>
					        <xsl:attribute name="value">0</xsl:attribute>
					        <xsl:if test="$paymentConditionsV = '0'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        текущим днем
				        </option>
				        <option>
					        <xsl:attribute name="value">1</xsl:attribute>
					        <xsl:if test="$paymentConditionsV = '1'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        следующим днем
				        </option>
				        <option>
					        <xsl:attribute name="value">2</xsl:attribute>
					        <xsl:if test="$paymentConditionsV = '2'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        через 2 дня
				        </option>
			        </select>
				</xsl:with-param>
			</xsl:call-template>
		
	<script type="text/javascript">			

		var allAccount = new Array();
		var nds=document.getElementById('nds');
		function show(elem)
		{
			document.getElementById(elem).style.display = "";
		}

		function hide(elem)
		{
			document.getElementById(elem).style.display = "none";
		}		

		function account(){
		}

		function loadAllAccount()
		{
			var i=0;
			<xsl:variable name="allAccounts" select="document('active-accounts-info.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
	            <xsl:variable name="date" select="field[@name='openingDate']/text()"/>
				allAccount[i] = new account();
				allAccount[i].value = '<xsl:value-of select="./@key"/>';
				allAccount[i].text = '<xsl:value-of select="./@key"/>&nbsp;[&nbsp;<xsl:value-of select="./field[@name='type']"/>&nbsp;]&nbsp;<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/> &nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>';
				allAccount[i].accDate = '<xsl:value-of select="substring($date,9,2)"/>.<xsl:value-of select="substring($date,6,2)"/>.<xsl:value-of select="substring($date,1,4)"/>'
				allAccount[i].contractNumber = '<xsl:value-of select="./field[@name='contractNumber']"/>';
				allAccount[i].amount = '<xsl:value-of select="./field[@name='amountDecimal']"/>';
	            allAccount[i].accountType = '<xsl:value-of select="./field[@name='type']"/>';
	            allAccount[i].currency = '<xsl:value-of select="./field[@name='currencyCode']"/>';
				i=i+1;
            </xsl:for-each>
		}

		function createAccount()
		{
			var account = document.getElementById("account");
			if(allAccount.length == 0)
			    account.options[0] = new Option("нет доступных счетов","noAccount");
		}

		function refreshToAccount()
		{
			var currency = document.getElementById('currency');
			var account = document.getElementById("account");
			var toAccount = document.getElementById('toAccount');			  	 
			toAccount.length = 0;
			var j = 1;
			var num = 0;
			for (var i=0; i &lt; allAccount.length; i++)
			{
				if((currency.value == allAccount[i].currency)  &amp;&amp;  (account.value != allAccount[i].value))
				{					
					toAccount.options[j] = new Option(allAccount[i].text,allAccount[i].value);
					j=j+1;
				}
				if(account.value == allAccount[i].value)
				{
					num = i;
				}
			}
			if(allAccount.length == 0)
			    toAccount.options[0] = new Option("нет доступных счетов","noAccount");
			else
			{
                toAccount.options[0] = new Option("Выберите счет зачисления", "");
				toAccount.options[j] = new Option(" в другом банке","AnotherBank");
				setElement("contractNumber", allAccount[num].contractNumber);
				setElement("openingDate", allAccount[num].accDate);
				setElement("amount", allAccount[num].amount);
				setElement("accountType", allAccount[num].accountType);
				setElement("toAccount", '<xsl:value-of select="toAccount"/>');
				if(toAccount.value == '')
					setElement("toAccount", toAccount[0].value);
			}
		}

		<![CDATA[

			function updateCurrency()
			{
				var account = document.getElementById('account');
				for (var i=0; i < allAccount.length; i++)
				{
					if(account.value == allAccount[i].value)
					{
						if(document.getElementById('currency').value != allAccount[i].currency)
						{
							setElement("currency", allAccount[i].currency);
						}
					}
				}
			}

        	function updateToAccountType()
        	{
				var toAccount = document.getElementById('toAccount');
				for (var i=0; i < allAccount.length; i++)
				{
					if(toAccount.value == allAccount[i].value)
					{
						document.getElementById('toAccountType').value = allAccount[i].accountType;
					}
				}
        	}

			function refreshAnotherBank()
			{			
			var currency = document.getElementById('currency');
			var toAccount = document.getElementById('toAccount');
			if (toAccount.value == 'AnotherBank' && currency.value == 'RUB')
			{
				show('rurPaymantLabel');
				hide('curPaymantLabel');
				show('reciverPropertiesLabel');
				show('typePerson');
				show('receiverNameBlock');
				show('selectReceiverButton');
				hide('receiverCC');
				show('INN');
				show('KPP');
				show('Acc');
				hide('receiverCityBlock');
				hide('receiverAddressBlock');
				show('bankPropertiesLabel');
				show('bankName');
				show('BIC');
				show('CorAccount');
				hide('receiverBankCountryCodeBlock');
				hide('receiverBankCityBlock');
				hide('receiverBankAddressBlock');
				show('ndsBlock');
				show('groundBlock');
				hide('paymentConditionsBlock');
				checkSelectTypePerson();
				
			}else if (toAccount.value == 'AnotherBank' && currency.value !='RUB')
			{
				hide('rurPaymantLabel');
				show('curPaymantLabel');
				show('reciverPropertiesLabel');
				hide('typePerson');
				show('receiverNameBlock');
				hide('selectReceiverButton');
				show('receiverCC');
				hide('INN');
				hide('KPP');
				show('Acc');
				show('receiverCityBlock');
				show('receiverAddressBlock');
				show('bankPropertiesLabel');
				show('bankName');
				hide('BIC');
				show('CorAccount');
				show('receiverBankCountryCodeBlock');
				show('receiverBankCityBlock');
				show('receiverBankAddressBlock');
				hide('ndsBlock');
				show('groundBlock');
				show('paymentConditionsBlock');
				selectTypePerson("");
			}
			else 
			{
				hide('rurPaymantLabel');
				hide('curPaymantLabel');
				hide('reciverPropertiesLabel');
				hide('typePerson');
				hide('receiverNameBlock');
				hide('receiverCC');
				hide('INN');
				hide('KPP');
				hide('Acc');
				hide('receiverCityBlock');
				hide('receiverAddressBlock');
				hide('bankPropertiesLabel');
				hide('bankName');
				hide('BIC');
				hide('CorAccount');
				hide('receiverBankCountryCodeBlock');
				hide('receiverBankCityBlock');
				hide('receiverBankAddressBlock');
				hide('paymentConditionsBlock');
				hide('ndsBlock');
				hide('groundBlock');
				updateToAccountType();
			}
		}
         
		]]>
			
			
			function setReceiverInfo(Info)
			{
				document.getElementById('typePersonJur').checked = (Info["type"]=='J');
				document.getElementById('typePersonPh').checked = (Info["type"]=='I');
				if(Info["type"]=='I')selectTypePerson('Ph');
				if(Info["type"]=='J')selectTypePerson('Jur');
				setElement("receiverName", Info["receiverName"]);
				setElement("receiverINN", Info["receiverINN"]);
				if (document.getElementById("typePersonJur").checked)
	            {
	                setElement("receiverKPP", Info["receiverKPP"]);
				}
	            else setElement("receiverKPP", "");
			    setElement("receiverAccount", Info["receiverAccount"]);
				setElement("receiverBIC", Info["BIC"]);
				setElement("receiverBankName", Info["name"]);
				setElement("receiverCorAccount", Info["account"]);
			}

			function setBankInfo(Info)
			{
				setElement("receiverBIC", Info["BIC"]);
				setElement("receiverBankName", Info["name"]);
				setElement("receiverCorAccount", Info["account"]);
			}
			
			function setReceiverBankInfo(Info)
	        {
	         	setElement("receiverBankName",Info["name"]);
             	setElement("receiverBankCity",Info["place"]);
	         	setElement("receiverBankCountryCode",Info["country"]);
 	         	setElement("receiverBankAddress",Info["address"]);
	        }

			function setReceiverCountryInfo(Info)
	        {
	        	setElement("receiverCountryCode",Info["code"]);
	        }

	        function setReceiverBankCountryInfo(Info)
	        {
	        	setElement("receiverBankCountryCode",Info["code"]);
	        }

			function openSelectReceiver()
			{
				window.open(document.webRootPrivate + '../../../receivers/list.do?action=getReceiverInfo&amp;kind=PJ',
		        	'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

			function calculateNDS()
			{
				var amount = document.getElementById("amount").value;
				var ndsFieldValue = document.getElementById("nds").value;
				if (document.getElementById("typePersonJur").checked)
				{
					var nds = amount - amount / (1 + ndsFieldValue / 100);
		            <!--var nds = document.getElementById("nds").value * amount / 100;-->
		            document.getElementById("ground").value = "Перевод средств при закрытии счета. В том числе НДС "+ ndsFieldValue +"% : " + nds.toFixed(2) + " руб.";
				}
				else
				{
	        		document.getElementById("ground").value = "Перевод средств при закрытии счёта. НДС не облагается";
        		}
    		}



			function selectTypePerson(type)
			{
				var currency   = document.getElementById("currency");
				if (currency.value != 'RUB')
				{
					document.getElementById("receiverTypeName").innerHTML = "Название получателя платежа&lt;span class='asterisk'&gt;*&lt;/span&gt;";
				}
				else if (type == "Jur")
				{	
					document.getElementById("receiverTypeName").innerHTML = "Название организации&lt;span class='asterisk'&gt;*&lt;/span&gt;";
					document.getElementById("receiverKPPIdent").innerHTML = "КПП получателя платежа&lt;span class='asterisk'&gt;*&lt;/span&gt;";
					tmp = document.getElementById("receiverINN").value;					
					tmp = tmp.length > 10?tmp.substring(0,10):tmp;					
					document.getElementById("receiverINN").maxLength = "10";					
	        		document.getElementById("receiverINN").value = tmp;					
	        		document.getElementById("receiverKPP").disabled = false;
					document.getElementById("nds").disabled = false;
					document.getElementById("receiverType").value = "Jur";
					
				}
				else if (type == "Ph")
				{
					document.getElementById("receiverTypeName").innerHTML = "Ф.И.О.&lt;span class='asterisk'&gt;*&lt;/span&gt;";
		        	document.getElementById("receiverKPPIdent").innerHTML = "КПП получателя платежа&nbsp;";
		        	document.getElementById("receiverINN").maxLength = "12";
	    	    	document.getElementById("receiverKPP").disabled = true;
					document.getElementById("nds").disabled = true;
					document.getElementById("receiverType").value = "Ph";
				}
				calculateNDS();
			}
		
			function checkSelectTypePerson()
			{
				if(document.getElementById("receiverType").value == "Ph")
	    		{
					document.getElementById("typePersonPh").checked = true;
	        		selectTypePerson("Ph");
	    		}
	    		else
	    		{
	        		document.getElementById("typePersonJur").checked = true;
	        		selectTypePerson("Jur");
	    		}
			}

		function isFieldValue()
		{
			var INN = document.getElementById("receiverINN").value;
			var KPP = document.getElementById("receiverKPP").value;
			if (document.getElementById("typePersonJur").checked &amp;&amp; document.getElementById("toAccount").value == 'AnotherBank')
			{
				if (INN == "" &amp;&amp; KPP == "")
				{
					alert("Введите значение в поле ИНН получателя платежа\n" +
					"Введите значение в поле КПП получателя платежа");
					return false;
				}
				else if (INN == "")
				{
					alert("Введите значение в поле ИНН получателя платежа");
					return false;
				}
				else if (KPP == "")
				{
					alert("Введите значение в поле КПП получателя платежа");
					return false;
				}
	            else if (INN.length != 10)
				{
					alert("Поле ИНН должно состоять из 10 цифр");
					return false;
				}
		        else if (KPP.length != 9)
				{
					alert("Поле КПП должно состоять из 9 цифр");
					return false;
				}
				else return true;
			}
			else if (document.getElementById("typePersonPh").checked &amp;&amp; document.getElementById("toAccount").value == 'AnotherBank')
		    {
		        if (INN == "")
				{
					alert("Введите значение в поле ИНН получателя платежа");
					return false;
				}
		        else if (INN.length != 12)
				{
					alert("Поле ИНН должно состоять из 12 цифр");
					return false;
				}
		        else return true;
	        }
			else if (document.getElementById("toAccount").value != 'AnotherBank')
				return true;
	        else return true;
		}

		function init()
		{
			loadAllAccount();
			createAccount();
			updateCurrency();
			refreshToAccount();
			selectTypePerson("Ph");
			updateToAccountType();
			refreshAnotherBank();
			setElement("receiverType", '<xsl:value-of select="receiverType"/>'); 
			setElement("closingDate", '<xsl:value-of select="closingDate"/>');
			setElement("nds", '<xsl:value-of select="nds"/>');
			if(nds.value == '')
				setElement("nds", 18);
			checkSelectTypePerson();
			var btnSave = findCommandButton("button.save");
			if (findCommandButton("button.save"))
				btnSave.setValidationFunction(isFieldValue);
		}   		    
            init();


		</script>
</xsl:template>

<xsl:template match="/form-data" mode="view">
	<input id="currency" name="currency" type="hidden">
		<xsl:attribute name="value">
			<xsl:value-of select="currency"/>
        </xsl:attribute>
	</input>
	<input id="toAcc" name="toAcc" type="hidden">
		<xsl:attribute name="value">
			<xsl:value-of select="toAccount"/>
        </xsl:attribute>
	</input>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет для закрытия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="account"/>&nbsp;[&nbsp;
				<xsl:value-of select="accountType"/>&nbsp;]&nbsp;
				<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер договора</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="contractNumber"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата открытия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="openingDate" select="openingDate"/>
				<xsl:value-of select="openingDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата закрытия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="closingDate" select="closingDate"/>
				<xsl:value-of select="closingDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:variable name="toAccount" select="toAccount"/>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Перевести на счёт</xsl:with-param>
			<xsl:with-param name="td2Id">toAccount</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test = "$toAccount = 'AnotherBank'">
					<xsl:attribute name="value">
						<xsl:value-of select="toAccount"/>
        			</xsl:attribute>
					в другом банке
				</xsl:if>
				<xsl:if test="$toAccount != 'AnotherBank'">
					<xsl:value-of select="toAccount"/>&nbsp;[&nbsp;
					<xsl:value-of select="toAccountType"/>&nbsp;]&nbsp;
					<xsl:value-of select="currency"/>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="titleRow">
	        <xsl:with-param name="rowId">reciverProperties</xsl:with-param>
	        <xsl:with-param name="id">reciverPropertiesLabel</xsl:with-param>
			<xsl:with-param name="rowName">Реквизиты получателя</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverNameBlock</xsl:with-param>
			<xsl:with-param name="rowName">Наименование</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverName"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverINNBlock</xsl:with-param>
			<xsl:with-param name="rowName">ИНН получателя платежа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverINN"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverKPPBlock</xsl:with-param>
			<xsl:with-param name="rowName">КПП получателя платежа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverKPP"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverCountryCodeBlock</xsl:with-param>
			<xsl:with-param name="rowName">Код страны получателя</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverCountryCode"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverAccountBlock</xsl:with-param>
			<xsl:with-param name="rowName">Счет получателя</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverAccount"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverCityBlock</xsl:with-param>
			<xsl:with-param name="rowName">Город</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverCity"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverAddressBlock</xsl:with-param>
			<xsl:with-param name="rowName">>Адрес получателя</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverAddress"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverAddressBlock</xsl:with-param>
			<xsl:with-param name="rowName">Адрес получателя</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverAddress"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="titleRow">
	        <xsl:with-param name="rowId">reciverBankProperties</xsl:with-param>
			<xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverBankNameBlock</xsl:with-param>
			<xsl:with-param name="rowName">Наименование</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverBankName"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverBICBlock</xsl:with-param>
			<xsl:with-param name="rowName">БИК</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverBIC"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverBankCountryCodeBlock</xsl:with-param>
			<xsl:with-param name="rowName">Код страны</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverBankCountryCode"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverBankCityBlock</xsl:with-param>
			<xsl:with-param name="rowName">Город банка получателя</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverBankCity"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverBankAddress</xsl:with-param>
			<xsl:with-param name="rowName">Адрес банка получателя</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverBankAddress"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">receiverCorAccountBlock</xsl:with-param>
			<xsl:with-param name="rowName">Кор. счёт</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="receiverCorAccount"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">ndsBlock</xsl:with-param>
			<xsl:with-param name="rowName">Размер НДС</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="nds"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">groundBlock</xsl:with-param>
			<xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="ground"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">paymentConditionsBlock</xsl:with-param>
			<xsl:with-param name="rowName">Условия перевода</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="paymentConditionsV" select="paymentConditions"/>
				<xsl:if test="$paymentConditionsV = '0'">
					текущим днем
				</xsl:if>
				<xsl:if test="$paymentConditionsV = '1'">
					следующим днем
				</xsl:if>
				<xsl:if test="$paymentConditionsV = '2'">
					через 2 дня
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId">commissionBlock</xsl:with-param>
			<xsl:with-param name="rowName">Сумма комиссии</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="commissionAmount"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус документа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:with-param>
		</xsl:call-template>

	<script type="text/javascript">
	
		function show(elem)
		{
			document.getElementById(elem).style.display = "";
		}

		function hide(elem)
		{
			document.getElementById(elem).style.display = "none";
		}

		<![CDATA[
		
		function init()
		{	
			var currency = document.getElementById('currency');
			var toAccount = document.getElementById('toAcc');
			var reciverProperties = document.getElementById('reciverProperties');
			if(toAccount.value == 'AnotherBank' && currency.value == 'RUB')
			{
				show('reciverProperties');
				show('receiverNameBlock');
				show('receiverINNBlock');
				show('receiverKPPBlock');
				hide('receiverCountryCodeBlock');
				show('receiverAccountBlock');
				hide('receiverCityBlock');
				hide('receiverAddressBlock');
				show('reciverBankProperties');
				show('receiverBankNameBlock');
				show('receiverBICBlock');
				hide('receiverBankCountryCodeBlock');
				hide('receiverBankCityBlock');
				hide('receiverBankAddress');
				show('receiverCorAccountBlock');
				show('ndsBlock');
				show('groundBlock');
				hide('paymentConditionsBlock');
				show('commissionBlock');
			}
			else if(toAccount.value == 'AnotherBank' && currency.value != 'RUB')
			{
				show('reciverProperties');
				show('receiverNameBlock');
				hide('receiverINNBlock');
				hide('receiverKPPBlock');
				show('receiverCountryCodeBlock');
				show('receiverAccountBlock');
				show('receiverCityBlock');
				show('receiverAddressBlock');
				show('reciverBankProperties');
				show('receiverBankNameBlock');
				hide('receiverBICBlock');
				show('receiverBankCountryCodeBlock');
				show('receiverBankCityBlock');
				show('receiverBankAddress');
				show('receiverCorAccountBlock');
				hide('ndsBlock');
				show('groundBlock');
				show('paymentConditionsBlock');
				show('commissionBlock');
			}
			else
			{
				hide('reciverProperties');
				hide('receiverNameBlock');
				hide('receiverINNBlock');
				hide('receiverKPPBlock');
				hide('receiverCountryCodeBlock');
				hide('receiverAccountBlock');
				hide('receiverCityBlock');
				hide('receiverAddressBlock');
				hide('reciverBankProperties');
				hide('receiverBankNameBlock');
				hide('receiverBICBlock');
				hide('receiverBankCountryCodeBlock');
				hide('receiverBankCityBlock');
				hide('receiverBankAddress');
				hide('receiverCorAccountBlock');
				hide('ndsBlock');
				hide('groundBlock');
				hide('paymentConditionsBlock');
				hide('commissionBlock');
			}
		}
		]]>

		init();

	</script>
	</xsl:template>

	<xsl:template name="standartRow">
		<xsl:param name="td1Id"/>
		<xsl:param name="td2Id"/>
		<xsl:param name="rowId"/>
		<xsl:param name="required" select="'true'"/>
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
		<xsl:param name="id"/>
		<xsl:param name="rowId"/>
		<tr  id="{$rowId}">
			<td colspan="2" id="{$id}" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
		</tr>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\AccountClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->