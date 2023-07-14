package com.rssl.phizic.messaging.mail.rsalarm;
/*************************************************************************
 FILE         	:   RSAlarmConnect.java
 COPYRIGHT    	:   R-Style Softlab, 2006
 DESCRIPTION  	:   Поддержка взаимодействия с транспортным сервером
 PROGRAMMED BY	:   Иванов Александр
 CREATION DATE	:   22.03.2006
*************************************************************************/
import	java.io.*;
import	java.nio.charset.*;
import	java.net.*;
import	java.util.*;
import javax.mail.MessagingException;

public class RSAlarmConnect
{
	public	RSAlarmConnect(String host, int port)
	{
		m_host		= host;
		m_port		= port;
		m_ostream	= null;
		m_istream	= null;
		m_socket	= null;
	}

	public	interface	Property
	{
		public	int		getId		();
		public	int		getSize		();
		public	int		getType		();
		public	int		getSupp		();
		public	int		getFlags	();
		public	int		toNumber	();
		public	String	toString	();
		public	String	toString	(int base);
	}

	public	interface	Properties
	{
		public	Property	getProperty			(int i);
		public	Property	getProperty			(int id, int type);
		public	Property	getPropertyByVal	(int id, String name);
		public	Property	getPropertyByVal	(int id, int val);

		public	int			count				();
	}

	public	interface	Container
	{
		public	String		getName			(int i);

		public	Properties	getProperties	(String name);
		public	Properties	getProperties	(int i);

		public	int			count			();
	}

	public	Container	getCarriers() throws Throwable
	{
		return getObjectPropsMap(RSALARM_CMD_CARRIERS, RSALARM_PROP_CARRIER_NAME);
	}

	public	Container	getResources() throws Throwable
	{
		return getObjectPropsMap(RSALARM_CMD_RESOURCES, RSALARM_PROP_RESOURCE_NAME);
	}

	public	Container	getCharsets() throws Throwable
	{
		return getObjectPropsMap(RSALARM_CMD_CHARSETS, RSALARM_PROP_CHARSET_NAME);
	}

	public	Properties	Submit(String resource, String to, String path, String text, String subj) throws IOException, MessagingException
	{
		Request	req = new Request(RSALARM_CMD_SUBMIT);

		if(resource != null)
		{
           	req.addParameter(RSALARM_PROP_RESOURCE_NAME, resource);
		}

		req.addParameter(RSALARM_PROP_CARRIER_NAME, path);
		req.addParameter(RSALARM_PROP_MESSAGE_ADDR, to);
		req.addParameter(RSALARM_PROP_MESSAGE_TEXT, text);

		if(subj != null)
		{
			req.addParameter(RSALARM_PROP_MESSAGE_SUBJ, subj);
		}

		return req.Execute();
	}

	public	Properties	Accept(String resource, String position) throws Throwable
	{
		Request	req = new Request(RSALARM_CMD_ACCEPT);

		if(resource != null)
		{
			req.addParameter(RSALARM_PROP_RESOURCE_NAME, resource);
		}
		if(position != null)
		{
           	req.addParameter(RSALARM_PROP_MESSAGE_ID, position);
		}
		
		return req.Execute();
	}

	public	Properties	Delete(String position) throws Throwable
	{
		Request	req = new Request(RSALARM_CMD_DELETE);

		req.addParameter(RSALARM_PROP_MESSAGE_ID, position);
		
		return req.Execute();
	}

	public	Socket connect() throws MessagingException, IOException
	{
		try
		{
			if(m_socket == null)
			{
				m_socket  = new Socket(m_host, m_port);
				m_ostream = m_socket.getOutputStream();
				m_istream = m_socket.getInputStream();
			}
			return m_socket;
		}
		catch(ConnectException e)
		{
			throw new MessagingException(e.toString());
		}
	}

	public	void	shutdown()	throws Throwable
	{
		if(m_socket != null)
		{
			try
			{
				new Request(RSALARM_CMD_SHUTDOWN).Execute();
			}
			catch(Throwable e)
			{
			}

			m_socket.shutdownOutput();
			m_socket.shutdownInput();
			m_socket.close();

			m_ostream = null;
			m_istream = null;
			m_socket  = null;
		}
	}

