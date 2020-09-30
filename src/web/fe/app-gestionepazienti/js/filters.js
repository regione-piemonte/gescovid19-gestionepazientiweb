'use strict';

/* Filters */
var appFilters  = angular.module('gestionepazienti.filters', []);

appFilters.filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return (input && input!=null) ? input.slice(start) : [];
    };
});

appFilters.filter('number_ellipse', function() {
	return function(input, min, max) {
		var output = input;
		if (input && Helpers.util.isNumber(input)) {
			if(Math.abs(input)<min)
				output ="<" + min; 
			else if(Math.abs(input)>1000)
				output =">" + max; 
			else
				output = input.toFixed(2);
	    }
		return output;
	};
});


appFilters.filter('dataset_date_format', function() {
	return function(input) {
		if (input && input.length==8) {
			return new Date(input.substring(0,4), input.substring(4,6)-1, input.substring(6,8)).format("dd/mm/yyyy");
	    }
		else
			return "";
	};
});

appFilters.filter('string_ellipse', function () {
    return function (text, length, end) {
    	if(typeof text === "undefined"  || text == null)
    		text = "";
    	else
    		text = "" + text;
        if (isNaN(length))
            length = 10;

        if (end === undefined)
            end = "...";

        if (text.length <= length || text.length - end.length <= length) {
            return text;
        }
        else {
            return String(text).substring(0, length-end.length) + end;
        }
    };
});


appFilters.filter('millisFormatted', function() {
    return function(millis) {
    	var dateOut = "";
    	if(millis && millis!=null){
    		var dateIn = new Date(millis);
    		dateOut  = dateIn.format("dd/mm/yyyy HH:MM");
    	}
        return dateOut;
    };
});

appFilters.filter('datetimeFormatted', function() {
    return function(dateIn) {
    	var dateOut = "";
    	if(dateIn && dateIn!=null)
    		dateOut  = dateIn.format("dd/mm/yyyy HH:MM");
        return dateOut;
    };
});

app.filter('default', [function(){
	  return function(value, def) {
	    return value || def;
	  };
}]);

appFilters.filter('dateFormatted', function() {
    return function(dateIn) {
    	var dateOut = "";
    	if(dateIn && dateIn!=null)
    		dateOut  = dateIn.format("dd/mm/yyyy");
        return dateOut;
    };
});


appFilters.filter('two_letters', function() {
    return function(input) {
    	var output = "";
    	if(input && input!=null && input.length>0){
    		if(input.length==1)
    			output = input.toUpperCase();
    		else if(input.length==2)
    			output = input[0].toUpperCase() + input.slice(1)[0];
    		else{
    			var words = input.split(" ");
    			if(words.length>1){
    				output = words[0][0].toUpperCase() + words[1][0].toUpperCase();
    			}
    			else
        			output = words[0].toUpperCase()[0] + words[0].slice(1)[0];
    		}
    	}
        return output;
    };
});


appFilters.filter('unsafe', function($sce) {
    return function(val) {
        return $sce.trustAsHtml(val);
    };
});


appFilters.filter('format_filesize', function() {
	return function(input) {
		var output = "";
		if (input) {
			input=Math.trunc(input);
			if(input<1000)
				output=input+"byte";
			else if(input<1000000)
				output=(input/1000).toFixed(1)+"Kb";
			else if(input<1000000000)
				output=(input/1000000).toFixed(1)+"Mb";
			else if(input<1000000000000)
				output=(input/1000000000).toFixed(1)+"Gb";
	    }
		return output;
	};
});

appFilters.filter('format_big_number', function() {
	return function(input) {
		var output = "";
		if (input) {
			input=Math.trunc(input);
			if(input<1000)
				output=input;
			else if(input<1000000)
				output=(input/1000).toFixed(2)+" <span class='counter-group'>mila</span>";
			else if(input<1000000000)
				output=(input/1000000).toFixed(2)+" <span class='counter-group'>mln</span>";
			else if(input<1000000000000)
				output=(input/1000000000).toFixed(2)+" <span class='counter-group'>mld</span>";
	    }
		return (""+output).replace(".", ","); 
	};
});

appFilters.filter('hide_server_error', function() {
	return function(input) {
		var output = input; 
		if(input && input.data && Helpers.util.startsWith(input.data,"<html>")){
			output = "Server Error";
		}
		return output;
	};
})

appFilters.filter('nvl', function() {
	return function(input, ifNull) {
		var output = input;
		if (!input || input ==null) {
			output = ifNull;
	    }
		return output;
	};
});

appFilters.filter('booleanToString', function() {
	return function(input) {
		var output = 'NO';
		if (input!=null && (input===1 || input==1 || input == 'true'||input === true))
			output = 'YES';
		return output;
	};
});

appFilters.filter('tamponeautorizzato', function() {
	return function(input) {
		var output = input;
		if (input!=null && input=='P')
			output = 'Proposto';
		else if(input!=null && input=='NI')
			output = 'NO Fornire maggiori informazioni Contattare UC: 0114326756';
		return output;
	};
});

appFilters.filter('laboratorioshort', function() {
	return function(input) {
		var output = input;
		if (input)
			output = input.replace('Laboratorio', 'Lab. ');
		return output;
	};
});


appFilters.filter('nomeareashort', function() {
	return function(input) {
		var output = input;
		if (input)
			output = input.split("-")[0];
		return output;
	};
});


appFilters.filter('inserimentotamponedate', function() {
	return function(input) {
		var output = input;
		if(input){			
			try{
				//2020-03-13 19:45:12.935	
				var date1 = input.split(" ")[0];
				var date = date1.split("-").reverse().join("/");
				//var time1 = input.split(" ")[1];
				//var time = time1.split(".")[0];
				
				//output = date + " " + time;
				output = date ;
			}catch(e){
				console.error("inserimentotamponedate", e);
			}
		}
		
		return output;
	};
});



appFilters.filter('decodeFromId', function() {
	return function(decodeId, decodes, decodeIdPropName) {
		var decode = null;
		if ((typeof decodes != "undefined") && decodes !=null && (typeof decodeId != "undefined") && decodeId !=null && (typeof decodeIdPropName != "undefined") && decodeIdPropName !=null) {
			for (var dIndex = 0; dIndex < decodes.length; dIndex++) {
				if(decodeId == decodes[dIndex][decodeIdPropName]){
					decode  = decodes[dIndex];
					break;
				}
			}
		}
		return decode ;
	};
});




appFilters.filter('isEmpty', [function() {
	  return function(object) {
	    return angular.equals({}, object);
	  }
}]);

appFilters.filter('keysLength', [function() {
	  return function(object) {
		  if(object)
			  return Object.keys(object).length;
		  else 
			 return 0;
	  }
}]);