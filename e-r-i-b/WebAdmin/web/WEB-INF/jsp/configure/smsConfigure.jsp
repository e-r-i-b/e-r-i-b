<%--
  User: IIvanova
  Date: 14.02.2006
  Time: 13:50:29
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% pageContext.getRequest().setAttribute("mode","Options");%>
<% pageContext.getRequest().setAttribute("userMode","Cards");%>

<html:form action="/sms/configure"  onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
	    <tiles:put name="submenu" type="string" value="SmsService"/>
        
        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <tiles:put name="pageName" value="Одноразовые пароли (SMS, PUSH)"/>
        <tiles:put name="pageDescription" value="Используйте данную форму для настройки одноразовых SMS и PUSH-паролей."/>
        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.sms.password.length"/>
                    <tiles:put name="fieldDescription">Длина сообщения</tiles:put>
                    <tiles:put name="fieldHint">Длина пароля</tiles:put>
                    <tiles:put name="inputDesc" value="символов"/>
                    <tiles:put name="textSize" value="2"/>
                    <tiles:put name="textMaxLength" value="2"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.sms.password.confirmAttemts"/>
                    <tiles:put name="fieldDescription">Количество попыток ввода</tiles:put>
                    <tiles:put name="fieldHint">Количество попыток ввода пароля Клиентом.</tiles:put>
                    <tiles:put name="textSize" value="2"/>
                    <tiles:put name="textMaxLength" value="2"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.sms.password.lifeTime"/>
                    <tiles:put name="fieldDescription">Время жизни (в секундах)</tiles:put>
                    <tiles:put name="fieldHint">Время действия пароля</tiles:put>
                    <tiles:put name="textSize" value="5"/>
                    <tiles:put name="textMaxLength" value="5"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.sms.password.allowedChars"/>
                    <tiles:put name="fieldDescription">Используемые символы</tiles:put>
                    <tiles:put name="fieldHint">Символы, используемые для создания пароля</tiles:put>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems" value="0123456789@Только цифры|0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@Цифры и заглавные буквы|0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz@Цифры и буквы"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="mobile.api.auto.switch.push"/>
                    <tiles:put name="fieldDescription">Автоматически переключать канал доставки на push</tiles:put>
                    <tiles:put name="fieldHint">При включении клиентом на МП сервиса push канал доставки паролей и оповещений автоматически будет переключен на push</tiles:put>
                    <tiles:put name="fieldType" value="checkbox"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>
            </table>
        </tiles:put>
		<tiles:put name="formButtons">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
			</tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="onclick" value="resetForm(event)"/>
		    </tiles:insert>
		</tiles:put>
		<tiles:put name="formAlign" value="center"/>
	</tiles:insert>
</html:form>