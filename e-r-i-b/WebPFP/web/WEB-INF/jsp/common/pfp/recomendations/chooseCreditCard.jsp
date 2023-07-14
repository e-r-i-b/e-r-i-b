<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:set var="outcomeSumm" value="${phiz:formatAmount(form.outcomeMoney)}"/>
<c:set var="outcome" value="${form.outcomeMoney.decimal}"/>
<c:set var="recommendation"  value="${form.cardRecommendation}"/>
<c:set var="cards" value="${recommendation.cards}"/>
<c:if test="${not empty recommendation && not empty cards}">
    <script type="text/javascript">
         var cards = [];
         var graphParams=[];
         var datas=[];

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

        function getBaloonText(program)
        {
            if(program == 'mts')
                return "Накоплено: [[value]] баллов";
            if(program == 'beneficent')
                return "Переведено [[value]] руб.";
            if(program == 'aeroflot')
                return "[[value]] миль.";
            return "";
        }

        function rgbToHex(r, g, b) {
            var hexR = Number(r).toString(16);
            var hexG = Number(g).toString(16);
            var hexB = Number(b).toString(16);
            hexR = hexR.length == 1 ? '0'+hexR : hexR;
            hexG = hexG.length == 1 ? '0'+hexG : hexG;
            hexB = hexB.length == 1 ? '0'+hexB : hexB;
            return "#" + hexR + hexG + hexB;
        }

        function getHexColor(string)
        {
            var a = string.match(/rgb\((\d{1,3}),(\d{1,3}),(\d{1,3})\)/);
            return rgbToHex(a[1], a[2], a[3]);
        }


        <c:set var="cardCounter" value="0"/>
        <c:forEach var="card" items="${cards}">
            <c:if test="${empty selectedCardNumber}">
                <c:set var="selectedCardNumber" value="${card.id == form.fields.cardId ? cardCounter : null}"/>
                <c:if test="${empty selectedCardNumber}">
                    <c:set var="selectedCardNumber" value="${card.showAsDefault ? cardCounter : null}"/>
                    <c:set var="existDefaultCard" value="${card.id}"/>
                </c:if>
            </c:if>
            <c:set var="imageData" value="${phiz:getImageById(card.programmIconId)}"/>
            cards[${cardCounter}] = new pfpCreditCard('${phiz:getAddressImage(imageData, pageContext)}', '${card.clause}', "${phiz:processBBCode(card.recommendation)}",
                    '${card.programmType}', '${card.description}', 'creditCardInformation');
            <c:if test="${card.programmType != 'empty'}">
                graphParams[${cardCounter}] = {
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
                        <c:choose>
                              <c:when test="${card.diagramParameters.useNet=='true'}">
                                 gridAlpha: 0.1,
                              </c:when>
                              <c:otherwise>
                                 gridAlpha: 0,
                              </c:otherwise>
                        </c:choose>
                        axisAlpha: 0,
                        gridPosition: "start"
                    },

                    valueAxisForDiagram: {
                        axisTypeForDiagram: 'valueAxis',
                        stackType: 'regular',
                        axisAlpha: 0
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
                            title:'${card.name}',
                            valueField: 'account',
                            colorField: 'color',
                            type: 'column',
                            lineAlpha: 0,
                            <c:choose>
                                <c:when test="${card.diagramParameters.useImage=='true'}">
                                    fillAlphas: 0,
                                    customBulletField: 'image',
                                    bulletSize: 64,
                                </c:when>
                                <c:otherwise>
                                    fillAlphas: 1,
                                </c:otherwise>
                            </c:choose>
                            balloonText: getBaloonText('${card.programmType}')
                        }]
                };

                var chartData = [];
                for(var i = 1; i < 6; i++)
                {
                    var type = {
                        type    : '${card.programmType}',
                        bonus   : ${card.bonus},
                        devider : ${(empty card.inputs) ? 100 : card.inputs}
                    };
                    var result = pfpMath.calculateIncomeForCreditCardByType(${outcome}, i, type);
                    var sum = FloatToString(result.income);

                    chartData[i-1] ={
                        year: i + getYear(i),
                        account:  sum,
                        <c:choose>
                            <c:when test="${card.diagramParameters.useImage=='true'}">
                                <c:set var="imageData" value="${phiz:getImageById(card.diagramParameters.imageId)}"/>
                                image: '${phiz:getAddressImage(imageData, pageContext)}'
                            </c:when>
                            <c:otherwise>
                                color: getHexColor('${card.diagramParameters.color}')
                            </c:otherwise>
                        </c:choose>
                    };
                }
                datas[${cardCounter}] = chartData;
            </c:if>
            <c:set var="cardCounter" value="${cardCounter + 1}"/>
        </c:forEach>

        function chooseCreditCard(choose)
        {
            $('[name=fields(chooseCreditCard)]').val(choose);
            if(choose)
            {
                $('#chooseCreditCardBlock').show();
                $('.noUseCreditCard').show();
                $('.useCreditCard').hide();
            }
            else
            {
                 $('#chooseCreditCardBlock').hide();
                 $('#choosenCardId').val("");
                 $('.noUseCreditCard').hide();
                 $('.useCreditCard').show()
            }
        }

        function chooseCard(id, cardId)
        {
            cards[id].createHtml(graphParams[id], datas[id]);
            $('#choosenCardId').val(cardId);
        }

        function resizeImg (element)
        {
            var widthImg = getImageSize(element);
            $(element).css("width", widthImg + 'px');
        }

        $(document).ready(function(){
            <c:if test="${not empty selectedCardNumber && (not empty form.fields.cardId || not empty existDefaultCard)}">
                chooseCard('${selectedCardNumber}', ${not empty form.fields.cardId ? form.fields.cardId : existDefaultCard});
            </c:if>

            <c:if test="${not empty cards}">
                // переопределяем ширину картинок для ИЕ6
                if (isIE(6))
                    $('.pfpCreditCardImg img').each(function(i, element){$(element).ready(function(){resizeImg(element)});});
            </c:if>

            chooseCreditCard($('[name=fields(chooseCreditCard)]').val() == "true");
        });

        doOnLoad(function ()
        {
            $('.choosePfpCreditCardBlock').click(function ()
            {
                 $('.clickedPfpCreditCard').removeClass('clickedPfpCreditCard');
                 $(this).addClass('clickedPfpCreditCard');
            });
        });
    </script>

    <div class="effectUseCreditCardsBlock">
        <div class="chooseCreditCardTitle">
            <div class="blockTitle">
                <bean:message key="label.choose.credit.card" bundle="pfpBundle"/>
            </div>

            <div class="chooseCard">
                <c:choose>
                    <c:when test="${not empty existDefaultCard}">
                        <html:hidden property="fields(chooseCreditCard)" value="true"/>
                    </c:when>
                    <c:otherwise>
                        <html:hidden property="fields(chooseCreditCard)"/>
                    </c:otherwise>
                </c:choose>
                <span class="noUseCreditCard" onclick="chooseCreditCard(false);">
                    <img src="${globalPath}/icon_delete.gif" />
                    <bean:message key="label.noUseCreditCard" bundle="pfpBundle"/>
                </span>
                <span class="useCreditCard" onclick="chooseCreditCard(true);" style="display: none"><bean:message key="label.useCreditCard" bundle="pfpBundle"/></span>
            </div>
            <div class="clear"></div>
        </div>

        <div id ="chooseCreditCardBlock" class="chooseCreditCardBlock">
            <html:hidden name="form" property="field(cardId)" value="${not empty form.fields.cardId ? form.fields.cardId : existDefaultCard}" styleId="choosenCardId"/>
            <c:set var="cardCounter" value="1"/>
            <c:forEach var="card" items="${cards}">
                <tiles:insert page="/WEB-INF/jsp/common/pfp/recomendations/creditCardTemplate.jsp" flush="false">
                    <tiles:put name="title" value="${card.name}"/>
                    <c:set var="imageData" value="${phiz:getImageById(card.cardIconId)}"/>
                    <tiles:put name="imgUrl" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                    <tiles:put name="click"  value="chooseCard('${cardCounter-1}', ${card.id});"/>
                    <c:if test="${card.id == (not empty form.fields.cardId ? form.fields.cardId : existDefaultCard)}">
                        <tiles:put name="isChoose" value="true"/>
                    </c:if>
                    <c:if test="${(cardCounter mod 4) eq 0}">
                        <tiles:put name="isLast" value="true"/>
                    </c:if>
                </tiles:insert>
                <c:set var="cardCounter" value="${cardCounter + 1}"/>
            </c:forEach>

            <div class="clear"></div>

            <div id="creditCardInformation">
                <div class="logoBlock">
                    <div class="logo"></div>
                    <div class="cardRecommendation">
                        <span class="recommendationTitle"></span>
                        <span class="recommendationDescription"></span>
                    </div>
                </div>
                <div class="clear"></div>

                <div id="creditCardsGraph"></div>
                <div class="information"></div>
            </div>

        </div>
    </div>
</c:if>