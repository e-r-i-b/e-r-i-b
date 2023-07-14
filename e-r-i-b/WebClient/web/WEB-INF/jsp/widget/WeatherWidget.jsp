<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="regionURL" value="${phiz:calculateActionURL(pageContext,'/dictionaries/cities/list')}"/>
<c:set var="city" value="${phiz:getCityByCode(form.widget.city)}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.WeatherWidget"/>
    <tiles:put name="cssClassname" value="WeatherWidget"/>
    <tiles:put name="borderColor" value="whiteTop"/>
    <tiles:put name="props">
        cityURL: "${regionURL}",
        imageBaseURL: globalUrl + "/commonSkin/images/weather_widget/",
        <c:if test="${not empty city}">
            enCityName: "${city.enName}",
            cityName: "${city.name}",
        </c:if>
        serviceKey: "${form.weatherServiceKey}"
    </tiles:put>

    <%-- Отображение  --%>
    <tiles:put name="viewPanel">
        <div id="waitWeatherWidget${form.codename}">
            <img src="${imagePath}/ajaxLoader.gif" alt="Загрузка..." title="Загрузка..." class="loader"/>
        </div>

        <div id="weatherWidgetBlock${form.codename}" style="display: none;">
            <!-- Текущая погода -->
            <div panel="today" class="todayPane">
                <div class="icon">
                    <img image="icon" src="" border="0"/>
                </div>
                <div class="description">
                    <span label="city"      class="city"><c:if test="${not empty city}"><c:out value="${city.name}"/></c:if></span><br/>
                    <span label="temp"      class="temperature"></span><br/>
                    <span label="condition" class="condition"></span><br/>
                    <span label="wind"      class="condition"></span><br/>
                    <span label="humidity"  class="condition"></span><br/>
                </div>
            </div>

            <!-- Прогноз -->
            <div class="forecastPane">
                <c:set var="forecast">
                    <span label="day"  class="condition"></span><br/>
                    <span label="low"  class="condition"></span>&nbsp;
                    <span label="high" class="condition"></span><br/>
                    <div class="icon">
                        <img image="icon" src="" border="0" alt=""/>
                    </div>
                </c:set>
                <div panel="forecast" class="description">
                    ${forecast}
                </div>
                <div panel="forecast" class="description">
                    ${forecast}
                </div>
                <div panel="forecast" class="description">
                    ${forecast}
                </div>
                <div panel="forecast" class="description">
                    ${forecast}
                </div>
            </div>
        </div>
    </tiles:put>

    <%-- Настройки  --%>
    <tiles:put name="editPanel">
        <%-- Та часть виджета, которая будет скроллиться в режиме редактирования--%>
        <%-- Название --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Название:</tiles:put>
            <tiles:put name="data"><input type="text" field="title" size="12" maxlength="20"/></tiles:put>
        </tiles:insert>

        <%-- Город --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Город:</tiles:put>
            <tiles:put name="data">
                <input type="hidden" id="enCityName${form.codename}">
                <input type="hidden" id="cityCode${form.codename}">
                <span field="city" class="regionUserSelect" id="cityName${form.codename}"></span>
            </tiles:put>
        </tiles:insert>

        <%-- Размер виджета --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title"><span class="paymentLabelVertical"> Вид:</span></tiles:put>
            <tiles:put name="data">
                <select field="size">
                    <option value="compact">Компактный</option>
                    <option value="wide">Полный</option>
                </select>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="${form.codename}"/>
            <tiles:put name="loadAjaxUrl" value="${regionURL}"/>
            <tiles:put name="styleClass" value="selectCityWindow"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
