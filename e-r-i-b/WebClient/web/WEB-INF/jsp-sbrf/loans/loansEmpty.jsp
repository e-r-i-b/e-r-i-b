<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="data">
        <p>Кредиты, предоставляемые Сбербанком России, - это возможность быстро и на выгодных
            условиях получить деньги на любые нужды.
        </p>
        <c:set var="isUsedNewAlgorithm" value="${phiz:isUseNewLoanClaimAlgorithm()}"/>
        <c:set var="takeCreditLinkAvailable" value="${phiz:takeCreditLinkAvailable(true)}"/>
        <c:choose>
            <c:when test="${phiz:impliesService('LoanProduct') && param['emptyLoanOffer'] == false && not isUsedNewAlgorithm}">
                <p>Пока у Вас нет ни одного кредита. Вы можете оформить заявку на кредит,
                    щелкнув по ссылке
                    <html:link action="/private/payments/loan_product" styleClass="text-green orangeText">
                        <span>«Оформить заявку на кредит». </span>
                    </html:link>
                </p>
            </c:when>
            <c:when test="${takeCreditLinkAvailable && phiz:isExtendedLoanClaimAvailable() && (phiz:impliesService('ExtendedLoanClaim') || phiz:impliesService('ShortLoanClaim')) && param['emptyLoanOffer'] == false && isUsedNewAlgorithm}">
                <p>Пока у Вас нет ни одного кредита. Вы можете оформить заявку на кредит,
                    щелкнув по ссылке
                    <html:link action="/private/payments/payment.do?form=ExtendedLoanClaim" styleClass="text-green orangeText">
                        <span>«Оформить заявку на кредит».</span>
                    </html:link>
                </p>
            </c:when>
            <c:otherwise>
                <p>Пока у Вас нет ни одного кредита.
                    Для оформления интересующего Вас кредита просто обратитесь в отделение банка.
                    Наши сотрудники будут рады Вам помочь!
                </p>

                <p>Узнать более подробную информацию можно по
                    <html:link href="http://www.sberbank.ru/ru/person/credits"
                               target="_blank"
                               styleClass="text-green orangeText">
                        <span>ссылке.</span>
                    </html:link>
                </p>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>