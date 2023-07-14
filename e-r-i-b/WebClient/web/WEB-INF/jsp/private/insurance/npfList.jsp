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

<tiles:importAttribute/>
<html:form action="/private/npf/list" onsubmit="return setEmptyAction(event)">

    <tiles:insert definition="NPF">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${globalUrl}/commonSkin/images/products"/>

        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="menu" type="string"/>

        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

            <c:if test="${phiz:impliesServiceRigid('PFRStatementClaim')}">
                <div class="mainWorkspace productListLink">
                   <tiles:insert definition="paymentsPaymentCardsDiv" service="PFRStatementClaim" operation="CreateFormPaymentOperation"
                              flush="false">
                        <tiles:put name="serviceId" value="PFRStatementClaim"/>
                    </tiles:insert>
                </div>
            </c:if>
            <c:if test="${phiz:impliesServiceRigid('InsuranceService') and !form.backError}">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Негосударственный пенсионный фонд Сбербанка"/>
                    <tiles:put name="data" type="string">
                        <c:choose>
                            <c:when test="${not empty form.insuranceLinks && not empty form.insuranceAppList}">
                                <c:set var="elementsCount" value="${fn:length(form.insuranceLinks)-1}"/>
                                <logic:iterate id="listElement" name="form" property="insuranceLinks" indexId="i">
                                    <c:set var="insuranceLink" value="${listElement}" scope="request"/>
                                    <c:set var="insuranceInfoLink" value="true" scope="request"/>
                                    <c:set var="insuranceApp" value="${form.insuranceAppList[listElement]}"/>
                                    <%@include file="insuranceTemplate.jsp" %>
                                    <c:if test="${elementsCount != i}">
                                        <div class="productDivider"></div>
                                    </c:if>
                                </logic:iterate>
                            </c:when>
                            <c:otherwise>
                                <div class="insuranceTemplate emptyTitle">
                                    <tiles:insert definition="productTemplate" flush="false">
                                        <tiles:put name="img" value="${imagePath}/icon_npf.jpg"/>
                                        <tiles:put name="productNumbers">
                                            <span class="no-wrap grayTitleText">Информация о продуктах НПФ Сбербанка отсутствует* </span>
                                        </tiles:put>
                                        <tiles:put name="additionalData">
                                            * может не отображаться информация о договорах об обязательном пенсионном страховании, заключенных ранее 01.03.2013 года, а также о договорах негосударственного пенсионного обеспечения.
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                </tiles:insert>

                <%--<tiles:insert definition="hidableRoundBorder" flush="false">
                    <tiles:put name="title" value="Закрытые пенсионные программы"/>
                    <tiles:put name="color" value="whiteTop"/>
                    <tiles:put name="data">

                    </tiles:put>
                </tiles:insert>--%>

            </c:if>
            <c:set var="pfrLink" value="${form.pfrLink}"/>
            <c:if test="${pfrLink.showInSystem}">
                <c:catch var="error">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="ПФР"/>
                        <tiles:put name="data">
                            <div class="pfrProduct">
                                <tiles:insert definition="productTemplate" flush="false" service="PFRHistoryFullService">
                                    <tiles:put name="img" value="${imagePath}/icon_pfr.jpg"/>
                                    <tiles:put name="title">Виды и размеры пенсий</tiles:put>
                                    <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
                                    <tiles:put name="src" value="${phiz:calculateActionURL(pageContext,'/private/pfr/pfrHistoryFull')}"/>
                                </tiles:insert>
                            </div>
                            <div class="productDivider"></div>
                            <jsp:include page="/WEB-INF/jsp/private/pfr/pfrTemplate.jsp" flush="false">
                                <jsp:param name="pfrClaims" value="${form.pfrClaims}"/>
                                <jsp:param name="hideShowInMainCheckBox" value="true"/>
                            </jsp:include>
                        </tiles:put>
                    </tiles:insert>
                </c:catch>
                <c:if test="${not empty error}">
                    ${phiz:setException(error, pageContext)}
                </c:if>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
