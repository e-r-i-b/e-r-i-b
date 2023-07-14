<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html"  %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>

&nbsp;
<tiles:importAttribute/>
<html:form action="/private/async/cards/editEmailDelivery" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="stateCode" value="${form.document.state.code}"/>

    <h1><bean:message bundle="cardInfoBundle" key="email.report.delivery.win.title.${stateCode}"/></h1>

    <c:choose>
        <c:when test="${stateCode eq 'SENDED'}">
            <tiles:insert definition="informMessageBlock" flush="false">
                <tiles:put name="regionSelector" value="informMessage${status.index}"/>
                <tiles:put name="color" value="infMesOrange"/>
                <tiles:put name="data">
                    <bean:message bundle="cardInfoBundle" key="email.report.delivery.win.label.SENDED"/>
                    <html:link action="/private/payments/common" styleClass="MenuItem" title="Истрория операций">
                        <bean:message bundle="cardInfoBundle" key="email.report.delivery.win.label.operation.history"/>
                    </html:link>.
                </tiles:put>
            </tiles:insert>
        </c:when>
        <c:when test="${stateCode eq 'REFUSED'}">
            <tiles:insert definition="informMessageBlock" flush="false">
                <tiles:put name="regionSelector" value="informMessage${status.index}"/>
                <tiles:put name="color" value="infMesOrange"/>
                <tiles:put name="data">
                    <bean:message bundle="cardInfoBundle" key="email.report.delivery.win.label.REFUSED"/>
                </tiles:put>
            </tiles:insert>
        </c:when>
    </c:choose>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="errMessagesBlock"/>
        <tiles:put name="isDisplayed" value="false"/>
    </tiles:insert>

    <input type="hidden" name="needSave" value="true"/>

    ${form.html}

    <div class="buttonsArea">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.email.report.delivery.cancel"/>
            <tiles:put name="commandHelpKey" value="button.email.report.delivery.cancel"/>
            <tiles:put name="bundle"         value="cardInfoBundle"/>
            <tiles:put name="onclick"        value="win.close('reportDelivery');"/>
        </tiles:insert>
    </div>
</html:form>