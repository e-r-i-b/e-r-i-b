package com.rssl.phizic.business.departments;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 05.12.14
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentsUtils
{
	private static DepartmentService departmentService = new DepartmentService();

	/**
	 * Получить номер основного ТБ из CB_CODE с учетом синонимов.
	 * Формат поля @CbCode такой:
	 * Код тербанка – 2 символа
	 * Код регионального банка – 2 символа
	 * Код ОСБ – 4 символа
	 * Все коды указываются слитно без пробелов, при необходимости с лидирующими нулями
	 * @param cbCode значие кода
	 * @return номер основого TB с лидирующими нулями.
	 */
	public static String getTBByCbCode(String cbCode)
	{
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("Значение кода не может быть null.");
		}

		return getTbBySynonymAndIdentical(getCutTBByCBCode(cbCode));
	}

	/**
	 * получить номер ТБ из CBCode
	 * @param cbCode CBCode
	 * @return номер ТБ
	 */
	public static String getCutTBByCBCode(String cbCode)
	{
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("Значение кода не может быть null.");
		}

		return cbCode.substring(0, 2);
	}

	/**
	 * Получить ТБ по иденфтификатору подразделения
	 * @param departmentId идентификатор подразделения
	 * @return тб с учетом синонима
	 * @throws BusinessException
	 */
	public static String getTbByDepartmentId(Long departmentId) throws BusinessException
	{
		if (departmentId == null)
		{
			throw new IllegalArgumentException("Идентификатор подразделения не может быть null.");
		}

		List<Code> codes = departmentService.getCodesByDepartmentIds(Collections.singletonList(departmentId));

		if (CollectionUtils.isEmpty(codes))
		{
			throw new BusinessException("Не найдено подразделение пользователя по id " + departmentId);
		}

		String tb = ((ExtendedCodeImpl) codes.get(0)).getRegion();
		return getTbBySynonymAndIdentical(tb);
	}

	/**
	 * @param tb тербанк
	 * @return номер основного ТБ
	 */
	public static String getTbBySynonymAndIdentical(String tb)
	{
		if (StringHelper.isEmpty(tb))
		{
			throw new IllegalArgumentException("Номер тербанка не может быть null.");
		}

		String tbBySynonym = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonymAndIdentical(tb);
		return StringHelper.addLeadingZeros(tbBySynonym, 2);
	}
}
