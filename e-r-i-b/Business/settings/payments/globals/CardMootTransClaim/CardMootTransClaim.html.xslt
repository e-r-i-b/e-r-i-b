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

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:call-template name="initCardsScript"/>
				<xsl:call-template name="initCurrencies"/>
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
		<xsl:apply-templates mode="view"/>
		<xsl:call-template name="initChecks"/>
	</xsl:template>

	<xsl:template name="editHtml">
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
	    <xsl:call-template name="standartRow">
	        <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:value-of select="$formData/documentNumber"/>
			    <input id="documentNumber" name="documentNumber" type="hidden"/>
			</xsl:with-param>
		</xsl:call-template>		
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Ф.И.О. клиента</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="fullName" name="fullName" type="text" value="" size="30" readonly="true" maxlength="100"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Контактный телефон (от 10 до 20 цифр)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="phone" name="phone" type="text" size="30" value="" maxlength="20"/>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Номер карты</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="card" name="card" onchange="refreshCards();"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата совершения операции (по выписке)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="payDate" name="payDate" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"/>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Место совершения операции (по выписке)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="payPlace" name="payPlace" maxlength="100"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма в валюте платежа</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="amountPayment" name="amountPayment" onChange="setSomeValue()" value=""/>&nbsp;
					<select id="paymentCurrency" name="paymentCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма в валюте карты</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="amountCard" name="amountCard" value=""/>&nbsp;<span id="cardCurrencySpan"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Тип спорной операции</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="checkbox" name="item1" id="item1" onChange="chg('item1')" value="">"я никогда не получал товара или услуг по вышеуказанной операции"</input><br />
					<input type="checkbox" name="item2" id="item2" onChange="chg('item2')" value="">"вышеуказанная операция совершалась один раз"</input><br />
					<input type="checkbox" name="item3" id="item3" onChange="chg('item3');enabledItem3()" value="">"сумма по вышеуказанной операции увеличена с&nbsp;<input id="startValue" disabled="true" name="startValue" type="text" size="9" maxlength="9"/>&nbsp;до&nbsp;<input id="endValue" name="endValue" disabled="true" type="text" size="9" maxlength="9"/>" (Прилагаю копию чека, содержащую правильную сумму) </input><br />
				    <input type="checkbox" name="item4" id="item4" onChange="chg('item4');enabledItem4()" value="">"я вернул товар по вышеуказанной операции&nbsp;<input id="returnDateDay" disabled="true" onChange="addToDate();" type="text" size="2" maxlength="2"/>&nbsp;<input id="returnDateMonth" disabled="true" onChange="addToDate();" type="text" size="2" maxlength="2"/>&nbsp;20<input id="returnDateYear" disabled="true" onChange="addToDate();" type="text" size="2" maxlength="2"/>&nbsp;г." </input><br />
					<input type="checkbox" name="item5" id="item5" onChange="chg('item5')" value="">"вышеуказанная операция была кредитовой (Прилагаю копию кредитного слипа, содержащую сумму удержанную с моего счета.)"</input><br />
					<input type="checkbox" name="item6" id="item6" onChange="chg('item6')" value="">"услуга не оказана"</input><br />
					<input type="checkbox" name="item7" id="item7" onChange="chg('item7');enabledItem7()" value="">&nbsp;"другое"&nbsp;<input type="text" id="other" name="other" size="30" disabled="true" maxlength="255"/>&nbsp;</input><br />
				</xsl:with-param>
			</xsl:call-template>
		<input type="hidden" id="currentDate"      name="currentDate"/>
		<input type="hidden" id="cardCurrency"     name="cardCurrency"/>
		<input type="hidden" id="returnDate"       name="returnDate"/>

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
			setValue("cardCurrency", cardObject.currency);
			setValue("cardCurrencySpan", cardObject.currency);
		}

		function chg(elementId)
		{
			var t = document.getElementById(elementId);

			if (t.checked)
				setValue(elementId, "true")
			else
				setValue(elementId, "");
		}

		function addToDate()
		{
			var day = document.getElementById("returnDateDay");
			var month = document.getElementById("returnDateMonth");
			var year = document.getElementById("returnDateYear");
			var datefield = document.getElementById("returnDate");

			setValue("returnDate", day.value+"."+month.value+".20"+year.value)
		}

		function setSomeValue()
		{
			var field = document.getElementById("amountPayment");
			setValue("startValue", field.value)
		}

		function enabledItem7()
		{
			var chk = document.getElementById("item7");
			var isChecked = chk.checked;
			var field = document.getElementById("other");
			if (isChecked) {
			   field.disabled = false;
			} else {
               field.disabled = true;
			}
		}

		function enabledItem3()
		{
			var chk = document.getElementById("item3");
			var isChecked = chk.checked;
			var field1 = document.getElementById("startValue");
			var field2 = document.getElementById("endValue");
			if (isChecked) {
			   field1.disabled = false;
			   var payment = document.getElementById("amountPayment");
			   field2.disabled = false;
			   setValue("startValue", payment.value);
			} else {
			   field1.disabled = true;
			   field2.disabled = true;
			}
		}

		function enabledItem4()
		{
			var chk = document.getElementById("item4");
			var isChecked = chk.checked;
			var dateFieldDay = document.getElementById("returnDateDay");
			var dateFieldMonth = document.getElementById("returnDateMonth");
			var dateFieldYear = document.getElementById("returnDateYear");
			if (isChecked) {
			   dateFieldDay.disabled = false;
			   dateFieldMonth.disabled = false;
			   dateFieldYear.disabled = false;
			} else {
			   dateFieldDay.disabled = true;
			   dateFieldMonth.disabled = true;
			   dateFieldYear.disabled = true;
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
										var option = new Option(value.number + ' [' + value.type + '] ' + value.amount.toFixed(2) + ' ' + value.currency , value.id);
										option.cardObject = value;
										return option;
									}

		updateSelect("card",createOptions(cards, cardOptionCreator, "Нет карт"));

		var currencyOptionCreator = function(value)
									{
										var option = new Option(' [' + value.type + '] ' + value.text, value.type);
										option.curr = value;
										return option;
									}

		updateSelect("paymentCurrency",createOptions(currencies, currencyOptionCreator, "Нет валют"));
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
			    return
			elem.value = value;
			if (elem.type == "checkbox")
			{
				var boolVal = false;
				if (value == "true")
					boolVal = true;
				elem.checked = boolVal;
			}
		}

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>
		<xsl:variable name="rDate" select="$formData/returnDate"/>
		<xsl:if test="string-length($rDate) > 0">
			var returnDate = "<xsl:value-of select="$rDate"/>";
			setInitValue('returnDateDay', '<xsl:value-of select="substring($rDate,1,2)"/>');
			setInitValue('returnDateMonth', '<xsl:value-of select="substring($rDate,4,2)"/>');
			setInitValue('returnDateYear', '<xsl:value-of select="substring($rDate,9,2)"/>');
		</xsl:if>

		refreshCards();
		enabledItem3();
		enabledItem4();
		enabledItem7();
		</script>
	</xsl:template>

	<xsl:template name="initCardsScript">
		<xsl:variable name="cards" select="document(concat($data-path,'cards.xml'))"/>
		<script type="text/javascript">
			var cards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
			card          = new Object()
			card.id       = '<xsl:value-of select="field[@name='cardLinkId']/text()"/>';
			card.number   = '<xsl:value-of select="concat(substring(@key, 1, 1), '..', substring(@key, string-length(@key)-3, 4))"/>';
			card.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			card.amount   =  <xsl:value-of select="field[@name='amountDecimal']/text()"/>;
			card.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			card.account  = '<xsl:value-of select="field[@name='cardAccount']/text()"/>'
			cards[cards.length] = card;
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initCurrencies">
		<script type="text/javascript" language="javascript">
			<xsl:variable name="optCurrency" select="document('currencies')/entity-list/entity"/>
			var currencies = new Array();
			var curr;
			<xsl:for-each select="$optCurrency">
				<xsl:variable name="optCurrency" select="@key"/>
			curr          = new Object()
			curr.type     = '<xsl:value-of select="$optCurrency"/>';
			curr.text     = '<xsl:value-of select="."/>';
			currencies[currencies.length] = curr;
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initChecks">
		<script type="text/javascript" language="javascript">
			setValueAndChecked("item1", item1);
			setValueAndChecked("item2", item2);
			setValueAndChecked("item3", item3);
			setValueAndChecked("item4", item4);
			setValueAndChecked("item5", item5);
			setValueAndChecked("item6", item6);
			setValueAndChecked("item7", item7);
		</script>
	</xsl:template>

	<xsl:template name="viewHtml" mode="view" match="form-data">
	<script type="text/javascript" language="javascript">
		function setValueAndChecked(elementId, value)
		{
			var elem = document.getElementById(elementId);

			if (elem != null)
			{
				elem.checked = value;
			}
		}

		var item1 = <xsl:value-of select="item1"/>;
		var item2 = <xsl:value-of select="item2"/>;
		var item3 = <xsl:value-of select="item3"/>;
		var item4 = <xsl:value-of select="item4"/>;
		var item5 = <xsl:value-of select="item5"/>;
		var item6 = <xsl:value-of select="item6"/>;
		var item7 = <xsl:value-of select="item7"/>;
		var other = "";
		var returnDate = "_________";
		var startValue = "_______";
		var endValue = "_______";
		<xsl:if test="item7 = 'true'">
		other = '<xsl:value-of select="other"/>';
		</xsl:if>
		<xsl:if test="item4 = 'true'">
		returnDate = '<xsl:value-of select="returnDate"/>';
		</xsl:if>
		<xsl:if test="item3 = 'true'">
		startValue = '<xsl:value-of select="amountPayment"/>';
		endValue = '<xsl:value-of select="endValue"/>';
		</xsl:if>

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
				<xsl:with-param name="rowName">Дата совершения операции (по выписке)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="substring(payDate,9,2)"/>.<xsl:value-of select="substring(payDate,6,2)"/>.<xsl:value-of select="substring(payDate,1,4)"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Место совершения операции (по выписке)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="payPlace"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма в валюте платежа</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="amountPayment"/>&nbsp;<xsl:value-of select="paymentCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма в валюте карты</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="amountCard"/>&nbsp;<xsl:value-of select="cardCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Тип спорной операции</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="checkbox" id="item1" disabled="true" value="">"я никогда не получал товара или услуг по вышеуказанной операции"</input><br />
					<input type="checkbox" id="item2" disabled="true" value="">"вышеуказанная операция совершалась один раз"</input><br />
					<input type="checkbox" id="item3" disabled="true" value="">"сумма по вышеуказанной операции увеличена с&nbsp;
						<xsl:variable name="i3chk" select="item3"/>
						<xsl:if	test="$i3chk = 'true'"><xsl:value-of select="amountPayment"/></xsl:if>&nbsp;до&nbsp;<xsl:value-of select="endValue"/>" (Прилагаю копию чека, содержащую правильную сумму)</input><br />
				    <input type="checkbox" id="item4" disabled="true" value="">"я вернул товар по вышеуказанной операции&nbsp;<xsl:value-of select="returnDate"/>" </input><br />
					<input type="checkbox" id="item5" disabled="true" value="">"вышеуказанная операция была кредитовой (Прилагаю копию кредитного слипа, содержащую сумму, удержанную с моего счета.)"</input><br />
					<input type="checkbox" id="item6" disabled="true" value="">"услуга не оказана"</input><br />
					<input type="checkbox" id="item7" disabled="true" value="">"другое"&nbsp;<xsl:value-of select="other"/>&nbsp;</input><br />
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