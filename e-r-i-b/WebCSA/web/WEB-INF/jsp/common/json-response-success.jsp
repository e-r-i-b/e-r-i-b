<%@ page contentType="application/json;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute ignore="true"/>
{
   "state": "success"
   <c:if test="${not empty timer}">
       ,"timer": ${timer}
   </c:if>
   <c:if test="${not empty token}">
       ,"token": "${token}"
   </c:if>
   <c:if test="${not empty redirect}">
       ,"redirect": "${redirect}"
   </c:if>
   <c:if test="${not empty hidePopups}">
       ,"hidePopups": true
   </c:if>
   <c:if test="${not empty goToStep}">
       ,"goToStep": ${goToStep}
   </c:if>
   <c:if test="${not empty onCloseRedirect}">
       ,"onCloseRedirect": "${onCloseRedirect}"
    </c:if>
    <c:if test="${not empty hideRegistered}">
        ,"hideRegistered": "true"
    </c:if>
}