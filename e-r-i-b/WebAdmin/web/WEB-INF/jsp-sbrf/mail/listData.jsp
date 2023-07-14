<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="isMultiBlockMode" value="${phiz:isMailMultiBlockMode()}"/>
<c:set var="currentNode" value="${phiz:getCurrentNode()}"/>

<c:set var="changeNodeURL" value="${phiz:calculateActionURL(pageContext, '/nodes/change?')}"/>
<c:set var="viewAction" value="${'/mail/manage'}"/>

<c:set var="isERKC" value="${phiz:impliesOperation('ViewMailOperation', 'MailManagementUseClientForm') and
                             not phiz:impliesOperation('ViewMailOperation', 'MailManagment')}"/>
<c:if test="${isERKC}">
    <c:set var="viewAction" value="/erkc${viewAction}"/>
    <c:set var="changeNodeURL" value="${phiz:calculateActionURL(pageContext, '/erkc/changeNode?functional=incomingMailView')}"/>
</c:if>

<c:set var="viewActionURL" value="${phiz:calculateActionURL(pageContext, viewAction)}?"/>
<c:if test="${form.fromQuestionary}">
    <c:set var="viewActionURL" value="${viewActionURL}fromQuestionary=true"/>
</c:if>

<c:set var="contextName" value="/${phiz:loginContextName()}"/>
<c:set var="emptyClientNodeMessage"><bean:message key="label.list.client.node.notAvailable" bundle="mailBundle"/></c:set>
<c:set var="emptyMailNodeMessage"><bean:message key="label.list.mail.node.notAvailable" bundle="mailBundle"/></c:set>

