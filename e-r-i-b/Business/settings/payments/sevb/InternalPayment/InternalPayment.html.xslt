<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>

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
        <input id="amountCurrency"  name="amountCurrency"  type="hidden"/>
	    <input id="documentDate" name="documentDate"  type="hidden" value="{documentDate}"/>
			<tr>
				<td class="Width120 LabelAll">Счет списания</td>
				<td>
					<xsl:variable name="fromAccount" select="fromAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="fromAccountSelect" name="fromAccountSelect" onchange="javascript:updateCurrencyCodes(); showHideOperationCode();">
							<xsl:for-each select="document('active-credit-accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$fromAccount = ./@key">
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
						<select disabled="disabled"><option selected="selected">Счет клиента</option></select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет зачисления</td>
				<td>
					<xsl:variable name="toAccount" select="toAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="toAccountSelect" name="toAccountSelect" onchange="javascript:updateCurrencyCodes(); showHideOperationCode();">
							<xsl:for-each select="document('active-debit-accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$toAccount = ./@key">
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
						<select disabled="disabled"><option selected="selected">Счет клиента</option></select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма <span class="asterisk">*</span></td>
				<td><input id="amount" name="amount" type="text" value="{amount}" size="24"/></td>
			</tr>
	        <tr id="operationCodeRow">
					<td class="Width120 LabelAll" nowrap="true">Код валютной операции</td>
					<td>
						<input id="ground" size="24" value="{ground}" name="ground" />&nbsp;
						<input type="button" class="buttWhite" style="height:18px;width:18;"
					       onclick="javascript:openSelectOperationTypeWindow();" value="..."/>
					</td>
			</tr>
        <script type="text/javascript">

	        function openSelectOperationTypeWindow()
			{
	            var val = getElementValue("ground");
	            if (val.length > 0)
	                 window.open('../operationCodes.do?' + "fltrCode=" + val.substring(3,8), 'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
	            else
                window.open('../operationCodes.do', 'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

            var currencyCodes = new Array();

            function updateCurrencyCodes()
            {
                var fromAccountSelect         = document.getElementById("fromAccountSelect");
                var code                     = currencyCodes[fromAccountSelect.value];
                var amountCurrency   = document.getElementById("amountCurrency");
                amountCurrency.value = code;
			}

	        function setOperationCodeInfo(operationCodeInfo)
			{
	            setElement('ground', "{VO" + operationCodeInfo["operationCode"] + "}");
			}

	        function showHideOperationCode()
			{
	            var isRes = '<xsl:value-of select="document('currentPerson.xml')/entity-list/entity/field[@name='isResident']"/>';
				if(isRes == 'false')
				{
					hideOrShow(document.getElementById("operationCodeRow"), false);
				}
				else
				{
					hideOrShow(document.getElementById("operationCodeRow"), true);
				}
			}

            function init()
            {
            <xsl:variable name="allAccounts" select="document('all-accounts.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
                currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
            </xsl:for-each>

                updateCurrencyCodes();
	            showHideOperationCode();
            }

            init();
        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">
            <tr>
				<td class="Width120 LabelAll"><b>Счет списания</b></td>
				<td>
					<xsl:value-of select="fromAccountSelect"/>&nbsp;
					[<xsl:value-of select="fromAccountSelectType"/>]&nbsp;
					<xsl:value-of select="amountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Счет зачисления</b></td>
				<td>
					<xsl:value-of select="toAccountSelect"/>&nbsp;
					[<xsl:value-of select="toAccountSelectType"/>]&nbsp;
					<xsl:value-of select="amountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Сумма</b></td>
				<td><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of select="amountCurrency"/></td>
			</tr>
	        <tr>
				<td class="Width120 LabelAll"><b>Комиссия</b></td>
				<td><xsl:value-of  select="commission"/>&nbsp;<xsl:value-of select="amountCurrency"/></td>
			</tr>
			<xsl:if test="ground != ''">
				<tr>
					<td class="Width120 LabelAll" nowrap="true"><b>Код валютной операции</b></td>
					<td>
						<xsl:value-of select="ground"/>
					</td>
				</tr>
			</xsl:if>			
			<tr>
				<td class="Width120 LabelAll"><b>Статус платежа</b></td>
				<td><div id="state">
						<a onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" style="text-decoration:underline">
							<xsl:call-template name="state2text">
								<xsl:with-param name="code">
									<xsl:value-of select="state"/>
								</xsl:with-param>
							</xsl:call-template>
						</a>
					</div>
				</td>
			</tr>
	         <tr>
				<td class="Width120 LabelAll">Плановая дата исполнения</td>
				<td><xsl:value-of  select="admissionDate"/></td>
			</tr>
    </xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='W'">Обрабатывается</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:when test="$code='V'">Отозван</xsl:when>
			<xsl:when test="$code='F'">Приостановлен</xsl:when>
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->