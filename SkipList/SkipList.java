/*
Alexandra Aguirre
NID: al373180
COP3503
2-23-17
*/
import java.io.*;
import java.util.*;

class Node <AnyType>
{
	AnyType value;
	int height;
	
	// nolds the next references of a node
	ArrayList<Node<AnyType>> next = new ArrayList<Node<AnyType>>();
	
	// constructor
	Node(int height)
	{
		this.height = height;

		// initially, all node's 'next' references should be initialized to null
		for(int i = 1; i < height + 1; i++)
			this.next.add(null);
		
		// this is useful when creating a head node, which does not store
		// anything meaningful in its data field
	}
	
	// constructor 
	Node(AnyType data, int height)
	{

		this.height = height;
		this.value = data;

		// all of the node's next references should be initialized to null
		for(int i = 1; i < height + 1; i++)
			this.next.add(null);
	}

	// getter	O(1)
	public AnyType value()
	{
		return this.value;
	}

	// getter	O(1)
	public int height()
	{
		return this.height;
	}
	
	// O(1)
	public Node<AnyType> next(int level)
	{
		// returns a reference to the next node at the particular level

		int currHeight = this.height - 1;

		if(level < 0 || level > currHeight)
			return null;
	
		return this.next.get(level);
	}

	// SUGGESTED METHODS
	
	public void setNext(int level, Node<AnyType> node)
	{
		// set the next reference at the given level within this node to 'node'
		this.next.set(level, node);
	}
	
	public void grow()
	{
		// grow this node by exactly 1 level
        // useful for forcing the skip list's 'head' to grow when inserting into skip list
        // causes list's maximum height to increase

		this.next.add(height, null);

		this.height += 1;
	}
	
	public void maybeGrow()
	{
		// grow this node by exactly 1 level with probability 50%
		// useful for when inserting causes the max height to increase

        if(Math.random() < 0.5)
        {
            //this.height = this.height + 1;
            this.next.add(this.height, null);
			this.height += 1;
        }
	}
	
	public void trim(int height)
	{
		// remove references from the top of this node's tower of next references until
        // this node's height has been reduced to the value given in the 'height' paramenter
        // useful for when deleting from skip list causes the list's maximum height to decrease

        for(int i = height; i < this.height; i++) 
        {
            this.next.set(i, null);
        }
        
        this.height = height;  
	}
	
}

public class SkipList<AnyType extends Comparable<AnyType> > 
{
	int height;
	int size;
	Node<AnyType> head;

	// constructor
	SkipList()
	{
		this.height = 1;
		this.head = new Node<AnyType>(height);
	}
	
	// constructor
	SkipList(int height)
	{
		this.height = height;
		this.head = new Node<AnyType>(height);

		// SkipList remains this tall until it contains so many elements
		// it must grow
	}
	
	// getter	O(1)
	public int size()
	{
		return this.size;
	}

	// getter	O(1)
	public int height()
	{
		return this.height;
	}

	// getter
	public Node<AnyType> head()
	{
		return this.head;
	}
	
	public void insert(AnyType data)
	{	
		/* NOTE: could just generate a random height and use the 
		insert(AnyType data, int height) method, but for some reason
		this passes all except Test Case 07.
		So, just using the same code as insert(AnyType data, int height) */

		//int newHeight = getMaxHeight(this.size);
		//int insertedNodeHeight = generateRandomHeight(newHeight);
		//insert(data, insertedNodeHeight);

		this.size += 1;
		
		int newHeight = getMaxHeight(this.size);
		
		// the new found height is bigger than original 
		// grow the skip list and set the new height
		if (newHeight > this.height)
		{
			this.height = newHeight;
			this.growSkipList();
		}
		
		int level = this.height - 1;

		Node<AnyType> tempNode = this.head;
		
		// keep track of all teh nodes that have to be reconnected
		HashMap<Integer,Node<AnyType>> reconnect = new HashMap<Integer,Node<AnyType>>();
		
		while(level >= 0 && tempNode != null)
		{
			// reference is null, go down a level 
			// the node is smaller than the current one, go down a level
			if(tempNode.next(level) == null || (tempNode.next(level).value()).compareTo(data) >= 0)
			{
				reconnect.put(level, tempNode);
				level -= 1;
			}
			// the node is bigger than the current one, move down the list
			else if((tempNode.next(level).value()).compareTo(data) < 0)
				tempNode = tempNode.next(level);
		}	
		
		// get random new height for node to be inserted
		int insertedNodeHeight = generateRandomHeight(this.height);

		// create the node to be inserted
		Node<AnyType> insertedNode = new Node<AnyType>(data, insertedNodeHeight);

		// attach old node references to the new ones
		// have the inserted node's references point to where the old ones did
		for(int i = insertedNodeHeight - 1; i >= 0; i--)
		{
			Node<AnyType> node = reconnect.get(i).next(i);
			insertedNode.setNext(i, node);

			reconnect.get(i).setNext(i, insertedNode);
		}

	}

	public void insert(AnyType data, int height)
	{	
		this.size += 1;
		
		int newHeight = getMaxHeight(this.size);
		
		// the new found height is bigger than original 
		// grow the skip list and set the new height
		if (newHeight > this.height)
		{
			this.height = newHeight;
			this.growSkipList();
		}
		
		int level = this.height - 1;

		Node<AnyType> tempNode = this.head;
		
		// keep track of all teh nodes that have to be reconnected
		HashMap<Integer,Node<AnyType>> reconnect = new HashMap<Integer,Node<AnyType>>();
		
		while(level >= 0 && tempNode != null)
		{
			// reference is null, go down a level 
			// the node is smaller than the current one, go down a level
			if(tempNode.next(level) == null || (tempNode.next(level).value()).compareTo(data) >= 0)
			{
				reconnect.put(level, tempNode);
				level -= 1;
			}
			// element is bigger, keep searching
			else if((tempNode.next(level).value()).compareTo(data) < 0)
				tempNode = tempNode.next(level);

		}
		
		// create the node to be inserted
		Node<AnyType> insertedNode = new Node<AnyType>(data, height);
	
		// attach old node references to the new ones
		// have the inserted node's references point to where the old ones did
		for(int i = height - 1; i >= 0 ; i--)
		{
			Node<AnyType> node = reconnect.get(i).next(i);
			insertedNode.setNext(i, node);

			reconnect.get(i).setNext(i, insertedNode);
		}
		
	}
	
