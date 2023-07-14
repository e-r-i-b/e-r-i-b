<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <%--
    formName - имя формы
    formDescription - описание формы
    needCloseButton - нужна ли кнопка "Закрыть"
    closeButtonFunction - действие кнопки "Закрыть"
    --%>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <script type="text/javascript">
        var personDocuments = new Array();

        function changeDocument(type)
        {
            for (var i=0; i<personDocuments.length; i++)
            {
                if (type != personDocuments[i].type)
                    continue;

                getElement("field(documentName)").innerHTML   = personDocuments[i].name;

                if (type == 'PASSPORT_WAY')
                {
                    getElement("field(documentSeries)").innerHTML = personDocuments[i].series.substr(0, 4);
                    getElement("field(documentNumber)").innerHTML = personDocuments[i].series.substr(4);
                }
                else
                {
                    getElement("field(documentSeries)").innerHTML = personDocuments[i].series;
                    getElement("field(documentNumber)").innerHTML = personDocuments[i].number;
                }

                if (type == "OTHER")
                    $('#documentName').show();
                else
                    $('#documentName').hide();

            }
        }

        function initPerson()
        {
            <c:if test="${form.personDocuments != null}">
                var doc;
                <logic:iterate id="personDocument" name="SearchPersonForm" property="personDocuments">
                    doc             = new Object();
                    doc.id          = '${personDocument.id}';
                    doc.name        = '${personDocument.documentName}';
                    doc.number      = '${personDocument.documentNumber}';
                    doc.series      = '${personDocument.documentSeries}';
                    doc.type        = '${personDocument.documentType}';
                    personDocuments[personDocuments.length] = doc;
                </logic:iterate>
            </c:if>
        }
        initPerson();

    </script>
    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="id" value="editPerson"/>
        <tiles:put name="name">
            ${formName}
        </tiles:put>
        <tiles:put name="description">
            ${formDescription}
        </tiles:put>
        <tiles:put name="alignTable" value="center"/>
        <tiles:put name="data">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.login_id" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data"><bean:write name="form" property="activePerson.login.id"/></tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.surName" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data"><bean:write name="form" property="field(surName)"/></tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.firstName" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data"><bean:write name="form" property="field(firstName)"/></tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.patrName" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data"><bean:write name="form" property="field(patrName)"/></tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.birthDay" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data"><bean:write name="form" property="fields.birthDay" format="dd.MM.yyyy"/></tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.citizenship" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data"><bean:write name="form" property="activePerson.citizenship"/></tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.documentType" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data">
                    <input type="hidden" name="field(documentId)" id="field(documentId)"  value='<bean:write name="form"  property="fields.documentId"/>'/>

                    <html:select property="field(documentType)" styleId="field(documentType)" onchange="changeDocument(getElementValue('field(documentType)'))" styleClass="select">
                        <logic:iterate id="personDocument" name="SearchPersonForm" property="personDocuments">
                            <html:option value="${personDocument.documentType}" styleClass="text-grey"><bean:message key="document.type.${personDocument.documentType}" bundle="personsBundle"/></html:option>
                        </logic:iterate>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <div id="documentName" class="displayNone">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title"><bean:message key="person.info.field.documentName" bundle="personsBundle"/></tiles:put>
                    <tiles:put name="data"><span name="field(documentName)" id="field(documentName)"></span></tiles:put>
                </tiles:insert>
            </div>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">Серия и номер</tiles:put>
                <tiles:put name="data">
                    <span name="field(documentSeries)" id="field(documentSeries)"></span>&nbsp;
                    <span name="field(documentNumber)" id="field(documentNumber)"></span>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.contractType" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data">
                    <c:set var="creationType" value="${form.activePerson.creationType}"/>
                    <c:choose>
                        <c:when test="${creationType == 'UDBO'}">
                            <c:set var="creationTypeValue" value="УДБО"/>
                        </c:when>
                        <c:when test="${creationType == 'SBOL'}">
                            <c:set var="creationTypeValue" value="СБОЛ"/>
                        </c:when>
                        <c:when test="${creationType == 'CARD'}">
                            <c:set var="creationTypeValue" value="Подключен по карте"/>
                        </c:when>
                    </c:choose>
                    <c:out value="${creationTypeValue}"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.contractNumber" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data"><bean:write name="form" property="activePerson.agreementNumber"/></tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.office" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data">
                    <c:if test="${not empty form.fields.department}">
                        <bean:write name="form" property="field(department).code.fields.branch"/>/
                        <bean:write name="form" property="field(department).code.fields.office"/>
                        <bean:write name="form" property="field(department).name"/>
                    </c:if>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message key="person.info.field.eribclient" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data">
                    <c:if test="${not empty form.activePerson.login}">
                        <input type="checkbox" disabled="true"${form.activePerson.isERIBPerson ? ' checked="true"' : ''}>
                    </c:if>
                </tiles:put>
            </tiles:insert>


            <script type="text/javascript">
                changeDocument(getElementValue('field(documentType)'));
            </script>
        </tiles:put>
        <c:if test="${needCloseButton}">
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"    value="person.info.button.close"/>
                    <tiles:put name="commandHelpKey"    value="person.info.button.closeHelpTextKey"/>
                    <tiles:put name="bundle"            value="personsBundle"/>
                    <tiles:put name="onclick"           value="${closeButtonFunction}"/>
                </tiles:insert>
            </tiles:put>
        </c:if>
    </tiles:insert>
</html:html>