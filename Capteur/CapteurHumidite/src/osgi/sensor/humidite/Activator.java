package osgi.sensor.humidite;
import org.osgi.framework.*;

import osgi.sensor.CapteurService;



public class Activator implements BundleActivator{
	CapteurService service;
	ServiceRegistration reg;
	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		service = new CapteurServiceHumidite();
		reg = context.registerService(CapteurService.class.getName(), service, null);
		
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		if( reg!=null){
				reg.unregister();
				reg=null;
		}
		
	}

}
