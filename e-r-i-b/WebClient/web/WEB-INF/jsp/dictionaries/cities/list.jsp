<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/dictionaries/cities/list">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cities" value="${frm.data}"/>
    <c:set var="size" value="${fn:length(cities)}"/>
    <c:set var="searchPage" value="${frm.searchPage}"/>
    <c:set var="resOnPage" value="${frm.resOnPage}"/>

    <div id="citiesListWeatherWidget">
        <h1>Выбор города</h1>

        <div class="cityDescription">Выберете город, для которого вы хотите просматривать прогноз погоды</div>

        <div class="cityFilter">
            <html:text property="field(cityName)" styleClass="float" style="width: 350px;"/>

            <div id="search-button">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.search"/>
                    <tiles:put name="commandHelpKey" value="button.search"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="onclick" value="citiesUtils.clickSearch(this); return false;"/>
                    <tiles:put name="isDefault" value="true"/>
                </tiles:insert>
            </div>
            <div class="clear"></div>
        </div>

        <c:if test="${size > 0}">
            <script type="text/javascript">
                $(document).ready(function(){
                   $('.dictionaryCityName').bind('click', function(){
                       var id = $(this).closest('#citiesListWeatherWidget').parent().attr('id');
                       
                       $('#cityName'+id).text($(this).find('a').text());
                       $('#enCityName'+id).val($(this).find('*[name=enCityName]').val());
                       $('#cityCode'+id).val($(this).find('*[name=cityCode]').val());

                       win.close(id);
                       return false;
                   });
                });
            </script>

            <c:forEach var="city" items="${cities}" end="${resOnPage - 1}">
                <div class="dictionaryCityName">
                    <a href="#" onclick="">${city.name}</a>
                    <input type="hidden" name="enCityName" value="${city.enName}">
                    <input type="hidden" name="cityCode" value="${city.code}">
                </div>
            </c:forEach>
            <div class="operationsListPagination">
                <html:hidden property="field(searchPage)" value="${searchPage}"/>
                <div class="align-center">
                    <c:choose>
                        <c:when test="${searchPage > 0}">
                            <a class="active-arrow" href="#" onclick="setElement('field(searchPage)', ${searchPage-1}); citiesUtils.changePage(this); return false;"><div class="activePaginLeftArrow"></div></a>
                        </c:when>
                        <c:otherwise>
                            <div class="inactivePaginLeftArrow"></div>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${size > resOnPage}">
                            <a class="active-arrow" href="#" onclick="setElement('field(searchPage)', ${searchPage+1}); citiesUtils.changePage(this); return false;"><div class="activePaginRightArrow"></div></a>
                        </c:when>
                        <c:otherwise>
                            <div class="inactivePaginRightArrow"></div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="operationsListPaginSize">
                    показывать по:
                    <html:hidden property="field(resOnPage)" value="${resOnPage}"/>
                    <c:choose>
                        <c:when test="${resOnPage == 10}">
                            <span><div class="greenSelector"><span>10</span></div></span>
                            <a onclick="citiesUtils.changeResOnPage(this, 20); return false;" href="#">20</a>
                            <a onclick="citiesUtils.changeResOnPage(this, 50); return false;" href="#">50</a>
                        </c:when>
                        <c:when test="${resOnPage == 20}">
                            <a onclick="citiesUtils.changeResOnPage(this, 10); return false;" href="#">10</a>
                            <div class="greenSelector"><span>20</span></div>
                            <a onclick="citiesUtils.changeResOnPage(this, 50); return false;" href="#">50</a>
                        </c:when>
                        <c:when test="${resOnPage == 50}">
                            <a onclick="citiesUtils.changeResOnPage(this, 10); return false;" href="#">10</a>
                            <a onclick="citiesUtils.changeResOnPage(this, 20); return false;" href="#">20</a>
                            <div class="greenSelector"><span>50</span></div>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </c:if>

        <c:if test="${size == 0}">
            <div class="emptyListText">
                По Вашему запросу ничего не найдено.
            </div>
        </c:if>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close.window"/>
                <tiles:put name="commandHelpKey" value="button.close.window"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="citiesUtils.closeSearch(this); return false;"/>
                <tiles:put name="viewType" value="simpleLink"/>
            </tiles:insert>
        </div>
    </div>
</html:form>