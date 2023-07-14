<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/mail/area/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="contactCenterAreaList">
        <tiles:put name="submenu" type="string" value="ContactCenterArea"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.title" bundle="contactCenterAreaBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditAreaOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="contactCenterAreaBundle"/>
                <tiles:put name="action"  value="/mail/area/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="areaList"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="contactCenterAreaBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="label.list.area.name">
                            <c:if test="${not empty listElement and not empty listElement.name}">
                                <phiz:link action="/mail/area/edit"
                                          operationClass="EditAreaOperation">
                                          <phiz:param name="id" value="${listElement.id}"/>
                                          <bean:write name="listElement" property="name"/>
                                </phiz:link>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.area.TB">
                            <c:if test="${not empty listElement and not empty listElement.departments}">
                                <c:set var="first" value="true"/>
                                <c:forEach var="department" items="${listElement.departments}">
                                    <c:if test="${first != true}">,&nbsp;&nbsp;</c:if><c:out value="${phiz:getTBNameByCode(department)}"/>
                                    <c:set var="first" value="false"/>
                                </c:forEach>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="emptyMessage"><bean:message key="emptyMessage" bundle="contactCenterAreaBundle"/></tiles:put>

                <script type="text/javascript">
                    var addUrl = "${phiz:calculateActionURL(pageContext,'/mail/area/edit')}";
                    function doEdit()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkOneSelection("selectedIds", 'Пожалуйста, выберите одну запись'))
                            return;
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = addUrl + "?id=" + id;
                    }

                </script>

                <%-- Кнопки --%>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditAreaOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle"  value="contactCenterAreaBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" operation="RemoveAreaOperation">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="contactCenterAreaBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', '<bean:message key="choose.text" bundle="contactCenterAreaBundle"/>');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText"><bean:message key="confirm.text" bundle="contactCenterAreaBundle"/></tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>