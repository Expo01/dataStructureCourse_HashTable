package datastructures.hashtable;

import java.util.ArrayList;


public class HashTable {
    private int size = 7; // defined as 7 for the number of hashcodes (buckets) we want.
    private Node[] dataMap; //array of Nodes

    class Node { //inner class within HashTable class containing key, value and pointer
        String key;
        int value;
        Node next;

        Node(String key, int value) { // pointer not in constructor, assigned later
            this.key = key;
            this.value = value;
        }
    }

    public HashTable() {  //constructor uses size to create the size of the Array of Nodes
        dataMap = new Node[size]; // this is not passing size as a parameter into a node constructor, this is a passing
        // size as defining the length of the array of Nodes 'new Node[]'
    }

    public void printTable() {
        for (int i = 0; i < dataMap.length; i++) {
            System.out.println(i + ":");
            Node temp = dataMap[i];
            while (temp != null) {
                System.out.println("   {" + temp.key + "= " + temp.value + "}");
                temp = temp.next;
            }
        }
    }

    private int hash(String key) {
        int hash = 0;
        char[] keyChars = key.toCharArray();
        for (int i = 0; i < keyChars.length; i++) {
            int asciiValue = keyChars[i];
            hash = (hash + asciiValue * 23) % dataMap.length; // With each loop, hash variable will be redefined
            // as a number between 0 and 6 since leength of the Node[] array 'dataMap' is of length 7 and remainder can
            // only  be 0-6 which correlatese with address space indexes. each char is looped through, further randomizing
            // the value of 'hash' and the final value of
            // 'hash' is returned at end of loop. '23' is again a prime number and used to increase randomization
        }
        return hash;
    } // private since it is only to be accessed through get and set methods

    public void set(String key, int value) { // instance method called from our HashTable object and pass info needed
        // to generate a Node through its constructor
        int index = hash(key); // hash value returned and correlates with index value
        Node newNode = new Node(key, value); // new Node constructed but not assigned location in hash map
        if (dataMap[index] == null) {  // if the hash index has no items
            dataMap[index] = newNode; // then 'head' of linked list in that hash space assigned as the new Node (dataMap[index]
            // points to first Node in array List like 'head' pointer we are familiar with
        } else {
            Node temp = dataMap[index]; // temp variable assigned to what is automatically retrieved a the head of the
            // linked list at the given index
            if (temp.key == key) {
                temp.value += value; //hasn;t explained or demonstrated this but this appears to be in the context of
                // wanting to alter the value associated with a key but not overwrite it, such as in the case of
                // creating an inventory where the key of an item may remain the same, but as items are sold or new
                // invenntory comees in, the value associated with the key will change
                return;
            }
            while (temp.next != null) { // As above.... in the video the set method is like the get method in format
                // except when null pointer found, temp.next set to new Node
                temp = temp.next;
                if (temp.key == key) {
                    temp.value += value;
                    return;
                }
            }
            temp.next = newNode;
        }
    }

    public int get(String key) {
        int index = hash(key); // hash index generated
        Node temp = dataMap[index]; // temp variable set to 'head' of linked list. if the hash index has no Nodes at the index,
        // then temp points to null and the while loop neever runs
        while (temp != null) { // while temp points to a Node loop continues
            if (temp.key == key) return temp.value; // if temp point to key passed as parameter, return the value of
            // that key
            temp = temp.next; // if temp doen't point to a Node with the correct key value, continue traversing the linked
            // list at the hash index until it is found OR
        }
        return 0; //return 0 if key not found
    }

    public ArrayList keys() {
        ArrayList<String> allKeys = new ArrayList<>(); // empty ArrayList of Strings created
        for (int i = 0; i < dataMap.length; i++) { //loops through all indexes of dataMap array of nodes: Node[]
            Node temp = dataMap[i]; // temp assigned to 'head' of linked list at the given hash index
            while (temp != null) { // while temp points to a node in the linked list
                allKeys.add(temp.key); // key of the pointed node added to ArrayList<String>
                temp = temp.next; //loops through linked list until null found
            }
        }
        //although this is a while loop in a for loop, it feels like O(2n) --> O(n) to me since there isn't any redundancy
        // such as with checking for equivalent value betweemn two lists where the nested list is iterrated for each item
        // of the encompassing for loop list
        return allKeys;
    }

}

