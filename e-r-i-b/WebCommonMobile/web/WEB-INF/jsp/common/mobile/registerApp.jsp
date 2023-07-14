<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<fmt:setLocale value="ru-RU"/>

<html:form action="/registerApp">
    <%--@elvariable id="form" type="com.rssl.phizic.web.common.mobile.ext.sbrf.security.RegisterAppForm"--%>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <loginCompleted>false</loginCompleted>
            <c:set var="mGUID" value="${form.mguid}"/>
            <c:if test="${not empty mGUID}">
                <confirmRegistrationStage>
                    <mGUID>${mGUID}</mGUID>
                </confirmRegistrationStage>
            </c:if>
            <c:if test="${not empty form.smsPasswordLifeTime and not empty form.smsPasswordAttemptsRemain}">
                <confirmInfo>
                    <type>smsp</type>
                    <smsp>
                        <lifeTime>${form.smsPasswordLifeTime}</lifeTime>
                        <attemptsRemain>${form.smsPasswordAttemptsRemain}</attemptsRemain>
                    </smsp>
                </confirmInfo>
            </c:if>
            <c:if test="${not empty form.minimumPINLength}">
                <registrationParameters>
                    <minimumPINLength>${form.minimumPINLength}</minimumPINLength>
                </registrationParameters>
            </c:if>
        </tiles:put>
        <tiles:put name="messagesBundle" value="securityBundle"/>
    </tiles:insert>
</html:form>
