package com.rssl.phizic.config;

import com.rssl.phizic.common.types.Application;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * класс для создания списка конфигов для приложения со списком геттеров.
 *
 * @author bogdanov
 * @ created 30.07.13
 * @ $Author$
 * @ $Revision$
 */

public class ConfigFactoryConfigUses
{
	private static ConfigFactoryConfigUses it = new ConfigFactoryConfigUses();

	private Set<Application> buildForApplication = new HashSet<Application>();
	private static final Set<String> classNames = new HashSet<String>();

	static
	{
		classNames.add("com.rssl.phizic.asfilial.listener.ASFilialConfig");
		classNames.add("com.rssl.phizic.business.accounts.AccountsConfig");
		classNames.add("com.rssl.phizic.business.accounts.AccountsConfigImpl");
		classNames.add("com.rssl.phizic.business.bankroll.BankrollRegistry");
		classNames.add("com.rssl.phizic.business.claims.config.PropertiesClaimsConfig");
		classNames.add("com.rssl.phizic.business.configuration.BeanConfigBase");
		classNames.add("com.rssl.phizic.business.dictionaries.DictionaryConfig");
		classNames.add("com.rssl.phizic.business.dictionaries.PropertiesDictionaryConfig");
		classNames.add("com.rssl.phizic.business.dictionaries.config.DictionaryPathConfig");
		classNames.add("com.rssl.phizic.business.dictionaries.config.SimpleDictionaryPathConfig");
		classNames.add("com.rssl.phizic.business.documents.templates.ConfigImpl");
		classNames.add("com.rssl.phizic.business.forms.types.BusinessTypesConfig");
		classNames.add("com.rssl.phizic.business.forms.types.SmsBusinessTypesConfig");
		classNames.add("com.rssl.phizic.business.log.operations.config.OperationDescriptorsConfig");
		classNames.add("com.rssl.phizic.business.log.operations.config.OperationDescriptorsConfigImpl");
		classNames.add("com.rssl.phizic.business.messaging.info.DistributionConfigImpl");
		classNames.add("com.rssl.phizic.business.messaging.info.DistributionMessagemakingConfig");
		classNames.add("com.rssl.phizic.business.operations.config.DbOperationsConfig");
		classNames.add("com.rssl.phizic.business.operations.config.OperationsConfig");
		classNames.add("com.rssl.phizic.business.operations.config.XmlOperationsConfig");
		classNames.add("com.rssl.phizic.business.persons.DataValidateConfig");
		classNames.add("com.rssl.phizic.business.persons.PersonCreateConfig");
		classNames.add("com.rssl.phizic.business.persons.PropertiesDataValidateConfig");
		classNames.add("com.rssl.phizic.business.persons.PropertiesPersonCreateConfig");
		classNames.add("com.rssl.phizic.business.providers.ProvidersConfig");
		classNames.add("com.rssl.phizic.business.schemes.AccessSchemesConfig");
		classNames.add("com.rssl.phizic.business.schemes.DbAccessSchemesConfig");
		classNames.add("com.rssl.phizic.business.schemes.XmlAccessSchemesConfig");
		classNames.add("com.rssl.phizic.business.web.WidgetConfig");
		classNames.add("com.rssl.phizic.business.xslt.lists.EntityListsConfig");
		classNames.add("com.rssl.phizic.business.xslt.lists.XmlEntitiesListsConfig");
		classNames.add("com.rssl.phizic.business.ermb.sms.config.SmsConfig");
		classNames.add("com.rssl.phizic.BankContext");
		classNames.add("com.rssl.phizic.BankContextConfig");
		classNames.add("com.rssl.phizic.JobRefreshConfig");
		classNames.add("com.rssl.phizic.ListenerConfig");
		classNames.add("com.rssl.phizic.TBRenameDictionary");
		classNames.add("com.rssl.phizic.TBSynonymsDictionary");
		classNames.add("com.rssl.phizic.config.LocalStoreConfigBase");
		classNames.add("com.rssl.phizic.config.OfficeCodeReplacer");
		classNames.add("com.rssl.phizic.config.ShopListenerConfig");
		classNames.add("com.rssl.phizic.config.StoredResourceRefreshTimeoutConfig");
		classNames.add("com.rssl.phizic.config.atm.AtmApiConfig");
		classNames.add("com.rssl.phizic.config.build.BuildContextConfig");
		classNames.add("com.rssl.phizic.config.build.BuildContextConfigImpl");
		classNames.add("com.rssl.phizic.config.build.MonitoringConfig");
		classNames.add("com.rssl.phizic.config.csa.CSAConfig");
		classNames.add("com.rssl.phizic.config.debug.DebugRefreshConfig");
		classNames.add("com.rssl.phizic.config.ermb.ErmbConfig");
		classNames.add("com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig");
		classNames.add("com.rssl.phizic.config.ips.IPSConfig");
		classNames.add("com.rssl.phizic.config.jmx.BarsConfig");
		classNames.add("com.rssl.phizic.config.loyalty.LoyaltyConfig");
		classNames.add("com.rssl.phizic.config.mobile.MobileApiConfig");
		classNames.add("com.rssl.phizic.jmx.BusinessSettingsConfig");
		classNames.add("com.rssl.phizic.jmx.MobileAPIConfigMBeanImpl");
		classNames.add("com.rssl.phizic.jmx.MobileBankConfig");
		classNames.add("com.rssl.phizic.messaging.mobilebank.MobileBankSmsConfig");
		classNames.add("com.rssl.phizic.utils.CardsConfig");
		classNames.add("com.rssl.phizic.utils.CardsConfigImpl");
		classNames.add("com.rssl.phizic.authgate.csa.CSAConfig");
		classNames.add("com.rssl.phizic.authgate.csa.CSARefreshConfig");
		classNames.add("com.rssl.auth.csa.back.Config");
		classNames.add("com.rssl.phizicgate.esberibgate.ESBEribConfig");
		classNames.add("com.rssl.common.forms.types.SimpleTypesConfig");
		classNames.add("com.rssl.common.forms.types.TypesConfig");
		classNames.add("com.rssl.common.forms.validators.passwords.PasswordValidationConfig");
		classNames.add("com.rssl.common.forms.validators.passwords.PasswordValidationConfigImpl");
		classNames.add("com.rssl.phizic.gate.GateConfig");
		classNames.add("com.rssl.phizic.gate.PropertiesGateConfig");
		classNames.add("com.rssl.phizic.gate.config.GateConnectedConfigImpl");
		classNames.add("com.rssl.phizic.gate.config.GateConnectedConfigImplMBean");
		classNames.add("com.rssl.phizic.gate.config.GateConnectionConfig");
		classNames.add("com.rssl.phizic.gate.config.GateSettingsConfig");
		classNames.add("com.rssl.phizic.gate.config.GateSettingsConfigImpl");
		classNames.add("com.rssl.phizic.gate.config.PropertiesPaymentsConfig");
		classNames.add("com.rssl.phizic.gate.config.SpecificGateConfig");
		classNames.add("com.rssl.phizic.gate.config.SpecificGateConfigImpl");
		classNames.add("com.rssl.phizic.gate.config.SpecificGateConfigImplMBean");
		classNames.add("com.rssl.phizic.gate.config.monitoring.MonitoringServicesConfiguration");
		classNames.add("com.rssl.phizic.gate.config.monitoring.MonitoringServicesConfigurationMBean");
		classNames.add("com.rssl.phizic.gate.payments.PaymentsConfig");
		classNames.add("com.rssl.phizgate.common.messaging.retail.RetailMessagingConfig");
		classNames.add("com.rssl.phizgate.common.messaging.retail.RetailMessagingConfigImpl");
		classNames.add("com.rssl.phizicgate.manager.config.AdaptersConfig");
		classNames.add("com.rssl.phizicgate.manager.config.AdaptersConfigImpl");
		classNames.add("com.rssl.phizic.gorod.messaging.GorodConfigImpl");
		classNames.add("com.rssl.phizic.logging.messaging.MessageLogConfig");
		classNames.add("com.rssl.phizic.logging.messaging.MessageLogConfigImpl");
		classNames.add("com.rssl.phizic.logging.operations.config.OperationsLogConfig");
		classNames.add("com.rssl.phizic.logging.operations.config.OperationsLogConfigImpl");
		classNames.add("com.rssl.phizic.logging.system.LogLevelConfig");
		classNames.add("com.rssl.phizic.logging.system.MockSystemLogConfig");
		classNames.add("com.rssl.phizic.logging.system.SystemLogConfig");
		classNames.add("com.rssl.phizic.logging.system.SystemLogConfigImpl");
		classNames.add("com.rssl.phizic.messaging.MessagingConfig");
		classNames.add("com.rssl.phizic.messaging.PropertiesMessagingConfig");
		classNames.add("com.rssl.phizic.messaging.mail.messagemaking.DistributionConfig");
		classNames.add("com.rssl.phizic.messaging.mail.messagemaking.MessagemakingConfig");
		classNames.add("com.rssl.phizicgate.mobilebank.MobileBankConfig");
		classNames.add("com.rssl.phizic.operations.loanOffer.UnloadConfig");
		classNames.add("com.rssl.phizic.operations.loanOffer.UnloadConfigImpl");
		classNames.add("com.rssl.phizic.operations.skins.SkinsConfig");
		classNames.add("ru.softlab.phizicgate.rsloansV64.junit.RSLoans64Config");
		classNames.add("com.rssl.phizicgate.sbrf.jmx.CodGateConfig");
		classNames.add("com.rssl.phizic.auth.modes.AuthenticationConfig");
		classNames.add("com.rssl.phizic.auth.modes.JAXBAuthenticationConfig");
		classNames.add("com.rssl.phizic.captcha.CaptchaConfig");
		classNames.add("com.rssl.phizic.security.config.SecurityConfig");
		classNames.add("com.rssl.phizic.security.config.SimpleSecurityConfig");
		classNames.add("com.rssl.phizic.self.registration.SelfRegistrationConfig");
		classNames.add("com.rssl.phizic.web.ERIBFrontConfig");
		classNames.add("com.rssl.phizic.web.PropertiesWebPageConfig");
		classNames.add("com.rssl.phizic.web.WebPageConfig");
		classNames.add("com.rssl.phizic.config.CSAFrontConfig");
		classNames.add("com.rssl.phizic.config.ermb.ErmbSubscribeFeeConfig");
		classNames.add("com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig");
		classNames.add("com.rssl.phizic.business.etsm.offer.service.EtsmConfig");
	}

