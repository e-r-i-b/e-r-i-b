<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%-- ������ ��� ���������� ������ � ������� ��������. --%>
<c:if test="${phiz:getPersonInfo() != null}">
    <tiles:importAttribute/>
    <%-- ����������� ��������� � ����������� ������ � ��������. --%>
    <c:set var="redirectUrl" value="${phiz:calculateActionURL(pageContext, '/pfp/person/search')}"/>
    <script type="text/javascript">
        function resetClientInfo()
        {
            if (window.confirm("<bean:message key="confirm.finishWork" bundle="pfpPassingBundle"/>"))
            {
                loadNewAction('','');
                window.location="${redirectUrl}?resetClientInformation=true";
            }
        }
    </script>

    <tiles:insert definition="clientButton" service="EmployeePfpEditService" flush="false">
        <tiles:put name="commandTextKey"    value="button.finishWorkWithClient"/>
        <tiles:put name="commandHelpKey"    value="button.finishWorkWithClient.help"/>
        <tiles:put name="bundle"            value="pfpPassingBundle"/>
        <tiles:put name="onclick"           value="resetClientInfo();"/>
        <tiles:put name="viewType"          value="blueBorder"/>
    </tiles:insert>
</c:if>
