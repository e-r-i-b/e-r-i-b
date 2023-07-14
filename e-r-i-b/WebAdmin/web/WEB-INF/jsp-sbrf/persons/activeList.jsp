<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<% pageContext.getRequest().setAttribute("mode", "Users");%>
<html:form action="/persons/activeList">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="dictionary">
<tiles:put name="submenu" type="string" value="PersonList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="personsBundle"/>
</tiles:put>
<tiles:put name="menu" type="string">
	<nobr>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel.help"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="onclick" value="window.close()"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</nobr>
</tiles:put>

<tiles:put name="filter" type="string">
    <tiles:put name="fastSearchFilter" value="true"/>
    <c:set var="colCount" value="3" scope="request"/>
    <%--  row 1 --%>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.client"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="fio"/>
        <tiles:put name="size"   value="50"/>
        <tiles:put name="maxlength"  value="255"/>
        <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
	</tiles:insert>

	<tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label"  value="label.agreementType"/>
        <tiles:put name="bundle" value="personsBundle"/>
         <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            <html:select property="filter(creationType)" styleClass="select">
                <html:option value="">Все</html:option>
				<html:option value="UDBO">УДБО</html:option>
				<html:option value="SBOL">СБОЛ</html:option>
				<html:option value="CARD">Подключен по карте</html:option>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.personId"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name" value="loginId"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="maxlength" value="16"/>
        <tiles:put name="isFastSearch" value="true"/>
    </tiles:insert>

    <%-- row 2 --%>
    <tiles:insert definition="filter2TextField" flush="false">
        <tiles:put name="label" value="label.document"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name"   value="documentSeries"/>
        <tiles:put name="size"   value="5"/>
        <tiles:put name="maxlength"  value="16"/>
        <tiles:put name="isDefault" value="Серия"/>
        <tiles:put name="name2"   value="documentNumber"/>
        <tiles:put name="size2"   value="10"/>
        <tiles:put name="maxlength2"  value="16"/>
        <tiles:put name="default2" value="Номер"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.tb"/>
        <tiles:put name="bundle" value="personsBundle"/>
         <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            <html:select property="filter(terBank)" styleClass="select">
                <html:option value="">Все</html:option>
                <c:forEach var="tb" items="${ListPersonsForm.allowedTB}">
                    <html:option value="${tb.code.region}">
                        <c:out value="${tb.name}"/>
                    </html:option>
                </c:forEach>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.status"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            <html:select property="filter(state)" styleClass="select">
                <html:option value="0">Активный</html:option>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <%-- row 3 --%>
    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthDay"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="birthDay"/>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
	    <tiles:put name="label" value="label.agreementNumber"/>
	    <tiles:put name="bundle" value="personsBundle"/>
	    <tiles:put name="name" value="agreementNumber"/>
	    <tiles:put name="maxlength" value="16"/>
        <tiles:put name="isFastSearch" value="true"/>
    </tiles:insert>

    <tiles:insert definition="filterEmptytField" flush="false">
    </tiles:insert>

    <%-- row 4 --%>
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.mobilePhone"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="mobile_phone"/>
        <tiles:put name="maxlength" value="20"/>
        <tiles:put name="isFastSearch" value="true"/>
    </tiles:insert>

	<script type="text/javascript">
		function initTemplates()
		{
			//setInputTemplate('passportSeries', PASSPORT_SERIES_TEMPLATE);
			//setInputTemplate('passportNumber', PASSPORT_NUMBER_TEMPLATE);
		}

		initTemplates();
		function sendClientInfo(event)
		{
		    var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			for (i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var a = new Array(2);
					a['name'] = trim(r.cells[1].innerHTML);
					a['id'] = ids.item(i).value;
					a['loginId'] = $("#"+a['id']).val();
					window.opener.setGroupData(a);
					window.close();
					return;
				}
			}
			alert("Выберите клиента.");
		}
        <c:if test="${form.fromStart}">
            //показываем фильтр при старте
            doOnLoad(switchFilter, this);
        </c:if>
	</script>
</tiles:put>
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>        
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="personList"/>
        <tiles:put name="text"  value=""/>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.select"/>
                <tiles:put name="commandHelpKey" value="button.select.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="onclick" value="sendClientInfo(event)"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" bundle="personsBundle" selectBean="person">
                <sl:collectionParam id="selectType" value="radio"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>
                <sl:collectionParam id="onRowDblClick"  value="sendClientInfo(event);"/>

                <c:set var="person" value="${listElement[0]}"/>
                <c:set var="login" value="${listElement[1]}"/>                
                <c:set var="loginId" value="${person.login.id}"/>                

                <sl:collectionItem title="label.FIO">
                    ${person.surName} ${person.firstName} ${person.patrName}
                </sl:collectionItem>
                <sl:collectionItem title="Номер договора">
                    ${person.agreementNumber}&nbsp;
                    <input type="hidden" id="${person.id}" value="${loginId}"/>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>

        <tiles:put name="isEmpty" value="${empty ListPersonsForm.data}"/>
        <tiles:put name="emptyMessage">
            <c:choose>
                <c:when test="${form.fromStart}">
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
