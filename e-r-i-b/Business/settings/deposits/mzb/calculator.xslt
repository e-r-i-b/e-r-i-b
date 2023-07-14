<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:set="http://exslt.org/sets"
	exclude-result-prefixes="set"
	>

<xsl:output method="html" version="1.0"  indent="yes"/>
<xsl:param name="webRoot" select="'webRoot'"/>
<xsl:param name="selectedProduct"/>
<xsl:variable name="products" select="//product"/>

<xsl:template match="/">
<!--	<script type="text/javascript" src="file://C:\Projects\RS\PhizIC-MAIN\WebResources\web\scripts\Utils.js"/>-->

	<xsl:call-template name="html"/>
	<xsl:call-template name="productScript"/>
	<script type="text/javascript">
		<![CDATA[
		// global
		function calculate()
		{
			try
			{
				var deposit  = getCurrentDeposit();
				var currency = getCurrentCurrency();
				var period   = getCurrentPeriod();
				var amount   = getCurrentAmount();
				var tuple    = deposit.getSuitableTuple(currency, period, amount);
				var reg =/(\.\d\d$)|(^\d+$)/;

                if  (!reg.test(amount)) alert("Значение суммы вклада введено некорректно");
				else{

				if ( tuple != null && tuple.period != "00-00-000")
				{
					var rate     = tuple.rate;
					var profit   = deposit.calculate(currency, period, amount);
					writeResult(formatMoney(amount + profit), currency, formatMoney(profit), rate);
				}
				else
				{
					tuple = getCurrentDeposit().getTupleWithMinAmount(currency, period);

					if ( tuple == null )
					{
						writeError("<span class='asterisk'>Невозможно открыть вклад на выбранных условиях.</span><br><span class='asterisk'>Введите другие значения валюты и/или срока.</span>");
					}
					else if ( amount < tuple.minAmount )
					{
						writeError("<span class='asterisk'>Минимальная сумма вклада должна быть больше " + "</span><br><span class='asterisk'>или равна" + tuple.minAmount + ". Введите корректное значение суммы.</span>");
					}
					else if (tuple.period == "00-00-000")
					{
						writeError("<span class='asterisk'>Невозможен расчет для бессрочных вкладов.</span>");
					}
				}}
			}
			catch ( ex )
			{
				alert(ex.message);
				throw ex;
			}
		}

		function getDeposits()
		{
			var result = new Array(deposits.length);

			for ( var i = 0; i < deposits.length; i++ )
			{
				result[i] = new Option(deposits[i].name, deposits[i].id);
				result[i].deposit = deposits[i];
			}

			return result;
		}

		function getCurrentDeposit()
		{	
			var depositSelect = getElementById("deposit");
			return depositSelect.options[depositSelect.selectedIndex].deposit;
		}

		function setCurrentDeposit(id)
		{
		    if ( id == null )
		        return;

			getElementById("deposit").value = id;
		}

		function getCurrentCurrency()
		{
			return getElementById("currency").value;
		}

		function getCurrentPeriod()
		{
			return getElementById("period").value;
		}

		function getCurrentAmount()
		{
			return new Number(getElementById("amount").value);
		}

		function getElementById(id)
		{
			var element = document.getElementById(id);

			if ( element == null )
				throw new Error("Елемент [id = " + id + "] не найден");

			return element;
		}

		function updateSelect(id, options)
		{
			var select  = getElementById(id);
			select.options.length = 0;

			for ( var i = 0 ; i < options.length; i++ )
			{
				select.options[i] = options[i];
			}
		}

		function updateTagText(id, text)
		{
			var div = getElementById(id);
			div.innerHTML = text;
		}

		function writeResult(amount, currency, profit, rate)
		{
			var text = "<table>"
					 + "<tr><td class='Width200 LabelAll'><b>&nbsp;Сумма денежных средств в конце срока вклада&nbsp;</b></td><td valign='top' class='asterisk'>&nbsp;" + amount + "&nbsp;" + currency + "&nbsp;</td></tr>"
					 + "<tr><td class='Width200 LabelAll'><b>&nbsp;Сумма дохода по вкладу&nbsp;</b></td><td valign='top' class='asterisk'>&nbsp;" + profit + "&nbsp;</td></tr>"
					 + "<tr><td class='Width200 LabelAll'><b>&nbsp;Размер процентной ставки&nbsp;</b></td><td valign='top' class='asterisk'>&nbsp;" + rate + "%&nbsp;</td></tr>"
					 + "<tr><td colspan='2'><b>&nbsp;Приведенный расчет является приблизительным, &nbsp;</b></td></tr>"
					 + "<tr><td colspan='2'><b>&nbsp;реальные данные могут незначительно &nbsp;</b></td></tr>"
		     		 + "<tr><td colspan='2'><b>&nbsp;отличаться от расчета.&nbsp;</b></td></tr>"
					 + "</table>";

			updateTagText("result", text);
		}

		function writeError(msg)
		{
			updateTagText("result", msg);
		}

		var deposits = new Array();

		function onDepositChanged()
		{
			var currencies = getCurrentDeposit().getCurrencies();
			var currencyOptions = new Array();

			for ( var i = 0; i < currencies.length; i++ )
			{
				currencyOptions[i] = new Option(currencies[i], currencies[i]);
			}

			updateSelect( "currency", currencyOptions );

			var periods = getCurrentDeposit().getPeriods();
			var periodOptions = new Array();

			for ( var i = 0; i < periods.length; i++ )
			{
				periodOptions[i] = new Option(formatPeriod(periods[i]), periods[i]);
			}

			updateSelect( "period", periodOptions );
			
			refreshTexts();
		}

		function refreshTexts()
		{
			var currency = getCurrentCurrency();
			var period   = getCurrentPeriod();
			var tuple    = getCurrentDeposit().getTupleWithMinAmount(currency, period);

			var text = tuple != null ? tuple.minAmount + "&nbsp;" + currency : "<span class='asterisk'> не установлена для выбранных валюты и срока.</span>";
			updateTagText( "minAmount", text );
			updateTagText( "amountCurrency", currency );
			updateTagText("result", "");
		}
	]]>
	</script>
		
	<xsl:apply-templates select="$products" mode="product"/>

	<script type="text/javascript">
        updateSelect( "deposit", getDeposits() );
        setCurrentDeposit(<xsl:value-of select="$selectedProduct"/> );
        onDepositChanged();
	</script>