	public	static	void	main(String[] args)	throws Throwable
	{
		String	resource	= "java-sample";
		String	to 			= null;
		String	path 		= null;
		String	text		= "Я пришел к тебе с приветом рассказать, что солнце встало!";
		String	cp			= "win1251";
		String	host		= "localhost";

		int		port 	= 12122;

		for(int i = 0; i < args.length; i++) if(args[i].substring(0, 3).compareToIgnoreCase("to=") == 0)
		{
			to = args[i].substring(3);
		}
		else if(args[i].substring(0, 9).compareToIgnoreCase("resource=") == 0)
		{
			resource = args[i].substring(9);
		}
		else if(args[i].substring(0, 3).compareToIgnoreCase("cp=") == 0)
		{
			cp = args[i].substring(3);
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("host=") == 0)
		{
			host = args[i].substring(5);
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("port=") == 0)
		{
			port = Integer.valueOf(args[i].substring(5)).intValue();
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("text=") == 0)
		{
			text = args[i].substring(5);
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("path=") == 0)
		{
			path = args[i].substring(5);
		}

		final	PrintStream	out = Charset.isSupported(cp) ? new PrintStream(System.out, true, cp) : System.out;

		try
		{
			RSAlarmConnect	rsaconn = new RSAlarmConnect(host, port)
			{
				private	void	printprops(String header, Container obj, int name_id)
				{
					out.println("\n" + header + "\n");

					for(int i = 0, size = obj.count(); i < size;)
					{
						Properties	props = obj.getProperties(i++);
						out.println(i + ".\t" + props.getProperty(name_id, RSALARM_TYPE_STRING).toString());
						for(int j = 0, l = props.count(), n = 0; j < l;)
						{
							Property	prop = props.getProperty(j++);
							
							if((prop.getId() != RSALARM_PROP_RESULT) && (prop.getId() != name_id))
							{
								out.println("\t" + i + "." + (++n) + ".\t" + prop.toString());
							}
						}
					}
				}
				
				{
					printprops("Доступные именованные ресурсы",	getResources(), RSALARM_PROP_RESOURCE_NAME);
					printprops("Доступные среды доставки",	 	getCarriers(),	RSALARM_PROP_CARRIER_NAME);
					printprops("Доступные кодировки текста",	getCharsets(),	RSALARM_PROP_CHARSET_NAME);
				}
			};

			out.println();

			String	message_id	= null;

			for(int n = 0; ; ++n)
			{
				Properties	props = rsaconn.Accept(resource, message_id);

				if(props.getProperty(RSALARM_PROP_RESULT, RSALARM_TYPE_INTEGER).toNumber() != 0)
				{
					if(n > 0)
					{
						out.println("\nRecords accepted: " + n);
					}

					break;
				}

				message_id = props.getProperty(RSALARM_PROP_MESSAGE_ID, RSALARM_TYPE_STRING).toString();

				props = rsaconn.Submit
					(
						props.getProperty(RSALARM_PROP_RESOURCE_NAME, RSALARM_TYPE_STRING).toString(), 
						props.getProperty(RSALARM_PROP_MESSAGE_ADDR, RSALARM_TYPE_STRING).toString(),
						props.getProperty(RSALARM_PROP_CARRIER_NAME, RSALARM_TYPE_STRING).toString(),
						resource + "> " + props.getProperty(RSALARM_PROP_MESSAGE_TEXT, RSALARM_TYPE_STRING).toString(),
						null
					);

				if(props.getProperty(RSALARM_PROP_RESULT, RSALARM_TYPE_INTEGER).toNumber() == 0)
				{
					out.println("Accept " + message_id + ", reply " + props.getProperty(RSALARM_PROP_MESSAGE_ID, RSALARM_TYPE_STRING).toString());
					
					props = rsaconn.Delete(message_id);

					if(props.getProperty(RSALARM_PROP_RESULT, RSALARM_TYPE_INTEGER).toNumber() == 0)
					{
						message_id = null;
					}
				}
			}

			if((to != null) && (path != null))
			{
				out.println("Submit " + rsaconn.Submit(resource, to, path, text, null).getProperty(RSALARM_PROP_MESSAGE_ID, RSALARM_TYPE_STRING).toString());
			}

			rsaconn.shutdown();
		}
		catch(Throwable einfo)
		{
			out.println("\nGeneral fault: " + einfo.getMessage() + "\n");
		}
	}

