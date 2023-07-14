<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="widget" value="${form.widget}"/>
<c:set var="loanDescriptors" value="${form.loanDescriptors}"/>
<c:set var="loanLinksCount" value="${fn:length(loanDescriptors)}"/>
<c:set var="showLoanLinkIds" value="${form.showLoanLinkIds}"/>
<c:set var="showLoanLinksCount" value="${fn:length(showLoanLinkIds)}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.LoanBlockWidget"/>
    <tiles:put name="cssClassname" value="wide-not-sizeable-widget LoanBlockWidget"/>
    <c:set var="loanListUrl" value="${phiz:calculateActionURL(pageContext, '/private/loans/list')}"/>
    <tiles:put name="title">
        У Вас
        <%--строку не форматировать--%>
        <a href="${loanListUrl}"><b>${loanLinksCount}&nbsp;${phiz:numeralDeclension(loanLinksCount, "кредит", "", "а", "ов")}</b></a><c:if
            test="${loanLinksCount > showLoanLinksCount}">, ${phiz:numeralDeclension(showLoanLinksCount, 'показан', '', 'о', 'о')} ${showLoanLinksCount}</c:if>
    </tiles:put>

    <tiles:put name="linksControl">
        <a href="#" onclick="hideAllOperations(this); return false;" onmouseover="changeTitle(this);">
            показать/свернуть операции
        </a>
        &nbsp;|&nbsp;
        <a href="${loanListUrl}" title="К списку">
            все кредиты
        </a>
        &nbsp;|&nbsp;
        <a href="${phiz:calculateActionURL(pageContext, '/private/favourite/list')}" title="Настроить">
            настроить
        </a>
    </tiles:put>

    <c:choose>
        <c:when test="${form.widgetOverdue and widget.notify}">
            <tiles:put name="borderColor" value="orangeTop"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="borderColor" value="greenTop"/>
        </c:otherwise>
    </c:choose>
    <tiles:put name="sizeable" value="false"/>
    <tiles:put name="editable" value="true"/>
    <tiles:put name="props">
        stoppedBlinkingByUser: ${form.blinkingStopped}
    </tiles:put>

    <tiles:put name="viewPanel">
        <c:if test="${not empty loanDescriptors}">
            <jsp:include page="/WEB-INF/jsp-sbrf/loans/annLoanMessageWindow.jsp" flush="false"/>
            <c:catch var="error">
                <c:set var="page" value="main" scope="request"/>
                <c:set var="currentHiddenCount" value="${0}"/>
                <logic:iterate id="listElement" name="form" property="loanDescriptors" indexId="i">
                    <c:set var="loanLink" value="${listElement.loanLink}" scope="request"/>
                    <c:set var="showInThisWidgetCheckBox" value="false" scope="request"/>
                    <c:set var="notificationButton" value="false" scope="request"/>
                    <c:set var="isOverdue" value="${listElement.overdue}" scope="request"/>
                    <c:set var="daysLeft" value="${listElement.daysLeft}" scope="request"/>
                    <c:choose>
                        <c:when test="${showLoanLinkIds ne null and phiz:contains(showLoanLinkIds, loanLink.id)}">
                            <tiles:insert page="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp" flush="false">
                                <tiles:put name="notNotifiedLoanLinkIds" beanName="widget" beanProperty="notNotifiedLoanLinkIds"/>
                                <tiles:put name="notify" beanName="widget" beanProperty="notify"/>
                            </tiles:insert>
                            <c:if test="${showLoanLinksCount - 1 > i - currentHiddenCount}">
                                <div class="productDivider"></div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="currentHiddenCount" value="${currentHiddenCount + 1}"/>
                        </c:otherwise>
                    </c:choose>
                </logic:iterate>
            </c:catch>
            <c:if test="${not empty error}">
                ${phiz:setException(error, pageContext)}
            </c:if>
        </c:if>
    </tiles:put>

    <tiles:put name="editPanel">
        <%-- Та часть виджета, которая будет скроллиться в режиме редактирования--%>
        <c:if test="${not empty loanDescriptors}">
            <c:catch var="error">
                <c:set var="page" value="main" scope="request"/>
                <logic:iterate id="listElement" name="form" property="loanDescriptors" indexId="i">
                    <c:set var="loanLink" value="${listElement.loanLink}" scope="request"/>
                    <c:set var="showInThisWidgetCheckBox" value="true" scope="request"/>
                    <c:set var="notificationButton" value="true" scope="request"/>
                    <c:set var="isOverdue" value="${listElement.overdue}" scope="request"/>
                    <c:set var="daysLeft" value="${listElement.daysLeft}" scope="request"/>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp" flush="false">
                        <tiles:put name="notNotifiedLoanLinkIds" beanName="widget" beanProperty="notNotifiedLoanLinkIds"/>
                        <tiles:put name="notify" beanName="widget" beanProperty="notify"/>
                    </tiles:insert>
                    <c:if test="${loanLinksCount - 1 > i}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </c:catch>
            <c:if test="${not empty error}">
                ${phiz:setException(error, pageContext)}
            </c:if>
             <div class="productDivider"></div>
            <div class="clear"></div>
            <div class="loanNotification" style="display : inline;">
                <span>
                    Напомнить об оплате отмеченных кредитов:
                </span>
                 <span class="radioButton"><input type="radio" class="notify" name="notifyRadioButton" onclick="cancelBubbling(event);" value="${notify}"/> Оповещать</span>
                 <span class="radioButton"><input type="radio" class="notNotify" name="notifyRadioButton" onclick="cancelBubbling(event);" value="${notNotify}"/> Не оповещать</span>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Оповещать за:</tiles:put>
                    <tiles:put name="data">
                        <select field="notifyDayCount">
                            <c:forEach var="element" begin="0" end="9" varStatus="status">
                                <option value="${status.count}">${status.count}</option>
                            </c:forEach>
                        </select>
                    </tiles:put>
                </tiles:insert>
               <div class="loanNotificationText">дней</div>
            </div>
        </c:if>

        <tiles:insert page="/WEB-INF/jsp/widget/widgetDeleteWindow.jsp" flush="false">
            <tiles:put name="productType" value="loan"/>
            <tiles:put name="widgetId" value="${form.codename}"/>
        </tiles:insert>

    </tiles:put>
</tiles:insert>
