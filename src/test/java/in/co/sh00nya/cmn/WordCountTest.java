package in.co.sh00nya.cmn;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class WordCountTest {
	
	@Test
	public void testSuccess() {
		Assert.assertEquals(WordCountUtil.countWord("this is a small line."), 5);
	}
	
	@Test
	public void nullSet() {
		Assert.assertEquals(WordCountUtil.countWord("this is a small line.", null), 0);
	}
	
	@Test
	public void nonEmptySet() {
		Set<String> nonEmptySet = new HashSet<String>();
		nonEmptySet.add("one");
		Assert.assertEquals(WordCountUtil.countWord("this is a small line.", nonEmptySet), 5);
	}
	
	@Test
	public void twoSentenceTests() {
		Assert.assertEquals(WordCountUtil.countWord("this is a small line. another one coming in."), 9);
	}

	@Test
	public void emptyLine() {
		Assert.assertEquals(WordCountUtil.countWord(""), 0);
	}
	
	@Test
	public void nullLine() {
		Assert.assertEquals(WordCountUtil.countWord(null), 0);
	}
}
