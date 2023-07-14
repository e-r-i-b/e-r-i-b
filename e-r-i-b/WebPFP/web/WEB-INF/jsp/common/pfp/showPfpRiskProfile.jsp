<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/showRiskProfile" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="profile" value="${form.riskProfile}"/>
    <c:set var="personRiskProfile" value="${form.personRiskProfile}"/>
    <c:set var="incomeMoney" value="${form.incomeMoney}"/>
    <c:set var="outcomeMoney" value="${form.outcomeMoney}"/>
    <c:set var="freeMoney" value="${form.freeMoney}"/>
    <c:set var="isEmptyPortfolio" value="${form.fields['isEmptyPortfolio']=='true'}"/>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="page.title"/>
        </tiles:put>
        <tiles:put name="description">
            <bean:message bundle="pfpBundle" key="showRiskProfile.description"/>
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

            <script type="text/javascript">
                    var pieDiagram = null;
                    var editPieDiagram = null;

                    <c:if test="${isEmptyPortfolio}">
                        function openChangeRiskProfileWindow()
                        {
                            win.open('changeRiskProfile');
                        }
                    </c:if>

                    <%-- Цвета для круговой диаграммы: вклады, ОМС, ПИФы, страхование --%>
                    var account_COLOR = "#5cc837";
                    var IMA_COLOR = "#b600ff";
                    var fund_COLOR = "#ff9e01";
                    var insurance_COLOR = "#0ebff0";
                    var trustManaging_COLOR = "#ed1c24";


                    var chartData = [
                        <c:set var="i" value="0"/>

                        <c:forEach items="${personRiskProfile.productsWeights}" var="weight">

                               <c:if test="${i > 0}">
                                    ,
                               </c:if>
                               {
                                   title: brakeTitle("${weight.key.description}"),
                                   percent: ${weight.value},
                                   color: ${weight.key}_COLOR
                                }
                                <c:set var="i" value="${i+1}"/>
                            
                        </c:forEach>
                    ];
                    var chartDataForWindow = [];

                    function brakeTitle(title)
                    {
                        return title.replace(" ", getDelimiter());
                    }

                    var chart2Data =[
                        {
                            axisLabel: 'Ваши'+getDelimiter()+ ' ежемесячные'+getDelimiter()+ ' доходы',
                            summ:${incomeMoney.decimal},
                            color:"#ffd306"
                        },
                        {
                            axisLabel: 'Рекомендуемая'+getDelimiter()+ ' сумма на'+getDelimiter()+ ' сберегательном счете',
                            summ:${outcomeMoney.decimal},
                            color:"#84b2d4"
                        },
                        {
                            axisLabel: 'Ежемесячные'+getDelimiter()+ ' свободные'+getDelimiter()+ ' средства',
                            summ:${freeMoney.decimal},
                            color:"#f58728"
                        }
                    ];

                    function getDelimiter()
                    {
                         if(isIE(6))
                            return '<br>';
                         else
                            return '\n';
                    }

                    var paramsPie = {
                        titleField: "title",
                        valueField: "percent",
                        colorField: "color",
                        outlineColor: "#FFFFFF",
                        outlineAlpha: 0.8,
                        outlineThickness: 0,
                        depth3D: 10,
                        angle: 30,
                        startRadius: '0%',
                        percentFormatter: {
                            precision:0,
                            decimalSeparator:'.',
                            thousandsSeparator:' '
                        },

                        balloonForDiagram:{
                            adjustBorderColor: true,
                            color: "#FFFFFF",
                            cornerRadius: 0,
                            fillColor: "#000000",
                            fillAlpha: 0.7,
                            borderThickness: 0,
                            pointerWidth: 10,
                            verticalPadding: 10
                        }
                    };



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
                                thousandsSeparator:' '
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
                            balloonText: "[[category]]: [[value]]",
                            type: "column",
                            lineAlpha: 0,
                            fillAlphas: 1
                        }]
                    };

                    $(document).ready(function(){
                        pieDiagram = new diagram(paramsPie, 'pfpDiagram', 'circle', chartData);
                        var columnDiagram = new diagram(paramsColumn, 'chart', 'column', chart2Data);
                        <c:if test="${isEmptyPortfolio}">
                            $.extend(true, chartDataForWindow, chartData);
                            editPieDiagram = new diagram(paramsPie, 'editRiskProfile', 'circle', chartDataForWindow);
                        </c:if>
                    });

                    function closeChangeRiskProfileWindow()
                    {
                        $.extend(true, chartDataForWindow, chartData);
                        editPieDiagram.redraw();
                        multiplyScrollUtils.updateDragDealerByValues('1', chartData);
                        return true;
                    }

            </script>

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
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.financePlan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.plan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>

                <div class="pfpRiskResTitle">
                    <span class="riskProf">Ваш риск-профиль – </span><span class="greenResult"><c:out value="${profile.name}"/></span>
                </div>
                <div>
                    <c:out value="${profile.description}"/>
                </div>

                <div class="pfpDiagram riskPfpDiagram">
                    <div class="riskProf">Расчётное распределение средств</div>

                    <div class="bankRecommend">
                        <c:choose>
                            <c:when test="${form.needBeCareful}">
                                <p><bean:message bundle="pfpBundle" key="show.riskProfile.message.needBeCareful"/></p>
                            </c:when>
                            <c:otherwise>
                                <p><bean:message bundle="pfpBundle" key="show.riskProfile.message.other"/></p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div id="pfpDescrDiagram">
                        <div class="legengDescr">
                            Диаграмма показывает рекомендуемое распределение Ваших свободных денежных средств для получения стабильного дохода.
                        </div>
                    </div>

                    <div id="pfpDiagram" style="width: 660px; height: 340px;"></div>
                    <c:if test="${isEmptyPortfolio}">
                        <div class="buttonsArea" id="pfpRiskProfileButtonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="bundle" value="pfpBundle"/>
                                <tiles:put name="commandTextKey" value="button.change.riskProfile"/>
                                <tiles:put name="commandHelpKey" value="button.change.riskProfile.help"/>
                                <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                                <tiles:put name="onclick" value="openChangeRiskProfileWindow();"/>
                            </tiles:insert>
                        </div>

                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="changeRiskProfile"/>
                            <tiles:put name="data">
                                <jsp:include page="/WEB-INF/jsp/common/pfp/windows/changeRiskProfile.jsp"/>
                            </tiles:put>
                            <tiles:put name="closeCallback" value="function(){return closeChangeRiskProfileWindow();}"/>
                        </tiles:insert>
                    </c:if>

                    <div class="pfpNoticeDiagram">
                        <tiles:insert definition="roundBorder" flush="false">
                            <tiles:put name="color" value="whiteAndGray"/>
                            <tiles:put name="data">
                                <div class="graySubTitle">Обратите внимание!</div>
                                <p>При использовании инвестиционных продуктов стоимость Ваших вложений может как вырасти, так и стать ниже первоначальной суммы вложенных средств.</p>
                                <p>Доход, полученный в прошлом, не гарантирует доходность в будущем.</p>
                                <p>ОАО &laquo;Сбербанк Росcии&raquo; не несёт ответственности за самостоятельно принятые инвестиционные решения.</p>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="pfpBudget">
                    <div class="riskProf">Месячный бюджет</div>

                    <div id="budget">
                        <div id="chart" style="width: 660px; height: 340px;"></div>
                    </div>

                    <div id="budgetDescr">
                        <div class="graySubTitle"><bean:message bundle="pfpBundle" key="riskProfile.recommendation.title"/></div>
                        Рекомендуем Вам поддерживать на сберегательном счете сумму ${phiz:formatAmount(outcomeMoney)}, которая в совокупности с лимитом
                        по кредитной карте является надежным резервом на случай непредвиденных обстоятельств. <a class="imgHintBlock save-template-hint" onclick="openFAQ('/PhizIC/faq.do#g04')" title="Часто задаваемые вопросы"></a>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="pfpButtonsBlock">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.next"/>
                        <tiles:put name="commandHelpKey" value="button.next.help"/>
                        <tiles:put name="validationFunction" value=""/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="pfpBundle"/>
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