package com.rssl.phizic.business.payments.forms.utils;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.FormParser;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.*;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.xml.ClassPathURIResolver;
import com.rssl.phizic.utils.xml.XmlFileReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 28.10.2005
 * Time: 18:30:56
 */
public class PaymentFormsLoader
{
	private static class CompositeFormInfo
    {
        private MetadataBean paymentMetadata;
	    private List<PaymentFormTransformation> transformations;
	    private PaymentListMetadataBean paymentListMetadataBean;

	    CompositeFormInfo(MetadataBean paymentMetadata, List<PaymentFormTransformation> transformations, PaymentListMetadataBean paymentListMetadataBean)
        {
            this.paymentMetadata = paymentMetadata;
	        this.transformations = transformations;
	        this.paymentListMetadataBean = paymentListMetadataBean;
        }
    }

	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    //*********************************************************************//
    //***************************  CLASS MEMBERS  *************************//
    //*********************************************************************//

	private static final String FORM_NAME_SUBSTR             = ".pfd.";
	private static final String XML_TRANSFORMER_NAME_SUFIX   = ".xml.xslt";
	private static final String TRANSFORMER_NAME_SUFIX = ".%s.xslt";
    private static final String LIST_FORM_NAME_SUBSTR = ".list-pfd.";
    private static final String LIST_HTML_TRANSFORMER_NAME_SUFIX = ".list-html.xslt";
	private static final String LIST_FILTER_HTML_TRANSFORMER_NAME_SUFIX = ".listFilter-html.xslt";
	private static final String STATE_MACHINE_SUFIX = ".state-machine.xml";
	private static final String TEMPLATE_STATE_MACHINE_SUFIX = ".template-state-machine.xml";
	private static final String IMPORT_NAME_SUFFIX = ".template.xslt";

	//generators
	private static final String DEFAULT_HTML_TRANSFORMER =
	        "com/rssl/phizic/business/payments/forms/resources/default-html-xslt-generator.xslt";
	private static final String DEFAULT_HTML_LIST_TRANSFORMER =
			"com/rssl/phizic/business/payments/forms/resources/default-list-html-xslt-generator.xslt";
	private static final String DEFAULT_HTML_FILTERLIST_TRANSFORMER =
			"com/rssl/phizic/business/payments/forms/resources/default-filterList-html-xslt-generator.xslt";
	private static final String FORMS_RESOURCES_PATH =
	        "com/rssl/phizic/business/payments/forms/resources/";

	private static final PaymentFormService FORM_SERVICE = new PaymentFormService();
	private static final ServiceService SERVICE_SERVICE = new ServiceService();

	private static final String IMPORTS_PATH = "payments/imports";

	/**
	 * default ctor
	 */
	public PaymentFormsLoader() throws BusinessException
	{
	}

	private List<CompositeFormInfo> getForms(String path) throws Exception
    {
        return getForms(path, new ArrayList<CompositeFormInfo>());
    }

    private List<CompositeFormInfo> getForms(String path, List<CompositeFormInfo> forms) throws Exception
    {
        File node = new File(path);
	    if ( node.isHidden() )
	    {
		    return forms;
	    }

	    if( node.isDirectory() )
        {
            MetadataBean paymentMetadata = setMetadataBean(node);
	        List<PaymentFormTransformation> transformations = setTransformations(node);
	        PaymentListMetadataBean paymentListMetadataBean = setListFormProperties(node);
	        if ( paymentMetadata != null )
		        forms.add(new CompositeFormInfo(paymentMetadata, transformations, paymentListMetadataBean));

	        String[] children = node.list();
	        for (String s : children)
            {
                getForms(path + "/" + s, forms);
            }
        }
        return forms;
    }

