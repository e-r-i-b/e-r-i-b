<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="title" value="Мобильный банк"/>
    <tiles:put name="data">

        <p class="textHead">Услуга «Мобильный банк» позволит Вам выполнять операции по картам, используя SMS-сообщения:
            оплачивать услуги, запрашивать баланс, блокировать карты и совершать многие другие операции.</p>

        <c:if test="${phiz:impliesService('MobileBankRegistration')}">
            <p class="textHead">Подключите услугу
                <a class="orangeText" target="_blank"
                   href="http://sberbank.ru/ru/person/dist_services/inner_mbank/"
                   title="Условия предоставления услуги"><span>Мобильный банк</span></a>
                (полный или экономный пакет).
                Для этого нажмите на ссылку
                <html:link styleClass="orangeText" action="/private/register-mobilebank/start"><span>Подключить</span></html:link>.
            </p>
        </c:if>
    </tiles:put>
</tiles:insert>