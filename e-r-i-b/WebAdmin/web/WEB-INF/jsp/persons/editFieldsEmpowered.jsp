<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<script type="text/javascript">
	var personProveDocuments = new Array();
    var personDocuments = new Array();
	function changeDocumentType()
	{
		elem = document.getElementById("field(documentType)");
		if(elem!=null)
		{
			service = elem.value;
			switch(service)
			{
				case "OTHER":{showField("field(documentName)");return;}
				default:{hideField("field(documentName)");return;}
			}
		}
	}
	function changeDocumentId()
	{
		clear();
		elemType = document.getElementById("field(documentType)");
		elemId = document.getElementById("field(documentId)");

		for ( i = 0; i <= personDocuments.length - 1; i ++ ){
			if (personDocuments[i].type != elemType.value)
				elemId.value = null
			else{
				(elemId.value = personDocuments[i].id); break;
			}
		}
		for ( i = 0; i <= personDocuments.length - 1; i ++ ){
			if (personDocuments[i].id == elemId.value){
				document.getElementById("field(documentName)").value = personDocuments[i].name;
				document.getElementById("field(documentType)").value = personDocuments[i].type;
				document.getElementById("field(documentSeries)").value = personDocuments[i].series;
				document.getElementById("field(documentNumber)").value = personDocuments[i].number;
				document.getElementById("field(documentIssueDate)").value = personDocuments[i].issueDate
				document.getElementById("field(documentIssueBy)").value = personDocuments[i].issueBy;
				document.getElementById("field(documentIssueByCode)").value = personDocuments[i].issueByCode;
			}
		}
	}
	function clear()
	{
		document.getElementById("field(documentName)").value = '';
		document.getElementById("field(documentSeries)").value = '';
		document.getElementById("field(documentNumber)").value = '';
		document.getElementById("field(documentIssueDate)").value = '';
		document.getElementById("field(documentIssueBy)").value = '';
		document.getElementById("field(documentIssueByCode)").value = '';
	}

	function hideField(field)
	{
		elem = document.getElementById(field);
		if(elem!=null)elem.disabled = true;
	}
	function showField(field)
	{
		elem = document.getElementById(field);
		if(elem!=null)elem.disabled = false;
	}
	function initPerson()
	{
		var doc;
		<logic:iterate id="personDocument" name="EditEmpoweredPersonForm" property="personDocuments">
			doc             = new Object()
			doc.id          = '${personDocument.id}'
			doc.name        = '${personDocument.documentName}';
			doc.number      = '${personDocument.documentNumber}';
			doc.series      = '${personDocument.documentSeries}';
			doc.type        = '${personDocument.documentType}';
			<c:if test="${not empty personDocument.documentIssueDate}">
					doc.issueDate   =  '<bean:write name="personDocument" property="documentIssueDate.time" format="dd.MM.yyyy"/>';
			</c:if>
			doc.issueBy     = '${personDocument.documentIssueBy}';
			doc.issueByCode = '${personDocument.documentIssueByCode}';
		    <c:if test="${personDocument.documentIdentify == true}">
				personDocuments[personDocuments.length] = doc;
		    </c:if>
		</logic:iterate>
	}
	function clearProveDocument()
	{
		document.getElementById("field(documentProveSeries)").value = '';
		document.getElementById("field(documentProveNumber)").value = '';
		document.getElementById("field(documentProveIssueDate)").value = '';
		document.getElementById("field(documentProveIssueBy)").value = '';
	}    
	function changeProveDocument()
	{
		clearProveDocument();
		var elemType = document.getElementById("field(documentProveType)");
		var elemId = document.getElementById("field(documentProveId)");

		for ( i = 0; i <= personProveDocuments.length - 1; i ++ ){
			if (personProveDocuments[i].type != elemType.value)
				elemId.value = null
			else{
				(elemId.value = personProveDocuments[i].id); break;
			}
		}
		for (var i = 0; i <= personProveDocuments.length - 1; i ++ ){
			if (personProveDocuments[i].id == elemId.value)
			{
				document.getElementById("field(documentProveType)").value = personProveDocuments[i].type;
				document.getElementById("field(documentProveSeries)").value = personProveDocuments[i].series;
				document.getElementById("field(documentProveNumber)").value = personProveDocuments[i].number;
				document.getElementById("field(documentProveIssueDate)").value = personProveDocuments[i].issueDate
				document.getElementById("field(documentProveIssueBy)").value = personProveDocuments[i].issueBy;
			}
		}
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
	addClearMasks(null,function(event)
	{
		clearInputTemplate('field(migratoryCardFromDate)', DATE_TEMPLATE);
		clearInputTemplate('field(migratoryCardTimeUpDate)', DATE_TEMPLATE);
        clearInputTemplate('field(documentProveIssueDate)', DATE_TEMPLATE);
	});
	initPerson();
</script>
<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="editPerson"/>
	<tiles:put name="name" value="������ �������������"/>
	<tiles:put name="description" value="����������� ������ ����� �������������� �������� � �������������."/>
    <%--<tiles:put name="alignTable" value="center"/>--%>
	<tiles:put name="data">
	<tr>
		<td>
		<fieldset>
		<legend><bean:message key="label.editUser" bundle="personsBundle"/></legend>
			<table cellpadding="0"cellspacing="0" width="100%">
			<tr>
				<td class="Width200 LabelAll" nowrap="true"><bean:message key="label.clientId" bundle="personsBundle"/></td>
				<td><html:text property="field(clientId)" size="40" styleClass="contactInput" disabled="true"/></td>
				<td><html:hidden property="field(clientId)" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><nobr><bean:message key="label.id" bundle="personsBundle"/></nobr></td>
				<td><html:text property="field(id)" styleClass="contactInput" disabled="true"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><nobr><bean:message key="label.surName" bundle="personsBundle"/> <span
					class="asterisk">*</span></nobr></td>
				<td width="100%"><html:text property="field(surName)" size="40" styleClass="contactInput" disabled="${not isShowSaves}"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.firstName" bundle="personsBundle"/> <span
					class="asterisk">*</span></td>
				<td><html:text property="field(firstName)" size="40" styleClass="contactInput" disabled="${not isShowSaves}"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.patrName" bundle="personsBundle"/></td>
				<td><html:text property="field(patrName)" size="40" styleClass="contactInput" disabled="${not isShowSaves}"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.gender" bundle="personsBundle"/></td>
				<td><html:radio property="field(gender)" styleId="field(gender)M" value="M" style="border:0" disabled="${not isShowSaves}"/>
					<bean:message key="label.genderMale" bundle="personsBundle"/>
					<html:radio property="field(gender)" value="F" style="border:0" disabled="${not isShowSaves}"/> <bean:message
						key="label.genderFemale" bundle="personsBundle"/>
					<c:if test="${form.fields.gender==null or form.fields.gender==''}">
						<script type="text/javascript">
							el = document.getElementById("field(gender)M");
							if(el!=null)
								el.checked = true;
						</script>
					</c:if>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.birthDay" bundle="personsBundle"/> <span
					class="asterisk">*</span></td>
				<td>
					<input type="text"
						   name="field(birthDay)"
						   value='<bean:write name="form" property="fields.birthDay" format="dd.MM.yyyy"/>'
						   size="10" class="contactInput"  onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
						   <c:if test="${not isShowSaves}">
								disabled
							</c:if>
							/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.birthPlace" bundle="personsBundle"/></td>
				<td><html:text property="field(birthPlace)" size="80" styleClass="contactInput" disabled="${not isShowSaves}"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.citizen" bundle="personsBundle"/> <span
					class="asterisk">*</span></td>
				<td><html:text disabled="${not isShowSaves}" property="field(citizen)" size="80" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.inn" bundle="personsBundle"/></td>
				<td><html:text disabled="${not isShowSaves}" property="field(inn)" size="20" maxlength="12" styleClass="contactInput"/></td>
			</tr>
			</table>
		</fieldset>
		</td>
	</tr>
	<tr>
		<td>
		<fieldset>
		<legend><bean:message key="label.document" bundle="personsBundle"/></legend>
			<table cellpadding="0"cellspacing="0" width="100%">
			<tr>
				<td class="pmntInfAreaTitle" colspan="2"><bean:message key="lable.document.identify" bundle="personsBundle"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.documentType" bundle="personsBundle"/> <span
						class="asterisk">*</span></td>
				<td>
					<input type="hidden" name="field(documentId)" id="field(documentId)"  value='<bean:write name="form"  property="fields.documentId"/>'/>
					<html:select property="field(documentType)" onchange="changeDocumentType();changeDocumentId()" styleClass="select" styleId="field(documentType)" disabled="${not isShowSaves}">
					<html:option value="REGULAR_PASSPORT_RF">��������������� ������� ��</html:option>
					<html:option value="MILITARY_IDCARD">������������� �������� ���������������</html:option>
					<html:option value="SEAMEN_PASSPORT">������� ������</html:option>
					<html:option value="RESIDENTIAL_PERMIT_RF">��� �� ���������� ��</html:option>
					<html:option value="FOREIGN_PASSPORT_RF">����������� ������� ��</html:option>
					<html:option value="OTHER">���� ��������</html:option>
				</html:select>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll" nowrap="true"><bean:message key="label.documentName" bundle="personsBundle"/>
				</td>
				<c:choose>
					<c:when test="${form.fields.documentType=='OTHER'}">
						<c:set var="disIdentityTypeName" value="false"/>
					</c:when>
					<c:otherwise>
						<c:set var="disIdentityTypeName" value="true"/>
					</c:otherwise>
				</c:choose>
				<td><html:text property="field(documentName)" disabled="${disIdentityTypeName||(not isShowSaves)}" styleId="field(documentName)" size="80" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.documentSeries" bundle="personsBundle"/><span class="asterisk">*</span></td>
				<td>
					<html:text property="field(documentSeries)" disabled="${not isShowSaves}"
							   size="20"
							   styleClass="contactInput"
							   styleId="field(documentSeries)"
							/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.documentNumber" bundle="personsBundle"/> <span
						class="asterisk">*</span></td>
				<td>
					<html:text property="field(documentNumber)" disabled="${not isShowSaves}"
							   size="20"
							   styleClass="contactInput"
							   styleId="field(documentNumber)"
							/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.documentIssueDate" bundle="personsBundle"/> <span
						class="asterisk">*</span></td>
				<td>
					<input type="text"
						   name="field(documentIssueDate)"
						   id="field(documentIssueDate)"
						   value='<bean:write name="form"  property="fields.documentIssueDate" format="dd.MM.yyyy"/>'
						   size="10" class="contactInput"  onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
						   <c:if test="${not isShowSaves}">
								disabled
							</c:if>
							/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.documentIssueBy" bundle="personsBundle"/> <span
						class="asterisk">*</span></td>

				<td><html:text disabled="${not isShowSaves}" property="field(documentIssueBy)"
							   size="80"
							   styleClass="contactInput"
							   styleId="field(documentIssueBy)"
							/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.documentIssueByCode" bundle="personsBundle"/>
				<td><html:text disabled="${not isShowSaves}" property="field(documentIssueByCode)"
							   size="20"
							   styleClass="contactInput"
							   styleId="field(documentIssueByCode)"
							/>
				</td>
			</tr>
			</table>
		</fieldset>
		</td>
	</tr>
    <tr>
        <td>
        <div  id="forNotResidentDocument">
        <fieldset>
		<legend><bean:message key="label.document.rightProve" bundle="personsBundle"/></legend>
             <table style="width:100%;" cellspacing="0" cellpadding="0" border="0">
    					<tr>
							<td class="Width200 LabelAll"><bean:message key="label.documentType" bundle="personsBundle"/></td>
							<td>
								<c:if test="${not isShowSaves}">
									<html:hidden property="field(documentProveType)"/>
								</c:if>
								<input type="hidden" name="field(documentProveId)" id="field(documentProveId)"  value='<bean:write name="form"  property="fields.documentProveId"/>'/>

								<html:select disabled="${not isShowSaves}" property="field(documentProveType)" style="width:100%;" styleId="field(documentProveType)" onchange="changeProveDocument()" styleClass="select">
								<html:option value="RESIDENTIAL_PERMIT_RF">��� �� ���������� ��</html:option>
								<html:option value="REFUGEE_IDENTITY">������������� ������� � ��</html:option>
								<html:option value="IMMIGRANT_REGISTRATION">������������� � ����������� ����������� ���������� � ��������� ��� ��������</html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll"><bean:message key="label.documentSeries" bundle="personsBundle"/></td>
							<td>

								<html:text readonly="${not isShowSaves}" property="field(documentProveSeries)"
										   size="20"
										   styleClass="contactInput"
										   styleId="field(documentProveSeries)"
										/>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll"><bean:message key="label.documentNumber" bundle="personsBundle"/></td>
							<td>
								<html:text readonly="${not isShowSaves}" property="field(documentProveNumber)"
										   size="20"
										   styleClass="contactInput"
										   styleId="field(documentProveNumber)"
										/>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll"><bean:message key="label.documentIssueDate" bundle="personsBundle"/></td>
							<td>
								<input type="text"
									   name="field(documentProveIssueDate)"
									   id="field(documentProveIssueDate)"
									   value='<bean:write name="form"  property="fields.documentProveIssueDate" format="dd.MM.yyyy"/>'
									   size="10" class="contactInput"  onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
									   <c:if test="${not isShowSaves}">
											readonly
										</c:if>
										/>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll"><bean:message key="label.documentIssueBy" bundle="personsBundle"/></td>
							<td><html:text readonly="${not isShowSaves}" property="field(documentProveIssueBy)"
										   size="80"
										   styleClass="contactInput"
										   styleId="field(documentProveIssueBy)"
										/>
							</td>
						</tr>

				</table>
        </fieldset>
        </div>
        </td>
    </tr>

	<tr>
		<td>
		<fieldset>
		<legend><bean:message key="label.contactInfo" bundle="personsBundle"/></legend>
			<table cellpadding="0"cellspacing="0" width="100%">
			<tr>
				<td class="pmntInfAreaTitle" colspan="2"><bean:message key="label.registrationAddress" bundle="personsBundle"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationPostalCode" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationPostalCode)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationProvince" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationProvince)" size="40" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationDistrict" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationDistrict)" size="40" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationCity" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationCity)" size="40" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationStreet" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationStreet)" size="20" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationHouse" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationHouse)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationBuilding" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationBuilding)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.registrationFlat" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(registrationFlat)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="pmntInfAreaTitle" colspan="2"><bean:message key="label.residenceAddress" bundle="personsBundle"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residencePostalCode" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residencePostalCode)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residenceProvince" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residenceProvince)" size="40" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residenceDistrict" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residenceDistrict)" size="40" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residenceCity" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residenceCity)" size="40" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residenceStreet" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residenceStreet)" size="20" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residenceHouse" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residenceHouse)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residenceBuilbing" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residenceBuilding)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.residenceFlat" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(residenceFlat)" size="10" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.resident" bundle="personsBundle"/></td>
				<td>
					<c:if test="${not isShowSaves}">
						<html:hidden property="field(resident)"/>
					</c:if>
					<html:radio disabled="${not isShowSaves}" property="field(resident)" styleId="field(resident)Y" value="true" style="border:0"  onclick="showNotResidentFields(false);"/> <bean:message key="label.yes"
																									bundle="personsBundle"/>
					<html:radio disabled="${not isShowSaves}" property="field(resident)" value="false" style="border:0"  onclick="showNotResidentFields(true);"/> <bean:message
						key="label.no" bundle="personsBundle"/>
				<c:if test="${(empty form.fields.resident)}">
					<script type="text/javascript">
						el = document.getElementById("field(resident)Y");
						if(el!=null)
							el.checked = true;
					</script>
				</c:if>
			</tr>
			<tr>
				<td colspan="2">
					<table style="width:100%;" cellspacing="0" cellpadding="0" border="0" id="forNotResidentMigratory">
						<tr>
							<td class="pmntInfAreaTitle" colspan="2"><bean:message key="label.migratoryMap" bundle="personsBundle"/></td>
						</tr>						
						<tr>
							<td class="Width200 LabelAll" nowrap="true">
								<bean:message key="label.migratoryMap.number" bundle="personsBundle"/>
							</td>
							<td style="padding-left:2px">
								<html:text readonly="${not isShowSaves}" property="field(migratoryCardNumber)" size="10" styleClass="contactInput" maxlength="11"/>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll"  nowrap="true">
								<bean:message key="label.migratoryMap.dateFrom" bundle="personsBundle"/>
							</td>
							<td style="padding-left:2px">
								<input type="text"
								   name="field(migratoryCardFromDate)"
								   id="field(migratoryCardFromDate)"
								   value='<bean:write name="form"  property="fields.migratoryCardFromDate" format="dd.MM.yyyy"/>'
								   size="10" class="contactInput"  onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
								   <c:if test="${not isShowSaves}">
										readonly
									</c:if>
								/>
							</td>
						</tr>
						<tr>
							<td class="Width200 LabelAll" nowrap="true"><bean:message key="label.migratoryMap.dateEnd" bundle="personsBundle"/></td>
							<td style="padding-left:2px">
								<input type="text"
								   name="field(migratoryCardTimeUpDate)"
								   id="field(migratoryCardTimeUpDate)"
								   value='<bean:write name="form"  property="fields.migratoryCardTimeUpDate" format="dd.MM.yyyy"/>'
								   size="10" class="contactInput"  onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
								   <c:if test="${not isShowSaves}">
										readonly
									</c:if>
								/>
							</td>
						</tr>
					</table>
					<script type="text/javascript">
						<c:choose>
							<c:when test="${empty form.fields.resident}">showNotResidentFields(false);</c:when>
							<c:otherwise>showNotResidentFields(${!form.fields.resident});</c:otherwise>
						</c:choose>
					</script>
				</td>
			</tr>
        			<script type="text/javascript">
						<c:choose>
							<c:when test="${empty form.fields.resident}">showNotResidentFields(false);</c:when>
							<c:otherwise>showNotResidentFields(${!form.fields.resident});</c:otherwise>
						</c:choose>
					</script>
				</td>
			</tr>
			<!--���������� �������������-->
			<tr>

				<td class="Width200 LabelAll"><bean:message key="label.pensionCertificate" bundle="personsBundle"/></td>
				<td><html:text readonly="${not isShowSaves}" property="field(pensionCertificate)" size="40" maxlength="15" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.email" bundle="personsBundle"/></td>
				<td><html:text disabled="${not isShowSaves}" property="field(email)" size="40" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.homePhone" bundle="personsBundle"/></td>
				<td><html:text disabled="${not isShowSaves}" property="field(homePhone)" size="20" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.jobPhone" bundle="personsBundle"/></td>
				<td><html:text disabled="${not isShowSaves}" property="field(jobPhone)" size="20" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.mobilePhone" bundle="personsBundle"/><span
						class="asterisk">*</span></td>
				<td><html:text disabled="${not isShowSaves}" property="field(mobilePhone)" size="20" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.mobileOperator" bundle="personsBundle"/><span
						class="asterisk">*</span></td>
				<td><html:text disabled="${not isShowSaves}" property="field(mobileOperator)" size="20" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.agreementNumber" bundle="personsBundle"/></td>
				<td><html:text property="field(agreementNumber)" size="20" styleClass="contactInput" disabled="true"/></td>
				<td><html:hidden property="field(agreementNumber)" styleClass="contactInput"/></td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.pinEnvelopeNumber" bundle="personsBundle"/> <span
						class="asterisk">*</span></td>
				<td>
					<html:text disabled="${not isShowSaves}" property="field(pinEnvelopeNumber)" size="20" styleClass="contactInput"/>
					<html:hidden property="field(oldPinEnvelopeNumber)"/>
				</td>
			</tr>
			<tr>
				<td class="Width200 LabelAll"><bean:message key="label.serviceInsertionDate" bundle="personsBundle"/> <span
						class="asterisk">*</span></td>
				<td>
					<input type="text"
						   name="field(serviceInsertionDate)"
						   value='<bean:write name="form" property="fields.serviceInsertionDate" format="dd.MM.yyyy"/>'
						   size="10" class="contactInput"  onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
						   <c:if test="${not isShowSaves}">
								disabled
							</c:if>
							/>
				</td>
			</tr>
			</tr>
				</table>
		</fieldset>
		</td>
	</tr>
	</tiles:put>
</tiles:insert>