</xsl:template>

<xsl:template name="html">
	<table border="0" cellspacing="0" cellpadding="2" width="100%">
		<tr>
			<td nowrap="true" class="Width120 LabelAll"><b>&nbsp;Вид вклада&nbsp;</b></td>
			<td>&nbsp;<select id="deposit" class="culcInput" onChange="javascript:onDepositChanged();"></select>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class="Width120 LabelAll"><b>&nbsp;Валюта вклада&nbsp;</b></td>
			<td>&nbsp;<select id="currency" onChange="javascript:refreshTexts()"></select>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class="Width120 LabelAll"><b>&nbsp;Срок вклада&nbsp;</b></td>
			<td>&nbsp;<select id="period" onChange="javascript:refreshTexts()"></select>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class="Width120 LabelAll"><b>&nbsp;Минимальная сумма вклада&nbsp;</b></td>
			<td valign="top">&nbsp;<span id="minAmount"></span>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="true" class="Width120 LabelAll"><b>&nbsp;Сумма вклада&nbsp;</b></td>
			<td>&nbsp;<input type="text" id="amount" class="culcInput" maxlength="9" size="9"/>&nbsp;<span id="amountCurrency"></span>&nbsp;</td>
		</tr>
        <tr>
			<td align="center" colspan="2">
				<table cellpadding="0" cellspacing="0">
				<tr>
				    <td class="buttPartL" width="3" height="18">&nbsp;</td>
				    <td valign="center" align="middle" height="18">
				         <table class="submit" height="18" cellSpacing="0" cellPadding="2" width="100%" border="0"
				                onmouseover="this.className = 'submitOver';"
				                onmouseout="this.className = 'submit';">
							<tr><td class="handCursor" onclick="calculate();">Рассчитать</td></tr>
						  </table>
					</td>
				    <td class="buttPartR" width="3" height="18">&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
    </table>
	<br/><br/>
	<span id="result"/>
