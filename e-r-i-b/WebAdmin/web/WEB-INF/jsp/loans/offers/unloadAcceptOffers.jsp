<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
<c:set var="form" value="${UnloadOfferForm}"/>
<html:form action="/loans/offers/unloading">
    <tiles:insert definition="loansMain">
        <tiles:put name="submenu" type="string" value="UnloadAcceptOffers"/>
        <tiles:put name="data" type="string">

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="offerLoad"/>
                <tiles:put name="name" value="Выгрузка заявок"/>
                <tiles:put name="description">
                    <script type="text/javascript">
                        <c:set var="info">
                            <bean:message key="message.forIE" bundle="loansBundle"/>
                        </c:set>
                        $(document).ready(function()
                        {
                            if (isIE()) {
                                var hrefStr = "&nbsp;<a href='#' onclick= 'openUnloadingHelp()'>здесь</a>";
                                document.getElementById("infoMessage").innerHTML += '${info}'+ hrefStr + '.';
                            }
                        });
                        function openUnloadingHelp() {
                            openHelp('${helpLink}');
                        }
                    </script>

                    <div id="infoMessage">
                        <bean:message key="message.forNotIE" bundle="loansBundle"/>
                    </div>

                </tiles:put>

                <tiles:put name="data">

                    <table>
                        <tr>
                            <td>
                                <html:radio property="field(type)" value="LOAN_PRODUCT"/>На кредит</td>
                            <td><html:radio property="field(type)" value="LOAN_CARD_PRODUCT"/>На кредитную карту</td>
                            <td><html:radio property="field(type)" value="VIRTUAL_CARD"/>На виртуальную карту</td>
                        </tr>
                        <tr>
                            <td><html:radio property="field(type)" value="LOAN_OFFER"/>На предодобренный кредит</td>
                            <td><html:radio property="field(type)" value="LOAN_CARD_OFFER"/>На предодобренную кредитную карту</td>
                        </tr>
                    </table>

                    <h4>Период выгрузки</h4>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">Начало периода</tiles:put>
                        <tiles:put name="data">
                            Дата
                            <html:text property="field(fromDate)" styleClass="filterInput dot-date-pick" size="10"
                                       value="${empty form.fields.fromDate ? phiz:сalendarToString(phiz:previousDate(phiz:currentDate())) : form.fields.fromDate }"/>
                            Время
                            <html:text property="field(fromTime)" styleClass="filterInput time-template" size="10"
                                       value="${empty form.fields.fromTime ? '00:00:00' : form.fields.fromTime }"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">Окончание периода</tiles:put>
                        <tiles:put name="data">
                            Дата
                            <html:text property="field(toDate)" styleClass="filterInput dot-date-pick" size="10"
                                       value="${empty form.fields.toDate ? phiz:сalendarToString(phiz:currentDate()) : form.fields.toDate }"/>
                            Время
                            <html:text property="field(toTime)" styleClass="filterInput time-template" size="10"
                                       value="${empty form.fields.toTime ? '00:00:00' : form.fields.toTime }"/>
                        </tiles:put>
                    </tiles:insert>

                    <script type="text/javascript">
                        addClearMasks(null,
                            function(event)
                            {
                                clearInputTemplate('field(fromDate)', '__.__.____');
                                clearInputTemplate('field(toDate)', '__.__.____');
                                clearInputTemplate('field(fromTime)', '__:__:__');
                                clearInputTemplate('field(toTime)', '__:__:__');
                            });
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.unload"/>
                        <tiles:put name="commandHelpKey" value="button.unload"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
