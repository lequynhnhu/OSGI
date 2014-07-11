package osgi.sensor.req;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.*;

import osgi.sensor.CapteurService;
import osgi.sensor.req.*;

public class CapteurServiceRequester implements Runnable {
BundleContext context;
Boolean stop;

CapteurServiceRequester(BundleContext context){
	this.context=context;
	stop=false;
}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop){
			ServiceReference refs[];
			try{
				refs=context.getServiceReferences((String) null, "(objectClass="+ CapteurService.class.getName()+")");	
			
			if (refs !=null && refs.length !=0){
				for(ServiceReference serRef : refs){
					CapteurService service = (CapteurService) context.getService(serRef);
					System.out.println(service.getValeur());
				}
			}
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
	

