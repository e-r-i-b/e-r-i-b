function onClick(elem)
{
	var selected = $('.chosenCondition');
	if ($(selected).length > 0)
	{
		for (var i=0; i<$(selected).length; i++)
		{
			$(selected[i]).removeClass('chosenCondition');
		}
	}

	$(elem).addClass('chosenCondition');
}

var amountFormat = {decimalSeparator: ',', thousandSeparator: ' ', numberOfDecimals: 0};

function formatAmount(amount, amountFormat)
{
	return $().number_format(amount, amountFormat);
}

var creditProduct = function (id, name, ensuring)
{
	this.id       = id;
	this.name     = name;
	this.ensuring = ensuring;
};

var creditType = function (id, name, code)
{
	this.id      = id;
	this.name    = name;
	this.code    = code;
	this.credits = [];
};

var currCondition = function (id, currencyCode, minPercentRate, maxPercentRate, minAmount, maxAmount, includeMaxAmount, percentageOfCost, maxLimitPercent, currencySign, json)
{
	this.id = id;
	this.currencyCode = currencyCode;
	this.minPercentRate = minPercentRate;
	this.maxPercentRate = maxPercentRate;
	this.minAmount = minAmount;
	this.maxAmount = maxAmount;
	this.includeMaxAmount = includeMaxAmount;
	this.percentageOfCost = percentageOfCost;
	this.maxLimitPercent = maxLimitPercent;
	this.currencySign    = currencySign;
	this.json = json;
};

var condition = function (id, creditTypeId, creditProductId, includeMaxDuration, minDuration, maxDuration, additionalConditions, json)
{
	this.id = id;
	this.creditTypeId = creditTypeId;
	this.creditProductId = creditProductId;
	this.includeMaxDuration = includeMaxDuration;
	this.minDuration = minDuration;
	this.maxDuration = maxDuration;
	this.currConditions = [];
	this.additionalConditions = additionalConditions;
	this.json = json;

	this.findCurrCondition = function (currencyCode)
	{
		for (var i = 0; i < this.currConditions.length; i++)
		{
			if (this.currConditions[i].currencyCode === currencyCode)
			{
				return this.currConditions[i];
			}
		}

		return null;
	};
};

function CreditTypes()
{
	var creditTypesArray = [];

	return {
		addCreditType: function (id, name, code)
		{
			if (this.findCreditTypeById(id) == null)
			{
				creditTypesArray.push(new creditType(id, name, code));
			}
		},

		addCreditProduct: function (creditTypeId, id, name, ensuring)
		{
			var creditType = this.findCreditTypeById(creditTypeId);
			if (creditType)
			{
				var exist = false;
				for (var i = 0; i < creditType.credits.length; i++)
				{
					if (creditType.credits[i].id == id)
					{
						exist = true;
						break;
					}
				}

				if (!exist)
				{
					creditType.credits.push(new creditProduct(id, name, ensuring));
				}
			}
		},

		getCreditTypeByIndex: function (i)
		{
			return creditTypesArray[i];
		},

		findCreditTypeById: function (creditTypeId)
		{
			for (var i = 0; i < creditTypesArray.length; i++)
			{
				if (creditTypesArray[i].id == creditTypeId)
				{
					return creditTypesArray[i];
				}
			}

			return null;
		},

		isEmpty: function ()
		{
			return creditTypesArray.length == 0;
		},

		size: function ()
		{
			return creditTypesArray.length;
		}
	}
}

