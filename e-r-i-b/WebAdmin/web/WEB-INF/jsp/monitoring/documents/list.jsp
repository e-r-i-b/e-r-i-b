<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<html:form action="/monitoring/documents" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${ViewMonitoredDocumentsListForm}"/>

<tiles:insert definition="reports">
	<tiles:put name="submenu" type="string" value="Monitoring"/>
    <tiles:put name="pageTitle"   type="string" value="Просмотр данных мониторинга"/>
    <tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="documentList"/>
            <tiles:put name="text" value="Количество документов в обработке"/>
            <tiles:put name="settingsBeforeInf">
                Подразделение: ${phiz:getDepartmentById(frm.departmentId).name}<br/>
                Время формирования: <fmt:formatDate value="${frm.reportTime.time}" pattern="dd.MM.yyyy HH:mm"/>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data">
                    <c:set var="document" value="${listElement[1]}" scope="request"/>
                    <c:set var="description" value="${listElement[0]}" scope="request"/>
                    <sl:collectionItem title="Номер документа">${document.documentNumber}</sl:collectionItem>
                    <sl:collectionItem title="Тип операции">${description}</sl:collectionItem>
                    <sl:collectionItem title="Сумма и валюта операции">
                        <c:if test="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                            <c:out value="${phiz:formatAmount(document.chargeOffAmount)}"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Время создания">
                        <fmt:formatDate value="${document.dateCreated.time}" pattern="dd.MM.yyyy HH:mm"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="Внешняя система">${document.systemName}</sl:collectionItem>
                </sl:collection>
            </tiles:put>
        </tiles:insert>
    </tiles:put>

</tiles:insert>
</html:form>
