package com.rssl.phizic.business.ermb.mobileOperator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Puzikov
 * @ created 11.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbMobileOperatorService
{
	private static final PhoneNumberFormat DEF_CODE_DICTIONARY_PHONE_FORMAT = PhoneNumberFormat.SIMPLE_NUMBER;

	/**
	 * @param phoneNumber номер телефона
	 * @return мобильный оператор
	 * @throws BusinessException
	 */
	public ErmbMobileOperator getByPhone(final PhoneNumber phoneNumber) throws BusinessException
	{
		ErmbConfig ermbConfig = ConfigFactory.getConfig(ErmbConfig.class);

		if (ermbConfig.isUseMNPPhones())
		{
			ErmbMobileOperator mobileOperator = getByPhoneByMNPPhones(phoneNumber);
			if (mobileOperator != null)
				return mobileOperator;
		}

		return getByPhoneByDefCodes(phoneNumber);
	}


	private ErmbMobileOperator getByPhoneByMNPPhones(final PhoneNumber phoneNumber) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ErmbMobileOperator>()
			{
				public ErmbMobileOperator run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.mobileOperator.ErmbMobileOperator.findByMNPPhones");
					String phone = PhoneNumberFormat.SIMPLE_NUMBER.format(phoneNumber);
					query.setParameter("phone", phone);
					return (ErmbMobileOperator) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private ErmbMobileOperator getByPhoneByDefCodes(final PhoneNumber phoneNumber) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ErmbMobileOperator>()
			{
				public ErmbMobileOperator run(Session session) throws Exception
				{
					return (ErmbMobileOperator) session.getNamedQuery("com.rssl.phizic.business.ermb.mobileOperator.ErmbMobileOperator.findByDefCodes")
							.setParameter("phone", DEF_CODE_DICTIONARY_PHONE_FORMAT.format(phoneNumber))
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
