<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<div class="warningMessage errMessagesBlock displayNone" id="editCardWarningMessages${cardNumber}" >
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="red"/>
        <tiles:put name="data">
        <div class="infoMessage">
            <div class="title"><span>Обратите внимание</span></div>
            <div class="messageContainer"><div class="itemDiv"></div></div>
        </div>
        </tiles:put>
    </tiles:insert>
</div>

<div class="chooseCardBlock">
    <div class="cardImg cardImgChoose"></div>
    <div class="debtCardParams">
        <div class="label_text"><bean:message key="field.cardCurrency" bundle="sbnkdBundle"/></div>
        <html:hidden property="field(cardCODEWAY4${cardNumber})"      styleId="cardCODEWAY4${cardNumber}"  />
        <html:hidden property="field(cardCODEWAY4SHORT${cardNumber})" styleId="cardCODEWAY4SHORT${cardNumber}" />
        <html:hidden property="field(cardBIOAPPLET${cardNumber})"     styleId="cardBIOAPPLET${cardNumber}" />
        <html:hidden property="field(cardPINPAC${cardNumber})"        styleId="cardPINPAC${cardNumber}" />
        <html:hidden property="field(cardBONUSCODE${cardNumber})"     styleId="cardBONUSCODE${cardNumber}" />
        <html:hidden property="field(cardCreditLimit${cardNumber})"   styleId="cardCreditLimit${cardNumber}" />
        <html:hidden property="field(cardName${cardNumber})"          styleId="cardName${cardNumber}" />
        <html:hidden property="field(cardType${cardNumber})"          styleId="cardType${cardNumber}" />
        <html:hidden property="field(cardTypeNumber${cardNumber})"    styleId="cardTypeNumber${cardNumber}" />
        <html:hidden property="field(cardClientCategory${cardNumber})"    styleId="cardClientCategory${cardNumber}" />
        <html:select styleId="cardCurrency${cardNumber}" styleClass="selectSbtS" style="width: 273px;" property="field(cardCurrency${cardNumber})" onclick="changeCardList('${cardNumber}')" onchange="changeCardList('${cardNumber}')">
            <html:option value="RUB">Рубли</html:option>
            <html:option value="USD">Доллары США</html:option>
            <html:option value="EUR">Евро</html:option>
        </html:select>

        <div class="label_text"><bean:message key="field.cardType" bundle="sbnkdBundle"/></div>
        <html:select styleId="cardTypeSelect${cardNumber}"  styleClass="selectSbtS"  style="width: 273px;" property="field(cardTypeSelect${cardNumber})" onclick="changeCardInfo(${cardNumber})" onchange="changeCardInfo(${cardNumber})"></html:select>
        <div id="availableBonus${cardNumber}" class="btmTick">
            <html:checkbox property="field(cardBonus${cardNumber})" value="PG" styleId="agreeTick${cardNumber}" styleClass="float" onchange="changeCard('${cardNumber}')"/>
            <label for="agreeTick${cardNumber}" class="tickDesc"><bean:message key="field.bonuses" bundle="sbnkdBundle"/></label>
        </div>
    </div>
</div>

<div class="clear"></div>
<%--Немного информации о вас--%>
<div id="someInfoAboutYou" class="title_common subtitle_2_level someInfoAboutYou"><bean:message key="label.someInfoAboutYou.title" bundle="sbnkdBundle"/></div>
<div class="contentData">
        <c:if test="${form.guest}">
            <%--Фамилия--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabel">
                    <span class="paymentTextLabel">
                        <bean:message key="field.lastName" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                    </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <input type="text" id='lastName${cardNumber}' class="sValueInput" maxlength="60" value="${form.fields['lastName']}" onchange="userNamesHandler(this);"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <%--Имя--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabel">
                    <span class="paymentTextLabel">
                        <bean:message key="field.firstName" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                    </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <input type="text" id='firstName${cardNumber}' class="sValueInput" maxlength="30" value="${form.fields['firstName']}" onchange="userNamesHandler(this);"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <%--Отчество--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabel">
                    <span class="paymentTextLabel">
                        <bean:message key="field.patrName" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                    </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <input type="text" id='patrName${cardNumber}' class="sValueInput float patrName" maxlength="30" value="${form.fields['patrName']}" onchange="userNamesHandler(this);" <c:if test="${form.patrNameAbsent}">disabled='true'</c:if>/>
                        <div class="btmTick-s">
                            <input type="checkbox"  value="true" onclick="checkPatrName(this, '${cardNumber}')" id="patrNameAbsent${cardNumber}" class="float" <c:if test="${form.patrNameAbsent}">checked</c:if> />
                            <label class="tickDesc"><bean:message key="field.patrName.absent" bundle="sbnkdBundle"/></label>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <%--Пол--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabel">
                    <span class="paymentTextLabel">
                        <bean:message key="field.gender" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                    </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <div class="lineRadioBtn">
                            <input type="radio" name="gender${cardNumber}" value="M" id="genderM${cardNumber}" <c:if test="${form.fields['gender'] == 'M'}">checked</c:if>/>
                            <label for="genderM${cardNumber}"><bean:message key="field.gender.male" bundle="sbnkdBundle"/></label>
                        </div>
                        <div class="lineRadioBtn">
                            <input type="radio" name="gender${cardNumber}" value="F" id="genderM${cardNumber}" <c:if test="${form.fields['gender'] == 'F'}">checked</c:if>/>
                            <label for="genderF${cardNumber}"><bean:message key="field.gender.female" bundle="sbnkdBundle"/></label>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
                <c:if test="${form.fields['gender']==null or form.fields['gender']==''}">
                    <script type="text/javascript">
                        el = document.getElementById("genderM${cardNumber}");
                        if(el != null)
                            el.checked = true;
                    </script>
                </c:if>
            </div>
        </c:if>
    <%--Имя и фамилия латинскими буквами--%>
    <div class="form-row orderRow">
        <div class="paymentLabel bLabel">
            <span class="paymentTextLabel">
                <bean:message key="field.lastName.firstName.latin" bundle="sbnkdBundle"/><span class="asterisk">*</span>
            </span>
        </div>
        <div class="paymentValue sValue">
            <div class="paymentInputDiv">
                <input type="text" id='firstNameAndLastNameLatin${cardNumber}' class="sValueInput" maxlength="60" value="${form.fields['firstNameAndLastNameLatin']}"/>
                <div class="field_hint"><bean:message key="field.lastName.firstName.latin.hint" bundle="sbnkdBundle"/></div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <%--E-mail для связи--%>
    <div class="form-row orderRow">
        <div class="paymentLabel bLabel">
            <span class="paymentTextLabel">
                <bean:message key="field.email" bundle="sbnkdBundle"/>
            </span>
        </div>
        <div class="paymentValue sValue">
            <div class="paymentInputDiv">
                 <input type="text" id='email${cardNumber}' class="sValueInput" value="${form.fields['email']}"/>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
