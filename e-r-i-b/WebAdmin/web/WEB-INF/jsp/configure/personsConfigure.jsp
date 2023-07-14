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
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<% pageContext.getRequest().setAttribute("mode","Options");%>
<% pageContext.getRequest().setAttribute("userMode","Persons");%>
<html:form action="/persons/configure"  onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="replicateAccessOperation" value="SetPersonSecuritySettingsOperation"/>
        <tiles:put name="submenu" type="string" value="ClientsEmployees"/>

       <tiles:put name="menu" type="string"/>

       <tiles:put name="data" type="string">

       <tiles:importAttribute/>
       <c:set var="globalImagePath" value="${globalUrl}/images"/>
       <c:set var="imagePath" value="${skinUrl}/images"/>
        <c:set var="passwordCharsets" value=""/>
        <c:forEach items="${form.passwordCharsets}" var="charset">
            <c:set var="chValue" value="${charset.charset}"/>
            <c:set var="chText" value="${charset.name}"/>
            <c:if test="${passwordCharsets != ''}"><c:set var="passwordCharsets"  value="${passwordCharsets}|"/></c:if>
            <c:set var="passwordCharsets"  value="${passwordCharsets}${charset.charset}@${charset.name}"/>
        </c:forEach>

	   <tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="personsConfigure"/>
		<tiles:put name="name" value="Настройки клиента"/>
		<tiles:put name="description" value="Используйте форму для изменения настроек клиента"/>
		<tiles:put name="data">

            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.password-generator.allowed.chars"/>
                    <tiles:put name="fieldDescription">Разрешенные символы для пароля</tiles:put>
                    <tiles:put name="fieldHint">Символы, используемые для создания паролей.</tiles:put>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems" value="${passwordCharsets}"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

             <c:choose>

                 <c:when test="${phiz:useOwnAuthentication()}">

                         <tiles:insert definition="propertyField" flush="false">
                             <tiles:put name="fieldName" value="com.rssl.iccs.login-generator.allowed.chars"/>
                             <tiles:put name="fieldDescription">Разрешенные символы для логина</tiles:put>
                             <tiles:put name="fieldHint">Символы, используемые для создания логинов.</tiles:put>
                             <tiles:put name="fieldType" value="select"/>
                             <tiles:put name="selectItems" value="0123456789@Только цифры|0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@Цифры и заглавные буквы|0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz@Цифры и буквы|ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz@Только буквы"/>
                             <tiles:put name="imagePath" value="${imagePath}"/>
                         </tiles:insert>

                 </c:when>
                 <c:otherwise>
                     <html:hidden property="field(com.rssl.iccs.login-generator.allowed.chars)"/>
                 </c:otherwise>
             </c:choose>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.password-generator.password.length"/>
                    <tiles:put name="fieldDescription">Длина пароля</tiles:put>
                    <tiles:put name="fieldHint">Длина создаваемых паролей.</tiles:put>
                    <tiles:put name="textSize" value="2"/>
                    <tiles:put name="textMaxLength" value="2"/>
                    <tiles:put name="inputDesc">символов</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.login.attempts"/>
                    <tiles:put name="fieldDescription">Количество попыток (регистрация)</tiles:put>
                    <tiles:put name="fieldHint">Количество попыток для ввода пароля до автоматической блокировки доступа.</tiles:put>
                    <tiles:put name="textSize" value="2"/>
                    <tiles:put name="textMaxLength" value="2"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.login.blockedTimeout"/>
                    <tiles:put name="fieldDescription">Период блокировки</tiles:put>
                    <tiles:put name="fieldHint">Время, на которое автоматически блокируется доступ к системе при многократном неверном вводе пароля.</tiles:put>
                    <tiles:put name="inputDesc"> сек.</tiles:put>
                    <tiles:put name="textSize" value="10"/>
                    <tiles:put name="textMaxLength" value="10"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="mobile.api.pin.length"/>
                    <tiles:put name="fieldDescription">Минимальная длина пароля для мобильного и социального приложения</tiles:put>
                    <tiles:put name="fieldHint">Минимальная длина паролей, создаваемых при регистрации мобильных и социальных приложений.</tiles:put>
                    <tiles:put name="textSize" value="2"/>
                    <tiles:put name="textMaxLength" value="2"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="mobile.api.pin.regexp"/>
                    <tiles:put name="fieldDescription">Регулярное выражение для пароля в мобильном и социальном приложении</tiles:put>
                    <tiles:put name="fieldHint">Регулярное выражение, определяющее допустимые символы в пароле для мобильного и социального приложения</tiles:put>
                    <tiles:put name="textSize" value="30"/>
                    <tiles:put name="textMaxLength" value="30"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>
            </table>

        </tiles:put>

     </tiles:insert>
</tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="SetPersonSecuritySettingsOperation">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="SetPersonSecuritySettingsOperation">
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
</tiles:insert>
</html:form>
