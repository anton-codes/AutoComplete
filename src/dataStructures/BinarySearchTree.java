package dataStructures;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class contains the code for the Binary Search Tree (BST) data structure.
 * In this implementation the tree is unbalanced and doesn't allow duplicate keys.
 * Inserting null values into this tree is also now allowed.
 * algorithms.algorithms implemented in this class make use of the <code> Comparable </code> interface,
 * so only those objects that implement <code> Comparable </code> can be stored in this data structure.
 *
 * @param <E> type of elements that will be stored inside the data structure.
 *
 * @author Anton Hrytsyk
 */
public class BinarySearchTree <E extends Comparable <E>> implements Serializable {

    /**
     * Tree Node class,
     * dataStructures.BinarySearchTree will consist of those.
     * @author Anton Hrytsyk
     */
    private class Node {
        /**
         * Declaring pointers for left and right nodes
         */
        Node left, right;
        E data;

        /**
         * Node constructor.
         * Assigns null values to left and right pointers,
         * assigns node's data to the specified data.
         * @param data initial data
         */
        Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

    }

    /**
     * Declaring the root node.
     */
    private Node root;

    /**
     * dataStructures.BinarySearchTree constructor.
     * Initializes the root node,
     * sets root's initial data to null.
     */
    public BinarySearchTree() {
        root = new Node(null);
    }


    /**
     * Performs insertion of the specified value into the tree.
     * @param key element that needs no be inserted into the tree.
     *
     * @throws IllegalArgumentException - null values are not allowed in this implementation.
     *
     * @return <code> true </code>  if element was successfully inserted,
     *         <code> false </code> if element is already in the tree.
     */
    public boolean insert(E key) {

        if (key == null)
            throw new IllegalArgumentException("null values are not allowed");

        if (root.data == null) {
            root.data = key;
            return true;
        }

        return insert(key, root);
    }


    /**
     * This function prints out all elements of the tree in the sorted order.
     */
    public void printInOrder() {
        System.out.print("[ ");
        inOrder(root);
        System.out.println("]");
    }


    /**
     * Performs recursive inorder traversal of the tree.
     * @param node
     */
    private void inOrder(Node node) {
        if (node == null) return;

        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }


    /**
     * Performs insert operation recursively.
     * @param key value that needs to be inserted.
     * @param node node at witch insert will be performed
     * @return <code> true </code>  if element was successfully inserted,
     *         <code> false </code> if element is already in the tree.
     */
    private boolean insert(E key, Node node) {

        // Stores comparison value
        int comp = node.data.compareTo(key);

        // Return false if value is already in the list.
        if (comp == 0) {
            return false;
        }

        // Inserts new node at the appropriate location.
        if (comp < 0 && node.right == null) {
            node.right = new Node(key);
            return true;
        }

        if (comp > 0 && node.left == null) {
            node.left = new Node(key);
            return true;
        }

        // Traverses the tree by updating value of the node
        node = (comp > 0) ? node.left : node.right;
        return insert(key, node);

    }

    /**
     * Performs a level order (breadth-first) traversal of the tree.
     * Prints out all elements in order in which tree would appear horizontally.
     * First node is being added to the queue.
     * While queue is not empty - remove and print the first element of the queue,
     * then if the children of the node are not null - add them to the queue as well.
     */
    public void levelOrderTraversal() {

        Queue<Node> queue = new LinkedList<>();

        Node node = root;
        queue.add(node);
        System.out.print("[ ");
        while (!queue.isEmpty()) {

            node = queue.remove();
            System.out.print(node.data + " ");

            if (node.left != null)  queue.add(node.left);
            if (node.right != null) queue.add(node.right);

        }
        System.out.println("]");

    }


    /**
     * Returns the smallest value in the tree.
     * @return the smallest value in the tree.
     */
    public E min() {
        return minValueAfter(root);
    }

    /**
     * Returns the smallest value after the given node.
     *
     * @param root node in relation to whom the smallest value needs to be found
     * @return value of the smallest node.
     */
     private E minValueAfter(Node root) {
        Node node = root;
        E minv = node.data;
        while (node.left != null) {
            node = node.left;
            minv = node.data;
        }
        return minv;
    }

    /**
     * Performs delete operation
     * @param el element that needs to be deleted
     * @return value of the deleted element.
     */
    public E delete(E el) {
        return (recDel(root, el) != null) ? el : null;
    }




    /**
     * Recursively deletes element from the tree.
     *
     * Description of the algorithm:
     *
     * <i> Base case </i>
     * Return <code> null </code> if value is not found or current node is <code> null </code>.
     *
     * If <code> node.data </code> isn't equal to to search data - update value of the appropriate node's child with it's old value.
     * With each of the recursive calls the traversed node's appropriate child becomes one of its children.
     * If the value isn't found - node reference eventually will fall off the tree and node will become <code> null </code>,
     * which triggers <i> the base case (leaf.left/right is null) </i>.
     *
     * When element is found in the tree the following happens:
     * a) if node that contain's the element is a leaf (left and right references are null) - delete it by sending null up the recursive stack.
     *    <code> null </code> will be set to the left or right of the appropriate parent node.
     * b) if node has only one child (left or right is null) - skip over the node that contains the key and return it's child up the recursive stack.
     *    This way the parent node of the node that contains the key will point to one of the children of the node that contained the key.
     * c) if node has two children (left and right are not null) - find the smallest element in it's right subtree and swap the data.
     *    then recursively delete the duplicate value from the root of the right subtree.
     *
     * In the end we return the node up the recursion stack with after all changes(if any) were made.
     *
     * @param node the node on which the algorithm will run recursively.
     * @param el element that needs to be deleted.
     * @return the node on which the algorithm was just performed.
     */
    private Node recDel(Node node, E el) {

        if (node == null)
            return node;

        if(node.data.compareTo(el) > 0)       node.left = recDel(node.left, el);
        else if (node.data.compareTo(el) < 0) node.right = recDel(node.right, el);
        else {
            if (node.right == null && node.left == null) return null;
            if (node.right == null)     return node.left;
            else if (node.left == null) return node.right;
            else {
                E min = minValueAfter(node.right);
                node.data = min;
                node.right = recDel(node.right, min);
            }
        }
        return node;
    }

    /**
     * Returns the biggest element in the tree.
     * @return the biggest element in the tree.
     */
    public E max() {
        Node node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node.data;
    }

    /**
     * This function determines if key is currently an element of the tree.
     * @param key value that needs to be checked.
     * @return <code> true </code>  if key is an element of the tree.
     *         <code> false </code> if key is not an element of the tree.
     */
    public boolean contains(E key) {

        Node node = root;
        int comp;
        while (node != null) {
            comp = node.data.compareTo(key);
            if (comp == 0) return true;
            else node = (comp > 0) ? node.left : node.right;
        }

        return false;
    }

}






















