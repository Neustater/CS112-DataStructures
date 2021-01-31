package tree;

public class BSTNode<T> {

	T key; // the node's key, what is used for searching
	BSTNode<T> left; // left (child) subtree: all nodes to the left of a node
	BSTNode<T> right; // right (child) subtree: all nodes to the right of a node
	
	// the data has to be kept apart from the key
	// For example if the key is last names at Rutgers, then
	// we need to keep each person's record in another reference 
	// here at the node
	
	public BSTNode (T key, BSTNode<T> left, BSTNode<T> right) {
		this.key = key;
		this.left = left;
		this.right = right;
	}
	// [key, null, null], [key, leftkey, rightkey]
	public String toString () {
		String str = "[" + key + ",";
		str += (left == null) ? "null" : left.key;
		str += ",";
		str += (right == null) ? "null" : right.key;
		str += "]";
		return str;
	}
}