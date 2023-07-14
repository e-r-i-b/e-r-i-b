<!--
    Настройки для CSA-BACK
-->
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<table>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="csa.back.webservice.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.csa.back.webservice.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес веб-сервиса для модуля "ЦСА"</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.ipas.csa.back.config.timeout"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.ipas.csa.back.config.timeout"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите тайм-аут соединения с АС "iPAS" в миллисекундах.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.integration.ipas.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.integration.ipas.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес веб-сервиса для  модуля  "iPAS".</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tr><td colspan="2">
<fieldset>
    <legend>Общие параметры</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.confirmation.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.confirmation.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">Укажите время ожидания подтверждения операции в секундах.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.confirmation.code.length"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.confirmation.code.length"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">Введите ограничение длины пароля, отправляемого в SMS для подтверждения операции.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint">Введите допустимые символы для пароля подтверждения операций.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.session.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.session.timeout"/></tiles:put>
            <tiles:put name="textSize" value="5"/>
            <tiles:put name="fieldHint">Укажите максимальное время жизни сессии в часах.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tr><td colspan="2">
<fieldset>
    <legend>Параметры регистрации пользователей</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <!--в бд для этой настройки true означает запрещено  -->
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.registration.user.deny-multiple"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.registration.user.deny-multiple"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@Разрешена|true@Запрещена"/>
            <tiles:put name="fieldHint">Укажите возможность повторной регистрации клиентов для данной внешней системы.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.registration.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.registration.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">Укажите время ожидания подтверждения регистрации клиента в миллисекундах. Если в течение этого времени клиент не подтвердит заявку на регистрацию, то ему будет отказано в регистрации в системе.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>

    </table>
</fieldset>
</td>
</tr>
<tr>
<td colspan="2">
<fieldset>
    <legend>Самостоятельная регистрация в СБОЛ</legend>
    <table cellpadding="0" cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.registration.mode"/>
            <tiles:put name="fieldDescription">Режим самостоятельной регистрации изнутри СБОЛ</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="OFF@Режим отключен|SOFT@Включен «мягкий» режим|HARD@Включен «жесткий» режим"/>
            <tiles:put name="fieldHint">Выберите режим для возможности в новой ЦСА самостоятельной регистрации клиентов, уже работающих в СБОЛ. Если режим отключен, то самостоятельная регистрация невозможна. Когда включен «мягкий» режим, регистрация возможна, но клиент может от нее отказаться. Если включен «жесткий» режим, то клиент не может отказаться от регистрации.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <!--в бд для этой настройки true означает запрещено  -->
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.registration.user.deny-multiple"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.iccs.registration.user.deny-multiple"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@Разрешена|true@Запрещена"/>
            <tiles:put name="fieldHint">Укажите возможность повторной регистрации клиентов для данной внешней системы.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.ban.ipas.login"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.iccs.ban.ipas.login"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@Нет|true@Да"/>
            <tiles:put name="fieldHint">Запрет входа с логином/алиасом iPAS.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_NOT_EXIST.mode"/>
            <tiles:put name="fieldDescription">Текст сообщения всплывающего окна в мягком режиме регистрации при отсутствии профиля в новой CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">Укажите текст сообщения всплывающего окна показываемого клиенту в мягком режиме самостоятельной регистрации при отсутствии профиля в новой CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_NOT_EXIST.mode"/>
            <tiles:put name="fieldDescription">Текст сообщения всплывающего окна в жестком режиме регистрации при отсутствии профиля в новой CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">Укажите текст сообщения всплывающего окна показываемого клиенту в жестком режиме самостоятельной регистрации при отсутствии профиля в новой CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_EXIST.mode"/>
            <tiles:put name="fieldDescription">Текст сообщения всплывающего окна в мягком режиме регистрации при наличии профиля в новой CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">Укажите текст сообщения всплывающего окна показываемого клиенту в мягком режиме самостоятельной регистрации при при наличии профиля в новой CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_EXIST.mode"/>
            <tiles:put name="fieldDescription">Текст сообщения всплывающего окна в жестком режиме регистрации при наличии профиля в новой CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">Укажите текст сообщения всплывающего окна показываемого клиенту в жестком режиме самостоятельной регистрации при наличии профиля в новой CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationForm.message"/>
            <tiles:put name="fieldDescription">Текст сообщения на форме регистрации</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">Укажите текст сообщения клиенту на форме регистрации.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationWindow.title"/>
            <tiles:put name="fieldDescription">Заголовок сообщения</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">Укажите заголовок сообщения клиенту.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.self.registration.new.design"/>
            <tiles:put name="fieldDescription">Дизайн самостоятельной регистрации изнутри СБОЛ:</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@Старый|true@Новый"/>
            <tiles:put name="fieldHint">Выберите дизайн для самостоятельной регистрации клиентов, уже работающих в СБОЛ</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.multiple.registration.part.visible"/>
            <tiles:put name="fieldDescription">Отображение текста «Либо зарегистрируйтесь»</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@Разрешено|false@Запрещено"/>
            <tiles:put name="fieldHint">Укажите должен ли отображаться блок с текстом «Либо зарегистрируйтесь»</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.self.registration.show.login.self.registration.screen"/>
            <tiles:put name="fieldDescription">Отображение страницы входа по собственному логину и паролю:</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@Разрешено|false@Запрещено"/>
            <tiles:put name="fieldHint">Укажите, необходимо ли отображать страницу входа по собственному логину и паролю, если клиент вошел под iPAS-логином или временным логином.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>

