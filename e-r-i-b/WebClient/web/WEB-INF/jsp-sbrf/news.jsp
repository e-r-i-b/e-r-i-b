<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<div id="news">
    <div class="news-list">
        <c:forEach items="${news}" var="entity">
            <c:set var="newsDate" value="${entity.newsDate}"/>
            <c:choose>
                <c:when test="${entity.important == 'HIGH'}">
                    <div class="bold important NextNews">
                        ${phiz:formatDateDependsOnSysDate(newsDate, true, true)}
                        <br/>
                        <a class="sbrfLink newsLinkTitle word-wrap whole-words"
                           href="${phiz:calculateActionURL(pageContext,"/private/news/view")}?id=${entity.id}">
                            <bean:write name="entity" property="title"/>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="plain">
                        <h3 class="bold">${phiz:formatDateDependsOnSysDate(newsDate, true, true)}</h3>
                        <a class="sbrfLink newsLinkTitle word-wrap whole-words"
                           href="${phiz:calculateActionURL(pageContext,"/private/news/view")}?id=${entity.id}">
                            <bean:write name='entity' property="title"/>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <div class="clear"></div>
    </div>
</div>