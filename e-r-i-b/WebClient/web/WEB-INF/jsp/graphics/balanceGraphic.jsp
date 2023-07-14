<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<%--
    Шаблон для постороения графической выписки по accountAbstruct
    !!! Данный шаблон расчитан только на едениченое применение на странице

    title - заголовок "По счету"
    dateFrom - дата начала MM/DD/YYYY
    dateTo - дата окончания MM/DD/YYYY
    resourceAbstract - выписка
    descriptionBoth - описание для двух графиков
    descriptionLine - описание для графика балансов
    descriptionBar  - описание для графика оборотов
--%>
<tiles:importAttribute />
<c:set var="imagePath" value="${skinUrl}/images"/>
<script type="text/javascript" language="javascript">
var plot;
var BAR_SERIES_DATA = { sid: 0, zeroLineId: 2, yaxis: "yaxis", xaxis: "xaxis" };
var LINE_SERIES_DATA = { sid: 1, zeroLineId: 3, yaxis: "y2axis", xaxis: "xaxis" };
var BORDER_WIDTH = 2;
var THOUSAND_POSTFIX = " тыс.";
var CURRENCY_POSTFIX = "${phiz:getCurrencySign(resourceAbstract.closingBalance.currency.code)}";
var GRAPH_TITLE = "${title}";

var moneyFormat = {
    <logic:iterate id="element" collection="${phiz:getMoneyFormat()}" indexId="i">
        <c:if test="${i>0}">,</c:if>
        ${element.key}:"${element.value}"
    </logic:iterate>
    };

var graphicOperations = {};

<%--
  graphicOperations["MM/dd/yyyy"] = {
    dayBalance: 1000,
    dayOperations: -10,
    oparations: [ {sum: 1, description: 'За победу'}, ... ]
  };

--%>
<c:set var="lastDate" value="" />

<c:forEach items="${resourceAbstract.transactions}" var="transaction" >
    <c:set var="curDate"><bean:write name="transaction" property="date.time" format="MM/dd/yyyy"/></c:set>
    <%-- Конец дня --%>
    <c:if test="${curDate != lastDate }">
       <c:if test="${!empty lastDate}">
            graphicOperations["${lastDate}"]= {
                dayBalance : ${curBalance},
                dayOperations: ${curDayBalance},
                oparations: [${curOperations}]
                };
       </c:if>
       <%-- Текущий баланс дня --%>
       <c:set var="curDayBalance" value="0"/>                            <%--очищаем сумму дня --%>
       <c:set var="curOperations" value=""/>                             <%--очищаем операции дня --%>
       <c:set var="lastDate" value="${curDate}" />                       <%--запоминаем дату --%>
    </c:if>
    <c:set var="curBalance" value="${transaction.balance.decimal}" /> <%--запоминаем сумму в конце дня --%>
    <%-- Определяем размер операции --%>
    <c:set var="transactionSum" value="0" />
    <%-- В плюс --%>
    <c:if test="${(empty transaction.creditSum || transaction.creditSum.decimal == 0) && !empty transaction.accountCreditSum && transaction.accountCreditSum.decimal!=0}">
        <c:set var="transactionSum" value="${transactionSum + transaction.accountCreditSum.decimal }"/>
    </c:if>
    <c:if test="${!empty transaction.creditSum && transaction.creditSum.decimal != 0}">
        <c:set var="transactionSum" value="${transactionSum + transaction.creditSum.decimal }"/>
    </c:if>
    <%-- Минус --%>
    <c:if test="${(empty transaction.debitSum || transaction.debitSum.decimal == 0) && !empty transaction.accountDebitSum && transaction.accountDebitSum.decimal!=0}">
        <c:set var="transactionSum" value="${transactionSum - transaction.accountDebitSum.decimal }"/>
    </c:if>
    <c:if test="${!empty transaction.debitSum && transaction.debitSum.decimal != 0}">
        <c:set var="transactionSum" value="${transactionSum - transaction.debitSum.decimal }"/>
    </c:if>
    <c:set var="curDayBalance" value="${curDayBalance + transactionSum}"/>
    <%-- Добавляем операцию в масив --%>
    <c:set var="transactionTitle">
        <c:if test="${not empty transaction.description}">
            ${transaction.description}
        </c:if>&nbsp;
        <c:if test="${not empty transaction.shopInfo}">
            ${transaction.shopInfo}
        </c:if>
    </c:set>
    <c:if test="${!empty curOperations}"><c:set var="curOperations">${curOperations}, </c:set></c:if>
    <c:set var="curOperations">${curOperations} { sum: ${transactionSum}, description: "${phiz:escapeForJS(transactionTitle, true)}" }</c:set>
