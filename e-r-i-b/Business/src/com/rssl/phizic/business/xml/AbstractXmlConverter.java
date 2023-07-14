package com.rssl.phizic.business.xml;

import com.rssl.common.forms.FormException;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.utils.StringHelper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;

/**
 * @author Evgrafov
 * @ created 02.11.2005
 * @ $Author: erkin $
 * @ $Revision$
 */

public abstract class AbstractXmlConverter implements XmlConverter
{

	private Templates          templates;
	private Source             dataSource;
	private final Map<String,Object> parameters = new HashMap<String, Object>();

	private final Map<String, Source> dictionarySources = new HashMap<String, Source>();

	private final URIResolver dictionaryURIResolver = new URIResolver()
	{
		public Source resolve(String href, String base)
		{
			return dictionarySources.get(href);
		}
	};

	protected AbstractXmlConverter(Templates templates)
	{
		if(templates == null)
			throw new NullPointerException("templates can't be null");

		this.templates = templates;
	}

	/**
     * ������ ��� ��������������
     */
    public Source getData()
    {
        return dataSource;
    }

    /**
     * ������ ��� ��������������
     */
    public void setData(Source dataSource)
    {
        this.dataSource = dataSource;
    }

	/**
	 * ���������� �������� �������������, ����� ������� � Transformer.setParameter()
	 * @param name
	 * @param value
	 */
	public void setParameter(String name, Object value)
	{
		parameters.put(name, value);
	}

	public void setDictionary(String dictionaryName, Source dictionarySource)
	{
		if (StringHelper.isEmpty(dictionaryName))
		    throw new IllegalArgumentException("�������� 'dictionaryName' �� ����� ���� ������");

		if (dictionarySource == null)
		    throw new IllegalArgumentException("�������� 'dictionarySource' �� ����� ���� null");

		dictionarySources.put(dictionaryName, dictionarySource);
	}

	protected URIResolver getDictionaryURIResolver()
	{
		return dictionaryURIResolver;
	}

	public StringBuffer convert() throws FormException
    {
	    StringWriter stringWriter = new StringWriter();
	    Result streamResult = new StreamResult(stringWriter);

	    convert(streamResult);

        return stringWriter.getBuffer();
    }

	public void convert(Result result) throws FormException
	{
		Transformer transformer;

		try
        {
	        transformer = templates.newTransformer();

	        URIResolver resolver = getDictionaryURIResolver();
	        if(resolver != null)
		        transformer.setURIResolver(resolver);

			setTransformerParameters(transformer);
		}
		catch (TransformerConfigurationException e)
		{
		    throw new FormException(e);
		}


		try
        {
	        transformer.setErrorListener(new TransformErrorListener());
            transformer.transform(dataSource, result);
		}
		catch (TransformerException e)
		{
			throw new FormException(e);
		}
	}

	private void setTransformerParameters(Transformer transformer)
	{
		for (Map.Entry<String, Object> entry : parameters.entrySet())
		{
			transformer.setParameter(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * ��������� ����� ��� �������� Templates
	 * @param source ��������
	 * @return ���������
	 */
	protected static Templates createTemplates(Source source)
	{
		try
		{
			return TransformerFactory.newInstance().newTemplates(source);
		}
		catch (TransformerConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

}
