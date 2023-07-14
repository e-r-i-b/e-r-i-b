<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 17.03.2009
  Time: 13:40:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/skins/list"  onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="skinsList">
		<tiles:put name="submenu" type="string" value="ManageSkins"/>

	    <tiles:put name="menu" type="string">
		    <tiles:insert definition="clientButton" flush="false" operation="EditSkins">
			    <tiles:put name="commandTextKey" value="button.add"/>
			    <tiles:put name="commandHelpKey" value="button.add"/>
			    <tiles:put name="bundle" value="commonBundle"/>
			    <tiles:put name="image" value=""/>
			    <tiles:put name="action" value="/skins/edit"/>
                <tiles:put name="viewType" value="blueBorder"/>
		    </tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">

			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/skins/edit')}";
				function doEdit()
				{
                    checkIfOneItem("selectedIds");
					if (!checkOneSelection("selectedIds", "Выберите один стиль!"))
						return;
					var id = getRadioValue(document.getElementsByName("selectedIds"));
					window.location = addUrl + "?id=" + id;
				}
			</script>

			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="skinsList"/>
				<tiles:put name="text" value="Список доступных стилей"/>
				<tiles:put name="buttons">

					<tiles:insert definition="clientButton" flush="false" operation="EditSkins">
						<tiles:put name="commandTextKey" value="button.edit"/>
						<tiles:put name="commandHelpKey" value="button.edit"/>
						<tiles:put name="bundle" value="commonBundle"/>
						<tiles:put name="onclick" value="doEdit()"/>
					</tiles:insert>

					<tiles:insert definition="commandButton" flush="false" operation="DeleteSkins">
						<tiles:put name="commandKey" value="button.remove"/>
						<tiles:put name="commandHelpKey" value="button.remove"/>
						<tiles:put name="bundle" value="commonBundle"/>
						<tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите стиль!');
                            }
                        </tiles:put>
						<tiles:put name="confirmText"    value="Вы действительно хотите удалить выбранные стили?"/>
					</tiles:insert>

				</tiles:put>
				<tiles:put name="grid">
					<c:set var="canEdit" value="${phiz:impliesOperation('EditSkins','EditSkinsOperation')}"/>
					<sl:collection id="item" bundle="skinsBundle" property="data" model="list" selectType="checkbox" selectName="selectedIds" selectProperty="id">
						<sl:collectionItem title="label.name" property="name">
							<sl:collectionItemParam id="action" value="/skins/edit.do?id=${item.id}" condition="${canEdit}"/>
						</sl:collectionItem>
						<sl:collectionItem title="label.type">
							<c:choose>
								<c:when test="${not empty item.systemName}">
									<bean:message key="label.skin.category.system" bundle="skinsBundle"/>
								</c:when>
								<c:otherwise>
									 <bean:message key="label.skin.category.user" bundle="skinsBundle"/>
								</c:otherwise>
							</c:choose>
						</sl:collectionItem>
						<sl:collectionItem title="label.activity">
							<c:set var="clientActive" value="0"/>
							<c:if test="${item.clientDefaultSkin}">
								<bean:message key="lable.application.type.client" bundle="skinsBundle"/>
								<c:set var="clientActive" value="1"/>
							</c:if>
							<c:if test="${item.adminDefaultSkin}">
								<c:if test="${clientActive==1}"></br></c:if>
								<bean:message key="lable.application.type.admin" bundle="skinsBundle"/>
							</c:if>
						</sl:collectionItem>
						<sl:collectionItem title="label.path" property="url"/>
					</sl:collection>
				</tiles:put>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>