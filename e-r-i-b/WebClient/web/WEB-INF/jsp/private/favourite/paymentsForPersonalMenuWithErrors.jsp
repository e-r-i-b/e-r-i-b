<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<html:form action="/private/async/favourite/listAutoPaymentsForPersonalMenu" onsubmit="return setEmptyAction(event)">
<c:set var="autoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/favourite/list/AutoPayments.do")}</c:set>
<c:set var="addAutoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/autopayment/select-category-provider.do")}</c:set>
<phiz:linksList num="0" styleClass="underlined" listSourceName="autoPaymentsLinkList" title="Мои автоплатежи" titleStyleClass="linksListTitle">
    <c:set var="titleTag" value="Вы не можете посмотреть Ваши автоплатежи. Пожалуйста, повторите попытку позже."/>
    <phiz:linksListItem title="${titleTag}" styleClass="note"/>
    <c:set var="titleTag" value="<span>Подключить автоплатеж</span>"/>
    <phiz:linksListItem title="${titleTag}" styleClass="templateAndPaymentControl" href="${addAutoPaymentsURL}" onClick="return redirectResolved();"/>
    <c:set var="titleTag" value="<span>Управление автоплатежами</span>"/>
    <phiz:linksListItem title="${titleTag}" styleClass="templateAndPaymentControl" href="${autoPaymentsURL}" onClick="return redirectResolved();"/>
</phiz:linksList>
</html:form>