	private	final	String			m_host;

	private final	int				m_port;

	private			Socket			m_socket;

	private			OutputStream	m_ostream;
	private			InputStream		m_istream;

	private	static	final	int	RSALARM_PROTO_SIGNATURE		= 0x35475641;

	private	static	final	int	RSALARM_CMD_SHUTDOWN		= 0;
	private	static	final	int	RSALARM_CMD_CHARSETS		= 5;
	private	static	final	int	RSALARM_CMD_CARRIERS		= 6;
	private	static	final	int	RSALARM_CMD_RESOURCES		= 7;
	private	static	final	int	RSALARM_CMD_SUBMIT			= 11;
	private	static	final	int	RSALARM_CMD_ACCEPT			= 12;
	private	static	final	int	RSALARM_CMD_DELETE			= 13;

	public	static	final	int	RSALARM_PROP_DUMMY			= 0;
	public	static	final	int	RSALARM_PROP_RESULT			= 1;

	public	static	final	int	RSALARM_PROP_CHARSET_ID		= 0x100;
	public	static	final	int	RSALARM_PROP_CHARSET_NAME	= 0x101;

	public	static	final	int	RSALARM_PROP_RESOURCE_NAME	= 0x200;

	public	static	final	int	RSALARM_PROP_CARRIER_NAME	= 0x300;

	public	static	final	int	RSALARM_PROP_MESSAGE_ID		= 0x400;
	public	static	final	int	RSALARM_PROP_MESSAGE_ADDR	= 0x401;
	public	static	final	int	RSALARM_PROP_MESSAGE_TEXT	= 0x402;
	public  static  final   int RSALARM_PROP_MESSAGE_SUBJ   = 0x40a;

	public	static	final	int	RSALARM_TYPE_INTEGER		= 1;
	public	static	final	int	RSALARM_TYPE_STRING			= 2;

	private	static	final	int	RSALARM_FLAG_TERMINATOR		= 0x0080;

	private	static	final	int	RSALARM_CHAR_UNICODE		= 0;

	private	class	Request
	{
		public	Request(int cmd) throws IOException, MessagingException
		{
			connect();

			m_cmd = cmd;
			m_req = new Vector<Object>();

			addParameter(RSALARM_PROP_CHARSET_ID, RSALARM_CHAR_UNICODE);
		}

