/**
 * @author Alexander Ivanov
 * @ created 11.08.2011
 * @ $Author$
 * @ $Revision$
 */

package com.rssl.phizic.messaging.mail.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

/*
 * Реализация транспорта почтовых сообщений через файлы
 */
public class Email2FileTransport extends Transport
{
	public Email2FileTransport(Session session,URLName url) throws MessagingException
	{
		super(session,url);
		try
		{
			folder = new File(getProperty(session,TRANSPORT_MAIL_TO_FILE_OUTPUT_FOLDER)).getCanonicalFile();
		}
		catch(Throwable e)
		{
			throw new TransportException("",e);
		}
		prefix = getProperty(session,TRANSPORT_MAIL_TO_FILE_OUTPUT_PREFIX,"erib-");
		suffix = getProperty(session,TRANSPORT_MAIL_TO_FILE_OUTPUT_SUFFIX,".msg");
		if(folder.exists())
		{
			if(!folder.isDirectory())
			{
				throw new TransportException("Путь к исходящим файлам не является директорией.");
			}
		}
		else if(!folder.mkdirs())
		{
			throw new TransportException("Невозможно создать папку для исходящих файлов.");
		}
		if(prefix.length() < 3)
		{
			throw new TransportException("Недопустимый префикс для формирования файла.");
		}
		originators = getFrom(session);
	}
	@Override
	public void connect() throws MessagingException
	{
	}
	@Override
	public void connect(String name,int i,String name1,String name2) throws MessagingException
	{
	}
	@Override
	public void connect(String name,String name1,String name2) throws MessagingException
	{
	}
	@Override
	public void sendMessage(Message message,Address[] recipients) throws MessagingException
	{
		File file = null;
		try
		{
			Map<String,Boolean> exists = new TreeMap<String,Boolean>(String.CASE_INSENSITIVE_ORDER);
			if(message.getAllRecipients() != null) for(Address address: message.getAllRecipients())
			{
				exists.put(address.toString(),Boolean.TRUE);
			}
			for(Address address: recipients) if(!Boolean.TRUE.equals(exists.get(address.toString())))
			{
				message.addRecipient(RecipientType.TO,address);
			}
			if(originators.length > 0)
			{
				exists.clear();
				if(message.getFrom() != null) for(Address address: message.getFrom())
				{
					exists.put(address.toString(),Boolean.TRUE);
				}
				Collection<Address> from = new ArrayList<Address>();
				for(Address address: originators) if(!exists.containsKey(address.toString()))
				{
					from.add(address);
				}
				if(from.size() > 0)
				{
					message.addFrom(from.toArray(new Address[]{}));
				}
			}
			OutputStream out = new FileOutputStream(file=File.createTempFile(prefix,suffix,folder));
            try
            {
				for(Enumeration enumerator = message.getAllHeaders();enumerator.hasMoreElements();)
				{
					Header header = (Header)enumerator.nextElement();
					out.write(header.getName().getBytes());
					out.write(COLON);
					out.write(header.getValue().getBytes());
					out.write(CRLF);
				}
				out.write(CRLF);
				writeContent(out,message.getContent());
			}
            catch(Throwable e)
            {
				try
				{
					out.close();
				}
				catch(Throwable x)
				{
				}
				throw e;
			}
			out.close();
		}
		catch(IOException e)
		{
			processSendError(file,e,"Ошибка ввода/вывода.");
		}
		catch(Throwable e)
		{
			processSendError(file,e,"");
		}
	}
	private static void writeContent(OutputStream out,Object content) throws IOException,MessagingException
	{
		if(content instanceof MimeMultipart)
		{
			((MimeMultipart)content).writeTo(out);
		}
		else if(content != null)
		{
			out.write(content.toString().getBytes());
		}
	}
	private static void processSendError(File file,Throwable error,String cause) throws MessagingException
	{
		if(file != null)
		{
			try
			{
				file.deleteOnExit();
			}
			catch(Throwable r)
			{
			}
		}
		throw (error instanceof TransportException) ? (TransportException)error : new TransportException(cause,error);
	}
	private static String getProperty(Session session,String name)
	{
		return getProperty(session,name,"");
	}
	private static String getProperty(Session session,String name,String defValue)
	{
		String value = session.getProperty(name);
		if((value == null) || (value.trim().length() == 0))
		{
			value = defValue;
		}
		return value;
	}
	private static Address[] getFrom(Session session) throws AddressException
	{
		String originator = getProperty(session,TRANSPORT_MAIL_TO_FILE_LETTER_SENDER);
		if(originator != null)
	    {
			originator = originator.trim();
			if(originator.length() > 0)
			{
				return new Address[]{new InternetAddress(originator)};
			}
	    }
		return new Address[]{};
	}
	private final File folder;
	private final String prefix;
	private final String suffix;
	private final Address[] originators;
	public static final String TRANSPORT_MAIL_TO_FILE_OUTPUT_FOLDER = "transport.mail.file.folder";
	public static final String TRANSPORT_MAIL_TO_FILE_OUTPUT_PREFIX = "transport.mail.file.prefix";
	public static final String TRANSPORT_MAIL_TO_FILE_OUTPUT_SUFFIX = "transport.mail.file.suffix";
	public static final String TRANSPORT_MAIL_TO_FILE_LETTER_SENDER = "mail.smtp.from";
	private static final byte[] COLON = ": ".getBytes();
	private static final byte[] CRLF = "\r\n".getBytes();
}
