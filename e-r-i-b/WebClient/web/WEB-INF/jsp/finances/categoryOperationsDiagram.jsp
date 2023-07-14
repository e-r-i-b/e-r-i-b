<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--

type - тип данных дл€ диаграммы(списани€ или поступлени€): income, outcome.
categoryOperations - данные дл€ построени€ диаграммы: списани€ по категори€м или поступлени€ по категори€м
legendLabel - заголовок легенды

--%>

<tiles:importAttribute/>
<c:set var="isIncome" value="${0}"/>

<div id="outcomeOperationsData" class="hidableCategoriesOperationsData">
    <div class="emptyText" style="display:none;">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="greenBold"/>
            <tiles:put name="data">
                <span class="text-dark-gray normal relative">
                    <bean:message bundle="financesBundle" key="label.finance.emptyMessage"/>
                </span>
            </tiles:put>
        </tiles:insert>
    </div>
    <div id="outcomeOperationsPlot" style="width:350px; height:350px; overflow: hidden;float:left; display:none;"> </div>
    <div id="outcomeOperationsLegend" class="financeLegendBlock" style="display:none">
        <div class="totalBalance">
            <div>¬сего списаний</div>
            <div id="outcomeTotalAmount" class="totalAmount"> </div>
        </div>
        <div id="outcomeLegend" class="financeLegend"></div>
    </div>
    <div class="clear"></div>
    <input id="incomeOperationsoutcome" type="hidden" value="${isIncome}">
    <div id="Operationsoutcome" class="operationsListBlock" style="display: none;">
        <div class="align-right closeOperationsList">
            <a href="#" class="blueGrayLinkDotted" onclick="if(!redirectResolved()) return false;closeOperations('Operationsoutcome'); return false;">скрыть выписку</a>
        </div>

        <div id="listOperationsoutcome" class="operationsList"></div>
    </div>
