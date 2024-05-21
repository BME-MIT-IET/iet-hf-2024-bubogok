from unittest import TestCase, main

import example_testable as example

class ExampleTest(TestCase):
    def test_two_plus_two_is_four(self):
        result = example.add(2, 2)
        assert(result == 4)

    def test_two_times_two_is_four(self):
        result = example.mul(2, 2)
        assert(result == 4)

    def test_minus_two_absolute_is_two(self):
        result = example.abs(-2)
        assert(result == 2)

if __name__ == "__main__":
    _ = main()

