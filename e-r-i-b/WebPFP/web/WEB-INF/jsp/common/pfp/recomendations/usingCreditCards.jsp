<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<c:set var="recommendationSteps" value="${form.cardRecommendation.steps}"/>
<c:set var="summ" value="${phiz:formatAmount(form.outcomeMoney)}"/>
<c:set var="currDate" value="${phiz:currentDate()}"/>
<c:set var="outcomeText"><bean:message key="usingCreditCardsGraph.outcome.text" bundle="pfpBundle"/></c:set>
<c:set var="outcomeHint"><bean:message key="usingCreditCardsGraph.outcome.hint" bundle="pfpBundle"/></c:set>
<c:set var="outcomeCardText"><bean:message key="usingCreditCardsGraph.outcomeCard.text" bundle="pfpBundle"/></c:set>
<c:set var="outcomeCardHint"><bean:message key="usingCreditCardsGraph.outcomeCard.hint" bundle="pfpBundle"/></c:set>
<c:set var="investmentText"><bean:message key="usingCreditCardsGraph.investment.text" bundle="pfpBundle"/></c:set>
<c:set var="investmentHint"><bean:message key="usingCreditCardsGraph.investment.hint" bundle="pfpBundle"/></c:set>
<c:set var="incomeText"><bean:message key="usingCreditCardsGraph.income.text" bundle="pfpBundle"/></c:set>
<c:set var="incomeHint"><bean:message key="usingCreditCardsGraph.income.hint" bundle="pfpBundle"/></c:set>
<c:set var="thanksText"><bean:message key="usingCreditCardsGraph.thanks.text" bundle="pfpBundle"/></c:set>
<c:set var="thanksHint"><bean:message key="usingCreditCardsGraph.thanks.hint" bundle="pfpBundle"/></c:set>

