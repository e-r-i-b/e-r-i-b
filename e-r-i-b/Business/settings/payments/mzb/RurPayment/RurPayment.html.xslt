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
			
	function calculateNDS()
	{
	    var amount = checkSum("amount", "Сумма", false);
		if (amount == null)
		{
	    	alert(sumErr);
			return;
		}

		var NDSFieldValue =  checkSum("NDS", "Размер НДС", false);
		if (NDSFieldValue == null)
		{
	    	alert(sumErr);
			return;
		}
		else
		{
			var nds = amount*NDSFieldValue/(100 + NDSFieldValue);
			document.getElementById("ndsValue").value =  nds.toFixed(2);
		}
    }

	function selectNDS(nullNDS)
	{
		if(nullNDS)
		{
	        document.getElementById("NDS").disabled = true;
			document.getElementById("NDS").value = 0;
			document.getElementById("hiddenNDS").value = 0;
			document.getElementById("ndsValue").value = 0;
			document.getElementById("ndsValue").disabled = true;
			documemt.getElementById
		}
		else
		{
			document.getElementById("NDS").disabled = false;
			document.getElementById("NDS").value = 0;
			document.getElementById("hiddenNDS").value = 0;
			document.getElementById("ndsValue").value = 0;
			document.getElementById("ndsValue").disabled = false;
		}
	}

	function selectTypePerson(type)
	{
		if (type == "Jur")
		{
			document.getElementById("receiverTypeName").innerHTML = "Название организации&lt;span class='asterisk'&gt;*&lt;/span&gt;";
			document.getElementById("receiverKPPIdent").innerHTML = "КПП получателя платежа";
			document.getElementById("receiverINNIdent").innerHTML = "ИНН получателя платежа&lt;span class='asterisk'&gt;*&lt;/span&gt;";
			tmp = document.getElementById("receiverINN").value;
			tmp = tmp.length > 10?tmp.substring(0,10):tmp;
			document.getElementById("receiverINN").maxLength = "10";
	        document.getElementById("receiverINN").value = tmp;
			document.getElementById("receiverKPP").disabled = false;
			document.getElementById("radioNDS").disabled = false;
			document.getElementById("ndsValue").disabled = false;
			document.getElementById("NDS").disabled = false;
			document.getElementById("radioNDS").checked = true;
			calculateNDS();
		}
		if (type == "Ph")
		{
			document.getElementById("receiverTypeName").innerHTML = "Ф.И.О.&lt;span class='asterisk'&gt;*&lt;/span&gt;";
			document.getElementById("receiverINNIdent").innerHTML = "ИНН получателя платежа";
	        document.getElementById("receiverKPPIdent").innerHTML = "КПП получателя платежа";
	        document.getElementById("receiverINN").maxLength = "12";
			document.getElementById("receiverKPP").value = "";
			document.getElementById("receiverKPP").disabled = true;
	        document.getElementById("radioNDS").disabled = true;
			document.getElementById("radioNoNDS").checked = true;
			selectNDS(true);
		}
	}
</script>

<input id="fromAccountCurrency" name="fromAccountCurrency" type="hidden"/>
<input id="fromAccountAmount"   name="fromAccountAmount"   type="hidden"/>
<input id="fromAccountType"     name="fromAccountType"     type="hidden"/>

<input id="documentNumber" name="documentNumber" type="hidden">
	<xsl:attribute name="value">
		<xsl:value-of select="documentNumber"/>
	</xsl:attribute>
</input>

<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
<tr>
	<td align="right" valign="middle"><img src="{$resourceRoot}/images/RurPayment.gif" border="0"/></td>
	<td colspan="2">
		<table class="MaxSize paymentTitleFon">
			<tr>
				<td align="center" style="border-bottom:1 solid silver" class="paperTitle">
					<table height="100%" width="420" cellspacing="0" cellpadding="0" class="paymentTitleFon">
						<tr>
							<td class="titleHelp">
								<span class="formTitle">Перевод рублей РФ</span>
								<br/>
								Перевод денежных средств с вашего счета на счет физического лица или юридического лица. 
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
    <td colspan="2" align="right">
	    <a href="/PhizIC/private/payments/payment.do?form=TaxPayment" style="text-decoration:underline;">Осуществить налоговый платеж </a>
    </td>

	</tr>
	</xsl:if>
