<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz"%>

<%--
serviceName          -- имя сервиса
MQModeDescription    -- подсказка к режиму интеграции через MQ
WSModeDescription    -- подсказка к режиму интеграции через WS
timeoutMQDescription -- подсказка к таймауту для режима интеграции через MQ
timeoutMQModeUnit    -- единицы измерения таймаута для режима интеграции через MQ
index                -- номер поля
isEditable           -- редактируемо ли поле
modeDescription      -- подсказка к полю
imagePath            -- путь к картинкам
--%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="radioInputName" value="com.rssl.es.integration.service.mode.${param.serviceName}"/>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldDescription">
        <bean:message bundle="configureBundle" key="ESB.com.rssl.es.integration.service.mode.${param.serviceName}"/>
    </tiles:put>
    <tiles:put name="fieldType" value="custom"/>
    <tiles:put name="fieldName" value="${radioInputName}"/>
    <tiles:put name="customField">
        <div class="settings-esb-service-row">
            <c:set var="inputName" value="field(${radioInputName})"/>
            <div class="settings-esb-service-row-mode">
                <div class="settings-esb-service-row-mode-row">
                    <div class="settings-esb-service-row-mode-row-control">
                        <html:radio styleId="modeMQ${param.index}" property="${inputName}" value="MQ" disabled="${param.isEditable == 'true'}" onchange="onChangeMode('MQ', '${param.index}')"/>
                        <label for="modeMQ${param.index}">${param.MQModeDescription}</label>
                    </div>
                    <div class="settings-esb-service-row-mode-row-option-title">
                        ${param.timeoutMQDescription}
                    </div>
                    <div class="settings-esb-service-row-mode-row-option-value">
                        <c:set var="timeoutInputName" value="com.rssl.es.integration.service.timeout.mq.${param.serviceName}"/>
                        <input class="js-modeMQOption${param.index}"
                               type="text"
                               size="20" maxlength="10"
                               name="field(${timeoutInputName})"
                               value="<bean:write name="form" property="field(${timeoutInputName})"/>"
                        <c:if test="${form.fields[radioInputName] ne 'MQ' or param.isEditable == 'true'}"> disabled=""</c:if>
                        />
                        &nbsp;
                        ${param.timeoutMQModeUnit}
                    </div>
                </div>
            </div>
            <div class="settings-esb-service-row-mode">
                <div class="settings-esb-service-row-mode-row">
                    <div class="settings-esb-service-row-mode-row-control">
                        <html:radio styleId="modeWS${param.index}" property="${inputName}" value="WS"  disabled="${param.isEditable == 'true'}" onchange="onChangeMode('WS', '${param.index}')"/>
                        <label for="modeWS${param.index}">${param.WSModeDescription}</label>
                    </div>
                </div>
            </div>
        </div>
    </tiles:put>
    <tiles:put name="fieldHint" value="${param.modeDescription}"/>
    <tiles:put name="imagePath" value="${param.imagePath}"/>
    <tiles:put name="tdStyle"   value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