	public static <T extends Config> void getInfo(Application application)
	{
		if (it.buildForApplication.contains(application))
			return;

		try
		{
			it.build(application);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void build(Application application) throws IOException
	{
		it.buildForApplication.add(application);

		FileWriter fw = new FileWriter("D:\\work\\configsTest\\config_" + application.name() + ".jsp");
		FileWriter fw2 = new FileWriter("D:\\work\\configsTest\\config_" + application.name() + "Action.java");

		StringBuilder jspBuilder = new StringBuilder(1024);
		StringBuilder javaBuilder = new StringBuilder(1024);
		beginJSP(jspBuilder, application);
		beginJava(javaBuilder);
		for (String clazz : classNames)
		{
			addClass(clazz, jspBuilder, javaBuilder);
		}
		endJava(javaBuilder);
		endJSP(jspBuilder);
		fw.append(jspBuilder.toString());
		fw2.append(javaBuilder.toString());
		fw2.flush();
		fw2.close();
		fw.flush();
		fw.close();
	}

	private void beginJSP(StringBuilder dos, Application application) throws IOException
	{
		dos.append("<%@ page contentType=\"text/html;charset=windows-1251\" language=\"java\" %>\n" +
				"<%@ taglib uri=\"http://jakarta.apache.org/struts/tags-bean\" prefix=\"bean\" %>\n" +
				"<%@ taglib uri=\"http://jakarta.apache.org/struts/tags-html\" prefix=\"html\" %>\n" +
				"<%@ taglib uri=\"http://jakarta.apache.org/struts/tags-tiles\" prefix=\"tiles\" %>\n" +
				"<%@ taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>\n" +
				"<%@ taglib uri=\"http://rssl.com/tags\" prefix=\"phiz\" %>\n" +
				"\n" +
				"<html:form action=\"/config/test" + application + "\" onsubmit=\"return setEmptyAction(event);\">\n" +
				"<c:set var=\"form\" value=\"${phiz:currentForm(pageContext)}\"/>\n" +
				"<table>\n");
	}

	private void beginJava(StringBuilder dos) throws IOException
	{
		dos.append("public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception\n" +
				"{\n");
	}

	private void endJava(StringBuilder dos) throws IOException
	{
		dos.append("}");
	}

	private void endJSP(StringBuilder dos) throws IOException
	{
		dos.append("</table></html:form>");
	}

	private void addClass(String clazz, StringBuilder dos, StringBuilder dosa)
	{
		try
		{
			ConfigFactoryHelper.getConfigFactoryDocument().getConfig(clazz);
		}
		catch (ConfigurationException ex)
		{
			return;
		}

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try
		{
			Class clss = loader.loadClass(clazz);
			for (Method method : clss.getMethods())
			{
				String name = method.getName();
				if (!name.startsWith("get") || name.startsWith("getPropert") || name.equals("getAllProperties") || name.equals("configLastUpdateTime") || name.equals("getUpdatePeriod") || name.equals("getClass"))
					continue;

				String fieldName = clss.getName() + "_" + name;
				dos.append("<tr><td>" + fieldName + "</td><td>" + "<c:out value=\"${form.fields['" + fieldName + "']}\"/>" + "</td></tr>\n");
				dosa.append("\tfrm.setField(\"" + fieldName + "\", ConfigFactory.getConfig(" + clss.getSimpleName() + ".class)." + name + "());\n");
			}
		}
		catch (ClassNotFoundException ignore)
		{
		}
	}
}
