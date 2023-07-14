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
<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.VkGroupsWidget"/>
    <tiles:put name="cssClassname" value="VkGroupWidget"/>
    <tiles:put name="borderColor" value="whiteTop"/>

    <%-- Настройки  --%>
    <tiles:put name="editPanel">
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Вид:</tiles:put>
            <tiles:put name="data">
                <select field="size">
                    <option value="compact">Компактный</option>
                    <option value="wide">Полный</option>
                </select>
            </tiles:put>
        </tiles:insert>
        <div class="clear"></div>
    </tiles:put>

    <%-- Отображение  --%>
    <tiles:put name="viewPanel">
        <div panel="waiter" style="display:none">
            <img src="${imagePath}/ajaxLoader.gif" alt="Загрузка..." title="Загрузка..." class="loader"/>
        </div>
    </tiles:put>
</tiles:insert>
