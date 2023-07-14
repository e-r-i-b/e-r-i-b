package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.StringHelper;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 18.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentTemplateUpdate implements Serializable, ConfirmableObject
{
	private static final OutputFormat XML_SERIALIZE_FORMAT
			= new OutputFormat(Method.XML, "UTF-8", false);

	private static final PersonService             personService     = new PersonService();
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private Long id;

	private CommonLogin login;

	private String cardNumber;

	private String phoneNumber;

	private String destlist;

	private Integer type;

	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getDestlist()
	{
		return destlist;
	}

	public void setDestlist(String destlist)
	{
		this.destlist = destlist;
	}

	// never null
	public UpdateType getType()
	{
		return UpdateType.forCode(type);
	}

	public void setType(UpdateType type)
	{
		this.type = type.getCode();
	}

	public byte[] getSignableObject()
	{
		if (id == null)
			throw new IllegalStateException("ConfirmableObject is not ready to be signed");
		if (login == null)
			throw new IllegalStateException("ConfirmableObject is not ready to be signed");
		if (StringHelper.isEmpty(cardNumber))
			throw new IllegalStateException("ConfirmableObject is not ready to be signed");
		if (StringHelper.isEmpty(phoneNumber))
			throw new IllegalStateException("ConfirmableObject is not ready to be signed");
		if (StringHelper.isEmpty(destlist))
			throw new IllegalStateException("ConfirmableObject is not ready to be signed");

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			String sid = id.toString();

			String fullname = personService.findByLogin((Login)getLogin()).getFullName();

			XMLSerializer serializer = new XMLSerializer(stream, XML_SERIALIZE_FORMAT);

			serializer.startDocument();
			serializer.startElement("signable-document", null);

			serializer.startElement("id", null);
			serializer.characters(sid.toCharArray(), 0, sid.length());
			serializer.endElement("id");

			serializer.startElement("fullname", null);
			serializer.characters(fullname.toCharArray(), 0, fullname.length());
			serializer.endElement("fullname");

			serializer.startElement("card-number", null);
			serializer.characters(cardNumber.toCharArray(), 0, cardNumber.length());
			serializer.endElement("card-number");

			serializer.startElement("phone-number", null);
			serializer.characters(phoneNumber.toCharArray(), 0, phoneNumber.length());
			serializer.endElement("phone-number");

			serializer.startElement("destlist", null);
			serializer.characters(destlist.toCharArray(), 0, destlist.length());
			serializer.endElement("destlist");

			serializer.endElement("signable-document");
			serializer.endDocument();

			return stream.toByteArray();

		}
		catch (SAXException ex)
		{
			throw new SecurityException(ex);
		}
		catch (BusinessException ex)
		{
			throw new SecurityException(ex);
		}
		finally
		{
			try	{ stream.close(); } catch (IOException ignored) {}
		}
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		
	}

	/**
	 * Собирает данные для использования в СМС
	 * @return карта параметров для шаблонизатора
	 * @throws IKFLMessagingException
	 */
	public Map<String, Object> getTemplate() throws IKFLMessagingException
	{
		Map<String, Object> map = new HashMap<String, Object>();

		List<SmsCommand> commands = null;
		try
		{
			commands = mobileBankService.getUpdateSmsCommands(this);
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new IKFLMessagingException(e);
		}

		if (commands.size() != 1)
		{
			throw new IllegalStateException("Должна быть одна смс-команда.");
		}

		SmsCommand command = commands.get(0);
		map.put("name",           command.getName());
		map.put("format", command.getFormat());

		return map;
	}
}
