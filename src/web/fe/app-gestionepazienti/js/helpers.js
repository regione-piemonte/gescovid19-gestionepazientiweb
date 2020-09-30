var Helpers = Helpers || {};

Helpers.util = {
	isStringEmpty : function(str) {
		return (!str || 0 === str.length);
	},
	isValidEmail: function(email){
	    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(email);
	},
	
	isValidCF: function(cf) {
	      var validi, i, s, set1, set2, setpari, setdisp;
	      if( cf == '' )  return '';
	      cf = cf.toUpperCase();
	      if( cf.length != 16 )
	          return false;
	      validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	      for( i = 0; i < 16; i++ ){
	          if( validi.indexOf( cf.charAt(i) ) == -1 )
	              return false;
	      }
	      set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
	      s = 0;
	      for( i = 1; i <= 13; i += 2 )
	          s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	      for( i = 0; i <= 14; i += 2 )
	          s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	      if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) )
	          return false;
	      return true;
	},
	isNumber: function (n) {
		  return !isNaN(parseFloat(n)) && isFinite(n);
	},

	scrollTo : function(elementId) {
		var top = 0;
		if (elementId)
			top = $('#' + elementId).offset().top;

		$('html,body').animate({
			scrollTop : top
		}, 500);

	},
	
	scrollTopModal : function(window){
		window.animate({
			scrollTop : 0
		}, 500);
	},

	startsWith: function(str, word) {
	    return str.lastIndexOf(word, 0) === 0;
	},
	formatDateForInputHtml5: function(dateIn){
		var dateOut = "";
		try{
			var day = dateIn.getDate();
			var month = 1+ dateIn.getMonth();
			
			dateOut = ""+dateIn.getFullYear()+"-"+(month<10?"0":"")+month+"-"+(day<10?"0":"")+day;
		}
		catch (e) {
			console.error("Helpers.util.formatDateForInputHtml5 - dateIn, error",dateOut, e);
		}

		return dateOut;
		
	}, 

	capitaliseFirstLetter:function (input){
		if(input && input!=null)
			return string.charAt(0).toUpperCase() + string.slice(1);
		return "";
	}, 
	camelize: function (str) {
		if(str && str!=null)
			return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
		else
			return "";
	},
	
	stringEllipse: function(text, length, end) {
    	
    	if(typeof text === "undefined"  || text == null)
    		text = "";
    	
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
	}, 
		
	endsWith:function(str, suffix){
		if(!str || str==null)
			str = "";
		return str.indexOf(suffix, str.length - suffix.length) !== -1;
	}, 
	
	downloadCSV : function(csv, filename) {
	    var csvFile;
	    var downloadLink;

	    // CSV file
	    csvFile = new Blob([csv], {type: "text/csv"});

	    // Download link
	    downloadLink = document.createElement("a");

	    // File name
	    downloadLink.download = filename;

	    // Create a link to the file
	    downloadLink.href = window.URL.createObjectURL(csvFile);

	    // Hide download link
	    downloadLink.style.display = "none";

	    // Add the link to DOM
	    document.body.appendChild(downloadLink);

	    // Click download link
	    downloadLink.click();
		document.body.removeChild(downloadLink);
	},
	
	downloadMultiCSV:  function(csvFiles) {
		  var link = document.createElement('a');
		  link.style.display = 'none';
		  document.body.appendChild(link);
		  for (var i = 0; i < csvFiles.length; i++) {
			  link.setAttribute('download', csvFiles[i].name);
		    link.setAttribute('href', window.URL.createObjectURL(csvFiles[i]));
		    link.click();
		  }

		  document.body.removeChild(link);
	},
	

	getQueryParams: function(qs) {
		var params = {};
		if(typeof qs !== 'undefined' && qs!=null){
		    qs = qs.split('+').join(' ');
	
		    var tokens;
		    var re = /[?&]?([^=]+)=([^&]*)/g;
	
		    while (tokens = re.exec(qs)) {
		        params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
		    }
		}
	    return params;
	},
	has : function(obj, key) {
	    return key.split(".").every(function(x) {
	        if(typeof obj != "object" || obj === null || !(x in obj))
	            return false;
	        obj = obj[x];
	        return true;
	    });
	},
	
	isEmpty : function(obj) {
	  for(var prop in obj) {
	    if(obj.hasOwnProperty(prop)) {
	      return false;
	    }
	  }

	  return JSON.stringify(obj) === JSON.stringify({});
	},

	lZero: function pad(num, size) {
	    var s = num+"";
	    while (s.length < size) s = "0" + s;
	    return s;
	},
	
	safeString: function(str){
		return (typeof str == 'undefined' || str == null)?"":str;
	}

	
	
};


