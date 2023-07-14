<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>
<%--
 	data - данные
--%>
<tiles:insert definition="iphone" flush="false">
    <tiles:put name="data">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <alfServiceStatus>
            <status>
                <c:choose>
                    <c:when test="${form.financesStatus == 'allOk'}">
                        connected
                    </c:when>
                    <c:otherwise>
                        <bean:write name="form" property="financesStatus" />
                    </c:otherwise>
                </c:choose>
            </status>
            <c:if test="${not empty form.lastModified}">
                <lastModified>
                    <bean:write name="form" property="lastModified.time" format="dd.MM.yyyy"/>
                </lastModified>
            </c:if>
        </alfServiceStatus>
        ${data}
    </tiles:put>
</tiles:insert>    