function Conditions()
{
	var conditions = [];

	function findConditionByItId(conditionId)
	{
		for (var i = 0; i < conditions.length; i++)
		{
			if (conditions[i].id == conditionId)
			{
				return conditions[i];
			}
		}

		return null;
	}

	return {
		addCondition: function (id, creditTypeId, creditProductId, includeMaxDuration, minDuration, maxDuration, additionalConditions, json)
		{
			conditions.push(new condition(id, creditTypeId, creditProductId, includeMaxDuration, minDuration, maxDuration, additionalConditions, json));
		},

		addCurrCondition: function (conditionId, currConditionId, currencyCode, minPercentRate, maxPercentRate, minAmount, maxAmount, includeMaxAmount, percentageOfCost, maxLimitPercent, currencySign, json)
		{
			var condition = findConditionByItId(conditionId);
			if (condition)
			{
				condition.currConditions.push(new currCondition(currConditionId, currencyCode, minPercentRate, maxPercentRate, minAmount, maxAmount, includeMaxAmount, percentageOfCost, maxLimitPercent, currencySign, json));
			}
		},

		findConditionById: function (conditionId)
		{
			return findConditionByItId(conditionId);
		},

		findConditionByIds: function (creditTypeId, creditProductId)
		{
			for (var i = 0; i < conditions.length; i++)
			{
				var condition = conditions[i];

				if (condition.creditTypeId == creditTypeId && condition.creditProductId == creditProductId)
				{
					return condition;
				}
			}

			return null;
		},

		isEmpty: function ()
		{
			return conditions.length == 0;
		}
	}
}

