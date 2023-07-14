<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="bundle" value="mobilebankBundle"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q16')}"/>
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<c:if test="${not empty form.smsCommands}">

<c:set var="smsRequest">
    SMS-запрос
    <c:if test="${not empty needHintMark}">
        <a class="imgHintBlock" title="Часто задаваемые вопросы" onclick="javascript:openFAQ('${faqLink}')"></a>
    </c:if>
</c:set>

    <sl:collection id="command" name="form" property="smsCommands" model="no-pagination" styleClass="depositProductInfo">
        <sl:collectionItem styleTitleClass="align-left" width="160px" styleClass="align-left" styleId="vertical-align: top" title="Операция">
            <span class="word-wrap"><bean:write name="command" property="name" ignore="true" filter="true"/></span>
        </sl:collectionItem>
        <sl:collectionItem styleTitleClass="align-left" styleClass="align-left" width="85px" styleId="vertical-align: bottom" title="${smsRequest}">
            <span class="word-wrap"><bean:write name="command" property="format" ignore="true"/></span>
        </sl:collectionItem>
    </sl:collection>

</c:if>
