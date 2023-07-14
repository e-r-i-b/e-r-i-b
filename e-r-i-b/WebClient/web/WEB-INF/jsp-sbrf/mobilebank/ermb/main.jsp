<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/mobilebank/ermb/main" onsubmit="return setEmptyAction(event);">
<tiles:importAttribute/>
    <c:set var="availableNewProfile" value="${phiz:availableNewProfile()}"/>
    <c:choose>
        <c:when test="${availableNewProfile}">
            <tiles:insert definition="newUserProfile">
               <tiles:put name="data" type="string">
                   <tiles:insert definition="profileTemplate" flush="false">
                       <tiles:put name="activeItem">mobilebank</tiles:put>
                       <tiles:put name="data">
                            <tiles:insert page="mainNew.jsp" flush="false"/>
                       </tiles:put>
                   </tiles:insert>
               </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="mobilebank">
                <tiles:put name="menu" type="string"/>
                <tiles:put name="data" type="string">
                    <tiles:insert page="mainNew.jsp" flush="false"/>
                </tiles:put>
            </tiles:insert>
        </c:otherwise>
     </c:choose>
</html:form>