</div>
<c:set var="totalAmount" value="0"/>
<script type="text/javascript">
    var otherCategoryPercentLimit = 3.5;//количество процентов от общего объема средств, при котором категорию относим в "ќстальные"
    var jsoutcomePlot;

    var outcomePie = {
            showCents: 'ShowCents',
            categories:[],
            totalAmount:
            {
                amount: 0,
                currency: "${phiz:getCurrencySign('RUB')}"
            },
            plotData:
            {
                data:[],
                clickFunction:[],
                label:[]
            },
            ajaxLoading: false,
            categoryComparator: function  (a, b)
            {
                return Math.abs(b.money.total) - Math.abs(a.money.total);
            },
            init: function()
            {
                var myself = this;
                <c:forEach items="${form.outcomeOperations}" var="operation">
                    <c:if test="${operation.categorySum!= null and operation.categorySum != 0}">
                    this.categories.push ({
                        name: '<c:out value="${operation.name}"/>',
                        categoryId: ${operation.id},
                        money: {
                            total: ${operation.categorySum},
                            currencyISO: "RUB",
                            postfix: "${phiz:getCurrencySign('RUB')}"
                        },
                        onclick:
                            function()
                            {
                                if(!redirectResolved())
                                    return false;
                                if (myself.ajaxLoading)
                                    return;

                                myself.ajaxLoading = true;
                                financesUtils.getOperationsToCategories('Operationsoutcome',
                                    {
                                        category: ${operation.id},
                                        onlyAvailableCategories:false,
                                        selectedCardIds: '${selectedCardIds}',
                                        openPageDate: '${openPageDate}'
                                    },
                                    function()
                                    {
                                        myself.ajaxLoading = false;
                                    });
                            }
                    });
                    <c:set var="totalAmount" value="${totalAmount + operation.categorySum}"/>
                    </c:if>
                </c:forEach>
                this.totalAmount.amount = ${totalAmount};

                var parent = this;
                this.categories.sort(function(a, b)
                {
                    return parent.categoryComparator(a, b);
                });

                this.initPlotData();
                editOperation.addCallbackFunction(myself.updateCategories);
            },
            getCategoryHighlightText: function(category)
            {
                return category.name + '<br/>' +
                       this.showSumm(-category.money.total) + ' ' + category.money.postfix + '<br/>' +
                       this.getStrPercent(category.money.total / this.totalAmount.amount * 100);
            },
            getStrPercent: function(percent)
            {
                percent = Math.round(percent);
                if (percent == 0)
                    return '<1%';
                else
                    return percent + '%';
            },
            drawLegend: function(plot, seriesIndex)
            {
                $('#outcomeTotalAmount').html(this.showSumm(-this.totalAmount.amount) + ' ' + this.totalAmount.currency);
                var legend = document.getElementById("outcomeLegend");
                $(legend).empty();
                var series = plot.series[seriesIndex];
                var otherCategories = [];
                var count = 0;
                for(var i=0; i<this.categories.length; i++)
                {
                    var category = this.categories[i];
                    var categoryPercent = category.money.total / this.totalAmount.amount * 100;
                    if(categoryPercent > otherCategoryPercentLimit)
                    {
                        this.appendColorBox(legend,i,series.seriesColors[i]);
                        this.appendTitleBox(legend,i,category,categoryPercent);
                        if(!(i == this.categories.length-1 && otherCategories.length == 0))
                            this.appendBorderLine(legend);
                        count++;
                    }
                    else
                        otherCategories.push(category);
                }
                if(otherCategories.length == 1)
                {
                    this.appendColorBox(legend,i-1,series.seriesColors[count]);
                    this.appendTitleBox(legend,i-1,otherCategories[0],otherCategories[0].money.total / this.totalAmount.amount * 100);
                }
                else if(otherCategories.length > 1)
                {
                    this.appendColorBox(legend,'outcomeOtherCategoriesPercent',series.seriesColors[count]);

                    var titleBox = document.createElement("div");
                    titleBox.id = "outcometitleBoxOtherPercent";
                    titleBox.className = "titleBox";

                    var percentBox = document.createElement("div");
                    percentBox.id = "outcomepercentBoxOtherPercent";
                    percentBox.className = "percent";
                    titleBox.appendChild(percentBox);

                    var textBox = document.createElement("div");
                    textBox.id = "outcomeotherCategoriesList";
                    textBox.className = "titleBoxText";

                    var otherCategoriesAmount = 0;
                    this.appendBorderLine(textBox);
                    for(var i=0;i<otherCategories.length;i++)
                    {
                        this.appendCategoryTextDiv(textBox,otherCategories[i],"");
                        //добавл€ем линию ко всем категори€м кроме последней
                        if(i < otherCategories.length -1)
                            this.appendBorderLine(textBox);
                        otherCategoriesAmount += otherCategories[i].money.total;
                    }
                    percentBox.innerHTML = this.getStrPercent(otherCategoriesAmount / this.totalAmount.amount * 100);
                    legend.appendChild(titleBox);

                    var otherCategoriesBlock = document.createElement("div");
                    otherCategoriesBlock.id = "outcomeotherCategoriesBlock";
                    otherCategoriesBlock.className = "otherBlock";

                    var otherCategoriesBlockTitle = document.createElement("div");
                    var parent = this;
                    otherCategoriesBlockTitle.onclick = function(){parent.showOtherCategories();};
                    otherCategoriesBlockTitle.className = "simpleLink";
                    otherCategoriesBlockTitle.innerHTML = "${otherCategoriesLabel}";
                    otherCategoriesBlock.appendChild(otherCategoriesBlockTitle);

                    var otherCategoriesBlockAmount = document.createElement("div");
                    otherCategoriesBlockAmount.className = "amount";
                    otherCategoriesBlockAmount.innerHTML = this.showSumm(-otherCategoriesAmount) + ' ' + this.totalAmount.currency;
                    otherCategoriesBlock.appendChild(otherCategoriesBlockAmount);
                    otherCategoriesBlock.appendChild(textBox);
                    titleBox.appendChild(otherCategoriesBlock);
                    $('#outcomeotherCategoriesList').hide();
                }
            },
            appendColorBox: function(parent,id,color)
            {
                var colorBox = document.createElement("div");
                colorBox.id = "colorBox" + id;
                colorBox.className = "colorBox";
                colorBox.style.backgroundColor = color;
                parent.appendChild(colorBox);
            },
            appendTitleBox: function(parent,id,category, percent)
            {
                var titleBox = document.createElement("div");
                titleBox.id = "outcometitleBox" + id;
                titleBox.className = "titleBox";

                var percentBox = document.createElement("div");
                percentBox.id = "percentBox" + id;
                percentBox.className = "percent";
                percentBox.innerHTML = this.getStrPercent(percent);
                titleBox.appendChild(percentBox);
                this.appendCategoryTextDiv(titleBox,category,id,true);

                parent.appendChild(titleBox);
            },
            appendCategoryTextDiv: function(parent, category, id, addCclass)
            {
                var textBox = document.createElement("div");
                textBox.id = "titleBoxText"+id;
                textBox.onclick = function(){category.onclick();};
                if(addCclass)
                    textBox.className = "titleBoxText";
                textBox.innerHTML = category.name;
                textBox.innerHTML = textBox.innerHTML + "<div class='amount'>" + this.showSumm(-category.money.total) + ' ' + category.money.postfix + "</div>";
                parent.appendChild(textBox);
            },
            appendBorderLine: function(parent)
            {
                var borderLine = document.createElement("div");
                borderLine.className = "borderBottom";
                parent.appendChild(borderLine);
            },
            showOtherCategories: function()
            {
                if(!redirectResolved())
                    return false;
                $('#outcomeotherCategoriesList').toggle();
                $('#outcomeotherCategoriesBlock .simpleLink').toggleClass("hide");
            },
            reDrawPlot: function()
            {
                var parent = this;
                this.categories.sort(function(a, b)
                {
                    return parent.categoryComparator(a, b);
                });
                this.initPlotData();
                if (this.totalAmount == 0)
                {
                    $(this).hide();
                    this.drawLegend(jsoutcomePlot, 0);
                    return;
                }

               // $(this).show();
                jsoutcomePlot.series[0].data = this.normilizeData(this.plotData.data);
                jsoutcomePlot.replot();
                this.drawLegend(jsoutcomePlot, 0);
            },
            initPlotData: function()
            {
                this.plotData.data = [];
                this.plotData.clickFunction = [];
                this.plotData.label = [];
                var otherProducts = [];
                var product;
                var tempProducts = this.categories;
                for(var i = 0; i < tempProducts.length; i++)
                {
                    product = tempProducts[i];
                    if(product.money.total / this.totalAmount.amount * 100 > otherCategoryPercentLimit)
                    {
                        this.plotData.data.push(product.money.total);
                        this.plotData.clickFunction.push(product.onclick);
                        this.plotData.label.push(this.getCategoryHighlightText(product));
                    }
                    else
                        otherProducts.push(product);
                }
                //если продукт, занимиющий менее otherProductPercentLimit один, то отображаем его как остальные, без группировки
                if(otherProducts.length == 1)
                {
                    product = otherProducts[0];
                    this.plotData.data.push(product.money.total);
                    this.plotData.clickFunction.push(product.onclick);
                    this.plotData.label.push(this.getCategoryHighlightText(product));
                }
                else if(otherProducts.length > 1)
                {
                    var otherProductAmount = 0;
                    for(var i = 0; i < otherProducts.length; i++)
                        otherProductAmount += otherProducts[i].money.total;
                    this.plotData.data.push(otherProductAmount);
                    var diagram = this;
                    this.plotData.clickFunction.push(function(){diagram.showOtherCategories();});
                    this.plotData.label.push("ќстальные категории списаний<br/>" +
                                        FloatToString(otherProductAmount,2,' ') + '<br/>' +
                                        this.getStrPercent(otherProductAmount / this.totalAmount.amount * 100));
                }
            },
            drawData: function()
            {
                if(this.plotIsDraw != undefined)
                    hideoutcomeOperationsList();
                //если график уже отрисован, то не рисуем его
                else
                {
                    $.jqplot.config.enablePlugins = true;
                    this.init();

                    if (outcomePie.plotData.data.length == 0)
                    {
                        $("#outcomeOperationsData .emptyText").show();
                        return;
                    }
                    hideoutcomeOperationsList();

                    jsoutcomePlot = $.jqplot('outcomeOperationsPlot', [outcomePie.plotData.data], {
                        grid: {
                            borderWidth: 0,
                            shadow: false
                        },
                        gridPadding: {
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

                    var sbrf = {
                        seriesStyles:{
                            seriesColors: window.graphics.pieColor,
                            highlightColors: window.graphics.pieHighlightColor
                        },
                        grid: {
                            background: 'rgba(0,0,0,0)'
                        }
                    };
                    jsoutcomePlot.themeEngine.newTheme('sbrf', sbrf);
                    jsoutcomePlot.activateTheme('sbrf');

                    this.drawLegend(jsoutcomePlot,0);

                    var plotPie = this;

                    /*—ектор под мышкой*/
                    $('#outcomeOperationsPlot').bind('jqplotDataHighlight',
                        function (ev, seriesIndex, pointIndex, data)
                        {
                            if (plotPie.lastSelected != undefined)
                                $('#'+plotPie.lastSelected).removeClass("pieOver");
                            $('#outcomeOperationsPlot').addClass("pointer");

                            var titleBox = document.getElementById("outcometitleBox" + pointIndex);
                            if(!titleBox)
                            {
                                titleBox = document.getElementById("outcometitleBoxOtherPercent");
                            }

                            var innerData = plotPie.plotData.label[pointIndex];
                            window.graphics.showInfoDiv(innerData, ev);

                            plotPie.lastSelected = titleBox.id;
                            $(titleBox).addClass("pieOver");
                        });

                    /*ћышка вне сектора*/
                    $('#outcomeOperationsPlot').bind('jqplotDataUnhighlight',
                        function (ev, seriesIndex, pointIndex, data)
                        {
                            if (plotPie.lastSelected != undefined)
                                $('#'+plotPie.lastSelected).removeClass("pieOver");
                            window.graphics.closeInfoDiv();
                            $('#outcomeOperationsPlot').removeClass("pointer");
                        });

                    $('#outcomeOperationsPlot').bind('jqplotDataClick',
                        function (ev, seriesIndex, pointIndex, data)
                        {
                            var clickFunction = plotPie.plotData.clickFunction[pointIndex];
                            if (clickFunction)
                                clickFunction.call();
                        });

                    $('#' + this.showCents).bind('click',
                    function(ev, seriesIndex, pointIndex, data)
                    {
                        if(!redirectResolved())
                            return false;
                        plotPie.reDrawPlot();
                    });

                    this.plotIsDraw = true;
                    }
                },
            showSumm: function(amount)
            {
                if($('#' + this.showCents).is(':checked'))
                    return FloatToString(amount, 2, ' ', 21, RoundingMode.HALF_UP);
                else
                    return FloatToString(amount, 0, ' ', 21,  RoundingMode.HALF_UP);
            },
            normilizeData : function(data)
            {
                var temp = [];
                for(var i= 0; i < data.length; i++)
                    temp.push([i+1,data[i]]);
                return temp;
            },
            updateCategory : function(operation)
            {
                if(operation.sum == 0)
                    return;

                var myself = this;
                for(var i=0; i<this.categories.length; i++)
                {
                    var category = this.categories[i];
                    if(operation.categoryId == category.categoryId)
                    {
                        category.money.total = category.money.total + operation.sum;
                        if(category.money.total  == 0)
                            this.categories.splice(i,1);
                        return;
                    }
                }
                //если дошли до сюда, значит данной категории пока нет на диаграмме. ƒобавим еЄ.
                this.categories.push ({
                    name: operation.categoryName,
                    categoryId: operation.categoryId,
                    money: {
                        total: operation.sum,
                        currencyISO: "RUB",
                        postfix: "${phiz:getCurrencySign('RUB')}"
                    },
                    onclick:
                        function()
                        {
                            if(!redirectResolved())
                                return false;
                            if (myself.ajaxLoading)
                                return;

                            myself.ajaxLoading = true;
                            financesUtils.getOperationsToCategories('Operationsoutcome',
                                {
                                    category: operation.categoryId,
                                    onlyAvailableCategories:false,
                                    selectedCardIds: '${selectedCardIds}',
                                    openPageDate: '${openPageDate}'
                                },
                                function()
                                {
                                    myself.ajaxLoading = false;
                                });
                        }
                    });

            },
            updateCategories: function(data)
            {
                for(var i = 0; i < data.newOperations.length; i ++)
                {
                    outcomePie.updateCategory(data.newOperations[i]);
                }
                outcomePie.updateCategory(data.oldOperation);
                outcomePie.updateCategory(data.newOperation);
                outcomePie.reDrawPlot();
            }

        };

        function hideoutcomeOperationsList()
        {
            $('#Operationsoutcome').hide();
            $('#outcomeOperationsPlot').show();
            $('#outcomeOperationsLegend').show();
        }
        $(document).ready(function(){outcomePie.drawData();});

</script>