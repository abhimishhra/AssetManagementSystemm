package com.cg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AssetEurekaDiscoveryServerApplication 
{

	public static void main(String[] args)
	{
		SpringApplication.run(AssetEurekaDiscoveryServerApplication.class, args);
		System.out.println("Asset Eureka discovery server strted on 8761 port");
	}

}
