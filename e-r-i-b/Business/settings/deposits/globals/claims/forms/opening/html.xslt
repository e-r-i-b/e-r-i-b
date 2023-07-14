<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="1.0" indent="yes"/>
<xsl:param name="mode" select="'view'"/>

<xsl:variable name="formData" select="/form-data"/>
<xsl:variable name="productId" select="/form-data/product"/>
<!--<xsl:variable name="product"  select="document('product/description.xml')"/>-->
<xsl:variable name="products" select="document('products.xml')"/>
<xsl:variable name="accounts" select="document('accounts.xml')"/>

	<xsl:template match="/">
	<xsl:call-template name="commonScript"/>
	<xsl:call-template name="productScript"/>
	<xsl:choose>
		<xsl:when test="$mode = 'edit'">
			<xsl:call-template name="editForm"/>
		</xsl:when>
		<xsl:when test="$mode = 'view'">
			<xsl:call-template name="viewForm"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template name="editForm">
	<xsl:apply-templates select="$products//product[@id=$productId]" mode="product"/>
	<xsl:apply-templates select="$accounts" mode="accounts"/>
	<xsl:call-template name="editHtml"/>
	<xsl:call-template name="editInitControls"/>
	<xsl:call-template name="editInitValues"/>
</xsl:template>
<xsl:template name="editHtml">
	<table border="0" cellspacing="0" cellpadding="2">
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;<b>Валюта вклада</b>&nbsp;</td>
			<td>&nbsp;<select id="currency" name="currency" onchange="javascript:refreshTexts()"></select>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;<b>Срок вклада</b>&nbsp;</td>
			
			<td>&nbsp;<select id="period" name="period" onchange="javascript:refreshTexts()"></select>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;<b>Минимальная сумма вклада</b>&nbsp;</td>
			<td>&nbsp;<span id="minAmountSpan"/>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;<b>Сумма вклада</b>&nbsp;</td>
			<td>&nbsp;<input type="text" id="amount" name="amount"/>&nbsp;<span id="amountCurrency"/>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;<b>Счет списания</b>&nbsp;</td>
			<td nowrap="true">&nbsp;<select id="fromAccount" name="fromAccount"/>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;<b>По окончании срока вклада перечислить денежные средства на счет</b>&nbsp;</td>
			<td nowrap="true">&nbsp;<select id="toAccount" name="toAccount"/>&nbsp;</td>
		</tr>
	</table>
	<input type="hidden" id="minAmount" name="minAmount"/>
	<script type="text/javascript">
		function refreshTexts()
		{
			var currency = document.getElementById('currency').value;
			var period   = document.getElementById('period').value;
			var tuple    = product.getTupleWithMinAmount(currency, period);

			var text = tuple != null ? tuple.minAmount + "&nbsp;" + currency : "";
			setValue("minAmountSpan", text);
			setValue("minAmount", tuple.minAmount);
			setValue("amountCurrency", currency);
		}
	</script>
</xsl:template>
<xsl:template name="editInitControls">
	<script type="text/javascript">
		<![CDATA[ 
		function createOptions(array, func)
		{
			var options = new Array();
			for ( var i = 0; i < array.length; i++ )
			{
				options[i] = func(array[i]);
			}
			return options;
		}

		function updateSelect(id, options)
		{
			var select  = document.getElementById(id);
			select.options.length = 0;

			for ( var i = 0 ; i < options.length; i++ )
			{
				select.options[i] = options[i];
			}
		}
		
		document.getElementById("product").innerHTML = product.name;
		
		updateSelect("currency", 
			createOptions(product.getCurrencies(), 
						  function(value){return new Option(value, value)}));

		updateSelect("period", 
			createOptions(product.getPeriods().sort(), 
						  function(value){return new Option(formatPeriod(value), value)}));
		
		updateSelect("fromAccount", createOptions(
				accounts, 
				function(account)
				{
					return new Option(account.number + ' [' + account.type + '] ' + account.amount.toFixed(2) + ' ' + account.currency , account.number)
				}));

		updateSelect("toAccount", createOptions(
				accounts, 
				function(account)
				{
					return new Option(account.number + ' [' + account.type + '] ' + account.amount.toFixed(2) + ' ' + account.currency , account.number)
				}));
			
		]]>
	</script>
</xsl:template>
<xsl:template name="editInitValues">
	<script type="text/javascript">
        setSelectValue('currency', '<xsl:value-of select="$formData/currency"/>');
		setSelectValue('period', '<xsl:value-of select="$formData/period"/>');
		setValue('amount', formatMoney('<xsl:value-of select="$formData/amount"/>'));
		setSelectValue('fromAccount', '<xsl:value-of select="$formData/fromAccount"/>');
		setSelectValue('toAccount', '<xsl:value-of select="$formData/toAccount"/>');

		refreshTexts();
	</script>
</xsl:template>

<xsl:template name="viewForm">
	<xsl:call-template name="viewHtml"/>
	<xsl:call-template name="viewInitValues"/>
