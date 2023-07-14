package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.reports.CountOperations;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.utils.DateHelper;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author lukina
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProactiveReport extends CountOperations
{
	private Long tb_id;
	private String tb_name;  // название ТБ
	private String type;
	private long countErrorOperations; // количество ошибочных операций
	private long smallTime;
	private long averageTime;
	private long longTime;

	public Long getTb_id()
	{
		return tb_id;
	}

	public void setTb_id(Long tb_id)
	{
		this.tb_id = tb_id;
	}

	public String getTb_name()
	{
		return tb_name;
	}

	public void setTb_name(String tb_name)
	{
		this.tb_name = tb_name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public long getCountErrorOperations()
	{
		return countErrorOperations;
	}

	public void setCountErrorOperations(long countErrorOperations)
	{
		this.countErrorOperations = countErrorOperations;
	}

	public long getSmallTime()
	{
		return smallTime;
	}

	public void setSmallTime(long smallTime)
	{
		this.smallTime = smallTime;
	}

	public long getAverageTime()
	{
		return averageTime;
	}

	public void setAverageTime(long averageTime)
	{
		this.averageTime = averageTime;
	}

	public long getLongTime()
	{
		return longTime;
	}

	public void setLongTime(long longTime)
	{
		this.longTime = longTime;
	}

	private static Map<String, String> sqlParams = new HashMap<String, String>();    // значения для поля business_documents

	static
	{
		sqlParams.put("application", "PhizIC");
	}

	protected static Map<String, String> sqlParamsDescription = new HashMap<String, String>();    // значения для поля description

	static
	{
		// вход клиента
		sqlParamsDescription.put("login", "com.rssl.phizic.web.client.ext.sbrf.security.LoginNamePasswordAction.login");

		sqlParamsDescription.put("confirmLogin", "com.rssl.phizic.web.client.ext.sbrf.security.ConfirmLoginAction.validate");

		sqlParamsDescription.put("loginDepartments", "com.rssl.phizic.web.client.ext.sbrf.security.ChooseDepartmentAction.choose");

		sqlParamsDescription.put("internetSecurity", "com.rssl.phizic.web.client.ext.sbrf.persons.UseInternetSecurityAction.start");
		sqlParamsDescription.put("useOfert", "com.rssl.phizic.web.client.ext.sbrf.persons.UseOfertAction.start");
		sqlParamsDescription.put("clientAutentication", "client.autentication");

//		информационно-управляющие
		 
		sqlParamsDescription.put("cardGraphicAbstract", "com.rssl.phizic.web.client.ext.sbrf.cards.ShowCardGraphicAbstractAction.start");
		sqlParamsDescription.put("accountGraphicAbstract", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountGraphicAbstractAction.start");

		sqlParamsDescription.put("cardAmountDetail", "com.rssl.phizic.web.client.ext.sbrf.cards.ShowCardDetailAction.start");
		sqlParamsDescription.put("cardAmountMainPage", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.cards");

		sqlParamsDescription.put("accAmountDetail", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountInfoAction.start");
		sqlParamsDescription.put("accAmountMainPage", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.accounts");

		sqlParamsDescription.put("loansDetail", "com.rssl.phizic.web.client.ext.sbrf.loans.ShowLoanInfoAction.start");
		sqlParamsDescription.put("loansDetailMainPage", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.loans");

		sqlParamsDescription.put("autoPayment", "com.rssl.phizic.web.client.autopayments.ShowAutoPaymentInfoAction.start");
		sqlParamsDescription.put("longOffer", "com.rssl.phizic.web.client.longoffers.ShowLongOfferInfoAction.start");

    	sqlParamsDescription.put("printCard", "com.rssl.phizic.web.client.ext.sbrf.cards.PrintCardInfoAction.start");
		sqlParamsDescription.put("printAccount", "com.rssl.phizic.web.client.ext.sbrf.accounts.PrintAccountInfoAction.start");

		sqlParamsDescription.put("depoAccAmount", "com.rssl.phizic.web.client.depo.ShowDepoAccountsListAction.start");
		sqlParamsDescription.put("depoAccAmountDetail", "com.rssl.phizic.web.client.depo.ShowDepoAccountPositionAction.start");
		sqlParamsDescription.put("depoAccAmountDetailMainPage", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.depoAccounts");

		sqlParamsDescription.put("IMAccount", "com.rssl.phizic.web.client.ima.IMAccountAction.start");
		sqlParamsDescription.put("IMAccountDetail", "com.rssl.phizic.web.client.ima.IMAccountAction.start");
		sqlParamsDescription.put("IMAccountMainPage", "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.IMAccounts");


		sqlParamsDescription.put("accountOperation", "com.rssl.phizic.web.common.client.ext.sbrf.accounts.PrintAccountAbstractExtendedAction.start");

		sqlParamsDescription.put("confirmDocument", "com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction.confirm");
		sqlParamsDescription.put("confirmUserSettings", "com.rssl.phizic.web.client.userprofile.EditUserSettingsAction.confirm");
		sqlParamsDescription.put("confirmSetupSecurity", "com.rssl.phizic.web.client.userprofile.SetupSecurityAction.confirm");
		sqlParamsDescription.put("confirmIndividualLimit", "com.rssl.phizic.web.client.limits.IndividualLimitAction.confirm");
		sqlParamsDescription.put("confirmProductsSystemView", "com.rssl.phizic.web.client.ext.sbrf.accounts.SetupProductsSystemViewAction.confirmEnter");
		sqlParamsDescription.put("confirmUserNotifications", "com.rssl.phizic.web.client.userprofile.EditUserNotificationsAction.confirm");
		sqlParamsDescription.put("confirmUserNotificationsMail", "com.rssl.phizic.web.client.userprofile.EditUserNotificationsAction.confirmMail");
		sqlParamsDescription.put("confirmTemplate", "com.rssl.phizic.web.client.payments.forms.ConfirmTemplateAction.confirm");
		sqlParamsDescription.put("confirmSmsTemplate", "com.rssl.phizic.web.client.ext.sbrf.mobilebank.ConfirmSmsTemplateAction.confirm");


		sqlParamsDescription.put("editDocument",    "com.rssl.phizic.web.client.payments.forms.EditPaymentAction.start");
		sqlParamsDescription.put("saveDocument",    "com.rssl.phizic.web.client.payments.forms.EditPaymentAction.save");
		sqlParamsDescription.put("confirmSMS",      "com.rssl.phizic.web.actions.payments.forms.AsyncConfirmDocumentAction.changeToSMS");
		sqlParamsDescription.put("confirmCard",     "com.rssl.phizic.web.actions.payments.forms.AsyncConfirmDocumentAction.changeToCard");
		sqlParamsDescription.put("confirmCap",     "com.rssl.phizic.web.actions.payments.forms.AsyncConfirmDocumentAction.changeToCap");
		sqlParamsDescription.put("confirm",     "com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction.start");
		sqlParamsDescription.put("confirmDocument", "com.rssl.phizic.web.actions.payments.forms.AsyncConfirmDocumentAction.confirm");
		sqlParamsDescription.put("viewDocument",    "com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction.start");

		sqlParamsDescription.put("saveTemplate",    "com.rssl.phizic.web.client.payments.forms.EditTemplateAction.save");
		sqlParamsDescription.put("confirmTemplateSMS",      "com.rssl.phizic.web.client.payments.forms.ConfirmTemplateAction.changeToSMS");
		sqlParamsDescription.put("confirmTemplate", "com.rssl.phizic.web.client.payments.forms.ConfirmTemplateAction.confirm");
		sqlParamsDescription.put("viewTemplate",    "com.rssl.phizic.web.common.client.payments.forms.ViewTemplateAction.start");
		sqlParamsDescription.put("removeTemplate",    "com.rssl.phizic.web.common.client.payments.forms.ViewTemplateAction.remove");

		sqlParamsDescription.put("createAutoPayment",    "%CreateAutoPaymentImpl%");
		sqlParamsDescription.put("refuseAutoPayment",    "%com.rssl.phizic.business.documents.payments.RefuseAutoPaymentImpl%");
		sqlParamsDescription.put("editAutoPayment",    "%com.rssl.phizic.business.documents.payments.EditAutoPaymentImpl%");

		sqlParamsDescription.put("createLongOffer",    "%Длительное поручение = true%");
		sqlParamsDescription.put("showLongOffer",    "com.rssl.phizic.web.client.longoffers.ShowLongOfferInfoAction.start");
		sqlParamsDescription.put("saveLongOfferName",    "com.rssl.phizic.web.client.longoffers.ShowLongOfferInfoAction.saveLongOfferName");
		sqlParamsDescription.put("refuseLongOfferClaim",    "%com.rssl.phizic.business.documents.payments.RefuseLongOfferClaim%");


		sqlParamsDescription.put("lossPassbookApplication",    "%com.rssl.phizic.business.documents.LossPassbookApplication%");
		sqlParamsDescription.put("accountClosingPayment",    "%com.rssl.phizic.business.documents.AccountClosingPayment%");
		sqlParamsDescription.put("accountOpeningPayment",    "%com.rssl.phizic.business.documents.AccountOpeningClaim%");

		sqlParamsDescription.put("rurPayment",    "%com.rssl.phizic.business.documents.payments.RurPayment%");
		sqlParamsDescription.put("rurPaymentExtAcc",    "%Подтип получателя платежа = externalAccount%");
		sqlParamsDescription.put("depoPaymentParam",    "%Оплата задолженности по счету депо = true%");
		sqlParamsDescription.put("taxPaymentParam",    "%Налоговый платеж = true%");

		sqlParamsDescription.put("fnsPaymentSave",    "com.rssl.phizic.web.client.fns.EditPaymentFNSAction.save");
		sqlParamsDescription.put("fnsPaymentParam",    "%ФНС = true%");

		sqlParamsDescription.put("internalTransfer",    "%com.rssl.phizic.business.documents.InternalTransfer%");
		sqlParamsDescription.put("internalTransferToCard",       "%Тип счета получателя = CARD%");
		sqlParamsDescription.put("internalTransferToAccount",    "%Тип счета получателя = ACCOUNT%");


		sqlParamsDescription.put("jurPayment",    "%com.rssl.phizic.business.documents.payments.JurPayment%");
		sqlParamsDescription.put("isJurPayment",    "%Тип получателя = jur%");
		sqlParamsDescription.put("isBillingPayment",    "%Биллинговый платеж = true%");
//		sqlParamsDescription.put("isNotBillingPayment",    "%Биллинговый платеж = false%");
		sqlParamsDescription.put("choiceServiceProvider",    "com.rssl.phizic.web.client.payments.forms.EditServicePaymentAction.start");

		sqlParamsDescription.put("viewMail",    "com.rssl.phizic.web.client.ext.sbrf.mail.EditMailAction.start");
		sqlParamsDescription.put("sentMailDirection",    "%Кому отправлено письмо = ADMIN%");
		sqlParamsDescription.put("receivedMailDirection",    "%Кому отправлено письмо = CLIENT%");
		sqlParamsDescription.put("saveMail",    "com.rssl.phizic.web.client.ext.sbrf.mail.EditMailAction.save");

		sqlParamsDescription.put("saveSmsTemplate",    "com.rssl.phizic.web.client.ext.sbrf.mobilebank.CreateSmsTemplateAction.save");
		sqlParamsDescription.put("preConfirmSmsTemplate",    "com.rssl.phizic.web.client.ext.sbrf.mobilebank.ConfirmSmsTemplateAction.preConfirm");
		sqlParamsDescription.put("confirmSmsTemplate",    "com.rssl.phizic.web.client.ext.sbrf.mobilebank.ConfirmSmsTemplateAction.confirm");
		sqlParamsDescription.put("viewSmsTemplate",    "com.rssl.phizic.web.client.ext.sbrf.mobilebank.ViewSmsTemplateAction.start");

		sqlParamsDescription.put("removeSmsTemplate",    "com.rssl.phizic.web.client.ext.sbrf.mobilebank.ListPaymentsAction.remove");


		sqlParamsDescription.put("LoanPayment",    "%com.rssl.phizic.business.documents.payments.LoanPayment%");

		sqlParamsDescription.put("loanProductClaim",    "%com.rssl.phizic.business.documents.payments.LoanProductClaim%");
		sqlParamsDescription.put("choiceLoanProduct",    "com.rssl.phizic.web.common.client.product.LoanProductAction.next");

		sqlParamsDescription.put("loanCardProductClaim",    "%com.rssl.phizic.business.documents.payments.LoanCardProductClaim%");
		sqlParamsDescription.put("choiceLoanCardProduct",    "com.rssl.phizic.web.common.client.product.LoanCardProductAction.next");

		sqlParamsDescription.put("loanOfferClaim",    "%com.rssl.phizic.business.documents.payments.LoanOfferClaim%");
		sqlParamsDescription.put("choiceLoanOffer",    "com.rssl.phizic.web.client.loans.offer.LoanOfferAction.next");

		sqlParamsDescription.put("loanCardOfferClaim",    "%com.rssl.phizic.business.documents.payments.LoanCardOfferClaim%");
		sqlParamsDescription.put("choiceLoanCardOffer",    "com.rssl.phizic.web.client.loans.offer.LoanCardOfferAction.next");

	}
	public String getQueryName(ReportAbstract report)
	{
		if (report.getParams() == null)
		{
			return "com.rssl.phizic.business.ext.sbrf.reports.getProactiveMonitoringSBRF";	
		}
		else
		{
			return "com.rssl.phizic.business.ext.sbrf.reports.getProactiveMonitoring";
		}
	}

	/**
	 * Получаем допольнительные параметры для sql-запроса
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
		map.put("minTime", 5000);
		map.put("maxTime", 10000);
		map.putAll(sqlParams);

		map.putAll(sqlParamsDescription);

		return map;
	}

	public boolean isIds()
	{
		return true;
	}

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report) throws BusinessException
	{
		this.setReport_id(report);                                  
		this.setType(obj[0].toString());
		this.setCount(Long.decode(obj[1].toString()));
		this.setCountErrorOperations(Long.decode(obj[2].toString())+Long.decode(obj[5].toString()));
		this.setSmallTime(Long.decode(obj[3].toString()));
		this.setAverageTime(Long.decode(obj[4].toString()));
		this.setLongTime(Long.decode(obj[5].toString()));
		if (report.getParams() != null)
		{
			this.setTb_id(Long.decode(obj[6].toString()));
			this.setTb_name(obj[7].toString());
		}
		return this;
	}

	/**
	 * Считает процент операций от общего числа
	 * @param count  - количество операций
	 * @return строку вида 0.00%
	 */
	public String getPercentOperations(long count)
	{
		double percentErrors = (getCount() <= 0 ? 0 : (double) count * 100 / getCount());
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return decimalFormat.format(percentErrors) + '%';
	}
}
