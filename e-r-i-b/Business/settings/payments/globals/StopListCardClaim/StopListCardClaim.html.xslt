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

	<xsl:variable name="formData" select="/form-data"/>

	<xsl:variable name="actionType" select="document('actionTypes')/entity-list/entity"/>

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:call-template name="initCardsScript"/>
				<xsl:call-template name="initActionTypes"/>
				<xsl:call-template name="editForm"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:call-template name="viewForm"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

 	<xsl:template name="editForm">
		<xsl:call-template name="editHtml"/>
		<xsl:call-template name="editInitControls"/>
		<xsl:call-template name="editInitValues"/>
	</xsl:template>

	<xsl:template name="viewForm">
		<xsl:call-template name="initActionTypes"/>
		<xsl:apply-templates mode="view"/>
		<xsl:call-template name="initChecks"/>
	</xsl:template>

	<xsl:template name="editHtml">
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
            <xsl:call-template name="standartRow">
			    <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			    <xsl:with-param name="required">false</xsl:with-param>
			    <xsl:with-param name="rowValue">
			        <xsl:value-of select="$formData/documentNumber"/>
			        <input id="documentNumber" name="documentNumber" type="hidden"/>
			    </xsl:with-param>
		    </xsl:call-template>
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
			<xsl:with-param name="rowName">Номер карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="card" name="card" onChange="refreshCards();"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Тип действия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="actionType" name="actionType"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Срок блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="expireBlockDate" name="expireBlockDate" maxlength="7" onkeydown="enterNumericTemplateFld(event,this,MONTH_TEMPLATE);"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Регионы блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="checkbox" id="region1" name="region1" onChange="chg('region1')" value="">&nbsp;РФ&nbsp;</input><br />
				<input type="checkbox" id="region2" name="region2" onChange="chg('region2')" value="">&nbsp;США&nbsp;</input><br />
				<input type="checkbox" id="region3" name="region3" onChange="chg('region3')" value="">&nbsp;Канада&nbsp;</input><br />
				<input type="checkbox" id="region4" name="region4" onChange="chg('region4')" value="">&nbsp;Латинская и Центральная Америка&nbsp;</input><br />
				<input type="checkbox" id="region5" name="region5" onChange="chg('region5')" value="">&nbsp;Азия и Тихоокеанский регион&nbsp;</input><br />
				<input type="checkbox" id="region6" name="region6" onChange="chg('region6')" value="">&nbsp;Европа&nbsp;</input><br />
				<input type="checkbox" id="region7" name="region7" onChange="chg('region7')" value="">&nbsp;Восточная Европа, Ближний Восток и Африка&nbsp;</input><br />
				<input type="checkbox" id="region8" name="region8" onChange="chg('region8');switchOther()" value="">&nbsp;Другое&nbsp;<input type="text" id="other" name="other" size="30" disabled="true" maxlength="255"/>&nbsp;</input><br />
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина блокирования</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="checkbox" id="reason1" name="reason1" onChange="chg('reason1')" value="">&nbsp;мошенничество&nbsp;</input><br />
				<input type="checkbox" id="reason2" name="reason2" onChange="chg('reason2')" value="">&nbsp;подделанная карта&nbsp;</input><br />
				<input type="checkbox" id="reason3" name="reason3" onChange="chg('reason3')" value="">&nbsp;потерянная карта&nbsp;</input><br />
				<input type="checkbox" id="reason4" name="reason4" onChange="chg('reason4')" value="">&nbsp;украденная карта&nbsp;</input><br />
				<input type="checkbox" id="reason5" name="reason5" onChange="chg('reason5')" value="">&nbsp;захват карты&nbsp;</input><br />
			</xsl:with-param>
		</xsl:call-template>
		<input type="hidden" id="documentDate"     name="documentDate"/>
		<input type="hidden" id="claimEndingDate"  name="claimEndingDate"/>
		<input type="hidden" id="currentDate"      name="currentDate"/>

		<input type="hidden" id="expireCardDate"   name="expireCardDate"/>

		<script type="text/javascript">
		<![CDATA[
        function refreshCards()
		{
			var cardSelect = document.getElementById("card");
			if (cardSelect.selectedIndex < 0)
				cardSelect.selectedIndex = 0;
			var cardObject = cardSelect.options[cardSelect.selectedIndex].cardObject;

			if ( cardObject == null ) {
				return;
			}


			setValue("expireCardDate", cardObject.expireDate);
		}

		function chg(elementId)
		{
			var t = document.getElementById(elementId);

			if (t.checked)
				setValue(elementId, "true")
			else
				setValue(elementId, "");
		}

		function switchOther()
		{
			var chk = document.getElementById("region8");
			var isChecked = chk.checked;
			var field = document.getElementById("other");
			if (isChecked) {
			   field.disabled = false;
			} else {
               field.disabled = true;
			}
		}

		]]>
		var fName = '<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>';
		var phone = '';
		<xsl:if test="$currentPerson/entity/field[@name='homePhone'] != 'null'">
		phone = '<xsl:value-of select="$currentPerson/entity/field[@name='homePhone']"/>';
		</xsl:if>
		<xsl:if test="$currentPerson/entity/field[@name='jobPhone'] != 'null'">
		phone = '<xsl:value-of select="$currentPerson/entity/field[@name='jobPhone']"/>';
		</xsl:if>
		<xsl:if test="$currentPerson/entity/field[@name='mobilePhone'] != 'null'">
		phone = '<xsl:value-of select="$currentPerson/entity/field[@name='mobilePhone']"/>';
		</xsl:if>
		setElement("phone", phone);
		setElement("fullName", fName);
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

		updateSelect("card",createOptions(cards, cardOptionCreator, "Нет карт"));

		var actionOptionCreator = function(value)
									{
										var option = new Option(value.type, value.code);
										option.cardObject = value;
										return option;
									}

		updateSelect("actionType",createOptions(actions, actionOptionCreator, "Без вариантов"));
		]]>
		</script>
	</xsl:template>

	<xsl:template name="editInitValues">
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

		setValue('card', 1);

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
		]]>

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>

		refreshCards();
		switchOther();
		</script>
	</xsl:template>

	<xsl:template name="initCardsScript">
		<xsl:variable name="cards" select="document(concat($data-path,'cards.xml'))"/>
		<script type="text/javascript">
			var cards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
			var state = '<xsl:value-of select="field[@name='cardState']/text()"/>';
			if (state == 'active' || state == 'replenishment' || state == 'changing' || state == 'ordered')
			{
			card            = new Object()
			card.id         = '<xsl:value-of select="field[@name='cardLinkId']/text()"/>';
			card.number     = '<xsl:value-of select="concat(substring(@key, 1, 1), '..', substring(@key, string-length(@key)-3, 4))"/>';
			card.type       = '<xsl:value-of select="field[@name='type']/text()"/>';
			card.expireDate = '<xsl:value-of select="field[@name='expireDate']/text()"/>'
			cards[cards.length] = card;
			}
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initActionTypes">
		<script type="text/javascript" language="javascript">
			var actions = new Array();
			var act;
			<xsl:for-each select="$actionType">
				<xsl:variable name="actionType" select="@key"/>
			act          = new Object()
			act.code     = '<xsl:value-of select="@key"/>';
			act.type     = '<xsl:value-of select="."/>';
			actions[actions.length] = act;
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initChecks">
		<script type="text/javascript" language="javascript">
			setValueAndChecked("region1", reg1);
			setValueAndChecked("region2", reg2);
			setValueAndChecked("region3", reg3);
			setValueAndChecked("region4", reg4);
			setValueAndChecked("region5", reg5);
			setValueAndChecked("region6", reg6);
			setValueAndChecked("region7", reg7);
			setValueAndChecked("region8", reg8);

			setValueAndChecked("reason1", rea1);
			setValueAndChecked("reason2", rea2);
			setValueAndChecked("reason3", rea3);
			setValueAndChecked("reason4", rea4);
			setValueAndChecked("reason5", rea5);
		</script>
	</xsl:template>

	<xsl:template name="viewHtml" mode="view" match="form-data">
	<script type="text/javascript" language="javascript">
		function setValueAndChecked(elementId, value)
		{
			var elem = document.getElementById(elementId);

			if (elem != null)
			{
				elem.value = value;
				elem.checked = value;
			}
		}

		var reg1 = <xsl:value-of select="region1"/>;
		var reg2 = <xsl:value-of select="region2"/>;
		var reg3 = <xsl:value-of select="region3"/>;
		var reg4 = <xsl:value-of select="region4"/>;
		var reg5 = <xsl:value-of select="region5"/>;
		var reg6 = <xsl:value-of select="region6"/>;
		var reg7 = <xsl:value-of select="region7"/>;
		var reg8 = <xsl:value-of select="region8"/>;

		var rea1 = <xsl:value-of select="reason1"/>;
		var rea2 = <xsl:value-of select="reason2"/>;
		var rea3 = <xsl:value-of select="reason3"/>;
		var rea4 = <xsl:value-of select="reason4"/>;
		var rea5 = <xsl:value-of select="reason5"/>;
	</script>
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
			<xsl:with-param name="rowName">Номер карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="concat(substring(card, 1, 1), '..', substring(card, string-length(card)-3, 4), ' ', cardType)"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Тип действия</xsl:with-param>
			<xsl:with-param name="rowValue">
				<script type="text/javascript">
					var code = <xsl:value-of select="actionType"/>;
				<![CDATA[
					for (var i = 0; i < actions.length; i++ )
					{
						if (actions[i].code == code) {
							document.write(""+actions[i].type);
						}
					}
				]]>
				</script>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Срок блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="expireBlockDate"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Регионы блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="checkbox" id="region1" disabled="true">&nbsp;РФ&nbsp;</input><br />
				<input type="checkbox" id="region2" disabled="true">&nbsp;США&nbsp;</input><br />
				<input type="checkbox" id="region3" disabled="true">&nbsp;Канада&nbsp;</input><br />
				<input type="checkbox" id="region4" disabled="true">&nbsp;Латинская и Центральная Америка&nbsp;</input><br />
				<input type="checkbox" id="region5" disabled="true">&nbsp;Азия и Тихоокеанский регион&nbsp;</input><br />
				<input type="checkbox" id="region6" disabled="true">&nbsp;Европа&nbsp;</input><br />
				<input type="checkbox" id="region7" disabled="true">&nbsp;Восточная Европа, Ближний Восток и Африка&nbsp;</input><br />
				<input type="checkbox" id="region8" disabled="true">&nbsp;Другое&nbsp;<xsl:value-of select="other"/>&nbsp;</input><br />
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина блокирования</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="checkbox" id="reason1" disabled="true">&nbsp;мошенничество&nbsp;</input><br />
				<input type="checkbox" id="reason2" disabled="true">&nbsp;подделанная карта&nbsp;</input><br />
				<input type="checkbox" id="reason3" disabled="true">&nbsp;потерянная карта&nbsp;</input><br />
				<input type="checkbox" id="reason4" disabled="true">&nbsp;украденная карта&nbsp;</input><br />
				<input type="checkbox" id="reason5" disabled="true">&nbsp;захватить карту&nbsp;</input><br />
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