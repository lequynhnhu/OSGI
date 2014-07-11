/*
* TP VAP ASR - CSC5004 
* Copyright (C) 2009-2010  Institut TELECOM ; TELECOM Sudparis
* All rights reserved.
* Author: S. LERICHE - sebastien.leriche@it-sudparis.eu
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the Institut TELECOM, TELECOM SudParis nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package osgi.sensor;


import java.util.concurrent.TimeUnit;

import hidException.HidInterruptReportException;
import hidException.HidNotOpenDeviceException;


import oakRH.OakUsbRH;

public class TempHumSensor implements Runnable {

    private OakUsbRH rhSensor;

    private boolean stop = false;

    private int humidity;

    private int temperature;


    public TempHumSensor() {

        // Instanciation du capteur USB
        rhSensor = new OakUsbRH();

        try {
            // Ouverture du capteur
            rhSensor.openSensor();

            // Démarrage du thread si pas d'exception
            new Thread(this).start();
        } catch (HidNotOpenDeviceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       
    }

    @Override
    public void run() {
        byte[] data;

        // le thread tourne tant qu'on ne le stoppe pas et que
        // le capteur est accesssible
        while (!stop && rhSensor.isOpened()) {

            try {
                // lecture de l'information depuis le capteur
                // cette étape est bloquante et peut prendre jusqu'à
                // 2 secondes suivant la configuration du capteur
                data = rhSensor.readData();

                // mutex pour l'accès aux variables
                synchronized (this) {
                    // interprétation
                    humidity = (int) Math
                            .round(rhSensor.getHumidity(data) * 1e-4 * 100);
                    temperature = (int) Math.round(rhSensor
                            .getTemperature(data) * 1e-2 - 273.15);
                }

            

            } catch (HidInterruptReportException e) {
                stop = true;
                e.printStackTrace();
            } catch (HidNotOpenDeviceException e) {
                stop = true;
                e.printStackTrace();
            }
        }

    }


    // pour arrêter le thread
    public void stop() {
        stop = true;
    }

    public int getTemperature() {
        // mutex pour l'accès aux variables
        synchronized (this) {
            return temperature;
        }
    }

    public int getHumidity() {
        // mutex pour l'accès aux variables
        synchronized (this) {
            return humidity;
        }
    }
    
    public static void main(String[] args) {
    	TempHumSensor t = new TempHumSensor();
    	while (true)
    	{
    		System.out.println(t.getTemperature());
    		try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }


}