</c:forEach>

<%-- Добавляем последнее значение  --%>
<c:if test="${!empty lastDate}">
    graphicOperations["${lastDate}"]= {
        dayBalance : ${curBalance},
        dayOperations: ${curDayBalance},
        oparations: [${curOperations}]
        };
</c:if>

<%--
   Метод для получения данных для графиков
--%>
function getGraphicData ()
{
    var START = {
        date: "<bean:write name="resourceAbstract" property="fromDate.time" format="MM/dd/yyyy"/>",
        <c:if test="${not empty resourceAbstract.openingBalance.decimal}">
            sum: ${resourceAbstract.openingBalance.decimal},
        </c:if>
        dateFrom: new Date("${dateFrom}").getTime()
    };

    if (window.lineBarData && window.lineBarData)
       return { line: lineGraphicData, bar:lineBarData }
    window.lineGraphicData = [];
    window.lineBarData = [];

    <c:set var="today" value="${phiz:currentDate()}"/>
    <c:set var="todayJs"><bean:write name="today" property="time" format="MM/dd/yyyy"/></c:set>
    <%-- Сегодняшняя дата --%>
    var today = (new Date("${todayJs}")).getTime();
    <%-- Окончание периода --%>
    var endPeriodTime = (new Date("${dateTo}") ).getTime();
    var startTime = (new Date(START.date)).getTime();
    <%-- Защита от рисования баланса для будущего --%>
    var endTime = endPeriodTime>today?today:endPeriodTime;

    <%-- Для баров достаточно перебрать операции разложеные по дням --%>
    var zeroBarDate = (new Date(START.date)).getTime() ;
    <%-- В барах всегда должен быть 0--%>
    window.lineBarData.push([zeroBarDate, 0]) ;
    for (var date in graphicOperations)
    {
        var dateTime = (new Date(date)).getTime();
        if (START.dateFrom <= dateTime && endPeriodTime >= dateTime)
            window.lineBarData.push([date, graphicOperations[date].dayOperations]) ;
    }
    <%-- Балансы --%>
    var lastSum = START.sum;
    var curStringDate = "";
    <%-- перебераем с начала по endTime Включительно --%>
    for (var i = startTime; i < endTime + graphics.ONE_DAY; i = i + graphics.ONE_DAY )
    {
        curStringDate = graphics.dateToString(new Date(i));
        if (graphicOperations[curStringDate] != undefined)
        {
            lastSum = graphicOperations[curStringDate].dayBalance;
            if (START.dateFrom <= i && endPeriodTime + graphics.ONE_DAY > i)
                window.lineGraphicData.push([curStringDate, lastSum]);
            continue;
        }
        
        if (START.dateFrom <= i && endPeriodTime + graphics.ONE_DAY > i && lastSum)
            window.lineGraphicData.push([curStringDate, lastSum]);

    }


    return { line: window.lineGraphicData, bar: window.lineBarData }
}


<%--
   Метод для отрисовки легенды столбцов
