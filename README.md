# security-course-project

### In the current branch, implementing login form authentication and connection to REAL database - Postgres


##### How to create the Database?
After you have downloaded postgres to your machine, create an ApplicationUser table using the following code in your CLI:<br />

```sql  
CREATE TABLE application_user (
       username varchar(255) NOT NULL PRIMARY KEY,
        authorities bytea,
        is_account_non_expired BOOLEAN,
        is_account_non_locked BOOLEAN,
        is_credentials_non_expired BOOLEAN,
        is_enabled BOOLEAN,
        password VARCHAR(255)
    );
```
  <br />
  
 ##### How to initialize the Database?
In order to initialize the database with some default users, run the following code **ONE TIME** in the config file:
```java

@Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            ApplicationUser anna = new ApplicationUser("anna",
                    passwordEncoder.encode(password),
                    STUDENT.getGrantedAuthorities(),
                    true, true, true, true);
                    
            ApplicationUser linda = new ApplicationUser("tom",
                    passwordEncoder.encode("password2"),
                    ADMIN.getGrantedAuthorities(),
                    true, true, true, true);

            applicationUserPostgresRepository.save(anna);
            applicationUserPostgresRepository.save(linda);
        };
    }
```
