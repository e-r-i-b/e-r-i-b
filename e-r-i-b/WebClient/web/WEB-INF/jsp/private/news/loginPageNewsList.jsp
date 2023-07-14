<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="newsList">
    <tiles:put name="data" type="string">
        <html:form action="/news/list" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">

        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <ul id="breadcrumbs" class="word-wrap">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Вход на личную страницу"/>
                <tiles:put name="action" value="/login"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="События"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </ul>
        <div class="clear"></div>
        <div id="news">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="title" value="Cобытия Сбербанка России"/>
                <tiles:put name="data">
                    <div class="header-container">
                        <table>
                            <tr>
                                <td><img src="${globalImagePath}/icon_news.png" alt=""/></td>
                                <td>
                                    <div class="description">
                                        На этой странице Вы можете посмотреть последние новости банка. Для того чтобы найти нужную новость, воспользуйтесь расширенным фильтром.
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div class="clear"></div>
                    </div>
                    <%-- Фильтр --%>

                    <c:set var="periodName" value="Date"/>
                    <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type${periodName})"/></c:set>

                    <tiles:insert definition="filterDataPeriod" flush="false">
                        <tiles:put name="week" value="false"/>
                        <tiles:put name="month" value="false"/>
                        <tiles:put name="name" value="${periodName}"/>
                        <tiles:put name="buttonKey" value="button.filter"/>
                        <tiles:put name="buttonBundle" value="accountInfoBundle"/>
                        <tiles:put name="needErrorValidate" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="filter" flush="false">
                        <tiles:put name="hiddenData">
                            <div>
                                <html:checkbox property="filter(important)"/>
                                <span>искать среди важных новостей</span>
                            </div>
                            <html:text property="filter(search)" styleClass="news-search-string customPlaceholder" title="введите слова для поиска"/>

                        </tiles:put>
                        <tiles:put name="hideFilterButton" value="true"/>
                        <tiles:put name="buttonKey" value="button.filter"/>
                        <tiles:put name="buttonBundle" value="newsBundle"/>
                    </tiles:insert>

                    <%-- /Фильтр --%>
                    <div class="clear"></div>
                    <div id="news-line">
                        <sl:collection id="entity" model="simple-pagination" selectBean="form" property="data">
                            <sl:collectionItem>
                                <c:set var="newsDate" value="${entity.newsDate}"/>
                                <c:choose>
                                    <c:when test="${entity.important == 'HIGH'}">
                                        <div class="important">
                                        <div class="news-container-important">
                                        <div class="news-header bold">
                                        ${phiz:formatDateDependsOnSysDate(newsDate, true, true)}
                                    </c:when>
                                    <c:otherwise>
                                        <div class="news-container">
                                        <div class="news-header">
                                        <span class="news-date">
                                            ${phiz:formatDateDependsOnSysDate(newsDate, true, true)}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                                <a href="${phiz:calculateActionURL(pageContext,"/news/view.do")}?id=${entity.id}" class="news-title">
                                    <span class="word-wrap">
                                        <bean:write name="entity" property="title" ignore="true"/>
                                    </span>
                                </a>
                                </div>

                                <p class="shortText">
                                    <span class="word-wrap">
                                        ${phiz:processBBCode(entity.shortText)}
                                    </span>
                                </p>
                                </div>
                                <c:if test="${entity.important == 'HIGH'}">
                                    </div>
                                </c:if>
                            </sl:collectionItem>
                        </sl:collection>
                    </div>
                    <c:if test="${empty form.data}">
                        <div class="emptyText">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="greenBold"/>
                                <tiles:put name="data">
                                    Не найдено данных, удовлетворяющих заданным параметрам поиска.
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:if>
                    <div class="clear"></div>

                </tiles:put>
            </tiles:insert>
        </div>
    </html:form>
 </tiles:put>
</tiles:insert>


