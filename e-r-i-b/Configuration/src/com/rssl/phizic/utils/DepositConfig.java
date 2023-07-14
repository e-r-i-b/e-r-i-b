package com.rssl.phizic.utils;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bogdanov
 * @ created 22.01.14
 * @ $Author$
 * @ $Revision$
 */

public class DepositConfig extends Config
{
	// Ключ для получения списка разрешенных для загрузки депозитных продуктов
	public static final String DEPOSITPRODUCT_KINDS_ALLOWED_UPLOADING = "com.rssl.iccs.depositproduct.kinds.allowed.uploading";
	// Ключ для получения режима загрузки депозитных продуктов
	public static final String DEPOSITPRODUCT_KINDS_UPLOADING_MODE = "com.rssl.iccs.depositproduct.uploading.mode";
	public static final String ACCOUNTS_KINDS_FORBIDDEN_CLOSING = "com.rssl.business.simple.accountsKindsForbiddenClosing";
	public static final String ACCOUNTS_KINDS_FORBIDDEN_CLOSING_DELIMETER = ",";
	public static final String NEW_ALGORITHM_FOR_PROLONGATION_DATE = "com.rssl.iccs.depositproduct.prolongationDate.newAlgorithm";
	public static final String USE_CAS_NSI_DICTIONARIES = "com.rssl.iccs.depositproduct.use.cas.nsi.dictionaries";
	private String uploadMode;
	private String uploadingDepositProductList;
	private String tariff1AgreementCode;
	private String tariff2AgreementCode;
	private String tariff3AgreementCode;
	private List<Long> accountsKindsForbiddenClosing;
	private String type4Kind61;
	private String code4Kind61;
	private boolean newAlgorithmForProlongationDate;
	private boolean useCasNsiDictionaries;

	public DepositConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		uploadMode = getProperty(DEPOSITPRODUCT_KINDS_UPLOADING_MODE);
		uploadingDepositProductList = getProperty(DEPOSITPRODUCT_KINDS_ALLOWED_UPLOADING);
		tariff1AgreementCode = getProperty("com.rssl.iccs.depositproduct.tariff.1.agreement.code");
		tariff2AgreementCode = getProperty("com.rssl.iccs.depositproduct.tariff.2.agreement.code");
		tariff3AgreementCode = getProperty("com.rssl.iccs.depositproduct.tariff.3.agreement.code");

		accountsKindsForbiddenClosing = new ArrayList<Long>();
		String accountsKindsString = getProperty(ACCOUNTS_KINDS_FORBIDDEN_CLOSING);
		if (!StringHelper.isEmpty(accountsKindsString))
		{
			String[] list = accountsKindsString.split(ACCOUNTS_KINDS_FORBIDDEN_CLOSING_DELIMETER);
			for (int i = 0; i < list.length; i++)
			{
				accountsKindsForbiddenClosing.add(Long.valueOf(list[i]));
			}
		}

		type4Kind61 = getProperty("com.rssl.iccs.depositproduct.type4Kind61");
		code4Kind61 = getProperty("com.rssl.iccs.depositproduct.code4Kind61");
		newAlgorithmForProlongationDate = getBoolProperty(NEW_ALGORITHM_FOR_PROLONGATION_DATE);
		useCasNsiDictionaries = getBoolProperty(USE_CAS_NSI_DICTIONARIES);
	}

	/**
	 * @return режим загрузки депозитных продуктов.
	 */
	public String getUploadMode()
	{
		return uploadMode;
	}

	/**
	 * @return список разрешенных для загрузки депозитных продуктов.
	 */
	public String getUploadingDepositProductsList()
	{
		return uploadingDepositProductList;
	}

	public String getTariff1AgreementCode()
	{
		return tariff1AgreementCode;
	}

	public String getTariff2AgreementCode()
	{
		return tariff2AgreementCode;
	}

	public String getTariff3AgreementCode()
	{
		return tariff3AgreementCode;
	}

	/**
	 * @return Виды вкладов, запрещённые к закрытию
	 */
	public List<Long> getAccountsKindsForbiddenClosing()
	{
		return accountsKindsForbiddenClosing;
	}

	/**
	 * @return Тип шаблона Сберегательного счета
	 */
	public String getType4Kind61()
	{
		return type4Kind61;
	}

	/**
	 * @return Код шаблона Сберегательного счёта
	 */
	public String getCode4Kind61()
	{
		return code4Kind61;
	}

	public boolean isNewAlgorithmForProlongationDate()
	{
		return newAlgorithmForProlongationDate;
	}

	/**
	 * Использовать ли для отображения вкладов сущности, дублирующие справочники ЦАС НСИ
	 * @return
	 */
	public boolean isUseCasNsiDictionaries()
	{
		return useCasNsiDictionaries;
	}
}
