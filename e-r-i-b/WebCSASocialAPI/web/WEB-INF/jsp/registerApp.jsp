<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<fmt:setLocale value="ru-RU"/>

<c:set var="form" value="${RegisterAppForm}"/>

<tiles:insert definition="response" flush="false">
    <tiles:put name="data">
        <loginCompleted>false</loginCompleted>
        <c:set var="host" value="${form.host}"/>
        <c:set var="token" value="${form.token}"/>

        <c:choose>
            <c:when test="${empty host and empty token}">
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
            </c:when>
            <c:otherwise>
                    <loginData>
                       <host>${host}</host>
                       <token>${token}</token>
                   </loginData>
            </c:otherwise>
        </c:choose>

    </tiles:put>
    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>