<div class="usingCreditCardsBlock">
    <div class="usingCreditCardsGraphBlock">
        <div class="usingCreditCardsGraphTitle"><bean:message key="usingCreditCardsGraph.title" bundle="pfpBundle"/></div>
        <div class="effectUseCreditCardsCosts">
            <div class="float"><bean:message key="label.month.costs" bundle="pfpBundle"/> ${outcomeSumm}</div>
            <tiles:insert definition="floatMessageShadow" flush="false">
                <tiles:put name="id" value="pfpEditLoanHint"/>
                <tiles:put name="text">
                    <bean:message key="label.card.outcome.hint" bundle="pfpBundle"/>
                </tiles:put>
                <tiles:put name="dataClass" value="dataHint"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
        <div class="usingCreditCardsGraph">
            <div class="aboveAxis">

                <div class="usingCreditCardsGraphColUp usingCreditCardsFirstMonth">
                    <c:set var="firstMonthName"><fmt:formatDate value="${currDate.time}" pattern="MMMM"/></c:set>
                    <div class="graphMonth">
                        <div class="paleGrayLeft"></div>
                        <div class="paleGrayCenter">
                            <span>${firstMonthName}</span>
                        </div>
                        <div class="paleGrayRight"></div>
                    </div>

                    <div class="clear"></div>

                    <div class="creditCardImg">
                        <img src="${globalPath}/pfp/creditCard.gif">
                    </div>
                    <div class="usingCreditCardsColDescr">
                        ${recommendationSteps[0].name}
                    </div>
                </div>
                <div class="headerUCCB">
                    <div class="topUsing">
                        <div class="arrowNextStep">
                            <img src="${globalPath}/arrowNextStep.png">
                        </div>
                        <div class="graphMonth">
                            <div class="paleGrayLeft"></div>
                            <div class="paleGrayCenter">
                                <fmt:formatDate value="${phiz:addToDate(currDate.time, 0, 1, 0)}" pattern="MMMM"/>
                            </div>
                            <div class="paleGrayRight"></div>
                        </div>
                        <div class="clear"></div>

                        <div class="usingCreditCardsColDescr">
                            ${recommendationSteps[1].name}
                        </div>
                    </div>
                    <div class="topUsing">
                        <div class="arrowNextStep">
                            <img src="${globalPath}/arrowNextStep.png">
                        </div>
                        <div class="graphMonth">
                            <div class="paleGrayLeft"></div>
                            <div class="paleGrayCenter">
                                <fmt:formatDate value="${phiz:addToDate(currDate.time, 0, 2, 0)}" pattern="MMMM"/>
                            </div>
                            <div class="paleGrayRight"></div>
                        </div>
                        <div class="clear"></div>

                        <div class="usingCreditCardsColDescr">
                            ${recommendationSteps[2].name}
                        </div>
                    </div>
                    <div class="topUsing">
                        <div class="arrowNextStep">
                            <img src="${globalPath}/arrowNextStep.png">
                        </div>
                        <div class="graphMonth">
                            <div class="paleGrayLeft"></div>
                            <div class="paleGrayCenter">
                                <fmt:formatDate value="${phiz:addToDate(currDate.time, 0, 3, 0)}" pattern="MMMM"/>
                            </div>
                            <div class="paleGrayRight"></div>
                        </div>
                        <div class="clear"></div>

                        <div class="usingCreditCardsColDescr">
                            ${recommendationSteps[3].name}
                        </div>
                    </div>
                </div>
                <div class="firstUsingCreditBlock">
                <div class="usingCreditCardsGraphColUp usingCreditCardsMonth2">
                    <div class="purpleBox">
                        <div class="bold">${summ}</div>
                        <div>${investmentText}</div>
                    </div>
                </div>

                <div class="usingCreditCardsGraphColUp usingCreditCardsMonth3">
                    <div class="greenBox">
                        <div>${thanksText}</div>
                    </div>
                    <div class="blueBox">
                        <div>${incomeText}</div>
                    </div>
                    <div class="purpleBox">
                        <div class="bold">${summ}</div>
                        <div>${investmentText}</div>
                    </div>
                </div>

                <div class="usingCreditCardsGraphColUp usingCreditCardsMonth4">
                    <div class="greenBox">
                        <div>${thanksText}</div>
                    </div>
                    <div class="blueBox">
                        <div>${incomeText}</div>
                    </div>
                    <div class="purpleBox">
                        <div class="bold">${summ}</div>
                        <div>${investmentText}</div>
                    </div>
                </div>
                 </div>
                <div class="clear"></div>
            </div>

            <div class="usingCreditCardsGraphAxis">
                <div class="graphArrowRight"></div>
            </div>
            
            <div class="belowAxis">
                <div class="usingCreditCardsGraphColUnder usingCreditCardsFirstMonth">
                    <div class="grayBox">
                        <div class="bold">-${summ}</div>
                        <div>${outcomeText}</div>
                    </div>
                </div>

                <div class="usingCreditCardsGraphColUnder usingCreditCardsMonth2">
                    <div class="orangeBox">
                        <div class="bold">-${summ}</div>
                        <div>${outcomeCardText}</div>
                    </div>
                </div>

                <div class="usingCreditCardsGraphColUnder usingCreditCardsMonth3">
                    <div class="orangeBox">
                        <div class="bold">-${summ}</div>
                        <div>${outcomeCardText}</div>
                    </div>
                </div>

                <div class="usingCreditCardsGraphColUnder usingCreditCardsMonth4">
                    <div class="orangeBox">
                        <div class="bold">-${summ}</div>
                        <div>${outcomeCardText}</div>
                    </div>
                </div>

                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="clear"></div>

    <div class="usingCreditCardsDescrBlock">
        <c:forEach items="${recommendationSteps}" var="recommendation" varStatus="i">
            <div class="usingCreditCardsDescr">
                <div class="graphMonth">
                    <div class="brightGreenLeft"></div>
                    <div class="brightGreenCenter">
                        <span>
                            <c:choose>
                                <c:when test="${i.index == 0}">
                                    <fmt:formatDate value="${currDate.time}" pattern="MMMM"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatDate value="${phiz:addToDate(currDate.time, 0, i.index, 0)}" pattern="MMMM"/>
                                </c:otherwise>
                            </c:choose>
                            <fmt:formatDate value="${stepDate}" pattern="MMMM"/>
                        </span>
                    </div>
                    <div class="brightGreenRight"></div>
                </div>


                <div class="usingCreditCardsDescrTitle">
                    <c:out value="${recommendation.name}"/>
                </div>
                <div class="clear"></div>

                <div class="usingCreditCardsDescrText">
                    ${phiz:processBBCode(recommendation.description)}
                </div>
            </div>
            <c:if test="${fn:length(recommendationSteps)-1 != i.index}">
                <div class="productDivider"></div>
            </c:if>
        </c:forEach>
    </div>
</div>

<link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/jquery.jqplot.css"/>
<script type="text/javascript" src="${globalUrl}/scripts/graphics.js"></script>
<script type="text/javascript">

    function bindEnterBox(boxClass, text)
    {
        $('.' + boxClass).bind('mouseenter',function(event){window.graphics.showInfoDiv(text, event);});
    }

    function bindLeaveBox(boxClass)
    {
        $('.' + boxClass).bind('mouseleave',function(event){window.graphics.closeInfoDiv();});
    }

    $(document).ready(function(){
        bindEnterBox('grayBox', '${outcomeHint} <span class="textToLowercase">${firstMonthName}</span>: ${summ}');
        bindEnterBox('orangeBox', '${outcomeCardHint} ${summ}');
        bindEnterBox('purpleBox', '${investmentHint}');
        bindEnterBox('blueBox', '${incomeHint}');
        bindEnterBox('greenBox', '${thanksHint}');

        bindLeaveBox('grayBox');
        bindLeaveBox('orangeBox');
        bindLeaveBox('purpleBox');
        bindLeaveBox('blueBox');
        bindLeaveBox('greenBox');
    });
</script>

