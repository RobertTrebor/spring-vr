package de.lengsfeld.apps.vr;

/*
@Configuration
@EnableJpaRepositories(basePackages = "de.lengsfeld.apps.vr.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")*/
public class JpaConfiguration {
/*
    @Autowired
    private Environment environment;

    @Value("${datasource.vr.maxPoolSize:10}")
    private int maxPoolSize;

    *//*
     * Populate SpringBoot DataSourceProperties object directly from application.yml
     * based on prefix.Thanks to .yml, Hierachical data is mapped out of the box with matching-name
     * properties of DataSourceProperties object].
     *//*
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.vr")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    *//*
     * Configure HikariCP pooled DataSource.
     *//*
    @Bean
    public DataSource dataSource() {
        DataSourceProperties dataSourceProperties = dataSourceProperties();
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
                .create(dataSourceProperties.getClassLoader())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(maxPoolSize);
        return dataSource;
    }

    *//*
     * Entity Manager Factory setup.

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[] { "de.lengsfeld.apps.vr.entity" });
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }
*//*
    *//*
     * Provider specific adapter.
     *//*
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
    }

    *//*
     * Here you can specify any provider specific properties.
     *//*
    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.vr.hibernate.dialect"));
        //properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.vr.hibernate.hbm2ddl.method"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.vr.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.vr.hibernate.format_sql"));
        if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.vr.defaultSchema"))) {
            properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.vr.defaultSchema"));
        }
        return properties;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
    */
}
