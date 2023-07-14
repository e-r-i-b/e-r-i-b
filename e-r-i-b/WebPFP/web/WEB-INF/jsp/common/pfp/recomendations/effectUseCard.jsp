<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:set var="recommendation"  value="${form.cardRecommendation}"/>
<c:set var="accountEfficacy" value="${recommendation.accountEfficacy}"/>
<c:set var="thanksEfficacy"  value="${recommendation.thanksEfficacy}"/>

<c:set var="accountPercentMin" value="${accountEfficacy.fromIncome}"/>
<c:set var="accountPercentMax" value="${accountEfficacy.toIncome}"/>
<c:choose>
    <c:when test="${not empty form.fields.accountIncome}">
        <c:set var="accountPercentCur" value="${form.fields.accountIncome}"/>
    </c:when>
    <c:otherwise>
        <c:set var="accountPercentCur" value="${accountEfficacy.defaultIncome}"/>
    </c:otherwise>
</c:choose>

<c:set var="thanksPercentMin" value="${thanksEfficacy.fromIncome}"/>
<c:set var="thanksPercentMax" value="${thanksEfficacy.toIncome}"/>
<c:choose>
    <c:when test="${not empty form.fields.thanksPercent}">
        <c:set var="thanksPercentCur" value="${form.fields.thanksPercent}"/>
    </c:when>
    <c:otherwise>
        <c:set var="thanksPercentCur" value="${thanksEfficacy.defaultIncome}"/>
    </c:otherwise>
</c:choose>

<c:if test="${not empty accountPercentCur and not empty thanksPercentCur}">

<script type="text/javascript">

    var columnDiagram = null;

    function getYear(i)
    {
        switch(i){
            case 1:
                return " год";
            case 2:
            case 3:
            case 4:
                return " года";
            case 5:
                return " лет";
        }
        return "";
    }

    function init(outcome, accountPercent, thanksPercent)
    {
        var chartData = [];
        for(var i = 1; i < 6; i++)
        {
            var result = pfpMath.calculateIncomeForCreditCard(outcome, accountPercent, thanksPercent, i);
            var sum = FloatToString(result.account.income);
            var tnx = FloatToString(result.thanks.income);
            chartData[i-1] ={
                year: i + getYear(i),
                account:  sum,
                thanks: tnx
            };
        }
        return chartData;
    }

    var paramsColumn = {
        categoryField:  "year",
        plotAreaBorderAlpha: 0,
        depth3D: 20,
        angle: 30,
        columnWidth: 0.4,
        numberFormatter: {
                precision:-1,
                decimalSeparator:'.',
                thousandsSeparator:' '
        },

        categoryAxisForDiagram: {
            gridColor: "#cbcbcb",
            gridAlpha: 1,
            axisAlpha: 1,
            axisColor: "#cbcbcb",
            gridPosition: "start"
        },

        valueAxisForDiagram: {
            axisTypeForDiagram: 'valueAxis',
            stackType: 'regular',
            gridColor: "#cbcbcb",
            gridAlpha: 1,
            dashLength: 5,
            axisColor: "#cbcbcb",
            axisAlpha: 1
        },

        graphForDiagram: [{
                graphTypeForDiagram: 'AmGraph',
                title:'<bean:message key="label.graph.income.account" bundle="pfpBundle"/>',
                valueField: 'account',
                type: 'column',
                lineAlpha: 0,
                fillAlphas: 1,
                lineColor: "#00bff3",
                balloonText: "Доход: [[value]] руб."
            },
            {
                graphTypeForDiagram: 'AmGraph',
                title:'<bean:message key="label.graph.income.thanks" bundle="pfpBundle"/>',
                valueField: 'thanks',
                type: 'column',
                lineAlpha: 0,
                fillAlphas: 1,
                lineColor: "#c1ee00",
                balloonText: "Спасибо: [[value]] баллов"
        }],
        
        legendForDiagram:{
            legendTypeForDiagram:'AmLegend', 
            borderAlpha: 0,
            valueWidth: 0,
            horizontalGap: 10,
            maxColumns: 1,
            switchable: false
        },

        balloonForDiagram:{
            adjustBorderColor: true,
            color: "#FFFFFF",
            cornerRadius: 0,
            fillColor: "#000000",
            fillAlpha: 0.7,
            borderThickness: 0,
            pointerWidth: 10
        }
    };

    $(document).ready(function(){
        var chartData = init(${outcome}, ${accountPercentCur}, ${thanksPercentCur});
        columnDiagram = new diagram(paramsColumn, 'effectUseCreditCardsGraph', 'column', chartData);

        var accountNotChecked = $('[name=field(account)]').is(':not(:checked)');
        var thanksNotChecked  = $('[name=field(thanks)]').is(':not(:checked)');
        if (accountNotChecked)
            $($("[name='field(account)']")).parents('.scrollBlock:first').find('#blocker').toggleClass("blocker");
        if (thanksNotChecked)
            $($("[name='field(thanks)']")).parents('.scrollBlock:first').find('#blocker').toggleClass("blocker");
        if (accountNotChecked && thanksNotChecked)
        {
            $('#graphBlock').hide();
        }
        eventManager.fire('updatePercent', {'accountvalue':${accountPercentCur}, 'thanksvalue':${thanksPercentCur}});
    });

    function changeGraph()
    {
        var accountChecked = $('[name=field(account)]').is(':checked');
        var thanksChecked  = $('[name=field(thanks)]').is(':checked');

        var accountPercent = accountChecked ? $('[name=field(accountIncome)]').val() : 0;
        var thanksPercent  = thanksChecked ? $('[name=field(thanksPercent)]').val() : 0;
        columnDiagram.updateChartData(init(${outcome}, accountPercent, thanksPercent));
        columnDiagram.redraw();
        eventManager.fire('updatePercent', {'accountvalue':accountPercent, 'thanksvalue':thanksPercent});
    }

    function onclickCheckbox(elem)
    {
        var accountChecked = $('[name=field(account)]').is(':checked');
        var thanksChecked  = $('[name=field(thanks)]').is(':checked');

        var accountPercent = accountChecked ? $('[name=field(accountIncome)]').val() : 0;
        var thanksPercent  = thanksChecked ? $('[name=field(thanksPercent)]').val() : 0;

        $(elem).parents('.scrollBlock:first').find('#blocker').toggleClass("blocker");

        if($('[name=field(account)]').is(':not(:checked)') && $('[name=field(thanks)]').is(':not(:checked)'))
        {
            $('#graphBlock').hide();
        }
        else if($('#effectUseCreditCardsGraph').is(':not(:visible)'))
        {
             $('#graphBlock').show();
        }
        columnDiagram.init(init(${outcome}, accountPercent, thanksPercent));
        eventManager.fire('updatePercent', {'accountvalue':$('[name=field(accountIncome)]').val(), 'thanksvalue':$('[name=field(thanksPercent)]').val()});
    }

