<%--
  Created by IntelliJ IDEA.
  User: Rydvanskiy
  Date: 27.04.2010
  Time: 12:00:33
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/dictionaries/regions/list">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${frm.saved}">OK</c:if>
    <c:if test="${fn:length(frm.regions) gt 0 and not frm.saved}">
        <c:set var="hasNavigation" value="${frm.navigation != null and not empty frm.navigation}"/>
        <c:set var="personRegion" value="${phiz:getProfileRegion()}"/>
        <c:set var="parentRegion" value="${phiz:getParentRegion(phiz:getProfileRegion())}"/>
        <c:set var="saveRegion" value="${empty frm.saveRegion ? true : frm.saveRegion}"/>

        <div class="regionsAlphabetList">
            <c:if test="${!hasNavigation}">
                <div class="currentRegion">
                    <div class="float"><span>Вы сейчас здесь: </span></div>
                    <div class="currentRegionName">
                        <span>
                            <c:choose>
                                <c:when test="${personRegion.name == null}">
                                    регион не выбран
                                </c:when>
                                <c:otherwise>
                                    ${saveRegion ? personRegion.name : parentRegion.name}
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="clear"></div>
                </div>

                <c:choose>
                    <c:when test="${saveRegion}">
                         <div class="selectRegionHint">
                            Выберите свой регион, и Вам станут доступны местные поставщики коммунальных услуг, сотовой связи и многое другое.
                         </div>
                    </c:when>
                    <c:otherwise>
                        <div class="selectRegionHint">
                            Выберите регион обслуживания:
                         </div>
                    </c:otherwise>
                </c:choose>

            </c:if>

            <tiles:insert definition="alphabet" flush="false">
                <c:if test="${hasNavigation}">
                    <tiles:put name="title" value="Выбор региона"/>
                </c:if>
                <tiles:put name="onClickFunctionName" value="clickRegion.click"/>
                <tiles:put name="onClickFunctionParameters" value="id,name,codeTB"/>
                <tiles:put name="selectFunctionName" value="clickRegion.choose"/>
                <tiles:put name="selectFunctionParameters" value="id,name,codeTB"/>
                <tiles:put name="saveRegion" value="${saveRegion}"/>
                <tiles:put name="data" beanName="frm" beanProperty="regions"/>
                <c:if test="${hasNavigation}">
                    <tiles:put name="navigations" beanName="frm" beanProperty="navigation"/>
                    <tiles:put name="breadTitleAll" value="все регионы"/>
                </c:if>
            </tiles:insert>

            <c:choose>
                <c:when test="${saveRegion}">
                     <div class="selectRegionHint">
                         Если для оплаты Вам необходимы поставщики по всем регионам, выберите
                         <a href="#" onclick="clickRegion.choose(-1, 'Все регионы', '', ${saveRegion}); return false;">все регионы</a>.
                     </div>
                </c:when>
                <c:otherwise>
                    <div class="selectRegionHint">
                         Если Вам необходимы все регионы, выберите
                         <a href="#" onclick="clickRegion.choose(-1, 'Все регионы', '', ${saveRegion}); return false;">все регионы</a>.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</html:form>