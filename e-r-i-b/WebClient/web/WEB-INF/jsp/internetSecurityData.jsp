<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<br/>
<html:form action="/internetSecurity" show="true" onsubmit="return setEmptyAction(event)">
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
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
		if(document.getElementById('agree').checked)
			return true;

		return false;
	}

    $(document).ready(function()
    {
        var button = $('#confirmButtons');
        $('#opacityBlock').css("width", button.width()+"px");
        $('#opacityBlock').css("height", button.height()+"px")
    });
</script>

    <c:set var="titleText"><bean:message bundle="securityBundle" key="internetSecurity.title"/></c:set>
    <tiles:insert definition="roundBorder" flush="false">
            <tiles:put name="color" value="greenTop"/>
            <tiles:put name="title" value="${titleText}"/>
            <tiles:put name="data" type="string">
                <br/>
                <div class="align-center"><h2 class="grayStyleText">Уважаемый Клиент!</h2></div>
                <br/>
                <bean:message bundle="securityBundle" key="internetSecurity.informationSecurity.notification"/>
                <ul class="list">
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.authPage.notification"/>
                    </li>
                    <li>
                        <b>Никому не сообщайте</b> информацию о Вашем SMS-пароле и паролях с чека, даже сотрудникам
                        банка. В случае потери или кражи чека с паролями обратитесь в Банк или распечатайте новый чек
                        в банкомате.
                    </li>
                    <li>
                        Перед вводом одноразового SMS-пароля всегда <b>сравнивайте реквизиты</b> выполняемой Вами
                        операции с текстом SMS-сообщения. Если реквизиты не совпадают, ни в коем случае не
                        используйте этот пароль и обратитесь в службу технической поддержки банка по телефону
                        +7 (495) 500 5550.
                    </li>
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.cancellationPasswords.notification"/>
                    </li>
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.useDifferentDevices"/>
                    </li>
                    <li>
                        При <b>утрате мобильного телефона</b>, на который Вы получаете сообщения с SMS-паролем, сразу
                        же обратитесь к оператору сотовой связи и <b>заблокируйте SIM-карту</b>.
                    </li>
                    <li>
                        <bean:message bundle="securityBundle" key="internetSecurity.visibilitySettings.notification"/>
                    </li>
                    <li>
                        <b>Не устанавливайте на телефон</b>, на который приходят SMS-сообщения из банка, <b>приложения</b>, полученные из
                        недостоверных источников. Помните, что <b>банк не рассылает</b> своим клиентам ссылки или указания по установке
                        приложений через <b>SMS/MMS/Email - сообщения</b>.
                    </li>
                    <li>
                        В начале работы с системой проверьте, что <b>защищенное соединение установлено</b> именно <b>с
                        официальным сайтом</b> услуги (<a href="https://esk.sberbank.ru" target="_blank">https://esk.sberbank.ru</a> или <a target="_blank" href="https://online.sberbank.ru">https://online.sberbank.ru</a>).
                        Переходить на данную страницу необходимо по ссылке только с официальных ресурсов
                        банка, например, <a href="http://www.sberbank.ru" target="_blank">www.sberbank.ru</a> или <a target="_blank" href="http://www.sberbank.ru">www.sberbank.ru</a>. Не пользуйтесь ссылками с других
                        страниц или ссылками, поступившими по электронной почте.
                    </li>
                    <li>
                        Храните чеки с одноразовыми паролями, напечатанные в банкомате, так же, как ПИН-коды
                        банковских карт: никто кроме Вас не должен иметь доступ к чеку с одноразовыми паролями.
                        <b>В случае их потери</b> или кражи незамедлительно обратитесь в Банк или распечатайте
                        новый чек в банкомате.
                    </li>
                    <li>
                        Используйте <b>современные антивирусные программы</b>, следите за их <b>обновлением</b> и регулярно
                        <b>выполняйте антивирусную проверку</b> на своем компьютере.
                    </li>
                    <li>
                        <b>Своевременно устанавливайте обновления</b> операционной системы своего компьютера,
                        рекомендуемые компанией-производителем.
                    </li>
                    <li>
                        Рекомендуем Вам использовать <b>дополнительное программное обеспечение</b>, позволяющее 
                        повысить уровень защиты Вашего компьютера, например, программы поиска шпионских
                        компонент, программы защиты от «СПАМ» – рассылок и пр.
                    </li>
                    <li>
                        Для безопасного завершения работы с системой необходимо нажимать на кнопку <b>Выход</b>, а не
                        закрывать окно браузера.
                    </li>
                </ul>
                <br />
                <b> Если у Вас есть подозрения, что кто-либо использует Ваш пароль, или исполняются операции, 
                которых Вы не совершали, то необходимо обратиться в Банк!</b>

                <div class="noTitle">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="data">
                            <label><html:checkbox property="field(selectAgreed)" styleId="agree"
                                                  onclick="clickBox(this)"/>
                                &nbsp;Я согласен с условиями соглашения и буду их соблюдать</label>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div class="relative">
                    <div class="opacityBlock opacity65" id="opacityBlock"></div>
                    <div class="buttonsArea" id="confirmButtons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.confirm"/>
                            <tiles:put name="commandTextKey" value="button.confirmOfert"/>
                            <tiles:put name="commandHelpKey" value="button.confirmOfert.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="validationFunction" >checkSelected();</tiles:put>
                        </tiles:insert>
                    </div>
                </div>

         </tiles:put>
    </tiles:insert>
</html:form>