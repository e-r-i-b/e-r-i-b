<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--<c:set var="form" value="${phiz:currentForm(pageContext)}"/>--%>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="data">
        <p>Вклады, открытые в Сбербанке России, - это наиболее выгодное вложение Ваших денег под высокие проценты.</p>

        <p>Пока у Вас нет ни одного вклада. Для того чтобы открыть интересующий Вас вклад, нажмите на ссылку
            <c:choose>
                <c:when test="${phiz:impliesService('AccountOpeningClaim')}">
                    <html:link href="${phiz:calculateActionURL(pageContext, '/private/deposits/products/list')}?form=AccountOpeningClaim" styleClass="text-green orangeText"><span>«Открыть вклад»</span></html:link>
                </c:when>
                <c:otherwise>«Открыть вклад»</c:otherwise>
            </c:choose>
            или обратитесь в ближайшее отделение банка.</p>
    </tiles:put>
</tiles:insert>