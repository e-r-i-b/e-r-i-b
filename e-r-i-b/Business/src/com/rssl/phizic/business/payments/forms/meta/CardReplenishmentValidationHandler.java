package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author Gainanov
 * @ created 21.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class CardReplenishmentValidationHandler extends BusinessDocumentHandlerBase
{
	private static final String CARD_NUMBER_FIELD_NAME = "cardNumber";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof InternalTransfer))
			throw new DocumentException("Ожидается InternalTransfer");

		InternalTransfer transfer = (InternalTransfer) document;

		String cardNumber = transfer.getAttribute(CARD_NUMBER_FIELD_NAME).getStringValue();

		BankrollService service = GateSingleton.getFactory().service(BankrollService.class);

		try
		{
			Card card = GroupResultHelper.getOneResult(service.getCard(cardNumber));
			Account toAccount = GroupResultHelper.getOneResult(service.getCardPrimaryAccount(card));
			if (toAccount == null)
				throw new GateException("Не обнаружен карт-счет для карты: "+card.getNumber());
			Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();

			DepartmentService departmentService = new DepartmentService();
			Account fromAccount = GroupResultHelper.getOneResult(service.getAccountByNumber(new Pair<String, Office>(transfer.getChargeOffAccount(), departmentService.findById(departmentId))));

			if (!fromAccount.getCurrency().compare(toAccount.getCurrency()))
				throw new DocumentLogicException("Невозможно совершить платеж! Валюты карты и счета не совпадают!");

			String toBranch = toAccount.getOffice().getCode().getFields().get("branch");
			String fromBranch = fromAccount.getOffice().getCode().getFields().get("branch");

			if (!toBranch.equals(fromBranch))
				throw new DocumentLogicException("Счет списания и счет банковской карты должны быть открыты в одном ОСБ!");

			if (!toAccount.getCurrency().getNumber().equals("810") && !toAccount.getCurrency().getNumber().equals("643"))
			{
				String toAccountFullName = GroupResultHelper.getOneResult(service.getOwnerInfo(toAccount)).getFullName();
				String fromAccountFullName = GroupResultHelper.getOneResult(service.getOwnerInfo(fromAccount)).getFullName();
				if (!toAccountFullName.equalsIgnoreCase(fromAccountFullName))
					throw new DocumentLogicException("Перевод в иностранной валюте возможен только на счет карты, открытой на Ваше имя!");
			}

			if ( showOperationTypeList(fromAccount.getNumber(), toAccount.getNumber()) )
			{
				RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
				if(!requiredFieldValidator.validate(transfer.getGround()))
					throw makeValidationFail("Укажите код валютной операции!");

				RegexpFieldValidator validator = new RegexpFieldValidator("\\{VO\\d{5}\\}");
				if (!validator.validate(toAccount.getNumber()))
					throw makeValidationFail("Код валютной операции должен быть вида: {VOкод}, где код - 5 цифр.");
			}

			transfer.setReceiverAccount(toAccount.getNumber());
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	private boolean showOperationTypeList(String payer, String receiver)
	{
		return ((isResident(payer) || isResident(receiver)) && ((checkReceiverAccount(receiver) && !payer.substring(0, 3).equals("423") && !payer.substring(0, 5).equals("40817")) || checkPayerAccount(payer)));
	}

	private boolean isResident(String account)
	{
		String accountStart = account.substring(0, 5);
		return (accountStart.equals("40807") || accountStart.equals("40820") || account.substring(0, 3).equals("426"));
	}

	private boolean checkReceiverAccount(String account)
	{
		String accountStart = account.substring(0, 5);
		String[] availableAccounts = new String[6];
		availableAccounts[0] = "40817";
		availableAccounts[1] = "40819";
		availableAccounts[2] = "40820";
		availableAccounts[3] = "40813";
		availableAccounts[4] = "40814";
		availableAccounts[5] = "40815";

		for (int i = 0; i < 6; i++)
			if (accountStart == availableAccounts[i])
				return true;
		String accountMiddle = account.substring(13, 15);

		if (accountStart.equals("40818") && (Integer.parseInt(accountMiddle) > 13) && (Integer.parseInt(accountMiddle) < 19))
			return true;
		if (account.substring(0, 3).equals("426"))
			return true;
		return false;
	}

	private boolean checkPayerAccount(String account)
	{
		return (account.substring(0, 5).equals("40820") || account.substring(0, 3).equals("426"));
	}
}