<tr>
	<td class="Width120 LabelAll">Номер документа<span class="asterisk">*</span></td>
	<td>
		<input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}"
		       disabled="true"/>
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
<tr>
	<td class="Width120 LabelAll">Счет списания<span class="asterisk">*</span></td>
	<td>
		<xsl:variable name="payerAccount" select="payerAccountSelect"/>
		<xsl:if test="$personAvailable">
			<select id="payerAccountSelect" name="payerAccountSelect" onChange="javascript:updateCurrencyCodes()">
				<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
					<option>
						<xsl:attribute name="value">
							<xsl:value-of select="./@key"/>
						</xsl:attribute>
						<xsl:if test="$payerAccount = ./@key">
							<xsl:attribute name="selected">true</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./@key"/>
						&nbsp;
						[
						<xsl:value-of select="./field[@name='type']"/>
						]
						<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
						&nbsp;
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
	<td class="Width120 LabelAll">Сумма<span class="asterisk">*</span></td>
	<td>
		<input onChange="calculateNDS();" id="amount" name="amount" type="text" value="{amount}" size="10"/>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;
		<b>Реквизиты получателя платежа</b>
	</td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td>
		<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="50%">
					<input type="radio" id="typePersonJur" name="receiverType" value="Jur" onclick="selectTypePerson('Jur')">
						<xsl:if test="receiverType='Jur'">
							<xsl:attribute name="checked"/>
						</xsl:if>
						Юридическое лицо
					</input><br/>
				</td>
				<td>
					<input type="radio" id="typePersonPh" name="receiverType" value="Ph" onclick="selectTypePerson('Ph')">
						<xsl:if test="receiverType='Ph'">
							<xsl:attribute name="checked"/>
						</xsl:if>
						Физическое лицо
					</input>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll" id="receiverTypeName">
		<xsl:if test="receiverType='Ph'">
			Ф.И.О.<span class='asterisk'>*</span>
		</xsl:if>
		<xsl:if test="receiverType='Jur'">
			Название организации<span class="asterisk">*</span>
		</xsl:if>
	</td>
	<td><input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="60"/>
		<xsl:if test="$personAvailable">
		<input type="button" class="buttWhite" style="height:18px;width:18;"
			onclick="javascript:openSelectReceiver();" value="..."/>
		</xsl:if>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll" id="receiverINNIdent">ИНН получателя платежа<span class="asterisk">*</span></td>
	<td><input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="20" maxlength="10"/>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll" id="receiverKPPIdent">
		КПП получателя платежа
	</td>
	<td><input id="receiverKPP" name="receiverKPP" type="text" value="{receiverKPP}" size="20" maxlength="9">
		<xsl:if test="receiverType='Ph'">
			<xsl:attribute name="disabled">
				true
			</xsl:attribute>
		</xsl:if>
	</input></td>
</tr>
<tr>
	<td class="Width120 LabelAll">Счет получателя<span class="asterisk">*</span></td>
	<td><input id="receiverAccount" name="receiverAccount" type="text" value="{receiverAccount}"
	           maxlength="20" size="24"/></td>
</tr>
<tr>
	<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка получателя</b>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll" id="receiverTypeName">Наименование<span class="asterisk">*</span></td>
	<td><input id="receiverBank" name="receiverBank" type="text" value="{receiverBank}" size="60"/></td>
</tr>
<tr>
	<td class="Width120 LabelAll">БИК<span class="asterisk">*</span></td>
	<td><input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" size="14" maxlength="9"/>
		<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('receiverBank'),getFieldValue('receiverBIC'));" value="..."/>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll">Кор. счет<span class="asterisk">*</span></td>
	<td><input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}"
	           size="24" maxlength="20"/></td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
	<td class="Width120 LabelAll">Назначение платежа<span class="asterisk">*</span></td>
	<td><input id="ground" name="NDSText" type="text" value="{NDSText}" size="60" maxlength="200"/></td>
</tr>
<tr>
	<td class="Width120 LabelAll">&nbsp;</td>
	<td style="font-size:7pt;">Например: перевод денежных средств на счет физ. лица</td>
</tr>
<tr>
	<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>НДС</b>
	</td>
</tr>
<tr>

	<td colspan="2" style="padding-left:29px">
		<input type="radio" id="radioNDS" name="chargeNDS" value="yes" onclick="selectNDS(false);">
			<xsl:if test="chargeNDS='yes'">
				<xsl:attribute name="checked"/>
			</xsl:if>
			<xsl:if test="receiverType='Ph'">
				<xsl:attribute name="disabled"/>
			</xsl:if>
		</input>
		В т.ч. НДС &nbsp;
		<input id="NDS" name="NDS" onChange="calculateNDS();" type="text" value="{NDS}" size="10" maxlength="9">
			<xsl:if test="chargeNDS='no'">
				<xsl:attribute name="disabled"/>
			</xsl:if>
		</input>%
	    <input id="ndsValue" size="10" maxlength="9" name="NDSSum" value="{NDSSum}">
		    <xsl:if test="chargeNDS='no'">
				<xsl:attribute name="disabled"/>
			</xsl:if>
	    </input>p.
	</td>
</tr>
<tr >
	<td colspan="2" style="padding-left:29px">
	<input type="radio" id="radioNoNDS" name="chargeNDS" value="no" onclick="selectNDS(true);">
		<xsl:if test="chargeNDS='no'">
				<xsl:attribute name="checked"/>
			</xsl:if>
	</input>
	НДС не облагается
	</td>
