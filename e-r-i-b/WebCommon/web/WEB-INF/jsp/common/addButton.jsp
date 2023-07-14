 <%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--
 text - название кнопки
 textClass - дополнительный класс для стиля текста кнопки
 --%>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<div class="clientButton buttonRoundGray buttonWithImg">
    <div>
        <a title="<c:out value="${text}"/>">
            <div class="left-corner"></div>

            <div class="text<c:if test="${not empty textClass}"> ${textClass}</c:if>">
                <div class="buttonText">
                    <span><c:out value="${text}"/></span>
                </div>
                <div class="buttonGrad"></div>
            </div>

            <div class="right-corner">
                <img src="${imagePath}/plus.png" style="width:16px; height:16px;">
            </div>
        </a>
    </div>
    <div class="clear"></div>
</div>