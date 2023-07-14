<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleClassFieldset" select="''"/>
	<xsl:variable name="styleClassLegend" select="''"/>
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
<xsl:variable name="cards" select="document(concat($data-path,'cards.xml'))"/>

	<xsl:template name="editHTML" >
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
		<script type="text/javascript" language="JavaScript">
		document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>
            <xsl:call-template name="standartRow">
			    <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			    <xsl:with-param name="required">false</xsl:with-param>
			    <xsl:with-param name="rowValue">
			        <xsl:value-of select="$formData/documentNumber"/>
			        <input id="documentNumber" name="documentNumber" type="hidden"/>
			    </xsl:with-param>
		    </xsl:call-template>		
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="cardNumber" name="cardNumber" onchange="refreshText();"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер СКС</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="account" name="account" type="text" size="30" readonly="true" maxlength="20"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Карта</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="newCardType" name="newCardType">
						<option value="VISA Gold">VISA Gold</option>
						<option value="VISA Classic">VISA Classic</option>
						<option value="VISA Electron">VISA Electron</option>
						<option value="MasterCard Gold">MasterCard Gold</option>
						<option value="MasterCard Standard">MasterCard Standard</option>
						<option value="CirrusMaestro">CirrusMaestro</option>
					</select>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Срочность изготовления</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="urgency" name="urgency">
					<option value='Обычный выпуск'>Обычный выпуск</option>
					<option value='Срочный выпуск'>Срочный выпуск</option>
				</select>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="fieldset">
            <xsl:with-param name="legend">Данные пользователя</xsl:with-param>
			<xsl:with-param name="innerTable">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Имя (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" id="nameInLatin" name="nameInLatin" size="30" maxlength="30"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Фамилия (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" id="surnameInLatin" name="surnameInLatin" size="30" maxlength="30"/>
					</xsl:with-param>
				</xsl:call-template>
			    <xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Кодовое слово</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" id="codeWord" name="codeWord" size="30" maxlength="30"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="fullName" name="fullName" type="text" value="{$currentPerson/entity/field[@name='fullName']}" size="30" readonly="true" maxlength="255"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Пол</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="gender" name="gender" type="text" size="30" value="{$currentPerson/entity/field[@name='gender']}" readonly="true" maxlength="1"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Дата рождения</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="birthDay" name="birthDay" type="text" size="30" readonly="true"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Гражданство</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="citizen" name="citizen" type="text" size="30" value="{$currentPerson/entity/field[@name='citizen']}" readonly="true" maxlength="255"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Статус</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="status" name="status" type="text" size="30" readonly="true" maxlength="30"/>
							<script type="text/javascript">
								statusValue = '<xsl:value-of select="$currentPerson/entity/field[@name='isResident']"/>';
								elem = document.getElementById('status');
								if (statusValue == 'true') elem.value = 'резидент';
								else elem.value='нерезидент';
							</script>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">ИНН</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="inn" name="inn" type="text" value="{$currentPerson/entity/field[@name='inn']}" size="30" readonly="true" maxlength="10"/>
					</xsl:with-param>
				</xsl:call-template>
			    <xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Паспорт</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Серия</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="passportSeries" name="passportSeries" type="text" value="{$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-series']}" size="30" readonly="true" maxlength="4"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Номер</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="passportNumber" name="passportNumber" type="text" value="{$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-number']}" size="30" readonly="true" maxlength="6"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Выдан</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="passportIssueDate" name="passportIssueDate" type="text" size="30" readonly="true" maxlength="255"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Кем выдан</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="passportIssueBy" name="passportIssueBy" type="text" value="{$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-issue-by']}" size="30" readonly="true"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Адрес прописки</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Адрес</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="registrationAddress" name="registrationAddress" type="text" value="{$currentPerson/entity/field[@name='registrationAddress']}" size="30" maxlength="100"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Телефон (о 10 до 20 цифр)</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="registrationPhone" name="registrationPhone" type="text" size="30" maxlength="20"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Адрес проживания</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Адрес</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="residenceAddress" name="residenceAddress" type="text" value="{$currentPerson/entity/field[@name='residenceAddress']}" size="30" maxlength="100"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Телефон (от 10 до 20 цифр)</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="residencePhone" name="residencePhone" type="text" size="30" maxlength="20"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">E-mail</xsl:with-param>
					<xsl:with-param name="rowValue">
						 <input id="eMail" name="eMail" type="text" value="{$currentPerson/entity/field[@name='eMail']}" size="30" maxlength="30"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:with-param>
		</xsl:call-template>
	    <script type="text/javascript">
		<![CDATA[

		function refreshText()
		{
			var cardSelect = document.getElementById('cardNumber');
			if (cardSelect.selectedIndex < 0) return;
			var cardObject = cardSelect.options[cardSelect.selectedIndex].cardObject;
			if ( cardObject == null )	return;
			setValue("account", cardObject.account);
		}
		function showDate(id, dateValue)
		{
		    elem = document.getElementById(id);
		    if (dateValue!="" && elem !=null)
		    {
		     if (dateValue.charAt(2)!=".")
		        dateValue = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
		     elem.value=dateValue;
		    }
		}
		]]>
 		showDate('passportIssueDate','<xsl:value-of select="$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-issue-date']"/>');
		showDate('birthDay','<xsl:value-of select="$currentPerson/entity/field[@name='birthDay']"/>');
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

		updateSelect('cardNumber',createOptions(cards, cardOptionCreator, "Нет карт"));
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
		refreshText();
		</script>
	</xsl:template>

	<xsl:template name="initCardsScript">
		<script type="text/javascript">
			var cards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
			var state = '<xsl:value-of select="field[@name='cardState']/text()"/>';
			var isMain = '<xsl:value-of select="field[@name='isMain']/text()"/>';
			if(isMain == 'true')
			{
				if (state == 'active')
				{
					card          = new Object()
					card.id       = '<xsl:value-of select="field[@name='cardLinkId']/text()"/>';
					card.number   = '<xsl:value-of select="concat(substring(@key, 1, 1), '..', substring(@key, string-length(@key)-3, 4))"/>';
					card.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
					card.account       = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
					cards[cards.length] = card;
				}
			}</xsl:for-each>
		</script>
	</xsl:template>
	<xsl:template match="/form-data" mode="view">
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
	<xsl:variable name="offices" select="document('departments.xml')"/>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер карты</xsl:with-param>
			<xsl:with-param name="rowValue">
				 <xsl:value-of select="concat(substring(cardNumber, 1, 1), '..', substring(cardNumber, string-length(cardNumber)-3, 4), ' ', cardType)"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер СКС</xsl:with-param>
			<xsl:with-param name="rowValue">
				 <xsl:value-of select="account"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Карта</xsl:with-param>
			<xsl:with-param name="rowValue">
				 <xsl:value-of select="newCardType"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Срочность изготовления</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="urgency"/>
			</xsl:with-param>
		</xsl:call-template>

		 <xsl:call-template name="fieldset">
            <xsl:with-param name="legend">Данные пользователя</xsl:with-param>
			<xsl:with-param name="innerTable">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Имя (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="nameInLatin"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Фамилия (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="surnameInLatin"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Кодовое слово</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="codeWord"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="fullName"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Пол</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="gender"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Дата рождения</xsl:with-param>
					<xsl:with-param name="rowValue">
						<script type="text/javascript">
								var dateValue = '<xsl:value-of select="birthDay"/>';
								tempDate = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
								document.write(tempDate);
						</script>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Гражданство</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="citizen"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Статус</xsl:with-param>
					<xsl:with-param name="rowValue">
					<xsl:value-of select="status"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">ИНН</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="inn"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Паспорт</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Серия</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="passportSeries"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Номер</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="passportNumber"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Выдан</xsl:with-param>
					<xsl:with-param name="rowValue">
						<script type="text/javascript">
								var dateValue = '<xsl:value-of select="passportIssueDate"/>';
								tempDate = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
								document.write(tempDate);
						</script>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Кем выдан</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="passportIssueBy"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Адрес прописки</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Адрес</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="registrationAddress"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Телефон</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="registrationPhone"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Адрес проживания</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Адрес</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="residenceAddress"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Телефон</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="residencePhone"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">E-mail</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="eMail"/>
					</xsl:with-param>
				</xsl:call-template>
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

	<xsl:variable name='department' select='department'/>
	<xsl:if test="state='ACCEPTED'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Ваша заявка одобрена банком. Вам необходимо прийти в отделение банка
			<xsl:variable name="department" select="department"/>
			("<xsl:value-of select="$offices/entity-list/entity[@key=$department]/field[@name='fullName']/text()"/>")
			для завершения процедуры открытия вклада
		</div>
	</xsl:if>
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

	<xsl:template name="titleRow">
		<xsl:param name="rowName"/>
		<tr>
			<td colspan="2" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
		</tr>
	</xsl:template>

	<xsl:template name="fieldset">
		<xsl:param name="legend"/>
		<xsl:param name="innerTable"/>
		<tr>
			<td colspan="2">
			<fieldset class="{$styleClassFieldset}">
			<legend class="{$styleClassLegend}"><xsl:copy-of select="$legend"/></legend>
				<table cellpadding="0" cellspacing="0" width="100%">
					<xsl:copy-of select="$innerTable"/>
				</table>
			</fieldset>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="init-values">
	<xsl:param name="form-data"/>
	<xsl:choose>
		<xsl:when test="$form-data">
			<xsl:for-each select="$form-data/*">
				<xsl:if test="string-length(text()) > 0">
				<xsl:variable name="text" select="text()"/>
				<xsl:variable name="from">'</xsl:variable>
				<xsl:variable name="to">\'</xsl:variable>				
			setInitValue('<xsl:value-of select="name()"/>', '<xsl:call-template name="replace">
			                                                       <xsl:with-param name="text" select="$text"/>
			                                                       <xsl:with-param name="from" select="$from"/>
			                                                       <xsl:with-param name="to" select="$to"/>
			                                                  </xsl:call-template>');
				</xsl:if>
			</xsl:for-each>
		</xsl:when>
	</xsl:choose>
	</xsl:template>

	 <xsl:template name="replace">
        <xsl:param name="text"/>
        <xsl:param name="from"/>
        <xsl:param name="to"/>
        <xsl:choose>
          <xsl:when test="contains($text, $from)">
                <xsl:value-of select="substring-before($text,  $from)"/>
                <xsl:value-of select="$to"/>
                <!-- Recurse through HTML -->
                <xsl:call-template name="replace">
                    <xsl:with-param name="text" select="substring-after($text, $from)"/>
                    <xsl:with-param name="from" select="$from"/>
                    <xsl:with-param name="to" select="$to"/>
                </xsl:call-template>
          </xsl:when>
          <xsl:otherwise>
                <xsl:value-of select="$text"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:template>
      
<xsl:template match="state">

</xsl:template>
</xsl:stylesheet>