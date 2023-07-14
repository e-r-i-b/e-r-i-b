<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="bundleName" value="commonBundle"/>
<c:catch var="errorJSP">
<phiz:webModuleContainer>
    <tiles:insert definition="accountInfo">
        <tiles:put name="aditionalHeaderData" value="/WEB-INF/jsp/common/pfp/pfpAdditionalHeader.jsp"/>
        <tiles:put name="breadcrumbs">
            ${phiz:webModulePageBreadcrumbs(pageContext)}
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderWithoutTop" flush="false">
                <tiles:put name="top">
                    <c:set var="selectedTab" value="pfp"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="financeContainer" flush="false">
                        <tiles:put name="infoText">${phiz:webModulePageDescription(pageContext)}</tiles:put>
                        <tiles:put name="showTitle" value="false"/>
                        <tiles:put name="showFavourite" value="false"/>
                        <tiles:put name="data">
                            ${phiz:webModulePageBody(pageContext)}
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</phiz:webModuleContainer>
</c:catch>
<c:if test="${not empty errorJSP}">
        ${phiz:processExceptionEntry(errorJSP,pageContext)}
        <script type="text/javascript">
            window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
        </script>
</c:if>