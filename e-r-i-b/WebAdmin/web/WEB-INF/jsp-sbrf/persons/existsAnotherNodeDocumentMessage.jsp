<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"          prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"                       prefix="phiz" %>

<c:set var="clientAnotherNodeInfo" value="${phiz:getClientAnotherNodeInfo()}"/>
<c:if test="${not empty clientAnotherNodeInfo}">
    <table cellpadding="4" width="100%">
        <tbody>
        <tr>
            <td align="center" class="messageTab">
                <c:set var="changeNodeUrl" value="${phiz:calculateActionURL(pageContext, '/erkc/changeNode')}?nodeId="/>
                <bean:message key="label.operation.document.node.another.exists.prefix" bundle="personsBundle"/>
                <c:set var="onclickAction" value="goTo('${changeNodeUrl}${clientAnotherNodeInfo.id}');"/>
                <c:set var="targetNode" value="${phiz:getNodeInfo(clientAnotherNodeInfo.id)}"/>
                <c:if test="${not targetNode.adminAvailable}">
                    <c:set var="onclickAction" value="alert('Блок не доступен.'); return false;"/>
                </c:if>
                <a href="#" onclick="${onclickAction}"><bean:message key="label.operation.document.node.another.exists.href" bundle="personsBundle"/>&nbsp;${clientAnotherNodeInfo.name}</a>.
            </td>
        </tr>
        </tbody>
    </table>
</c:if>