		public	Properties	readRep() throws IOException
		{
			class	PropertiesImpl implements Properties
			{
				public	Property	getProperty(int i)
				{
					return (Property)m_rep.get(i);
				}

				public	Property	getProperty	(int id, int type)
				{
					for(int i = 0, size = m_rep.size(); i < size; i++)
					{
						Property prop = (Property)m_rep.get(i);
						if(id == prop.getId())
						{
							if((type != 0) && (type != prop.getType()))
							{
								continue;
							}
							return prop;
						}
					}
				
					return null;
				}

				public	Property	getPropertyByVal(int id, String name)
				{
					Property prop = getProperty(id, RSALARM_TYPE_STRING);
				
					return ((prop != null) && (name.compareToIgnoreCase(prop.toString()) == 0)) ? prop : null;		
				}

				public	Property	getPropertyByVal(int id, int val)
				{
					Property prop = getProperty(id, RSALARM_TYPE_INTEGER);
				
					return ((prop != null) && (val == prop.toNumber())) ? prop : null;		
				}

				public	int		count()
				{
					return m_rep.size();
				}

				private	int	recvInt() throws IOException
				{
					int	val = 0;

					for(int i = 0; i < 4; i++)
					{
						val |= (m_istream.read() & 255) << (i << 3);	
					}
		
					return val;
				}

				public	PropertiesImpl() throws IOException
				{
					m_rep = new Vector<Object>();

					if(recvInt() != RSALARM_PROTO_SIGNATURE)
					{
						throw new IOException("Invalid reply signature");
					}

					if(recvInt() != 0)
					{
						throw new IOException("Invalid reply structure");
					}

					if(m_cmd != recvInt())
					{
						throw new IOException("Ivalid reply command - 0x" + Integer.toString(m_cmd, 16));
					}

					Property	prop;

					int			errcode = 0;

					String		errtext = "";

					do
					{
						prop = new Property()
						{
							public	int	getId()
							{
								return m_id;
							}

							public	int	getSize()
							{
								return m_size;
							}

							public	int	getType()
							{
								return m_type & 255;
							}

							public	int	getSupp()
							{
								return (m_type >> 8) & 255;
							}

							public	int	getFlags()
							{
								return m_type >> 16;
							}

							public	int	toNumber()
							{
								int	val	= 0;

								if((m_type & 255) == RSALARM_TYPE_STRING)
								{
									String	data = toString();
							
									int	size = data.length();
									int	sign = 1;
									int	base = 10;
				
									for(int i = 0; i < size; i++)
									{
										int	j = 0;

										switch(data.charAt(i))
										{
											case 'x'	:
											case 'X'	:

												switch(i)
												{
													case 1	:

														if((data.charAt(0) != '0') && (data.charAt(0) != '\\'))
														{	
															break;
														}
 					
													case 0	:
							
														base = 16;
														continue;
												}
												break;

											case 'F'	:
											case 'f'	:
												j++;
											case 'E'	:
											case 'e'	:
												j++;
											case 'D'	:
											case 'd'	:
												j++;
											case 'C'	:
											case 'c'	:
												j++;
											case 'B'	:
											case 'b'	:
												j++;
											case 'A'	:
											case 'a'	:
												j++;
				
												if(base != 16)
												{
													break;
												}
				
											case '9'	:
												j++;
											case '8'	:
												j++;
											case '7'	:
												j++;
											case '6'	:
												j++;
											case '5'	:
												j++;
											case '4'	:
												j++;
											case '3'	:
												j++;
											case '2'	:
												j++;
											case '1'	:
												j++;
											case '0'	:
				
												val *= base;
												val += j;
												continue;
				
											case '-'	:
											
												if(i == 0)
												{
													sign = -1;
													continue;
												}
												break;
				
											case '+'	:
											case '\\'	:
				
												if(i == 0)
												{
													continue;
												}
										}
										break;
									}

									return val * sign;
								}

								for(int i = 0, size = (m_size < 4) ? m_size : 4; i < size; i++)
								{
									val |= (m_buff[i] & 255) << (i << 3);
								}

								return val;
							}

							public	String	toString()
							{
								return toString(10);
							}

							public	String	toString(int base)
							{
								switch(m_type & 255)
								{
									case RSALARM_TYPE_INTEGER	:

										return Integer.toString(toNumber(), base);

									case RSALARM_TYPE_STRING	:

										break;

									default				:

										return "";
								}

								char	val[] = new char[m_size >> 1];

								for(int i = 0; i < m_size; i += 2)
								{
									val[i >> 1] = (char)((m_buff[i] & 255) | ((m_buff[i + 1] & 255) << 8));
								}

								return new String(val);
							}

							private	final	byte	m_buff[];

							private	final	int		m_id;
							private	final	int		m_type;
							private	final	int		m_size;

							{
								m_size = recvInt();
								m_id   = recvInt();
								m_type = recvInt();
								m_buff = new byte[m_size];

								for(int i = 0; i < m_size; i++)
								{
									m_buff[i] = (byte)m_istream.read();
								}
							}
						};

						switch(prop.getId())
						{
							case RSALARM_PROP_DUMMY		:
							
								break;

							case RSALARM_PROP_RESULT	:

								switch(prop.getType())
								{
									case RSALARM_TYPE_INTEGER	:
			
										errcode = prop.toNumber();
										break;

									case RSALARM_TYPE_STRING	:

										errtext = prop.toString();
								}
					
							default				:

								m_rep.add(prop);
						}
				
					} while((prop.getFlags() & RSALARM_FLAG_TERMINATOR) == 0);

				}

				private	final	Vector<Object>	m_rep;
			}
				
			return new PropertiesImpl();
		}

