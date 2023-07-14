<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="actionUrl" value="/private/userprofile/listServices" scope="request"/>
<html:form action="${actionUrl}">

<c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
<c:if test="${not empty form.searchServices}">
    <c:set var="isSearch" value="true"/>
</c:if>

    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>

    <tiles:insert definition="searchServiceProviders" flush="false">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
        <c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/receivers/list')}"/>
        <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q19')}"/>
        <c:set var="faqLinkPersonal" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm19')}"/>
        <c:set var="url" value="${phiz:calculateActionURL(pageContext, actionUrl)}"/>

        <tiles:put name="mainmenu" value=""/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="activeItem" value="searchAccounts"/>
                <tiles:put name="data">
                    <c:set var="pageType" value="basketServices" scope="request"/>
                    <c:set var="serviceURL" value="/private/userprofile/serviceContent.do" scope="request"/>

                    <c:if test="${not empty form.accountingEntityId}">
                        <input id="accountingEntityId" name="accountingEntityId" type="hidden" value="${form.accountingEntityId}">
                        <c:set var="accountingEntityIdParam" value="${form.accountingEntityId}"/>
                    </c:if>
                    <c:set var="providerURL" value="/private/userprofile/basket/subscription/create.do?accountingEntityId=${accountingEntityIdParam}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp-sbrf/payments/listServicesProviderSearch.jsp"/>
                    <jsp:include page="/WEB-INF/jsp-sbrf/payments/payments.jsp" />
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="basketBundle"/>
                        <tiles:put name="viewType" value="blueGrayLink"/>
                        <tiles:put name="action" value="/private/userprofile/basket.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
