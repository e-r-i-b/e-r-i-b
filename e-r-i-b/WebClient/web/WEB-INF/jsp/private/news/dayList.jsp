<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<tiles:importAttribute/>
<html:form action="/private/news/dayList">
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="news">

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                $(document).ready(function()
                {
                    if(isIE())
                    {
                       $('p.shortText').css("letter-spacing","-1px");
                    }
                });
            </script>
            <div id="news" class="list-news">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Cобытия дня"/>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${globalImagePath}/icon_news.png"/>
                            <tiles:put name="description">
                                <h3>
                                    На этой странице Вы можете просмотреть события текущего дня. Чтобы найти нужное событие, воспользуйтесь расширенным поиском.
                                </h3>
                            </tiles:put>
                        </tiles:insert>

                        <%-- Фильтр --%>
                        <tiles:insert definition="filterDay" flush="false">
                            <tiles:put name="name" value="Date"/>
                            <tiles:put name="title" value="События"/>
                            <tiles:put name="buttonKey" value="button.filter"/>
                            <tiles:put name="buttonBundle" value="newsBundle"/>
                            <tiles:put name="needErrorValidate" value="false"/>
                        </tiles:insert>
                        <tiles:insert definition="filter" flush="false">
                            <tiles:put name="hiddenData">
                                <div class="align-left">
                                    <html:checkbox property="filter(important)"/>
                                    <span>искать среди важных событий</span>
                                </div>
                                <html:text property="filter(search)" styleClass="news-search-string customPlaceholder" title="введите слова для поиска"/>
                            </tiles:put>

                            <tiles:put name="hideFilterButton" value="true"/>
                            <tiles:put name="buttonKey" value="button.filter"/>
                            <tiles:put name="buttonBundle" value="newsBundle"/>
                        </tiles:insert>
                        <%-- /Фильтр --%>

                        <div id="news-line">
                            <sl:collection id="entity" model="simple-pagination" selectBean="form" property="data">
                                <sl:collectionItem>
                                    <c:set var="newsDate" value="${entity.newsDate}"/>
                                    <c:set var="className" value=""/>
                                    <c:if test="${entity.important == 'HIGH'}">
                                       <c:set var="className" value="bold"/>
                                    </c:if>
                                    <div class="news-container">
                                        <div class="news-header">
                                            <div class="news-date ${className}">${phiz:formatDateDependsOnSysDate(newsDate, true, true)}</div>
                                            <div class="news-title ${className} word-wrap whole-words">
                                                <a href="${phiz:calculateActionURL(pageContext,"/private/news/view.do")}?id=${entity.id}" class="news-title">
                                                    <span class="word-wrap"><bean:write name="entity" property="title" ignore="true"/></span>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="shortText ${className}">
                                            <span class="word-wrap whole-words">
                                               ${phiz:processBBCode(entity.shortText)}
                                            </span>
                                        </div>
                                    </div>
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
        </tiles:put>
    </tiles:insert>
</html:form>