--%>
function showBarLegend (poitData, ev)
{
    var date = new Date(poitData[0]);
    var operations = graphicOperations[poitData[0]].oparations;

    var text = "<b>"+date.getDate()+" "+ window.graphics.getMonth(date.getMonth()) +" "+date.getFullYear()+"</b>";
    text += "<table>";
    for (var i=0; i < operations.length; i++) {
        text += "<tr><td>"+operations[i].description+"</td><td class='sum'>";
        if (operations[i].sum >0 ) text += "+";
        text += $().number_format(operations[i].sum, moneyFormat)+CURRENCY_POSTFIX;
        text +="</td></tr>";
    }
    text += "</table>";
    window.graphics.showInfoDiv(text, ev);
}

<%--
   Метод для отображения легенды
--%>
function chartMove(ev, gridpos, datapos, neighbor, plot)
{
    var line = document.getElementById("lineGraph");
    var bar =  document.getElementById("barGraph");

    if (neighbor == null)
    {
        <%--
            Библиотека почему-то не обрабатывает негативные бары так, что поработаем за нее.
        --%>
        if (datapos[BAR_SERIES_DATA.yaxis] < 0 && bar.checked)
        {
          var negativeBarDate = new Date (datapos[BAR_SERIES_DATA.xaxis]);
          var dateString = window.graphics.dateToString(negativeBarDate);  
          if (new Date(dateString).getTime()+window.graphics.ONE_DAY/2 < negativeBarDate.getTime())
            dateString = window.graphics.dateToString(new Date(new Date(dateString).getTime()+window.graphics.ONE_DAY));

          if (graphicOperations[dateString] && graphicOperations[dateString].dayOperations < 0 &&
              graphicOperations[dateString].dayOperations <= datapos[BAR_SERIES_DATA.yaxis] )
          {
            showBarLegend ([dateString, graphicOperations[dateString].dayOperations], ev);
            return ;
          }
        }

        window.graphics.closeInfoDiv();
        return;
    }

    <%-- курсор над графоком - линии--%>
    if (neighbor.seriesIndex == LINE_SERIES_DATA.sid && line.checked)
    {
        var poitData = getGraphicData().line[neighbor.pointIndex];
        var date = new Date(poitData[0]);

        var text = "<nobr>"+ date.getDate()+" "+ window.graphics.getMonth(date.getMonth()) +" "+date.getFullYear();
        text += " баланс:"+$().number_format(poitData[1], moneyFormat)+CURRENCY_POSTFIX + "</nobr>";
        window.graphics.showInfoDiv(text, ev);
        return ;
    }
    <%-- курсор над графоком - столбец--%>
    if (neighbor.seriesIndex == BAR_SERIES_DATA.sid && bar.checked)
    {
        var poitData = getGraphicData().bar[neighbor.pointIndex];
        showBarLegend (poitData, ev);
        return ;
    }
}