Helpers.pdf = {
	dettaglioPaziente: function(paziente){
		var doc = new jsPDF()
		doc.setFont('arial');
		// 

		doc.setDrawColor(0);
		doc.rect(10, 185, 5, 5); 
		doc.text('AUTORIZZANO', 25, 190, null, null, 'left');
		doc.rect(10, 195, 5, 5); 
		doc.text('NON AUTORIZZANO', 25, 200, null, null, 'left');
		if(module.delegate.authorize=='true'){
			doc.setLineWidth(.5);
			doc.line(10, 185, 15, 190);
			doc.line(10, 190, 15, 185);
		}
		else if(module.delegate.authorize=='false'){
			doc.setLineWidth(.5);
			doc.line(10, 195, 15,200);
			doc.line(10, 200, 15, 195);
		}

		doc.text('MODULO DELEGA RITIRO/USCITA PARTECIPANTI', doc.internal.pageSize.width/2, 20, null, null, 'center');
		doc.text('ACTION THEATRE SUMMER CAMP', doc.internal.pageSize.width/2, 30, null, null, 'center');
		doc.text('I sottoscritti  ', 10, 60, null, null, 'left');
		this.setValue(doc, module.parents, 70, 60);
		doc.text('Genitori dell\'alunno/a', 10, 70, null, null, 'left');
		this.setValue(doc, module.childs, 70, 70);
		doc.text('Delegano', doc.internal.pageSize.width/2, 90, null, null, 'center');
		doc.text('Nome e Cognome', 10, 110, null, null, 'left');
		this.setValue(doc, module.delegate.name, 70, 110);
		doc.text('Qualifica ', 10, 120, null, null, 'left');
		this.setValue(doc, module.delegate.qualification, 70, 120);
		doc.text('Carta d\'identità n.', 10, 130, null, null, 'left');
		this.setValue(doc, module.delegate.document, 70, 130, 130);
		doc.text('Data di nascita', 120, 130, null, null, 'left');
		var birthdateString;
		if(module.delegate.birthdate){
			var d = module.delegate.birthdate;
			birthdateString = d.getDate()  + "/" + (d.getMonth()+1) + "/" + d.getFullYear() ;
		}
		this.setValue(doc, birthdateString, 160, 130);
		
		var extempt = "esonerare la società Learning English Through Fun srl e i suoi collaboratori da ogni responsabilità civile e penale successiva all'affidamento del minore"
		var lines = doc.splitTextToSize(extempt, doc.internal.pageSize.width - 20);
		doc.text(10, 150, lines);
		doc.text('Oppure', doc.internal.pageSize.width/2, 175, null, null, 'center');

		var authorizes = "il/la figlio/a ad uscire autonomamente al termine delle attività del Summer Camp sollevando la società Learning English Through Fun srl e i suoi collaboratori da ogni responsabilità civile e penale successiva all'affidamento del minore"
		var lines2 = doc.splitTextToSize(authorizes, doc.internal.pageSize.width - 20);
		doc.text(10, 215, lines2);
		doc.text('Luogo ', 10, 250, null, null, 'left');
		this.setValue(doc, module.location, 30, 250, 120);
		doc.text('Data', 10, 260, null, null, 'left');
		var dateString;
		if(module.date){
			var d = module.date;
			dateString = d.getDate()  + "/" + (d.getMonth()+1) + "/" + d.getFullYear() ;
		}
		this.setValue(doc, dateString, 30, 260, 120);
		

		doc.text("Firma dei genitori dell'alunno/a ", 120, 250, null, null, 'left');
		doc.setLineWidth(.2);
		doc.line(120, 260, doc.internal.pageSize.width - 20, 260);
		doc.line(120, 270, doc.internal.pageSize.width - 20, 270);


		doc.save('MODULO DELEGA.pdf');
	}	
};


/*
 * Date Format 1.2.3
 * (c) 2007-2009 Steven Levithan <stevenlevithan.com>
 * MIT license
 *
 * Includes enhancements by Scott Trenda <scott.trenda.net>
 * and Kris Kowal <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask.
 * Returns a formatted version of the given date.
 * The date defaults to the current date/time.
 * The mask defaults to dateFormat.masks.default.
 */

var dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoDateTime2:   "yyyy-mm-dd'+'HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
	return dateFormat(this, mask, utc);
};
