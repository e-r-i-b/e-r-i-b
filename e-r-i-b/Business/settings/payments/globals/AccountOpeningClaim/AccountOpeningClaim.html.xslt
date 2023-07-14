<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
 	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'label120'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="accountTypes" select="document('accountTypes')/entity-list/entity"/>
    <xsl:template match="/">
	<xsl:choose>
		<xsl:when test="$mode = 'edit'">
			<xsl:apply-templates mode="edit"/>
			<xsl:call-template name="editInitValues"/>
		</xsl:when>
		<xsl:when test="$mode = 'view'">
			<xsl:apply-templates mode="view"/>
		</xsl:when>
	</xsl:choose>
    </xsl:template>

	<xsl:template name="editHtml" mode="edit" match="form-data">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="false"/>
            <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:value-of select="documentNumber"/>
			    <input id="documentNumber" name="documentNumber" type="hidden"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:variable name="departments" select="document('departments.xml')/entity-list"/>
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="false"/>
			<xsl:with-param name="rowName">Открыть</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select onchange="this.value=0;window.location='{$webRoot}/private/claims/claim.do?form=DepositOpeningClaim';">
						<option value="0" selected="true">Счет</option>
						<option>Вклад</option>
				</select>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта счета</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="currency" name="currency">
						<option value="RUB">(RUB) Рубль</option>
						<option value="USD">(USD) Доллар США</option>
						<option value="EUR">(EUR) Евро</option>
				</select>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Тип счета</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="accountType" name="accountType">
					<xsl:for-each select="$accountTypes">
						<xsl:variable name="accType" select="@key"/>
							<option value="{$accType}"><xsl:value-of select="."/></option>
					</xsl:for-each>
				</select>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата визита в отделение банка</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="visitDate" name="visitDate" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>&nbsp;ДД.ММ.ГГГГ
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Офис банка</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="department" name="department">
					<xsl:choose>
						<xsl:when test="count($departments) = 0">
							<option value="">Нет офисов</option>
						</xsl:when>
						<xsl:otherwise>
							<xsl:for-each select="$departments/entity">
								<option>
									<xsl:if test="@key = $currentPerson/entity/field[@name='currentDepartmentId']/text()">
										<xsl:attribute name="selected">true</xsl:attribute>
									</xsl:if>
									<xsl:attribute name="value">
										<xsl:value-of select="@key"/>
									</xsl:attribute>
									<xsl:value-of select="field[@name='fullName']/text()"/>
								</option>
							</xsl:for-each>
						</xsl:otherwise>
					</xsl:choose>
				</select>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="fullName" name="fullName" size="50" maxlength="100"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Гражданство</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="citizenship" name="citizenship" size="30" maxlength="100"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Контактный телефон</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="phone" name="phone" size="30" maxlength="13"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">E-mail</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="email" name="email" size="30" maxlength="30"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="titleRow">
				<xsl:with-param name="rowName"><a href ="javascript:void(0)" onclick="javascript:openSelectDocument();">Посмотреть договор</a></xsl:with-param>
		</xsl:call-template>

		<input type="hidden" id="currentDate" name="currentDate"/>
		<script type="text/javascript" language="JavaScript">
	        setElement("fullName", "<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>");
		</script>
	</xsl:template>

	<xsl:template name="editInitValues">
		<script type="text/javascript">

			document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';

			function clearMasks(event)
			{
				clearInputTemplate("visitDate", DATE_TEMPLATE);
			}

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

			function openSelectDocument()
			{				
				window.open(document.webRootPrivate + '/templates/documents/list.do?action=start',
		       	'TemplatesDocuments', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

			<xsl:call-template name="init-values">
			   <xsl:with-param name="form-data" select="$formData"/>
			</xsl:call-template>
		</script>
	</xsl:template>

	<xsl:template name="viewHtml" mode="view" match="form-data">
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта счета</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Тип счета</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="accType" select="accountType"/>
				<xsl:value-of select="$accountTypes[@key=$accType]/text()"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата визита в отделение банка</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="vDate" select="visitDate"/>
					<xsl:if test="string-length($vDate) > 0">
						<xsl:value-of select="substring(visitDate,9,2)"/>.<xsl:value-of select="substring(visitDate,6,2)"/>.<xsl:value-of select="substring(visitDate,1,4)"/>
				    </xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Офис банка</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="office"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fullName"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Гражданство</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="citizenship"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Контактный телефон</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="phone"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">E-mail</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="email"/>
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


		<xsl:if test="state='ACCEPTED' and $webRoot != '/PhizIA'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Ваша заявка одобрена банком. Вам необходимо прийти в отделение банка
			("<xsl:value-of select="office"/>")
			для завершения процедуры открытия счета
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
			<xsl:when test="$code='SENDED'">Исполняется банком</xsl:when>
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
