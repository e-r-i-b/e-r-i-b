var otherProductPercentLimit = 3.5;//количество процентов от общего объема средст, при котором продукт относим в "ќстальные"


function financeDiagram(diagramId, products, rate, rubCurrency)
{
    return{
        totalProductsAmount: 0,//общее количество средств на счетах клиента
        totalProductsOwnAmount: 0,//общее количество собственных средст средств на счетах клиента(totalProductsAmount - лимиты по кредитным и овердрафтным картам)
        jsFinancePlot: {},
        plotData: {
        data:[], //данные дл€ построени€ круговой диаграммы
        clickFunction:[], //функции, вызываемые при клике на сектор диаграммы
        label:[] //тексты всплывающих подсказок
        },
        products: products,

        plotId: diagramId + 'Plot',
        legendId: diagramId + 'Legend',
        labelId: diagramId + 'Label',
        totalBalanceAmountId: diagramId + 'TotalBalanceAmount',
        titleBoxOtherPercentId: diagramId + 'TitleBoxOtherPercent',
        percentBoxOtherPercentId: diagramId + 'PercentBoxOtherPercent',
        otherProductListId: diagramId + 'OtherProductList',
        titleBoxId: diagramId + 'TitleBox',
        percentBoxId: diagramId +  'PercentBox',
        titleBoxTextId: diagramId + 'TitleBoxText',
        otherProductBlockId: diagramId + 'OtherProductBlock',
        colorBoxId: diagramId + 'ColorBox',
        showCreditCardAmountId: diagramId + 'ShowCreditCardAmount',
        showCents: diagramId + 'ShowCents',

        productComparator: function  (a, b)
        {
            if($('#' + this.showCreditCardAmountId).is(':checked'))
                return b.money.inRUB - a.money.inRUB;
            return b.money.ownInRUB - a.money.ownInRUB;
        },

        getTotalAmount: function()
        {
            if($('#' + this.showCreditCardAmountId).is(':checked'))
                return this.totalProductsAmount;
            else
                return this.totalProductsOwnAmount;
        },

        init: function ()
        {
            rate['RUB'] = {
                rate: 1
            };
            var parent = this;
            this.products = products;
            this.products.sort(function(a, b)
            {
                return parent.productComparator(a, b);
            });
            this.totalProductsAmount = 0;
            var tempProducts = this.products;
            for (var i = 0; i < tempProducts.length; i++)
            {
                if (tempProducts[i].money.inRUB > 0)
                    this.totalProductsAmount += tempProducts[i].money.inRUB;
                if (tempProducts[i].money.ownInRUB > 0)
                    this.totalProductsOwnAmount += tempProducts[i].money.ownInRUB;
            }

        },

        drawLegend: function(seriesIndex)
        {
            var totalAmount = this.getTotalAmount();
            $('#' + this.totalBalanceAmountId).html(this.showSumm(totalAmount) + ' ' + rubCurrency);
            $('#' + this.legendId).empty();

            if (totalAmount == 0)
                return;
            var legend = document.getElementById(this.legendId);
            var s = this.jsFinancePlot.series[seriesIndex];
            var otherProducts = [];
            var tempProducts = this.products;

            var count = 0;
            for (var i = 0; i < tempProducts.length; i++)
            {
                var product = tempProducts[i];
                var percent = 0;
                if (product.getProductAmount() > 0)
                    percent = product.getProductAmount() / this.getTotalAmount() * 100;

                if(percent > otherProductPercentLimit)
                {
                    this.appendColorBox(legend,count,s.seriesColors[count]);
                    this.appendTitleBox(legend,count,product,percent);
                    this.appendBorderLine(legend);
                    count++;
                }
                else if(percent >= 0)
                {
                    //галка учитывать кредитные средства отжата, а баланс по счету отличаетс€ от того,
                    // что был при нажатой галке, значит, что продукт использует кредитные средства и отображать его не надо,
                    // если только собственные средства по нему не положительные
                    if (product.money.inRUB == product.getProductAmount() || product.getProductAmount() > 0)
                        otherProducts.push(product);
                }
            }
            if(otherProducts.length == 1)
            {
                var otherProduct = otherProducts[0];
                this.appendColorBox(legend,i-1,s.seriesColors[count]);
                this.appendTitleBox(legend,i-1,otherProduct,percent);
            }
            else if(otherProducts.length > 1)
            {
                this.appendColorBox(legend,"OtherPercent",s.seriesColors[count]);

                var titleBox = document.createElement("div");
                titleBox.id = this.titleBoxOtherPercentId;
                titleBox.className = "titleBox";

                var percentBox = document.createElement("div");
                percentBox.id = this.percentBoxOtherPercentId;
                percentBox.className = "percent";
                titleBox.appendChild(percentBox);

                var textBox = document.createElement("div");
                textBox.id = this.otherProductListId;
                textBox.className = "titleBoxText";

                var otherProductAmount = 0;
                this.appendBorderLine(textBox);
                for(var i = 0; i < otherProducts.length; i++)
                {
                    this.appendProductTextDiv(textBox,otherProducts[i],"");
                    //добавл€ем линию ко всем продуктам кроме последнего
                    if(i < otherProducts.length -1)
                        this.appendBorderLine(textBox);
                    if (otherProducts[i].getProductAmount() > 0)
                        otherProductAmount += otherProducts[i].getProductAmount();
                }
                percentBox.innerHTML = this.getStrPercent(otherProductAmount / this.getTotalAmount() * 100);

                legend.appendChild(titleBox);

                var otherProductBlock = document.createElement("div");
                otherProductBlock.id = this.otherProductBlockId;
                otherProductBlock.className = "otherBlock";

                var otherProductBlockTitle = document.createElement("div");
                var diagram = this;
                otherProductBlockTitle.onclick = function(){diagram.showOtherProducts();};
                otherProductBlockTitle.className = "simpleLink";
                otherProductBlockTitle.innerHTML = "ќстальные доступные средства";
                otherProductBlock.appendChild(otherProductBlockTitle);

                var otherProductBlockAmount = document.createElement("div");
                otherProductBlockAmount.className = "amount";
                otherProductBlockAmount.innerHTML = diagram.showSumm(otherProductAmount) + ' ' + rubCurrency;
                otherProductBlock.appendChild(otherProductBlockAmount);
                otherProductBlock.appendChild(textBox);
                titleBox.appendChild(otherProductBlock);
                $('#' + this.otherProductListId).hide();
            }
        } ,

        showOtherProducts : function()
        {
            $('#' + this.otherProductListId).toggle();
            $('#' + this.otherProductBlockId + " .simpleLink").toggleClass("hide");
        },

        getProductHighlightText: function(product)
        {
            var result = product.title + '<br/>' +
                               this.showSumm(product.money.total) + ' ' + product.money.currencyISO + '<br/>' +
                               this.getStrPercent(product.getProductAmount() / this.getTotalAmount() * 100);
            if (product.number)
            {
                result += '<br/>' + product.number;
            }
            return result;
        },

        getStrPercent: function(percent)
        {
            percent = Math.round(percent);
            if (percent == 0)
                return '<1%';
            else
                return percent + '%';
        },

        initJsFinancePlot: function()
        {
            var d = this;
            d.jsFinancePlot = $.jqplot(d.plotId, [d.plotData.data], {
               grid: {
                   borderWidth: 0,
                   shadow: false
               },
               gridPadding:{
                   top:0,
                   right:0,
                   bottom:0,
                   left:0
               },
               seriesDefaults:{
                   renderer:$.jqplot.DonutRenderer,
                   rendererOptions:{
                       diameter:300,
                       innerDiameter:140,
                       startAngle: -90,
                       dataLabels: 'value',
                       shadowOffset: 0
                   }
               }
            });
        },

        initPlotData: function()
        {
            this.plotData.data = [];
            this.plotData.clickFunction = [];
            this.plotData.label = [];
            var otherProducts = [];
            var product;
            var tempProducts = this.products;
            for(var i = 0; i < tempProducts.length; i++)
            {
                product = tempProducts[i];
                if(product.getProductAmount() > 0 && product.getProductAmount() / this.getTotalAmount() * 100 > otherProductPercentLimit)
                {
                    this.plotData.data.push(product.getProductAmount());
                    this.plotData.clickFunction.push(product.onclick);
                    this.plotData.label.push(this.getProductHighlightText(product));
                }
                else
                    otherProducts.push(product);
            }
            //если продукт, занимиющий менее otherProductPercentLimit один, то отображаем его как остальные, без группировки
            if(otherProducts.length == 1)
            {
                product = otherProducts[0];
                this.plotData.data.push(product.getProductAmount());
                this.plotData.clickFunction.push(product.onclick);
                this.plotData.label.push(this.getProductHighlightText(product));
            }
            else if(otherProducts.length > 1)
            {
                var otherProductAmount = 0;
                for(var i = 0; i < otherProducts.length; i++)
                    if (otherProducts[i].getProductAmount() > 0)
                        otherProductAmount += otherProducts[i].getProductAmount();
                this.plotData.data.push(otherProductAmount);
                var diagram = this;
                this.plotData.clickFunction.push(function(){diagram.showOtherProducts();});
                this.plotData.label.push("ќстальные доступные средства<br/>" +
                                    this.showSumm(otherProductAmount) + '<br/>' +
                                    this.getStrPercent(otherProductAmount / this.getTotalAmount() * 100));
            }
        },

        drawPlot: function()
        {
            if (this.getTotalAmount() <= 0)
            {
                $('#' + this.plotId).hide();
                return;
            }

            $('#' + this.plotId).show();

            var sbrf = {
                seriesStyles:{
                    seriesColors: window.graphics.pieColor,
                    highlightColors: window.graphics.pieHighlightColor
                },
                grid: {
                    background: 'rgba(0,0,0,0)'
                }
            };
            this.jsFinancePlot.themeEngine.newTheme('sbrf', sbrf);
            this.jsFinancePlot.activateTheme('sbrf');
            var diagram = this;
            /* —ектор под мышкой */
            $('#' + diagram.plotId).bind('jqplotDataHighlight',
                    function (ev, seriesIndex, pointIndex, data)
                    {
                        if (diagram.lastSelected != undefined)
                            $('#'+diagram.lastSelected).removeClass("pieOver");
                        $('#' + diagram.plotId).addClass("pointer");

                        var titleBox = document.getElementById(diagram.titleBoxId + pointIndex);
                        if(!titleBox)
                        {
                            titleBox = document.getElementById(diagram.titleBoxOtherPercentId);
                        }

                        var innetData =diagram.plotData.label[pointIndex];
                        window.graphics.showInfoDiv(innetData, ev);
                        diagram.lastSelected = titleBox.id;
                        $(titleBox).addClass("pieOver");
                    });

            /* ћышка вне сектора */
            $('#' + diagram.plotId).bind('jqplotDataUnhighlight',
                    function (ev, seriesIndex, pointIndex, data)
                    {
                        if (diagram.lastSelected != undefined)
                            $('#'+diagram.lastSelected).removeClass("pieOver");
                        window.graphics.closeInfoDiv();
                        $('#' + diagram.plotId).removeClass("pointer");
                    });

            $('#' + diagram.plotId).bind('jqplotDataClick',
                    function (ev, seriesIndex, pointIndex, data)
                    {
                        var clickFunction = diagram.plotData.clickFunction[pointIndex];
                        if (clickFunction)
                            clickFunction.call();
                    });

            $('#' + diagram.showCreditCardAmountId).bind('click',
                    function (ev, seriesIndex, pointIndex, data)
                    {
                        diagram.reDrawPlot();
                    });

            $('#' + diagram.showCents).bind('click',
                    function(ev, seriesIndex, pointIndex, data)
                    {
                        diagram.reDrawPlot();
                    });
        },

        reDrawPlot: function()
        {
            var parent = this;
            this.products.sort(function(a, b)
            {
                return parent.productComparator(a, b);
            });
            this.initPlotData();
            if (this.getTotalAmount() == 0)
            {
                $('#' + this.plotId).hide();
                this.drawLegend(0);
                return;
            }

            $('#' + this.plotId).show();
            this.jsFinancePlot.series[0].data = this.normilizeData(this.plotData.data);
            this.jsFinancePlot.replot();
            this.drawLegend(0);
        },

        appendColorBox : function(parent,id,color)
        {
            var colorBox = document.createElement("div");
            colorBox.id = this.colorBoxId + id;
            colorBox.className = "colorBox";
            colorBox.style.backgroundColor = color;
            parent.appendChild(colorBox);
        },

       appendBorderLine: function(parent)
        {
            var borderLine = document.createElement("div");
            borderLine.className = "borderBottom";
            parent.appendChild(borderLine);
        },

       appendTitleBox : function(parent,id,product, percent)
        {
            var titleBox = document.createElement("div");
            titleBox.id = this.titleBoxId + id;
            titleBox.className = "titleBox";

            var percentBox = document.createElement("div");
            percentBox.id = this.percentBoxId + id;
            percentBox.className = "percent";
            percentBox.innerHTML = this.getStrPercent(percent);
            titleBox.appendChild(percentBox);
            this.appendProductTextDiv(titleBox,product,id,true);

            parent.appendChild(titleBox);
        },

        appendProductTextDiv: function(parent, product, id, addCclass)
        {

            var textBox = document.createElement("div");
            textBox.id = this.titleBoxTextId + id;
            textBox.onclick = function(){product.onclick();};
            if(addCclass)
                textBox.className = "titleBoxText";
            textBox.innerHTML = product.title;
            if(product.type == "card")
                textBox.innerHTML = textBox.innerHTML + " <span class='subText'>" + product.typeText + "</span>";
            if(product.money.currencyISO != "RUB" && product.money.currencyISO != "RUR")
                textBox.innerHTML = textBox.innerHTML + " <span class='subText'>" + product.money.text + "</span>";
            textBox.innerHTML = textBox.innerHTML + "<div class='legendNumber'>" + product.number + "</div>";
            textBox.innerHTML = textBox.innerHTML + "<div class='amount'>" + this.showSumm(product.getProductAmount()) + ' ' + rubCurrency + "</div>";
            parent.appendChild(textBox);
        },

        /**
         * ¬озвращает сумму с копейками или без
         * @param amount сумма
         */
        showSumm: function(amount)
        {
            if($('#' + this.showCents).is(':checked'))
                return FloatToString(amount, 2, ' ', 21, RoundingMode.HALF_UP);
            else
                return FloatToString(amount, 0, ' ', 21,  RoundingMode.DOWN);
        },

        normilizeData : function(data)
        {
            var temp = [];
            for(var i= 0; i < data.length; i++)
                temp.push([i+1,data[i]]);
            return temp;
        }
    };
}

function initDiagramData(diagram)
{
    $.jqplot.config.enablePlugins = true;
    diagram.init();
    diagram.initPlotData();
}

function drawDiagram(diagram)
{
    diagram.initJsFinancePlot();
    diagram.drawPlot();
    diagram.drawLegend(0);
}