		public	Properties	Execute() throws IOException
		{
			sendInt(RSALARM_PROTO_SIGNATURE);
			sendInt(0);
			sendInt(m_cmd);

			int	n = m_req.size();

			if(n > 0) for(int i = 0; i < n; )
			{
				Parameter prop = (Parameter)m_req.get(i++);
						
				int	size = prop.size();

				sendInt(size);
				sendInt(prop.id());
	     		sendInt(prop.type() | ((i < n) ? 0 : (RSALARM_FLAG_TERMINATOR << 16)));

				for(int j = 0; j < size; j++)
				{
					m_ostream.write(prop.byteAt(j));	
				}
			}
			else
			{
				sendInt(0);
				sendInt(0);
				sendInt(RSALARM_FLAG_TERMINATOR << 16);
			}

			return readRep();
		}

		public	Request	addParameter(int id, int val)
		{
			m_req.add(new Parameter(id, val));

			return this;
		}

		public	Request	addParameter(int id, String val)
		{
			m_req.add(new Parameter(id, val));
			return this;
		}

		private	void	sendInt(int val) throws IOException
		{
			for(int i = 0; i < 4; i++)
			{
				m_ostream.write((val >> (i << 3)) & 255);	
			}
		}

		private	class	Parameter
		{
			public	int	id()
			{
				return m_id;
			}

			public	int	size()
			{
				return m_size;
			}

			public	int	type()
			{
				return m_type;
			}

			public	int	byteAt(int i)
			{
				return (i < m_size) ? (m_buff[i] & 255) : 0;
			}

			private	Parameter(int id, String val)
			{
				m_id   = id;
				m_type = RSALARM_TYPE_STRING;
				m_size = (val == null) ? 0 : (val.length() << 1);
				m_buff = new byte[m_size];

				for(int i = 0; i < m_size; )
				{
					int j = val.charAt(i >> 1);

					m_buff[i++] = (byte)j;
					m_buff[i++] = (byte)(j >> 8);
					
				}
			}

			private	Parameter(int id, int val)
			{
				m_id   = id;
				m_type = RSALARM_TYPE_INTEGER;
				m_size = 4;
				m_buff = new byte[4];

				for(int i = 0; i < m_size; i++)
				{
					m_buff[i] = (byte)(val >> (i << 3));	
				}
			}

			private	final	byte	m_buff[];

			private	final	int		m_id;
			private	final	int		m_type;
			private	final	int		m_size;
		}

		private	final	int				m_cmd;
		private	final	Vector<Object>	m_req;
	}

	private	Container	getObjectPropsMap(final int cmd, final int id)	throws Throwable
	{
		class	ContainerImpl implements Container
		{
			private	ContainerImpl()
			{
				m_vec = new Vector<Object>();
			}

			public	String	getName(int i)
			{
				return ((Properties)m_vec.get(i)).getProperty(id, RSALARM_TYPE_STRING).toString();
			}

			public	Properties	getProperties(String name)
			{
				for(int i = 0, size = m_vec.size(); i < size; i++)
				{
					Properties propset = (Properties)m_vec.get(i);
					if(propset.getPropertyByVal(id, name) != null)
					{
						return propset;
					}			
				}
				return null;
			}

			public	Properties	getProperties(int i)
			{
				return (Properties)m_vec.get(i);
			}
	
			public	int		count()
			{
				return m_vec.size();
			}

			public	void	add(Object obj)
			{
				m_vec.add(obj);
			}

			private	final	Vector<Object>	m_vec;
		}

		return new ContainerImpl()
			{
				{
					Properties 	objlist = new Request(cmd).Execute();

					for(int i = 0, size = objlist.count(); i < size; i++) 
					{
						Property prop = objlist.getProperty(i);
						if((prop.getId() == id) && (prop.getType() == RSALARM_TYPE_STRING))
						{
							add(new Request(cmd + 3).addParameter(id, prop.toString()).Execute());
						}			
					}
				}
			};
	}
}
