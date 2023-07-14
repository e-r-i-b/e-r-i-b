<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<c:set var="form" value="${LoginViewNewsForm}"/>
<c:set var="news" value="${form.news}"/>
<c:set var="title" value="${news.title}"/>
<tiles:insert definition="main" flush="false">
    <tiles:put name="needRegionSelector" value="true"/>
    <tiles:put name="data" type="string">
        <div id="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="${csa:calculateActionURL(pageContext, '/index')}"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="События"/>
                <tiles:put name="action" value="${csa:calculateActionURL(pageContext, '/news/list')}"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </div>
        <div id="CSAnews">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title"><span class="word-wrap"><c:out value="${title}"/></span></tiles:put>
            <tiles:put name="data">
                <div id="news">
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="description">
                            <p>На этой странице Вы можете просмотреть текст события и вернуться к списку событий.</p>
                        </tiles:put>
                    </tiles:insert>
                    <div class="news-header">
                        <c:if test="${news.important == 'HIGH'}">
                            <c:set var="className" value="bold"/>
                        </c:if>
                        <div class="news-title">
                            <span class="word-wrap">
                                <span class="newsDate">${csa:formatDateDependsOnSysDate(news.newsDate, true, true)}&nbsp;</span>
                                <div class="news-title-text ${className}"><c:out value="${title}"/></div>
                            </span>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <div class="full newsWidth">
                        <p class="introText">
                            <span class="word-wrap">
                                ${csa:process(news.text)}
                            </span>
                        </p>
                    </div>
                    <a href="${csa:calculateActionURL(pageContext,"/news/list")}" class="blueGrayLink">&laquo; к списку событий</a>
                    <div class="clear"></div>
                </div>
            </tiles:put>
        </tiles:insert>
        </div>
    </tiles:put>
    <tiles:put name="footer" value="/WEB-INF/jsp/common/copyright.jsp"/>
</tiles:insert>