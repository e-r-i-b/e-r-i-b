<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<script type="text/javascript">

    var run = false;
    var LINK_TAG_NAME = "A";
    var COMMA = ",";


    $.ajaxSetup({
       async: false      // синхронный режим, чтобы избежать многократного нажатия на ссылку во время коннекта к бд в результате чего может возникнуть дублирование данных
     });

    // ставим галки или снимаем галки на всех чекбоксах, включенных в таблицу с id = 'DepartmentsList'
    function selectionAll()
    {
        var checked = $('#selectionCheckbox').attr("checked");
        var elem = $('#DepartmentsList');
        openListCheckboxes(elem , checked);
    }

    // ставим или снимаем галки на всех чебоксах, относящихся к выбранному департаменту
    function checkDepartment(elem)
    {
        var checked = $(elem).attr("checked");
        openListCheckboxes($(elem).parent() , checked);
    }

    // раскрываем списки
    function openListCheckboxes(element, checked)
    {

        $( element ).find('input:checkbox').attr("checked",
                function()
                {
                    var linkElement = $(this).parent().siblings().filter(":first").children()[0];     // ищем в соседней ячейке ссылку
                    var nextTr = $(this).parent().parent().next();                                      // это строка, в которую подгружаются данные по включенному департаменту

                    if (linkElement!= null && linkElement != undefined  && linkElement.tagName == LINK_TAG_NAME )                    // значит ссылка
                    {

                        var current_checkbox = this;

                        if ($(nextTr).find('table').size() == 0 // еше не была открыта ни разу (table внутри tr-td создается только, если запрашивались данные по подразделению )
                            || !$(nextTr).is(':visible')    // значит данные были уже загружены, но были скрыты пользователем
                        )
                            var clickInterval = window.setInterval(function() {
                                    if (run == true)
                                       return;

                                    window.clearInterval(clickInterval);
                                    clickInterval = null;
                                    $(linkElement).click();  // щелкаем по ссылке

                                    // продолжаем раскрывать список
                                    var interval = window.setInterval(function() {
                                              if (run == true)
                                                return;

                                              window.clearInterval(interval);
                                              interval = null;
                                              openListCheckboxes($(current_checkbox).parent().parent().next(), checked);
                                        },
                                        10
                                   )
                                },
                                1
                            )
                        else
                            openListCheckboxes($(current_checkbox).parent().parent().next(), checked);
                    }

                    return checked;
                }
        );
    }

    // проверяем, входит ли данная галка в список отмечанных
    function isChecked(valueChecked)
    {
        return $('#checkedDeps').val().match(valueChecked);
    }

    // подгрузка вложенных подразделений
    function createNewDiv(id, current_level)
    {
        if ($('[name=level_' + current_level + '_' + id + ']').size() <= 0)
        {
           var addUrlList = "${phiz:calculateActionURL(pageContext,'/reports/departmetns/list')}";
           var params = "level=" + <c:out value="${param.level}" default="''"/> + "&current_level=" +  current_level + "&id=" + id;

           beforeAjax();
           setTimeout(function(){    // приблуда для IE (чтобы показывал часы при запросах в синхронном режиме)
                ajaxQuery(
                    params,
                    addUrlList,
                    function(data){
                           $("tr#" + id).after('<tr class="ListLine0" id="' + current_level + '_' + id + '"><td class="listItem" colspan="2"><table cellpadding="0" cellspacing="0" >' + getCodeForIE6(data) + '</table></td></tr>');
                           afterAjax();
                    }
                );
           }, 10);
        }
        else
        {
            $("tr#" + current_level + '_' + id ).toggle();
        }
    }


    function showListDepartmetns()
    {
        var divDeps = document.getElementById("list_departments");

        if (divDeps == null)
            return;

        divDeps.style.display = divDeps.style.display == 'none' ? 'block' : 'none';

        if (divDeps.style.display == 'block' &&  $("#DepartmentsList > tbody > tr").size() <= 1)
        {
            var addUrlList = "${phiz:calculateActionURL(pageContext,'/reports/departmetns/list')}";
            var params = "level=" + <c:out value="${param.level}" default="''"/> + "&current_level=1";

            beforeAjax();

            setTimeout(function(){    // приблуда для IE (чтобы показывал часы при запросах в синхронном режиме)
                ajaxQuery(params, addUrlList, showLitsTB);
                afterAjax();
             }, 10);
        }
    }

    /**
     * получаем список департаментов
     */
    function showLitsTB(data)
    {
        $("#DepartmentsList > tbody").append(getCodeForIE6(data));
        afterAjax();
    }

    /**
     *  возвращает код начала строки и ячейки (лечит глюк ie6)
     */
     function getCodeForIE6(data)
     {
         return data.replace('&nbsp;', '');
     }

    /**
     * выполняем перед ajax
     */
    function beforeAjax()
    {
        $('#centerLoadDiv').show();
        run = true;

    }

    /**
     * завершающая функция после выполнения ajax
     */
    function afterAjax()
    {
        $('#centerLoadDiv').hide();
        run = false;
    }

    /**
     * Отправляет данные из формы на сервер для сохранения
     */
    function sendData()
    {
        var selectedIds = [];
        beforeAjax();
        $(":checkbox:checked[name=selectedIds]").each(function(i) { selectedIds[i] = $(this).val() });       // пишем в массив все отмеченные галки c name="selectedIds"

        var periodType = "";
        if (${form.periodTypes == 'week|month'})
        {
            periodType = getRadioValue(document.getElementsByName("field(periodType)"));
            if (periodType != 'week' && periodType != 'month')
            {
                alert("Не указан период формирования отчета");
                return;
            }
        }
        var params = "operation=button.save&field(startDate)=" + $("#" + periodType + "startDate").val() +
                     "&field(endDate)=" + $("#" + periodType + "endDate").val() + "&level=" + $("#level").val() +
                     (periodType!="" ? "&field(periodType)=" + periodType : "");

        <c:if test="${param.proactive == true}">

            if ($("#everyWeek").attr("checked"))
            {
                params =  "operation=button.save&field(periodType)=week&field(startDate)=" + $("#startDateWeek").val() + "&field(endDate)=" + $("#endDateWeek").val() + "&level=" + $("#level").val();
            } else if ($("#everyMonth").attr("checked"))
            {
                params =  "operation=button.save&field(periodType)=month&field(startDate)=" + $("#startDateMonth").val() + "&field(endDate)=" + $("#endDateMonth").val() + "&level=" + $("#level").val();
            } else if ($("#everyDay").attr("checked"))
            {
                params =  "operation=button.save&field(periodType)=day&field(startDate)=" + $("#startDateDay").val() + "&field(endDate)=" + $("#startDateDay").val() + "&level=" + $("#level").val();
            }
        </c:if>
        for (var i = 0; i < selectedIds.length; i++ )
        {
            params += "&selectedIds=" + selectedIds[i];
        }

        try
        {
            new XMLHttpRequest();     <%-- значит вместо ajax будут фреймы и передача параметров get-методом --%>
        }
        catch(e)
        {
            if (params.length >= 2048 )
            {
                alert("Слишком большое количество выбранных подразделений");
                afterAjax();
                return;
            }            
        }



        var url = $(location).attr('href');
        setTimeout(function(){    // приблуда для IE (чтобы показывал часы при запросах в синхронном режиме)
                ajaxQuery(params, url, redirectToList),
                10
            }
        );

    }

    /**
     * Выводит сообщение об ошибке, пришедшее с сервера, или делает редирект на страницу со списком отчетов
     * @param msg - пришедший ответ 
     */
    function redirectToList(msg)
    {
      afterAjax();
      if (msg.length < 5)
      {
          if("${param.type_report}" == "")
            //для бизнес-отчетов тип не задаем
            window.location = "/${phiz:loginContextName()}/reports/list.do";
          else
            window.location = "/${phiz:loginContextName()}/reports/${param.type_report}/list.do";
      }
      else
      {
        alert(trim(msg));
      }
    }
    function changePeriod(type)
    {
        $("#startDateDay").attr("readonly", true);
        $("#startDateWeek").attr("readonly", true);
        $("#endDateWeek").attr("readonly", true);
        $("#startDateMonth").attr("readonly", true);
        $("#endDateMonth").attr("readonly", true);
        
        if (type == 'day'){
            $("#startDateDay").attr("readonly", false);
        }
        else if(type == 'week')
        {
            $("#startDateWeek").attr("readonly", false);
            $("#endDateWeek").attr("readonly", false);
        }
        else if(type == 'month')
        {
            $("#startDateMonth").attr("readonly", false);
            $("#endDateMonth").attr("readonly", false);
        }
    }

    function updatePeriod(periodType)
    {
        if (periodType == null || periodType.length == 0)
            return;

        var disablePeriod = periodType == 'week' ? 'month' : 'week';

        document.getElementById(periodType + 'startDate').disabled = false;
        document.getElementById(periodType + 'endDate').disabled = false;

        document.getElementById(disablePeriod + 'startDate').disabled = true;
        document.getElementById(disablePeriod + 'endDate').disabled = true;

    }
