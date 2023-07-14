<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
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
	    <input id="fromAccountAmount"  name="fromAccountAmount"  type="hidden"/>
		<input id="fromAccountType"    name="fromAccountType"    type="hidden"/>
	    <!--TODO зачем это скрытое поле?!! если ниже есть с таким же именем и ID(!!!!!) нескрытое?!!!-->
	     <input id="documentNumber" name="documentNumber" type="hidden">
			    <xsl:attribute name="value">
				    <xsl:value-of select="documentNumber"/>
                </xsl:attribute>
	    </input>

        <table cellspacing="2" cellpadding="0" border="0"  class="paymentFon">
             <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/CurrencyPayment.gif" border="0"/></td>
                <td colspan="2" >
                    <table class="MaxSize paymentTitleFon">
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr>
                        <td align="center" class="silverBott paperTitle">
                            <table height="100%" width="420" cellspacing="0" cellpadding="0" class="paymentTitleFon">
                            <tr>
                                <td class="titleHelp">
                                     <span class="formTitle">Перевод иностранной валюты</span>
                                     <br/>
	                                Перечисление денежных средств с вашего счета на счет получателя в иностранной валюте.                                     
                                </td>
                            </tr>
                            </table>
                        </td>
                        <td width="100%">&nbsp;</td>
                    </tr>
                    </table>
                </td>
            </tr>
	        <xsl:if test="$personAvailable">
				<tr>
					<td class="Width120 LabelAll">Номер перевода<span class="asterisk">*</span></td>
					<td>
						<input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Дата перевода<span class="asterisk">*</span></td>
					<td>
						<input type="text" id="documentDate" name="documentDate" size="10">
							<xsl:attribute name="value">
								<xsl:value-of select="documentDate"/>
							</xsl:attribute>
						</input>
					</td>
				</tr>
	        </xsl:if>
			<tr>
				<td class="Width120 LabelAll">Счет списания<span class="asterisk">*</span></td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:updateCurrencyCodes()">
							<xsl:for-each select="document('active-foreign-currency-accounts.xml')/entity-list/*">
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
						<select disabled="disabled"><option selected="selected">Счет клиента<span class="asterisk">*</span></option></select>
					</xsl:if>
				</td>
			</tr>
	        <tr>
		        <td class="Width120 LabelAll">Сумма перевода<span class="asterisk">*</span></td>
		        <td><input id="amount" name="amount" type="text" value="{amount}" size="24"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты получателя платежа</b></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Название получателя платежа<span class="asterisk">*</span></td>
		        <td><input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="50"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Код страны получателя<span class="asterisk">*</span></td>
		        <td><input id="receiverCountryCode" name="receiverCountryCode" type="text" value="{receiverCountryCode}" size="50"/><input type="button" class="buttWhite smButt" 
						onclick="javascript:openCountryDictionary(setReceiverCountryInfo,getFieldValue('receiverCountryCode'));" value="..."/>
		        </td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Счет получателя<span class="asterisk">*</span></td>
		        <td><input id="receiverAccountSelect" name="receiverAccountSelect" type="text" value="{receiverAccountSelect}" size="50" maxlength="20"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Город<span class="asterisk">*</span></td>
		        <td><input id="receiverCity" name="receiverCity" type="text" value="{receiverCity}" size="50"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Адрес получателя<span class="asterisk">*</span></td>
		        <td><input id="receiverAddress" name="receiverAddress" type="text" value="{receiverAddress}" size="50"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка получателя</b></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Название банка<span class="asterisk">*</span></td>
		        <td><input id="receiverBankName" name="receiverBankName" type="text" value="{receiverBankName}" size="50"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">SWIFT<span class="asterisk">*</span></td>
		        <td><input id="receiverBankSWIFT" name="receiverBankSWIFT" type="text" value="{receiverBankSWIFT}" size="50"/>
			        <input type="button" class="buttWhite smButt" onclick="javascript:openCountryDictionary(setReceiverBankInfo,getFieldValue('receiverBankSWIFT'));" value="..."/>
		        </td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Код страны<span class="asterisk">*</span></td>
		        <td><input id="receiverBankCountryCode" name="receiverBankCountryCode" type="text" value="{receiverBankCountryCode}" size="50"/><input type="button" class="buttWhite smButt"
							onclick="javascript:openCountryDictionary(setReceiverBankCountryInfo,getFieldValue('receiverBankCountryCode'));" value="..."/>
	            </td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Город банка получателя<span class="asterisk">*</span></td>
		        <td><input id="receiverBankCity" name="receiverBankCity" type="text" value="{receiverBankCity}" size="50"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Адрес банка получателя<span class="asterisk">*</span></td>
		        <td><input id="receiverBankAddress" name="receiverBankAddress" type="text" value="{receiverBankAddress}" size="50"/></td>
	        </tr>
	        <tr>
		        <td class="Width120 LabelAll">Назначение платежа<span class="asterisk">*</span></td>
		        <td><input id="ground" name="ground" type="text" value="{ground}" size="50"/></td>
	        </tr>
            <tr><td colspan="2">&nbsp;</td></tr>
        </table>
        <script type="text/javascript">

	        function setReceiverBankInfo(Info)
	        {
	         setElement("receiverBankSWIFT",Info["SWIFT"]);
	         setElement("receiverBankName",Info["name"]);
             setElement("receiverBankCity",Info["place"]);
	         setElement("receiverBankCountryCode",Info["country"]);
 	         setElement("receiverBankAddress",Info["address"]);
	        }

	        function setReceiverCountryInfo(Info)
	        {
	         setElement("receiverCountryCode",Info["code"]);
	        }

	        function setReceiverBankCountryInfo(Info)
	        {
	         setElement("receiverBankCountryCode",Info["code"]);
	        }

	        var currencyCodes = new Array();
            var accountTypes   = new Array();
	        var accountAmounts = new Array();

	        function updateCurrencyCodes()
            {
                var payerAccountSelect = document.getElementById("payerAccountSelect");
                var code                     = currencyCodes[payerAccountSelect.value];
                var currency   = document.getElementById("amountCurrency");
                currency.value = code;

	            var fromType =  accountTypes[payerAccountSelect.value];
		        var fromAccountType = document.getElementById("fromAccountType");
		        fromAccountType.value = fromType;

				var fromAmount =  accountAmounts[payerAccountSelect.value];
	            var fromAccountAmount = document.getElementById("fromAccountAmount");
	            fromAccountAmount.value = fromAmount;
			}


            <xsl:if test="$personAvailable">
	        function init()
            {
            <xsl:variable name="allAccounts" select="document('foreign-currency-accounts.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
                currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
	            accountTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='type']"/>';
	            accountAmounts['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='amountDecimal']"/>';
            </xsl:for-each>

                updateCurrencyCodes();
            }

            init();
	        </xsl:if>
        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" class="PaymentFon" style="width:540px;">

            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/CurrencyPayment.gif" border="0"/></td>
                <td>
                    <table>
                     <tr>
                        <td align="center" class="silverBott paperTitle">
                            <table width="420" cellspacing="0" cellpadding="0">
                            <tr>
                                <span class="formTitle">Перевод иностранной валюты</span>
                                <br/>
                                <td class="titleHelp"><span id="titleHelp">Перечисление денежных средств с вашего счета на счет получателя в иностранной валюте.</span></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>

			<tr>
				<td class="Width120 LabelAll">Номер перевода</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата перевода</td>
				<td>
					<xsl:value-of select="documentDate"/>
				</td>
			</tr>
			<xsl:variable name="adDate" select="admissionDate"/>
			<xsl:if test="not($adDate = '')">
				<tr>
					<td class="Width120 LabelAll">Дата приема платежа</td>
					<td>
						<xsl:value-of select="admissionDate"/>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td class="Width120 LabelAll">Счет списания</td>
				<td>
					<xsl:value-of select="payerAccountSelect"/>
				   [<xsl:value-of select="fromAccountType"/>]
					<xsl:value-of select="format-number(fromAccountAmount, '0.00')"/>&nbsp;
					<xsl:value-of select="amountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма перевода</td>
				<td><xsl:value-of select="amount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты получателя платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Название получателя платежа</td>
				<td><xsl:value-of select="receiverName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Код страны получателя</td>
				<td><xsl:value-of select="receiverCountryCode"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет получателя</td>
				<td><xsl:value-of select="receiverAccountSelect"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Город</td>
				<td><xsl:value-of select="receiverCity"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Адрес получателя</td>
				<td><xsl:value-of select="receiverAddress"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка получателя</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Название банка</td>
				<td><xsl:value-of select="receiverBankName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">SWIFT</td>
				<td><xsl:value-of select="receiverBankSWIFT"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Код страны</td>
				<td><xsl:value-of select="receiverBankCountryCode"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Город банка получателя</td>
				<td><xsl:value-of select="receiverBankCity"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Адрес банка получателя</td>
				<td><xsl:value-of select="receiverBankAddress"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Назначение платежа</td>
				<td><xsl:value-of select="ground"/><xsl:value-of select="groundAdd"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма комиссии</td>
				<td><xsl:value-of select="commissionAmount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Статус платежа</td>
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
            <tr><td colspan="2">&nbsp;<br/>&nbsp;</td></tr>
        </table>
	<xsl:if test="(state='E') or (state='D') and (stateDescription != '')">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа:<xsl:value-of select="stateDescription"/>
		</div>
	</xsl:if>
    </xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='W'">На исполнении</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:when test="$code='D'">Отказан</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='V'">Отозван</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->