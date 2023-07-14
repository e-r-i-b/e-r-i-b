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
		var amount = document.getElementById("amount").value;
		if (document.getElementById("typePersonJur").checked){
            var nds = 0.18 * amount;
            document.getElementById("ground").value = "В том числе НДС: " + nds.toFixed(2) + " руб.";
		}else{
	        document.getElementById("ground").value = "НДС не облагается.";
        }
    }

	function selectTypePerson(type)
	{
		if (type == "Jur"){
			document.getElementById("receiverTypeName").innerHTML = "Наименование&lt;span class='asterisk'&gt;*&lt;/span&gt;";
			document.getElementById("receiverKPPIdent").innerHTML = "КПП&lt;span class='asterisk'&gt;*&lt;/span&gt;";
			tmp = document.getElementById("receiverINN").value;
			tmp = tmp.length > 10?tmp.substring(0,10):tmp;
			document.getElementById("receiverINN").maxLength = "10";
	        document.getElementById("receiverINN").value = tmp;
	        document.getElementById("receiverKPP").disabled = false;
			document.getElementById("receiverType").value = "Jur";
		}
		if (type == "Ph"){
			document.getElementById("receiverTypeName").innerHTML = "Ф.И.О.&lt;span class='asterisk'&gt;*&lt;/span&gt;";
	        document.getElementById("receiverKPPIdent").innerHTML = "КПП&nbsp;";
	        document.getElementById("receiverINN").maxLength = "12";
	        document.getElementById("receiverKPP").disabled = true;
			document.getElementById("receiverType").value = "Ph";
		}
		calculateNDS();
	}

	function isFieldValue()
	{
		var INN = document.getElementById("receiverINN").value;
		var KPP = document.getElementById("receiverKPP").value;
		if (document.getElementById("typePersonJur").checked)
		{
			if (INN == "" &amp;&amp; KPP == "")
			{
				alert("Введите значение в поле ИНН получателя платежа\n" +
				"Введите значение в поле КПП получателя платежа");
				return false;
			}
			else if (INN == "")
			{
				alert("Введите значение в поле ИНН получателя платежа");
				return false;
			}
			else if (KPP == "")
			{
				alert("Введите значение в поле КПП получателя платежа");
				return false;
			}
	        else if (INN.length != 10)
			{
				alert("Поле ИНН должно состоять из 10 цифр");
				return false;
			}
	        else if (KPP.length != 9)
			{
				alert("Поле КПП должно состоять из 9 цифр");
				return false;
			}
			else return true;
		}
		else if (document.getElementById("typePersonPh").checked)
	    {
	        if (INN == "")
			{
				alert("Введите значение в поле ИНН получателя платежа");
				return false;
			}
	        else if (INN.length != 12)
			{
				alert("Поле ИНН должно состоять из 12 цифр");
				return false;
			}
	        else return true;
	    }
	    else return false;
	}

	var btnSave = findCommandButton("button.save");
	btnSave.setValidationFunction(isFieldValue);
</script>

	<!--TODO зачем это скрытое поле?!! если ниже есть с таким же именем и ID(!!!!!) нескрытое?!!!-->
<input id="documentNumber" name="documentNumber" type="hidden">
	<xsl:attribute name="value">
		<xsl:value-of select="documentNumber"/>
	</xsl:attribute>
</input>
<input id="receiverType" name="receiverType" type="hidden">
	<xsl:attribute name="value">
		<xsl:value-of select="receiverType"/>
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
	<td class="Width120 LabelAll">Сумма<span class="asterisk">*</span></td>
	<td>
		<input onChange="calculateNDS();" id="amount" name="amount" type="text" value="{amount}" size="10"/>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll">Счет списания<span class="asterisk">*</span></td>
	<td>
		<xsl:variable name="payerAccount" select="payerAccountSelect"/>
		<xsl:if test="$personAvailable">
			<select id="payerAccountSelect" name="payerAccountSelect">
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
	<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты получателя
		платежа</b></td>
