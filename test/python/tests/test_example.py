from unittest import TestCase, main

class ExampleTest(TestCase):
    def test_two_plus_two_is_four(self):
        number = 2
        number += 2
        assert(number == 4)

    def test_this_will_fail(self):
        assert(False)

if __name__ == "__main__":
    _ = main()

