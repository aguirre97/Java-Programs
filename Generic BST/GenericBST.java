// Alexandra Aguirre
// NID: al373180
// 1-26-17
// COP 3503

// ====================
// GenericBST: GenericBST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. 
// Assignment done for Dr. Szumlanski, UCF - Computer Science 2.


import java.io.*;
import java.util.*;

// Declaring the class node to accept any type of data
class Node<AnyType>
{
	AnyType data;
	Node<AnyType> left, right;

	Node(AnyType data)
	{
		this.data = data;
	}
}

// Extending comparable allows us to compare (i.g. greater than or less than) AnyType data
public class GenericBST<AnyType extends Comparable<AnyType> >
{
	private Node<AnyType> root;

	public void insert(AnyType data)
	{
		root = insert(root, data);
	}

	// inserting AnyType data into the tree
	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		// if the root is null, create a new Node
		if (root == null)
		{
			return new Node<AnyType>(data);
		}
		// if the data is less than the root, insert through the left side of the tree
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		// if the root is greater than the data, insert througb the right side of the tree
		else if ((root.data).compareTo(data) < 0)
		{
			root.right = insert(root.right, data);
		}
		else
		{
			// Stylistically, I have this here to explicitly state that we are
			// disallowing insertion of duplicate values. This is unconventional
			// and a bit cheeky.
			;
		}

		return root;
	}

	// deleting a node from the tree
	public void delete(AnyType data)
	{
		root = delete(root, data);
	}

	// deleting a node from the tree
	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		// if there is nothing there, return null
		if (root == null)
		{
			return null;
		}
		// if the data is less than the root, delete from the left side of the tree
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		// if the data is greater than the root, delete from the right side of the tree
		else if ((root.data).compareTo(data) < 0)
		{
			root.right = delete(root.right, data);
		}
		else
		{
			// if both sides are null (there is no children) return null
			if (root.left == null && root.right == null)
			{
				return null;
			}
			// if the right side is null (there is no right child) return the left child
			else if (root.right == null)
			{
				return root.left;
			}
			// if the left side is null (there is no left child) return the right child
			else if (root.left == null)
			{
				return root.right;
			}
			// else try to find the max node on the left side and delete it
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is non-empty.
	private AnyType findMax(Node<AnyType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// Returns true if the value is contained in the BST, false otherwise.
	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}

	// check if the value is within the BST
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		// if the value is not there, return false
		if (root == null)
		{
			return false;
		}
		// is the value less than the roo? check the left side of the tree
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		// is the value greater than the root? check the right side of the tree
		else if ((root.data).compareTo(data) < 0)
		{
			return contains(root.right, data);
		}
		// value is there, return true
		else
		{
			return true;
		}
	}

	// print out the tree in order, of left children to root then to right children
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// print out the tree in order, of left children to root then to right children
	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	// print out the root first then left children to right children
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// print out the root first then left children to right children
	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}
	// print out the tree form left children to right children then root
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// print out the tree form left children to right children then root
	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static double difficultyRating()
	{
		return 2.3;
	}

	public static double hoursSpent()
	{
		return 4.0;
	}
	
}