</xsl:template>    

<xsl:template match="product" mode="product">
	<script type="text/javascript">
		var index = deposits.length;
		deposits[index] = new Deposit(<xsl:value-of select="@id"/>, '<xsl:value-of select="@name"/>');

		<xsl:for-each select="data/element">
			deposits[index].addTuple
			(
				'<xsl:value-of select="value[@field='currency']"/>',
				'<xsl:value-of select="value[@field='period']"/>',
				<xsl:value-of select="value[@field='minimumAmount']"/>,
				'<xsl:value-of select="value[@field='interestRate']"/>',
				<xsl:value-of select="value[@field='capitalization']"/>,
				'<xsl:value-of select="value[@field='paymentPeriod']"/>'
			);
		</xsl:for-each>
	</script>
</xsl:template>

<xsl:template name="productScript">
	<script type="text/javascript">
	<![CDATA[
		/* Продукт */
		function Deposit(id, name)
		{
		    this.id     = id;
			this.name   = name;
			this.tuples = new Array();

			this.addTuple = function(currency, period, minAmount, rate, capitalization, paymentPeriod)
			{
				var tuple            = new Object();
				tuple.currency       = currency;
				tuple.period         = period;
				tuple.minAmount      = minAmount;
				tuple.rate           = rate;
				tuple.capitalization = capitalization;
				tuple.paymentPeriod  = paymentPeriod;

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
				var tuple  = this.getSuitableTuple(currency, period, amount);
				var result = 0;

				if ( !tuple.capitalization )
				{
					// без капитализации
					result = amount * tuple.rate * periodToDays(period) / 365 / 100;
				}
				else if ( tuple.capitalization && tuple.paymentPeriod == 'at-end' )
				{
					// c капитализацией, но начисление процентов в конце срока, считаем обычно
					result = amount * tuple.rate * periodToDays(period) / 365 / 100;
				}
				else
				{
					// c капитализацией, с периодическим начислением процентов
					var remainingDays = periodToDays(period);
					var paymentPeriodDays;

					if ( tuple.paymentPeriod == 'monthly' )
					{
						paymentPeriodDays = 31;
					}
					else if ( tuple.paymentPeriod == 'quarterly')
					{
						paymentPeriodDays = 92;
					}

					if ( remainingDays < paymentPeriodDays )
						throw new Error("Срок вклада меньше чем период начисления процентов");

					while ( remainingDays >= paymentPeriodDays )
					{
						result        = result + (amount + result) * tuple.rate * paymentPeriodDays / 365 / 100; 
						remainingDays = remainingDays - paymentPeriodDays;
					}
		
					result = result + (amount + result) * tuple.rate * remainingDays / 365 / 100; 
				}

				return result;
			}
		}
		/* End of Продукт */
		]]>
	</script>
</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Do" userelativepaths="yes" externalpreview="yes" url="products.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="6" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\products\Доходный\description.xml" srcSchemaRoot="product" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="xsl:apply&#x2D;templates" x="157" y="0"/><block path="script[1]/xsl:value&#x2D;of" x="157" y="133"/></template><template match="product"><block path="script/xsl:value&#x2D;of" x="157" y="133"/><block path="script/xsl:for&#x2D;each" x="87" y="103"/><block path="" x="37" y="93"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->