<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html:form action="/private/accounts/graphic-abstract" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="imagePath" value="${skinUrl}/images" scope="request"/>
    <c:set var="image" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm11')}" scope="request"/>

    <c:if test="${not empty form.accountLink}">
        <c:set var="accountLink" value="${form.accountLink}" scope="request"/>
        <c:set var="resourceAbstract" value="${form.accountAbstract}" scope="request"/>
        <c:set var="account" value="${accountLink.account}" scope="request"/>
        <c:set var="isLock"  value="${account.accountState!='OPENED'}" scope="request"/>
        <c:set var="accountBalans" value="${fn:substring(account.number, 0,5)}" scope="request"/>
        <c:set var="accountInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/accounts/info.do?id=')}" scope="request"/>

        <c:set var="creationType" scope="request">${phiz:getPersonInfo().creationType}</c:set>
    </c:if>

    <tiles:insert definition="accountPlot">
        <tiles:put name="menu" type="string"/>
        <c:if test="${not empty form.accountLink}">
            <%--"хлебные крошки"--%>
            <tiles:put name="breadcrumbs">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="main" value="true"/>
                    <tiles:put name="action" value="/private/accounts.do"/>
                </tiles:insert>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="¬клады"/>
                    <tiles:put name="action" value="/private/accounts/list.do"/>
                </tiles:insert>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name">
                        <bean:write name="accountLink" property="name"/> ${phiz:getFormattedAccountNumber(account.number)}
                    </tiles:put>
                    <tiles:put name="last" value="true"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data" type="string">
                <%--
                    —ущность вклады(вклады) [42301-42307, 42601-42507],
                    сущность вклады(счета) [40817 и 40820, если они не €вл€ютс€ — —, т.е. к ним не прив€зана карта]
                    --%>
                <c:choose>
                    <c:when test="${accountBalans=='40817' ||accountBalans=='40820'}">
                        <c:set var="type" scope="request">
                            <bean:message key="favourite.link.account" bundle="paymentsBundle"/>
                        </c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="type" scope="request">
                            <bean:message key="favourite.link.deposit" bundle="paymentsBundle"/>
                        </c:set>
                    </c:otherwise>
                </c:choose>
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="data">
                        <jsp:include page="accountGraphicAbstractData.jsp"/>
                    </tiles:put>
                </tiles:insert>

                <c:if test="${not empty form.otherAccounts}">
                    <div id="another-deposits">
                        <tiles:insert definition="mainWorkspace" flush="false">
                            <c:set var="accountCount" value="${phiz:getClientProductLinksCount('ACCOUNT')}"/>
                            <tiles:put name="title">
                                ќстальные вклады
                                (<a href="${phiz:calculateActionURL(pageContext, '/private/accounts/list')}" class="blueGrayLink">показать все ${accountCount}</a>)
                            </tiles:put>
                            <tiles:put name="data">
                                <div class="anotherDepositsContainer">
                                    <c:forEach items="${form.otherAccounts}" var="others">
                                        <c:set var="otherBalans" value="${fn:substring(others.number, 0,5)}"/>
                                        <div class="another-container">
                                            <a href="${accountInfoUrl}${others.id}">
                                                <c:choose>
                                                    <c:when test="${otherBalans=='40817' || otherBalans=='40820'}">
                                                        <img src="${image}/deposits_type/account32.jpg"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${image}/deposits_type/deposit32.jpg"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </a>
                                            <a href="${accountInfoUrl}${others.id}" class="another-name decoration-none"><bean:write name="others" property="name"/></a>

                                            <div>
                                                <a href="${accountInfoUrl}${others.id}" class="another-number">${phiz:getFormattedAccountNumber(others.number)}</a>
                                                <c:set var="state" value="${others.account.accountState}"/>
                                                <c:set var="className">
                                                    <c:choose>
                                                        <c:when test="${state eq 'CLOSED' or state eq 'ARRESTED'}">
                                                            red
                                                        </c:when>
                                                        <c:when test="${state eq 'LOST_PASSBOOK'}">
                                                            green
                                                        </c:when>
                                                    </c:choose>
                                                </c:set>

                                                <span class="${className}">
                                                    <span class="prodStatus status" style="font-weight:normal;">
                                                        <c:if test="${not empty className}">
                                                            <nobr>(${state.description})</nobr>
                                                        </c:if>
                                                    </span>
                                                </span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    &nbsp;
                                </div>
                                <div class="clear"></div>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </c:if>
            </tiles:put>
        </c:if>
    </tiles:insert>
</html:form>
