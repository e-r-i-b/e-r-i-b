/* Вагон Аэроэкспресса */
function AeroexpressCar(root, editable, maxCountSelectedPlaces)
{
    this.root = root;
    this.editable = editable;
    this.maxCountSelectedPlaces = maxCountSelectedPlaces;
    // выбранные места
    this.selectedPlaces = [];
    this.initialize = function()
    {
        var self = this;
        var selectedPlacesStr =  $(this.root).find("#selectedHiddenPlaces").val();
        if(!isEmpty(selectedPlacesStr))
            self.initSelectedPlaces(selectedPlacesStr.split("@"));

        if(editable)
        {
             $(this.root).find(".coach table tr span").click(function(){
                if($(this).hasClass("free"))
                {
                    self.selectPlace(this);
                    if(self.maxCountSelectedPlaces > 0 && self.selectedPlaces.length > self.maxCountSelectedPlaces)
                    {
                        var firstPlace = $(self.root)
                                .find(".coach table td")
                                .find("span#place" + self.selectedPlaces[0])
                                .get(0);
                        self.freePlace(firstPlace);
						addError("В одном заказе Вы можете забронировать не более " +  self.maxCountSelectedPlaces + " мест.", null, true);
                        payInput.fieldError('aeroexpress-choice-place');
                    }
                }
                else if($(this).hasClass("selected"))
                {
                    self.freePlace(this);
                }
            });
        }
    };

    this.initSelectedPlaces = function(selectedPlacesArray)
    {
        var self = this;
        var cols = $(self.root).find(".coach table td");
        for(var i = 0; i < selectedPlacesArray.length; i++)
            self.selectPlace(cols.find("span#place" + selectedPlacesArray[i]).get(0));
    };

    this.selectPlace = function(place)
    {
        this.selectedPlaces[this.selectedPlaces.length] = place.innerHTML;
        this.updateAdditionFields();
        $(place).removeClass("free").addClass("selected");
    };

    this.freePlace = function(place)
    {
        for(var i = 0; i < this.selectedPlaces.length; i++)
        {
            if(this.selectedPlaces[i] == place.innerHTML)
                this.selectedPlaces.splice(i,1);
        }
        this.updateAdditionFields();
        $(place).removeClass("selected").addClass("free");
    };

    this.updateAdditionFields = function()
    {
        $(this.root).find("#selectedVisiblePlaces").text(this.getSelectedPlace(","));
        $(this.root).find("#selectedHiddenPlaces").val(this.getSelectedPlace("@"));
    };

    this.getSelectedPlace = function(dilimiter)
    {
        var result = "";
        for(var i = 0; i < this.selectedPlaces.length; i++)
            result += (i == 0 ? "" : dilimiter) + this.selectedPlaces[i];
        return result;
    };
}