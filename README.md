# ComparusTestProject

### Documentation

The project contains two branches with slightly different solutions for the given task:
  * JDBC-prototype(low level solution, that uses plain jdbc without any ORM framework)
  * SpringDataJpa-prototype, that is also merged to master branch(high-level solution, that involves SpringDataJpa framework, AOP in order to aggregate data from different data sources)

To ensure that the application works correctly, first of all, you must create DataBases, tables with the same schema, and users to operate with those tables and fill tables with data.
You may use the following scripts as an example:

```
CREATE TABLE user_table (  
    id UUID PRIMARY KEY,  
    username VARCHAR(255) UNIQUE,  
    first_name VARCHAR(255), 
	surname VARCHAR(255)
);

CREATE USER test_user_1 WITH PASSWORD 'Test1';

GRANT ALL PRIVILEGES ON TABLE public.user_table TO test_user_1;

INSERT INTO public.user_table(
	id, username, first_name, surname)
	VALUES ('322fd4ab-0973-43f5-bb8c-b5123adbbe03', 'TestUsername1', 'TestFirstName1', 'TestSurname1');
```

You may run the application after compiling via the following command
```
java -Dlogging.config='path to logback configuration file' -jar test-comparus-project-0.0.1-SNAPSHOT.jar
```
The example application.yaml and logback.xml are included in the repository.
