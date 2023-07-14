<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loanClaim/create" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="LoanClaimFind">
    <tiles:put name="submenu" type="string" value="LoanClaimFind"/>
    <tiles:put name="data" type="string">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var ="seriesText" value="Серия"/>
    <c:set var ="numberText" value="Номер"/>
    <tiles:importAttribute/>
    <script type="text/javascript">
        function clearDocumentSeriesAndNumber()
        {
            $(getField("documentSeries")).val('');
            $(getField("documentSeries")).blur();

            $(getField("documentNumber")).val('');
            $(getField("documentNumber")).blur();
        }

        function changeDocumentType()
        {
            var elem = document.getElementById("field(documentType)");
            if (elem != null)
            {
                switch (elem.value)
                {
                    case "OTHER" :
                    {
                        hideOrShow("documentName", false);
                        return;
                    }
                    default:
                    {
                        hideOrShow("documentName", true);
                        return;
                    }
                }
            }
        }
    </script>
    <c:if test="${empty form.activePersons}">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="editPerson"/>
            <tiles:put name="name"><bean:message key="person.search.form.name" bundle="personsBundle"/></tiles:put>
            <tiles:put name="description"><bean:message key="person.data.search.form.description" bundle="personsBundle"/></tiles:put>
            <tiles:put name="alignTable" value="center"/>
            <tiles:put name="data">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="person.search.field.surName" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(surName)" styleId="field(surName)" size="40" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="person.search.field.firstName" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(firstName)" styleId="field(firstName)" size="40" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="person.search.field.patrName" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(patrName)" styleId="field(patrName)" size="40" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>


                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="person.search.field.documentType" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:select property="field(documentType)" styleId="field(documentType)" onchange="clearDocumentSeriesAndNumber();changeDocumentType()" styleClass="select" style="width:500px">
                            <c:forEach var="documentType" items="${form.documentTypes}">
                                <html:option value="${documentType}" styleClass="text-grey">
                                    <bean:message key="document.type.${documentType}" bundle="personsBundle"/>
                                </html:option>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>

                <div id="documentName">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="person.search.field.documentName" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(documentName)" styleId="field(documentName)" size="80" styleClass="contactInput"/>
                        </tiles:put>
                    </tiles:insert>

                </div>
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">&nbsp;</tiles:put>
                    <tiles:put name="data">
                        <tiles:insert definition="textField" flush="false">
                            <tiles:put name="name" value="field(documentSeries)"/>
                            <tiles:put name="isDefault" value="${seriesText}"/>
                            <tiles:put name="size" value="10"/>
                        </tiles:insert>
                        <tiles:insert definition="textField" flush="false">
                            <tiles:put name="name" value="field(documentNumber)"/>
                            <tiles:put name="isDefault" value="${numberText}"/>
                            <tiles:put name="size" value="10"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="person.search.field.birthDay" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <input type="text"
                               id="field(birthDay)"
                               name="field(birthDay)"
                               class="dot-date-pick contactInput"
                               value='<bean:write name="form" property="fields.birthDay" format="dd.MM.yyyy"/>'
                               size="10"
                                />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">&nbsp;</tiles:put>
                    <tiles:put name="data"><div id="officeName"></div></tiles:put>
                </tiles:insert>

                <c:if test="${form.guestPerson}">
                    <script type="text/javascript">
                        doOnLoad(function()
                        {
                            if (confirm("Клиент не найден в ЕРИБ. Оформить гостевую заявку?")) {
                                var button = createCommandButton('button.withGuest', '');
                                button.click('', false);
                            }
                        });
                    </script>

                </c:if>
                <script type="text/javascript">

                    changeDocumentType();

                    function clearClientDetailFields()
                    {
                        ensureElement("field(surName)").value = '';
                        ensureElement("field(firstName)").value = '';
                        ensureElement("field(patrName)").value = '';
                        ensureElement("field(documentType)").value = 'REGULAR_PASSPORT_RF';
                        ensureElement("field(documentName)").value = '';
                        ensureElement("field(birthDay)").value = '';
                        ensureElement("field(region)").value='';
                        $("#officeName").text("");
                        clearDocumentSeriesAndNumber();
                        changeDocumentType();
                    }
                </script>

            </tiles:put>
            <%--кнопки--%>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"    value="person.search.button.clearTextKey"/>
                    <tiles:put name="commandHelpKey"    value="person.search.button.clearHelpTextKey"/>
                    <tiles:put name="bundle"            value="personsBundle"/>
                    <tiles:put name="onclick"           value="clearClientDetailFields()"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandTextKey"    value="button.search"/>
                    <tiles:put name="commandHelpKey"    value="person.search.button.searchCommandHelpKey"/>
                    <tiles:put name="bundle"            value="personsBundle"/>
                    <tiles:put name="commandKey" value="button.preSearch"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </c:if>

    <c:if test="${not empty form.activePersons}">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="personList"/>
            <tiles:put name="grid">
                <sl:collection id="item" model="list" property="activePersons" bundle="personsBundle" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                    <sl:collectionItem title="ФИО">
                            <a onclick="onChooseClient(${item.id})">${item.surname} ${item.firstname} ${item.patrname}</a>
                    </sl:collectionItem>
                    <sl:collectionItem title="Документ" property="document"/>
                    <sl:collectionItem title="Дата рождения">
                        ${phiz:formatDateToStringOnPattern(item.birthday, 'dd.MM.yyyy')}
                    </sl:collectionItem>
                    <sl:collectionItem title="ТБ" property="tb"/>
                </sl:collection>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.activePersons}"/>
        </tiles:insert>
        <input type="hidden" name="clientId" id="clientId">
        <script type="text/javascript">
            function onChooseClient(clientId)
            {
                ensureElement("clientId").value=clientId;
                var button = createCommandButton('button.chooseClient', '');
                button.click('', false);
            }
        </script>
    </c:if>
</tiles:put>
</tiles:insert>
</html:form>
