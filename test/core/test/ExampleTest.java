import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ExampleTest {
	@Test
	void test_two_plus_two_is_four() {
		var result = 2 + 2;
		Assertions.assertEquals(4, result);
	}
}
