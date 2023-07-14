<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
      <!ENTITY nbsp "&#160;">
      ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:set="http://exslt.org/sets" exclude-result-prefixes="set"
				xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
   <xsl:output method="html" version="1.0" indent="yes"/>
   <xsl:param name="webRoot" select="'webRoot'"/>
   <xsl:param name="resourceRoot" select="'resourceRoot'"/>
   <xsl:param name="selectedProduct"/>
   <xsl:variable name="kinds" select="calculator/loan-kinds/loan-kind"/>
   <xsl:variable name="offices" select="calculator/loan-offices/loan-office"/>
   <xsl:variable name="products" select="calculator/loan-products/loan-product"/>

   <xsl:template match="/">
      <xsl:call-template name="html"/>
      <xsl:call-template name="productScript"/>
      <script type="text/javascript"><![CDATA[

   var kinds = new Array();
   var offices = new Array();
   var credits = new Array();

   function compareNumbers(a, b)
   {
        return a - b;
   }

   /**
   *вычисление эффективной процентной ставки
   * n - срок кредита (количество месяцев)
   * CF - сумма денежного потока (ежемесячный платеж, с комиссиями и т.п.)
   * initialThread - сумма нулевого денежного потока (сумма кредита со знаком минус)
   **/
   function calculateIRR(n, CF, initialThread)
   {
      function recursion(a, b)
	  {
		  var c = (a+b)/2;
		  var fc = f(c);
		  if (fc == 0 || b-a < 0.00005) return c;

		  if (f(a)*fc < 0)
			 return recursion(a, c);
		  else
			 return recursion(c, b);
	  }

   	  function f(x)
	  {
		  var result = initialThread;
		  var d = 30;

		  for (var i=1; i<=n; i++)
		  {
			 result += CF/Math.pow(1 + x, d*i/365);
		  }

		  return result;
	  }

   	  var a = 0, b = 100;

	  if (f(a) == 0) return a;
	  if (f(b) == 0) return b;

	  return recursion(a, b)*100;
   }

   function roundTo2(number)
   {
   	  return Math.round(number * 100) / 100;
   }

   function durationToMonth(duration)
   {
      if (duration.length == 0) return null;

      var durationType = duration.substring(0, 1);

	  var res = duration.substring(2, duration.length);
      if (durationType == "M")
      {
         return res;
      }
      if (durationType == "D")
      {
         return roundTo2(parseInt(res)/30);
      }
      return null;
   }

   function setCurrentCredit(id)
   {
		if ( id != null ) getElementById("credit").value = id;
   }

   function getCreditKinds()
   {
      var result = new Array(kinds.length);

      for (var i = 0; i < kinds.length; i++)
      {
         result[i] = new Option(kinds[i].name, kinds[i].id);
		 result[i].kind = kinds[i];
      }
      return result;
   }

   function getOffices()
   {
      var result = new Array(offices.length);

      for (var i = 0; i < offices.length; i++)
      {
         result[i] = new Option(offices[i].name, offices[i].id);
		 result[i].office = offices[i];
      }
      return result;
   }

   function getCurrentKind()
   {
      var kindSelect = getElementById("kind");
	  if (kindSelect.options.length == 0) return null;
      return kindSelect.options[kindSelect.selectedIndex].kind;
   }

   function getCurrentOffice()
   {
   	  var officeSelect = getElementById("office");
	  if (officeSelect.options.length == 0) return null;
      return officeSelect.options[officeSelect.selectedIndex].office;
   }

   function getCurrentCredit()
   {
   	  var creditSelect = getElementById("credit");
	  if (creditSelect.options.length == 0) return null;
	  return creditSelect.options[creditSelect.selectedIndex].credit;
   }

	//возвращает срок кредита, указаный пользователем в соответствующем поле
   function getCurrentDuration()
   {
   	  return parseInt(getElementById("duration").value);
   }

	//возвращает сроки кредита из текущего condition
	function getCurrentDurations()
    {
   	  var condition = getCurrentCondition();
   	  if (condition == null)
		return null;

	  return condition.durations;
    }

	function getCurrentCurrency()
	{
   	  var currencySelect = getElementById("currency");
	  if (currencySelect.options.length == 0) return null;
	  return currencySelect.options[currencySelect.selectedIndex].value;
	}

   //Возвращает condition для выбранных офиса, кредитного продукта и валюты
   function getCurrentCondition()
   {
      var currentCredit = getCurrentCredit();
      if (currentCredit == null) return null;
      var currentOffice = getCurrentOffice();
      if (currentOffice == null) return null;
	  var currentOfficeId = currentOffice.id;
      var currentCurrency = getCurrentCurrency();
      if (currentCurrency == null) return null;

      var conditions = currentCredit.conditions;
      for (var i=0; i< conditions.length; i++)
	  {
	     var tmp = conditions[i];
	     if (tmp.currency = currentCurrency)
	     {
			 var selectedOffices = tmp.selectedOffices;
			 for (var j=0; j< selectedOffices.length; j++)
			 {
				if (selectedOffices[j] == currentOfficeId)
					return tmp;
			 }
		 }
	  }
	  return null;
   }

   function getElementById(id)
   {
      var element = document.getElementById(id);

      if (element == null)
         alert("Елемент [id = " + id + "] не найден");

      return element;
   }

   function updateSelect(id, options)
   {
   	  if (options == null) return;
      var select = getElementById(id);
      select.options.length = 0;

      for (var i = 0; i < options.length; i++)
      {
         select.options[i] = options[i];
      }
   }

   //возвращает true, если данный кредит разрешено
	//выдавать в офисе с id = officeId
   function creditSolvedAtOffice(credit, officeId)
   {
   		var conditions = credit.conditions;
		for (var i=0; i< conditions.length; i++)
		{
			var selectedOffices = conditions[i].selectedOffices;
			for (var j=0; j< selectedOffices.length; j++)
			{
				if (selectedOffices[j] == officeId)
					return true;
			}
		}

		return false;
   }

   function onKindOrOfficeChanged()
   {
 	  var currentCredits = new Array();
      var kindId = getCurrentKind().id;
	  var currentOffice = getCurrentOffice();
	  if (currentOffice != null)
	  {
	      for (var i = 0; i < credits.length; i++)
	      {
		  	 var currentCredit = credits[i];

	         if (currentCredit.kindId == kindId && creditSolvedAtOffice(currentCredit, currentOffice.id))
	         {
	            var length = currentCredits.length;
	            currentCredits[length] = new Option(currentCredit.name, currentCredit.id);
	            currentCredits[length].credit = currentCredit;
	         }
	      }
	  }
      updateSelect("credit", currentCredits);
	  if (currentCredits.length > 0)
	  	onCreditChanged();
      else document.getElementById("allowedDurations").value = "-";

	  getElementById("amount").value = "";
	  hideFields();
   }

   function onCreditChanged()
   {
      var currentCredit = getCurrentCredit();
      if (currentCredit == null) return null;
      var conditions = currentCredit.conditions;
      var currentOfficeId = getCurrentOffice().id;
   	  var currentCurrencies = new Array();
      for (var i = 0; i < conditions.length; i++)
      {
         var tmp = conditions[i];

         //если для выбранных офиса и кредита есть кондишион, то берем из него валюту
         var selectedOffices = tmp.selectedOffices;
		 for (var j=0; j< selectedOffices.length; j++)
		 {
			if (selectedOffices[j] == currentOfficeId)
			{
				if (!currentCurrencies.contains(tmp))
                   currentCurrencies[currentCurrencies.length] = tmp.currency;
			}
		 }
      }

      var currencyOptions = new Array();
      for (var i = 0; i < currentCurrencies.length; i++)
      {
          currencyOptions[currencyOptions.length] = new Option(currentCurrencies[i], currentCurrencies[i]);
      }

      updateSelect("currency", currencyOptions);
	  if (currencyOptions.length > 0)
	      onCurrencyChanged();

	  showAllowedDurations();
   	  fillRateField();
   }

   //Вписать в поле "Процентная ставка" соответствующую ставку
   function fillRateField()
   {
      var condition = getCurrentCondition();
      if (condition == null)
      {
        getElementById("interestRate").value = "";
        return;
      }

      var rate = condition.rate;
	  if (rate == null)
	    getElementById("interestRate").value = "";
	  else
	    getElementById("interestRate").value = rate;
   }

   function onCurrencyChanged()
   {
      getElementById("currentCurrency").innerHTML = getCurrentCurrency();
      showAllowedDurations();
	  onGoodsCostChange();
   }

   function onGoodsCostChange()
   {
   	  getElementById("firstPayment").value = "";

	  var firstPayment = getMinFirstPayment();
	  if (firstPayment != null)
   	  	getElementById("firstPayment").value = firstPayment;
   }

   function onDurationChange()
   {
	  fillRateField();
   }

   function getMinFirstPayment()
   {
   	  var goodsCost = getElementById("goodsCost").value;
	  if (goodsCost == "" || isNaN(goodsCost))
	  	return null;
	  else
	  	goodsCost = parseFloat(goodsCost);

   	  var condition = getCurrentCondition();
	  if (condition == null)
	  	return null;

   	  var minInitialInstalment = condition.minInitialInstalment;
	  if (minInitialInstalment == "")
	  	return null;
	  else
	  	minInitialInstalment = parseFloat(minInitialInstalment);

	  return minInitialInstalment*goodsCost;
   }

   function getFirstPayment()
   {
        var element = getElementById("firstPayment");
		var firstPayment = parseFloat(element.value);
        if (firstPayment == "" || isNaN(firstPayment))
        {
            firstPayment=0.0;
            element.value = firstPayment;
	    }
	    return firstPayment;
   }

   function kindIsTarget()
   {
      return getCurrentKind().kindIsTarget == 'true';
   }

   function hideFields()
   {
   	  if (kindIsTarget())
      {
         getElementById("goodsCostTr").style.display = "";
         getElementById("firstPaymentTr").style.display = "";
         getElementById("amountTr").style.display = "none";
         getElementById("goodsCostTr").style.display = "";
      }
      else
      {
	  	 getElementById("goodsCostTr").style.display = "none";
         getElementById("firstPaymentTr").style.display = "none";
		 getElementById("amountTr").style.display = "";
		 getElementById("goodsCostTr").style.display = "none";
      }
   }

   	function calculate()
	{
		if (!checkFields())
		{
			return;
		}

		var condition = getCurrentCondition();
		var duration = getCurrentDuration();
        var rate = condition.rate;

		if (isNaN(rate))
		{
			alert("Ошибка: для выбранных условий не указана процентная ставка.");
			return;
		}
		var commissionRate = parseFloat(condition.commissionRate);
		if (isNaN(commissionRate))
		{
			commissionRate=parseFloat('0.0');
		}

		getElementById("monthlyPaymentText").value = "";
		getElementById("RisePriceGoodsText").value = "";
		getElementById("commissionText").value = "";

		getElementById("additionalFields").style.display = "block";
		getElementById("infoTable").style.display = "block";

		if (kindIsTarget())
		{
			var goodsCost = parseFloat(getElementById("goodsCost").value);
			var firstPayment = getFirstPayment();
			var creditAmount = goodsCost - firstPayment;
			getElementById("amount").value = creditAmount;
            getElementById("amountTr").style.display = "";

			createResultTable(duration, creditAmount, rate, commissionRate);
		}
		else
		{
			var creditAmount = parseFloat(getElementById("amount").value);
			createResultTable(duration, creditAmount, rate, commissionRate);
		}
	}

	function checkFields()
	{
		if (getCurrentKind() == null)
		{
			alert("Не выбран вид кредита");
			return false;
		}

		if (getCurrentOffice() == null)
		{
			alert("Не выбран кредитный офис");
			return false;
		}

		if (getCurrentCredit() == null)
		{
			alert("Не выбран кредитный продукт");
			return false;
		}

		var errorMessage = checkDuration();
	    if (errorMessage != null)
	    {
	        alert(errorMessage);
	        return;
	    }

		if (!kindIsTarget())
		{
			var amount = getElementById("amount").value;
			if (amount== "" || isNaN(amount))
			{
				alert("Введите сумму кредита");
				return false;
			}
			return checkAmount(parseFloat(amount));
		}
		else
		{
			var goodsCost = parseFloat(getElementById("goodsCost").value);
			if (isNaN(goodsCost))
			{
				alert("Введите стоимость товара");
				return false;
			}
			var duration = parseFloat(getElementById("duration").value);
			if (isNaN(duration))
			{
				alert("Введите срок кредита");
				return false;
			}

			var firstPayment = getFirstPayment();
			var minFirstPayment = getMinFirstPayment();
			if (firstPayment < minFirstPayment)
			{
				alert("Сумма первоначального взноса должна быть не менее " + minFirstPayment);
				return false;
			}
			if (goodsCost < firstPayment)
			{
				alert("Размер первоначального взноса больше стоимости товара!");
				return false;
			}

			return true;
		}
	}

	function checkAmount(amount)
	{
		var condition = getCurrentCondition();
		if (condition.minAmount != "" && amount < parseFloat(condition.minAmount))
		{
			alert("Для данного вида кредита сумма кредита должна быть не менее " + condition.minAmount + " (Сумма = " + amount + ")");
			return false;
		}
		if (condition.maxAmount != "" && parseFloat(condition.maxAmount) < amount)
		{
			alert("Для данного вида кредита сумма кредита должна быть не более " + condition.maxAmount + " (Сумма = " + amount + ")");
			return false;
		}
		return true;
	}


	function getMonthlyPaymentByDutyAmount(amount, rate, commissionInterestRate, duration)
	{
		return ((amount * (rate / 12 + commissionInterestRate) / 100) / (1 - 1 / Math.pow((1 + rate / 100 / 12 + commissionInterestRate / 100), duration)));
	}

	function getMonthlyPaymentByCreditAmount(amount, rate, commissionInterestRate, duration)
	{
		return ((amount * rate / 100 / 12) / (1 - 1 / Math.pow((1 + rate / 100 / 12), duration))) + (amount * commissionInterestRate / 100);
	}

	function getCommission(amount, commissionInterestRate)
	{
		return amount * commissionInterestRate / 100;
	}

	function getInterestsPayment(basicDutyAmount, interestRate)
	{
		return basicDutyAmount * interestRate / 100 / 12;
	}

	function getBasicDutyPayment(monthlyPayment, interestsPayment, commission)
	{
		return monthlyPayment - interestsPayment - commission;
	}

	function getTdText(str, width)
	{
		var text = "<td nowrap='true' style='text-align:center;'";
		if (width != null)
		{
			text += " width='" + width + "'";
		}
		text += ">" + str + "</td>";

		return text;
	}

	function getTableText(rowElementsText)
	{
		var text = "<table id='resultTable' border='0' cellspasing='0' cellpadding='0'>";
		text += "<tr>";

		text += getTdText("Месяц", "45px");
		text += getTdText("Выплата по основному долгу", "90px");
		text += getTdText("Выплата по процентам", "100px");
		text += getTdText("Комиссия", "60px");
		text += getTdText("Сумма ежемесячного платежа", "80px");

		text += "</tr>";

		text += rowElementsText;
		text += "</table>";

		return text;
	}

	function getRowText(month, basicDutyPayment, interestsPayment, commission, monthlyPayment)
	{
		var text = "<tr>";

		text += getTdText(month);
		text += getTdText(roundTo2(basicDutyPayment));
		text += getTdText(roundTo2(interestsPayment));
		text += getTdText(roundTo2(commission));
		text += getTdText(roundTo2(monthlyPayment));

		text += "</tr>";

		return text;
	}


	function fillAdditionalFields(monthlyPayment, duration, creditAmount)
	{
		//Ежемесячный платеж
		getElementById("monthlyPaymentText").value = roundTo2(monthlyPayment);


		//Комиссия за ведение ссудного счета
		var condition = getCurrentCondition();
		var rate = condition.commissionRate;
		if (rate == "") rate = 0;
		var base = condition.commissionBase.substring(0, 1);
		if (base == '1')
		{
			base = "от суммы кредита";
		}
		if (base == '2')
		{
			base = "от суммы долга";
		}
		getElementById("commissionText").value = roundTo2(rate) + "% " + base;


		var fullPayments = monthlyPayment*duration;

		getElementById("fullCreditCost").value = roundTo2(fullPayments);


        //Процент удорожания товара
		if (kindIsTarget())
		{
			var goodsCost = getElementById("goodsCost").value;
			getElementById("RisePriceGoodsTextTr").style.display = "";
			var firstPayment = getFirstPayment();
			if (fullPayments == 0)
				getElementById("RisePriceGoodsText").value = 0;
			else
				getElementById("RisePriceGoodsText").value = roundTo2((fullPayments + firstPayment - goodsCost)/fullPayments*100);
		}
		else
		{
			getElementById("RisePriceGoodsTextTr").style.display = "none";
		}


		//Эффективная процентная ставка по кредиту
		getElementById("IRR").value = roundTo2(calculateIRR(duration, monthlyPayment, -1*creditAmount)) + "%";
	}

	//строит таблицу выплат по кредиту при зависимости комиссии от суммы кредита
	function getResultTableC(duration, creditAmount, interestRate, commissionInterestRate)
	{
		var basicDutyAmount = creditAmount;
		var interestsPayment = 0;
		var basicDutyPayment = 0;
        var rowElements = "";
		var monthlyPayment = getMonthlyPaymentByCreditAmount(creditAmount, interestRate, commissionInterestRate, duration);
		
		var commission = getCommission(creditAmount, commissionInterestRate);

		for (var i = 1; i <= duration; i++)
		{
			basicDutyAmount -= basicDutyPayment;
			interestsPayment = getInterestsPayment(basicDutyAmount, interestRate);
			basicDutyPayment = getBasicDutyPayment(monthlyPayment, interestsPayment, commission);

			rowElements += getRowText(i, basicDutyPayment, interestsPayment, commission, monthlyPayment);
		}

		fillAdditionalFields(monthlyPayment, duration, creditAmount);
		getElementById("resultTableSpan").innerHTML = getTableText(rowElements);
	}

	//строит таблицу выплат по кредиту при зависимости комиссии от суммы долга
	function getResultTableD(duration, creditAmount, interestRate, commissionInterestRate)
	{
		var basicDutyAmount = creditAmount;
		var commission = 0;
		var interestsPayment = 0;
		var basicDutyPayment = 0;
		var rowElements = "";
		var monthlyPayment = getMonthlyPaymentByDutyAmount(creditAmount, interestRate, commissionInterestRate, duration);

		for (var i = 1; i <= duration; i++)
		{
			basicDutyAmount -= basicDutyPayment;
			interestsPayment = getInterestsPayment(basicDutyAmount, interestRate);
			commission = getCommission(basicDutyAmount, commissionInterestRate);
			basicDutyPayment = getBasicDutyPayment(monthlyPayment, interestsPayment, commission);

			rowElements += getRowText(i, basicDutyPayment, interestsPayment, commission, monthlyPayment);
		}

		fillAdditionalFields(monthlyPayment, duration, creditAmount);
		getElementById("resultTableSpan").innerHTML = getTableText(rowElements);
	}

	function createResultTable(duration, creditAmount, interestRate, commissionInterestRate)
	{
		var commissionBase = getCurrentCondition().commissionBase;

		if (commissionBase.substring(0,1) == "2") //комиссия зависит от суммы задолженности
		{
			getResultTableD(duration, creditAmount, interestRate, commissionInterestRate);
		}
		else
		{
			getResultTableC(duration, creditAmount, interestRate, commissionInterestRate);
		}
	}


	function showAllowedDurations()
	{
		var allowedDurations = document.getElementById("allowedDurations");
		var durations = getCurrentDurations();
		if (durations == null || durations.length == 0)
			allowedDurations.value = "-";
        else
        {
			var durationsStr = "";
			for (var i=0; i < durations.length; i++)
			{
				if (i != 0)
					durationsStr += ", ";
				durationsStr += durations[i];
			}

            var length = durationsStr.length;
            if (length < 20) length = 20;
            allowedDurations.size = length;
            allowedDurations.value = durationsStr;
        }
	}

	//проверка, введенного пользователем, срока кредита на принадлежность допустимым значениям
	//возвращает сообщение об ошибке, если проверки не пройдены, иначе null
	function checkDuration()
	{
	    var condition = getCurrentCondition();
        if (condition == null)
            throw new Error("Не заданы текущие сроки кредита");

	    var duration = getElementById("duration").value;

	    if (duration == "")
	        return null;

		if (isNaN(duration))
		{
            return "Неверно указан срок кредита";
        }

        duration = parseInt(duration);
        var durations = condition.durations;
        for (var i=0; i < durations.length; i++ )
        {
            if (duration == durations[i])
                return null;
        }

        return "Неверное значение в поле [Срок кредита]. См. Допустимые сроки кредита";
	}
]]>
	//переход на страницу редактирования заявки
	function claimEdit()
	{
		var selectedKind = getCurrentKind();
	    if (selectedKind == null)
	    {
	        alert("Не выбран вид кредита");
	        return;
        }
	    var kind = selectedKind.id;

	    var selectedProduct = getCurrentCredit();
	    if (selectedProduct == null)
		{
			alert("Не выбран кредитный продукт");
			return;
		}
	    var product = selectedProduct.id;

	    var currency = getCurrentCondition().currency;

		var selectedOffice = getCurrentOffice();
	    if (selectedOffice == null)
	    {
	        alert("Не выбран кредитный офис");
		    return;
	    }
		var office = selectedOffice.id;

	    var winLocation = '<xsl:value-of select="$webRoot"/>' + "/private/claims/claim.do?form=LoanClaim" +
	      "&amp;kind=" + kind + "&amp;product=" + product + "&amp;loan-currency=" + currency + "&amp;office=" + office;

	    var duration = getCurrentDuration();
	    var errorMessage = checkDuration();
	    if (errorMessage != null)
	    {
	        alert(errorMessage);
	        return;
	    }

	    if (duration != "")
	        winLocation += "&amp;client-request-term=" + duration;

	    var amount = getElementById("amount").value;
	    if (amount != "")
	    {
		    winLocation += "&amp;client-request-amount=" + amount;
	    }

	    var firstPayment = getElementById("firstPayment").value;
	    if (firstPayment != "")
	    {
		    winLocation += "&amp;first-amount=" + firstPayment;
	    }

	    window.location = winLocation;
	}
      </script>

      <xsl:apply-templates select="$kinds" mode="kind"/>
	  <xsl:call-template name="loan-offices"/>
      <xsl:apply-templates select="$products" mode="product"/>

      <script type="text/javascript">
	  	 updateSelect( "kind", getCreditKinds() );
		 updateSelect( "office", getOffices() );
		 setCurrentCredit('<xsl:value-of select="$selectedProduct"/>');
         onKindOrOfficeChanged();
       </script>
   </xsl:template>

   <xsl:template name="html">
	   <table>
			<tr><td>
				<table border="0" cellspacing="0" cellpadding="2" width="550px">
					 <tr>
						<td nowrap="true" class="Width200 LabelAll">
						   Вид кредита&nbsp;
						</td>
						<td>&nbsp;<select id="kind" class="culcInput"
										  onChange="javascript:onKindOrOfficeChanged();"/></td>
					 </tr>
					 <tr>
						<td nowrap="true" class="Width200 LabelAll">
						   Офис&nbsp;
						</td>
						<td>&nbsp;<select id="office" class="culcInput"
										  onChange="javascript:onKindOrOfficeChanged();"/></td>
					 </tr>
					 <tr>
						<td nowrap="true" class="Width200 LabelAll">
						   Кредитный продукт&nbsp;
						</td>
						<td>&nbsp;<select id="credit" class="culcInput"
										  onChange="javascript:onCreditChanged();"/></td>
					 </tr>
					 <tr>
						<td nowrap="true" class="Width200 LabelAll">
						   Валюта кредита&nbsp;
						</td>
						<td>&nbsp;<select id="currency" class="culcInput"
										  onChange="javascript:onCurrencyChanged();"/></td>
					 </tr>
					 <tr id="goodsCostTr">
						<td nowrap="true" class="Width200 LabelAll">
						  Стоимость товара&nbsp;
						</td>
						<td>&nbsp;<input type="text" id="goodsCost" class="culcInput" maxlength="15"
										 onchange="javascript:onGoodsCostChange()"/></td>
					 </tr>
					 <tr>
						<td nowrap="true" class="Width200 LabelAll">
							Допустимые сроки кредита (месяцы)&nbsp;
						</td>
						<td>&nbsp;<input type="text" id="allowedDurations" class="culcInput" disabled="true"/>
						</td>
					 </tr>
					 <tr>
						<td nowrap="true" class="Width200 LabelAll">
						   Срок кредита (месяцы)&nbsp;
						</td>
						<td>&nbsp;<input type="text" id="duration" class="culcInput" maxlength="15" onchange="javascript:onDurationChange()"/></td>
					 </tr>
					 <tr>
						<td nowrap="true" class="Width200 LabelAll">
						   Процентная ставка&nbsp;
						</td>
						<td>&nbsp;<input type="text" id="interestRate" class="culcInput"
										 maxlength="6" disabled="true"/></td>
					 </tr>
					 <tr id="firstPaymentTr">
						<td class="Width200 LabelAll">
						   Сумма первоначального взноса&nbsp;
						</td>
						<td>&nbsp;<input type="text" id="firstPayment" class="culcInput"
										 maxlength="15"/></td>
					 </tr>
					 <tr id="amountTr">
						<td nowrap="true" class="Width200 LabelAll">
						   Сумма кредита&nbsp;
						</td>
						<td>&nbsp;<input type="text" id="amount" class="culcInput"
										 maxlength="15"/><span id="currentCurrency"></span>&nbsp;</td>
					 </tr>
					 <tr>
					   <td>&nbsp;</td>
						<td align="left" colspan="2">
						   <table cellpadding="0" cellspacing="0" class='noPadding' border="0">
							  <tr>
								 <td class="btnLeftCorner" width="3" height="18">&nbsp;</td>
								 <td valign="center" align="middle" height="18">
									<table class="btnBase" height="18" cellSpacing="0"
										   cellPadding="2" width="100%"
										   border="0" onmouseover="this.className = 'btnBase';"
										   onmouseout="this.className = 'btnBase';">
									   <tr>
										  <td class="handCursor" onclick="calculate();"><img src="{$resourceRoot}/images/imagesDemo/iconSm_calculate.gif" alt="" border="0"/></td>
										  <td class="handCursor" onclick="calculate();">&nbsp;Рассчитать</td>
									   </tr>
									</table>
								 </td>
								  <td class="btnRightCorner" width="3" height="18">&nbsp;</td>
								  <td class="btnLeftCorner" width="3" height="18">&nbsp;</td>
								  <td valign="center" align="middle" height="18">
									<table class="btnBase" height="18" cellSpacing="0"
										   cellPadding="2" width="100%"
										   border="0" onmouseover="this.className = 'btnBase';"
										   onmouseout="this.className = 'btnBase';">
									   <tr>
										  <td class="handCursor" onclick="claimEdit();"><img src="{$resourceRoot}/images/imagesDemo/iconSm_addClaim.gif" alt="" border="0"/></td> 
										  <td class="handCursor" onclick="claimEdit();">&nbsp;Оформить заявку</td>
									   </tr>
									</table>
								 </td>
								 <td class="btnRightCorner" width="3" height="18">&nbsp;</td>
							  </tr>
						   </table>
						</td>
						<td>&nbsp;</td>
					 </tr>
					 <tr>
						<td></td>
						<td>&nbsp;</td>
					 </tr>
				  </table>
			</td></tr>
	   </table>
      <span id="additionalFieldsSpan">
         <table border="0" cellspacing="0" cellpadding="2" width="550px" id="additionalFields" style="display:none">
            <tr>
               <td class="Width200 LabelAll">
                  Ежемесячный платеж&nbsp;
               </td>
               <td>&nbsp;
			   	   <input type="text" id="monthlyPaymentText" class="culcInput" disabled="true" size="35"/>
			   </td>
            </tr>
            <tr>
               <td class="Width200 LabelAll">
                  Комиссия за ведение ссудного счета&nbsp;
               </td>
               <td>&nbsp;
			   	   <input type="text" id="commissionText" class="culcInput" disabled="true" size="35"/>
			   </td>
            </tr>
            <tr>
               <td class="Width200 LabelAll">
                  Полная стоимость кредита&nbsp;
               </td>
               <td>&nbsp;
			   	   <input type="text" id="fullCreditCost" class="culcInput" disabled="true" size="35"/>
			   </td>
            </tr>
	        <tr>
               <td class="Width200 LabelAll">
                  Полная стоимость кредита в %&nbsp;
               </td>
               <td>&nbsp;
			   	  <input type="text" id="IRR" class="culcInput" disabled="true" size="35"/>
			   </td>
            </tr>
            <tr id="RisePriceGoodsTextTr">
               <td class="Width200 LabelAll">
                  Процент удорожания товара&nbsp;
               </td>
               <td>&nbsp;
			   	  <input type="text" id="RisePriceGoodsText" class="culcInput" disabled="true" size="35"/>
			   </td>
            </tr>
         </table>
      </span>
      <span id="resultTableSpan" class="pmntInnerTbl">
      </span>
      <span id="InfoSpan">
         <table border="0" width="550px" id="infoTable" style="display:none">
            <tr>
               <td>&nbsp;</td>
            </tr>
            <tr>
               <td>
                  <b>&nbsp;Напоминаем, что приведенный расчет аннуитетного платежа является приблизительным и реальные данные могут незначительно отличаться от расчета&nbsp;</b>
               </td>
            </tr>
         </table>
      </span>
   </xsl:template>

   <xsl:template match="loan-kind" mode="kind">
      <script type="text/javascript">
	  		kinds[kinds.length] = new Kind('<xsl:value-of select="@id"/>', '<xsl:value-of select="@target"/>',
										   '<xsl:value-of select="@name"/>');
	  </script>
   </xsl:template>

   <xsl:template name="loan-offices">
	  <script type="text/javascript">
		  <xsl:for-each select="set:distinct(//conditions/dynamic/condition/value[@name='selected-office'])">
			 <xsl:variable name="id" select="text()"/>
			 <xsl:variable name="currentOffice" select="//loan-office[@id=$id]"/>
			 offices[offices.length] = new Office('<xsl:value-of select="$currentOffice/@id"/>', '<xsl:value-of select="$currentOffice/@name"/>');
		  </xsl:for-each>
	  </script>
   </xsl:template>

   <xsl:template match="loan-product" mode="product">
      <script type="text/javascript">
	  var index = credits.length;
	  credits[index] = new Credit(<xsl:value-of select="@id"/>, '<xsl:value-of select="@name"/>', '<xsl:value-of
            select="@kindId"/>');

         <xsl:for-each select="conditions/dynamic/condition">
			var durations = new Array();
			var duration;
	        <xsl:for-each select="set:distinct(value[@name='duration'])">
				duration = durationToMonth('<xsl:value-of select="text()"/>');
				if (duration != null)
					durations[durations.length] = parseInt(duration);
			</xsl:for-each>

	        var selectedOffices = new Array();
		    <xsl:for-each select="value[@name='selected-office']">
			   selectedOffices[selectedOffices.length] = '<xsl:value-of select="text()"/>';
		    </xsl:for-each>

			credits[index].addCondition
			(
				 '<xsl:value-of select="value[@name='conditionId']"/>',
         		 '<xsl:value-of select="value[@name='currency']"/>',
		         '<xsl:value-of select="value[@name='rate']"/>',
		         '<xsl:value-of select="value[@name='commissionRate']"/>',
				 '<xsl:value-of select="value[@name='commissionBase']"/>',
		         '<xsl:value-of select="value[@name='minAmount']"/>',
		         '<xsl:value-of select="value[@name='maxAmount']"/>',
				 '<xsl:value-of select="value[@name='minInitialInstalment']"/>',
				 durations.sort(compareNumbers),
	             selectedOffices
	         );
		</xsl:for-each>
      </script>
   </xsl:template>

   <xsl:template name="productScript">
      <script type="text/javascript"><![CDATA[
	  /* Вид кредита */
      function Kind(id, kindIsTarget, name)
      {
         this.id       = id;
         this.kindIsTarget  = kindIsTarget;
         this.name     = name;
      }

	  /* кредитный офис */
      function Office(id, name)
      {
         this.id       = id;
         this.name     = name;
      }

      /* Продукт */
      function Credit(id, name, kindId)
      {
         this.id     = id;
         this.name   = name;
         this.kindId   = kindId;
         this.conditions = new Array();

         this.addCondition = function(conditionId, currency, rate, commissionRate, commissionBase,
		 					minimumAmount, maximumAmount, minInitialInstalment, durations, selectedOffices)
         {
            var condition = new Object();
			condition.conditionId    = conditionId;
            condition.currency       = currency;
            condition.rate           = parseFloat(rate);
			condition.commissionRate = commissionRate;
			condition.commissionBase = commissionBase;
            condition.minimumAmount  = minimumAmount;
            condition.maximumAmount  = maximumAmount;
			condition.minInitialInstalment = minInitialInstalment;
			condition.durations		 = durations;
			condition.selectedOffices  = selectedOffices;

            this.conditions[this.conditions.length] = condition;
         }
      }
      /* End of Продукт */
      ]]>
      </script>
   </xsl:template>
</xsl:stylesheet>