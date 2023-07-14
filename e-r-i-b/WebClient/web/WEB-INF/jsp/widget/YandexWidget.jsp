<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="iFrameWidget" flush="false">
    <tiles:put name="cssClassFrame" value="YandexFrame"/>
    <tiles:put name="cssClassname" value="YandexWidget"/>
    <tiles:put name="url" value="http://pda.news.yandex.ru/quotes/1006.html"/>

    <tiles:put name="editPanel">
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Название:</tiles:put>
            <tiles:put name="data"><input type="text" field="title" size="20" maxlength="20"/></tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