</script>
<div align="center">
<c:set var="bundle" value="reportsBundle"/>

<tiles:insert definition="paymentForm" flush="false">
 <tiles:put name="id" value="reports"/>
 <tiles:put name="description" value="Задайте параметры для формирования отчетов"/>
 <tiles:put name="buttons">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey"     value="button.save"/>
        <tiles:put name="commandHelpKey" value="button.save.help"/>
        <tiles:put name="onclick"  value="sendData()"/>
        <tiles:put name="bundle"  value="${bundle}"/>
    </tiles:insert>
 </tiles:put>

    <tiles:put name="data">
        <c:choose>
            <c:when test="${param.proactive == true}">

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.period" bundle="${bundle}"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <input type="radio" value="everyDay" name="field(periodType)" onclick="changePeriod('day');" CHECKED id="everyDay"/> Ежедневный    <bean:message key="label.proactive.date" bundle="${bundle}"/>&nbsp;
                        <html:text property="field(startDateDay)"  styleId="startDateDay" size="10" styleClass="dot-date-pick" />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <input type="radio" value="everyWeek" id="everyWeek" name="field(periodType)"  onclick="changePeriod('week');"/> Еженедельный&nbsp;&nbsp;&nbsp;
                        <bean:message key="label.startDate" bundle="${bundle}"/>&nbsp;<html:text property="field(startDateWeek)"  readonly="true" styleId="startDateWeek" size="10" styleClass="dot-date-pick" />
                        <bean:message key="label.endDate" bundle="${bundle}"/>&nbsp;&nbsp;<html:text property="field(endDateWeek)" readonly="true" styleId="endDateWeek" size="10" styleClass="dot-date-pick" />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <input type="radio" value="everyMonth" id="everyMonth" name="field(periodType)" onclick="changePeriod('month');"/> Ежемесячный&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <bean:message key="label.startDate" bundle="${bundle}"/>&nbsp;<html:text property="field(startDateMonth)"  readonly="true" styleId="startDateMonth" size="10" styleClass="dot-date-pick" />
                        <bean:message key="label.endDate" bundle="${bundle}"/>&nbsp;&nbsp;<html:text property="field(endDateMonth)" readonly="true" styleId="endDateMonth" size="10" styleClass="dot-date-pick"/>
                    </tiles:put>
                </tiles:insert>

            </c:when>
            <c:when test="${param.period == true}">
                <c:choose>
                    <c:when test="${form.periodTypes == 'week|month'}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <div style="float: left;">
                                    <html:radio  property="field(periodType)" value="week" style="border:0" onclick="updatePeriod('week')"/> Еженедельный
                                </div>
                            </tiles:put>
                            <tiles:put name="data">
                                <bean:message key="label.startDate" bundle="${bundle}"/>&nbsp;<html:text property="field(weekstartDate)"  styleId="weekstartDate" size="10" styleClass="dot-date-pick"/>
                                <bean:message key="label.endDate" bundle="${bundle}"/>&nbsp;&nbsp;<html:text property="field(weekendDate)" styleId="weekendDate" size="10" styleClass="dot-date-pick"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <div style="float: left;">
                                    <html:radio  property="field(periodType)" value="month" style="border:0"  onclick="updatePeriod('month')"/> Ежемесячный
                                </div>
                            </tiles:put>
                            <tiles:put name="data">
                                <bean:message key="label.startDate" bundle="${bundle}"/>&nbsp;<html:text property="field(monthstartDate)"  styleId="monthstartDate" size="10" styleClass="dot-date-pick" />
                                <bean:message key="label.endDate" bundle="${bundle}"/>&nbsp;&nbsp;<html:text property="field(monthendDate)" styleId="monthendDate" size="10" styleClass="dot-date-pick" />
                            </tiles:put>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.period" bundle="${bundle}"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <bean:message key="label.startDate" bundle="${bundle}"/>&nbsp;<html:text property="field(startDate)"  styleId="startDate" size="10" styleClass="dot-date-pick" />
                                <bean:message key="label.endDate" bundle="${bundle}"/>&nbsp;&nbsp;<html:text property="field(endDate)" styleId="endDate" size="10" styleClass="dot-date-pick" />
                            </tiles:put>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.date" bundle="${bundle}"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(startDate)" styleId="startDate" size="10" styleClass="dot-date-pick" />
                    </tiles:put>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
        <c:if test="${param.level > 0}">

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.department" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="needMargin" value="true"/>
                <tiles:put name="data">
                    <a href="#" onclick="showListDepartmetns();return false;">Выбор подразделения</a>
                    <input name="level" id="level" value="${param.level}" type="hidden"/>
                </tiles:put>
            </tiles:insert>

               <div id="list_departments" class="list_departments" style="display:none;width:100%;">
                          <tiles:insert definition="tableTemplate" flush="false">
                            <tiles:put name="id" value="DepartmentsList"/>
                            <tiles:put name="text" value="Подразделения банка"/>

                            <tiles:put name="data">
                                <tbody>
                                    <tr class="tblInfHeader">
                                        <th width="20px" class="titleTable"><input type="checkbox" id="selectionCheckbox" onclick="selectionAll();"/></th>
                                        <th>Наименование</th>
                                    </tr>
                                </tbody>
                            </tiles:put>
                         </tiles:insert>
               </div>
          </c:if>
        <c:if test="${form.periodTypes == 'week|month'}">
            <script type="text/javascript">
                doOnLoad(function() {updatePeriod('${form.fields.periodType}')});
            </script>
        </c:if>
    </tiles:put>

</tiles:insert>
</div>