<%-- Создаем графики --%>
$(document).ready(function()
{
    $.jqplot.config.enablePlugins = true;
    var barLabel = "Оборот средств "+GRAPH_TITLE+",";
    var lineLabel = "Баланс,";
    var ticks = window.graphics.getTicks(new Date("${dateFrom}"), new Date("${dateTo}"));
    var graphicData = getGraphicData ();

    var balanceZero = [
        ["${dateFrom}", 0],
        ["${dateTo}", 0]
    ];

    var barYTicks = window.graphics.getYTicks(graphicData.bar);
    var lineYTicks = window.graphics.getYTicks(graphicData.line);

    if (barYTicks.index == 1000) barLabel += THOUSAND_POSTFIX;
    if (lineYTicks.index == 1000) lineLabel += THOUSAND_POSTFIX;
    barLabel += " "+CURRENCY_POSTFIX;
    lineLabel += " "+CURRENCY_POSTFIX;
    // У оси
    // Бары
    var barYaxis = {
        useSeriesColor:true
    };
    // линия
    var lineYaxis = {
        useSeriesColor:true
    };

    barYaxis.ticks = barYTicks.ticks;
    lineYaxis.ticks = lineYTicks.ticks;

    if (window.graphics.isCanvasTextSupported())
    {
        barYaxis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
        barYaxis.labelOptions = {
            angle:-90,
            fontFamily: "Arial",
            enableFontSupport: true
        };
        barYaxis.label = barLabel;

        lineYaxis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
        lineYaxis.labelOptions = {
            angle:90,
            fontFamily: "Arial",
            enableFontSupport: true
        };
        lineYaxis.label = lineLabel;
    }
    else
    {
        barYaxis.label = window.graphics.getTurnedText(barLabel, -90);
        lineYaxis.label = window.graphics.getTurnedText(lineLabel, 90);
    }

    // x ось
    var barXaxis = {
        renderer: $.jqplot.DateAxisRenderer,
        pad: 0,
        borderWidth: 0,
        tickInterval: "1 day"
    };

    if (window.graphics.isCanvasTextSupported())
    {
        barXaxis.tickRenderer = $.jqplot.CanvasAxisTickRenderer;
        barXaxis.tickOptions = {
            angle:-60,
            fontFamily: "Arial"
        };

        barXaxis.ticks = ticks.data;
    }
    else
    {
        var ieTicks = ticks.data;
        for (var i = 0; i < ieTicks.length; i++)
            if (trim(ieTicks[i][1]) != '')
            {
                ieTicks[i][1] = window.graphics.getTurnedText(ieTicks[i][1], -60);
            }
        barXaxis.ticks = ieTicks;
    }


    $.jqplot.eventListenerHooks.push(['jqplotMouseMove', chartMove]);
    $.jqplot.eventListenerHooks.push(['jqplotMouseLeave', function() { window.graphics.closeInfoDiv(); } ]);

    <%-- Размер точек --%>
    var dotSize = 6;
    <%-- Согласно ТЗ по графикам если период выпискИ больше или равен 85 дней, то точки необходимо убрать --%>
    if (ticks.totalDays >= 85)
        dotSize = 1;

    plot = $.jqplot('chart', [ graphicData.bar, graphicData.line, balanceZero, balanceZero  ], {
        grid: {
            borderWidth: BORDER_WIDTH,
            shadow: false,
            backgroundColor: 'rgb(255, 255, 255)'
        },
        series:[
            {
                renderer:$.jqplot.BarRenderer,
                rendererOptions: {
                    barWidth: Math.round((document.getElementById("chart").offsetWidth - 250) / ticks.totalDays),
                    barMargin: 0,
                    shadowOffset: 0.5,
                    barPadding: 0,
                    highlightMouseOver: false
                },
                negativeSeriesColors: ["#ffa200"],
                color: '#82cb3b',
                fillToZero: true
            },
            {
                color: '#009cff',
                markerOptions:{
                    style:'filledBorderCircle',
                    lineWidth:1,
                    size: dotSize
                },
                xaxis:'x2axis',
                yaxis:'y2axis'
            },
            {
                shadow: false,
                lineWidth: 1,
                showMarker: false,
                color: '#777777',
                yaxis:'yaxis'

            },
            {
                shadow: false,
                lineWidth: 1,
                showMarker: false,
                color: '#777777',
                yaxis:'y2axis'
            }

        ],
        seriesDefaults:{pointLabels: {show: false}},
        axesDefaults:{ useSeriesColor:true },
        axes: {

            xaxis: barXaxis,
            yaxis: barYaxis,

            x2axis: {
                renderer:$.jqplot.DateAxisRenderer,
                tickOptions:{
                    show: false
                },
                ticks: ticks.data,
                borderWidth: 0,
                tickInterval: "1 day"
            },

            y2axis: lineYaxis
        }

    });
    // сдвигаем нули в низ
    plot.moveSeriesToBack(2);
    plot.moveSeriesToBack(3);

    <%--
    Не совсем понятно почему, но пользователи ИЕ без повторной перерисовки видят смещение оси нулей.
    Видимо всё-таки судьба у пользователей ие всегда ждать дольше всех
    --%>
    if (isIE())
     plot.redraw();

    <%-- Устанавливаем легенду --%>
    document.getElementById("balanceLegend").innerHTML += lineLabel;
    document.getElementById("negBarLegend").innerHTML += barLabel.toLowerCase();
    document.getElementById("posBarLegend").innerHTML += barLabel.toLowerCase();
});

