<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/clientProfile/ident/edit"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:insert definition="configEdit">
            <tiles:put name="submenu" type="string" value="IdentConfig"/>
            <tiles:put name="pageTitle" type="string"><bean:message bundle="configureBundle" key="settings.clientident.editidentdeptitle"/></tiles:put>
            <tiles:put name="descTitle"><bean:message bundle="configureBundle" key="settings.clientident.department"/> ‘Â‰</tiles:put>
            <tiles:put name="data" type="string">
                <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext,'/clientProfile/ident/editIdent.do')}"/>
                <script type="text/javascript">
                    function sendData(add)
                    {
                        if (!add) {
                            checkIfOneItem("selectedIds");
                            if (!checkSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>") || !checkOneSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>"))
                                 return;
                        }

                        var selectedInput = $("[name=selectedIds]:checked");
                        location.href = '${actionUrl}?' + (add ? ('add=true') : ('id='+selectedInput.val()));
                    }

                    function removeIdent()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>") || !checkOneSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>"))
                             return;

                        if (!confirm('<bean:message bundle="configureBundle" key="settings.clientident.confirmDelete.message"/>'))
                            return;

                        var selectedInput = $("[name=selectedIds]:checked");
                        ajaxQuery('id='+selectedInput.val() + "&remove=true", '${actionUrl}',
                                function () {location.reload();});
                    }
                </script>

                <div class="clear minHeightClear"></div>

                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="IdentListTable"/>
                    <tiles:put name="showButtons" value="true"/>
                    <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.clientident.idents"/></tiles:put>
                    <tiles:put name="buttons">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.add"/>
                            <tiles:put name="commandHelpKey" value="button.add.help"/>
                            <tiles:put name="bundle"         value="configureBundle"/>
                            <tiles:put name="onclick"        value="sendData(true);"/>
                            <tiles:put name="viewType"       value="blueBorder"/>
                        </tiles:insert>

                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.edit"/>
                            <tiles:put name="commandHelpKey" value="button.edit.help"/>
                            <tiles:put name="bundle"         value="configureBundle"/>
                            <tiles:put name="onclick"        value="sendData(false);"/>
                            <tiles:put name="viewType"       value="blueBorder"/>
                        </tiles:insert>

                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.delete"/>
                            <tiles:put name="commandHelpKey" value="button.delete.help"/>
                            <tiles:put name="bundle"         value="configureBundle"/>
                            <tiles:put name="onclick"        value="removeIdent();"/>
                            <tiles:put name="viewType"       value="blueBorder"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="identifiers">
                            <sl:collectionParam id="selectName"     value="selectedIds"/>
                            <sl:collectionParam id="selectType"     value="checkbox"    condition="true"/>
                            <sl:collectionParam id="selectProperty" value="first.id"/>
                            <sl:collectionParam id="onRowDblClick"  value="sendData(false);" />

                            <c:set var="identName"><bean:message bundle="configureBundle" key="settings.clientident.name"/></c:set>
                            <c:set var="setOfAttr"><bean:message bundle="configureBundle" key="settings.clientident.setofattr"/></c:set>
                            <sl:collectionItem hidden="true" value="${listElement.first.id}"/>
                            <sl:collectionItem title="${identName}" value="${listElement.first.name}"/>
                            <sl:collectionItem title="${setOfAttr}" value="${listElement.second}"/>
                        </sl:collection>
                    </tiles:put>

                    <tiles:put name="isEmpty" value="${empty form.identifiers}"/>
                    <tiles:put name="emptyMessage"><bean:message bundle="configureBundle" key="empty.message"/></tiles:put>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
</html:form>
