<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:catch var="errorJSP">
<phiz:webModuleContainer>
    <tiles:insert definition="pfpPassing">
        <tiles:put name="aditionalHeaderData" value="/WEB-INF/jsp/common/pfp/pfpAdditionalHeader.jsp"/>
        <tiles:put name="submenu" type="string" value="${phiz:webModulePageSubmenu(pageContext)}"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="simpleForm" flush="false">
                <tiles:put name="name" value="${phiz:webModulePageTitle(pageContext)}"/>
                <tiles:put name="description">${phiz:webModulePageDescription(pageContext)}</tiles:put>
                <tiles:put name="additionalStyle" value="width750"/>
                <tiles:put name="data">
                    ${phiz:webModulePageBody(pageContext)}
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