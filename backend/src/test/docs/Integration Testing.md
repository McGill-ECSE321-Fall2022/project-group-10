# Integration Testing Guide

## File Structure
- Scenarios go in src/test/resources/features
- Step definitions go in src/test/java/ca/mcgill/ecse321/museum/integration

## CucumberSpringConfig Guide
- @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) allows us to Autowire TestRestTemplate
- @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) clears the database after every scenario.

## Feature Writing Guide
- For now, all scenarios related to an endpoint with the same root path (e.g., /artworks) are in the same feature file.
- Treat each scenario as isolated, i.e., whatever changes you do in a previous scenario do not carry over to the next
scenario. This was done so that the order that the scenarios are in does not matter.
- **Background** allows you to have Given statements that are run every scenario. The database changes that a Background does are 
normally carried over between scenarios, but because I modified the @DirtiesContext, this is not the case anymore, and it
can therefore be treated as a way to initialize the database.

## Step Definition Writing Guide
- **TO SPECIFY: FILE STRUCTURE** For now, everything in the ArtworkStepDefinitions either uses the ArtworkRepository, or
makes an API call to urls with root path /artworks. We can continue this I guess?
- For Given statements, they usually need to modify the database directly, so I use the repository.
- For When statements, they are requests to a certain endpoint formatted as such.
```java
        // I have not used headers so I just initialize this as such
        HttpEntity requestEntity = new HttpEntity<>(null, new HttpHeaders());

        // Params:
        // "endpoint" is the endpoint to be used e.g., "/artworks:
        // HttpMethod can be HttpMethod.GET, HttpMethod.POST, etc., and it is the HttpMethod you need to use
        // requestEntity is the one you defined above
        // new ParameterizedTypeReference<Object>() is what the endpoint returns. So 
        // it should be new ParameterizedTypeReference<List<ArtworkDto>>() if the call returns List<ArtworkDto> 
        var response = client.exchange("endpoint", HttpMethod.[], requestEntity, new ParameterizedTypeReference<Object>() {
        });
        
        // Define these as global variables in your class and set them here
        // But make an @Before function that resets them
        
        // This is the object that your method returns
        // If you call a void method, e.g., a DELETE, you should not set this
        object = response.getBody();
        
        // This is the status code of the response
        httpStatus = response.getStatusCode();
```
- For Then statements, they compare a datatable defined in the scenario with a state variable that was set in a When
statement. They need to be the ones that assert things.

