package com.rssl.phizic.business.sms;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason;
import com.rssl.phizic.messaging.mail.messagemaking.sms.TemplateMessageBuilder;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ���������� ��������� ��� ��� ���� � �������� ������
 * @author Puzikov
 * @ created 17.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbBusinessTextMessageBuilder
{
	/**
	 * @param changedFields ��������� ������, ������� ����������
	 * @return ��� �� ��������� ���������� ������
	 */
	public TextMessage buildSmsChangedData(Collection<String> changedFields) throws IKFLMessagingException
	{
		String changedFieldsString = StringUtils.join(changedFields, ", ");
		return makeMessage(
				"com.rssl.iccs.ermb.sms.changedFields",
				Collections.<String, Object>singletonMap("changedFields", changedFieldsString),
				Collections.<String, Object>emptyMap()
		);
	}

	/**
	 * @param phoneNumber ����� ��������
	 * @param main ���� ��������/����������
	 * @return ��� �� ������� �������� �������
	 */
	public TextMessage buildSmsChangeMainPhone(String phoneNumber, boolean main) throws IKFLMessagingException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("phoneNumber", phoneNumber);
		paramMap.put("main", main);
		return makeMessage(
				"com.rssl.iccs.ermb.sms.changeMain",
				paramMap,
				paramMap
		);
	}

	/**
	 * @param udbo � ������������� ������� ���� ���
	 * @return �������������� ��� ��� ����������� ���������� �����
	 */
	public TextMessage buildSmsWelcome(boolean udbo) throws IKFLMessagingException
	{
		String messageKey = udbo
				? "com.rssl.iccs.ermb.sms.welcome.udbo"
				: "com.rssl.iccs.ermb.sms.welcome.notUdbo";
		return makeMessage(
				messageKey,
				Collections.<String, Object>emptyMap(),
				Collections.<String, Object>emptyMap()
		);
	}

	/**
	 * @return �������������� ���, ���� ������� ������� ��������
	 * @param available �������� �� ������� ������� �������
	 */
	public TextMessage buildSmsFastServices(boolean available) throws IKFLMessagingException
	{
		return makeMessage(
				"com.rssl.iccs.ermb.sms.quickServices",
				Collections.<String, Object>singletonMap("quickServiceStatus", available),
				Collections.<String, Object>emptyMap()
		);
	}

	/**
	 * @param disconnectedPhone ����������� �������
	 * @param activePhone ����� �������� �������
	 * @param reason ������� ���������� ��������
	 * @return ��������� �������� ��� ���������� �������� (�� ���������� ��������); �������� �� ����� - null
	 */
	public TextMessage buildDisconnectPhoneBroadcastMessage(String disconnectedPhone, String activePhone, PhoneDisconnectionReason reason) throws IKFLMessagingException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("disconnectedPhone", disconnectedPhone);
		paramMap.put("activePhone", activePhone);
		switch (reason)
		{
			case CHANGE_MSISDN:
			case OSS_DISCONNECT:
			case ABONENT_DISCONNECT:
			case PHIZ_TRANSFER_MSISDN:
			case JUR_TRANSFER_MSISDN:
				String messageKey = "DisconnectPhone." + reason.name();
				return makeMessage(messageKey, paramMap, Collections.<String, Object>emptyMap());
			case OTHER:
			default:
				return null;
		}
	}

	/**
	 * @return ��������� � ���������� ������� �� ��������
	 *
	 */
	public TextMessage buildNonPayedBlockMessage() throws IKFLMessagingException
	{
		return makeMessageWithoutParams("com.rssl.phizic.business.ermb.writedown.NonPayedBlockInfo");
	}

	/**
	 * @return ��������� � ������������� ������� �� ��������
	 */
	public TextMessage buildNonPayedUnblockMessage() throws IKFLMessagingException
	{
		return makeMessageWithoutParams("com.rssl.phizic.business.ermb.writedown.NonPayedUnblockInfo");
	}

	private TextMessage makeMessageWithoutParams(String key) throws IKFLMessagingException
	{
		return makeMessage(
				key,
				Collections.<String, Object>emptyMap(),
				Collections.<String, Object>emptyMap()
		);
	}

	private TextMessage makeMessage(String key, Map<String, Object> messageProperties, Map<String, Object> loggingProperties) throws IKFLMessagingException
	{

		ErmbBusinessMessageLoader loader = new ErmbBusinessMessageLoader();

		TextMessage message = TemplateMessageBuilder.build(
				loader,
				key,
				messageProperties,
				loggingProperties
		);

		Long priority = loader.getSmsResource().getPriority();

		return new TextMessage(message.getText(), message.getTextToLog(), priority);
	}
}
