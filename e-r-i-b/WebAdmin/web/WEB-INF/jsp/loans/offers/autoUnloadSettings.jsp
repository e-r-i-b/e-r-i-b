<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
Блок настройки автоматической выгрузки заявок.
	id          -   имя инструмента (на что заявка)
	name        -   название отображаемое в UI
--%>
<fieldset>
    <html:checkbox property="field(${id}Enabled)" onclick="changeEditing('${id}',this);"/><h5 class="inlineBlock" >${name}</h5>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            Периодичность
        </tiles:put>
        <tiles:put name="data">
            <span id="${id}SpanHoursAll">
                <html:radio property="field(${id}Radio)" value="Hours" onclick="switchPeriodType('${id}', 'Hours');"/>
                <span id="${id}SpanHours">
                    каждые
                    <html:text property="field(${id}Hour)" styleClass="filterInput" size="3" maxlength="3"/>
                    часа
                </span>
            </span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="data">
            <span id="${id}SpanDaysAll">
                <html:radio property="field(${id}Radio)" value="Days" onclick="switchPeriodType('${id}', 'Days');"/>
                <span id="${id}SpanDays">
                    каждые
                    <html:text property="field(${id}Day)" styleClass="filterInput" size="3"/>
                    дней в
                    <html:text property="field(${id}DayTime)" styleClass="filterInput short-time-template" size="5"/>
                </span>
            </span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            Каталог выгрузки
        </tiles:put>
        <tiles:put name="data">
            <html:text property="field(${id}Folder)" styleClass="filterInput" style="width:98%" />
        </tiles:put>
    </tiles:insert>

</fieldset>
<script type="text/javascript">

    init('${id}');

    function init(id)
   {
       var dayValue = document.getElementsByName('field('+id+'Day)')[0].value;
       var isDays = (dayValue != null && dayValue != "");
       switchPeriodType(id, isDays ? 'Days' : 'Hours');
       <%-- выключаем отключенные --%>
       var checkBox = document.getElementsByName('field('+id+'Enabled)')[0];
       if (!checkBox.checked)
       {
         changeEditing(id,checkBox);
       }
   }

    function switchPeriodType(id, periodType)
    {
        //проставляем радио
        var radio = document.getElementsByName('field('+id+'Radio)');
        for(var i = 0; i < radio.length; i++)
            radio[i].checked = (radio[i].value == periodType);

        //и устанавливаем доступность инпутов
        switchAvailability(id+'SpanHours', periodType);
        switchAvailability(id+'SpanDays', periodType);
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

    function disableEditing(spanId)
    {
        var inputs = document.getElementById(spanId).getElementsByTagName("INPUT");
        for (var i = 0; i < inputs.length; i++)
        {
             inputs[i].disabled = true;
        }
     }

     function enableRadio(id)
     {
          var radio = document.getElementsByName('field('+id+'Radio)');
          for (var i = 0; i <radio.length; i++ )
          {
              radio[i].disabled = false;
          }
     }

    function changeEditing(id,obj)
    {
        var dir;
        if (obj.checked)
        {
            dir  =  document.getElementsByName('field('+id+'Folder)')[0];
            dir.disabled = false;
            enableRadio(id);
            init(id);
        }
        else
        {
            dir =  document.getElementsByName('field('+id+'Folder)')[0];
            dir.disabled = true;
            disableEditing(id+'SpanHoursAll');
            disableEditing(id+'SpanDaysAll');
        }
    }
</script>