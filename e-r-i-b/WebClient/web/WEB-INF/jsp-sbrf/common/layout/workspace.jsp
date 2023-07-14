<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 15:53:23
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>


<div id="workspace">
    <c:if test="${not empty breadcrumbs and breadcrumbs ne ''}">
        <ul id="breadcrumbs" class="word-wrap">
            ${breadcrumbs}
        </ul>
        <div class="clear"></div>
    </c:if>

    <%-- Блок "подождите" --%>
    <div id="loading" style="left:-3300px;">
        <div id="loadingImg"><img src="${imagePath}/ajax-loader64.gif"/></div>
        <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
    </div>

    <%-- Сообщения об ошибках --%>
    <c:if test="${needShowMessages == 'true'}">
        <tiles:insert page="messages.jsp">
            <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
            <c:set var="bundleName" value="${messagesBundle}"/>
        </tiles:insert>
        <%-- Конец сообщений об ошибках --%>
        <div class="clear"></div>
    </c:if>

    <%-- Центральная секция --%>
    <tiles:insert attribute="data"/>
    <c:if test="${needSave == 'true'}">
        <script type="text/javascript">$(document).ready(initData);</script>
    </c:if>

    <c:if test="${not empty sessionScope['fromBanner']}">
        <c:set var="fromBanner" value="" scope="session"/>
    </c:if>
</div>




