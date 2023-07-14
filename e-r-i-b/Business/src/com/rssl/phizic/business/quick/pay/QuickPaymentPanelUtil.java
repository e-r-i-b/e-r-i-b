package com.rssl.phizic.business.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.*;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;

/** 
 * @author komarov
 * @ created 13.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class QuickPaymentPanelUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final QuickPaymentPanelService panelService = new QuickPaymentPanelService();

	/**
	 * Получает блоки панели быстрой оплаты.
	 * @return блоки
	 */
	public static List<PanelBlock> getQuickPaymentPanel()
	{
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		if (personDataProvider==null || personDataProvider.getPersonData() == null)
			return Collections.emptyList();
		try
		{
			Department tb = getCurrentTerbankFromContext();
			return panelService.findByTRB(tb.getRegion());
		}
		catch(BusinessException e)
		{
			log.error("Ошибка поиска панели быстрой оплаты", e);
			return Collections.emptyList();
		}
	}

	/**
	 * @return текущий тербанк. Тербанк берется из контекста пользователя
	 */
	private static Department getCurrentTerbankFromContext()
	{
		try
		{
			if (!PersonContext.isAvailable())
				return null;
			// получаем текущего пользователя и определяем департамент, к которому он привязан
			return  PersonContext.getPersonDataProvider().getPersonData().getTb();
		}
		catch (Exception ex)
		{
			log.error("Ошибка получения тербанка клиента. ", ex);
			return null;
		}
	}


	public static String getHiddenPhoneNumber()
	{
		return PhoneNumberUtil.getSimplePhoneNumber(getPhoneNumber());
	}

	public static String getPhoneNumber()
	{
		try
		{
			PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
			if (dataProvider != null)
			{
				PersonData data = dataProvider.getPersonData();
				if (data != null)
				{
					String phone = MobileBankManager.getMainPhoneForCurrentUser();
					if(StringHelper.isEmpty(phone))
						return StringUtils.EMPTY;

					return PhoneNumberFormat.SIMPLE_NUMBER.format(PhoneNumber.fromString(phone));
				}
			}
			return StringUtils.EMPTY;
		}
		catch (Exception ignore)
		{
			return StringUtils.EMPTY;
		}
	}

	public static String getCutPhoneNumberForQPP()
	{
		try
		{
			PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
			if (dataProvider != null)
			{
				PersonData data = dataProvider.getPersonData();
				if (data != null)
				{
					return PhoneNumberUtil.getCutPhoneNumber(MobileBankManager.getMainPhoneForCurrentUser());
				}
			}
			return "+7 (XXX) " + StringUtils.repeat(MaskHelper.getActualMaskSymbol(), 3) + "XXXX";
		}
		catch (Exception ignore)
		{
			return "";
		}
	}
}
