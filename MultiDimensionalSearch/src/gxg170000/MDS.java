/** @authors
 * Meetika Sharma - mxs173530
 * Ch Muhammad Talal Muneer - cxm180004
 * Chirag Shahi - cxs180005
 * Gnaneswar Gandu - gxg170000
 * Multi Dimensional Search
 **/

// Change to your net id
package gxg170000;

import java.util.*;

// If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
    // Add fields of MDS here

    private TreeMap<Long, Item> treeMap;
    private HashMap<Long, TreeSet<Item>> table;
    private long size;

    // Information about each Item included in Item Class
    static class Item{
        private Long id;
        private HashSet<Long> description; // used HashSet for distinct descriptions
        private Money price;

        public Item(long id, Money price, List<Long> list){
            this.id = id;
            this.price = price;
            this.description = new HashSet<Long>();
            for(Long l : list){
                this.description.add(l);
            }
        }

        public long getId(){
            return this.id;
        }

        public int compareTo(Item item){
            return this.id.compareTo(item.id);
        }

        public String toString(){
            return "[ id : " + id + ",\t price : " + price.toString() + ",\t description : " + description.toString() + "]";
        }
    }

    // Constructors
    public MDS() {
        this.size = 0;
        this.treeMap = new TreeMap<>();
        this.table = new HashMap<>();
    }

    /** Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(long id, Money price, List<Long> list) {
        if(treeMap.containsKey(id)){
            if(!(list==null || list.isEmpty())){
                deleteInTable(id);
                treeMap.get(id).description.clear();
                for(Long dd : list){
                    treeMap.get(id).description.add(dd);
                }
                insertInTable(id);
            }
            treeMap.get(id).price = price;
            return 0;
        }
        Item newItem = new Item(id,price,list);
        treeMap.put(id, newItem);
        insertInTable(id);
        size++;
        return 1;
    }

    /**
     * Helper method: Inserts descriptions of the Item(id) in table and
     * adds mapping from that description to the HashSet
     * updated with the Item(id), if needed.
     */
    private void insertInTable(Long id){
        Item item = treeMap.get(id);
        for(Long l: item.description){
            if(table.containsKey(l)){
                table.get(l).add(item); // adding/replacing the item
            }
            else{
                TreeSet<Item> newSet = new TreeSet<>();
                newSet.add(item);      // adding first element in TreeSet
                table.put(l, newSet); //inserting the map from l to new TreeSet
            }
        }
    }

    /**
     * Helper method: To remove all the descriptions from table
     * whatever existing descriptions the Item(id) has.
     * Post Condition: need to perform descriptions.clear() from treeMap
     */
    private void deleteInTable(Long id){
        Item item = treeMap.get(id);
        for(Long l : item.description){
            table.get(l).remove(item);
            if(table.get(l).isEmpty()){   // when the TreeSet is emptied
                table.remove(l);
            }
        }
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public Money find(long id) {
        Item item = treeMap.get(id);
        if(item==null) return new Money();
	    return item.price;
    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       long ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public long delete(long id) {
	return 0;
    }

    /* 
       d. FindMinPrice(n): given a long int, find items whose description
       contains that number (exact match with one of the long ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMinPrice(long n) {
	return new Money();
    }

    /* 
       e. FindMaxPrice(n): given a long int, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMaxPrice(long n) {
	return new Money();
    }

    /* 
       f. FindPriceRange(n,low,high): given a long int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(long n, Money low, Money high) {
	return 0;
    }

    /* 
       g. PriceHike(l,h,r): increase the price of every product, whose id is
       in the range [l,h] by r%.  Discard any fractional pennies in the new
       prices of items.  Returns the sum of the net increases of the prices.
    */
    public Money priceHike(long l, long h, double rate) {
	return new Money();
    }

    /*
      h. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public long removeNames(long id, java.util.List<Long> list) {
	return 0;
    }
    
    // Do not modify the Money class in a way that breaks LP3Driver.java
    public static class Money implements Comparable<Money> { 
	long d;  int c;
	public Money() { d = 0; c = 0; }
	public Money(long d, int c) { this.d = d; this.c = c; }
	public Money(String s) {
	    String[] part = s.split("\\.");
	    int len = part.length;
	    if(len < 1) { d = 0; c = 0; }
	    else if(part.length == 1) { d = Long.parseLong(s);  c = 0; }
	    else { d = Long.parseLong(part[0]);  c = Integer.parseInt(part[1]); }
	}
	public long dollars() { return d; }
	public int cents() { return c; }
	public int compareTo(Money other) { // Complete this, if needed
	    return 0;
	}
	public String toString() { return d + "." + c; }
    }

}
