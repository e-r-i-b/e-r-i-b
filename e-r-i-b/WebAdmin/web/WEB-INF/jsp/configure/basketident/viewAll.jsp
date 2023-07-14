<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/clientProfile/ident/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="IdentConfig"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="configureBundle" key="settings.clientident.title"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext,'/clientProfile/ident/edit.do')}"/>
            <script type="text/javascript">
                function sendData()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkOneSelection("selectedIds", '<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>'))
                        return;

                    location.href = '${actionUrl}?id=-1';
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="IdentListTable"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle"         value="configureBundle"/>
                        <tiles:put name="onclick"        value="sendData();"/>
                        <tiles:put name="viewType"       value="blueBorder"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="identifiers">
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectType"     value="radio"    condition="true"/>
                        <sl:collectionParam id="selectProperty" value="first"/>

                        <sl:collectionParam id="onRowClick"     value="selectRow(this, 'selectedIds');"/>
                        <sl:collectionParam id="onRowDblClick"  value="sendData();" />

                        <c:set var="depName"><bean:message bundle="configureBundle" key="settings.clientident.department"/></c:set>
                        <c:set var="setOfIdent"><bean:message bundle="configureBundle" key="settings.clientident.setofident"/></c:set>
                        <sl:collectionItem title="${depName}">‘Â‰</sl:collectionItem>
                        <sl:collectionItem title="${setOfIdent}" value="${listElement.second}"/>
                    </sl:collection>
                </tiles:put>

                <tiles:put name="isEmpty" value="${empty form.identifiers}"/>
                <tiles:put name="emptyMessage"><bean:message bundle="configureBundle" key="empty.message"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
