<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%-- Кнопка для завершения работы с текущим клиентом. --%>

<tiles:importAttribute/>
<%-- Отображение сообщения о прекращении работы с клиентом. --%>
<c:set var="redirectUrl" value="${phiz:calculateActionURL(pageContext, '/person/search')}"/>
<script type="text/javascript">
    function resetClientInfo()
    {
        if (window.confirm("<bean:message key="confirm.finishWork" bundle="autopaymentsBundle"/>"))
        {
            loadNewAction('','');
            window.location="${redirectUrl}?resetClientInformation=true";
        }
    }
</script>

<tiles:insert definition="clientButton" service="AutoSubscriptionManagment" flush="false">
    <tiles:put name="commandTextKey"    value="button.finishWorkWithClient"/>
    <tiles:put name="commandHelpKey"    value="button.finishWorkWithClient.help"/>
    <tiles:put name="bundle"            value="autopaymentsBundle"/>
    <tiles:put name="onclick"           value="resetClientInfo();"/>
    <tiles:put name="viewType" value="blueBorder"/>
</tiles:insert>