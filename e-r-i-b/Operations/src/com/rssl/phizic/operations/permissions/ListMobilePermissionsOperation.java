package com.rssl.phizic.operations.permissions;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.advertising.ViewAdvertisingBlockOperation;
import com.rssl.phizic.operations.autopayments.GetAutoPaymentInfoOperation;
import com.rssl.phizic.operations.autopayments.links.ListAutoPaymentLinksOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionLinksOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.departments.CreditCardOfficeOperation;
import com.rssl.phizic.operations.dictionaries.GetBankListOperation;
import com.rssl.phizic.operations.dictionaries.GetOperationCodesOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ListMobilePaymentTemplatesOperation;
import com.rssl.phizic.operations.limits.MobileCheckLimitOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import com.rssl.phizic.operations.longoffers.links.ListLongOfferLinksOperation;
import com.rssl.phizic.operations.loyalty.LoyaltyGenRefOperation;
import com.rssl.phizic.operations.loyaltyProgram.LoyaltyProgramInfoOperation;
import com.rssl.phizic.operations.payment.GetCommonPaymentListOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.operations.documents.templates.RemoveClientTemplateOperation;
import com.rssl.phizic.operations.userprofile.HideProductMobileOperation;
import com.rssl.phizic.operations.userprofile.HideTemplatesMobileOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.util.MobileApiUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Список доступных операций в мобильном приложении
 * @author Dorzhinov
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMobilePermissionsOperation extends OperationBase
{
    private Map<String, Boolean> permissions; //Карта <ключ услуги mAPI, доступность клиенту данной услуги>
    private OperationFactory operationFactory;
	private Map<String, Integer> dictionaries; //Карта <ключ справочника доверенных получателей, доступность 1-доступен/0 недоступен.>
	private AddressBookService addressBookService = new AddressBookService();

    public void initialize(OperationFactory operationFactory) throws BusinessException
    {
        setFactory(operationFactory);
        permissions = new HashMap<String, Boolean>();

        permissions.put("Permissions", check(ListMobilePermissionsOperation.class)); //self
        permissions.put("Products", (check(GetAccountAbstractExtendedOperation.class) && check(GetAccountsOperation.class))
                || check(GetCardsOperation.class) || check(GetLoanListOperation.class)
                || (check("GetIMAccountShortAbstractOperation") && check("GetIMAccountOperation")));
	    permissions.put("ReceiveLoansAmongAll", checkService("ReceiveLoansOnLogin"));
        permissions.put("Offers", check(GetLoanOfferViewOperation.class) || check(GetLoanCardOfferViewOperation.class));
        permissions.put("Advertising", check(ViewAdvertisingBlockOperation.class));
        permissions.put("Payments", check(GetCommonPaymentListOperation.class, "PaymentList"));
        permissions.put("Rates", checkService("CurrenciesRateInfo"));
        permissions.put("Templates", check(ListTemplatesOperation.class));
	    permissions.put("RemoveTemplate", MobileApiUtil.isAuthorizedZone() && check(RemoveClientTemplateOperation.class));

        permissions.put("CardAbstract", check(GetCardAbstractOperation.class));

        permissions.put("AccountAbstract", check(GetAccountAbstractExtendedOperation.class));

        permissions.put("LoanInfo", check(GetLoanInfoOperation.class));
        permissions.put("LoanAbstract", check(GetLoanInfoOperation.class) && check(GetLoanAbstractOperation.class));

        permissions.put("IMAccountInfo", check("GetIMAccountFullAbstractOperation") || check("GetIMAccountShortAbstractOperation"));
        permissions.put("IMAccountAbstract", check("GetIMAccountFullAbstractOperation") || check("GetIMAccountShortAbstractOperation"));

        permissions.put("ChangeProductName", check(EditExternalLinkOperation.class));

        permissions.put("RegularPayments", check(ListAutoPaymentLinksOperation.class) || check(ListLongOfferLinksOperation.class)
                || check(ListAutoSubscriptionLinksOperation.class, "AutoSubscriptionLinkManagment"));

        permissions.put("AutoPaymentInfo", check(GetAutoPaymentInfoOperation.class));
        permissions.put("LongOfferInfo", check(GetLongOfferInfoOperation.class));
        permissions.put("AutoSubscriptionInfo", check(GetAutoSubscriptionInfoOperation.class, "AutoSubscriptionLinkManagment"));

        permissions.put("FinanceCategories",        checkService("CategoriesCostsService"));
	    permissions.put("FinanceCategoriesEdit",    checkService("EditCategoriesService"));
	    permissions.put("FinanceOperations",        checkService("FinanceOperationsService"));
        permissions.put("FinanceOperationsEdit",    checkService("EditOperationsService"));
        permissions.put("Targets",                  checkService("TargetsService"));

        permissions.put("ChangeRegion", check("RegionsListOperation"));
        permissions.put("LoyaltyExternal", check(LoyaltyGenRefOperation.class));
        permissions.put("LoyaltyInternal", check(LoyaltyProgramInfoOperation.class));
	    permissions.put("Stash", checkService("StashService"));

        permissions.put("MBTemplates", check(ListMobilePaymentTemplatesOperation.class));

        permissions.put("RurPayJurSB",                      checkService(FormConstants.SERVICE_PAYMENT_FORM));
        permissions.put("InternalPayment",                  checkService(FormConstants.INTERNAL_PAYMENT_FORM));
        permissions.put("RurPayment",                       checkService(FormConstants.RUR_PAYMENT_FORM));
        permissions.put("LoanPayment",                      checkService(FormConstants.LOAN_PAYMENT_FORM));
        permissions.put("JurPayment",                       checkService(FormConstants.JUR_PAYMENT_FORM));
        permissions.put("BlockingCardClaim",                checkService(FormConstants.BLOCKING_CARD_CLAIM));
        permissions.put("CreateAutoPaymentPayment",         checkService(FormConstants.CREATE_AUTOPAYMENT_FORM));
        permissions.put("EditAutoPaymentPayment",           checkService(FormConstants.EDIT_AUTOPAYMENT_FORM));
        permissions.put("RefuseAutoPaymentPayment",         checkService(FormConstants.REFUSE_AUTOPAYMENT_FORM));
        permissions.put("CreateAutoSubscriptionPayment",    checkService("ClientCreateAutoPayment"));
        permissions.put("EditAutoSubscriptionPayment",      checkService(FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT));
	    permissions.put("DelayAutoSubscriptionPayment",     checkService(FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM));
	    permissions.put("RecoveryAutoSubscriptionPayment",  checkService(FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM));
        permissions.put("CloseAutoSubscriptionPayment",     checkService(FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM));
        permissions.put("AccountOpeningClaim",              checkService(FormConstants.ACCOUNT_OPENING_CLAIM_FORM));
        permissions.put("AccountClosingPayment",            checkService(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM));
        permissions.put("IMAPayment",                       checkService(FormConstants.IMA_PAYMENT_FORM));
        permissions.put("LoanCardProduct",                  checkService(FormConstants.LOAN_CARD_PRODUCT_FORM));
        permissions.put("LoanCardOffer",                    checkService(FormConstants.LOAN_CARD_OFFER_FORM));
        permissions.put("CreditCardOffice",                 check(CreditCardOfficeOperation.class, "CreditCardOfficeService")); //справочник кред.офисов для получения предодобренной кред.карты
        permissions.put("LoanProduct",                      checkService(FormConstants.LOAN_PRODUCT_FORM));
        permissions.put("LoanOffer",                        checkService(FormConstants.LOAN_OFFER_FORM));
        permissions.put("IMAOpeningClaim",                  checkService(FormConstants.IMA_OPENING_CLAIM));
        permissions.put("CreateLongOfferPayment",           checkService("CreateLongOfferPayment"));
        permissions.put("CreateLongOfferPaymentForRur",     checkService("CreateLongOfferPaymentForRur"));
        permissions.put("RefuseLongOffer",                  checkService(FormConstants.REFUSE_LONGOFFER_FORM));
        permissions.put("ExternalProviderPayment",          checkService(FormConstants.EXTERNAL_PROVIDER_PAYMENT_FORM));
		permissions.put("AirlineReservationPayment",        checkService(FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM));
		permissions.put("RefundGoodsClaim",                 checkService(FormConstants.REFUND_GOODS_FORM));
		permissions.put("RollbackOrderClaim",               checkService(FormConstants.RECALL_ORDER_FORM));
	    permissions.put("NewRurPayment",                    checkService(FormConstants.NEW_RUR_PAYMENT));

        permissions.put("BanksDictionary", check(GetBankListOperation.class));
        permissions.put("OperationCodesDictionary", check(GetOperationCodesOperation.class));
        permissions.put("RegionsDictionary", check("RegionsListOperation"));

        permissions.put("News", checkService("ViewNewsManagment"));
        permissions.put("Mail", checkService("ClientMailManagment"));

	    permissions.put("MobileWallet", check(MobileCheckLimitOperation.class));
	    permissions.put("HideProduct",  check(HideProductMobileOperation.class));
	    permissions.put("HideTemplate", check(HideTemplatesMobileOperation.class));

	    permissions.put("ContactSync",  checkService("ContactSyncService"));

	    permissions.put("InternetOrderPayments", checkService("InternetOrderPayments"));

	    permissions.put("AccountBankDetails", checkService("AccountBankDetailsService"));
	    permissions.put("ViewFinanceMobile", checkService("ViewFinanceMobileService"));
	    permissions.put("ClientProfilePush", checkService("ClientProfilePush"));
	    //Добавление/удаление операции в АЛФ
	    permissions.put("AddOperations", checkService("AddOperationsService"));
	    //Перевод на карту Visa в другой банк
	    permissions.put("VisaMoneyTransfer", checkService("VisaMoneyTransferService"));
	    //Перевод на карту MasterCard в другой банк
	    permissions.put("MasterCardMoneySend", checkService("MasterCardMoneyTransferService"));
    }

	/**
	 * Инициализация состояния справочников доверенных получателей. Для версии API начиная с 6й.
	 * @throws BusinessException
	 */
	public void initializeDictionariesStates() throws BusinessException
	{
		dictionaries = new HashMap<String, Integer>();
		Long loginId = AuthModule.getAuthModule().getPrincipal().getLogin().getId();
		dictionaries.put("ourCard", 0);
		dictionaries.put("ourPhone", Math.max(addressBookService.getClientContacts(loginId).size(), 1));
		dictionaries.put("ourAccount", 0);
		dictionaries.put("extAccount", 0);
		dictionaries.put("extMasterCard", 0);
		dictionaries.put("extVisa", 0);
	}

	public Map<String, Integer> getDictionaries()
	{
		return dictionaries;
	}

    public Map<String, Boolean> getPermissions()
    {
        return permissions;
    }

	public void setPermissions(Map<String, Boolean> permissions)
	{
		this.permissions = permissions;
	}

	protected boolean check(Class operationClass) throws BusinessException
    {
        return operationFactory.checkAccess(operationClass);
    }

    protected boolean check(String operationKey) throws BusinessException
    {
        return operationFactory.checkAccess(operationKey);
    }

    protected boolean check(Class operationClass, String serviceKey) throws BusinessException
    {
        return operationFactory.checkAccess(operationClass, serviceKey);
    }

    protected boolean checkService(String serviceKey)
    {
	    return PermissionUtil.impliesServiceRigid(serviceKey);
    }

	protected void setFactory(OperationFactory operationFactory)
	{
		this.operationFactory = operationFactory;
	}
}