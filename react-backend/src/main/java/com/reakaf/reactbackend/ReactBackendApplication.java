package com.reakaf.reactbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Calendar;

@SpringBootApplication
public class ReactBackendApplication {
	public static boolean keepRunning = true;

	public static void main(String[] args) {
		SpringApplication.run(ReactBackendApplication.class, args);
	}

	private synchronized void waitMethod() {

		while (true) {
			//System.out.println("always running program ==> " + Calendar.getInstance().getTime());
			try {
				this.wait(2000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

}

//public class
//
//	@Override
//	public void run(String... args) throws Exception {
//		KafkaRestaurantMessageProcessor processor = new KafkaRestaurantMessageProcessor();
//		processor.kafkaReceiver = new KafkaConfig().kafkaReceiver();
//		processor.repository = ApplicationContext.g
//		processor.run();
//
//		ReactBackendApplication object = new ReactBackendApplication();
//		object.waitMethod();
//	}
//}
