<%@ page import="com.rssl.phizic.test.externalSystem.monitoring.MonitoringESRequestServlet" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Test contents</title></head>
<script type="text/javascript">
    function runPFR()
    {
        var url;

        url = document.getElementById("urlPFR");
        window.location = "${phiz:calculateActionURL(pageContext,'/pfr')}" + "?url=" + url.value;
    }
</script>
<body>
<h3>Test contents</h3>
<ul>
    <% if (MonitoringESRequestServlet.isStarted()){ %>
        <li><a href='${phiz:calculateActionURL(pageContext,'/externalSystem/monitoring')}'>Мониторинг запросов к ВС</a></li>
    <% } %>
    <li><a href='${phiz:calculateActionURL(pageContext,'/cod')}'> Test COD Listener </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/cod2esk')}'> COD to ERIB Web-Services Listener TEST </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/esk2cpfl')}'> ESK to CPFL Web-Services TEST </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/gorod')}'> Test Gorod </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/quartz')}'> Test Jobs (quartz) </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/sl')}'> Test Grid (sl:collection) </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/tree')}'> Test tree view </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/rapida')}'> Test Рапида</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/cms')}'> Test WebService CMS </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/wsgate')}'> Test WSGATE </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/esberib')}'> ESB ERIB Adapter test </a></li>
    <li>Test MDM
        (
            <a href='${phiz:calculateActionURL(pageContext,'/mdm?mockMode=profileAndProduct')}'>notify profileAndProduct</a>
        )
    </li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/jndi')}'> JNDI test </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/jms')}'> JMS test </a>/<a href='${phiz:calculateActionURL(pageContext,'/jms/queue')}'> JMS input output queues test </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/autopayToEribQueue')}'> Basket JMS test </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/ntlm')}'> NTLM test </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/eskToIqwave')}'> SBOL to IQWAVE Web-Services
        TEST </a>
    </li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/iqwaveToEsk')}'> IQWAVE to SBOL Web-Services
        TEST </a>
    </li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/depoAndAutopay')}'>test DocStateUpdate and SecDicInfo</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/mbv')}'>Test MBV WebService</a></li>

    <c:url var="url" value="/mobile5.do">
        <c:param name="url" value="http://localhost:8888/mobile5"/>
        <c:param name="params" value="version=5.30&appType=iPhone&appVersion=82&deviceName=SiemensA35&operation=button.login&&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3"/>
    </c:url>
    <li><a href='${url}'> mAPI v5.x test </a></li>

    <c:url var="url" value="/mobile6.do">
        <c:param name="url" value="http://localhost:8888/mobile6"/>
        <c:param name="params"
                 value="version=6.00&appType=iPhone&appVersion=82&deviceName=SiemensA35&operation=button.login&&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3&devID=F5F276B4BF71039948B9590B21E3B2FE"/>
    </c:url>
    <li><a href='${url}'> mAPI v6.x test </a></li>

    <c:url var="url" value="/mobile7.do">
        <c:param name="url" value="http://localhost:8888/CSAMAPI"/>
        <c:param name="params"
                 value="version=7.00&appType=iPhone&appVersion=82&deviceName=SiemensA35&operation=button.login&&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3&devID=F5F276B4BF71039948B9590B21E3B2FE"/>
    </c:url>
    <li><a href='${url}'> mAPI v7.x test </a></li>

    <c:url var="url" value="/mobile8.do">
        <c:param name="url" value="http://localhost:8888/CSAMAPI"/>
        <c:param name="params"
                 value="version=8.00&appType=iPhone&appVersion=82&deviceName=SiemensA35&operation=button.login&&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3&devID=F5F276B4BF71039948B9590B21E3B2FE"/>
    </c:url>
    <li><a href='${url}'> mAPI v8.x test </a></li>

    <c:url var="url" value="/mobile9.do">
        <c:param name="url" value="http://localhost:8888/CSAMAPI"/>
        <c:param name="params"
                 value="version=9.00&operation=button.login&appType=iPhone&appVersion=82&deviceName=SiemensA35&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3&devID=F5F276B4BF71039948B9590B21E3B2FE&mobileSdkData=mobileSdkDataExample"/>
    </c:url>
    <li><a href='${url}'> mAPI v9.x test </a></li>

    <c:url var="url" value="/socialApi.do">
        <c:param name="url" value="http://localhost:8888/CSASocialAPI"/>

        <%--
           - appType - Тип приложения
           - devID   - Внешний идентификатор клиента
           --%>
        <c:param name="params" value="appType=vkontakte&operation=button.login&mGUID=4e61ee1b04558f39b5ed0e409169af19&extClientID=5465478654"/>
    </c:url>
    <li><a href="${url}">Social Api</a></li>

    <c:url var="url" value="/atm.do">
        <c:param name="url" value="http://localhost:8888/CSAATM"/>
        <c:param name="params" value="operation=login&pan=0396521324057616&codeATM=qwe123&atmRegionCode=&isChipCard="/>
    </c:url>
    <li><a href='${url}'> ATM </a></li>

    <c:url var="url" value="/webapi.do">
        <c:param name="url" value="http://localhost:8888/WebAPI"/>
        <c:param name="params" value="ip=&token="/>
    </c:url>
    <li><a href='${url}'> WebAPI </a></li>

    <li><a href='#' onclick="runPFR();"> PFR test </a>
        <input type="text" name="urlPFR" id="urlPFR" size="70"
               value="http://localhost:8888/ESBERIBListener/axis-services/PfrDoneServicePort"/>
    </li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/shopAll')}'> ShopTest </a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/packetEPD')}'> Test FNS </a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/uec')}'> Test UEC </a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/auth')}'> AUTH</a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/ermb/sms/sendsms')}'>ЕРМБ СМС-канал: Отправка СМС</a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/ermb/update/updatePhones')}'>ЕРМБ: регистрация или удаление телефонов</a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/mbProviders')}'> Test Mobile Bank Providers (ERMB)</a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/asfilial')}'> Test ASFilial</a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/ermb/update/updateclient')}'>Оповещение ЕРИБ об изменении данных клиента</a></li>

    <li><a href='${phiz:calculateActionURL(pageContext,'/ermb/update/updateresource')}'>Оповещение ЕРИБ об изменении продукта клиента</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/ermb/update/servicefeeresult')}'>Оповещение ЕРИБ о списании абонентской платы</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/rates')}'>Обновление курсов валют</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/limits')}'>Приложение для работы с лимитами</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/crm/loanclaim/messages')}'>Тест кредитных заявок</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/sbnkd')}'> Сбербанк на каждый день </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/monitoring/fraud')}'>Тестовая страница шлюза к ВС ФМ</a></li>
</ul>
</body>
</html>
