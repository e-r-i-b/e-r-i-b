package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentReceiverXmlSerializer;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Kosyakov
 * @ created 27.07.2006
 * @ $Author: sergunin $
 * @ $Revision: 52811 $
 */
public abstract class PaymentReceiverBase implements ConfirmableObject
{
	private Long id;
	private Login login;
	private String name;
	private String alias;
	private String description = "";
	private ReceiverState state;
	private String ground;
	private String uniqueNumber;
	private String kind;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Login getLogin()
	{
		return login;
	}

	public void setLogin(Login login)
	{
		this.login = login;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public ReceiverState getState()
	{
		return state;
	}

	public void setState(ReceiverState state)
	{
		this.state = state;
	}

	public String getGround()
	{
		return ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public String getUniqueNumber()
	{
		return uniqueNumber;
	}

	public void setUniqueNumber(String uniqueNumber)
	{
		this.uniqueNumber = uniqueNumber;
	}

	public String getKind()
	{
		return kind;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}

	public abstract PaymentReceiverXmlSerializer getXmlSerializer();

	//может заюзать PaymentReceiverXmlSerializer??
	public byte[] getSignableObject()
	{
		try
		{
			PersonService personService = new PersonService();
			String fullname   = personService.findByLogin(login).getFullName();
			PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
			ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptor(kind);
			List<FieldDescriptor> fieldDescriptors = receiverDescriptor.getFieldDescriptors();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			XMLSerializer serializer = new XMLSerializer(stream, new OutputFormat(Method.XML, "UTF-8", false));
			serializer.startDocument();
			serializer.startElement("signable-document", null);

			serializer.startElement("fullname", null);
			serializer.characters(fullname.toCharArray(), 0, fullname.length());
			serializer.endElement("fullname");

			for (FieldDescriptor descriptor : fieldDescriptors)
			{
				String value = dictionary.calculateFieldValue(this, descriptor);

				serializer.startElement(descriptor.getName(), null);
				serializer.characters(value.toCharArray(), 0, value.length());
				serializer.endElement(descriptor.getName());
			}
			serializer.endElement("signable-document");
			serializer.endDocument();
			return stream.toByteArray();
		}
		catch (BusinessException ex)
		{
			throw new RuntimeException("Ошибка при создании документа для пoдписания", ex);
		}
		catch (SAXException ex)
		{
			throw new RuntimeException("Ошибка при создании документа для подписания", ex);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException("Ошибка при создании документа для подписания", e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Ошибка при создании документа для подписания", e);
		}
		catch (InvocationTargetException e)
		{
			throw new RuntimeException("Ошибка при создании документа для подписания", e);
		}
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}
}
