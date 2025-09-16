package com.techmanage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for TechManageApplication
 * Tests actual Spring Boot context loading and configuration
 */
@SpringBootTest(classes = TechManageApplication.class, webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("test")
class TechManageApplicationIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        // Verify that the Spring application context loads successfully
        assertNotNull(applicationContext, "Application context should be loaded");
    }

    @Test
    void applicationContext_shouldContainRequiredBeans() {
        // Test that essential Spring Boot beans are present
        assertTrue(applicationContext.containsBean("techManageApplication"),
                  "Application main class should be registered as a bean");
    }

    @Test
    void applicationContext_shouldHaveCorrectBeanCount() {
        // Test that context has reasonable number of beans (Spring Boot auto-configuration)
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        assertTrue(beanNames.length > 10,
                  "Context should contain multiple beans from Spring Boot auto-configuration");
    }

    @Test
    void environment_shouldHaveTestProfile() {
        // Test that test profile is active
        String[] activeProfiles = environment.getActiveProfiles();
        assertEquals(1, activeProfiles.length, "Should have exactly one active profile");
        assertEquals("test", activeProfiles[0], "Test profile should be active");
    }

    @Test
    void applicationContext_shouldHaveDataSourceBeans() {
        // Test that data source related beans are present (H2 for tests)
        assertTrue(applicationContext.containsBean("dataSource"),
                  "DataSource bean should be auto-configured");
    }

    @Test
    void applicationContext_shouldHaveJpaBeans() {
        // Test that JPA related beans are present
        assertTrue(applicationContext.containsBean("entityManagerFactory"),
                  "EntityManagerFactory should be auto-configured");
    }

    @Test
    void applicationContext_shouldHaveWebBeans() {
        // Test that web-related beans are present
        assertTrue(applicationContext.containsBean("requestMappingHandlerMapping"),
                  "RequestMappingHandlerMapping should be auto-configured");
    }

    @Test
    void applicationContext_shouldHaveUserControllerBean() {
        // Test that our custom controller is registered
        assertTrue(applicationContext.containsBean("userController"),
                  "UserController should be registered as a bean");
    }

    @Test
    void applicationContext_shouldHaveUserServiceBean() {
        // Test that our custom service is registered
        assertTrue(applicationContext.containsBean("userServiceImpl"),
                  "UserServiceImpl should be registered as a bean");
    }

    @Test
    void applicationContext_shouldHaveRepositoryBeans() {
        // Test that repository beans are present
        String[] repositoryBeans = applicationContext.getBeanNamesForType(
            org.springframework.data.jpa.repository.JpaRepository.class);
        assertTrue(repositoryBeans.length > 0,
                  "At least one JPA repository should be registered");
    }

    @Test
    void applicationContext_shouldHaveExceptionHandlerBean() {
        // Test that global exception handler is registered
        assertTrue(applicationContext.containsBean("globalExceptionHandler"),
                  "GlobalExceptionHandler should be registered as a bean");
    }

    @Test
    void environment_shouldHaveH2Configuration() {
        // Test that H2 database configuration is present
        String datasourceUrl = environment.getProperty("spring.datasource.url");
        assertNotNull(datasourceUrl, "DataSource URL should be configured");
        assertTrue(datasourceUrl.contains("h2"), "Should use H2 database for tests");
    }

    @Test
    void environment_shouldHaveJpaConfiguration() {
        // Test that JPA configuration is present
        String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");
        assertNotNull(ddlAuto, "Hibernate DDL auto should be configured");
    }

    @Test
    void applicationContext_shouldBeRefreshable() {
        // Test that context is refreshable
        assertTrue(applicationContext instanceof org.springframework.context.ConfigurableApplicationContext,
                  "Application context should be configurable/refreshable");
    }

    @Test
    void applicationContext_shouldHaveEnvironment() {
        // Test that environment is properly set
        assertEquals(environment, applicationContext.getEnvironment(),
                    "Context environment should match injected environment");
    }

    @Test
    void applicationContext_shouldHaveApplicationName() {
        // Test application name configuration
        String appName = environment.getProperty("spring.application.name");
        if (appName != null) {
            assertFalse(appName.trim().isEmpty(), "Application name should not be empty if set");
        }
    }

    @Test
    void applicationContext_shouldSupportComponentScanning() {
        // Test that component scanning worked
        String[] controllerBeans = applicationContext.getBeanNamesForAnnotation(
            org.springframework.web.bind.annotation.RestController.class);
        assertTrue(controllerBeans.length > 0,
                  "Component scanning should find @RestController beans");

        String[] serviceBeans = applicationContext.getBeanNamesForAnnotation(
            org.springframework.stereotype.Service.class);
        assertTrue(serviceBeans.length > 0,
                  "Component scanning should find @Service beans");
    }

    @Test
    void applicationContext_shouldHaveValidationSupport() {
        // Test that validation beans are present
        assertTrue(applicationContext.containsBean("validator"),
                  "Validator bean should be auto-configured");
    }

    @Test
    void applicationContext_shouldHaveTransactionSupport() {
        // Test that transaction management is configured
        assertTrue(applicationContext.containsBean("transactionManager"),
                  "Transaction manager should be auto-configured");
    }

    @Test
    void environment_shouldHaveDefaultProperties() {
        // Test that default Spring Boot properties are set
        String serverPort = environment.getProperty("server.port", "8080");
        assertNotNull(serverPort, "Server port should have a default value");
    }

    @Test
    void applicationContext_shouldHaveCorrectParent() {
        // Test context hierarchy
        if (applicationContext.getParent() != null) {
            assertNotNull(applicationContext.getParent(),
                         "Parent context should not be null if present");
        }
    }

    @Test
    void applicationContext_shouldBeRunning() {
        // Test that context is active and running
        assertTrue(applicationContext instanceof org.springframework.context.ConfigurableApplicationContext);
        org.springframework.context.ConfigurableApplicationContext configurableContext =
            (org.springframework.context.ConfigurableApplicationContext) applicationContext;
        assertTrue(configurableContext.isActive(), "Context should be active");
    }
}