<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>

<tiles:importAttribute/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<tr>
    <td>
        <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.systemLog.level.${module}"/>
    </td>
    <td nowrap="true">
        <div class="float">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.${module}"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="inputClass" value="Width150"/>
                <tiles:put name="selectItems">0@Максимальный|1@Отладка|2@Информация|3@Предупреждения|4@Ошибки|5@Выключен</tiles:put>
            </tiles:insert>
        </div>

        <tiles:insert definition="floatMessageShadow" flush="false">
            <tiles:put name="id" value="logInfo${module}"/>
            <tiles:put name="hintClass" value="indent-top normal-white-space"/>
            <tiles:put name="data"><div class="imgHintBlock"></div></tiles:put>
            <tiles:put name="showHintImg" value="false"/>
            <tiles:put name="text">
                <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.systemLog.level.${module}.hint"/>
            </tiles:put>
            <tiles:put name="dataClass" value="dataHint"/>
            <tiles:put name="floatClassSyffix" value="Right"/>
        </tiles:insert>&nbsp;&nbsp;
    </td>
    <td nowrap="true">
        <div class="float">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.extended.level.${module}"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="inputClass" value="Width150"/>
                <tiles:put name="selectItems">0@Максимальный|1@Отладка|2@Информация|3@Предупреждения|4@Ошибки|5@Выключен</tiles:put>
            </tiles:insert>

        </div>

        <tiles:insert definition="floatMessageShadow" flush="false">
            <tiles:put name="id" value="logInfoExtended${module}"/>
            <tiles:put name="hintClass" value="indent-top normal-white-space"/>
            <tiles:put name="data"><div class="imgHintBlock"></div></tiles:put>
            <tiles:put name="showHintImg" value="false"/>
            <tiles:put name="text">
                <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.systemLog.extended.level.${module}.hint"/>
            </tiles:put>
            <tiles:put name="dataClass" value="dataHint"/>
            <tiles:put name="floatClassSyffix" value="Right"/>
        </tiles:insert>&nbsp;&nbsp;
    </td>
</tr>