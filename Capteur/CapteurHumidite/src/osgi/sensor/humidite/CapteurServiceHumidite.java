package osgi.sensor.humidite;
import osgi.sensor.CapteurService;

import java.util.concurrent.TimeUnit;

import hidException.HidInterruptReportException;
import hidException.HidNotOpenDeviceException;


import oakRH.OakUsbRH;


public class CapteurServiceHumidite implements CapteurService  {
	private OakUsbRH rhSensor;

//    private boolean stop = false;
//    private int temperature;
	
    public CapteurServiceHumidite(){
        // Instanciation du capteur USB
        rhSensor = new OakUsbRH();

        try {
            // Ouverture du capteur
            rhSensor.openSensor();
            // Démarrage du thread si pas d'exception
           // new Thread(this).start();
        } catch (HidNotOpenDeviceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	@Override
	public int getValeur() {
		// TODO Auto-generated method stub
		
//		  byte[] data;
        try {
		if (rhSensor.isOpened()) {


//	                data = rhSensor.readData();
//
//	                // mutex pour l'accès aux variables
////	                synchronized (this) {
	                byte[] data  = rhSensor.readData();
	               int  humidity = (int) Math .round(rhSensor.getHumidity(data) * 1e-4 * 100);
	                return humidity;
	                }       
        else{
        	return 0;
        }
        }catch (HidInterruptReportException e) {
//	                stop = true;
	                e.printStackTrace();
	            } catch (HidNotOpenDeviceException e) {
//	                stop = true;
	                e.printStackTrace();
	            }
//	        }		
		return 0;
	}
	

}