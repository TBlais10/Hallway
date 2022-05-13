package com.careerdevs.myHalwayLocker;

import com.careerdevs.myHalwayLocker.Auth.ERole;
import com.careerdevs.myHalwayLocker.repositories.RoleRepository;
import com.careerdevs.myHalwayLocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@SpringBootApplication
public class MyHallwayLockerApplication {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Value("${spring.datasource.driver-class-name}")
	public String myDriver;

	@Value("${spring.datasource.url}")
	public String myUrl;

	@Value("${spring.datasource.username}")
	public String username;

	@Value("${spring.datasource.password}")
	public String password;

	public static void main(String[] args) {
		SpringApplication.run(MyHallwayLockerApplication.class, args);
	}

	@Bean
	public void loadENums() {
		int roleCheck = roleRepository.checkTableSize();

		if (roleCheck < ERole.values().length){
			int id = 1;
			for (ERole role : ERole.values()) {
				if (roleRepository.findByName(role).isEmpty()){
					try	{
						Connection conn = DriverManager.getConnection(myUrl, username, password);
						Class.forName(myDriver);
						String query = "Insert into role (id, name) values (?,?)";
						PreparedStatement statement = conn.prepareStatement(query);

						statement.setString(1, Integer.toString(id));
						statement.setString(2, role.toString());

						statement.executeUpdate();
					} catch (Exception e){
						System.out.println(e.getMessage());
					}
				}
				id++;
			}
		}
	}
}
