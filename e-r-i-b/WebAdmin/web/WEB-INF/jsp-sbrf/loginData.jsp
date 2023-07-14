<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>

<html:form action="/login" show="true" onsubmit="this.onsubmit = function(){ alert('Ваш запрос обрабатывается, нажмите OК для продолжения'); return false; }; return setEmptyAction();">
<html:hidden property="clientRandom" styleId="clientRandom"/>
<html:hidden property="serverRandom" styleId="serverRandom"/>
<html:hidden property="password" styleId="password"/>
<script language="JavaScript">
	function onLoadFn()
	{
		var el = document.getElementById("LoginDiv");
		var h = Math.round((document.body.clientHeight - el.offsetHeight) / 2);
		var w = Math.round((document.body.clientWidth - el.offsetWidth) / 2);
		if (h < 1) h = 1;
		if (w < 1) w = 1;
		el.style.top = h + "px";
		el.style.left = w + "px";
	}
	function checkData()
	{
		var n = getElement("login");
		var p = getElement("passwordTxt");

		if (n.value.length == 0 || p.value.length == 0)
		{
			alert("Введите логин пользователя и пароль.");
			return false;
		}

		var challenge = sr + cr;
		var ph = fnStrHashLikeGOST(p.value);
		var ar = fnHmacLikeGOST(ph, challenge);
		document.getElementById("password").value = ar;
		document.getElementById("clientRandom").value = cr;
		p.value = '';
		return true;
	}

    var HEX_DIGITS = "0123456789ABCDEF";
	function randomHex(n)
	{
		var res = "";

		for (var i = 0; i < n; i++)
		{
			var p = Math.floor(Math.random() * 16);
			res = res + HEX_DIGITS.charAt(p);
		}

		return res;
	}

	var sr = getElement("serverRandom").value;
	var cr = randomHex(16);

	addEventListenerEx(window, 'load', onLoadFn, false);
</script>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<div id="pageContent" class="fonContainer loginBlock">
    <input type="hidden" name="accessType" value="employee"/>
        <html:form show="true" style="margin: 0;">

            <div id="header">
                <div class="roundTL">
                    <div class="roundTR">
                        <div class="clear"></div>
                        <div class="hdrContainer ">
                            <div class="NewHeader">
                                <div class="Logo">
                                    <a class="logoImage logoImageText" onclick="return redirectResolved();" href="${phiz:calculateActionURL(pageContext, '/login')}">
                                        <img alt="" src="${skinUrl}/images/logoHeader.gif">
                                    </a>
                                </div>
                            </div>
                            <div class="topLineContainer">
                                <div class="UserInfo">
                                    <div class="timeBlock">
                                        <div class="hourBlock" id="hours">
                                            <script type="text/javascript">
                                                obj_hours=document.getElementById("hours");
                                                function wr_hours()
                                                {
                                                    time=new Date();

                                                    time_min=time.getMinutes();
                                                    time_hours=time.getHours();
                                                    time_wr=((time_hours<10)?"0":"")+time_hours;
                                                    time_wr+=":";
                                                    time_wr+=((time_min<10)?"0":"")+time_min;
                                                    obj_hours.innerHTML=time_wr;
                                                }
                                                wr_hours();
                                                setInterval("wr_hours();",1000);
                                            </script>

                                        </div>
                                        <script type="text/javascript">
                                            var dateStr = time.getDate() + ' ' + monthToStringByNumber(time.getMonth()) + ' ' + (time.getFullYear());
                                            document.write(dateStr);
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="clear"></div>
            <div class="wrapper">
                <div class="content noLeftMenu" id="authBlock">
                    <div class="mainInnerBlock">
                        <div class="whiteLoginBlock" id="LoginDiv">
                            <div class="login" onkeypress="onEnterKey(event);">
                                <h2>
                                    <img src="${skinUrl}/images/lock.jpg"/>
                                    <span class="auth-title"> Вход в систему</span>
                                </h2>
                                <div class="clear"></div>

                                <div class="loginFields">
                                    <label>Логин</label>
                                    <div class="clear"></div>
                                    <html:text property="login"  styleClass="inputLogin" maxlength="${LoginForm.maxLengthLogins}"/>
                                </div>
                                <div class="loginFields">
                                    <label>Пароль</label>
                                    <div class="clear"></div>
                                    <input type="password" id="passwordTxt" name="passwordTxt" maxlength="30" class="inputLogin"/>
                                </div>

                                <div class="buttonsArea">
                                    <input type="hidden" name="accessType" value="employee"/>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey" value="button.login"/>
                                        <tiles:put name="commandTextKey" value="button.enter"/>
                                        <tiles:put name="commandHelpKey" value="button.login.help"/>
                                        <tiles:put name="bundle" value="securityBundle"/>
                                        <tiles:put name="isDefault" value="true"/>
                                        <tiles:put name="validationFunction">
                                            checkData();
                                        </tiles:put>
                                    </tiles:insert>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>

                <div class="error">&nbsp;
                    <html:messages id="error" bundle="securityBundle">
                        <%=error%>
                    </html:messages>
                </div>

                <div class="loginCopyright">R-Style Softlab, 2015. Все права защищены ©</div>

            </div>
            <div class="clear"></div>
        </html:form>
    </div>
</html:form>
