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
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="fromAccountAmount" name="fromAccountAmount" type="hidden"/>
		<input id="fromAccountType"   name="fromAccountType"   type="hidden"/>
		<input id="amountCurrency"    name="amountCurrency"    type="hidden"/>
		<input id="documentNumber" name="documentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="documentNumber"/>
	            </xsl:attribute>
		</input>
		<table cellspacing="2" cellpadding="0" border="0"  class="paymentFon">
		     <tr>
		        <td align="right" valign="middle"><img src="{$resourceRoot}/images/TaxPayment.gif" border="0"/></td>
		        <td colspan="2" >
		            <table class="MaxSize paymentTitleFon">
		            <tr><td colspan="2">&nbsp;</td></tr>
		            <tr>
		                <td align="center" class="paperTitle silverBott">
		                    <table height="100%" width="420" cellspacing="0" cellpadding="0" class="paymentTitleFon">
		                    <tr>
		                        <td class="titleHelp">
		                             <span class="formTitle">Оплата налогов</span>
		                             <br/>
			                        Перечисление денежных средств с вашего счета в счет уплаты налогов и сборов.		                             
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
					<td class="Width120 LabelAll">Номер документа<span class="asterisk">*</span></td>
					<td>
						<input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Дата документа<span class="asterisk">*</span></td>
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
				<td class="Width120 LabelAll">Сумма<span class="asterisk">*</span></td>
				<td><input id="amount" name="amount" type="text" value="{amount}" size="10"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет списания<span class="asterisk">*</span></td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:updateAccount()">
							<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
								<xsl:variable name="key" select="./@key"/>
								
								<xsl:if test="starts-with($key,'40817') or starts-with($key,'40820')">
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
								</xsl:if>
							</xsl:for-each>
						</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select disabled="disabled"><option selected="selected">Счет клиента&nbsp;</option></select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты получателя платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование<span class="asterisk">*</span></td>
				<td>
					<input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="60"/>
					<xsl:if test="$personAvailable">
						<input type="button" class="buttWhite smButt"  onclick="javascript:openSelectReceiver();" value="..."/>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">ИНН<span class="asterisk">*</span></td>
				<td><input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="20" maxlength="10"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">КПП<span class="asterisk">*</span></td>
				<td><input id="receiverKPP" name="receiverKPP" type="text" value="{receiverKPP}" size="20" maxlength="9"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет<span class="asterisk">*</span></td>
				<td><input id="receiverAccount" name="receiverAccount" type="text" value="{receiverAccount}" maxlength="20" size="24"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка получателя</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование<span class="asterisk">*</span></td>
				<td><input id="receiverBank" name="receiverBank" type="text" value="{receiverBank}" size="60"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">БИК<span class="asterisk">*</span></td>
				<td><input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" size="14" maxlength="9"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('receiverBank'),getFieldValue('receiverBIC'));" value="..."/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Кор. счет&nbsp;</td>
				<td><input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" size="24" maxlength="20"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Назначение платежа<span class="asterisk">*</span></td>
				<td><input id="ground" name="ground" type="text" value="{ground}" size="60"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Налоговые поля</b></td>
			</tr>
		    <tr>
			    <td colspan="2">
				    <table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="Width120 LabelAll">Статус<span class="asterisk">*</span></td>
						<td width="80px"><input id="taxStatus" name="taxStatus" type="text" value="13" size="5" disabled="true"/>
							<!--<input type="button" class="buttWhite smButt"-->
										<!--onclick="javascipt:displayPeriodList(event,'taxstatus')" value="..."/>-->
										</td>
						<td class="Width120 LabelAll">КБК<span class="asterisk">*</span></td>
						<td><input id="taxKBK" name="taxKBK" type="text" value="{taxKBK}" size="25"/>
							<input type="button" class="buttWhite smButt" onclick="javascript:openKBKDictionary(setKBKInfo,getFieldValue('taxKBK'));" value="..."/>
					   </td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">Основание<span class="asterisk">*</span></td>
						<td><input id="taxGround" name="taxGround" type="text" value="{taxGround}" size="5"  onkeydown="enterNumericFld(event,this);" disabled="true"/><input type="button" class="buttWhite smButt"
										onclick="javascipt:displayPeriodList(event,'taxfund')" value="..."/></td>
						<td class="Width120 LabelAll">ОКАТО<span class="asterisk">*</span></td>
						<td><input id="taxOKATO" name="taxOKATO" type="text" value="{taxOKATO}" size="25"/></td>
					</tr>
					 <tr>
						<td class="Width120 LabelAll">Тип<span class="asterisk">*</span></td>
						<td><input id="taxType" name="taxType" type="text" value="{taxType}" size="5"  onkeydown="enterNumericFld(event,this);" disabled="true"/><input type="button" class="buttWhite smButt"
										onclick="javascipt:displayPeriodList(event,'taxtype')" value="..."/></td>
						<td class="Width120 LabelAll">Дата<span class="asterisk">*</span></td>
						<td><input id="taxDocumentDate" name="taxDocumentDate" type="text" value="{taxDocumentDate}" size="10"/></td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">Период<span class="asterisk">*</span></td>
						<td>
							<input id="taxPeriod1" name="taxPeriod1" type="text" maxLength="2" size="2" value="{taxPeriod1}"/>.
							<input id="taxPeriod2" name="taxPeriod2" type="text" onkeydown="enterNumericTemplateFld(event,this,'__');"
                                   maxLength="2" size="2" value="{taxPeriod2}"/>.
							<input id="taxPeriod3" name="taxPeriod3" type="text" onkeydown="enterNumericTemplateFld(event,this,'____');"
                                   maxLength="4" size="4" value="{taxPeriod3}"/>
                            
						    <input type="button" class="buttWhite smButt"
										onclick="javascipt:displayPeriodList(event,'periodfill')" value="..."/></td>
						<td class="Width120 LabelAll">Номер<span class="asterisk">*</span></td>
						<td><input id="taxDocumentNumber" name="taxDocumentNumber" type="text" value="{taxDocumentNumber}" size="10"/></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>
		<input type="hidden" id="hiddenTaxType" name="taxType" value="{taxType}"/>
		<input type="hidden" id="hiddenTaxGround" name="taxGround" value="{taxGround}"/>
		<input type="hidden" id="hiddenTaxStatus" name="taxStatus" value="{taxStatus}"/>
		<script type="text/javascript">
			function openSelectReceiver()
			{
			   win=window.open( document.webRootPrivate + '/receivers/list.do?action=getReceiverInfo',
				'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1, height=450, width=820");
			   win.moveTo(screen.width/2-410, screen.height/2-225);
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
				win=window.open(document.webRootPrivate + "/PD4.do?action="+action,"","resizable=0,menubar=0,toolbar=0,scrollbars=0,status=0");
				win.resizeTo(800,263);
				win.moveTo(screen.width/2-400,screen.height/2-130);
			}
			function setTaxStatus(taxInfo)
			{
			  setElement('taxStatus',taxInfo["taxStatus"]);
			}
			function setTaxType(taxInfo)
			{
			  setElement('taxType',taxInfo["taxType"]);
			  setElement('hiddenTaxType',taxInfo["taxType"]);
			}
			function setTaxFund(taxInfo)
			{
			  setElement('taxGround',taxInfo["taxFund"]);
			  setElement('hiddenTaxGround',taxInfo["taxFund"]);
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

			var accountTypes   = new Array();
			var accountAmounts = new Array();
			var currencyCodes = new Array();

			function updateAccount()
            {
                var fromAccountSelect = document.getElementById("payerAccountSelect");
                var code              = currencyCodes[fromAccountSelect.value];
                var amountCurrency    = document.getElementById("amountCurrency");
                amountCurrency.value  = code;

			    var fromType =  accountTypes[fromAccountSelect.value];
		        var fromAccountType = document.getElementById("fromAccountType");
		        fromAccountType.value = fromType;

				var fromAmount =  accountAmounts[fromAccountSelect.value];
	            var fromAccountAmount = document.getElementById("fromAccountAmount");
	            fromAccountAmount.value = fromAmount;
			}
			function init()
            {
            <xsl:variable name="allAccounts" select="document('all-accounts.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
                currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
	            accountTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='type']"/>';
	            accountAmounts['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='amountDecimal']"/>';
            </xsl:for-each>
			    setElement('hiddenTaxStatus',13);
				setElement('taxKBK',0);
				setElement('taxOKATO',0);
				setElement('taxGround',0);
				setElement('taxPeriod1',0);
				setElement('taxDocumentNumber',0);
				setElement('taxDocumentDate',0);
				setElement('taxType',0);
				setElement('hiddenTaxType',0);
				setElement('hiddenTaxGround',0);
                updateAccount();
            }

            init();

			function checkData()
			{
				var cc = new Array();
				var i = 0;
				<xsl:for-each select="document('kbkList.xml')/entity-list/*">
					cc[i] = '<xsl:value-of select="./field[@name='code']"/>';
					i++;
				</xsl:for-each>
				var kbk = document.getElementById("taxKBK").value;
				if(kbk=='0')
					return true;
				for(var j = 0; j  &lt; cc.length;j++)
					if(cc[j] == kbk)
						return true;
				return confirm("Код КБК не найден в справочнике.\n Продолжить сохранение платежа?");
			}

		</script>

	</xsl:template>
    <xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" class="PaymentFon" style="width:540px;">

            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/TaxPayment.gif" border="0"/></td>
                <td>
                    <table>
                     <tr>
                        <td align="center" style="border-bottom:1 solid silver"  class="paperTitle">
                            <table width="420" cellspacing="0" cellpadding="0">
                            <tr>
                                <span class="formTitle">Оплата налогов</span>
                                <br/>
                                <td class="titleHelp"><span id="titleHelp">Перечисление денежных средств с вашего счета в счет уплаты налогов и сборов.</span></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>

			<tr>
				<td class="Width120 LabelAll">Номер документа</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата документа</td>
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
				<td class="Width120 LabelAll">Сумма</td>
				<td><xsl:value-of select="amount"/></td>
			</tr>
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
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты получателя платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td><xsl:value-of select="receiverName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">ИНН</td>
				<td><xsl:value-of select="receiverINN"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">КПП</td>
				<td><xsl:value-of select="receiverKPP"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет</td>
				<td><xsl:value-of select="receiverAccount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка получателя</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td><xsl:value-of select="receiverBank"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">БИК</td>
				<td><xsl:value-of select="receiverBIC"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Кор. счет</td>
				<td><xsl:value-of select="receiverCorAccount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Назначение платежа</td>
				<td><xsl:value-of select="ground"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Налоговые поля</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Статус</td>
				<td><xsl:value-of select="taxStatus"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">КБК</td>
				<td><xsl:value-of select="taxKBK"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">ОКАТО</td>
				<td><xsl:value-of select="taxOKATO"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Основание</td>
				<td><xsl:value-of select="taxGround"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Тип</td>
				<td><xsl:value-of select="taxType"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата</td>
				<td><xsl:value-of select="taxDocumentDate"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Номер</td>
				<td><xsl:value-of select="taxDocumentNumber"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Период</td>
				<td><xsl:value-of select="taxPeriod1"/>.<xsl:value-of select="taxPeriod2"/>.<xsl:value-of select="taxPeriod3"/></td>
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
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
