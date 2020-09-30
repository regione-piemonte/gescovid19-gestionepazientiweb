package it.csi.gestionepazienti.gestionepazientiweb.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.ControlliApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.CurrentUserApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.DecodificheApiImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.IstitutoScolasticoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.LaboratorioApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.LocalLogoutApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.MmgVisuraDeleganteApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.PingApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.RefertiApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.SoggettoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.SoggettoScolasticoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.TamponeApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl.DecodeAreaApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl.DisponibilitaApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl.EnteApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl.StrutturaApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg.impl.MmgVisuraSoggettoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg.impl.MmgVisuraSoggettoScolasticoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.visurapazienti.impl.VisuraSoggettoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.util.SpringInjectorInterceptor;
import it.csi.gestionepazienti.gestionepazientiweb.util.SpringSupportedResource;

@ApplicationPath("api")
public class GestionepazientiwebRestApplication extends Application {
	private Set<Object> singletons = new HashSet<>();
	private Set<Class<?>> classes = new HashSet<>();

	public GestionepazientiwebRestApplication() {
		configureSwagger();
		
		singletons.add(new PingApiServiceImpl());
		singletons.add(new LocalLogoutApiServiceImpl());
		singletons.add(new CurrentUserApiServiceImpl());
		singletons.add(new SoggettoApiServiceImpl());
		singletons.add(new TamponeApiServiceImpl());
		singletons.add(new DecodificheApiImpl());
		// api posti letto
		singletons.add(new DisponibilitaApiServiceImpl());
		singletons.add(new DecodeAreaApiServiceImpl());
		singletons.add(new EnteApiServiceImpl());
		singletons.add(new VisuraSoggettoApiServiceImpl());
		singletons.add(new MmgVisuraSoggettoApiServiceImpl());
		singletons.add(new MmgVisuraDeleganteApiServiceImpl());
		singletons.add(new MmgVisuraSoggettoScolasticoApiServiceImpl());
		singletons.add(new SoggettoScolasticoApiServiceImpl());
		singletons.add(new LaboratorioApiServiceImpl());
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new StrutturaApiServiceImpl());
		singletons.add(new RefertiApiServiceImpl());
		singletons.add(new ControlliApiServiceImpl());
		singletons.add(new IstitutoScolasticoApiServiceImpl());
		

		for (Object c : singletons) {
			if (c instanceof SpringSupportedResource) {
				SpringApplicationContextHelper.registerRestEasyController(c);
			}
		}
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
	
	private void configureSwagger() {

		classes.add(ApiListingResource.class);
		classes.add(SwaggerSerializers.class);
		
        BeanConfig config = new BeanConfig();
        config.setConfigId("gest-paz-api");
        config.setTitle("Gestione Pazienti API");
        config.setVersion("v1");
        config.setContact("CSI Piemonte");
        config.setSchemes(new String[]{"http" /*, "https"*/});
        config.setBasePath(getContextRoot()+"/api");
        config.setHost("localhost:8080");
        config.setResourcePackage("it.csi.gestionepazienti.gestionepazientiweb.business.be");
        config.setDescription("API della piattaforma per l'emergenza COVID-19 di CSI Piemonte.");
        config.setPrettyPrint(true);
        config.setScan(true);
    }

	private String getContextRoot() {
		return "/gestionepazientiapiwebsrv";
//		return "/visurammgweb2";
//		String subwebapp;
//		ApplicationContext appContext = SpringApplicationContextHelper.getAppContext();
//		if(appContext==null) {
//			return "/nonlosoweb";
//		}
//		subwebapp = appContext.getEnvironment().getProperty("subwebapp");
//		if(subwebapp==null) {
//			return "/nonloso2web";
//		}
//		return "/" + subwebapp + "web";
	}

}
