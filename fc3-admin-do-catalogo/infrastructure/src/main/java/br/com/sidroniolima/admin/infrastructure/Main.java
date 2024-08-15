package br.com.sidroniolima.admin.infrastructure;

import br.com.sidroniolima.admin.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "development");
        //System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class);
    }

    /*@Bean
    @DependsOnDatabaseInitialization
    ApplicationRunner runner(
            @Autowired CreateCategoryUseCase createCategoryUseCase,
            @Autowired UpdateCategoryUseCase updateCategoryUseCase,
            @Autowired DeleteCategoryUseCase deleteCategoryUseCase,
            @Autowired GetCategoryByIdUseCase getByIdCategoryUseCase,
            @Autowired ListCategoriesUseCase listCategoryUseCase
            ) {
        return args -> {};
    }*/
}