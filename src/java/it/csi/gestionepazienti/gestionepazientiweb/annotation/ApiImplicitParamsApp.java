package it.csi.gestionepazienti.gestionepazientiweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/** 
* Parametri impliciti per tutte le API 
*  
* @author domenico.lisi 
* 
*/ 
@ApiImplicitParams({ 
   //obbligatori 
	@ApiImplicitParam(name = "Shib-Identita-CodiceFiscale", dataType="string",  paramType = "header", required = true,  
			example = "AAAAAA00B77B000"), 
	@ApiImplicitParam(name = "X-Request-ID", dataType="string", paramType = "header", required = true,  
			example= "3545567c-e871-4a2d-a5c3-0a0d8370ba37"), 
	@ApiImplicitParam(name = "X-Applicazione-Codice", dataType="string", paramType = "header", required = true,  
			example="gestionepazientiweb"), 
	@ApiImplicitParam(name = "X-Forwarded-For", dataType="string", paramType = "header", required = true, 
	example = "216.3.128.12"), 
	//facoltativi 
	@ApiImplicitParam(name = "User-Agent", dataType="string", paramType = "header",  
			example = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0. 2403.157 Safari/537.36."), 
	 
	}) 
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface ApiImplicitParamsApp { 

} 