<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="ru-RU"/>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script type="text/javascript" src="${globalUrl}/scripts/hint.js"></script>

<html:form action="/showFinancePlan" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="executionDate" value="${form.executionDate}"/>

    <c:set var="executionYear"><fmt:formatDate value="${executionDate.time}" pattern="yyyy"/></c:set>
    <c:set var="executionMonth"><fmt:formatDate value="${executionDate.time}" pattern="MM"/></c:set>
    <fmt:parseNumber value="${(executionMonth-1)/3 + 1}" var="executionQuarter" integerOnly="true"/>
    <c:set var="startCapital" value="${form.startCapital}"/>
    <c:set var="quarterlyInvest" value="${form.quarterlyInvest}"/>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="page.title"/>
        </tiles:put>
        <tiles:put name="description">
            <bean:message key="show.financeplan.header.message" bundle="pfpBundle"/>
        </tiles:put>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message bundle="pfpBundle" key="index.breadcrumbsLink"/></tiles:put>
                <c:choose>
                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                        <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="action" value="/private/graphics/finance"/>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">Финансовое планирование</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data">
            <div class="pfpBlocks">
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.targets" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.riskProfile" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <span class="largeMessageInLifeLine"><bean:message key="label.line.financePlan" bundle="pfpBundle"/></span>
                        </tiles:put>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.plan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>

                <div id="portfolioInfo">
                    <div class="pfpBlock">
                        <fieldset class="dashedBorder fullWidthPFP">
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title"><bean:message key="label.show.financePlan.START_CAPITAL.amount" bundle="pfpBundle"/></tiles:put>
                                <tiles:put name="data">
                                    <span class="bold">
                                        ${phiz:formatAmount(startCapital.productSum)}
                                    </span>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title"><bean:message key="label.show.financePlan.QUARTERLY_INVEST.amount" bundle="pfpBundle"/></tiles:put>
                                <tiles:put name="data">
                                    <span class="bold">
                                        ${phiz:formatAmount(quarterlyInvest.productSum)}
                                    </span>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title"><bean:message key="label.show.financePlan.START_CAPITAL.income" bundle="pfpBundle"/></tiles:put>
                                <tiles:put name="data">
                                    <span class="bold">
                                        <bean:write name="startCapital" property="income"/>
                                        <bean:message key="label.addProduct.income.help" bundle="pfpBundle"/>
                                    </span>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title"><bean:message key="label.show.financePlan.QUARTERLY_INVEST.income" bundle="pfpBundle"/></tiles:put>
                                <tiles:put name="data">
                                    <span class="bold">
                                        <bean:write name="quarterlyInvest" property="income"/>
                                        <bean:message key="label.addProduct.income.help" bundle="pfpBundle"/>
                                    </span>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                    </div>
                    <div class="pfpBlock">
                    <fieldset class="dashedBorder fullWidthPFP">
                        <legend><bean:message key="totalReturnPortfolios" bundle="pfpBundle"/></legend>
                        <div id="zoomInMessage" style="display:none;">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="orange"/>
                                <tiles:put name="data">
                                    <bean:message key="label.show.financePlan.zoom.in.message" bundle="pfpBundle"/>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                        <div class="planPlotRangeScroll">
                            <c:set var="currentYear" value="${phiz:currentYear()}"/>
                            <tiles:insert definition="rangeScrollTemplate" flush="false">
                                <tiles:put name="id" value="PlotScale"/>
                                <tiles:put name="minValue" value="${currentYear}"/>
                                <tiles:put name="maxValue" value="${currentYear + pfptags:getDefaultPeriodValue()}"/>
                                <tiles:put name="step" value="2"/>
                                <tiles:put name="startFieldName" value="startFieldName"/>
                                <tiles:put name="endFieldName" value="endFieldName"/>
                                <tiles:put name="callback" value="financePlan.redrawChart()"/>
                            </tiles:insert>
                        </div>
                        <div id="planPlot"></div>

                    </fieldset>
                    </div>
                    <div id="myTargets" class="pfpBlock">
                        <c:set var="personTargetList" value="${form.personTargetList}" scope="request"/>
                        <c:set var="maxTargetCount" value="${form.maxTargetCount}" scope="request"/>
                        <c:set var="showThermometer" value="true" scope="request"/>
                        <%@ include file="/WEB-INF/jsp/common/pfp/personTarget/clientTargetList.jsp"%>
                    </div>

                    <%@ include file="/WEB-INF/jsp/common/pfp/financePlanRecomendation.jsp" %>

                    <c:if test="${not form.elderPerson}">
                        <div id="myLoans" class="pfpBlock">
                            <c:set var="personLoanList" value="${form.personLoanList}"/>
                            <%@ include file="/WEB-INF/jsp/common/pfp/personLoan/personLoanList.jsp"%>
                        </div>
                    </c:if>
                </div>

                <div class="pfpBlock">
                    <fieldset>
                        <div id="calculateDescriptionFile">
                            <html:link href="${globalImagePath}/pfp/file/CalculationDescription.pdf" target="blank">
                                <div class="floatLeft"><img src="${globalImagePath}/pfp/PDF_icon.png" border="0"></div>
                                <div><bean:message bundle="pfpBundle" key="label.show.financePlan.calculateDescription"/></div>
                            </html:link>
                        </div>

                        <div id="calculateFile">
                            <html:link onclick="new CommandButton('button.unloadCalculation').click('', false); return false;" href="#">
                                <div class="floatLeft"><img src="${globalImagePath}/pfp/PDF_icon.png" border="0"></div>
                                <div><bean:message bundle="pfpBundle" key="label.show.financePlan.calculatePortfolioAmount"/></div>
                            </html:link>
                        </div>
                        <div class="clear"></div>
                    </fieldset>
                </div>

                    <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="existNegativeAmount"/>
                            <tiles:put name="styleClass" value="confirmWidow"/>
                            <tiles:put name="data">
                                <jsp:include page="/WEB-INF/jsp/common/pfp/windows/existNegativeAmountWindow.jsp"/>
                            </tiles:put>
                    </tiles:insert>

                <div class="pfpButtonsBlock">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.next"/>
                        <tiles:put name="commandHelpKey" value="button.next.help"/>
                        <tiles:put name="bundle" value="pfpBundle"/>
                        <tiles:put name="validationFunction" value="checkNegativeAmount();"/>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.back2"/>
                    <tiles:put name="commandHelpKey" value="button.back2.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

