<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="migrationMain">
    <c:choose>
        <c:when test="${vip}">
            <tiles:put name="submenu" type="string" value="VipClients"/>
            <tiles:put name="pageTitle" type="string">
                <bean:message key="migration.conflict.vip.title" bundle="migrationBundle"/>
            </tiles:put>
        </c:when>
        <c:otherwise>
            <tiles:put name="submenu" type="string" value="Clients"/>
            <tiles:put name="pageTitle" type="string">
                <bean:message key="migration.conflict.client.title" bundle="migrationBundle"/>
            </tiles:put>
        </c:otherwise>
    </c:choose>

    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label" value="label.fio"/>
            <tiles:put name="bundle" value="migrationBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="size"   value="50"/>
            <tiles:put name="maxlength"  value="100"/>
            <tiles:put name="isDefault" value="Фамилия, имя и отчество"/>
            <tiles:put name="name" value="fio"/>
        </tiles:insert>

        <tiles:insert definition="filter2TextField" flush="false">
            <tiles:put name="label" value="label.document"/>
            <tiles:put name="bundle" value="migrationBundle"/>
            <tiles:put name="name"   value="docSeries"/>
            <tiles:put name="size"   value="5"/>
            <tiles:put name="maxlength"  value="16"/>
            <tiles:put name="isDefault" value="Серия"/>
            <tiles:put name="name2"   value="docNumber"/>
            <tiles:put name="size2"   value="10"/>
            <tiles:put name="maxlength2"  value="16"/>
            <tiles:put name="default2" value="Номер"/>
        </tiles:insert>

        <tiles:insert definition="filterDateField" flush="false">
            <tiles:put name="label" value="label.birthday"/>
            <tiles:put name="bundle" value="migrationBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="name" value="birthday"/>
        </tiles:insert>

        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.status.type"/>
            <tiles:put name="bundle" value="migrationBundle"/>
            <tiles:put name="data">
                <html:select property="filter(status)" styleClass="select" style="width:150px">
                    <c:forEach var="status" items="${form.statuses}">
                        <html:option value="${status}">
                            <bean:message key="status.${status}" bundle="migrationBundle"/>
                        </html:option>
                    </c:forEach>
                </html:select>
            </tiles:put>
        </tiles:insert>

        <c:if test="${vip}">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.department"/>
                <tiles:put name="bundle" value="migrationBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="maxlength" value="30"/>
                <tiles:put name="isDefault" value="Департамент"/>
                <tiles:put name="name" value="department"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.phone"/>
                <tiles:put name="bundle" value="migrationBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="maxlength" value="20"/>
                <tiles:put name="isDefault" value="Телефон"/>
                <tiles:put name="name" value="phone"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.segment"/>
                <tiles:put name="bundle" value="migrationBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(segment)" styleClass="select">
                        <c:forEach var="segment" items="${form.segments}">
                            <option value="${segment.value}" ${segment.value=='3_1' ? 'selected' : ''}>
                                <c:out value="${segment.value}"/>
                            </option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
        </c:if>
        <html:hidden property="filter(vip)" value="${vip}"/>
    </tiles:put>

    <tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="data">
                <tiles:put name="grid">
                    <sl:collection id="client" model="list" bundle="migrationBundle" property="data">
                        <sl:collectionItem title="label.fio">
                            <c:choose>
                                <c:when test="${phiz:isManuallyResolvable(client) && client.status == 'NOT_MIGRATED'}">
                                    <phiz:link action="/ermb/migration/conflict">
                                        <c:if test="${vip}">
                                            <phiz:param name="vip" value="true"/>
                                        </c:if>
                                        <phiz:param name="id" value="${client.id}"/>
                                        <c:out value="${client.lastName}"/>
                                        <c:out value="${client.firstName}"/>
                                        <c:out value="${client.middleName}"/>
                                    </phiz:link>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${client.lastName}"/>
                                    <c:out value="${client.firstName}"/>
                                    <c:out value="${client.middleName}"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.document">
                            <c:out value="${client.document}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.birthday">
                            <fmt:formatDate value="${client.birthday.time}" pattern="dd.MM.yyyy"/>
                        </sl:collectionItem>
                        <c:if test="${vip}">
                            <sl:collectionItem title="label.department">
                                <c:out value="${client.tb} ${client.vsp} ${client.osb}"/>
                            </sl:collectionItem>
                        </c:if>
                        <c:set var="phones" value="${phiz:findUnresolvedPhones(client)}"/>
                        <sl:collectionItem title="label.phones">
                            <c:forEach items="${phones}" var="phone" end="1">
                                <c:out value="${phone}"/>
                            </c:forEach>
                            <c:if test="${phiz:size(phones) > 2}">
                                <c:out value="..."/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.conflicts">
                            <c:if test="${phiz:isManuallyResolvable(client)}">
                                <input type="checkbox" disabled="true" ${not empty phones?"":"checked"}/>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.data}"/>
            <tiles:put name="emptyMessage">
                <c:choose>
                    <c:when test="${ListClientsMigrationForm.fromStart}">
                        <script type="text/javascript">switchFilter(this);</script>
                        Для поиска клиентов в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                    </c:when>
                    <c:otherwise>
                        Не найдено ни одного клиента, <br/>соответствующего заданному фильтру!
                    </c:otherwise>
                </c:choose>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
