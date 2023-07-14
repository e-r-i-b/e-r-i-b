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
<html:form action="/private/insurance/list" onsubmit="return setEmptyAction(event)">
    <tiles:insert definition="insurance">
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <c:set var="productUrl" value="${globalUrl}/commonSkin/images/products"/>
        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="menu" type="string"/>

        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:if test="${!form.backError}">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Страхование"/>
                    <tiles:put name="data" type="string">
                        <c:choose>
                            <c:when test="${not empty form.insuranceLinks && not empty form.insuranceAppList}">
                                <c:set var="elementsCount" value="${fn:length(form.insuranceLinks)-1}"/>
                                <logic:iterate id="listElement" name="form" property="insuranceLinks" indexId="i">
                                    <c:set var="insuranceLink" value="${listElement}" scope="request"/>
                                    <c:set var="insuranceApp" value="${form.insuranceAppList[listElement]}"/>
                                    <c:set var="insuranceInfoLink" value="true" scope="request"/>
                                    <%@include file="insuranceTemplate.jsp" %>
                                    <c:if test="${elementsCount != i}">
                                        <div class="productDivider"></div>
                                    </c:if>
                                </logic:iterate>
                            </c:when>
                            <c:otherwise>
                                <div class="insuranceTemplate emptyTitle">
                                    <tiles:insert definition="productTemplate" flush="false">
                                        <tiles:put name="img" value="${productUrl}/icon_insurance.jpg"/>
                                        <tiles:put name="productNumbers">
                                            <span class="no-wrap grayTitleText">Информация о страховых продуктах отсутствует*</span>
                                        </tiles:put>
                                        <tiles:put name="additionalData">
                                                * может не отображаться информация о:<br />
                                                 - договорах страхования, заключенных ранее 01.07.2010<br />
                                                 - договорах инвестиционного (накопительного) страхования жизни, заключенных ранее 01.04.2012, а также о договорах инвестиционного (накопительного) страхования жизни, оформленных страховыми компаниями по результатам индивидуальной оценки риска.
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                </tiles:insert>
                
                <%--<tiles:insert definition="hidableRoundBorder" flush="false">
                    <tiles:put name="title" value="Закрытые страховые программы"/>
                    <tiles:put name="color" value="whiteTop"/>
                    <tiles:put name="data">

                    </tiles:put>
                </tiles:insert>--%>

            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