</html:form>

<script type="text/javascript">
    var quarterInYear = 4;
    <c:set var="executionDate"><fmt:formatDate value="${executionDate.time}" pattern="MM/dd/yyyy"/></c:set>
    var executionDate = new Date("${executionDate}");
    var executionDateStartQuarter = getStartCurrentQuarterDate(executionDate);
    var minNegativeAmount = -0.005;

    var jsPlanPlot;

    var financePlan = {
        QUARTERS_IN_YEAR: 4,
        planPlot: null,//график общей доходности портфелей
        loanList: {},//список кредитов клиента
        targetList: {},//список целей клиента
        totalAmountList: null,
        negativeBalanceList:[],
        financePlanCalculator: null,
        init: function()
        {
            var startCapitalQuarterIncome = ${startCapital.income}/quarterInYear/100;
            var quarterlyInvestQuarterIncome = ${quarterlyInvest.income}/quarterInYear/100;
            var quarterlyInvestAmount = ${quarterlyInvest.productSum.decimal};
            var startCapitlAmount = ${startCapital.productSum.decimal};
            var executionDate = new Date(executionDateStartQuarter);
            this.financePlanCalculator = new FinancePlanCalculator(startCapitlAmount,quarterlyInvestAmount,startCapitalQuarterIncome,quarterlyInvestQuarterIncome,executionDate);

            <c:forEach var="personTarget" items="${personTargetList}">
                var target = {
                    id: ${personTarget.id},
                    name: "${personTarget.name}",
                    nameComment: "${personTarget.nameComment}",
                    plannedDate: new Date("<fmt:formatDate value="${personTarget.plannedDate.time}" pattern="MM/dd/yyyy"/>"),
                    amount: ${personTarget.amount.decimal},
                    veryLast: ${personTarget.veryLast}
                };
                this.targetList[target.id] = target;
            </c:forEach>

            <c:forEach var="personLoan" items="${personLoanList}">
                var loan = {
                    id: ${personLoan.id},
                    name: "${personLoan.name}",
                    comment: "${personLoan.comment}",
                    startDate: new Date("<fmt:formatDate value="${personLoan.startDate.time}" pattern="MM/dd/yyyy"/>"),
                    endDate: new Date("<fmt:formatDate value="${personLoan.endDate.time}" pattern="MM/dd/yyyy"/>"),
                    amount: ${personLoan.amount.decimal},
                    quarterPayment: ${pfptags:calcQuarterPayment(personLoan)},
                    rate: ${personLoan.rate}
                };
                this.loanList[loan.id] = loan;
            </c:forEach>
            this.updateTotalAmount();
        },
        updateFinancePlanByTargets : function(targets)
        {
            financePlan.targetList = targets;
            financePlan.updateTotalAmount();
            financePlan.redrawChart();
            financePlan.updateTargetThermometer();
            financePlanRecomendations.calcRecomendations();
        },
        updateFinancePlanRemoveTarget : function(targetId)
        {
            delete financePlan.targetList[targetId];
            financePlan.updateTotalAmount();
            financePlan.redrawChart();
            financePlan.updateTargetThermometer();
            financePlanRecomendations.calcRecomendations();
        },
        updateFinancePlanByLoan : function(personLoan)
        {
            financePlan.loanList[personLoan.id] = personLoan;
            financePlan.updateTotalAmount();
            financePlan.redrawChart();
            financePlan.updateTargetThermometer();
            financePlanRecomendations.calcRecomendations();
        },
        updateFinancePlanRemoveLoan : function(personLoanId)
        {
            delete financePlan.loanList[personLoanId];
            financePlan.updateTotalAmount();
            financePlan.redrawChart();
            financePlan.updateTargetThermometer();
            financePlanRecomendations.calcRecomendations();
        },
        updateScalePanel: function(quarterCount)
        {
            if(quarterCount <= 4 * this.QUARTERS_IN_YEAR)
            {
                $('.planPlotRangeScroll').hide();
            }
            else
            {
                $('.planPlotRangeScroll').show();
            }

            rangeScrollManagerPlotScale.update(this.financePlanCalculator.getYearCount(), this.financePlanCalculator.getYearStep());
        },
        updateTotalAmount: function()
        {
            this.financePlanCalculator.setTargetList(this.targetList);
            this.financePlanCalculator.setLoanList(this.loanList);
            this.financePlanCalculator.calculate();

            this.negativeBalanceList = this.financePlanCalculator.getNegativeBalanceList();

            this.totalAmountList = this.financePlanCalculator.getTotalAmount();
            this.updateScalePanel(this.totalAmountList.length - 1);
        },
        updateTargetThermometer: function()
        {
            var targetListArray = [];
            for(var key in financePlan.targetList)
            {
                targetListArray.push(financePlan.targetList[key]);
            }
            targetListArray.sort((function(target1, target2){return target2.id - target1.id;}));
            var negativeBalanceList = $.extend(true,[],this.negativeBalanceList);

            var personTarget = null;
            var thermometer = null;
            var targetRatio = null;
            var color = null;
            var message = null;
            for(var i = 0; i < targetListArray.length; i++)
            {
                personTarget = targetListArray[i];
                targetRatio = 100;
                color = "green";
                message = "Данная цель будет достигнута в срок.";
                for(var j=0; j < negativeBalanceList.length; j++)
                {
                    var negativeBalance = negativeBalanceList[j];
                    if(negativeBalance.amount > 0 && negativeBalance.date.getTime() == personTarget.plannedDate.getTime())
                    {
                        negativeBalance.amount = negativeBalance.amount - personTarget.amount
                        targetRatio = - Math.ceil(negativeBalance.amount/personTarget.amount*100);
                        targetRatio = Math.max(0,targetRatio);
                        color = "orange";
                        message = "К планируемой дате Вы сможете накопить " + targetRatio + "% от стоимости цели.";
                    }
                }
                thermometer = thermometerManager.getThermometer(personTarget.id);
                if(!thermometer)
                {
                    thermometer = new Thermometer(personTarget.id, targetRatio, 100, false,'orange');
                    thermometerManager.addThermometer(thermometer);
                }
                thermometer.setValue(targetRatio);
                thermometer.setColor(color);
                thermometer.calculateWidths();
                thermometer.scale();
                thermometer.redraw();
                var mouseEnter = function(id,message)
                {
                    return function()
                    {
                        pfpTarget.showThermometerHint('hint'+id, id, message);
                    };
                }(thermometer.id,message);

                var mouseLeave = function(id)
                {
                    return function()
                    {
                        hintUtils.closeElementHint('hint'+id);
                    };
                }(thermometer.id);

                thermometer.addMouseenter(mouseEnter);
                thermometer.addMouseleave(mouseLeave);
            }
        },
        getChartData: function()
        {
            var fromYear = rangeScrollManagerPlotScale.getFromValue();
            var toYear = rangeScrollManagerPlotScale.getToValue();
            var quarterCount = this.financePlanCalculator.getQuartersInPeriod(fromYear, toYear);
            var chartData = [];
            var isShowNegativeBalance = false;
            for(var i = 0; i < this.totalAmountList.length; i++)
            {
                var totalAmount = this.totalAmountList[i];
                if(this.validatePlanDate(totalAmount.date, fromYear, toYear, quarterCount))
                {
                    var color = "#82cc3b";
                    if(totalAmount.amount <= minNegativeAmount)
                    {
                        color = "#ffa200";
                        isShowNegativeBalance = true;
                    }
                    chartData.push(
                        {
                            axisLabel: this.getAxisLabel(totalAmount.date, fromYear, toYear),
                            summ: totalAmount.amount,
                            summText: FloatToString(totalAmount.amount,2,' '),
                            color: color
                        }
                    );
                }
            }
            if (financePlan.existNegativeAmount() && !isShowNegativeBalance)
                $('#zoomInMessage').show();
            else
                $('#zoomInMessage').hide();
            return chartData;
        },
        validatePlanDate: function(date, fromYear, toYear, quarterCount)
        {
            if(date.getFullYear() < fromYear || date.getFullYear() > toYear)
                return false;

            if(quarterCount <= 4 * this.QUARTERS_IN_YEAR)
                return true;

            if(quarterCount <= 8 * this.QUARTERS_IN_YEAR)
                return pfpMath.getQuarter(date) == 4;

            if(quarterCount <= 26 * this.QUARTERS_IN_YEAR)
                return pfpMath.getQuarter(date) == 4 && (date.getFullYear() - fromYear) % 2 == 0;

            return pfpMath.getQuarter(date) == 4 && (date.getFullYear() - fromYear) % 4 == 0;
        },
        existNegativeAmount: function()
        {
            return this.negativeBalanceList.length > 0;
        },
        drawChart: function()
        {
            chartData = this.getChartData();
            var paramsColumn = {
                categoryField:  "axisLabel",
                marginRight: 0,
                marginTop: 0,
                autoMarginOffset: 0,
                depth3D: 20,
                angle: 30,
                numberFormatter: {
                        precision:-1,
                        decimalSeparator:'.',
                        thousandsSeparator: amChartHelper.getNonBreakingSpace()
                },

                categoryAxisForDiagram: {
                    labelRotation: 0,
                    dashLength: 5,
                    gridPosition: "start"
                },

                valueAxisForDiagram: {
                    axisTypeForDiagram: 'valueAxis',
                    title: "Сумма, руб.",
                    dashLength: 5
                },

                balloonForDiagram:{
                    adjustBorderColor: true,
                    color: "#FFFFFF",
                    cornerRadius: 0,
                    fillColor: "#000000",
                    fillAlpha: 0.7,
                    borderThickness: 0,
                    pointerWidth: 10
                },

                graphForDiagram: [{
                    graphTypeForDiagram: 'AmGraph',
                    valueField: "summ",
                    colorField: "color",
                    balloonText: "[[category]] год:" + amChartHelper.getDelimiter() + " [[summText]] руб.",
                    type: "column",
                    lineAlpha: 0,
                    fillAlphas: 1
                }]
            };
            this.planPlot = new diagram(paramsColumn, 'planPlot', 'column', chartData);
        },
        redrawChart: function()
        {
            chartData = this.getChartData();
            this.planPlot.setChartData(chartData);
            this.planPlot.redraw();
        },
        getAxisLabel: function(date,fromYear,toYear)
        {
            var yearCout = toYear - fromYear;
            if(yearCout <= 4)
            {
                var month = date.getMonth();
                var quarter = parseInt(month/3) + 1;//получаем номер квартала от 1 до 4-х
                return quarter + ' кв. ' + amChartHelper.getDelimiter() + date.getFullYear();
            }
            else
            {
                return date.getFullYear();
            }
        }
    };

    $(document).ready(function()
    {
        <c:if test="${not form.elderPerson}">
        pfpLoan.addEditLoanCallbackFunction(financePlan.updateFinancePlanByLoan);
        pfpLoanList.addCallbackFunction(financePlan.updateFinancePlanRemoveLoan);
        </c:if>
        pfpTarget.addEditCallbackFunction(financePlan.updateFinancePlanByTargets);
        pfpTarget.addRemoveCallbackFunction(financePlan.updateFinancePlanRemoveTarget);
        financePlan.init();
        financePlan.drawChart();
        financePlan.updateTargetThermometer();

        financePlanRecomendations.init(${startCapital.productSum.decimal},${quarterlyInvest.productSum.decimal},${startCapital.income}/quarterInYear/100,${quarterlyInvest.income}/quarterInYear/100,new Date(executionDateStartQuarter));
        financePlanRecomendations.calcRecomendations();
    });

    function checkNegativeAmount()
    {
        if (financePlan.existNegativeAmount())
        {
            win.open('existNegativeAmount');
            return false;
        }
        return true;
    }

    <%-- Для выгрузки файла используем $(window).load. Иначе не прогружаются скрипты на странице (например для селектов) --%>
    $(window).load(function(){
    <c:if test="${form.fields.relocateToDownload != null && form.fields.relocateToDownload == 'true'}">
        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=PfpFinancePlan&contentType=pdf&clientFileName=${form.fields.clientFileName}"/>
        clientBeforeUnload.showTrigger=false;
        if(window.navigator.userAgent.indexOf("iPhone") != -1 || window.navigator.userAgent.indexOf("iPad") != -1)
            window.open('${downloadFileURL}');
        else
            goTo('${downloadFileURL}');
        clientBeforeUnload.showTrigger=false;
    </c:if>
    });
</script>