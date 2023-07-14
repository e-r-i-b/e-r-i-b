<%@ page import="java.util.Calendar" %>
<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 27.06.2008
  Time: 12:16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="dictionary">

    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
           <tiles:put name="commandTextKey" value="button.lock"/>
           <tiles:put name="commandHelpKey" value="button.lock.help"/>
           <tiles:put name="bundle" value="employeesBundle"/>
           <tiles:put name="onclick" value="javascript:sendComment(event)"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
           <tiles:put name="commandTextKey" value="button.cancel"/>
           <tiles:put name="commandHelpKey" value="button.cancel"/>
           <tiles:put name="bundle" value="personsBundle"/>
           <tiles:put name="onclick" value="javascript:window.close()"/>
        </tiles:insert>
        <script type="text/javascript">
        function correct()
        {
            var isTermless = document.getElementById("termless").checked;
            var isPeriod = document.getElementById("period").checked;
            var periodStartDate = document.getElementById("periodStartDate");
            var periodEndDate = document.getElementById("periodEndDate");
            var termlessStartDate = document.getElementById("termlessStartDate");
            var blockReason = document.getElementById("blockReason");

            if(isTermless && (termlessStartDate.value==null || termlessStartDate.value.length==0))
            {
                alert("Заполните поле начала блокировки пользователя.");
                return false;
            }
            if(isPeriod && (periodStartDate.value==null || periodStartDate.value.length==0 || periodEndDate.value==null || periodEndDate.value.length==0))
            {
                alert("Заполните поля периода блокировки пользователя.");
                return false;
            }
            if(trim(blockReason.value) == "")
            {
                alert("Заполните причину блокировки.");
                return false;
            }

            return true;
            }

            function sendComment (event)
            {
            var periodStartDate = document.getElementById("periodStartDate");
            var periodEndDate = document.getElementById("periodEndDate");
            var termlessStartDate = document.getElementById("termlessStartDate");
            var blockReason = document.getElementById("blockReason");

            clearInputTemplate("periodStartDate",DATE_FULLTIME_TEMPLATE);
            clearInputTemplate("periodEndDate",DATE_FULLTIME_TEMPLATE);
            clearInputTemplate("termlessStartDate",DATE_FULLTIME_TEMPLATE);

            if (!correct())
              return;

            preventDefault(event);
            if (termlessStartDate.value==null || termlessStartDate.value=="")
                window.opener.setReason(blockReason.value, periodStartDate.value, periodEndDate.value);
            else
            {
                window.opener.setReason(blockReason.value, termlessStartDate.value, null);
            }
            window.close();
            return;
        }
        </script>
    </tiles:put>
     <tiles:put name="data" type="string">
        <div class="pageTitle">Срок блокировки</div>
        <div class="form-row">
            <div class="paymentLabel">
                <input type="radio" name="term" id="termless" value="termless" style="border:none" onclick="enablePeriodDate();">
            </div>
            <div class="paymentValue">
                бессрочная, начиная с <input type="text" id="termlessStartDate" class="filterInput full-time-template" size="20"/>
            </div>
            <div class="clear"></div>
        </div>
        <div class="form-row">
            <div class="paymentLabel">
                <input type="radio" name="term" id="period" value="period" style="border:none" onclick="enableTermlessDate();">
            </div>
            <div class="paymentValue">
                на период с <input type="text" id="periodStartDate" class="filterInput full-time-template" size="20"/> по <input type="text" id="periodEndDate" class="filterInput full-time-template" size="20"/>
            </div>
            <div class="clear"></div>
        </div>
        <div class="form-row">
            <div class="paymentLabel">Причина блокировки</div>
            <div class="paymentValue">
                <input type="text" id="blockReason" size="60" maxlength="256"/>
            </div>
            <div class="clear"></div>
        </div>

		<script type="text/javascript">
		    var termless = document.getElementById("termless");
			var periodStartDate = document.getElementById("periodStartDate");
			var periodEndDate = document.getElementById("periodEndDate");
			var termlessStartDate = document.getElementById("termlessStartDate");

			function init()
			{
				termless.checked = "1";
				termlessStartDate.value = "<%=String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",new Object[]{Calendar.getInstance().getTime()})%>";
				periodStartDate.readOnly = "true";
				periodEndDate.readOnly = "true";
                applyMask($('#termlessStartDate'));
			}

			function enableTermlessDate()
			{
				periodStartDate.removeAttribute("readOnly");
				periodStartDate.value = "<%=String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",new Object[]{Calendar.getInstance().getTime()})%>";
				periodEndDate.removeAttribute("readOnly");
				periodEndDate.value = "<%=String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",new Object[]{DateHelper.add(Calendar.getInstance().getTime(),0,0,1)})%>";
				clearFields(termlessStartDate);
				termlessStartDate.readOnly = "true";
                applyMask($('#periodStartDate'));
                applyMask($('#periodEndDate'));
			}

			function enablePeriodDate()
			{
				clearFields(periodStartDate);
				periodStartDate.readOnly = "true";
				clearFields(periodEndDate);
				periodEndDate.readOnly = "true";
				termlessStartDate.removeAttribute("readOnly");
				termlessStartDate.value = "<%=String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",new Object[]{Calendar.getInstance().getTime()})%>";
                applyMask($('#termlessStartDate'));
			}

            function applyMask(element)
            {
                if (!element.hasClass("ui-mask"))
                    element.createMask(DATE_FULLTIME_TEMPLATE);
            }

            doOnLoad(init);
		</script>
     </tiles:put>
 </tiles:insert>