    private MetadataBean setMetadataBean(File directory)
    {
	    MetadataBean paymentMetadata = null;

	    File[] children = directory.listFiles(getFilenameFilter(FORM_NAME_SUBSTR));
	    if(children != null && children.length > 0 )
        {
	        paymentMetadata = new MetadataBean();
	        try
            {
	            File definitionFile = children[0];
	            String formFileName = definitionFile.getName();

	            String formName = formFileName.substring(0, formFileName.indexOf(FORM_NAME_SUBSTR));
	            String definition = new XmlFileReader(definitionFile).readString(); //PFD
	            File xmlTransformationFile = new File(directory, formName + XML_TRANSFORMER_NAME_SUFIX);
	            File stateMachineFile = new File(directory, formName + STATE_MACHINE_SUFIX);
	            File templateStateMachineFile = new File(directory, formName + TEMPLATE_STATE_MACHINE_SUFIX);

	            //metadata
	            paymentMetadata.setName(formName);
	            paymentMetadata.setDefinition(definition);
	            paymentMetadata.setXmlTransformation( new XmlFileReader(xmlTransformationFile).readString() );

	            if (stateMachineFile.exists())
		            paymentMetadata.setStateMachine(new XmlFileReader(stateMachineFile).readString());

	            if (templateStateMachineFile.exists())
	            {
		            paymentMetadata.setTemplateStateMachine(new XmlFileReader(templateStateMachineFile).readString());
	            }

	            FormParser parser = new FormParser();
	            parser.parse(definition);
	            paymentMetadata.setDescription(parser.getForm().getDescription());
            }
            catch(Exception e)
            {
                paymentMetadata = null;
                log.error("Failed to parse form from directory " + directory.getName(), e);
            }

        }
        return paymentMetadata;
    }

	private List<PaymentFormTransformation> setTransformations(File directory)
    {
	    List<PaymentFormTransformation> transformations = new ArrayList<PaymentFormTransformation>();

	    File[] children = directory.listFiles(getFilenameFilter(FORM_NAME_SUBSTR));
	    if(children != null && children.length > 0 )
        {
	        try
            {
	            File definitionFile = children[0];
	            String formFileName = definitionFile.getName();
	            String formName = formFileName.substring(0, formFileName.indexOf(FORM_NAME_SUBSTR));
	            MetadataBean form = new MetadataBean();
	            form.setName(formName);

	            //html
	            File htmlTransformationFile = new File(directory, formName + String.format(TRANSFORMER_NAME_SUFIX, TransformType.html));
	            if ( htmlTransformationFile.exists() )
	            {
		            transformations.add(new PaymentFormTransformation(form, TransformType.html, new XmlFileReader(htmlTransformationFile).readString()));
	            }
	            else
	            {
		            InputStream defaultHtmlTransformation = ClassHelper.getInputStreamFromClassPath(DEFAULT_HTML_TRANSFORMER);
		            String content = generateDefaultFormHtml(defaultHtmlTransformation, definitionFile);
		            transformations.add(new PaymentFormTransformation(form, TransformType.html, content));
	            }

	            //print
	            File printTransformationFile = new File(directory, formName + String.format(TRANSFORMER_NAME_SUFIX, TransformType.print));
	            if (printTransformationFile.exists())
	                transformations.add(new PaymentFormTransformation(form, TransformType.print, new XmlFileReader(printTransformationFile).readString()));

	            //atm
	            File atmTransformationFile = new File(directory, formName + String.format(TRANSFORMER_NAME_SUFIX, TransformType.atm));
	            if (atmTransformationFile.exists())
	                transformations.add(new PaymentFormTransformation(form, TransformType.atm, new XmlFileReader(atmTransformationFile).readString()));

	            //check
	            File checkTransformationFile = new File(directory, formName + String.format(TRANSFORMER_NAME_SUFIX, TransformType.check));
	            if (checkTransformationFile.exists())
	                transformations.add(new PaymentFormTransformation(form, TransformType.check, new XmlFileReader(checkTransformationFile).readString()));

	            //mobile
	            for (Application application : ApplicationUtil.getMobileApiApplicationList())
	            {
		            String typeName = application.name();
		            try
		            {
			            TransformType type = TransformType.valueOf(typeName);
			            File mApiTransformationFile = new File(directory, formName + String.format(TRANSFORMER_NAME_SUFIX, type));
			            if (mApiTransformationFile.exists())
			                transformations.add(new PaymentFormTransformation(form, type, new XmlFileReader(mApiTransformationFile).readString()));
		            }
		            catch (IllegalArgumentException e)
		            {
			            //mAPI добавлен в Application, но в TransformType забыли => сохран€ем остальное, не пада€
			            log.error("Ќе найден тип преобразовани€ " + typeName, e);
		            }
	            }

	            //social
	            File socialTransformationFile = new File(directory, formName + String.format(TRANSFORMER_NAME_SUFIX, TransformType.social));
	            if (socialTransformationFile.exists())
		            transformations.add(new PaymentFormTransformation(form, TransformType.social, new XmlFileReader(socialTransformationFile).readString()));
            }
            catch(Exception e)
            {
                log.error("Failed to parse form from directory " + directory.getName(), e);
            }
        }
        return transformations;
    }

