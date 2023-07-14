<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:insert definition="tableTemplate" flush="false">
    <script type="text/javascript">
        addClearMasks(null,
               function(event)
               {
                   clearInputTemplate('filter(fromDate)', '__.__.____');
                   clearInputTemplate('filter(toDate)', '__.__.____');
               });
        function edit(event, paymentId, state, formName)
        {
            preventDefault(event);
            <c:set var="u" value="/private/payments/default-action.do"/>
            var url = "${phiz:calculateActionURL(pageContext,u)}";
            window.location = url + "?id=" + paymentId + "&history=true" + "&objectFormName=" + formName + "&stateCode=" + state;
        }
    </script>
    <tiles:put name="id" value="AutoSubscriptionsClaims"/>
    <tiles:put name="grid">
        <sl:collection id="document" model="list" property="data" bundle="autopaymentsBundle">
            <c:if test="${not empty document}">
                <jsp:include page="../payments/subscriptions/support-payment-${document.formName}.jsp" flush="false" />
            </c:if>

            <sl:collectionItem title="label.date">
                <c:set var="creationDate">
                    <c:set var="dateCreated" value="${document.dateCreated}"/>
                    <c:if test="${!empty dateCreated}">
                        <bean:write name="dateCreated" property="time" format="dd.MM.yyyy"/>
                        <bean:write name="dateCreated" property="time" format="HH:mm:ss"/>
                    </c:if>
                </c:set>
                <sl:collectionItemParam id="value">
                    <a href="#" onclick="edit(event, '${document.id}', '${document.state.code}','${document.formName}');">
                        <c:out value="${creationDate}"/>
                    </a>
                </sl:collectionItemParam>
            </sl:collectionItem>
            <sl:collectionItem title="label.number">
                <sl:collectionItemParam id="value">
                    <a href="#" onclick="edit(event, '${document.id}', '${document.state.code}','${document.formName}');">
                        <c:out value="${document.documentNumber}"/>
                    </a>
                </sl:collectionItemParam>
            </sl:collectionItem>

            <sl:collectionItem title="label.autosubscription.name">
                <sl:collectionItemParam id="value">
                    <c:if test="${not empty document.friendlyName}">
                        <a href="#" onclick="edit(event, '${document.id}', '${document.state.code}','${document.formName}');">
                            <c:out value="${document.friendlyName}"/>
                        </a>
                    </c:if>
                </sl:collectionItemParam>
            </sl:collectionItem>
            <sl:collectionItem title="label.autosubscription.type">
                <sl:collectionItemParam id="value">
                    <!-- после реализации других типов заявок необходимо доработать -->
                    <c:out value="${printActionType}"/>
                </sl:collectionItemParam>
            </sl:collectionItem>

            <c:set var="state">documentState.${document.state.code}</c:set>
            <sl:collectionItem title="label.state">
                <sl:collectionItemParam id="value"><bean:message key="${state}" bundle="autopaymentsBundle"/></sl:collectionItemParam>
            </sl:collectionItem>

            <sl:collectionItem title="label.receiverName">
                <c:choose>
                    <c:when test="${phiz:isInstance(document,'com.rssl.phizic.business.documents.P2PAutoTransferClaimBase')}">
                        <c:choose>
                            <c:when test="${document.receiverType == 'several' and not empty document.receiverAccount}">
                                <c:out value="${phiz:getCutCardNumber(document.receiverAccount)}"/>&nbsp;<c:out value="${document.toAccountName}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${document.receiverName}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${document.receiverName}"/>
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>

            <c:set var="summaVal" value=""/>
            <sl:collectionItem title="label.summa" >
                <c:if test="${document.periodic && document.autoSubType == 'ALWAYS'}">
                    <c:set var="exactAmount" value="${document.exactAmount}"/>
                    <c:if test="${not empty exactAmount}">
                        <c:out value="${phiz:formatAmount(exactAmount)}"/>
                    </c:if>
                </c:if>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="emptyMessage">
        <c:choose>
            <c:when test="${fromStart}">
                Для поиска заявки в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
            </c:when>
            <c:otherwise>
                Не найдено данных  согласно критериям поиска.
             </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>