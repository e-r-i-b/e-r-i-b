<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/account/payments" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="imagePath" value="${skinUrl}/images" scope="request"/>
    <c:set var="image" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm11')}" scope="request"/>

    <c:if test="${not empty form.accountLink}">
        <c:set var="accountLink" value="${form.accountLink}" scope="request"/>
        <c:set var="account" value="${accountLink.account}" scope="request"/>
        <c:set var="accountBalans" value="${fn:substring(account.number, 0,5)}" scope="request"/>
        <c:set var="accountInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/accounts/info.do?id=')}" scope="request"/>
    </c:if>

    <tiles:insert definition="accountInfo">
        <tiles:put name="mainmenu" value="Deposits"/>
        <tiles:put name="menu" type="string"/>
        <c:if test="${not empty form.accountLink}">

            <%--"������� ������"--%>
            <tiles:put name="breadcrumbs">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="main" value="true"/>
                    <tiles:put name="action" value="/private/accounts.do"/>
                </tiles:insert>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="������"/>
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
                        <jsp:include page="accountPaymentsData.jsp"/>
                    </tiles:put>
                </tiles:insert>

                <c:if test="${not empty form.otherAccounts}">
                    <div id="another-deposits">
                        <tiles:insert definition="mainWorkspace" flush="false">
                            <c:set var="accountCount" value="${phiz:getClientProductLinksCount('ACCOUNT')}"/>
                            <tiles:put name="title">
                                ��������� ������
                                (<a href="${phiz:calculateActionURL(pageContext, '/private/accounts/list')}" class="blueGrayLink">�������� ��� ${accountCount}</a>)
                            </tiles:put>
                            <tiles:put name="data">
                                <div class="anotherDepositsContainer">
                                    <c:forEach items="${form.otherAccounts}" var="others">
                                        <c:set var="otherBalans"
                                               value="${fn:substring(others.number, 0,5)}"/>
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

                                            <div class="another-number">
                                                <a href="${accountInfoUrl}${others.id}" class="another-number">${phiz:getFormattedAccountNumber(others.number)}</a>
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
