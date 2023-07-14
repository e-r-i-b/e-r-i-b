package com.rssl.phizic.web.client.sberbankForEveryDay;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.AllowedTbForSBNKDValidator;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.login.GuestHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.sbnkd.SberbankForEveryDayHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.limits.LimitsConfig;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.claims.sbnkd.CardInfo;
import com.rssl.phizic.gate.claims.sbnkd.FullAddress;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardStatus;
import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.sberbankForEveryDay.CreateSberbankForEveryDayClaimOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsGuestImpl;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.action.*;

import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ���������� ������ "�������� �� ������ ����"
 * @author shapin
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateSberbankForEveryDayClaimAction extends OperationalActionBase
{
	protected static final String FORWARD_CONFIRM = "Confirm";
	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	public static final String NOT_ALLOWED_TB_ERROR_MESSAGE = "<div class=\"notice noticeCancel\"><div class=\"noticeTitle\">������ �� �������������� ������ ����</div> ���������� �������� ������ � ���� ������� ����� ��������� �����. " +
			                                                  "�� ������ ���������� �� ������������� � ���������� ����� ��������� �� ��������� +7 (495) 500-55-50, 8 (800) 555-55-50</div>";

	public static final String TB_NOT_ALLOWED_ERROR_MESSAGE = "<div class=\"notice noticeCancel\"><div class=\"noticeTitle\">� ��������� ������� �� �������������� ������ ����</div>���������� �������� ������ � ���� ������� ����� ��������� �����" +
			                                                  "��� �������� ������ ������ �� ������ ���������." +
			                                                  "�� ������ ���������� �� ������������� � ���������� ����� ��������� �� ��������� +7 (495) 500-55-50, 8 (800) 555-55-50</div>";

	private static final String EMPTY_DEPARTMENT_ERROR_MESSAGE = "RequiredFieldValidator.message[������������ ���������]";

	private enum ClaimState {
		STAGE_CARD_SELECT,
		STAGE_PERSONAL_INFO,
		STAGE_ADDING_SERVICES,
		STAGE_SELECT_OFFICE,
		STAGE_CONFIRM_BOX;
	}
	private enum ClientCategory {
		OTHERS(0L),
		NOT_RESIDENT(100L),
		YOUNG(211L);

		private Long number;
		public Long getNumber()
		{
			return this.number;
		}

		ClientCategory(Long num)
		{
			this.number = num;
		}
	}
	private static List<String> guestDocumentTypes = new ArrayList<String>();
	static
	{
		guestDocumentTypes.add(ClientDocumentType.REGULAR_PASSPORT_RF.toString());
		guestDocumentTypes.add(ClientDocumentType.REGULAR_PASSPORT_USSR.toString());
		guestDocumentTypes.add(ClientDocumentType.MILITARY_IDCARD.toString());
		guestDocumentTypes.add(ClientDocumentType.OFFICER_IDCARD.toString());
		guestDocumentTypes.add(ClientDocumentType.RESIDENTIAL_PERMIT_RF.toString());
		guestDocumentTypes.add(ClientDocumentType.TEMPORARY_PERMIT.toString());
		guestDocumentTypes.add(ClientDocumentType.FOREIGN_PASSPORT.toString());
	}

	private static final String RF_CITIZENSHIP = "��";
	private static final String MB_FULL = "full";
	private static final String MB_ECONOM = "econom";
	private static final String ON_VALUE = "on";
	private static final String MOBILE_PHONE_FIELD = "mobilePhone";
	private static final String NEED_AUTOPAYMENT_FIELD = "needAutopayment";
	private static final String RF_CITIZEN_FIELD = "RFCitizen";
	private static final String PATR_NAME_ABSENT_FIELD = "patrNameAbsent";
	private static final String LIVING_BY_REGISTRATION_PLACE_FIELD = "livingByRegistrationPlace";
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	private static final String YOUTH_CARD_TYPE_CLASSIC_CODE = "PRRCBVCM--";
	private static final String YOUTH_CARD_TYPE_STANDART_CODE = "PRRCBMSM--";
	public static final int STREET_MASK_CHARS_NUMBER = 4;
	public static final String CHAR_FOR_MASK = "�";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.continue", "validateAndDoNextStage");
		map.put("button.confirm", "confirm");
		map.put("button.addCard", "addCard");
		map.put("button.confirmSMS", "changeToSMS");
		map.put("button.edit", "editStages");
		map.put("button.nextStage", "doNextStage");
		map.put("button.removeCard", "removeCard");
		map.put("button.orderCard", "validateAndDoNextStage");
		map.put("button.backToEdit", "backToEdit");
		return map;
	}

	private ClaimState getState(int stageNumber)
	{
		if (stageNumber <= ClaimState.values().length)
			return ClaimState.values()[stageNumber - 1];
		return null;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimOperation operation = createOperation(CreateSberbankForEveryDayClaimOperation.class, "CreateSberbankForEveryDayClaimService");
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;

		//������ ������������ ��������
		for(String fieldKey : frm.getFields().keySet())
		{
			if(frm.getField(fieldKey) != null && StringHelper.isNotEmpty(frm.getField(fieldKey).toString()))
				frm.setField(fieldKey, StringHelper.formatStringForJavaScript(getStringWithoutDoubleQuotes(frm.getField(fieldKey).toString()), true));
		}

		try
		{
			if (frm.getId() == null)
			{
				operation.initializeNewClaim();
			}
			else
			{
				operation.initialize(frm.getId());
			}
		}
		catch (BusinessLogicException e)
		{
			if (operation.isVipClient())
			{
				frm.setVipClient(true);
				frm.setVipClientMessage(ConfigFactory.getConfig(SBNKDConfig.class).getTextMessageVipClient());
			}
			else
			{
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveErrors(request, errors);
			}
		}

		IssueCardStatus status = operation.getEntity().getStatus();
		saveOperation(request, operation);
		if (status != IssueCardStatus.INIT_NO_VIP && status != IssueCardStatus.INIT && status != IssueCardStatus.VIP_CLIENT)
			return doNextStage(mapping, form, request, response);

		updateFormAdditionalData(frm, operation, request);
		//����� ������ �� �������� ������ �� ������� �������� � ������ �� ��� ���������� ������������ ������ (2 ���, �����)
		//�������� ��������� "� ��������� ��" � "� �������� �� ����� ��������" ��� �� ���������� �������������
		//������� ��������� �������� ����� ���������� �� ������ �� �����, ��� ����� ���� �������� �� ���������: RFCitizen = true; livingByRegistrationPlace = true;
		if(!frm.isFromHistory() && operation.getEntity().getStageNumber() > ClaimState.STAGE_PERSONAL_INFO.ordinal() + 1)
		{
			updateFormSigns(frm);
		}

		//�������� ������� ��� ����� �� �������� ���������� ������
		if (frm.getStageNumber() == 1 && !operation.checkLimit())
		{
			saveErrors(request, Collections.singletonList(ConfigFactory.getConfig(LimitsConfig.class).getTextMessageCardClaimeLimit()));
			return mapping.findForward(FORWARD_START);
		}

		if(frm.getEditStageNumber() > ClaimState.STAGE_CARD_SELECT.ordinal() + 1)
			maskFields(frm, operation.getEntity());

		//���� ������ ������� �� ������� ��������, �� ����������, ������ �� ��� � ������������� ��� ���, �.�.
		//������� isPreparedForConfirm � �� �� ��������, ������� ����������� ���
		if(frm.isFromHistory())
		{
			if(frm.getStageNumber() == ClaimState.values().length)
				frm.setPreparedForConfirm(true);
			frm.setFromHistory(false);
		}
		//����  ������ ��������� ��������� � ��� ����� ���������� - ��������� �� ����� �������������
		if (frm.getStageNumber() > ClaimState.values().length && frm.getEditStageNumber() == ClaimState.STAGE_CARD_SELECT.ordinal() + 1)
			return validateAndDoNextStage(mapping, form, request, response);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward validateAndDoNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;
		CreateSberbankForEveryDayClaimOperation operation = createOperation(CreateSberbankForEveryDayClaimOperation.class, "CreateSberbankForEveryDayClaimService");

		//������ ������������ ��������
		for(String fieldKey : frm.getFields().keySet())
		{
			if(frm.getField(fieldKey) != null && StringHelper.isNotEmpty(frm.getField(fieldKey).toString()))
				frm.setField(fieldKey, StringHelper.formatStringForJavaScript(getStringWithoutDoubleQuotes(frm.getField(fieldKey).toString()), true));
		}

		try
		{
			if (frm.getId() == null)
			{
				operation.initializeNewClaim();
			}
			else
			{
				operation.initialize(frm.getId());
			}
		}
		catch (BusinessLogicException e)
		{
			if (operation.isVipClient())
			{
				frm.setVipClient(true);
				frm.setVipClientMessage(ConfigFactory.getConfig(SBNKDConfig.class).getTextMessageVipClient());
			}
			else
			{
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveErrors(request, errors);
			}
		}

		frm.setGuest(operation.getEntity().isGuest());
		updateFormSigns(frm);
		unmaskFields(frm, operation.getEntity());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), frm.createForm());

		if (frm.getStageNumber() == 1 && !operation.checkLimit())
		{
			saveErrors(request, Collections.singletonList(ConfigFactory.getConfig(LimitsConfig.class).getTextMessageCardClaimeLimit()));
			maskFields(frm, operation.getEntity());
			return mapping.findForward(FORWARD_START);
		}

		if (!processor.process())
		{
			String errMsg = (processor.getErrors().get().next()).toString();
			if(errMsg.contains(EMPTY_DEPARTMENT_ERROR_MESSAGE))
			{
				frm.setNeedEmptyDeptErrorMessage(true);
			}
			else
			{
				if(errMsg.contains(TB_NOT_ALLOWED_ERROR_MESSAGE))
				{
					saveError(request, TB_NOT_ALLOWED_ERROR_MESSAGE);
				}
				else
				{
					saveErrors(request, processor.getErrors());
				}
			}
			frm.setError(true);
			frm.setPreparedForConfirm(false);
		}
		else
		{
			try
			{
				updateEntity(frm, operation.getEntity());
				operation.save();
			}
			catch (BusinessLogicException ex)
			{
				if (operation.isVipClient())
				{
					frm.setVipClient(true);
					frm.setVipClientMessage(ConfigFactory.getConfig(SBNKDConfig.class).getTextMessageVipClient());
				}
				else
				{
					ActionMessages errors = new ActionMessages();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
					saveErrors(request, errors);
				}
				maskFields(frm, operation.getEntity());
				return mapping.findForward(FORWARD_START);
			}
		}

		if (frm.getStageNumber() >= 4)
		{
			String tb = frm.getField(CreateSberbankForEveryDayClaimForm.TB_FIELD).toString();
			String osb = frm.getField(CreateSberbankForEveryDayClaimForm.OSB_FIELD).toString();
			String vsp = frm.getField(CreateSberbankForEveryDayClaimForm.VSP_FIELD).toString();
			if(operation.getConvertedDepartment(tb, osb, vsp) == null)
			{
				// ���� ������������� �� ���� �������, �� ��������� ������ �� ��������� (������������ ��������� ��������� �� ������)
				if(StringHelper.isNotEmpty(tb + osb + vsp))
				{
					saveErrors(request, Collections.singletonList(StrutsUtils.getMessage("message.emptyDepartmentFromSPOOBK2", "sbnkdBundle")));
				}
				frm.setError(true);
				frm.setPreparedForConfirm(false);
			}
		}

		updateFormAdditionalData(frm, operation, request);

		if (frm.getStageNumber() <= ClaimState.values().length)
		{
			//��������� ������ ��� ��������� � ��������������
			if(frm.getStageNumber() == ClaimState.values().length && frm.getEditStageNumber() == 1)
				frm.setPreparedForConfirm(true);
			return mapping.findForward(FORWARD_START);
		}
		else
		{
			//������� � �������������
			//��������� ���������� ���� � ������
			if (!validateCardCount(frm, request))
			{
				frm.setStageNumber(ClaimState.values().length);
				return mapping.findForward(FORWARD_START);
			}
			frm.setConfirmStrategy(operation.getConfirmStrategy());

			unmaskFields(frm, operation.getEntity());
			//��������� �� �������� ������������� ������ ���� �� ���� ������
			if(processor.process())
			{
				frm.setConfirmableObject(operation.getEntity());
				operation.resetConfirmStrategy();
				ConfirmationManager.sendRequest(operation);
				saveOperation(request, operation);
				frm.setIssueCardDoc(operation.getEntity());
				maskFields(frm, operation.getEntity());
				return mapping.findForward(FORWARD_CONFIRM);
			}
			else
			{
				frm.setError(true);
				frm.setPreparedForConfirm(false);
				frm.setStageNumber(ClaimState.values().length);
				maskFields(frm, operation.getEntity());
				return mapping.findForward(FORWARD_START);
			}
		}
	}

	public ActionForward addCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;
		CreateSberbankForEveryDayClaimOperation operation = createOperation(CreateSberbankForEveryDayClaimOperation.class, "CreateSberbankForEveryDayClaimService");
		operation.initialize(frm.getId());
		frm.setGuest(operation.getEntity().isGuest());

		unmaskFields(frm, operation.getEntity());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), frm.createAddCardForm());
		if (!processor.process())
		{
			if(operation.getEntity().getCardCount() == frm.getCardCount())
			{
				frm.setCardError("editCardWin" + frm.getEditCardNumber() + " " + (processor.getErrors().get().next()).toString());
			}
			else
			{
				frm.setCardError("addCardWin " + (processor.getErrors().get().next()).toString());
			}

			frm.setError(true);
			frm.setPreparedForConfirm(false);
		}
		else
		{
			updateCardEntity(frm, operation.getEntity());
			try
			{
				operation.save();
			}
			catch (BusinessLogicException ex)
			{
				if (operation.isVipClient())
				{
					frm.setVipClient(true);
					frm.setVipClientMessage(ConfigFactory.getConfig(SBNKDConfig.class).getTextMessageVipClient());
				}
				else
				{
					ActionMessages errors = new ActionMessages();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
					saveErrors(request, errors);
				}
				maskFields(frm, operation.getEntity());
				return mapping.findForward(FORWARD_START);
			}
		}
		updateFormAdditionalData(frm, operation, request);
		updateFormSigns(frm);

		//6 ��� - �������� ����������� ������, � ��� ������ � ������� ������ ������ �� �����,
		//������� ������ �� ����� ��� = 5
		if(frm.getStageNumber() == ClaimState.values().length + 1)
		{
			frm.setStageNumber(ClaimState.values().length);
		}
		maskFields(frm, operation.getEntity());
		return mapping.findForward(FORWARD_START);
	}

	private boolean validateCardCount(CreateSberbankForEveryDayClaimForm frm, HttpServletRequest request)
	{
		int maxDebitCard = ConfigFactory.getConfig(SBNKDConfig.class).getMaxDebitCard();
		if (frm.getCardCount() > maxDebitCard && maxDebitCard != 0)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ConfigFactory.getConfig(SBNKDConfig.class).getMaxDebitCardMessage(), false));
			saveErrors(request, errors);
			return false;
		}
		return true;
	}

	public ActionForward changeToSMS(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimForm form = (CreateSberbankForEveryDayClaimForm) frm;
		CreateSberbankForEveryDayClaimOperation operation = getOperation(request);
		updateFormAdditionalData(form, operation, request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);

		Login login;
		ConfirmRequest confirmRequest = operation.getRequest();
		CallBackHandler callBackHandler;
		login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if(login instanceof GuestLogin)
		{
			callBackHandler = new CallBackHandlerSmsGuestImpl();
			callBackHandler.setConfirmableObject(operation.getConfirmableObject());

		}
		else
		{
			callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setConfirmableObject(operation.getConfirmableObject());
			callBackHandler.setLogin(login);
			callBackHandler.setOperationType(OperationType.PAYMENT_OPERATION);
		}

		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("�������������� ��������", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(SecurityMessages.translateException(e)));
		}

		form.setConfirmStrategy(operation.getConfirmStrategy());
		form.setConfirmableObject(operation.getConfirmableObject());
		return mapping.findForward(FORWARD_CONFIRM);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)    throws Exception
	{
		CreateSberbankForEveryDayClaimOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;
		try
		{
			frm.setConfirmStrategy(operation.getConfirmStrategy());

			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (errors.isEmpty())
			{
				if (operation.checkLimit())
				{
					try
					{
						operation.confirm();
						GuestHelper.fillGuestContextAdditionDataIfNotGuestId();
						addLogParameters(new BeanLogParemetersReader("�������������� ��������", operation.getConfirmableObject()));
						return doNextStage(mapping, frm, request, response);
					}
					catch (BusinessLogicException e)
					{
						ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));
						frm.setConfirmStrategy(operation.getConfirmStrategy());
						frm.setConfirmableObject(operation.getConfirmableObject());
						return mapping.findForward(FORWARD_CONFIRM);
					}
					catch (SecurityLogicException e)
					{
						ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));
						frm.setConfirmStrategy(operation.getConfirmStrategy());
						frm.setConfirmableObject(operation.getConfirmableObject());
						return mapping.findForward(FORWARD_CONFIRM);
					}
				}
				ConfirmHelper.saveConfirmErrors(confirmRequest, Collections.singletonList(ConfigFactory.getConfig(LimitsConfig.class).getTextMessageCardClaimeLimit()));
				frm.setConfirmStrategy(operation.getConfirmStrategy());
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_CONFIRM);
			}


			ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
			return mapping.findForward(FORWARD_CONFIRM);
		}
		catch (TemporalBusinessException ignore)
		{
			String exceptionMessage = getResourceMessage("paymentsBundle", "message.operation.not.available");
			ConfirmHelper.saveConfirmErrors(confirmRequest, Collections.singletonList(exceptionMessage));
			maskFields(frm, operation.getEntity());
			return mapping.findForward(FORWARD_SHOW);
		}
	}

	private void updateEntity(CreateSberbankForEveryDayClaimForm frm, IssueCardDocumentImpl document) throws BusinessException, ParseException
	{
		if (frm.getStageNumber() > ClaimState.values().length)
			return;
		if (frm.isGuest())
		{
			document.setTb(frm.getField(CreateSberbankForEveryDayClaimForm.PERSON_TB_FIELD).toString());
			document.setPersonRegionId(Long.valueOf(frm.getField(CreateSberbankForEveryDayClaimForm.REGION_ID_FIELD).toString()));
		}

		//��� ������: ����� �������� �����
		if(frm.getStageNumber() == ClaimState.STAGE_CARD_SELECT.ordinal() + 1)
		{
			updateCardEntity(frm, document);
		}

		boolean editClaim = frm.getEditStageNumber() > ClaimState.STAGE_CARD_SELECT.ordinal() + 1; //������ ������� ��� ��������������
		//��� ������: ���������� ������������ ������
		if(frm.isGuest() && (frm.getStageNumber() == ClaimState.STAGE_PERSONAL_INFO.ordinal() + 1 ||
				editClaim && frm.getEditStageNumber() <= ClaimState.STAGE_PERSONAL_INFO.ordinal() + 1 && ClaimState.STAGE_PERSONAL_INFO.ordinal() + 1 < frm.getStageNumber()))
		{
			fillDocFields(frm, document);
			document.setPersonBirthday(DateHelper.toCalendar(DateHelper.parseStringTime(frm.getField(CreateSberbankForEveryDayClaimForm.BIRTH_DATE_FIELD).toString() + " " + DateHelper.BEGIN_DAY_TIME)));
			document.setPersonBirthplace(frm.getField(CreateSberbankForEveryDayClaimForm.BIRTH_PLACE_FIELD).toString());
			document.setPersonCitizenship(!frm.isRFCitizen() ? frm.getField(CreateSberbankForEveryDayClaimForm.NATIONALITY_FIELD).toString() : RF_CITIZENSHIP);

			FullAddress resAddr = new FullAddress();

			//����� ��������
			if(frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_INDEX_FIELD) != null)
				resAddr.setPostalCode(frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_INDEX_FIELD).toString());
			resAddr.setRegion(frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_REGION_FIELD).toString());
			resAddr.setCity(frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_LOCALITY_FIELD).toString());
			resAddr.setAfterSityAdress(SberbankForEveryDayHelper.getAfterCityAddress(
					frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_STREET_FIELD).toString(),
					frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_BUILDING_FIELD) != null ? frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_BUILDING_FIELD).toString() : "",
					frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_CORPS_FIELD) != null ? frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_CORPS_FIELD).toString() : "",
					frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_ROOM_FIELD) != null ? frm.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_ROOM_FIELD).toString() : ""
			));

			//����� ����������
			if(!frm.isLivingByRegistrationPlace())
			{
				FullAddress regAddr = new FullAddress();
				if(frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_INDEX_FIELD) != null)
					regAddr.setPostalCode(frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_INDEX_FIELD).toString());
				regAddr.setRegion(frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_REGION_FIELD).toString());
				regAddr.setCity(frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_LOCALITY_FIELD).toString());
				regAddr.setAfterSityAdress(SberbankForEveryDayHelper.getAfterCityAddress(
						frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_STREET_FIELD).toString(),
						frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_BUILDING_FIELD) != null ? frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_BUILDING_FIELD).toString() : "",
						frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_CORPS_FIELD) != null ? frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_CORPS_FIELD).toString() : "",
						frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_ROOM_FIELD) != null ? frm.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_ROOM_FIELD).toString() : ""
				));
				document.setAddress(new FullAddress[]{resAddr, regAddr});
			}
			else
			{
				document.setAddress(new FullAddress[]{resAddr});
			}
		}

		//��� ������: ���������� �����
		if(frm.getStageNumber() == ClaimState.STAGE_ADDING_SERVICES.ordinal() + 1 ||
				editClaim && frm.getEditStageNumber() <= ClaimState.STAGE_ADDING_SERVICES.ordinal() + 1 && ClaimState.STAGE_ADDING_SERVICES.ordinal() + 1 < frm.getStageNumber())
		{
			for(CardInfo card: document.getCardInfos())
			{
				card.setMBCStatus(true);
				card.setMBCContractType(frm.getField(CreateSberbankForEveryDayClaimForm.MOBILE_BANK_TARIFF_FIELD).toString().equals(MB_ECONOM) ? 1L : 0L);
				card.setMBCPhone(document.getPhone());
			}

			if(frm.isNeedAutopayment())
			{
				document.setCardAcctAutoPayInfo(Double.parseDouble(frm.getField(CreateSberbankForEveryDayClaimForm.REFILL_ON_SUM_FIELD).toString()));
				document.setBalanceLessThan(frm.getField(CreateSberbankForEveryDayClaimForm.BALANCE_LESS_THAN_FIELD).toString());
				//CHG084412 �������� ������� ���� ��������� ��������
				//document.getCardInfos().get(0).setMBCPhoneCode(frm.getField(CreateSberbankForEveryDayClaimForm.MOBILE_OPERATOR_FIELD).toString());
			}
			else
			{
				document.setCardAcctAutoPayInfo(0);
			}
		}

		//��� ������: ����� ��������� ��� ������ �����
		if(frm.getStageNumber() == ClaimState.STAGE_SELECT_OFFICE.ordinal() + 1 ||
				editClaim && frm.getEditStageNumber() <= ClaimState.STAGE_SELECT_OFFICE.ordinal() + 1 && ClaimState.STAGE_SELECT_OFFICE.ordinal() + 1< frm.getStageNumber())
		{
			document.setContractRegionId(frm.getField(CreateSberbankForEveryDayClaimForm.TB_FIELD).toString());
			document.setContractBranchId(frm.getField(CreateSberbankForEveryDayClaimForm.VSP_FIELD).toString());
			document.setContractAgencyId(frm.getField(CreateSberbankForEveryDayClaimForm.OSB_FIELD).toString());
			document.setContractCreditCardOffice(frm.getField(CreateSberbankForEveryDayClaimForm.CREDIT_CARD_OFFICE_FIELD).toString());
			//��������� ���������� �� ������(��������� �������)
			updateCardEntity(frm, document);
		}
		//����� ��� ���������� �������� �������� �� ����������� ����� ����.
		//��� ���������� ���������� ������ � ����� ������� ������ ����� � ������ � �� ����� ������ ��������� (�� ����������� ������, ����� ������������ � ��������������)
		if(document.getStageNumber() == frm.getStageNumber())
		{
			if(document.getStageNumber() <= ClaimState.values().length)
				document.setStageNumber(document.getStageNumber() + 1);
			if(!document.isGuest() && getState(document.getStageNumber()) == ClaimState.STAGE_PERSONAL_INFO)
			{
				document.setStageNumber(document.getStageNumber() + 1);
			}
		}
		//���������� ��� ��������������, �� ������ �� �����
		if(frm.getEditStageNumber() > ClaimState.STAGE_CARD_SELECT.ordinal() + 1)
			frm.setEditStageNumber(ClaimState.STAGE_CARD_SELECT.ordinal() + 1);
	}

	private void updateCardEntity(CreateSberbankForEveryDayClaimForm frm, IssueCardDocumentImpl document) throws BusinessException
	{
		List<CardInfoImpl> cardList = document.getCardInfos();
		boolean isResident;
		if(document.isGuest())
		{
			if(StringHelper.isEmpty(document.getIdentityCardType()))
				isResident = true;
			else
			{
				isResident = !(PassportTypeWrapper.getClientDocumentType(document.getIdentityCardType()).equals(ClientDocumentType.TEMPORARY_PERMIT) || PassportTypeWrapper.getClientDocumentType(document.getIdentityCardType()).equals(ClientDocumentType.FOREIGN_PASSPORT));
			}
		}
		else
		{
			isResident = BooleanUtils.isTrue(PersonContext.getPersonDataProvider().getPersonData().getPerson().getIsResident());
		}
		for(int i = 0; i <= frm.getCardCount() - 1; i++)
		{
			CardInfoImpl card = i < cardList.size()? cardList.get(i) : new CardInfoImpl();
			long pinpack = Long.parseLong(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_PINPACK_FIELD + i).toString());
			card.setFirstCard(i == 0);
			card.setCardName(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_NAME_FIELD + i).toString());
			card.setCardTypeString(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_FIELD + i).toString());
			card.setContractCardType(Long.parseLong(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_NUMBER_FIELD  + i).toString()));
			card.setContractCurrency(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CURRENCY_FIELD + i).toString());
			card.setContractProductCode(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_SHORT_FIELD + i).toString());
			card.setCardAcctProductCode(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_FIELD + i).toString());
			card.setContractBIOData(Boolean.getBoolean(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_BIOAPPLET_FIELD + i).toString()));
			card.setCardAcctPinPack(pinpack);
			if(card.getCardAcctProductCode().equals(YOUTH_CARD_TYPE_CLASSIC_CODE) || card.getCardAcctProductCode().equals(YOUTH_CARD_TYPE_STANDART_CODE))
			{
				card.setContractClientCategory(ClientCategory.YOUNG.getNumber());
			}
			else
			{
				if(isResident)
				{
					card.setContractClientCategory(ClientCategory.OTHERS.getNumber());
				}
				else
				{
					card.setContractClientCategory(ClientCategory.NOT_RESIDENT.getNumber());
				}
			}
			if(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_BONUS_FIELD + i) != null)
				card.setBonusInfoCode(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_BONUS_FIELD + i).toString());
			else
				card.setBonusInfoCode("0");
			if(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CREDIT_LIMIT_FIELD + i) != null && StringHelper.isNotEmpty(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CREDIT_LIMIT_FIELD + i).toString()))
			{
				card.setCardAcctCreditLimit(NumberUtils.createBigDecimal(frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CREDIT_LIMIT_FIELD + i).toString()));
			}
			if(StringHelper.isEmpty(card.getMBCPhone()))
				card.setMBCPhone(document.getPhone());
			if(document.getStageNumber() > ClaimState.STAGE_ADDING_SERVICES.ordinal() + 1)
			{
				card.setMBCStatus(true);
				card.setMBCContractType(frm.getField(CreateSberbankForEveryDayClaimForm.MOBILE_BANK_TARIFF_FIELD ).toString().equals(MB_ECONOM) ? 1L : 0L);
				//CHG084412 �������� ������� ���� ��������� ��������
				//if(i == 0)
				//card.setMBCPhoneCode(frm.getField(CreateSberbankForEveryDayClaimForm.MOBILE_OPERATOR_FIELD).toString());
			}
			card.setParent(document);
			if (i >= cardList.size())
				cardList.add(card);
		}
		/*if(document.getStageNumber() > ClaimState.STAGE_ADDING_SERVICES.ordinal() + 1)
		{
			//CHG084412 �������� ������� ���� ��������� ��������
			//cardList.get(0).setMBCPhoneCode(frm.getField(CreateSberbankForEveryDayClaimForm.MOBILE_OPERATOR_FIELD).toString());
		}*/

		document.setCardInfos(cardList);

		if (document.isGuest())
		{
			document.setPersonLastName(frm.getField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD).toString());
			document.setPersonFirstName(frm.getField(CreateSberbankForEveryDayClaimForm.FIRST_NAME_FIELD).toString());
			if(!frm.isPatrNameAbsent())
			{
				document.setPersonMiddleName(frm.getField(CreateSberbankForEveryDayClaimForm.PATR_NAME_FIELD).toString());
			}
			else
			{
				document.setPersonMiddleName("");
			}
			document.setPersonGender(frm.getField(CreateSberbankForEveryDayClaimForm.GENDER_FIELD).toString());
		}
		document.setContractEmbossedText(frm.getField(CreateSberbankForEveryDayClaimForm.FIRST_NAME_AND_LAST_NAME_LATIN_FIELD).toString());

		document.setEmail(frm.getField(CreateSberbankForEveryDayClaimForm.EMAIL_FIELD).toString());
	}


	private void updateFormAdditionalData(CreateSberbankForEveryDayClaimForm frm, CreateSberbankForEveryDayClaimOperation operation, HttpServletRequest request) throws BusinessException
	{
		IssueCardDocumentImpl document = operation.getEntity();
		frm.setId(document.getId());
		frm.setGuest(document.isGuest());
		frm.setIssueCardDoc(document);

		if (!frm.isGuest() || (frm.getId() != null && document.getStageNumber() > ClaimState.STAGE_CARD_SELECT.ordinal()))
		{
			if(document.getPersonLastName() != null)
				frm.setField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD, document.getPersonLastName().substring(0,1) + ".");
			frm.setField(CreateSberbankForEveryDayClaimForm.FIRST_NAME_FIELD, document.getPersonFirstName());
			frm.setField(CreateSberbankForEveryDayClaimForm.PATR_NAME_FIELD, document.getPersonMiddleName());
			frm.setPatrNameAbsent(StringHelper.isEmpty(document.getPersonMiddleName()));
			frm.setField(CreateSberbankForEveryDayClaimForm.GENDER_FIELD, document.getPersonGender());
		}

		if (!frm.isGuest() || (frm.getId() != null && document.getStageNumber() > ClaimState.STAGE_PERSONAL_INFO.ordinal() + 1))
		{
			frm.setField(CreateSberbankForEveryDayClaimForm.DOC_TYPE_FIELD, PassportTypeWrapper.getClientDocumentType(document.getIdentityCardType()));
			if(StringHelper.isNotEmpty(document.getIdentityCardSeries()))
				frm.setField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD, document.getIdentityCardSeries() + " " + document.getIdentityCardNumber());
			else
				frm.setField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD, document.getIdentityCardNumber());
			frm.setField(CreateSberbankForEveryDayClaimForm.OFFICE_CODE_FIELD, document.getIdentityCardIssuedCode());
			frm.setField(CreateSberbankForEveryDayClaimForm.ISSUED_BY_FIELD, document.getIdentityCardIssuedBy());

			if(document.getIdentityCardIssueDate() != null)
				frm.setField(CreateSberbankForEveryDayClaimForm.ISSUE_DATE_FIELD, DateFormatUtils.format(document.getIdentityCardIssueDate(), DATE_FORMAT));
			if(document.getIdentityCardExpDate() != null)
				frm.setField(CreateSberbankForEveryDayClaimForm.EXPIRE_DATE_FIELD, DateFormatUtils.format(document.getIdentityCardExpDate(), DATE_FORMAT));
			frm.setField(CreateSberbankForEveryDayClaimForm.BIRTH_PLACE_FIELD, document.getPersonBirthplace());
			if(document.getPersonBirthday() != null)
				frm.setField(CreateSberbankForEveryDayClaimForm.BIRTH_DATE_FIELD, DateFormatUtils.format(document.getPersonBirthday(), DATE_FORMAT));
			frm.setField(CreateSberbankForEveryDayClaimForm.NATIONALITY_FIELD, document.getPersonCitizenship());

			if (document.getAddress().length > 0 && document.getAddress()[0] != null)
			{
				frm.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_INDEX_FIELD, document.getAddress()[0].getPostalCode());
				frm.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_REGION_FIELD, document.getAddress()[0].getRegion());
				frm.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_LOCALITY_FIELD, document.getAddress()[0].getCity());
				//afterCityAddress
				String regStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, document.getAddress()[0].getAfterSityAdress());
				if(StringHelper.isNotEmpty(regStreet))
					frm.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_STREET_FIELD, MaskUtil.maskString(regStreet, 0,
							regStreet.length() >= STREET_MASK_CHARS_NUMBER ? regStreet.length() - STREET_MASK_CHARS_NUMBER : 0,
							CHAR_FOR_MASK));
				frm.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_BUILDING_FIELD, MaskUtil.maskString(SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, document.getAddress()[0].getAfterSityAdress()), 0, 0, CHAR_FOR_MASK));
				frm.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_CORPS_FIELD, MaskUtil.maskString(SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, document.getAddress()[0].getAfterSityAdress()), 0, 0, CHAR_FOR_MASK));
				frm.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_ROOM_FIELD, MaskUtil.maskString(SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, document.getAddress()[0].getAfterSityAdress()), 0, 0, CHAR_FOR_MASK));
			}

			if (document.getAddress().length > 1 && document.getAddress()[1] != null)
			{
				frm.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_INDEX_FIELD, document.getAddress()[1].getPostalCode());
				frm.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_REGION_FIELD, document.getAddress()[1].getRegion());
				frm.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_LOCALITY_FIELD, document.getAddress()[1].getCity());
				String resStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, document.getAddress()[1].getAfterSityAdress());
				if(StringHelper.isNotEmpty(resStreet))
					frm.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_STREET_FIELD, MaskUtil.maskString(resStreet, 0,
							resStreet.length() >= STREET_MASK_CHARS_NUMBER ? resStreet.length() - STREET_MASK_CHARS_NUMBER : 0,
							CHAR_FOR_MASK));
				frm.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_BUILDING_FIELD, MaskUtil.maskString(SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, document.getAddress()[1].getAfterSityAdress()), 0, 0, CHAR_FOR_MASK));
				frm.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_CORPS_FIELD, MaskUtil.maskString(SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, document.getAddress()[1].getAfterSityAdress()), 0, 0, CHAR_FOR_MASK));
				frm.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_ROOM_FIELD, MaskUtil.maskString(SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, document.getAddress()[1].getAfterSityAdress()), 0, 0, CHAR_FOR_MASK));
			}
			if (RF_CITIZENSHIP.equals(document.getPersonCitizenship()))
				frm.setField(RF_CITIZEN_FIELD, ON_VALUE);

			if (document.getAddress() != null && document.getAddress().length == 1)
				frm.setField(LIVING_BY_REGISTRATION_PLACE_FIELD, ON_VALUE);
		}
		if (frm.isGuest())
		{
			updateRegionData(frm, request, document);
		}

		if(document.getStageNumber() > ClaimState.STAGE_CARD_SELECT.ordinal())
		{
			//�� ������ ���� ������������� ���� �� ������ ������ ���� �� ���� ������
			//(� ������ ������ ������ �� ����� � ������ �� �����������, ������ �����)
			if(!frm.isError())
			{
				frm.setField(CreateSberbankForEveryDayClaimForm.FIRST_NAME_AND_LAST_NAME_LATIN_FIELD, document.getContractEmbossedText());
				frm.setField(CreateSberbankForEveryDayClaimForm.EMAIL_FIELD, document.getEmail());
			}
		}

		if(document.getId() != null && !frm.isError())
		{
			List<CardInfoImpl> cardList = document.getCardInfos();

			int i = 0;
			for(CardInfoImpl card : cardList)
			{
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_NAME_FIELD + i, card.getCardName());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_FIELD + i, card.getCardTypeString());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_NUMBER_FIELD + i, card.getContractCardType());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CURRENCY_FIELD + i, card.getContractCurrency());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_SHORT_FIELD + i, card.getContractProductCode());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_FIELD + i, card.getCardAcctProductCode());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_BIOAPPLET_FIELD + i, card.getContractBIOData());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_PINPACK_FIELD + i, card.getCardAcctPinPack());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_BONUS_FIELD + i, card.getBonusInfoCode());
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CREDIT_LIMIT_FIELD + i, card.getCardAcctCreditLimit() != null ? card.getCardAcctCreditLimit().toString() : null);
				frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CLIENT_CATEGORY_FIELD + i, card.getContractClientCategory());
				i++;
			}
		}

		//�� ���� ���������� ������������ ������ ��������� ������ ����������
		if (getState(document.getStageNumber()) == ClaimState.STAGE_PERSONAL_INFO || getState(frm.getEditStageNumber()) != ClaimState.STAGE_CARD_SELECT)
			if(frm.isGuest())
			{
				frm.setDocumentTypes(guestDocumentTypes);
			}
			else
				frm.setDocumentTypes(PersonHelper.getDocumentStringTypes());

		if (getState(document.getStageNumber()) == ClaimState.STAGE_ADDING_SERVICES && frm.getField(CreateSberbankForEveryDayClaimForm.MOBILE_BANK_TARIFF_FIELD) == null)
		{
			frm.setField(CreateSberbankForEveryDayClaimForm.MOBILE_BANK_TARIFF_FIELD , frm.getDefaultMobileBankTariff());
		}

		if(document.getStageNumber() > ClaimState.STAGE_ADDING_SERVICES.ordinal() + 1)
		{
			if(document.getCardInfos().get(0).getMBCContractType() != null)
				frm.setField(CreateSberbankForEveryDayClaimForm.MOBILE_BANK_TARIFF_FIELD , document.getCardInfos().get(0).getMBCContractType() == 0 ? MB_FULL : MB_ECONOM);
			else
			{
				SBNKDConfig sbnkdConfig = ConfigFactory.getConfig(SBNKDConfig.class);
				frm.setField(CreateSberbankForEveryDayClaimForm.MOBILE_BANK_TARIFF_FIELD, sbnkdConfig.getDefaultPackageMobileBank());
			}
		}


		if(document.getCardAcctAutoPayInfo() != 0)
		{
			frm.setField(NEED_AUTOPAYMENT_FIELD, ON_VALUE);
			frm.setField(CreateSberbankForEveryDayClaimForm.REFILL_ON_SUM_FIELD, document.getCardAcctAutoPayInfo());
			frm.setField(CreateSberbankForEveryDayClaimForm.MOBILE_OPERATOR_FIELD, document.getCardInfos().get(0).getMBCPhoneCode());
		}
		frm.setField(CreateSberbankForEveryDayClaimForm.AUTOPAYMENT_IS_AVAILABLE, document.getAutopaymentIsAvailable());
		frm.setField(CreateSberbankForEveryDayClaimForm.BALANCE_LESS_THAN_FIELD, document.getBalanceLessThan());
		frm.setField(CreateSberbankForEveryDayClaimForm.MIN_SUM_FIELD, document.getMinAutopaymentSum());
		frm.setField(CreateSberbankForEveryDayClaimForm.MAX_SUM_FIELD, document.getMaxAutopaymentSum());

		frm.setField(CreateSberbankForEveryDayClaimForm.TB_FIELD, document.getContractRegionId());
		frm.setField(CreateSberbankForEveryDayClaimForm.VSP_FIELD, document.getContractBranchId());
		frm.setField(CreateSberbankForEveryDayClaimForm.OSB_FIELD, document.getContractAgencyId());
		frm.setField(CreateSberbankForEveryDayClaimForm.CREDIT_CARD_OFFICE_FIELD, document.getContractCreditCardOffice());

		//!frm.isError() : �������, ����������� �� ��, ��� ������������ �������� ������ ��� ���������� ������
		if(!frm.isError())
		{
			//frm.getStageNumber() == ClaimState.values().length : 5 ��� ��������� �� ��, ��� ������������ ������� �������� ��� ���� ����� � ����� ������������ �����, ���� �������� � �������������� ������
			//frm.isPreparedForConfirm() : �������, ������� ����������, ������ �� ������ � ������������� (��� ���� ��������� �����), � ����� �� ���������� ������� ����������
			//document.getStageNumber() == ClaimState.values().length + 1 : 6 ��� � ����������� ������ ��������� �� ��, ��� ������������ �������� � �������������� ������ �� �������� ���������, ��� ����� �� ���������� � ���������
			if(frm.getStageNumber() == ClaimState.values().length && !frm.isPreparedForConfirm() && document.getStageNumber() == ClaimState.values().length + 1 && frm.getEditStageNumber() == ClaimState.STAGE_CARD_SELECT.ordinal() + 1)
			{
				frm.setPreparedForConfirm(true);
			}
			else
			{
				//��������� � ��������������
				if(frm.getEditStageNumber() != ClaimState.STAGE_CARD_SELECT.ordinal() + 1 && frm.getStageNumber() >= ClaimState.values().length)
				{
					frm.setStageNumber(ClaimState.values().length);
				}
				else
				{
					frm.setStageNumber(document.getStageNumber());
				}
			}
		}

		frm.setCardCount(document.getCardInfos().size());

	}

	/**
	 * �������� ������
	 */
	private void updateRegionData(EditFormBase frm, HttpServletRequest request, IssueCardDocumentImpl document) throws BusinessException
	{
		Long regionId = document.getPersonRegionId();
		if (regionId == null)
		{
			List<String> allowedTb = ConfigFactory.getConfig(SBNKDConfig.class).getAllowedTB();
			if (!allowedTb.isEmpty())
			{
				List<Region> regions = regionService.findByCodeTB(allowedTb.get(0));
				if (!regions.isEmpty())
				{
					Region region = regions.get(0);
					frm.setField(CreateSberbankForEveryDayClaimForm.REGION_ID_FIELD, region.getId());
					frm.setField(CreateSberbankForEveryDayClaimForm.REGION_NAME_FIELD, region.getName());
					frm.setField(CreateSberbankForEveryDayClaimForm.PERSON_TB_FIELD, region.getCodeTB());
				}
				else
				{
					throw new BusinessException("�� ��������� �������� ������ ��������� ��. �� ������� ����� ������ ��� ������� �� �� ������");
				}
			}
			else
			{
				saveError(request, NOT_ALLOWED_TB_ERROR_MESSAGE);
			}
		}
		else
		{
			frm.setField(CreateSberbankForEveryDayClaimForm.REGION_ID_FIELD, regionId);
			frm.setField(CreateSberbankForEveryDayClaimForm.REGION_NAME_FIELD, regionService.findByIdFromCache(regionId).getName());
			frm.setField(CreateSberbankForEveryDayClaimForm.PERSON_TB_FIELD, document.getTb());
			if (!(new AllowedTbForSBNKDValidator().validate(document.getTb())))
				saveError(request, TB_NOT_ALLOWED_ERROR_MESSAGE);
		}
	}

	private void updateFormSigns(CreateSberbankForEveryDayClaimForm frm)
	{
		if(frm.getStageNumber() > ClaimState.STAGE_CARD_SELECT.ordinal() + 1)
		{
			frm.setRFCitizen(StringHelper.isNotEmpty((String)frm.getField(RF_CITIZEN_FIELD)));
			frm.setLivingByRegistrationPlace(StringHelper.isNotEmpty((String)frm.getField(LIVING_BY_REGISTRATION_PLACE_FIELD)));
		}

		frm.setNeedAutopayment(frm.getStageNumber() <= ClaimState.STAGE_ADDING_SERVICES.ordinal() || StringHelper.isNotEmpty((String)frm.getField(NEED_AUTOPAYMENT_FIELD)));

	}

	public ActionForward editStages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;
		frm.setPreparedForConfirm(false);
		frm.setFromHistory(false);
		updateFormSigns(frm);
		return start(mapping, form, request, response);
	}

	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimOperation operation = createOperation(CreateSberbankForEveryDayClaimOperation.class, "CreateSberbankForEveryDayClaimService");
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;
		operation.initialize(frm.getId());
		IssueCardDocumentImpl document = operation.getEntity();
		frm.setId(document.getId());
		//����������� ������� ������ �� ���� �������������� ������
		if(frm.getStageNumber() == ClaimState.values().length + 1)
		{
			document = operation.getDocumentById();
			SBNKDConfig config = ConfigFactory.getConfig(SBNKDConfig.class);

			if (config.isInformClientAboutSBNKDStatus())
			{
				if (document.getStatus() == IssueCardStatus.ERROR )
					saveMessage(request, config.getTextMessageErrorExternalSystem());
				else if (document.getStatus() != IssueCardStatus.EXECUTED)
					saveMessage(request, config.getTextMessageWaitAnswerExternalSystem());
			}
			else
			{
				frm.setSuccessConfirmMessage(config.getTextMessageStartProcessingExternalSystem());
			}
		}
		frm.setIssueCardDoc(document);

		updateRegionData(frm, request, document);
		maskFields(frm, document);
		return mapping.findForward(FORWARD_SHOW);
	}

	private void removeCardFields(CreateSberbankForEveryDayClaimForm frm, int number)
	{
		for(int i = number; i < frm.getCardCount() - 1; i++)
		{
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_NAME_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_NAME_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_NUMBER_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_NUMBER_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CURRENCY_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CURRENCY_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_SHORT_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_SHORT_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_BIOAPPLET_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_BIOAPPLET_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_PINPACK_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_PINPACK_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_BONUS_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_BONUS_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CREDIT_LIMIT_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CREDIT_LIMIT_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CLIENT_CATEGORY_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_CLIENT_CATEGORY_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_SELECT + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_FIELD + (i + 1)));
			frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_FIELD + i, frm.getField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_FIELD + (i + 1)));
		}
		//cardCount ���������� �� 1 ������, � ������ �� ����� �������� ������� ��,
		// �������, ���� ����� ����������� ����� ��������, ����� �������� ������ �������� ������ ��� ��������� ��������
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_NAME_FIELD + (frm.getCardCount() - 1), "VISA Classic");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_NUMBER_FIELD + (frm.getCardCount() - 1), "111705");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CURRENCY_FIELD + (frm.getCardCount() - 1), "RUB");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_SHORT_FIELD + (frm.getCardCount() - 1), "PRRC--");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CODEWAY_FIELD + (frm.getCardCount() - 1), "PRRCBVCP--");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_BIOAPPLET_FIELD + (frm.getCardCount() - 1), "false");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_PINPACK_FIELD + (frm.getCardCount() - 1), "1");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_BONUS_FIELD + (frm.getCardCount() - 1), "PG");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CREDIT_LIMIT_FIELD + (frm.getCardCount() - 1), "");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_CLIENT_CATEGORY_FIELD + (frm.getCardCount() - 1), "100");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_SELECT + (frm.getCardCount() - 1), "RUR Visa Classic ��������");
		frm.setField(CreateSberbankForEveryDayClaimForm.CARD_TYPE_FIELD + (frm.getCardCount() - 1), "RUR Visa Classic ��������");
	}

	public ActionForward removeCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;
		CreateSberbankForEveryDayClaimOperation operation = createOperation(CreateSberbankForEveryDayClaimOperation.class, "CreateSberbankForEveryDayClaimService");
		operation.initialize(frm.getId());
		unmaskFields(frm, operation.getEntity());
		updateCardEntity(frm, operation.getEntity());
		frm.setGuest(operation.getEntity().isGuest());
		updateFormSigns(frm);
		operation.removeCard(operation.getEntity().getCardInfos().get(frm.getRemoveCardNumber()));
		operation.getEntity().getCardInfos().remove(frm.getRemoveCardNumber());
		removeCardFields(frm, frm.getRemoveCardNumber());
		frm.setCardCount(frm.getCardCount() - 1);
		operation.getEntity().setCardCount(frm.getCardCount());
		operation.save();
		frm.setFromHistory(false);
		return start(mapping, form, request, response);
	}

	public ActionForward backToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateSberbankForEveryDayClaimForm frm = (CreateSberbankForEveryDayClaimForm) form;
		CreateSberbankForEveryDayClaimOperation operation = createOperation(CreateSberbankForEveryDayClaimOperation.class, "CreateSberbankForEveryDayClaimService");
		operation.initialize(frm.getId());
		frm.setEditStageNumber(ClaimState.STAGE_PERSONAL_INFO.ordinal() + 1);
		frm.setFromHistory(false);
		unmaskFields(frm, operation.getEntity());
		return start(mapping, form, request, response);
	}

	/**
	 * ��������� ������ ���� ���� � ������ (�������� ����������)
	 * @param frm - ������� �����
	 * @param claim - ������ (��������)
	 * @throws ParseException
	 */
	private void fillDocFields(CreateSberbankForEveryDayClaimForm frm, IssueCardDocumentImpl claim) throws ParseException
	{
		ClientDocumentType doc = ClientDocumentType.valueOf(frm.getField(CreateSberbankForEveryDayClaimForm.DOC_TYPE_FIELD).toString());

		claim.setIdentityCardType(PassportTypeWrapper.getPassportType(PassportTypeWrapper.getClientDocumentType(
				PassportTypeWrapper.getPassportType(ClientDocumentType.valueOf(frm.getField(CreateSberbankForEveryDayClaimForm.DOC_TYPE_FIELD).toString())))));
		if(frm.getField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD).toString().contains(" "))
		{
			claim.setIdentityCardSeries(frm.getField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD).toString().substring(0, frm.getField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD).toString().lastIndexOf(" ")));
			claim.setIdentityCardNumber(frm.getField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD).toString().substring(frm.getField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD).toString().lastIndexOf(" ") + 1, frm.getField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD).toString().length()));
		}
		else
		{
			claim.setIdentityCardNumber(frm.getField(CreateSberbankForEveryDayClaimForm.SERIES_AND_NUMBER_FIELD).toString());
			claim.setIdentityCardSeries("");
		}
		claim.setIdentityCardIssueDate(DateHelper.toCalendar(DateHelper.parseStringTime(frm.getField(CreateSberbankForEveryDayClaimForm.ISSUE_DATE_FIELD).toString() + " " + DateHelper.BEGIN_DAY_TIME)));
		if(doc.equals(ClientDocumentType.TEMPORARY_PERMIT) || doc.equals(ClientDocumentType.RESIDENTIAL_PERMIT_RF))
		{
			claim.setIdentityCardExpDate(DateHelper.toCalendar(DateHelper.parseStringTime(frm.getField(CreateSberbankForEveryDayClaimForm.EXPIRE_DATE_FIELD).toString() + " " + DateHelper.BEGIN_DAY_TIME)));
		}
		else
		{
			claim.setIdentityCardExpDate(null);
		}

		if(!doc.equals(ClientDocumentType.FOREIGN_PASSPORT))
		{
			claim.setIdentityCardIssuedBy(frm.getField(CreateSberbankForEveryDayClaimForm.ISSUED_BY_FIELD).toString());
		}
		else
		{
			claim.setIdentityCardIssuedBy(null);
		}

		if(doc.equals(ClientDocumentType.REGULAR_PASSPORT_RF))
		{
			claim.setIdentityCardIssuedCode(frm.getField(CreateSberbankForEveryDayClaimForm.OFFICE_CODE_FIELD).toString());
		}
		else
		{
			claim.setIdentityCardIssuedCode(null);
		}
	}

	protected CreateSberbankForEveryDayClaimOperation getOperation(HttpServletRequest request) throws NoActiveOperationException
	{
		CreateSberbankForEveryDayClaimOperation operation = super.getOperation(request);
		OperationContextUtil.synchronizeObjectAndOperationContext(operation.getEntity());

		return operation;
	}

	/**
	 * �������� ������ ��� ������� �������
	 * @param inputStr
	 * @return ������ ��� ������� �������
	 */
	protected String getStringWithoutDoubleQuotes(String inputStr)
	{
		return inputStr.replace("\"", "");
	}

	/**
	 * ����� ���������� � ����� ��� ���������� �������� �� �������.
	 * ��������� ���� ����� ���� �����������, ����� �� ���������� ������ �������� �� ��������
	 * @param form - ����� ������ �����
	 * @param doc - ������ �����
	 */
	private void unmaskFields(CreateSberbankForEveryDayClaimForm form, IssueCardDocumentImpl doc)
	{
		//�������
		if(StringHelper.isNotEmpty(doc.getPersonLastName()) && form.getField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD) != null && StringHelper.isNotEmpty(form.getField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD).toString()))
		{
			if((doc.getPersonLastName().substring(0,1) + ".").equals(form.getField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD).toString()))
			{
				form.setField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD, doc.getPersonLastName());
			}
		}

		if(doc.isGuest())
		{
			//�����_(���, ��� ����� ������)_1 (����������)
			if(doc.getAddress().length > 0 && doc.getAddress()[0] != null &&
					form.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_STREET_FIELD) != null && StringHelper.isNotEmpty(form.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_STREET_FIELD).toString()))
			{
				//�����
				String resStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, doc.getAddressAfterCity0());
				//���
				String resBuilding = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, doc.getAddressAfterCity0());
				//������
				String resCorps = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, doc.getAddressAfterCity0());
				//��������
				String resRoom = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, doc.getAddressAfterCity0());

				//����������� ��������������� �������� ������ ���� �������� �� ���� ��������
				if(form.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_STREET_FIELD).equals(MaskUtil.maskString(resStreet, 0,
						resStreet.length() >= STREET_MASK_CHARS_NUMBER ? resStreet.length() - STREET_MASK_CHARS_NUMBER : 0,
						CHAR_FOR_MASK)))
				{
					form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_STREET_FIELD, resStreet);
				}

				String resBuildingForm = (String)form.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_BUILDING_FIELD);
				String resCorpsForm = (String)form.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_CORPS_FIELD);
				String resRoomForm = (String)form.getField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_ROOM_FIELD);

				if(resBuildingForm != null && resBuildingForm.equals(MaskUtil.maskString(resBuilding, 0, 0, CHAR_FOR_MASK)))
					form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_BUILDING_FIELD, resBuilding);
				if(resCorpsForm != null && resCorpsForm.equals(MaskUtil.maskString(resCorps, 0, 0, CHAR_FOR_MASK)))
					form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_CORPS_FIELD, resCorps);
				if(resRoomForm != null && resRoomForm.equals(MaskUtil.maskString(resRoom, 0, 0, CHAR_FOR_MASK)))
					form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_ROOM_FIELD, resRoom);
			}

			//�����_(���, ��� ����� ������)_2 (��������)
			if(doc.getAddress().length > 1 && doc.getAddress()[1] != null &&
					form.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_STREET_FIELD) != null && StringHelper.isNotEmpty(form.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_STREET_FIELD).toString()))
			{
				//�����
				String regStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, doc.getAddressAfterCity1());
				//���
				String regBuilding = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, doc.getAddressAfterCity1());
				//������
				String regCorps = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, doc.getAddressAfterCity1());
				//��������
				String regRoom = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, doc.getAddressAfterCity1());

				if(form.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_STREET_FIELD).equals(MaskUtil.maskString(regStreet, 0,
						regStreet.length() >= STREET_MASK_CHARS_NUMBER ? regStreet.length() - STREET_MASK_CHARS_NUMBER : 0,
						CHAR_FOR_MASK)))
				{
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_STREET_FIELD, SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, doc.getAddressAfterCity1()));
				}

				String regBuildingForm = (String)form.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_BUILDING_FIELD);
				String regCorpsForm = (String)form.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_CORPS_FIELD);
				String regRoomForm = (String)form.getField(CreateSberbankForEveryDayClaimForm.REGISTRATION_ROOM_FIELD);

				if(regBuildingForm != null && regBuildingForm.equals(MaskUtil.maskString(regBuilding, 0, 0, CHAR_FOR_MASK)))
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_BUILDING_FIELD, SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, doc.getAddressAfterCity1()));
				if(regCorpsForm != null && regCorpsForm.equals(MaskUtil.maskString(regCorps, 0, 0, CHAR_FOR_MASK)))
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_CORPS_FIELD, SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, doc.getAddressAfterCity1()));
				if(regRoomForm != null && regRoomForm.equals(MaskUtil.maskString(regRoom, 0, 0, CHAR_FOR_MASK)))
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_ROOM_FIELD, SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, doc.getAddressAfterCity1()));
			}
		}
	}

	/**
	 * ����������� ������ ���� ����� ����� ��������� ������������
	 * @param form - ����� ������ �����
	 * @param doc - ������ �����
	 */
	private void maskFields(CreateSberbankForEveryDayClaimForm form, IssueCardDocumentImpl doc)
	{
		//�������
		if(StringHelper.isNotEmpty(doc.getPersonLastName()) && form.getField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD) != null && StringHelper.isNotEmpty(form.getField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD).toString()))
			form.setField(CreateSberbankForEveryDayClaimForm.LAST_NAME_FIELD, doc.getPersonLastName().substring(0,1) + ".");

		//�����
		if(doc.isGuest())
		{
			//�����_(���, ��� ����� ������)_1 (����������)
			String resStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, doc.getAddressAfterCity0());
			String resBuilding = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, doc.getAddressAfterCity0());
			String resCorps = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, doc.getAddressAfterCity0());
			String resRoom = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, doc.getAddressAfterCity0());

			//����� - ����������� ������ 4 ��������
			if(StringHelper.isNotEmpty(resStreet))
			{
				resStreet = MaskUtil.maskString(resStreet, 0,
						resStreet.length() >= STREET_MASK_CHARS_NUMBER ? resStreet.length() - STREET_MASK_CHARS_NUMBER : 0,
						CHAR_FOR_MASK);
				form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_STREET_FIELD, resStreet);
			}

			//��� - ����������� ���������
			if(StringHelper.isNotEmpty(resBuilding))
			{
				resBuilding = MaskUtil.maskString(resBuilding, 0, 0, CHAR_FOR_MASK);
				form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_BUILDING_FIELD, resBuilding);
			}

			//������ - ����������� ���������
			if (StringHelper.isNotEmpty(resCorps))
			{
				resCorps = MaskUtil.maskString(resCorps, 0, 0, CHAR_FOR_MASK);
				form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_CORPS_FIELD, resCorps);
			}

			//�������� - ����������� ���������
			if(StringHelper.isNotEmpty(resRoom))
			{
				resRoom = MaskUtil.maskString(resRoom, 0, 0, CHAR_FOR_MASK);
				form.setField(CreateSberbankForEveryDayClaimForm.RESIDENTIAL_ROOM_FIELD, resRoom);
			}
			form.setMaskedAfterCityResAddr(SberbankForEveryDayHelper.getAfterCityAddress(resStreet, resBuilding, resCorps, resRoom));

			if(StringHelper.isNotEmpty(doc.getAddressAfterCity1()))
			{
				//�����_(���, ��� ����� ������)_2 (��������)
				String regStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, doc.getAddressAfterCity1());
				String regBuilding = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, doc.getAddressAfterCity1());
				String regCorps = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, doc.getAddressAfterCity1());
				String regRoom = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, doc.getAddressAfterCity1());

				//����� - ����������� ������ 4 ��������
				if(StringHelper.isNotEmpty(regStreet))
				{
					regStreet = MaskUtil.maskString(regStreet, 0,
							regStreet.length() >= STREET_MASK_CHARS_NUMBER ? regStreet.length() - STREET_MASK_CHARS_NUMBER : 0,
							CHAR_FOR_MASK);
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_STREET_FIELD, regStreet);
				}

				//��� - ����������� ���������
				if(StringHelper.isNotEmpty(regBuilding))
				{
					regBuilding = MaskUtil.maskString(regBuilding, 0, 0, CHAR_FOR_MASK);
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_BUILDING_FIELD, regBuilding);
				}

				//������ - ����������� ���������
				if (StringHelper.isNotEmpty(regCorps))
				{
					regCorps = MaskUtil.maskString(regCorps, 0, 0, CHAR_FOR_MASK);
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_CORPS_FIELD, regCorps);
				}

				//�������� - ����������� ���������
				if(StringHelper.isNotEmpty(regRoom))
				{
					regRoom = MaskUtil.maskString(regRoom, 0, 0, CHAR_FOR_MASK);
					form.setField(CreateSberbankForEveryDayClaimForm.REGISTRATION_ROOM_FIELD, regRoom);
				}
				form.setMaskedAfterCityRegAddr(SberbankForEveryDayHelper.getAfterCityAddress(regStreet, regBuilding, regCorps, regRoom));
			}
		}
	}
}
