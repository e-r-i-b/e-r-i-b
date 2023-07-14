<%--
  User: Omeliyanchuk
  Date: 10.11.2006
  Time: 15:56:57
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/groups/edit" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<c:if test="${form.category=='A'}">
		<c:set var="def" value="employersGroup"/>
        <c:set var="canEdit" value="${false}"/>
	</c:if>
	<c:if test="${form.category=='C'}">
		<c:set var="def" value="personGroup"/>
        <c:set var="canEdit" value="${phiz:impliesOperation('EditGroupOperationC','PersonGroupManagement')}"/>
	</c:if>

	<tiles:insert definition="${def}">
		<tiles:put name="submenu" type="string" value="General"/>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle" value="groupsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/groups/list.do?category=${form.category}"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<input type="hidden" name="category" value="${form.category}"/>
            <script type="text/javascript">
                function defaultStyle ()
                {
                    var useDefault = getRadioValue(document.getElementsByName("field(useDefaultStyle)"));
                    var defaultStyle = getElement("field(defaultStyle)");
                    defaultStyle.disabled = useDefault != "true";
                }

                doOnLoad (function(){ defaultStyle (); });
            </script>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="editGroups"/>
				<tiles:put name="name" value="Редактирование группы"/>
				<tiles:put name="description" value="Используйте данную форму редактирования данных группы."/>
				<tiles:put name="data">
                    <div class="form-row">
                        <div class="paymentLabel">
                            Наименование<span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(name)" maxlength="25" disabled="${!canEdit}" styleClass="inputWidth"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            Описание
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(description)" maxlength="250" disabled="${!canEdit}" styleClass="inputWidth"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            Стиль по умолчанию
                        </div>
                        <div class="paymentValue">
                            <html:radio property="field(useDefaultStyle)" value="false" onclick="defaultStyle();" disabled="${!canEdit}"/> Не использовать
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel"></div>
                        <div class="paymentValue">
                            <html:radio property="field(useDefaultStyle)" value="true" onclick="defaultStyle();" disabled="${!canEdit}"/> Применять
                            <html:select property="field(defaultStyle)" disabled="${!canEdit}">
                                <c:forEach var="skin" items="${form.skins}">
                                    <html:option value="${skin.id}">${skin.name}</html:option>
                                </c:forEach>
                            </html:select>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            Приоритет
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(priority)" size="10" maxlength="9" disabled="${!canEdit}"/>
                        </div>
                        <div class="clear"></div>
                    </div>
				</tiles:put>
				<tiles:put name="buttons">
                    <c:if test="${canEdit}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                            <tiles:put name="bundle"  value="groupsBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                        </tiles:insert>
                    </c:if>
				</tiles:put>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>