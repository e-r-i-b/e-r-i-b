<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/payments/loan_card_offer" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="loanClaim">
           <tiles:put name="title" value="Индивидуальные условия по кредитным картам"/>
           <tiles:put name="emptyMessage" value="Предложения по кредитным картам не найдены."/>
           <tiles:put name="dataSize" value="${not empty form.data}"/>
           <tiles:put name="imageId" value="cardCondition"/>
           <tiles:put name="loanCardClaimAvailable" value="${form.loanCardClaimAvailable}"/>
           <tiles:put name="description">
               <c:set var="bankCardLoanLink" value="${phiz:getBankCardLoanLink()}"/>
               <c:choose>
                   <c:when test="${form.changeType}">
                       Чтобы изменить тип кредитной карты,ознакомьтесь с условиями. Подробнее условия получения кредитной карты можно посмотреть
                       <a href="${bankCardLoanLink}" target="_blank">здесь</a>.<br/>
                       Затем отметьте подходящее Вам кредитное предложение банка и нажмите на кнопку «Продолжить».
                   </c:when>
                   <c:otherwise>
                       Вам осталось сделать всего пару простых шагов: ознакомьтесь с условиями, выберите карту и нажмите «Продолжить».
                   </c:otherwise>
               </c:choose>

           </tiles:put>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Предложения банка"/>
                <tiles:put name="action" value="/private/loan/loanoffer/show.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Индивидуальные условия по кредитным картам"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

           <tiles:put name="data">
               <c:set var="name" value=""/>
               <c:set var="terms" value=""/>
               <div class="loanTitle">
                   <input type="hidden" name="offerId" value=""/> <%--id предложения (нужен, чтобы после подтверждения заявки удалить предложение)--%>
                    <c:forEach items="${form.data}" var="conditionProductByOffer">
                        <c:if test="${name ne conditionProductByOffer.productId}">
                           <c:if test="${name ne ''}">
                               </table>
                               <br/><br/>
                           </c:if>
                           <c:set var="terms" value="${conditionProductByOffer.terms}"/>

                           <div class="loanTitle  kind"><c:out value="${conditionProductByOffer.name}"/></div>
                           <c:if test="${conditionProductByOffer.allowGracePeriod}">
                               <div class="loanTitle">
                                    <span>Льготный период кредитования</span>
                                    <span class="bold">&nbsp;до ${conditionProductByOffer.gracePeriodDuration} дней</span>
                                </div>
                                <div>
                                    <span>Процентная ставка в льготный период</span>
                                    <span class="bold">&nbsp;${conditionProductByOffer.gracePeriodInterestRate}%</span>
                                </div>

                               <c:if test="${not empty terms}">
                                   <a onclick="showOrHideItems('terms_${conditionProductByOffer.productId}')">
                                       <div class="text-green underline">
                                           Условия и преимущества
                                       </div>
                                   </a>
                                   <div id="terms_${conditionProductByOffer.productId}" class="loanTitle hiddenDiv word-wrap">
                                           ${terms}
                                   </div>
                               </c:if>
                           </c:if>

                           <table class="detailInfoTable titleTable">
                               <tr>
                                   <th></th>
                                   <th><br/>Кредитный лимит</th>
                                   <th><br/>Процентная ставка</th>
                                   <th>Годовое обслуживание<br/>(первый/последующие годы)</th>
                               </tr>
                           <c:set var="name" value="${conditionProductByOffer.productId}"/>

                       </c:if>
                           <tr>
                               <td>
                                   <input type="radio" name="loanId" value="${conditionProductByOffer.conditionId}" onchange="setOfferIdValue(${conditionProductByOffer.offerId});"/>
                               </td>
                               <td>до ${phiz:formatAmountWithoutCents(conditionProductByOffer.maxLimit)}</td>
                               <td><c:out value="${conditionProductByOffer.offerInterestRate}"/> %</td>
                               <td>
                                    ${phiz:formatAmountWithoutCents(conditionProductByOffer.firstYearPayment)} /
                                    ${phiz:formatAmountWithoutCents(conditionProductByOffer.nextYearPayment)}
                               </td>
                           </tr>
                    </c:forEach>
                    </table>
               </div>

               <script type="text/javascript">
                   function setOfferIdValue(offerId)
                   {
                       document.getElementsByName("offerId")[0].value = offerId;
                   }

                   function afterCheckIfOneItem()
                   {
                       if (document.getElementsByName("offerId")[0].value == '' && document.getElementsByName("loanId").length == 1)
                          document.getElementsByName("loanId")[0].onchange();
                   }

                   function checkLoanCardOffer(name) {
                        var list = document.getElementsByName(name);
                        if (list != undefined && list.length > 0) {
                            var cnt = getSelectedQnt(name);
                            if (cnt == 0) {
                                list[0].checked = true;
                                list[0].onchange();
                            }
                        }
                    }

                    doOnLoad(function() {
                        //хотябы одно условие должно быть выбрано
                        checkLoanCardOffer("loanId");
                    });
               </script>
           </tiles:put>

    </tiles:insert>
</html:form>