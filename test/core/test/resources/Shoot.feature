Feature: Shooting
  #TODO: expand further on what this feature is

  Scenario: shoot
    Given the infantry is on the field
    When a tank shoots on the field
    Then the infantry takes 30 damage

  Scenario: shoot2
    Given the infantry is on the field
    When a tank shoots on the field
    Then the infantry takes 20 damage
