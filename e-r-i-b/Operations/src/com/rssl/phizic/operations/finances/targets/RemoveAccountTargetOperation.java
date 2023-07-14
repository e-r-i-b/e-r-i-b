package com.rssl.phizic.operations.finances.targets;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.profile.images.ImageInfo;
import com.rssl.phizic.business.profile.images.UserImageService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.operations.payment.support.DocumentTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 * ќпераци€ удалени€ цели, к которой не прив€зан вклад или вклад с нулевым остатком
 */
public class RemoveAccountTargetOperation extends OperationBase
{
	private static final String ZERO_AMOUNT = "0.00";

	private static final State SAVED_STATE = new State("SAVED");
	private static final State EXECUTED_STATE = new State("EXECUTED");

	private static final AccountTargetService targetService = new AccountTargetService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final DocumentTarget documentTarget = new DbDocumentTarget();

	private AccountTarget target;
	private Long accountLinkId;
	private Long claimId;
	private String documentStateCode;

	/**
	 * @return идентификатор линка счета, прив€занного к цели
	 */
	public Long getAccountLinkId()
	{
		return accountLinkId;
	}

	/**
	 * @return идентификатор за€вки на закрытие вклада
	 */
	public Long getClaimId()
	{
		return claimId;
	}

	/**
	 * @return код статуса за€вки на закрытие вклада
	 */
	public String getDocumentStateCode()
	{
		return documentStateCode;
	}

	/**
	 * »нициализаци€ операции
	 * @param id - идентификатор цели клиента
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		target = targetService.findTargetById(id);

		if (target == null)
			throw new ResourceNotFoundBusinessException("÷ель с id=" + id + " не найдена.",AccountTarget.class);

		AccountLink accountLink = target.getAccountLink();
		if(accountLink != null && !accountLink.getAccount().getBalance().isZero())
			throw new BusinessException("ƒанна€ операци€ предназначена только дл€ удалени€ целей, дл€ которых нет вклада или вклад с нулевым остатком.");

		if (accountLink != null)
			accountLinkId = accountLink.getId();

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(target.getLoginId()))
			throw new AccessException(" лиент с id = " + login.getId() + " не имеет доступа к цели с id = " + target.getId());
	}

	/**
	 * ћетод удалени€ цели
	 * @return результат удалени€ цели (см. com.rssl.phizic.operations.finances.targets.RemoveTargetState)
	 * @throws BusinessException
	 */
	public RemoveTargetState remove() throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = target.getAccountLink();
		if (accountLink != null)
			return closeAccount(accountLink);
		if(target.getUserImage()!=null)
		{
			ImageInfo imgInfo = UserImageService.get().getImageInfo(target.getUserImage());
			if (imgInfo!= null)
				UserImageService.get().deleteImageInfo(imgInfo, null, false);
		}
		targetService.remove(target);
		return RemoveTargetState.TARGET_REMOVED;
	}

	private RemoveTargetState closeAccount(AccountLink accountLink) throws BusinessException, BusinessLogicException
	{
		AccountClosingPayment document = createClaim(accountLink);
		processDocument(document, DocumentEvent.SAVE);
		claimId = document.getId();
		documentStateCode = document.getState().getCode();
		if (!SAVED_STATE.equals(document.getState()))
			return RemoveTargetState.CLAIM_REQUIRE_CLIENT_ACTION;
		processDocument(document, DocumentEvent.CONFIRM);
		documentStateCode = document.getState().getCode();
		if (!EXECUTED_STATE.equals(document.getState()))
			return RemoveTargetState.CLAIM_REQUIRE_CLIENT_ACTION;

		return RemoveTargetState.CLAIM_COMPLETED;
	}

	private FieldValuesSource getValuesSource(AccountLink accountLink)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PaymentFieldKeys.AMOUNT,              ZERO_AMOUNT);
		map.put(PaymentFieldKeys.FROM_RESOURCE_TYPE,  AccountLink.class.getName());
		map.put(PaymentFieldKeys.FROM_ACCOUNT_SELECT, accountLink.getNumber());
		map.put(PaymentFieldKeys.FROM_RESOURCE_KEY,   accountLink);
		map.put("fromPersonalFinance", true);
		return new MapValuesSource(map);
	}

	private AccountClosingPayment createClaim(AccountLink accountLink) throws BusinessException, BusinessLogicException
	{
		DocumentSource source = new NewDocumentSource(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM, getValuesSource(accountLink), CreationType.internet, CreationSourceType.ordinary);
		return (AccountClosingPayment) source.getDocument();
	}

	@Transactional
	private void processDocument(AccountClosingPayment document, DocumentEvent event) throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.setStateMachineEvent(getStateMachineEvent());
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(event, ObjectEvent.CLIENT_EVENT_TYPE));
		documentTarget.save(document);
	}
}
