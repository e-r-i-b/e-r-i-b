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
	<xsl:variable name="styleSpecial" select="''"/>

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
		<xsl:call-template name="editHTML"/>
		<xsl:call-template name="editInitControls"/>
		<xsl:call-template name="editInitValues"/>
	</xsl:template>

<xsl:variable name="formData" select="/form-data"/>

<xsl:template name="editHTML" >
		<script type="text/javascript" language="JavaScript">
		document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="card" name="card" onchange="refreshText();"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="reason" name="reason" onchange="refreshReason();">
						<option value="Карта украдена">Карта украдена</option>
						<option value="Карта утеряна">Карта утеряна</option>
						<option value="Карта захвачена банкоматом">Карта захвачена банкоматом</option>
						<option value="Иная причина">Иная причина</option>
					</select>
			</xsl:with-param>
		</xsl:call-template>

		<input id="account" name="account" type="hidden" size="30" readonly="true"/>
	    <script type="text/javascript">
		<![CDATA[

		function refreshText()
		{
			var cardSelect = document.getElementById('card');
			if (cardSelect.selectedIndex < 0) return;
			var cardObject = cardSelect.options[cardSelect.selectedIndex].cardObject;
			if ( cardObject == null )	return;
			setValue("account", cardObject.account);

		}
		function refreshReason()
		{
			var reasonSelected= document.getElementById('reason');
			var elem = document.getElementById('paymentSystem');
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

				var cardOptionCreator = function(value)
								   {
								   		var option = new Option(value.number + ' [' + value.type + '] ', value.id);
										option.cardObject = value;
										return option;
								   }

		updateSelect('card',createOptions(cards, cardOptionCreator, "Нет карт"));
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
			if (elem == null)
			    return;
			elem.value = value;
		}
		refreshText();

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>
		refreshReason();
		<xsl:if test="string-length($formData/conditionsOfLost) > 0">
			setInitValue('conditionsOfLost', '<xsl:value-of select="$formData/conditionsOfLost"/>');
		</xsl:if>
		</script>
	</xsl:template>

	<xsl:template name="initCardsScript">
		<script type="text/javascript">
			<xsl:variable name="cards" select="document(concat($data-path,'cards.xml'))"/>

			var cards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
			<xsl:variable name="state" select="field[@name='cardState']/text()"/>
				<xsl:if test="$state = 'active' or $state = 'replenishment' or $state = 'changing' or $state = 'ordered'">
					card          = new Object()
					card.id       = '<xsl:value-of select="field[@name='cardLinkId']/text()"/>';
					card.number   = '<xsl:value-of select="concat(substring(@key, 1, 4), ' XXXX XXXX ', substring(@key, string-length(@key)-3, 4))"/>';
					card.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
					card.account  = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
					cards[cards.length] = card;
				</xsl:if>
			</xsl:for-each>
		</script>
	</xsl:template>
	
<xsl:template match="/form-data" mode="view">
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="concat(substring(card, 1, 4), ' XXXX XXXX ', substring(card, string-length(card)-3, 4), ' ', cardType)"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="reason"/>
				<xsl:variable name="lost" select="conditionsOfLost"/>
					<xsl:choose>
						<xsl:when test="$lost!='не заполняется'">
							&nbsp;(<xsl:value-of select="conditionsOfLost"/>)&nbsp;
						</xsl:when>
					</xsl:choose>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:variable name="infoAnotherCardType" select="anotherCardType"/>
		<xsl:variable name="infoCurrency" select="currency"/>
		<xsl:if test="$infoAnotherCardType!=''">

				<tr>
					<td colspan="2"><b>Перевыпуск карты</b></td>
				</tr>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Тип карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="anotherCardType"/>
			</xsl:with-param>
		</xsl:call-template>
			<xsl:if test="$infoCurrency!=''">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Валюта</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="currency"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
		</xsl:if>
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
	<xsl:if test="state='E'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа: <xsl:value-of select="refusingReason"/>
		</div>
	</xsl:if>
    </xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введена</xsl:when>
			<xsl:when test="$code='W'">Принята</xsl:when>
			<xsl:when test="$code='A'">Одобрена</xsl:when>
			<xsl:when test="$code='S'">Исполнена</xsl:when>
			<xsl:when test="$code='D'">Отказана</xsl:when>
			<xsl:when test="$code='E'">Отказана</xsl:when>
			<xsl:when test="$code='F'">Приостановлена</xsl:when>
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
		<xsl:param name="id"/>
		<tr>
			<td colspan="2" id="{$id}" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
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
<xsl:template match="state">

</xsl:template>
</xsl:stylesheet>