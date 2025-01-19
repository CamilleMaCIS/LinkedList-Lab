package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 *
 */
public class ListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        // case 1: if circular doubly LinkedList is empty
        if (head == null){
            return 0;
        }

        // case 2: if circular doubly LinkedList contains only one node, and head.next = head,
        // then the while loop won't run, so by default, size = 1
        int size = 1;

        // case 3: if circular doubly LinkedList contains more than one node
        ListNode temp = head;
        /** because the LinkedLists in AbstractListManipulatorTest are circular doubly linked list, this means that
          the previous and next pointers are never null
          to check when the circular linked list loops back, simply check if node.next points to the head again
         */
        while(temp.next != head && temp.next != null){
            size++;
            System.out.println(size);
            temp = temp.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty(ListNode head) {
        if (head == null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        // case 1: if circular doubly LinkedList is empty
        // if the LinkedList is empty, then it won't contain any elements
        if (isEmpty(head)){
            return false;
        }

        // case 2: if circular doubly LinkedList contains only one node, and head.next = head
        if (size(head) == 1){
            if(head.element.equals(element)){
                return true;
            }
            else{
                return false;
            }
        }

        // case 3: if circular doubly LinkedList contains more than one node
        ListNode temp = head;
        // check if temp node reached the end of the circular LinkedList
        // by checking if temp.next loops back to the head node
        while(temp.next != head && temp.next != null){
            if (temp.element.equals(element)) {
                return true;
            }
            temp = temp.next;
        }
        // after the while loop, temp is now = the last element
        // however the while loop hasn't checked the last element yet, so need to check outside the while loop
        if (temp.element.equals(element)) {
            return true;
        }
        return false;
    }

    // count() returns how many times the target element appears in the LinkedList
    @Override
    public int count(ListNode head, Object element) {
        // case 1: if circular doubly LinkedList is empty
        // if the LinkedList is empty, then it won't contain any elements
        if (isEmpty(head)){
            return 0;
        }

        // case 2: if circular doubly LinkedList contains only one node, and head.next = head
        if (size(head) == 1){
            if(head.element.equals(element)){
                return 1;
            }
            else{
                return 0;
            }
        }

        // case 3: if circular doubly LinkedList contains more than one node
        ListNode temp = head;
        int count = 0;
        // check if temp node reached the end of the circular LinkedList
        // by checking if temp.next loops back to the head node
        while(temp.next != head && temp.next != null){
            System.out.println(temp.element);
            if (temp.element.equals(element)) {
                count++;
            }
            temp = temp.next;
        }
        // after the while loop, temp is now = the last element
        // however the while loop hasn't checked the last element yet, so need to check outside the while loop
        if (temp.element.equals(element)) {
            count++;
        }
        return count;
    }

    @Override
    public String convertToString(ListNode head) {
        // case 1: if circular doubly LinkedList is empty
        String convertedString = "";
        if (head == null){
            return convertedString;
        }

        // case 2: if circular doubly LinkedList contains only one node, and head.next = head
        if (size(head) == 1){
            return head.element.toString();
        }

        // case 3: if circular doubly LinkedList contains more than one node
        ListNode temp = head;
        // check if temp node reached the end of the circular LinkedList
        // by checking if temp.next loops back to the head node
        while(temp.next != head && temp.next != null){
            String addedString = temp.element.toString() + ",";
            convertedString += addedString;
            temp = temp.next;
        }
        // after the while loop, temp is now = the last element
        // however the while loop hasn't checked the last element yet, so need to check outside the while loop
        // after the last element, no need to add another comma
        convertedString += temp.element.toString();

        return convertedString;
    }

    // gets the object at index n from the front
    // e.g. if n = 0, then return the first element in the list, if it exists
    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        // if LinkedList is null,
        // or if n is negative,
        // or if n larger than the size of the LinkedList, throw invalid index error
        if (head == null || n < 0 || n >= size(head)){
            throw new InvalidIndexException();
        }

        // if n is valid or head isn't null, then use for loop until finds the element at index n
        ListNode temp = head;
        // check if temp node reached the end of the circular LinkedList
        // by checking if temp.next loops back to the head node
        for (int i = 0; i < n; i++){
            temp = temp.next;
        }
        return temp.element;
    }


    // gets the object at index n from the back
    // e.g. if n = 0, then return the last element in the list, if it exists
    // similar to getFromFront(), but using .previous instead of .next
    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        // if LinkedList is null,
        // or if n is negative,
        // or if n larger than the size of the LinkedList, throw invalid index error
        if (head == null || n < 0 || n >= size(head)){
            throw new InvalidIndexException();
        }

        // start at the tail, tail = index 0
        ListNode temp = head.previous;
        // check if temp node reached the end of the circular LinkedList
        // by checking if temp.next loops back to the head node
        for (int i = 0; i < n; i++){
            temp = temp.previous;
        }
        return temp.element;
    }

    // checks if each node in the LinkedList has the same value
    @Override
    public boolean equals(ListNode head1, ListNode head2) {
        // case 1: if circular doubly LinkedList is empty
        // if BOTH LinkedLists are empty, then both have 0 elements, so they're equal
        if (head1 == null && head2 == null){
            return true;
        }

        // if both LinkedLists have different sizes, then they can't possibly be equal
        if (size(head1) != size(head2)){
            return false;
        }

        // case 2: both LinkedLists have size one, then check if head1 equals head2
        if (size(head1) == 1 && size(head2) == 1){
            if(head1.equals(head2)){
                return true;
            }
            else{
                return false;
            }
        }

        // case 3: if circular doubly LinkedList contains more than one node
        // since program already checked if both LinkedLists have the same size or not, then for case 3,
        // we can assume that both LinkedLists have the same size
        ListNode temp1 = head1;
        ListNode temp2 = head2;
        // both LinkedLists must have the same amount of equal node values in order to be equal
        int countSame = 0;
        // check if temp1 node reached the end of the circular LinkedList by checking if temp1.next loops back to head1 node
        while(temp1.next != head1 && temp1.next != null){
            if (temp1.equals(temp2)) {
                // if the 2 temporary nodes are equal, then add 1 to the count
                countSame++;
            }
            temp1 = temp1.next;
            temp2 = temp2.next;
        }
        // after the while loop, temp is now = the last element
        // however the while loop hasn't checked the last element yet,
        // so need to check outside the while loop
        if (temp1.equals(temp2)) {
            countSame++;
        }

        // now check if the amount of equal nodes is the same as the
        // amount of nodes in total in the entire LinkedList
        if (countSame == size(temp1)) {
            return true;
        }
        return false;
    }

    // checks if there are duplicate values
    @Override
    public boolean containsDuplicates(ListNode head) {
        // case 1: circular doubly LinkedList is empty or contains only 1 element
        // if the LinkedList has 0 or 1 elements only, then there can't be any possible duplicates
        if (isEmpty(head) || size(head) == 1){
            return false;
        }

        // case 2: if circular doubly LinkedList contains more than one node
        // temp can start at the SECOND node
        ListNode temp = head.next;
        // LinkedList uniqueNodes will add the first element of head into the list
        ListNode uniqueNodes = new ListNode(head.element);
        // check if temp node reached the end of the circular LinkedList
        // by checking if temp.next loops back to the head node
        while(temp.next != head && temp.next != null){
            if (contains(uniqueNodes, temp.element)) {
                return true;
            }
            // if LinkedList uniqueNodes doesn't already contain temp.element,
            // then append the new node to the list
            append(uniqueNodes, temp);
            temp = temp.next;
        }
        // after the while loop, temp is now = the last element
        // however the while loop hasn't checked the last element yet, so need to check outside the while loop
        if (contains(uniqueNodes, temp.element)) {
            return true;
        }
        return false;
    }

    // adds nodeToBeAdded to the very front of the first LinkedList parameter called head
    @Override
    public ListNode addHead(ListNode head, ListNode nodeToBeAdded) {
        ListNode headTail = head.previous;
        ListNode nodeToBeAddedTail = nodeToBeAdded.previous;

        // nodeToBeAdded previous pointer now points to the current last node
        nodeToBeAdded.previous = headTail;
        // next pointer of the current tail now points to nodeToBeAdded instead of the head
        headTail.next = nodeToBeAdded;
        // changes head's previous pointer from pointing to the last node, to nodeToBeAdded instead
        head.previous = nodeToBeAdded;
        // nodeToBeAdded previous pointer now points to the current last node
        nodeToBeAddedTail.next = head;
        return nodeToBeAdded;
    }

    // appends head2 after head1
    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        if (head1 == null && head2 == null){
            return null;
        }
        if (head1 == null){
            return head2;
        }
        if (head2 == null){
            return head1;
        }

        ListNode tail1 = head1.previous;
        ListNode tail2 = head2.previous;
        //connect the first tail to the second head

        if (tail1 != null && tail2 != null){
            //connect the first tail to the second head
            tail1.next = head2;
            head2.previous = tail1;
            //connect the second tail to the first head
            tail2.next = head1;
            head1.previous = tail2;
        }
        return head1;
    }

    // insert node at index n of head
    // e.g. head = {3, 5, 7}, node = 9, n = 3
    // then insert() returns {3, 5, 7, 9}
    // or head = {1, 6, 7}, node = {2, 3, 4, 5}, n = 1
    // then insert() returns {1, 2, 3, 4, 5, 6, 7}
    // if n = 0, insert at head, if n = head.size(), insert at tail
    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        if (n < 0 || n > size(head)){
            throw new InvalidIndexException();
        }
        if (head == null){
            return null;
        }
        if (node == null){
            return head;
        }
        // if insert at the beginning, use addHead()
        if (n == 0){
            return addHead(head, node);
        }
        // if insert at the end, use append()
        if (n == size(head)){
            return append(head, node);
        }

        // remember that the second LinkedList will be added BETWEEN temp (index n) and temp.previous (index n-1)
        ListNode temp = head;
        // after this loop, temp = node at index n
        for (int i = 0; i < n; i++){
            temp = temp.next;
        }
        ListNode tempPrevious = temp.previous;
        ListNode nodePrevious = node.previous;
        // replace temp.previous with the tail of new node
        temp.previous = node.previous;
        // replace temp.previous.next with the head of the new node
        tempPrevious.next = node;
        //replace node.previous.next with temp;
        nodePrevious.next = temp;
        //replace node.previous with the original temp.previous
        node.previous = tempPrevious;

        // print string for test
        System.out.println(convertToString(head));
        return head;
    }

    // deletes only the FIRST occurence of elem
    // e.g. in AbstractListManipulatorTest test class, deleting 5 from {5, 5, 9} will return {5, 9}
    public ListNode delete(ListNode head, Object elem) {
        // if the element does not appear in the LinkedList, return head without any changes
        if (elem == null || !contains(head, elem)){
            return head;
        }
        if (head == null){
            return null;
        }
        // if LinkedList only contains 1 element AND the element = elem we want to delete, then returns null
        if (size(head) == 1 && head.element.equals(elem)){
            return null;
        }

        ListNode temp = head.next;
        // check if temp node reached the end of the circular LinkedList
        // by checking if temp.next loops back to the head node
        while(temp.next != head && temp.next != null){
            // if temp.element = element, then remove and then return
            if (temp.element.equals(elem)){
                ListNode tempNext = temp.next;
                ListNode tempPrevious = temp.previous;
                tempPrevious.next = tempNext;
                tempNext.previous = tempPrevious;
            }
            temp = temp.next;
        }
        // after the while loop, temp is now = the last element
        if (temp.element.equals(elem)){
            ListNode tempNext = temp.next;
            ListNode tempPrevious = temp.previous;
            tempPrevious.next = tempNext;
            tempNext.previous = tempPrevious;
        }
        return head;
    }

    @Override
    public ListNode reverse(ListNode head) {
        // if head is empty, return null
        if (size(head) == 0){
            return null;
        }
        // if head has only one element, return head without changes
        if (size(head) == 1){
            return head;
        }

        if (size(head) == 2){
            ListNode newTail = new ListNode(head.previous.element);
            ListNode newHead = new ListNode(head.element);
            newTail.next = newHead;
            newTail.previous = newHead;
            newHead.next = newTail;
            newHead.previous = newTail;
            return newTail;
        }


        ListNode newList = new ListNode(head.previous.element);
        newList.previous = newList;
        newList.next = newList;
        // since the loop will go backwards, temp starts at the tail
        ListNode temp = head.previous;
        // check if temp node reached the head of the circular LinkedList by checking if temp.next loops back to the head node
        while(temp.previous != head && temp.previous != null){
            temp = temp.previous;
            ListNode newNode = new ListNode(temp.element);
            newNode.previous = newNode;
            newNode.next = newNode;
            // append will only work if the second node has a non-null previous and next pointers, check append() if statement
            append(newList, newNode);
        }
        // after the while loop, the head node still hasn't been included because while loop stopped at temp.previous == head
        ListNode newNode = new ListNode(head.element);
        newNode.next = newNode;
        newNode.previous = newNode;
        // append will only work if the second node has a non-null previous and next pointers, check append() if statement
        append(newList, newNode);

        return newList;
    }

    // splits into a LinkedList OF LinkedList
    // e.g. from test class, head = {5, 3} and node = {3},
    // then split() will return {{5}, {3}}, the list is split right before 3 (which is index of node)
    // a list can't split itself
    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        if (head == null || node == null) {
            throw new InvalidListException();
        }
        // since head can't equal to node, then head can't be split by itself
        if (head.equals(node)) {
            throw new InvalidListException();
        }
        // if the head LinkedList doesn't contain the node:
        if (!contains(head, node.element)) {
            throw new InvalidListException();
        }
        // if LinkedList only contains 1 element:
        if (size(head) == 1) {
            // returns {{}, {head element}}, first list is an empty list
            ListNode listOfLists1 = new ListNode(null);
            ListNode listOfLists2 = new ListNode(head);
            listOfLists1.previous = listOfLists2;
            listOfLists1.next = listOfLists2;
            listOfLists2.previous = listOfLists1;
            listOfLists2.next = listOfLists1;
            return listOfLists1;
        }

        // if to-be-cut-off-node = tail, or if size = 2
        if (head.previous.element.equals(node.element) || size(head) == 2) {
            // listOfLists 1 and 2 will contain the first half and the second half after the split
            ListNode listOfLists1 = new ListNode(null);
            ListNode listOfLists2 = new ListNode(null);
            listOfLists1.previous = listOfLists2;
            listOfLists1.next = listOfLists2;
            listOfLists2.previous = listOfLists1;
            listOfLists2.next = listOfLists1;

            ListNode cutoffTail = head.previous;
            ListNode newTail = head.previous.previous;

            // cut off the tail
            cutoffTail.next = cutoffTail;
            cutoffTail.previous = cutoffTail;

            // reattach the second last node to the head
            newTail.next = head;
            head.previous = newTail;

            // set listOfLists1 element = head, and listOfLists2 = cutOffTail
            listOfLists1.element = head;
            listOfLists2.element = cutoffTail;
            return listOfLists1;
        }

        // for all other cases:
        ListNode listOfLists1 = new ListNode(null);
        ListNode listOfLists2 = new ListNode(null);
        listOfLists1.previous = listOfLists2;
        listOfLists1.next = listOfLists2;
        listOfLists2.previous = listOfLists1;
        listOfLists2.next = listOfLists1;

        // remember that after the split, the two new circular LinkedLists need to connect back to its own head
        // e.g. {5, 4, 3, 2, 1} split at {3} = {{5, 4}, {3, 2, 1}}

        ListNode temp = head;
        // check if temp node reached the end of the circular LinkedList by checking if temp.next loops back to the head node
        while (temp.next != head && temp.next != null) {
            // if temp.element = element, then program has reached the node where the split should happen
            if (temp.element.equals(node.element)) {
                ListNode tempPrevious = temp.previous;
                ListNode headPrevious = head.previous;

                // list1 head node = head, list1 tail node = temp.previous
                head.previous = tempPrevious;
                tempPrevious.next = head;
                listOfLists1.element = head;

                //list2 head node = temp, list2 tail node = head.previous
                temp.previous = headPrevious;
                headPrevious.next = temp;
                listOfLists2.element = temp;
            }
            temp = temp.next;
        }

        return listOfLists1;
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        if (isEmpty(head)){
            return null;
        }
        if (size(head) == 1){
            System.out.println("element before: " + head.element);
            head.element = transformation.transform(head.element);
            System.out.println("element after: " + head.element);
            return head;
        }

        ListNode temp = head;
        // check if temp node reached the end of the circular LinkedList by checking if temp.next loops back to the head node
        while (temp.next != head && temp.next != null) {
            System.out.println("element before: " + temp.element);
            temp.element = transformation.transform(temp.element);
            System.out.println("element after: " + temp.element);
            temp = temp.next;
        }
        System.out.println("element before: " + temp.element);
        temp.element = transformation.transform(temp.element);
        System.out.println("element after: " + temp.element);
        return head;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        if (isEmpty(head)){
            return 0;
        }
        if (size(head) == 1){
            initial = operator.operate(initial, head.element);
            return initial;
        }

        ListNode temp = head;
        // check if temp node reached the end of the circular LinkedList by checking if temp.next loops back to the head node
        while (temp.next != head && temp.next != null) {
            System.out.println("initial sum before: " + initial);
            initial = operator.operate(initial, temp.element);
            System.out.println("initial sum after: " + initial);
            temp = temp.next;
        }
        System.out.println("initial sum before: " + initial);
        initial = operator.operate(initial, temp.element);
        System.out.println("initial sum after: " + initial);
        return initial;
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        if (isEmpty(head)){
            return null;
        }

        if (size(head) == 1){
            if (condition.isSatisfied(head.element)){
                System.out.println("unsatisfied element: " + head.element);
                return head;
            }
            else{
                System.out.println("unsatisfied element: " + head.element);
                return null;
            }
        }

        ListNode newList = new ListNode(null);
        ListNode temp = head;
        while (temp.next != head && temp.next != null) {
            if (condition.isSatisfied(temp.element)){
                System.out.println("satisfied element: " + temp.element);
                // make sure that newList isn't null
                if (newList.element == null){
                    newList.element = temp.element;
                }
                else{
                    append(newList, temp);
                }
            }
            else{
                System.out.println("unsatisfied element: " + temp.element);
            }
            temp = temp.next;
        }
        // end of while loop
        // temp is now at the last element, last node also needs to be checked
        if (condition.isSatisfied(temp.element)){
            System.out.println("satisfied element: " + temp.element);
            // make sure that newList isn't null
            if (newList.element == null){
                newList.element = temp.element;
            }
            else{
                append(newList, temp);
            }
        }
        else{
            System.out.println("unsatisfied element: " + temp.element);
        }

        return newList;
    }


}
