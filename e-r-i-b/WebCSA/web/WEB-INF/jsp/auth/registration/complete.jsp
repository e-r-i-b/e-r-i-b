<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<div class="complete-message">
    <bean:message bundle="commonBundle" key="message.registration.complete"/>

    <div class="buttonsArea">
        <div class="clientButton" onclick="win.close('stageForm');">
            <div class="buttonGrey">
                <div class="left-corner"></div>
                <div class="text">
                    <span>Закрыть</span>
                </div>
                <div class="right-corner"></div>
                <div class="clear"></div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        function closeCallback()
        {
            authForm.showForm("login-form", true);
            return true;
        }

        if(window.win)
            win.aditionalData('stageForm', {closeCallback: closeCallback});
    </script>
</div>