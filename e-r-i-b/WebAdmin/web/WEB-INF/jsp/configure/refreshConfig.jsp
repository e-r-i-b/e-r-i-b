<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/schemes/configure" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="configEdit">
   	<tiles:put name="submenu" type="string" value="FastRefreshConfig"/>

<tiles:put name="menu" type="string"></tiles:put>

<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="FastRefreshConfig"/>
		<tiles:put name="name" value="Применение настроек"/>
		<tiles:put name="description" value="На этой странице Вы можете запустить процесс обновления настроек."/>
		<tiles:put name="data">
		</tiles:put>
		<tiles:put name="buttons">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"         value="button.refreshCSA"/>
				<tiles:put name="commandHelpKey"     value="button.refreshCSA.help"/>
				<tiles:put name="bundle"             value="commonBundle"/>
			</tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"         value="button.refreshPhizIC"/>
                <tiles:put name="commandHelpKey"     value="button.refreshPhizIC.help"/>
                <tiles:put name="bundle"             value="commonBundle"/>
                <tiles:put name="isDefault"          value="true"/>
            </tiles:insert>
		</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>