package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.MaskUtil;

import java.util.Map;

/**
 * Список карт с добавлением информации о подразделении, полученной из GFL
 * @author Pankin
 * @ created 18.12.13
 * @ $Author$
 * @ $Revision$
 */
public class CardListSourceWithGFLOffices extends CardListSourceWithOwnerInfo
{
	protected static final DepartmentService departmentService = new DepartmentService();

	public CardListSourceWithGFLOffices(EntityListDefinition definition)
	{
		super(definition);
	}

	public CardListSourceWithGFLOffices(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public CardListSourceWithGFLOffices(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected boolean skipStoredResource(Card card)
	{
		return true;
	}

	/**
	 * Получение информации о подразделении
	 * @param builder
	 * @param cardLink
	 */
	protected void getOfficeInfo(EntityListBuilder builder, CardLink cardLink)
	{
		ExtendedCodeImpl officeCode = new ExtendedCodeImpl(cardLink.getGflTB(), cardLink.getGflOSB(), cardLink.getGflVSP());

		builder.appentField("officeRegionCode", officeCode.getRegion());
		builder.appentField("officeBranchCode", officeCode.getBranch());
		builder.appentField("officeOfficeCode", officeCode.getOffice());

		try
		{
			appentOfficeInfo(builder, cardLink, officeCode);
		}
		catch (BusinessException e)
		{
			log.info("Ошибка при получении информации о подразделении, к которому привязана карта № " + MaskUtil.getCutCardNumberForLog(cardLink.getNumber()), e);
		}
	}

	protected void appentOfficeInfo(EntityListBuilder builder, CardLink cardLink, ExtendedCodeImpl officeCode) throws BusinessException
	{
		Department department = departmentService.findByCode(officeCode);
		if (department == null)
		{
			log.info("Ошибка при получении информации о подразделении, к которому привязана карта № " + MaskUtil.getCutCardNumberForLog(cardLink.getNumber()));
			return;
		}
		builder.appentField("officeName", department.getName());
		builder.appentField("officeAddress", department.getAddress());
	}
}
