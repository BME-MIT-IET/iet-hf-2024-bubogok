Feature: moving
  These scenarios describe moving around the map by a unit.

  Background:
    Given the map size is 5
    Given a red tank on 1, 1

  Scenario Outline: Neighbor move
    Given the red tank has 2 action points
    When the red tank tries to move to <x>, <y>
    Then the red tank is on <x>, <y>
    And the red tank has 1 action points left

    Examples:
      | x | y |
      | 1 | 0 |
      | 0 | 1 |
      | 1 | 2 |
      | 2 | 1 |


  Scenario Outline: Diagonal move
    Given the red tank has 2 action points
    When the red tank tries to move to <x>, <y>
    Then the red tank is on <x>, <y>
    And the red tank has 1 action points left

    Examples:
      | x | y |
      | 0 | 0 |
      | 2 | 2 |
      | 0 | 2 |
      | 2 | 0 |

  Scenario: No action point move
    Given the red tank has 0 action points
    When the red tank tries to move to 2, 1
    Then the red tank is on 1, 1
    And the red tank has 0 action points left

  Scenario: Non-neighboring move
    Given the red tank has 2 action points
    When the red tank tries to move to 1, 3
    Then the red tank is on 1, 1
    And the red tank has 2 action points left

  Scenario: Out of bounds move
    Given the red tank has 2 action points
    When the red tank tries to move to 1, 5
    Then the red tank is on 1, 1
    And the red tank has 2 action points left
