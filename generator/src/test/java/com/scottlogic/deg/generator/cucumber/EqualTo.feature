Feature: User can specify that a value is equalTo a required value

  # WHAT NOT TO DO - NOT THE WAY THE GENERATOR WORKS - VALUES ARE NOT SET


  Scenario:
    Given



  Scenario: User requires that the value is equalTo to string
    Given the following field exists:
      | forename  |
      | "Joe" |
    Then expect forename is equalTo "Joe"

  Scenario: User requires that value is equalTo to string OR string
    Given the following field exists:
      | forename  |
      | "Joe" |
      | "Paul" |
    Then expect forename is equalTo "Joe" or "Paul"

  Scenario: User requires that value is equalTo to integer
    Given the following field exists:
      | age  |
      | 21 |
    Then expect age is equalTo 21

  Scenario: User requires that value is equalTo to integer OR integer
    Given the following field exists:
      | age  |
      | 21 |
      | 32 |
    Then expect age is equalTo 21 or 32

