<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:choose>

    <%-- Если есть подключения --%>
    <c:when test="${not empty form.registrations}">
        <input type="hidden" name="cardCode"  value=""/>
        <input type="hidden" name="phoneCode" value=""/>

        <c:forEach var="reg" items="${form.registrations}">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Мобильный банк"/>
                <tiles:put name="data">
                    <div id="mobilebank">
                        <c:set target="${form}" property="registration" value="${reg}"/>
                        <tiles:insert page="registration.jsp" flush="false"/>
                     </div>
                </tiles:put>
            </tiles:insert>
        </c:forEach>
    </c:when>

    <%-- Подключений нет --%>
    <c:otherwise>
        <c:if test="${not form.technoBreak}">
            <tiles:insert page="no-registration.jsp" flush="false"/>
        </c:if>
    </c:otherwise>
</c:choose>