<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if IE 7 ]><html lang="ru-RU" class="ie ie7 ie78"><![endif]-->
<!--[if IE 8 ]><html lang="ru-RU" class="ie ie8 ie78"><![endif]-->
<!--[if IE 9 ]><html lang="ru-RU" class="ie ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="ru-RU"><!--<![endif]-->
    <%@ include file="/WEB-INF/jsp/common/import/reg/html-head.jsp"%>
    <body class="jsOff">
        <tiles:insert definition="googleTagManager"/>
        <div class="simplePage">
        <tiles:useAttribute name="needRegionSelector"/>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
        <div class="g-wrapper">
            <div id="loading" style="left:-3300px;">
                <div id="loadingImg"><img src="${skinUrl}/skins/sbrf/images/ajax-loader64.gif"/></div>
                <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
            </div>
            <div class="g-inner">
                <tiles:insert attribute="data"/>
            </div>
        </div>
        <div id="footer">
            <tiles:insert attribute="footer"/>
        </div>
        <div id="Overlay" class="b-overlay" style="display: none">
            <tiles:insert attribute="overlay"/>
        </div>
        </div>
    </body>
</html>