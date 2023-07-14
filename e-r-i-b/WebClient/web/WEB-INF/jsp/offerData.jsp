<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<tiles:importAttribute/>
<html:form action="/private/async/offer">
    <c:catch var="error">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:if test="${not empty form.bottomLoanOffers or not empty form.bottomLoanCardOffers}">
            <c:set var="allLoanOfferUrl">${phiz:calculateActionURL(pageContext,"/private/loan/loanoffer/show.do")}</c:set>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Персональные предложения от Сбербанка"/>
                <tiles:put name="control">
                    <a class="blueGrayLink" href="${allLoanOfferUrl}" title="К списку">
                        Все предложения
                    </a>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="loanOfferMultiColumn" flush="false">
                        <%--в каком виде выводить если у клиенто только одно предложение--%>
                        <tiles:put name="loanOffers" beanName="form" beanProperty="bottomLoanOffers"/>
                        <tiles:put name="loanCardOffers" beanName="form" beanProperty="bottomLoanCardOffers"/>
                        <tiles:put name="loanOffersNoInformed" beanName="form" beanProperty="loanOffersNoInformed"/>
                        <tiles:put name="loanCardOffersNoInformed" beanName="form" beanProperty="loanCardOffersNoInformed"/>
                        <tiles:put name="loanOffersNoPresentation" beanName="form" beanProperty="loanOffersNoPresentation"/>
                        <tiles:put name="loanCardOffersNoPresentation" beanName="form" beanProperty="loanCardOffersNoPresentation"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </c:if>
    </c:catch>
    <c:if test="${not empty error}">
        ${phiz:setException(error, pageContext)}
    </c:if>
</html:form>