<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loans/offers/settingLoanCardLoad">
   <tiles:insert definition="loansEdit">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="submenu" type="string" value="SettingLoanCardOfferLoad"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="loanCardOfferLoad"/>
			<tiles:put name="name" value="Настройка загрузки предложений по кредитным картам "/>
			<tiles:put name="description" value="Укажите путь к каталогу, из которого будет происходить загрузка предложения по предодобренным кредитным картам"/>
			<tiles:put name="data">

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <b>Ручная загрузка</b>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Каталог
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(manualLoadPath)" size="50" maxlength="255"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Файл
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(manualLoadFile)" size="50" maxlength="255"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <b>Автоматическая загрузка</b>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Каталог
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(automaticLoadPath)" size="50" maxlength="255"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Файл
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(automaticLoadFile)" size="50" maxlength="255"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Периодичность
                    </tiles:put>
                    <tiles:put name="data">
                        <table>
                            <tr>
                                <td>
                                        <span id="SpanMonthAll">

                                        <html:radio property="field(radio)" value="MonthDay" onclick="switchPeriodType('MonthDay');"/>
                                            <span id="SpanMonthDay">
                                                каждые
                                                <html:text property="field(DayInMonth)" styleClass="filterInput" size="3" maxlength="2"/>
                                                число месяца
                                            </span>
                                        </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                        <span id="SpanHoursAll">
                                            <html:radio property="field(radio)" value="Hours"onclick="switchPeriodType('Hours');"/>
                                            <span id="SpanHours">
                                                каждые
                                                <html:text property="field(Hour)" styleClass="filterInput" size="3" maxlength="2"/>
                                                часа
                                            </span>
                                        </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                        <span id="SpanDaysAll">
                                            <html:radio property="field(radio)" value="Days"onclick="switchPeriodType('Days');"/>
                                            <span id="SpanDays">
                                                каждые
                                                <html:text property="field(Day)" styleClass="filterInput" size="3"/>
                                                дней в
                                                <html:text property="field(DayTime)" styleClass="filterInput short-time-template" size="5"/>
                                            </span>
                                        </span>
                                </td>
                            </tr>
                        </table>
                    </tiles:put>
                </tiles:insert>

<script type="text/javascript">

    init();

    function init()
   {
       var dayValue = document.getElementsByName('field(Day)')[0].value;
       var dayInMonthValue = document.getElementsByName('field(DayInMonth)')[0].value;
       var hourValue = document.getElementsByName('field(Hour)')[0].value;

       var isDays = (dayValue != null && dayValue != "");
       var isDayInMonth = (dayInMonthValue != null && dayInMonthValue != "");
       var isHours = (hourValue != null && hourValue != "");

       var period ="";

       if (isDays)
           period = 'Days';
       else if (isDayInMonth)
           period = 'MonthDay';
       else if (isHours)
           period = 'Hours';

       switchPeriodType(period);
   }

    function switchPeriodType(periodType)
    {
        //проставляем радио
        var radio = document.getElementsByName('field(radio)');
        for(var i = 0; i < radio.length; i++)
            radio[i].checked = (radio[i].value == periodType);

        //и устанавливаем доступность инпутов
        switchAvailability('SpanHours', periodType);
        switchAvailability('SpanDays', periodType);
        switchAvailability('SpanMonthDay', periodType);
    }

    function switchAvailability(spanId, periodType)
    {
        var inputs = document.getElementById(spanId).getElementsByTagName("INPUT");
        for (var i = 0; i < inputs.length; i++)
        {
            if (spanId.indexOf(periodType) != -1)
            {
                inputs[i].disabled = false;
            }
            else
            {
                inputs[i].value = '';
                inputs[i].disabled = true;
            }
        }
    }
</script>   

            </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandTextKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
   </tiles:insert>
</html:form>