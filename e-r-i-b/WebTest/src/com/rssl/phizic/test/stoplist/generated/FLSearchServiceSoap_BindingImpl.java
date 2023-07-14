/**
 * FLSearchServiceSoap_BindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.stoplist.generated;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializer;
import com.rssl.phizicgate.stoplist.generated.jaxb.ObjectFactory;
import com.rssl.phizicgate.stoplist.generated.jaxb.Request;
import com.rssl.phizicgate.stoplist.generated.jaxb.Response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

/**
 * ����� ����������� ��� �������� ������� ������� "����-����". ��� ���� ����� ����������� ��������� ��������� ��. ����� handRequest.
 */
public class FLSearchServiceSoap_BindingImpl implements com.rssl.phizic.test.stoplist.generated.FLSearchServiceSoap_PortType
{

	private static final String ENCODING = "UTF-8";
	public final static Random random = new Random();
	private static volatile JAXBContext jaxbContext;

	public com.rssl.phizic.test.stoplist.generated.SearchFLResponse searchFL(com.rssl.phizic.test.stoplist.generated.SearchFL parameters) throws java.rmi.RemoteException
	{
		try
		{
			Request request = unmarshal(parameters.getXmlRequest());
			Response answer = handRequest(request);
			String responseString = marshal(answer);
			return new SearchFLResponse(responseString);
		}
		catch (Exception e)
		{
			throw new java.rmi.RemoteException(e.getMessage(), e);
		}
	}

	/**
	 * ��������� �������
	 * @param request  ������
	 * @return �����, ����������� ������ ������� � ����-�����
	 */
	private Response handRequest(Request request)
	{
		Response response = new ObjectFactory().createResponse();
		int number = random.nextInt(100);
		if (number == 50)
		{
			//ClientStopListState.blocked;
			response.setCode(2);
			response.setMessage("��������� �� ��������! �������� ���������. ������ ���������� � ����-�����");

		}
		else if (number < 50)
		{
			//ClientStopListState.shady;
			response.setCode(1);
			response.setMessage("��������� �� ��������! �������� ���������, ���� ������ ��������� � ����-�����");
		}
		else if (50 < number && number < 90)
		{
			//ClientStopListState.trusted;
			response.setCode(0);
		}
		else
		{
			//������
			response.setCode(-(random.nextInt(2) + 1));
			response.setMessage("��������� �� ��������! �����-�� ��������� ������.");
		}
		return response;
	}

	/**
	 * ��������������� ������� {@link Response} � XML
	 * @param response ����� � ��������� �������������
	 * @return ����� � XML �������������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	private String marshal(Response response) throws GateException
	{
		try
		{
			Marshaller marshaller = getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);

			StringWriter writer = new StringWriter();

			marshaller.marshal(response, writer);

			return writer.toString();
		}
		catch (JAXBException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������������� XML � ������ {@link Request}
	 * @param requestString ������ � XML �������������
	 * @return ������ � ��������� ������������� {@link Response}
	 * @throws GateException
	 */
	private Request unmarshal(String requestString) throws GateException
	{
		try
		{
			Unmarshaller um = getContext().createUnmarshaller();
			InputStream stream = new ByteArrayInputStream(requestString.getBytes(ENCODING));
			return (Request)um.unmarshal(new StreamSource(stream));
		}
		catch (JAXBException e)
		{
			throw new GateException(e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("���������������� ��������� " + ENCODING, e);
		}
	}

	/**
	 * ��������� JAXB-���������
	 * @return JAXB-��������
	 * @throws GateException
	 */
	private JAXBContext getContext() throws GateException
	{
		try
		{
			if (jaxbContext == null)
			{
				synchronized (FLSearchServiceSoap_BindingImpl.class)
				{
					if (jaxbContext == null)
					{
						jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
					}
				}
			}
			return jaxbContext;
		}
		catch (JAXBException e)
		{
			throw new GateException("�� ������� ������������������� ����-���� ������ �� ������� �������",e);
		}
	}
}
