<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/moneyBox/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="autoSubscriptions" flush="false">
        <tiles:put name="submenu" type="string" value="MoneyBox"/>

        <%--Заголовок--%>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="page.title" bundle="moneyBoxBundle"/>
        </tiles:put>

        <%--Кнопки--%>
        <tiles:put name="menu" type="string">
            <script type="text/javascript">
                function printList(event)
                {
                    <c:set var="url" value="/moneyBox/printList.do"/>
                    openWindow(event, "${phiz:calculateActionURL(pageContext, url)}");
                }

                function printModifyClaim(event, changeStatus)
                {
                    <c:set var="url" value="/private/payments/modifyMoneybox/print.do"/>
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Укажите одну запись.') || (!checkOneSelection("selectedIds", 'Укажите только одну запись.')))
                       return false;
                    if (checkId(id))
                       openWindow(event, "${phiz:calculateActionURL(pageContext, url)}?field(autoSubNumber)=" + id + "&field(changeStatus)=" + changeStatus);
                }

                function createMoneyBox()
                {
                    location.href = "${phiz:calculateActionURL(pageContext, "/private/payments/edit.do")}" + "?form=CreateMoneyBoxPayment";
                }

                function editMoneyBox()
                {
                    <c:set var="url" value="/private/payments/edit"/>
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                        checkIfOneItem("selectedIds");
                        if (!checkSelection("selectedIds", 'Укажите одну запись.') || (!checkOneSelection("selectedIds", 'Укажите только одну запись.')))
                            return false;
                        if (checkId(id))
                            window.location = "${phiz:calculateActionURL(pageContext, url)}?autoSubNumber=" + id + "&form=EditMoneyBoxClaim";
                }

                function validateSelection()
                {
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Укажите одну запись.') || (!checkOneSelection("selectedIds", 'Укажите только одну запись.')))
                        return false;
                    if (checkId(id))
                        return true;
                }

                function checkId(id)
                {
                    var searchedName = "claim|" + id;
                    if (document.getElementsByName(searchedName).length != 0)
                    {
                       alert("Для проведения действия над копилкой, она должна быть подтверждена");
                       return false;
                    }
                    return true;
                }

                function validateClaimSelection()
                {
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Укажите одну запись.') || (!checkOneSelection("selectedIds", 'Укажите только одну запись.')))
                        return false;
                    var searchedName = "claim|" + id;
                    if (document.getElementsByName(searchedName).length != 0)
                        return true;
                    alert("Для данного действия выбранная заявка должна быть в статусе \"Черновик\"");
                    return false;
                }
            </script>

            <c:if test="${phiz:impliesService('EmployeeCreateMoneyBoxPayment')}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.connectMoneybox"/>
                    <tiles:put name="commandHelpKey" value="button.connectMoneybox.help"/>
                    <tiles:put name="bundle"         value="moneyBoxBundle"/>
                    <tiles:put name="viewType"       value="blueBorder"/>
                    <tiles:put name="onclick">
                        createMoneyBox(event);
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"            value="button.claim.confirm"/>
                    <tiles:put name="commandHelpKey"        value="button.claim.confirm"/>
                    <tiles:put name="bundle"                value="moneyBoxBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="validationFunction"    value="validateClaimSelection();"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"            value="button.claim.remove"/>
                    <tiles:put name="commandHelpKey"        value="button.claim.remove"/>
                    <tiles:put name="bundle"                value="moneyBoxBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="validationFunction"    value="validateClaimSelection();"/>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty form.data}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print"/>
                    <tiles:put name="commandHelpKey" value="button.print.help"/>
                    <tiles:put name="bundle"         value="moneyBoxBundle"/>
                    <tiles:put name="viewType"       value="blueBorder"/>
                    <tiles:put name="onclick">
                        printList(event);
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${phiz:impliesService('EmployeeEditMoneyBoxClaim')}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                    <tiles:put name="bundle"         value="autopaymentsBundle"/>
                    <tiles:put name="viewType"       value="blueBorder"/>
                    <tiles:put name="onclick">
                        editMoneyBox();
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${phiz:impliesService('EmployeeRefuseMoneyBoxPayment')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"            value="button.confirmRefuse"/>
                    <tiles:put name="commandHelpKey"        value="button.refuse.help"/>
                    <tiles:put name="bundle"                value="autopaymentsBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="validationFunction"    value="validateSelection();"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"        value="button.refuse.print"/>
                    <tiles:put name="commandHelpKey"        value="button.refuse.print.help"/>
                    <tiles:put name="bundle"                value="autopaymentsBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="onclick"               value="printModifyClaim(event,'REFUSE');"/>
                </tiles:insert>
            </c:if>

            <c:if test="${phiz:impliesService('EmployeeRecoverMoneyBoxPayment')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"            value="button.confirmRecover"/>
                    <tiles:put name="commandHelpKey"        value="button.recover.help"/>
                    <tiles:put name="bundle"                value="autopaymentsBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="validationFunction"    value="validateSelection();"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"        value="button.recover.print"/>
                    <tiles:put name="commandHelpKey"        value="button.recover.print.help"/>
                    <tiles:put name="bundle"                value="autopaymentsBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="onclick"               value="printModifyClaim(event, 'RECOVER');"/>
                </tiles:insert>
            </c:if>

            <c:if test="${phiz:impliesService('EmployeeCloseMoneyBoxPayment')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"            value="button.confirmDisable"/>
                    <tiles:put name="commandHelpKey"        value="button.disable.help"/>
                    <tiles:put name="bundle"                value="autopaymentsBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="validationFunction"    value="validateSelection();"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"        value="button.close.print"/>
                    <tiles:put name="commandHelpKey"        value="button.close.print.help"/>
                    <tiles:put name="bundle"                value="autopaymentsBundle"/>
                    <tiles:put name="viewType"              value="blueBorder"/>
                    <tiles:put name="onclick"               value="printModifyClaim(event, 'CLOSE');"/>
                </tiles:insert>
            </c:if>

            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>

        <%--Список копилок--%>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="moneyBoxList"/>

                <%--Таблица--%>
                <tiles:put name="grid">
                    <sl:collection id="link" model="list" property="data">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="number"/>

                        <c:set var="moneyBox" value="${link.value}"/>
                        <c:set var="isClaim" value="${phiz:isInstance(moneyBox, 'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}"/>
                        <%--Дата--%>
                        <c:set var="dateTitle">
                            <bean:message key="list.label.date" bundle="moneyBoxBundle"/>
                        </c:set>

                        <c:set var="startDate" value="${isClaim ? moneyBox.dateCreated : moneyBox.startDate}"/>
                        <sl:collectionItem title="${dateTitle}" value="${phiz:сalendarToString(startDate)}"/>

                        <%--Название--%>
                        <c:set var="nameTitle">
                            <bean:message key="list.label.name" bundle="moneyBoxBundle"/>
                        </c:set>
                        <sl:collectionItem title="${nameTitle}" value="${moneyBox.friendlyName}"/>

                        <%--Карта списания--%>
                        <c:set var="fromResourceTitle">
                            <bean:message key="list.label.from.resource" bundle="moneyBoxBundle"/>
                        </c:set>
                        <sl:collectionItem title="${fromResourceTitle}" value="${phiz:getCutCardNumber(moneyBox.cardNumber)}"/>

                        <%--Счет зачисления--%>
                        <c:set var="toResourceTitle">
                            <bean:message key="list.label.to.resource" bundle="moneyBoxBundle"/>
                        </c:set>
                        <sl:collectionItem title="${toResourceTitle}" value="${moneyBox.accountNumber}"/>

                        <%--Вид копилки--%>
                        <c:set var="typeTitle">
                            <bean:message key="list.label.type" bundle="moneyBoxBundle"/>
                        </c:set>
                        <c:set var="sumType" value="${moneyBox.sumType}"/>
                        <sl:collectionItem title="${typeTitle}" value="${sumType.description}"/>

                        <%--Условия выполнения--%>
                        <c:set var="executionCaseTitle">
                            <bean:message key="list.label.execution.case" bundle="moneyBoxBundle"/>
                        </c:set>
                        <c:set var="amount" value="${moneyBox.amount}"/>
                        <sl:collectionItem title="${executionCaseTitle}">
                            <c:choose>
                                <c:when test="${sumType == 'FIXED_SUMMA'}">
                                    <c:set var="executionEventType" value="${moneyBox.executionEventType}"/>
                                    <c:set var="payDay" value="${moneyBox.longOfferPayDay}"/>

                                    <c:out value="${phiz:formatAmount(amount)}"/>&nbsp;
                                    <bean:message key="executionType.${executionEventType}.desc" bundle="moneyBoxBundle"/>
                                    <c:choose>
                                        <c:when test="${executionEventType == 'ONCE_IN_WEEK'}">
                                            <bean:message bundle="moneyBoxBundle" key="executionType.dayOfWeek.desc"/>&nbsp;
                                            <c:out value="${phiz:dayNumberToString(payDay.weekDay)}"/>.
                                        </c:when>
                                        <c:when test="${executionEventType == 'ONCE_IN_MONTH'}">
                                            <c:out value="${payDay.day}"/><bean:message bundle="moneyBoxBundle" key="executionType.dayOfMonth.desc"/>.
                                        </c:when>
                                        <c:when test="${executionEventType == 'ONCE_IN_QUARTER'}">
                                            <c:out value="${payDay.day}"/><bean:message bundle="moneyBoxBundle" key="executionType.dayOfMonth.desc"/>&nbsp;
                                            <c:out value="${payDay.monthInQuarter}"/><bean:message bundle="moneyBoxBundle" key="executionType.monthOfQuarter.desc"/>.
                                        </c:when>
                                        <c:when test="${executionEventType == 'ONCE_IN_YEAR'}">
                                            <c:out value="${payDay.day}"/>&nbsp;
                                            ${phiz:monthNumberToString(payDay.monthInYear)}.
                                        </c:when>
                                    </c:choose>
                                </c:when>

                                <c:when test="${sumType == 'PERCENT_BY_ANY_RECEIPT' || sumType == 'PERCENT_BY_DEBIT'}">
                                    <c:out value="${moneyBox.percent}"/><bean:message key="sumType.${sumType}.desc" bundle="moneyBoxBundle"/>&nbsp;
                                    <c:choose>
                                        <c:when test="${isClaim}">
                                            <c:set var="maxSumWrite" value="${moneyBox.amount}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:catch>
                                                <c:set var="maxSumWrite" value="${link.autoSubscriptionInfo.maxSumWritePerMonth}"/>
                                            </c:catch>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${not empty maxSumWrite}">
                                        <bean:message key="sumType.max.desc" bundle="moneyBoxBundle"/>
                                        <c:out value="${phiz:formatAmount(maxSumWrite)}"/>
                                    </c:if>
                                </c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <%--Статус--%>
                        <c:set var="stateTitle">
                            <bean:message key="list.label.state" bundle="moneyBoxBundle"/>
                        </c:set>
                        <c:choose>
                            <c:when test="${not empty moneyBox.autoPayStatusType }">
                                <c:set var="statusText" value="${moneyBox.autoPayStatusType.description}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="statusText" value="Черновик"/>
                            </c:otherwise>
                        </c:choose>
                        <sl:collectionItem title="${stateTitle}" value="${statusText}"/>
                        <c:if test="${isClaim}">
                            <input type="hidden" name="claim|${moneyBox.id}"/>
                        </c:if>
                        <c:set var="autoPayNumberTitle"><bean:message key="list.label.autopay.number" bundle="moneyBoxBundle"/> </c:set>
                        <sl:collectionItem title="${autoPayNumberTitle}" value="${moneyBox.autopayNumber}"/>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        var claims = $('[name^="claim|"]');
        $(claims).each(function() {
            var id = this.name.substring(this.name.indexOf('|') + 1);
            var checkBox = $(this.parentNode).find('[name="selectedIds"]');

            //костыль для ie8
            if (checkBox.length == 0)
            {
                checkBox = $(this.parentNode.parentNode).find('[name="selectedIds"]');
            }
            checkBox.attr('value', id);
        });
    </script>
</html:form>