	public void delete(AnyType data)
	{
		// delete a single occurance of data from SkipList
		// if there are multiple, delete the first node that contains data
		// use the search algorithm described in class O(log n)

		int level = this.height - 1;

		Node<AnyType> tempNode = this.head;
		
		// keep track of the nodes that need to be reconnected
		HashMap<Integer,Node<AnyType>> reconnect = new HashMap<Integer,Node<AnyType>>();
		
		// if the level is negative, it is not there
        // if the head is null, nothing there
		while(level >= 0 && tempNode != null)
		{
			// the node is pointing to null, go down a level
            // the node is smaller than the current one, go down a level
			if(tempNode.next(level) == null || (tempNode.next(level).value()).compareTo(data) > 0)
				level -= 1;
			// the node is bigger than the current one, move down the list
			else if((tempNode.next(level).value()).compareTo(data) < 0)
				tempNode = tempNode.next(level);
			else  
			{
				reconnect.put(level, tempNode);
				level -= 1;
			}
		}
		
		// have tempNode point to the Node being deleted
		tempNode = tempNode.next(level + 1);
		
		if(tempNode != null && (tempNode.value()).compareTo(data) == 0)
		{	
			int newLevel = tempNode.height();

			// reconnect the references to the nodes the deleted node pointed to
			for(int i = newLevel - 1; i >= 0; i--)
				reconnect.get(i).setNext(i, tempNode.next(i));
			
			this.size -= 1;
			
			int newHeight;

			if(this.size <= 1)
				newHeight = 1;
			else
				newHeight = getMaxHeight(this.size);
			
			// check if the height has to be adjusted
			if (newHeight < this.height)
			{	
				this.height = newHeight;
				this.trimSkipList();
			}

		}

	}
	
	public boolean contains(AnyType data)
	{
		// return true if SkipList contains data
        // must use the search algorithm described in class O(log n)

		int level = this.height - 1;

		Node<AnyType> tempNode = this.head;

		// the level is negative, it is not there
        // the head is null, nothing there
		while(level >= 0 && tempNode != null)
		{
			// the node is pointing to null, go down a level
			// the node is smaller than the current one, go down a level
			if(tempNode.next(level) == null || (tempNode.next(level).value()).compareTo(data) > 0)
				level -= 1;
			// the node is bigger than the current one, move down the list
			else if((tempNode.next(level).value()).compareTo(data) < 0)
				tempNode = tempNode.next(level);
			else
				return true;
		}
	
		return false;
	}

	public Node<AnyType> get(AnyType data)
	{
		// return a reference to a node in the SkipList that contains 'data'
        // if no such node exists, return null

		int level = this.height - 1;

		Node<AnyType> tempNode = this.head;

		// the level is negative, it is not there
        // the head is null, nothing there
		while(level >= 0 && tempNode != null)
		{
			// the node is pointing to null, go down a level
			// the node is smaller than the current one, go down a level
			if(tempNode.next(level) == null || (tempNode.next(level).value()).compareTo(data) > 0)
				level -= 1;
			// the node is bigger than the current one, move down the list
			else if((tempNode.next(level).value()).compareTo(data) < 0)
				tempNode = tempNode.next(level);
			else
				return tempNode;
		}

		return null;
	}
	
	// SUGGESTED METHODS

	private static int getMaxHeight(int n)
	{
		return (int)Math.ceil((Math.log(n) / Math.log(2)));
	}
	
	private static int generateRandomHeight(int maxHeight)
	{
		// returns 1 with 50%, 2 with 25%, 3 with 12.5%
        // without exceeding maxHeight 
		int i; 
		
		for(i = 1; i < maxHeight; i++)
		{
			double prob = Math.random();
			if(prob < 0.5)
				break;
		}
		
		return i;
	}
	
	private void growSkipList()
	{
		// grow SkipList using procedure described for insert()
		int newLevel = this.height - 1;
		int level = newLevel - 1;
		this.head.grow();

		// create a reference node
		Node<AnyType> refNode =this.head;
		
		// create a tempNode to hold the next level
		Node<AnyType> tempNode = refNode.next(level);

		while(tempNode != null)
		{	
			//see if the height grows
			tempNode.maybeGrow();
		
			if(tempNode.height() == newLevel + 1)
			{
				refNode.setNext(newLevel, tempNode);
				refNode = refNode.next(newLevel);
			}
			// tempNode did not grow, check the next level
			tempNode = tempNode.next(level);
		}
	}

	private void trimSkipList()
	{
		// trim skiplist using procedure described for delete()
		trimSkipList(this.head);
	}
	
	private void trimSkipList(Node<AnyType> tempNode)
	{
		int level = this.height - 1;

		// at end of list
		if(tempNode == null)
			return;
		
		// traverse list until reaching the end
		trimSkipList(tempNode.next(level));	

		// trim the node once it points to nothing
		tempNode.trim(this.height);
	}
	
	public static double difficultyRating()
	{
		return 5.0;
	}
	
	public static double hoursSpent()
	{
		return 12.5;
	}

}