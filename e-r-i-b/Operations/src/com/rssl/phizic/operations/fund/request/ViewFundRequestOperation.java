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
 * �������� ��� ��� ��������� ��������� ���������� �� ���������� �������
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
	 * ������������� �������� �� ID
	 * @param id ID ������� �� ���� �������
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
	 * ��������� �������� "���������� ������� �� ���� �������"
	 * @param id ID �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void takeFundRequest(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null || id == 0)
		{
			throw new IllegalArgumentException("������������ id");
		}

		fundRequest = fundRequestService.getById(id);

		if (fundRequest == null)
		{
			throw new BusinessException("�� ������ ������ �� ���� ������� � id = " + id);
		}
		if (!getRestriction().accept(fundRequest))
		{
			throw new BusinessException("������ �� ���� ������� � id = " + id + " �� ����������� �������");
		}
	}

	/**
	 * ��������� ������ ������� �� id �������� "��������� ������ �� ���� �������"
	 * @param id ID ���������� ������� �� ���� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void takeListResponse(Long id) throws BusinessException, BusinessLogicException
	{
		listResponse = fundResponseService.getByRequestId(id);
	}

	/**
	 * ��������� Map'� ��������� ����������� ������� �� loginId ���������� � ������� ����������� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void takeContactsMapAndSum(Long id) throws BusinessException, BusinessLogicException
	{		
		for (FundInitiatorResponse response : listResponse)
		{
			//������� ����� ����������� ����� �� ��������������� �������
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
	 * �������� �������� "��������� ������ �� ���� �������"
	 * @return ��������� ������ �� ���� �������
	 */
	public FundRequest getFundRequest()
	{
		return fundRequest;
	}

	/**
	 * �������� ������ ������������ �����
	 * @return ������ ������������ �����
	 */
	public List<FundInitiatorResponse> getListResponse()
	{
		return listResponse;
	}

	/**
	 * ��������� Map'�, ���������� �������� ������������ �����.
	 * @return Map'� ��������
	 */
	public Map<Long, Contact> getContactsMap()
	{
		return contactsMap;
	}

	/**
	 * ��������� Map'� �������� ��������, ������� ���� � �������� ����� ���������� �������, �� �������.
	 * @return Map'� �������� ��������
	 */
	public Map<String, String> getProfileAvatarMap()
	{
		return profileAvatarMap;
	}

	/**
	 * ��������� �������� �������� �� �������, ��� �������, ��� �� ������ �� ��������� �� ����� ������� "���������"
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
		//�������� ��������� ������� ���������
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
	 * @return ����� ��������� �����
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}
}
