package tree;

public class BSTApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BST<String> songs = new BST<String>();
		songs.insert("Hello");
		songs.insert("Senorita");
		songs.insert("Havana");
		songs.insert("Old town road");
		songs.insert("Roxane");
		songs.insert("Party rock");
		songs.insert("Despacito");
		BST.inorder(songs.root);
		System.out.println();
		
		BST<Integer> classExample = new BST<Integer>();
		classExample.insert(20);
		classExample.insert(10);
		classExample.insert(5);
		classExample.insert(15);
		classExample.insert(13);
		classExample.insert(18);
		classExample.insert(25);
		classExample.insert(30);
		classExample.insert(26);
		classExample.insert(7);
		classExample.insert(6);
		BST.inorder(classExample.root);
		System.out.println();
		classExample.delete(26);
		BST.inorder(classExample.root);
		System.out.println();
		classExample.delete(5);
		BST.inorder(classExample.root);
		System.out.println();
		classExample.delete(10);
		BST.inorder(classExample.root);
		
	}

}