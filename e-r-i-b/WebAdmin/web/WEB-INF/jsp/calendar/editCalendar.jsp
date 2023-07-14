<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 25.03.2009
  Time: 13:22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<% pageContext.getRequest().setAttribute("mode", "Calendar");%>
<c:set var="form" value="${EditCalendarForm}"/>
<html:form action="/editcalendar" onsubmit="return setEmptyAction(event);">

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="ListCalendar"/>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="calendarBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="action" value="/listcalendar.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">

<input type="hidden" name="workdays" id="workdays" value="${form.workdays}">
<input type="hidden" name="holidays" id="holidays" value="${form.holidays}">
<input type="hidden" name="id" id="id" value="${form.id}"/>
<input type="hidden" id="departmentId" name="field(departmentId)" value="${form.fields.departmentId}"/>

<script type="text/javascript">
    function setDepartmentInfo(resource)
    {
        $("#departmentId").val(resource['region']);
        $("#region").val(resource['region']);
    }


 function addDate(date)
 {
	 var workdays = document.getElementById("workdays").value;
	 var holidays = document.getElementById("holidays").value;
	 var match = date.split('.');
	 var day = match[0];
	 var month = match[1];
     var year = match[2];
	 var ddate = new Date();
	 ddate.setYear(year);
	 ddate.setMonth(month-1);
	 ddate.setDate(day);
	 if(workdays.indexOf(date)>-1)
	 {
	    workdays = workdays.replace(date,'');
		if(ddate.getDay() != 0 && ddate.getDay() != 6)
		   holidays = holidays+date+";";
		document.getElementById("workdays").value = workdays;
		document.getElementById("holidays").value = holidays;
		return;
	 }
	 if(holidays.indexOf(date)>-1)
	 {
	    holidays = holidays.replace(date,'');
		if(ddate.getDay() == 0 || ddate.getDay()==6)
		   workdays = workdays+date+";";
		document.getElementById("workdays").value = workdays;
		document.getElementById("holidays").value = holidays;
		return;
	 }

	 if(ddate.getDay() == 0 || ddate.getDay() == 6)
	    workdays = workdays+date+";";
	 else
	    holidays = holidays+date+";";
	 document.getElementById("workdays").value = workdays;
	 document.getElementById("holidays").value = holidays;

 }

</script>
<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="calendar"/>
	<tiles:put name="name" value="Редактирование календаря"/>
	<tiles:put name="description" value="Используйте данную форму для редактирования рабочего календаря."/>
	<tiles:put name="data">
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Название&nbsp;календаря</tiles:put>
            <tiles:put name="data">
                <html:text property="field(name)" styleId="name" maxlength="255"/>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="data">
                <div id="date_embed" class="dp-popup grayBorder"></div>
                <script type="text/javascript">
                    $(function()
                    {
                        var addWorkHoliday = function($td, thisDate, month, year)
                        {
                            if(thisDate!=undefined)
                            {
                                var workdays = document.getElementById("workdays").value;
                                var holidays = document.getElementById("holidays").value;

                                if(workdays.indexOf(thisDate.asString("dd.mm.yyyy"))>-1)
                                    $td.removeClass("weekend");
                                if(holidays.indexOf(thisDate.asString("dd.mm.yyyy"))>-1)
                                {
                                    $td.addClass("weekend");
                                    if ($td.hasClass("today"))
                                    {
                                        $td.removeClass("today");
                                        $td.addClass("todayWeekend");
                                    }
                                }
                            }
                        };
                        $('.dp-popup').datePicker({inline:true, renderCallback: addWorkHoliday })
                                .bind(
                                'dateSelected',
                                function(e, selectedDate, $td)
                                {
                                    addDate(selectedDate.asString("dd.mm.yyyy"));
                                    var controller = $.event._dpCache[this._dpId];
                                    controller.clearSelected();
                                    if (($td.hasClass("weekend")))
                                    {
                                        if ($td.hasClass("todayWeekend"))
                                        {
                                            $td.removeClass("todayWeekend");
                                            $td.removeClass("weekend");
                                            $td.addClass("today");
                                        }
                                        else
                                            $td.removeClass("weekend");
                                    }
                                    else
                                    {
                                        if ($td.hasClass("today"))
                                        {
                                            $td.removeClass("today");
                                            $td.addClass("weekend");
                                            $td.addClass("todayWeekend");
                                        }
                                        else
                                            $td.addClass("weekend");
                                    }

                                    return;
                                }
                        );

                    });
                </script>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="data">
                <table cellpadding="0" cellspacing="2" class="component_calendar_table">
                    <tr>
                        <td class="work">17</td>
                        <td class="helpText">Рабочий день</td>
                    </tr>
                </table>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="data">
                <table cellpadding="0" cellspacing="2" class="component_calendar_table">
                    <tr>
                        <td class="holiday">17</td>
                        <td class="helpText">Выходной или праздничный день</td>
                    </tr>
                </table>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.TB" bundle="calendarBundle"/>
            </tiles:put>
            <c:if test="${not form.caAdmin}"><tiles:put name="isNecessary" value="true"/></c:if>
            <tiles:put name="data">
                <table cellpadding="0" cellspacing="0" border="0" class="noPadding">
                    <tr>
                        <td>
                            <nobr>
                                <input class="contactInput" id="region" name="field(region)" type="text" value="${form.fields.region}" size="4" maxlength="2" readonly="true"/>
                                <input class="buttWhite smButt" id="officeSelectButton" type="button" value="..." onclick="openAllowedTBDictionary(setDepartmentInfo, true)"/>
                            </nobr>
                        </td>
                    </tr>
                </table>
            </tiles:put>
        </tiles:insert>

	</tiles:put>
	<tiles:put name="buttons">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
			<tiles:put name="bundle" value="calendarBundle"/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
</tiles:put>
</tiles:insert>

</html:form>
