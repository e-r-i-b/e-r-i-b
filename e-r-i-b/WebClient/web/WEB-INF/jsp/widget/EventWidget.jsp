<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="news" value="${phiz:getClientNewsCount(form.widget.numberOfShowItems)}"/>
<c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/news/view.do')}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.EventWidget"/>
    <tiles:put name="cssClassname" value="EventWidget"/>
    <tiles:put name="borderColor" value="whiteTop"/>
    <tiles:put name="sizeable" value="true"/>
    <tiles:put name="editable" value="true"/>
    <c:if test="${not empty news}">
        <c:set var="allNewsUrl">${phiz:calculateActionURL(pageContext,"/private/news/list.do")}</c:set>
        <tiles:put name="linksControl">
           <a href="${phiz:calculateActionURL(pageContext, "/private/news/dayList.do")}" title="к списку">события дня</a>
            &nbsp;|
           <a href="${allNewsUrl}" title="к списку">все события</a>
        </tiles:put>
    </c:if>
    <tiles:put name="viewPanel">
         <c:if test="${not empty news}">
            <%@ include file="/WEB-INF/jsp-sbrf/news.jsp"%>
         </c:if>
    </tiles:put>

    <tiles:put name="editPanel">
         <c:if test="${not empty news}">
            <%@ include file="/WEB-INF/jsp-sbrf/news.jsp"%>
         </c:if>
    </tiles:put>
    <tiles:put name="additionalSetting">
        <div class="pagination">
            <h2>Количество записей:</h2>
            <table cellspacing="0" cellpadding="0" class="tblNumRec EventWidget">
                <tr>
                    <td><div button="numberOfItems3"><span class="paginationSize">3</span></div></td>
                    <td><div button="numberOfItems5"><span class="paginationSize">5</span></div></td>
                    <td><div button="numberOfItems10"><span class="paginationSize">10</span></div></td>
                </tr>
            </table>
        </div>
    </tiles:put>
</tiles:insert>