    private PaymentListMetadataBean setListFormProperties(File directory) throws Exception
    {
	    PaymentListMetadataBean paymentListMetadataBean = null;

	    File[] children = directory.listFiles(getFilenameFilter(LIST_FORM_NAME_SUBSTR));
	    if (children != null && children.length > 0)
        {
            paymentListMetadataBean = new PaymentListMetadataBean();
            try
            {
	            File definitionFile = children[0];
	            String formFileName = definitionFile.getName();

				String formName = formFileName.substring(0, formFileName.indexOf(LIST_FORM_NAME_SUBSTR));
				File listHtmlTransformationFile = new File(directory, formName + LIST_HTML_TRANSFORMER_NAME_SUFIX);
				File listFilterHtmlTransformationFile = new File(directory, formName + LIST_FILTER_HTML_TRANSFORMER_NAME_SUFIX);

				paymentListMetadataBean.setDefinition( new XmlFileReader(definitionFile).readString() );
				if( listHtmlTransformationFile.exists() )
				{
	                paymentListMetadataBean.setListTransformation( new XmlFileReader(listHtmlTransformationFile).readString() );
				}
				else
				{
					InputStream defaultHtmlTransformation =
							ClassHelper.getInputStreamFromClassPath(DEFAULT_HTML_LIST_TRANSFORMER);
					String content = generateDefaultFormHtml(defaultHtmlTransformation, definitionFile);
					paymentListMetadataBean.setListTransformation(content);
				}

	            if( listFilterHtmlTransformationFile.exists() )
	            {
	                paymentListMetadataBean.setFilterTransformation( new XmlFileReader(listFilterHtmlTransformationFile).readString());
	            }
	            else
	            {
		            InputStream defaultHtmlTransformation =
				            ClassHelper.getInputStreamFromClassPath(DEFAULT_HTML_FILTERLIST_TRANSFORMER);
		            String content = generateDefaultFormHtml(defaultHtmlTransformation, definitionFile);
		            paymentListMetadataBean.setFilterTransformation(content);
	            }
            }
            catch (Exception e)
            {
                paymentListMetadataBean = null;
                log.error("Failed to read payments' list form from ", e);
            }
        }
        return paymentListMetadataBean;
    }


