<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="href" value="${csa:calculateActionURL(pageContext,\"/news/list\")}"/>
<h2>События</h2>
    <div id="MainPageNews">
        <div class="news">
           <span class="newsDate"></span>
           <a href="${href}"></a>
       </div>
    </div>
<a href="${href}">посмотреть все события »</a>

