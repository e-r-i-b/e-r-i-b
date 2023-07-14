<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <%--
    accessService - сервис доступа
    --%>

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="id" value="editPerson"/>
        <tiles:put name="name"><bean:message key="person.search.form.name" bundle="personsBundle"/></tiles:put>
        <tiles:put name="description"><bean:message key="person.search.form.description.pfp" bundle="personsBundle"/></tiles:put>
        <tiles:put name="data">

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="person.search.field.surName" bundle="personsBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:text property="field(surName)" styleId="field(surName)" size="40" styleClass="contactInput"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="person.search.field.firstName" bundle="personsBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:text property="field(firstName)" styleId="field(firstName)" size="40" styleClass="contactInput"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="person.search.field.patrName" bundle="personsBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(patrName)" styleId="field(patrName)" size="40" styleClass="contactInput"/>
                    <div> Введите отчество клиента. Если у клиента есть отчество, то для поиска его обязательно необходимо указать. Обратите внимание, если у клиента нет отчества, его вводить не нужно.</div>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
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
            <c:set var="documentNameVisibilityStyle" value=""/>
            <c:if test="${form.fields['documentType'] ne 'OTHER'}">
                <c:set var="documentNameVisibilityStyle" value="display: none;"/>
            </c:if>
            <div id="documentName" style="${documentNameVisibilityStyle}">
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="person.search.field.documentName" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(documentName)" styleId="field(documentName)" size="59" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>
            </div>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="data">
                    <tiles:insert definition="textField" flush="false">
                        <tiles:put name="name" value="field(documentSeries)"/>
                        <tiles:put name="isDefault" value="Серия"/>
                        <tiles:put name="size" value="10"/>
                        <tiles:put name="style" value="personDocumentData"/>
                    </tiles:insert>
                    <tiles:insert definition="textField" flush="false">
                        <tiles:put name="name" value="field(documentNumber)"/>
                        <tiles:put name="isDefault" value="Номер"/>
                        <tiles:put name="size" value="10"/>
                        <tiles:put name="style" value="personDocumentData"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
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

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="person.search.field.tb" bundle="personsBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <c:set var="needAdditionalSeach" value="${phiz:impliesServiceRigid('SeachClientsByTB')}"/>
                    <div class="inlineBlock paddBottom10">
                        <c:set var="regionForSearch" value="${form.fields.region}"/>
                        <c:if test="${empty regionForSearch and not needAdditionalSeach}">
                            <c:set var="allowedTB" value="${phiz:getAllowedTerbanks()}"/>
                            <c:if test="${phiz:size(allowedTB) > 0}">
                                <c:set var="regionForSearch" value="${allowedTB[0].region}"/>
                            </c:if>
                        </c:if>
                        <html:text property="field(region)" styleId="regionForSearch" size="4" maxlength="2" readonly="${not needAdditionalSeach}" value="${regionForSearch}"/>
                        <input class="buttWhite smButt" id="officeSelectButton" type="button" value="..." onclick="openAllowedTBDictionary(setDepartmentInfo, ${accessService=='EmployeePfpEditService'});" <c:if test="${not needAdditionalSeach}">disabled</c:if/> />
                    </div>
                    <c:if test="${needAdditionalSeach}">
                        <span class="inlineBlock">Укажите территориальный банк, в котором обслуживается клиент.</span>
                    </c:if>
                    <html:hidden styleId="regionNameForSearch" property="field(regionName)"/>
                    <span id="officeName" class="displayBlock"><c:out value="${form.fields.regionName}" default=""/></span>
                </tiles:put>
            </tiles:insert>

            <script type="text/javascript">
                function clearDocumentSeriesAndNumber()
                {
                    $('.personDocumentData').each(function(i,e)
                    {
                        $(e).val('');
                        $(e).blur();
                    });
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

                function clearClientDetailFields()
                {
                    $("[name=field(surName)]").val('');
                    $("[name=field(firstName)]").val('');
                    $("[name=field(patrName)]").val('');
                    $("[name=field(documentType)]").val('REGULAR_PASSPORT_RF');
                    $("[name=field(documentName)]").val('');
                    $("[name=field(birthDay)]").val('');
                    <c:if test="${needAdditionalSeach}">
                        $("[name=field(regionName)]").val('');
                        $("[name=field(region)]").val('');
                        $("#officeName").text('');
                    </c:if>
                    clearDocumentSeriesAndNumber();
                    changeDocumentType();
                }

                function setDepartmentInfo(resource)
                {
                    $("#regionForSearch").val(resource['region']);
                    $("#regionNameForSearch").val(resource['name']);
                    $("#officeName").text(resource['name']);
                }
            </script>

        </tiles:put>
        <%--кнопки--%>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" service="${accessService}" flush="false">
                <tiles:put name="commandTextKey"    value="person.search.button.clearTextKey"/>
                <tiles:put name="commandHelpKey"    value="person.search.button.clearHelpTextKey"/>
                <tiles:put name="bundle"            value="personsBundle"/>
                <tiles:put name="onclick"           value="clearClientDetailFields()"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" service="${accessService}" flush="false">
                <tiles:put name="commandKey"        value="button.search"/>
                <tiles:put name="commandTextKey"    value="person.search.button.searchCommandTextKey"/>
                <tiles:put name="commandHelpKey"    value="person.search.button.searchCommandHelpKey"/>
                <tiles:put name="bundle"            value="personsBundle"/>
                <tiles:put name="isDefault"           value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:html>
