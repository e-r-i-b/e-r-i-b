package com.rssl.phizic.operations.ermb.person;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.ProfileNotFoundException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbClientPhone;
import com.rssl.phizic.business.ermb.ErmbClientPhoneService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.person.ermb.ErmbConfirmPhoneHolderHelper;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.PhoneEncodeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: Moshenko
 * Date: 18.06.2013
 * Time: 15:02:08
 * Управление телефонами ЕРМБ
 */
public class ErmbControlPhoneOpertion extends OperationBase
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final ErmbClientPhoneService phoneService = new ErmbClientPhoneService();

	private static final ErmbConfirmPhoneHolderHelper phoneHolderHelper = new ErmbConfirmPhoneHolderHelper();

	/**
	 * Проверка номера на доступность
	 * @param number Номер добавляемого телефона
	 * @param phonesToDelete номера удаляемых телефонов
	 * @return  free                - доступен,
	 *          id ермб профиля     - занят в текущем блоке,
	 *          used-in-other-block - занят в другом блоке,
	 *          phone-to-restore:   - был отмечен на удаление, надо восстановить
	 *          used-in-mbk         - занят в мбк
	 */
	public String isPhoneNumberAvailable(String number, String[] phonesToDelete)
	{
		try
		{
			ErmbClientPhone phoneByNumber = phoneService.findPhoneByNumber(number);
			if (phoneByNumber != null)
			{
				if (phonesToDelete!=null)
				{
					Map<String, String> phoneNumbersToDelete = PhoneEncodeUtils.decodePhoneNumbersForRestoreRemoved(new HashSet<String>(Arrays.asList(phonesToDelete)), phoneByNumber.getProfile().getPhoneNumbers(), true);
					for (String numberToDelete : phoneNumbersToDelete.keySet())
					{
						if (number.equals(phoneNumbersToDelete.get(numberToDelete)))
						{
							return "phone-to-restore:"+numberToDelete;
						}
					}
				}
				return phoneByNumber.getProfile().getId().toString();
			}

			if (usedInMbk(number))
			{
				return "used-in-mbk";
			}

			Document document = CSABackRequestHelper.sendGetUserInfoByPhoneRq(number, false);
			if (document != null)
			{
				return "used-in-other-block";
			}

			return "free";
		}
		catch (ProfileNotFoundException ignored)
		{
			return "free";
		}
		catch (Exception e)
		{
			log.error(e);
			return "error";
		}
	}

	//проверка наличия связок в МБК на этот номер телефона
	private boolean usedInMbk(String number) throws BusinessException, BusinessLogicException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			Set<String> mbkCards = mobileBankService.getCardsByPhone(number);
			return CollectionUtils.isNotEmpty(mbkCards);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * Отправить код подтверждения на номе
	 * @param phoneNumber Номер телефона на который высылаем
	 * @return сообщение отправлено
	 */
	public boolean sendConfirmCode(String phoneNumber) throws BusinessException
	{
		try
		{      //
			phoneHolderHelper.sendConfirmCode(phoneNumber, TranslitMode.DEFAULT);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}

		return true;
	}

	/**
	 * Проверяем код подтверждения для смены держателя
	 * @param swapPhoneNumber номер телефона которому будем менять владельца
	 * @param confirmCode код для смены держателя
	 * @return true если код верен
	 */
	public boolean testSwapPhoneNumberCode(String confirmCode,String swapPhoneNumber) throws BusinessException, SecurityDbException
	{
		return phoneHolderHelper.testSwapPhoneNumberCode(confirmCode,swapPhoneNumber);
	}
}
