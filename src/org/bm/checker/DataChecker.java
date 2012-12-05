package org.bm.checker;

public interface DataChecker<T> {
   /**
    * @param object
    * @return a CheckError object, or null, if there is no problem. 
    */
   CheckError check(T object);
}
