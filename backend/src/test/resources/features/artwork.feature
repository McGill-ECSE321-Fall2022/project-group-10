Feature: Artwork
  Background:
    Given the following artworks exist:
      |title |author     |creationDate|description                 |imageLink     |price|isAvailable|
      |Test  |Test Author|2022-02-02  |This is a test artwork      |   test.com   |12   |    true   |
      |Hello |Joe Biden  |2021-05-12  |America yeet                |   usa.com    |3.50 |    false  |
  Scenario: getArtwork

  Scenario: getArtworks
    When a GET request to artworks is made
    Then the following artworks are returned:
      |id |title |author     |creationDate|description                 |imageLink     |price|isAvailable|
      | 1 |Test  |Test Author|2022-02-02  |This is a test artwork      |   test.com   |12   |    true   |
      | 2 |Hello |Joe Biden  |2021-05-12  |America yeet                |   usa.com    |3.50 |    false  |
    Then an HTTP Status of "200" is returned
  Scenario: deleteArtwork

  Scenario: deleteArtwork fails

  Scenario: createArtwork

  Scenario: createArtwork fails

  Scenario: moveArtworkToRoom

  Scenario: moveArtworkToRoom fails
