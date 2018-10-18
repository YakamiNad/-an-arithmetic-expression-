
import java.util.HashMap;
import textbook.LinkedBinaryTree;
import textbook.LinkedQueue;
import textbook.Position;
import java.util.*;

public class Assignment{
   
    
	/**
	 * Convert an arithmetic expression (in prefix notation), to a binary tree
	 * 
	 * Binary operators are +, -, * (i.e. addition, subtraction, multiplication)
	 * Anything else is assumed to be a variable or numeric value
	 * 
	 * Example: "+ 2 15" will be a tree with root "+", left child "2" and right
	 * child "15" i.e. + 2 15
	 * 
	 * Example: "+ 2 - 4 5" will be a tree with root "+", left child "2", right
	 * child a subtree representing "- 4 5" i.e. + 2 - 4 5
	 * 
	 * This method runs in O(n) time
	 * 
	 * @param expression	
	 *            - an arithmetic expression in prefix notation
	 * @return BinaryTree representing an expression expressed in prefix
	 *         notation
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	public static LinkedBinaryTree<String> prefix2tree(String expression) throws IllegalArgumentException {
		if (expression == null) {
			throw new IllegalArgumentException("Expression string was null");
		}
		
		// break up the expression string using spaces, into a queue
		LinkedQueue<String> tokens = new LinkedQueue<String>();
		for (String token : expression.split(" ")) {
			tokens.enqueue(token);
		}
		// recursively build the tree
		return prefix2tree(tokens);
	}
  
	/**
	 * Recursive helper method to build an tree representing an arithmetic
	 * expression in prefix notation, where the expression has already been
	 * broken up into a queue of tokens
	 * 
	 * @param tokens
	 * @return
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	
	private static LinkedBinaryTree<String> prefix2tree(LinkedQueue<String> tokens) throws IllegalArgumentException {
		LinkedBinaryTree<String> tree = new LinkedBinaryTree<String>();

		// use the next element of the queue to build the root
		if (tokens.isEmpty()){
			throw new IllegalArgumentException("String was not a valid arithmetic expression in prefix notation");
		}
		String element = tokens.dequeue();
		tree.addRoot(element);

		// if the element is a binary operation, we need to build the left and
		// right subtrees
		if (element.equals("+") || element.equals("-") || element.equals("*")) {
			LinkedBinaryTree<String> left = prefix2tree(tokens);
			LinkedBinaryTree<String> right = prefix2tree(tokens);
			tree.attach(tree.root(), left, right);
		}
		// otherwise, assume it's a variable or a value, so it's a leaf (i.e.
		// nothing more to do)

		return tree;
	}
	
	/**
	 * Test to see if two trees are identical (every position in the tree stores the same value)
	 * 
	 * e.g. two trees representing "+ 1 2" are identical to each other, but not to a tree representing "+ 2 1"
	 * @param a
	 * @param b
	 * @return true if the trees have the same structure and values, false otherwise
	 */
	public static boolean equals(LinkedBinaryTree<String> a, LinkedBinaryTree<String> b) {
		return equals(a, b, a.root(), b.root());
	}

