<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<fmt:setLocale value="ru-RU"/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<tr id="target${target.id}" isVeryLast="${target.veryLast}">
    <td>
        <span id="dictionaryTarget${target.id}" class="displayNone">${target.dictionaryTarget}</span>
        <span id="planedDateTarget${target.id}" class="displayNone"><fmt:formatDate value="${target.plannedDate.time}" pattern="MM/dd/yyyy"/></span>
        <c:choose>
            <c:when test="${!empty target.imageId}">
                <c:set var="imageData" value="${phiz:getImageById(target.imageId)}"/>
                <img src="${phiz:getAddressImage(imageData, pageContext)}" class="targetImg">
            </c:when>
            <c:otherwise>
                <img src="${globalImagePath}/pfp/otherIcon.jpg" class="targetImg">
            </c:otherwise>
        </c:choose>
    </td>
    <td>
        <div class="targetNameDiv">
            <span class="word-wrap">
                <span id="targetName${target.id}">
                    <c:out value="${target.name}"/>
                </span>
                <c:if test="${not empty target.nameComment}">
                    <bean:message bundle="pfpBundle" key="label.target.line.dash"/>
                    <span id="targetNameComment${target.id}">
                        <c:out value="${target.nameComment}"/>
                    </span>
                </c:if>
            </span>
        </div>
    </td>
    <td class="alignCenter">
        <c:set var="planedQuarter" value="${phiz:getQuarter(target.plannedDate)}"/>
        <c:set var="planedYear"><fmt:formatDate value="${target.plannedDate.time}" pattern="yyyy"/></c:set>
        <span id="targetPlanedQuarter${target.id}">${planedQuarter}</span>
        <bean:message bundle="pfpBundle" key="label.target.line.quarter"/>
        <span id="targetPlanedYear${target.id}">${planedYear}</span>
    </td>

    <td class="alignRight targetAmountTd">
        <span id="targetAmount${target.id}" class="displayNone">${target.amount.decimal}</span>
        ${phiz:formatAmount(target.amount)}
    </td>

    <c:if test="${showThermometer}">
        <td>
            <div class="pfpTargetThermometer">
                <tiles:insert definition="thermometerTemplate" flush="false">
                    <tiles:put name="id" value="${target.id}"/>
                    <tiles:put name="maxValue" value="100"/>
                    <tiles:put name="value" value="50"/>
                    <tiles:put name="color" value="orange"/>
                    <tiles:put name="showDribbleHint" value="false"/>
                    <tiles:put name="enterScript" value="pfpTarget.showThermometerHint('hint${target.id}', '${target.id}', 'К планируемой дате Вы сможете накопить 50% от стоимости цели.');"/>
                    <tiles:put name="leaveScript" value="hintUtils.closeElementHint('hint${target.id}');"/>
                </tiles:insert>
            </div>
        </td>
    </c:if>

    <td class="icon">
        <a class="editIconButton" title="Редактировать" onclick="pfpTarget.editPersonTarget(${target.id});"></a>
    </td>
    <td class="icon">
        <c:set var="currentTargetName">
            <c:out value="${target.name}"/><c:if test="${not empty target.nameComment}"> <bean:message bundle="pfpBundle" key="label.target.line.dash"/> <c:out value="${target.nameComment}"/></c:if>
        </c:set>
        <img class="iconButton" title="Удалить" alt="X" src="${globalImagePath}/icon_trash.gif" onclick="pfpTarget.removePersonTarget(${target.id},'<c:out value="${currentTargetName}"/>');"/>
    </td>
</tr>