<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:importAttribute/>
<div id="content">
    <tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
        <tiles:put name="bundle"    value="commonBundle" type="string"/>
        <tiles:put name="usePopup"  value="true"/>
    </tiles:insert>

    <div id="${formId}" class="sliderBlock businessEnvironment">
        <html:form action="${formAction}" onsubmit="${onsubmit}">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="shadow"/>
                <tiles:put name="data" type="string">
                    <div onkeypress="clickDefButtonIfEnterKeyPress(this, event);">
                        ${formData}
                    </div>
                </tiles:put>
            </tiles:insert>
            <div id="blocking-message-restriction-block">
                <c:if test="${not empty blockingMessage && blockingMessage ne ''}">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="color" value="yellowTop"/>
                        <tiles:put name="data" type="string">
                            <c:out value="${blockingMessage}"/>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </div>
        </html:form>
    </div>
    <%-- всплывающие окна --%>
    <%@ include file="/WEB-INF/jsp/common/popupWindows.jsp"%>

    <%-- Блок с новостями --%>
    <div class="newsBlock" id="newsBlock">
        <tiles:insert page="/WEB-INF/jsp/common/newsBlock.jsp" flush="false"/>
    </div>

    <%-- Блок с вкладками --%>
    <div class="rightBlock" id="tabsBlock">
        <tiles:insert page="/WEB-INF/jsp/common/tabs/tabsBlock.jsp" flush="false">
            <tiles:put name="id" type="string" value="frontInfo"/>
            <tiles:putList name="tabs">
                <tiles:add value="/WEB-INF/jsp/sliders/precaution.jsp"/>
                <tiles:add value="/WEB-INF/jsp/sliders/opportunities.jsp"/>
                <tiles:add value="/WEB-INF/jsp/sliders/mobileApplications.jsp"/>
            </tiles:putList>
        </tiles:insert>
    </div>
</div>
<div class="clear"></div>
<%--  --%>
<c:set var="additionalFooterData" scope="request">
    <div class="businessEnvironment_logo">
        <a href="${csa:getBusinessEnvironmentMainURL()}"><img src="${skinUrl}/skins/commonSkin/images/csa/logo_businessEnvironment.jpg"/></a>
    </div>
</c:set>
