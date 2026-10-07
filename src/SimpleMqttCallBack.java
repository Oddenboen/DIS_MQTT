//opg7pgm

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class SimpleMqttCallBack implements MqttCallback {
    int status = 0;
    private boolean powerOn = true;

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String res = new String(mqttMessage.getPayload());
        // res indeholder en m ling som et JSON-object
        // put real stuff here     < --------    !!!!!!!!!!
        JSONObject jo = new JSONObject(res);
        JSONObject jo2 = jo.getJSONObject("AM2301");
        double temp = jo2.getDouble("Temperature");
        double humid = jo2.getDouble("Humidity");
        System.out.println("Temp: " + temp);
        System.out.println("Humidi: " + humid);
        System.out.println(res);
        if (humid >= 85 && !powerOn) {
            App.MQTTprogram.powerOn();
            powerOn = true;
        } else if (powerOn) {
            App.MQTTprogram.powerOff();
            powerOn = false;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}
