<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%-- ќкно подтверждени€ удалени€ шаблона. --%>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="confirm_delete_template"/>
    <tiles:put name="styleClass" value="confirm_delete_template"/>
    <tiles:put name="data">
        <h1 class="textTransformNone">
            ѕодтверждение удалени€ шаблона
        </h1>

        <div class="clear"></div>
        <br>

        <h2>&nbsp;¬ы действительно хотите удалить выбранный шаблон?</h2>
        
        <div class="clear"></div>
        <br>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('confirm_delete_template');"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>
<script type="text/javascript">
    function openConfirmWindow()
    {
        win.open('confirm_delete_template');
    }
</script>