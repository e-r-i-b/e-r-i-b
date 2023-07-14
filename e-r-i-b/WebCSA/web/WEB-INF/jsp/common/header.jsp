<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<tiles:importAttribute/>

<c:set var="regionUrl" value="${csa:calculateActionURL(pageContext,'/dictionaries/regions/list')}"/>
<div id="header">
    <div class="hdrContainer">
        <div class="NewHeader">
            <div class="Logo">
                <c:set var="payOrderBackURL" value="${csa:getPayOrderBackURL()}"/>
                <c:set var="defaultBackURL" value="${csa:calculateActionURL(pageContext, '/index')}"/>
                <c:set var="mainUrl" value="${payOrderBackURL != null ? payOrderBackURL : defaultBackURL}"/>
                <a class="logoImage" href="${mainUrl}">
                    <img src="${skinUrl}/skins/sbrf/images/csa/logoHeader.png" alt="">
                </a>
            </div>

            <div class="PhoneNambers">
                <div class="centerText">
                    <span class="phoneText">Круглосуточная помощь</span><br />
                    <span>+7 (495) <span>500-55-50, </span><span style="display: none;">_</span></span>
                    <span>8 (800) <span>555-55-50</span><span style="display: none;">_</span></span>
                </div>
            </div>

            <div class="UserInfo">

                <c:set var="needRegionSelector" value="${needRegionSelector && $$show_region_functionality}"/>
                <c:if test="${needRegionSelector == 'true'}">
                    <%-- Окно для выбора региона --%>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="regionsDiv"/>
                        <tiles:put name="loadAjaxUrl" value="${regionUrl}"/>
                        <tiles:put name="styleClass" value="regionsDiv"/>
                    </tiles:insert>
                </c:if>

                <c:if test="${needRegionSelector == 'true'}">
                    <div class="region">
                        <div class="regionIco"></div>
                        <c:set var="personRegionName">
                            <c:out value="${sessionScope.session_region.name}"/>
                        </c:set>
                        <span class="regionSelector" onclick="win.open('regionsDiv'); return false;" title="Выберите регион, в котором хотите оплачивать услуги">
                            <span id="regionNameSpan">
                                <c:set var="fullName" value="Все регионы"/>
                                <c:choose>
                                    <%--  Текущий регион --%>
                                    <c:when test="${personRegionName == null}">
                                        Все регионы
                                    </c:when>
                                    <c:otherwise>
                                        ${personRegionName}
                                        <c:set var="fullName" value="${personRegionName}"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </span>
                    </div>
                </c:if>

                <tiles:useAttribute name="showHelpLink"/>
                <c:if test="${showHelpLink}">
                    <script type="text/javascript">
                        <c:set var="helpURL" value="${csa:calculateActionURL(pageContext, '/help')}"/>
                        var additionalHelpId = '';

                        //скриптовое изменение страницы хелпа (когда формы меняются скриптом)
                        function setCurrentHelpId(id)
                        {
                            additionalHelpId = id;
                        }

                        function openCSAHelp()
                        {
                            var helpUrl = '${helpURL}?id=' + '${$$helpId}';
                            if (additionalHelpId != '')
                                helpUrl += '/' + additionalHelpId;
                            return openHelp(helpUrl);
                        }

                        <c:if test="${$$show_region_functionality and needRegionSelector == 'true'}">
                            var REGION_DICTIONARY_URL = '${regionUrl}';
                        </c:if>
                    </script>
                    <a href="#" onclick="openCSAHelp();" title="Открыть справку по системе" alt="Открыть справку по системе">
                        <span>Помощь</span>
                    </a>
                </c:if>
                <a href="http://sberbank.ru" target="_blank" title="Перейти на страницу сайта Сбербанка" alt="Перейти на главную страницу сайта Сбербанка">
                    <span>sberbank.ru</span>
                </a>
            </div>
            <div class="clear"></div>

        </div>
        <div class="clear"></div>
    </div>
</div>