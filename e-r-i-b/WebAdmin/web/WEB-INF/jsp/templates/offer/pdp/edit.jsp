<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz" %>

<html:form action="/templates/offer/pdp/edit">
    <c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="templatesBundle"/>

    <tiles:insert definition="loansEdit">
        <tiles:put name="submenu"        type="string" value="CreditOfferPdpTemplate"/>
        <tiles:put name="messagesBundle" type="string" value="templatesBundle"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.edit.credit.loan.pdp.offer" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="descTitle">
            <bean:message key="label.credit.loan.offer.pdp.description" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="data" type="string">

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.credit.loan.offer.pdp.title" bundle="${bundle}"/>:
                </tiles:put>
                <tiles:put name="data">
                    <html:textarea styleId="text" property="fields(text)" value="${form.template.text}" cols="100" rows="10"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="data">
                    <tiles:insert definition="bbCodeButtons"  flush="false">
                        <tiles:put name="showTopUp"           value="true"/>
                        <tiles:put name="showIdContract"      value="true"/>
                        <tiles:put name="showAgreementNumber" value="true"/>
                        <tiles:put name="showProductName"     value="true"/>
                        <tiles:put name="showTermStart"       value="true"/>
                        <tiles:put name="showAmount"          value="true"/>
                        <tiles:put name="textAreaId"          value="text"/>
                        <tiles:put name="showBold"            value="true"/>
                        <tiles:put name="showItalics"         value="true"/>
                        <tiles:put name="showUnderline"       value="true"/>
                        <tiles:put name="showColor"           value="true"/>
                        <tiles:put name="showHyperlink"       value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <html:hidden property="id"/>
        </tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="bundle"         value="${bundle}"/>
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>