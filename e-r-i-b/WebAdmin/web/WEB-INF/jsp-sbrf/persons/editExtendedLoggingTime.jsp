<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="dictionary">

    <tiles:put name="pageTitle" value="Включение расширенного логирования для клиента"/>

    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.extenededLog.on"/>
            <tiles:put name="commandHelpKey" value="button.extenededLog.on.help"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="onclick" value="extendedLoggingOn()"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="onclick" value="javascript:window.close()"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">
        <script type="text/javascript">
            $(document).ready(function(){
                $('.full-time-template').createMask(DATE_TIME_TEMPLATE);

                var date = new Date();
                var format = 'dd.mm.yyyy hh:min';
                $('#endDate').val((new Date()).addHours(1).asString(format));

            });

            function disableEndDate(checkbox)
            {
                if (checkbox.checked)
                    $('#endDate').attr('disabled', 'disabled');
                else
                    $('#endDate').removeAttr('disabled');
            }

            function validate(endDate, termless)
            {
                if(!termless && !endDate)
                {
                    alert("Заполните поле окончания логирования.");
                    return false;
                }

                return true;
            }

            function extendedLoggingOn(event)
            {
                var endDate = $('#endDate').val();
                var termless = $('#termless').is(':checked');

               if (!validate(endDate, termless))
                  return;

               preventDefault(event);
                    window.opener.sendExtendedLoggingOn(endDate, termless);
               window.close();
               return;
            }
        </script>
        <div class="form-row">
            <div class="paymentLabel"></div>
            <div class="paymentValue">
                До <input type="text" id="endDate" class="filterInput full-time-template" size="20"/>
                <input type="checkbox" name="termless" id="termless" onclick="disableEndDate(this);"/><span style="vertical-align:2px;">бессрочно</span>
            </div>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>