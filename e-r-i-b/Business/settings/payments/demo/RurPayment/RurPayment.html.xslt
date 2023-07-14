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
<script type="text/javascript">
	document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
			
    function cvtSum(amount)
      {
           var pos = amount.indexOf(".");
           if (pos > 0)
           {
              if (amount.substring(pos + 1) == "00")
                 return amount.replace(".00", "=");
              else
                 return amount.replace(".", "-");
           }
           else
               return amount + "=";
     }

	function calculateNDS()
	{
       var amount = document.getElementById("amount");
       var ground = document.getElementById("ground");
       var groundTmp = document.getElementById("groundTmp");
       var Nds = document.getElementById("NDSAmount");

       if (document.getElementById("typePersonJur").checked)
       {
          var nds = 18 * amount.value/118;
          Nds.value = "В том числе НДС: " + cvtSum(nds.toFixed(2)) + " руб."
       }
       else
       {
          Nds.value = "НДС не облагается.";
       }
       ground.value = groundTmp.value + " "+ Nds.value;
    }

	function selectTypePerson(type)
	{
		if (type == "Jur"){
			document.getElementById("receiverTypeName").innerHTML = "Наименование";
			document.getElementById("asterisk_kpp").style.display = "";
			tmp = document.getElementById("receiverINN").value;
			tmp = tmp.length > 10?tmp.substring(0,10):tmp;
			document.getElementById("receiverINN").maxLength = "10";
	        document.getElementById("receiverINN").value = tmp;
	        document.getElementById("receiverKPP").disabled = false;
			document.getElementById("receiverType").value = "Jur";
		}
		if (type == "Ph"){
			document.getElementById("receiverTypeName").innerHTML = "Ф.И.О.&lt;span class='asterisk'&gt;*&lt;/span&gt;";
			document.getElementById("asterisk_kpp").style.display = "none";
	        document.getElementById("receiverINN").maxLength = "12";
	        document.getElementById("receiverKPP").disabled = true;
            document.getElementById("receiverKPP").value = "";
			document.getElementById("receiverType").value = "Ph";
		}
        parseGround ();
		calculateNDS();
	}

    function parseGround ()
    {
       var amount = document.getElementById("amount");
       var ground = document.getElementById("ground");
       var groundTmp = document.getElementById("groundTmp");
       var Nds = document.getElementById("NDSAmount");
       var strNDS1 = "В том числе НДС:"; var strNDS2 = "НДС не облагается";
       var pos = ground.value.indexOf(strNDS1);
       if (pos >= 0)
       {
          groundTmp.value = ground.value.substr(0, pos);
          Nds.value = ground.value.substr(pos, ground.value.length - pos);
       }
       else
       {
          pos = ground.value.indexOf(strNDS2);
          if (pos >= 0)
          {
             groundTmp.value = ground.value.substr(0, pos);
             Nds.value = strNDS2;
          }
          else
          {
             groundTmp.value = ground.value;
             Nds.value = strNDS2;
             ground.value = ground.value+" "+Nds.value;
          }
       }
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
	if (findCommandButton("button.save"))
		btnSave.setValidationFunction(isFieldValue);
</script>

<input id="ground" name="ground" type="hidden">
	<xsl:attribute name="value">
		<xsl:value-of select="ground"/>
	</xsl:attribute>
</input>
<input id="receiverType" name="receiverType" type="hidden">
	<xsl:attribute name="value">
		<xsl:value-of select="receiverType"/>
	</xsl:attribute>
</input>
<xsl:if test="$personAvailable">
	<tr>
		<td colspan="2" align="right">
			<a href="/PhizIC/private/payments/payment.do?form=TaxPayment" style="text-decoration:underline;">Осуществить налоговый платеж </a>
		</td>
	</tr>
</xsl:if>

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

	<xsl:call-template name="standartRow">
		<xsl:with-param name="rowName">Сумма</xsl:with-param>
		<xsl:with-param name="rowValue"><input onChange="calculateNDS();" id="amount" name="amount" type="text" value="{amount}" size="10"/></xsl:with-param>
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
				<select  id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
					<option value="" selected="selected">Счет клиента</option>
				</select>
			</xsl:if>
		</xsl:with-param>
	</xsl:call-template>

	<xsl:call-template name="titleRow">
		<xsl:with-param name="rowName">Реквизиты получателя платежа</xsl:with-param>
	</xsl:call-template>

	<xsl:call-template name="standartRow">
		<xsl:with-param name="required" select="'false'"/>
		<xsl:with-param name="rowName">&nbsp;</xsl:with-param>
		<xsl:with-param name="rowValue">
			<xsl:variable name="receiverType" select="receiverType"/>
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
		</xsl:with-param>
	</xsl:call-template>

	<xsl:call-template name="standartRow">
		<xsl:with-param name="id" select="'receiverTypeName'"/>
		<xsl:with-param name="rowName">Наименование</xsl:with-param>
		<xsl:with-param name="rowValue">
			<input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="60"/>
			<xsl:if test="$personAvailable">
			<input type="button" class="buttWhite smButt"
				onclick="javascript:openSelectReceiver();" value="..."/>
			</xsl:if>
		</xsl:with-param>
	</xsl:call-template>

	<xsl:call-template name="standartRow">
		<xsl:with-param name="rowName">ИНН</xsl:with-param>
		<xsl:with-param name="rowValue"><input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="20" maxlength="12"/></xsl:with-param>
	</xsl:call-template>

	<xsl:call-template name="standartRow">
		<xsl:with-param name="id" select="'kpp'"/>
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
		<xsl:with-param name="rowValue"><input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}"
	           size="24" maxlength="20"/></xsl:with-param>
	</xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="groundTmp" name="groundTmp" type="text" size="60" onChange="calculateNDS();"/><br/>
            <input id="NDSAmount" name="NDSAmount" type="text" size="60" disabled="true"/>
        </xsl:with-param>
    </xsl:call-template>

<script type="text/javascript">
	function openSelectReceiver()
	{
		window.open(document.webRootPrivate + '/receivers/list.do?action=getReceiverInfo&amp;kind=PJ',
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

	function setDefaultGround()
	{
		var ground = document.getElementById("ground").defaultValue;
		document.getElementById("ground").value = ground;
	}
	
	checkSelectTypePerson();
	setDefaultGround();
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
			<xsl:with-param name="rowName">Сумма</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="amount"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="payerAccountType"/>]
				<xsl:value-of select="payerAccountCurrency"/>
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
</xsl:stylesheet>