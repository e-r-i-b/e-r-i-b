package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.utils.DateHelper;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 24.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Класс, от которого следует наследоваться всем отчетам по бизнес-операциям,
 * здесь следует менять общие для данных отчетов параметры для sql запросов
 */

public abstract class CountOperations  extends SubReports implements ReportAdditionalInfo
{
	private long id;                         
	private ReportAbstract report_id;
	private long count;                    // количество операций

	private static Map<String, String> sqlParams = new HashMap<String, String>();    // значения для поля business_documents

	static
	{
		// статус платежей
		sqlParams.put("stateCodeSavedTemplate", "TEMPLATE");
		sqlParams.put("stateCodeInitial", "INITIAL");
		sqlParams.put("stateCodeSaved", "SAVED");
		sqlParams.put("stateCodeTemplate", "SAVED_TEMPLATE");

		// тип приложения
		sqlParams.put("application", "PhizIC");

		// тип платежей
		sqlParams.put("stopCardKind", "Q");
		sqlParams.put("stopAccountKind", "O");
		sqlParams.put("paymentKindR", "R");
		sqlParams.put("paymentKindE", "E");
		sqlParams.put("toForeignAccountKind", "H");
		sqlParams.put("creditKind", "T");
		sqlParams.put("toBillingOrganization", "P");
		sqlParams.put("taxPayment", "I");
		sqlParams.put("PFRKind", "Y");
		sqlParams.put("loanProduct", "6");
		sqlParams.put("loanOffer", "4");
		sqlParams.put("loanCardProduct", "7");
		sqlParams.put("loanCardOffer", "5");
		sqlParams.put("virtualCard", "8");

		// внешние поля
		sqlParams.put("from_resource_type", "from-resource-type");
		sqlParams.put("receiver_type", "receiver-type");
		sqlParams.put("to_resource_type", "to-resource-type");
		sqlParams.put("receiver_name", "receiver-name");
		sqlParams.put("resourceAccount", "com.rssl.phizic.business.resources.external.AccountLink");
		sqlParams.put("resourceCard",    "com.rssl.phizic.business.resources.external.CardLink");
		sqlParams.put("ph", "ph");
		sqlParams.put("jur", "jur");

		sqlParams.put("tax", "TAX");
	}


	protected static Map<String, String> sqlParamsDescription = new HashMap<String, String>();    // значения для поля description

