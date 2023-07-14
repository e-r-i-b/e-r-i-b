package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.ermb.card.PrimaryCardService;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.images.AvatarJurnalService;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * Поиск клиента в ЕРМБ/МБК по номеру телефона
 * @author gladishev
 * @ created 10.10.2014
 * @ $Author$
 * @ $Revision$
 */

public class FindMBClientOperation extends OperationBase implements ViewEntityOperation<Pair<Client, Card>>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final AvatarJurnalService AVATAR_JURNAL_SERVICE = new AvatarJurnalService();

	private String phone;
	private Pair<Client, Card> result;

	/**
	 * Инициализация операции
	 * @param phone - номер телефона
	 * @throws BusinessException
	 */
	public void initialize(String phone) throws BusinessException
	{
		this.phone = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phone.trim()));
		try
		{
			initializeResult(this.phone);
		}
		catch (NumberFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	public Pair<Client, Card> getEntity() throws BusinessException, BusinessLogicException
	{
		return result;
	}

	protected void initializeResult(String phone) throws BusinessException
	{
		Card card = MobileBankManager.getCardByPhone(phone, PersonContext.getPersonDataProvider().getPersonData().getPerson());
		if (card == null)
			return;

		result = new Pair<Client, Card>(card.getCardClient(), card);
	}

	/**
	 * Поиск аватара получателя платежа
	 * @return путь до аватара
	 */
	public String searchAvatar()
	{
		try
		{
			return AVATAR_JURNAL_SERVICE.getAvatar(this.phone);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
