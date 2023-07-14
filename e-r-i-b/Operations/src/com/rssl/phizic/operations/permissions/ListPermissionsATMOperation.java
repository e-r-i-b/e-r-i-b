package com.rssl.phizic.operations.permissions;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.exceptions.DegradationFromUDBOToCardException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.autopayments.GetAutoPaymentInfoOperation;
import com.rssl.phizic.operations.autopayments.links.ListAutoPaymentLinksOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionLinksOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.departments.CreditCardOfficeOperation;
import com.rssl.phizic.operations.dictionaries.GetBankListOperation;
import com.rssl.phizic.operations.dictionaries.GetOperationCodesOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import com.rssl.phizic.operations.longoffers.links.ListLongOfferLinksOperation;
import com.rssl.phizic.operations.loyaltyProgram.LoyaltyProgramInfoOperation;
import com.rssl.phizic.operations.payment.GetCommonPaymentListOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.operations.documents.templates.RemoveClientTemplateOperation;
import com.rssl.phizic.operations.pfr.ListPFRClaimOperation;
import com.rssl.phizic.operations.pfr.ViewPFRStatementATMOperation;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.CardsConfig;

import java.util.*;

/**
 * Список доступных операций для УС
 * @author Pankin
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ListPermissionsATMOperation extends ListMobilePermissionsOperation
{
	private static final PersonImportService personImportService = new PersonImportService();

	public void checkUDBO(boolean needCheckUDBO) throws BusinessLogicException, BusinessException
	{
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		if (!needCheckUDBO || authenticationContext.isCheckedCEDBO())
			return;
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		AuthData authData = restoreAuthData();

		Client client = LoginHelper.findClient(authData, Collections.singletonList(person));
		if (client.isUDBO())
			LoginHelper.updatePerson(person, client, authData.getDocument(), UserVisitingMode.BASIC, authData);
		else
		{
			if (person.getCreationType() == CreationType.UDBO && client.getCancellationDate() != null)
			{
				// Если клиент был УДБО, но признак заключенного УДБО не вернулся, смотрим, надо ли
				// переводить его в разряд "карточных" клиентов

				boolean changeCreationType = ConfigFactory.getConfig(CardsConfig.class).isUdboToCard();
				//Если можно переводить и наступила дата закрытия УДБО, то переводим в "карточного" клиента
				//только в этом случае меняем
				if (changeCreationType && !client.getCancellationDate().after(Calendar.getInstance()))
				{
					personImportService.changeCreationType(person, CreationType.CARD, authData.getProfileType());
					LoginHelper.updateCSAProfile(authData, person);

					throw new DegradationFromUDBOToCardException(person.getLogin(), client.getCancellationDate());
				}
			}
		}
		CommonLogin login = authenticationContext.getLogin();
		AccessPolicy policy = authenticationContext.getPolicy();
		Properties props = authenticationContext.getPolicyProperties();

		PrincipalImpl principal = login == null ? null : new PrincipalImpl(login, policy, props);
		SecurityUtil.createAuthModule(principal);
	}

	public void initialize(OperationFactory operationFactory) throws BusinessException
	{
		setFactory(operationFactory);
		Map<String,Boolean> permissions = new HashMap<String, Boolean>();

		permissions.put("Permissions", check(ListPermissionsATMOperation.class));
        permissions.put("Products", (check(GetAccountAbstractExtendedOperation.class) && check(GetAccountsOperation.class))
                || check(GetCardsOperation.class) || check(GetLoanListOperation.class)
                || (check("GetIMAccountShortAbstractOperation") && check("GetIMAccountOperation")));

        permissions.put("CardAbstract", check(GetCardAbstractOperation.class));

        permissions.put("AccountAbstract", check(GetAccountAbstractExtendedOperation.class));

        permissions.put("LoanInfo", check(GetLoanInfoOperation.class));
        permissions.put("LoanAbstract", check(GetLoanInfoOperation.class) && check(GetLoanAbstractOperation.class));

        permissions.put("IMAccountAbstract", (check("GetIMAccountFullAbstractOperation") || check("GetIMAccountShortAbstractOperation")) && check("GetIMAccountOperation"));

        permissions.put("RegularPayments", check(ListAutoPaymentLinksOperation.class) || check(ListLongOfferLinksOperation.class)
                || check(ListAutoSubscriptionLinksOperation.class, "AutoSubscriptionLinkManagment"));

        permissions.put("AutoPaymentInfo", check(GetAutoPaymentInfoOperation.class));
        permissions.put("LongOfferInfo", check(GetLongOfferInfoOperation.class));
        permissions.put("AutoSubscriptionInfo", check(GetAutoSubscriptionInfoOperation.class, "AutoSubscriptionLinkManagment"));

		permissions.put("Payments", check(GetCommonPaymentListOperation.class, "PaymentList"));

        permissions.put("InternalPayment",          checkService(FormConstants.INTERNAL_PAYMENT_FORM));
		permissions.put("CreateLongOfferPayment",           checkService("CreateLongOfferPayment"));
        permissions.put("RurPayment",               checkService(FormConstants.RUR_PAYMENT_FORM));
		permissions.put("CreateLongOfferPaymentForRur",     checkService("CreateLongOfferPaymentForRur"));
        permissions.put("JurPayment",               checkService(FormConstants.JUR_PAYMENT_FORM));
		permissions.put("RurPayJurSB",              checkService(FormConstants.SERVICE_PAYMENT_FORM));
        permissions.put("CreateAutoPaymentPayment", checkService(FormConstants.CREATE_AUTOPAYMENT_FORM));
		permissions.put("CreateAutoSubscriptionPayment",    checkService("ClientCreateAutoPayment"));
        permissions.put("EditAutoPaymentPayment",   checkService(FormConstants.EDIT_AUTOPAYMENT_FORM));
        permissions.put("RefuseAutoPaymentPayment", checkService(FormConstants.REFUSE_AUTOPAYMENT_FORM));
        permissions.put("EditAutoSubscriptionPayment",      checkService(FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT));
        permissions.put("CloseAutoSubscriptionPayment",     checkService(FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM));
        permissions.put("RefuseLongOffer",          checkService(FormConstants.REFUSE_LONGOFFER_FORM));

		permissions.put("Templates", check(ListTemplatesOperation.class));
		permissions.put("RemoveTemplate", check(RemoveClientTemplateOperation.class));

        permissions.put("BanksDictionary", check(GetBankListOperation.class));
        permissions.put("OperationCodesDictionary", check(GetOperationCodesOperation.class));
        permissions.put("RegionsDictionary", check("RegionsListOperation"));
        permissions.put("Rates", checkService("CurrenciesRateInfo"));

		permissions.put("LoanCardProduct",          checkService(FormConstants.LOAN_CARD_PRODUCT_FORM));
		permissions.put("LoanCardOffer",            checkService(FormConstants.LOAN_CARD_OFFER_FORM));

		permissions.put("LoyaltyProgramRegistrationClaim" , checkService(FormConstants.LOYALTY_PROGRAM_REGISTRATION_CLAIM));

		permissions.put("AccountClosingPayment", checkService(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM));
		permissions.put("AccountOpeningClaim",   checkService(FormConstants.ACCOUNT_OPENING_CLAIM_FORM));
		permissions.put("CreditCardOffice",      check(CreditCardOfficeOperation.class, "CreditCardOfficeService"));
		permissions.put("LoyaltyInternal",       check(LoyaltyProgramInfoOperation.class));
		permissions.put("Offers",                check(GetLoanOfferViewOperation.class) || check(GetLoanCardOfferViewOperation.class));
		permissions.put("PFRClaimsList",         check(ListPFRClaimOperation.class));
		permissions.put("PFRStatement",          check(ViewPFRStatementATMOperation.class));
		permissions.put("PFRStatementClaim",     checkService(FormConstants.PFR_STATEMENT_CLAIM));
		permissions.put("Registration",          Boolean.TRUE);

		setPermissions(permissions);
	}

	private AuthData restoreAuthData()
	{
		AuthData authData = new AuthData();
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		authData.setSID(authenticationContext.getCSA_SID());
		authData.setASPKey(authenticationContext.getRandomString());
		authData.setMoveSession(authenticationContext.isMoveSession());
		authData.setUserId(authenticationContext.getUserId());
		authData.setUserAlias(authenticationContext.getUserAlias());
		authData.setMB(authenticationContext.isMB());
		authData.setCB_CODE(authenticationContext.getCB_CODE());
		authData.setPAN(authenticationContext.getPAN());
		authData.setLastName(authenticationContext.getLastName());
		authData.setFirstName(authenticationContext.getFirstName());
		authData.setMiddleName(authenticationContext.getMiddleName());
		authData.setDocument(authenticationContext.getDocumentNumber());
		authData.setBirthDate(authenticationContext.getBirthDate());
		authData.setCameFromYoungWebsite(authenticationContext.isCameFromYoungPeopleWebsite());
		authData.setLastLoginDate(PersonContext.getPersonDataProvider().getPersonData().getLogin().getLastLogonDate());
		authData.setLoginType(authenticationContext.getLoginType());
		authData.setExpiredPassword(authenticationContext.isExpiredPassword());
		authData.setCsaGuid(authenticationContext.getCsaGuid());
		authData.setCsaType(authenticationContext.getCsaType());
		authData.setMobileAppScheme(authenticationContext.getMobileAppScheme());
		authData.setRegistrationStatus(authenticationContext.getRegistrationStatus());
		authData.setDeviceId(authenticationContext.getDeviceId());
		authData.setDeviceInfo(authenticationContext.getDeviceInfo());
		authData.setBrowserInfo(authenticationContext.getBrowserInfo());
		return authData;
	}
}
