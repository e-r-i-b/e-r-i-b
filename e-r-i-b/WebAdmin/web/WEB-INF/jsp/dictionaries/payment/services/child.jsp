<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="parentId" value="${ListPaymentServicesForm.id}"/>
<c:set var="children" value="${ListPaymentServicesForm.data}"/>
&nbsp;
<c:forEach items="${children}" var="paymentServiceInfo">
    <c:set var="hasChild" value="${paymentServiceInfo[0]}"/>
    <c:set var="paymentService" value="${paymentServiceInfo[1]}"/>

    <tr class="tree">
        <td>
            &nbsp;
            <input type="checkbox" name="selectedIds" value="${paymentService.id}"  style="border:none;"/>
            <input type="hidden" name="name${paymentService.id}" value="${paymentService.name}"/>
            <c:choose>
                <c:when test="${hasChild}">
                    <a id="parent${paymentService.id}" href="javascript:showChild('${paymentService.id}', '${parentId}')">
                        ${paymentService.name} (${paymentService.synchKey})
                    </a>
                </c:when>
                <c:otherwise>
                    ${paymentService.name} (${paymentService.synchKey})
                </c:otherwise>
            </c:choose>
            <table  id="${paymentService.id}_${parentId}" style="display:none; margin-left: 2em;height:auto;">
                <tbody>
                </tbody>
            </table>
        </td>
    </tr>
</c:forEach>