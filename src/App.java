import java.io.IOException;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class App {
    //opg5pgm

    public static class MQTTprogram {
        static MqttClient sampleClient;

        public static void main(String[] args) throws InterruptedException {
            // TODO Auto-generated method stub
            String broker = "tcp://192.168.0.115:1883";
            MemoryPersistence persistence = new MemoryPersistence();
            try {
                sampleClient = new MqttClient(broker, MqttClient.generateClientId(), persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                sampleClient.setCallback(new SimpleMqttCallBack());
                System.out.println("Connecting to broker: " + broker);
                sampleClient.connect(connOpts);
                System.out.println("Connected");
//                String topicPower = "cmnd/grp3254/Power1";
//                String on = "1";
//                String off = "0";
                String topicRecieve = "tele/+/SENSOR";

                // put real stuff here        < -------- !!!!
               // publishMessage(sampleClient,topic,off);
                sampleClient.subscribe(topicRecieve);
                Thread.sleep(200000);
                powerOff();

                sampleClient.disconnect();
                System.out.println("Disconnected");
                System.exit(0);
            } catch (MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
                me.printStackTrace();

            }
        }

        //opg6pgm
        public static void publishMessage(MqttClient sampleClient, String topicsend, String content) throws MqttPersistenceException, MqttException {
            // Laver en publish p  sampleClient med topic topicsend og indhold content.
            MqttMessage message = new MqttMessage();
            message.setPayload(content.getBytes());
            System.out.println(content.getBytes());
            sampleClient.publish(topicsend, message);
            System.out.println("Message published");
        }

        public static void powerOn() {
            String topicPower = "cmnd/grp3254/Power1";
            String on = "1";
            try {
                publishMessage(sampleClient, topicPower, on);
            } catch (MqttException e) {

            }
        }

        public static void powerOff() {
            String topicPower = "cmnd/grp3254/Power1";
            String off = "0";
            try {
                publishMessage(sampleClient, topicPower, off);
            } catch (MqttException e) {

            }
        }

    }
}