</script>

<div class="effectUseCreditCardsBlock">
    <div class="effectUseCreditCardsScrollBlock">
        <div class="usingCreditCardsGraphTitle"><bean:message key="effect.use.credit.card.title" bundle="pfpBundle"/></div>
        <div class="effectUseCreditCardsCosts">
            <div class="float"><bean:message key="label.month.costs" bundle="pfpBundle"/> ${outcomeSumm}</div>
            <tiles:insert definition="floatMessage" flush="false">
                <tiles:put name="id" value="outcomeFloatMessage"/>
                <tiles:put name="text">
                    <bean:message key="label.card.outcome.hint" bundle="pfpBundle"/>
                </tiles:put>
            </tiles:insert>
            <div class="clear"></div>
        </div>
        <c:if test="${not empty accountPercentCur}">
            <div class="scrollBlock">
                <div class="effectUseCardAccountImage">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="color" value="gray"/>
                        <tiles:put name="data">
                            <img src="${globalPath}/deposits_type/account.jpg"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div class="effectUseCardContainer">
                    <div class="scrollTitle">
                        <html:checkbox property="field(account)" onclick="onclickCheckbox(this)"/><bean:message  key="label.account" bundle="pfpBundle"/>
                    </div>
                    <div class="effectUseCardScroll">
                        <div class="scrollDescription"><bean:message key="label.income" bundle="pfpBundle"/></div>
                        <div class="horizontalScroll">
                            <c:choose>
                                <c:when test="${not empty accountPercentMin}">
                                    <div id="blocker"></div>
                                    <tiles:insert definition="scrollTemplate2" flush="false">
                                        <tiles:put name="id" value="account"/>
                                        <tiles:put name="minValue" value="${accountPercentMin}"/>
                                        <tiles:put name="maxValue" value="${accountPercentMax}"/>
                                        <tiles:put name="unit" value="%"/>
                                        <tiles:put name="currValue" value="${accountPercentCur}"/>
                                        <tiles:put name="fieldName" value="field(accountIncome)"/>
                                        <tiles:put name="inputData"><div class="float" style="margin-top: 7px;">&nbsp;% годовых</div>
                                            <tiles:insert definition="floatMessage" flush="false">
                                                <tiles:put name="id" value="accountFloatMessage"/>
                                                <tiles:put name="text">
                                                    ${phiz:processBBCode(accountEfficacy.description)}
                                                </tiles:put>
                                            </tiles:insert>
                                        </tiles:put>
                                        <tiles:put name="step" value="0.1"/>
                                        <tiles:put name="round" value="1"/>
                                        <tiles:put name="callback" value="changeGraph();"/>
                                        <tiles:put name="dataPosition" value="right"/>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <div class="scrollInput">
                                           <div class="float">
                                               <html:hidden property="field(accountIncome)" value="${accountPercentCur}"/>
                                               <span>${accountPercentCur}</span>
                                           </div>

                                           <div class="float">% годовых</div>
                                           <tiles:insert definition="floatMessage" flush="false">
                                               <tiles:put name="id" value="accountFloatMessage"/>
                                               <tiles:put name="text">
                                                   ${phiz:processBBCode(accountEfficacy.description)}
                                               </tiles:put>
                                           </tiles:insert>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </c:if>
        <c:if test="${not empty thanksPercentCur}">
            <div class="scrollBlock">
                <div class="effectUseCardAccountImage">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="color" value="gray"/>
                        <tiles:put name="data">
                            <img src="${globalPath}/payment/loyaltyProgramIcon.jpg"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div class="effectUseCardContainer">
                    <div class="scrollTitle">
                        <html:checkbox property="field(thanks)" onclick="onclickCheckbox(this)"/><bean:message  key="label.bonus.program" bundle="pfpBundle"/>
                    </div>
                    <div class="effectUseCardScroll">
                        <div class="scrollDescription"><bean:message key="label.thanks" bundle="pfpBundle"/></div>
                        <div class="horizontalScroll">
                            <c:choose>
                                <c:when test="${not empty thanksPercentMin}">
                                    <div id="blocker"></div>
                                    <tiles:insert definition="scrollTemplate2" flush="false">
                                        <tiles:put name="id" value="thanks"/>
                                        <tiles:put name="minValue" value="${thanksPercentMin}"/>
                                        <tiles:put name="maxValue" value="${thanksPercentMax}"/>
                                        <tiles:put name="unit" value="%"/>
                                        <tiles:put name="currValue" value="${thanksPercentCur}"/>
                                        <tiles:put name="fieldName" value="field(thanksPercent)"/>
                                        <tiles:put name="inputData"><div class="float"  style="padding-top: 7px;">&nbsp;%</div>
                                            <tiles:insert definition="floatMessage" flush="false">
                                                <tiles:put name="id" value="thanksFloatMessage"/>
                                                <tiles:put name="text">
                                                    ${phiz:processBBCode(thanksEfficacy.description)}
                                                </tiles:put>
                                            </tiles:insert>
                                        </tiles:put>
                                        <tiles:put name="step" value="0.1"/>
                                        <tiles:put name="round" value="1"/>
                                        <tiles:put name="callback" value="changeGraph();"/>
                                        <tiles:put name="dataPosition" value="right"/>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <div class="scrollInput">
                                        <div class="float">
                                            <html:hidden property="field(thanksPercent)" value="${thanksPercentCur}"/>
                                            <span>${thanksPercentCur}</span>
                                        </div>

                                        <div class="float">%</div>
                                        <tiles:insert definition="floatMessage" flush="false">
                                            <tiles:put name="id" value="thanksFloatMessage"/>
                                            <tiles:put name="text">
                                                ${phiz:processBBCode(thanksEfficacy.description)}
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </c:if>
        <div id="graphBlock">
            <div class="effectUseCreditCardsGraphTitle"><bean:message key="graph.title" bundle="pfpBundle"/></div>
            <div id="effectUseCreditCardsGraph"></div>
        </div>
    </div>
    <div>
        ${phiz:processBBCode(recommendation.recommendation)}
    </div>
</div>
</c:if>

