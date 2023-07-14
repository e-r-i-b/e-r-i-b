package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.ServiceImpl;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

/**
 * @author Mescheryakova
 * @ created 08.12.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class BillingServiceProviderBase  extends ServiceProviderBase
{

	private String  codeService;
	private boolean ground;
	private AccountType accountType;
	private Billing billing;
	private PaymentService paymentService;
	private String  attrDelimiter;
	private String  attrValuesDelimiter;
	private String nameService;
	private String codeRecipientSBOL;
	private String alias;
	private String legalName;
	private String nameOnBill;
	private boolean federal;


	/**
	 * Код услуги,
	 * которая предоставляется данным поставщиком услуг
	 * @return - код
	 */
	public String getCodeService()
	{
		return codeService;
	}

	public Service getService()
	{
		return new ServiceImpl(getCodeService(), getNameService());
	}	

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	/**
	 * Признак необходимости формировать назначение платежа
	 * @return - true (формировать)
	 */
	public boolean isGround()
	{
		return ground;
	}

	public void setGround(boolean ground)
	{
		this.ground = ground;
	}

	/**
	 * Тип счета при оплате
	 * @return - Тип счета при оплате
	 */
	public AccountType getAccountType()
	{
		return accountType;
	}

	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	/**
	 * Предоставляемая услуга
	 * @return - услуга
	 */
	public PaymentService getPaymentService()
	{
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService)
	{
		this.paymentService = paymentService;
	}	

	/**
	 * Биллинговая система
	 * @return - биллинговая система
	 */
	public Billing getBilling()
	{
		return billing;
	}

	public void setBilling(Billing billing)
	{
		this.billing = billing;
	}

	/**
	 * Разделитель дополнительных атрибутов поставщика услуг
	 * (реквизитов платежа)
	 * @return - разделитель
	 */
	public String getAttrDelimiter()
	{
		return attrDelimiter;
	}

	public void setAttrDelimiter(String attrDelimiter)
	{
		this.attrDelimiter = attrDelimiter;
	}

	/**
	 * Разделитель значений дополнительных атрибутов поставщика услуг
	 * (реквизитов платежа)
	 * @return - разделитель
	 */
	public String getAttrValuesDelimiter()
	{
		return attrValuesDelimiter;
	}

	public void setAttrValuesDelimiter(String attrValuesDelimiter)
	{
		this.attrValuesDelimiter = attrValuesDelimiter;
	}


	/**
	 * @return название услуги из Биллинговой системы
	 */
	public String getNameService()
	{
		return nameService;
	}

	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}

	/**
	 * Код для группировки данных при отображении в СБОЛе.
	 * Разные записи справочника с одним значением CodeRecipientSBOL будут в СБОЛе отображаться как один поставщик
	 * @return код для группировки данных при отображении в СБОЛ
	 */
	public String getCodeRecipientSBOL()
	{
		return codeRecipientSBOL;
	}

	public void setCodeRecipientSBOL(String codeRecipientSBOL)
	{
		this.codeRecipientSBOL = codeRecipientSBOL;
	}

	/**
	 * Псевдоним(ы) поставщика, если несколько - указываются через запятую.
	 * Используются в СБОЛ только для поиска поставщика услуг по наименованию (по подстроке, введенной пользователем)
	 * Все записи с одним и тем же CodeRecipientSBOL должны иметь одно и то же значение атрибута Alias
	 * @return псевдоним(ы) поставщика
	 */
	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	/**
	 * @return юридическое наименование поставщика услуг
	 * (используется в СБОЛ для поиска поставщика, фактически, дополнение к Alias)
	 */
	public String getLegalName()
	{
		return legalName;
	}

	public void setLegalName(String legalName)
	{
		this.legalName = legalName;
	}

	/**
	 * @return наименование поставщика, выводимое в чеке
	 */
	public String getNameOnBill()
	{
		return nameOnBill;
	}

	public void setNameOnBill(String nameOnBill)
	{
		this.nameOnBill = nameOnBill;
	}


	/**
	 * Признак федерального поставщика услуг.
	 * Если указано «0» или тэг отсутствует, то поставщик не федеральный.
	 * Если указано «1» - федеральный.
	 * Все записи с одним и тем же CodeRecipientSBOL должны иметь одно и то же значение атрибута IsFederal
	 * @return 0/1
	 */
	public boolean isFederal()
	{
		return federal;
	}

	public void setFederal(boolean federal)
	{
		this.federal = federal;
	}
}
