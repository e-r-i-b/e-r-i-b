<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 30.07.2008
  Time: 15:10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">
	var personDocuments = new Array();
	function changeMessageService()
	{
		var elem = getField("messageService");
        manageSMSFieldVisibility(elem.value != "sms");
	}

    function manageSMSFieldVisibility(disabled)
    {
		var elem = document.getElementsByName("SMSFormat");
        disableOrEnableRadio(elem, disabled);
    }

	function changeDocumentType()
	{
		var elem = document.getElementById("field(documentType)");
		if(elem!=null)
		{
			var service = elem.value;
			switch(service)
			{
				case "OTHER":{disableOrEnableField("documentName", false);return;}
				default:{disableOrEnableField("documentName", true);return;}
			}
		}
	}
	function changeDocumentId()
	{
		clear();
		var elemType = getField("documentType");
		var elemId = getField("documentId");

		for (var i = 0; i <= personDocuments.length - 1; i ++ ){
			if (personDocuments[i].type != elemType.value)
				elemId.value = null
			else{
				(elemId.value = personDocuments[i].id); break;
			}
		}
		for ( i = 0; i <= personDocuments.length - 1; i ++ ){
			if (personDocuments[i].id == elemId.value){
				getField("documentName").value = personDocuments[i].name;
				getField("documentType").value = personDocuments[i].type;
				getField("documentSeries").value = personDocuments[i].series;
				getField("documentNumber").value = personDocuments[i].number;
				getField("documentIssueDate").value = personDocuments[i].issueDate
				getField("documentIssueBy").value = personDocuments[i].issueBy;
				getField("documentIssueByCode").value = personDocuments[i].issueByCode;
			}
		}
	}
	function clear()
	{
		getField("documentName").value = '';
		getField("documentSeries").value = '';
		getField("documentNumber").value = '';
		getField("documentIssueDate").value = '';
		getField("documentIssueBy").value = '';
		getField("documentIssueByCode").value = '';
	}

    function selectClient(event)
    {
        var params="";
        params = addParam2List(params,"clientId","id");
        params = addParam2List(params,"surName");
        params = addParam2List(params,"firstName");
        params = addParam2List(params,"patrName");
        if ( params.length > 0 ) params = "?" + params;
        openWindow(event,
            "clients/list.do" + params,
            "Clients",
            "resizable=1,menubar=0,toolbar=0,scrollbars=1");
    }

    function showNotResidentFields(show)
    {
        var elemDocument = document.getElementById("forNotResidentDocument");
        var elemMigratory = document.getElementById("forNotResidentMigratory");
        if (show)
        {
            elemDocument.style.display="block";
            elemMigratory.style.display="block";
        }
        else
        {
            elemDocument.style.display="none";
            elemMigratory.style.display="none";
        }
    }

    function initPerson(){
        <c:if test="${form.personDocuments != null}">
        var doc;
        <logic:iterate id="personDocument" name="EditPersonForm" property="personDocuments">
			doc             = new Object();
			doc.id          = '${personDocument.id}';
            doc.name        = '${personDocument.documentName}';
            doc.number      = '${personDocument.documentNumber}';
            doc.series      = '${personDocument.documentSeries}';
            doc.type        = '${personDocument.documentType}';
            doc.issueDate   =  '';
            <c:if test="${not empty personDocument.documentIssueDate}">
                doc.issueDate   =  '<bean:write name="personDocument" property="documentIssueDate.time" format="dd.MM.yyyy"/>';
            </c:if>
            doc.issueBy     = '${personDocument.documentIssueBy}';
            doc.issueByCode = '${personDocument.documentIssueByCode}';
            personDocuments[personDocuments.length] = doc;
        </logic:iterate>
        </c:if>
    }
    initPerson();

    function updateValueFromRadio(name)
    {
        var field = getField(name);
        var elem = document.getElementsByName(name);
        field.value = getRadioValue(elem);
    }

    function initRadioFromValue(name)
    {
        var field = getField(name);
        var elem = document.getElementsByName(name);
        if (field !=null && field.value != null)
            setRadioValue(elem, field.value);
    }

    function markExistDocuments()
    {
        var docSelect = ensureElement("field(documentType)");
        var docOptions = docSelect.options;
        if(docOptions != undefined)
            for (var i = 0; i < personDocuments.length; i++)
            {
                for (var j = 0; j < docOptions.length; j++)
                {
                    if (docOptions[j].value == personDocuments[i].type)
                    {
                        docOptions[j].className = "";
                        break;
                    }
                }
            }
    }

    doOnLoad(markExistDocuments);

