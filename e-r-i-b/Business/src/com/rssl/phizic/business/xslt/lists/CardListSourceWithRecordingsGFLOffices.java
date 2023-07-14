package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecordingService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.MaskUtil;

import java.util.Map;

/**
 * Список карт с добавлением информации о подразделении, полученной из GFL. Ищем подразделения в нашем справочнике, если не находим смотртим в DEPARTMENTS_RECORDINGS
 * @author basharin
 * @ created 14.01.14
 * @ $Author$
 * @ $Revision$
 */

public class CardListSourceWithRecordingsGFLOffices extends CardListSourceWithGFLOffices
{
	protected static final DepartmentsRecordingService departmentsRecordingService = new DepartmentsRecordingService();

	public CardListSourceWithRecordingsGFLOffices(EntityListDefinition definition)
	{
		super(definition);
	}

	public CardListSourceWithRecordingsGFLOffices(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public CardListSourceWithRecordingsGFLOffices(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	@Override protected void appentOfficeInfo(EntityListBuilder builder, CardLink cardLink, ExtendedCodeImpl officeCode) throws BusinessException
	{
		Department department = departmentService.findByCode(officeCode);
		if (department != null)
		{
			builder.appentField("officeName", department.getName());
			builder.appentField("officeAddress", department.getAddress());
			return;
		}

		Code code = departmentsRecordingService.getRecordingOfficeCode(officeCode);
		if (code != null)
		{
			department = departmentService.findByCode(code);
			if (department != null)
			{
				builder.appentField("officeName", department.getName());
				builder.appentField("officeAddress", department.getAddress());
				return;
			}
		}

		log.info("Ошибка при получении информации о подразделении, к которому привязана карта № " + MaskUtil.getCutCardNumberForLog(cardLink.getNumber()));
	}
}
