<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/departments/replication/task/list">
    <tiles:insert definition="departmentsMain">
       <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="pageTitle">
            <bean:message bundle="departmentsBundle" key="background.replication.list.title"/>
        </tiles:put>

        <tiles:put name="submenu" value="ListBackgroundTasks"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="BackgroundDepartmentsReplicationTable"/>
                <tiles:put name="grid">
                    <sl:collection id="task" model="list" property="data" selectBean="task" selectType="checkbox" selectName="selectedIds" selectProperty="id">

                        <sl:collectionItem title="Номер" value="${task.id}" action="/departments/replication/task/view.do?id=${task.id}"/>
                        <sl:collectionItem title="Тип задачи">
                            <c:if test="${not empty task}">
                                <bean:message key="background.replication.list.label.mode.${task.replicationMode}" bundle="departmentsBundle"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="Дата создания">
                            <fmt:formatDate value="${task.creationDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ФИО сотрудника">
                            ${task.ownerFIO}
                        </sl:collectionItem>
                        <sl:collectionItem title="Статус">
                            <c:if test="${not empty task}">
                                <bean:message key="background.replication.label.status.${task.state}" bundle="departmentsBundle"/>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" service="DepartmentsManagement">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="blockingRulesBundle"/>
                        <tiles:put name="validationFunction">
                            checkSelection('selectedIds', 'Выберите хотябы одну запись для удаления');
                        </tiles:put>
                        <tiles:put name="confirmText" value="Вы действительно хотите удалить выбранные фоновые задачи?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="emptyMessage">
                    <bean:message bundle="departmentsBundle" key="background.replication.list.empty.message"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
