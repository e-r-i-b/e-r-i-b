<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/promocodes/list" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="configEdit">
        <c:set var="bundle" value="promocodesBundle"/>
        <tiles:put name="submenu" type="string" value="PromoCodeSettings"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="${bundle}" key="page.list.title"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="action" value="/promocodes/edit"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">

			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/promocodes/edit')}";
				function doEdit()
				{
                    checkIfOneItem("selectedIds");
					if (!checkOneSelection("selectedIds", "Выберите одну настройку!"))
						return;
					var id = getRadioValue(document.getElementsByName("selectedIds"));
					window.location = addUrl + "?id=" + id;
				}
			</script>

			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="promoCodesList"/>
				<tiles:put name="buttons">

					<tiles:insert definition="clientButton" flush="false" operation="EditPromoCodeSettingsOperation">
						<tiles:put name="commandTextKey" value="button.edit"/>
						<tiles:put name="commandHelpKey" value="button.edit"/>
						<tiles:put name="bundle" value="commonBundle"/>
						<tiles:put name="onclick" value="doEdit()"/>
					</tiles:insert>

					<tiles:insert definition="commandButton" flush="false" operation="RemovePromoCodeSettingsOperation">
						<tiles:put name="commandKey" value="button.remove"/>
						<tiles:put name="commandHelpKey" value="button.remove"/>
						<tiles:put name="bundle" value="commonBundle"/>
						<tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите настройки!');
                            }
                        </tiles:put>
						<tiles:put name="confirmText"><bean:message bundle="${bundle}" key="confirmRemove"/></tiles:put>
					</tiles:insert>

				</tiles:put>
                <tiles:put name="data">
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="data">
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectName" value="selectedIds"/>
                            <sl:collectionParam id="selectProperty" value="id"/>

                            <sl:collectionItem title="Период" >
                                <c:if test="${not empty listElement.startDate}">
                                    с <bean:write name="listElement" property="startDate.time" format="dd.MM.yyyy HH:mm"/>
                                    <c:choose>
                                        <c:when test="${not empty listElement.endDate}">
                                            по <bean:write name="listElement" property="endDate.time" format="dd.MM.yyyy HH:mm"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message bundle="${bundle}" key="label.termless"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem title="Тербанк">${listElement.terbank.name}</sl:collectionItem>
                        </sl:collection>
                    </tiles:put>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">Настроек нет!</tiles:put>
		    </tiles:insert>
		</tiles:put>
    </tiles:insert>
</html:form>