	/**
	 * Recursive helper method to compare two trees
	 * @param aTree one of the trees to compare
	 * @param bTree the other tree to compare
	 * @param aRoot a position in the first tree
	 * @param bRoot a position in the second tree (corresponding to a position in the first)
	 * @return true if the subtrees rooted at the given positions are identical
	 */
	private static boolean equals(LinkedBinaryTree<String> aTree, LinkedBinaryTree<String> bTree, Position<String> aRoot, Position<String> bRoot) {
		//if either of the positions is null, then they are the same only if they are both null
		if(aRoot == null || bRoot == null) {
			return (aRoot == null) && (bRoot == null);
		}
		//first check that the elements stored in the current positions are the same
		String a = aRoot.getElement();
		String b = bRoot.getElement();
		if((a==null && b==null) || a.equals(b)){
			//then recursively check if the left subtrees are the same...
			boolean left = equals(aTree, bTree, aTree.left(aRoot), bTree.left(bRoot));
			//...and if the right subtrees are the same
			boolean right = equals(aTree, bTree, aTree.right(aRoot), bTree.right(bRoot));
			//return true if they both matched
			return left && right;
		}
		else {
			return false;
		}
	}

	
	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in prefix notation, without (parenthesis) (also
	 * known as Polish notation)
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "+ 2 15" Example: A tree with root "-", left child a subtree
	 * representing "(2+15)" and right child "4" would be "- + 2 15 4"
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return prefix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 //public static String tree2prewfixhelp(LinkedBInaryTree<String> tree.Position)
	 */
	public static String tree2prefixhelp(LinkedBinaryTree<String> tree1,Position<String> root,String b){
		if (root.getElement().equals("+") || root.getElement().equals("-") || root.getElement().equals("*")) {
		b=b+root.getElement()+" ";
		 
		 String lt=tree2prefixhelp(tree1,tree1.left(root),"");
			// 
		 String rt=tree2prefixhelp(tree1,tree1.right(root),"");
		b=b+lt+" " +rt;}
		else{
			b=b+root.getElement();
		}			
		
		return b;
	}
	public static String tree2prefix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
		String b="";
		if(isArithmeticExpression(tree)==false){ 
			throw new IllegalArgumentException();
		}
		else{ 
		
		return tree2prefixhelp(tree,tree.root(),b);
	}
	}

	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in infix notation with parenthesis (i.e. the most
	 * commonly used notation).
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "(2+15)"
	 * 
	 * Example: A tree with root "-", left child a subtree representing "(2+15)"
	 * and right child "4" would be "((2+15)-4)"
	 * 
	 * Optionally, you may leave out the outermost parenthesis, but it's fine to
	 * leave them on. (i.e. "2+15" and "(2+15)-4" would also be acceptable
	 * output for the examples above)
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return infix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static String tree2infixhelp(LinkedBinaryTree<String> tree1,Position<String> root) throws IllegalArgumentException{
		String b="";
       if(tree1.left(root)!=null){
		   b=b+"("+tree2infixhelp(tree1,tree1.left(root));
	   }
		if(root!=null){
			b=b+root.getElement();
		}
		if(tree1.right(root)!=null){
			b=b+tree2infixhelp(tree1,tree1.right(root))+")";
			
		}
		return b;
	}
	public static String tree2infix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
		
		if(isArithmeticExpression(tree)==false){ 
			throw new IllegalArgumentException();
		}
		else{ 
		
		return tree2infixhelp(tree,tree.root());
	}
		
	}

	/**
	 * Given a tree, this method should simplify any subtrees which can be
	 * evaluated to a single integer value.
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after evaluating as many of the subtrees as
	 *         possible
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	
	public static boolean hnumber(Position<String> root) {
    try { 
        Integer.parseInt(root.getElement()); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
	
    // only got here if we didn't return false
    return true;
}
	public static int helpadd(String a,int b,int c){
		int d=0;
		if(a.equals("+")){
			 d=b+c;
		}
		if(a.equals("-")){
			d= b-c;
		}
		if(a.equals("*")){
			d= b*c;
		}
		return d; 
	}

	public static LinkedBinaryTree<String> helpsimp(LinkedBinaryTree<String> tree,Position<String> root) throws IllegalArgumentException{
		if(tree.left(root)==null && tree.right(root)==null){
			return tree;}
			
		
			   helpsimp(tree,tree.left(root));
               helpsimp(tree,tree.right(root)); 
			  if(hnumber(tree.left(root)) && hnumber(tree.right(root))){
				String a=root.getElement();
				int b=Integer.parseInt(tree.left(root).getElement());
				int c=Integer.parseInt(tree.right(root).getElement());
				int d=helpadd(a,b,c);
				String e=d+"";
				tree.remove(tree.left(root));
				tree.remove(tree.right(root));
				tree.set(root,e);		
			}
		
		return tree;
		
	}
	public static LinkedBinaryTree<String> simplify(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
		if(isArithmeticExpression(tree)==false){
			throw new IllegalArgumentException();
		}
		else{
			return helpsimp(tree,tree.root());
		}
		
	}

	/**
	 * This should do everything the simplify method does AND also apply the following rules:
	 *  * 1 x == x  i.e.  (1*x)==x
	 *  * x 1 == x        (x*1)==x
	 *  * 0 x == 0        (0*x)==0
	 *  * x 0 == 0        (x*0)==0
	 *  + 0 x == x        (0+x)==x
	 *  + x 0 == 0        (x+0)==x
	 *  - x 0 == x        (x-0)==x
	 *  - x x == 0        (x-x)==0
	 *  
	 *  Example: - * 1 x x == 0, in infix notation: ((1*x)-x) = (x-x) = 0
	 *  
	 * Ideally, this method should run in O(n) time (harder to achieve than for other methods!)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after applying the simplifications
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static LinkedBinaryTree<String> simplifyFancy(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
	if(isArithmeticExpression(tree)==false){ 
		throw new IllegalArgumentException();
	}
	else{
		LinkedBinaryTree<String> a=simplify(tree);
		return fancyhelp(a,a.root());
	}
		 
		
}
	// this general method is just to avoid too much code on the screen,
	//which is just to get a value with the String operation and two corrsponding numbers 
	public static String fancyzero(String a,int b,String c){ 
		String e="";
		if(a.equals("+")||a.equals("-")){
			e=c;
		}
		if(a.equals("*")){ 
	   e="0";
	 }
		return e;
	}
	// this general method is just to  check when one chile is "1",if the parent is "*",if so return true so that we can do the remove method to child and parent
	public static boolean fancyone(String a){
		if(a.equals("*")){
			return true;
		}
		return false;
	}
	public static boolean isOperator(String a){
		if(a.equals("+")||a.equals("-")||a.equals("*")){
			return true;
		}
		return false;
	}
  // this method covers the possible base case which to simplify the tree as much as possible
  // same as the helpsimp method,it use the top-bottom recursion to do the operations so as to avoid the error that validate(p) can throws. 
	public static LinkedBinaryTree<String> fancyhelp(LinkedBinaryTree<String> tree,Position<String> root) throws IllegalArgumentException{
		if(tree.left(root)!=null && tree.right(root)!=null){
			//do the recursion from top,when it finishes the right subtree,it will do fancyhelp(tree,tree.left(root)),which can go through the right sub-tree
		   fancyhelp(tree,tree.right(root));
		   fancyhelp(tree,tree.left(root));
			//the following is the basecases 
			//i only do remove method when there is no operation symbol in the two child of a root 
			//if opertion symbol found in the two child of a root,simply go through another recursion from the symbol,which is actully is the same running time if you are still in  the previous recursion,
			//both recursion has to go through all the root
			
		if(tree.left(root).getElement().equals("0")&& hnumber(tree.right(root))==false&& (isOperator(tree.right(root).getElement()))==false){
			String a=tree.right(root).getElement();
			String b=root.getElement();
			String c=fancyzero(b,0,a);
			tree.remove(tree.left(root));
			tree.remove(tree.right(root));
			tree.set(root,c);
		}
		else if(tree.right(root).getElement().equals("0")&& hnumber(tree.left(root))==false&& (isOperator(tree.right(root).getElement()))==false){
			String a=tree.left(root).getElement();
			String b=root.getElement();
			String c=fancyzero(b,0,a);
			tree.remove(tree.left(root));
			tree.remove(tree.right(root));
			tree.set(root,c);	
	}
		else if(tree.left(root).getElement().equals("1") && hnumber(tree.right(root))==false && (isOperator(tree.right(root).getElement()))==false){
			String a=tree.right(root).getElement();
			String b=root.getElement();
			if(fancyone(b)){
			tree.remove(tree.left(root));
			tree.remove(tree.right(root));
			tree.set(root,a);
				
			}
			
		}
		else if(tree.right(root).getElement().equals("1") &&hnumber(tree.left(root))==false&& (isOperator(tree.left(root).getElement()))==false){
			String a=tree.left(root).getElement();
			String b=root.getElement();
			if(fancyone(b)){
			tree.remove(tree.left(root));
			tree.remove(tree.right(root));
			tree.set(root,a);	
			}	
		}
		else if( isOperator(tree.left(root).getElement())){
			fancyhelp(tree,tree.left(root));
		}
		else if(  isOperator(tree.right(root).getElement())){
			fancyhelp(tree,tree.right(root));
		}
		else if(hnumber(tree.left(root))==false&& isOperator(tree.left(root).getElement())==false&&hnumber(tree.right(root))==false&& isOperator(tree.right(root).getElement())==false){
			tree.remove(tree.left(root));
			tree.remove(tree.right(root));
			tree.set(root,"0");
		 }
		}
			return tree;
	}
		
	
		
				
				
	/**
	 * Given a tree, a variable label and a value, this should replace all
	 * instances of that variable in the tree with the given value
	 * 
	 * Ideally, this method should run in O(n) time (quite hard! O(n^2) is easier.)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param variable
	 *            - a variable label that might exist in the tree
	 * @param value
	 *            - an integer value that the variable represents
	 * @return Tree after replacing all instances of the specified variable with
	 *         it's numeric value
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or either of the other
	 *             arguments are null
	 */
	
	
	public static LinkedBinaryTree<String> shelp(LinkedBinaryTree<String> tree,Position<String> root,String varible,int value){
		if(tree.numChildren(root)==0){
			return tree;
		}
		else{
			
			if(tree.left(root).getElement().equals(varible)){
			String a=""+value;
			tree.remove(tree.left(root));
			
			tree.addLeft(root,a);
			shelp(tree,tree.left(root),varible,value);
			shelp(tree,tree.right(root),varible,value);}
		
			
			if(tree.right(root).getElement().equals(varible)){
			String a=""+value;
			tree.remove(tree.right(root));
			tree.addRight(root,a);
			shelp(tree,tree.left(root),varible,value);
			shelp(tree,tree.right(root),varible,value);}
		}
		//the above two if are the basecase to check whether we find the variable we have to change
		     
			voidhelp(tree,tree.left(root),varible,value);
			voidhelp(tree,tree.right(root),varible,value);
			
		return tree;
	}
		
	
	public static boolean ifInt(String a) {
    try { 
        Integer.parseInt(a); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
		return true;
	}
		  
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, String varible, int value) throws IllegalArgumentException {
		if(isArithmeticExpression(tree)==false|| varible==null ){
			throw new IllegalArgumentException();
		}
		
		else{
		   	return shelp(tree,tree.root(),varible,value);
		}
			
		// TODO: Implement this method	
	}

	/**
	 * Given a tree and a a map of variable labels to values, this should
	 * replace all instances of those variables in the tree with the
	 * corresponding given values
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param map
	 *            - a map of variable labels to integer values
	 * @return Tree after replacing all instances of variables which are keys in
	 *         the map, with their numeric values
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or map is null, or tries
	 *             to substitute a null into the tree
	 
	 */
	public static void voidhelp(LinkedBinaryTree<String> tree,Position<String> root,String varible,int value){
		if(isArithmeticExpression(tree)==false|| varible==null|| ifInt(varible) ){
			throw new IllegalArgumentException();
		}
		
		if(tree.numChildren(root)==0){
			return;
		}
		else{
			
			if(tree.left(root).getElement().equals(varible)){
			String a=""+value;
			tree.remove(tree.left(root));
			
			tree.addLeft(root,a);
			voidhelp(tree,tree.left(root),varible,value);
		   voidhelp(tree,tree.right(root),varible,value);
			}
		   if(tree.right(root).getElement().equals(varible)){
			String a=""+value;
			tree.remove(tree.right(root));
			tree.addRight(root,a);
			voidhelp(tree,tree.left(root),varible,value);
			voidhelp(tree,tree.right(root),varible,value);
			}
			else{
				voidhelp(tree,tree.left(root),varible,value);
			voidhelp(tree,tree.right(root),varible,value);
			}
		}
		return;
	}
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, HashMap<String, Integer> map){ 
// 		Set keys = treemap.keySet();
		
// 		for (Iterator i = keys.iterator(); i.hasNext();) {
//           String key = (String) i.next();
		boolean isfind=true;
		if(map==null){
			throw new IllegalArgumentException();
		}
	    Set keys=map.keySet();
	   for (String key : map.keySet()){ 
		    if(map.get(key)==null){
				isfind=false;
			}
		}
		if(isArithmeticExpression(tree)==false || map==null || isfind==false){
			throw new IllegalArgumentException();
		} 
		String a="";
		Integer b=1;
	   for (String key : map.keySet()){ 
		    voidhelp(tree,tree.root(),key,map.get(key));
		   a=key;
			b=map.get(key);
		}
		    return shelp(tree,tree.root(),a,b);
	}
	


	/**
	 * Given a tree, identify if that tree represents a valid arithmetic
	 * expression (possibly with variables)
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return true if the tree is not null and it obeys the structure of an
	 *              arithmetic expression. Otherwise, it returns false
	 */
	public static boolean isArihelp(LinkedBinaryTree<String> tree,Position<String> root){
	    boolean isfind=true;
        if(root==null){
			return true;
		}
		
		    if(root.getElement().equals("-")||root.getElement().equals("+")||root.getElement().equals("*")){
		       if(tree.numChildren(root)==0 ){
			     return  false;
		   }
				
			else if (tree.numChildren(root)==1){
				
						return false;
				
				}
			
			else if(tree.numChildren(root)==2){
				return isArihelp(tree,tree.left(root))&&isArihelp(tree,tree.right(root));
				
			}
		  
		}
	
	return true;
	}

	public static boolean isArithmeticExpression(LinkedBinaryTree<String> tree) {

		if(tree==null){
			return false;
		}
		return isArihelp(tree,tree.root());

 }
}

