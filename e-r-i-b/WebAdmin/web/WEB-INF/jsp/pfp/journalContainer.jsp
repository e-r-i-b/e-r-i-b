<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:catch var="errorJSP">
<phiz:webModuleContainer>
    <tiles:insert definition="pfpJournal">
        <tiles:put name="messagesBundle" value="pfpJournalBundle"/>
        <tiles:put name="aditionalHeaderData" value="/WEB-INF/jsp/common/plotAdditionalHeader.jsp"/>
        <tiles:put name="pageTitle" value="${phiz:webModulePageDescription(pageContext)}"/>
        <tiles:put name="submenu" type="string" value="journalPFP"/>
        <tiles:put name="filter" type="string">
             ${phiz:webModulePageFilter(pageContext)}
        </tiles:put>
        <tiles:put name="data" type="string">
            ${phiz:webModulePageBody(pageContext)}
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