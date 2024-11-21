package com.urbanclap.usermanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.urbanclap.management"})
@ComponentScan("com.urbanclap.management")
@Slf4j
public class UsermanagementApplication {

	public static void main( String[] args )
	{
		SpringApplication.run(UsermanagementApplication.class,args);
		log.info(
				" " +
						"\n" +
						"\n" +
						":::'###::::'##:::::'##:'########::'######:::'#######::'##::::'##:'########:\n" +
						"::'## ##::: ##:'##: ##: ##.....::'##... ##:'##.... ##: ###::'###: ##.....::\n" +
						":'##:. ##:: ##: ##: ##: ##::::::: ##:::..:: ##:::: ##: ####'####: ##:::::::\n" +
						"'##:::. ##: ##: ##: ##: ######:::. ######:: ##:::: ##: ## ### ##: ######:::\n" +
						" #########: ##: ##: ##: ##...:::::..... ##: ##:::: ##: ##. #: ##: ##...::::\n" +
						" ##.... ##: ##: ##: ##: ##:::::::'##::: ##: ##:::: ##: ##:.:: ##: ##:::::::\n" +
						" ##:::: ##:. ###. ###:: ########:. ######::. #######:: ##:::: ##: ########:\n" +
						"..:::::..:::...::...:::........:::......::::.......:::..:::::..::........::"
		);
	}

}
