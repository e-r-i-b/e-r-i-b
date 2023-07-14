<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 17.03.2009
  Time: 13:40:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/skins/current"  onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="skinsEdit">
		<tiles:put name="submenu" type="string" value="CurrentSkin"/>

	<tiles:put name="data" type="string">

		<script type="text/javascript">
			function CallCheckWindow()
			{
				var globalUrl = getElement("field(globalUrl)");
				if (globalUrl==null || globalUrl.value==null || globalUrl.value=="")
				{
					alert("Введите значение в поле [Каталог общих стилей]");
					return;
				}
				var path = "${phiz:calculateActionURL(pageContext, '/skins/checkGlobalUrl.do')}?globalUrl="+globalUrl.value;
				window.open(path, "", "width=400,height=400");
			}
		</script>
        
        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

	   <tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="skins"/>
		<tiles:put name="name" value="Выбор стандартного стиля приложений"/>
		<tiles:put name="description" value="Используйте форму для изменения настроек стандартного стиля приложений"/>
		<tiles:put name="data">

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="lable.application.type.client" bundle="skinsBundle"/>:
                </tiles:put>
                <tiles:put name="data">
                    <html:select property="field(clientSkin)" style="width:500px;">
                        <c:forEach items="${form.usersSkins}" var="usersSkin">
                            <c:set var="skinId" value="${usersSkin.id}"/>
                            <c:set var="skinName" value="${usersSkin.name}"/>
                            <html:option value="${skinId}"><c:out value="${skinName}"/></html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px">
                </tiles:put>
                <tiles:put name="data">
                    текущий стиль пользовательского интерфейса клиентского приложения
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="lable.application.type.admin" bundle="skinsBundle"/>:
                </tiles:put>
                <tiles:put name="data">
                    <html:select property="field(adminSkin)" disabled="${!form.changeAdminSkinAllowed}" style="width:500px;">
                        <c:forEach items="${form.systemSkins}" var="systemSkin">
                            <c:set var="skinId" value="${systemSkin.id}"/>
                            <c:set var="skinName" value="${systemSkin.name}"/>
                            <html:option value="${skinId}"><c:out value="${skinName}"/></html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px">
                </tiles:put>
                <tiles:put name="data">
                    текущий стиль пользовательского интерфейса АРМ сотрудника банка
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.global.url" bundle="skinsBundle"/>:
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(globalUrl)" size="59" maxlength="32"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <img src="${imagePath}/info.gif" alt="" border="0" width="11px" height="11px">
                </tiles:put>
                <tiles:put name="data">
                    каталог, где размещаются общие для всех приложений стили и картинки, а также логотип банка
                </tiles:put>
            </tiles:insert>

            </tiles:put>
				<tiles:put name="buttons">
					<tiles:insert definition="clientButton" flush="false" operation="ChangeCurrentSkins">
						<tiles:put name="commandTextKey"     value="button.check.url"/>
						<tiles:put name="commandHelpKey" value="button.check.url"/>
						<tiles:put name="bundle"  value="skinsBundle"/>
						<tiles:put name="onclick" value="CallCheckWindow()"/>
					</tiles:insert>

					<tiles:insert definition="clientButton" flush="false" operation="ChangeCurrentSkins">
						<tiles:put name="commandTextKey"     value="button.cancel"/>
						<tiles:put name="commandHelpKey" value="button.cancel.help"/>
						<tiles:put name="bundle"  value="commonBundle"/>
						<tiles:put name="onclick" value="javascript:resetForm(event)"/>
					</tiles:insert>

					<tiles:insert definition="commandButton" flush="false" operation="ChangeCurrentSkins">
						<tiles:put name="commandKey"     value="button.save"/>
						<tiles:put name="commandHelpKey" value="button.save.help"/>
						<tiles:put name="bundle"  value="commonBundle"/>
						<tiles:put name="isDefault" value="true"/>
						<tiles:put name="postbackNavigation" value="true"/>
					</tiles:insert>
			</tiles:put>
		 </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>