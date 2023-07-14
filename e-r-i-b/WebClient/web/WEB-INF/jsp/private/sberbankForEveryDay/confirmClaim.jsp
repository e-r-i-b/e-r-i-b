<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <html:hidden property="id"/>
    <div class="page_content">
        <input id="stageNumber" type="hidden" name="stageNumber" value="${form.stageNumber}"/>
        <input id="cardCount" type="hidden" name="cardCount" value="${form.cardCount}"/>
        <%--Заказ дебетовых карт--%>
        <c:if test="${!form.guest || hasGuestAnyAccount}">
            <div class="title_common head_title margin_bottom_40"><bean:message key="label.title" bundle="sbnkdBundle"/></div>
        </c:if>
        <div class="clear"></div>

        <%@ include file="viewClaimData.jsp" %>

        <div class="next_stage_button">

            <div class="orderseparate margin_top_60"></div>

            <div class="float">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit"/>
                    <tiles:put name="bundle" value="sbnkdBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="toEdit()"/>
                    <tiles:put name="image"       value="backIcon2.png"/>
                    <tiles:put name="imageHover"     value="backIconHover2.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                </tiles:insert>
            </div>
            <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
            <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
            <c:set var="visitingMode" value="${form.guest ? 'guest':'private'}"/>
            <tiles:insert definition="confirmButtons" flush="false">
                <tiles:put name="ajaxUrl" value="/${visitingMode}/async/sberbankForEveryDay"/>
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
            </tiles:insert>
        </div>
    </div>
