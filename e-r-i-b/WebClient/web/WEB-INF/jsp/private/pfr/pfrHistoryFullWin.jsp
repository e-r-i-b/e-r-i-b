<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<html:form action="/private/pfr/pfrHistoryFull">
    <h1>Виды и размеры пенсий ПФР</h1>
    <script type="text/javascript">

        function openExtendedAbstract()
        {
            removeAllErrors('errMessagesBlock');

            if (document.getElementById('typePeriod').checked)
            {
                var err = validatePeriod("filter(fromPeriod)", "filter(toPeriod)", "Ошибка в формате начальной даты.", "Ошибка в формате конечной даты.",templateObj.ENGLISH_DATE);
                if (err!="")
                {
                    $('#errMessagesBlock').css("display","block");
                    addError(err,'errMessagesBlock',true);
                    checkErrors();
                    return;
                }
                else
                    $('#errMessagesBlock').css("display","none");
                checkErrors();
            }

            var url = "${phiz:calculateActionURL(pageContext,'/private/pfr/pfrHistoryFullPrint.do')}";
            var params = "?fromResource="+$("#fromResource").val();

            var ajaxUrl = '${phiz:calculateActionURL(pageContext,"/private/async/pfr/pfrHistoryFull.do")}';
            var ajaxParams = "fromResource="+$("#fromResource").val();
            ajaxParams += '&operation=button.printPFRHistoryFull';

            var typePeriod =  $('input[name="filter(typePeriod)"]:checked').val();
            if (typePeriod == 'period')
            {
                params = addParam2List(params, "filter(fromPeriod)", "fromDateString");
                params = addParam2List(params, "filter(toPeriod)", "toDateString");

                ajaxParams = addParam2List(ajaxParams, "filter(fromPeriod)", "filter(fromPeriod)");
                ajaxParams = addParam2List(ajaxParams, "filter(toPeriod)", "filter(toPeriod)");
                ajaxParams += "&filter(typePeriod)=period";
            }
            else
            {
                params += "&typePeriod=" + typePeriod;
                ajaxParams += "&filter(typePeriod)=" + typePeriod;
            }

            ajaxQuery(ajaxParams, ajaxUrl, function(data){
                    if(data.indexOf("emptyListPFRRecords") !== -1){
                        $('#startTextMessage').hide();
                        $('#failTextMessage').show();
                    }
                    else {
                        window.open(url + params, "PrintAbstract", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
                        $('#failTextMessage').hide();
                        $('#startTextMessage').show();
                    }
                }, 'POST');

            checkErrors();
        }

        function checkErrors() {
            if($('#errMessagesBlock:first').css('display') == 'block') {
                $('.form-row.active-help:first').removeClass('active-help').addClass('error');
            } else {
                $('.form-row.error:first').removeClass('error').addClass('active-help');
            }
        }

    </script>

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <div class="clear"></div>
    <div name="startTextMessage" id="startTextMessage" style="margin:5px">
        Если Вы получаете пенсию или другие социальные выплаты от ПФР, то для получения справки о видах и их размерах в поле "Операции" укажите интересующий Вас период и нажмите на кнопку "Показать справку"
    </div>
    <div name="failTextMessage" id="failTextMessage" style="display:none;">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="greenBold"/>
            <tiles:put name="control" value="false"/>
            <tiles:put name="data">
                <p>Зачислений от Пенсионного Фонда России за указанный период не обнаружено</p>
            </tiles:put>
        </tiles:insert>
    </div>

    <html:hidden property="fromResource" styleId="fromResource"/>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="errMessagesBlock"/>
        <tiles:put name="isDisplayed" value="false"/>
    </tiles:insert>

    <tiles:insert definition="filterDataPeriod" flush="false">
        <tiles:put name="title" value="Операции"/>
        <tiles:put name="week" value="false"/>
        <tiles:put name="name" value="Period"/>
        <tiles:put name="buttonKey" value="button.printPFRHistoryFull"/>
        <tiles:put name="buttonBundle" value="pfrBundle"/>
        <tiles:put name="windowId" value="pfrHistoryFull"/>
        <tiles:put name="onclick" value="openExtendedAbstract();"/>
    </tiles:insert>
</html:form>