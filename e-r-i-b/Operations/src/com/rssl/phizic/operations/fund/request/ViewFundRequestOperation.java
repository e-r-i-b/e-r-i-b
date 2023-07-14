package com.rssl.phizic.operations.fund.request;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponse;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponseService;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.business.fund.initiator.FundRequestService;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhone;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhonesService;
import com.rssl.phizic.business.operations.restrictions.FundRequestRestriction;
import com.rssl.phizic.business.profile.addressbook.AddedType;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.business.profile.images.AvatarJurnalService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author usachev
 * @ created 09.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция для для получения детальной информации по исходящему запросу
 */

public class ViewFundRequestOperation extends OperationBase<FundRequestRestriction>
{
	private static IncognitoPhonesService incognitoService = new IncognitoPhonesService();
	private static FundRequestService fundRequestService = new FundRequestService();
	private static FundInitiatorResponseService fundResponseService = new FundInitiatorResponseService();
	private static AddressBookService addressBookService = new AddressBookService();
	private static AvatarJurnalService avatarService = new AvatarJurnalService();

	private FundRequest fundRequest;
	private List<FundInitiatorResponse> listResponse;
	private Map<Long, Contact> contactsMap;
	private Map<String, String> profileAvatarMap = new HashMap<String, String>();
	private Set<String> bufOfPhoneNotFoundInAddressBook = new HashSet<String>();	
	private BigDecimal accumulatedSum = BigDecimal.ZERO;

	/**
	 * Инициализация операции по ID
	 * @param id ID запроса на сбор средств
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void init(Long id) throws BusinessException, BusinessLogicException
	{
		takeFundRequest(id);
		takeListResponse(id);
		contactsMap = new HashMap<Long, Contact>(listResponse.size());
		takeContactsMapAndSum(fundRequest.getLoginId());
		if (bufOfPhoneNotFoundInAddressBook.size() != 0)
		{
			takeProfilesAvatar();
		}
	}

	/**
	 * Получение сущности "исходящего запроса на сбор средств"
	 * @param id ID запроса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void takeFundRequest(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null || id == 0)
		{
			throw new IllegalArgumentException("Недопустимый id");
		}

		fundRequest = fundRequestService.getById(id);

		if (fundRequest == null)
		{
			throw new BusinessException("Не найден запрос на сбор средств с id = " + id);
		}
		if (!getRestriction().accept(fundRequest))
		{
			throw new BusinessException("Запрос на сбор средств с id = " + id + " не принадлежит клиенту");
		}
	}

	/**
	 * Получение списка ответов по id сущности "исходящий запрос на сбор средств"
	 * @param id ID исходящего запроса на сбор средств
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void takeListResponse(Long id) throws BusinessException, BusinessLogicException
	{
		listResponse = fundResponseService.getByRequestId(id);
	}

	/**
	 * Получение Map'ы контактов получателей запроса по loginId инициатора и подсчет накопленной суммы
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void takeContactsMapAndSum(Long id) throws BusinessException, BusinessLogicException
	{		
		for (FundInitiatorResponse response : listResponse)
		{
			//Считаем общую накопленную сумму по удовлетворенным ответам
			if (response.getState() == FundResponseState.SUCCESS && response.getSum() != null)
			{
				accumulatedSum = accumulatedSum.add(response.getSum());
			}
			String phonePayment = response.getPhone();
			Contact contact = addressBookService.findContactClientByPhone(id, response.getPhone());
			if (contact != null && contact.getAddedBy() == AddedType.MOBILE && contact.getStatus() == ContactStatus.ACTIVE)
			{
				contactsMap.put(response.getId(), contact);
			}
			else
			{
				bufOfPhoneNotFoundInAddressBook.add(phonePayment);
			}
		}
	}

	/**
	 * Получить сущности "исходящий запрос на сбор средств"
	 * @return Исходящий запрос на сбор средств
	 */
	public FundRequest getFundRequest()
	{
		return fundRequest;
	}

	/**
	 * Получить список отправителей денег
	 * @return Список отправителей денег
	 */
	public List<FundInitiatorResponse> getListResponse()
	{
		return listResponse;
	}

	/**
	 * Получение Map'ы, содержащей контакты отправителей денег.
	 * @return Map'а аватаров
	 */
	public Map<Long, Contact> getContactsMap()
	{
		return contactsMap;
	}

	/**
	 * Получение Map'ы аватаров клиентов, которых нету в адресной книги инициатора запроса, из профиля.
	 * @return Map'а аватаров клиентов
	 */
	public Map<String, String> getProfileAvatarMap()
	{
		return profileAvatarMap;
	}

	/**
	 * Получение аватаров клиентов из профиля, при условии, что ни клиент ни инициатор не имеют признак "Инкогнито"
	 * @throws BusinessException
	 */
	private void takeProfilesAvatar() throws BusinessException
	{
		boolean isInitiatorIncognito = PersonContext.getPersonDataProvider().getPersonData().isIncognito();
		if (isInitiatorIncognito)
		{
			return;
		}
		List<IncognitoPhone> list = incognitoService.findPhones(bufOfPhoneNotFoundInAddressBook);
		//Получаем множество видимых телефоном
		for (IncognitoPhone incognitoPhone : list)
		{
			bufOfPhoneNotFoundInAddressBook.remove(incognitoPhone.getPhoneNumber());
		}
		for (String phone : bufOfPhoneNotFoundInAddressBook)
		{
			String avatar = avatarService.getAvatar(phone);
			if (StringHelper.isNotEmpty(avatar))
			{
				profileAvatarMap.put(phone, avatar);
			}
		}
	}
	/**
	 * @return общая собранная сумма
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}
}
