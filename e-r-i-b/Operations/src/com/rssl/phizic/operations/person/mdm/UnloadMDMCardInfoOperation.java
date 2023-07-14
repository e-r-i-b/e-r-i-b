package com.rssl.phizic.operations.person.mdm;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mdm.MDMCardInfo;
import com.rssl.phizic.business.mdm.MDMClientInfo;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;

import java.util.Iterator;

/**
 * Выгрузка паспортных данных клиента
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class UnloadMDMCardInfoOperation extends UnloadMDMInfoOperation<MDMCardInfo>
{
	@Override
	protected Iterator<MDMCardInfo> getMdmClientInfo(String[] ids) throws BusinessException
	{
		return service.getMDMCardInfo(ids);
	}

	protected void createRecordLine(StringBuilder builder, MDMClientInfo info)
	{
		MDMCardInfo cardInfo = (MDMCardInfo)info;
		addCell(builder, cardInfo.getCsaProfileId());
		addCell(builder, cardInfo.getSurname());
		addCell(builder, cardInfo.getFirstName());
		addCell(builder, cardInfo.getPatrName());
		addCell(builder, PassportTypeWrapper.getPassportType(ClientDocumentType.valueOf(cardInfo.getDocumentKind().name())));
		addCell(builder, cardInfo.getSeries());
		addCell(builder, cardInfo.getNo());
		addCell(builder, cardInfo.getIssuedby());
		addCell(builder, DateHelper.formatCalendar(cardInfo.getIssueday()));
		addCell(builder, cardInfo.getActual() ? "1" : "0");
		addCell(builder, cardInfo.getSubdivision());
		addCell(builder, DateHelper.formatCalendar(cardInfo.getEndDay()));
		addLastCell(builder, cardInfo.getEntryDay());
	}
}