function showOrHideSeries(sdata, control)
{
    showOrHideWaitDiv(true);
    setTimeout(function ()
    {
        var show = true;
        var display = "";
        var axes = plot.axes[plot.series[sdata.sid].yaxis];
        if (control.checked)
        {
            axes.borderWidth = BORDER_WIDTH;
            show = true;
        }
        else
        {
            axes.borderWidth = 0;
            show = false;
            display = "none";
        }
        plot.series[sdata.sid].show = show;
        plot.series[sdata.zeroLineId].show = show;

        axes.tickOptions.show = show;
        axes.labelOptions.show = show;

        plot.redraw();

        if (sdata == BAR_SERIES_DATA)
        {
            document.getElementById("negBarLegend").style.display = display;
            document.getElementById("posBarLegend").style.display = display;
        }
        else
            document.getElementById("balanceLegend").style.display = display;

        showOrHideWaitDiv(false);
    }, 100);

}

function graphCheckboxCheck ()
{
    var line = document.getElementById("lineGraph");
    var bar =  document.getElementById("barGraph");
    var graph = document.getElementById("graph");
    var noGraph = document.getElementById("noGraph");

    if (!(line.checked || bar.checked))
    {
        graph.style.display = "none";
        noGraph.style.display = "block";
        return false;
    }

    if (graph.style.display == "none")
    {
        graph.style.display = "block";
        noGraph.style.display = "none";
    }

    if (line.checked && bar.checked)
    {
        $("#graph .lineGraph, #graph .barGraph").hide();
        $("#graph .bothGraph").show();
    }
    else if (line.checked)
    {
        $("#graph .bothGraph, #graph .barGraph").hide();
        $("#graph .lineGraph").show();
    }
    else
    {
       $("#graph .bothGraph, #graph .lineGraph").hide();
       $("#graph .barGraph").show();
    }

    return true;
}

function showHideMovement(control)
{
    graphCheckboxCheck ();
    showOrHideSeries(BAR_SERIES_DATA, control);
}

function showHideBalance(control)
{
    graphCheckboxCheck ();
    showOrHideSeries(LINE_SERIES_DATA, control);
}

</script>
<br/>

<input type="checkbox" name="movement" onclick="showHideMovement(this)"
       checked="true"  id="barGraph"/> показать движение средств ${title} <br/>
<input type="checkbox" name="balance" onclick="showHideBalance(this)"
       checked="true" id="lineGraph"/> показать изменения баланса <br/>
<br/>
<div id="graph">

    <div class="title bothGraph">Графическая выписка</div>
    <div class="title lineGraph" style="display: none">График изменения баланса</div>
    <div class="title barGraph" style="display: none">Оборот средств ${title}</div>

    <div class="description bothGraph">${descriptionBoth}</div>
    <div class="description lineGraph" style="display: none">${descriptionLine}</div>
    <div class="description barGraph" style="display: none">${descriptionBar}</div>

    <div id="chart"></div>

    <div id="balanceLegend">
        <img src="${imagePath}/legendLine.png" alt="" />
    </div>

    <div id="posBarLegend">
        <img src="${imagePath}/legendPosBar.png" alt="" /> Положительный
    </div>

    <div id="negBarLegend">
        <img src="${imagePath}/legendNegBar.png" alt="" /> Отрицательный
    </div>

</div>

<div id="noGraph" style="display: none">
    <div class="emptyText greenBlock">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="greenBold"/>
            <tiles:put name="data">
                У Вас не выбран ни один вид графика. Для построения графической выписки выберите интересующие Вас графики
            </tiles:put>
        </tiles:insert>
    </div>
</div>