function CommonConditionsControls()
{
	var conditions = new Conditions();
	var creditTypes = new CreditTypes();

	var amountSliderConfig =
	    {
		    formatting: {
			    enabled           : true,
			    decimalSeparator  : ',',
			    thousandsSeparator: ' ',
			    handler           : formatFieldsByClassname,
			    field             : 'loanAmount'
		    }
	    };

	var amountSlider = new HidedArrowSlider().extendConfiguration(amountSliderConfig);
	var durationSlider = new DateSlider();

	var creditTypeSelect;
	var creditProductSelect;
	var loanCurrencySelect;

	var rateElement;
	var currencySignElement;
	var conditionIdElement;
	var conditionCurrIdElement;
	var formattedDurationElement;
	var loanDescriptionElement;
	var percentageOfCostElement;
	var jsonCondition;
	var jsonCurrency;

	function createOption(value, text, selected)
	{
		var option = document.createElement("option");
		$(option).val(value);
		$(option).text(text);

		if (selected)
		{
			option.selected = 'true';
		}

		return option;
	}

	function clearSelect(select)
	{
		if (select == null)
		{
			return;
		}

		while (select.options.length > 0)
		{
			select.remove(0);
		}
	}

	function getSelectedCreditType()
	{
		var creditTypeId = creditTypeSelect.options[creditTypeSelect.selectedIndex].value;
		if (creditTypeId == 0)
		{
			return null;
		}

		return creditTypes.findCreditTypeById(creditTypeId);
	}

	function getSelectedCreditProduct(creditType)
	{
		if (creditType == null)
		{
			return null;
		}

		var creditProductId = creditProductSelect.options[creditProductSelect.selectedIndex].value;
		if (creditProductId == 0)
		{
			return null;
		}

		for (var i = 0; i < creditType.credits.length; i++)
		{
			if (creditType.credits[i].id == creditProductId)
			{
				return creditType.credits[i];
			}
		}

		return null;
	}

	function getSelectedCondition()
	{
		var creditType = getSelectedCreditType();
		var creditProduct = getSelectedCreditProduct(creditType);

		if (creditType && creditProduct)
		{
			return conditions.findConditionByIds(creditType.id, creditProduct.id);
		}

		return null;
	}

	function getSelectedCurrCondition()
	{
		var condition = getSelectedCondition();
		if (condition)
		{
			switch (condition.currConditions.length)
			{
				case 0:
				{
					break;
				}
				default:
				{
					return condition.findCurrCondition(loanCurrencySelect.options[loanCurrencySelect.selectedIndex].value);
				}
			}
		}

		return null;
	}

	function loadCreditTypes()
	{
		if (creditTypeSelect.options.length == 0)
		{
            var name = null;
            if(isNotEmpty(jsonCondition.val()))
                name = $.parseJSON(jsonCondition.val())["creditProductType"]["name"];
			for (var i = 0; i < creditTypes.size(); i++)
			{
				var creditType = creditTypes.getCreditTypeByIndex(i);
				creditTypeSelect.appendChild(createOption(creditType.id, creditType.name, isEmpty(name) ? i==0 : name == creditType.name));
			}
		}
	}

	function loadCreditProducts()
	{
		clearSelect(creditProductSelect);

		var selCreditType = getSelectedCreditType();
		if (selCreditType)
		{
            var name = null;
            if(isNotEmpty(jsonCondition.val()))
                name = $.parseJSON(jsonCondition.val())["creditProduct"]["name"];
			var credits = selCreditType.credits;
			for (var i = 0; i < credits.length; i++)
			{
				var credit = credits[i];
				creditProductSelect.appendChild(createOption(credit.id, credit.name, isEmpty(name) ? i==0 : name == credit.name));
			}
		}
	}

	function loadCurrencies()
	{
		clearSelect(loanCurrencySelect);

		var condition = getSelectedCondition();
		if (condition)
		{
            var currencyCode = null;
            if(isNotEmpty(jsonCurrency.val()))
                currencyCode = $.parseJSON(jsonCurrency.val())["currency"];
			var currConditions = condition.currConditions;
			if (currConditions.length > 0)
			{
				for (var i = 0; i < currConditions.length; i++)
				{
					var currCondition = currConditions[i];
					loanCurrencySelect.appendChild(createOption(currCondition.currencyCode, currCondition.currencyCode, (isEmpty(currencyCode) ? currCondition.currencyCode =='RUB' : currCondition.currencyCode == currencyCode)));
				}
			}
		}
	}

	function changeLoanDuration(value)
	{
		if (!value)
		{
			formattedDurationElement.text('');
			return;
		}

		var year = value.hasOwnProperty('years') ? value['years'] : 0;
		var month = value.hasOwnProperty('months') ? value['months'] : 0;

		var text = '';
		if (year > 0)
		{
			text += year + ' ' + selectSklonenie(year, years);
		}

		if (month > 0)
		{
			if (year > 0)
			{
				text += ' и ';
			}

			text += month + ' ' + selectSklonenie(month, months);
		}

		if (text)
		{
			text = '— '.concat(text);
		}

		formattedDurationElement.text(text);
	}

	function showPercentageOfCostDesc(currCondition)
	{
		var minAmount = formatAmount(currCondition.minAmount, amountFormat);
		if (currCondition.percentageOfCost)
		{
			$('input[name="usePercent"]').val("true");
			$('input[name="fromLoanAmount"]').val(currCondition.minAmount);
			percentageOfCostElement.css('display', '')
					.text('от ' + minAmount + ' до ' + currCondition.maxLimitPercent + '% стоимости ' + (currCondition.includeMaxAmount ? 'включительно' : 'не включительно'));
		}

		else
		{
			$('input[name="fromLoanAmount"]').val(currCondition.minAmount);
			percentageOfCostElement.css('display', 'none')
		}

	}

	function showConditions()
	{
		$(".loanClass").css('display', '');
		$(".offerClass").css('display', 'none');
	}

	return {
        getAmountSlider : amountSlider,
        getDurationSlider : durationSlider,
		onChangeType: function ()
		{
			loadCreditTypes();
			loadCreditProducts();
			$(creditProductSelect).trigger('change');
		},

		onChangeProduct: function ()
		{
			var condition = getSelectedCondition();
			if (condition)
			{
				durationSlider.extendValues({
					maximum: parseFloat(condition.maxDuration),
					minimum: parseFloat(condition.minDuration)
				});

				durationSlider.extendConfiguration({includeMaximum: condition.includeMaxDuration});
				changeLoanDuration(durationSlider.getAdaptiveValue());

				conditionIdElement.val(condition.id);
				jsonCondition.val(condition.json);

				//<%-- Описание кредита на общих условиях  --%>
				while (loanDescriptionElement.hasChildNodes())
				{
					loanDescriptionElement.removeChild(loanDescriptionElement.childNodes[0]);
				}

				var selCreditType = getSelectedCreditType();
				if (selCreditType)
				{
					//<%-- Информация об обеспечении --%>
					var liEnsuring = document.createElement('li');
					var creditProduct = getSelectedCreditProduct(selCreditType);
					if (creditProduct.ensuring)
					{
						liEnsuring.innerHTML = '&mdash;&nbsp;Требуется обеспечение';
					}
					else
					{
						liEnsuring.innerHTML = '&mdash;&nbsp;Обеспечение не требуется';
					}
					loanDescriptions.appendChild(liEnsuring);

					//<%-- Информация о сроке --%>
					var liDuration = document.createElement('li');
					var minYear = (condition.minDuration / 12) >> 0;
					var minMonth = (condition.minDuration % 12);

					var maxValue = (condition.includeMaxDuration) ? condition.maxDuration : condition.maxDuration - 1;
					var maxYear  = (maxValue / 12) >> 0;
					var maxMonth = (maxValue % 12);

					var text = '&mdash;&nbsp;От ';
					if (minYear > 0)
					{
						text += minYear + (minYear == 1 ? ' года' : ' лет');
					}

					if (minMonth > 0)
					{
						text += ' ' + (minMonth == 1 ? minMonth + ' месяца' : minMonth + ' месяцев');
					}

					text += ' до ';
					if (maxYear > 0)
					{
						text += maxYear + (maxYear == 1 ? ' года' : ' лет');
					}

					if (maxMonth > 0)
					{
						text += (maxYear > 0 ? ' и ' : '') + maxMonth + (maxMonth == 1 ? ' месяца' : ' месяцев');
					}

					liDuration.innerHTML = text;
					loanDescriptions.appendChild(liDuration);

					//<%-- Дополнительная информация --%>
					if (condition.additionalConditions.length > 0)
					{
						var linkA = document.createElement('a');
						linkA.href = '#';
						linkA.innerHTML = 'Подробнее';
						linkA.className = 'blueGrayLinkDotted';

						var linkLi = document.createElement('li');
						linkLi.appendChild(linkA);

						var hiddenLi = document.createElement('li');
						hiddenLi.id = 'hideDescription';
						hiddenLi.innerHTML = '&mdash;&nbsp;'.concat(condition.additionalConditions);

						loanDescriptions.appendChild(hiddenLi);
						loanDescriptions.appendChild(linkLi);

						$(hiddenLi).css('display', 'none');
						$(linkA).click(function ()
						{
							if ($(hiddenLi).css('display') == 'none')
							{
								$(hiddenLi).css('display', '');
								$(linkA).text('Скрыть');
							}
							else
							{
								$(hiddenLi).css('display', 'none');
								$(linkA).text('Подробнее');
							}

							return false;
						});
					}
				}
			}

			loadCurrencies();
			$(loanCurrencySelect).trigger('change');
		},

		onChangeCurrency: function ()
		{
			var currCondition = getSelectedCurrCondition();
			$('input[name="usePercent"]').val("false");
			if (currCondition)
			{
				var rate = '';
				if (currCondition.minPercentRate.length > 0 && currCondition.maxPercentRate.length > 0)
				{
					rate = currCondition.minPercentRate + '-' + currCondition.maxPercentRate;
				}
				else if (currCondition.minPercentRate.length > 0)
				{
					rate = 'от ' + currCondition.minPercentRate;
				}
				else if (currCondition.maxPercentRate.length > 0)
				{
					rate = 'до ' + currCondition.maxPercentRate;
				}

				showPercentageOfCostDesc(currCondition);

				amountSlider.extendValues({
					minimum: parseFloat(currCondition.minAmount),
					maximum: parseFloat(currCondition.maxAmount)
				});

				amountSlider.extendConfiguration({
					arrowDisabled : currCondition.percentageOfCost,
					includeMaximum: currCondition.includeMaxAmount
				});

				rateElement.text(rate);
				conditionCurrIdElement.val(currCondition.id);
				currencySignElement.text(currCondition.currencySign);
				jsonCurrency.val(currCondition.json);
			}
		},

		onSelectCondition: function ()
		{
			if (conditions.isEmpty())
			{
				return;
			}

			showConditions();
			$(creditTypeSelect).trigger('change');
            initByUserValues();
        },

		hasConditions: function ()
		{
			return !conditions.isEmpty();
		},

		init: function ()
		{
			if (arguments.length > 0)
			{
				conditions  = arguments[0] || {};
				creditTypes = arguments[1] || {};
			}

			if (conditions.isEmpty())
			{
				return;
			}

			creditTypeSelect         = document.getElementById('creditTypeId');
			creditProductSelect      = document.getElementById('creditProductId');
			loanCurrencySelect       = document.getElementById('loanCurrency');
			loanDescriptionElement   = document.getElementById('loanDescriptions');

			formattedDurationElement = $('#formattedLoanDuration');
			rateElement              = $('#loanRate');
			currencySignElement      = $('#currencySign');
			percentageOfCostElement  = $('#percentageOfCostDesc');

			conditionIdElement       = $("input[name='condId']");
			jsonCondition            = $("input[name='jsonCondition']");
			conditionCurrIdElement   = $("input[name='condCurrId']");
			jsonCurrency             = $("input[name='jsonCurrency']");

			amountSlider.bind2(document.getElementById('loanAmount'));
			durationSlider.bind2(document.getElementById('loanPeriod'));

			durationSlider.valueChangeListener = function ()
			{
				changeLoanDuration(durationSlider.getAdaptiveValue());
			};
		}
	}
}