	private String generateDefaultFormHtml(InputStream htmlTransformation, File pfdFile) throws BusinessException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);

		try
		{
			transformerFactory.setURIResolver(new ClassPathURIResolver(FORMS_RESOURCES_PATH));
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(htmlTransformation));
			transformer.transform(new StreamSource(pfdFile), streamResult);
		}
		catch (Exception e)
		{
			throw new BusinessException("ќшибка генерации преобразовани€ по умолчанию! ", e);
		}

		return stringWriter.getBuffer().toString();
	}

	private List<PaymentFormImport> getImports(String configurationPath)
	{
		List<PaymentFormImport> imports = new ArrayList<PaymentFormImport>();

		//файлы импорта обновл€ем независимо от того загружаетс€ одна форма или все
		String subPath = "/Business/settings/";
		String path = configurationPath.substring(0, configurationPath.lastIndexOf(subPath) + subPath.length());
		File directory = new File(path + IMPORTS_PATH);
		File[] files = directory.listFiles(getFilenameFilter(IMPORT_NAME_SUFFIX));
		if (ArrayUtils.isNotEmpty(files))
		{
			for (File file : files)
			{
				try
				{
					PaymentFormImport pfi = new PaymentFormImport(file.getName(), new XmlFileReader(file).readString());
					imports.add(pfi);
				}
				catch (Exception e)
				{
					log.error("Failed to parse import file " + file.getName(), e);
				}
			}
		}
		return imports;
	}

	/**
	 * «агрузка форм
	 * @param globalPath путь каталога дл€ поиска и загрузки основных форм
	 * @param configurationPath путь каталога дл€ поиска и загрузки индивидуальных дл€ конфигурации форм
	 * @throws BusinessException
	 */
	public void saveFormsDataFromPath(String globalPath, String configurationPath) throws BusinessException
    {
	    List<CompositeFormInfo> globalFormsData=null;
	    List<CompositeFormInfo> configurationFormsData=null;

	    try
	    {
		    if (globalPath!=null)
		        globalFormsData = getForms(globalPath);
		    configurationFormsData = getForms(configurationPath);
	    }
	    catch (Exception e)
	    {
		    throw new BusinessException(e);
	    }

	    if (globalPath!=null)
	    {

			//заполн€ем таблицу форм платежей и за€вок общими формами (из globals)
			for ( CompositeFormInfo globalForm : globalFormsData )
			{
				final String globalMetadataName = globalForm.paymentMetadata.getName();
				if ( SERVICE_SERVICE.findByKey(globalMetadataName) != null )
				{
					for ( Iterator iterator = configurationFormsData.iterator(); iterator.hasNext(); )
					{
						final CompositeFormInfo configForm = (CompositeFormInfo) iterator.next();
						final String configurationMetadataName = configForm.paymentMetadata.getName();

						//если есть в текущем конфиге => берем оттуда, а не из globals
						if ( configurationMetadataName.equals(globalMetadataName) )
						{
							saveFormData(configForm);
							iterator.remove();
							break;
						}
						//в текущем конфиге не нашли => берем из globals
						if ( !iterator.hasNext() )
						{
							saveFormData(globalForm);
						}
					}
				}
				else
				{
					System.out.println("[PaymentFormsLoader.saveFormsDataFromPath] не найден сервис по ключу " + globalMetadataName + ". ѕроверь operations.xml и повтори попытку!!!");
				}
			}
	    }

	    //заполн€ем таблицу форм платежей и за€вок формами, актуальными только дл€ данной конфигурации
	    for (CompositeFormInfo configForm : configurationFormsData )
	    {
			final String metadataName = configForm.paymentMetadata.getName();
		    if (SERVICE_SERVICE.findByKey(metadataName) != null)
		        saveFormData(configForm);
		    else
			    System.out.println("[PaymentFormsLoader.saveFormsDataFromPath] не найден сервис по ключу " + metadataName + ". ѕроверь operations.xml и повтори попытку!!!");
	    }

	    //заполн€ем таблицу файлов импорта
	    List<PaymentFormImport> imports = getImports(configurationPath);
	    for (PaymentFormImport pfi : imports)
	    {
		    saveImport(pfi);
	    }
    }

	private void saveFormData(CompositeFormInfo formInfo) throws BusinessException
	{
		final MetadataBean metadata = formInfo.paymentMetadata;
		final List<PaymentFormTransformation> transformations = formInfo.transformations;
		final PaymentListMetadataBean listForm = formInfo.paymentListMetadataBean;

		final String name = metadata.getName();
		final MetadataBean result = FORM_SERVICE.findByName(name);
		final List<PaymentFormTransformation> transformationsResult = FORM_SERVICE.findTransformationsByFormName(name);
		final PaymentListMetadataBean listFormResult = FORM_SERVICE.findListFormData(name);

		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
		            if (result != null)
		            {
			            copyPaymentMetadata(result, metadata);

		                session.update(result);
		                log.info("metadata " + name + " updated");
		            }
		            else
		            {
		                session.save(metadata);
		                log.info("metadata " + name + " added");
		            }

		            if(listForm != null)
		            {
			            listForm.setName(name);
			            if( listFormResult != null)
		                {
			                copyPaymnetListMetadata(listFormResult, listForm);

						    session.update(listFormResult);
						    log.info("listForm " + name + " updated");
		                }
			            else
			            {
				            session.save(listForm);
				            log.info("listForm " + name + " added");
			            }
		            }

					for (PaymentFormTransformation transform : transformations)
					{
						final int i = transformationsResult == null ? -1 : transformationsResult.indexOf(transform);
						if (i >= 0)
						{
							final PaymentFormTransformation transformResult = transformationsResult.get(i);
							transformResult.setTransformation(transform.getTransformation());

							session.update(transformResult);
							log.info("transformation " + name + "." + transformResult.getType().name() + " updated");
						}
						else
						{
							transform.setForm(result != null ? result : metadata);

							session.save(transform);
							log.info("transformation " + name + "." + transform.getType().name() + " added");
						}
					}
		            return null;
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	private void saveImport(final PaymentFormImport pfi) throws BusinessException
	{
		final PaymentFormImport saved = FORM_SERVICE.findImportByName(pfi.getName());
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (saved != null)
					{
						saved.setBody(pfi.getBody());

						session.update(saved);
						log.info("template xslt " + pfi.getName() + " updated");
					}
					else
					{
						session.save(pfi);
						log.info("template xslt " + pfi.getName() + " added");
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void copyPaymnetListMetadata(PaymentListMetadataBean listFormResult, PaymentListMetadataBean listForm)
	{
		listFormResult.setDefinition( listForm.getDefinition() );
		listFormResult.setListTransformation( listForm.getListTransformation() );
		listFormResult.setFilterTransformation( listForm.getFilterTransformation() );
	}

	private void copyPaymentMetadata(MetadataBean result, MetadataBean metadata)
	{
		result.setName( metadata.getName() );
		result.setDefinition( metadata.getDefinition() );
		result.setXmlTransformation( metadata.getXmlTransformation() );
		result.setDescription( metadata.getDescription() );
		result.setStateMachine( metadata.getStateMachine() );
		result.setStateMachine( metadata.getStateMachine() );
		result.setTemplateStateMachine(metadata.getTemplateStateMachine() );
	}

	private FilenameFilter getFilenameFilter(final String partOfFileName)
	{
	    return new FilenameFilter()
	    {
	        public boolean accept(File dir, String name)
	        {
	            String fileName = new File(name).getName();
	            return fileName.indexOf(partOfFileName) != -1;
	        }
	    };
	}

	/**
	 * —охранить формы списков и платежа.
	 * @param form pfd
	 * @param xmlForm xml.xslt
	 * @param listForm list pfd
	 * @param htmlListForm html.xslt списка
	 * @param htmlListFilterForm html.xslt фильтра списка
	 * @param transformationMap карта < тип преобразовани€, преобразование >
	 * @return сохраненные метеданные формы
	 */
	public MetadataBean saveForm(String form,
	                             String xmlForm,
	                             String listForm,
	                             String htmlListForm,
	                             String htmlListFilterForm,
	                             Map<TransformType, String> transformationMap) throws BusinessException
	{
		MetadataBean paymentMetadata = new MetadataBean();
		Document document;

		try
		{
			document = XmlHelper.parse(form);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}

		String formName = document.getDocumentElement().getAttribute("name");
		paymentMetadata.setName(formName);
		paymentMetadata.setDefinition(form);
		paymentMetadata.setXmlTransformation(xmlForm);
		paymentMetadata.setDescription(document.getDocumentElement().getAttribute("description"));

		PaymentListMetadataBean paymentListMetadataBean = new PaymentListMetadataBean();
		paymentListMetadataBean.setDefinition(listForm);
		paymentListMetadataBean.setListTransformation(htmlListForm);
		paymentListMetadataBean.setFilterTransformation(htmlListFilterForm);

		List<PaymentFormTransformation> transformations = new ArrayList<PaymentFormTransformation>();
		if (transformations != null)
			for (Map.Entry<TransformType, String> transformEntry : transformationMap.entrySet())
				transformations.add(new PaymentFormTransformation(paymentMetadata, transformEntry.getKey(), transformEntry.getValue()));

		saveFormData(new CompositeFormInfo(paymentMetadata, transformations, paymentListMetadataBean));

		return paymentMetadata;
	}

}
