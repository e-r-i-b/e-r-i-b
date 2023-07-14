<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 13.11.2006
  Time: 17:05:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/groups/personDictionary">
<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
	<bean:message key="personList.title" bundle="groupsBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function sendPersonData(event)
		{
			var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			var idString = "";
			for (var i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					idString = idString + ids.item(i).value + ";";
				}
			}

			if (idString == "")
				alert("Выберите пользователя.");
			else
			{
				window.opener.setPersons(idString);
				window.close();
				return;
			}
		}
	</script>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey"     value="button.choose"/>
        <tiles:put name="commandHelpKey" value="button.choose"/>
        <tiles:put name="bundle"  value="commonBundle"/>
        <tiles:put name="image"   value=""/>
        <tiles:put name="onclick" value="sendPersonData(event)"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey"     value="button.return"/>
        <tiles:put name="commandHelpKey" value="button.return"/>
        <tiles:put name="bundle"  value="commonBundle"/>
        <tiles:put name="image"   value=""/>
        <tiles:put name="onclick" value="window.close();"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.surName"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="surName"/>
        <tiles:put name="maxlength" value="42"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.documentType"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<html:select property="filter(documentType)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="REGULAR_PASSPORT_RF">Общегражданский паспорт РФ</html:option>
				<html:option value="MILITARY_IDCARD">Удостоверение личности военнослужащего</html:option>
				<html:option value="SEAMEN_PASSPORT">Паспорт моряка</html:option>
				<html:option value="RESIDENTIAL_PERMIT_RF">Вид на жительство РФ</html:option>
				<html:option value="FOREIGN_PASSPORT_RF">Заграничный паспорт РФ</html:option>
				<html:option value="OTHER">Иной документ</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.pinEnvelopeNumber"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="pinEnvelopeNumber)"/>
		<tiles:put name="maxlength" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.firstName"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="firstName"/>
        <tiles:put name="maxlength" value="42"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.documentSeries"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="documentSeries"/>
		<tiles:put name="maxlength" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.agreementNumber"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="agreementNumber"/>
		<tiles:put name="name" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.patrName"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="patrName"/>
        <tiles:put name="maxlength" value="42"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.documentNumber"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="documentNumber"/>
		<tiles:put name="maxlength" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.status"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="filterSelect" style="width:100px">
				<html:option value="">Все</html:option>
				<html:option value="0">Активный</html:option>
				<html:option value="1">Заблокирован</html:option>
				<html:option value="T">Подключение</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>

	<script type="text/javascript">
		function initTemplates()
		{
		}

		initTemplates();
        <c:if test="${ShowPersonDictionaryForm.fromStart}">
            switchFilter(this);
        </c:if>
	</script>
</tiles:put>
<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="tableTemplate" flush="false">
        <input type="hidden" name="groupId" value="${ShowPersonDictionaryForm.groupId}"/>
        <tiles:put name="id" value=""/>
        <tiles:put name="text" value=""/>
        <tiles:put name="grid">
            <sl:collection model="list" property="data" id="listElement" bundle="personsBundle" selectBean="person">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="person" value="${listElement[1]}"/>
                <c:set var="login" value="${listElement[2]}"/>
                <c:set var="scheme" value="${listElement[3]}"/>
                <c:set var="status" value="${person.status}"/>
                <c:set var="blocksId" value="${listElement[0]}"/>

                <sl:collectionItem title="label.FIO">
                    <c:if test="${status == 'A'}">
                        <c:if test="${not empty blocksId}">
                            <img src="${imagePath}/iconSm_lock.gif"
                                 width="12px" height="12px" alt="" border="0"/>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty person}">
                        <bean:write name="person" property="surName"/>&nbsp;
                        <bean:write name="person" property="firstName"/>&nbsp;
                        <bean:write name="person" property="patrName"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="ПИН-конверт" name="login" property="userId"/>
                <sl:collectionItem title="Номер договора" name="person" property="agreementNumber"/>
                <sl:collectionItem title="Схема прав доступа" name="scheme" property="name">
                    <sl:collectionItemParam id="styleClass" value="errorText" condition="${empty scheme || scheme.name=='personal'}"/>
                    <sl:collectionItemParam id="value" value="Нет схемы прав" condition="${empty scheme}"/>
                    <sl:collectionItemParam id="value" value="Индивидуальные права" condition="${scheme.name=='personal'}"/>
                </sl:collectionItem>
                <sl:collectionItem title="Статус">
                    <sl:collectionItemParam id="value" value="Активный" condition="${status=='A' && (empty blocksId)}"/>
                    <sl:collectionItemParam id="value" value="Подключение" condition="${status=='T'}"/>
                    <sl:collectionItemParam id="value" value="На расторжении" condition="${status=='W'}"/>
                    <sl:collectionItemParam id="value" value="Ошибка расторжения" condition="${status=='E'}"/>
                    <sl:collectionItemParam id="value" value="Подписание заявления" condition="${status=='S'}"/>
                    <sl:collectionItemParam id="value" value="Заблокирован" condition="${status!='T' && status!='W' && status!='E' && status!='S' && (not empty blocksId)}"/>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>

        <tiles:put name="isEmpty" value="${empty ShowPersonDictionaryForm.data}"/>
        <tiles:put name="emptyMessage">
        <c:choose>
            <c:when test="${ShowPersonDictionaryForm.fromStart}">
                Для поиска клиентов в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
            </c:when>
            <c:otherwise>
                Не найдено ни одного клиента, <br/>соответствующего заданному фильтру!
            </c:otherwise>
        </c:choose>
    </tiles:put>
    </tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
