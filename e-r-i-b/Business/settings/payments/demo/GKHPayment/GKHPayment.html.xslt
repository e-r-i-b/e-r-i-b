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
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="currency" name="currency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="currency"/>
			    </xsl:attribute>
		</input>
		<!--TODO зачем это скрытое поле?!! если ниже есть с таким же именем и ID(!!!!!) нескрытое?!!!-->
		<input id="documentNumber" name="documentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="documentNumber"/>
	            </xsl:attribute>
		</input>
		
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер платежа</xsl:with-param>
			<xsl:with-param name="rowValue"><input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата платежа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="documentDate" name="documentDate" size="10">
						<xsl:attribute name="value">
							<xsl:value-of select="documentDate"/>
						</xsl:attribute>
					</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Фамилия, имя, отчество</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="payerName" name="payerName" size="40">
					<xsl:attribute name="value">
						<xsl:value-of select="payerName"/>
					</xsl:attribute>
				</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Код плательщика</xsl:with-param>
			<xsl:with-param name="rowValue">
					<input type="text" id="payerCode" name="payerCode" size="12">
						<xsl:attribute name="value">
							<xsl:value-of select="payerCode"/>
						</xsl:attribute>
					</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Адрес</xsl:with-param>
			<xsl:with-param name="rowValue">
					<xsl:variable name="payerAddress" select="payerAddress"/>
					<input type="text" id="payerAddress" name="payerAddress"  size="40">
						<xsl:if test="$payerAddress = ''">
							<xsl:attribute name="value">г. Москва</xsl:attribute>
						</xsl:if>
						<xsl:if test="$payerAddress != ''">
							<xsl:attribute name="value"><xsl:value-of select="payerAddress"/></xsl:attribute>
						</xsl:if>
					</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Период оплаты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="monthSelect" select="monthSelect"/>
					<select id="monthSelect" name="monthSelect">
						<xsl:if test="$isTemplate = 'true'">
							<option value="">
								<xsl:if test="$monthSelect = ''">
									<xsl:attribute name="selected">true</xsl:attribute>
								</xsl:if>
								Не задан
							</option>
						</xsl:if>
						<option value="1">
							<xsl:if test="$monthSelect = '1'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Январь
						</option>
						<option value="2">
							<xsl:if test="$monthSelect = '2'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Февраль
						</option>
						<option value="3">
							<xsl:if test="$monthSelect = '3'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Март
						</option>
						<option value="4">
							<xsl:if test="$monthSelect = '4'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Апрель
						</option>
						<option value="5">
							<xsl:if test="$monthSelect = '5'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Май
						</option>
						<option value="6">
							<xsl:if test="$monthSelect = '6'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Июнь
						</option>
						<option value="7">
							<xsl:if test="$monthSelect = '7'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Июль
						</option>
						<option value="8">
							<xsl:if test="$monthSelect = '8'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Август
						</option>
						<option value="9">
							<xsl:if test="$monthSelect = '9'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Сентябрь
						</option>
						<option value="10">
							<xsl:if test="$monthSelect = '10'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Октябрь
						</option>
						<option value="11">
							<xsl:if test="$monthSelect = '11'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Ноябрь
						</option>
						<option value="12">
							<xsl:if test="$monthSelect = '12'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Декабрь
						</option>
					</select>
					<select id="yearSelect" name="yearSelect">
						<xsl:variable name="yearSelect" select="yearSelect"/>
						<xsl:if test="$isTemplate = 'true'">
							<option value="">
								<xsl:if test="$yearSelect = ''">
									<xsl:attribute name="selected">true</xsl:attribute>
								</xsl:if>
								Не задан
							</option>
						</xsl:if>
						<option value="2006">
							<xsl:if test="$yearSelect = '2006'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2006
						</option>
						<option value="2007">
							<xsl:if test="$yearSelect = '2007'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2007
						</option>
						<option value="2008">
							<xsl:if test="$yearSelect = '2008'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2008
						</option>
						<option value="2009">
							<xsl:if test="$yearSelect = '2009'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2009
						</option>
						<option value="2010">
							<xsl:if test="$yearSelect = '2010'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2010
						</option>
						<option value="2011">
							<xsl:if test="$yearSelect = '2011'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2011
						</option>
						<option value="2012">
							<xsl:if test="$yearSelect = '2012'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2012
						</option>
					</select>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма к оплате с учетом страхования</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" value="{amount}" size="10"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма добровольного страхования</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="insuranceAmount" name="insuranceAmount" type="text" value="{insuranceAmount}" size="10"/></xsl:with-param>
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
							<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
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
						<select  id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
							<option value="" selected="selected">Счет клиента</option>
						</select>
					</xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		
		<script type="text/javascript">
		var currencyCodes = new Array();
	    function updateCurrencyCodes()
	    {
	        var payerAccountSelect         = document.getElementById("payerAccountSelect");
	        var code                     = currencyCodes[payerAccountSelect.value];
	        var currency   = document.getElementById("currency");
	        currency.value = code;
		}


	    function init()
	    {
	    <xsl:variable name="allAccounts" select="document('rur-accounts.xml')/entity-list/*"/>
	    <xsl:for-each select="$allAccounts">
	        currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
	    </xsl:for-each>

	        updateCurrencyCodes();
	    }

	    init();
	</script>

	</xsl:template>
	<xsl:template match="/form-data" mode="view">

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер платежа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата платежа</xsl:with-param>
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
			<xsl:with-param name="rowName">Фамилия, имя, отчество</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="payerName"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Код плательщика</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="payerCode"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Адрес</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="payerAddress"/></xsl:with-param>
		</xsl:call-template>

		<xsl:variable name="monthV" select="monthSelect"/>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Период оплаты</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$monthV = '1'">январь</xsl:if>
				<xsl:if test="$monthV = '2'">февраль</xsl:if>
				<xsl:if test="$monthV = '3'">март</xsl:if>
				<xsl:if test="$monthV = '4'">апрель</xsl:if>
				<xsl:if test="$monthV = '5'">май</xsl:if>
				<xsl:if test="$monthV = '6'">июнь</xsl:if>
				<xsl:if test="$monthV = '7'">июль</xsl:if>
				<xsl:if test="$monthV = '8'">август</xsl:if>
				<xsl:if test="$monthV = '9'">сентябрь</xsl:if>
				<xsl:if test="$monthV = '10'">октябрь</xsl:if>
				<xsl:if test="$monthV = '11'">ноябрь</xsl:if>
				<xsl:if test="$monthV = '12'">декабрь</xsl:if>
				&nbsp;<xsl:value-of select="yearSelect"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма к оплате с учетом страхования</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="amount"/></xsl:with-param>
		</xsl:call-template>

 		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма добровольного страхования</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="insuranceAmount"/></xsl:with-param>
		</xsl:call-template>

 		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="payerAcc" select="payerAccountSelect"/>
				<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="payerAccountType"/>]
				<xsl:value-of select="currency"/>
			</xsl:with-param>
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

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->