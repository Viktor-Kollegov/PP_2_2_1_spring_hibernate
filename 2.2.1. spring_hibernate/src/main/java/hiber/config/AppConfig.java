package hiber.config;

import hiber.model.Car;
import hiber.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "hiber")  // ПОЧЕМУ ОН В ЭТОМ КЛАССЕ, А НЕ В MainApp????
public class AppConfig {

    //@Autowired
    private Environment env;  //ОТКУДА ПОДВЯЗЫВАЕТСЯ ЭТОТ БИН??????????????????? из самого окружения приложения

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() { //ГДЕ У НАС ИСПОЛЬЗУЕТСЯ ДАННЫЙ БИН????? для получения сессии через подтягивание гибернейтом и jpa нашей реализации
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean // ТИПО ПОТОКОБЕЗОПАСНАЯ ЛОКАЛЬНАЯ СЕССИЯ??? ко второму ревью
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());

        Properties props = new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        factoryBean.setHibernateProperties(props);
        factoryBean.setAnnotatedClasses(User.class, Car.class);
        return factoryBean;
    }

    @Bean  // ЭТОТ БИН НУЖЕН ДЛЯ РАБОТЫ АННОТАЦИЙ @TRANSACTIONAL ИЛИ ДЛЯ ЧЕГО-ТО ЕЩЁ??? ко второму ревью
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
