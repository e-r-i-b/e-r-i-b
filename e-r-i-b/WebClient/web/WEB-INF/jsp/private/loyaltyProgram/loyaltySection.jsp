<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute ignore="true"/>
<%-- isWidget=true, если эта jsp используется в виджете --%>

<c:set var="loyaltyProgramUrl"  value="${phiz:calculateActionURL(pageContext, '/private/loyalty/detail')}"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<c:set var="content">
    <c:set var="loyaltyProgramLink"  value="${phiz:getLoyaltyProgramLink()}"/>
    <div id="loyaltyLogoDiv" align="center" class="loyaltyBlockImage${isWidget ? "Widget" : ""}">
        <img src="${imagePath}/loyaltyProgramRightSectionIcon.gif" onerror="onImgError(this)" border="0"/>
    </div>
    <div id="loyaltyClearDiv" class="clear"></div>
    <div id="loyaltyInfoDiv" align="center">
        <c:choose>
            <c:when test="${loyaltyProgramLink.state == 'UNREGISTERED'}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.regist"/>
                    <tiles:put name="commandHelpKey" value="button.regist"/>
                    <tiles:put name="bundle" value="loyaltyBundle"/>
                    <tiles:put name="onclick" value=";"/>
                </tiles:insert>
            </c:when>
            <c:when test="${loyaltyProgramLink.state == 'ACTIVE'}">
                <c:set var="loyaltyProgram"  value="${loyaltyProgramLink.value}"/>
                <c:if test="${not empty loyaltyProgram}">
                    <tiles:insert definition="roundedPlate" flush="false">
                        <tiles:put name="data">
                           ${phiz:getStringInNumberFormat(loyaltyProgram.balance)} <bean:message bundle="loyaltyBundle" key="label.thanks"/>
                        </tiles:put>
                 </tiles:insert>
                </c:if>
            </c:when>
        </c:choose>
    </div>
</c:set>

<div onclick="goTo('${loyaltyProgramUrl}');" class="pointer">
    <c:choose>
        <c:when test="${isWidget}">
            ${content}
        </c:when>
        <c:otherwise>
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="title"><bean:message key="label.bonus.program" bundle="loyaltyBundle"/></tiles:put>
                <tiles:put name="color" value="gray"/>
                <tiles:put name="data">
                    ${content}
                </tiles:put>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</div>