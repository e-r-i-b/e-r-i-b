<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insert definition="webModulePage">
    <tiles:put name="data">
        <div id="warnings">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="orange"/>
                <tiles:put name="data">
                    <div class="infoMessage">
                        <div class="messageContainer">
                            <div class="itemDiv">
                                <c:choose>
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
    </tiles:put>
</tiles:insert>