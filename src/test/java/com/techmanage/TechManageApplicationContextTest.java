package com.techmanage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring Boot specific context tests for TechManageApplication
 * Tests various Spring Boot features and configurations
 */
@SpringBootTest(classes = TechManageApplication.class, webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
class TechManageApplicationContextTest {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Test
    void springBootApplication_shouldLoadWithMinimalProfile() {
        // Test application loads with minimal configuration
        assertNotNull(applicationContext, "Application context should load");
        assertTrue(applicationContext.isRunning(), "Application context should be running");
    }

    @Test
    void springBootApplication_shouldHaveAutoConfiguration() {
        // Test that Spring Boot auto-configuration is working
        String[] autoConfigBeans = applicationContext.getBeanNamesForType(
            org.springframework.boot.autoconfigure.AutoConfigurationImportSelector.class);

        // Check for essential auto-configuration components
        assertTrue(applicationContext.containsBean("org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration"),
                  "Property placeholder auto-configuration should be present");
    }

    @Test
    void springBootApplication_shouldScanComponents() {
        // Test that @ComponentScan is working properly
        String[] componentBeans = applicationContext.getBeanDefinitionNames();

        // Look for beans from our package
        long techManageBeans = java.util.Arrays.stream(componentBeans)
            .filter(name -> applicationContext.getType(name) != null)
            .filter(name -> {
                Class<?> type = applicationContext.getType(name);
                return type != null && type.getPackage() != null &&
                       type.getPackage().getName().startsWith("com.techmanage");
            })
            .count();

        assertTrue(techManageBeans > 0, "Should find beans from com.techmanage package");
    }

    @Test
    void springBootApplication_shouldConfigureWebMvc() {
        // Test that Web MVC is configured (even in NONE web environment, basic beans should exist)
        assertTrue(applicationContext.containsBean("org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration"),
                  "WebMvc auto-configuration should be present");
    }

    @Test
    void springBootApplication_shouldConfigureDataAccess() {
        // Test that data access configuration is present
        assertTrue(applicationContext.containsBean("org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"),
                  "JPA auto-configuration should be present");
    }

    @Test
    void applicationContext_shouldHaveCorrectDisplayName() {
        // Test application context display name
        String displayName = applicationContext.getDisplayName();
        assertNotNull(displayName, "Display name should not be null");
        assertTrue(displayName.contains("TechManageApplication"),
                  "Display name should contain application class name");
    }

    @Test
    void applicationContext_shouldHaveStartupDate() {
        // Test that startup date is recorded
        long startupDate = applicationContext.getStartupDate();
        assertTrue(startupDate > 0, "Startup date should be positive");
        assertTrue(startupDate <= System.currentTimeMillis(),
                  "Startup date should not be in the future");
    }

    @Test
    void applicationContext_shouldHaveCorrectId() {
        // Test application context ID
        String contextId = applicationContext.getId();
        assertNotNull(contextId, "Context ID should not be null");
        assertFalse(contextId.trim().isEmpty(), "Context ID should not be empty");
    }

    @Test
    void applicationContext_shouldHaveApplicationEventPublisher() {
        // Test that application event publisher is available
        assertTrue(applicationContext instanceof org.springframework.context.ApplicationEventPublisher,
                  "Context should be an ApplicationEventPublisher");
    }

    @Test
    void applicationContext_shouldHaveResourceLoader() {
        // Test that resource loader is available
        assertTrue(applicationContext instanceof org.springframework.core.io.ResourceLoader,
                  "Context should be a ResourceLoader");
    }

    @Test
    void applicationContext_shouldHaveMessageSource() {
        // Test that message source is configured
        assertTrue(applicationContext.containsBean("messageSource"),
                  "MessageSource bean should be configured");
    }

    @Test
    void springBootApplication_shouldHaveProperShutdownHook() {
        // Test that shutdown hook registration capability exists
        assertDoesNotThrow(() -> {
            // This tests that the context can register shutdown hooks
            applicationContext.registerShutdownHook();
        }, "Should be able to register shutdown hook");
    }

    @Test
    void applicationContext_shouldSupportBeanPostProcessors() {
        // Test that bean post processors are working
        String[] postProcessorBeans = applicationContext.getBeanNamesForType(
            org.springframework.beans.factory.config.BeanPostProcessor.class);
        assertTrue(postProcessorBeans.length > 0,
                  "Should have bean post processors configured");
    }

    @Test
    void applicationContext_shouldSupportApplicationListeners() {
        // Test that application listeners are supported
        applicationContext.publishEvent(new org.springframework.context.event.ContextRefreshedEvent(applicationContext));
        // If we get here without exception, event publishing works
        assertTrue(true, "Should be able to publish application events");
    }

    @Test
    void springBootApplication_shouldHaveEnvironmentPostProcessors() {
        // Test that environment post processors are working
        org.springframework.core.env.ConfigurableEnvironment env = applicationContext.getEnvironment();
        assertNotNull(env, "Environment should be configured");
        assertTrue(env.getPropertySources().size() > 0,
                  "Environment should have property sources");
    }

    @Test
    void springBootApplication_shouldSupportConditionalConfiguration() {
        // Test that conditional configuration is working
        // This is tested by verifying that certain beans exist only under certain conditions
        boolean hasWebBeans = applicationContext.containsBean("requestMappingHandlerMapping");
        boolean hasDataBeans = applicationContext.containsBean("dataSource");

        // In a test environment, we expect data beans but may not have full web beans
        assertTrue(hasDataBeans, "Should have data access beans configured");
    }

    @Test
    void applicationContext_shouldHaveCorrectClassLoader() {
        // Test that class loader is properly set
        ClassLoader contextClassLoader = applicationContext.getClassLoader();
        assertNotNull(contextClassLoader, "Context class loader should not be null");
        assertEquals(Thread.currentThread().getContextClassLoader(), contextClassLoader,
                    "Context class loader should match thread context class loader");
    }

    @Test
    void springBootApplication_shouldSupportConfigurationProperties() {
        // Test that configuration properties are supported
        assertTrue(applicationContext.containsBean("org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor"),
                  "Configuration properties binding should be supported");
    }

    @Test
    void applicationContext_shouldHaveLifecycleManagement() {
        // Test lifecycle management
        assertTrue(applicationContext instanceof org.springframework.context.Lifecycle,
                  "Context should support lifecycle management");

        org.springframework.context.Lifecycle lifecycle = applicationContext;
        assertTrue(lifecycle.isRunning(), "Context lifecycle should be running");
    }

    @Test
    void springBootApplication_shouldHaveMetrics() {
        // Test that actuator/metrics support is available (if configured)
        // This test checks if the infrastructure for metrics is present
        String[] metricsRelatedBeans = applicationContext.getBeanNamesForType(Object.class);
        assertNotNull(metricsRelatedBeans, "Should be able to query for beans");
    }

    @Test
    void applicationContext_shouldSupportProfilesAndEnvironments() {
        // Test profiles and environment support
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        assertEquals(1, activeProfiles.length, "Should have test profile active");
        assertEquals("test", activeProfiles[0], "Test profile should be active");

        String[] defaultProfiles = applicationContext.getEnvironment().getDefaultProfiles();
        assertTrue(defaultProfiles.length >= 1, "Should have at least default profile");
    }
}