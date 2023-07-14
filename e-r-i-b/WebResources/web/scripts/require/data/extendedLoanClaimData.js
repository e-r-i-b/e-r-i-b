require(['../requirecfg'], function()
{
    require(['marionette', 'underscore', 'radio', 'backbone'], function (Mn, _, Radio, Backbone)
    {
        var offerChannel = Radio.channel('offer');

        var LoanOfferCollection = Backbone.Collection.extend({
            model : (Backbone.Model)
        });

        //  --------------------------------------------------
        //  Views --------------------------------------------
        //  --------------------------------------------------
        var TopUpItemView = Mn.ItemView.extend({
            template : '#topUpView'
        });

        var TopUpsView = Mn.CollectionView.extend({

            template   : '#topUpsView',
            collection : new LoanOfferCollection,

            childView  : TopUpItemView,

            initialize : function(options)
            {
                this.collection.reset();

                _.each(options['topUps'], function(topUp)
                {
                    this.collection.add(new this.collection.model(topUp));

                }, this);
            }
        });

        var DescriptionView = Mn.CompositeView.extend({

            template : '#descriptionView',

            ui :
            {
                provisionalSum  : '#provisionalSum',
                provisionalCur  : '#provisionalCurr',
                descriptionDate : '#descriptionDate'
            },

            serializeModel : function ()
            {
                return this.model;
            },

            initialize : function (options)
            {
                this.listenTo(offerChannel, 'description:amount:show', (this.onDescriptionAmountShow));
            },

            /**
             * Показать предварительную сумму к выдаче
             */
            onDescriptionAmountShow : function(data)
            {
                this.ui.provisionalSum.text(data.amount);
                this.ui.provisionalCur.text(data.currency);

                var currentDate = new Date();
                this.ui.descriptionDate.text(currentDate.getDate() + ' '.concat(monthToStringByNumber(currentDate.getMonth())));
            }
        });

        var TopUpView = Mn.LayoutView.extend({

            template : '#topUpsLayout',
            regions  :
            {
                topUpsRegion      : '#topUpsRegion',
                descriptionRegion : '#descriptionRegion'
            }
        });

        var ExtendedLoanClaimView = Mn.LayoutView.extend({

            el : 'body',
            ui :
            {
                form    : 'form',
                sliderCurrency : '#loanOfferCurrencySign',
                sliderAmount   : '.loanOfferAmount.fakeSliderInput',
                conditions     : '.selectConditions'
            },

            triggers :
            {
                'click @ui.conditions' :
                {
                    event          : 'condition:change description:amount:change',
                    preventDefault : false
                },
                // TODO исправить
                'click @ui.form' :
                {
                    event          : 'description:amount:change',
                    preventDefault : false
                },
                'mousemove @ui.form' :
                {
                    event          : 'description:amount:change',
                    preventDefault : false
                },
                'keyup @ui.form' :
                {
                    event          : 'description:amount:change',
                    preventDefault : false
                }
                // TODO
            }
        });

        var extendedLoanClaim = new (Mn.Application.extend({

            view       : new ExtendedLoanClaimView,
            initialize : function ()
            {
                this.view.parent = this;
                this.listenTo(offerChannel, 'loan:amount:update', (this.onLoanAmountUpdate));
            },

            onRender : function ()
            {
                console.log('extendedLoanClaim render');
            },

            onBeforeStart : function (options)
            {
                this.pageControls = options.pageControls;

                // Оставляем только предложения содержащие topup`ы
                this.offers = _.filter(pageControls.getLoanOffersControls().getLoanOffers(), function (offer)
                {
                    if (offer['topUps'] !== undefined && offer['topUps'].length > 0)
                    {
                        return offer;
                    }

                    return [];
                });
            },

            onStart : function()
            {
                this.view.trigger('condition:change');
                this.view.trigger('description:amount:change');
            },

            /**
             * Установка новой минимальной суммы кредита
             * @param offer выбранное предложение
             */
            onLoanAmountUpdate : function(offer)
            {
                var slider = this.pageControls.getLoanOfferAmountSlider;
                var amount = slider.getValues().minimum;

                _.each(offer['topUps'], function (topUp)
                {
                    amount += parseFloat(topUp['repaymentAmount']);
                });

                slider.extendValues({
                    minimum : amount
                });
            }
        }));

        //  --------------------------------------------------
        //  Other Events -------------------------------------
        //  --------------------------------------------------
        /**
         * Обрабока смены предложения (клиент кликает по плиткам).
         */
        extendedLoanClaim.view.on('condition:change', function ()
        {
            var offers = this.parent.offers;
            if (offers.length == 0)
            {
                return;
            }

            _.each(this.$el.find(this.ui.conditions), function (control)
            {
                if (control.className.indexOf('chosenCondition') > 0)
                {
                    var offer = _.find(offers, function (val)
                    {
                        return control.id.split('_')[0] === val.id;
                    });

                    if (offer && offer['topUps'].length > 0)
                    {
                        var topUpView = new TopUpView;

                        this.addRegions   ({topUpRegion : '#topUpRegion'});
                        this.showChildView('topUpRegion',  topUpView);
                        topUpView.showChildView('topUpsRegion',      new TopUpsView(offer));
                        topUpView.showChildView('descriptionRegion', new DescriptionView({model: offer}));

                        offerChannel.trigger('loan:amount:update', offer);
                    }
                    else
                    {
                        if (this.getRegion('topUpRegion'))
                        {
                            this.removeRegion('topUpRegion');
                        }
                    }
                }
            }, this);
        });

        /**
         * Изменить предварительную сумму к выдаче. Здесь произвадится только
         * поиск этой суммы, а также валюты
         */
        extendedLoanClaim.view.on('description:amount:change', function ()
        {
            var amount   = this.$el.find(this.ui.sliderAmount).val();
            var currency = this.$el.find(this.ui.sliderCurrency).text();

            offerChannel.trigger('description:amount:show', {amount: amount, currency: currency});
        });

        extendedLoanClaim.start({
            pageControls : pageControls
        });
    });
});