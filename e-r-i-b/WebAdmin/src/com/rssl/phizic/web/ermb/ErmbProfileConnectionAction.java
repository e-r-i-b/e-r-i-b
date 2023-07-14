package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ermb.person.ErmbProfileActivateOperation;
import com.rssl.phizic.operations.ermb.person.ErmbProfileOperation;
import com.rssl.phizic.operations.ermb.person.ErmbProfileOperationEntity;
import com.rssl.phizic.utils.PhoneEncodeUtils;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.persons.PersonUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.sql.Time;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: moshenko
 * Date: 08.10.2012
 * Time: 11:37:53
 * Подключение/редактирование профиля ЕРМБ
 */
public class ErmbProfileConnectionAction extends EditActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.connect", "connect");
		map.put("button.ermb.activate", "activate");
		return map;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ErmbProfileOperation operation = createOperation(ErmbProfileOperation.class);
		ErmbProfileConnectionForm form = (ErmbProfileConnectionForm) frm;
		Long personId = PersonUtils.getPersonId(currentRequest());
		operation.initialize(personId, form.getConnect());
		return  operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessException, BusinessLogicException
	{
		ErmbProfileConnectionForm frm = (ErmbProfileConnectionForm) editForm;
		ErmbProfileOperation operation =(ErmbProfileOperation) editOperation;
		ErmbProfileOperationEntity entity = operation.getEntity();
		ErmbProfileImpl profile = entity.getProfile();
		if (frm.getProfileOldVersion() != null && !frm.getProfileOldVersion().equals(profile.getProfileVersion()))
			throw new BusinessLogicException("Сохранение изменений невозможно. За время редактирования профиль изменился извне.");
		profile.setNotificationStartTime(Time.valueOf(frm.getNtfStartTimeString() + ":00"));
		profile.setNotificationEndTime(Time.valueOf(frm.getNtfEndTimeString() + ":00"));
		String[] nfrDays = frm.getNtfDays();
		if (nfrDays!=null)
			profile.setDaysOfWeek(new DaysOfWeek(nfrDays,false));
		else
			profile.setDaysOfWeek(new DaysOfWeek());
		profile.setTimeZone(frm.getTimeZone());
		profile.setFastServiceAvailable(frm.isFastServiceAvailable());
		profile.setDepositsTransfer(frm.isDepositsTransferAvailable());
		profile.setSuppressAdv(!frm.isPromotionalMailingAvailable());
		entity.setSelectedTarif(frm.getSelectedTarif());
		entity.setCardsNotify(getProductIdsArrayAsList(frm.getCardsNtf()));
		entity.setAccountsNotify(getProductIdsArrayAsList(frm.getAccountsNtf()));
		entity.setLoansNotify(getProductIdsArrayAsList(frm.getLoansNtf()));
		entity.setCardsShowInSms(getProductIdsArrayAsList(frm.getCardsShowInSms()));
		entity.setAccountsShowInSms(getProductIdsArrayAsList(frm.getAccountsShowInSms()));
		entity.setLoansShowInSms(getProductIdsArrayAsList(frm.getLoansShowInSms()));
		entity.setCardsAvailableInSmsBank(getProductIdsArrayAsList(frm.getCardsAvailableInSmsBank()));
		entity.setAccountsAvailableInSmsBank(getProductIdsArrayAsList(frm.getAccountsAvailableInSmsBank()));
		entity.setLoansAvailableInSmsBank(getProductIdsArrayAsList(frm.getLoansAvailableInSmsBank()));

		boolean productAvailabilityCommonAttribute = ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute();
		if (productAvailabilityCommonAttribute)
			profile.setNewProductShowInSms(frm.isNewProductsAvailableInSmsBank());
		else
		{
			profile.setNewProductShowInSms(frm.isNewProductsShowInSms());
			profile.setNewProductNotification(frm.isNewProductsNtf());
		}
		entity.setMainCardId(frm.getMainCardId());

		Set<String> initialPhoneNumbers = entity.getPhonesNumber();
		if (StringHelper.isNotEmpty(frm.getMainPhoneNumberCode()))
			entity.setMainPhoneNumber(PhoneEncodeUtils.decodePhoneNumber(initialPhoneNumbers, frm.getMainPhoneNumberCode(), true));
		else
			entity.setMainPhoneNumber(null);

		Set<String> phoneCodes = frm.getCodesToPhoneNumber().keySet();
		entity.setPhonesNumber(PhoneEncodeUtils.decodePhoneNumbers(phoneCodes, initialPhoneNumbers, true));

		entity.setPhonesToDelete(PhoneEncodeUtils.decodePhoneNumbers(frm.getCodePhonesToDelete().keySet(), initialPhoneNumbers, true));
		entity.setMbkPhones(new HashSet<String>(Arrays.asList(frm.getMbkPhones())));
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		//Do nothing
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		ErmbProfileConnectionForm form = (ErmbProfileConnectionForm) frm;
		ErmbProfileOperationEntity ermbProfileOperationEntity = (ErmbProfileOperationEntity) entity;
		ErmbProfileImpl profile = ermbProfileOperationEntity.getProfile();
		form.setFastServiceAvailable(profile.getFastServiceAvailable());
		form.setDepositsTransferAvailable(profile.getDepositsTransfer());
		form.setPromotionalMailingAvailable(!profile.isSuppressAdv());
		form.setProfile(profile);
		form.setProfileOldVersion(profile.getProfileVersion());
		form.setTarifs(ermbProfileOperationEntity.getTarifs());
		CardLink foreginProduct = profile.getForeginProduct();
		if (foreginProduct!=null)
			form.setMainCardId(String.valueOf(foreginProduct.getId()));
		Time endTime = profile.getNotificationEndTime();
		if (endTime!=null)
			form.setNtfEndTimeString(String.format("%1$tH:%1$tM", endTime));
		Time startTime = profile.getNotificationStartTime();
		if (startTime!=null)
			form.setNtfStartTimeString(String.format("%1$tH:%1$tM", startTime));
		form.setTimeZone(profile.getTimeZone());
		form.setTimeZoneList(TimeZone.getTimeZoneList(profile.getTimeZone()));
		DaysOfWeek daysOfWeek = profile.getDaysOfWeek();
		if (daysOfWeek!=null)
			form.setNtfDays(daysOfWeek.getStringDays());
		form.setCardsNtf(getProductIdsListAsArray(ermbProfileOperationEntity.getCardsNotify()));
		form.setAccountsNtf(getProductIdsListAsArray(ermbProfileOperationEntity.getAccountsNotify()));
		form.setLoansNtf(getProductIdsListAsArray(ermbProfileOperationEntity.getLoansNotify()));
		form.setCardsShowInSms(getProductIdsListAsArray(ermbProfileOperationEntity.getCardsShowInSms()));
		form.setAccountsShowInSms(getProductIdsListAsArray(ermbProfileOperationEntity.getAccountsShowInSms()));
		form.setLoansShowInSms(getProductIdsListAsArray(ermbProfileOperationEntity.getLoansShowInSms()));
		form.setCardsAvailableInSmsBank(getProductIdsListAsArray(ermbProfileOperationEntity.getCardsAvailableInSmsBank()));
		form.setAccountsAvailableInSmsBank(getProductIdsListAsArray(ermbProfileOperationEntity.getAccountsAvailableInSmsBank()));
		form.setLoansAvailableInSmsBank(getProductIdsListAsArray(ermbProfileOperationEntity.getLoansAvailableInSmsBank()));
		form.setSelectedTarif(ermbProfileOperationEntity.getSelectedTarif());
		form.setNewProductsAvailableInSmsBank(profile.getNewProductShowInSms());
		form.setNewProductsShowInSms(profile.getNewProductShowInSms());
		form.setNewProductsNtf(profile.getNewProductNotification());

		Set<String> phoneNumbers = ermbProfileOperationEntity.getPhonesNumber();
		String mainPhone = ermbProfileOperationEntity.getMainPhoneNumber();
		if (!StringHelper.isEmpty(mainPhone))
			form.setMainPhoneNumberCode(PhoneEncodeUtils.encodePhoneNumber(phoneNumbers, mainPhone));
		form.setCodesToPhoneNumber(PhoneEncodeUtils.encodePhoneNumbers(phoneNumbers));

		form.setActivePerson((ActivePerson) ermbProfileOperationEntity.getProfile().getPerson());
		form.setLockDescription(profile.getLockDescription());
		form.setLastRequestTime(ermbProfileOperationEntity.getLastRequestTime());
		form.setLastSmsRequestTime(ermbProfileOperationEntity.getLastSmsRequestTime());
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.save(mapping, form, request, response);
		return start(mapping, form, request, response);
	}

	private List<String> getProductIdsArrayAsList(String[] productIds)
	{
		if (productIds != null)
			return Arrays.asList(productIds);
		return Collections.emptyList();
	}

	private String[] getProductIdsListAsArray(List<String> productIds)
	{
		if (productIds != null)
			return productIds.toArray(new String[productIds.size()]);
		return null;
	}

	public ActionForward connect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbProfileConnectionForm frm = (ErmbProfileConnectionForm) form;
		frm.setConnect(true);
		super.save(mapping, form, request, response);
		return start(mapping, form, request, response);
	}

	/**
	 * Активировать профиль в ЦОД/Way
	 * @param mapping  стратс-маппинг
	 * @param form     стратс-форма
	 * @param request  запрос
	 * @param response ответ
	 * @return форвард на успешное выполнение операции
	 */
	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ErmbProfileActivateOperation operation = createOperation(ErmbProfileActivateOperation.class);
			operation.initialize(PersonUtils.getPersonId(currentRequest()));
			operation.activate();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
		}

		return start(mapping, form, request, response);
	}
}
