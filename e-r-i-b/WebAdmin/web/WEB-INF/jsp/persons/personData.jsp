<c:choose>
	<c:when test="${!(empty EditPersonForm)}">
		<c:set var="form" value="${EditPersonForm}"/>
		<c:set var="path" value=""/>
	</c:when>
	<c:when test="${!(empty AssignPersonAccessForm)}">
		<c:set var="form" value="${AssignPersonAccessForm}"/>
		<c:set var="path" value=""/>
	</c:when>
	<c:when test="${!(empty ListPersonResourcesForm)}">
		<c:set var="form" value="${ListPersonResourcesForm}"/>
		<c:set var="path" value=""/>
	</c:when>
	<c:when test="${!(empty EmpoweredPersonsListForm)}">
		<c:set var="form" value="${EmpoweredPersonsListForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ShowPaymentReceiverListForm)}">
		<c:set var="form" value="${ShowPaymentReceiverListForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty EditEmpoweredPersonForm)}">
		<c:set var="form" value="${EditEmpoweredPersonForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty EditPaymentReceiverForm)}">
		<c:set var="form" value="${EditPaymentReceiverForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty PaymentReceiverJListForm)}">
		<c:set var="form" value="${PaymentReceiverJListForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ListPersonGroupsForm)}">
		<c:set var="form" value="${ListPersonGroupsForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ListUserPassworCardsForm)}">
		<c:set var="form" value="${ListUserPassworCardsForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty PasswordCardUnblockPasswordForm)}">
		<c:set var="form" value="${PasswordCardUnblockPasswordForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ShowDictionaryPackageForm)}">
		<c:set var="form" value="${ShowDictionaryPackageForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
	<c:when test="${!(empty ListPersonCertificateForm)}">
		<c:set var="form" value="${ListPersonCertificateForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
</c:choose>

<c:set var="person" value="${form.activePerson}"/>

<c:set var="personId" value="${empty param.person or param.person==0 ? '$$newId' : param.person}"/>
<c:set var="login"  value="${person.login}"/>
<c:set var="isNewOrTemplate" value="${empty form.activePerson  or  person.status == 'T'}"/>