define(['marionette', 'backbone', 'relational'], function(Mn, Backbone)
{
	var TopUpModel  = Backbone.RelationalModel.extend({});
	var TopUpsModel = Backbone.Collection.extend
	({
		model : TopUpModel
	});

	var LoanOfferModel = Backbone.RelationalModel.extend
	({
		relations :
		[{
			 key            : 'topUps',
			 type           : Backbone.HasMany,
			 relatedModel   : TopUpModel,
			 collectionType : TopUpsModel
		}]
	});

	var PageModel = Backbone.RelationalModel.extend
	({
		relations :
		[{
			 key          : 'loanOffer',
			 type         : Backbone.HasOne,
			 relatedModel : LoanOfferModel
		}],

		keyWords :
		[
			'agreementNumber',
			'productName',
			'termStart',
			'idContract',
		    'amount'
		],

		parse : function(attr)
		{
			var loanOffer = attr.loanOffer;
			if (!loanOffer || loanOffer === undefined)
			{
				return;
			}

			var pdpText = loanOffer.pdpText;
			if (!pdpText || pdpText === undefined)
			{
				return;
			}

			var pdpHead   = null;
			var pdpFooter = null;

			if (pdpText && pdpText !== undefined)
			{
				var topUpReg = /(<\/?topUp>)/g;
				var begin    = -1;
				var end      = -1;
				var result   = [];

				/*
				 * ищем блок с шаблоном описывающим погашаемый кредит
				 */
				while ((result = topUpReg.exec(pdpText)) !== null)
				{
					if (begin == -1)
					{
						begin = topUpReg.lastIndex;

						if (result.index > 0)
						{
							pdpHead = pdpText.substring(0, result.index)
						}

						continue;
					}

					if (end == -1 || end < result.index)
					{
						end = result.index;

						if (topUpReg.lastIndex < pdpText.length)
						{
							pdpFooter = pdpText.substring(topUpReg.lastIndex, pdpText.length);
						}
					}
				}

				if (!(begin == -1 || end == -1))
				{
					var template   = pdpText.substring(begin, end);
					var keyWordReg = '';

					_.each(this.keyWords, function(word)
					{
						if (keyWordReg.length > 0)
						{
							keyWordReg += '|';
						}

						keyWordReg += '(\\<s/?>)'.replace('s', word);
					});

					/*
					 * заменить в извлеченном шаблоне предустановленные переменные на переменные использующиеся
					 * при сериализации объекта в шаблон view`a
					 */
					var pdpTextLoans = template.replace(new RegExp(keyWordReg, 'g'), function(match)
					{
						return match.replace('<', '{{=').replace('/>', '}}');
					});

					attr.loanOffer.pdpText = pdpHead;

					_.each(attr.loanOffer.topUps, function(topUp)
					{
						attr.loanOffer.pdpText += _.template(pdpTextLoans).call(this, topUp);
					});

					attr.loanOffer.pdpText += pdpFooter;
				}
			}

			return attr;
		}
	});

	var PageView = Mn.LayoutView.extend
	({
		el       : '#top-up-offer-layout',
		template : '#top-up-offer-layout-template',
		model    : PageModel,
		regions  :
		{
			topUpRegion : '#top-up-region'
		},

		serializeModel : function()
		{
			return this.model.toJSON();
		},

		onRender : function()
		{
			var loanOffer = this.model.get('loanOffer');

			if (loanOffer && loanOffer !== undefined)
			{
				if (loanOffer.get('topUps').length > 0)
				{
					this.showChildView('topUpRegion', new TopUpCompositeView(this.model));
				}
			}
		}
	});

	var TopUpCompositeView = Mn.CompositeView.extend
	({
		template   : '#top-up-composite-template',
		initialize : function(options)
		{
			this.model = options.get('loanOffer');
		},

		onRender : function()
		{
			this.$('#top-up-pdp-offer').append(this.model);
		}
	});

	return {

		types :
		{
			viewType      : PageView,
			viewModelType : PageModel
		}
	}
});