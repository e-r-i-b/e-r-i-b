<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<div id="warnings">
<tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="orange"/>
        <tiles:put name="data">
            <div class="infoMessage">
                <div class="messageContainer">
                    <div class="itemDiv">
                        <c:choose>
                            <c:when test="${not empty errorMessage}">
                                ${phiz:processBBCode(errorMessage)}
                            </c:when>
                            <c:when test="${not empty errorMessageKey}">
                                <bean:message key="${errorMessageKey}" bundle="commonBundle"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message key="error.errorHeader" bundle="commonBundle"/>
                            </c:otherwise>
                        </c:choose>
                     </div>
                </div>
            </div>
        </tiles:put>
</tiles:insert>
</div>