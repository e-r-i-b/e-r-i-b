<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/finances/categories">
    <div>
        <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            <c:set var="errors">${errors}${error}</c:set>
        </phiz:messages>
        <c:if test="${not empty errors}">
            <div id="messages">
                <c:out value="${errors}"/>
            </div>
        </c:if>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="category" value="${form.category}"/>
        <c:if test="${not empty category}">
            <table>
                <tr id="category${category.id}">
                    <input type="hidden" id='categoryName${category.id}' value="<c:out value='${category.name}'/>">
                    <td width="50%">
                        <span class="word-wrap"><a href="#" onclick="operationCategories.openEditWindow(${category.id}); return false;"><c:out value="${category.name}"/></a></span>
                    </td>
                    <td width="50%">
                        <a href="#" onclick="operationCategories.openConfirmRemoveCategoryWindow(${category.id}); return false;">Удалить</a>
                    </td>
                </tr>
            </table>
        </c:if>
    </div>
</html:form>