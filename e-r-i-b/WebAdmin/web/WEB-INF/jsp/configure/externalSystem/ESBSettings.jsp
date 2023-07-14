<%--
    Настройки для системы КСШ
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz"%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="showCheckbox" value="${form.replication}"/>
<table>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.gate.connection.timeout.esb"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="ESB.com.rssl.gate.connection.timeout"/></tiles:put>
        <tiles:put name="textSize" value="10"/>
        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="ESB.com.rssl.gate.connection.timeout.description"/></tiles:put>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>

    <c:set var="modeDescription"><bean:message bundle="configureBundle" key="ESB.com.rssl.es.integration.service.mode.description"/></c:set>
    <c:set var="MQModeDescription"><bean:message bundle="configureBundle" key="ESB.com.rssl.es.integration.service.mode.MQ"/></c:set>
    <c:set var="WSModeDescription"><bean:message bundle="configureBundle" key="ESB.com.rssl.es.integration.service.mode.WS"/></c:set>
    <c:set var="timeoutMQModeUnit"><bean:message bundle="configureBundle" key="ESB.com.rssl.es.integration.service.timeout.mq.unit"/></c:set>
    <c:set var="timeoutMQDescription"><bean:message bundle="configureBundle" key="ESB.com.rssl.es.integration.service.timeout.mq.description"/></c:set>

    <script type="text/javascript">
        function getAnotherModes(mode)
        {
            return mode == 'MQ'? ['WS']: ['MQ']
        }

        function onChangeMode(mode, id)
        {
            var optionPrefix = '.js-mode';
            var optionId = 'Option' + id;

            $(optionPrefix + mode + optionId).removeAttr('disabled');

            var anotherModes = getAnotherModes(mode);
            for (var i=0; i < anotherModes.length; i++)
                $(optionPrefix + anotherModes[i] + optionId).attr('disabled', 'true');
        }
    </script>
    <c:forEach var="serviceSetting" items="${form.esbServices}" varStatus="index">
        <jsp:include page="/WEB-INF/jsp/configure/externalSystem/esb/services/${serviceSetting.mode}.jsp" flush="false">
            <jsp:param name="form"                 value="${form}"/>
            <jsp:param name="serviceName"          value="${serviceSetting.name}"/>
            <jsp:param name="MQModeDescription"    value="${MQModeDescription}"/>
            <jsp:param name="WSModeDescription"    value="${WSModeDescription}"/>
            <jsp:param name="timeoutMQDescription" value="${timeoutMQDescription}"/>
            <jsp:param name="timeoutMQModeUnit"    value="${timeoutMQModeUnit}"/>
            <jsp:param name="index"                value="${index.index}"/>
            <jsp:param name="isEditable"           value="${showCheckbox}"/>
            <jsp:param name="modeDescription"      value="${modeDescription}"/>
            <jsp:param name="imagePath"            value="${imagePath}"/>
        </jsp:include>
    </c:forEach>
</table>