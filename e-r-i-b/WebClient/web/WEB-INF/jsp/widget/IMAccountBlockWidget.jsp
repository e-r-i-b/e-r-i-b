<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imAccountLinks" value="${form.imAccounts}"/>
<c:set var="imAccountLinksCount" value="${fn:length(imAccountLinks)}"/>
<c:set var="showIMAccountLinkIds" value="${form.showIMAccountLinkIds}"/>
<c:set var="showIMAccountLinksCount" value="${fn:length(showIMAccountLinkIds)}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.ProductBlockWidget"/>
    <tiles:put name="cssClassname" value="wide-not-sizeable-widget"/>

    <c:set var="imaListUrl" value="${phiz:calculateActionURL(pageContext, '/private/ima/list')}"/>
    <tiles:put name="title">
        У Вас
        <%--строку не форматировать--%>
        <a href="${imaListUrl}"><b>${imAccountLinksCount}
            мет. ${phiz:numeralDeclension(imAccountLinksCount, "счет", "", "а", "ов")}</b></a><c:if
            test="${imAccountLinksCount > showIMAccountLinksCount}">, ${phiz:numeralDeclension(showIMAccountLinksCount, 'показан', '', 'о', 'о')} ${showIMAccountLinksCount}</c:if>
    </tiles:put>

    <tiles:put name="linksControl">
        <a href="#" onclick="hideAllOperations(this); return false;" onmouseover="changeTitle(this);">
            показать/свернуть операции
        </a>
        &nbsp;|&nbsp;
        <a href="${imaListUrl}" title="К списку">
            все металлические счета
        </a>
        &nbsp;|&nbsp;
        <a href="${phiz:calculateActionURL(pageContext, '/private/favourite/list')}" title="Настроить">
            настроить
        </a>
    </tiles:put>

    <tiles:put name="borderColor" value="greenTop"/>
    <tiles:put name="sizeable" value="false"/>
    <tiles:put name="editable" value="true"/>

    <tiles:put name="viewPanel">
        <c:if test="${not empty imAccountLinks}">
            <c:catch var="error">
                <c:set var="page" value="main" scope="request"/>
                <c:set var="imaInfoLink" value="true" scope="request"/>
                <c:set var="currentHiddenCount" value="${0}"/>
                <logic:iterate id="listElement" name="form" property="imAccounts" indexId="i">
                    <c:set var="imAccountLink" value="${listElement}" scope="request"/>
                    <c:set var="showInThisWidgetCheckBox" value="false" scope="request"/>
                    <c:choose>
                        <c:when test="${showIMAccountLinkIds ne null and phiz:contains(showIMAccountLinkIds, imAccountLink.id)}">
                            <c:set var="imAccountAbstract" value="${form.IMAccountAbstract[listElement]}"  scope="request"/>
                            <tiles:insert page="/WEB-INF/jsp/private/ima/imaTemplate.jsp" flush="false"/>
                            <c:if test="${showIMAccountLinksCount - 1 > i - currentHiddenCount}">
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
        <c:if test="${not empty imAccountLinks}">
            <c:catch var="error">
                <c:set var="page" value="main" scope="request"/>
                <c:set var="accountInfoLink" value="true" scope="request"/>
                <logic:iterate id="listElement" name="form" property="imAccounts" indexId="i">
                    <c:set var="imAccountLink" value="${listElement}" scope="request"/>
                    <c:set var="showInThisWidgetCheckBox" value="true" scope="request"/>
                    <c:set var="imAccountAbstract" value="${form.IMAccountAbstract[listElement]}" scope="request"/>
                    <tiles:insert page="/WEB-INF/jsp/private/ima/imaTemplate.jsp" flush="false"/>
                    <c:if test="${imAccountLinksCount - 1 > i}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </c:catch>
            <c:if test="${not empty error}">
                ${phiz:setException(error, pageContext)}
            </c:if>
        </c:if>

        <tiles:insert page="/WEB-INF/jsp/widget/widgetDeleteWindow.jsp" flush="false">
            <tiles:put name="productType" value="imaccount"/>
            <tiles:put name="widgetId" value="${form.codename}"/>
        </tiles:insert>

    </tiles:put>
</tiles:insert>
