<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<html:form action="/private/favourite/list/PaymentsAndTemplates" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="paymentMain">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/receivers/list')}"/>
        <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q19')}"/>
        <c:set var="faqLinkPersonal" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm19')}"/>

        <tiles:put name="mainmenu" value=""/>

        <tiles:put name="data" type="string">
            <div id="payments-templates">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Управление автоплатежами"/>
                    <tiles:put name="data">
                        <%-- ЗАГОЛОВОК --%>
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePathGlobal}/iconPmntList_LongOffer.jpg"/>
                            <tiles:put name="description">
                                <div class="titleItems">
                                    <span>Автоплатеж — это автоматическая оплата услуг за сотовую связь, ЖКХ, городской телефон и Интернет,
                                    а также кредитов других банков, штрафов ГИБДД и налогов. Услуга предоставляется бесплатно.</span>
                                </div>
                                <a class="separatedLink orangeText" target="_blank" href="http://sberbank.ru/ru/person/paymentsandremittances/payments/mobile/autopayment/"><span>Подробнее об услуге »</span></a>
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>


                        <div class="regular-payments" id="regular-payments" >
                            <jsp:include page="/private/async/favourite/list/list-auto-payments.do"/>
                        </div>

                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
