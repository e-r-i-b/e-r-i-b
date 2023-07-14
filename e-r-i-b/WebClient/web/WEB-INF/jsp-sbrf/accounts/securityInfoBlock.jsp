<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<div id="securityInfoBlock">
    <div class="control" onclick="$('#securityInfoBlock').hide();">
        Закрыть<div class="closeImg" title="Закрыть"></div>
    </div>
    <div class="content">
        <tiles:useAttribute name="securityType"/>
        <c:choose>
            <c:when test="${securityType == 'HIGHT'}">
                <img src="${skinUrl}/images/red.jpg"/>
            </c:when>
            <c:when test="${securityType == 'MIDDLE'}">
                <img src="${skinUrl}/images/orange.jpg"/>
            </c:when>
            <c:otherwise>
                <img src="${skinUrl}/images/green.jpg"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>