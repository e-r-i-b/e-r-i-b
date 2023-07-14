
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>


<html:form action="/private/person/documents/history" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <tiles:insert definition="personEdit">
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="submenu" type="string" value="PersonIdentityHistory"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.person.identity.history" bundle="identityBundle"/>
        </tiles:put>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="menu" type="string">
            <c:set var="personId" value="${form.activePerson.id}"/>
            <script type="text/javascript">
               var addUrl = "${phiz:calculateActionURL(pageContext,'/private/person/documents/history/edit')}";
               function doEdit()
               {
                   checkIfOneItem("selectedIds");
                  if (!checkOneSelection("selectedIds", "Выберите одну запись!"))
                     return;

                   var ids = document.getElementsByName("selectedIds");
                   for (var i = 0; i < ids.length; i++)
                   {
                       if (ids[i].checked)
                       {
                           var r = ids[i].parentNode.parentNode;

                           var status = trim(r.cells[6].innerHTML);
                           if (status == 'Действующая')
                               alert('Нельзя редактировать текущие данные');
                           else if (status == 'Удалена')
                               alert('Нельзя редактировать удаленные данные');
                           else
                           {
                               var checkedValue = ids[i].value;
                               window.location = addUrl + "?person=${personId}&identityId=" + encodeURIComponent(checkedValue);
                           }
                           return;
                       }
                   }
               }

                function checkRemoved()
                {
                    if (!checkSelection("selectedIds", "Выберите запись для удаления"))
                        return false;

                    var ids = document.getElementsByName("selectedIds");
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids[i].checked)
                        {
                            var r = ids[i].parentNode.parentNode;

                            var status = trim(r.cells[6].innerHTML);
                            if (status == 'Действующая')
                            {
                                alert('Нельзя удалить текущие данные');
                                return false;
                            }
                            else if (status == 'Удалена')
                            {
                                alert('Нельзя удалить удаленные данные');
                                return false;
                            }
                        }
                    }

                    return true;
                }

            </script>
            <tiles:insert definition="commandButton" flush="false" operation="PersonIdentityHistoryEditOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle" value="identityBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action"
                           value="/private/person/documents/history/edit.do?person=${personId}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="buttons" type="string">
                    <tiles:insert definition="commandButton" flush="false"
                                  operation="PersonIdentityHistoryEditOperation">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandTextKey" value="button.delete"/>
                        <tiles:put name="commandHelpKey" value="button.delete"/>
                        <tiles:put name="bundle" value="identityBundle"/>
                        <tiles:put name="validationFunction">
                            checkRemoved();
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="PersonIdentityHistoryEditOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle" value="identityBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">


                    <sl:collection id="listElement" model="list"  property="data" >

                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem
                                title="Фамилия И О">${listElement.firstName} ${listElement.surName} ${listElement.patrName}</sl:collectionItem>
                        <sl:collectionItem title="Дата рождения">
                            <c:out value="${phiz:сalendarToString(listElement.birthDay)}"/>
                        </sl:collectionItem>

                        <sl:collectionItem title="Документ">
                            <c:if test="${not empty listElement.docType}">
                                <bean:message key="document.type.${listElement.docType}" bundle="personsBundle"/>
                            </c:if>
                            <c:out value=" ${listElement.docSeries} ${listElement.docNumber}"/>
                        </sl:collectionItem>

                        <sl:collectionItem title="Изменено">
                            <c:if test="${not empty listElement.dateChange}">
                                <c:out value="${phiz:сalendarToString(listElement.dateChange)}"/>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="Сотрудник">
                            <c:if test="${not empty listElement.employee}">
                                <c:out value="${phiz:getEmployeeFIO(listElement.employee)}"/>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="Статус">
                            <c:out value="${listElement.status.value}"/>
                        </sl:collectionItem>

                    </sl:collection>

                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">У клиента нет старых идентификационных данных</tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>