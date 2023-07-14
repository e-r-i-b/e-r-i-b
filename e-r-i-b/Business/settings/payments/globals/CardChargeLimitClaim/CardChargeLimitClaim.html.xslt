<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
 	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'label200 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:variable name="formData" select="/form-data"/>

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:call-template name="initCardsScript"/>
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
	        <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:value-of select="$formData/documentNumber"/>
			    <input id="documentNumber" name="documentNumber" type="hidden"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Ф.И.О. клиента</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="fullName" name="fullName" type="text" size="30" readonly="true" maxlength="100"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Контактный телефон (от 10 до 20 цифр)</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="phone" name="phone" type="text" size="30" maxlength="20"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер основной карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="baseCardNumber" name="baseCardNumber" onchange="refreshBaseCards();"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер дополнительной карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="additCardNumber" name="additCardNumber" onchange="refreshAddCards();"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">ФИО держателя дополнительной карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="ownerName" name="ownerName" type="text" size="30" maxlength="100"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма лимита</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="limit" name="limit" />&nbsp;<span id="limitCur"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Период ограничений</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="period" name="period" onchange="">
					<option value='день'>день</option>
					<option value='месяц'>месяц</option>
				</select>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма наличных операций</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="amount" name="amount" />&nbsp;<span id="amountNal"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма безналичных операций</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="amountv" name="amountv" />&nbsp;<span id="amountBeznal"/>
			</xsl:with-param>
		</xsl:call-template>
		<input type="hidden" id="currency"         name="currency"/>
		<input type="hidden" id="account"          name="account"/>
		 <input type="hidden" id="documentDate"     name="documentDate"/>
		<input type="hidden" id="claimEndingDate"  name="claimEndingDate"/>
		<input type="hidden" id="currentDate"      name="currentDate"/>
		<script type="text/javascript">
		<![CDATA[

		function refreshPeriod()
		{
			var periodSelect = document.getElementById("period");
			if (periodSelect.selectedIndex < 0)
				periodSelect.selectedIndex = 0;
			var periodObject = periodSelect.options[periodSelect.selectedIndex];

			if ( periodObject == null ) {
				return;
			}

			setValue("periodDesc", periodObject.value);
		}

		function refreshAddCards()
		{
			var cardSelect = document.getElementById("additCardNumber");
			if (cardSelect.selectedIndex < 0)
				cardSelect.selectedIndex = 0;
			var cardObject = cardSelect.options[cardSelect.selectedIndex].cardObject;

			if ( cardObject == null ) {
				return;
			}

			setValue("ownerName", cardObject.owner);
		}

		function refreshBaseCards()
		{
			var cardSelect = document.getElementById("baseCardNumber");
			if (cardSelect.selectedIndex < 0)
				cardSelect.selectedIndex = 0;
			var cardObject = cardSelect.options[cardSelect.selectedIndex].cardObject;

			if ( cardObject == null )
			{
				return;
			}

			var account = cardObject.account;
			var cardSelect = document.getElementById("additCardNumber");
			cardSelect.options.length = 0;

			for (var i = 0; i < acards.length; i++)
			{
				var value = acards[i];
				if (value.account == account)
				{
					var option = new Option(value.number + ' [' + value.type + '] ' + value.amount.toFixed(2) + ' ' + value.currency , value.id);
					option.cardObject = value;
					cardSelect.options[cardSelect.options.length] = option;
				}
			}

			if (cardSelect.options.length == 0)
			{
				cardSelect.options[cardSelect.options.length] = new Option('Нет дополнительных карт',"");
				setValue("ownerName", "");
			} else {
			    refreshAddCards();
			}

			setValue("currency",     cardObject.currency);
			setValue("account",      cardObject.account);
			setValue("amountNal",    cardObject.currency);
			setValue("amountBeznal", cardObject.currency);
			setValue("limitCur",     cardObject.currency);
		}
		]]>
		var firstName = '<xsl:value-of select="$currentPerson/entity/field[@name='firstName']"/>';
		var surName = '<xsl:value-of select="$currentPerson/entity/field[@name='surName']"/>';
		var patrName = '<xsl:value-of select="$currentPerson/entity/field[@name='patrName']"/>';
		setElement("fullName", surName + " " + firstName + " " + patrName);
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
				options[i] = func(array[i]);
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

		var baseCardOptionCreator = function(value)
									{
										var option = new Option(value.number + ' [' + value.type + '] ' + value.amount.toFixed(2) + ' ' + value.currency , value.id);
										option.cardObject = value;
										return option;
									}

		updateSelect("baseCardNumber",createOptions(bcards, baseCardOptionCreator, "Нет основных карт"));

		var additCardOptionCreator = function(value)
									{
										var option = new Option(value.number + ' [' + value.type + '] ' + value.amount.toFixed(2) + ' ' + value.currency , value.id);
										option.cardObject = value;
										return option;
									}

		updateSelect("additCardNumber",createOptions(acards, additCardOptionCreator, "Нет дополнительных карт"));
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

		<xsl:variable name="bCardNb" select="$formData/baseCardNumber"/>
		<xsl:if test="string-length($bCardNb) > 0">
			setInitValue('baseCardNumber', '<xsl:value-of select="$bCardNb"/>');
			refreshAddCards();
		</xsl:if>
		refreshBaseCards();
		<xsl:variable name="aCardNb" select="$formData/additCardNumber"/>
		<xsl:if test="string-length($aCardNb) > 0">
			setInitValue('additCardNumber', '<xsl:value-of select="$aCardNb"/>');
			refreshAddCards();
		</xsl:if>
		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>
		</script>
	</xsl:template>

	<xsl:template name="initCardsScript">
		<xsl:variable name="cards" select="document(concat($data-path,'cards-with-owner.xml'))"/>
		<script type="text/javascript">
			var bcards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
				<xsl:if test="field[@name='isMain']/text() = 'true'">
			card          = new Object()
			card.id       = '<xsl:value-of select="field[@name='cardLinkId']/text()"/>';
			card.number   = '<xsl:value-of select="concat(substring(@key, 1, 1), '..', substring(@key, string-length(@key)-3, 4))"/>';
			card.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			card.amount   =  <xsl:value-of select="field[@name='amountDecimal']/text()"/>;
			card.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			card.account  = '<xsl:value-of select="field[@name='cardAccount']/text()"/>'
			bcards[bcards.length] = card;
				</xsl:if>
			</xsl:for-each>

			var acards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
				<xsl:if test="field[@name='isMain']/text() = 'false'">
			card          = new Object()
			card.id       = '<xsl:value-of select="field[@name='cardLinkId']/text()"/>';
			card.number   = '<xsl:value-of select="concat(substring(@key, 1, 1), '..', substring(@key, string-length(@key)-3, 4))"/>';
			card.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			card.amount   = <xsl:value-of select="field[@name='amountDecimal']/text()"/>;
			card.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			card.account  = '<xsl:value-of select="field[@name='cardAccount']/text()"/>'
			card.owner    = '<xsl:value-of select="field[@name='cardOwner']/text()"/>'
			acards[acards.length] = card;
				</xsl:if>
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="viewHtml" mode="view" match="form-data">
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Ф.И.О. клиента</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fullName"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Контактный телефон</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="phone"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер основной карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="concat(substring(baseCardNumber, 1, 1), '..', substring(baseCardNumber, string-length(baseCardNumber)-3, 4), ' ', baseCardType)"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер дополнительной карт</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="concat(substring(additCardNumber, 1, 1), '..', substring(additCardNumber, string-length(additCardNumber)-3, 4), ' ', additCardType)"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">ФИО держателя дополнительной карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="ownerName"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма лимита</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="limit"/>&nbsp;<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Период ограничений</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="period"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма наличных операций</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="amount"/>&nbsp;<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма безналичных операций</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="amountv"/>&nbsp;<xsl:value-of select="currency"/>
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
			<xsl:when test="$code='ACCEPTED'">Одобрен</xsl:when>
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
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\'"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="xsl:choose" x="64" y="147"/><block path="xsl:choose/=[0]" x="68" y="230"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template" x="233" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[1]" x="193" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates" x="153" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[1]" x="113" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[2]" x="73" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[2]" x="33" y="0"/><block path="xsl:choose/=[1]" x="177" y="205"/><block path="xsl:choose/xsl:when[1]/xsl:apply&#x2D;templates" x="249" y="30"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
--><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\'"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="yes" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\..\..\..\..\..\..\..\settings\claims\DepositOpeningClaimData\form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="xsl:choose" x="64" y="147"/><block path="xsl:choose/=[0]" x="68" y="230"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template" x="233" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[1]" x="193" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates" x="153" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[1]" x="113" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates[2]" x="73" y="0"/><block path="xsl:choose/xsl:when/xsl:call&#x2D;template[2]" x="33" y="0"/><block path="xsl:choose/=[1]" x="177" y="205"/><block path="xsl:choose/xsl:when[1]/xsl:apply&#x2D;templates" x="249" y="30"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->