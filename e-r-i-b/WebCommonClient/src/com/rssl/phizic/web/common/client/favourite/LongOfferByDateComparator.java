package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.web.common.client.ext.sbrf.LongOfferUtils;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: vagin
 * Date: 13.07.2011
 * Time: 11:32:33
 * ���������� ��� LongOffer �� ���� ����������
 * ������������� getValue() ���������, �.�. ������ ����������
 */
public class LongOfferByDateComparator implements Comparator<ExternalResourceLinkBase>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String errorKeyMessage = "������ ��������� LongOffer �� ���� ����������";

	/**
	 * ���������� ���� �������� ��� �����.
	 *
	 * @param link ����.
	 * @return ���� ��������.
	 * @throws BusinessException
	 */
	private Calendar getDateForLink(ExternalResourceLink link) throws BusinessException
	{
		if (link instanceof LongOfferLink)
		{
			return ((LongOfferLink) link).getValue().getStartDate();
		}
		else if (link instanceof AutoPaymentLink)
		{
			return ((AutoPaymentLink) link).getValue().getDateAccepted();
		}
		else if (link instanceof AutoSubscriptionLink)
		{
			return ((AutoSubscriptionLink) link).getValue().getStartDate();
		}
		return null;
	}

	public int compare(ExternalResourceLinkBase o1, ExternalResourceLinkBase o2)
	{
		try
		{
			boolean active1 = LongOfferUtils.isActiveAutoPayment(o1);
			boolean active2 = LongOfferUtils.isActiveAutoPayment(o2);

			//�������� ������ ������.
			if (active1 != active2)
				return (active2 ? 1 : 0) - (active1 ? 1 : 0);
			//�������� ������ ��� ���������� �� ������� �������
			Calendar date1 = getDateForLink(o1);

			//�������� ������ ��� ���������� �� ������� �������
			Calendar date2 = getDateForLink(o2);

			return DateHelper.nullSafeCompare(date1,date2);
		}
		catch(BusinessException e)
		{
			log.error(errorKeyMessage, e);
			return 0;
		}
	}
}
