<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>

<c:set var="showExtended" value="${not onlyShortClaim and phiz:impliesService('ExtendedLoanClaim')}"/>

<script type="text/javascript">
    function openExtendedLoanClaim()
    {
        <c:choose>
            <c:when test="${not empty loanOfferId}">
                var urlAttributes = 'loanOfferId=${loanOfferId}&loanAmount=${loanAmount.decimal}';
            </c:when>
            <c:otherwise>
                var urlAttributes = 'condId=${condId}&condCurrId=${condCurrId}&loanPeriod=${loanPeriod}&loanAmount=${loanAmount.decimal}';
            </c:otherwise>
        </c:choose>

        var phoneNumber = $("input[name='phoneNumber']").val();
        var jobLocation = $("input[name='jobLocation']").val();
        var incomePerMonth = $("input[name='incomePerMonth']").val();
        var shortLoanId = '${id}';

        if (validateFields(phoneNumber, jobLocation, incomePerMonth))
        {
            urlAttributes += generateUrlMobile(phoneNumber);
        }
        else if (shortLoanId && isMaskedCurrent(phoneNumber))
        {
            urlAttributes += '&shortLoanId=' + shortLoanId;
        }

        var url = document.webRoot+'/private/payments/payment.do?form=ExtendedLoanClaim&' + urlAttributes;
        window.location = url;
    }

    function validateFields(phoneNumber, jobLocation, incomePerMonth)
    {
        var phoneRegex = /^\d{10}$/;
        var jobRegex = /^.{1,100}$/;
        var incomeRegex = /^.{1,100}$/;

        return jobRegex.test(jobLocation) && incomeRegex.test(incomePerMonth) && phoneRegex.test(phoneNumber);
    }

    function isMaskedCurrent(phoneNumber)
    {
        var maskRegex = /^\d{3}\*{3}\d{4}/;

        return maskRegex.test(phoneNumber);
    }

    function generateUrlMobile(phoneNumber)
    {
        return '&mobileTelecom=' + phoneNumber.substring(0, 3) +
                '&mobileNumber=' + phoneNumber.substring(3);
    }

</script>


<div style="padding: 20px 0;">
    <table class="fastApplication">
        <tr>
        <td>
            <div>
                <div>
                    <span class="fastApplicationTitle">Быстрая заявка</span>
                </div>
                <div class="blockTop20">
                    <span>Сотрудник Сбербанка сам свяжется с Вами для консультации по вопросам оформления и уточнения недостающих личных данных.</span>
                </div>
            </div>

        </td>
        <td>
            <c:if test="${showExtended == true}">
                <div class="extendedLink">
                <span class="extendedLinkHint">На один поход<br/> в банк меньше</span>
                <div class="pointerBlock">
                    <div class="bigPointer">
                        <div class="bigPointerLeft"></div>

                        <div class="bigPointerCenter">
                            <div class="showPointer">
                                <div class="questionary"></div>
                                <div>
                                    <a class="needLight">Расширенная анкета на кредит</a>
                                </div>
                            </div>
                        </div>

                        <div class="bigPointerRightArrow"></div>
                        <div class="clear"></div>
                    </div>
                    <div class="bigPointerHidden">
                        <div class="bigPointerHiddenLT">
                            <div class="bigPointerHiddenRT">
                                <div class="bigPointerHiddenCT">

                                    <div class="showPointer">
                                        <div class="questionary"></div>
                                        <div>
                                            <a class="needLight">Расширенная анкета на кредит</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="bigPointerHiddenCL">
                            <div class="bigPointerHiddenCR">
                                <div class="bigPointerHiddenCC">
                                    <p>Для заполнения анкеты Вам понадобится паспорт и 15 минут свободного времени.</p>
                                    <br/>
                                    <c:if test="${empty loanOfferId}">
                                        <p class="loan-claim-only-for">ТОЛЬКО ДЛЯ</p>
                                        <p>Клиентов, получающих зарплату или пенсию на карту или счет в Сбербанке.</p>
                                    </c:if>
                                    <div class="buttonsArea buttonsBlock">
                                        <div class="commandButton ">
                                            <div class="buttonGreen" onclick="openExtendedLoanClaim();">
                                                <a>
                                                    <div class="left-corner"></div>
                                                    <div class="text">
                                                        <span>Заполнить прямо сейчас</span>
                                                    </div>
                                                    <div class="right-corner"></div>
                                                </a>
                                            </div>

                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="bigPointerHiddenBL">
                        <div class="bigPointerHiddenBR">
                            <div class="bigPointerHiddenBC"></div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
            </c:if>
        </td>
        </tr>
    </table>

    <input name="onlyShortClaim" type="hidden" value="${onlyShortClaim}"/>
    <div class="clear"></div>
</div>