<tr><td colspan="2">
<fieldset>
    <legend>Параметры аутентификации пользователей</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.authentication.blocking.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.authentication.blocking.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">Введите период времени в секундах, в течение которого доступ клиента в систему будет заблокирован после превышения попыток ввода логина или пароля при входе в систему.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.authentication.failed.limit"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.authentication.failed.limit"/></tiles:put>
            <tiles:put name="textSize" value="5"/>
            <tiles:put name="fieldHint">Введите количество неудачных попыток входа клиента в систему, после которых доступ клиента в систему будет временно заблокирован.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.authentication.ipas.password.store.allowed"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.authentication.ipas.password.store.allowed"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@Включено|false@Выключено"/>
            <tiles:put name="showHint" value="none"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.integration.ipas.allowed"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.integration.ipas.allowed"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@Разрешена|false@Не разрешена"/>
            <tiles:put name="showHint" value="none"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.password-restoration.timeout"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.password-restoration.timeout"/></tiles:put>
    <tiles:put name="textSize" value="10"/>
    <tiles:put name="fieldHint">Введите время ожидания подтверждения восстановления пароля клиента в миллисекундах. Если за указанное время клиент не подтвердит заявку, то ему будет отказано в восстановлении пароля.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tr><td colspan="2">
<fieldset>
    <legend>Параметры регистрации мобильного приложения</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">Введите время ожидания подтверждения клиентом регистрации мобильного приложения в миллисекундах. Если за указанное время клиент не подтвердит заявку, то ему будет отказано в регистрации мобильного приложения.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.max.connectors"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.max.connectors"/></tiles:put>
            <tiles:put name="textSize" value="5"/>
            <tiles:put name="fieldHint">Введите максимальное количество зарегистрированных мобильных устройств для одного клиента.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.request.limit"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.request.limit"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">Введите максимально допустимое количество неподтвержденных запросов на регистрацию клиента в системе.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">Введите время поиска (в секундах), за которое будет выполняться поиск неподтвержденных запросов клиента на регистрацию. Это время будет учитываться начиная с текущего времени.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="mb.system.id"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.mb.system.id"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="showHint" value="none"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<%--при репликации настройки для CSABack не должны показываться.--%>
<c:if test="${!form.replication}">
<tr><td colspan="2">
<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="logLevels"/>
    <tiles:put name="text" value="Настройка уровня детализации журналов системных действий"/>
    <tiles:put name="head">
        <td width="60%">
            Параметр
        </td>
        <td width="40%">
            Значение
        </td>
    </tiles:put>
    <tiles:put name="data">
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Core"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Core"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@Максимальный|1@Отладка|2@Информация|3@Предупреждения|4@Ошибки|5@Выключен"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo1"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">Выберите для модуля "Ядро" уровень детализации логируемых системных действий.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Gate"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Gate"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@Максимальный|1@Отладка|2@Информация|3@Предупреждения|4@Ошибки|5@Выключен"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo2"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">Выберите для модуля "Шлюз" уровень детализации логируемых системных действий. </tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Scheduler"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Scheduler"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@Максимальный|1@Отладка|2@Информация|3@Предупреждения|4@Ошибки|5@Выключен"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo3"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">Выберите для модуля "Обработчик расписаний" уровень детализации логируемых системных действий.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Cache"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Cache"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@Максимальный|1@Отладка|2@Информация|3@Предупреждения|4@Ошибки|5@Выключен"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo4"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">Выберите для модуля "Система кеширования" уровень детализации логируемых системных действий.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Web"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Web"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@Максимальный|1@Отладка|2@Информация|3@Предупреждения|4@Ошибки|5@Выключен"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo5"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">Выберите для веб-страниц уровень детализации логируемых системных действий.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
    </tiles:put>
