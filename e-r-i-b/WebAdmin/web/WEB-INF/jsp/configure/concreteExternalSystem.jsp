<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/adapters/configure/externalSystem">
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <c:if test="${form.pageMode != 'VIEW_SYNC_INFO'}">
            <tiles:put name="tilesDefinition" value="routingEdit"/>
            <c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
            <c:set var="req" value="${pageContext.request}"/>
            <tiles:put name="pageTitle"/>

            <tiles:put name="mainmenu"  type="string" value="ExternalSystems"/>
            <tiles:put name="submenu" value="Configure"/>
            <tiles:put name="formAlign" value="center"/>
            <tiles:put name="pageName"> Настройки параметров <c:out value="${ConcreteExternalSystemConfigForm.displayedSystemName}"/></tiles:put>
            <tiles:put name="pageDescription" value="На данной странице можно изменить параметры взаимодействия с внешней системой."/>
            <tiles:put name="replicateAction" value="${phiz:getUrlParameters(req)}"/>
            <tiles:put name="additionalStyle"   type="string" value="width750"/>

            <tiles:put name="data"      type="string">
                <html:hidden property="systemName"/>
                <c:choose>
                    <c:when test="${ConcreteExternalSystemConfigForm.systemName == 'ESB'}">
                        <%@ include file="externalSystem\ESBSettings.jsp" %>
                    </c:when>
                    <c:when test="${ConcreteExternalSystemConfigForm.systemName == 'IPAS'}">
                        <%@ include file="externalSystem\iPasSettings.jsp" %>
                    </c:when>
                    <c:when test="${ConcreteExternalSystemConfigForm.systemName == 'CSA'}">
                        <%@ include file="externalSystem\CSASettings.jsp" %>
                    </c:when>
                    <c:when test="${ConcreteExternalSystemConfigForm.systemName == 'CSA_FRONT'}">
                        <%@ include file="externalSystem\CSA_FRONTSettings.jsp" %>
                    </c:when>
                    <c:when test="${ConcreteExternalSystemConfigForm.systemName == 'CSA_BACK'}">
                        <%@ include file="externalSystem\CSA_BACKSettings.jsp" %>
                    </c:when>
                    <c:when test="${ConcreteExternalSystemConfigForm.systemName == 'RSA'}">
                        <%@ include file="externalSystem\fraud-monitoring-settings.jsp" %>
                    </c:when>
                </c:choose>
            </tiles:put>
            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"         value="button.save"/>
                    <tiles:put name="commandHelpKey"     value="button.save.help"/>
                    <tiles:put name="bundle"             value="configureBundle"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="action"             value="/adapters/configure/externalSystem.do?systemName=${form.systemName}"/>
                </tiles:insert>

                <tiles:insert definition="clientButton"  flush="false">
                   <tiles:put name="commandTextKey"      value="button.cancel"/>
                   <tiles:put name="commandHelpKey"      value="button.cancel.help"/>
                   <tiles:put name="bundle"              value="configureBundle"/>
                   <tiles:put name="action"              value="/adapters/configure.do"/>
                </tiles:insert>
            </tiles:put>
        </c:if>
    </tiles:insert>
</html:form>