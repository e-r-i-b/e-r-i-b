package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.task.TaskNotReadyException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gulov
 * @ created 11.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class LossPassbookTaskImpl extends PaymentTaskBase implements LossPassbookTask
{
	private static final Integer[] ACCOUNT_BLOCK_CODES = new Integer[]{0, 1};

	private String blockingProductAlias;

	private transient BankrollProductLink accountLink;

	/**
	 * Код причины блокировки счета:
	 *  0 – расторгнуть договор;
	 *  1 – выдать дубликат сберкнижки;
	 */
	private Integer blockCode;

	public void setLink(BankrollProductLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public void setBlockCode(Integer blockCode)
	{
		this.blockCode = blockCode;
	}

	@Override
	protected String getFormName()
	{
		return FormConstants.LOSS_PASSBOOK_APPLICATION;
	}

	@Override
	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();

		if (accountLink != null)
			map.put(PaymentFieldKeys.ACCOUNT_SELECT, accountLink.getCode());

		if (!isThroughKSSH()) // прямая интеграция (не через шину)
		{
			map.put(PaymentFieldKeys.LOSS_PASSBOOK_APPLICATION_REASON, String.valueOf(blockCode));
			map.put(PaymentFieldKeys.ISSUE_MONEY_OR_TRANSFER_TO_ACCOUNT, String.valueOf(blockCode));
		}

		return new MapValuesSource(map);
	}

	@Override
	protected void checkParameters() throws TaskNotReadyException
	{
		super.checkParameters();
		if (!isThroughKSSH() && (blockCode == null || Arrays.binarySearch(ACCOUNT_BLOCK_CODES, blockCode) < 0)) // если не через шину проверяем код блокировки
			throw new UserErrorException(new TextMessage("Неверный запрос: неверный код блокировки счета.\n" +
					"Возможные значения кода:\n" +
					"0 – расторгнуть договор;\n" +
					"1 – выдать дубликат сберкнижки."));
	}

	private boolean isThroughKSSH()
	{
		try
		{
			PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
			Person person = dataProvider.getPersonData().getPerson();
			return ESBHelper.isSACSSupported(person.getLogin().getId());
		}
		catch (GateException e)
		{
			throw new InternalErrorException("Ошибка определения реализации функционала подачи завки об утере сберкнижки для ТБ на КСШ", e);
		}
	}

	public String getBlockingProductAlias()
	{
		return blockingProductAlias;
	}

	public void setBlockingProductAlias(String blockingProductAlias)
	{
		this.blockingProductAlias = blockingProductAlias;
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.LOSS_PASSBOOK;
	}
}