</tiles:insert>
</td>
</tr>
</c:if>
<tr><td colspan="2">
<fieldset>
    <legend>Параметры журнала системных действий</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.system.DatabaseSystemLogWriter@БД|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@Консоль"/>
            <tiles:put name="fieldHint">Укажите место логирования записей журнала системных действий: в файл, в БД или через MQ.</tiles:put>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="Укажите идентификатор экземпляра приложения для синхронного логирования журнала системных действий."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="Укажите идентификатор очереди для асинхронного логирования журнала системных действий."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="Укажите идентификатор фабрики для асинхронного логирования журнала системных действий."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.backup"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.backup"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.system.DatabaseSystemLogWriter@БД|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@Консоль"/>
            <tiles:put name="fieldHint" value="Укажите место резервного логирования записей журнала системных действий: в файл, в БД или через MQ."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tr><td colspan="2">
<fieldset>
    <legend>Параметры журнала сообщений</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@БД|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@Консоль"/>
            <tiles:put name="fieldHint" value="Укажите место логирования записей журнала собщений: в файл, в БД или через MQ."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="Укажите идентификатор экземпляра приложения для синхронного логирования журнала сообщений."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="Укажите идентификатор очереди для асинхронного логирования журнала сообщений."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="Укажите идентификатор фабрики для асинхронного логирования журнала сообщений."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.backup"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.backup"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@БД|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@Консоль"/>
            <tiles:put name="fieldHint" value="Укажите место резервного логирования записей журнала системных действий: в файл, в БД или через MQ."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.level.jdbc"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.messagesLog.level.jdbc"/></tiles:put>
            <tiles:put name="fieldType" value="radio"/>
            <tiles:put name="selectItems" value="on@Включено|off@Выключено"/>
            <tiles:put name="fieldHint" value="Отметьте флажком, нужно ли логировать сообщения о взаимодействии между ЦСА-Back и JDBC."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.level.iPas"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.messagesLog.level.iPas"/></tiles:put>
            <tiles:put name="fieldType" value="radio"/>
            <tiles:put name="selectItems" value="on@Включено|off@Выключено"/>
            <tiles:put name="fieldHint">Отметьте флажком, нужно ли логировать сообщения о взаимодействии между ЦСА-Back и АС "iPAS".</tiles:put>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tr>
    <td colspan="2">
        <fieldset>
            <legend>Параметры гостевого входа</legend>
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.entry.mode"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.entry.mode"/></tiles:put>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems" value="false@Запрещён|true@Разрешён"/>
                    <tiles:put name="fieldHint" value="Выберите режим доступности гостевого входа в СБОЛ"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.phones.limit"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.phones.limit"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите количество попыток от 0 до 999"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.phones.limit.cooldown"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.phones.limit.cooldown"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите время в минутах"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.sms.count"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.sms.count"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите количество неуспешных попыток ввода"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.sms.count.global"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.sms.count.global"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите количество попыток отправки пароля"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.sms.cooldown"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.sms.cooldown"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите время блокировки при исчерпании количества отправленных паролей"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.captcha.delay"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.captcha.delay"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите максимальное время задержки между попытками регистрации с одного ip-адреса до включения теста CAPTCHA"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>


                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.captcha.delay.minimal"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.captcha.delay.minimal"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите время задержки между попытками регистрации с одного ip-адреса до выключения теста CAPTCHA"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.auth.csa.front.config.constant.captcha.control.enabled"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.front.config.constant.captcha.control.enabled"/></tiles:put>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems" value="false@Нет|true@Да"/>
                    <tiles:put name="fieldHint" value="Выберите режим постоянного включения теста CAPTCHA"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.entry.claims.show.period"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.entry.claims.show.period"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="Укажите период в течение которого хранятся операции в гостевом СБОЛе"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>
            </table>
        </fieldset>
    </td>
</tr>
<tr><td colspan="2">
</td>
</tr>
</table>