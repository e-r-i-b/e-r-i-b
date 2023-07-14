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
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<!--TODO зачем это скрытое поле?!! если ниже есть с таким же именем и ID(!!!!!) нескрытое?!!!-->
		<input id="documentNumber" name="documentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="documentNumber"/>
	            </xsl:attribute>
		</input>
			<xsl:if test="$personAvailable">
		        <xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Номер документа</xsl:with-param>
					<xsl:with-param name="rowValue"><input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/></xsl:with-param>
				</xsl:call-template>

		        <xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Дата документа</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" id="documentDate" name="documentDate" size="10">
							<xsl:attribute name="value">
								<xsl:value-of select="documentDate"/>
							</xsl:attribute>
						</input>
					</xsl:with-param>
				</xsl:call-template>
	        </xsl:if>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" value="{amount}" size="10"/></xsl:with-param>
			</xsl:call-template>			

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Счет списания</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect">
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
						<select id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
							<option value="" selected="selected">Счет клиента&nbsp;</option>
						</select>
					</xsl:if>
				</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="titleRow">
				<xsl:with-param name="rowName">Реквизиты получателя платежа</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Наименование</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="60"/>
					<xsl:if test="$personAvailable">
						<input type="button" class="buttWhite smButt"  onclick="javascript:openSelectReceiver();" value="..."/>
					</xsl:if>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">ИНН</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="20" maxlength="10"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">КПП</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverKPP" name="receiverKPP" type="text" value="{receiverKPP}" size="20" maxlength="9"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Счет</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverAccount" name="receiverAccount" type="text" value="{receiverAccount}" maxlength="20" size="24"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="titleRow">
				<xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Наименование</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverBank" name="receiverBank" type="text" value="{receiverBank}" size="60"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">БИК</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" size="14" maxlength="9"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('receiverBank'),getFieldValue('receiverBIC'));" value="..."/>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Кор. счет</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" size="24" maxlength="20"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
				<xsl:with-param name="rowValue"><input id="ground" name="ground" type="text" value="{ground}" size="60"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="titleRow">
				<xsl:with-param name="rowName">Налоговые поля</xsl:with-param>
			</xsl:call-template>

			<tr>
				<td colspan="2">
					<table cellpadding="0" cellspacing="0" width="100%">

						<xsl:call-template name="specialRow">
							<xsl:with-param name="rowNameLeft">Статус</xsl:with-param>
							<xsl:with-param name="rowValueLeft">
								<input id="taxStatus" name="taxStatus" type="text" value="{taxStatus}" size="5"  onkeydown="enterNumericTemplateFld(event,this,'__');"/><input type="button" class="buttWhite smButt"
													onclick="javascipt:displayPeriodList(event,'taxstatus')" value="..."/>
							</xsl:with-param>

							<xsl:with-param name="rowNameRight">КБК</xsl:with-param>
							<xsl:with-param name="rowValueRight">
								<input id="taxKBK" name="taxKBK" type="text" value="{taxKBK}" size="25"/>
								<input type="button" class="buttWhite smButt" onclick="javascript:openKBKDictionary(setKBKInfo,getFieldValue('taxKBK'));" value="..."/>
							</xsl:with-param>
						</xsl:call-template>

						<xsl:call-template name="specialRow">
							<xsl:with-param name="rowNameLeft">Основание</xsl:with-param>
							<xsl:with-param name="rowValueLeft">
								<input id="taxGround" name="taxGround" type="text" value="{taxGround}" size="5"/><input type="button" class="buttWhite smButt"
													onclick="javascipt:displayPeriodList(event,'taxfund')" value="..."/>
							</xsl:with-param>

							<xsl:with-param name="rowNameRight">ОКАТО</xsl:with-param>
							<xsl:with-param name="rowValueRight"><input id="taxOKATO" name="taxOKATO" type="text" value="{taxOKATO}" size="25"/></xsl:with-param>
						</xsl:call-template>

						<xsl:call-template name="specialRow">
							<xsl:with-param name="rowNameLeft">Тип</xsl:with-param>
							<xsl:with-param name="rowValueLeft">
								<input id="taxType" name="taxType" type="text" value="{taxType}" size="5"/><input type="button" class="buttWhite smButt"
													onclick="javascipt:displayPeriodList(event,'taxtype')" value="..."/>
							</xsl:with-param>

							<xsl:with-param name="rowNameRight">Дата</xsl:with-param>
							<xsl:with-param name="rowValueRight">
								<input id="taxDocumentDate" name="taxDocumentDate" type="text" value="{taxDocumentDate}" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"/>
							</xsl:with-param>
						</xsl:call-template>

						<xsl:call-template name="specialRow">
							<xsl:with-param name="rowNameLeft">Период</xsl:with-param>
							<xsl:with-param name="rowValueLeft">
								<input id="taxPeriod1" name="taxPeriod1" type="text" maxLength="2" size="2" value="{taxPeriod1}"/>.
										<input id="taxPeriod2" name="taxPeriod2" type="text" onkeydown="enterNumericTemplateFld(event,this,'__');"
											   maxLength="2" size="2" value="{taxPeriod2}"/>.
										<input id="taxPeriod3" name="taxPeriod3" type="text" onkeydown="enterNumericTemplateFld(event,this,'____');"
											   maxLength="4" size="4" value="{taxPeriod3}"/>

										<input type="button" class="buttWhite smButt"
													onclick="javascipt:displayPeriodList(event,'periodfill')" value="..."/>
							</xsl:with-param>

							<xsl:with-param name="rowNameRight">Номер</xsl:with-param>
							<xsl:with-param name="rowValueRight"><input id="taxDocumentNumber" name="taxDocumentNumber" type="text" value="{taxDocumentNumber}" size="10"/></xsl:with-param>
						</xsl:call-template>
					</table>
				</td>
			</tr>
		<script type="text/javascript">
			function openSelectReceiver()
			{
			   window.open( document.webRootPrivate + '/receivers/list.do?action=getReceiverInfo&amp;kind=J',
				'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

			function setReceiverInfo(Info)
			{
			 setElement("receiverName",Info["receiverName"]);
 			 setElement("receiverINN",Info["receiverINN"]);
			 setElement("receiverKPP",Info["receiverKPP"]);
			 setElement("receiverAccount",Info["receiverAccount"]);
			 setElement("receiverBIC",Info["BIC"]);
			 setElement("receiverBank",Info["name"]);
			 setElement("receiverCorAccount",Info["account"]);
			}

			function setKBKInfo(Info)
			{
			 setElement("taxKBK",Info["code"]);
			}

			function setBankInfo(Info)
			{
			 setElement("receiverBIC",Info["BIC"]);
			 setElement("receiverBank",Info["name"]);
	 		 setElement("receiverCorAccount",Info["account"]);
			}

			function displayPeriodList(event,action)
			{
				win=window.open(document.webRootPrivate + "/PD4.do?action="+action,"","resizable=0,menubar=0,toolbar=0,scrollbars=1,status=0");
				win.resizeTo(500,340);
				win.moveTo(300,300);
			}
			function setTaxStatus(taxInfo)
			{
			  setElement('taxStatus',taxInfo["taxStatus"]);
			}
			function setTaxType(taxInfo)
			{
			  setElement('taxType',taxInfo["taxType"]);
			}
			function setTaxFund(taxInfo)
			{
			  setElement('taxGround',taxInfo["taxFund"]);
			}
			function setTaxPeriod(taxInfo)
			{
			  setElement('taxPeriod1',taxInfo["taxPeriod"]);
			  setElement('taxPeriod2',taxInfo["month"]);
			}

			function setTaxPeriodNull()
			{
			   setElement('taxPeriod1',"00");
			   setElement('taxPeriod2',"00");
			   setElement('taxPeriod3',"0000");
			}
		</script>

	</xsl:template>
    <xsl:template match="/form-data" mode="view">
	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Номер документа</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата документа</xsl:with-param>
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
				<xsl:with-param name="rowName">Дата приема платежа</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="admissionDate"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="amount"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Счет списания</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="payerAccountType"/>]
					<xsl:value-of select="currency"/>
				</xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="titleRow">
				<xsl:with-param name="rowName">Реквизиты получателя платежа</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Наименование</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="receiverName"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">ИНН</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="receiverINN"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">КПП</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="receiverKPP"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Счет</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="receiverAccount"/></xsl:with-param>
			</xsl:call-template>

		    <xsl:call-template name="titleRow">
				<xsl:with-param name="rowName">Реквизиты банка получателя</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Наименование</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="receiverBank"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">БИК</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="receiverBIC"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Кор. счет</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="receiverCorAccount"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="ground"/></xsl:with-param>
			</xsl:call-template>

 		    <xsl:call-template name="titleRow">
				<xsl:with-param name="rowName">Налоговые поля</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Статус</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="taxStatus"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">КБК</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="taxKBK"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">ОКАТО</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="taxOKATO"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Основание</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="taxGround"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Тип</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="taxType"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="taxDocumentDate"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Номер</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="taxDocumentNumber"/></xsl:with-param>
			</xsl:call-template>

	    	<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Период</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="taxPeriod1"/>.<xsl:value-of select="taxPeriod2"/>.<xsl:value-of select="taxPeriod3"/>
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

	<xsl:template name="specialRow">

		<xsl:param name="idRight"/>
		<xsl:param name="requiredRight" select="'true'"/>
		<xsl:param name="rowNameRight"/>
		<xsl:param name="rowValueRight"/>

		<xsl:param name="idLeft"/>
		<xsl:param name="requiredLeft" select="'true'"/>
		<xsl:param name="rowNameLeft"/>
		<xsl:param name="rowValueLeft"/>

		<tr>
			<td class="{$styleClass}" style="{$styleSpecial}" id="{$idLeft}">
				<xsl:copy-of select="$rowNameLeft"/>
				<xsl:if test="$requiredLeft = 'true' and $mode = 'edit'">
					<span id="asterisk_{$idLeft}" class="asterisk">*</span>
				</xsl:if>
			</td>
			<td>
				<nobr>
					<xsl:copy-of select="$rowValueLeft"/>
				</nobr>
			</td>

			<td class="{$styleClass}" style="{$styleSpecial}" id="{$idRight}">
				<xsl:copy-of select="$rowNameRight"/>
				<xsl:if test="$requiredRight = 'true' and $mode = 'edit'">
					<span id="asterisk_{$idRight}" class="asterisk">*</span>
				</xsl:if>
			</td>
			<td>
				<nobr>
					<xsl:copy-of select="$rowValueRight"/>
				</nobr>
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>