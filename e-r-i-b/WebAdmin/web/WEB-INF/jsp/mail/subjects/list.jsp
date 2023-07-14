<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/mail/subjects/list">

    <tiles:insert definition="mailMain">

        <tiles:put name="submenu" type="string" value="MailSubjects"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="subject.list.title" bundle="mailBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditSubjectOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="action"  value="/mail/subjects/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.subject.name"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="subject"/>
                <tiles:put name="maxlength" value="50"/>
                <tiles:put name="size"      value="30"/>
	        </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                    var addUrl = "${phiz:calculateActionURL(pageContext,'/mail/subjects/edit')}";
                    function doEdit()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkOneSelection("selectedIds", 'Пожалуйста, выберите одну запись'))
                            return;
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = addUrl + "?id=" + id;
                    }

                </script>

             <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="MailSubjectsList"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="mailBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="Тематика обращения">
                            <c:out value="${listElement.description}"/>
                        </sl:collectionItem>

                    </sl:collection>
                </tiles:put>

                <%-- Кнопки --%>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditSubjectOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle"  value="mailBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveSubjectOperation">
                            <tiles:put name="commandKey"     value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove"/>
                            <tiles:put name="bundle"         value="mailBundle"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection('selectedIds', 'Выберите тематику обращния для удаления!');
                                }
                            </tiles:put>
                            <tiles:put name="confirmText" value="Вы действительно хотите удалить данную тематику?"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>

</html:form>