<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper" 
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="application" select="'application'"/>
    <xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
    <xsl:param name="isAdminApplication">
        <xsl:value-of select="$application = 'PhizIA'"/>
    </xsl:param>

    <xsl:template match="/">
        <xsl:apply-templates mode="view"/>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="claimDictionaries" select="document('loan-claim-dictionaries.xml')"/>
        <xsl:variable name="departments" select="document('tbList.xml')"/>

        <xsl:if test="stepFilled-INITIAL2='true'">
            <!--Персональные данные-->
            <xsl:call-template name="printTitle">
                <xsl:with-param name="value">Личные данные</xsl:with-param>
            </xsl:call-template>

            <input type="hidden" name="loanOfferId" id="loanOffer" value="{loanOfferId}"/>


            <!--блок "принадлежность к Сбербанку"-->
            <div id="personInfo">
                <xsl:if test="loanOfferId = ''">
                    <xsl:variable name="sbrfRelationType" select="sbrfRelationType"/>
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Принадлежность к Сбербанку:</xsl:with-param>
                        <xsl:with-param name="value">
                            <span><b>
                                <xsl:choose>
                                    <xsl:when test="$sbrfRelationType = 'getPaidOnSbrfAccount'">
                                        <xsl:value-of select="'Получаю з/п (пенсию) на карту/счет в Сбербанке'"/>
                                    </xsl:when>
                                    <xsl:when test="$sbrfRelationType = 'workInSbrf'">
                                        <xsl:value-of select="'Являюсь сотрудником Сбербанка'"/>
                                    </xsl:when>
                                    <xsl:when test="$sbrfRelationType = 'otherSbrfRelationType'">
                                        <xsl:value-of select="'Другое'"/>
                                    </xsl:when>
                                </xsl:choose>
                            </b></span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Счёт или карта:</xsl:with-param>
                        <xsl:with-param name="value">
                            <span><b>
                                <xsl:choose>
                                    <xsl:when test="sbrfResource != ''">
                                        <xsl:value-of select="sbrfResource"/>
                                    </xsl:when>
                                    <xsl:when test="sbrfAccount != ''">
                                        Счёт №<xsl:value-of select="sbrfAccount"/>
                                    </xsl:when>
                                </xsl:choose>
                            </b></span>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
                <xsl:call-template name="printTitle">
                     <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Краткая информация о Вас</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">ФИО:</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:choose>
                           <!--Не маскировать для АРМ сотрудника-->
                            <xsl:when test="$isAdminApplication='true'">
                                <span class="floatLeft">
                                    <b>
                                        <xsl:value-of select="firstName" disable-output-escaping="no"/>&nbsp;
                                        <xsl:value-of select="patrName" disable-output-escaping="no"/>&nbsp;
                                        <xsl:value-of select="surName" disable-output-escaping="no"/>
                                    </b>
                                </span>
                            </xsl:when>
                            <xsl:otherwise>
                                <span class="floatLeft"><b><xsl:value-of select="ph:getFormattedPersonName(firstName, surName, patrName)" disable-output-escaping="no"/></b></span>
                                <xsl:call-template name="popUpMessage">
                                    <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                    <xsl:with-param name="app" select="$app"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Ваш пол:</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:variable name="gender" select="gender"/>
                        <xsl:choose>
                            <xsl:when test="$gender = 'MALE'">Мужчина</xsl:when>
                            <xsl:when test="$gender = 'FEMALE'">Женщина</xsl:when>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Дата рождения:</xsl:with-param>
                    <xsl:with-param name="value">
                        <span class="floatLeft"><b><xsl:value-of  select="dh:formatXsdDateToString(birthDay)"/></b></span>
                        <xsl:if test="$isAdminApplication='false'">
                            <xsl:call-template name="popUpMessage">
                                <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                <xsl:with-param name="app" select="$app"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Гражданство:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b>Российская Федерация</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Место рождения:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="birthPlace"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Номер мобильного телефона:</xsl:with-param>
                    <xsl:with-param name="value">
                        <span class="floatLeft"><b>+<xsl:value-of  select="mobileCountry"/>&nbsp;<xsl:value-of  select="mobileTelecom"/>&nbsp;<xsl:value-of  select="mobileNumber"/></b></span>
                        <xsl:call-template name="popUpMessage">
                            <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                            <xsl:with-param name="app" select="$app"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Номер рабочего телефона:</xsl:with-param>
                    <xsl:with-param name="value">
                        <span class="floatLeft"><b>+<xsl:value-of  select="jobphoneCountry"/>&nbsp;<xsl:value-of  select="jobphoneTelecom"/>&nbsp;<xsl:value-of  select="jobphoneNumber"/></b></span>
                        <xsl:call-template name="popUpMessage">
                            <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                            <xsl:with-param name="app" select="$app"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="residencePhoneCountry != '' and residencePhoneTelecom != '' and residencePhoneNumber != ''">
                    <xsl:call-template name="additionalPhoneViewInfo">
                        <xsl:with-param name="phoneBlankType">RESIDENCE</xsl:with-param>
                        <xsl:with-param name="phoneBlankCountry"><xsl:value-of  select="residencePhoneCountry"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankTelecom"><xsl:value-of  select="residencePhoneTelecom"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankNumber"><xsl:value-of  select="residencePhoneNumber"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="phoneBlank_1_type != ''">
                    <xsl:call-template name="additionalPhoneViewInfo">
                        <xsl:with-param name="phoneBlankType"><xsl:value-of  select="phoneBlank_1_type"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankCountry"><xsl:value-of  select="phoneBlank_1_country"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankTelecom"><xsl:value-of  select="phoneBlank_1_telecom"/></xsl:with-param>
                        <xsl:with-param name="phoneBlankNumber"><xsl:value-of  select="phoneBlank_1_number"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Электронная почта:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="email"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Ваш ИНН:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="inn"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="fioChanged ='true'">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">&nbsp;</xsl:with-param>
                        <xsl:with-param name="value">
                            <div class="textTick">
                                <input class="float" type="checkbox" disabled="disabled" checked="checked"/>
                                <label class="float">&nbsp;<b>Я менял (-а) ФИО</b></label>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Дата изменения ФИО:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="dh:formatXsdDateToString(fioChangedDate)"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Предыдущая фамилия:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="previosSurname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Предыдущее имя:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="previosFirstname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Предыдущее отчество:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="previosPatr"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Причина изменения:</xsl:with-param>
                        <xsl:with-param name="value">
                            <xsl:choose>
                                <xsl:when test="fioChangedReasonType = 'MARRIAGE'">
                                    <b>Вступление в брак</b>
                                </xsl:when>
                                <xsl:when test="fioChangedReasonType = 'OTHER'">
                                    <b><xsl:value-of  select="fioChangedOtherReason"/></b>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Паспортные данные</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Дата выдачи:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="dh:formatXsdDateToString(passportIssueDate)"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Код подразделения:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="passportIssueByCode"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Серия и номер паспорта:</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:choose>
                            <!--Не маскировать для АРМ сотрудника-->
                            <xsl:when test="$isAdminApplication='true'">
                                <span class="floatLeft"><xsl:value-of select="passportSeries"/> &nbsp; <xsl:value-of select="passportNumber"/></span>
                            </xsl:when>
                            <xsl:otherwise>
                                <span class="floatLeft"><b><xsl:value-of select="sh:maskText(passportSeries)"/> &nbsp; <xsl:value-of select="sh:maskText(passportNumber)"/></b></span>
                                <xsl:call-template name="popUpMessage">
                                    <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                                    <xsl:with-param name="app" select="$app"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Кем выдан:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of select="passportIssueBy"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="hasOldPassport = 'true'">
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value">
                            <span class="blockTitle">Данные старого паспорта</span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Дата выдачи:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="dh:formatXsdDateToString(oldPassportIssueDate)"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Серия и номер паспорта:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of select="oldPassportSeriesAndNumber"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Кем выдан:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of select="oldPassportIssueBy"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">У вас есть загранпаспорт?:</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:variable name="hasForeignPassport" select="hasForeignPassport"/>
                        <xsl:choose>
                            <xsl:when test="$hasForeignPassport = 'true'">Да</xsl:when>
                            <xsl:when test="$hasForeignPassport = 'false'">Нет</xsl:when>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Образование</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Образование:</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:variable name="educationType" select="educationTypeSelect"/>
                        <b><xsl:value-of  select="$claimDictionaries/config/list-education/education[code = $educationType]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="educationTypeSelect = '4'">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Курс неоконченного высшего образования:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="higherEducationCourse"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

            </div>
        </xsl:if>

        <!--Семейное положение-->
        <xsl:if test="stepFilled-INITIAL3='true'">
            <xsl:call-template name="printTitle">
                <xsl:with-param name="value">Семья</xsl:with-param>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="familyInfo">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Семейное положение</b></xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="familyStatuses" select="$claimDictionaries/config/list-family-status"/>

                <xsl:variable name="familyStatus"><xsl:value-of  select="familyStatusSelect"/></xsl:variable>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Семейное положение:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="$familyStatuses/family-status[code=$familyStatus]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Информация о супруге-->
                <xsl:variable name="spouseInfoRequired"><xsl:value-of  select="$familyStatuses/family-status[code=$familyStatus]/spouseInfoRequired"/></xsl:variable>
                <xsl:if test="$spouseInfoRequired != 'NOT'">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Фамилия супруга(-и):</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="partnerSurname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Имя супруга(-и):</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="partnerFirstname"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Отчество супруга(-и):</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="partnerPatr"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Дата рождения супруга(-и):</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="dh:formatXsdDateToString(partnerBirthday)"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Супруг находится на иждивении:</xsl:with-param>
                        <xsl:with-param name="value">
                            <xsl:variable name="partnerOnDependent" select="partnerOnDependent"/>
                            <xsl:choose>
                                <xsl:when test="$partnerOnDependent = 'true'">Да</xsl:when>
                                <xsl:when test="$partnerOnDependent = 'false'">Нет</xsl:when>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Супруг имеет кредиты в Сбербанке</xsl:with-param>
                        <xsl:with-param name="value">
                            <xsl:variable name="partnerHasLoansInSber" select="partnerHasLoansInSber"/>
                            <xsl:choose>
                                <xsl:when test="$partnerHasLoansInSber = '1'">Да</xsl:when>
                                <xsl:when test="$partnerHasLoansInSber = '2'">Нет</xsl:when>
                                <xsl:when test="$partnerHasLoansInSber = '3'">Не знаю</xsl:when>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:if test="$spouseInfoRequired='REQUIRED'">
                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Есть ли брачный контракт</xsl:with-param>
                            <xsl:with-param name="value">
                                <xsl:variable name="hasPrenup" select="hasPrenup"/>
                                <xsl:choose>
                                    <xsl:when test="$hasPrenup = 'true'">Да</xsl:when>
                                    <xsl:when test="$hasPrenup = 'false'">Нет</xsl:when>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

                <xsl:variable name="familyRelations" select="$claimDictionaries/config/list-family-relation"/>
                <!--Информация о детях-->
                <xsl:variable name="childType1" select="child_1_relativeType"/>
                <xsl:if test="childType1 != ''">
                    <xsl:call-template name="printTitle">
                        <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Дети</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="relativesViewInfo">
                        <xsl:with-param name="dictionary" select="$familyRelations"/>
                        <xsl:with-param name="title" select="'Информация о ребенке'"/>
                        <xsl:with-param name="relativeType"><xsl:value-of  select="$childType1"/></xsl:with-param>
                        <xsl:with-param name="relativeSurname"><xsl:value-of select="child_1_surname"/></xsl:with-param>
                        <xsl:with-param name="relativeName"><xsl:value-of select="child_1_name"/></xsl:with-param>
                        <xsl:with-param name="relativePatrName"><xsl:value-of select="child_1_patrName"/></xsl:with-param>
                        <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_1_birthday)"/></xsl:with-param>
                        <xsl:with-param name="relativeDependent"><xsl:value-of select="child_1_dependent"/></xsl:with-param>
                        <xsl:with-param name="relativeCredit"><xsl:value-of select="child_1_credit"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_1_employee"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_1_employeePlace"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="childType2" select="child_2_relativeType"/>
                    <xsl:if test="$childType2 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType2"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_2_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_2_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_2_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_2_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_2_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_2_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_2_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_2_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType3" select="child_3_relativeType"/>
                    <xsl:if test="$childType3 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType3"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_3_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_3_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_3_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_3_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_3_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_3_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_3_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_3_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType4" select="child_4_relativeType"/>
                    <xsl:if test="$childType4 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType4"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_4_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_4_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_4_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_4_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_4_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_4_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_4_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_4_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType5" select="child_5_relativeType"/>
                    <xsl:if test="$childType5 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType5"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_5_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_5_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_5_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_5_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_5_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_5_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_5_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_5_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType6" select="child_6_relativeType"/>
                    <xsl:if test="$childType6 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType6"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_6_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_6_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_6_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_6_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_6_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_6_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_6_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_6_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType7" select="child_7_relativeType"/>
                    <xsl:if test="$childType7 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType7"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_7_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_7_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_7_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_7_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_7_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_7_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_7_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_7_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType8" select="child_8_relativeType"/>
                    <xsl:if test="$childType8 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType8"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_8_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_8_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_8_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_8_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_8_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_8_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_8_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_8_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType9" select="child_9_relativeType"/>
                    <xsl:if test="$childType9 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType9"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_9_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_9_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_9_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_9_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_9_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_9_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_9_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_9_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType10" select="child_10_relativeType"/>
                    <xsl:if test="$childType10 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType10"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_10_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_10_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_10_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_10_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_10_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_10_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_10_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_10_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType11" select="child_11_relativeType"/>
                    <xsl:if test="$childType11 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType11"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_11_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_11_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_11_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_11_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_11_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_11_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_11_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_11_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="childType12" select="child_12_relativeType"/>
                    <xsl:if test="$childType12 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$childType12"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="child_12_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="child_12_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="child_12_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(child_12_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="child_12_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="child_12_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="child_12_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="child_12_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

                <!--Информация о родственниках-->
                <xsl:variable name="relativeType1" select="relative_1_relativeType"/>
                <xsl:if test="$relativeType1 != ''">
                    <xsl:call-template name="printTitle">
                        <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Родственники</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="relativesViewInfo">
                        <xsl:with-param name="dictionary" select="$familyRelations"/>
                        <xsl:with-param name="title" select="'Информация о родственнике'"/>
                        <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType1"/></xsl:with-param>
                        <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_1_surname"/></xsl:with-param>
                        <xsl:with-param name="relativeName"><xsl:value-of select="relative_1_name"/></xsl:with-param>
                        <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_1_patrName"/></xsl:with-param>
                        <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_1_birthday)"/></xsl:with-param>
                        <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_1_dependent"/></xsl:with-param>
                        <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_1_credit"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_1_employee"/></xsl:with-param>
                        <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_1_employeePlace"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="relativeType2" select="relative_2_relativeType"/>
                    <xsl:if test="$relativeType2 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о родственнике'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType2"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_2_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_2_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_2_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_2_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_2_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_2_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_2_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_2_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType3" select="relative_3_relativeType"/>
                    <xsl:if test="$relativeType3 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о родственнике'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType3"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_3_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_3_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_3_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_3_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_3_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_3_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_3_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_3_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType4" select="relative_4_relativeType"/>
                    <xsl:if test="$relativeType4 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType4"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_4_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_4_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_4_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_4_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_4_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_4_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_4_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_4_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType5" select="relative_5_relativeType"/>
                    <xsl:if test="$relativeType5 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType5"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_5_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_5_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_5_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_5_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_5_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_5_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_5_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_5_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType6" select="relative_6_relativeType"/>
                    <xsl:if test="$relativeType6 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType6"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_6_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_6_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_6_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_6_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_6_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_6_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_6_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_6_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType7" select="relative_7_relativeType"/>
                    <xsl:if test="$relativeType7 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType7"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_7_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_7_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_7_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_7_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_7_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_7_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_7_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_7_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType8" select="relative_8_relativeType"/>
                    <xsl:if test="$relativeType8 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType8"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_8_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_8_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_8_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_8_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_8_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_8_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_8_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_8_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType9" select="relative_9_relativeType"/>
                    <xsl:if test="$relativeType9 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType9"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_9_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_9_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_9_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_9_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_9_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_9_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_9_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_9_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType10" select="relative_10_relativeType"/>
                    <xsl:if test="$relativeType10 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType10"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_10_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_10_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_10_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_10_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_10_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_10_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_10_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_10_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType11" select="relative_11_relativeType"/>
                    <xsl:if test="$relativeType11 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType11"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_11_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_11_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_11_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_11_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_11_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_11_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_11_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_11_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="relativeType12" select="relative_12_relativeType"/>
                    <xsl:if test="$relativeType12 != ''">
                        <xsl:call-template name="relativesViewInfo">
                            <xsl:with-param name="dictionary" select="$familyRelations"/>
                            <xsl:with-param name="title" select="'Информация о ребенке'"/>
                            <xsl:with-param name="relativeType"><xsl:value-of  select="$relativeType12"/></xsl:with-param>
                            <xsl:with-param name="relativeSurname"><xsl:value-of select="relative_12_surname"/></xsl:with-param>
                            <xsl:with-param name="relativeName"><xsl:value-of select="relative_12_name"/></xsl:with-param>
                            <xsl:with-param name="relativePatrName"><xsl:value-of select="relative_12_patrName"/></xsl:with-param>
                            <xsl:with-param name="relativeBirthday"><xsl:value-of select="dh:formatXsdDateToString(relative_12_birthday)"/></xsl:with-param>
                            <xsl:with-param name="relativeDependent"><xsl:value-of select="relative_12_dependent"/></xsl:with-param>
                            <xsl:with-param name="relativeCredit"><xsl:value-of select="relative_12_credit"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployee"><xsl:value-of select="relative_12_employee"/></xsl:with-param>
                            <xsl:with-param name="relativeEmployeePlace"><xsl:value-of select="relative_12_employeePlace"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

            </div>
        </xsl:if>

        <!--Прописка-->
        <xsl:if test="stepFilled-INITIAL4='true'">
            <xsl:call-template name="printTitle">
                <xsl:with-param name="value">Прописка</xsl:with-param>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="addressInfo">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Адрес регистрации</b></xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="registrationType1"><xsl:value-of select="registrationType1"/></xsl:variable>
                <xsl:if test="$registrationType1 != ''">
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value">
                            <span class="blockTitle">
                                <xsl:choose>
                                    <xsl:when test="$registrationType1 = 'FIXED'">
                                        <xsl:value-of select="'Иформация о постоянной регистрации'"/>
                                    </xsl:when>
                                    <xsl:when test="$registrationType1 = 'TEMPORARY'">
                                        <xsl:value-of select="'Иформация о временной регистрации'"/>
                                    </xsl:when>
                                </xsl:choose>
                            </span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="addressViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect1"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district1"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city1"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality1"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect1"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street1"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house1"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building1"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction1"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat1"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum1"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index1"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="registrationType2"><xsl:value-of select="registrationType2"/></xsl:variable>
                <xsl:if test="$registrationType2 != ''">
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value">
                            <span class="blockTitle">
                                <xsl:choose>
                                    <xsl:when test="$registrationType2 = 'FIXED'">
                                        <xsl:value-of select="'Иформация о постоянной регистрации'"/>
                                    </xsl:when>
                                    <xsl:when test="$registrationType2 = 'TEMPORARY'">
                                        <xsl:value-of select="'Иформация о временной регистрации'"/>
                                    </xsl:when>
                                </xsl:choose>
                            </span>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="addressViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect2"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district2"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city2"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality2"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect2"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street2"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house2"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building2"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction2"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat2"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum2"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index2"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Адрес фактического проживания</b></xsl:with-param>
                </xsl:call-template>
                <xsl:variable name="registrationType3"><xsl:value-of select="registrationType3"/></xsl:variable>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Адрес проживания</xsl:with-param>
                    <xsl:with-param name="value">
                        <span><b>
                            <xsl:choose>
                                <xsl:when test="$registrationType3 = 'FACT'">
                                    <xsl:value-of select="'Другой'"/>
                                </xsl:when>
                                <xsl:when test="$registrationType3 = 'FIXED'">
                                    <xsl:value-of select="'Совпадает с постоянной регистрацией'"/>
                                </xsl:when>
                                <xsl:when test="$registrationType3 = 'TEMPORARY'">
                                    <xsl:value-of select="'Совпадает с временной регистрацией'"/>
                                </xsl:when>
                            </xsl:choose>
                        </b></span>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="$registrationType3 = 'FACT'">
                    <xsl:call-template name="addressViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="regionSelectFieldValue"><xsl:value-of select="regionSelect3"/></xsl:with-param>
                        <xsl:with-param name="districtTypeSelectValue"><xsl:value-of select="districtTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="districtFieldValue"><xsl:value-of select="district3"/></xsl:with-param>
                        <xsl:with-param name="cityTypeSelectValue"><xsl:value-of select="cityTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="cityFieldValue"><xsl:value-of select="city3"/></xsl:with-param>
                        <xsl:with-param name="localityTypeSelectValue"><xsl:value-of select="localityTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="localityFieldValue"><xsl:value-of select="locality3"/></xsl:with-param>
                        <xsl:with-param name="streetTypeSelectValue"><xsl:value-of select="streetTypeSelect3"/></xsl:with-param>
                        <xsl:with-param name="streetFieldValue"><xsl:value-of select="street3"/></xsl:with-param>
                        <xsl:with-param name="houseFieldValue"><xsl:value-of select="house3"/></xsl:with-param>
                        <xsl:with-param name="buildingFieldValue"><xsl:value-of select="building3"/></xsl:with-param>
                        <xsl:with-param name="constructionFieldValue"><xsl:value-of select="construction3"/></xsl:with-param>
                        <xsl:with-param name="flatFieldValue"><xsl:value-of select="flat3"/></xsl:with-param>
                        <xsl:with-param name="flatWithNumFieldValue"><xsl:value-of select="flatWithNum3"/></xsl:with-param>
                        <xsl:with-param name="indexFieldValue"><xsl:value-of select="index3"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="residenceRight"><xsl:value-of  select="residenceRightSelect"/></xsl:variable>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Право проживания:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="$claimDictionaries/config/list-residence-right/residence-right[code = $residenceRight]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Срок проживания в населенном пункте на момент заполнения анкеты:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="residenceDuration"/>&nbsp; полных лет</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Срок проживания по фактическому адресу:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="factAddressDuration"/>&nbsp; полных лет</b>
                    </xsl:with-param>
                </xsl:call-template>

            </div>
        </xsl:if>

        <!--Работа и доход-->
        <xsl:if test="stepFilled-INITIAL5='true'">
            <xsl:call-template name="printTitle">
                <xsl:with-param name="value">Работа и доход</xsl:with-param>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="workInfo">
                <xsl:variable name="contractType"><xsl:value-of  select="contractType"/></xsl:variable>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Тип занятости:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="$claimDictionaries/config/list-work-on-contract/work-on-contract[code=$contractType]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="privatePracticeRequired"><xsl:value-of  select="$claimDictionaries/config/list-work-on-contract/work-on-contract[code=$contractType]/privatePractice"/></xsl:variable>
                <xsl:variable name="pensioner"><xsl:value-of  select="$claimDictionaries/config/list-work-on-contract/work-on-contract[code=$contractType]/pensioner"/></xsl:variable>
                <xsl:choose>
                    <xsl:when test="$privatePracticeRequired = 'true'">
                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Частная практика:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="privatePractice"/></b>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="$pensioner != 'true' and $privatePracticeRequired != 'true'">

                        <xsl:call-template name="printTitle">
                            <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Текущее место работы</b></xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="incorporationType"><xsl:value-of  select="incorporationType"/></xsl:variable>
                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Организационно – правовая форма:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-form-of-incorporation/form-of-incorporation[code=$incorporationType]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Полное наименование компании /организации:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="companyFullName"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:if test="isSberEmployee ='true'">
                            <xsl:variable name="department"><xsl:value-of  select="department"/></xsl:variable>
                            <xsl:call-template name="paramsCheck">
                                <xsl:with-param name="title">Подразделение:</xsl:with-param>
                                <xsl:with-param name="value">
                                    <b><xsl:value-of  select="$departments/entity-list/entity[@key=$department]/field[@name='name']/text()"/></b>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="paramsCheck">
                                <xsl:with-param name="title">Полное наименование подразделения:</xsl:with-param>
                                <xsl:with-param name="value">
                                    <b><xsl:value-of  select="departmentFullName"/></b>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:variable name="companyActivityScope"><xsl:value-of  select="companyActivityScope"/></xsl:variable>
                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Сфера деятельности компании:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-types-of-company/types-of-company[code=$companyActivityScope]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="orgActivityDescRequired"><xsl:value-of  select="$claimDictionaries/config/list-types-of-company/types-of-company[code=$companyActivityScope]/orgActivityDescRequired"/></xsl:variable>
                        <xsl:if test="$orgActivityDescRequired = 'true'">
                            <xsl:call-template name="paramsCheck">
                                <xsl:with-param name="title">Комментарий:</xsl:with-param>
                                <xsl:with-param name="value">
                                    <b><xsl:value-of  select="employeePosition"/></b>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">ИНН организации работодателя:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="companyInn"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Занимаемая должность:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="employeePosition"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="employeePositionType"><xsl:value-of  select="employeePositionType"/></xsl:variable>
                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Категория занимаемой должности:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-category-of-position/category-of-position[code=$employeePositionType]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="seniority"><xsl:value-of  select="seniority"/></xsl:variable>
                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Как долго вы работаете в компании:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-experience-on-current-job/experience-on-current-job[code=$seniority]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>

                        <xsl:variable name="numberOfEmployees"><xsl:value-of  select="numberOfEmployees"/></xsl:variable>
                        <xsl:call-template name="paramsCheck">
                            <xsl:with-param name="title">Количество сотрудников в компании:</xsl:with-param>
                            <xsl:with-param name="value">
                                <b><xsl:value-of  select="$claimDictionaries/config/list-number-of-employees/number-of-employees[code=$numberOfEmployees]/name"/></b>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>

                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Ежемесячный доход и расход</b></xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Среднемесячный основной доход:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="basicIncome"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Дополнительный доход:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="additionalIncome"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Среднемесячный доход семьи:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="monthlyIncome"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Среднемесячный расход:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of  select="monthlyExpense"/>&nbsp;руб.</b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="$pensioner != 'true' and $privatePracticeRequired != 'true'">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Количество рабочих мест за последние 3 года:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of  select="workPlacesCount"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </div>
        </xsl:if>

        <!--Собственность-->
        <xsl:if test="stepFilled-INITIAL6='true'">
            <xsl:variable name="realtyType1" select="realty_1_realtyType"/>
            <xsl:variable name="transportType1" select="transport_1_transportType"/>
            <xsl:variable name="debtType1" select="debt_1_debtType"/>
            
            <xsl:if test="$realtyType1 != '' or $transportType1 != '' or $debtType1 != ''">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">Собственность</xsl:with-param>
                </xsl:call-template>
            </xsl:if>
            <div class="clear"></div>

            <div id="propertyAndDebtsInfo">
                <xsl:if test="$realtyType1 != ''">
                    <xsl:call-template name="printTitle">
                        <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Недвижимость</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="realtyViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType1"/></xsl:with-param>
                        <xsl:with-param name="address"><xsl:value-of select="realty_1_address"/></xsl:with-param>
                        <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_1_yearOfPurchase"/></xsl:with-param>
                        <xsl:with-param name="square"><xsl:value-of select="realty_1_square"/></xsl:with-param>
                        <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_1_typeOfSquareUnit"/></xsl:with-param>
                        <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_1_approxMarketValue"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="realtyType2" select="realty_2_realtyType"/>
                    <xsl:if test="$realtyType2 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType2"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_2_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_2_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_2_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_2_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_2_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType3" select="realty_3_realtyType"/>
                    <xsl:if test="$realtyType3 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType3"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_3_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_3_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_3_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_3_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_3_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType4" select="realty_4_realtyType"/>
                    <xsl:if test="$realtyType4 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType4"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_4_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_4_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_4_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_4_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_4_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType5" select="realty_5_realtyType"/>
                    <xsl:if test="$realtyType5 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType5"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_5_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_5_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_5_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_5_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_5_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType6" select="realty_6_realtyType"/>
                    <xsl:if test="$realtyType6 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType6"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_6_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_6_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_6_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_6_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_6_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="realtyType7" select="realty_7_realtyType"/>
                    <xsl:if test="$realtyType7 != ''">
                        <xsl:call-template name="realtyViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="realtyType"><xsl:value-of  select="$realtyType7"/></xsl:with-param>
                            <xsl:with-param name="address"><xsl:value-of select="realty_7_address"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="realty_7_yearOfPurchase"/></xsl:with-param>
                            <xsl:with-param name="square"><xsl:value-of select="realty_7_square"/></xsl:with-param>
                            <xsl:with-param name="typeOfSquareUnit"><xsl:value-of select="realty_7_typeOfSquareUnit"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="realty_7_approxMarketValue"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>

                <xsl:if test="$transportType1 != ''">
                    <xsl:call-template name="printTitle">
                        <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Транспортное средство</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="transportViewInfo">
                        <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                        <xsl:with-param name="transportType"><xsl:value-of  select="$transportType1"/></xsl:with-param>
                        <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_1_registrationNumber"/></xsl:with-param>
                        <xsl:with-param name="brand"><xsl:value-of select="transport_1_brand"/></xsl:with-param>
                        <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_1_approxMarketValue"/></xsl:with-param>
                        <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_1_ageOfTransport"/></xsl:with-param>
                        <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_1_yearOfPurchase"/></xsl:with-param>
                    </xsl:call-template>

                    <xsl:variable name="transportType2" select="transport_2_transportType"/>
                    <xsl:if test="$transportType2 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType2"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_2_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_2_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_2_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_2_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_2_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType3" select="transport_3_transportType"/>
                    <xsl:if test="$transportType3 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType3"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_3_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_3_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_3_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_3_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_3_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType4" select="transport_4_transportType"/>
                    <xsl:if test="$transportType4 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType4"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_4_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_4_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_4_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_4_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_4_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType5" select="transport_5_transportType"/>
                    <xsl:if test="$transportType5 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType5"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_5_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_5_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_5_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_5_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_5_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType6" select="transport_6_transportType"/>
                    <xsl:if test="$transportType6 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType6"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_6_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_6_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_6_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_6_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_6_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:variable name="transportType7" select="transport_7_transportType"/>
                    <xsl:if test="$transportType7 != ''">
                        <xsl:call-template name="transportViewInfo">
                            <xsl:with-param name="dictionaries" select="$claimDictionaries"/>
                            <xsl:with-param name="transportType"><xsl:value-of  select="$transportType7"/></xsl:with-param>
                            <xsl:with-param name="registrationNumber"><xsl:value-of select="transport_7_registrationNumber"/></xsl:with-param>
                            <xsl:with-param name="brand"><xsl:value-of select="transport_7_brand"/></xsl:with-param>
                            <xsl:with-param name="approxMarketValue"><xsl:value-of select="transport_7_approxMarketValue"/></xsl:with-param>
                            <xsl:with-param name="ageOfTransport"><xsl:value-of select="transport_7_ageOfTransport"/></xsl:with-param>
                            <xsl:with-param name="yearOfPurchase"><xsl:value-of select="transport_7_yearOfPurchase"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:if>
            </div>
        </xsl:if>

        <!--Дополнительная информация-->
        <xsl:if test="stepFilled-INITIAL7='true'">
            <xsl:call-template name="printTitle">
                <xsl:with-param name="value">Доп. информация</xsl:with-param>
            </xsl:call-template>
            <div class="clear"></div>

            <div id="additionalInfo">

                <xsl:variable name="creditMethodObtaining"><xsl:value-of select="creditMethodObtaining"/></xsl:variable>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Как вы хотите получить кредит:</xsl:with-param>
                    <xsl:with-param name="value">
                        <b><xsl:value-of select="$claimDictionaries/config/list-loan-issue-method/creditMethodObtaining[code = $creditMethodObtaining]/name"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="newProductForLoan">
                    <xsl:value-of select="$claimDictionaries/config/list-loan-issue-method/creditMethodObtaining[code = $creditMethodObtaining]/newProductForLoan"/>
                </xsl:variable>
                <xsl:if test="$newProductForLoan != 'true'">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Карта\счет для зачисления кредита:</xsl:with-param>
                        <xsl:with-param name="value">
                            <xsl:choose>
                                <xsl:when test="receivingResource != ''">
                                    <b><xsl:value-of select="receivingResource"/></b>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:if test="accountNumber != ''">
                                        <b>Счет №<xsl:value-of select="accountNumber"/></b>
                                    </xsl:if>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title"  select="'Подразделение, в котором хотите получить кредит:'"/>
                    <xsl:with-param name="value">
                        <b><xsl:value-of select="receivingDepartmentName"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="cardBlankType_1" select="cardBlank_1_type"/>
                <xsl:variable name="cardBlankType_2" select="cardBlank_2_type"/>
                <xsl:variable name="cardBlankType_3" select="cardBlank_3_type"/>
                <xsl:variable name="accountBlankType_1" select="accountBlank_1_type"/>
                <xsl:variable name="accountBlankType_2" select="accountBlank_2_type"/>
                <xsl:variable name="accountBlankType_3" select="accountBlank_3_type"/>

                <xsl:if test="$accountBlankType_1 != '' or $accountBlankType_2 != '' or $accountBlankType_3 != ''
                              or $cardBlankType_1 != '' or $cardBlankType_2 != '' or $cardBlankType_3 != ''">
                    <xsl:call-template name="printTitle">
                        <xsl:with-param name="value">&nbsp;<b class="rowTitle18 size20">Зарплатная/пенсионная карта или счет в Сбербанке</b></xsl:with-param>
                    </xsl:call-template>

                    <xsl:if test="$cardBlankType_1 != ''">
                        <xsl:call-template name="additionalCardViewInfo">
                            <xsl:with-param name="cardBlankType"><xsl:value-of select="$cardBlankType_1"/></xsl:with-param>
                            <xsl:with-param name="cardBlankResource"><xsl:value-of select="cardBlank_1_resource"/></xsl:with-param>
                            <xsl:with-param name="cardBlankCustomNumber"><xsl:value-of select="cardBlank_1_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$cardBlankType_2 != ''">
                        <xsl:call-template name="additionalCardViewInfo">
                            <xsl:with-param name="cardBlankType"><xsl:value-of select="$cardBlankType_2"/></xsl:with-param>
                            <xsl:with-param name="cardBlankResource"><xsl:value-of select="cardBlank_2_resource"/></xsl:with-param>
                            <xsl:with-param name="cardBlankCustomNumber"><xsl:value-of select="cardBlank_2_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$cardBlankType_3 != ''">
                        <xsl:call-template name="additionalCardViewInfo">
                            <xsl:with-param name="cardBlankType"><xsl:value-of select="$cardBlankType_3"/></xsl:with-param>
                            <xsl:with-param name="cardBlankResource"><xsl:value-of select="cardBlank_3_resource"/></xsl:with-param>
                            <xsl:with-param name="cardBlankCustomNumber"><xsl:value-of select="cardBlank_3_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="$accountBlankType_1 != ''">
                        <xsl:call-template name="additionalAccountViewInfo">
                            <xsl:with-param name="accountBlankType"><xsl:value-of select="$accountBlankType_1"/></xsl:with-param>
                            <xsl:with-param name="accountBlankResource"><xsl:value-of select="accountBlank_1_resource"/></xsl:with-param>
                            <xsl:with-param name="accountBlankCustomNumber"><xsl:value-of select="accountBlank_1_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$accountBlankType_2 != ''">
                        <xsl:call-template name="additionalAccountViewInfo">
                            <xsl:with-param name="accountBlankType"><xsl:value-of select="$accountBlankType_2"/></xsl:with-param>
                            <xsl:with-param name="accountBlankResource"><xsl:value-of select="accountBlank_2_resource"/></xsl:with-param>
                            <xsl:with-param name="accountBlankCustomNumber"><xsl:value-of select="accountBlank_2_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$accountBlankType_3 != ''">
                        <xsl:call-template name="additionalAccountViewInfo">
                            <xsl:with-param name="accountBlankType"><xsl:value-of select="$accountBlankType_3"/></xsl:with-param>
                            <xsl:with-param name="accountBlankResource"><xsl:value-of select="accountBlank_3_resource"/></xsl:with-param>
                            <xsl:with-param name="accountBlankCustomNumber"><xsl:value-of select="accountBlank_3_customNumber"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>

                </xsl:if>

                <div class="darkGrayUnderline"></div>

                <xsl:variable name="stockholder"><xsl:value-of select="stockholder"/></xsl:variable>

                <xsl:if test="$stockholder = 'true'">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Количество обыкновенных акций Сбербанка России</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of select="ordinaryStockCount"/></b>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Количество привилигированных акций Сбербанка России</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of select="preferredStockCount"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="subjectCreditHistoryCode != ''">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Ваш код субъекта кредитной истории:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of select="subjectCreditHistoryCode"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:variable name="agreeRequestPFP"><xsl:value-of select="agreeRequestPFP"/></xsl:variable>
                <xsl:if test="$agreeRequestPFP ='true'">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">Ваш СНИЛС:</xsl:with-param>
                        <xsl:with-param name="value">
                            <b><xsl:value-of select="snils"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

            </div>
        </xsl:if>

    </xsl:template>

    <!--Шаблон отображения дополнительных телефонов заявителя. Для страницы подтверждения-->
    <xsl:template name="additionalPhoneViewInfo">
        <xsl:param name="phoneBlankType"/>
        <xsl:param name="phoneBlankCountry"/>
        <xsl:param name="phoneBlankTelecom"/>
        <xsl:param name="phoneBlankNumber"/>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <span class="blockTitle">Дополнительный телефон</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип телефона:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:choose>
                    <xsl:when test="$phoneBlankType = 'MOBILE'">Мобильный</xsl:when>
                    <xsl:when test="$phoneBlankType = 'RESIDENCE'">Домашний по адресу проживания</xsl:when>
                    <xsl:when test="$phoneBlankType = 'REGISTRATION'">Домашний по адресу регистрации</xsl:when>
                    <xsl:when test="$phoneBlankType = 'WORK'">Рабочий</xsl:when>
                </xsl:choose></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Номер телефона:</xsl:with-param>
            <xsl:with-param name="value">
                <span class="floatLeft"><b>+<xsl:value-of  select="$phoneBlankCountry"/>&nbsp;<xsl:value-of  select="$phoneBlankTelecom"/>&nbsp;<xsl:value-of  select="$phoneBlankNumber"/></b></span>
                <xsl:call-template name="popUpMessage">
                    <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                    <xsl:with-param name="app" select="$app"/>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="printTitle">
          <xsl:param name="value"/>
          <div class="checkSize title"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printTitleAdditional">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printText">
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="printTextForDispatched">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="paramsCheck">
          <xsl:param name="title"/>
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
      </xsl:template>    
    
    <!--Шаблон отображения данных о прописке и адресе. Для страницы подтверждения-->
    <xsl:template name="addressViewInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="regionSelectFieldValue"/>
        <xsl:param name="districtTypeSelectValue"/>
        <xsl:param name="districtFieldValue"/>
        <xsl:param name="cityTypeSelectValue"/>
        <xsl:param name="cityFieldValue"/>
        <xsl:param name="localityTypeSelectValue"/>
        <xsl:param name="localityFieldValue"/>
        <xsl:param name="streetTypeSelectValue"/>
        <xsl:param name="streetFieldValue"/>
        <xsl:param name="houseFieldValue"/>
        <xsl:param name="buildingFieldValue"/>
        <xsl:param name="constructionFieldValue"/>
        <xsl:param name="flatFieldValue"/>
        <xsl:param name="flatWithNumFieldValue"/>
        <xsl:param name="indexFieldValue"/>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Регион:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionaries/config/list-regions/regions[code=$regionSelectFieldValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип Района/Округа:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-regions/types-of-regions[code=$districtTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Район/Округ:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$districtFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип города:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-cities/types-of-cities[code=$cityTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Город:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$cityFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип населенного пункта:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-localities/types-of-localities[code=$localityTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Населенный пункт:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$localityFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип улицы:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionaries/config/list-types-of-streets/types-of-streets[code=$streetTypeSelectValue]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Улица:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:if test="$streetFieldValue != ''">
                    <span class="floatLeft"><b><xsl:value-of  select="$streetFieldValue"/></b></span>
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Дом:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:if test="$houseFieldValue != ''">
                    <span class="floatLeft"><b><xsl:value-of  select="$houseFieldValue"/></b></span>
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Корпус:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$buildingFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Строение:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$constructionFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Квартира:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:if test="$flatFieldValue != ''">
                    <span class="floatLeft"><b><xsl:value-of  select="$flatFieldValue"/></b></span>
                    <xsl:call-template name="popUpMessage">
                        <xsl:with-param name="text">В целях безопасности Ваши личные данные маскированы</xsl:with-param>
                        <xsl:with-param name="app" select="$app"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Индекс:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$indexFieldValue"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>         

    <!--Шаблон отображения данных о родственниках/детях. Для страницы подтверждения-->
    <xsl:template name="relativesViewInfo">
        <xsl:param name="dictionary"/>
        <xsl:param name="title"/>

        <xsl:param name="relativeType"/>
        <xsl:param name="relativeSurname"/>
        <xsl:param name="relativeName"/>
        <xsl:param name="relativePatrName"/>
        <xsl:param name="relativeBirthday"/>
        <xsl:param name="relativeDependent"/>
        <xsl:param name="relativeCredit"/>
        <xsl:param name="relativeEmployee"/>
        <xsl:param name="relativeEmployeePlace"/>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <span class="blockTitle">
                    <xsl:value-of select="$title"/>
                </span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Степень родства:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionary/family-relation[code=$relativeType]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Фамилия:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$relativeSurname"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Имя:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$relativeName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Отчество:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$relativePatrName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Дата рождения:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$relativeBirthday"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Находится на иждивении?:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:choose>
                    <xsl:when test="$relativeDependent = '1'">Да</xsl:when>
                    <xsl:when test="$relativeDependent = '2'">Нет</xsl:when>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Есть кредиты в Сбербанке?:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:choose>
                    <xsl:when test="$relativeCredit = '1'">Да</xsl:when>
                    <xsl:when test="$relativeCredit = '2'">Нет</xsl:when>
                    <xsl:when test="$relativeCredit = '3'">Не знаю</xsl:when>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <div class="textTick">
                    <xsl:choose>
                        <xsl:when test="$relativeEmployee ='true'">
                            <input class="float" type="checkbox" checked="checked" disabled="disabled"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input class="float" type="checkbox" disabled="disabled"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    <label class="float">&nbsp;Является сотрудником Сбербанка</label>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$relativeEmployee ='true'">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Место работы:</xsl:with-param>
                <xsl:with-param name="value">
                    <b><xsl:value-of  select="$relativeEmployeePlace"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>      

    <!--Шаблон отображения данных о недвижимости. Для страницы подтверждения-->
    <xsl:template name="realtyViewInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="realtyType"/>
        <xsl:param name="address"/>
        <xsl:param name="yearOfPurchase"/>
        <xsl:param name="square"/>
        <xsl:param name="typeOfSquareUnit"/>
        <xsl:param name="approxMarketValue"/>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <span class="blockTitle">Недвижимость</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Вид недвижимости:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionaries/config/list-type-of-realty/type-of-realty[code=$realtyType]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Адрес:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$address"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Год приобретения:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$yearOfPurchase"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Площадь:</xsl:with-param>
            <xsl:with-param name="value">
                <b>
                    <xsl:value-of  select="$square"/>&nbsp;<xsl:value-of select="$dictionaries/config/square-units/square-unit[code=$typeOfSquareUnit]/name"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Примерная рыночная стоимость:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$approxMarketValue"/>&nbsp;руб.</b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>  

    <!--Шаблон отображения данных о транспортном средстве. Для страницы подтверждения-->
    <xsl:template name="transportViewInfo">
        <xsl:param name="dictionaries"/>
        <xsl:param name="transportType"/>
        <xsl:param name="registrationNumber"/>
        <xsl:param name="brand"/>
        <xsl:param name="approxMarketValue"/>
        <xsl:param name="ageOfTransport"/>
        <xsl:param name="yearOfPurchase"/>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <span class="blockTitle">Транспортное средство (ТС)</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип транспортного средства:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of select="$dictionaries/config/list-type-of-vehicle/type-of-vehicle[code=$transportType]/name"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Регистрационный номер:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$registrationNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Марка:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$brand"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Примерная рыночная стоимость:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$approxMarketValue"/>&nbsp;руб.</b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Возраст ТС:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$ageOfTransport"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Год покупки:</xsl:with-param>
            <xsl:with-param name="value">
                <b><xsl:value-of  select="$yearOfPurchase"/></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>         

    <!--Шаблон отображения данных о зарплатных/пенсионных карт. Для страницы подтверждения-->
    <xsl:template name="additionalCardViewInfo">
        <xsl:param name="cardBlankType"/>
        <xsl:param name="cardBlankResource"/>
        <xsl:param name="cardBlankCustomNumber"/>

        <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <span class="blockTitle">Зарплатная/пенсионная карта</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:if test="$cardBlankType = 'SALARY'"><b>Зарплатная</b></xsl:if>
                <xsl:if test="$cardBlankType = 'PENSION'"><b>Пенсионная</b></xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Карта:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:choose>
                    <xsl:when test="$cardBlankResource != ''">
                        <b><xsl:value-of select="$cardBlankResource"/></b>
                    </xsl:when>
                    <xsl:otherwise>
                        <b>Карта №<xsl:value-of select="$cardBlankCustomNumber"/></b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>      

    <!--Шаблон отображения данных о зарплатных/пенсионных вкладах. Для страницы подтверждения-->
    <xsl:template name="additionalAccountViewInfo">
        <xsl:param name="accountBlankType"/>
        <xsl:param name="accountBlankResource"/>
        <xsl:param name="accountBlankCustomNumber"/>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">&nbsp;</xsl:with-param>
            <xsl:with-param name="value">
                <span class="blockTitle">Зарплатный/пенсионный счёт</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Тип:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:if test="$accountBlankType = 'SALARY'"><b>Зарплатный</b></xsl:if>
                <xsl:if test="$accountBlankType = 'PENSION'"><b>Пенсионный</b></xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Счёт:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:choose>
                    <xsl:when test="$accountBlankResource != ''">
                        <b><xsl:value-of select="$accountBlankResource"/></b>
                    </xsl:when>
                    <xsl:otherwise>
                        <b>Счет №<xsl:value-of select="$accountBlankCustomNumber"/></b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="popUpMessage">
        <xsl:param name="text"/>
        <xsl:param name="app"/>

        <xsl:if test="$app != 'PhizIA'">
            <div class="simpleHintBlock float">
                <a class="imgHintBlock imageWidthText simpleHintLabel" onclick="return false;"></a>
                <div class="clear"></div>
                <div class="layerFon simpleHint">
                    <div class="floatMessageHeader"></div>
                    <div class="layerFonBlock"><xsl:value-of select="$text"/></div>
                </div>
            </div>
        </xsl:if>
    </xsl:template>
    
</xsl:stylesheet>