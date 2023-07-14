<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:form action="/reports/departmetns/list">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="current_level" value="${param.current_level}"/>
<c:set var="level" value="${form.level}"/>

&nbsp;   <%-- странно, но без этого, в IE6 из ajax приходит обрезанная страница (обрезаются вся невидимая пользователю информация до первой видимой части)--%>
<c:forEach var="index" items="${form.allChildrenDepartmetns}">
    <tr class="ListLine0" id="<c:out value="${index[0]}"/>" name="level_<c:out value="${current_level}"/>_<c:out value="${form.id}"/>">
        <td class="listItem" style="width:30px;">
            <c:set value="${current_level}_${index[0]}" var="number_value"/>
             &nbsp;<input value="<c:out value="${number_value}"/>" name="selectedIds"  onclick="checkDepartment(this);"  type="checkbox"/>
        </td>
        <td class="listItem" style="width:100%;padding-left:${current_level * 20 - 10}px;">
            &nbsp;
            <c:choose>
                <c:when test="${index[2] > 0 && current_level < level}">
                   <a href="#" onclick="createNewDiv(<c:out value="${index[0]}"/>, <c:out value="${current_level + 1}"/>);return false;"><c:out value="${index[1]}"/></a>
                </c:when>
                <c:otherwise>
                   <c:out value="${index[1]}"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</c:forEach>

</html:form>