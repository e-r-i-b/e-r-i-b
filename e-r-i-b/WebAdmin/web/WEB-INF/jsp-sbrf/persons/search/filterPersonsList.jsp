<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%--Фильтр в "Клиенты" и "Клиенты-Группы-Список клиентов" и добавление клиента в группу--%>
<%--  row 1 --%>
<%--Клиент--%>
<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.client"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="size"   value="50"/>
    <tiles:put name="maxlength"  value="255"/>
    <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
    <tiles:put name="name" value="fio"/>
</tiles:insert>

<%--Тип договора--%>
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

<%--Login_ID--%>
<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.personId"/>
    <tiles:put name="bundle" value="logBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="maxlength" value="16"/>
    <tiles:put name="isFastSearch" value="true"/>
    <tiles:put name="name" value="loginId"/>
</tiles:insert>

<%-- row 2 --%>
<%--Документ--%>
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

<%--ТБ--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="filterEntryField" flush="false">
    <tiles:put name="label" value="label.tb"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="isFastSearch" value="true"/>
    <tiles:put name="data">
        <html:select property="filter(terBank)" styleClass="select">
            <html:option value="">Все</html:option>
            <c:forEach var="tb" items="${form.allowedTB}">
                <html:option value="${tb.code.region}">
                    <c:out value="${tb.name}"/>
                </html:option>
            </c:forEach>
        </html:select>
    </tiles:put>
</tiles:insert>

<%--Статус--%>
<tiles:insert definition="filterEntryField" flush="false">
    <tiles:put name="label" value="label.status"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="isFastSearch" value="true"/>
    <tiles:put name="data">
        <html:select property="filter(state)" styleClass="select">
            <html:option value="">Все</html:option>
            <html:option value="0">Активный</html:option>
            <html:option value="1">Заблокирован</html:option>
            <html:option value="T">Подключение</html:option>
            <html:option value="E">Ошибка расторжения</html:option>
            <html:option value="W">На расторжении</html:option>
            <html:option value="S">Подписание заявления</html:option>
        </html:select>
    </tiles:put>
</tiles:insert>

<%-- row 3 --%>
<%--Дата рождения--%>
<tiles:insert definition="filterDateField" flush="false">
    <tiles:put name="label" value="label.birthDay"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="name" value="birthDay"/>
</tiles:insert>

<%--Номер заявления--%>
<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.agreementNumber"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="maxlength" value="16"/>
    <tiles:put name="isFastSearch" value="true"/>
    <tiles:put name="name" value="agreementNumber"/>
</tiles:insert>

<tiles:insert definition="filterEmptytField" flush="false"></tiles:insert>

<%-- row 4 --%>
<%--Мобильный телефон--%>
<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.mobilePhone"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="maxlength" value="20"/>
    <tiles:put name="isFastSearch" value="true"/>
    <tiles:put name="name" value="mobile_phone"/>
</tiles:insert>