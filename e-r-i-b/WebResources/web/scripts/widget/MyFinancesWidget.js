define("widget/MyFinancesWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/on",
    "dojo/has",
    "widget/script",
    "widget/GenericWidget"
], function(lang, declare, on, has, script, GenericWidget)
{
    /**
     * Виджет "доступные средства"
     */

    return declare("widget.MyFinancesWidget", [ GenericWidget ],
    {
        financeDiagram : undefined,
        rate : undefined,
        products : undefined,
        diagramId : undefined,
        rubCurrency : undefined,
        fillCurrencyRate: undefined,

        /**
         * Функция построения данных для диаграммы
         * Заполняется в jsp
         * [readonly]
         */
        buildDiagramData : undefined,

    ///////////////////////////////////////////////////////////////////////
    // Умолчательные настройки виджета

        defaultSettings: function()
          {
            return lang.mixin(this.inherited(arguments), {
                title: "Доступные средства"
            });
          },

        onStartup: function()
          {
              this.inherited(arguments);

              if (has("ie") < 9)
                  script.include("myfinances-excanvas", this.resourcesRealPath + "/scripts/excanvas.js");
              
              if(this.fillCurrencyRate)
              {
                  this.diagramId = this.codename;
                  var a = this.buildDiagramData();
                  this.rate = a[0] ;
                  this.products = a[1];
                  this.financeDiagram = financeDiagram(this.diagramId, this.products, this.rate, this.rubCurrency);
                  initDiagramData(this.financeDiagram);
              }
          },

        onRefresh: function()
          {
              this.inherited(arguments);
              if(this.fillCurrencyRate)
                drawDiagram(this.financeDiagram);
          }
    });
});
