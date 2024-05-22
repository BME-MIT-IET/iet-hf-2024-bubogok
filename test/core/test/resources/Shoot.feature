Feature: Shooting
  These scenarios describe how units interacts with each other while shooting.

  Background:
    Given the map size is 40

  Scenario:
    Given a red tank on 1, 0
    And a white infantry on 1, 1
    And the white infantry has 1 ammo
    When the white infantry shoots the red tank
    Then the white infantry has 0 ammo left

  Scenario:
    Given a red tank on 1, 0
    And a white infantry on 1, 1
    And the white infantry has 0 ammo
    When the white infantry shoots the red tank
    Then the white infantry has 0 ammo left
    And the red tank is 0 below full health

  Scenario:
    Given a red tank on 1, 0
    And a white infantry on 1, 1
    When the red tank shoots the white infantry
    Then the white infantry is 30 below full health
    And the white infantry dies

  Scenario:
    Given a red tank on 1, 0
    And a red infantry on 1, 1
    When the red tank shoots the red infantry
    Then the red infantry is 30 below full health
    And the red infantry dies

  Scenario:
    Given a red tank on 1, 0
    And a white infantry on 1, 1
    When the white infantry shoots the red tank
    Then the red tank is 10 below full health
    But the red tank stays alive
