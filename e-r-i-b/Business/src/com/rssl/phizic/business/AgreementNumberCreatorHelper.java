package com.rssl.phizic.business;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.persons.AgrementNumberCreator;
import com.rssl.phizic.business.persons.PersonCreateConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author: Pakhomova
 * @created: 20.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class AgreementNumberCreatorHelper
{
	private static final AgreementNumberCreatorHelper instance = new AgreementNumberCreatorHelper();

	private AgreementNumberCreatorHelper() {}

	public static String getNextAgreementNumber(Department department) throws BusinessException
	{
		return instance.generateNextAgreementNumber(department);
	}

	private String generateNextAgreementNumber(Department department) throws BusinessException
	{
		try
		{
			PersonCreateConfig flowConfig = ConfigFactory.getConfig(PersonCreateConfig.class);
			String className = flowConfig.getAgreementCreator();
			if (className == null)
				throw new BusinessException("Ќе установлен класс, дл€ создани€ номера договора");

			AgrementNumberCreator creator = (AgrementNumberCreator) ClassHelper.loadClass(className).newInstance();
			creator.init(department);
			return creator.getNextAgreementNumber();

		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}
}
