package dataStructures;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Objects;

/**
 * Implementation of a doubly linked list
 * @param <E> element to be stored in the list.
 *
 * @author Anton Hrytsyk
 */
public class DoublyLinkedList<E> extends AbstractList<E> implements Serializable {


    /**
     * Used during deserialization to confirm that objects are compatible.
     * @value 1L
     */
    public static final long serialVersionUID = 1L;

    /**
     * This implementation stores references for both head and tail nodes.
     * Head and Tail are sentinel nodes in this implementation. They store null as their data,
     * head and tail are not counted when calculating size.
     */
    private ListNode head, tail;

    /**
     * Represents structure's size.
     * Number of nodes that contain data.
     */
    private int size;


    /**
     * Default constructor.
     * Sets up the structure by linking head to tail and tail to head.
     */
    public DoublyLinkedList() {
        head = new ListNode();
        tail = new ListNode();

        head.prev = null;
        tail.next = null;

        head.next = tail;
        tail.prev = head;

        // Initial size
        size = 0;
    }


    /**
     * Returns element at the given index.
     * @param index index of the element.
     *
     * @throws IndexOutOfBoundsException if attempting to select element outside structure's bounds.
     *
     * @return element at the specified index.
     */
    public E get(int index) {

        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Illegal index: " + index + "; size: " + size);

        ListNode node = head;

        for (int i = -1; i < index ; i++) {
            node = node.next;
        }
        return node.data;
    }


    /**
     * Appends element to the end of the list.
     *
     * @throws IllegalArgumentException when attempting to insert null value.
     * @param element element to be added to the list.
     * @return true if the element was added successfully.
     */
    public boolean add(E element) {

        if (element == null)
            throw new IllegalArgumentException("null is not allowed");

        try {
            ListNode node = new ListNode(element);

            node.next = tail;
            node.prev = tail.prev;

            tail.prev.next = node;
            tail.prev = node;

        } catch (OutOfMemoryError e) {
            return false;
        }

        size++;
        return true;
    }

    /**
     * Size accessor.
     * @return structure's size.
     */
    public int size() {
        return size;
    }


    /**
     * Prints out all elements in the structure,
     * starts from the head node.
     */
    public void print() {
        System.out.println(toString());
    }


    /**
     * String value that represents the structure.
     *
     * @return String that contains value of all nodes starting from the head node.
     */
    @Override
    public String toString() {
        String out = "[ ";
        ListNode node = head;

        while (node.next.data != null) {
            out = out.concat(node.next.data + " ");
            node = node.next;
        }
        return out +"]";

    }

    /**
     * Removes first occurrence of specific element.
     * @param element element to be removed from the list
     */
    public void removeElement(E element) {
        ListNode node = head;


        for (int i = 0; i < size ; i++) {
            node = node.next;
            if (node.data.equals(element)) {
                node.next.prev = node.prev;
                node.prev.next = node.next;

                node.next = null;
                node.prev = null;

                size--;
                return;
            }
        }

    }


    /**
     * Removes element at the specified index.
     * @param index at which element will be removed.
     *
     * @throws IndexOutOfBoundsException when trying to access an element outsides structure's bounds.
     *
     * @return removed element.
     */
    public E remove(int index) {

        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Illegal index: " + index + "; size: " + size);

        ListNode node = head;

        for (int i = -1; i < index ; i++) {
            node = node.next;
        }

        node.next.prev = node.prev;
        node.prev.next = node.next;

        node.next = null;
        node.prev = null;

        size--;

        return node.data;
    }


    /**
     * Sets element at the specified index to a specified values.
     *
     * @throws IllegalArgumentException when attempting to insert null value.
     * @throws IndexOutOfBoundsException when trying to access an element outsides structure's bounds.
     * @param index   index of the element.
     * @param element new value of the element.
     * @return old value at that index.
     */
    public E set(int index, E element) {

        if (element == null)
            throw new IllegalArgumentException("null is not allowed");


        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Illegal index: " + index + "; size: " + size);


        ListNode node = head;

        for (int i = -1; i < index ; i++) {
            node = node.next;
        }

        E oldData = node.data;

        node.data = element;

        return oldData;
    }

    /**
     * Inserts element at the specific index.
     *
     * @throws IllegalArgumentException when attempting to insert null value.
     * @throws IndexOutOfBoundsException when trying to access an element outsides structure's bounds.
     *
     * @param index    index at which new element will be inserted.
     * @param element  value of the new element.
     */
    public void add(int index, E element) {


        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Illegal index: " + index + "; size: " + size);

        if (element == null)
            throw new IllegalArgumentException("null is not allowed");


        ListNode node = head;

        for (int i = -1; i < index ; i++) {
            node = node.next;
        }

        ListNode nodeToAdd = new ListNode(element);

        nodeToAdd.next = node;
        nodeToAdd.prev = node.prev;

        node.prev.next = nodeToAdd;
        node.prev = nodeToAdd;

        size++;
    }


    /**
     * Implementation of a Doubly Linked List Node class.
     * @author Anton Hrytsyk
     */
    private class ListNode {

        /**
         * Each nodes stores a reference to the next and the previous node.
         */
        ListNode prev, next;

        /**
         * Each node holds a value.
         */
        E data;

        /**
         * Data constructor.
         * Initializes data and passes null to the rest of the values through base constructor.
         * @param data value of the node.
         */
        ListNode(E data) {
            this(data, null, null);
        }

        /**
         * Default constructor.
         * Assigns null to all values through base constructor.
         */
        ListNode () {
            this(null, null, null);
        }

        /**
         * Base constructor.
         *
         * @param data value of the node.
         * @param prev previous node reference.
         * @param next next node reference.
         */
        ListNode(E data, ListNode prev, ListNode next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        /**
         * Equals method.
         * This method is used for comparing nodes.
         * @param o object to compare with.
         * @return true if equal fales if not.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DoublyLinkedList)) return false;

            ListNode listNode = (ListNode) o;

            if (!Objects.equals(prev, listNode.prev)) return false;
            if (!Objects.equals(next, listNode.next)) return false;
            return Objects.equals(data, listNode.data);
        }

        /**
         * Generic hashCode generator method.
         * @return hashCode of a node.
         */
        @Override
        public int hashCode() {
            int result = prev != null ? prev.hashCode() : 0;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        /**
         * String value that represents the node.
         * @return String that contains all values of the node.
         */
        @Override
        public String toString() {
            return "ListNode{" +
                    "prev=" + prev +
                    ", next=" + next +
                    ", data=" + data +
                    '}';
        }
    }

}