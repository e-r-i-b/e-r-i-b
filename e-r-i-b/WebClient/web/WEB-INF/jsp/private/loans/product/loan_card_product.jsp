<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/payments/loan_card_product" onsubmit="return setEmptyAction()">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="definition" value="loanClaim"/>
    <c:if test="${person == null}">
        <c:set var="definition" value="guestMain"/>
    </c:if>

    <tiles:insert definition="${definition}">
        <tiles:importAttribute/>
        <c:if test="${person != null}">
            <tiles:put name="title"                  value="Условия по кредитным картам"/>
            <tiles:put name="emptyMessage"           value="Для указанного Вами уровня дохода банк не выдает кредитных карт"/>
            <tiles:put name="dataSize"               value="${not empty form.data}"/>
            <tiles:put name="imageId"                value="cardCondition"/>
            <tiles:put name="backToIncome"           value="${not empty param.income}"/>
            <tiles:put name="loanCardClaimAvailable" value="${form.loanCardClaimAvailable}"/>
            <tiles:put name="hideLinkBackTo"         value="true"/>

            <tiles:put name="description">
                <c:set var="bankCardLoanLink" value="${phiz:getBankCardLoanLink()}"/>
                Для того чтобы оформить заявку на кредитную карту, ознакомьтесь с условиями предоставления кредитных карт.
                <br/>
                Подробнее ознакомиться с условиями можно на сайте банка, щелкнув по ссылке <a
                    href="${bankCardLoanLink}" target="_blank">«Условия по кредитным картам»</a>.<br/>
                Затем выберите подходящую кредитную карту, отметьте ее в списке и нажмите на кнопку «Продолжить».
            </tiles:put>

            <tiles:put name="breadcrumbs">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                    <tiles:put name="action" value="/private/payments.do"/>
                </tiles:insert>
                <c:if test="${not empty param.income}">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name" value="Выбор дохода"/>
                        <tiles:put name="action" value="/private/payments/income_level.do"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="Условия по кредитным картам"/>
                    <tiles:put name="last" value="true"/>
                </tiles:insert>
            </tiles:put>
        </c:if>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function changeDateSelector(el)
                {
                    document.getElementById("changeDate").value = el;
                }

                function checkCardProduct(name)
                {
                    var list = document.getElementsByName(name);
                    if (list != undefined && list.length > 0)
                    {
                        var cnt = getSelectedQnt(name);
                        if (cnt == 0)
                        {
                            list[0].checked = true;
                            list[0].onclick();
                        }
                    }
                }

                doOnLoad(function ()
                {
                    //хотябы один продукт должен быть выбран
                    checkCardProduct("loanId");
                });
            </script>
            <c:set var="name" value=""/>
            <c:set var="terms" value=""/>
            <c:set var="include" value="0"/>
            <div class="loanTitle">
            <input type="hidden" name="changeDate" id="changeDate" value="">
            <c:forEach items="${form.data}" var="form_data">
                <c:set var="product" value="${form_data}"/>

                <c:if test="${name != product.productId}">
                    <c:if test="${name ne ''}">
                        </table></div>
                        <c:if test="${include == 1}">
                            <div class="loanTitle include">* - не включительно</div>
                            <c:set var="include" value="0"/>
                        </c:if>
                        <c:if test="${not empty terms}">
                            <div class="loanTitle">
                                <a onclick="showOrHideItems('terms_${product.id}')">Дополнительные условия</a>

                                <div id="terms_${product.id}" class="loanTitle hiddenDiv word-wrap">
                                        ${terms}
                                </div>
                            </div>
                        </c:if>
                        <br/><br/>
                    </c:if>
                    <c:set var="terms" value="${product.additionalTerms}"/>

                    <div class="loanTitle kind"><c:out value="${product.name}"/></div>
                    <c:if test="${product.allowGracePeriod == true}">
                        <div class="loanTitle">
                            <span>льготный период до:&nbsp;</span>
                            <span class="bold">${product.gracePeriodDuration} календарных дней</span>
                        </div>
                        <div>
                            <span>процентная ставка в льготный период:&nbsp;</span>
                            <span class="bold">${product.gracePeriodInterestRate}%</span>
                        </div>
                    </c:if>
                    <div class="noPadding">
                    <table class="tblInf shadow" width="100%">
                    <tr>
                        <th></th>
                        <th>Доступный кредитный лимит</th>
                        <th>Процентная ставка</th>
                        <th>Годовое обслуживание<br/>первый/послед.годы</th>
                    </tr>

                    <c:set var="name" value="${product.productId}"/>
                </c:if>
                <tr class="ListLine0">
                    <td><input type="radio" name="loanId" value="${product.id}" onclick="changeDateSelector(${product.changeDate.timeInMillis});"/></td>
                    <td>
                        От <c:out value="${phiz:formatAmount(product.minCreditLimit)}"/>
                        до <c:out value="${phiz:formatAmount(product.maxCreditLimit)}"/>
                        <c:if test="${product.maxCreditLimitInclude == false}">
                            *
                            <c:set var="include" value="1"/>
                        </c:if>
                    </td>
                    <td>${product.interestRate}%</td>
                    <td>
                            ${phiz:formatAmount(product.firstYearPayment)} /
                            ${phiz:formatAmount(product.nextYearPayment)}
                    </td>
                </tr>

            </c:forEach>
            </table></div>
            <c:if test="${include == 1}">
                <div class="loanTitle include">* - не включительно</div>
            </c:if>
            <c:if test="${not empty terms}">
                <div class="loanTitle">
                    <a onclick="showOrHideItems('terms_')">Дополнительные условия</a>

                    <div id="terms_" class="loanTitle hiddenDiv word-wrap">
                            ${terms}
                    </div>
                </div>
            </c:if>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>