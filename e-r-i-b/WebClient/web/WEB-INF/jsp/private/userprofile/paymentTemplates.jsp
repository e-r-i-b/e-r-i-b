<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<script type="text/javascript">
    function submitPaymentTemplate()
    {
        var close = true;
        var templateNamesArray = new Array();
        var rows = $('#paymentTemplateWin table.templateList tr');
        rows.each(function(index)
        {
            templateNamesArray[index] = trim($(this).find('input').eq(1).val());
        });
        rows.each(function()
        {
            var tr = $(this);
            tr.removeClass('error');
            var inputs = tr.find('input');
            var templateName = trim(inputs.eq(1).val());
            var nameCount = 0;

            for (var i = 0; i < templateNamesArray.length; i++)
            {
                if (templateNamesArray[i] == templateName)
                {
                    if (nameCount++ > 0)
                        break;
                }
            }

            if (nameCount > 1)
            {
                close = false;
                tr.addClass('error');
                inputs.eq(0).attr('checked', 'true');
                inputs.eq(1).attr('disabled', '');
                var errorBlock = $('#errorTemplatesDiv');
                errorBlock.find('div.messageContainer').text("Название шаблона должно быть уникальным.");
                errorBlock.css('display', 'block');
            }
        });

        if (close)
        {
            <c:forEach var="temp" items="${templates}" varStatus="line">
                var name = trim(ensureElement("templateNames${temp.id}").value);
                if(name == '')
                {
                    var errorBlock = $('#errorTemplatesDiv');
                    errorBlock.find('div.messageContainer').text("Пожалуйста, введите название шаблона.");
                    errorBlock.css('display', 'block');
                    window.scrollTo(0, 0);
                    return;
                }
                ensureElement("templateNamesMapVisile(${temp.id})").innerHTML = escapeHTML(name);
                $('input[name=templateNamesMap(${temp.id})]').eq(0).val(name);
            </c:forEach>
            win.close('paymentTemplate');
        }
        else
        {
            window.scrollTo(0, 0);
        }
    }

    function clickTempl(id)
    {
        var disable = $("#renameTempl"+id).attr("checked");
        ensureElement("templateNames"+id).disabled = !disable;
    }
</script>


<div class="warningMessage" id="errorTemplatesDiv">
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="color" value="red" />
        <tiles:put name="data">
             <div class="messageContainer">Пожалуйста, введите название шаблона.</div>
         </tiles:put>
     </tiles:insert>
 </div>
<h1>Шаблоны платежей</h1>
Для переименования шаблонов Вам необходимо отметить галочками шаблоны и ввести новое название. Затем нажмите кнопку &laquo;Применить&raquo;.
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:if test="${not empty templates}">
    <div>
        <div class="paymentLabel">
            <span class="paymentTextLabel">Переименовать:</span>
        </div>
        <div class="paymentValue">
            <div class="paymentInputDiv">
                <fieldset>
                    <table class="templateList">
                        <c:forEach var="temp" items="${templates}" varStatus="line">
                            <tr>
                                <td class="align-right">
                                    <input type="checkbox" id="renameTempl${temp.id}" value="${temp.id}" onclick="clickTempl(${temp.id})">
                                </td>
                                <td class="align-left">
                                    <input type="text" id="templateNames${temp.id}"  size="60" maxlength="128" disabled="true"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </fieldset>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</c:if>

<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="commonBundle"/>
        <tiles:put name="onclick" value="win.close('paymentTemplate')"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.save"/>
        <tiles:put name="commandHelpKey" value="button.save.help"/>
        <tiles:put name="bundle" value="userprofileBundle"/>
        <tiles:put name="onclick" value="submitPaymentTemplate()"/>
    </tiles:insert>
</div>
