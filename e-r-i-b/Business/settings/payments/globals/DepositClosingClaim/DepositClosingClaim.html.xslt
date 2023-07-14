<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
 	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="formData" select="/form-data"/>
	<xsl:variable name="styleClass" select="'label120 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:call-template name="initDepositsScript"/>
				<xsl:call-template name="initAccountsScript"/>
				<xsl:call-template name="editForm"/>
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

	<xsl:template name="editHtml">
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
				<xsl:with-param name="rowName">Закрыть</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select onchange="this.value=0;window.location='{$webRoot}/private/claims/claim.do?form=AccountClosingClaim';">
						<option value="0" selected="true">Вклад</option>
						<option>Счет</option>
					</select>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Вклад для закрытия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="deposit" name="deposit" onchange="refreshTexts();"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Номер договора</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="contractNumber" name="contractNumber" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата открытия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="openingDate" name="openingDate" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата окончания</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="finishingDate" name="finishingDate" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата закрытия</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="closingDate" name="closingDate" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Перечислить на счет</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="destination-account" name="destination-account" onchange="refreshAccountInfo();"/>
				</xsl:with-param>
			</xsl:call-template>

		<input type="hidden" id="destination-amount" name="destination-amount"/>
		<input type="hidden" id="destination-amountCurrency" name="destination-amountCurrency"/>
		<input type="hidden" id="destination-accountType" name="destination-accountType"/>
		
		<br/><br/>
		<b>При досрочном расторжении вклада проценты по вкладу будут начислены по ставке вклада "до востребования".</b>
		
		<input type="hidden" id="depositDescription" name="depositDescription"/>

		<script type="text/javascript">
		<![CDATA[
		function refreshAccountInfo()
		{
			var accountSelect = document.getElementById('destination-account');
			for( var i=0; i<accounts.length; i++)
			{
				if(accountSelect.value == accounts[i].number)
				{
					setValue("destination-amount", accounts[i].amount);
					setValue("destination-amountCurrency", accounts[i].currency);
                    setValue("destination-accountType", accounts[i].type);
				}
			}
		}
		function findAccountByNumber(number)
		{
			for ( var i = 0; i < accounts.length; i++ )
			{
				if ( accounts[i].number == number )
					return accounts[i];
			}
		}

		function refreshTexts()
		{
			var depositSelect = document.getElementById('deposit');
			if (depositSelect.selectedIndex == -1)
				return;

			var depositObject = depositSelect.options[depositSelect.selectedIndex].depositObject;

			if ( depositObject == null )
				return;

			setValue("depositDescription", depositObject.type);
			setValue("contractNumber", depositObject.contract);
			setValue("openingDate", depositObject.openingDate);
			setValue("finishingDate", depositObject.finishingDate);
			refreshDestinationAccounts(depositObject);
		}

		function refreshDestinationAccounts(deposit){
			this.selectedDeposit=deposit;
			updateSelect("destination-account", createOptions(accounts, accountsOptionCreator, "Нет доступных счетов", "Выберите счет зачисления"));
			setValue("destination-account", deposit.finalAccount);
			refreshAccountInfo();
		}
		var accountsOptionCreator= function(account)
									{
										if (this.selectedDeposit.currency != account.currency)
											return null;
										var value = account.number+"["+account.type+"] "+account.amount+ " "+account.currency;
										var option = new Option(value, account.number);
										option.depositObject = account; 
										return option;
								   	}
		]]>
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
			var option;
			var k =0;
			if (array.length > 0 && !isEmptyString(selectOptionText))
			    options[k++] = new Option(selectOptionText, "");
			for ( var i = 0; i < array.length; i++ )
			{
				option = func(array[i])
				if (option != null){
					options[k++] = option;
				}
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

		var depositOptionCreator = function(deposit)
								   {
										var value = deposit.type+" "+deposit.amount+ " "+deposit.currency;
								   		var option = new Option(value, deposit.id);
										option.depositObject = deposit; 
										return option;
								   }

		updateSelect("deposit", createOptions(deposits, depositOptionCreator, "Нет доступных видов вкладов", "Выберите вид вклада"));
		]]>
		</script>
	</xsl:template>

	<xsl:template name="editInitValues">
		<script type="text/javascript">
		function setValue(elementId, value)
		{
			var elem = document.getElementById(elementId);

			if(elem.value != null)
				elem.value = value;
			else if(elem.innerHTML != null)
				elem.innerHTML = value;
		}

		function setInitValue(elementId, value)
		{
			var elem = document.getElementById(elementId);
			if (elem != null)
			{
				if (elem.value != null)
					elem.value = value;
				else if(elem.innerHTML != null)
					elem.innerHTML = value;
			}
		}

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>
		refreshTexts();
		refreshAccountInfo();
		</script>
	</xsl:template>

	<xsl:template name="initAccountsScript">
		<script type="text/javascript">
			<xsl:variable name="accounts" select="document(concat($data-path,'active-accounts.xml'))"/>
			var accounts = new Array();
			var account;
			<xsl:for-each select="$accounts/entity-list/entity">
			account          = new Object()
			account.number   = '<xsl:value-of select="@key"/>';
			account.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			account.amount   = <xsl:value-of select="field[@name='amountDecimal']/text()"/>;
			account.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			accounts[accounts.length] = account;
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initDepositsScript">
		<script type="text/javascript">
			<xsl:variable name="deposits" select="document(concat($data-path,'deposits.xml'))"/>
			var deposits = new Array();
			var deposit;
			<xsl:for-each select="$deposits/deposits/deposit">
			<xsl:variable name="isOpen" select="@is-open"/>
			<xsl:if test="$isOpen='true'">
			deposit               = new Object();
			deposit.id            = <xsl:value-of select="@id"/>;
			deposit.type          = '<xsl:value-of select="@type"/>';
			deposit.contract      = '<xsl:value-of select="@contract-number"/>';
			deposit.amount        = '<xsl:value-of select="@amount"/>';
			deposit.finalAccount  = '<xsl:value-of select="@final-account"/>'
			deposit.openingDate   = '<xsl:value-of select="substring(@opening-date,9,2)"/>.<xsl:value-of select="substring(@opening-date,6,2)"/>.<xsl:value-of select="substring(@opening-date,1,4)"/>'; 
			deposit.finishingDate = '<xsl:value-of select="substring(@finishing-date,9,2)"/>.<xsl:value-of select="substring(@finishing-date,6,2)"/>.<xsl:value-of select="substring(@finishing-date,1,4)"/>';
			deposit.currency      = '<xsl:value-of select="@currency"/>';
			deposits[deposits.length] = deposit;
			</xsl:if>
			</xsl:for-each>
		</script>
	</xsl:template>


	<xsl:template name="viewHtml" mode="view" match="form-data">
       <xsl:call-template name="standartRow">
		    <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="required">false</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Вклад для закрытия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="depositDescription"/>
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
				<xsl:value-of select="openingDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата окончания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="finishingDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата закрытия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="closingDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Перечислить на счет</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="destination-account"/>&nbsp;
				[<xsl:value-of select="destination-accountType"/>]&nbsp;
				<xsl:value-of select="destination-amount"/>&nbsp;
				<xsl:value-of select="destination-amountCurrency"/>
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
		<xsl:if test="state='REFUSED'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа: <xsl:value-of select="refusingReason"/>
		</div>
	</xsl:if>
</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
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
<scenarios ><scenario default="no" name="Edit" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\'"/></scenario><scenario default="yes" name="View" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
--><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\'"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="xsl:choose" x="64" y="147"/><block path="xsl:choose/=[0]" x="68" y="230"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template" x="233" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[1]" x="193" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates" x="153" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[1]" x="113" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[2]" x="73" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[2]" x="33" y="0"/><block path="xsl:choose/=[1]" x="177" y="205"/><block path="xsl:choose/xsl:when[1]/xsl:apply&#x2D;templates" x="249" y="30"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->