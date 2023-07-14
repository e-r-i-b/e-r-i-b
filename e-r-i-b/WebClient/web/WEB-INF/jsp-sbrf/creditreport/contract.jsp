<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    creditContract - договор кредита
    isCredit - кредит или карта
--%>
<tiles:importAttribute/>
<div id="creditDetail" class="cred-hist-item-opened css3">
    <div class="cred-hist-item-opened-title">Общие сведения о кредитном договоре</div>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">Кредитор</td>
            <td>
                <c:out value="${creditContract.bankName}"/>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Тип финансирования</td>
            <td>
                <c:out value="${creditContract.financeType}"/>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Цель финансирования</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.purposeOfFinance}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Тип обеспечения</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.typeOfSecurity}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Тип владельца счёта</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.applicantType}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Срок кредитного договора</td>
            <td><c:out value="${creditContract.duration.value} ${creditContract.duration.unit}"/></td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Причина закрытия</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.reasonForClosure}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Комментарии</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.comment}"/>
                </tiles:insert>
            </td>
        </tr>
    </table>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">Валюта</td>
            <td>${creditContract.amount.currency}</td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <c:choose>
                <c:when test="${isCredit}">
                    <td>Сумма кредита</td>
                    <td>${creditContract.amount.value}</td>
                </c:when>
                <c:otherwise>
                    <td>Кредитный лимит</td>
                    <td>${creditContract.creditLimit.value}</td>
                </c:otherwise>
            </c:choose>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <c:choose>
                <c:when test="${isCredit}">
                    <td>Размер платежа</td>
                </c:when>
                <c:otherwise>
                    <td>Минимальный платеж</td>
                </c:otherwise>
            </c:choose>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.instalment.value}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Всего к выплате</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.balance.value}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Сумма просрочки</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.arrearsBalance.value}"/>
                </tiles:insert>
            </td>
        </tr>
    </table>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">Дата открытия</td>
            <td>${phiz:сalendarToString(creditContract.openDate)}</td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Дата последнего платежа</td>
            <c:set var="lastPaymentDate" value="${creditContract.lastPaymentDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:сalendarToString(lastPaymentDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Последний пропущенный платеж</td>
            <c:set var="lastMissedPaymentDate" value="${creditContract.lastMissedPaymentDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:сalendarToString(lastMissedPaymentDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Дата исполнения обязательств</td>
            <c:set var="fulfilmentDate" value="${creditContract.fulfilmentDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:сalendarToString(fulfilmentDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Дата закрытия (фактическая)</td>
            <c:set var="closedDate" value="${creditContract.closedDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:сalendarToString(closedDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Дата судебного рассмотрения</td>
            <c:set var="litigationDate" value="${creditContract.litigationDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:сalendarToString(litigationDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Дата списания</td>
            <c:set var="writeOffDate" value="${creditContract.writeOffDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:сalendarToString(writeOffDate)}"/>
                </tiles:insert>
            </td>
        </tr>
    </table>

    <div class="cred-hist-item-opened-title">Информация о заёмщике</div>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">Фамилия Имя Отчество</td>
            <td>${phiz:getFormattedPersonFIO(creditContract.firstName, creditContract.surname, creditContract.patronymic)}</td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Дата и место рождения</td>
            <td><c:out value="${phiz:getMaskedDate()} ${creditContract.birthPlace}"/></td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Документ удостоверяющий личность</td>
            <td><c:out value="${creditContract.personDocumentType}, ${phiz:getMaskedText(creditContract.personDocumentNumber)}"/></td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Адрес регистрации</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.registrationAddress}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Адрес проживания</td>
            <td>
                <c:out value="${creditContract.residentialAddress}"/>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Мобильный телефон</td>
            <c:set var="mobilePhoneNumber" value="${creditContract.mobilePhoneNumber}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:getCutPhoneNumber(mobilePhoneNumber)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>ИНН физического лица</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.inn}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Номер свид. госуд. пенс. стр.</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.pensionNumber}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>Номер свидетельства ИП</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.entrepreneurNumber}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>ОГРН ИП</td>
            <td><span class="gray">—</span></td>
        </tr>
    </table>

    <div class="cred-hist-item-opened-title">Источник кредитной истории</div>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">Наименование:</td>
            <td><c:out value="${creditContract.subscriberName}"/></td>
        </tr>
    </table>
    <div class="clear"></div>
    <a onclick="hideOrShowCreditDetail(true);" href="#" class="cred-hist-item-contract-hide">Скрыть информацию о договоре</a>
</div>