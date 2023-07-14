<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/income_level" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="definition" value="paymentMain"/>
    <c:if test="${person == null}">
        <c:set var="definition" value="guestMain"/>
    </c:if>
    <tiles:insert definition="${definition}">

        <c:if test="${person!=null}">
            <tiles:put name="mainmenu" value="Payments"/>
            <tiles:put name="breadcrumbs">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="main" value="true"/>
                    <tiles:put name="action" value="/private/accounts.do"/>
                </tiles:insert>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                    <tiles:put name="action" value="/private/payments.do"/>
                </tiles:insert>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="Выбор дохода"/>
                    <tiles:put name="last" value="true"/>
                </tiles:insert>
            </tiles:put>
        </c:if>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function selectIncomeLevel(id)
                {
                    ensureElement("radio_" + id).checked = "true";
                }
            </script>
            <c:if test="${form.timeToRefresh > 0}">
                <script>
                    function ajaxGetLoanOffer()
                    {
                        <c:set var="u" value="/private/payments/income_level.do?fromRefresh=true"/>
                        var url = "${phiz:calculateActionURL(pageContext,u)}";
                        window.location = url;
                    }
                    doOnLoad(function()
                    {
                        showOrHideWaitDiv(true);
                        setTimeout(function()
                        {
                            showOrHideWaitDiv(false);
                            ajaxGetLoanOffer();
                        }, ${form.timeToRefresh});
                    })
                </script>
            </c:if>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="title" value="Выбор дохода"/>
                <tiles:put name="id" value="cardCondition"/>
                <tiles:put name="description">
                    Обратите внимание! Решение о выдаче кредитной карты будет произведено с учетом информации о Вашем ежемесячном доходе. Укажите сумму Вашего среднего дохода и нажмите на кнопку «Продолжить».
                </tiles:put>
                <tiles:put name="data">
                    <table class="depositProductInfo">
                        <tr>
                            <th></th>
                            <th>Средняя сумма доходов клиента</th>
                        </tr>
                        <c:set var="include" value="0"/>
                        <c:set var="isFirst" value="true"/>
                        <c:forEach var="incomeLevel" items="${form.data}">
                            <tr onclick="selectIncomeLevel(${incomeLevel.id});">
                                <td>
                                    <c:choose>
                                        <c:when test="${isFirst}">
                                            <input id="radio_${incomeLevel.id}" type="radio" name="selectedIds" value="${incomeLevel.id}" checked="true"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input id="radio_${incomeLevel.id}" type="radio" name="selectedIds" value="${incomeLevel.id}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${incomeLevel.minIncome != null}">
                                        от
                                        <c:out value="${phiz:formatAmount(incomeLevel.minIncome)}"/>
                                    </c:if>
                                    <c:if test="${incomeLevel.maxIncome != null}">
                                        до
                                        <c:out value="${phiz:formatAmount(incomeLevel.maxIncome)}"/>
                                        <c:if test="${incomeLevel.maxIncomeInclude == false}">*<c:set var="include" value="1"/></c:if>
                                    </c:if>
                                </td>
                            </tr>
                            <c:set var="isFirst" value="false"/>
                        </c:forEach>
                    </table>
                    <br/>
                    <c:if test="${include eq 1}"><div style="padding-left:40px;">* не включительно</div></c:if>

                    <tiles:put name="buttons">
                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="action" value="/private/payments.do"/>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.next"/>
                                <tiles:put name="commandHelpKey" value="button.next"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="validationFunction">
                                    function()
                                    {
                                        checkIfOneItem("selectedIds");
                                        return checkSelection('selectedIds', 'Выберите уровень дохода');
                                    }
                                </tiles:put>
                                <tiles:put name="enabled" value="${form.loanCardClaimAvailable}"/>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>