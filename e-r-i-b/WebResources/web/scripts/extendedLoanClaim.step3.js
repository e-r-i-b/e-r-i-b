function init(value)
{
    formObject.init();
}

function createSelect()
{
    var factAddressTypeSelect = {
        select : null,
        firstOption : null,
        secondOption : null,

        init : function()
        {
            this.select = $('#factAddressTypeSelect');

            this.firstOption = $('<option value="FIXED">—овпадает с посто€нной регистрацией</option>');
            this.secondOption = $('<option value="TEMPORARY">—овпадает с временной регистрацией</option>');
        },
        // Ѕазова€ ф-€ дл€ добавлени€ опции "—овпадает с посто€нной регистраций". ¬сегда д.б. первой
        appendFirst : function()
        {
            this.select.prepend(this.firstOption);
        },

        // Ѕазова€ ф-€ дл€ добавлени€ опции "—овпадает с временной регистраций". ¬сегда д.б. второй
        appendSecond : function()
        {
            this.select.find('[value="FACT"]').before(this.secondOption);
        },

        setBothValues : function()
        {
            $(this.firstOption).remove();
            $(this.secondOption).remove();
            this.appendFirst();
            this.appendSecond();
        }

    };
    factAddressTypeSelect.init();
    return factAddressTypeSelect;
}


var formObject = {

    //  онстантные значени€ дл€ названий элементов
    fixedRegistrationAddBtnText : "ƒобавить временную регистрацию",
    temporaryRegistrationAddBtnText : "ƒобавить посто€нную регистрацию",
    fixedRegistrationAddressTitle : "»нформаци€ о посто€нной регистрации",
    temporaryRegistrationAddressTitle : "»нформаци€ о временной регистрации",

    // Ѕлоки и пол€, отображение или значение которых может мен€тьс€
    registrationAddressTitle : null,
    registrationAddBtnText : null,
    secondAddressBlock : null,
    factAddressBlock : null,
    addressTypeSelect: null,
    expDateValue : null,
    factAddressType: null,

    init : function()
    {
        this.registrationAddBtnText = $('#addSecondAddress')[0];
        this.registrationAddressTitle = $('#secondAddressTitle')[0];
        this.firstAddressBlock = $('#firstAddress')[0];
        this.secondAddressBlock = $('#secondAddress')[0];
        this.factAddressBlock = $('#factAddress')[0];
        this.factAddressType = $('#registrationType3');
        this.addressTypeSelect = createSelect();

        this.expDateValue = $('input[name="registrationExpiryDate"]')[0];

        this.initElements();
        this.changeFactAddressSelect();
        this.setInitialAddressType(null);
        this.addFactAddressBlock();

    },

    initElements : function()
    {
        this.secondAddressBlock.style.display = $('#registrationType2').val() == "" ? "none" : "";
        this.factAddressBlock.style.display = $('#registrationType3').val() == "" ? "none" : "";
    },

    // ќбновить опции селекта с типами адреса фактического проживани€
    changeFactAddressSelect : function()
    {
        var isVisible = this.secondAddressBlock.style.display != "none";

        // если удалили блок со второй регистрацией, надо убрать одну опцию из селекта
        if (!isVisible)
        {
            $(this.addressTypeSelect.firstOption).remove();
            $(this.addressTypeSelect.secondOption).remove();
            this.addressTypeSelect.appendFirst();
        }
        else
            this.addressTypeSelect.setBothValues();
    },

    // ѕоказать/спр€тать блок с адресом фактической регистрации
    addFactAddressBlock : function()
    {
        var val = this.addressTypeSelect.select.find(':selected').val();

        if (val == "FACT")
            this.factAddressBlock.style.display = "";
        else
            this.factAddressBlock.style.display = "none";
    },

    // добавить (показать) блок со вторым адресом
    addSecondAddressBlock : function()
    {
        var selected = this.addressTypeSelect.select.find(" :selected").val();
        this.secondAddressBlock.style.display = "";
        this.addressTypeSelect.setBothValues();
        this.setInitialAddressType(selected);
    },

    // удалить (спр€тать) блок со вторым адресом
    removeSecondAddressBlock : function()
    {
        var selected = this.addressTypeSelect.select.find(" :selected").val();
        this.secondAddressBlock.style.display = "none";
        this.changeFactAddressSelect();
        this.setInitialAddressType(selected);
    },

    // установить в селект с типами адреса фактического проживани€ выбранное значение
    setInitialAddressType : function(val)
    {
        var value = val == "FACT" ? val : this.factAddressType.val();
        var option = this.addressTypeSelect.select.find('[value=' +value+ ']');
        if (option.val() != undefined)
            option.attr("selected", "selected");
        // если опци€ не найдена, значит, соответствующий типу адрес регистрации не указан. Ќадо выбрать другой тип регистрации
        else
        {
            var firstOption = this.addressTypeSelect.select.find(':first');
            this.factAddressType.val(firstOption.val());
            firstOption.attr("selected", "selected");
        }
    },

    // проапдейтить пол€ с типами регистрации дл€ всех трех блоков. ѕеред отправкой.
    updateFields : function()
    {
        if (this.secondAddressBlock.style.display != "none")
            $('#registrationType2').val("TEMPORARY");

        if (this.factAddressBlock.style.display != "none")
            $('#registrationType3').val("FACT");
        else
            $('#registrationType3').val(this.addressTypeSelect.select.find(':selected').val());

        $('div:hidden').find('select').remove();
        $('div:hidden').find('input').remove();
    }

};



