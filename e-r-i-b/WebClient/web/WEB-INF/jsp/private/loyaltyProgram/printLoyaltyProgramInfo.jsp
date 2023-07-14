<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<html:form action="/private/loyalty/detail/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="operations" value="${form.operations}" scope="request"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
        <tiles:put name="data" type="string">
            <div class="printAbstract">
                <c:choose>
                    <c:when test="${not empty operations }">
                        <bean:message bundle="loyaltyBundle" key="label.table.desc"/> ${form.filters.fromDate}  <bean:message bundle="loyaltyBundle" key="label.to"/> ${form.filters.toDate}
                        <sl:collection id="schedule" model="no-pagination" name="operations" bundle="loyaltyBundle">
                            <sl:collectionItem styleClass="align-left" title="label.table.date">
                               <c:if test="${!empty schedule.operationDate}">
                                    <bean:write name='schedule' property="operationDate.time" format="dd.MM.yyyy"/>
                                </c:if>&nbsp;
                            </sl:collectionItem>
                            <sl:collectionItem title="label.table.partners">
                                ${schedule.operationInfo}
                            </sl:collectionItem>
                            <sl:collectionItem title="label.table.amount" styleClass="align-right" styleTitleClass="align-right">
                                 ${phiz:formatAmount(schedule.moneyOperationalBalance)}
                            </sl:collectionItem>
                            <sl:collectionItem title="label.table.thanks" styleClass="align-right" styleTitleClass="align-right">
                                <c:choose>
                                    <c:when test="${schedule.operationKind == 'debit'}">+${phiz:formatBigDecimal(schedule.operationalBalance)}</c:when>
                                    <c:when test="${schedule.operationKind == 'credit'}">
                                        <c:set var="balance" value="${phiz:formatBigDecimal(schedule.operationalBalance)}"/>
                                        &minus;${fn:replace(balance, '-', '')}
                                    </c:when>
                                    <c:otherwise>${phiz:formatBigDecimal(schedule.operationalBalance)}</c:otherwise>
                                </c:choose>
                            </sl:collectionItem>
                        </sl:collection>
                    </c:when>
                    <c:otherwise>
                        По данному бонусному счету не найдено ни одной операции.
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="abstractContainer NotPrintable">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print"/>
                    <tiles:put name="commandHelpKey" value="button.print"/>
                    <tiles:put name="bundle" value="loyaltyBundle"/>
                    <tiles:put name="viewType" value="simpleLink"/>
                    <tiles:put name="onclick">window.print();</tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>