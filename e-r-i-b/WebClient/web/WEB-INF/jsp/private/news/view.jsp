<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/news/view">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="news" value="${form.news}"/>
<c:set var="title" value="${news.title}"/>
<tiles:insert definition="news">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Cобытия"/>
            <tiles:put name="action" value="/private/news/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><span class="word-wrap"><c:out value="${title}"/></span></tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <div id="news" class="view-news">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" ><span class="word-wrap"><c:out value="${title}"/></span></tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${globalImagePath}/icon_news.png"/>
                        <tiles:put name="description">
                            <h3>
                                 На этой странице Вы можете просмотреть текст события и вернуться к списку событий.
                            </h3>
                        </tiles:put>
                    </tiles:insert>
                    <c:if test="${news.important == 'HIGH'}">
                       <c:set var="className" value="bold"/>
                    </c:if>
                    <div class="news-header">
                        <div class="news-date ${className}">${phiz:formatDateDependsOnSysDate(news.newsDate, true, true)}</div>
                        <div class="news-title ${className} word-wrap whole-words"><c:out value="${title}"/></div>
                    </div>
                    <div class="clear"></div>
                    <div class="full ${className}">
                        <span class="word-wrap whole-words">
                           ${phiz:processBBCode(news.text)}
                        </span>
                    </div>
                    <a href="${phiz:calculateActionURL(pageContext,"/private/news/list.do")}" class="to-news-list">&laquo; к списку событий</a>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
</html:form>