</tr>
<xsl:variable name="receiverType" select="receiverType"/>
<tr>
	<td>&nbsp;</td>
	<td>
		<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="50%">
					<input type="radio" id="typePersonJur" name="typePerson"
					       onclick="selectTypePerson('Jur')">Юридическое лицо</input><br/>
				</td>
				<td>
					<input type="radio" id="typePersonPh" name="typePerson" onclick="selectTypePerson('Ph')">
						<xsl:if test="$receiverType = 'Ph'">
							<xsl:attribute name="checked">true</xsl:attribute>
						</xsl:if>
						Физическое лицо</input>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll" id="receiverTypeName">Наименование<span class="asterisk">*</span></td>
	<td><input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="60"/>
		<xsl:if test="$personAvailable">
		<input type="button" class="buttWhite smButt"
			onclick="javascript:openSelectReceiver();" value="..."/>
		</xsl:if>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll">ИНН<span class="asterisk">*</span></td>
	<td><input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="20" maxlength="12"/>
	</td>
</tr>
<tr>
	<td class="Width120 LabelAll" id="receiverKPPIdent">КПП<span class="asterisk">*</span></td>
	<td><input id="receiverKPP" name="receiverKPP" type="text" value="{receiverKPP}" size="20" maxlength="9"/></td>
</tr>
<tr>
	<td class="Width120 LabelAll">Счет<span class="asterisk">*</span></td>
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
<tr>
	<td class="Width120 LabelAll">Назначение платежа<span class="asterisk">*</span></td>
	<td><input id="ground" name="ground" type="text" value="{ground}" size="60"/></td>
</tr>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
</table>
<script type="text/javascript">
	function openSelectReceiver()
	{
		window.open(document.webRootPrivate + '/receivers/list.do?action=getReceiverInfo',
				'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
	}

	function setReceiverInfo(Info)
	{
		setElement("receiverName", Info["receiverName"]);
		setElement("receiverINN", Info["receiverINN"]);
		if (document.getElementById("typePersonJur").checked)
	    {
	       setElement("receiverKPP", Info["receiverKPP"]);
		}
	    else setElement("receiverKPP", "");
	    setElement("receiverAccount", Info["receiverAccount"]);
		setElement("receiverBIC", Info["BIC"]);
		setElement("receiverBank", Info["name"]);
		setElement("receiverCorAccount", Info["account"]);
		//			 setElement("ground",Info["ground"]);
	}

	function setBankInfo(Info)
	{
		setElement("receiverBIC", Info["BIC"]);
		setElement("receiverBank", Info["name"]);
		setElement("receiverCorAccount", Info["account"]);
		//			 setElement("ground",Info["ground"]);
	}

	function checkSelectTypePerson()
	{
		if(document.getElementById("receiverType").value == "Ph")
	    {
	        selectTypePerson("Ph");
	    }
	    else
	    {
	        document.getElementById("typePersonJur").checked = true;
	        selectTypePerson("Jur");
	    }
	}
	
	checkSelectTypePerson();
	calculateNDS();
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
									<td class="titleHelp"><span id="titleHelp"></span></td>
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
				<xsl:variable name="payerAcc" select="payerAccountSelect"/>
				<!-- TODO на форме просмотра данные должны браться из базы -->
				<xsl:variable name="payerAccount"
				              select="document('all-accounts.xml')/entity-list/entity[@key=$payerAcc]"/>
				<xsl:value-of select="payerAccountSelect"/>
				[
				<xsl:value-of select="$payerAccount/field[@name='type']"/>
				]
				<xsl:value-of select="$payerAccount/field[@name='currencyCode']"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты получателя
				платежа</b></td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Наименование</td>
			<td>
				<xsl:value-of select="receiverName"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">ИНН</td>
			<td>
				<xsl:value-of select="receiverINN"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">КПП</td>
			<td>
				<xsl:value-of select="receiverKPP"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">Счет</td>
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
				<td>
					<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state"/>
					</xsl:with-param>
				</xsl:call-template>
				</td>
			</tr>
		<tr>
			<td colspan="2">&nbsp;<br/>&nbsp;</td>
		</tr>
	</table>
</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='S'">Отправлен в банк получателя</xsl:when>
			<xsl:when test="$code='A'">Получен банком получателем</xsl:when>
			<xsl:when test="$code='W'">Принят</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="yes" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->