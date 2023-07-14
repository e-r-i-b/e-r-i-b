<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="pages" value="${form.pages}"/>
<c:if test="${not empty pages && form.pages != null}">
    <c:forEach items="${pages}" var="page">
        <tr id="${page.id}">
            <td width="20px">

                <input type="checkbox" name="selectedPages" value="${page.id}" class="pageCheckbox"/>
                <input type="hidden" name="selectedIds" value="${page.id}" style="border:none;"/>
                </td>
            <td>
                <span id="${page.id}_id_name">
                ${page.name}
                </span>
            </td>
        </tr>
    </c:forEach>
</c:if>
<tr id="lastTableElement" style="display:none;">
    <td></td>
    <td></td>
</tr>