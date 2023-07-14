<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"             prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz" %>

<html:form action="/persons/migration/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="personList">
        <tiles:put name="pageTitle"><bean:message bundle="migrationClientsBundle" key="form.list.title"/></tiles:put>
        <tiles:put name="submenu" value="ListMigrationUsers"/>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="form.list.filter.fields.client"/>
                <tiles:put name="bundle" value="migrationClientsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="255"/>
                <tiles:put name="isDefault"><bean:message bundle="migrationClientsBundle" key="form.list.filter.fields.client.hint"/></tiles:put>
                <tiles:put name="name" value="client"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="form.list.filter.fields.document"/>
                <tiles:put name="bundle" value="migrationClientsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="255"/>
                <tiles:put name="name" value="document"/>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"  value="form.list.filter.fields.department"/>
                <tiles:put name="bundle" value="migrationClientsBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(department)" styleClass="select">
                        <html:option value=""><bean:message key="form.list.filter.fields.department.ALL" bundle="migrationClientsBundle"/></html:option>
                        <c:forEach var="department" items="${form.departments}">
                            <html:option value="${department.region}"><c:out value="${department.name}"/></html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label" value="form.list.filter.fields.birthday"/>
                <tiles:put name="bundle" value="migrationClientsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="birthday"/>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"  value="form.list.filter.fields.agreement.type"/>
                <tiles:put name="bundle" value="migrationClientsBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(agreementType)" styleClass="select">
                        <html:option value=""><bean:message key="form.list.filter.fields.agreement.ALL" bundle="migrationClientsBundle"/></html:option>
                        <c:forEach var="type" items="${form.agreementTypes}">
                            <html:option value="${type}"><bean:message key="form.list.filter.fields.agreement.${type}" bundle="migrationClientsBundle"/></html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="form.list.filter.fields.agreement.number"/>
                <tiles:put name="bundle" value="migrationClientsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="size"   value="10"/>
                <tiles:put name="maxlength"  value="10"/>
                <tiles:put name="name" value="agreementNumber"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="clientsForMigration"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.migrate"/>
                        <tiles:put name="commandTextKey" value="form.list.button.migration"/>
                        <tiles:put name="commandHelpKey" value="form.list.button.migration.hint"/>
                        <tiles:put name="bundle"  value="migrationClientsBundle"/>
                        <tiles:put name="validationFunction" value="doMigration();"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data">
                    <sl:collection id="listElement" model="list" property="data" bundle="migrationClientsBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="form.list.table.fields.client">
                            <sl:collectionItemParam id="value">
                                <phiz:link action="/persons/list/choose" serviceId="PersonManagement">
                                    <phiz:param name="field(firstname)" value="${listElement.firstname}"/>
                                    <phiz:param name="field(surname)"   value="${listElement.surname}"/>
                                    <phiz:param name="field(patrname)"  value="${listElement.patronymic}"/>
                                    <phiz:param name="field(birthDay)"><fmt:formatDate value="${listElement.birthday.time}" pattern="dd.MM.yyyy"/></phiz:param>
                                    <phiz:param name="field(passport)"  value="${listElement.document}"/>
                                    <phiz:param name="field(tb)"        value="${listElement.department}"/>
                                    <c:out value="${listElement.fullName}"/>
                                </phiz:link>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.fields.document" property="document"/>
                        <sl:collectionItem title="form.list.table.fields.department">
                            <sl:collectionItemParam id="value">
                                <c:choose>
                                    <c:when test="${not empty listElement}"><c:out value="${phiz:getDepartmentName(listElement.department, null, null)}"/></c:when>
                                    <c:otherwise>&nbsp;</c:otherwise>
                                </c:choose>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.fields.birthday">
                            <sl:collectionItemParam id="value">
                                <c:choose>
                                    <c:when test="${not empty listElement}"><fmt:formatDate value="${listElement.birthday.time}" pattern="dd.MM.yyyy"/></c:when>
                                    <c:otherwise>&nbsp;</c:otherwise>
                                </c:choose>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.fields.agreement.type">
                            <sl:collectionItemParam id="value"><bean:message key="form.list.filter.fields.agreement.${listElement.agreementType}" bundle="migrationClientsBundle"/></sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.fields.agreement.number" property="agreementNumber"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message bundle="migrationClientsBundle" key="form.list.table.empty.message"/></tiles:put>
            </tiles:insert>
            <script type="text/javascript">
                function doMigration()
                {
                    checkIfOneItem("selectedIds");
                    return checkSelection('selectedIds', '<bean:message bundle="migrationClientsBundle" key="form.list.table.select.any"/>');
                }

                <c:if test="${form.fromStart}">
                    //показываем фильтр при старте
                    switchFilter(this);
                </c:if>
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>