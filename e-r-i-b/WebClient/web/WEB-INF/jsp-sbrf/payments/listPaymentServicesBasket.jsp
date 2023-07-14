<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/userprofile/serviceContent" onsubmit="return setEmptyAction()">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="paymentService" value="${phiz:getPaymentServiceById(frm.serviceId)}"/>
    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>
    <c:set var="fromResource" value="${param['fromResource']}"/>
    <c:set var="currentPage" value="${frm.currentPage}"/>

    <c:set var="serviceURL" value="/private/userprofile/serviceContent.do" scope="request"/>
    <c:set var="providerURL" value="/private/userprofile/basket/subscription/create.do" scope="request"/>
    <c:set var="categoryURL" value="${serviceURL}" scope="request"/>
    <c:set var="paymentListURL" value="/private/userprofile/listServices" scope="request"/>
    <c:set var="pageKind" value="basket" scope="request"/>
    <c:set var="accountingEntityId" value="${frm.accountingEntityId}" scope="request"/>
    <c:set var="backURL" value="/private/userprofile/listServices.do"/>

    <tiles:insert definition="searchServiceProviders">
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="activeItem" value="searchAccounts"/>
                <tiles:put name="data" type="string">
                    <div class="Title sp-title">Подключение услуги с выставлением счета</div>
                    <div class="clear"></div>
                    <div class="searchProvidersDescription">Для оптимизации списка поставщиков воспользуйтесь поиском или выберите ваш регион</div>
                    <div class="clear"></div>
                    <%@ include file="/WEB-INF/jsp-sbrf/payments/listPaymentServices.jsp" %>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>