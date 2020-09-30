var Constants = Constants || {};

Constants.DASHBOARDCOVID_BASE_URL = "/dashboardcovidweb/api";
if (location.hostname === "localhost" || location.hostname === "127.0.0.1")
	Constants.DASHBOARDCOVID_BASE_URL = "http://localhost:8080/dashboardcovidweb/api";



Constants.VERY_FUTURE_DATE_MILLIS = 4102444800; // 01/01/2100
 



