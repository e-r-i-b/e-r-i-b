<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/autotransfer/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="paymentUrl" value="${phiz:calculateActionURL(pageContext, '/autotransfer/info?id=')}"/>

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="pageTitle" type="string" value="Список автопереводов"/>
        <tiles:put name="submenu" type="string" value="AutoTransfers"/>
        <tiles:put name="menu" type="string">
            <c:if test="${phiz:impliesServiceRigid('EmployeeCreateP2PAutoTransferClaim')}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.addTransfer"/>
                    <tiles:put name="commandHelpKey" value="button.addTransfer.help"/>
                    <tiles:put name="bundle"         value="autopaymentsBundle"/>
                    <tiles:put name="action"         value="private/payments/edit.do?form=CreateP2PAutoTransferClaim"/>
                    <tiles:put name="viewType"       value="blueBorder"/>
                </tiles:insert>
            </c:if>
            <c:if test="${not empty form.data}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print"/>
                    <tiles:put name="commandHelpKey" value="button.print.help"/>
                    <tiles:put name="bundle"         value="autopaymentsBundle"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                    <tiles:put name="onclick">
                        printList(event);
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function printList(event)
                {
                    <c:set var="url" value="/autotransfer/printlist.do"/>
                    openWindow(event, "${phiz:calculateActionURL(pageContext, url)}");
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="AutoSubscriptionsList"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="autopaymentsBundle">

                        <c:set var="startDate">
                            <fmt:formatDate value="${listElement.startDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                        </c:set>
                        <sl:collectionItem title="label.date" value="${startDate}"/>

                        <c:set var="asUrl">${paymentUrl}${listElement.number}</c:set>
                        <sl:collectionItem title="label.autosubscription.name" property="friendlyName" href="${asUrl}"/>

                        <sl:collectionItem title="label.chargeoffcard" value="${phiz:getCutCardNumber(listElement.cardLink.number)}"/>

                        <sl:collectionItem title="label.receiverName">
                            <c:if test="${not empty listElement}">
                                <c:choose>
                                    <c:when test="${listElement.value.type.simpleName == 'InternalCardsTransferLongOffer' and not empty listElement.value.receiverCard}">
                                        <c:set var="receiverCardLink" value="${phiz:getCardLink(listElement.value.receiverCard)}"/>
                                        <c:choose>
                                            <c:when test="${not empty receiverCardLink}">
                                                <c:out value="${phiz:getCutCardNumber(receiverCardLink.number)}"/>&nbsp;<c:out value="${receiverCardLink.description}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${phiz:getCutCardNumber(listElement.value.receiverCard)}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${phiz:getReceiverNameForView(listElement.value)}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.summa" value="${phiz:formatAmount(listElement.amount)}"/>

                        <c:set var="type">
                            <%--
                                1)    регулярных (PERIODIC):
                                    a.    ExeEventCode (ONCE_IN_WEEK/ONCE_IN_MONTH/ONCE_IN_QUARTER)
                                    b.    SummaKindCode (FIXED_SUMMA_IN_RECIP_CURR)
                                2)    по выставленному счету (CHECK):
                                    a.    ExeEventCode (ONCE_IN_WEEK/ONCE_IN_MONTH/ONCE_IN_QUARTER)
                                    b.    SummaKindCode (BY_BILLING)
                                3)    пороговых (LIMIT):
                                    a.    ExeEventCode (ON_REMAIND)
                                    b.    SummaKindCode (FIXED_SUMMA_IN_RECIP_CURR)
                            --%>
                            <c:set var = "typeSimpleName" value="${listElement.value.type.simpleName}"/>
                            <c:choose>
                                <c:when test="${typeSimpleName =='InternalCardsTransferLongOffer'}">
                                    <bean:message key="type.INTERNAL" bundle="autopaymentsBundle"/>
                                </c:when>
                                <c:when test="${typeSimpleName =='ExternalCardsTransferToOurBankLongOffer'}">
                                    <bean:message key="type.EXTERNAL_TO_OUR_BANK" bundle="autopaymentsBundle"/>
                                </c:when>
                                <c:when test="${typeSimpleName =='ExternalCardsTransferToOtherBankLongOffer'}">
                                    <bean:message key="type.EXTERNAL_TO_OUR_BANK" bundle="autopaymentsBundle"/>
                                </c:when>
                            </c:choose>
                        </c:set>

                        <sl:collectionItem title="label.autosubscription.type" value="${type}"/>
                        <sl:collectionItem title="label.state" value="${listElement.autoPayStatusType.description}"/>

                        <sl:collectionItem title="label.autopay.number" value="${listElement.value.autopayNumber}"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="emptyMessage" value="Не найдено ни одного автоплатежа для заданного клиента."/>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>

</html:form>
