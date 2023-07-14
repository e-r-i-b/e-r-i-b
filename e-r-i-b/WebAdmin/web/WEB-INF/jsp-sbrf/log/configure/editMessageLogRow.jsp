<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<c:set var="imagePath" value="${skinUrl}/images"/>

<tr>
    <td>
        <span><bean:message bundle="logBundle" key="config.message.log.field.name.prefix" arg0="${firstSystemName}" arg1="${secondSystemName}"/></span>
    </td>
    <td>
        <div class="float">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.level.${module}"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="selectItems">on@<bean:message bundle="loggingBundle" key="level0"/>|off@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
            </tiles:insert>
        </div>
        <tiles:insert definition="floatMessageShadow" flush="false">
            <tiles:put name="id" value="${module}MessagesLogInfo"/>
            <tiles:put name="hintClass" value="middleLine normal-white-space"/>
            <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" hspace="5px"></tiles:put>
            <tiles:put name="showHintImg" value="false"/>
            <tiles:put name="text">
                <bean:message bundle="logBundle" key="config.message.log.field.description" arg0="${firstSystemName}" arg1="${secondSystemName}"/>
            </tiles:put>
            <tiles:put name="dataClass" value="dataHint"/>
            <tiles:put name="floatClassSyffix" value="Right"/>
        </tiles:insert>&nbsp;&nbsp;
    </td>
    <td>
        <div class="float">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.extended.level.${module}"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="selectItems">on@<bean:message bundle="loggingBundle" key="level0"/>|off@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
            </tiles:insert>
        </div>
        <tiles:insert definition="floatMessageShadow" flush="false">
            <tiles:put name="id" value="${module}MessagesExtendedLogInfo"/>
            <tiles:put name="hintClass" value="middleLine normal-white-space"/>
            <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" hspace="5px"></tiles:put>
            <tiles:put name="showHintImg" value="false"/>
            <tiles:put name="text">
                <bean:message bundle="logBundle" key="config.message.extended.log.field.description" arg0="${firstSystemName}" arg1="${secondSystemName}"/>
            </tiles:put>
            <tiles:put name="dataClass" value="dataHint"/>
            <tiles:put name="floatClassSyffix" value="Right"/>
        </tiles:insert>&nbsp;&nbsp;
    </td>
</tr>