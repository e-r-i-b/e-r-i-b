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
<c:set var="currentNodeId" value="${currentNode.id}"/>

<c:set var="changeNodeURL" value="${phiz:calculateActionURL(pageContext, '/nodes/change?')}"/>
<c:set var="contextName" value="/${phiz:loginContextName()}"/>

<c:set var="isERKC" value="${phiz:impliesOperation('ViewMailOperation', 'MailManagementUseClientForm') and
                             not phiz:impliesOperation('ViewMailOperation', 'MailManagment')}"/>
<c:if test="${isERKC}">
    <c:set var="changeNodeURL" value="${phiz:calculateActionURL(pageContext, '/erkc/changeNode?functional=outgoingMailView')}"/>
</c:if>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="MailList"/>
    <tiles:put name="text"><bean:message key="sentList.title" bundle="mailBundle"/></tiles:put>
    <tiles:put name="buttons">
        <c:choose>
            <c:when test="${phiz:impliesOperation('ViewMailOperation', 'MailManagment')}">
                <tiles:insert definition="clientButton" flush="false" operation="ViewMailOperation" service="MailManagment">
                    <tiles:put name="commandTextKey" value="button.view"/>
                    <tiles:put name="commandHelpKey" value="button.view.help"/>
                    <tiles:put name="bundle"         value="mailBundle"/>
                    <tiles:put name="onclick"        value="goToMailAction('view');"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false" operation="ViewMailOperation" service="MailManagementUseClientForm">
                    <tiles:put name="commandTextKey" value="button.view"/>
                    <tiles:put name="commandHelpKey" value="button.view.help"/>
                    <tiles:put name="bundle"         value="mailBundle"/>
                    <tiles:put name="onclick"        value="goToMailAction('view');"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>

        <c:set var="emptyClientNodeMessage"><bean:message key="label.list.client.node.notAvailable" bundle="mailBundle"/></c:set>
        <c:set var="emptyMailNodeMessage"><bean:message key="label.list.mail.node.notAvailable" bundle="mailBundle"/></c:set>

        <script type="text/javascript">
            var actionsURL = {};
            actionsURL['view'] = {action: '/mail/view', url: '${phiz:calculateActionURL(pageContext, "/mail/view")}'};
            actionsURL['edit'] = {action: '/mail/edit', url: '${phiz:calculateActionURL(pageContext, "/mail/edit")}'};

            function validateNode(mailId, errorMessage)
            {
                if ($('#adminAvailable' + mailId).val() == 'true')
                    return true;

                alert(errorMessage);
                return false;
            }

            function goToMail(mailId, nodeId, action)
            {
                if (nodeId == '${currentNodeId}')
                    return goTo(actionsURL[action].url + '?id=' + mailId);

                if (!validateNode(mailId, '${emptyMailNodeMessage}'))
                    return false;

                return goTo('${changeNodeURL}&action=' + actionsURL[action].action + '.do&nodeId=' + nodeId + "&parameters(id)=" + mailId);
            }

            function goToMailAction(action)
            {
                checkIfOneItem("selectedIds");
                if (!checkSelection("selectedIds", "Выберите письмо") || !checkOneSelection("selectedIds", "Выберите одно письмо"))
                    return;

                var selectedInput = $("[name=selectedIds]:checked");
                var mailId = selectedInput.val();
                var nodeId = selectedInput.parents('tr:first').find("[name=nodeId" + mailId + "]").val();

                goToMail(mailId, nodeId, action);
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

                if (!confirm('<bean:message key="confirm.text" bundle="mailBundle"/>'))
                    return false;

                var operationName = 'button.remove';
                if (nodeId == '${currentNodeId}')
                {
                    createCommandButton(operationName, 'button.remove').click('', false);
                    return true;
                }

                if (!validateNode(mailId, '${emptyMailNodeMessage}'))
                    return false;

                var url = '${changeNodeURL}&action=' + window.location.pathname.substring('${contextName}'.length) + '&nodeId=' + nodeId;
                url += getParameter("operation", operationName);

                $('[name^=$$order_parameter_]').each(function(index, element){
                    url += getParameter(element.name, element.value);
                });

                url += getParameter("$$pagination_size0", $('[name=$$pagination_size0]').val());
                url += getParameter("$$pagination_offset0", $('[name=$$pagination_offset0]').val());

                url += getParameter("selectedIds", mailId);
                url += getFilterParameter('fromDate');
                url += getFilterParameter('toDate');
                url += getFilterParameter('num');
                url += getFilterParameter('surName');
                url += getFilterParameter('firstName');
                url += getFilterParameter('patrName');

                url += getFilterParameter('subject');
                url += getFilterParameter('isAttach');
                url += getFilterParameter('surNameEmpl');
                url += getFilterParameter('firstNameEmpl');
                url += getFilterParameter('patrNameEmpl');

                url += getFilterParameter('login');
                url += getFilterParameter('type');
                url += getFilterParameter('theme');

                url += getFilterParameter('response_method');
                url += getFilterParameter('user_TB');
                url += getFilterParameter('area_id');

                goTo(url);
                return true;
            }

        </script>

        <tiles:insert definition="clientButton" flush="false" operation="RemoveMailOperation" service="MailManagment">
            <tiles:put name="commandTextKey" value="button.remove"/>
            <tiles:put name="commandHelpKey" value="button.remove.help"/>
            <tiles:put name="bundle"         value="mailBundle"/>
            <tiles:put name="onclick"        value="doRemove();"/>
        </tiles:insert>
     </tiles:put>

    <tiles:put name="grid">
        <sl:collection id="outcomeMail" model="list" property="data" bundle="mailBundle" selectBean="outcomeMail" collectionSize="${phiz:getPaginationSize(form.fields.gridId)}">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <c:set var="mailId"     value="${outcomeMail.id}"/>
            <c:set var="mailNodeId" value="${outcomeMail.nodeId}"/>

            <c:if test="${not isMultiBlockMode}">
                <c:set var="mailNodeId" value="${currentNodeId}"/>
            </c:if>

            <sl:collectionItem title="label.number" property="number" sortProperty="mNum"/>
            <sl:collectionItem title="label.sendDate" sortProperty="mCreationDate">
                <fmt:formatDate value="${outcomeMail.creationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.mailType" property="typeDescription" sortProperty="mailTypeDescription"/>

            <sl:collectionItem title="label.subject" sortProperty="mSubject">
                <html:hidden property="nodeId${mailId}" value="${mailNodeId}"/>
                <html:hidden property="field(nodeId${mailId})" value="${mailNodeId}"/>

                <c:if test="${isMultiBlockMode}">
                    <c:set var="mailNodeInfo" value="${phiz:getNodeInfo(mailNodeId)}"/>
                    <input type="hidden" id="adminAvailable${mailId}" value="${mailNodeInfo.adminAvailable}"/>
                </c:if>

                <c:set var="subjectElement"><c:out value="${outcomeMail.subject}"/></c:set>
                <c:choose>
                    <c:when test="${outcomeMail.state == 'EMPLOYEE_DRAFT'}">
                        <c:if test="${phiz:impliesOperation('EditMailOperation', 'MailManagment')}">
                            <c:set var="subjectElement"><a onclick="goToMail(${mailId}, '${mailNodeId}', 'edit');">${subjectElement}</a></c:set>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${phiz:impliesOperation('ViewMailOperation', 'MailManagementUseClientForm') || phiz:impliesOperation('ViewMailOperation', 'MailManagment')}">
                            <c:set var="subjectElement"><a onclick="goToMail(${mailId}, '${mailNodeId}', 'view');">${subjectElement}</a></c:set>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                ${subjectElement}
            </sl:collectionItem>
            <sl:collectionItem title="label.recipient" sortProperty="User_FIO">
                <sl:collectionItemParam id="value">
                    <c:choose>
                        <c:when test="${currentNodeId eq mailNodeId}">
                            <phiz:link action="/persons/edit" operationClass="ViewPersonOperation">
                                <phiz:param name="person" value="${outcomeMail.recipientId}"/>
                                <c:out value="${outcomeMail.recipientFIO}"/>
                            </phiz:link>
                        </c:when>
                        <c:otherwise>
                            <phiz:link action="/nodes/change" onclick="return validateNode('${mailId}', '${emptyClientNodeMessage}');" operationClass="ViewPersonOperation">
                                <phiz:param name="parameters(person)" value="${outcomeMail.recipientId}"/>
                                <phiz:param name="action" value="/persons/edit.do"/>
                                <phiz:param name="nodeId" value="${mailNodeId}"/>
                                <c:out value="${outcomeMail.recipientFIO}"/>
                            </phiz:link>
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItemParam>
            </sl:collectionItem>
            <sl:collectionItem title="label.FIO"           property="employeeFIO"      sortProperty="Employee_FIO"/>
            <sl:collectionItem title="label.login"         property="employeeUserId"   sortProperty="lUserId"/>
            <sl:collectionItem title="label.status"        property="stateDescription" sortProperty="mailStateDescription"/>
            <sl:collectionItem title="label.person.TB"     property="tb"/>
            <sl:collectionItem title="label.employee.area" property="area"/>
        </sl:collection>
    </tiles:put>
    <tiles:put name="emptyMessage">
        <bean:message key="message.empty" bundle="mailBundle"/>
    </tiles:put>
</tiles:insert>
