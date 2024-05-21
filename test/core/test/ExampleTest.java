import static org.junit.Assert.*;

import org.junit.*;

public class ExampleTest {
    @Test
    public void test_two_plus_two_is_four() {
        var result = 2 + 2;
        assertEquals(4, result);
    }

    @Test
    public void this_test_should_fail() {
        assert(false);
    }
}
