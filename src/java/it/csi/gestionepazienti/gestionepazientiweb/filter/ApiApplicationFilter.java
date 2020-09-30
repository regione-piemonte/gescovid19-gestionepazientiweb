package it.csi.gestionepazienti.gestionepazientiweb.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import it.csi.gestionepazienti.gestionepazientiweb.util.Constants;

/**
 * Intercetta le chiamate alle api e si preoccupa di verificare se quell'api 
 * e' disponibile per una certa applicazione
 * @author 1759
 *
 */
public class ApiApplicationFilter implements Filter {

	private static List<Pattern> apiWhiteListRegExp;
	
	static {
		apiWhiteListRegExp =  new ArrayList<Pattern>();
		List<String> regs = Arrays.asList(
				new String[]{
					"^\\/gestionepazientiweb\\/api\\/",       // gestione pazienti web tutto
					"^\\/gestionepazientiapi\\/api\\/",       // gestione pazienti api tutto
					"^\\/gestionepazientiapiwebsrv\\/api\\/", // gestione pazienti api tutto
					"^\\/dashboardcovidweb\\/api\\/", // dashboard da sistemare
					"^\\/visurapazientiweb\\/api\\/visura\\/",
					"^\\/visurammgweb2\\/api\\/mmgvisura\\/",
					"^\\/visurammgweb2\\/api\\/aura\\/",
					"^\\/visurammgweb2\\/api\\/external\\/uscammg\\/",
					"^\\/visurammgweb2\\/api\\/currentUser",   // copia
					"^\\/visurammgweb2\\/api\\/decodifiche",    // copia
					"^\\/visurammgweb2\\/api\\/localLogout",    // copia
					"^\\/visurammgweb2\\/api\\/ping",   // copia
					"^\\/visurammgweb2\\/api\\/swagger.json",   // copia
					"^\\/[A-Za-z]+\\/api\\/currentUser",	// aperte a tutti 
					"^\\/[A-Za-z]+\\/api\\/decodifiche",	// aperte a tutti 
					"^\\/[A-Za-z]+\\/api\\/localLogout",	// aperte a tutti 
					"^\\/[A-Za-z]+\\/api\\/ping",	// aperte a tutti 
				}); 
		for (String regString : regs) {
			apiWhiteListRegExp.add(Pattern.compile(regString,Pattern.CASE_INSENSITIVE));
		}
	}
	
	protected static final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME + ".security");

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		LOG.info("[ApiApplicationFilter::doFilter] START");
		
		try {
			
			
			HttpServletRequest hreq = (HttpServletRequest) req;
			if(hreq == null) {
				LOG.debug("[ApiApplicationFilter::doFilter] request is null ");
			}
			
			if(resp == null) {
				LOG.debug("[ApiApplicationFilter::doFilter] response is null ");
			}
			
			if(chain == null) {
				LOG.info("[ApiApplicationFilter::doFilter] chain is null ");
			}else {
				LOG.info("[ApiApplicationFilter::doFilter] chain is NOT null ");
			}
			
			HttpServletResponse hresp = (HttpServletResponse) resp;
	
	
			LOG.info("[ApiApplicationFilter::doFilter] RequestURI: "+hreq.getRequestURI());
			
			if(hreq.getRemoteAddr()!=null)
				LOG.debug("[ApiApplicationFilter::doFilter] RemoteAddress: "+hreq.getRemoteAddr());
					
			String uri = hreq.getRequestURI();
			
			Enumeration parms = hreq.getHeaderNames();
			String parmname;
			String parmval;
			while (parms.hasMoreElements()) {
			    parmname = (String)parms.nextElement();
			    parmval = hreq.getHeader(parmname);
			    LOG.debug("Header ParamName  " + parmname + " [" + parmval +"]");
			}
			
			Boolean allowed = false;
			
			for (Pattern whiteListReg : apiWhiteListRegExp) {
				
				if (whiteListReg.matcher(uri).find())
				{
					LOG.info("[ApiApplicationFilter::doFilter] filter matched "+ whiteListReg);				
					allowed = true;
				}
			}
							
					
			if (allowed!=null && allowed) {
				LOG.info("[ApiApplicationFilter::doFilter] allowed: " + allowed );	
				LOG.info("[ApiApplicationFilter::doFilter] log response " + resp);
				chain.doFilter(req, resp);
			}
			else {
				LOG.error("[ApiApplicationFilter::doFilter] chiamata rifiutata per  "+hreq.getRequestURI());
				throw new ServletException("Api not Allowed "+ uri);
			}
		
		}
		catch (IOException e) {
			LOG.error("[ApiApplicationFilter::doFilter] IOException:" + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Eccezione IOException ");
		}
		catch (ServletException e) {
			LOG.error("[ApiApplicationFilter::doFilter] ServletException:" + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Eccezione ServletException ");
		}
		catch (Exception e) {
			LOG.error("[ApiApplicationFilter::doFilter] Eccezione:" + e.getCause().getMessage());
			e.printStackTrace();
			throw new ServletException("Eccezione generica ");
		}
		
		
		LOG.info("[ApiApplicationFilter::doFilter] END");
	}

	public static void main(String[] args) {
		String r  ="^\\/gestionepazientiweb\\/api\\/";
		
		System.out.println("/gestionepajzientiweb/api/piip".matches(r));
		Pattern p = Pattern.compile(r);
		
		java.util.regex.Matcher m = p.matcher("/gestionefpazientiweb/api/piip");
		System.out.println(m.find());
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}