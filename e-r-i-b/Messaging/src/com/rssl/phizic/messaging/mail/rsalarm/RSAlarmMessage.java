package com.rssl.phizic.messaging.mail.rsalarm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.activation.DataHandler;
import javax.mail.*;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3813 $
 */

public class RSAlarmMessage extends Message
{
	private List<Address> recipients = new ArrayList<Address>();
	private String body = "";
	private String id;

	public RSAlarmMessage()
	{
	}

	public RSAlarmMessage(Session session)
	{
		super(session);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Address[] getFrom() throws MessagingException
	{
		return new Address[]{};
	}

	/**
	 * Ничего не делает
	 * @throws MessagingException
	 */
	public void setFrom() throws MessagingException
	{
		return;
	}

	/**
	 * Ничего не делает
	 * @throws MessagingException
	 */
	public void setFrom(Address address) throws MessagingException
	{
	}

	/**
	 * Ничего не делает
	 * @throws MessagingException
	 */
	public void addFrom(Address[] addresses) throws MessagingException
	{

	}

	public Address[] getRecipients(RecipientType recipientType) throws MessagingException
	{
		if(recipientType == RecipientType.TO)
			return recipients.toArray(new Address[0]);
		else
			return new Address[0];
	}

	public void setRecipients(RecipientType recipientType, Address[] addresses) throws MessagingException
	{
		recipients.clear();
		addRecipients(recipientType, addresses);
	}

	public void addRecipients(RecipientType recipientType, Address[] addresses) throws MessagingException
	{
		if(recipientType != RecipientType.TO)
			throw new MessagingException("Supported ONLY RecipientType.TO");

		for (Address address : addresses)
		{
			recipients.add(address);
		}
	}

	public String getSubject() throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void setSubject(String name) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public Date getSentDate() throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void setSentDate(Date date) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public Date getReceivedDate() throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public Flags getFlags() throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void setFlags(Flags flags, boolean b) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public Message reply(boolean b) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void saveChanges() throws MessagingException
	{
		return;
	}

	public int getSize() throws MessagingException
	{
		return body.length();
	}

	public int getLineCount() throws MessagingException
	{
		return -1;
	}

	public String getContentType() throws MessagingException
	{
		return null;
	}

	public boolean isMimeType(String name) throws MessagingException
	{
		return false;
	}

	public String getDisposition() throws MessagingException
	{
		return null;
	}

	public void setDisposition(String name) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public String getDescription() throws MessagingException
	{
		return null;
	}

	public void setDescription(String name) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public String getFileName() throws MessagingException
	{
		return null;
	}

	public void setFileName(String name) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public InputStream getInputStream() throws IOException, MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public DataHandler getDataHandler() throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public String getContent()
	{
		return body;
	}

	public void setDataHandler(DataHandler dataHandler) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void setContent(Object object, String name) throws MessagingException
	{
		body = (String) object;
	}

	public void setText(String name) throws MessagingException
	{
		setContent(name, "text");
	}

	public void setContent(Multipart multipart) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void writeTo(OutputStream outputStream) throws IOException, MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public String[] getHeader(String name) throws MessagingException
	{
		return null;
	}

	public void setHeader(String name, String value) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void addHeader(String name, String value) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public void removeHeader(String name) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public Enumeration getAllHeaders() throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public Enumeration getMatchingHeaders(String[] strings) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	public Enumeration getNonMatchingHeaders(String[] strings) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}
}