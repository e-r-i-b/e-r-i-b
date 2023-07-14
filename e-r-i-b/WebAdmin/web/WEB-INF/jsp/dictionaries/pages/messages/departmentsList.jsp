<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="departments" value="${form.departments}"/>
<c:if test="${not empty departments && form.departments != null}">
    <table>
        <c:forEach items="${departments}" var="department">
            <tr id="${department}_cell_id_name">
                <td>
                    <span id="${department}_id_name">
                        <c:out value="${phiz:getDepartmentName(department, null, null)}"/>
                    </span>
                </td>
                <td width="50px">
                    <html:multibox name="form" property="selectedDepartmentsIds" style="border:none" value="${department}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
