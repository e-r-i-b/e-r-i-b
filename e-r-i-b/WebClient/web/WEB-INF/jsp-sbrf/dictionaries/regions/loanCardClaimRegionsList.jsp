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

<html:form action="/dictionaries/loan-card-claim-regions/list">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${frm.saved}">OK</c:if>
    <c:if test="${fn:length(frm.regions) gt 0 and not frm.saved}">
        <c:set var="hasNavigation" value="${frm.navigation != null and not empty frm.navigation}"/>
        <c:set var="personRegionName" value="${frm.regionName}"/>
        <c:set var="saveRegion" value="${empty frm.saveRegion ? false : frm.saveRegion}"/>
        <div class="regionsAlphabetList">
            <div class="currentSBNKDRegion">
                <div class="float"><span>Мой регион:&nbsp; </span></div>
                <div>
                    <span>
                        <c:choose>
                            <c:when test="${personRegionName == null}">
                                регион не выбран
                            </c:when>
                            <c:otherwise>
                                ${personRegionName}
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="clear"></div>
            </div>

            <div class="selectRegionHint"> Выберите регион, в котором возможно оформить заявку онлайн. Не нашли Ваш регион? Пожалуйста, зайдите позже, мы работаем над тем, чтоб услуга была доступна во всех регионах</div>

            <tiles:insert definition="alphabet" flush="false">
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
        </div>
    </c:if>
</html:form>