package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 11.10.2005 Time: 17:54:32 */
public class ExternalResourceService extends MultiInstanceExternalResourceService
{
	public PaymentSystemIdLink addPaymentSystemIdLink(CommonLogin login, String id, Billing billing) throws BusinessException
	{
		return super.addPaymentSystemIdLink(login, id, billing, null);
	}

	public AccountLink addAccountLink(CommonLogin login, Account account, String smsAutoAlias, ErmbProductSettings ermbSettings, ProductPermission permission) throws BusinessException
	{
		return super.addAccountLink(login, account, smsAutoAlias, ermbSettings, permission,  null);
	}

	public CardLink addCardLink(CommonLogin login, Card card, String smsAutoAlias, ErmbProductSettings ermbSettings, OTPRestriction restriction, ProductPermission permission) throws BusinessException
	{
		return super.addCardLink(login, card, smsAutoAlias, ermbSettings, restriction, permission, null);
	}

	public LoanLink addLoanLink(CommonLogin login, String smsAutoAlias, ErmbProductSettings ermbSettings, Loan loan) throws BusinessException
	{
		return super.addLoanLink(login, smsAutoAlias, ermbSettings, loan, null);
	}

	public LongOfferLink addLongOfferLink(CommonLogin login, LongOffer longOffer) throws BusinessException
	{
		return super.addLongOfferLink(login, longOffer, null);
	}

	public LongOfferLink addLongOfferLink(LongOfferLink longOfferLink) throws BusinessException
	{
		return super.addLongOfferLink(longOfferLink, null);
	}

	public AutoPaymentLink addAutoPaymentLink(CommonLogin login, AutoPayment autoPayment) throws BusinessException
	{
		return super.addAutoPaymentLink(login, autoPayment, null);
	}

	public DepoAccountLink addDepoAccountLink(CommonLogin login, DepoAccount depoAccount) throws BusinessException
	{
		return super.addDepoAccountLink(login, depoAccount, null);
	}

	public IMAccountLink addIMAccountLink(CommonLogin login, IMAccount imAccount, ProductPermission permission) throws BusinessException
	{
		return super.addIMAccountLink(login, imAccount, permission, null);
	}

	public SecurityAccountLink addSecurityAccountLink(CommonLogin login, SecurityAccount securityAccount) throws BusinessException
	{
		return super.addSecurityAccountLink(login, securityAccount, null);
	}

	/**
	 * ƒобавл€ем линк на страховой продукт
	 * @param login - логин пользовател€
	 * @param insuranceApp - страховой продукт
	 * @return  линк на страховой продукт
	 * @throws BusinessException
	 */
	public InsuranceLink addInsuranceLink(CommonLogin login, InsuranceApp insuranceApp) throws BusinessException
	{
		return super.addInsuranceLink(login, insuranceApp, null);
	}

	public PaymentTemplateLink addPaymentTemplateLink(CommonLogin login, BillingServiceProvider provider, GatePaymentTemplate template)
			throws BusinessException
	{
		return super.addPaymentTemplateLink(login, provider, template, null);
	}

	public <T extends ExternalResourceLink> T findLinkById(Class<T> clazz, Long id)
			throws BusinessException
	{
		return super.findLinkById(clazz, id, null);
	}

