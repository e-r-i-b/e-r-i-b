<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/logging/messagesLog/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="loggingEdit">
    <tiles:put name="submenu" type="string" value="EditMesagesLoggingLevel"/>
	<tiles:put name="pageTitle" type="string">
		<bean:message key="edit.message.log.title" bundle="loggingBundle"/>
	</tiles:put>

	<tiles:put name="menu" type="string">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
            <tiles:put name="postbackNavigation" value="true"/>
			<tiles:put name="bundle" value="loggingBundle"/>
			<tiles:put name="image" value=""/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</tiles:put>

<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="logLovels"/>
		<tiles:put name="text" value="Настройка уровня ведения логов"/>
		<tiles:put name="head">
	        <td>
				Система
			</td>
			<td>
				Уровень детализации
			</td>
		</tiles:put>
		<tiles:put name="data">
		<!-- строки списка -->
		<tr>
			<td>
				<span><bean:message key="message.log.system.retail" bundle="logBundle"/></span>
			</td>
			<td>
                <html:select property="field(com.rssl.phizic.logging.messagesLog.level.retail)">
                    <html:option value="on"  key="level0" bundle="loggingBundle"/>
                    <html:option value="off" key="level6" bundle="loggingBundle"/>
                </html:select>
			</td>
		</tr>
		</tiles:put>

    </tiles:insert>
    <c:set var="prefix" value="com.rssl.phizic.logging.messagesLog."/>
    <%@ include file="editAutoArchivationSettings.jsp" %>
</tiles:put>
</tiles:insert>
</html:form>
