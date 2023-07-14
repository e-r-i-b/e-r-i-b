<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
Компонент закругленная плашка маркер/ однастрочная

data - данные
color - цвет либо green либо red либо gray
action - если задан то переходить по клику на экшен


--%>

<tiles:importAttribute/>

<c:set var="action" value="${fn:trim(action)}"/>

<c:if test="${action!=''}">
    <c:set var="actionUrl">${phiz:calculateActionURL(pageContext, action)}</c:set>
    <c:set var="action">onclick = "window.location='${actionUrl}';"</c:set>
</c:if>

<span class="roundPlate ${color} ${action!=''?'pointer':''}" ${action}>
    <span class="roundPlateLeft">
       <span class="roundPlateRight">
           <span class="data">
               ${data}
           </span>
       </span>
    </span>
</span>