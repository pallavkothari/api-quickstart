package com.pk;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

    private final MetricRegistry metricRegistry;
    private final HealthCheckRegistry healthCheckRegistry;

    @Autowired
    public MetricsConfig(MetricRegistry metricRegistry, HealthCheckRegistry healthCheckRegistry) {
        this.metricRegistry = metricRegistry;
        this.healthCheckRegistry = healthCheckRegistry;
    }

    @PostConstruct
    public void init() {
        configureReporters(metricRegistry);
        healthCheckRegistry.register("cpuLoad", new CpuHealthCheck());
    }


    @Bean
    public MetricsServletContextListener metricsServletContextListener(MetricRegistry metricRegistry, HealthCheckRegistry healthCheckRegistry) {
        return new MetricsServletContextListener(metricRegistry, healthCheckRegistry);
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        // registerReporter allows the MetricsConfigurerAdapter to
        // shut down the reporter when the Spring context is closed
        registerReporter(ConsoleReporter
                .forRegistry(metricRegistry)
                .build())
                .start(1, TimeUnit.MINUTES);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new AdminServlet(),"/dropwizard/*");
    }

    public static final class CpuHealthCheck extends HealthCheck {

        @Override
        protected Result check() throws Exception {
            double cpuLoad = getProcessCpuLoad();
            return cpuLoad == Double.NaN || cpuLoad < 80 ? Result.healthy() : Result.unhealthy("cpu: " + cpuLoad);
        }
    }

    @SneakyThrows
    public static double getProcessCpuLoad() {
        MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
        ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });
        if (list.isEmpty())     return Double.NaN;

        Attribute att = (Attribute)list.get(0);
        Double value  = (Double)att.getValue();

        // usually takes a couple of seconds before we get real values
        if (value == -1.0)      return Double.NaN;
        // returns a percentage value with 1 decimal point precision
        return ((int)(value * 1000) / 10.0);
    }


    private static class MetricsServletContextListener implements ServletContextListener {

        private MetricRegistry metricRegistry;
        private HealthCheckRegistry healthCheckRegistry;

        MetricsServletContextListener(MetricRegistry metricRegistry, HealthCheckRegistry healthCheckRegistry) {
            this.metricRegistry = metricRegistry;
            this.healthCheckRegistry = healthCheckRegistry;
        }

        @Override
        public void contextInitialized(ServletContextEvent servletContextEvent) {
            servletContextEvent.getServletContext().setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY,healthCheckRegistry);
            servletContextEvent.getServletContext().setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry);
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {

        }
    }
}