function LoanOffersControls()
{
	var loanOffers = [];
	var loanSliderConfig =
	    {
		    formatting: {
			    enabled           : true,
			    decimalSeparator  : ',',
			    thousandsSeparator: ' ',
			    handler           : formatFieldsByClassname,
			    field             : 'loanOfferAmount'
		    }
	    };

	var loanOfferAmountSlider = new ValueSlider().extendConfiguration(loanSliderConfig);

	var selected;
	var loanRateElement;
	var offerTitleElement;
	var currencySignElement;
	var loanDurationElement;
	var loanDurationField;

	var loanOfferIdElement;

	function getMinAmountByTb(loanOffer)
	{
		if (loanOffer)
		{
			if (loanOffer.hasOwnProperty('currency') && loanOffer.hasOwnProperty('terbank'))
			{
				switch (loanOffer['currency'])
				{
					case 'RUB':
					{
						return (loanOffer['terbank'] == 38 || loanOffer['terbank'] == 99) ? 45000 : 15000;
					}

					case 'USD':
					{
						return (loanOffer['terbank'] == 38 || loanOffer['terbank'] == 99) ? 1400 : 450;
					}

					case 'EUR':
					{
						return (loanOffer['terbank'] == 38 || loanOffer['terbank'] == 99) ? 1000 : 300;
					}
				}
			}
		}
	}

	function changeTitle()
	{
		if (selected)
		{
			offerTitleElement.text(selected['productName']);
		}
	}

	function changeRate()
	{
		if (selected)
		{
			loanRateElement.text(selected['rate']);
		}
	}

	function changeAmount()
	{
		if (selected)
		{
			currencySignElement.text(selected.currencySign);
		}
	}

	function changeDuration(duration)
	{
		if (selected)
		{
			loanDurationElement.text(duration);
			loanDurationField.val(duration);
		}
	}

	function showLoanOffers()
	{
		$(".loanClass").css('display', 'none');
		$(".offerClass").css('display', '');
	}

	return {

        getLoanOfferAmountSlider : loanOfferAmountSlider,

		getLoanOffers : function ()
		{
			return loanOffers;
		},

		firstLoanOfferId: function ()
		{
			if (this.hasLoanOffers())
			{
				return loanOffers[0].id + "_" + loanOffers[0].duration;
			}

			return null;
		},

		hasLoanOffers: function ()
		{
			return loanOffers.length > 0;
		},

		init: function ()
		{
			if (arguments.length > 0)
			{
				loanOffers = arguments[0] || [];
			}

			loanOfferAmountSlider.bind2(document.getElementById('loanOfferAmount'));

			loanOfferIdElement = $('input[name="loanOfferId"]');

			loanRateElement     = $('#loanRate');
			offerTitleElement   = $('#offerTitle');
			currencySignElement = $('#loanOfferCurrencySign');
			loanDurationElement = $('#loanDurationSpan');
			loanDurationField   = $('input[name="loanPeriod"]');

			loanOffers.sort(function (current, next)
			{
				if (current.currency != next.currency)
				{
					if ("RUB" == current.currency)
					{
						return -1;
					}

					if ("RUB" == next.currency)
					{
						return 1;
					}

					if ("EUR" == current.currency)
					{
						return 1;
					}

					if ("EUR" == next.currency)
					{
						return -1;
					}
				}

				return next['maxLimit'] > current['maxLimit'] ? 1 : -1;
			});
		},

		onSelectLoanOffer: function (element)
		{
			if (loanOffers.length > 0)
			{
				if (element)
				{
					for (var i = 0; i < loanOffers.length; i++)
					{
						if (loanOffers[i].id == element.id.split("_")[0])
						{
							selected = loanOffers[i];
							break;
						}
					}
				}
				else
				{
					selected = loanOffers[0];
				}

				if (selected)
				{
					var duration = element.id.split("_")[1];
					changeRate();
					changeTitle();
					changeAmount();
					changeDuration(duration);
					showLoanOffers();

					var selectMaxLimit;
					switch (duration)
					{
						case('6'):
							selectMaxLimit = selected['month6'];
							break;
						case('12'):
							selectMaxLimit = selected['month12'];
							break;
						case('18'):
							selectMaxLimit = selected['month18'];
							break;
						case('24'):
							selectMaxLimit = selected['month24'];
							break;
						case('36'):
							selectMaxLimit = selected['month36'];
							break;
						case('48'):
							selectMaxLimit = selected['month48'];
							break;
						case('60'):
							selectMaxLimit = selected['month60'];
							break;
					}
					loanOfferAmountSlider.extendValues({
						maximum: parseFloat(selectMaxLimit),
						minimum: parseFloat(getMinAmountByTb(selected))
					});

					loanOfferIdElement.val(selected['id']);
				}
			}
		}
	}
}

