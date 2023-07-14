<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/sbnkd/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="personEdit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:put name="needSave" type="string" value="false"/>
    <tiles:put name="submenu" value="sbnkdClaim" type="string"/>
    <tiles:put name="pageTitle" type="string"><bean:message bundle="personsBundle" key="listpage.title"/></tiles:put>
    <tiles:put name="menu" type="string">
        <script type="text/javascript">
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/persons/sbnkd/edit')}"/>

            function isDataChanged(notChangedElemId)
            {
                return false;
            }


            function doEdit()
            {
                checkIfOneItem("selectedIds");
                if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                    return;
                var id = getRadioValue(document.getElementsByName("selectedIds"));
                window.location = '${url}?id=' + id + '&person=${form.person}';
            }
        </script>

    </tiles:put>

    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterDataSpan" flush="false">
            <tiles:put name="label" value="label.date"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="name" value="Date"/>
            <tiles:put name="template" value="DATE_TEMPLATE"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">

        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="AdapterListTable"/>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.view"/>
                    <tiles:put name="commandHelpKey" value="button.view.help"/>
                    <tiles:put name="bundle"         value="personsBundle"/>
                    <tiles:put name="onclick"        value="doEdit();"/>
                </tiles:insert>

            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data">

                    <sl:collectionParam id="selectName"     value="selectedIds"/>
                    <sl:collectionParam id="selectType"     value="checkbox"/>
                    <sl:collectionParam id="selectProperty" value="id"/>

                    <sl:collectionItem title="Идентификатор">
                        <phiz:link action="/persons/sbnkd/edit">
                            <phiz:param name="id" value="${listElement.id}"/>
                            <phiz:param name="person" value="${form.person}"/>
                            ${listElement.id}
                        </phiz:link>
                    </sl:collectionItem>
                    <sl:collectionItem title="Статус">
                        <bean:message bundle="personsBundle" key="sbnkd.status.${listElement.status}" failIfNone="false"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="Дата создания">
                        <fmt:formatDate value="${listElement.creationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                    </sl:collectionItem>
                </sl:collection>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.data}"/>
            <tiles:put name="emptyMessage"><bean:message bundle="personsBundle" key="empty.message"/></tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>