package com.rssl.phizic.gate.impl.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public class GateDocumentXMLRouter extends AbstractService implements DocumentService
{
	private static final String CONFIG_FILE_NAME = "gate-documents-config.xml";

	private Map<Class<? extends GateDocument>, CommissionCalculator> commissionCalculators = new HashMap<Class<? extends GateDocument>, CommissionCalculator>();
	private Map<Class<? extends GateDocument>, DocumentSender> senders = new HashMap<Class<? extends GateDocument>, DocumentSender>();
	private Map<Class<? extends GateDocument>, DocumentUpdater> updaters = new HashMap<Class<? extends GateDocument>, DocumentUpdater>();

	private XPathExpression selectSender;
	private XPathExpression selectCommissionCalculator;
	private XPathExpression selectUpdater;

	public GateDocumentXMLRouter(GateFactory factory)
	{
		super(factory);
		initialize();
	}

	private void initialize()
	{

		try
		{
			XPath xpath = XPathFactory.newInstance().newXPath();
			selectSender = xpath.compile("sender");
			selectCommissionCalculator = xpath.compile("commission-calculator");
			selectUpdater = xpath.compile("updater");

			Document config = XmlHelper.loadDocumentFromResource(CONFIG_FILE_NAME);
			Element rootElem = config.getDocumentElement();

			readDocuments(rootElem);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void readDocuments(Element root) throws Exception
	{
		//считываем описани€ документов
		XmlHelper.foreach(root, "document", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String documentClassName = element.getAttribute("class");
				Class<? extends GateDocument> documentClass = ClassHelper.loadClass(documentClassName);

				DocumentSender documentSender = parseSender(element);
				CommissionCalculator commissionCalculator = parseCommissionCalculator(element);
				DocumentUpdater documentUpdater = parseUpdater(element);

				registerSender(documentClass, documentSender);
				registerCommissionCalculator(documentClass, commissionCalculator);
				registerUpdater(documentClass, documentUpdater);
			}
		}
		);
		//считываем описани€ отзывов
		XmlHelper.foreach(root, "revoke-payment", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String documentClassName = element.getAttribute("class");
				Class<? extends WithdrawDocument> documentClass = ClassHelper.loadClass(documentClassName);

				String revokeDocumentTypePropety = element.getAttribute("document-type-field");
				registerSender(documentClass, new RevokeSender(documentClass, revokeDocumentTypePropety));
			}
		}
		);
	}

	private CommissionCalculator parseCommissionCalculator(Element element) throws Exception
	{
		CommissionCalculator commissionCalculator = new NullCommissionCalculator();
		Element comissioCalculatorElement = (Element) selectCommissionCalculator.evaluate(element, XPathConstants.NODE);
		if (comissioCalculatorElement != null)
		{
			String comissionCalculatorClassName = comissioCalculatorElement.getAttribute("class");
			Class<? extends CommissionCalculator> comissionCalculatorClass = ClassHelper.loadClass(comissionCalculatorClassName);
			Constructor[] constructors = comissionCalculatorClass.getConstructors();
			if (constructors.length==0)
				throw new GateException("Ќе найден ни один конструктор. "+comissionCalculatorClass);
			for (Constructor constructor : constructors)
			{
				if (checkGateFactoryInitializationNessesary(constructor.getParameterTypes()))
					commissionCalculator = (CommissionCalculator) constructor.newInstance(getFactory());
			}
			if (commissionCalculator instanceof NullCommissionCalculator)
				commissionCalculator = comissionCalculatorClass.newInstance();
			Map<String, String> params = parseParameters(comissioCalculatorElement);
			commissionCalculator.setParameters(params);
		}
		return commissionCalculator;
	}

	private DocumentSender parseSender(Element element) throws Exception
	{
		DocumentSender sender = new NullDocumentSender();
		Element senderElement = (Element) selectSender.evaluate(element, XPathConstants.NODE);
		if (senderElement != null)
		{
			String senderClassName = senderElement.getAttribute("class");
			Class<? extends DocumentSender> senderClass = ClassHelper.loadClass(senderClassName);
			Constructor[] constructors = senderClass.getConstructors();
			if (constructors.length==0)
				throw new GateException("Ќе найден ни один конструктор. "+senderClass);
			for (Constructor constructor : constructors)
			{
				if (checkGateFactoryInitializationNessesary(constructor.getParameterTypes()))
					sender = (DocumentSender) constructor.newInstance(getFactory());
			}
			if (sender instanceof NullDocumentSender)
				sender = senderClass.newInstance();
			Map<String, String> params = parseParameters(senderElement);
			sender.setParameters(params);
		}
		return sender;
	}

	private DocumentUpdater parseUpdater(Element element) throws Exception
	{
		DocumentUpdater updater = null;
		Element updaterElement = (Element) selectUpdater.evaluate(element, XPathConstants.NODE);
		if (updaterElement != null)
		{
			String updaterClassName = updaterElement.getAttribute("class");
			Class<? extends DocumentUpdater> updaterClass = ClassHelper.loadClass(updaterClassName);
			Constructor[] constructors = updaterClass.getConstructors();
			if (constructors.length==0)
				throw new GateException("Ќе найден ни один конструктор. "+updater);
			for (Constructor constructor : constructors)
			{
				if (checkGateFactoryInitializationNessesary(constructor.getParameterTypes()))
					updater = (DocumentUpdater) constructor.newInstance(getFactory());
			}
			if (updater == null)
				updater = updaterClass.newInstance();
			updater.setParameters(parseParameters(updaterElement));
		}
		return updater;
	}

	private boolean checkGateFactoryInitializationNessesary(Class<?>[] params)
	{
		return params.length==1 && params[0].isAssignableFrom(GateFactory.class);
	}

	private Map<String, String> parseParameters(Element element) throws Exception
	{
		final Map<String, String> params = new HashMap<String, String>();
		//считываем описани€ документов
		XmlHelper.foreach(element, "parameter", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute("name");
				String value = element.getAttribute("value");
				params.put(name, value);
			}
		}
		);
		return params;
	}

	/**
	 * «арегистрировать сендера
	 * @param documentClass - класс документа
	 * @param sender сендер дл€ класса документов documentClass
	 */
	private void registerSender(Class<? extends GateDocument> documentClass, DocumentSender sender)
	{
		senders.put(documentClass, sender);
	}

	private DocumentSender getDocumentSender(Class<? extends GateDocument> documentClass) throws GateException
	{
		DocumentSender documentSender = senders.get(documentClass);
		if (documentSender == null)
		{
			throw new GateException("Sender дл€ платежа " + documentClass.getName() + " не найден");
		}
		return documentSender;
	}

	public void recall(GateDocument document) throws GateException, GateLogicException
	{
		SystemWithdrawDocument withdrawDocument = new SystemWithdrawDocument(document);
		DocumentSender sender = getDocumentSender(document.getType());
		sender.rollback(withdrawDocument);
	}

	/**
	 * «арегистрировать обновл€тора
	 * @param documentClass - класс документа
	 * @param updater updater дл€ класса документов documentClass
	 */
	private void registerUpdater(Class<? extends GateDocument> documentClass, DocumentUpdater updater)
	{
		updaters.put(documentClass, updater);
	}

	private DocumentUpdater getDocumentUpdater(Class<? extends GateDocument> documentClass)
	{
		return updaters.get(documentClass);
	}

	/**
	 * «арегистрировать получател€ комиссии
	 * @param documentClass - класс документа
	 * @param calculator - получатель комиссии дл€ класса документов documentClass
	 */
	private void registerCommissionCalculator(Class<? extends GateDocument> documentClass, CommissionCalculator calculator)
	{
		commissionCalculators.put(documentClass, calculator);
	}

	/**
	 * @param documentClass - класс документа дл€ получени€ комиссии.
	 * @return поучатель комиссии.
	 */
	private CommissionCalculator getCommissionCalculator(Class<? extends GateDocument> documentClass)
	{
		return commissionCalculators.get(documentClass);
	}

	/**
	 * получить комиссию дл€ платежа.
	 *
	 * @param document документ
	 */
	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		CommissionCalculator commissionCalculator = getCommissionCalculator(document.getType());
		if (commissionCalculator == null)
		{
			throw new GateException("ѕравила расчета комиссии дл€ платежа " + document.getClass().getName() + "с типом " + document.getType().getName() + " не найдены");
		}
		commissionCalculator.calcCommission(document);
	}

	/**
	 *
	 * @param document документ
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		DocumentSender documentSender = getDocumentSender(document.getType());
		documentSender.send(document);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		DocumentSender documentSender = getDocumentSender(document.getType());
		documentSender.repeatSend(document);
	}

	/**
	 *
	 * @param document документ
	 * @return —осто€ние
	 */
	public StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException
	{
		DocumentUpdater documentUpdater = getDocumentUpdater(document.getType());
		if (documentUpdater == null )
			throw new GateException("Updater дл€ платежа " + document.getClass().getName() + " не найден");
		return documentUpdater.execute(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		DocumentSender documentSender = getDocumentSender(document.getType());
		documentSender.prepare(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		DocumentSender documentSender = getDocumentSender(document.getType());
		documentSender.confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		DocumentSender documentSender = getDocumentSender(document.getType());
		documentSender.validate(document);
	}

	private class RevokeSender implements DocumentSender
	{
		private Class<? extends WithdrawDocument> documentRevokeClass;
		private String revokeDocumentTypePropety;

		RevokeSender(Class<? extends WithdrawDocument> documentRevokeClass, String revokeDocumentTypePropety)
		{
			this.revokeDocumentTypePropety = revokeDocumentTypePropety;
			this.documentRevokeClass = documentRevokeClass;
		}

		public void setParameters(Map<String, ?> params)
		{
			//TODO realize
		}

		public void send(GateDocument document) throws GateException, GateLogicException
		{

			WithdrawDocument withdrawDocument = null;
			try
			{
				withdrawDocument = documentRevokeClass.cast(document);
			}
			catch (ClassCastException e)
			{
				throw new GateException("Ќеверный тип документа " + document.getClass().getName() + ". ќжидаетс€ " + documentRevokeClass.getName(), e);
			}
			Class<? extends GateDocument> revokeDocumentType = null;
			try
			{
				revokeDocumentType = (Class<? extends GateDocument>) PropertyUtils.getProperty(document, revokeDocumentTypePropety);
			}
			catch (IllegalAccessException e)
			{
				throw new GateException(e);
			}
			catch (InvocationTargetException e)
			{
				throw new GateException(e);
			}
			catch (NoSuchMethodException e)
			{
				throw new GateException(e);
			}
			DocumentSender documentSender = getDocumentSender(revokeDocumentType);
			documentSender.rollback(withdrawDocument);
		}

		public void prepare(GateDocument document) throws GateException, GateLogicException
		{
			throw new UnsupportedOperationException();
		}

		public void repeatSend(GateDocument document) throws GateException, GateLogicException
		{
			throw new UnsupportedOperationException();
		}

		public void rollback(WithdrawDocument document) throws GateException, GateLogicException
		{
			throw new UnsupportedOperationException();
		}

		public void confirm(GateDocument document) throws GateException, GateLogicException
		{
			throw new UnsupportedOperationException("¬озможность подтверждени€ платежа не реализована");
		}

		public void validate(GateDocument document) throws GateException, GateLogicException
		{
			throw new UnsupportedOperationException("¬озможность валидации платежа не реализована");
		}
	}
}