<script type="text/javascript">
    function validateNode(mailId, errorMessage)
    {
        if ($('#adminAvailable' + mailId).val() == 'true')
            return true;

        alert(errorMessage);
        return false;
    }

    function goToMail(mailId, nodeId, action)
    {
        if (nodeId == '${currentNode.id}')
        {
            goTo('${viewActionURL}&mailId=' + mailId + "&action=" + action);
            return true;
        }

        if (!validateNode(mailId, '${emptyMailNodeMessage}'))
            return false;

        var url = '${changeNodeURL}&action=${viewAction}.do&nodeId=' + nodeId;
        url += getParameter('mailId', mailId.toString());
        url += getParameter('action', action);
        <c:if test="${form.fromQuestionary}">
            url += getParameter('fromQuestionary', 'true');
        </c:if>
        goTo(url);
        return true;
    }

    function goToSelectedMail(mode)
    {
        checkIfOneItem("selectedIds");
        if (!checkSelection("selectedIds", "Выберите письмо") || !checkOneSelection("selectedIds", "Выберите одно письмо"))
             return;

        var selectedInput = $("[name=selectedIds]:checked");
        var mailId = selectedInput.val();
        var nodeId = selectedInput.parents('tr:first').find("[name=nodeId" + mailId + "]").val();

        goToMail(mailId, nodeId, mode);
    }

    function goToView()
    {
        goToSelectedMail('view');
    }

    function goToReply()
    {
        goToSelectedMail('reply');
    }

    function getParameter(name, value)
    {
        if (isEmpty(value))
            return '';

        var parameterName = "parameters(" + name + ")";
        return "&" + parameterName + "=" + value.replace(/#/g, '%23');
    }

    function getFilterParameter(name)
    {
        return getParameter("filter(" + name + ")", $('[name=filter(' + name + ')]').val());
    }

    function doRemove()
    {
        checkIfOneItem("selectedIds");
        if (!checkSelection("selectedIds", "Выберите письмо для удаления") || !checkOneSelection("selectedIds", "Выберите одно письмо для удаления"))
            return false;

        var selectedInput = $("[name=selectedIds]:checked");
        var mailId = selectedInput.val();
        var nodeId = selectedInput.parents('tr:first').find("[name=nodeId" + mailId + "]").val();
        var state = getElementValue("field(received)"+mailId);
        if (state == 'NEW')
        {
            clearLoadMessage();
            groupError("Вы можете удалять только прочитанные письма. Сначала ознакомьтесь с содержанием письма");
            return false;
        }

        if (!confirm('<bean:message key="confirm.text" bundle="mailBundle"/>'))
            return false;

        var operationName = 'button.remove';
        if (nodeId == '' || nodeId == '${currentNode.id}')
        {
            createCommandButton(operationName, 'button.remove').click('', false);
            return true;
        }

        if (!validateNode(mailId, '${emptyMailNodeMessage}'))
            return false;

        var url = '${changeNodeURL}&action=' + window.location.pathname.substring('${contextName}'.length) + '&nodeId=' + nodeId;
        url += getParameter("operation", operationName);

        <c:if test="${form.fromQuestionary}">
            url += getParameter("fromQuestionary", true);
        </c:if>

        $('[name^=$$order_parameter_]').each(function(index, element){
            url += getParameter(element.name, element.value);
        });

        url += getParameter("$$pagination_size0", $('[name=$$pagination_size0]').val());
        url += getParameter("$$pagination_offset0", $('[name=$$pagination_offset0]').val());

        url += getParameter("selectedIds", mailId);
        url += getFilterParameter('employeeLogin');
        url += getFilterParameter('firstName');
        url += getFilterParameter('firstNameEmpl');
        url += getFilterParameter('fromDate');
        url += getFilterParameter('isAttach');
        url += getFilterParameter('num');
        url += getFilterParameter('patrName');
        url += getFilterParameter('patrNameEmpl');
        url += getFilterParameter('response_method');
        url += getFilterParameter('showDraft');
        url += getFilterParameter('showNew');
        url += getFilterParameter('showReceived');
        url += getFilterParameter('subject');
        url += getFilterParameter('surName');
        url += getFilterParameter('surNameEmpl');
        url += getFilterParameter('toDate');
        url += getFilterParameter('type');
        url += getFilterParameter('area_id');
        url += getFilterParameter('theme');
        url += getFilterParameter('user_TB');

        goTo(url);
        return true;
    }
 </script>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="MailList"/>
    <tiles:put name="text">
        <bean:message key="receivedList.title" bundle="mailBundle"/>
    </tiles:put>
    <html:hidden name="form" property="fromQuestionary" styleId="fromQuestionary"/>

    <tiles:put name="buttons">
        <c:choose>
            <c:when test="${isERKC}">
                <tiles:insert definition="clientButton" flush="false" operation="ViewMailOperation" service="MailManagementUseClientForm">
                    <tiles:put name="commandTextKey" value="button.view"/>
                    <tiles:put name="commandHelpKey" value="button.view.help"/>
                    <tiles:put name="bundle"         value="mailBundle"/>
                    <tiles:put name="onclick"        value="goToView();"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false" operation="ViewMailOperation" service="MailManagment">
                    <tiles:put name="commandTextKey" value="button.view"/>
                    <tiles:put name="commandHelpKey" value="button.view.help"/>
                    <tiles:put name="bundle"         value="mailBundle"/>
                    <tiles:put name="onclick"        value="goToView();" />
                </tiles:insert>
            </c:otherwise>
        </c:choose>
        <tiles:insert definition="clientButton" flush="false" operation="EditMailOperation" service="MailManagment">
            <tiles:put name="commandTextKey"        value="button.reply"/>
            <tiles:put name="commandHelpKey"        value="button.reply.help"/>
            <tiles:put name="bundle"                value="mailBundle"/>
            <tiles:put name="onclick"               value="goToReply();" />
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false" operation="RemoveMailOperation" service="MailManagment">
            <tiles:put name="commandTextKey" value="button.remove"/>
            <tiles:put name="commandHelpKey" value="button.remove.help"/>
            <tiles:put name="bundle"         value="mailBundle"/>
            <tiles:put name="onclick"        value="doRemove();"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="grid">
        <sl:collection id="incomeMail" model="list" property="data" bundle="mailBundle" collectionSize="${phiz:getPaginationSize(form.fields.gridId)}">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <c:set var="mailId"     value="${incomeMail.id}"/>
            <c:set var="mailNodeId" value="${incomeMail.nodeId}"/>

            <c:if test="${not isMultiBlockMode}">
                <c:set var="mailNodeId" value="${currentNode.id}"/>
            </c:if>

            <sl:collectionItem title="label.number" name="incomeMail" property="number" sortProperty="mNum"/>
            <sl:collectionItem title="label.receivedDate" sortProperty="mCreationDate">
                <fmt:formatDate value="${incomeMail.creationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.status" sortProperty="mailStateDescription">
                <html:hidden property="field(received)${mailId}" value="${incomeMail.state}"/>
                <html:hidden property="field(nodeId${mailId})" value="${mailNodeId}"/>
                <html:hidden property="nodeId${mailId}" value="${mailNodeId}"/>

                <c:if test="${isMultiBlockMode}">
                    <c:set var="mailNodeInfo" value="${phiz:getNodeInfo(mailNodeId)}"/>
                    <input type="hidden" id="adminAvailable${mailId}" value="${mailNodeInfo.adminAvailable}"/>
                </c:if>

                <c:choose>
                    <c:when test="${mailState == 'DRAFT'}">
                        <a href="#" onclick="goToMail(${mailId}, '${mailNodeId}', 'view');">
                            <c:out value="${incomeMail.stateDescription}"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${incomeMail.stateDescription}"/>
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.mailType"   property="typeDescription" sortProperty="mailTypeDescription"/>
            <sl:collectionItem title="label.mail.theme" property="theme"           sortProperty="msDescription"/>
            <sl:collectionItem title="label.subject"                               sortProperty="mSubject">
                <a href="#" onclick="goToMail(${mailId}, '${mailNodeId}', 'view');">
                    <c:out value="${incomeMail.subject}"/>
                </a>
            </sl:collectionItem>
            <sl:collectionItem title="label.sender" sortProperty="User_FIO">
                <c:set var="mailSenderId" value="${incomeMail.senderId}"/>
                <c:set var="mailSenderFIO" value="${incomeMail.senderFIO}"/>
                <c:if test="${not empty mailSenderId}">
                    <c:choose>
                        <c:when test="${currentNode.id eq mailNodeId}">
                            <phiz:link action="/persons/edit" operationClass="ViewPersonOperation">
                                <phiz:param name="person" value="${mailSenderId}"/>
                                <c:out value="${mailSenderFIO}"/>
                            </phiz:link>
                        </c:when>
                        <c:otherwise>
                            <phiz:link action="/nodes/change" onclick="return validateNode('${mailId}', '${emptyClientNodeMessage}');" operationClass="ViewPersonOperation">
                                <phiz:param name="parameters(person)" value="${mailSenderId}"/>
                                <phiz:param name="action" value="/persons/edit.do"/>
                                <phiz:param name="nodeId" value="${mailNodeId}"/>
                                <c:out value="${mailSenderFIO}"/>
                            </phiz:link>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.FIO"           property="employeeFIO"    sortProperty="Employee_FIO"/>
            <sl:collectionItem title="label.login"         property="employeeUserId" sortProperty="lUserId"/>
            <sl:collectionItem title="response_method"     property="responseMethod" sortProperty="mResponseMethodDescription"/>
            <sl:collectionItem title="label.person.TB"     property="tb"/>
            <sl:collectionItem title="label.employee.area" property="area"/>
        </sl:collection>
    </tiles:put>
</tiles:insert>