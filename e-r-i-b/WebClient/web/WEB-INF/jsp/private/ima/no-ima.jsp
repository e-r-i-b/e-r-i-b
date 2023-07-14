<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="data">
        <p>Обезличенные металлические счета, открытые в Сбербанке России, - это выгодное вложение Ваших денег.</p>

        <p>Пока у Вас нет ни одного металлического счёта. Вы можете открыть металлический счет, щелкнув по ссылке
        <phiz:link action="/private/ima/products/list.do?form=IMAOpeningClaim" styleClass="orangeText"><span>"Открытие обезличенного металлического счета"</span></phiz:link>.</p>

        <p>Получить более подробную информацию можно
            <html:link href="http://sbrf.ru/ru/person/contributions/"
                       target="_blank"
                       styleClass="text-green orangeText">
                <span>здесь.</span>
            </html:link>
        </p>
    </tiles:put>
</tiles:insert>