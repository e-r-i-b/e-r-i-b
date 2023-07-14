<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<script type="text/javascript">
    $(document).ready(function(){
        <c:choose>
            <c:when test="${form.useWidget}">$("#standartModeImg").hide();</c:when>
            <c:otherwise>$("#extendedModeImg").hide();</c:otherwise>
        </c:choose>
        $('#standartMode').hover(function(){
           $(this).addClass('hover');
           }, function() {
           $(this).removeClass('hover');
        });
        $('#extendedMode').hover(function(){
           $(this).addClass('hover');
           }, function() {
           $(this).removeClass('hover');
        });
        $('#standartMode').click(function(){
            $("#standartModeImg").show();
            $("#extendedModeImg").hide();
            $("input[name=useWidget]").val(false);
        });
         $('#extendedMode').click(function(){
            $("#standartModeImg").hide();
            $("#extendedModeImg").show();
            $("input[name=useWidget]").val(true);
        });
    });
</script>
<div class="interfaceModeBlock">
    <html:hidden property="useWidget" />
    <table>
        <tr>
            <td class="alignRight Width90">Интерфейс:</td>
            <td>

                <div class="interfaceMode" id="standartMode">
                    <div class="tick"><img src="${imagePath}/tick.png" id="standartModeImg" width="32" height="32"/></div><span class="modeName">Стандартный</span>
                </div>
                <div class="interfaceMode" id="extendedMode">
                    <div class="tick"><img src="${imagePath}/tick.png" id="extendedModeImg" width="32" height="32"/></div><span class="modeName">Расширенный</span><span class="descriptionMode"> Этот стиль использует технологию виджетов.</span>
                    <div class="note">Обновленный вид Сбербанк Онлайн недоступен в браузере Internet Explorer 6.</div>
               </div>
            </td>
        </tr>
    </table>
    <div class="buttonsArea">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
            <tiles:put name="bundle" value="commonBundle"/>
            <tiles:put name="action" value="/private/accounts.do"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.setInterfaceMode"/>
            <tiles:put name="commandHelpKey" value="button.setInterfaceMode"/>
            <tiles:put name="bundle" value="favouriteBundle"/>
        </tiles:insert>
    </div>
</div>