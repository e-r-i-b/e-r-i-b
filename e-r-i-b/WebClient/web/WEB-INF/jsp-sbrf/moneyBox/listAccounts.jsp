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

<html:form action="/private/accounts/moneyBoxList" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="imagePath" value="${skinUrl}/images" scope="request"/>
    <c:set var="image" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm11')}" scope="request"/>

    <c:if test="${not empty form.accountLink}">
        <c:set var="accountLink" value="${form.accountLink}" scope="request"/>
        <c:set var="account" value="${accountLink.account}" scope="request"/>
        <c:set var="target" value="${accountLink.target}"/>
        <c:set var="isLock" value="${account.accountState!='OPENED'}" scope="request"/>
        <c:set var="accountBalans" value="${fn:substring(account.number, 0,5)}" scope="request"/>
        <c:set var="accountInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/accounts/info.do?id=')}" scope="request"/>

        <c:set var="personInf" value="${phiz:getPersonInfo()}" scope="request"/>
        <c:set var="creationType" value="${personInf.creationType}" scope="request"/>
    </c:if>

    <c:choose>
        <c:when test="${not empty target}">
            <tiles:insert definition="financePlot">
                <%--Хлебные крошки--%>
                <tiles:put name="breadcrumbs">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="main" value="true"/>
                        <tiles:put name="action" value="/private/accounts.do"/>
                    </tiles:insert>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name"><bean:message key="label.finance" bundle="favouriteBundle"/></tiles:put>
                        <c:choose>
                            <c:when test="${phiz:impliesService('UseWebAPIService')}">
                                <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="action" value="/private/graphics/finance"/>
                            </c:otherwise>
                        </c:choose>
                    </tiles:insert>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name"><bean:write name="target" property="name"/></tiles:put>
                        <tiles:put name="last" value="true"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="data" type="string">
                    <tiles:insert definition="roundBorderWithoutTop" flush="false">
                        <tiles:put name="top">
                            <c:set var="selectedTab" value="myTargets"/>
                            <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="type" value="Цель" scope="request"/>
                            <tiles:insert definition="financeContainer" flush="false">
                                <c:set var="isDetailInfoPage" value="true" scope="request"/>
                               <%@ include file="/WEB-INF/jsp-sbrf/accounts/targetTemplate.jsp"%>
                            </tiles:insert>
                            <!-- вкладки -->
                            <%@ include file="listAccountsMoneyBoxesTabs.jsp"%>
                        </tiles:put>
                    </tiles:insert>
                    <!-- копилки -->
                    <%@ include file="listAccountsMoneyBoxesData.jsp"%>
                </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="accountInfo">
                <tiles:put name="mainmenu" value="Deposits"/>
                <tiles:put name="menu" type="string"/>
                <c:if test="${not empty form.accountLink}">
                    <%--"хлебные крошки"--%>
                    <tiles:put name="breadcrumbs">
                        <tiles:insert definition="breadcrumbsLink" flush="false">
                            <tiles:put name="main" value="true"/>
                            <tiles:put name="action" value="/private/accounts.do"/>
                        </tiles:insert>
                        <tiles:insert definition="breadcrumbsLink" flush="false">
                            <tiles:put name="name" value="Вклады"/>
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
                                <c:set var="detailsPage" value="true" scope="request"/>
                                <div class="productdetailInfo">
                                    <%@ include file="/WEB-INF/jsp-sbrf/accounts/accountTemplate.jsp"%>
                                </div>
                                <%@ include file="listAccountsMoneyBoxesTabs.jsp"%>
                            </tiles:put>
                        </tiles:insert>
                        <%@ include file="listAccountsMoneyBoxesData.jsp"%>
                    </tiles:put>
                </c:if>
            </tiles:insert>
        </c:otherwise>
    </c:choose>

    <c:if test="${phiz:isScriptsRSAActive()}">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>

        <script type="text/javascript">
            <%-- формирование основных данных для ФМ --%>
            new RSAObject().toHiddenParameters();
        </script>
    </c:if>
</html:form>
