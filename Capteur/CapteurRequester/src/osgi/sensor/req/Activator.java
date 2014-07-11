package osgi.sensor.req;
import org.osgi.framework.*;

import osgi.sensor.CapteurService;

public class Activator implements BundleActivator{
	boolean stop = false;
	BundleContext context;
	Thread th;
	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		this.context=context;
		
		CapteurServiceRequester req= new CapteurServiceRequester(context);
		th= new Thread(req);
		th.start();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		th.stop();
	}

}
