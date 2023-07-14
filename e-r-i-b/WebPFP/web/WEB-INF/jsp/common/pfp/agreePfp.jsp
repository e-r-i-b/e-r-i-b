<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/agree" onsubmit="return setEmptyAction(event)">

    <script type="text/javascript">
        function clickBox(obj)
        {
            var opacityBlock = $("#opacityBlock");
            if (obj.checked)
            {
                opacityBlock.hide();
                return;
            }

            opacityBlock.show();
        }

        function checkSelected()
        {
            if($('#agree').attr("checked"))
                return true;

            return false;
        }

        $(document).ready(function() 
        {
            var button = $('#confirmButtons');
            $('#opacityBlock').css("width", button.width()+"px");
            $('#opacityBlock').css("height", button.height()+"px");
        });
    </script>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="description">
            <tiles:put name="title">
                <bean:message bundle="pfpBundle" key="page.title"/>
            </tiles:put>
            <bean:message bundle="pfpBundle" key="index.description"/>
        </tiles:put>

        <tiles:put name="data">
            <div class="pfpBlocks">

                <div class="noTitle">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="data">
                            <html:checkbox property="field(selectAgreed)" styleId="agree" onclick="clickBox(this)"/>
                            <label for="agree">&nbsp;—огласен с услови€ми персонального планировани€</label>
                        </tiles:put>
                        <tiles:put name="clazz" value="pfpFormRow"/>
                    </tiles:insert>
                </div>

                <div class="pfpButtonsBlock confirmBlock relative">
                    <div class="opacityBlock opacity65" id="opacityBlock"></div>
                    <div id="confirmButtons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.startNewPlanning"/>
                            <tiles:put name="commandTextKey" value="index.button.next.text"/>
                            <tiles:put name="commandHelpKey" value="index.button.next.help"/>
                            <tiles:put name="isDefault"        value="true"/>
                            <tiles:put name="bundle"         value="pfpBundle"/>
                            <tiles:put name="validationFunction" >checkSelected();</tiles:put>
                        </tiles:insert>
                    </div>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

</html:form>
