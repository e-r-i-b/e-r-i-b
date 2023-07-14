<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/mail/choose/client" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="personList">
        <tiles:put name="submenu" type="string" value="ClientListFull"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="list.title" bundle="personsBundle"/></tiles:put>
        <tiles:put name="menu" type="string">
            <c:set var="removeMailURL" value="${phiz:calculateActionURL(pageContext, '/private/async/mail/remove')}"/>
            <script type="text/javascript">
                function removeCurrentNewMail()
                {
                    ajaxQuery("id=${form.id}", "${removeMailURL}", function(data){}, null, true);
                    return true;
                }

                function redirectResolved()
                {
                    removeCurrentNewMail();
                    return true;
                }

                function checkClient()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", '<bean:message key="message.choose.client.need.any" bundle="mailBundle"/>') || (!checkOneSelection("selectedIds", '<bean:message key="message.choose.client.need.single" bundle="mailBundle"/>')))
                        return false;

                    var dataContainer = $($('[name=selectedIds]:checked')[0]).parents('tr:first');
                    $('#recipientIdField').val(dataContainer.find('.recipientId').text());
                    $('#recipientNameField').val(dataContainer.find('.recipientName').text());
                    return true;
                }
            </script>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="action" value="/mail/sentList"/>
                <tiles:put name="onclick" value="removeCurrentNewMail();"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <c:if test="${not empty form.data}">
                <table width="100%" cellpadding="4">
                    <tbody>
                    <tr>
                        <td align="center" class="messageTab"><bean:message key="message.choose.client.have.many" bundle="mailBundle"/></td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="personList"/>
                <tiles:put name="text" value="Клиенты"/>
                <tiles:put name="buttons">
                    <c:set var="mailState" value="${form.fields.mailState}"/>
                    <c:choose>
                        <c:when test="${mailState eq 'NEW'}">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.send"/>
                                <tiles:put name="commandTextKey" value="button.send"/>
                                <tiles:put name="commandHelpKey" value="button.send.help"/>
                                <tiles:put name="bundle"         value="mailBundle"/>
                                <tiles:put name="validationFunction" value="checkClient();"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${mailState eq 'EMPLOYEE_DRAFT'}">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.send"/>
                                <tiles:put name="commandTextKey" value="button.save"/>
                                <tiles:put name="commandHelpKey" value="button.save.help"/>
                                <tiles:put name="bundle"         value="mailBundle"/>
                                <tiles:put name="validationFunction" value="checkClient();"/>
                            </tiles:insert>
                        </c:when>
                    </c:choose>
                </tiles:put>
                <tiles:put name="grid">
                    <html:hidden property="id" name="form"/>
                    <html:hidden property="mailRecipientId" name="form" styleId="recipientIdField"/>
                    <html:hidden property="mailRecipientName" name="form" styleId="recipientNameField"/>

                    <sl:collection id="person" model="list" property="data" bundle="personsBundle" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                        <c:set var="login" value="${person.login}"/>
                        <sl:collectionItem title="LOGIN_ID"><span class="recipientId"><c:out value="${login.id}"/></span></sl:collectionItem>
                        <sl:collectionItem title="label.FIO">
                            <span class="recipientName">${person.surName} ${person.firstName} ${person.patrName}</span>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.login" name="login" property="userId"/>
                        <sl:collectionItem title="label.agreementNumber" property="agreementNumber"/>
                        <sl:collectionItem title="label.agreementType"><bean:message key="label.agreementType.${person.creationType}" bundle="personsBundle"/></sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="message.choose.client.have.nothing" bundle="mailBundle"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
