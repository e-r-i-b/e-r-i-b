<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/urlAddress/configure"  onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="UrlListSettings"/>
        <tiles:put name="pageName" type="string">URL-адреса. Настройки.</tiles:put>
        <tiles:put name="pageDescription" type="string">На данной странице можно изменить настройки URL-адресов внешних ссылок.</tiles:put>
        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.fns.registration.url"/>
                    <tiles:put name="fieldDescription" value="Адрес регистрации индивидуального частного предпринимателя на сайте ФНС"/>
                    <tiles:put name="fieldHint" value="Укажите адрес внешней ссылки для регистрации индивидуального частного предпринимателя на сайте ФНС."/>
                    <tiles:put name="textSize" value="40"/>
                    <tiles:put name="textMaxLength" value="100"/>
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
            <tiles:insert definition="clientButton" flush="false" >
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>