	static
	{
		// работа с профилем клиента
		sqlParamsDescription.put("userProfileEdit", "com.rssl.phizic.web.client.userprofile.EditUserSettingsAction.save");
		sqlParamsDescription.put("listFavouriteEdit", "com.rssl.phizic.web.client.favourite.ListFavouriteAction.save");
		sqlParamsDescription.put("mainMenuEdit", "com.rssl.phizic.web.client.userprofile.MainMenuSettingsAction.save");
		sqlParamsDescription.put("setupAccountsViewInMobile", "com.rssl.phizic.web.client.ext.sbrf.accounts.SetupProductsSystemViewAction.saveShowInMobile");
		sqlParamsDescription.put("setupAccountsViewInSocial", "com.rssl.phizic.web.client.ext.sbrf.accounts.SetupProductsSystemViewAction.saveShowInSocial");
		sqlParamsDescription.put("setupAccountsViewInSystem", "com.rssl.phizic.web.client.ext.sbrf.accounts.SetupProductsSystemViewAction.saveShowInSystem");
		sqlParamsDescription.put("setupAccountsViewInES", "com.rssl.phizic.web.client.ext.sbrf.accounts.SetupProductsSystemViewAction.saveShowInES");
		sqlParamsDescription.put("newPassword", "com.rssl.phizic.web.client.userprofile.SetupSecurityAction.newPassword");
		sqlParamsDescription.put("setupSecurity", "com.rssl.phizic.web.client.userprofile.SetupSecurityAction.save");

		// информация по кредитам
		sqlParamsDescription.put("viewCredit", "com.rssl.phizic.web.client.ext.sbrf.loans.PrintLoanInfoAction.start");
		sqlParamsDescription.put("creditProducts", "com.rssl.phizic.web.client.ext.sbrf.loans.PrintLoanPaymentInfoAction.start");
		sqlParamsDescription.put("creditCalculator", "com.rssl.phizic.web.client.ext.sbrf.loans.PrintLoanPaymentsAction.start");
		sqlParamsDescription.put("createCredit", "com.rssl.phizic.web.client.ext.sbrf.loans.ShowLoanInfoAction.start");
		sqlParamsDescription.put("infoCredit", "com.rssl.phizic.web.client.ext.sbrf.loans.ShowLoanInfoAction.saveLoanName");
		sqlParamsDescription.put("infoCreditProduct", "com.rssl.phizic.web.client.ext.sbrf.loans.ShowLoanPaymentInfoAction.start");
		sqlParamsDescription.put("resultCredit", "com.rssl.phizic.web.client.ext.sbrf.loans.ShowLoansListAction.start");

		// печать чеков
		sqlParamsDescription.put("printCheck", "com.rssl.phizic.web.client.payments.forms.PrintCheckAction.start");

		//Предоставление информации о 10 последних операциях по карте
		sqlParamsDescription.put("cardDetail", "com.rssl.phizic.web.client.ext.sbrf.cards.ShowCardDetailAction.start");
		sqlParamsDescription.put("operationInfo", "com.rssl.phizic.web.client.ext.sbrf.cards.ShowCardInfoAction.start");

		/* Подтверждение одноразовым паролем */
		sqlParamsDescription.put("entryPassword", "com.rssl.phizic.web.client.ext.sbrf.security.AsyncConfirmLoginAction.validate");
		sqlParamsDescription.put("setupSecurityActionConfirm", "com.rssl.phizic.web.client.userprofile.SetupSecurityAction.confirm");
		sqlParamsDescription.put("setupAccountsSystemViewConfirmEnter", "com.rssl.phizic.web.client.ext.sbrf.accounts.AsyncSetupProductsSystemViewAction.confirmEnter");
		sqlParamsDescription.put("setupAccountsSystemViewConfirmSystem", "com.rssl.phizic.web.client.ext.sbrf.accounts.AsyncSetupProductsSystemViewAction.confirmSystem");
		sqlParamsDescription.put("setupAccountsSystemViewConfirmES", "com.rssl.phizic.web.client.ext.sbrf.accounts.AsyncSetupProductsSystemViewAction.confirmES");
		sqlParamsDescription.put("setupAccountsSystemViewConfirmMobile", "com.rssl.phizic.web.client.ext.sbrf.accounts.AsyncSetupProductsSystemViewAction.confirmMobile");
		sqlParamsDescription.put("setupAccountsSystemViewConfirmSocial", "com.rssl.phizic.web.client.ext.sbrf.accounts.AsyncSetupProductsSystemViewAction.confirmSocial");
		sqlParamsDescription.put("editUserSettingsActionConfirm", "com.rssl.phizic.web.client.userprofile.EditUserSettingsAction.confirm");
		sqlParamsDescription.put("documentConfirm", "com.rssl.phizic.web.actions.payments.forms.AsyncConfirmDocumentAction.confirm");
		sqlParamsDescription.put("templateConfirm", "com.rssl.phizic.web.client.payments.forms.ConfirmTemplateAction.confirm");

		/*Создание шаблона мобильного банка*/
		sqlParamsDescription.put("mobileTemplate2", "com.rssl.phizic.web.client.ext.sbrf.mobilebank.CreateSmsTemplateAction.save");
		 /* Создание шаблонов для мобильного банка(шаблон подтвержден) */
		sqlParamsDescription.put("mobileTemplate1", "com.rssl.phizic.web.client.ext.sbrf.mobilebank.ConfirmSmsTemplateAction.confirm");		

		/* Просмотр шаблонов с использованием Мобильного банка */
		sqlParamsDescription.put("viewSmsTemplate", "com.rssl.phizic.web.client.ext.sbrf.mobilebank.ViewSmsTemplateAction.start");

		/* Вход в систему */
		sqlParamsDescription.put("auth", "default.authentification.finish");

		/* История операций*/
		sqlParamsDescription.put("operationsHistory", "com.rssl.phizic.web.client.payments.ShowCommonPaymentListAction.start");

		/* Предоставление информации по мет. счетам */
		sqlParamsDescription.put("IMAccountInfo", "com.rssl.phizic.web.common.client.ima.IMAccountInfoAction.start");
		sqlParamsDescription.put("IMAccount", "com.rssl.phizic.web.client.ima.IMAccountAction.start");
		sqlParamsDescription.put("IMAccountPrint", "com.rssl.phizic.web.client.ima.IMAccountPrintAction.start");
		sqlParamsDescription.put("IMAccountSave", "com.rssl.phizic.web.common.client.ima.IMAccountInfoAction.saveIMAccountName");

		/* Предоставление информации по счету депо */
		sqlParamsDescription.put("showDepoAccountPositionAction", "com.rssl.phizic.web.client.depo.ShowDepoAccountPositionAction.start");
		sqlParamsDescription.put("showDepoAccountsListAction", "com.rssl.phizic.web.client.depo.ShowDepoAccountsListAction.start");
		sqlParamsDescription.put("showDepoDebtInfoAction", "com.rssl.phizic.web.client.depo.ShowDepoDebtInfoAction.start");

		/* Обращение в службу поддержки */
		sqlParamsDescription.put("viewMail", "com.rssl.phizic.web.client.ext.sbrf.mail.ListMailAction.start");
		sqlParamsDescription.put("viewOutputMail", "com.rssl.phizic.web.client.ext.sbrf.mail.ArchiveMailAction.rollback");
		sqlParamsDescription.put("viewInputMail", "com.rssl.phizic.web.client.ext.sbrf.mail.EditMailAction.save");
		sqlParamsDescription.put("createResponding", "com.rssl.phizic.web.client.ext.sbrf.mail.ListSentMailAction.remove");

		/* Просмотр новостей */
	    sqlParamsDescription.put("newsList", "com.rssl.phizic.web.common.client.news.ListNewsAction.start");
	    sqlParamsDescription.put("newsView", "com.rssl.phizic.web.common.client.news.ViewNewsAction.start");

		/* просмотр главной страницы */
		sqlParamsDescription.put("titlePage", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.start");

		/* просмотр выписки из ПФР */
		sqlParamsDescription.put("viewPFRStatement", "com.rssl.phizic.web.client.pfr.ViewPFRStatementAction.start");
	}
	

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
	}

	public long getCount()
	{
		return count;
	}

	public void setCount(long count)
	{
		this.count = count;
	}
	
	/**
	 * Получаем дополнительные параметры для sql-запроса
	 * @param report - отчет
	 * @return хеш вида "ключ => значение", кот. потом будут подставляться в sql
	 */
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> map = new HashMap<String, Object>();

		Calendar endDate = DateHelper.getCurrentDate();
		endDate.setTime( report.getEndDate().getTime() );
		endDate.add(Calendar.DATE, 1);      // +1 день

		map.put("endDate", endDate);
		map.putAll(sqlParams);

		return map;
	}

	/**
	 * Обработать полученные из sql-запроса данные
	 * @param list результат sql-запроса
	 */
	public List processData(List list)
	{
		return list;
	}
}
