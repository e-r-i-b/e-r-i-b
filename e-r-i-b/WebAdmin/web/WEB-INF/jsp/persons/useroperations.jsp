<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/persons/useroperations" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${form.activePerson}"/>
    <c:set var="isShowSaves" value="${not (person.status == 'T' && phiz:isAgreementSignMandatory()) and phiz:impliesServiceRigid('PersonAccessManagement')}"/>

    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="Operations"/>
        <tiles:put name="pageTitle" type="string">Настройка прав доступа</tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.closeResources.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action" value="/persons/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="userOperations"/>
                <tiles:put name="buttons">
                    <c:if test="${isShowSaves}">
                        <tiles:insert definition="commandButton" flush="false" operation="AssignPersonAccessOperation">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.saveOperation.help"/>
                            <tiles:put name="bundle" value="personsBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
                <tiles:put name="data">
                    <c:set var="subform" value="${form.simpleAccess}"/>
                    <%@ include file="editAccess.jsp"%>
                </tiles:put>
            </tiles:insert>
            <script type="text/javascript">
                doOnLoad(function(){
                    var widthClient = getClientWidth();
                    if (navigator.appName=='Microsoft Internet Explorer')
                        document.getElementById("workspaceDiv").style.width = widthClient - leftMenuSize - 147;
                });
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>
