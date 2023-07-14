<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/synchronize/deposit_settings" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showCheckbox" value="${form.replication}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="dictionariesEdit"/>
        <tiles:put name="submenu" type="string" value="DepositSettings"/>
        <tiles:put name="pageName" type="string" value="Настройка вкладов для загрузки"/>
        <tiles:put name="pageDescription" value="Используйте данную форму для настройки загрузки вкладов."/>
        <tiles:put name="formAlign"  value="center"/>
        <tiles:put name="data" type="string">

            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.depositproduct.uploading.mode"/>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="fieldType" value="radio"/>
                    <tiles:put name="selectItems" value="ALL@Все виды|OPTIONALLY@Укажите номера видов вкладов для загрузки:"/>
                    <tiles:put name="onclick" value="switchProductNumbersTable();"/>
                </tiles:insert>
            </table>

            <%--Следующие строки скрываем если выбрано "ALL"--%>

            <c:choose>
                <c:when test="${showCheckbox == 'true'}">
                    <div class="propertySplitter">
                        <input type="checkbox" id="replicationSelect" value="com.rssl.iccs.depositproduct.kinds.allowed.uploading" name="selectedProperties" style="display: none;"/>
                        <label for="replicationSelect" id="replicationSelectLabel" style="display: none;"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
                </c:when>
                <c:otherwise>
                    <div id="titleRow">
                        <p><b>Загружать следующие номера вкладов:</b></p>
                    </div>
                    <div id="buttonsRow" align="right">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.add"/>
                            <tiles:put name="commandHelpKey" value="button.add"/>
                            <tiles:put name="bundle" value="dictionariesBundle"/>
                            <tiles:put name="onclick">addNumber();</tiles:put>
                            <tiles:put name="viewType" value="buttonGrayNew"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove"/>
                            <tiles:put name="bundle" value="dictionariesBundle"/>
                            <tiles:put name="onclick">removeNumbers();</tiles:put>
                            <tiles:put name="viewType" value="buttonGrayNew"/>
                        </tiles:insert>
                    </div>
                    <div class="clear">&nbsp;</div>
                </c:otherwise>
            </c:choose>
            <div id="numbersRow">
                <div id="cardSynchronizeSettingsDiv" style="display: none;">
                    Не заданы данные для загрузки
                </div>
                <table id="numbersTable" class="standartTable" cellspacing="0" cellpadding="0" style="display: none;">
                    <tbody>
                        <tr>
                            <c:if test="${showCheckbox != 'true'}">
                                <th>
                                    <input type="checkbox" id="commonCheckbox" checked="false" onclick="checkAll();"/>
                                </th>
                            </c:if>
                            <th>Код вида вклада</th>
                            <th>Коды подвидов вклада</th>
                        </tr>
                    </tbody>
                </table>
            </div>
            <c:if test="${showCheckbox == 'true'}"></div></c:if>

        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false"
                          operation="EditDepositSynchronizeSettingsOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="dictionariesBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        var wrongNumberMessage = "Указанный Вами код соответствует карточному виду вклада. Пожалуйста, введите другой код."
        doOnLoad(function() {
            init();
            switchProductNumbersTable();
        });

        function init()
        {
            loadNumbers();
            drawTableOnLoad();
            initData();
        }

        function switchProductNumbersTable()
        {
            var radioValue = getRadioValue(document.getElementsByName('field(com.rssl.iccs.depositproduct.uploading.mode)'));
            var hide = (radioValue == 'ALL');
            <c:if test="${showCheckbox != 'true'}">
                ensureElement('titleRow').style.display = hide ? "none" : "";
                ensureElement('buttonsRow').style.display = hide ? "none" : "";
            </c:if>
            ensureElement('numbersRow').style.display = hide ? "none" : "";
            ensureElement('replicationSelect').style.display = hide ? "none" : "";
            ensureElement('replicationSelectLabel').style.display = hide ? "none" : "";
        }

        //Вид с подвидами
        function number(kind, subkinds)
        {
            this.kind = kind;
            this.subkinds = subkinds;
        }

        var numbers = new Array(); //массив всех видов с подвидами
        var editingNumber = null; //редактируемый вид с подвидами
        //Первоначальная загрузка номеров видов и подвидов с формы в js-массив numbers. Вызывается по onLoad.
        function loadNumbers()
        {
            numbers = new Array();
            <c:forEach var="entry" items="${form.numbers}">
                var subkinds = '';
                var isFirst = true;
                <c:forEach var="subkind" items="${entry.value}">
                    if(!isFirst)
                        subkinds += ',';
                    subkinds += '${subkind}';
                    isFirst = false;
                </c:forEach>
                numbers.push(new number(${entry.key}, subkinds));
            </c:forEach>
        }

        //Заполняет всю таблицу из массива.
        function drawTableOnLoad()
        {
            for(var i = 0; i < numbers.length; ++i)
                drawRow(numbers[i].kind, numbers[i].subkinds);
            if (numbers.length == 0)
                setTableOrDivStyle("none", "block");
            else
                setTableOrDivStyle("block", "none");
            <c:if test="${showCheckbox != 'true'}">
                ensureElement("commonCheckbox").checked = false;
            </c:if>
        }

        //Добавляет строку в таблицу
        function drawRow(kind, subkinds)
        {
            var table = document.getElementById("numbersTable");
            var tbody = table.getElementsByTagName('tbody')[0];

            var row = document.createElement("tr");
            tbody.appendChild(row);
            row.id = "number"+kind;

            <c:if test="${showCheckbox != 'true'}">
                //1. checkbox (не отображаем на форме репликации)
                var td = document.createElement("td");
                row.appendChild(td);
                td.innerHTML = "<input name='chkName' type='checkbox' id='chkNumber" + kind + "'/>";
            </c:if>

            //2. вид
            td = document.createElement("td");
            td.className = "kind";
            td.align = "center";

            <c:choose>
                <c:when test="${showCheckbox != 'true'}">
                    var a = document.createElement("a");
                    a.innerHTML = kind;
                    a.href = "#";
                    a.onclick = function(){
                        for(var i = 0; i < numbers.length; ++i)
                            if(numbers[i].kind == kind)
                            {
                                editingNumber = numbers[i];
                                break;
                            }
                        openEditNumberWindow(true);
                    };
                    td.appendChild(a);
                </c:when>
                <c:otherwise>
                    td.innerHTML = kind;
                </c:otherwise>
            </c:choose>

            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "kinds[]";
            input.value = kind;
            td.appendChild(input);

            row.appendChild(td);

            //3. подвиды
            td = document.createElement("td");
            td.className = "subkinds";
            td.align = "center";
            td.innerHTML = (subkinds == '')?'&nbsp;':subkinds;

            input = document.createElement("input");
            input.type = "hidden";
            input.name = "subkinds[]";
            input.value = subkinds;
            td.appendChild(input);

            row.appendChild(td);

            <c:if test="${showCheckbox != 'true'}">
                ensureElement("commonCheckbox").checked = false;
            </c:if>
        }

        //Обновляет строку в таблице
        function redrawRow(kind, subkinds)
        {
            var tdSubkinds = findChildByClassName(document.getElementById("number"+kind), "subkinds");
            tdSubkinds.innerHTML = (subkinds == '')?'&nbsp;':subkinds;

            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "subkinds[]";
            input.value = subkinds;
            tdSubkinds.appendChild(input);
        }

        //update: true - редактирование, false - добавление
        function openEditNumberWindow(update)
        {
            var url = "${phiz:calculateActionURL(pageContext,'/dictionaries/synchronize/card_settings/edit.do')}" + "?update=" + update;
            var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1"+
                    ",width=1000" +
                    ",height=0" + (screen.height - 750) +
                    ",left=0" + ((screen.width) / 2 - 450) +
                    ",top=0" + 100;
            window.open(url, "EditCardSynchronizeSettings", winpar);
        }

        //Открывает окно добавления
        function addNumber()
        {
            openEditNumberWindow(false);
        }

        //Открывает окно редактирования
        function editNumber(kind){
            for(var i = 0; i < numbers.length; ++i)
                if(numbers[i].kind == kind)
                {
                    editingNumber = numbers[i];
                    break;
                }
            openEditNumberWindow(true);
        }

        //Удаляет выбранные виды из массива и из таблицы.
        function removeNumbers()
        {
            checkIfOneItem("chkName");
            if(!checkSelection("chkName", "Для удаления вида вклада выберите одну или несколько записей."))
                return;

            var tmp = numbers;
            for(var i = numbers.length - 1; i >= 0; --i)
            {
                var kind = numbers[i].kind;
                var chkbx = ensureElement("chkNumber"+kind);
                if(chkbx.checked)
                {
                    var rem = document.getElementById("number"+kind);
                    rem.parentNode.removeChild(rem);
                    tmp.splice(i, 1);
                }
            }
            numbers = tmp;
            if (numbers.length == 0)
                setTableOrDivStyle("none", "block");
            else
                setTableOrDivStyle("block", "none");
            ensureElement("commonCheckbox").checked = false;
        }

        //Все следующие функции вызываются из editCardSynchronizeSettings.jsp
        function getEditingNumber()
        {
            var tmp = editingNumber;
            editingNumber = null;
            return tmp;
        }

        /**
         * Добавляет/обновляет вид в массиве и в таблице.
         * @param editingNumber Объект с видом и подвидами
         * @param update true - редактирование, false - добавление
         * @return 0 при успешном выполнении метода
         * -1 если вид вклада является карточным (com.rssl.iccs.cards.kinds.allowed.downloading)
         * -2 при попытке добавить уже существующий вид
         */
        function addOrUpdateNumber(editingNumber, update)
        {
            var isNew = true;
            var kind = editingNumber.kind;
            var subkinds = editingNumber.subkinds;

            if(kind >= ${form.minCode} && kind <= ${form.maxCode})
                return -1;

            for(var i = 0; i < numbers.length; ++i)
                if(numbers[i].kind == kind)
                {
                    if(!update)
                        return -2;

                    numbers[i].subkinds = subkinds;
                    isNew = false;
                    break;
                }

            if(isNew)
            {
                numbers.push(new number(kind, subkinds));
                drawRow(kind, subkinds);
                setTableOrDivStyle("block", "none");
            }
            else
                redrawRow(kind, subkinds);
            return 0;
        }

        function checkAll()
        {
            var checkboxes = document.getElementsByName("chkName");
            for (var i = 0; i < checkboxes.length; i++)
                checkboxes[i].checked = ensureElement("commonCheckbox").checked;
        }

        function setTableOrDivStyle(styleTable, styleDiv)
        {
            var tableOrDiv = ensureElement("numbersTable");
            tableOrDiv.style.display = styleTable;
            tableOrDiv = ensureElement("cardSynchronizeSettingsDiv");
            tableOrDiv.style.display = styleDiv;
        }

        function getPageTitle()
        {
            return "Добавить вид (подвид) вклада для загрузки";
        }

        var superReinitFields = window.reinitField;
        this.reinitField = function(){
            var trArray = document.getElementById('numbersTable').getElementsByTagName('tr');
            for(var i = trArray.length-1; i>0; --i){
                trArray[i].parentNode.removeChild(trArray[i]);
            }
            init();
            switchProductNumbersTable();
        };



    </script>
</html:form>