	public <T extends ExternalResourceLink> T findInSystemLinkById(final CommonLogin login, Class<T> clazz, Long id)
			throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria = criteria.add(Expression.eq("id", id));
		criteria = criteria.add(Expression.eq("showInSystem", true));
		criteria = criteria.add(Expression.eq("loginId", login.getId()));
		return super.<T>findLinkByCriteria(criteria, null);
	}

	public <T extends ExternalResourceLink> T findInMobileLinkById(final CommonLogin login, Class<T> clazz, Long id)
			throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria = criteria.add(Expression.eq("id", id));
		criteria = criteria.add(Expression.eq("showInMobile", true));
		criteria = criteria.add(Expression.eq("loginId", login.getId()));
		return super.<T>findLinkByCriteria(criteria, null);
	}

	public <T extends ExternalResourceLink> T findInSocialLinkById(final CommonLogin login, Class<T> clazz, Long id)
			throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria = criteria.add(Expression.eq("id", id));
		criteria = criteria.add(Expression.eq("showInSocial", true));
		criteria = criteria.add(Expression.eq("loginId", login.getId()));
		return super.<T>findLinkByCriteria(criteria, null);
	}

	public <T extends ExternalResourceLink> T findInATMLinkById(final CommonLogin login, Class<T> clazz, Long id)
			throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria = criteria.add(Expression.eq("id", id));
		criteria = criteria.add(Expression.eq("showInATM", true));
		criteria = criteria.add(Expression.eq("loginId", login.getId()));
		return super.<T>findLinkByCriteria(criteria, null);
	}

	public <T extends ExternalResourceLink> T findLinkByExternalId(
			Class<T> clazz,
			String externalId,
			CommonLogin login
	) throws BusinessException
	{
		return super.findLinkByExternalId(clazz, externalId, login, null);
	}

	public <T extends ExternalResourceLink> List<T> getLinks(final CommonLogin login, final Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		return super.getLinks(login, clazz, null);
	}

	public <T extends ExternalResourceLink> List<T> getLinks(Long loginId, final Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		return super.getLinks(loginId, clazz, null);
	}

	public <T extends ExternalResourceLink> List<T> getInSystemLinks(final CommonLogin login, final Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		return super.getInSystemLinks(login, clazz, null);
	}

	public <T extends ExternalResourceLink> List<T> getInMobileLinks(final CommonLogin login, final Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		return super.getInMobileLinks(login, clazz, null);
	}

	public <T extends ExternalResourceLink> List<T> getInSocialLinks(final CommonLogin login, final Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		return super.getInSocialLinks(login, clazz, null);
	}

	public <T extends ExternalResourceLink> List<T> getInATMLinks(final CommonLogin login, final Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		return super.getInATMLinks(login, clazz, null);
	}

	public void removeLink(ExternalResourceLink link) throws BusinessException
	{
		super.removeLink(link, null);
	}

	public void updateLink(ExternalResourceLink link) throws BusinessException
	{
		super.updateLink(link, null);
	}

	public void addStoredResource(AbstractStoredResource storedResource) throws BusinessException
	{
		super.addStoredResource(storedResource, null);
	}

	public void updateStoredResource(AbstractStoredResource storedResource) throws BusinessException
	{
		super.updateStoredResource(storedResource, null);
	}


	public void addOrUpdateStoredResource(AbstractStoredResource storedResource) throws BusinessException
	{
		super.addOrUpdateStoredResource(storedResource, null);	
	}

	public void removeStoredResource(AbstractStoredResource storedResource) throws BusinessException
	{
		super.removeStoredResource(storedResource, null);	
	}

	public void addOrUpdateLink(ExternalResourceLink link) throws BusinessException
	{
		super.addOrUpdateLink(link, null);
	}

	/**
	 * ”далить все внешние ресурсы по логину
	 * @param login - логин, дл€ которого удал€ютс€ все внешние ресурсы
	 * @throws BusinessException
	 */
	public void removeAll(CommonLogin login) throws BusinessException, BusinessLogicException
	{
		super.removeAll(login, null);
	}

	public void removeAll(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException
	{
		super.removeAll(login, instanceName);
	}

	public <T extends ExternalResourceLink> T findLinkByNumber(CommonLogin login, ResourceType type, String number) throws BusinessException
	{
		return super.<T>findLinkByNumber(login, type, number, false, false, false, false, null);
	}

	public <T extends ExternalResourceLink> T findLinkByNumber(Long loginId, ResourceType type, String number) throws BusinessException
	{
		return super.<T>findLinkByNumber(loginId, type, number, false, false, false, false, null);
	}

	public <T extends ExternalResourceLink> T findVisibleLinkByNumber(CommonLogin login, ResourceType type, String number) throws BusinessException
	{
		ExternalResourceLink link;
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();

		if (applicationConfig.getApplicationInfo().isMobileApi())
			link = findInMobileLinkByNumber(login, type, number);
        else if (applicationConfig.getApplicationInfo().isSocialApi())
            link = findInSocialLinkByNumber(login, type, number);
		else if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
			//дл€ приложени€ сотрудника, мы не должны скрывать карты и счета, невидимые в системе.
			link = findLinkByNumber(login, type, number);
		else if(applicationConfig.getApplicationInfo().isATM())
			link = findInATMLinkByNumber(login, type, number);
		else
			link = findInSystemLinkByNumber(login, type, number);
		return (T) link;
	}

	public <T extends ExternalResourceLink> T findInSystemLinkByNumber(CommonLogin login, ResourceType type, String number) throws BusinessException
	{
		return super.<T>findLinkByNumber(login, type, number, true, false, false, false, null);
	}

	public <T extends ExternalResourceLink> T findInMobileLinkByNumber(CommonLogin login, ResourceType type, String number) throws BusinessException
	{
		return super.<T>findLinkByNumber(login, type, number, false, true, false, false, null);
	}

	public <T extends ExternalResourceLink> T findInSocialLinkByNumber(CommonLogin login, ResourceType type, String number) throws BusinessException
	{
		return super.<T>findLinkByNumber(login, type, number, false, false, false, true, null);
	}

	public <T extends ExternalResourceLink> T findInATMLinkByNumber(CommonLogin login, ResourceType type, String number) throws BusinessException
	{
		return super.<T>findLinkByNumber(login, type, number, false, false, true, false, null);
	}

	public LoyaltyProgramLink getLoyaltyProgramLink(CommonLogin login) throws BusinessException
	{
		return super.getLoyaltyProgramLink(login, null);
	}

	/**
	 * @param login - логин клиента
	 * @param businessProcess - бизнес-процесс, в рамках которого оформлена страховка(NPF или Insurance) 
	 * @return список линков страховых продуктов
	 * @throws BusinessException
	 */
	public List<InsuranceLink> getInsuranceLinks(final CommonLogin login, BusinessProcess businessProcess)  throws BusinessException
	{
		return super.getInsuranceLinks(login, businessProcess,null);
	}

	/**
	 * Ќайти банкролл-продукт по его —ћ—-алиасу
	 * @param productClass - класс продукта
	 * @param login - логин клиента-держател€ продукта
	 * @param smsAlias - —ћ—-алиас продукта (never empty)
	 * @return линк-на-банкролл-продукт или null, если не найден
	 */
	public <T extends BankrollProductLink> T findProductBySmsAlias(Class<T> productClass, Login login, String smsAlias) throws BusinessException
	{
		if (StringHelper.isEmpty(smsAlias))
			throw new IllegalArgumentException("Ќе задан —ћ—-алиас");

		DetachedCriteria criteria = DetachedCriteria.forClass(productClass);
		criteria.add(Expression.eq("loginId", login.getId()));
		criteria.add(Expression.or(
				Expression.eq("ermbSmsAlias", smsAlias),
				Expression.eq("autoSmsAlias", smsAlias)));
		return simpleService.<T>findSingle(criteria, null);
	}

	/**
	 * ќбновить у вклада признак показа сообщени€ о закрытии
	 * @param accountNumber - номер вклада
	 * @throws BusinessException
	 */
	public void setAccountLinksFalseClosedState(final String accountNumber) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.resources.external.AccountLink.setFalseClosedState");
			executorQuery.setParameter("accountNumber", accountNumber).executeUpdate();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ќбновить у карты признак показа сообщени€ о закрытии
	 * @param cardNumber - номер карты
	 * @throws BusinessException
	 */
	public void setCardLinksFalseClosedState(final String cardNumber) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.resources.external.CardLink.setFalseClosedState");
			executorQuery.setParameter("cardNumber", cardNumber).executeUpdate();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ќбновить у кредита признак показа сообщени€ о закрытии
	 * @param loanNumber - номер кредитного счета
	 * @throws BusinessException
	 */
	public void setLoanLinksFalseClosedState(final String loanNumber) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.resources.external.LoanLink.setFalseClosedState");
			executorQuery.setParameter("loanNumber", loanNumber).executeUpdate();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

}
