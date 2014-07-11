package osgi.hello.en;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import osgi.hello.HelloService;


public class Activator implements BundleActivator {
    HelloService service;
    ServiceRegistration reg;
    
	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		HelloService service = new HelloServiceEn();
		reg = context.registerService(HelloService.class.getName(), service, null);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
        service = null;
        
        if (reg != null) {
                reg.unregister();
        }
		
		
	}

}

