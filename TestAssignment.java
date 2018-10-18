import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import textbook.LinkedBinaryTree;

public class TestAssignment {
	
	// Set up JUnit to be able to check for expected exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Some simple testing of prefix2tree
	@Test(timeout = 100)
	public void testPrefix2tree() {
		
		LinkedBinaryTree<String> tree;

		tree = Assignment.prefix2tree("hi");
		assertEquals(1, tree.size());
		assertEquals("hi", tree.root().getElement());

		tree = Assignment.prefix2tree("+ 5 10");
		assertEquals(3, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("- 5 10");
		assertEquals(3, tree.size());
		assertEquals("-", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("* 5 10");
		assertEquals(3, tree.size());
		assertEquals("*", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
				
		tree = Assignment.prefix2tree("+ 5 - 4 3");
		assertEquals(5, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("-", tree.right(tree.root()).getElement());
		assertEquals("4", tree.left(tree.right(tree.root())).getElement());
		assertEquals("3", tree.right(tree.right(tree.root())).getElement());
		
		thrown.expect(IllegalArgumentException.class);
		tree = Assignment.prefix2tree("+ 5 - 4");
	}
	
	// example of using the Assignment.equals method to check that "- x + 1 2" simplifies to "- x 3"
	@Test(timeout = 100)
	public void testSimplify1() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- x + 1 2");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("- x 3");
		assertTrue(Assignment.equals(tree, expected));
	}
	//check the prefix
	@Test(timeout = 100)
	public void testprefix(){
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("- + 2 15 4");
		assertEquals("- + 2 15 4",Assignment.tree2prefix(expected));	
		}
	
	//check the deep prefix
		@Test(timeout = 100)
		public void testdeepprefix(){
			LinkedBinaryTree<String> expected=Assignment.prefix2tree("- + 2 15 * 3 4");
			assertEquals("- + 2 15 * 3 4",Assignment.tree2prefix(expected));	
			}
		//check more corner prefix
		@Test(timeout=100)
		public void testcornerprefix2(){
			LinkedBinaryTree<String> a=Assignment.prefix2tree("* * * 0 a 2 + 1 - 8 * 2 6");
			assertEquals("* * * 0 a 2 + 1 - 8 * 2 6",Assignment.tree2prefix(a));
		}
		
	@Test(timeout = 100)
	public void deepsimplify(){
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("+ - x 1 * + 1 1 - 5 2");
	    expected=Assignment.simplify(expected);
	    LinkedBinaryTree<String> tree=Assignment.prefix2tree("+ - x 1 6");
	    assertTrue(Assignment.equals(tree,expected));
		
	}
	@Test(timeout = 100)
	//this is to test tree with deep depth and combined with 0 1 a simplify cases all
	public void testSimplifyFancy1(){
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("+ 0 * 1 - 0 a");
		expected=Assignment.simplifyFancy(expected);
		LinkedBinaryTree<String> tree=Assignment.prefix2tree("a");
		assertTrue(Assignment.equals(tree,expected));			
	}
	//check if the input variable is number
	@Test(timeout = 100)
	public void testSubstitueHashmapthorwvariblenumberException(){
	
		HashMap<String,Integer> map=new HashMap<String,Integer>();
        map.put("c",1);
        map.put("2", 2);
        LinkedBinaryTree<String> tree=Assignment.prefix2tree("+ 1 2");
        thrown.expect(IllegalArgumentException.class);
		tree=Assignment.substitute(tree, map);
       
  
	}
	//check invalid expression
	@Test(timeout = 100)
	public void testIsArithemtericExpression(){
		 thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree=Assignment.prefix2tree("+ + + + + 1 2");
	}
	
	//check the tree infix method
	@Test(timeout = 100)
	public void testinfix(){
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("- + 2 15 4");
		assertEquals("((2+15)-4)",Assignment.tree2infix(expected));
	}
	
	//check for the deep treeinfix
	@Test(timeout = 100)
	public void testdeepinfix(){
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("- + 2 15 * 3 4");
		assertEquals("((2+15)-(3*4))",Assignment.tree2infix(expected));
	}
	
	//check the basic subsitute
	@Test(timeout = 100)
	public void testbasicsubsitute(){
		LinkedBinaryTree<String> origin=Assignment.prefix2tree("- + a 15 * c 4");
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("- + 3 15 * 7 4");
		origin=Assignment.substitute(origin, "a", 3);
		origin=Assignment.substitute(origin, "c", 7);
		assertTrue(Assignment.equals(origin,expected));
	}
	
	//check if the first substitue can throw Expcetion when the varible is a number;
	@Test(timeout = 100)
	public void testSubstituethorwvariblenumberException(){
		LinkedBinaryTree<String> origin=Assignment.prefix2tree("- + a 15 * c 4");
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("- + 3 15 * 7 4");
		 thrown.expect(IllegalArgumentException.class);
		origin=Assignment.substitute(origin, "15", 3);
	}
	
	//check more corner simplifyfancy
	@Test(timeout = 100)
	public void  testcornersimpliy2(){
		LinkedBinaryTree<String> a=Assignment.prefix2tree("* * * 0 2 2 + 1 2");
		LinkedBinaryTree<String> b=Assignment.prefix2tree("0");
		a=Assignment.simplifyFancy(a);
		assertTrue(Assignment.equals(a, b));
		
	}
	
	//check more corner infix
	@Test(timeout=100)
	public void testcornerinfix(){
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("* * * 0 2 2 + 1 2");
		assertEquals("(((0*2)*2)*(1+2))",Assignment.tree2infix(expected));
	}
	//check more corner susbstitution of negative integer
	@Test(timeout=100)
	public void testcornersubstitution(){
		LinkedBinaryTree<String> origin=Assignment.prefix2tree("- + a 15 * c 4");
		LinkedBinaryTree<String> expected=Assignment.prefix2tree("- + -3 15 * -7 4");
		origin=Assignment.substitute(origin, "a", -3);
		origin=Assignment.substitute(origin, "c", -7);
		assertTrue(Assignment.equals(origin, expected));
	}
	
	//check more simplifyfancy corner case
@Test(timeout=100)
public void testsimplifycornercase1(){
	LinkedBinaryTree<String> a=Assignment.prefix2tree("- - 2 1 - 2 1");
	LinkedBinaryTree<String> b=Assignment.prefix2tree("0");
	a=Assignment.simplifyFancy(a);
	assertTrue(Assignment.equals(a, b));
}
}
