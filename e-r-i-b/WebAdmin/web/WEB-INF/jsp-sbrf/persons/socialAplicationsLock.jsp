<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/persons/socialAplicationsLock" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="SocialApplicationsLock"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="edit.socialApplicationsLock.title" bundle="personsBundle"/>
        </tiles:put>
        <tiles:put name="needSave" type="string" value="false"/>
        <c:if test="${!empty form.socialDevices}">
            <tiles:put name="menu" type="string">
                <%--Отключить все--%>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.socialapplication.lockAll"/>
                    <tiles:put name="commandHelpKey" value="button.socialapplication.lockAll.help"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="confirmText" type="string" value="Вы действительно хотите отключить все приложения?"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
                <%--Отключить--%>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.socialapplication.lock"/>
                    <tiles:put name="commandHelpKey" value="button.socialapplication.lock.help"/>
                    <tiles:put name="bundle"  value="personsBundle"/>
                    <tiles:put name="validationFunction" value="validateSelectedApplications();"/>
                    <tiles:put name="confirmText" type="string" value="Вы действительно хотите отключить приложение?"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </tiles:put>
        </c:if>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function validateSelectedApplications()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", "Выберите приложение для отключения."))
                        return false;
                    return true;
                }
            </script>
            <%--Собственно список социальных приложений--%>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="devicesList"/>
                <tiles:put name="text" value="Подключенные приложения"/>
                <tiles:put name="isEmpty" value="${empty form.socialDevices}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного подключенного приложения!"/>
                <tiles:put name="grid">
                    <sl:collection id="socialApplication" model="list" property="socialDevices" bundle="personsBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
					    <sl:collectionParam id="selectName" value="selectedIds"/>
					    <%--Социальное приложение--%>
                        <sl:collectionItem title="title.socialapplication.name">
                            <c:out value="${socialApplication.deviceInfo}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="title.socialapplication.identifier">
                            <c:out value="${socialApplication.deviceID}"/>
                        </sl:collectionItem>
                        <%--Дата подключения--%>
                        <sl:collectionItem title="title.socialapplication.connectionDate">
                            <c:out value="${phiz:formatDateWithStringMonth(socialApplication.creationDate)}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>