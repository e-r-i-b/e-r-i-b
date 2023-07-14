<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/payments/loan_product" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="loanClaim">
        <tiles:put name="title" value="Условия по кредитам ${phiz:getBankName()}"/>
        <tiles:put name="emptyMessage" value="Кредитные продукты не найдены"/>
        <tiles:put name="dataSize" value="${not empty form.data}"/>
        <tiles:put name="imageId" value="loanCondition"/>
        <tiles:put name="description">
            Для того чтобы оформить заявку на кредит, ознакомьтесь с условиями предоставления кредитов.<br/>
            Подробнее ознакомиться с условиями можно на сайте банка, щелкнув по ссылке <a
                href="${phiz:getBankLoanLink()}" target="_blank" class="orangeText"><span>«Условия по кредитам»</span></a>.<br/>
            Затем выберите подходящий Вам кредит, отметьте его в списке и нажмите на кнопку «Продолжить».
        </tiles:put>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                <tiles:put name="action" value="/private/payments.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Условия по кредитам"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data">
            <script type="text/javascript">
                function changeDateSelector(el)
                {
                    document.getElementById("changeDate").value = el;
                }
            </script>
            <c:set var="kind"/>
            <c:set var="minDuration" value="от 1 мес."/>
            <c:set var="maxDuration" value="до 999 мес."/>
            <c:forEach items="${form.data}" var="product">
                <div class="loanTitle">
                    <c:if test="${kind ne product.loanKind.name}">
                        <c:set var="kind" value="${product.loanKind.name}"/>
                        <div class="loanTitle kind"><c:out value="${kind}"/></div>
                    </c:if>
                    <div class="loanTitle credit">
                        <c:out value="${product.name}"/>
                        &nbsp;
                        (
                        <c:choose>
                            <c:when test="${empty(product.minDuration.duration)}">
                                <c:out value="${minDuration}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${product.minDuration.string2}"/>
                            </c:otherwise>
                        </c:choose>
                        &nbsp;
                        <c:choose>
                            <c:when test="${empty(product.maxDuration.duration)}">
                                <c:out value="${maxDuration}"/>
                                <c:if test="${product.maxDurationInclude == false}">*</c:if>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${product.maxDuration.string2}"/>
                                <c:if test="${product.maxDurationInclude == false}">*</c:if>
                            </c:otherwise>
                        </c:choose>
                        )
                    </div>

                    <c:if test="${product.needInitialInstalment == true}">
                        <div class="loanTitle needSurety">Первоначальный взнос:
                            <c:if test="${!empty product.minInitialInstalment}">
                                от ${product.minInitialInstalment}%
                            </c:if>
                            <c:if test="${!empty product.maxInitialInstalment}">
                                до ${product.maxInitialInstalment}%
                            </c:if>
                        </div>
                    </c:if>

                    <div class="loanTitle needSurety">
                        Обеспечение: ${product.needSurety ? 'требуется' : 'не требуется'}</div>
                    <div class="noPadding">
                        <table class="tblInf shadow" width="100%" cellspacing="0" cellpadding="0"
                               align="CENTER">
                            <tr class="tblInfHeader">
                                <th class="titleTable"></th>
                                <th class="titleTable">Сумма кредита</th>
                                <th class="titleTable">Процентная ставка (%)</th>
                            </tr>
                            <c:set var="include" value="0"/>
                            <input type="hidden" name="changeDate" id="changeDate" value=""/>
                            <c:forEach items="${product.conditions}" var="condition">
                                <tr class="ListLine0">
                                    <td><input type="radio" name="loanId" value="${condition.id}" onclick="changeDateSelector(${product.changeDate.timeInMillis});"/></td>                                    <td>
                                        <c:if test="${not empty condition.minAmount}">
                                            От ${phiz:formatAmount(condition.minAmount)}
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${not empty condition.maxAmount}">
                                                до ${phiz:formatAmount(condition.maxAmount)}
                                                <c:if test="${condition.maxAmountInclude == false}">*<c:set
                                                        var="include" value="1"/></c:if>
                                            </c:when>
                                            <c:when test="${not empty condition.maxAmountPercent}">
                                                до ${condition.maxAmountPercent} % от стоимости
                                                <c:if test="${condition.maxInterestRateInclude == false}">*<c:set
                                                        var="include" value="1"/></c:if>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty condition.minInterestRate and not empty condition.maxInterestRate}">
                                                <c:out value="${condition.minInterestRate}-${condition.maxInterestRate}"/>
                                                <c:if test="${condition.maxAmountInclude == false}">*<c:set
                                                        var="include" value="1"/></c:if>
                                            </c:when>
                                            <c:when test="${not empty condition.minInterestRate and empty condition.maxInterestRate}">
                                                от <c:out value="${condition.minInterestRate}"/>
                                            </c:when>
                                            <c:when test="${empty condition.minInterestRate and not empty condition.maxInterestRate}">
                                                до <c:out value="${condition.maxInterestRate}"/>
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <c:if test="${include eq 1}">
                        <div class="loanTitle include">* - не включительно</div>
                    </c:if>
                    <c:if test="${not empty product.additionalTerms}">
                        <div class="loanTitle">
                            <a onclick="showOrHideItems('terms_${product.id}')">Дополнительные условия</a>

                            <div id="terms_${product.id}" class="loanTitle hiddenDiv word-wrap">
                                <c:out value="${product.additionalTerms}"/>
                            </div>
                        </div>
                    </c:if>
                    <br/>
                </div>
            </c:forEach>
        </tiles:put>
    </tiles:insert>
</html:form>