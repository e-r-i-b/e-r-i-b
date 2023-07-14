<?xml version='1.0' encoding='windows-1251'?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
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
	        <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Счет для закрытия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="closingAccount" select="account"/>
					<xsl:if test="$personAvailable">
						<select id="account" name="account" onchange="javascript:updateCurrency();refreshToAccount();showHideOperationCode();">
							<xsl:for-each select="document('active-accountsDeposits.xml')/entity-list/*">
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
				<xsl:with-param name="required" select="false"/>
				<xsl:with-param name="rowId">finishingDateBlock</xsl:with-param>
				<xsl:with-param name="rowName">Дата окончания</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="finishingDate" size="10" name="finishingDate" readonly="true"/>
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
						<select id="receiverAccount" name="receiverAccount"/>
					</xsl:if>
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
			<br/>
			<center><b>При досрочном расторжении вклада проценты по вкладу будут начислены по ставке вклада "до востребования".</b></center>
			<br/>

	<script type="text/javascript">

		var allAccount = new Array();

		function show(elem)
		{
			document.getElementById(elem).style.display = "";
		}

		function hide(elem)
		{
			document.getElementById(elem).style.display = "none";
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

		function account(){
		}

		function loadAllAccount()
		{
			var i=0;
			<xsl:variable name="allAccounts" select="document('active-accountsDeposits.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
	            <xsl:variable name="date" select="field[@name='openingDate']/text()"/>
	            <xsl:variable name="finDate" select="field[@name='depositFinishingDate']/text()"/>
				allAccount[i] = new account();
				allAccount[i].value = '<xsl:value-of select="./@key"/>';
				allAccount[i].text = '<xsl:value-of select="./@key"/>&nbsp;[&nbsp;<xsl:value-of select="./field[@name='type']"/>&nbsp;]&nbsp;<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/> &nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>';
				allAccount[i].accDate = '<xsl:value-of select="substring($date,9,2)"/>.<xsl:value-of select="substring($date,6,2)"/>.<xsl:value-of select="substring($date,1,4)"/>'
				allAccount[i].contractNumber = '<xsl:value-of select="./field[@name='contractNumber']"/>';
	            allAccount[i].currency = '<xsl:value-of select="./field[@name='currencyCode']"/>';
	            allAccount[i].finDate = '<xsl:value-of select="./field[@name='depositFinishingDate']"/>';
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
			var toAccount = document.getElementById('receiverAccount');
			toAccount.length = 0;
			var j = 0;
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
			if(allAccount.length == 0 || j == 0)
		    {
			    toAccount.options[j] = new Option("нет доступных счетов","noAccount");
		    }
			{   // обновим информацию на странице для счета
				refreshFinDate(num);
				setElement("contractNumber", allAccount[num].contractNumber);
				setElement("openingDate", allAccount[num].accDate);
				setElement("finishingDate", allAccount[num].finDate);
				setElement("receiverAccount", '<xsl:value-of select="receiverAccount"/>');
				if(toAccount.value == ''){
					setElement("receiverAccount", toAccount[0].value);
				}
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
				var toAccount = document.getElementById('receiverAccount');
				for (var i=0; i < allAccount.length; i++)
				{
					if(toAccount.value == allAccount[i].value)
					{
						document.getElementById('toAccountType').value = allAccount[i].accountType;
					}
				}
        	}

        	function refreshFinDate(num){
        	    if (allAccount[num].finDate==''){
			    	hide('finishingDateBlock');
				}else{
		        	show('finishingDateBlock');
				}
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

		function init()
		{
			loadAllAccount();
			createAccount();
			updateCurrency();
			refreshToAccount();
			setElement("closingDate", '<xsl:value-of select="closingDate"/>');
			var btnSave = findCommandButton("button.save");
			if (findCommandButton("button.save"))
				btnSave.setValidationFunction(isFieldValue);
			showHideOperationCode();
		}    
		init();

		</script>
</xsl:template>

<xsl:template match="/form-data" mode="view">
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
			<xsl:with-param name="rowId">finishingDateBlock</xsl:with-param>
			<xsl:with-param name="rowName">Дата окончания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="finishingDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата закрытия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="closingDate" select="closingDate"/>
				<xsl:value-of select="closingDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:variable name="receiverAccount" select="receiverAccount"/>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Перевести на счёт</xsl:with-param>
			<xsl:with-param name="td2Id">receiverAccount</xsl:with-param>
			<xsl:with-param name="rowValue">
					<xsl:value-of select="receiverAccount"/>&nbsp;[&nbsp;
					<xsl:value-of select="toAccountType"/>&nbsp;]&nbsp;
					<xsl:value-of select="currency"/>
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
				<a style="text-decoration:underline">
					<xsl:call-template name="state2text">
						<xsl:with-param name="code">
							<xsl:value-of select="state"/>
						</xsl:with-param>
					</xsl:call-template>
				</a>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="admissionDate"/>
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

			var finDate = '<xsl:value-of select="finDateValue"/>';
			if (finDate==''){
				hide('finishingDateBlock');
			}else{
				show('finishingDateBlock');
			}
			
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
			<xsl:when test="$code='I'">Введена</xsl:when>
			<xsl:when test="$code='W'">Принята</xsl:when>
			<xsl:when test="$code='S'">Исполнена</xsl:when>
			<xsl:when test="$code='E'">Отказана</xsl:when>
			<xsl:when test="$code='F'">Приостановлена</xsl:when>
			<xsl:when test="$code='V'">Отозвана</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\AccountClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->