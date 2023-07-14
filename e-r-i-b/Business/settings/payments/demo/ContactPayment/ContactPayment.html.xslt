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
	<xsl:param name="isTemplate" select="'isTemplate'"/>
	
	<xsl:variable name="styleClass" select="'label120'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
				<xsl:call-template name="initAccountsScript"/>
				<xsl:call-template name="editInitControls"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>


	<xsl:template match="/form-data" mode="edit">
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="amountCurrency" name="amountCurrency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="amountCurrency"/>
			    </xsl:attribute>
		</input>
		<input id="receiverBankCode" name="receiverBankCode" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankCode"/>
	            </xsl:attribute>
		</input>
		<input id="receiverBankName" name="receiverBankName" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankName"/>
	            </xsl:attribute>
		</input>
		<input id="receiverBankPhone" name="receiverBankPhone" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankPhone"/>
	            </xsl:attribute>
		</input>
		<input id="receiverBankCity" name="receiverBankCity" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankCity"/>
	            </xsl:attribute>
		</input>
		<input id="receiverBankAddress" name="receiverBankAddress" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankAddress"/>
	            </xsl:attribute>
		</input>

        <input id="regMask" name="regMask" type="hidden">
                <xsl:attribute name="value">
                    <xsl:value-of select="regMask"/>
                </xsl:attribute>
        </input>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата перевода</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="documentDate" name="documentDate" size="10">
						<xsl:attribute name="value">
							<xsl:value-of select="documentDate"/>
						</xsl:attribute>
					</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта перевода</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="amountCurrencyV" select="amountCurrency"/>
					<select name="сurrencyCodeSelect"
							id = "сurrencyCodeSelect"
					        onchange="javascript:setCurrencyCode()"
							onkeypress="javascript:setCurrencyCode()"
							onkeydown="javascript:setCurrencyCode()"
							onkeyup="javascript:setCurrencyCode()">
						<xsl:if test="$isTemplate = 'true'">
							<option value="">Не задан</option>
						</xsl:if>
						<option>
							<xsl:attribute name="value">RUB</xsl:attribute>
							<xsl:if test="$amountCurrencyV = 'RUB'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Рубль
						</option>
						<option>
							<xsl:attribute name="value">USD</xsl:attribute>
							<xsl:if test="$amountCurrencyV = 'USD'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Доллар США
						</option>
						<option>
							<xsl:attribute name="value">EUR</xsl:attribute>
							<xsl:if test="$amountCurrencyV = 'EUR'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							ЕВРО
						</option>
					</select>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" value="{amount}" size="10"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect">
						 </select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
							<option selected="selected" value="">Счет клиента</option>
						</select>
					</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="titleRow">
			<xsl:with-param name="rowName">Получатель платежа</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Фамилия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="receiverSurName" name="receiverSurName" type="text" value="{receiverSurName}" size="20"/>
				<xsl:if test="$personAvailable">
					<input type="button" class="buttWhite smButt" onclick="javascript:openSelectReceiver();" value="..."/>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Имя</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="receiverFirstName" name="receiverFirstName" type="text" value="{receiverFirstName}" size="20"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Отчество</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="receiverPatrName" name="receiverPatrName" type="text" value="{receiverPatrName}" size="20"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Дата рождения</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="receiverBirthDay" name="receiverBirthday" type="text" value="{receiverBirthday}" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="titleRow">
			<xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Код</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="receiverBankCodeV" name="receiverBankCodeV" type="text" value="{receiverBankCode}" size="6" disabled="true"/>
				<input type="button" class="buttWhite smButt" onclick="javascript:openContactMembersDictionary(setBankInfo,getFieldValue('receiverBankCodeV'));" value="..."/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Банк</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="receiverBankNameV" name="receiverBankNameV" type="text" value="{receiverBankName}" size="60" disabled="true"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Телефон</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="receiverBankPhoneV" name="receiverBankPhoneV" type="text" value="{receiverBankPhone}" size="20" disabled="true"/>
				<input id="receiverBankAddressV" name="receiverBankAddressV" type="hidden" value="{receiverBankAddress}" size="20"/>
				<input id="receiverBankCityV" name="receiverBankCityV" type="hidden" value="{receiverBankCity}" size="20" />
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Дополнительная информация</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="addInformation" name="addInformation" type="text" value="{addInformation}" size="60" maxlength="255"/></xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Комментарий</xsl:with-param>
            <xsl:with-param name="rowValue">
                <textarea id="addComment" name="addComment" cols="65" rows="4" readonly="true">
					   <xsl:value-of select="addComment"/>
			    </textarea>
            </xsl:with-param>
        </xsl:call-template>

		<script type="text/javascript">
			<![CDATA[
			function checkPayment()
			{
			   var addInformation = getElementValue("addInformation");
			   var regMask = getElementValue("regMask");
			   if ((addInformation != "") && (addInformation != "undefined") &&
			       (regMask != "undefined") && (regMask != ""))
			   {
			      var reg = new RegExp(regMask,"i");
                  if (addInformation.match(reg) == null)
                  {
                     var addComment = document.getElementById("addComment");
                     if (addComment != "")
                     {
                        alert (addComment.innerText);
                        return false;
                     }
                  }
			   }
			   return true;
			}
            ]]>
			
			function openSelectReceiver()
			{
			   window.open( document.webRootPrivate + '/contact/receivers/list.do?action=getPaymentReceiverInfo&amp;kind=C',
				'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

			function setReceiverInfo(Info)
			{
			 setElement("receiverSurName",Info["surName"]);
			 setElement("receiverFirstName",Info["firstName"]);
		     setElement("receiverPatrName",Info["patrName"]);
			 if(Info["birthDay"].toString() != '__.__.____')
			    setElement("receiverBirthday",Info["birthDay"]);
			 setElement("receiverBankCodeV",Info["contactBankCode"]);
			 setElement("receiverBankCode",Info["contactBankCode"]);
			 setElement("addInformation",Info["addInfo"]);
 			 setElement("receiverBankName",Info["contactBankName"]);
		     setElement("receiverBankPhone",Info["contactBankPhone"]);
			 setElement("receiverBankCity",Info["contactBankCity"]);
			 setElement("receiverBankAddress",Info["contactBankAddress"]);
			 setElement("receiverBankNameV",Info["contactBankName"]);
		     setElement("receiverBankPhoneV",Info["contactBankPhone"]);
			 setElement("receiverBankAddressV",Info["contactBankAddress"]);
			 setElement("receiverBankCityV",Info["contactBankCity"]);
			}

			function setBankInfo(Info)
			{
			 setElement("receiverBankCode",Info["code"]);
			 setElement("receiverBankName",Info["name"]);
		     setElement("receiverBankPhone",Info["phone"]);
			 setElement("receiverBankCity",Info["city"]);
			 setElement("receiverBankAddress",Info["address"]);
			 setElement("receiverBankCodeV",Info["code"]);
			 setElement("receiverBankNameV",Info["name"]);
		     setElement("receiverBankPhoneV",Info["phone"]);
			 setElement("receiverBankAddressV",Info["address"]);
			 setElement("receiverBankCityV",Info["city"]);
			 setElement("addInformation","");
			 var addComment = getElement("addComment");
			 if (addComment != null)
			    addComment.innerText = Info["comm"];
			  setElement("regMask",Info["req"]);
			}

			function setCurrencyCode()
			{
				var currency = document.getElementById("сurrencyCodeSelect");
				var currencyCodeElem       = document.getElementById("amountCurrency");
				currencyCodeElem.value  = currency.value;
				refreshAccounts(currencyCodeElem.value, "payerAccountSelect") ;
			}


            function refreshAccounts(currency, accountSelect)
            {
			    var accountsOptionCreator= function(account)
				{
				    if (currency != account.currency)
					    return null;
					var value = account.number+"["+account.type+"] "+account.amount.toFixed(2)+ " "+account.currency;
					var option = new Option(value, account.number);
					option.account = account;
					return option;
			   	}
			    updateSelect(accountSelect, createOptions(accounts, accountsOptionCreator, "Нет доступных счетов"));
		    }
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

		function updateSelect(id, options)
		{
		    var select  = document.getElementById(id);
		    select.options.length = 0;
        ]]>
		<xsl:choose>
			<xsl:when test="$isTemplate = 'true'">
			<![CDATA[
				if (document.all("сurrencyCodeSelect").value != "")
				{
					select.options[0] = new Option("Не задан", "");
		            for ( var i = 0 ; i < options.length; i++ )
					{
						select.options[i + 1] = options[i];
					}
				}
				else select.options[0] = new Option("Нет доступных счетов", "");	
			]]>
			</xsl:when>
			<xsl:otherwise>
			<![CDATA[
				for ( var i = 0 ; i < options.length; i++ )
				{
					select.options[i] = options[i];
				}
			]]>
			</xsl:otherwise>
		</xsl:choose>
		<![CDATA[
		}

        updateSelect("payerAccountSelect", createOptions(accounts,
					function(account)
					{
						var currency = document.getElementById("сurrencyCodeSelect");
						var currencyCodeElem   = document.getElementById("amountCurrency");
						currencyCodeElem.value = currency.value;
						if (currencyCodeElem.value != account.currency)
						    return null;
						var option = new Option(account.number + '[' + account.type + '] ' + account.amount.toFixed(2) + ' ' + account.currency , account.number);
						option.account = account;
						return option;
					},
					"Нет доступных счетов"));
		]]>

		</script>
	</xsl:template>
	<xsl:template name="initAccountsScript" match="/form-data" mode="accounts">
		<script type="text/javascript">
			var accounts = new Array(); //1
			<xsl:for-each select="document('active-accounts.xml')/entity-list/*">
			var account = new Object()
			account.number   = '<xsl:value-of select="@key"/>';
			account.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			account.amount   = <xsl:value-of select="field[@name='amountDecimal']/text()"/>;
			account.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			accounts[accounts.length] = account;
			</xsl:for-each>
		</script>
	</xsl:template>
    <xsl:template match="/form-data" mode="view">

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentDate"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта перевода</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="amountCurrency" select="amountCurrency"/>
				<xsl:if test="$amountCurrency = 'RUR'">
					Рубль
				</xsl:if>
				<xsl:if test="$amountCurrency = 'USD'">
					Доллар США
				</xsl:if>
				<xsl:if test="$amountCurrency = 'EUR'">
					ЕВРО
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="amount"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="payerAccountType"/>]
				<xsl:value-of select="amountCurrency"/>
			</xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="titleRow">
		    <xsl:with-param name="rowName">Получатель платежа</xsl:with-param>
	    </xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Фамилия</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverSurName"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Имя</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverFirstName"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Отчество</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverPatrName"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата рождения</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBirthday"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="titleRow">
		    <xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
	    </xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Код</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankCode"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Банк</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankName"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Телефон</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankPhone"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дополнительная информация</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="addInformation"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма комиссии</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="commissionAmount"/></xsl:with-param>
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

    </xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='RECEIVED'">Получен банком получателем</xsl:when>
			<xsl:when test="$code='SUCCESSED'">Выдан</xsl:when>
			<xsl:when test="$code='CANCELATION'">Аннулирование</xsl:when>
			<xsl:when test="$code='MODIFICATION'">Изменение</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван</xsl:when>
			<xsl:when test="$code='RETURNED'">Возвращен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Ожидание обработки</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
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

	<xsl:template name="titleRow">
		<xsl:param name="rowName"/>
		<tr>
			<td colspan="2" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
		</tr>
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->