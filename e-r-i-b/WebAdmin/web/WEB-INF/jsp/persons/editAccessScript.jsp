<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<script type="text/javascript">
    /**
     * Общее описание чекбоса
     * @param {HTMLInputElement} checkBox
     */
    function checkBox(checkBox)
    {
        this.getCheckBox = function()
        {
            return checkBox;
        };

        this.disableCheckBox = function()
        {
            checkBox.setAttribute('disabled', 'disabled');
        };

        this.enableCheckBox = function()
        {
            checkBox.removeAttribute('disabled');
        };

        this.checkBoxSwitchOn = function()
        {
            checkBox.checked = 'checked';
        };

        this.checkBoxSwitchOff = function()
        {
            checkBox.checked = false;
        };

        this.isCheckBoxOn = function()
        {
            return checkBox.checked;
        }
    }

    /**
     *
     * Объекты данного типа описывают чекбоксы отвечающие за включение/выключение сервисов доступных сотруднику.
     *
     * @param {HTMLInputElement} element
     * @constructor
     */
    function ServiceCheckBox(element)
    {
        checkBox.call(this, element);

        var idComponents    = element.id.split('_');
        this.id             = element.id;
        this.accessType     = idComponents[0];
        this.serviceId      = idComponents[1].replace(/(\w+)(\d+)/i, '$2');
        this.helperCategory = idComponents[2];

        element.onclick     = function()
        {
            var service     = groupServicesList.findServiceByElement(this);
            var group       = groupServicesList.findGroupByCategory(service.helperCategory);

            if (!service.isCheckBoxOn())
            {
                group.off();

                if (!group.hasSelected)
                {
                    var other = groupServicesList.findGroupExcept(group.getCheckBox());

                    for (var x=0; x<other.length; x++)
                    {
                        var groupServices = other[x].services;

                        for (var y=0; y<groupServices.length; y++)
                        {
                            groupServices[y].enableCheckBox();
                        }
                    }
                }
            }
            else
            {
                group.on();
                onServiceClick(this, service.helperCategory);

                if (group.hasSelected)
                {
                    var otherGroups = groupServicesList.findGroupExcept(group.getCheckBox());

                    for (var i=0; i<otherGroups.length; i++)
                    {
                        var services = otherGroups[i].services;

                        for (var j=0; j<services.length; j++)
                        {
                            services[j].checkBoxSwitchOff();
                            services[j].disableCheckBox();
                        }
                    }
                }
            }

            getLabel(service.getCheckBox()).innerHTML = service.isCheckBoxOn() ? 'разрешен' : 'запрещен';
        }
    }

    /**
     * Объекты данного типа описывают чекбоксы позволяющие включать/выключать группу сервисов относящихся к определенной
     * категории прав. Каждый такой объект содержит список объектов типа ServiceCheckBox, относящихся к той же категории
     * прав что и он сам.
     *
     * @param {HTMLInputElement} element
     * @constructor
     */
    function GroupServicesCheckBox(element)
    {
        checkBox.call(this, element);

        var nameComponents  = element.name.split('_');
        this.helperCategory = nameComponents[0];
        this.accessType     = nameComponents[1];

        /**
         * Все сервисы группы включены
         * @type {boolean}
         */
        this.allSelected    = true;

        /**
         * В группе есть включенные сервисы
         * @type {boolean}
         */
        this.hasSelected    = false;
        this.services       = [];

        /**
         * Функция включает/выключает чекбокс данного объекта только когда все сервисы входящие в данную группу
         * тоже включены. Данный метод вызывается в 2-ух случаях:
         *
         * 1. Когда нажимается чекбокс сервиса входящий в список сервисов данного объекта;
         * 2. Когда добавляется новый сервис в список сервисов.
         *
         * Дополнительно устанавливается признак того, что в группе есть выбранные сервисы, а, также устанавливается
         * признак того что в группе выбраны все сервисы если это действительно так.
         */
        this.on = function()
        {
            this.hasSelected = true;

            if (!this.allSelected)
            {
                for (var i=0; i<this.services.length; i++)
                {
                    if (!this.services[i].isCheckBoxOn())
                    {
                        return;
                    }
                }
            }

            this.allSelected = true;
            this.checkBoxSwitchOn();
        };

        /**
         * Данная функция вызывается также и там же где и on(). Отличие в том что признак allSelected выставляется
         * в false, а также проверяется есть ли включенные в группе сервисы и в зависимости от этого признак hasSelected
         * выставляется в true или false.
         */
        this.off = function()
        {
            this.hasSelected = false;

            for (var i=0; i<this.services.length; i++)
            {
                if (this.services[i].isCheckBoxOn())
                {
                    this.hasSelected = true;
                    break;
                }
            }

            this.allSelected = false;
            this.checkBoxSwitchOff();
        };

        /**
         * Добавляет к группе новый сервис
         * @param {ServiceCheckBox} serviceObj
         */
        this.put = function(serviceObj)
        {
            if (this.allSelected)
            {
                if (serviceObj.isCheckBoxOn())
                {
                    this.on();
                }
                else
                {
                    this.off();
                }
            }

            this.services.push(serviceObj);
        };

        element.onclick = function()
        {
            var group = groupServicesList.findGroupByElement(this);
            var other = groupServicesList.findGroupExcept(this);

            for (var i=0; i<group.services.length; i++)
            {
                if (group.isCheckBoxOn())
                {
                    group.services[i].checkBoxSwitchOn();
                }
                else
                {
                    group.services[i].checkBoxSwitchOff();
                }

                group.services[i].getCheckBox().onclick();
                group.services[i].enableCheckBox();
            }

            for (var j=0; j<other.length; j++)
            {
                var services = other[j].services;

                for (var h=0; h<services.length; h++)
                {
                    if (group.isCheckBoxOn())
                    {
                        services[h].checkBoxSwitchOff();
                        services[h].getCheckBox().onclick();
                        services[h].disableCheckBox();
                    }
                    else
                    {
                        services[h].enableCheckBox();
                    }
                }
            }
        }
    }

    /**
     * Список объектов GroupServicesCheckBox
     */
    var groupServicesList =
    {
        key : [],
        val : [],
        len : 0,

        /**
         * Добавляет в список новый объект
         * @param {GroupServicesCheckBox} element
         */
        put : function(element)
        {
            var service = new ServiceCheckBox(element);

            if (this.key.indexOf(service.helperCategory) == -1)
            {
                var groupServiceObj = new GroupServicesCheckBox( document.getElementsByName(service.helperCategory + '_' + service.accessType + '_isSelectAll')[0] );

                this.key.push( service.helperCategory );
                this.val.push( groupServiceObj );
                this.len ++;
            }

            this.val[this.len - 1].put(service);
        },

        /**
         * Возвращает объект типа ServicesCheckBox.
         *
         * @param {HTMLInputElement} element
         */
        findServiceByElement : function(element)
        {
            for (var i=0; i<this.len; i++)
            {
                var services = this.val[i].services;

                for (var j=0; j<services.length; j++)
                {
                    if (services[j].id == element.id)
                    {
                        return services[j];
                    }
                }
            }

            return null;
        },

        /**
         * @param   {String} category
         * @returns {GroupServicesCheckBox}
         */
        findGroupByCategory : function(category)
        {
            return this.val[ this.key.indexOf(category) ];
        },

        /**
         * @param {HTMLInputElement} element
         */
        findGroupByElement : function(element)
        {
            for (var i=0; i<this.len; i++)
            {
                var group = this.val[i];

                if(group.getCheckBox().name == element.name)
                {
                    return group;
                }
            }

            return null;
        },

        /**
         *
         * @param {HTMLInputElement} element
         */
        findGroupExcept : function(element)
        {
            var groups = [];
            for (var i=0; i<this.len; i++)
            {
                var group = this.val[i];

                if(group.getCheckBox().name != element.name)
                {
                    groups.push(group);
                }
            }

            return groups;
        },

        /**
         * Вызывается после заполнения списка и выполняет включение/выключение необходимых чекбоксов
         */
        afterFilling : function()
        {
            var index = -1;

            for (var i=0; i<this.len; i++)
            {
                if (index == -1)
                {
                    if (this.val[i].hasSelected)
                    {
                        index = i;
                        break;
                    }
                }
            }

            if (index > -1)
            {
                for (var j=0; j<this.len; j++)
                {
                    if (j == index)
                    {
                        continue;
                    }

                    var services = this.val[j].services;

                    for (var x=0; x<services.length; x++)
                    {
                        services[x].disableCheckBox();
                    }
                }
            }
        }
    };

    function updateStyle(elem)
	{
		var index = elem.selectedIndex;
		var selectedOption = elem.options[index];
		elem.style.color = selectedOption.style.color;
	}

	function initServicesTable(access)
	{
		var elems  = document.getElementsByName(access + '.selectedServices');
		for (var i = 0; i < elems.length; i++)
		{
			setAccessDescription(elems[i]);
            groupServicesList.put(elems[i]);
		}

        var disabledElems = document.getElementsByName(access + '.caadminServices');
        for (var i = 0; i < disabledElems.length; i++)
		{
			setAccessDescription(disabledElems[i]);
		}

        groupServicesList.afterFilling();
	}

	function setAccessDescription(el)
	{
		getLabel(el).innerHTML = (el.checked ? "разрешен" : "запрещен");
	}

	function onAccessChanged(access)
	{
		var checkbox = document.getElementById(access + "_enabled");
		if (checkbox == null)
			return;
		forEach(checkbox.form.elements,
				function(e)
				{
					e.disabled = !checkbox.checked;
				},
				function(e)
				{
					if (e == checkbox)
						return false;
					if (e.name == null) return false;
					return e.name.match(access)
				}
				);
	}
	function disableAll(access)
	{
		var checkbox = document.getElementById(access + "_enabled");
		if (checkbox == null)
			return;
		forEach(checkbox.form.elements,
				function(e)
				{
					e.disabled = true;
				},
				function(e)
				{
					if (e == checkbox)
						return false;

					return e.name.match(access)
				}
				);
	}

	var updateScheme;
	function onSchemeChanged(select, customRulesDeny)
	{
		var namePrefix = select.name.split('.')[0];
		var option = select.options[select.selectedIndex];
		var optionId = option.id;
		var category;
		if (optionId != "")
		{
			category = optionId.substring(optionId.length - 1, optionId.length);
		}
		else
		{
			category = "none";
		}
		setElement(namePrefix + ".category", category);
		updateScheme = false;
		disableServices(namePrefix, category, true, customRulesDeny);
        enableServicesForScheme(namePrefix, optionId, select, customRulesDeny);
		updateScheme = true;
	}

    /**
     * Активирует чекбоксы для выбранной схемы прав
     * @param namePrefix префикс для чекбокса
     * @param optionId идентификатор выбранной схемы
     */
    function enableServicesForScheme(namePrefix, optionId, select, customRulesDeny)
    {
        var schemeId;
        var category   = optionId.substring(optionId.length - 1, optionId.length);
        var checkboxes = document.getElementsByName(namePrefix + ".selectedServices");

        if(category == null || category.length == 0)
        {
            for (var y = 0; y < checkboxes.length; y++)
            {
                checkboxes[y].checked = false;
                checkboxes[y].onclick();
            }

            return;
        }

        schemeId = optionId.substring(optionId.indexOf('_') + 1, optionId.lastIndexOf('_'));

        for (var i = 0; i < schemes.length; i++)
        {
            if (schemes[i].schemeId == schemeId)
            {
                for (var j = 0; j < checkboxes.length; j++)
                {
                    var services = schemes[i].services;
                    for(var z=0; z<services.length; z++)
                    {
                        if(checkboxes[j].value == services[z])
                        {
                           checkboxes[j].checked = 'checked';
                           checkboxes[j].onclick();
                           break;
                        }
                        else
                        {
                           checkboxes[j].checked = false;
                           checkboxes[j].onclick();
                        }
                    }
                }
            }
        }

        if (customRulesDeny)
        {
            var opt = select.options;
            for (var index=0; index<opt.length; index ++)
            {
                if(opt[index].id.indexOf('personal') > 0)
                {
                    select.remove(index);
                }
            }
        }
    }

    /**
     * В массиве хранятся схемы с прикрепленными к ним сервисами
     */
    var schemes = new Array();
    /**
     * Инициализирует список сервисов для каждой схемы
     * @param schemeId идентификатор схемы прав
     * @param serviceId идентификатор сервиса
     */
    function initServicesForScheme(schemeId, serviceId)
    {
        var finded   = null;
        var services = null;
        
        for(var j=0; j<schemes.length; j++)
        {     
            if(schemes[j].schemeId == schemeId)
            {
                finded = schemes[j];
                break;
            }
        }
        if(finded == null)
        {
            finded   = new Object();
            services = new Array();
            
            finded.services = services;
            schemes[schemes.length] = finded;
        }
        finded.schemeId = schemeId;
        finded.services[finded.services.length] = serviceId;
    }
    
	function disableServices(namePrefix, exceptCategory, clearDisabled, customRulesDeny, serviceName)
	{
		var rx = "_" + exceptCategory + "_chk$";
		var checkboxes = document.getElementsByName(namePrefix + serviceName);
		for (var i = 0; i < checkboxes.length; i++)
		{
			var chk = checkboxes[i];
			var d = customRulesDeny || chk.disabled ? true : !chk.id.match(rx);
			chk.disabled = d;
			if (d & clearDisabled)
			{
				try
                {
                    chk.onclick();
                }
                catch(exception){};
			}
		}
	}

	function onServiceClick(chk, category)
	{
		if (updateScheme)
		{
			var sel = getElement(chk.name.split('.')[0] + '.accessSchemeId');
			sel.value = 'personal' + category;
			sel.onchange();
		}
	}

	function turnSelection(isSelectAll, listName, event)
	{
		//получаем имя выбранной категории и имя контрола для выбора
		var index = listName.indexOf('^');
		if(index==-1)
		{
			var ids = document.getElementsByName(listName);
			turnSelectionAlg(ids, isSelectAll, event);
		}
		var selectedName = listName.substring(index+1,listName.length);
		var exceptCategory = listName.substring(0,index);
		//получаем все чекбоксы
		var ids = document.getElementsByName(selectedName);
		var rx = "_" + exceptCategory + "_chk$";
		var k =0;
		var selectedIds = new Array();
		//отбираем только нужную категорию
		for (var i = 0; i < ids.length; i++)
		{
			var checkBox = ids[i];
			var d = checkBox.id.match(rx);
			if(d)
			{
				selectedIds[k] = checkBox;
				k++;
			}
		}
		//меняем
		turnSelectionAlg(selectedIds, isSelectAll, event);
	}
</script>