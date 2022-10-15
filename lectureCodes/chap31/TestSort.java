import org.junit.Test;

public class TestSort {
    /** Tests the sort method of the Sort class. */
    @Test
    public void testSort() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "egg", "have", "i"};
        Sort.sort(input);

        org.junit.Assert.assertArrayEquals(expected,input);

    }

    @Test
    public void testFindSmallest() {
        String[] input = {"i", "have", "an", "egg"};
        String expected = "an";

        int actual = Sort.findSmallest(input, 0);
        System.out.println(Sort.findSmallest(input, 0));
        org.junit.Assert.assertEquals(expected, input[actual]);
    }
    @Test
    public void testSwap() {
        String[] input = {"i", "have", "an", "egg"};
        int a = 0;
        int b = 2;
        String[] expected = {"an", "have", "i", "egg"};

        Sort.swap(input, a, b);
        org.junit.Assert.assertArrayEquals(expected, input);
    }
//    public static void main(String[] args) {
//        testSwap();
//        testFindSmallest();
//        testSort();
//    }
}