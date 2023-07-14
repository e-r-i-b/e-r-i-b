<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="form" value="${frm.form}"/>
<fieldset>
    <table class="additional-deposit-info">
        <c:if test="${form.registrationAddress  != ''}">
            <c:set var="addr" value="${form.registrationAddress}" />
            <tr>
                <td class="align-right field"><span class="bold depoAddress">Адрес регистрации</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">Страна:</td>
                <td><span class="bold">${form.regAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Почтовый индекс:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Регион(республика, область, край,<br/>автономный округ, автономная область):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Район:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Город:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Населенный пункт:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Улица:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Дом:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Корпус:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Квартира:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>
        <c:if test="${form.residenceAddress != ''}">
            <c:set var="addr" value="${form.residenceAddress}" />
            <tr>
                <td class="align-right field"><span class="bold">Адрес проживания</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">Страна:</td>
                <td><span class="bold">${form.resAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Почтовый индекс:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Регион(республика, область, край,<br/>автономный округ, автономная область):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Район:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Город:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Населенный пункт:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Улица:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Дом:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Корпус:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Квартира:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>

        <c:if test="${form.forPensionAddress  != ''}">
            <c:set var="addr" value="${form.forPensionAddress}" />
            <tr>
                <td class="align-right field"><span class="bold depoAddress">Адрес для получения пенсии военными пенсионерами</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">Страна:</td>
                <td><span class="bold">${form.forPensionAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Почтовый индекс:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Регион(республика, область, край,<br/>автономный округ, автономная область):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Район:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Город:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Населенный пункт:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Улица:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Дом:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Корпус:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Квартира:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>

        <c:if test="${form.mailAddress  != ''}">
            <c:set var="addr" value="${form.mailAddress}" />
            <tr>
                <td class="align-right field"><span class="bold">Адрес для почтовых уведомлений</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">Страна:</td>
                <td><span class="bold">${form.mailAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Почтовый индекс:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Регион(республика, область, край,<br/>автономный округ, автономная область):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Район:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Город:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Населенный пункт:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Улица:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Дом:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Корпус:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Квартира:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>
        <c:if test="${form.workAddress  != ''}">
            <c:set var="addr" value="${form.workAddress}" />
            <tr>
                <td class="align-right field"><span class="bold">Адрес места работы</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">Страна:</td>
                <td><span class="bold">${form.workAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Почтовый индекс:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Регион(республика, область, край,<br/>автономный округ, автономная область):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Район:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Город:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Населенный пункт:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Улица:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Дом:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Корпус:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Квартира:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>

        <tr>
            <td class="align-right field"><span class="bold depoAddress">Дополнительные виды связи:</span> </td>
            <td>&nbsp;</td>
        </tr>
        <c:if test="${not empty form.homeTel}">
            <tr>
                <td class="align-right field">Домашний телефон:</td>
                <td><span class="bold">${form.homeTel}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.workTel}">
            <tr>
                <td class="align-right field">Рабочий телефон:</td>
                <td><span class="bold">${form.workTel}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.mobileTel}">
            <tr>
                <td class="align-right field">Мобильный телефон:</td>
                <td><span class="bold">${form.phoneOperator} ${form.mobileTel}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.fax}">
            <tr>
                <td class="align-right field">Факс:</td>
                <td><span class="bold">${form.fax}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.privateEmail}">
            <tr>
                <td class="align-right field">Персональный E-mail:</td>
                <td><span class="bold">${form.privateEmail}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.workEmail}">
            <tr>
                <td class="align-right field">Рабочий E-mail:</td>
                <td><span class="bold">${form.workEmail}</span></td>
            </tr>
        </c:if>
    </table>
</fieldset>