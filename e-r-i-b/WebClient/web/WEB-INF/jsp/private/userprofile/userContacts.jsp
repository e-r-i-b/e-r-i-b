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
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="bundle" value="userprofileBundle"/>

    <script type="text/javascript">
        function submitUserContacts()
        {
            var email = trim(ensureElement("fieldEmail").value);
            var hPhone = trim(ensureElement("fieldHomePhone").value);
            var jPhone = trim(ensureElement("fieldJobPhone").value);

            var mailFormat = trim(ensureElement("fieldMailFormat").value);

            //валидируем поля, если валидация прошла - закрываем окно
            if (runValidateInputs())
            {
                ensureElement("email").innerHTML = escapeHTML(email);
                ensureElement("homePhone").innerHTML = escapeHTML(hPhone);
                ensureElement("jobPhone").innerHTML = escapeHTML(jPhone);
                var mailFormatOptionName = escapeHTML(mailFormat);
                var outputMailFormatName;
                if (mailFormatOptionName == "PLAIN_TEXT")
                    outputMailFormatName = "<bean:message key="mailFormat.value.PLAIN_TEXT.description" bundle="${bundle}"/>";
                else if (mailFormatOptionName == "HTML")
                    outputMailFormatName = "<bean:message key="mailFormat.value.HTML.description" bundle="${bundle}"/>";

                if (email == '')
                    ensureElement("mailFormat").innerHTML = "<bean:message key="label.profile.email.empty" bundle="${bundle}"/>";
                else
                    ensureElement("mailFormat").innerHTML = outputMailFormatName;

                setField("email", email);
                setField("homePhone", hPhone);
                setField("jobPhone", jPhone);
                setField("mailFormat", mailFormat);

                win.close('userContacts');
            }
        }

        //валидируем все поля на форме
        function runValidateInputs()
        {
        	var userContactsDiv = document.getElementById('userContacts');
        	var inputs = userContactsDiv.getElementsByTagName('input');
        	var allMsgs = [];
        	var element = null;
            //для каждого инпута формы запускаем его валидаторы и выводим сообщения если получили их
        	for (var i=0; i < inputs.length; i++)
        	{
        		element = inputs[i];
        		var parentDiv = findParentByClassName(element, payInput.CLASS_NAME);
        		if (parentDiv == document)
        			parentDiv = findParentByClassName(element, payInput.ACTIVE_CLASS_NAME);
        		if (parentDiv == document)
        			parentDiv = findParentByClassName(element, payInput.ERROR_CLASS_NAME);
        		if (payInput.validators[element.name] != undefined)
        		{
        			var msgs = payInput.validators[element.name](element);
        			if (msgs != undefined && msgs.length > 0)
                    {
        				payInput.openClosePreventErrorDiv(parentDiv, msgs, false);
        				allMsgs.push(msgs);
        			}
                    else
                        payInput.openClosePreventErrorDiv(parentDiv, null, true);
        		}
        	}

            return !(allMsgs != undefined && allMsgs.length > 0);
        }

        //валидация и вывод сообщений
        function validateUserContacts(obj, currentValidators)
        {
            var regexp = currentValidators.VALIDATE_REGEXP_LETTERS_NAME;

            var doubleHyphenRegexp = /.*-{2,}.*/;     //двойной дефис
            var doubleSpacesRegexp = /.*\s{2,}.*/;      //двойной пробел
            var wordSpaceHyphenRegexp = /.*(\s-|-\s).*/;   //пробел с тире и в обратном порядке

            var rowNames = {};
            rowNames ["fieldEmail"] = "<bean:message key="label.profile.email" bundle="${bundle}"/>";
            rowNames ["fieldHomePhone"] = "<bean:message key="label.profile.phone.home" bundle="${bundle}"/>";
            rowNames ["fieldJobPhone"] = "<bean:message key="label.profile.phone.job" bundle="${bundle}"/>";

            var msgs = [];

            if (doubleHyphenRegexp.test(obj.value))
            {
                msgs.push("Поле " + rowNames[obj.name] + " содержит два или более дефиса подряд");
            }
            else if (doubleSpacesRegexp.test(obj.value))
            {
                msgs.push("Поле " + rowNames[obj.name] + " содержит два или более пробела подряд");
            }
            else if (wordSpaceHyphenRegexp.test(obj.value))
            {
                msgs.push("Поле " + rowNames[obj.name] + " содержит пробел между словом и дефисом");
            }

            else if (regexp != undefined && regexp == EMAIL_REGEXP && !matchRegexp(obj.value, regexp))
            {
               msgs.push("Пожалуйста, введите корректный адрес. E-mail должен содержать точку и символ @.");
            }
            else if (regexp != undefined && !matchRegexp(obj.value, regexp))
            {
                msgs.push("Пожалуйста, проверьте, правильно ли Вы заполнили данное поле.");
            }
            else if((obj.value == "" || obj.value == null) && regexp == EMAIL_REGEXP)
            {
                msgs.push("Пожалуйста, укажите Ваш E-mail.");
            }

            return msgs;
        }
    </script>


<h1><bean:message key="title.profile.contacts" bundle="${bundle}"/></h1>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message key="label.profile.email" bundle="${bundle}"/></tiles:put>
    <tiles:put name="data">
        <input type="text" size="40" maxlength="30" id="fieldEmail" name="fieldEmail"/>
    </tiles:put>
    <tiles:put name="minValue">6</tiles:put>
    <tiles:put name="regexp">EMAIL_REGEXP</tiles:put>
    <tiles:put name="fieldName">fieldEmail</tiles:put>
    <tiles:put name="validateMethod">validateUserContacts</tiles:put>


</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message key="label.profile.mailFormat" bundle="${bundle}"/></tiles:put>
    <tiles:put name="data">
        <html:select property="field(mailFormat)" styleId="fieldMailFormat">
            <html:option value="PLAIN_TEXT"><bean:message key="mailFormat.value.PLAIN_TEXT" bundle="${bundle}"/> </html:option>
            <html:option value="HTML"><bean:message key="mailFormat.value.HTML" bundle="${bundle}"/> </html:option>
        </html:select>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message key="label.profile.phone.home" bundle="${bundle}"/></tiles:put>
    <tiles:put name="data">
        <input type="text" size="40" maxlength="10" id="fieldHomePhone" name="fieldHomePhone" ${form.clientUDBO ? "disabled" : ""}/>
    </tiles:put>
    <tiles:put name="minValue">6</tiles:put>
    <tiles:put name="regexp">PHONE_LETTERS_REGEXP</tiles:put>
    <tiles:put name="fieldName">fieldHomePhone</tiles:put>
    <tiles:put name="validateMethod">validateUserContacts</tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message key="label.profile.phone.job" bundle="${bundle}"/></tiles:put>
    <tiles:put name="data">
        <input type="text" size="40" maxlength="10" id="fieldJobPhone" name="fieldJobPhone" ${form.clientUDBO ? "disabled" : ""}/>
    </tiles:put>
    <tiles:put name="minValue">6</tiles:put>
    <tiles:put name="regexp">PHONE_LETTERS_REGEXP</tiles:put>
    <tiles:put name="fieldName">fieldJobPhone</tiles:put>
    <tiles:put name="validateMethod">validateUserContacts</tiles:put>
</tiles:insert>

<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="commonBundle"/>
        <tiles:put name="onclick" value="win.close('userContacts')"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.save"/>
        <tiles:put name="commandHelpKey" value="button.save.help"/>
        <tiles:put name="bundle" value="userprofileBundle"/>
        <tiles:put name="onclick" value="submitUserContacts()"/>
    </tiles:insert>
</div>
