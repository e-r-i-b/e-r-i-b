package com.rssl.phizic.config.ant;

import org.apache.tools.ant.Task;
import com.rssl.phizic.config.ant.parser.ApplicationConfigHandler;
import org.apache.tools.ant.BuildException;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * User: Balovtsev
 * Date: 06.08.2012
 * Time: 12:59:15
 */
public class ApplicationXMLGenerationTask extends Task
{
    private String    formIn;
	private String    group;
	private String    context;
    private String    earConfig;

	private SAXParser parser;

	@Override
	public void init() throws BuildException
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			parser = factory.newSAXParser();
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}

	@Override
    public void execute() throws BuildException
	{
		if (earConfig == null || earConfig.length() == 0)
		{
			throw new BuildException("Параметр earConfig должен быть задан.");
		}

		try
		{
			ApplicationConfigHandler handler = new ApplicationConfigHandler(group, context);
			parser.parse(earConfig, handler);
			
			write( handler.getApplicationXMLDocument() );
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}

	private void write(final Document document)
	{
		if (formIn == null || formIn.length() == 0)
		{                 
			System.out.println("Атрибут formIn не задан. Файл application.xml не будет создан.");
			return;
		}

		try
		{
			makeNecessaryDirs();

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer   trans = factory.newTransformer();
			
			DOMSource    source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileOutputStream(new File(formIn + "/application.xml")));
			StreamResult webinf = new StreamResult(new FileOutputStream(new File(formIn + "/META-INF/application.xml")));

			trans.transform(source, result);
			trans.transform(source, webinf);
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}

	private void makeNecessaryDirs()
	{
		File path = new File(formIn + "/META-INF/");

		if (path.exists())
		{
			return;
		}

		if (path.mkdirs())
		{
			System.out.println("Enviroment directories was created.");
		}
		else
		{
			System.out.println("Enviroment directories already exists.");
		}
	}

	public void setContext(String context)
	{
		this.context = context;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	public void setEarConfig(String earConfig)
    {
        this.earConfig = earConfig;
    }

    public void setFormIn(String formIn)
    {
        this.formIn = formIn;
    }
}