</tr>
</table>
<input type="hidden" id="hiddenNDS" name="NDS" value="{NDS}"/>
<script type="text/javascript">
	function openSelectReceiver()
	{
		window.open(document.webRootPrivate + '/receivers/list.do?action=getReceiverInfo',
				'Receivers', "fullscreen=yes,resizable=1,menubar=0,toolbar=0,scrollbars=1");
	}

	function setReceiverInfo(Info)
	{
		document.getElementById('typePersonJur').checked = (Info["type"]=='J');
		document.getElementById('typePersonPh').checked = (Info["type"]=='I');
		if(Info["type"]=='I')selectTypePerson('Ph');
		if(Info["type"]=='J')selectTypePerson('Jur');
		setElement("receiverName", Info["receiverName"]);
		setElement("receiverINN", Info["receiverINN"]);
		setElement("receiverKPP", Info["receiverKPP"]);
		setElement("receiverAccount", Info["receiverAccount"]);
		setElement("receiverBIC", Info["BIC"]);
		setElement("receiverBank", Info["name"]);
		setElement("receiverCorAccount", Info["account"]);
	}

	function setBankInfo(Info)
	{
		setElement("receiverBIC", Info["BIC"]);
		setElement("receiverBank", Info["name"]);
		setElement("receiverCorAccount", Info["account"]);
		//			 setElement("ground",Info["ground"]);
	}

		    var currencyCodes = new Array();
            var accountTypes = new Array();
	        var accountAmounts = new Array();

	        function updateCurrencyCodes()
            {
                var payerAccountSelect = document.getElementById("payerAccountSelect");
                var fromCurrency = currencyCodes[payerAccountSelect.value];
                var fromAccountCurrency = document.getElementById("fromAccountCurrency");
                fromAccountCurrency.value = fromCurrency;

	            var fromType = accountTypes[payerAccountSelect.value];
		        var fromAccountType = document.getElementById("fromAccountType");
		        fromAccountType.value = fromType;

				var fromAmount = accountAmounts[payerAccountSelect.value];
	            var fromAccountAmount = document.getElementById("fromAccountAmount");
	            fromAccountAmount.value = fromAmount;
			}

			<xsl:if test="$personAvailable">
	        function init()
            {
            <xsl:variable name="allAccounts" select="document('all-accounts.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
                currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
	            accountTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='type']"/>';
	            accountAmounts['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='amountDecimal']"/>';

            </xsl:for-each>
			updateCurrencyCodes()
            }

			init();
           </xsl:if>
</script>
</xsl:template>	
<xsl:template match="/form-data" mode="view">
	<table cellspacing="2" cellpadding="0" class="PaymentFon" style="width:540px;">
		<tr>
			<td align="right" valign="middle"><img src="{$resourceRoot}/images/InternalPayment.gif"
			                                       border="0"/></td>
			<td>
				<table>
					<tr>
						<td align="center" class="silverBott paperTitle">
							<table width="420" cellspacing="0" cellpadding="0">
								<tr>
									<span class="formTitle">Перевод рублей РФ</span>
									<br/>
									<td class="titleHelp"><span id="titleHelp">Перевод денежных средств с вашего счета на счет физического лица или юридического лица.</span></td>
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
			<td>
				<xsl:value-of select="amount"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Счет списания</td>
			<td>
				<xsl:value-of select="payerAccountSelect"/>
			    [<xsl:value-of select="fromAccountType"/>]
				<xsl:value-of select="format-number(fromAccountAmount, '0.00')"/>&nbsp;
				<xsl:value-of select="fromAccountCurrency"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты получателя
				платежа</b></td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">
				<xsl:if test="receiverType='Ph'">
					Ф.И.О.
				</xsl:if>
				<xsl:if test="receiverType='Jur'">
					Наименование
				</xsl:if>

			</td>
			<td>
				<xsl:value-of select="receiverName"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">ИНН получателя платежа</td>
			<td>
				<xsl:value-of select="receiverINN"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">КПП получателя платежа</td>
			<td>
				<xsl:value-of select="receiverKPP"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Счет получателя</td>
			<td>
				<xsl:value-of select="receiverAccount"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка
				получателя</b></td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Наименование</td>
			<td>
				<xsl:value-of select="receiverBank"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">БИК</td>
			<td>
				<xsl:value-of select="receiverBIC"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Кор. счет</td>
			<td>
				<xsl:value-of select="receiverCorAccount"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Размер НДС</td>
			<td>
				<xsl:value-of select="NDS"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Назначение платежа</td>
			<td>
				<xsl:value-of select="ground"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Сумма комиссии</td>
			<td>
				<xsl:value-of select="commissionAmount"/>
			</td>
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
		<tr>
			<td colspan="2">&nbsp;<br/>&nbsp;</td>
		</tr>
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
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="yes" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
