<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<tr id="personLoan${personLoan.id}" class="personLoanLine">
    <td>
        <c:set var="imageData" value="${phiz:getImageById(personLoan.imageId)}"/>
        <c:set var="imgAddress" value="${phiz:getAddressImage(imageData, pageContext)}"/>
        <c:if test="${empty imgAddress}">
            <c:set var="imgAddress" value="${globalImagePath}/pfp/otherIcon.jpg"/>
        </c:if>
        <img src="${imgAddress}" class="targetImg">
    </td>
    <td style="word-break: break-all;">
        <c:set var="currentLoanName">
            ${personLoan.name}<c:if test="${not empty personLoan.comment}"> - <c:out value="${personLoan.comment}"/></c:if>
        </c:set>
        ${currentLoanName}
    </td>
    <td>
        ${pfptags:formatDateWithQuarters(personLoan.startDate)}
    </td>
    <td>
        ${pfptags:formatDateWithQuarters(personLoan.endDate)}
    </td>
    <td class="alignRight">
        <c:set var="quarterPayment" value="${pfptags:calcQuarterPayment(personLoan)}"/>
        <span class="bold">${phiz:formatDecimalToAmount(quarterPayment,2)} руб.</span>
    </td>
    <td class="icon">
        <c:set var="startDateStr" value="${phiz:formatDateToStringWithSlash(personLoan.startDate)}"/>
        <a class="editIconButton" title="Редактировать" onclick="pfpLoan.editPersonLoan(${personLoan.id},${personLoan.dictionaryLoan},'${personLoan.comment}','${personLoan.amount.decimal}',Str2Date('${startDateStr}'),${personLoan.quarterDuration},'${personLoan.rate}')"></a>
    </td>
    <td class="icon">
        <img class="iconButton" title="Удалить" alt="X" src="${globalImagePath}/icon_trash.gif" onclick="pfpLoanList.removePersonLoan(${personLoan.id},'${currentLoanName}')"/>
    </td>
</tr>