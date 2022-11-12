Feature: Artwork Endpoints

  Background:
    Given the following artworks exist:
      |title |author     |creationDate|description                 |imageLink     |price|isAvailable|
      |Test  |Test Author|2022-02-02  |This is a test artwork      |   test.com   |12   |    true   |
      |Hello |Joe Biden  |2021-05-12  |America yeet                |   usa.com    |3.50 |    false  |

  Scenario: getArtwork
    When a GET request to artworks."1" is made
    Then the following artwork is returned:
      |title |author     |creationDate|description                 |imageLink     |price|isAvailable|
      |Test  |Test Author|2022-02-02  |This is a test artwork      |   test.com   |12   |    true   |

  Scenario: getArtworks
    When a GET request to artworks is made
    Then the following artworks are returned:
      |id |title |author     |creationDate|description                 |imageLink     |price|isAvailable|
      | 1 |Test  |Test Author|2022-02-02  |This is a test artwork      |   test.com   |12   |    true   |
      | 2 |Hello |Joe Biden  |2021-05-12  |America yeet                |   usa.com    |3.50 |    false  |
    Then an HTTP Status of "200" is returned

  Scenario: deleteArtwork
    When a DELETE request to artworks."1" is made
    Then the artwork with id "1" shall not exist in the system
    Then an HTTP Status of "200" is returned

  Scenario: deleteArtwork fails
    When a DELETE request to artworks."5" is made
    Then an HTTP Status of "403" is returned

  Scenario: createArtwork
    When a POST request to "artworks" is made with body:
      |title    |author     |creationDate|description                                 |imageLink     |price|isAvailable|
      |Posties  |Postman Pat|2022-05-05  |Postman Pat and his black and white cat     |   test.com   |129  |    false  |
    Then the following artwork shall exist in the system:
      |id|title    |author     |creationDate|description                                 |imageLink     |price|isAvailable|
      |3 |Posties  |Postman Pat|2022-05-05  |Postman Pat and his black and white cat     |   test.com   |129  |    false  |

  Scenario: createArtwork fails
    When a POST request to "artworks" is made with body:
      |title    |author     |creationDate|description                                 |imageLink     |price|isAvailable|
      |Single   |Kim K's Ex |no dates    |This person has no dates                    |   test.com   |129  |    false  |
  Scenario: moveArtworkToRoom

  Scenario: moveArtworkToRoom fails
