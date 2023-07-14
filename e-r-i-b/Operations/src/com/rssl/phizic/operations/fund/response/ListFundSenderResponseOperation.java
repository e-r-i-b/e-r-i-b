package com.rssl.phizic.operations.fund.response;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhone;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhonesService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.addressbook.AddedType;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.business.profile.images.AvatarJurnalService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author usachev
 * @ created 12.12.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ��������� ������ �������� �������� �� ���� �������
 */

public class ListFundSenderResponseOperation extends OperationBase
{
	private static FundSenderResponseService senderResponseService = new FundSenderResponseService();
	private static AddressBookService addressBookService = new AddressBookService();
	private static AvatarJurnalService avatarService = new AvatarJurnalService();
	private static IncognitoPhonesService incognitoService = new IncognitoPhonesService();

	private List<FundSenderResponse> listResponse = new LinkedList<FundSenderResponse>();
	private Map<String, String> avatarMap;
	private PersonData personData;
	/**
	 * ������������� ���������� �� �������� �������� �� ���� �������
	 * @param params ��������� ��� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void init(Map<String, Object> params) throws BusinessException, BusinessLogicException
	{
		personData = PersonContext.getPersonDataProvider().getPersonData();
		listResponse = initListSenderResponse(DateHelper.toCalendar((Date)params.get("from_date")));
		avatarMap = initAvatarMap(listResponse);
	}

	/**
	 * ��������� ������ �������� �������� �� ���� �������
	 * @param fromDate ���� ������ �������
	 * @return ������ �������� �������� �� ���� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private List<FundSenderResponse> initListSenderResponse(Calendar fromDate) throws BusinessException, BusinessLogicException
	{
		Person person = personData.getPerson();
		List<UserInfo> listUserInfo = initUserInfo(person.getId());
		List<FundSenderResponse> result = new LinkedList<FundSenderResponse>();
		for (UserInfo info: listUserInfo)
		{
			List<FundSenderResponse> bufList = senderResponseService.getByUniversalIdAndDate(info.getSurname(), info.getFirstname(), info.getPatrname(), info.getBirthdate(), info.getTb(), info.getPassport(), fromDate);
			result.addAll(bufList);
		}
		return result;
	}

	/**
	 * ��������� Map'� �������� ����������� �������
	 * @param listResponse ������ �������� �������� �� ���� ������� ��� �������� ����� ��������� ������ �����������
	 * @return Map � ��������� �����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private Map<String, String> initAvatarMap(List<FundSenderResponse> listResponse)throws BusinessException, BusinessLogicException
	{
		//���� - externalId ��������� �������. �������� - ���� � �������
		Map<String, String> mapAvatars = new HashMap<String, String>();
		//����� ���������, ������� �� ������� � �������� �����. ���� - �������, �������� - ��������� externalID
		Map<String, Set<String>> bufOfPhoneNotFoundInAddressBook = new HashMap<String, Set<String>>();
		long userId = personData.getLogin().getId();
		for (FundSenderResponse response: listResponse)
		{
			String[] phones = response.getListInitiatorPhones();
			Contact contact = null;
			boolean setContact = false;
			for (String phone: phones)
			{
				contact = addressBookService.findContactClientByPhone(userId, phone);
				if (contact != null && contact.getAddedBy() == AddedType.MOBILE && contact.getStatus() == ContactStatus.ACTIVE && StringHelper.isNotEmpty(contact.getAvatarPath()))
				{
					mapAvatars.put(response.getExternalId(), contact.getAvatarPath());
					setContact = true;
					break;
				}
			}
			//�� ����� �������� � �������� �����, ���������� ��� ��� ������������ ������ ������� � �������
			if (!setContact){
				String phone = response.getListInitiatorPhones()[0];
				if (bufOfPhoneNotFoundInAddressBook.containsKey(phone)){
					Set<String> set = bufOfPhoneNotFoundInAddressBook.get(phone);
					set.add(response.getExternalId());
				}else{
					Set<String> set = new HashSet<String>();
					set.add(response.getExternalId());
					bufOfPhoneNotFoundInAddressBook.put(phone,set);
				}

			}
		}
		if (!(bufOfPhoneNotFoundInAddressBook.isEmpty() || personData.isIncognito())){
			Map<String, String> mapOfProfileAvatar = getProfilesAvatars(bufOfPhoneNotFoundInAddressBook);
			mapAvatars.putAll(mapOfProfileAvatar);
		}
		return mapAvatars;
	}

	/**
	 * ��������� ������ �������� �������� �� ���� �������
	 * @return ������ �������� �������� �� ���� �������
	 */
	public List<FundSenderResponse> getData()
	{
		return listResponse;
	}

	/**
	 * ��������� Map'� �������� ����������� �������
	 * @return
	 */
	public Map<String, String> getAvatarMap()
	{
		return avatarMap;
	}

	/**
	 * ��������� ������� ��������� ������ ������� �� ID ������������
	 * @param personId ID ������������, ��� �������� ������� ���������
	 * @return ������ ��������� ��� ������� ������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private List<UserInfo> initUserInfo(Long personId)throws BusinessException, BusinessLogicException
	{
		try
		{
			PersonService personService = new PersonService();
			UserInfo userInfo = PersonHelper.buildUserInfo(personService.findById(personId));
			Document document = CSABackRequestHelper.sendGetProfileHistoryInfo(userInfo);
			List<UserInfo> userInfos = CSABackResponseSerializer.getHistoryUserInfoList(document);
			if (userInfos.size() == 0)
			{
				throw new BusinessException("������� ������� �� ����� ���� ������");
			}
			return userInfos;
		}catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}catch (BackException e)
		{
			throw new BusinessException(e);
		}catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������� �������� �� �������
	 * @param mapPhones  ���'� ���������, �� ������� ����� ������. (���� - �������)
	 * @return ���'�. ���� - externalId ��������� �������. �������� - ���� � �������
	 * @throws BusinessException
	 */
    private Map<String, String> getProfilesAvatars(Map<String, Set<String>> mapPhones) throws BusinessException
    {
	    //���� - externalId ��������� �������. �������� - ���� � �������
	    Map<String, String> profileAvatarMap = new HashMap<String, String>(mapPhones.size());

	    //�������� ������ ���������-���������
	    List<IncognitoPhone> list = incognitoService.findPhones(mapPhones.keySet());

	    for (IncognitoPhone phone: list){
		     mapPhones.remove(phone.getPhoneNumber());
	    }

	    for (String phone : mapPhones.keySet())
	    {
		    String avatar = avatarService.getAvatar(phone);
		    if (StringHelper.isNotEmpty(avatar))
		    {
			    for (String externalId: mapPhones.get(phone)){
				    profileAvatarMap.put(externalId, avatar);
			    }
		    }
	    }
	    return profileAvatarMap;
	}
}