</xsl:template>
<xsl:template name="viewHtml">
	<table border="0" cellspacing="0" cellpadding="2">
        <tr>
            <td nowrap="true" class='Width120 LabelAll'>&nbsp;Вид вклада&nbsp;</td>
            <td>&nbsp;<span  id="product"/>&nbsp;</td>
        </tr>
        <tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;Валюта вклада&nbsp;</td>
			<td>&nbsp;<span id="currency"/>&nbsp;</td>
        </tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;Срок вклада&nbsp;</td>
			<td>&nbsp;<span id="period"/>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;Сумма вклада&nbsp;</td>
			<td>&nbsp;<span id="amount"/>&nbsp;<span id="amountCurrency"/>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;Счет списания&nbsp;</td>
			<td>&nbsp;<span id="fromAccount"/>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class='Width120 LabelAll'>&nbsp;По окончании срока вклада перечислить денежные средства на счет&nbsp;</td>
			<td valign="top">&nbsp;<span id="toAccount"/>&nbsp;</td>
		</tr>
    </table>
</xsl:template>
<xsl:template name="viewInitValues">
	<script type="text/javascript">
		setValue('product', '<xsl:value-of select="$products//product[@id=$productId]/@name"/>');
		setValue('currency', '<xsl:value-of select="$formData/currency"/>');
		setValue('period', formatPeriod('<xsl:value-of select="$formData/period"/>'));
		setValue('amount', formatMoney('<xsl:value-of select="$formData/amount"/>'));
		<xsl:variable name="fromAccountNum" select="$formData/fromAccount"/>
		<xsl:variable name="fromAccount" select="$accounts//entity[@key=$fromAccountNum]"/>
		setValue('fromAccount', '<xsl:value-of select="$fromAccountNum"/> [<xsl:value-of select="$fromAccount/field[@name='type']"/>]');
		<xsl:variable name="toAccountNum" select="$formData/toAccount"/>
		<xsl:variable name="toAccount" select="$accounts//entity[@key=$toAccountNum]"/>
		setValue('toAccount',  '<xsl:value-of select="$toAccountNum"/> [<xsl:value-of select="$toAccount/field[@name='type']"/>]');
	</script>
</xsl:template>

<xsl:template name="initAccountsArray" match="entity-list" mode="accounts">
	<script type="text/javascript">
		var accounts = new Array();
		
		function initAccountsArray()
		{
		<xsl:for-each select="entity">
			var account = new Object()
			account.number   = '<xsl:value-of select="@key"/>';
			account.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			account.amount   = <xsl:value-of select="field[@name='amountDecimal']/text()"/>;
			account.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			accounts[accounts.length] = account;
		</xsl:for-each>
		}

		initAccountsArray();
	</script>
</xsl:template>
<xsl:template name="initProductScript" match="product" mode="product">
	<script type="text/javascript">
		var product = new DepositProduct(0, '<xsl:value-of select="@name"/>');
		<xsl:for-each select="data/element">
			product.addTuple
			(
				'<xsl:value-of select="value[@field='currency']"/>',
				'<xsl:value-of select="value[@field='period']"/>',
				<xsl:value-of select="value[@field='minimumAmount']"/>,
				<xsl:value-of select="value[@field='interestRate']"/>
			);
		</xsl:for-each>
	</script>
</xsl:template>
<xsl:template name="productScript">		
	<script type="text/javascript">
	<![CDATA[
		/* Продукт */
		function DepositProduct(id, name)
		{
		    this.id     = id;
			this.name   = name;
			this.tuples = new Array();

			this.addTuple = function(currency, period, minAmount, rate)
			{
				var tuple      = new Object();
				tuple.currency = currency;
				tuple.period   = period;
                tuple.minAmount   = minAmount;
				tuple.rate     = rate;

				this.tuples[ this.tuples.length ] = tuple;
			}

			this.getTupleWithMinAmount = function(currency, period)
			{
				var result;

				for ( var i = 0; i < this.tuples.length; i++ )
				{
					var tuple = this.tuples[i];

					if ( currency == tuple.currency &&
						 period == tuple.period     &&
						 (result == null || tuple.minAmount < result.minAmount) )
					{
						result = tuple;
					}
				}

				return result;
			}

			this.getSuitableTuple = function(currency, period, amount)
			{
				var result;

				for ( var i = 0; i < this.tuples.length; i++ )
				{
					var tuple = this.tuples[i];

					if ( currency == tuple.currency &&
						 period == tuple.period     &&
						 tuple.minAmount <= amount        &&
						 (result == null || tuple.minAmount > result.minAmount) )
					{
						result = tuple;
					}
				}

				return result;
			}

			this.getCurrencies = function()
			{
				var result = new Array();

				for ( var i = 0; i < this.tuples.length; i++ )
				{
					var currency = this.tuples[i].currency;

					if ( !result.contains(currency) )
						result[result.length] = currency;
				}

				return result.sort();
			}

			this.getPeriods = function()
			{
				var result = new Array();

				for ( var i = 0; i < this.tuples.length; i++ )
				{
					var period = this.tuples[i].period;

					if ( !result.contains(period) )
						result[result.length] = period;
				}

				return result.sort();
			}

			this.calculate = function(currency, period, amount)
			{
				var tuple = this.getSuitableTuple(currency, period, amount);
				return amount * tuple.rate * periodToDays(period) / 365 / 100;
			}
		}
		/* End of Продукт */
		]]>
	</script>
</xsl:template>
<xsl:template name="commonScript">
	<script type="text/javascript">
        <![CDATA[
        function setSelectValue(elementId, value)
        {
            var elem = document.getElementById(elementId);

            if (elem != null && elem.options.length > 0 && value == '')
                elem.selectedIndex = 0;
        }


        function setValue(elementId, value)
		{
			var elem = document.getElementById(elementId);

			if(elem.value != null)
				elem.value = value;
			else if(elem.innerHTML != null)
				elem.innerHTML = value;
		}
		]]>
    </script>
</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="yes" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="yes" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->