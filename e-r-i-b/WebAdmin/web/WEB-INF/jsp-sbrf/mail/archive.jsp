<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/mail/archive">
    <tiles:insert definition="mailMain">
        <tiles:put name="submenu" type="string" value="ArhMailList"/>
         <tiles:put name="pageTitle" type="string">
            <bean:message key="list.title" bundle="mailBundle"/>.&nbsp;<bean:message key="removedList.title" bundle="mailBundle"/>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.subject"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="subject"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>

             <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.number"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="num"/>
            </tiles:insert>

             <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.mailType"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(type)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="CONSULTATION"><bean:message key="mailType.CONSULTATION" bundle="mailBundle"/></html:option>
                        <html:option value="COMPLAINT"><bean:message key="mailType.COMPLAINT" bundle="mailBundle"/></html:option>
                        <html:option value="CLAIM"><bean:message key="mailType.CLAIM" bundle="mailBundle"/></html:option>
                        <html:option value="GRATITUDE"><bean:message key="mailType.GRATITUDE" bundle="mailBundle"/></html:option>
                        <html:option value="IMPROVE"><bean:message key="mailType.IMPROVE" bundle="mailBundle"/></html:option>
                        <html:option value="OTHER"><bean:message key="mailType.OTHER" bundle="mailBundle"/></html:option>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>

            <tiles:insert definition="filterDataSpan" flush="false">
                <tiles:put name="label" value="label.date"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="Date"/>
                <tiles:put name="template" value="DATE_TEMPLATE"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.type"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(mailType)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="CLIENT"><bean:message key="directionC" bundle="mailBundle"/></html:option>
                        <html:option value="ADMIN"><bean:message key="directionA" bundle="mailBundle"/></html:option>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.name"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>

            <%-- Логин сотрудника --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.login"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="employeeLogin"/>
                <tiles:put name="maxlength" value="50"/>
                <tiles:put name="size"      value="22"/>
	        </tiles:insert>

            <%-- ФИО Сотрудника --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.surNameEmpl"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="surNameEmpl"/>
                <tiles:put name="maxlength" value="42"/>
	        </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.firstNameEmpl"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="firstNameEmpl"/>
                <tiles:put name="maxlength" value="42"/>
	        </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.patrNameEmpl"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="patrNameEmpl"/>
                <tiles:put name="maxlength" value="42"/>
	        </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="response_method"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(response_method)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="BY_PHONE"><bean:message key="method.by_phone" bundle="mailBundle"/></html:option>
                        <html:option value="IN_WRITING"><bean:message key="method.in_writing" bundle="mailBundle"/></html:option>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.mail.theme"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(theme)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                            <c:forEach items="${phiz:getAllMailSubjects()}" var="theme">
                                <html:option value="${theme.id}">${theme.description}</html:option>
                            </c:forEach>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                 <tiles:put name="label" value="label.person.TB"/>
                 <tiles:put name="bundle" value="mailBundle"/>
                 <tiles:put name="data">
                     <html:select property="filters(user_TB)" styleClass="select">
                         <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                         <c:forEach items="${phiz:getAllowedTB()}" var="department">
                             <html:option value="${department.region}">${department.name}</html:option>
                         </c:forEach>
                 </html:select>
              </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                 <tiles:put name="label" value="label.employee.area"/>
                 <tiles:put name="bundle" value="mailBundle"/>
                 <tiles:put name="data">
                     <html:select property="filters(area_uuid)" styleClass="select">
                         <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                         <c:forEach items="${phiz:getAllowedArea()}" var="area">
                             <html:option value="${area.uuid}">${area.name}</html:option>
                         </c:forEach>
                 </html:select>
              </tiles:put>
            </tiles:insert>

        </tiles:put>
          <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <tiles:insert definition="tableTemplate" flush="false">

                <tiles:put name="buttons">
                    <c:set var="currentNode" value="${phiz:getCurrentNode()}"/>
                    <c:set var="changeNodeURL" value="${phiz:calculateActionURL(pageContext, '/nodes/change')}"/>
                    <c:set var="contextName" value="/${phiz:loginContextName()}"/>
                    <script type="text/javascript">
                        function validateNode(mailId)
                        {
                            if (isEmpty($('[name=field(nodeId' + mailId + ')]').val()))
                            {
                                alert('<bean:message key="label.list.mail.node.notAvailable" bundle="mailBundle"/>');
                                return false;
                            }
                            return true;
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

                        function doRollback()
                        {
                            checkIfOneItem("selectedIds");
                            if (!checkSelection("selectedIds", '<bean:message key="rollback.error.select-one-mail" bundle="mailBundle"/>') || !checkOneSelection("selectedIds", '<bean:message key="rollback.error.select-one-mail" bundle="mailBundle"/>'))
                                return false;

                            var selectedInput = $("[name=selectedIds]:checked");
                            var mailId = selectedInput.val();
                            var nodeId = selectedInput.parents('tr:first').find(".nodeId").val();

                            if (!confirm('<bean:message key="confirmRestore.text" bundle="mailBundle"/>'))
                                return false;

                            var operationName = 'button.rollback';
                            if (nodeId == '' || nodeId == '${currentNode.id}')
                            {
                                createCommandButton(operationName, 'button.remove').click('', false);
                                return true;
                            }

                            if (!validateNode(mailId))
                                return false;

                            var url = '${changeNodeURL}?action=' + window.location.pathname.substring('${contextName}'.length) + '&nodeId=' + nodeId;
                            url += getParameter("operation", operationName);

                            $('[name^=$$order_parameter_]').each(function(index, element){
                                url += getParameter(element.name, element.value);
                            });

                            url += getParameter("$$pagination_size0", $('[name=$$pagination_size0]').val());
                            url += getParameter("$$pagination_offset0", $('[name=$$pagination_offset0]').val());

                            url += getParameter("selectedIds", mailId);

                            url += getFilterParameter('employeeLogin');
                            url += getFilterParameter('firstNameEmpl');
                            url += getFilterParameter('fromDate');
                            url += getFilterParameter('mailType');
                            url += getFilterParameter('name');
                            url += getFilterParameter('num');
                            url += getFilterParameter('patrNameEmpl');
                            url += getFilterParameter('response_method');
                            url += getFilterParameter('subject');
                            url += getFilterParameter('surNameEmpl');
                            url += getFilterParameter('theme');
                            url += getFilterParameter('toDate');
                            url += getFilterParameter('type');
                            url += getFilterParameter('area_id');
                            url += getFilterParameter('user_TB');

                            goTo(url);
                            return true;
                        }
                    </script>
                    <tiles:insert definition="clientButton" flush="false" operation="RemoveMailOperation">
                        <tiles:put name="commandTextKey" value="button.rollback"/>
                        <tiles:put name="commandHelpKey" value="button.rollback.help"/>
                        <tiles:put name="bundle"         value="mailBundle"/>
                        <tiles:put name="onclick"        value="doRollback();"/>
                    </tiles:insert>
                 </tiles:put>

                 <tiles:put name="grid">
                     <sl:collection id="removedMail" model="list" property="data" bundle="mailBundle" selectBean="removedMail" collectionSize="${phiz:getPaginationSize(form.fields.gridId)}">
                         <sl:collectionParam id="selectType" value="checkbox"/>
                         <sl:collectionParam id="selectName" value="selectedIds"/>
                         <sl:collectionParam id="selectProperty" value="id"/>

                         <c:set var="mailNodeId" value=""/>
                         <c:set var="mailNodeInfo" value="${phiz:getNodeInfo(removedMail.nodeId)}"/>
                         <c:if test="${mailNodeInfo.adminAvailable}">
                             <c:set var="mailNodeId" value="${removedMail.nodeId}"/>
                         </c:if>

                         <sl:collectionItem title="label.number"        property="number"           sortProperty="mNum"/>
                         <sl:collectionItem title="label.client"        property="recipientName"    sortProperty="User_FIO"/>
                         <sl:collectionItem title="label.mail.theme"    property="theme"            sortProperty="msDescription"/>
                         <sl:collectionItem title="label.subject"       property="subject" sortProperty="mSubject" filter="true"/>
                         <sl:collectionItem title="label.dateTime" sortProperty="mCreationDate">
                             <input type="hidden" class="nodeId" value="${removedMail.nodeId}"/>
                             <html:hidden property="field(nodeId${removedMail.id})" value="${mailNodeId}"/>
                             <fmt:formatDate value="${removedMail.creationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                         </sl:collectionItem>
                         <sl:collectionItem title="label.mailType"      property="typeDescription"  sortProperty="mailTypeDescription"/>
                         <sl:collectionItem title="label.status"        property="stateDescription" sortProperty="mailStateDescription"/>
                         <sl:collectionItem title="label.type"          property="directionDescription" sortProperty="mailDirectionDescription"/>
                         <sl:collectionItem title="label.FIO"           property="employeeFIO"      sortProperty="Employee_FIO"  filter="true"/>
                         <sl:collectionItem title="label.login"         property="employeeUserId"   sortProperty="lUserId"  filter="true"/>
                         <sl:collectionItem title="response_method"     property="responseMethod"   sortProperty="mResponseMethodDescription"/>
                         <sl:collectionItem title="label.person.TB"     property="tb"/>
                         <sl:collectionItem title="label.employee.area" property="area"/>
                     </sl:collection>
                 </tiles:put>
                <tiles:put name="emptyMessage">
                    <bean:message key="message.empty.arh" bundle="mailBundle"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
 </html:form>