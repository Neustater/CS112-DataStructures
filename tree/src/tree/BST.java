package tree;

import java.util.NoSuchElementException;

/*
 * BST: binary search tree 
 * 
 * This implementation does not allow duplicates
 * 
 * Comparable is an interface that imposes a total ordering of the objects.
 * An interface is not a class, it defines a set of variables and methods.
 * 
 * Comparable imposes that T has to implement the compareTo method
 */
public class BST<T extends Comparable<T>> {

	BSTNode<T> root;
	int size;
	
	public BST () {
		root = null;
		size = 0;
	}
	
	public void insert (T key) {
		// 1. search until it fails
		BSTNode<T> ptr = root;
		BSTNode<T> prev = null;
		int c = 0; // store the compareTo result		
		while ( ptr != null ) {
			c = key.compareTo(ptr.key);
			if ( c == 0 ) {
				throw new IllegalArgumentException(key + " already in the BST");
			} else if ( c < 0 ) {
				prev = ptr;
				ptr = ptr.left; // go to the left subtree
			} else {
				prev = ptr;
				ptr = ptr.right; // go to the right subtree
			}
		}
		// 2. insert key as prev's child
		BSTNode<T> newnode = new BSTNode<T>(key, null, null); // newnode is always a leaf
		if ( prev == null ) {
			// tree is empty
			root = newnode;
		} else if ( c < 0 ) {
			// left child
			prev.left = newnode;
		} else {
			// right child
			prev.right = newnode;
		}
		size += 1;
	}
	
	public T search (T target) {
		
		BSTNode<T> ptr = root;
		while ( ptr != null ) {
			int c = target.compareTo(ptr.key); // compares target with ptr.key
			if ( c == 0 ) {
				// found it
				return ptr.key; // returns the key, if we had data we would return the data
			} else if ( c < 0 ) {
				// target < ptr.key
				ptr = ptr.left;
			} else {
				ptr = ptr.right;
			}
		}
		return null; // target not found
	}
	
	public static <T extends Comparable<T>> void inorder (BSTNode<T> node) {
		
		if ( node == null ) return; // return back to caller method (previous inorder caller)
		inorder(node.left); // go into the left node
		System.out.print(node.key + " ");
		inorder(node.right);
	}
	
	public void delete(T key) {
		//1.find the node to delete, call it x
		BSTNode<T> p = null;
		BSTNode<T> x = root;
		int c = 0;
		
		while(x != null) {
			c = key.compareTo(x.key);
			if( c == 0) {
				//found
				break;
			}
			p = x;
			x = c < 0 ? x.left : x.right; //if c < 0 x = x.left (?) means if condition is true, else x.right	
		}
		//2. key is not found
		if(x == null) {
			throw new NoSuchElementException(key + "not found");
		}
		//3. if x has two children (check case 3)
		//Find x in-order predecessor (largest value on left subtree
		BSTNode<T> y = null; //y is the in-order predecessor
		if(x.left != null && x.right != null) {
			y = x.left; //go one left
			p = x;
			while(y.right != null) {//go all the way right
				p = y;
				y =  y.right;
				
			}
			
			//copy entry at y into x
			x.key = y.key;
			
			//prepare to delete
			x = y;
		}
		//4. x is the root, only has one child
		if(p == null) { //no previous exists
			root = x.left !=null ? x.left : x.right;
			return;
		}
		//5. handle case 1 and case 2 in the same code
		BSTNode<T> tmp =  x.right != null ? x.right : x.left;
		if(x == p.right) {
			p.right  = tmp;
		}else {
			p.left = tmp;
		}
		size -=1;
	}
}