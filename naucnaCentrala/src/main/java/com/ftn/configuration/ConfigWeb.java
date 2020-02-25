package com.ftn.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ftn.security.CheckTokenFilter;

@Configuration
@EnableWebSecurity 	
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableElasticsearchRepositories(basePackages = "com.ftn.elasticSearch.repository")
public class ConfigWeb extends WebSecurityConfigurerAdapter {
	
	@Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.clustername}")
    private String EsClusterName;
 
    @Bean
    public Client client() throws UnknownHostException {
        Settings elasticsearchSettings = Settings.builder()
          .put("client.transport.sniff", true)
          .put("cluster.name", EsClusterName).build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }
 
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }

	@Bean
	public CheckTokenFilter authenticationFilter() throws Exception {
	CheckTokenFilter authenticationFilter = new CheckTokenFilter();

    authenticationFilter.setAuthenticationManager(authenticationManagerBean());
    return authenticationFilter;
	}
	
	 @Bean
	 @Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	 }
	 
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	@Override
  	protected void configure(HttpSecurity http) throws Exception {
		
		http
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()
				.antMatchers("/admin/**").permitAll()
				.antMatchers("/editor/**").permitAll()
				.antMatchers("/user/**").permitAll().and()
				
				//.anyRequest().authenticated().and()
				
				
				.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
				

				
		http		.csrf().disable();
		
	}
	
}
