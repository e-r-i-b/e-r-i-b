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
		<script type="text/javascript">
			document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>

		<input id="foreignCurrency" name="foreignCurrency" type="hidden"/>
				<tr>
					<td class="Width120 LabelAll">Списать со счета</td>
					<td>
						<xsl:variable name="payerAccount" select="payerAccountSelect"/>
						<xsl:if test="$personAvailable">
							<select name="payerAccountSelect" id="payerAccountSelect" onchange="javascript:showHideOperationCode();">
								<xsl:for-each select="document('rur-accounts.xml')/entity-list/*">
									<option>
										<xsl:attribute name="value">
											<xsl:value-of select="./@key"/>
										</xsl:attribute>
										<xsl:if test="$payerAccount = ./@key">
											<xsl:attribute name="selected">true</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
										<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
										<xsl:value-of select="./field[@name='currencyCode']"/>
									</option>
								</xsl:for-each>
							</select>
						</xsl:if>
						<xsl:if test="not($personAvailable)">
							<select disabled="disabled">
								<option selected="selected">Счет клиента</option>
							</select>
						</xsl:if>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Сумма в рублях</td>
					<td>
						<input id="rurAmount" size="24" value="{rurAmount}" name="rurAmount"
							onchange="javascript:refreshForeignCurrencySum()"
							onkeypress="javascript:refreshForeignCurrencySum()"
							onkeydown="javascript:refreshForeignCurrencySum()"
							onkeyup="javascript:refreshForeignCurrencySum()"
							/>&nbsp;RUB
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Зачислить на счет</td>
					<td>
						<xsl:variable name="receiverAccount" select="receiverAccountSelect"/>
						<xsl:if test="$personAvailable">
							<select id="receiverAccountSelect" name="receiverAccountSelect" onchange="javascript:updateCurrencyCode(); showHideOperationCode();">
								<xsl:variable name="foreignCurrencyAccounts" select="document('foreign-currency-accounts.xml')/entity-list/*"/>

								<xsl:choose>
									<xsl:when test="count($foreignCurrencyAccounts) = 0">
										<option value="">нет валютных счетов</option>
									</xsl:when>
									<xsl:otherwise>
										<xsl:for-each select="$foreignCurrencyAccounts">
											<option>
												<xsl:attribute name="value">
													<xsl:value-of select="./@key"/>
												</xsl:attribute>
												<xsl:if test="$receiverAccount = ./@key">
													<xsl:attribute name="selected">true</xsl:attribute>
												</xsl:if>
												<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
												<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
												<xsl:value-of select="./field[@name='currencyCode']"/>
											</option>
										</xsl:for-each>
									</xsl:otherwise>
								</xsl:choose>
							</select>
						</xsl:if>
						<xsl:if test="not($personAvailable)">
							<select disabled="disabled">
								<option selected="selected">Счет клиента</option>
							</select>
						</xsl:if>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">Сумма в иностранной валюте</td>
					<td>
						<input id="foreignCurrencyAmount" size="24" value="{foreignCurrencyAmount}" name="foreignCurrencyAmount"
							onchange="javascript:refreshRurSum()"
							onkeypress="javascript:refreshRurSum()"
							onkeydown="javascript:refreshRurSum()"
							onkeyup="javascript:refreshRurSum()"
							/>&nbsp;<span id="currencyCode"/>
					</td>
				</tr>
				<tr id="operationCodeRow">
					<td class="Width120 LabelAll" nowrap="true">Код валютной операции</td>
					<td>
						<input id="operationCode" size="24" value="{operationCode}" name="operationCode" />&nbsp;
						<input type="button" class="buttWhite" style="height:18px;width:18;"
					       onclick="javascript:openSelectOperationTypeWindow();" value="..."/>
					</td>
				</tr>
		<script type="text/javascript">

			function openSelectOperationTypeWindow()
			{
                window.open('../operationCodes.do?' + addParam2List("","operationCode","fltrCode"),
                        'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

			function refreshRurSum()
			{
				document.getElementById("rurAmount").value = '';
			}

			function refreshForeignCurrencySum()
			{
				document.getElementById("foreignCurrencyAmount").value = '';
			}

			var currencyCodes = new Array();

			function updateCurrencyCode()
			{
				var receiverAccountElem    = document.getElementById("receiverAccountSelect");
				var code                   = currencyCodes[receiverAccountElem.value];

				if (code == null)
					 code = "";

				var currencyCodeElem       = document.getElementById("currencyCode");
				currencyCodeElem.innerHTML = code;
				var foreignCurrencyElem    = document.getElementById("foreignCurrency");
				foreignCurrencyElem.value  = code;
			}

			function showHideOperationCode()
			{
				if(showOperationTypeList(document.getElementById("payerAccountSelect").value, document.getElementById("receiverAccountSelect").value))
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
			<xsl:variable name="foregnCurrencyAccounts" select="document('foreign-currency-accounts.xml')/entity-list/*"/>
			<xsl:for-each select="$foregnCurrencyAccounts">
				currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
			</xsl:for-each>

				updateCurrencyCode();
				showHideOperationCode();
			}

			init();
		</script>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
			<tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:value-of select="payerAccountSelect"/>
				</td>
			</tr>
			<xsl:if test="rurAmount != '0.00'">
				<tr>
					<td class="Width120 LabelAll">Сумма в рублях</td>
					<td>
						<xsl:value-of select="rurAmount"/>&nbsp;
						<xsl:value-of select="rurCurrency"/>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td class="Width120 LabelAll">Зачислить на счет</td>
				<td>
					<xsl:value-of select="receiverAccountSelect"/>					
				</td>
			</tr>
			<xsl:if test="foreignCurrencyAmount != ''">
				<tr>
					<td class="Width120 LabelAll" nowrap="true">Сумма в иностранной валюте</td>
					<td>
						<xsl:value-of select="foreignCurrencyAmount"/>&nbsp;
						<xsl:value-of select="foreignCurrency"/>
					</td>
				</tr>
			</xsl:if>
			<xsl:if test="operationCode != ''">
				<tr>
					<td class="Width120 LabelAll" nowrap="true">Код валютной операции</td>
					<td>
						<xsl:value-of select="operationCode"/>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td class="Width120 LabelAll">Статус документа</td>
				<td><div id="state">
						<span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
							<xsl:call-template name="state2text">
								<xsl:with-param name="code">
									<xsl:value-of select="state"/>
								</xsl:with-param>
							</xsl:call-template>
						</span>
					</div>
				</td>
			</tr>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
		</xsl:choose>
	</xsl:template>	
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;>html" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="rur&#x2D;accounts.xml" srcSchemaRoot="entity&#x2D;list" AssociatedInstance="file:///c:/Projects/RS/PhizIC&#x2D;SBRF/Business/settings/forms/PurchaseCurrencyPayment/rur&#x2D;accounts.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template><template match="/form&#x2D;data"><block path="script/xsl:value&#x2D;of" x="172" y="18"/><block path="table/tr[1]/td[1]/xsl:if" x="172" y="97"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="12" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="126" y="175"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="172" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="172" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="57"/><block path="table/tr[1]/td[1]/xsl:if[1]" x="132" y="97"/><block path="table/tr[3]/td[1]/xsl:if" x="92" y="97"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="52" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="212" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="86" y="175"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="132" y="177"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="12" y="57"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="212" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="132" y="17"/><block path="table/tr[3]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="92" y="17"/><block path="table/tr[3]/td[1]/xsl:if[1]" x="52" y="97"/><block path="table/tr[4]/td[1]/span/xsl:value&#x2D;of" x="12" y="97"/><block path="script[1]/xsl:for&#x2D;each" x="222" y="107"/><block path="" x="12" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of" x="132" y="137"/><block path="script[1]/xsl:for&#x2D;each/xsl:value&#x2D;of[1]" x="52" y="137"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->