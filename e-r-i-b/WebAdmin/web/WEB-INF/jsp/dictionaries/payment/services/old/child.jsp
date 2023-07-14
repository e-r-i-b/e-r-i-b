<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="id" value="${ListPaymentServicesForm.id}"/>
<c:set var="parentService" value="${phiz:getPaymentServiceOldById(id)}"/>
<c:set var="childrens" value="${phiz:getPaymentServiceChildrensOld(parentService)}"/>
&nbsp;
<c:forEach items="${childrens}" var="paymentService">
    <tr class="tree">
        <td>
            &nbsp;
            <c:set var="children" value="${phiz:getPaymentServiceChildrensOld(paymentService)}"/>
            <input type="checkbox" name="selectedIds" value="${paymentService.id}"  style="border:none;"/>
            <input type="hidden" name="name${paymentService.id}" value="${paymentService.name}"/>
            <c:choose>
                <c:when test="${not empty children}">
                    <a id="parent${paymentService.id}" href="javascript:showChild('${paymentService.id}')">
                        ${paymentService.name} (${paymentService.synchKey})
                    </a>
                </c:when>
                <c:otherwise>
                    ${paymentService.name} (${paymentService.synchKey})
                </c:otherwise>
            </c:choose>
            <table  id="${paymentService.id}" style="display:none; margin-left: 2em;height:auto;">
                <tbody>
                </tbody>
            </table>
        </td>
    </tr>
</c:forEach>