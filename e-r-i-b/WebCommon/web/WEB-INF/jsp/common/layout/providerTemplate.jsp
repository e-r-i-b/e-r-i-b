<%--
  Created by IntelliJ IDEA.
  User: lepihina
  Date: 10.11.2011
  Time: 9:39:15
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%--
    Шаблон для отображения поставщика услуг при проведении платежа "перевод организации"
    providerName   - название поставщика
    KPP   - КПП
    INN - ИНН
    account - расчетный счет
    region  - "основной регион"
    otherRegions  - остальные регионы
    service  - список услуг
    image   -  иконка потсавщика
    action   -  ссылка
    id - id поставщика
    idForDiv - для добавления дивам
    zIndFunc - функция, для установки z-index
    serviceName - название биллинговой услуги
--%>

<tiles:importAttribute/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:if test="${empty idForDiv}"><c:set var="idForDiv" value="${id}"/> </c:if>
<div id="provider${idForDiv}" class="provider">
    <table>
        <tbody>
            <tr>
                <td class="providerIcon">
                    <div class="icon">
                        <c:choose>
                            <c:when test="${empty action}">
                                <a href="#" onclick="next(${id}); return false;">
                                    <img src="${image}" border="0"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${action}"><img src="${image}" border="0"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </td>
                <td class="providerInfo">
                    <c:choose>
                        <c:when test="${empty action}">
                            <a class="orangeText" href="#" onclick="next(${id}); return false;">
                                <span class="providerTitle word-wrap">${providerName}</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                           <a class="orangeText" href="${action}"><span class="providerTitle word-wrap">${providerName}</span></a>
                        </c:otherwise>
                    </c:choose>
                    <div class="providerRequisites">
                        <c:if test="${not empty serviceName}"><span class="bold">Услуга:</span> ${serviceName}<br/></c:if>
                        <c:if test="${not empty KPP}"><span class="bold">КПП:</span> ${KPP}<br/></c:if>
                        <c:if test="${not empty INN}"><span class="bold">ИНН:</span> ${INN}<br/></c:if>
                        <c:if test="${not empty account}"><span class="bold">р/сч:</span> ${account}<br/></c:if>
                    </div>
                    <c:if test="${not empty region}">
                        <c:set var="providerRegionClick" value=""/>
                        <c:if test="${not empty otherRegions}">
                            <c:set var="providerRegionClick" value="providerRegionClick"/>
                        </c:if>
                        <div id="region${idForDiv}" class="providerRegion ${providerRegionClick}" onclick="hideOrShowRegions('${idForDiv}');">
                            <ul>
                                ${region}<c:if test="${not empty otherRegions}">, ...</c:if>
                            </ul>
                        </div>
                    </c:if>
                    <c:if test="${not empty otherRegions}">
                        <div id="otherRegions${idForDiv}" style="display: none;" class="otherRegions">
                            <div class="otherRegionsHeader"></div>
                            <div class="otherRegionsBlock">
                                <span class="otherRegionsTitle">Этот получатель предоставляет свои услуги также в:</span>
                                <span class="otherRegionsText">
                                     ${otherRegions}
                                </span>
                                <div class="closeOtherRegions"><img src="${imagePath}/colorClose.gif" alt="Закрыть" title="Закрыть" border="0" onclick="hideOrShow('otherRegions${idForDiv}',true)"/></div>
                            </div>
                        </div>
                    </c:if>
                    <!--[if lte IE 7]>
                        <script type="text/javascript">
                            if(window.isClientApp != undefined)
                                ${zIndFunc}("${idForDiv}");
                       </script>
                    <![endif]-->
                    <span class="word-wrap">${service}</span>
                </td>
            </tr>
        </tbody>
    </table>
</div>
