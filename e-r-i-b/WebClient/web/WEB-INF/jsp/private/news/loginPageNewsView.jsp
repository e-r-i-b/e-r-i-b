<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/news/view" >
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="newsList">
<tiles:put name="data" type="string">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="news" value="${form.news}"/>
    <c:set var="title" value="${news.title}"/>
           <ul id="breadcrumbs" class="word-wrap">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="Вход на личную страницу"/>
                    <tiles:put name="action" value="/login"/>
                </tiles:insert>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="Cобытия"/>
                    <tiles:put name="action" value="/news/list"/>
                </tiles:insert>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="${title}"/>
                    <tiles:put name="last" value="true"/>
                </tiles:insert>
            </ul>
            <div class="clear"></div>
            <div id="news">

                <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="color" value="greenTop"/>
                    <tiles:put name="title" ><c:out value="${title}"/></tiles:put>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${globalImagePath}/icon_news.png"/>
                            <tiles:put name="description">
                                <h3>
                                    На этой странице Вы можете просмотреть полный текст новости и вернуться к списку новостей.
                                </h3>
                            </tiles:put>
                        </tiles:insert>
                        <div class="news-header">
                            <div class="news-title">
                                <c:if test="${news.important == 'HIGH'}">
                                    <div class="float"><img src="${imagePath}/icon_importantNews.gif" alt=""/></div>
                                </c:if>
                                <span class="word-wrap"><span class="news-date">${phiz:formatDateDependsOnSysDate(news.newsDate, true, true)}&nbsp;</span><c:out value="${title}"/></span>
                            </div>
                        </div>
                        <div class="clear"></div>
                        <div class="full">
                            <span class="word-wrap">

                                ${phiz:processBBCode(news.text)}

                            </span>
                        </div>
                        <a href="${phiz:calculateActionURL(pageContext,"/news/list.do")}" class="to-news-list">&laquo; Назад к списку новостей</a>
                        <div class="clear"></div>                       
                    </tiles:put>
                </tiles:insert>
            </div>
 </tiles:put>
</tiles:insert>
</html:form>


