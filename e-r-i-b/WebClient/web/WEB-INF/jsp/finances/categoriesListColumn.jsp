<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
categoryListId
categoriesList
startIndex
endIndex

--%>
<c:set var="canEdit" value="${phiz:impliesService('EditCategoriesService')}"/>
<c:set var="categoriesSize" value="${fn:length(categoriesList)}"/>
<c:set var="tableId" value=""/>
<c:if test="${endIndex == categoriesSize-1 and startIndex <= endIndex}">
    <c:set var="tableId" value="id='${categoryListId}'"/>
</c:if>
<table class="operationCategoriesListTable" ${tableId}>
    <tbody>
        <c:if test="${startIndex <= endIndex && startIndex >= 0}">
            <c:forEach var="category" items="${categoriesList}" begin="${startIndex}" end="${endIndex}">
                <tr id="category${category.id}">
                    <td width="50%">
                        <input type="hidden" id='categoryName${category.id}' value="<c:out value='${category.name}'/>">
                         <c:choose>
                            <c:when test="${empty category.ownerId || not canEdit}">
                                <c:out value="${category.name}"/>
                            </c:when>
                            <c:otherwise>
                                <span class="word-wrap">
                                    <a href="#" onclick="if(!redirectResolved()) return false; operationCategories.openEditWindow(${category.id}); return false;">
                                        <c:out value="${category.name}"/>
                                    </a>
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td width="50%">
                        <c:choose>
                            <c:when test="${empty category.ownerId}">
                                <span class="italicInfo">назначена автоматически</span>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${canEdit}">
                                    <a href="#" onclick="if(!redirectResolved()) return false;operationCategories.openConfirmRemoveCategoryWindow(${category.id}); return false;">Удалить</a>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </tbody>
</table>
<c:if test="${endIndex == categoriesSize-1 and startIndex <= endIndex}">
    <c:if test="${canEdit}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.category.add"/>
            <tiles:put name="commandHelpKey" value="button.category.add.help"/>
            <tiles:put name="bundle" value="financesBundle"/>
            <tiles:put name="viewType" value="blueGrayLinkDotted"/>
            <tiles:put name="onclick" value="if(!redirectResolved()) return false;operationCategories.openWindow('${categoryListId}');"/>
        </tiles:insert>
    </c:if>
</c:if>