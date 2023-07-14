package com.rssl.phizic.operations.person.mdm;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mdm.MDMClientInfo;
import com.rssl.phizic.business.mdm.MDMPersonInfo;
import com.rssl.phizic.utils.DateHelper;

import java.util.Iterator;

/**
 * Выгрузка анкетных данных клиента
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class UnloadMDMPersonInfoOperation extends UnloadMDMInfoOperation
{
	@Override protected Iterator getMdmClientInfo(String[] ids) throws BusinessException
	{
		return service.getMDMPersonInfo(ids);
	}

	@Override
	protected void createRecordLine(StringBuilder builder, MDMClientInfo info)
	{
		MDMPersonInfo cardInfo = (MDMPersonInfo)info;
		addCell(builder, cardInfo.getCsaProfileId());
		addCell(builder, cardInfo.getSex());
		addCell(builder, cardInfo.getSurname());
		addCell(builder, cardInfo.getFirstName());
		addCell(builder, cardInfo.getPatrName());

		addCell(builder, cardInfo.getCitizenship());
		addCell(builder, DateHelper.formatCalendar(cardInfo.getBirthDate()));
		addCell(builder, cardInfo.getBirthPlace());
		addCell(builder, cardInfo.getResident());
		addCell(builder, cardInfo.getTaxPayerid());
		addCell(builder, DateHelper.formatCalendar(cardInfo.getChangeDay()));
		addCell(builder, cardInfo.getLiterate());
		addCell(builder, cardInfo.getActAddress());
		addCell(builder, cardInfo.getEmail());
		addCell(builder, cardInfo.getMobphone());
		addCell(builder, cardInfo.getRiskLevel());
		addCell(builder, cardInfo.getLife_state());
		addCell(builder, cardInfo.getDeath_date());
		addLastCell(builder, cardInfo.getVip_state());
	}
}
