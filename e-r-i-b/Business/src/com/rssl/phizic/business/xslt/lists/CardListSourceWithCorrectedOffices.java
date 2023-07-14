package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 * @author Ismagilova
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * В дополнение к CardListSource добавлена информация о подразделении, изменяются значения ОСБ (на 9038)
 * для ТБ=38, ОСБ= 1569,5278,5281,6901,7811,7813,7954,7970,7977,7978,7981,7982,8641
 */

public class CardListSourceWithCorrectedOffices extends CardListSourceWithOwnerInfo
{
	private static final DepartmentService departmentService = new DepartmentService();

	public CardListSourceWithCorrectedOffices(EntityListDefinition definition)
	{
		super(definition);
	}

    public CardListSourceWithCorrectedOffices(EntityListDefinition definition, CardFilter cardFilter)
    {
        super(definition, cardFilter);
    }

    public CardListSourceWithCorrectedOffices(EntityListDefinition definition, Map parameters) throws BusinessException
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
		// Уточняем ОСБ
		OfficeCodeReplacer officeCodeReplacer = ConfigFactory.getConfig(OfficeCodeReplacer.class);
		officeCode.setBranch(officeCodeReplacer.replaceCode(officeCode.getRegion(), officeCode.getBranch()));

		builder.appentField("officeRegionCode", officeCode.getRegion());
		builder.appentField("officeBranchCode", officeCode.getBranch());
		builder.appentField("officeOfficeCode", officeCode.getOffice());

		try
		{
			Department department = departmentService.findByCode(officeCode);
			if (department == null)
			{
				log.info("Ошибка при получении информации о подразделении, к которому привязана карта № " + MaskUtil.getCutCardNumberForLog(cardLink.getNumber()));
				builder.appentField("isImaOpening", "0");
				officeCode.setOffice(null);
				Department osb = departmentService.findByCode(officeCode);
				if (osb != null && osb.getSynchKey() != null)
					builder.appentField("officeSynchKey", osb.getSynchKey().toString());
				return;
			}
			builder.appentField("officeName", department.getName());
			builder.appentField("officeAddress", department.getAddress());
			builder.appentField("isImaOpening", department.isOpenIMAOffice() ? "1" : "0");
			builder.appentField("officeSynchKey", department.getSynchKey().toString());
		}
		catch (BusinessException e)
		{
			log.info("Ошибка при получении информации о подразделении, к которому привязана карта № " + MaskUtil.getCutCardNumberForLog(cardLink.getNumber()), e);
			return;
		}
	}
}
