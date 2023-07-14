<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="bundle" value="mobilebankBundle"/>

<tiles:importAttribute/>

<c:if test="${not empty form.smsCommands}">
    <tiles:insert definition="simpleTableTemplate" flush="false">
        <tiles:put name="id" value="detailInfoTable"/>
        <tiles:put name="grid">
            <sl:collection id="command" property="smsCommands" model="list" indexId="index">
                <c:set var="commandName"><c:out value="${command.name}"/></c:set>
                <sl:collectionItem title="Операция"><div><span class="word-wrap">${commandName}</span></div></sl:collectionItem>
                <sl:collectionItem title="Формат запроса"><div><span class="word-wrap">${command.format}</span></div></sl:collectionItem>
                <sl:collectionItem title="${headerExamples}"><div><span class="word-wrap">${command.example}</span></div></sl:collectionItem>
                <c:if test="${not empty deletable}">
                    <sl:collectionItem title="" styleClass="red">
                        <tiles:insert definition="confirmationButton" flush="false">
                            <tiles:put name="winId" value="confirmation_${reg_index}_${index}"/>
                            <tiles:put name="title" value="Подтверждение удаления шаблона"/>
                            <tiles:put name="currentBundle"  value="mobilebankBundle"/>
                            <tiles:put name="confirmCommandKey" value="button.remove"/>
                            <tiles:put name="validationFunction">removeSmsCommand('${command.recipientCode}', '${command.payerCode}');</tiles:put>
                            <tiles:put name="message">Вы действительно хотите удалить шаблон '${commandName}' из списка?</tiles:put>
                        </tiles:insert>
                    </sl:collectionItem>
                </c:if>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</c:if>