</script>

<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="editPerson"/>
	<tiles:put name="name" value="Анкета клиента"/>
	<tiles:put name="description" value="Используйте данную форму для редактирования сведений о клиенте."/>
	<tiles:put name="data">
        <c:if test="${not empty extendedLoggingEntry}">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="redBlock"/>
                <tiles:put name="data">
                    Внимание: у клиента включено расширенное логирование
                    <c:choose>
                        <c:when test="${empty extendedLoggingEntry.endDate}">бессрочно</c:when>
                        <c:otherwise>до ${phiz:formatDateToStringWithSlash2(extendedLoggingEntry.endDate)}</c:otherwise>
                    </c:choose>
                </tiles:put>
            </tiles:insert>
        </c:if>

        <c:set var="additionalMigrationState" value="${form.clientNodeState}" scope="request"/>
        <%@include file="clientInfoBlock.jsp"%>

        <table width="100%" class="doubleColumnBlock">
            <tr>
                <td>
                    <fieldset>
                        <legend><bean:message key="label.editUser" bundle="personsBundle"/></legend>
                        <div class="clear"></div>

                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.editUser" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue"></div>
                                <div class="clear"></div>
                            </div>
                            <c:if test="${phiz:impliesOperation('SearchMDMInformationOperation', 'SearchMDMInformationService')}">
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.mdm.id" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text disabled="true" property="mdmId" size="40" maxlength="64" styleClass="contactInput"/>
                                </div>
                                <div>
                                    <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                        <tiles:put name="commandKey" value="button.search.in.mdm"/>
                                        <tiles:put name="commandTextKey" value="button.search.in.mdm"/>
                                        <tiles:put name="commandHelpKey" value="button.search.in.mdm.help"/>
                                        <tiles:put name="bundle" value="personsBundle"/>
                                        <tiles:put name="availableOperation" value="SearchMDMInformationOperation"/>
                                        <tiles:put name="availableService" value="SearchMDMInformationService"/>
                                    </tiles:insert>
                                </div>
                                <div class="clear"></div>
                            </div>
                            </c:if>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.id" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text property="field(loginId)" size="40" styleClass="contactInput" disabled="true"/>
                                    <html:hidden property="field(id)" styleId="clientId" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.clientId" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text property="field(clientId)" size="40" styleClass="contactInput" disabled="true"/>
                                    <html:hidden property="field(clientId)" styleId="clientId" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.agreementType" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <c:choose>
                                        <c:when test="${person.creationType == 'UDBO'}">
                                            <c:set var="creationType" value="УДБО"/>
                                        </c:when>
                                        <c:when test="${person.creationType == 'CARD'}">
                                            <c:set var="creationType" value="Подключен по карте"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="creationType" value="СБОЛ"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="text" class="contactInput" disabled="disabled" value="${creationType}" size="40"/>
                                </div>
                                <div class="clear"></div>
                            </div>

                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.login" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <html:text property="field(login)" disabled="true" size="40" styleClass="contactInput"/>
                            </div>
                            <div class="clear"></div>
                        </div>

                            <c:choose>
                                <c:when test="${useOwnAuthentication}">
                                    <div class="form-row">
                                </c:when>
                                <c:otherwise>
                                    <div class="form-row" style="display:none;">
                                </c:otherwise>
                            </c:choose>
                                <div class="paymentLabel">
                                    <bean:message key="label.pinEnvelopeNumber" bundle="personsBundle"/><span class="asterisk">*</span>
                                </div>
                                <div class="paymentValue">
                                    <c:choose>
                                        <c:when test="${useOwnAuthentication}">
                                            <html:text readonly="true" property="field(pinEnvelopeNumber)" size="20" maxlength="16" styleClass="contactInput"/>
                                        </c:when>
                                        <c:otherwise>
                                            <html:hidden property="field(pinEnvelopeNumber)" styleClass="contactInput"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <html:hidden property="field(oldPinEnvelopeNumber)"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.surName" bundle="personsBundle"/> <span class="asterisk">*</span>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${notShowSavesIgnoreCreationType}" property="field(surName)" styleId="surName" size="40" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.firstName" bundle="personsBundle"/> <span class="asterisk">*</span>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${notShowSavesIgnoreCreationType}" property="field(firstName)" styleId="firstName" size="40" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.patrName" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${notShowSavesIgnoreCreationType}" property="field(patrName)" styleId="patrName" size="40" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <c:if test="${phiz:isERMBConnected(person)}">
                                <div class="form-row">
                                    <div class="paymentLabel"></div>
                                    <div class="paymentValue">
                                        <tiles:insert definition="commandButton" flush="false" operation="PersonIdentityHistoryListOperation">
                                            <tiles:put name="commandTextKey" value="button.view.history"/>
                                            <tiles:put name="commandHelpKey" value="button.view.history"/>
                                            <tiles:put name="bundle"         value="personsBundle"/>
                                            <tiles:put name="action"         value="/private/person/documents/history.do?person=${person.id}"/>
                                            <tiles:put name="viewType"       value="buttonGrayNew"/>
                                        </tiles:insert>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </c:if>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.gender" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:hidden property="field(gender)"/>
                                    <input type="radio" name="gender" value="M" onchange="updateValueFromRadio('gender');"
                                           style="border:0" <c:if test="${editNotSupported}">disabled</c:if>>
                                    <bean:message key="label.genderMale" bundle="personsBundle"/>

                                    <input type="radio" name="gender" value="F" onchange="updateValueFromRadio('gender');"
                                           style="border:0" <c:if test="${editNotSupported}">disabled</c:if>>
                                    <bean:message key="label.genderFemale" bundle="personsBundle"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.birthDay" bundle="personsBundle"/> <span class="asterisk">*</span>
                                </div>
                                <div class="paymentValue">
                                    <input type="text"
                                           name="field(birthDay)" class="dot-date-pick"
                                           value='<bean:write name="form" property="fields.birthDay" format="dd.MM.yyyy"/>'
                                           size="10" class="contactInput"
                                            <c:if test="${notShowSavesIgnoreCreationType}">
                                                readonly
                                            </c:if>
                                            />
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.birthPlace" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${not isShowSaves}" property="field(birthPlace)" size="40" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.citizen" bundle="personsBundle"/> <span class="asterisk">*</span>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${not isShowSaves}" property="field(citizen)" size="40" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.inn" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${not isShowSaves}" property="field(inn)" size="20" maxlength="12" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.SNILS" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text disabled="true" property="field(SNILS)" size="40" maxlength="14" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.segmentCodeType" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="true" property="field(segmentCodeType)" size="40" maxlength="14" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.tarifPlanCodeType" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="true" property="field(tarifPlanCodeType)" size="40" maxlength="14" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.tarifPlanConnectionDate" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <input type="text"
                                           name="field(tarifPlanConnectionDate)"
                                           value='<bean:write name="form" property="fields.tarifPlanConnectionDate" format="dd.MM.yyyy"/>'
                                           size="10" class="contactInput" readonly/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.managerId" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="true" property="field(managerId)" size="40" maxlength="14" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.incognito" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <c:set var="incognitoValue">${form.fields['incognito']}</c:set>
                                    <c:choose>
                                        <c:when test="${phiz:impliesService('ChangePersonIncognitoSettings')}">
                                            <input type="hidden" id="IncognitoContainer" name="field(incognito)" value="${incognitoValue}"/>
                                            <input type="checkbox"
                                                   id="incognitoController"
                                                   onchange="changeIncognito($(this).is(':checked'));"
                                                   <c:if test="${incognitoValue == 'incognito'}">
                                                       checked="checked"
                                                   </c:if>/>
                                            <label for="incognitoController"><bean:message key="label.incognito.description" bundle="personsBundle"/></label>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text"
                                                   value='<bean:message key="label.incognito.${incognitoValue}" bundle="personsBundle"/>'
                                                   size="30"
                                                   maxlength="30"
                                                   disabled="disabled"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="clear"></div>
                            </div>
                    </fieldset>
                    <fieldset>
                        <legend><bean:message key="label.document" bundle="personsBundle"/></legend>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.documentType" bundle="personsBundle"/> <span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <c:if test="${notShowSavesIgnoreCreationType}">
                                    <html:hidden property="field(documentType)"/>
                                </c:if>
                                <input type="hidden" name="field(documentId)" id="field(documentId)"  value='<bean:write name="form"  property="fields.documentId"/>'/>

                                <html:select disabled="${notShowSavesIgnoreCreationType}" property="field(documentType)" styleId="field(documentType)" onchange="changeDocumentType();changeDocumentId()" style="width:250px" styleClass="select">
                                    <c:forEach var="documentType" items="${form.documentTypes}">
                                        <html:option value="${documentType}" styleClass="text-grey"><bean:message key="document.type.${documentType}" bundle="personsBundle"/></html:option>
                                    </c:forEach>
                                </html:select>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.documentName" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <c:choose>
                                    <c:when test="${form.fields.documentType=='OTHER'}">
                                        <c:set var="disIdentityTypeName" value="false"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="disIdentityTypeName" value="true"/>
                                    </c:otherwise>
                                </c:choose>
                                <html:text property="field(documentName)" disabled="${disIdentityTypeName}" readonly="${notShowSavesIgnoreCreationType}" styleId="field(documentName)" size="40" styleClass="contactInput"/>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.documentSeries" bundle="personsBundle"/><span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <html:text readonly="${notShowSavesIgnoreCreationType}" property="field(documentSeries)"
                                           size="20"
                                           styleClass="contactInput"
                                           styleId="field(documentSeries)"
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.documentNumber" bundle="personsBundle"/> <span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <html:text readonly="${notShowSavesIgnoreCreationType}" property="field(documentNumber)"
                                           size="20"
                                           styleClass="contactInput"
                                           styleId="field(documentNumber)"
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.documentIssueDate" bundle="personsBundle"/> <span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <input type="text"
                                       name="field(documentIssueDate)" class="dot-date-pick"
                                       id="field(documentIssueDate)"
                                       value='<bean:write name="form"  property="fields.documentIssueDate" format="dd.MM.yyyy"/>'
                                       size="10" class="contactInput"
                                        <c:if test="${notShowSavesIgnoreCreationType}">
                                            readonly
                                        </c:if>
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.documentIssueBy" bundle="personsBundle"/> <span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <html:text readonly="${notShowSavesIgnoreCreationType}" property="field(documentIssueBy)"
                                           size="40"
                                           styleClass="contactInput"
                                           styleId="field(documentIssueBy)"
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.documentIssueByCode" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <html:text readonly="${notShowSavesIgnoreCreationType}" property="field(documentIssueByCode)"
                                           size="20"
                                           styleClass="contactInput"
                                           styleId="field(documentIssueByCode)"
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                    </fieldset>
                    <div id="forNotResidentDocument">
                        <fieldset>
                            <legend><bean:message key="label.document.rightProve" bundle="personsBundle"/></legend>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.documentType" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <c:if test="${not isShowSaves}">
                                        <html:hidden property="field(documentProveType)"/>
                                    </c:if>
                                    <input type="hidden" name="field(documentProveId)" id="field(documentProveId)"  value='<bean:write name="form"  property="fields.documentProveId"/>'/>

                                    <html:select disabled="${not isShowSaves}" property="field(documentProveType)" style="width:50%;" styleId="field(documentProveType)" onchange="changeProveDocument()" styleClass="select">
                                        <html:option value="RESIDENTIAL_PERMIT_RF">Вид на жительство РФ</html:option>
                                        <html:option value="REFUGEE_IDENTITY">Удостоверение беженца в РФ</html:option>
                                        <html:option value="IMMIGRANT_REGISTRATION">Свидетельство о регистрации ходатайства иммигранта о признании его беженцем</html:option>
                                    </html:select>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.documentSeries" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${not isShowSaves}" property="field(documentProveSeries)"
                                               size="20"
                                               styleClass="contactInput"
                                               styleId="field(documentProveSeries)"
                                            />
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.documentNumber" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${not isShowSaves}" property="field(documentProveNumber)"
                                               size="20"
                                               styleClass="contactInput"
                                               styleId="field(documentProveNumber)"
                                            />
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.documentIssueDate" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <input type="text"
                                           name="field(documentProveIssueDate)"
                                           id="field(documentProveIssueDate)"  class="dot-date-pick"
                                           value='<bean:write name="form"  property="fields.documentProveIssueDate" format="dd.MM.yyyy"/>'
                                           size="10" class="contactInput"
                                            <c:if test="${not isShowSaves}">
                                                readonly
                                            </c:if>
                                            />
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.documentIssueBy" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${not isShowSaves}" property="field(documentProveIssueBy)"
                                               size="40"
                                               styleClass="contactInput"
                                               styleId="field(documentProveIssueBy)"
                                            />
                                </div>
                                <div class="clear"></div>
                            </div>
                        </fieldset>
                    </div>
                    <fieldset>
                    <legend><bean:message key="label.contactInfo" bundle="personsBundle"/></legend>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationAddress" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue"></div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationPostalCode" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationPostalCode)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationProvince" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationProvince)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationDistrict" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationDistrict)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationCity" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationCity)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationStreet" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationStreet)" size="20" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationHouse" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationHouse)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationBuilding" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationBuilding)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.registrationFlat" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(registrationFlat)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <c:if test="${not isShowSaves}">
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.fullRegistrationAddress" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <html:text readonly="${not isShowSaves}" property="field(registrationAddress)" size="40" styleClass="contactInput"/>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </c:if>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceAddress" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue"></div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residencePostalCode" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residencePostalCode)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceProvince" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceProvince)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceDistrict" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceDistrict)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceCity" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceCity)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceStreet" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceStreet)" size="20" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceHouse" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceHouse)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceBuilbing" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceBuilding)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.residenceFlat" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceFlat)" size="10" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.fullResidenceAddress" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(residenceAddress)" size="40" maxlength="255" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.resident" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:hidden property="field(resident)"/>
                            <input type="radio" name="resident" value="true"
                                   onchange="updateValueFromRadio('resident');" style="border:0"
                                   <c:if test="${not isShowSaves}">disabled</c:if>>
                            <bean:message key="label.yes" bundle="personsBundle"/>

                            <input type="radio" name="resident" value="false"
                                   onchange="updateValueFromRadio('resident');" style="border:0"
                                   <c:if test="${not isShowSaves}">disabled</c:if>>
                            <bean:message key="label.no" bundle="personsBundle"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">

                        </div>
                        <div class="paymentValue">

                        </div>
                        <div class="clear"></div>
                    </div>
                    <div id="forNotResidentMigratory">
                        <div class="form-row">
                            <div class="paymentLabel">
                                <b><bean:message key="label.migratoryMap" bundle="personsBundle"/></b>
                            </div>
                            <div class="paymentValue"></div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.migratoryMap.number" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <html:text readonly="${not isShowSaves}" property="field(migratoryCardNumber)" size="10" styleClass="contactInput" maxlength="11"/>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.migratoryMap.dateFrom" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <input type="text"
                                       name="field(migratoryCardFromDate)"
                                       id="field(migratoryCardFromDate)"  class="dot-date-pick"
                                       value='<bean:write name="form"  property="fields.migratoryCardFromDate" format="dd.MM.yyyy"/>'
                                       size="10" class="contactInput"
                                        <c:if test="${not isShowSaves}">
                                            readonly
                                        </c:if>
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.migratoryMap.dateEnd" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <input type="text"
                                       name="field(migratoryCardTimeUpDate)"
                                       id="field(migratoryCardTimeUpDate)"  class="dot-date-pick"
                                       value='<bean:write name="form"  property="fields.migratoryCardTimeUpDate" format="dd.MM.yyyy"/>'
                                       size="10" class="contactInput"
                                        <c:if test="${not isShowSaves}">
                                            readonly
                                        </c:if>
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                        <script type="text/javascript">
                            <c:choose>
                            <c:when test="${empty form.fields.resident}">showNotResidentFields(false);</c:when>
                            <c:otherwise>showNotResidentFields(${!form.fields.resident});</c:otherwise>
                            </c:choose>
                        </script>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.pensionCertificate" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(pensionCertificate)" size="40" maxlength="15" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.messageService" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <c:if test="${not isShowSaves}">
                                <html:hidden property="field(messageService)"/>
                            </c:if>
                            <html:select disabled="${not isShowSaves}" property="field(messageService)" styleId="field(messageService)" styleClass="select" onchange="changeMessageService()">
                                <html:option value="none">не доставлять</html:option>
                                <html:option value="email">e-mail</html:option>
                                <html:option value="sms">sms-сообщения</html:option>
                            </html:select>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.email" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(email)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.homePhone" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(homePhone)" size="20" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.jobPhone" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(jobPhone)" size="20" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.mobilePhone" bundle="personsBundle"/> <span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${not isShowSaves}" property="field(mobilePhone)" size="20" maxlength="16" styleClass="contactInput"/>
                            Формат поля +7(код_оператора)номер_телефона.
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.mobileOperator" bundle="personsBundle"/> <span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text readonly="${true}" property="field(mobileOperator)" size="20" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.SMSFormat" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:hidden property="field(SMSFormat)"/>
                            <input type="radio" name="SMSFormat" value="DEFAULT"
                                   onchange="updateValueFromRadio('SMSFormat');" style="border:0"
                                   <c:if test="${editNotSupported}">disabled</c:if>>
                            <bean:message key="label.SMSFormatRus" bundle="personsBundle"/>

                            <input type="radio" name="SMSFormat" value="TRANSLIT"
                                   onchange="updateValueFromRadio('SMSFormat');" style="border:0"
                                   <c:if test="${editNotSupported}">disabled</c:if>>
                            <bean:message key="label.SMSFormatTranslit" bundle="personsBundle"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    </fieldset>
                    <fieldset>
                        <legend><bean:message key="label.contractInfo" bundle="personsBundle"/></legend>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.agreementNumber" bundle="personsBundle"/>
                            </div>
                            <div class="paymentValue">
                                <html:text  property="field(agreementNumber)" size="20" styleClass="contactInput" readonly="true"/>
                                <html:hidden property="field(agreementNumber)" styleClass="contactInput"/>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <c:set var="department" value="${form.department}"/>
                        <c:if test="${not empty form.department}">
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.branchAndDepartment" bundle="personsBundle"/>
                                    <!-- todo необходимо разделить реализации и в случае сбрф выводить в названии branch и.т.д.-->
                                </div>
                                <div class="paymentValue">
                                    <input type="text" name="branchAndDepartment" value="${department.fullName}" size="40" class="contactInput" readonly="true"/>
                                    <html:hidden property="field(departmentId)" value="${department.id}"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </c:if>
                        <c:if test="${not editNotSupported}">
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.secretWord" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <html:text readonly="${not isShowSaves}" property="field(secretWord)" size="20" styleClass="contactInput"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </c:if>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.serviceInsertionDate" bundle="personsBundle"/> <span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <input type="text"
                                       name="field(serviceInsertionDate)" class="dot-date-pick"
                                       value='<bean:write name="form" property="fields.serviceInsertionDate" format="dd.MM.yyyy"/>'
                                       size="10" class="contactInput"
                                        <c:if test="${not isShowSaves}"> readonly="readonly"	</c:if>
                                        />
                            </div>
                            <div class="clear"></div>
                        </div>
                        <c:if test="${not editNotSupported}">
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.prolongationRejectionDate" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <input type="text"
                                           name="field(prolongationRejectionDate)" class="dot-date-pick"
                                           value='<bean:write name="form" property="fields.prolongationRejectionDate" format="dd.MM.yyyy"/>'
                                           size="10" class="contactInput"
                                            <c:if test="${not isShowSaves}">
                                                readonly
                                            </c:if>
                                            />
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.contractCancellationCouse" bundle="personsBundle"/>
                                </div>
                                <div class="paymentValue">
                                    <c:if test="${not isShowSaves}">
                                        <html:hidden property="field(contractCancellationCouse)"/>
                                    </c:if>
                                    <html:select disabled="${(not isShowSaves)}" property="field(contractCancellationCouse)" styleClass="select">
                                        <html:option value="C">По инициативе клиента</html:option>
                                        <html:option value="A">Из-за закрытия клиентом счета, с которого осуществляется оплата услуг</html:option>
                                        <html:option value="B">По инициативе банка</html:option>
                                    </html:select>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </c:if>
                    </fieldset>
                </td>
                <td class="vertical-align" width="310px">
                    <fieldset class="editPersonDataButtons">
                        <legend><bean:message key="label.logs" bundle="personsBundle"/></legend>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.show.common-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.common-log"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="availableOperation" value="DownloadCommonLogOperation"/>
                                <tiles:put name="availableService" value="CommonLogServiceEmployee"/>
                                <tiles:put name="action" value="/log/common.do?clientId=${person.id}"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandKey"     value="button.show.common-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.common-log"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="availableOperation" value="DownloadCommonLogOperation"/>
                                <tiles:put name="availableService" value="CommonLogServiceEmployeeUseClientForm"/>
                                <tiles:put name="notAvailableService" value="CommonLogServiceEmployee"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandKey"     value="button.show.operations-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.operations-log"/>
                                <tiles:put name="bundle"  value="personsBundle"/>
                                <tiles:put name="availableOperation" value="DownloadUserLogOperation"/>
                                <tiles:put name="availableService" value="LogsServiceEmployeeUseClientForm"/>
                                <tiles:put name="notAvailableService" value="LogsServiceEmployee"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandKey"     value="button.show.system-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.system-log"/>
                                <tiles:put name="bundle"  value="personsBundle"/>
                                <tiles:put name="availableOperation" value="DownloadSystemLogOperation"/>
                                <tiles:put name="availableService" value="LogsServiceEmployeeUseClientForm"/>
                                <tiles:put name="notAvailableService" value="LogsServiceEmployee"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandKey"     value="button.show.messages-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.messages-log"/>
                                <tiles:put name="bundle"  value="personsBundle"/>
                                <tiles:put name="availableOperation" value="MessageLogOperation"/>
                                <tiles:put name="availableService" value="MessageLogServiceEmployeeUseClientForm"/>
                                <tiles:put name="notAvailableService" value="MessageLogServiceEmployee"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.show.audit-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.audit-log"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="availableOperation" value="GetEmployeePaymentListOperation"/>
                                <tiles:put name="availableService" value="ViewPaymentList"/>
                                <tiles:put name="action">/audit/businessDocument.do?<c:if test="${person.id != null}">field(loginId)=${person.login.id}</c:if></tiles:put>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandKey"     value="button.show.audit-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.audit-log"/>
                                <tiles:put name="bundle"  value="personsBundle"/>
                                <tiles:put name="availableOperation" value="GetEmployeePaymentListOperation"/>
                                <tiles:put name="availableService" value="ViewPaymentListUseClientForm"/>
                                <tiles:put name="notAvailableService" value="ViewPaymentList"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandKey"     value="button.show.entries-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.entries-log"/>
                                <tiles:put name="bundle"  value="personsBundle"/>
                                <tiles:put name="availableOperation" value="LoggingJournalOperation"/>
                                <tiles:put name="availableService" value="LoggingJournalServiceEmployeeUseClientForm"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                        <div>
                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                <tiles:put name="commandTextKey"     value="button.show.csa.entries-log"/>
                                <tiles:put name="commandHelpKey" value="button.show.csa.entries-log"/>
                                <tiles:put name="bundle"  value="personsBundle"/>
                                <tiles:put name="availableOperation" value="ViewCSAActionLogOperation"/>
                                <tiles:put name="availableService" value="ViewCSAActionLogService"/>
                                <tiles:put name="action">/log/csa/action.do?id=${person.id}</tiles:put>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>

                    </fieldset>

                    <c:if test="${phiz:impliesOperation('ListMailOperation','MailManagementUseClientForm')}">
                        <fieldset class="editPersonDataButtons">
                            <legend><bean:message key="label.mails" bundle="personsBundle"/></legend>
                            <div>
                                <c:set var="isMailManagmentAvailable" value="${phiz:impliesOperation('ListMailOperation', 'MailManagment')}"/>
                                <c:if test="${isMailManagmentAvailable}">
                                    <c:set var="parameters" value=""/>
                                    <c:set var="employeeInfoForFilter" value="${phiz:getEmployeeInfo()}"/>

                                    <c:set var="parameters" value="${parameters}&filter(fio)=${form.fields.surName} ${form.fields.firstName}"/>
                                    <c:if test="${not empty form.fields.patrName}">
                                        <c:set var="parameters" value="${parameters} ${form.fields.patrName}"/>
                                    </c:if>

                                    <c:set var="parameters" value="${parameters}&filter(fioEmpl)=${employeeInfoForFilter.surName} ${employeeInfoForFilter.firstName}"/>
                                    <c:if test="${not empty employeeInfoForFilter.patrName}">
                                        <c:set var="parameters" value="${parameters} ${employeeInfoForFilter.patrName}"/>
                                    </c:if>
                                </c:if>

                                <c:choose>
                                    <c:when test="${not isMailManagmentAvailable}">
                                        <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                            <tiles:put name="commandKey"     value="button.show.incoming-mail"/>
                                            <tiles:put name="commandHelpKey" value="button.show.incoming-mail"/>
                                            <tiles:put name="bundle"  value="personsBundle"/>
                                            <tiles:put name="availableOperation" value="ListMailOperation"/>
                                            <tiles:put name="availableService" value="MailManagementUseClientForm"/>
                                            <tiles:put name="notAvailableService" value="MailManagment"/>
                                        </tiles:insert>
                                        <div class="clear"></div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="parameters" value="${parameters}&filter(showNew)=true"/>
                                        <c:set var="parameters" value="${parameters}&filter(showAnswer)=false"/>
                                        <c:set var="parameters" value="${parameters}&filter(showReceived)=true"/>
                                        <c:set var="parameters" value="${parameters}&filter(showDraft)=true"/>
                                        <c:if test="${isMailManagmentAvailable}">
                                            <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                                <tiles:put name="commandTextKey"     value="button.show.incoming-mail"/>
                                                <tiles:put name="commandHelpKey" value="button.show.incoming-mail"/>
                                                <tiles:put name="bundle"  value="personsBundle"/>
                                                <tiles:put name="availableOperation" value="ListMailOperation"/>
                                                <tiles:put name="availableService" value="MailManagementUseClientForm"/>
                                               <tiles:put name="action">/mail/list.do?${parameters}&fromQuestionary=true</tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                        <div class="clear"></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                                <c:choose>
                                    <c:when test="${not isMailManagmentAvailable}">
                                        <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                            <tiles:put name="commandKey"     value="button.show.outgoing-mail"/>
                                            <tiles:put name="commandHelpKey" value="button.show.outgoing-mail"/>
                                            <tiles:put name="bundle"  value="personsBundle"/>
                                            <tiles:put name="availableOperation" value="ListMailOperation"/>
                                            <tiles:put name="availableService" value="MailManagementUseClientForm"/>
                                            <tiles:put name="notAvailableService" value="MailManagment"/>
                                        </tiles:insert>
                                        <div class="clear"></div>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert definition="clientFormAdditionalButton" flush="false">
                                            <tiles:put name="commandTextKey"     value="button.show.outgoing-mail"/>
                                            <tiles:put name="commandHelpKey" value="button.show.outgoing-mail"/>
                                            <tiles:put name="bundle"  value="personsBundle"/>
                                            <tiles:put name="availableOperation" value="ListMailOperation"/>
                                            <tiles:put name="availableService" value="MailManagementUseClientForm"/>
                                            <tiles:put name="action">/mail/sentList.do?${parameters}</tiles:put>
                                        </tiles:insert>
                                        <div class="clear"></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </fieldset>
                    </c:if>
                </td>
            </tr>
        </table>
	</tiles:put>
	<tiles:put name="buttons">
		<c:if test="${isTemplate}">
            <tiles:insert definition="commandButton" flush="false" operation="EditPersonOperation">
                <tiles:put name="commandKey"     value="button.partly.save.person"/>
                <tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
                <tiles:put name="bundle"  value="personsBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </c:if>
         <c:if test="${not isNew and isTemplate}">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.activate.person"/>
                <tiles:put name="commandHelpKey" value="button.activate.person.help"/>
                <tiles:put name="bundle"  value="personsBundle"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not (isNew or isTemplate or isDeleted)}">
            <c:if test="${phiz:impliesOperation('EditPersonOperation', null) || phiz:impliesService('ChangePersonIncognitoSettings')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.partly.save.person"/>
                    <tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
                    <tiles:put name="bundle"  value="personsBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="validationFunction">
                        validateOnSave();
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </c:if>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>

<script type="text/javascript">
    var incognitoContainer = $('#IncognitoContainer');
    function changeIncognito(value)
    {
        incognitoContainer.val(value == true? 'incognito': 'publicly');
    }


    <%-- Первичная инициализация после заполнения элементов --%>
    initRadioFromValue('gender');
    initRadioFromValue('resident');
    initRadioFromValue('SMSFormat');

    changeMessageService();
    addClearMasks(null,
            function(event)
            {
                clearInputTemplate('field(migratoryCardFromDate)', '__.__.____');
                clearInputTemplate('field(migratoryCardTimeUpDate)', '__.__.____');
            });
</script>
