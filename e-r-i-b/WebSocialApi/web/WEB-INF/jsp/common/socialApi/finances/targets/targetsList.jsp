<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c"%>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"            prefix="fmt"%>

<html:form action="/private/finances/targets/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">

            <c:set var="targets" value="${form.targets}"/>
            <c:if test="${not empty targets}">
                <targets>
                    <c:forEach var="target" items="${targets}">
                        <c:set var="accountLink" value="${target.accountLink}"/>

                        <target>
                            <type>${target.dictionaryTarget}</type>
                            <id>${target.id}</id>
                            <name>${target.name}</name>

                            <c:set var="comment" value="${target.nameComment}"/>
                            <c:if test="${not empty comment}">
                                <comment>${target.nameComment}</comment>
                            </c:if>

                            <tiles:insert definition="mobileDateTimeType" flush="false">
                                <tiles:put name="name"     value="date"/>
                                <tiles:put name="calendar" beanName="target" beanProperty="plannedDate"/>
                                <tiles:put name="pattern"  value="dd.MM.yyyy"/>
                            </tiles:insert>

                            <c:set var="amount" value="${target.amount}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name"         value="amount"/>
                                <tiles:put name="decimal"      value="${amount.decimal}"/>
                                <tiles:put name="currencyCode" value="${amount.currency.code}"/>
                            </tiles:insert>

                            <c:choose>
                                <c:when test="${not empty accountLink}">
                                    <c:set var="account" value="${accountLink.account}"/>
                                    <c:set var="balance" value="${account.balance}"/>

                                    <c:choose>
                                        <c:when test="${not accountLink.showInMobile}">
                                            <c:set var="status"      value="accountDisabled"/>
                                            <c:set var="description" value="Отображение данного счета для мобильного приложения отключено в настройках видимости продуктов."/>
                                        </c:when>

                                        <c:when test="${account.accountState == 'CLOSED'}">
                                            <c:set var="status"      value="accountClosed"/>
                                            <c:set var="description" value="Вклад для достижения цели закрыт. Вы можете открыть новый вклад или удалить цель."/>
                                        </c:when>

                                        <c:when test="${empty balance}">
                                            <c:set var="status"      value="accountUnavailable"/>
                                            <c:set var="description" value="Информация по вкладам временно недоступна."/>
                                        </c:when>

                                        <c:otherwise>
                                            <c:set var="status"      value="accountEnabled"/>
                                            <c:set var="description" value="Информация о вкладе недоступна. Возможны две причины: задержка получения данных или вклад Вами был закрыт."/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="claim"       value="${phiz:getAccountOpeningClaimById(target.claimId)}"/>
                                    <c:set var="claimState"  value="${claim.state.code}"/>

                                    <c:choose>
                                        <c:when test="${claimState == 'DELAYED_DISPATCH'}">
                                            <c:set var="status"      value="claimDelayed"/>

                                            <c:set var="formattedDate"><fmt:formatDate value="${claim.admissionDate.time}" pattern="dd.MM.yyyy в HH:mm"/></c:set>
                                            <c:set var="description" value="Заявка на открытие вклада отправлена в банк на обработку. Плановая дата исполнения ${formattedDate}"/>
                                        </c:when>

                                        <c:when test="${claimState == 'SAVED'}">
                                            <c:set var="status"      value="claimNotConfirmed"/>
                                            <c:set var="description" value="Для открытия вклада Вам необходимо подтвердить заявку."/>
                                        </c:when>

                                        <c:when test="${claimState == 'EXECUTED'}">
                                            <c:set var="status"      value="claimExecuted"/>
                                            <c:set var="description" value="Информация о вкладе недоступна. Возможны две причины: задержка получения данных или вклад Вами был закрыт."/>
                                        </c:when>

                                        <c:when test="${claimState == 'REFUSED' || claimState == 'DELETED'}">
                                            <c:set var="status"      value="claimRefused"/>
                                            <c:set var="description" value="Для открытия вклада создайте новую заявку. Оформленная ранее заявка отказана."/>
                                        </c:when>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>

                            <status>${status}</status>

                            <c:if test="${status eq 'accountEnabled'}">
                                <account>
                                    <id>${accountLink.id}</id>
                                    <rate>${phiz:formatDecimalToAmountWithCustomSeparator(account.interestRate, 2, '.')}</rate>

                                    <tiles:insert definition="mobileMoneyType" flush="false">
                                        <tiles:put name="name"         value="value"/>
                                        <tiles:put name="decimal"      value="${balance.decimal}"/>
                                        <tiles:put name="currencyCode" value="${balance.currency.code}"/>
                                    </tiles:insert>
                                </account>
                            </c:if>
                            <statusDescription>${description}</statusDescription>
                            <c:if test="${not empty target.imagePath}">
                                <image>
                                    <c:set var="userImageUrl" value="${phiz:buildUserImageUri(pageContext, target.imagePath, null, false)}"/>
                                    <staticUrl><c:out value="${userImageUrl[0]}"/></staticUrl>
                                    <defaultUrl><c:out value="${userImageUrl[1]}"/></defaultUrl>
                                </image>
                            </c:if>
                        </target>
                    </c:forEach>
                </targets>
            </c:if>

        </tiles:put>
    </tiles:insert>
</html:form>