<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/credit/offert/accept" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="oneTimePassword" value="${form.oneTimePassword}"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
        <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    </c:if>

    <tiles:insert definition="creditOffert">
        <tiles:put name="title" value="Выдача кредита"/>
        <tiles:put name="emptyMessage" value="Офферт по кредитам не найдено"/>
        <tiles:put name="dataSize" value="${not empty form.offertErib or not empty form.offertOffice}"/>
        <tiles:put name="oneTimePassword" value="${oneTimePassword}"/>
        <c:if test="${not empty form.confirmableObject}">
            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
        </c:if>
        <tiles:put name="breadcrumbs"></tiles:put>

        <tiles:put name="data">
            <c:set value="${form.offertTemplate}" var="offertTemplate"/>
            <input type="hidden" name="offertTemplateId" value="${offertTemplate.id}"/>
            <c:set var="claimDrawDepartment" value="${form.claimDrawDepartment}"/>

            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/require/require.js"></script>
            <script type="text/javascript">
                $(document).ready(function ()
                {
                    $("#trueMainOfferTr")[0].click();


                    require(['${initParam.resourcesRealPath}/scripts/require/requirecfg.js'], function()
                    {
                        require(['marionette', 'topUpOffer'], function(Mn, TopUpOffer)
                        {
                            var loanOffer = new TopUpOffer.types.viewModelType
                            ({
                                <c:set var="loanOffer" value="${form.loanOffer}"/>

                                <c:if test="${not empty loanOffer}">
                                loanOffer :
                                {
                                    <c:if test="${not empty loanOffer.topUps}">
                                    topUps :
                                    [
                                        <c:forEach var="topUp" items="${loanOffer.topUps}">
                                        {
                                            agreementNumber : '${topUp.agreementNumber}',
                                            productName     : '${phiz:getLoanLink(topUp.agreementNumber).loan.loanType}',
                                            termStart       : '${phiz:formatDateWithStringMonth(topUp.startDate)}',
                                            idContract      : '${topUp.idContract}',
                                            amount          : '${phiz:formatDecimalToAmount(topUp.totalAmount, 2)} ${phiz:getCurrencySign(topUp.currency)}'
                                        },
                                        </c:forEach>
                                    ],
                                    </c:if>

                                    pdpText : '${phiz:escapeForJS(form.pdpOfferTemplate.text, false)}'
                                }
                                </c:if>
                            },
                            {
                                parse : true
                            });

                            var application = new (Mn.Application.extend
                            ({
                                onStart : function(attributes)
                                {
                                    (new TopUpOffer.types.viewType({model : attributes})).render();
                                }
                            }));

                            application.start(loanOffer);
                        });
                    });

                });

                function setOfferId(offerId)
                {
                    var offerIdElement = document.getElementById("offerId");
                    offerIdElement.value = offerId;
                }

                function loanConditions()
                {
                    var url = "${phiz:calculateActionURL(pageContext,'/private/credit/offert/conditions.do')}";
                    var offerIdElement = document.getElementById("offerId");
                    var params = "?appNum=${form.appNum}&offerId=" + offerIdElement.value;
                    openWindow(event, url + params, "loanConditionsWin", "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1024, height=700");
                }
                function setMainOffer(tr)
                {
                    $.each($("#offerTable").find('tr') , function()
                    {
                        $(this).removeClass('mainCreditOffer');
                    });

                    $(tr).addClass('mainCreditOffer');
                }

                function setAgrimentText(offerId)
                {
                    var offerAgrimentText = $("#agriment"+offerId)[0].value;
                    $("#accountOpenText").text(offerAgrimentText);
                }
            </script>
            <input type="hidden" id="offerId" name="offerId"/>
            <c:set var="paramName" value="offerId"/>
            <c:set var="name" value=""/>

            <div class="simpleSubTtl">Кредит одобрен. Для получения денежных средств подтвердите ваше согласие с индивидуальными условиями кредитования.</div>

            <div class="creditOffers">
                <div class="creditOffersTop">
                    <c:if test="${phiz:size(form.offertErib) > 1}">
                        <a class="showHideCreditOffers">Скрыть другие условия</a>
                    </c:if>
                    <div class="creditOffersType"><c:out value="${form.productName}"/></div>
                </div>
                <div class="creditOfferConditions">
                    <ul>
                        <li>Аннуитетный</li>
                        <li>Обеспечение не требуется</li>
                    </ul>
                </div>
                <div class="alternativeOffersStyle">
                    <table class="mainTblHead">
                        <tr>
                            <th class="offerSum align-right">Сумма кредита, руб.</th>
                            <th class="offerPeriodColTtl align-left">Срок кредита</th>
                            <th class="offerRateTtl align-left">Процентная ставка</th>
                            <th class="mounthPaySumTtl align-right">ежемесячный<br /> платёж, руб.</th>
                        </tr>
                    </table>
                    <div class="fixHeightTbl">
                        <c:if test="${phiz:size(form.offertErib) > 3}">
                            <div class="offerBubble">
                                <div class="offerBubbleTxt">Для вас одобрены дополнительные <br /> условия по кредиту</div>
                            </div>
                        </c:if>
                        <table class="alternativeOffers" id="offerTable">
                            <%-- Для основного/выбранного предложения к tr добавляем класс mainCreditOffer --%>
                        <c:choose>
                            <c:when test="${not empty form.offertErib}">
                                <c:forEach items="${form.offertErib}" var="offert">
                                    <tr onclick='setOfferId("${offert.id}");setMainOffer(this);setAgrimentText("${offert.id}");' id="${offert.priority}MainOfferTr">
                                        <td class="mainOfferTick">
                                            <input type="hidden" id="agriment${offert.id}" value="${phiz:getEribOfferAgreementText(offert)}"/>
                                            <div class="mainOfferTickImg"></div>
                                        </td>
                                        <td class="offerSum align-right"><c:out value="${offert.altAmount}"/></td>
                                        <td class="offerPeriodCol"><c:out value="${offert.altPeriod}"/><span class="offerPeriod">мес.</span> </td>
                                        <td class="offerRate"><c:out value="${offert.altInterestRate}%"/></td>
                                        <td class="mounthPaySum align-right"><c:out value="${offert.altAnnuityPayment}"/></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty form.offertOffice}">
                                <c:forEach items="${form.offertOffice}" var="offert">
                                    <tr  onclick='setOfferId("${offert.id}");setMainOffer(this);setAgrimentText("${offert.id}");' id="${offert.priority}MainOfferTr">
                                        <td class="mainOfferTick">
                                            <input type="hidden" id="agriment${offert.id}" value="${phiz:getOfficeOfferAgreementText(offert)}"/>
                                            <div class="mainOfferTickImg"></div>
                                        </td>
                                        <td class="offerSum align-right"><c:out value="${offert.altAmount}"/></td>
                                        <td class="offerPeriodCol"><c:out value="${offert.altPeriod}"/><span class="offerPeriod">мес.</span> </td>
                                        <td class="offerRate"><c:out value="${offert.altInterestRate}%"/></td>
                                        <td class="mounthPaySum align-right"><c:out value="${offert.altAnnuityPayment}"/></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                         </c:choose>
                        </table>
                    </div>
                </div>
            </div>

            <div class="clear"></div>

            <h4 class="formSubTtl">Дополнительная информация</h4>

            <div class="formRow">
                <div class="paymentLabel">
                    Счет для зачисления:
                </div>
                <div class="paymentValue">
                    <div class="rowMainInfo">
                        <c:out value="${phiz:getCutCardNumber(form.enrollAccount)}"/>
                    </div>

                    <div class="attentionMessage infMesOrange">
                        Для противодействия мошенничеству операции по <span class="noWrap">счёту/карте</span> в Сбербанк Онлайн, мобильных приложениях и мобильном банке могут быть ограничены.
                        Операции в отделениях, банкоматах, терминалах без ограничений.
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <div class="formRow">
                <div class="paymentLabel">
                    Отделение обслуживания кредита:
                </div>
                <div class="paymentValue">
                    <div class="rowRightData">
                        <a class="showDepOnMap">Показать на карте</a>
                    </div>
                    <div class="rowMainInfo leftData">
                        <c:out value="${claimDrawDepartment.address}"/>
                    </div>
                    <div class="clear"></div>
                    <div class="depDetail">
                        <c:out value="${claimDrawDepartment.fullName}"/>
                    </div>

                    <div class="contactDetails">
                        <div class="contactData">
                            Телефоны:<br />
                            <c:out value="${claimDrawDepartment.telephone}"/>
                        </div>

                        <div class="contactData">
                            Время работы:<br />
                            <c:out value="${phiz:getTime(claimDrawDepartment.weekOperationTimeBegin)} -"/>
                            <c:out value="${phiz:getTime(claimDrawDepartment.weekOperationTimeEnd)}"/>
                        </div>
                    </div>

                </div>
                <div class="clear"></div>
            </div>

            <div class="formRow">
                <div id="top-up-offer-layout"></div>
                <div class="clear"></div>
            </div>

            <c:choose>
                <c:when test="${oneTimePassword}">
                    <h4 class="formSubTtl addMargin">Подтвердите согласие с условиями кредитования</h4>

                    <div class="okb-dogovor conditionality fullSizeCondition">
                        <div class="okb-dogovor-yellow">
                            <div class="okb-dogovor-top">Открыть условия кредита в <a class="okb-dogovor-new-win" onclick="loanConditions()">новом окне</a></div>
                            <div class="okb-dogovor-text accountOpenText" id="accountOpenText">

                            </div>
                            <div id="agreeForAllRow">
                                <input type="checkbox" id="agreeForAll" name="agreeForAll" value="" class="agreeChbx"><label for="agreeForAll">Я согласен с условиями</label>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <h4 class="formSubTtl addMargin">Просмотреть и подтвердить договор с индивидуальными условиями кредита</h4>
                    <div class="confirmCondition">
                        Внимание: в тексте условия кредитования будут указаны Ваши персональные данные,
                        в целях предотвращения мошеннических действий введите пароль из SMS-сообщения для просмотра условий.
                        Убедитесь, что реквизиты выполняемой операции совпадают с текстом SMS-сообщения. Будьте осторожны, никому не сообщайте пароль.
                    </div>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>

    <script id="top-up-offer-layout-template" type="text/template">
        <div id="top-up-region"></div>
    </script>
    <script id="top-up-composite-template" type="text/template">
        <h4  class="formSubTtl">Погашение текущих кредитов</h4>
        <div id="top-up-pdp-offer" class="simpleSubTtl fullSizeCondition">
            {{= pdpText}}
        </div>
    </script>
</html:form>
