package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.BeanConfigBase;

/**
 * @author Erkin
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реестр банкролл-конфигов
 */
public class BankrollRegistry extends BeanConfigBase<BankrollRegistryBean>
{
	/**
	 * Кодификатор реестра в таблице CONFIGS
	 */
	private static final String REGISTRY_CODENAME = "BankrollRegistry";

	private final BankrollConfig accountConfig = new AccountConfig();

	private final BankrollConfig cardConfig = new CardConfig();

	private final BankrollConfig loanConfig = new LoanConfig();

	private BankrollRegistryBean registryBean;

	///////////////////////////////////////////////////////////////////////////

	public BankrollRegistry(PropertyReader reader)
	{
		super(reader);
	}

	protected String getCodename()
	{
		return REGISTRY_CODENAME;
	}

	protected Class<BankrollRegistryBean> getConfigDataClass()
	{
		return BankrollRegistryBean.class;
	}

	protected void doRefresh() throws ConfigurationException
	{
		registryBean = getConfigData();
	}

	/**
	 * @return конфиг по счетам
	 */
	public BankrollConfig getAccountConfig()
	{
		return accountConfig;
	}

	/**
	 * @return конфиг по картам
	 */
	public BankrollConfig getCardConfig()
	{
		return cardConfig;
	}

	/**
	 * @return конфиг по кредитам
	 */
	public BankrollConfig getLoanConfig()
	{
		return loanConfig;
	}

	///////////////////////////////////////////////////////////////////////////

	private abstract class BankrollConfigBase implements BankrollConfig
	{
		public boolean isProductUsed()
		{
			//noinspection InnerClassTooDeeplyNested
			return getConfigBean().isUsed();
		}

		public long getProductListLifetime()
		{
			//noinspection InnerClassTooDeeplyNested
			return getConfigBean().getListLifetime();
		}

		protected abstract BankrollConfigBean getConfigBean();
	}

	private class AccountConfig extends BankrollConfigBase
	{
		protected BankrollConfigBean getConfigBean()
		{
			return registryBean.getAccountConfigBean();
		}
	}

	private class CardConfig extends BankrollConfigBase
	{
		protected BankrollConfigBean getConfigBean()
		{
			return registryBean.getCardConfigBean();
		}
	}

	private class LoanConfig extends BankrollConfigBase
	{
		protected BankrollConfigBean getConfigBean()
		{
			return registryBean.getLoanConfigBean();
		}
	}
}