var pageControls = new (function ()
{
	var loanOffersControls = new LoanOffersControls();
	var conditionsControls = new CommonConditionsControls();
	var signOfUseLoanOffer;

	return {
		init: function ()
		{
			if (arguments.length > 0)
			{
				loanOffersControls.init(arguments[2] || []);
				conditionsControls.init(arguments[0] || {}, arguments[1] || {});
			}

			signOfUseLoanOffer = $('input[name="useLoanOffer"]');
			if (signOfUseLoanOffer.val() === 'true')
			{
				var loanOffer  = $("input[name='loanOfferId']").val();
				var loanPeriod = $("input[id='loanOfferPeriod']").val();

                if (loanOffersControls.hasLoanOffers())
                {
                    var element = document.getElementById(loanOffer.concat('_', loanPeriod));
                    if (!$.isEmptyObject(element))
                    {
                        element.click();
                        return;
                    }
                    else
                    {
                        $('#'.concat(loanOffersControls.firstLoanOfferId())).trigger('click');
                        return;
                    }
                }
			}
            else if (signOfUseLoanOffer.val() == 'false')
            {
                changeLoanTypeConditions("usualLoanButton");
                this.onSelectCondition();
                hideOrShow('loanOffersBlock', true);
                return;
            }

			var condition = $("input[name='condId']").val();
			if (condition)
			{
				if (conditionsControls.hasConditions())
				{
					if (loanOffersControls.hasLoanOffers())
					{
						$('#usualLoanBlock').trigger('click');
					}
					else
					{
						this.onSelectCondition();
					}

					return;
				}
			}

			if (!loanOffer || !condition)
			{
				if (loanOffersControls.hasLoanOffers())
				{
					$('#'.concat(loanOffersControls.firstLoanOfferId())).trigger('click');
				}
				else
				{
					this.onSelectCondition();
				}
			}
		},

		onSelectCondition: function ()
		{
			conditionsControls.onSelectCondition();
			signOfUseLoanOffer.val(false);
            $("input[name='loanOfferId']").val('');
		},

		onSelectLoanOffer: function (element)
		{
			loanOffersControls.onSelectLoanOffer(element);
			signOfUseLoanOffer.val(true);
		},

		onChangeCreditType: function ()
		{
			conditionsControls.onChangeType();
		},

		onChangeCreditProduct: function ()
		{
			conditionsControls.onChangeProduct();
		},

		onChangeLoanCurrency: function ()
		{
			conditionsControls.onChangeCurrency();
		},
        getAmountSlider : conditionsControls.getAmountSlider,
        getDurationSlider : conditionsControls.getDurationSlider,
        getLoanOfferAmountSlider: loanOffersControls.getLoanOfferAmountSlider,

		getLoanOffersControls 	: function ()
		{
			return loanOffersControls;
		}
	}
});
