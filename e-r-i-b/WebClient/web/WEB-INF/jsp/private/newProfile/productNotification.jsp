<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>

<html:form action="/private/userprofile/productNotification">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="newUserProfile">
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="activeItem">mobilebank</tiles:put>
                <tiles:put name="data">
                    <div class="newProfileContentWidth">
                        <tiles:insert definition="mainWorkspace" flush="false">
                            <tiles:put name="title" value="Мобильный банк"/>
                            <tiles:put name="data">
                                <tiles:insert definition="formHeader" flush="false">
                                    <tiles:put name="image" value="${imagePathGlobal}/ico_mobileBank.jpg"/>
                                    <tiles:put name="description">
                                        <h3>На этой странице можно изменить настройки подключения к Мобильному банку, просмотреть SMS-запросы и шаблоны, а также историю отправленных в банк запросов. Для этого перейдите на соответствующую вкладку. </h3>
                                    </tiles:put>
                                </tiles:insert>
                                <c:set var="selectedTab" value="notification"/>
                                <%@ include file="/WEB-INF/jsp-sbrf/mobilebank/ermb/mobileBankHeader.jsp" %>
                                <c:set var="isNewProfile" value="true"/>
                                <c:set var="cancelURL" value="/private/userprofile/productNotification.do"/>
                                <div id="mobilebank">
                                    <%@ include file="/WEB-INF/jsp/private/userprofile/userProductNotificationData.jsp" %>
                                </div>
                        </tiles:put>
                    </tiles:insert>
                    </div>
                </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>