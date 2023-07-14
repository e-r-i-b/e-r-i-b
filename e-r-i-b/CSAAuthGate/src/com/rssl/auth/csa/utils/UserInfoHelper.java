package com.rssl.auth.csa.utils;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ��������� ���������� � �������
 */
public class UserInfoHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * �������� ���������� � ������������ �� ������ ��������.
	 *
	 * !!! ���� ����� ���������� �� � ����, �� ���������� �������� ��� �� !!!
	 *
	 * @param phone ����� ��������
	 * @return ���������� � ������������
	 */
	public static UserInfo getByPhoneWithoutTb(String phone)
	{
		if (StringHelper.isEmpty(phone))
		{
			throw new IllegalArgumentException("����� �������� �� ����� ���� null.");
		}

		//�� ������ �������� ���������� ����� ������� �� ���
		String cardNumber = getCardNumber(phone);
		if (StringHelper.isEmpty(cardNumber))
		{
			//���� ������ �� �����, ������� ����� ����
			return getUserInfoInERMB(phone);
		}

		return getUserInfoByCard(cardNumber);
	}

	private static UserInfo getUserInfoInERMB(String phone)
	{
		try
		{
			Document document = CSABackRequestHelper.sendGetUserInfoByPhoneRq(phone, false);
			return CSABackResponseSerializer.getUserInfo(document);
		}
		catch (BackException e)
		{
			log.error("������ ��� ��������� ���������� � ���� � ������������ �� �������� " + phone, e);
		}
		catch (BackLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (TransformerException e)
		{
			log.error("������ ��� ������� ���������� � ������������, ���������� �� ���� �� �������� " + phone, e);
		}
		catch (Exception e)
		{
			log.error("������ ��� ��������� ���������� � ���� � ������������ �� �������� " + phone, e);
		}
		return null;
	}

	private static UserInfo getUserInfoByCard(String cardNumber)
	{
		try
		{
			//������ � WAY4U (����� ���) ��� ��������� ���+���+��+�� �������
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = GroupResultHelper.getOneResult(bankrollService.getOwnerInfoByCardNumber(new Pair<String, Office>(cardNumber, null)));

			if (client != null)
			{
				//���� ���������� ������, ������ UserInfo
				UserInfo userInfo = new UserInfo();

				userInfo.setFirstname(client.getFirstName());
				userInfo.setSurname(client.getSurName());
				userInfo.setPatrname(client.getPatrName());
				userInfo.setBirthdate(client.getBirthDay());
				userInfo.setPassport(getClientPassport(client));

				return userInfo;
			}
		}
		catch (Exception e)
		{
			log.error("������ ��� ��������� ���������� � ������������ �� ������ ����� " + cardNumber, e);
		}
		return null;
	}

	private static String getCardNumber(String phoneNumber)
	{
		try
		{
			//�������� ����� ����� �� ������ �������� ���������� �������
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			return mobileBankService.getCardNumberByPhone(null, phoneNumber);
		}
		catch (Exception e)
		{
			log.error("������ ��� ��������� ������ ����� �� �������� " + phoneNumber, e);
			return StringUtils.EMPTY;
		}
	}

	private static String getClientPassport(Client client)
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		ClientDocument passport = null;

		for (ClientDocument document : documents)
		{
			if (document.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
				return StringHelper.getEmptyIfNull(document.getDocSeries()) + StringHelper.getEmptyIfNull(document.getDocNumber());

			if(document.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
				passport = document;
		}

		if (passport != null)
		{
			return getPassportWayNumber(passport.getDocSeries(), passport.getDocNumber());
		}
		//����� ����� ������ ����������
		return getPassportWayNumber(documents.get(0).getDocSeries(), documents.get(0).getDocNumber());
	}

	private static String getPassportWayNumber(String docSeries, String docNumber)
	{
		String series = StringHelper.getEmptyIfNull(docSeries);
		String number = StringHelper.getEmptyIfNull(docNumber);
		String delimiter = StringUtils.isNotEmpty(series) && StringUtils.isNotEmpty(number) ? " " : "";

		return series + delimiter + number;
	}
}
