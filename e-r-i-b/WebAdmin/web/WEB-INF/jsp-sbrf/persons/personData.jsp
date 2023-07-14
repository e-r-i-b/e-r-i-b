<%--
  User: Zhuravleva
  Date: 19.06.2009
  Time: 17:03:20
--%>
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
    <c:when test="${!(empty GetPersonalPaymentCardForm)}">
        <c:set var="form" value="${GetPersonalPaymentCardForm}"/>
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
    <c:when test="${!(empty ChargeOffListForm)}">
		<c:set var="form" value="${ChargeOffListForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
    <c:when test="${!(empty TotalLimitForm)}">
		<c:set var="form" value="${TotalLimitForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
    <c:when test="${!(empty ProductsSetupForm)}">
		<c:set var="form" value="${ProductsSetupForm}"/>
		<c:set var="path" value="../"/>
	</c:when>
    <c:when test="${!(empty EditPersonUIForm)}">
        <c:set var="form" value="${EditPersonUIForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ProductsVisibilityForm)}">
        <c:set var="form" value="${ProductsVisibilityForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ListPayWaitingConfirmForm)}">
        <c:set var="form" value="${ListPayWaitingConfirmForm}"/>
        <c:set var="path" value=""/>
    </c:when>
     <c:when test="${!(empty ViewPayWaitingConfirmForm)}">
        <c:set var="form" value="${ViewPayWaitingConfirmForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ViewOTPRestrictionForm)}">
        <c:set var="form" value="${ViewOTPRestrictionForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty SearchPersonForm)}">
        <c:set var="form" value="${SearchPersonForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ListAutoSubscriptionForm)}">
        <c:set var="form" value="${ListAutoSubscriptionForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ListAutoSubscriptionClaimForm)}">
        <c:set var="form" value="${ListAutoSubscriptionClaimForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty CreateAutoSubscriptionPaymentForm)}">
        <c:set var="form" value="${CreateAutoSubscriptionPaymentForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty CreatePaymentForm)}">
        <c:set var="form" value="${CreatePaymentForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ShowAutoSubscriptionInfoForm)}">
        <c:set var="form" value="${ShowAutoSubscriptionInfoForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ShowAutoSubscriptionPaymentInfoForm)}">
        <c:set var="form" value="${ShowAutoSubscriptionPaymentInfoForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ListAutoPayServiceProviderForm)}">
        <c:set var="form" value="${ListAutoPayServiceProviderForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty CreateESBAutoPaymentForm)}">
        <c:set var="form" value="${CreateESBAutoPaymentForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ShowAutoSubscriptionInfoForEmployeeForm)}">
        <c:set var="form" value="${ShowAutoSubscriptionInfoForEmployeeForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty CreateEmployeePaymentForm)}">
        <c:set var="form" value="${CreateEmployeePaymentForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ShowAutoSubscriptionPaymentInfoForEmployeeForm)}">
        <c:set var="form" value="${ShowAutoSubscriptionPaymentInfoForEmployeeForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ConfirmEmployeePaymentByFormForm)}">
        <c:set var="form" value="${ConfirmEmployeePaymentByFormForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ViewEmployeePaymentForm)}">
        <c:set var="form" value="${ViewEmployeePaymentForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ListSBNKDClaimForm)}">
        <c:set var="form" value="${ListSBNKDClaimForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ViewSBNKDClaimForm)}">
        <c:set var="form" value="${ViewSBNKDClaimForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty EditMobileWalletForm)}">
        <c:set var="form" value="${EditMobileWalletForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty RestorePasswordForm)}">
        <c:set var="form" value="${RestorePasswordForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ListUserLimitsForm)}">
        <c:set var="form" value="${ListUserLimitsForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty MobileApplicationsLockForm)}">
        <c:set var="form" value="${MobileApplicationsLockForm}"/>
        <c:set var="path" value=""/>
    </c:when>
     <c:when test="${!(empty PersonHistoryIdentityEditForm)}">
        <c:set var="form" value="${PersonHistoryIdentityEditForm}"/>
        <c:set var="path" value=""/>
    </c:when>
     <c:when test="${!(empty PersonHistoryIdentityListForm)}">
        <c:set var="form" value="${PersonHistoryIdentityListForm}"/>
        <c:set var="path" value=""/>
    </c:when>

      <c:when test="${!(empty ErmbProfileConnectionForm)}">
        <c:set var="form" value="${ErmbProfileConnectionForm}"/>
        <c:set var="path" value=""/>
    </c:when>

    <c:when test="${!(empty EmpolyeeListTemplatesForm)}">
        <c:set var="form" value="${EmpolyeeListTemplatesForm}"/>
        <c:set var="path" value=""/>
    </c:when>

    <c:when test="${!(empty EmployeeViewTemplateForm)}">
        <c:set var="form" value="${EmployeeViewTemplateForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty EditPotentialPersonInfoForm)}">
        <c:set var="form" value="${EditPotentialPersonInfoForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ViewCSAActionLogForm)}">
        <c:set var="form" value="${ViewCSAActionLogForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty EditBasketRequisitesForm)}">
        <c:set var="form" value="${EditBasketRequisitesForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty ErmbSmsHistoryListForm)}">
        <c:set var="form" value="${ErmbSmsHistoryListForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty SocialApplicationsLockForm)}">
        <c:set var="form" value="${SocialApplicationsLockForm}"/>
        <c:set var="path" value=""/>
    </c:when>
    <c:when test="${!(empty EmployeeInvoicesListForm)}">
        <c:set var="form" value="${EmployeeInvoicesListForm}"/>
        <c:set var="path" value=""/>
    </c:when>
</c:choose>

<c:set var="person" value="${form.activePerson}"/>
<c:set var="personId" value="${person.id}"/>
<c:set var="login"  value="${person.login}"/>
<c:set var="isNewOrTemplate" value="${person.status == 'T'}"/>
<c:set var="isCancelation" value="${person.status == 'W'}"/>
<c:set var="isErrorCancelation" value="${person.status == 'E'}"/>
<c:set var="isSignAgreement" value="${empty person or person.status == 'S'}"/>
<c:set var="isModified" value="${form.modified}"/>
<c:set var="isUDBO" value="${form.activePerson.creationType == 'UDBO'}"/>
<c:set var="isTrusted" value="${form.activePerson.securityType == 'LOW'}"/>