package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import com.rssl.phizic.business.ext.sbrf.mobilebank.PaymentTemplateUpdate;
import com.rssl.phizic.business.ext.sbrf.mobilebank.SmsCommand;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 17.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */

/**
 * Операция подтверждения SMS-шаблона
 */
@Deprecated
//todo CHG059738 удалить
public class ConfirmSmsTemplateOperation extends ConfirmableOperationBase
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private PaymentTemplateUpdate update;

	private CardLink cardlink;

	private List<SmsCommand> newSmsCommands;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация операции для сохранённого апдейта SMS-шаблонов
	 * @param id - ID апдейта (см. PaymentTemplateUpdate)
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
			throw new NullPointerException("Argument 'id' cannot be null");

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Login login = personData.getPerson().getLogin();

		update = mobileBankService.getUpdate(login, id);
		if (update == null)
			throw new BusinessException("Не найден апдейт шаблонов SMS-платежей " +
					"(PaymentTemplateUpdate) ID = " + id);

		cardlink = MobileBankUtils.selectCardLinkByCardNumber(personData.getCards(), update.getCardNumber(), true);
		newSmsCommands = mobileBankService.getUpdateSmsCommands(update);

		setStrategyType();
	}

	public PaymentTemplateUpdate getUpdate()
	{
		return update;
	}

	public CardLink getCardLink() throws BusinessException
	{
		return cardlink;
	}

	/**
	 * @return список новых шаблонов, образованных в процессе добавления
	 * @throws BusinessException
	 */
	public List<SmsCommand> getNewSmsCommands() throws BusinessException
	{
		return Collections.unmodifiableList(newSmsCommands);
	}

	public ConfirmableObject getConfirmableObject()
	{
		return update;
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		// nothing to save
	}
}
