<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/routing/adapter/settings/edit">
    <c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
    <c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="adapterSettingsEdit"/>

        <tiles:put name="replicateAction"  value="nodeType=${form.nodeType}&id=${form.id}"/>
        <tiles:put name="mainmenu"  type="string" value="ExternalSystems"/>
        <tiles:put name="submenu"   type="string" value="Adapter"/>
        <tiles:put name="additionalStyle"   type="string" value="width1000"/>
        <tiles:put name="pageName">
            <bean:message bundle="adapterBundle" key="settings.edit.name"/>
        </tiles:put>
        <tiles:put name="pageDescription">
            <bean:message bundle="adapterBundle" key="settings.edit.description"/>
        </tiles:put>
        <tiles:put name="data"      type="string">
            <html:hidden property="id"/>
            <html:hidden property="nodeType"/>
            <jsp:include page="LoggerSettings.jsp"/>

            <c:choose>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'COD'}">
                    <jsp:include page="CODSettings.jsp"/>
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'RETAIL_V6'}">
                    <jsp:include page="RetailSettings.jsp"/>
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'GOROD'}">
                    <jsp:include page="GorodSettings.jsp"/>
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'CPFL'}">
                    <jsp:include page="CPFLSettings.jsp" />
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'ENISEY'}">
                    <jsp:include page="EniseySettings.jsp"/>
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'IQWAVE'}">
                    <jsp:include page="IQWaveSettings.jsp"/>
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'SOFIA_BILLING'}">
                    <jsp:include page="SofiaSettings.jsp"/>
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'SOFIA'}">
                    <jsp:include page="SofiaVMSSettings.jsp"/>
                </c:when>
                <c:when test="${EditAdapterSettingsForm.nodeType == 'XBANK'}">
                    <jsp:include page="XBankSettings.jsp"/>
                </c:when>
            </c:choose>
            <script type="text/javascript">
                $(document).ready(function()
                {
                    <!-- меняем ссылку на хелп, т.к. для адаптеров с разными внешними системами необходимо сделать разные ссылки -->
                    document.getElementById("helpLink").href = "javascript:openHelp('${helpLink}');";
                });

            </script>

        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"         value="button.save"/>
                <tiles:put name="commandHelpKey"     value="button.save.help"/>
                <tiles:put name="bundle"             value="adapterBundle"/>
                <tiles:put name="isDefault"            value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>

            <tiles:insert definition="clientButton"  flush="false">
               <tiles:put name="commandTextKey"      value="button.cancel"/>
               <tiles:put name="commandHelpKey"      value="button.cancel.help"/>
               <tiles:put name="bundle"              value="adapterBundle"/>
               <tiles:put name="action"              value="/dictionaries/routing/adapter/edit.do?id=${EditAdapterSettingsForm.id}"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
