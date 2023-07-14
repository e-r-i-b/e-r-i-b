<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/person/search" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="submenu" type="string" value="SearchClient"/>
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

                function changeIdentityType()
                {
                    var identityType = document.getElementsByName("field(identityType)");
                    var enable = identityType[0].checked;

                    disableOrEnableElem("field(surName)",           enable);
                    disableOrEnableElem("field(firstName)",         enable);
                    disableOrEnableElem("field(patrName)",          enable);
                    disableOrEnableElem("field(documentType)",      enable);
                    disableOrEnableElem("field(documentName)",      enable);
                    disableOrEnableElem("field(documentSeries)",    enable);
                    disableOrEnableElem("field(documentNumber)",    enable);
                    disableOrEnableElem("field(birthDay)",          enable);
                    disableOrEnableElem("field(region)",            enable);

                    if (enable)
                        $("#officeSelectButton").hide();
                    else
                        $("#officeSelectButton").show();
                }
            </script>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editPerson"/>
                <tiles:put name="name"><bean:message key="person.search.form.name" bundle="personsBundle"/></tiles:put>
                <tiles:put name="description"><bean:message key="person.search.form.description" bundle="personsBundle"/></tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="data">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title"><bean:message bundle="personsBundle" key="person.search.identity.type"/></tiles:put>
                        <tiles:put name="data">
                            <html:radio property="field(identityType)" onclick="changeIdentityType()" style="border:0" value="by_card"/>
                            <bean:message bundle="personsBundle" key="person.search.card.identity"/>
                            <br/>
                            <html:radio property="field(identityType)" onclick="changeIdentityType()" style="border:0" value="by_identity_document" />
                            <bean:message bundle="personsBundle" key="person.search.document.identity"/>
                        </tiles:put>
                    </tiles:insert>

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
                            <div>
                                Введите отчество клиента. Если у клиента есть отчество, то для поиска его обязательно необходимо указать. Обратите внимание, если у клиента нет отчества, его вводить не нужно.
                            </div>
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
                        <tiles:put name="title">
                            <bean:message key="person.search.field.tb" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="needAdditionalSearch" value="${phiz:impliesServiceRigid('SeachClientsByTB')}"/>
                            <table cellpadding="0" cellspacing="0" border="0"><tr>
                                <td>
                                    <nobr>
                                        <c:set var="regionForSearch" value="${form.fields.region}"/>
                                        <c:if test="${empty regionForSearch and not needAdditionalSearch}">
                                            <c:set var="allowedTB" value="${phiz:getAllowedTerbanks()}"/>
                                            <c:if test="${phiz:size(allowedTB) > 0}">
                                                <c:set var="regionForSearch" value="${allowedTB[0].region}"/>
                                            </c:if>
                                        </c:if>
                                        <html:text property="field(region)" styleId="regionForSearch" size="4" maxlength="2" readonly="${not needAdditionalSearch}" value="${regionForSearch}"/>
                                        <input class="buttWhite smButt" id="officeSelectButton" type="button" value="..." onclick="openAllowedTBDictionary(setDepartmentInfo, true)" <c:if test="${!needAdditionalSearch}">disabled</c:if/> />
                                    </nobr>
                                </td>
                                <td>
                                    <c:if test="${needAdditionalSearch}">
                                        Укажите территориальный банк, в котором обслуживается клиент.
                                    </c:if>
                                </td>
                            </tr></table>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">&nbsp;</tiles:put>
                        <tiles:put name="data"><div id="officeName"></div></tiles:put>
                    </tiles:insert>

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
                            $("[name='field(region)']").val('');
                            $("#officeName").text("");
                            clearDocumentSeriesAndNumber();
                            changeDocumentType();
                        }

                        function setDepartmentInfo(resource)
                        {
                            $("[name='field(region)']").val(resource['region']);
                            $("#officeName").text(resource['name']);
                        }

                        function search()
                        {
                            var identityType = document.getElementsByName("field(identityType)");
                            if (identityType[0].checked)
                            {
                                window.location = "${phiz:calculateActionURL(pageContext,'/person/search/card')}";
                            }
                            else if (identityType[1].checked)
                            {
                                createCommandButton('button.search', 'Найти').click('', false);
                            }
                            else
                            {
                                alert("Заданы некорректные условия поиска.");
                            }
                        }
                    </script>

                </tiles:put>
                <%--кнопки--%>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" service="AutoSubscriptionManagment" flush="false">
                        <tiles:put name="commandTextKey"    value="person.search.button.clearTextKey"/>
                        <tiles:put name="commandHelpKey"    value="person.search.button.clearHelpTextKey"/>
                        <tiles:put name="bundle"            value="personsBundle"/>
                        <tiles:put name="onclick"           value="clearClientDetailFields()"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" service="AutoSubscriptionManagment" flush="false">
                        <tiles:put name="commandTextKey"    value="button.search"/>
                        <tiles:put name="commandHelpKey"    value="person.search.button.searchCommandHelpKey"/>
                        <tiles:put name="bundle"            value="personsBundle"/>
                        <tiles:put name="isDefault"           value="true"/>
                        <tiles:put name="onclick">
                            search();
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
