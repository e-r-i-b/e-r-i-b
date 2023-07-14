<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>


<c:forEach items="${form.depositEntitySubTypes}" var="entitySubType">
    <c:set var="subType" value="${entitySubType.subType}"/>
    <tr>
        <td>
            <c:out value="${subType}"/>
        </td>
        <td>
            <div style="margin-left:20px;">
                "<c:out value="${entitySubType.name}"/>"
                <c:choose>
                    <c:when test="${not empty entitySubType.period}">на срок <c:out value="${fn:toLowerCase(phiz:preparePeriod(entitySubType.period))}"/></c:when>
                    <c:otherwise>бессрочный</c:otherwise>
                </c:choose>
                по ставке <c:out value="${phiz:formatBigDecimal(entitySubType.rate)}"/>%
            </div>
        </td>
        <td>
            <c:set var="checked" value="${entitySubType.availableOnline}"/>
            <input type="checkbox" ${checked ? "checked" : ""}
                   id="depositSubTypeIds${subType}"
                   name="depositSubTypeIds"
                   value="${subType}"
                   onclick="changeVisibleSubType(this.id);">
            <label id="depositSubTypeIds${subType}Label">
                <c:choose>
                    <c:when test="${checked}"><c:out value=" доступен"/></c:when>
                    <c:otherwise><c:out value=" не доступен"/></c:otherwise>
                </c:choose>
            </label>
        </td>
    </tr>
</c:forEach>

<script type="text/javascript">
    function changeVisibleSubType(elementId)
    {
        if ($("input[id="+elementId+"]").attr("checked") == true)
        {
            $("#"+elementId+"Label").html(' доступен');
        }
        else
        {
            $("#"+elementId+"Label").html(' не доступен');
        }
    }

</script>

