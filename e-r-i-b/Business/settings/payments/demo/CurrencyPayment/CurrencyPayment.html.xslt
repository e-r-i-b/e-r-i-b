<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="isTemplate" select="'isTemplate'"/>

	<xsl:variable name="styleClass" select="'label120'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="'width:200px'"/>	

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
        <input id="currency"  name="currency"  type="hidden"/>

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
				<xsl:with-param name="rowName">Счет списания</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:updateCurrencyCodes()">
							<xsl:if test="$isTemplate = 'true'">
								<option value="">Не задан</option>
							</xsl:if>
							<xsl:for-each select="document('active-foreign-currency-accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$payerAccount = ./@key">
										<xsl:attribute name="selected">true</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="./@key"/>&nbsp;
									[<xsl:value-of select="./field[@name='type']"/>]
									<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
									<xsl:value-of select="./field[@name='currencyCode']"/>
								</option>
							</xsl:for-each>
						</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
							<option value="" selected="selected">Счет клиента<span class="asterisk">*</span></option>
						</select>
					</xsl:if>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма перевода</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" value="{amount}" size="24"/></xsl:with-param>
			</xsl:call-template>

	        <xsl:call-template name="titleRow">
		        <xsl:with-param name="rowName">Реквизиты получателя платежа</xsl:with-param>
	        </xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Название получателя платежа</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Код страны получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverCountryCode" name="receiverCountryCode" type="text" value="{receiverCountryCode}" size="50"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openCountryDictionary(setReceiverCountryInfo,getFieldValue('receiverCountryCode'));" value="..."/>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Счет получателя</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverAccountSelect" name="receiverAccountSelect" type="text" value="{receiverAccountSelect}" size="50" maxlength="30"/>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Город</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverCity" name="receiverCity" type="text" value="{receiverCity}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Адрес получателя</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverAddress" name="receiverAddress" type="text" value="{receiverAddress}" size="50"/></xsl:with-param>
			</xsl:call-template>

	        <xsl:call-template name="titleRow">
		        <xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
	        </xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Название банка</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverBankName" name="receiverBankName" type="text" value="{receiverBankName}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Код страны</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverBankCountryCode" name="receiverBankCountryCode" type="text" value="{receiverBankCountryCode}" size="50"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openCountryDictionary(setReceiverBankCountryInfo,getFieldValue('receiverBankCountryCode'));" value="..."/>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Город банка получателя</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverBankCity" name="receiverBankCity" type="text" value="{receiverBankCity}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Адрес банка получателя</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverBankAddress" name="receiverBankAddress" type="text" value="{receiverBankAddress}" size="50"/></xsl:with-param>
			</xsl:call-template>

	        <xsl:call-template name="titleRow">
		        <xsl:with-param name="rowName">Реквизиты банка посредника</xsl:with-param>
	        </xsl:call-template>

		    <xsl:call-template name="standartRow">
			    <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Название банка</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="intermediaryBankName" name="intermediaryBankName" type="text" value="{intermediaryBankName}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
			    <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Код страны</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="intermediaryBankCountryCode" name="intermediaryBankCountryCode" type="text" value="{intermediaryBankCountryCode}" size="50"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openCountryDictionary(setIntermediaryBankCountryInfo,getFieldValue('intermediaryBankCountryCode'));" value="..."/>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
			    <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Город банка посредника</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="intermediaryBankCity" name="intermediaryBankCity" type="text" value="{intermediaryBankCity}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
			    <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Адрес банка посредника</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="intermediaryBankAddress" name="intermediaryBankAddress" type="text" value="{intermediaryBankAddress}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
			    <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Корр. счет банка получателя в банке посреднике</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverBankСorrAccount" name="receiverBankСorrAccount" type="text" value="{receiverBankСorrAccount}" size="50" maxlength="20"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="ground" name="ground" type="text" value="{ground}" size="50"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Условия перевода</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="paymentConditionsV" select="paymentConditions"/>
			        <select name="paymentConditions">
				        <xsl:if test="$isTemplate = 'true'">
							<option value="">Не задан</option>
						</xsl:if>
				        <option>
					        <xsl:attribute name="value">tod</xsl:attribute>
					        <xsl:if test="$paymentConditionsV = 'tod'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        текущим днем
				        </option>
				        <option>
					        <xsl:attribute name="value">tom</xsl:attribute>
					        <xsl:if test="$paymentConditionsV = 'tom'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        следующим днем
				        </option>
				        <option>
					        <xsl:attribute name="value">spot</xsl:attribute>
					        <xsl:if test="$paymentConditionsV = 'spot'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        через 2 дня
				        </option>
			        </select>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Комиссия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="commissionV" select="commission"/>
			        <select name="commission" onchange="javascript:setCommission()" onkeypress="javascript:setCommission()" onkeydown="javascript:setCommission()" onkeyup="javascript:setCommission()">
				        <xsl:if test="$isTemplate = 'true'">
							<option value="">Не задан</option>
						</xsl:if>
				        <option>
					        <xsl:attribute name="value">MAIN</xsl:attribute>
					        <xsl:if test="$commissionV = 'MAIN'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        С моего счета
				        </option>
				        <option>
					        <xsl:attribute name="value">OTHER</xsl:attribute>
					        <xsl:if test="$commissionV = 'OTHER'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        Со счета получателя
				        </option>
			        </select>
				</xsl:with-param>
			</xsl:call-template>
	        <!-- В МЗБ счет списания комиссии отсутсвует -->
 		    <xsl:call-template name="standartRow">
                <xsl:with-param name="id">commissionAccountLabel</xsl:with-param> 
				<xsl:with-param name="rowName">Счет списания комиссии</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="commissionAccount" select="commissionAccount"/>
				        <xsl:if test="$personAvailable">
					        <select id="commissionAccount" name="commissionAccount">
						        <xsl:if test="$isTemplate = 'true'">
									<option value="">Не задан</option>
								</xsl:if>
						        <xsl:for-each select="document('active-accounts.xml')/entity-list/*">
							        <option>
								        <xsl:attribute name="value">
									        <xsl:value-of select="./@key"/>
								        </xsl:attribute>
								        <xsl:if test="$commissionAccount = ./@key">
									        <xsl:attribute name="selected">true</xsl:attribute>
								        </xsl:if>
								        <xsl:value-of select="./@key"/>&nbsp;
								        [<xsl:value-of select="./field[@name='type']"/>]
								        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
								        <xsl:value-of select="./field[@name='currencyCode']"/>
							        </option>
						        </xsl:for-each>
					        </select>
				        </xsl:if>
				        <xsl:if test="not($personAvailable)">
					        <select id="commissionAccount" name="commissionAccount" disabled="disabled">
						        <option value="" selected="selected">Счет клиента</option>
						    </select>
				        </xsl:if>
				</xsl:with-param>
			</xsl:call-template>
	    
        <script type="text/javascript">

	        function setReceiverBankInfo(Info)
	        {
	         setElement("receiverBankName",Info["name"]);
             setElement("receiverBankCity",Info["place"]);
	         setElement("receiverBankCountryCode",Info["country"]);
 	         setElement("receiverBankAddress",Info["address"]);
	        }

	        function setIntermediaryBankInfo(Info)
	        {
	        setElement("intermediaryBankName",Info["name"]);
            setElement("intermediaryBankCity",Info["place"]);
	        setElement("intermediaryBankCountryCode",Info["country"]);
            setElement("intermediaryBankAddress",Info["address"]);
	        }

	        function setReceiverCountryInfo(Info)
	        {
	         setElement("receiverCountryCode",Info["code"]);
	        }

	        function setReceiverBankCountryInfo(Info)
	        {
	         setElement("receiverBankCountryCode",Info["code"]);
	        }

	        function setIntermediaryBankCountryInfo(Info)
	        {
	        setElement("intermediaryBankCountryCode",Info["code"]);
	        }

	        function setCommission()
	        {
	            var commissionAccount = document.getElementById("commissionAccount");
    	        var commissionAccountLabel = document.getElementById("commissionAccountLabel");
    	        var commissionElem = document.getElementById("commission");
	            if (commissionElem.value == "MAIN")
	            {
	                commissionAccount.style.display = "block";
			        commissionAccountLabel.style.display = "block";
	            }
	            else
	            {
		            commissionAccount.style.display = "none";
			        commissionAccountLabel.style.display = "none";
	            }
	        }

	        var currencyCodes = new Array();
            function updateCurrencyCodes()
            {
                var payerAccountSelect         = document.getElementById("payerAccountSelect");
                var code                     = currencyCodes[payerAccountSelect.value];
                var currency   = document.getElementById("currency");
                currency.value = code;
			}


            <xsl:if test="$personAvailable">
	        function init()
            {
            <xsl:variable name="allAccounts" select="document('foreign-currency-accounts.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
                currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
            </xsl:for-each>
	            currencyCodes[""] = "";
                updateCurrencyCodes();
                setCommission();
            }

            init();
	        </xsl:if>
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

			<xsl:variable name="adDate" select="admissionDate"/>
			<xsl:if test="not($adDate = '')">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Дата приема платежа</xsl:with-param>
					<xsl:with-param name="rowValue"><xsl:value-of select="admissionDate"/></xsl:with-param>
				</xsl:call-template>
			</xsl:if>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="payerAccountType"/>]
				<xsl:value-of select="payerAccountCurrency"/>
			</xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="amount"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="titleRow">
		    <xsl:with-param name="rowName">Реквизиты получателя платежа</xsl:with-param>
	    </xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Название получателя платежа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverName"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Код страны получателя</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverCountryCode"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет получателя</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverAccountSelect"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Город</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverCity"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Адрес получателя</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverAddress"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="titleRow">
		    <xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
	    </xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Название банка</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankName"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Код страны</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankCountryCode"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Город банка получателя</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankCity"/></xsl:with-param>
		</xsl:call-template>

 	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Адрес банка получателя</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankAddress"/></xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="titleRow">
		    <xsl:with-param name="rowName">Реквизиты банка посредника</xsl:with-param>
	    </xsl:call-template>

	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Название банка</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="intermediaryBankName"/></xsl:with-param>
		</xsl:call-template>

	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Код страны</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="intermediaryBankCountryCode"/></xsl:with-param>
		</xsl:call-template>

	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Город банка посредника</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="intermediaryBankCity"/></xsl:with-param>
		</xsl:call-template>	     

	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Адрес банка посредника</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="intermediaryBankAddress"/></xsl:with-param>
		</xsl:call-template>

 	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Корр. счет банка получателя в банке посреднике</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverBankСorrAccount"/></xsl:with-param>
		</xsl:call-template>

	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="ground"/><xsl:value-of select="groundAdd"/></xsl:with-param>
		</xsl:call-template>

	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Условия перевода</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="paymentConditionsV" select="paymentConditions"/>
				<xsl:if test="$paymentConditionsV = 'tod'">
					текущим днем
				</xsl:if>
				<xsl:if test="$paymentConditionsV = 'tom'">
					следующим днем
				</xsl:if>
				<xsl:if test="$paymentConditionsV = 'spot'">
					через 2 дня
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

	    <xsl:variable name="commissionV" select="commission"/>
	     <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Комиссия</xsl:with-param>
			<xsl:with-param name="rowValue">
					<xsl:if test="$commissionV = 'MAIN'">
						С моего счета
					</xsl:if>
					<xsl:if test="$commissionV = 'OTHER'">
						Со счета получателя
					</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

			<xsl:if test="$commissionV = 'MAIN'">
			    <xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Счет списания комиссии</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="commissionAccount"/>[<xsl:value-of select="commissionAccountType"/>]
						<xsl:value-of select="commissionAccountCurrency"/>
					</xsl:with-param>
				</xsl:call-template>

			    <xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Сумма комиссии</xsl:with-param>
					<xsl:with-param name="rowValue"><xsl:value-of select="commissionAmount"/></xsl:with-param>
				</xsl:call-template>

			</xsl:if>

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
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Ожидание обработки</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван</xsl:when>
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

</xsl:stylesheet>